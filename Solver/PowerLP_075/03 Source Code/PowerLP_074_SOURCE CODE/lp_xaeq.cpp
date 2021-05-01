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
#include "lp_xaeq.h"
#define MAXVARNAME 30 // Maximale Lange eines Variablenbezeichners
#define MAXVAR 1000 // maximale Anzahl an Variablen
#define MAXRES 1000 // maximale Anzahl an Restriktionen

/**
 *Klasse die *.lp-Dateien im XA Equation Style ueber einen Parser einliest, ueberprueft und schliesslich ins LP-Modell zur weiteren Verwendung ueberfuehrt
 */
class XAParser {
  char line[2048];
  int file;
  char variables[MAXVAR][MAXVARNAME];
  float factorszf[MAXVAR];
  int nbs;
  float factornb[MAXVAR][MAXRES];
  float factornbb[MAXRES];
  float lbound[MAXVAR];
  float hbound[MAXVAR];
  relation nbcond[MAXVAR];
  bool maximize;
  bool integer[MAXVAR];
  int vars;
  int scanObjective();
  int scanBounds();
  int scanConstraints();
  int getVarIndex(char* varname, char* cname);
  int check();
  void delvar(int x);
  void delcs(int x);
  void finalmapping();
  ThrowErrorMsg* emsg;
public:
  int pline;
  XAParser(int pfile, ThrowErrorMsg* lpm);
  void __fastcall toLPM(LPModell* LPM);
  int parse();
};
// typedef enum ptype {ALPHA, NUMERIC, SIGN, OPERATOR} ptype;
//---------------------------------------------------------------------------
void __fastcall XAParser::toLPM(LPModell* LPM) {
  LPM->setSize(vars, nbs);
  LPM->setMinimize(!maximize);
  for (int x = 0; x < vars; x++) {
    LPM->setBounds(x, lbound[x], hbound[x]);
    LPM->setInteger(x, integer[x]);
    LPM->setZF(x, factorszf[x]);
    for (int y = 0; y < nbs; y++) {
      LPM->setRes(x,y, factornb[y][x]);

    }
  }
  for (int x = 0; x < nbs; x++) {
    LPM->setRel(x, nbcond[x]);
    LPM->setBV(x, factornbb[x]);
  }
}
//------------------------------------------------------------------------------
XAParser::XAParser(int pfile, ThrowErrorMsg* pemsg) {
  file = pfile;
  vars = 0;
  emsg = pemsg;
  nbs = 0;
  pline = 0;
  memset(lbound, 0, sizeof(lbound));
  memset(hbound, 0, sizeof(hbound));
  memset(variables, 0, sizeof(variables));
  memset(integer, 0, sizeof(integer));
  memset(factornb,0, sizeof(factornb));
  memset(factornbb, 0, sizeof(factornbb));
}
//------------------------------------------------------------------------------
char* ignorechars(char* z,int* len = NULL) {
  while (z[0]) {
    switch (z[0]) {
      case ' ': z++; if (len) ++*len; break;
      case '(': z++; if (len) ++*len; break;
      case ')': z++; if (len) ++*len; break;
      default: return z;
    }
  }
  return z;
}
//------------------------------------------------------------------------------
char* movetonext(char* z, ptype p, int* len = NULL) {
  bool firstchar = true;
  while (z[0]) {
    char* oldz = z;
    z = ignorechars(z);
    if (z!=oldz) return z;  // at next
    switch (p) {
      case ALPHA:
      if (!(z[0] >= 'A' && z[0] <= 'Z' ||
            z[0] >= 'a' && z[0] <= 'z' ||
            z[0] >= '0' && z[0] <= '9' || z[0] == '\''
            || (!firstchar && ( z[0] == '.' || z[0] == '-') )
            ))
            return z;
      break;
      case NUMERIC:
        if ((z[0] < '0' || z[0] > '9') && z[0] != '.') {
          if (!(firstchar && z[0] == '+' || z[0] == '-')) return z;
        }
      break;
      case SIGN:
        if (z[0] != '+' && z[0] != '-') return z;
      break;
      case OPERATOR:
        if (z[0] != '<' && z[0] != '>' && z[0] != '=') return z;
      break;
    }
    z++;
    if (len) ++*len;
    firstchar = false;
  }
  z += strlen(z);
  if (len) *len+= strlen(z);
  return z;
}
//------------------------------------------------------------------------------
int parseStr(char* z, char* str, ptype p=ALPHA) {
  int len = 0;
  z = ignorechars(z,&len);
  movetonext(z, p, &len);   //  die Länge ist wichtig !
  if (len > 0) strpncpy(str, z, len+1);
  else return 0;
  return 1;
}
//------------------------------------------------------------------------------
int parseFloat(char* z, float* value) {
  char tmp[2048];
  strcpy(tmp, z);
  strreplace(tmp,",",".");
  if (z[0] == '.') {
    strcpy(&tmp[1],z);
    tmp[0] = '0';
  }
  return sscanf(z,"%f", value);
}
//------------------------------------------------------------------------------
int XAParser::scanObjective() {
  int cvar = 0;
  bool cint = false;
  while (readLF(file, line, sizeof(line))) {
    pline ++;
    if (!memcmp(line, "..",2)) break; // break condition
    // first char
    char* z = line;
    float factor = 1;
    while (strlen(z)) {
      z = ignorechars(z);
      if (z[0] == '*') break; // comment
      bool fs = false;
      float sign = 1.0;
      char varname[sizeof(line)];
      varname[0] = 0;
      // check for integer brackets
      if (z[0] == '[') { cint = true;  z++;fs =true;}
      if (z[0] == ']') { cint = false; z++;fs =true;}
      // check for sign
      if (z[0] == '+') { sign = 1.0; z++; fs =true;}
      if (z[0] == '-') { sign = -1.0; z++;fs =true;}
      z = ignorechars(z);
      // check for number
      if (z[0] >= '0' && z[0] <= '9' || z[0] == '.') {
        if (parseFloat(z, &factor) != 1) {return 2;}
        z = movetonext(z, NUMERIC);
        fs =true;
      }
      z = ignorechars(z);
      // check for varname
      if (z[0] >= 'A' && z[0] <= 'Z' ||
          z[0] >= 'a' && z[0] <= 'z') {
        if (parseStr(z,varname) != 1) {return 3;}
        z = movetonext(z, ALPHA);
        fs =true;
      };
      // here we got variable:
      if (!varname[0]) {
//        emsg->PutError("WARNING (%i): ignoring constant %g in objective\n",pline, sign*factor);
        if (!fs) z++;
      } else {
        factorszf[cvar] = sign*factor;
        if (!varname[0]) return 4;
        strpncpy(variables[cvar], varname, MAXVARNAME);
        integer[cvar] = cint;
        if (cint) hbound[cvar] = 1; // set default 0, 1 integer
        cvar++;
      }
    }
  }
  vars = cvar;
  printf("scanned %i variables in objective function:\n",vars);
  for (int x = 0; x<cvar; x++) {
    if (factorszf[x] != 0.0)
      printf("%+g*%s ", factorszf[x], variables[x]);
  }
  printf("\n");
  return 0;
}
//------------------------------------------------------------------------------
int XAParser::scanBounds() {
  int cvar = 0;
  while (readLF(file, line, sizeof(line))) {
    pline ++;
    if (!memcmp(line, "..",2)) break; // break condition
    // first char
    char* z = line;
    while (strlen(z)) {
      z = ignorechars(z);
      if (z[0] == '*') break; // comment
      char varname[sizeof(line)];
      varname[0] = 0;
      char cname[sizeof(line)];
      // check for bound constraintname
      cname[0] = 0;
      if (z[0] >= 'A' && z[0] <= 'Z' ||
          z[0] >= 'a' && z[0] <= 'z') {
        if (parseStr(z,cname) != 1) {return 5;}
        char* dotpos = strchr(z,':');
        if (dotpos) z = ignorechars(dotpos+1);
        else cname[0] = 0;
      }
      // check for varname
      if (z[0] >= 'A' && z[0] <= 'Z' ||
          z[0] >= 'a' && z[0] <= 'z') {
        if (parseStr(z,varname) != 1) {return 3;}
        z = movetonext(z, ALPHA);
      };
      z = ignorechars(z);
      float boundl = 0;
      float boundh = 0;
      // check for relation and value
      char cconds[sizeof(line)];
      if (z[0] == '>' || z[0] == '<' || z[0] == '=') {
        if (parseStr(z,cconds,OPERATOR) != 1) {return 8;}
        z = movetonext(z, OPERATOR);
        float bound = 0;
        if (parseFloat(z, &bound) != 1) {return 9;}
        z = movetonext(z, NUMERIC);
        if (!strcmp(cconds, "<=")) boundh = bound;
        if (!strcmp(cconds, ">=")) boundl = bound;
      }
      // check for second relation and value
      if (z[0] == '>' || z[0] == '<' || z[0] == '=') {
        if (parseStr(z,cconds,OPERATOR) != 1) ;
        z = movetonext(z, OPERATOR);
        float bound = 0;
        if (parseFloat(z, &bound) != 1) ;
        z = movetonext(z, NUMERIC);
        if (!strcmp(cconds, "<=")) boundh = bound;
        if (!strcmp(cconds, ">=")) boundl = bound;
      }
      z = ignorechars(z);
      // here we got variable:
      if (!varname[0]) {
        emsg->PutError("Warnung: Fehlen beim Parsen der Grenzen");
        z++;
      } else {
        int index = getVarIndex(varname,"");
        lbound[index] = boundl;
        hbound[index] = boundh;
        cvar++;
      }
    }
  }
  if (!cvar) return 0;
  for (int x = 0; x<vars; x++) {
    if (lbound[x] != 0.0 || hbound[x] != 0)
      printf("%g <= %s <= %g\n", lbound[x], variables[x], hbound[x]);
  }
  printf("\n");
  return 0;
}
//------------------------------------------------------------------------------
int XAParser::getVarIndex(char* varname, char* cname) {
  for (int x = 0; x<vars; x++) {
    if (!strcmp(variables[x], varname)) return x;
  }
  vars++;
  char msg[1000];
  sprintf(msg, "Warnung: Neue Variable %s in Restriktion %s gefunden, füge sie mit Gewichtung 0 zur Zielfunktion.",varname,cname);
  emsg->PutError(msg);
  strpncpy(variables[vars], varname, MAXVARNAME);
  factorszf[vars] = 0;
  return vars;
}
//------------------------------------------------------------------------------
int XAParser::scanConstraints() {
  int cvar = 0;
  bool firstcondition = true;
  while (readLF(file, line, sizeof(line))) {
    pline ++;
    if (!memcmp(line, "..",2)) break; // break condition
    // first char
    char* z = line;
    char cname[sizeof(line)];
    bool cfinished = false;
    while (strlen(z) && !cfinished) {
      z = ignorechars(z);
      if (z[0] == '*') break; // comment
      // check for constraintname
      cname[0] = 0;
      if (z[0] >= 'A' && z[0] <= 'Z' ||
          z[0] >= 'a' && z[0] <= 'z') {
        if (parseStr(z,cname) != 1) {return 5;}
        char* dotpos = strchr(z,':');
        if (dotpos) z = ignorechars(dotpos+1);
        else cname[0] = 0;
      }
      // check for sign
      float sign = 1.0;
      if (z[0] == '+') { sign = 1.0; z++;}
      if (z[0] == '-') { sign = -1.0; z++; }
      z = ignorechars(z);
      // check for number
      float factor = 1;
      if (z[0] >= '0' && z[0] <= '9' || z[0] == '.') {
        if (parseFloat(z,&factor) != 1) {return 6;}
        z = movetonext(z, NUMERIC);
      }
      char varname[sizeof(line)];
      varname[0] = 0;
      z = ignorechars(z);
      // check for varname
      if (z[0] >= 'A' && z[0] <= 'Z' ||
          z[0] >= 'a' && z[0] <= 'z') {
        if (parseStr(z,varname) != 1) {return 7;}
        z = movetonext(z, ALPHA);
      }
      // here we must have a variable:
//      if (!varname[0]) return 10;
      if (varname[0]) {
        firstcondition = true; // new constraint
        int index = getVarIndex(varname,cname);
        factornb[cvar][index] = sign*factor;
      }
      if (z[0] == '>' || z[0] == '<' || z[0] == '=') cfinished = true;
    }
    while (strlen(z)) {
      // check for end of constraint
      char cconds[sizeof(line)];
      if (z[0] == '>' || z[0] == '<' || z[0] == '=') {
        if (parseStr(z,cconds,OPERATOR) != 1) {return 8;}
        z = movetonext(z, OPERATOR);
        float nbb = 0;
        if (parseFloat(z, &nbb) != 1) {return 9;}
        z = movetonext(z, NUMERIC);
        enum relation nbc;
        nbc = LTEQUAL;
        if (!strcmp(cconds, "<=")) nbc = STEQUAL;
        if (!strcmp(cconds, ">=")) nbc = LTEQUAL;
        if (!strcmp(cconds, ">")) nbc = LARGER;
        if (!strcmp(cconds, "<")) nbc = SMALLER;
        if (!strcmp(cconds, "=")) nbc = EQUAL;
        factornbb[cvar] = nbb;
        nbcond[cvar] = nbc;
        if (!firstcondition) {
          // simply copy last constraint values and add new condition set
          for (int x = 0; x < vars; x++) {
            factornb[cvar][x] = factornb[cvar-1][x];
          }
        }
        firstcondition = false;
        cvar++;
      } else {
        break;
      }
    }
    /*
    if (cfinished) {
      for (int x = 0; x<vars; x++) {
        if (factornb[cvar][x] != 0.0)
          printf("%+g*%s ", factornb[cvar][x], variables[x]);
      }
      char tmp[5];
      memcpy(tmp, &(nbcond[cvar]), 2);
      tmp[2] = 0;
      printf("%s %g", tmp, factornbb[cvar]);
      printf("\n");
    } // */
  }
  nbs = cvar;
  return 0;
}
//------------------------------------------------------------------------------
void XAParser::delvar(int x) {
      for (int i = x; i<vars; i++) {
        for (int j= 0; j<nbs; j++) {
          factornb[j][i] = factornb[j][i+1];
        }
        factorszf[i] = factorszf[i+1];
        integer[i] = integer[i+1];
        strpncpy(variables[i], variables[i+1], MAXVARNAME);
        lbound[i] = lbound[i+1];
        hbound[i] = hbound[i+1];
      }
      vars--;
}
//------------------------------------------------------------------------------
void XAParser::delcs(int x) {
  for (int j= x; j<nbs; j++) {
    for (int i = 0; i<vars; i++) {
       factornb[j][i] = factornb[j+1][i];
    }
    factornbb[j] = factornbb[j+1];
    nbcond[j] = nbcond[j+1];
  }
  nbs--;
}
//------------------------------------------------------------------------------
int XAParser::check() {
  // check for empty vars
  for (int x = 0; x < vars ;x++) {
    int appearance = 0;
    for (int y = 0; y<nbs; y++) {
      if (factornb[y][x] != 0.0) appearance ++;
    }
    if (!appearance) {
      char msg[1000];
      sprintf(msg,"Warnung: Variable %s ist ungültig / erscheint in keiner Nebenbedingung.",variables[x]);
      emsg->PutError(msg);
      delvar(x);
      x--;
    }
  }
  return 0;
}
//------------------------------------------------------------------------------
void XAParser::finalmapping() {
  printf("- final variable mapping:\n");
  for (int x = 0; x < vars; ) {
    int y = 0;
    for (; y < 15; y++) {
      char msg[10];
      sprintf(msg,"X%02i",x+1);
      printf("%05s", msg);
      x++;
      if (x >= vars) { y++; break;}
    }
    x-= y;
    printf("\n");
    for (int y = 0; y < 15; y++) {
      printf("%05s",variables[x]);
      x++;
      if (x > vars) break;
    }
    printf("\n");
    printf("\n");
  }
}
//------------------------------------------------------------------------------
int XAParser::parse() {
  int rval = 0;
  bool objfound = false;
  bool csfound = false;
  while (readLF(file, line, sizeof(line))) {
    pline ++;
    bool goback = true;
    while (goback) {
      goback = false;
      if (!stricmp(line, "..TITLE")) {
        readLF(file, line, sizeof(line));
        pline ++;
        printf("Titel: %s\n", line);
//        goback = true;
      }
      if (strstr(line, "..OBJECTIVE")) {
        if (strstr(line, "MINIMIZE")) maximize = false;
        else maximize = true;
        rval = scanObjective();
        goback = true;
        objfound = true;
      }
      if (!stricmp(line, "..BOUNDS")) {
        scanBounds();
        goback = true;
      }
      if (!stricmp(line, "..CONSTRAINTS")) {
        rval = scanConstraints();
        goback = true;
        csfound = true;
      }
    }
    if (rval) {
      char msg[1000];
      sprintf(msg, "Parser-Fehler bei Linie %i: %s", pline, line);
      emsg->PutError(msg);
      return rval;
    }
  }
  if (!objfound || !csfound) {
//    emsg->PutError("Fehler: Diese Datei ist keine XA-LP Datei.");
    return -1;
  }
  check();
  finalmapping();
  return 0;
}
//---------------------------------------------------------------------------
bool __fastcall GetLPfromXAEQFile(LPModell* LPM, char* filename) {
  int f = open(filename, O_RDONLY | O_BINARY, S_IREAD);
  if (f < 0) return false;
  XAParser* xap = new XAParser(f, LPM);
  int rval = xap->parse();
  switch (rval) {
    case -1:/*LPM->PutError("ERROR: parsing failed completely.\n");*/ break;
    case 0: /* LPM->PutError("SUCCESS.\n"); */ break;
    case 1: LPM->PutError("ERROR: source file cannot be opened\n"); break;
    case 2: LPM->PutError("ERROR: while parsing objective\n"); break;
    case 3: LPM->PutError("ERROR: while parsing objective\n"); break;
    case 4: LPM->PutError("ERROR: while parsing objective\n"); break;
    case 5: LPM->PutError("ERROR: while parsing constraints [constraint name]\n"); break;
    case 6: LPM->PutError("ERROR: while parsing constraints [wrong number format]\n"); break;
    case 7: LPM->PutError("ERROR: while parsing constraints [variable]\n"); break;
    case 8: LPM->PutError("ERROR: while parsing constraints [no constraint relation found]\n"); break;
    case 9: LPM->PutError("ERROR: while parsing constraints [no constraint value found]\n"); break;
    case 10: LPM->PutError("ERROR: while parsing constraints [no variable found]\n"); break;
    default: LPM->PutError("ERROR: unknown error\n"); break;
  }
  close(f);
  if (rval) return false;
  xap->toLPM(LPM);
  delete xap;
  return true;
}
//------------------------------------------------------------------------------
char* floatToStrEN(char* str, float value) {
  float absv = fabs(value);
  if (absv > 999999) sprintf(str, "%f", value);
  else if (absv > 0 && absv < 0.0001) sprintf(str, "%.20f", value);
  else sprintf(str, "%g", value);
  return str;
}
//------------------------------------------------------------------------------
bool __fastcall PutLPtoXAEQFile(LPModell* LPM, char* filename) {
  int f = OpenCreateFile(LPM, filename);
  if (f < 0) return false;
  writeLF(f, "..TITLE\r\n  Power-LP");

  if (LPM->getMinimize()) writeLF(f, "..OBJECTIVE MINIMIZE");
  else writeLF(f, "..OBJECTIVE MAXIMIZE");

  int vars= LPM->getVarCount();
  int res = LPM->getResCount();
  AnsiString zf;
  char str[200];
  write(f, "  ",2);
  bool integer = false;
  int colwidth = 2;
  for (int x = 0; x < vars ;x++) {
    if (colwidth > 70) {
      colwidth = 2;
      writeLF(f, "");
      writeStr(f, "  ");
    }
    float zfvalue = LPM->getZF(x);
    if (integer != LPM->getInteger(x)) {
      integer = !integer;
      if (integer) write(f, "[",1);
      else write(f, "]",1);
      colwidth ++;
    }
    if (zfvalue < 0) {
      write(f, "- ",2); zfvalue = -zfvalue;
    } else {
      write(f, "+ ",2);
    }
    colwidth += 2;
    floatToStrEN(str, zfvalue);
    strcat(str, " ");
    colwidth += write(f, str, strlen(str));
    sprintf(str, "x%i", x+1);
    strcat(str, " ");
    colwidth += write(f, str, strlen(str));
  }
  if (integer) write(f, "]",1);
  writeLF(f, "");
  writeLF(f, "..BOUNDS");
  for (int x = 0; x < vars ;x++) {
    write(f, "  ",2);
    sprintf(str, "x%i", x+1);
    strcat(str, " >= ");
    write(f, str, strlen(str));
    floatToStrEN(str, LPM->getLBound(x));
    write(f, str, strlen(str));
    float hbound = LPM->getHBound(x);
    if (hbound > 0) {
      write(f," <= ",4);
      floatToStrEN(str, hbound);
      write(f, str, strlen(str));
    }
    writeLF(f,"");
  }
  writeLF(f, "..CONSTRAINTS");
  for (int x = 0; x<res; x++) {
    write(f, "  ", 2);
    int varcount = 0;
    int colwidth = 2;
    for (int y = 0; y<vars; y++) {
      if (colwidth > 70) {
        colwidth = 2;
        writeLF(f, "");
        writeStr(f, "  ");
      }
      float value = LPM->getRes(y, x);
      if (value == 0) continue;
      varcount++;
      if (value < 0) {
        colwidth += write(f, "- ",2); value = -value;
      } else {
        colwidth += write(f, "+ ",2);
      }
      floatToStrEN(str, value);
      strcat(str, " ");
      colwidth += write(f, str, strlen(str));
      sprintf(str, "x%i", y+1);
      strcat(str, " ");
      colwidth += write(f, str, strlen(str));
    }
    if (!varcount) continue;
    char* pstr = "=";
    switch (LPM->getRel(x)) {
      case EQUAL:   pstr = "=";   break;
      case LTEQUAL: pstr = ">="; break;
      case STEQUAL: pstr = "<="; break;
      case LARGER:  pstr = ">";  break;
      case SMALLER: pstr = "<"; break;
    }
    write(f, pstr, strlen(pstr));
    floatToStrEN(str, LPM->getBV(x));
    writeLF(f, str);
  }
  close(f);
  return true;
}

 /**
 *   Erstellung der ILOG.lp Datei, welche den zu lösenden LP-Ansatz enthält
 */

