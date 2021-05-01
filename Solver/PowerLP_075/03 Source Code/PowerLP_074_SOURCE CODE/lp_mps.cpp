//---------------------------------------------------------------------------

#include <vcl.h>
#pragma hdrstop
#include <io.h>
#include <stdio.h>
#include <sys\stat.h>
#include <fcntl.h>
#include <dir.h>
#include <math.h>
#include "common.h"

#include "lp_mps.h"
#include "lp_xaeq.h"

char* breaklines[7] = {"NAME", "ROWS", "COLUMNS", "RHS", "BOUNDS", "ENDATA","RANGES"};

char* movetonext(char* z, ptype p, int* len = NULL);
int parseFloat(char* z, float* value);
int parseStr(char* z, char* str, ptype p=ALPHA);
char* ignorechars(char* z,int* len = NULL);

/**
 *Klasse die *.mps-Dateien ueber einen Parser einliest, ueberprueft und schliesslich ins LP-Modell zur weiteren Verwendung ueberfuehrt
 */
class MPSParser {                        // gehört normalerweise in *.h file !
  char line[2048];
  int file;
  int res;
  bool checkBreakLine();
  int scanRows();
  int scanColumns();
  int scanRHS();
//  int check();
  void delvar(int x);
  void delcs(int x);
//  void finalmapping();
  int getVarIndex(char* varname, bool warn= false);
  int scanBounds();
  int scanRanges();
public:
  int pline;
  LPModell* LPM;                           // Zeiger auf Objekt deklariren
  MPSParser(int pfile, LPModell* pLPM);    // Konstructor
//  void __fastcall toLPM(LPModell* LPM);
  int parse();
};
//------------------------------------------------------------------------------
MPSParser::MPSParser(int pfile, LPModell* pLPM) {       // Konstructor

        // pfile : Name der Datei, die geladen werden soll
        // pLPM  : neues leeres LP - Modell in das die Datei geladen werden soll

  file = pfile;
  LPM = pLPM;
  res = 0;          //    Anzahl Restriktionen
  pline = 0;
}
//------------------------------------------------------------------------------
bool MPSParser::checkBreakLine() {
  for (int x = 0; x < 6; x++) {
  // breaklines[6] = {"NAME", "ROWS", "COLUMNS", "RHS", "BOUNDS", "ENDATA"};
    if (!strcmp(line, breaklines[x])) return true;    // Compares one string to another.
  }
  return false;
}
//------------------------------------------------------------------------------
int MPSParser::scanRows() {
  res = 0;
  while (readLF(file, line, sizeof(line))) {       // holt die nächste Zeile in line
                                                   // aus der Section 'Rows'
    pline ++;                                      // Zeilenzähler
    if (checkBreakLine()) break;        // break condition - sucht die Schlüsselwörter im file
    // first char
    char* z = line;                     // Zeigerarray
    char cname[sizeof(line)];           // cname = der Name der Restriktion (ZF)
    bool cfinished = false;
    while (strlen(z) && !cfinished) {
      bool objective = false;     // Zielfunktions-Kennzeichen
      z = ignorechars(z);         // eliminiert alle Leerstellen vor dem nächsten Element
      if (z[0] == '*') break; // comment
      // check for rowtype
      cname[0] = 0;
      if (z[0] >= 'A' && z[0] <= 'Z' ||   // Buchstabe A-Z oder a-z
          z[0] >= 'a' && z[0] <= 'z') {   
        if (z[0] != 'N' && z[0] != 'E' && z[0] != 'L' && z[0] != 'G')
          return 1; // erster Buchstabe muss N, E, L oder G sein, ansonsten Fehler
        /*
           N - Zielfunktion
           E - Nebenbedingung =
           L - Nebenbedingung <=
           G - Nebenbedingung >=
        */
        char cond = z[0];           // *** Typ der Restriktion ***
        if (cond == 'N') objective = true; // Zielfunktion
        z = movetonext(z, ALPHA);   // geht zum nächsten Eintrag in der Zeile
                                    // Rückgabewert ist die (gekürzte) Zeile
                                    // -> der Name der Restriktion
//        z = ignorechars(z);
        if (parseStr(z,cname) != 1) return 2;          // *** Namen der Restriktion ***
        if (!objective) {                              // keine Zielfunktion
          LPM->setSize(LPM->getVarCount(), res+1);     // hier ist es eine Restriktion
          LPM->setResName(res, cname);
          relation rel = STEQUAL;                // Vorgabewert
          switch (cond) {
            case 'L': rel = STEQUAL; break;
            case 'E': rel = EQUAL;   break;
            case 'G': rel = LTEQUAL; break;
          }
          LPM->setRel(res, rel);
          res++;                       // Restriktion
        } else {
          LPM->setZFName(cname);    // Name der Zielfunktion-Referenz (1. Durchlauf)
                                    // braucht man später wieder für scanColums
        }
      }
      break;
    }
  }
  return 0;
}
//------------------------------------------------------------------------------
int MPSParser::scanColumns() {
  bool integer = false;  // default : keine Ganzzahligkeit
  while (readLF(file, line, sizeof(line))) {
    pline ++;
    if (checkBreakLine()) break; // break condition
    // first char
    char* z = line;
    char vname[sizeof(line)];      // VariablenBezeichner
    char cname[sizeof(line)];
    int varindex = -1;
    while (strlen(z)) {
      z = ignorechars(z);
      if (z[0] == '*') break; // comment
      // check for varname
      vname[0] = 0;
      if (z[0] >= 'A' && z[0] <= 'Z' ||             // erste Element Anfang Buchstabe
          z[0] >= 'a' && z[0] <= 'z') {
        if (parseStr(z,vname) != 1) return 11;    // *** VariablenName ***
        z = movetonext(z, ALPHA);                 // nächster Eintrag
//        varindex = getVarIndex(vname); // marker ident is not a var
      } else return 11;                    // Fehler beim VariablenBez.
      // check for resname
      cname[0] = 0;
      bool iszf = false;
      int resindex = -1;
      if (varindex >= 0 && LPM->getResNameIX(vname) >= 0) {
        // zweite Spalte RESNAME WERT, dann ist vname = Restriktionsbezeichner
        // nach Standard ist die Regel "Varname Resname Wert Resname Wert"
        // es gibt aber auch files mit "Varname Resname Wert Varname Resname Wert"
        if (LPM->isZFName(vname)) iszf = true;      // es handelt sich um einen ZF-Wert
        else resindex = LPM->getResNameIX(vname);   // index Restriktionen
        resindex = LPM->getResNameIX(vname);
        // varindex vom letzten durchlauf
      } else {
        if (z[0] >= 'A' && z[0] <= 'Z' ||        // nächste Element Anfang Buchstabe
            z[0] >= 'a' && z[0] <= 'z') {
          if (parseStr(z,cname) != 1) return 12;  // *** RestriktionenName ***
          z = movetonext(z, ALPHA);              // nächster Eintrag
          if (LPM->isZFName(cname)) iszf = true;      // es handelt sich um einen ZF-Wert
          else resindex = LPM->getResNameIX(cname);   // index Restriktionen
          varindex = getVarIndex(vname);              // index Variablen !!!
        } else {
          z = movetonext(z, ALPHA);          //     'Marker' - wir einfach das
                                             //      naechste geholt
        }
      }
      // check for value                         // *** Variablenwert ***
      float value = 0.0;
      // ist es eine Zahl ?
      if (z[0] >= '0' && z[0] <= '9' || z[0] == '.' || z[0] == '-') {
        if (parseFloat(z, &value) != 1) {return 13;}
        z = movetonext(z, NUMERIC);
      } else {                                    // check for int marker
        if (!parseStr(z, vname)) return 13;      //  in vname einkopieren !
        if (resindex >= 0) return 13;           // darf keinen RestriktionsIndex haben
        if (!stricmp(vname, "'INTORG'")) {      // the same as == 0
                                                // Umschalter Ganzzahligkeit
          integer = true;
          break;
        } else if (!stricmp(vname, "'INTEND'")) {
          integer = false;
          break;
        } else return 16;
      }
      if (varindex < 0) return 14;
      if (iszf) LPM->setZF(varindex, value);  // setzen der Zielfunktionsvar.
      else {
        if (resindex < 0) return 15;
        LPM->setRes(varindex, resindex, value);   // setzen der Restriktionsvar.
                                                  // 2-dim Array
      }
      LPM->setInteger(varindex, integer);    // ganzzahlig
//      break; // next line
    }
  }
  return 0;         // alles gut gegangen !
}
//------------------------------------------------------------------------------
int MPSParser::scanRHS() {
  while (readLF(file, line, sizeof(line))) {
    pline ++;
    if (checkBreakLine()) break; // break condition
    // first char
    char* z = line;
    char vname[sizeof(line)];
    while (strlen(z)) {
      z = ignorechars(z);
      if (z[0] == '*') break; // comment
       vname[0] = 0;                             // *** Bez. des b-Wertes ***
      if (z[0] >= 'A' && z[0] <= 'Z' ||
          z[0] >= 'a' && z[0] <= 'z') {
        if (parseStr(z,vname) != 1) return 21;
        z = movetonext(z, ALPHA);
      } else return 21;
      // check for resname
      vname[0] = 0;                            // *** Bez. der Restriktion ***
      int resindex = -1;
      if (z[0] >= 'A' && z[0] <= 'Z' ||
          z[0] >= 'a' && z[0] <= 'z') {
        if (parseStr(z,vname) != 1) return 22;
        z = movetonext(z, ALPHA);
        if (LPM->isZFName(vname)) return 23;    // Fehler
        else resindex = LPM->getResNameIX(vname);     // Restriktionsindex wird geholt
      } else return 22;
      // check for value
      float value = 0.0;                     // *** Wert des b-Wertes ***
      if (z[0] >= '0' && z[0] <= '9' || z[0] == '.' || z[0] == '-') {
        if (parseFloat(z, &value) != 1) {return 24;}
        z = movetonext(z, NUMERIC);
      } else return 24;
      if (resindex < 0) return 25;
      LPM->setBV(resindex, value);       // !!!! der b-Wert wird im LPM gespeichert
      break; // next line
    }
  }
  return 0;
}
//------------------------------------------------------------------------------
int MPSParser::scanBounds() {
  while (readLF(file, line, sizeof(line))) {
    pline ++;
    if (checkBreakLine()) break; // break condition
    // first char
    char* z = line;
    char vname[sizeof(line)];
    bool boundup = false;
    while (strlen(z)) {
      z = ignorechars(z);
      if (z[0] == '*') break; // comment
      vname[0] = 0;                                   // *** Bound-Typ ***
      if (z[0] >= 'A' && z[0] <= 'Z' ||
          z[0] >= 'a' && z[0] <= 'z') {
        if (parseStr(z,vname) != 1) return 31;
        if (!stricmp(vname, "UP")) boundup = true;       // obere Grenze
        else if (!stricmp(vname, "LO")) boundup = false;   // untere Grenze
        else return 32;
        z = movetonext(z, ALPHA);
      } else return 31;
      vname[0] = 0;                               // *** Bound-Name***
      if (z[0] >= 'A' && z[0] <= 'Z' ||
          z[0] >= 'a' && z[0] <= 'z') {
        if (parseStr(z,vname) != 1) return 33;
        z = movetonext(z, ALPHA);
      } else return 33;
      vname[0] = 0;                              // *** Variablen-Name****
      int varindex = -1;
      if (z[0] >= 'A' && z[0] <= 'Z' ||
          z[0] >= 'a' && z[0] <= 'z') {
        if (parseStr(z,vname) != 1) return 34;
        z = movetonext(z, ALPHA);
        varindex = LPM->getVarNameIX(vname);      // hole Index der Variablen
      } else return 34;
      // check for value
      float value = 0.0;
      if (z[0] >= '0' && z[0] <= '9' || z[0] == '.' || z[0] == '-') {
        if (parseFloat(z, &value) != 1) {return 35;}
        z = movetonext(z, NUMERIC);     // ab hier z="" raus aus der while()
      } else return 35;
      if (varindex < 0) return 36;
      if (boundup) LPM->setBounds(varindex, LPM->getLBound(varindex), value);     // !!! Setzen ins LPM
      else LPM->setBounds(varindex, value, LPM->getHBound(varindex));
      break; // next line
    }
  }
  return 0;
}
//------------------------------------------------------------------------------
int MPSParser::scanRanges() {
  while (readLF(file, line, sizeof(line))) {
    pline ++;
    if (checkBreakLine()) break; // break condition
  }
  return 0;
}
//------------------------------------------------------------------------------
int MPSParser::getVarIndex(char* varname, bool warn) {
  int ix = LPM->getVarNameIX(varname);
  if (ix >= 0) return ix;
  int vars = LPM->getVarCount();       // gibt's noch nicht, weiter ...
  LPM->setSize(vars+1, LPM->getResCount());
  if (warn) {
    char msg[1000];
    sprintf(msg, "Warnung: Neue Variable %s, füge sie mit Gewichtung 0 zur Zielfunktion.",varname);
    LPM->PutError(msg);
  }
  LPM->setVarName(vars, varname);
  LPM->setZF(vars, 0.0);
  return vars;
}
//------------------------------------------------------------------------------
int MPSParser::parse() {
  int rval = 0;
  bool rowsfound = false;
  bool rhsfound = false;     // b-wert
  bool colsfound = false;
  LPM->setSize(0,0);
  while (readLF(file, line, sizeof(line))) {       /* line hat das Format char[2048]
                                                   die while-Schleife liest das file zeilenweise
                                                   aus, bis ENDDATA, dann wird die Schleife mit Break
                                                   verlassen oder bread = 0 */
  pline ++;          // zaehlt die Zeilen mit
    bool goback = true;
    while (goback) {
      goback = false;
      if (strstr(line, "NAME")) {           // Scans a string for the occurrence of a given substring
                                            // in line steht immer eine Zeile des *.mps - files
        LPM->setMinimize(false); // default
        if ( strstr(line, "MINIMIZE") ) LPM->setMinimize(true);  // Minimierung-Problem
        else if (strstr(line, "MAXIMIZE")) LPM->setMinimize(false); // Maximierungs-Problem
        else LPM->PutError("Richtung der Optimierung (Minimierung/Maximierung) in MPS-Datei nicht angegeben.");
      }
      if (strstr(line, "ROWS")) {   // Scans a string for the occurrence of a given substring
        rval = scanRows();       // macht alle Zeilen durch
        goback = true;
        rowsfound = true;
      }
      if (!stricmp(line, "COLUMNS")) {        // the same as == 0
        rval = scanColumns();
        goback = true;
        colsfound = true;
      }
      if (!stricmp(line, "RHS")) {
        rval = scanRHS();
        goback = true;
        rhsfound = true;
      }
      if (!stricmp(line, "BOUNDS")) {
        rval = scanBounds();
        goback = true;
      }
      if (!stricmp(line, "ENDATA")) {
        break;
      }
      if (!stricmp(line, "RANGES")) {
        // scan Ranges
        rval = scanRanges();
        goback = true;
      }
    }
    if (rval) {           // rval = 0
      char msg[1000];
      sprintf(msg, "Parser-Fehler bei Linie %i: %s", pline, line);
      LPM->PutError(msg);
      return rval;
    }
      // ... naechste Zeile einlesen ...
  }
  if (!rowsfound || !rhsfound || !colsfound) {
//    LPM->PutError("Fehler: Diese Datei ist keine MPS Datei.");
    return 101;
  }
//  check();
//  finalmapping();
  return 0;
}
//------------------------------------------------------------------------------
char* floatToStrENDOT(char* str, float value) {
  float absv = fabs(value);
  if (absv > 999999) sprintf(str, "%f", value);
  else if (absv > 0 && absv < 0.0001) sprintf(str, "%.9f", value);
  else sprintf(str, "%g", value);
  if (!strchr(str,'.')) strcat(str, ".");
  return str;
}
//------------------------------------------------------------------------------
bool __fastcall GetLPfromMPSFile(LPModell* LPM, char* filename) {
  int f = open(filename, O_RDONLY | O_BINARY, S_IREAD);   //
  int rval = 0;            // Rückgabewert
  if (f < 0) return false; // Fehlermeldung hierfuer wird ausgegeben

  MPSParser* mpsp = new MPSParser(f, LPM);    // Objekt von Parser wird angelegt
  rval = mpsp->parse();
  switch (rval) {
//    case -1:LPM->PutError("Fehler: Das Parsen war nicht möglich."); break;
    case 0: /* LPM->PutError("SUCCESS."); */ break;
    case 1: LPM->PutError("Fehler: parsen der ROW-Sektion [falscher Typ]"); break;
    case 2: LPM->PutError("Fehler: parsen der ROW-Sektion [ungültiger Variablenbezeichner]"); break;
    case 11: LPM->PutError("Fehler: parsen der COLUMNS-Sektion [Variablenbezeichner]"); break;
    case 12: LPM->PutError("Fehler: parsen der COLUMNS-Sektion [Restriktionsbezeichner]"); break;
    case 13: LPM->PutError("Fehler: parsen der COLUMNS-Sektion [Wert]"); break;
    case 14: LPM->PutError("Fehler: parsen der COLUMNS-Sektion [Unbekannter Variablenbezeichner]"); break;
    case 15: LPM->PutError("Fehler: parsen der COLUMNS-Sektion [Unbekannter Restriktionsbezeichner]"); break;
    case 16: LPM->PutError("Fehler: parsen der COLUMNS-Sektion [Unbekannter Marker]"); break;
    case 21: LPM->PutError("Fehler: parsen der RHS-Sektion [Name]"); break;
    case 22: LPM->PutError("Fehler: parsen der RHS-Sektion [Restriktionsbezeichner]"); break;
    case 23: LPM->PutError("Fehler: parsen der RHS-Sektion [ZF in RHS]"); break;
    case 24: LPM->PutError("Fehler: parsen der RHS-Sektion [Wert]"); break;
    case 25: LPM->PutError("Fehler: parsen der RHS-Sektion [Unbekannter Restriktionsbezeichner]"); break;
    case 31: LPM->PutError("Fehler: parsen der BOUNDS-Sektion [Typ]"); break;
    case 32: LPM->PutError("Fehler: parsen der BOUNDS-Sektion [falscher Typ]"); break;
    case 33: LPM->PutError("Fehler: parsen der BOUNDS-Sektion [Name]"); break;
    case 34: LPM->PutError("Fehler: parsen der BOUNDS-Sektion [Variablenbezeichner]"); break;
    case 35: LPM->PutError("Fehler: parsen der BOUNDS-Sektion [Wert]"); break;
    case 36: LPM->PutError("Fehler: parsen der BOUNDS-Sektion [Unbekannter Variablenbezeichner]"); break;
    case 101:
//      LPM->PutError("Fehler: mindestens 1 Sektion (ROWS, COLUMNS, RHS) fehlt.");
    break;
    default: LPM->PutError("Fehler: unbekannt"); break;
  }
  close(f);
  delete mpsp;
  if (rval) return false;   // nicht das richtige Dateiformat
  return true;
}
//------------------------------------------------------------------------------
bool __fastcall PutLPtoMPSFile(LPModell* LPM, char* filename, bool boundssupport) {
  int f = OpenCreateFile(LPM, filename);
  if (f < 0) return false;
  // Grenz-Bedingungen zaehlen fuer den Fall das boundssupport = false
  int boundsres = 0;
  writeStr(f, "NAME          Power-LP ");       // mps - Power-LP - Dateien
  if (LPM->getMinimize()) writeLF(f, "MINIMIZE");
  else writeLF(f, "MAXIMIZE");
  int vars= LPM->getVarCount();     // Anzahl Variablen
  int res = LPM->getResCount();     // Anzahl Restriktionen
  char str[200];
  char rs[30];
  writeLF(f, "ROWS");       //  --- ROWS - Section ---
  writeLF(f, " N  ZF");     // Zielfunktionszeile
  for (int x = 0; x<res; x++) {
    char* pstr = "";
    switch (LPM->getRel(x)) {        // eine Restriktion nach der anderen 
      case EQUAL:   pstr = "E"; break;
      case LTEQUAL: pstr = "G"; break;
      case STEQUAL: pstr = "L"; break;
      case LARGER:  pstr = "G"; break;
      case SMALLER: pstr = "L"; break;
    }
    writeStr(f," ");
    writeStr(f,pstr);
    writeStr(f,"  ");
    sprintf(str, "R%i", x+1);
    writeLF(f,str);
  }
  if (!boundssupport) {        
    for (int x = 0; x<vars; x++) {
      float lb = LPM->getLBound(x);     // lower bound
      if (lb != 0) {
        boundsres++;
        sprintf(str, " G  RB%i", boundsres);
        writeLF(f,str);                    // schreiben als Restriktion
      }
      float hb = LPM->getHBound(x);         // higher bound
      if (hb != 0) {
        boundsres++;
        sprintf(str, " L  RB%i", boundsres);
        writeLF(f,str);             // schreiben als Restriktionen
      }
    }
  }
  writeLF(f, "COLUMNS");     // --- COLUMNS - Section ---
  bool intmode = false;
  int markerno = 0;
  boundsres = 0;
  for (int x = 0; x<vars; x++) {         // geht alle Variablen des LP durch
    float value = LPM->getZF(x);         // *** Zielfunktion ***
    char cvar[30];
    sprintf(str,"X%i", x+1);
    sprintf(cvar, "%-010s", str);
    if (value != 0.0) {                  // *** Ganzzahligkeit ***
      if (LPM->getInteger(x) != intmode) {  // Ganzzahligkeit für diese Variable
        sprintf(str, "    MARK%04i  'MARKER'                 ", markerno);
        markerno++;
        writeStr(f,str);
        if (!intmode) writeLF(f,"'INTORG'");    // Anfang
        else         writeLF(f,"'INTEND'");     // Ende
        intmode = !intmode;
      }
      writeStr(f, "    ");
      writeStr(f,cvar);
      writeStr(f,"ZF        ");
      floatToStrENDOT(str, value);
      writeLF(f,str);
    }     // if (value != 0.0)
                                       // für die Variable  ...
    for (int y = 0; y < res; y++) {   // alle Restriktionen durchlaufen
      float value = LPM->getRes(x,y);    // 2-dimensionales Array
      if (value != 0.0) {
        if (LPM->getInteger(x) != intmode) {
          sprintf(str, "    MARK%04i  'MARKER'                 ", markerno);
          markerno++;
          writeStr(f,str);
          if (!intmode) writeLF(f,"'INTORG'");
          else         writeLF(f,"'INTEND'");
          intmode = !intmode;
        }
        writeStr(f,"    ");
        writeStr(f,cvar);
        sprintf(rs, "R%i",y+1);
        sprintf(str, "%-010s", rs);
        writeStr(f,str);
        floatToStrENDOT(str, LPM->getRes(x,y));
        writeLF(f,str);
      }
    }
    // bounds                      // ... nach den Restriktionen
    if (!boundssupport) {         // kein Bounds-Support
      float lb = LPM->getLBound(x);        // wenn die Var. Grenzen hat
      if (lb != 0) {
        boundsres++;
        writeStr(f,"    ");
        writeStr(f,cvar);
        sprintf(rs,"RB%i",boundsres);
        sprintf(str,"%-010s", rs);
        writeStr(f, str);
        writeLF(f,"1.");
      }
      float hb = LPM->getHBound(x);
      if (hb != 0) {
        boundsres++;
        writeStr(f,"    ");
        writeStr(f,cvar);
        sprintf(rs, "RB%i",boundsres);
        sprintf(str,"%-010s", rs);
        writeStr(f, str);
        writeLF(f,"1.");
      }
    }
  }        // for (int x = 0; x<vars; x++)  - Ende

  // für die letzte Variable
  if (intmode) {
    sprintf(str, "    MARK%04i  'MARKER'                 'INTEND'", markerno);
    writeLF(f,str);
  }

  writeLF(f, "RHS");     // --- RHS-Section (b-Wert) ---
  for (int x = 0; x<res; x++) {      // alle Restriktionen durchlaufen
    float value = LPM->getBV(x);     // die b-Werte holen
    if (value != 0.0) {
      sprintf(rs, "R%i", x+1);
      sprintf(str,"    MYRHS     %-010s",rs);
      writeStr(f,str);                 // // schreiben
      floatToStrENDOT(str, LPM->getBV(x));
      writeLF(f,str);
    }
  }
  // BOUNDS              //  ... nach den Restriktionen
  if (!boundssupport) {
    boundsres = 0;
    for (int x = 0; x<vars; x++) {
      float lb = LPM->getLBound(x);    // welche Variable hat Grenzen ?
      if (lb != 0) {
        boundsres++;
        sprintf(rs, "RB%i", boundsres);
        sprintf(str,"    MYRHS     %-010s",rs);
        writeStr(f,str);
        floatToStrENDOT(str, lb);
        writeLF(f,str);
      }
      float hb = LPM->getHBound(x);
      if (hb != 0) {
        boundsres++;
        sprintf(rs, "RB%i", boundsres);
        sprintf(str,"    MYRHS     %-010s",rs);
        writeStr(f,str);
        floatToStrENDOT(str, hb);
        writeLF(f,str);
      }
    }
  } else {                     // --- Bounds-Section ---
      bool bound = false;
    for (int x = 0; x<vars; x++) {
      float lb = LPM->getLBound(x);
      float hb = LPM->getHBound(x);
      if (lb != 0.0 || hb != 0.0) {
        if (!bound) writeLF(f, "BOUNDS");
        bound = true;
      }
      sprintf(rs, "X%i", x+1);
      if (lb != 0.0) {
        sprintf(str, " LO MYBOUND   %-010s",rs);   // untere Grenze
        writeStr(f,str);
        floatToStrENDOT(str, lb);
        writeLF(f, str);
      }
      if (hb != 0.0) {
        sprintf(str, " UP MYBOUND   %-010s",rs);  // obere Grenze
        writeStr(f,str);
        floatToStrENDOT(str, hb);
        writeLF(f, str);
      }
    }
  }
  writeLF(f, "ENDATA");     // --- ENDDATA - Section ---
  close(f);
  return true;
}
#pragma package(smart_init)