bool __fastcall PutLPtoILOGFile(LPModell* LPM, char* filename) {

int f = OpenCreateFile(LPM, filename);
  if (f < 0) return false;
  //Zielfunktion
  if (LPM->getMinimize()) writeStr(f, "Minimize\n");
  else writeStr(f, "Maximize\n");

  int vars= LPM->getVarCount();
  int res = LPM->getResCount();
  AnsiString zf;
  char str[200];
  int colwidth = 2;
  for (int x = 0; x < vars ;x++) {
  if (colwidth > 70) {
      colwidth = 2;
      writeLF(f, "");
      writeStr(f, "  ");
    }
    float zfvalue = LPM->getZF(x);

    if (zfvalue < 0) {
      write(f, "- ",2); zfvalue = -zfvalue;
    } else {
      write(f, "+ ",2);
    }
    colwidth += 2;
    floatToStrEN(str, zfvalue);
    strcat(str, " ");
    colwidth += write(f, str, strlen(str));
    sprintf(str, "x%i", x+1);
    strcat(str, " ");
    colwidth += write(f, str, strlen(str));
  }

  //Restriktionen
  writeLF(f, "\nSubject To");
  for (int x = 0; x<res; x++) {
    int varcount = 0;
    for (int y = 0; y<vars; y++) {
      float value = LPM->getRes(y, x);
      if (value == 0) continue;
      varcount++;
      if (value < 0) {
        write(f, "- ",2); value = -value;
      } else {
        write(f, "+ ",2);
      }
      floatToStrEN(str, value);
      strcat(str, " ");
      write(f, str, strlen(str));
      sprintf(str, "x%i", y+1);
      strcat(str, " ");
      write(f, str, strlen(str));
    }
    if (!varcount) continue;
    char* pstr = "=";
    switch (LPM->getRel(x)) {
      case EQUAL:   pstr = "=";   break;
      case LTEQUAL: pstr = ">="; break;
      case STEQUAL: pstr = "<="; break;
      case LARGER:  pstr = ">";  break;
      case SMALLER: pstr = "<"; break;
    }
    writeStr(f, pstr);
    floatToStrEN(str, LPM->getBV(x));
    writeLF(f, str);
  }

  // Grenzen
  writeLF(f, "Bounds");

  for (int x = 0; x < vars ;x++) {
    sprintf(str, "x%i", x+1);
    float lbound = LPM->getLBound(x);
    if (lbound != 0.0){
      strcat(str, " >= ");
      writeStr(f, str);
      floatToStrEN(str, lbound);
      writeLF(f, str);
    }
    float hbound = LPM->getHBound(x);
    if (hbound > 0) {
      sprintf(str, "x%i", x+1);
      writeStr(f, str);
      write(f," <= ",4);
      floatToStrEN(str, hbound);
      writeLF(f, str);
    }

    //Wenn keine obere Grenze vorhanden ist,  wird diese auf "infinity" also unendlich gesetzt
    else{ sprintf(str, "x%i", x+1);
        writeStr(f, str);
        writeStr(f, " <= ");
      writeStr(f, "infinity \n");
      }
  }
  // Ganzzahligkeit
  int intfound = false;
  for (int x = 0; x < vars ;x++) {
    if (LPM->getInteger(x)) {
      if (!intfound) writeStr(f, "Integers\n");
      intfound = true;
      strcat(str, " ");
      sprintf(str, "x%i ", x+1);
      writeStr(f,str);
    }

  }
  //End
  writeLF(f, "\nEnd");
  close(f);
  return true;
}
//---------------------------------------------------------------------------
#pragma package(smart_init)
