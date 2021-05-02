//---------------------------------------------------------------------------
#include <vcl.h>
#pragma hdrstop
#include <io.h>
#include <stdio.h>
#include <sys\stat.h>
#include <fcntl.h>
#include <dir.h>
#include "common.h"
#include "lpmodell.h"
#include "childwin.h"
#include "lp_lpi.h"
#include "lp_xaeq.h"
#include "lp_mps.h"

__fastcall LPModell::LPModell(ThrowErrorMsg* pOwner) {
  Owner = pOwner;
  vars = 0;
  ress = 0;
  cvars = 0;      // Anzahl Variablen
  cress = 0;      // Anzahl der Restriktionen
  res = NULL;
  bvec = NULL;
  resname = NULL;
  zfname[0] = 0;
  varname = NULL;
  zf = NULL;
  rels = NULL;
  hbound = NULL;
  lbound = NULL;
  integer = NULL;       // ganzzahlig
  minimize = false;     // default      Minimierungsproblem
}
//---------------------------------------------------------------------------
__fastcall LPModell::~LPModell() {      // Destruktor
  if (res) {
    for (int x = 0; x < ress; x++) {
      free(res[x]);
    }
    free(res);
    res = NULL;
    free(resname);
    free(rels);
    free(bvec);
  }
  if (zf) {
    free(varname);
    free(zf);
    free(integer);
    free(lbound);
    free(hbound);
  }
}
//---------------------------------------------------------------------------
void __fastcall LPModell::clear() {
  for (int x = 0; x < ress;x++) {
    memset(res[x], 0, sizeof(float)*vars);
    bvec[x] = 0.0;
  }
  for (int x = 0; x < vars;x++) {
    lbound[x] = 0;
    hbound[x] = 0;
    varname[x][0] = 0;
    integer[x] = false;
    zf[x] = 0;
  }
}
//---------------------------------------------------------------------------
void __fastcall LPModell::setSize(int pvars, int pres) {
//  if (pvars < 1 || pres < 1) return;
  if (pres > ress) {
    rels = (relation*)realloc(rels, sizeof(relation)*pres);
    res = (float**)realloc(res, sizeof(float*)*pres);
    bvec = (float*)realloc(bvec, sizeof(float)*pres);
    resname = (svarname*) realloc(resname, sizeof(svarname)*pres);
    for (int x = ress; x < pres; x++) {
      if (minimize) rels[x] = LTEQUAL; else rels[x] = STEQUAL;
      res[x] = (float*)malloc(sizeof(float)*vars);
      memset(res[x], 0, sizeof(float)*vars);
      bvec[x] = 0.0;
    }
    ress = pres;
  }
  if (pvars > vars) {
    zf = (float*)realloc(zf, sizeof(float)*pvars);
    lbound = (float*)realloc(lbound, sizeof(float)*pvars);
    hbound = (float*)realloc(hbound, sizeof(float)*pvars);
    integer= (bool*) realloc(integer, sizeof(bool)*pvars);
    varname= (svarname*) realloc(varname, sizeof(svarname)*pvars);
    for (int x = vars; x < pvars; x++) {
      zf[x] = 0.0;
      integer[x] = false;
      lbound[x] = 0.0;
      hbound[x] = 0.0;
      varname[x][0] = 0;
    }
    if (res) {
      for (int x = 0; x < ress; x++) {
        res[x] = (float*)realloc(res[x], sizeof(float)*pvars);
        for (int y = vars; y < pvars; y++) res[x][y] = 0.0;
      }
    }
    vars = pvars;
  }
  cvars = pvars;
  cress = pres;
}
//---------------------------------------------------------------------------
int __fastcall LPModell::getVarCount() {
  return cvars;
}
//---------------------------------------------------------------------------
int __fastcall LPModell::getResCount() {
  return cress;
}
//---------------------------------------------------------------------------
float __fastcall LPModell::getZF(int varno) {
  if (varno >= cvars) return 0.0;
  return zf[varno];
}
//---------------------------------------------------------------------------
void __fastcall LPModell::setZF(int varno, float value) {
  if (varno >= cvars) return;
  zf[varno] = value;
}
//---------------------------------------------------------------------------
float __fastcall LPModell::getRes(int varno, int resno) {
  if (varno >= cvars) return 0.0;
  if (resno >= cress) return 0.0;
  return res[resno][varno];           // 2 dim. Array
}
//---------------------------------------------------------------------------
void __fastcall LPModell::setRes(int varno, int resno, float value) {
  if (varno >= cvars) return;
  if (resno >= cress) return;
  res[resno][varno] = value;       // 2 dim Array
}
//---------------------------------------------------------------------------
void __fastcall LPModell::setResName(int resno, char* name) {
  if (resno >= cress) return;
  strpncpy(resname[resno], name, sizeof(svarname));
}
//---------------------------------------------------------------------------
void __fastcall LPModell::setZFName(char* name) {
  strpncpy(zfname, name, MAXVARNAME);
}
//---------------------------------------------------------------------------
char* __fastcall LPModell::getZFName(char* name) {
  if (name) return zfname;
  strpncpy(zfname, name, MAXVARNAME);
  return name;
}
//---------------------------------------------------------------------------
bool __fastcall LPModell::isZFName(char* name) {
  return !strcmp(zfname, name);
}
//---------------------------------------------------------------------------
int __fastcall LPModell::getResNameIX(char* name) {
  for (int x = 0; x<cress; x++) {
    if (!strcmp(resname[x], name)) return x;     // index wird zurückgegeben
  }
  return -1;
}
//---------------------------------------------------------------------------
void __fastcall LPModell::setVarName(int varno, char* name) {
  if (varno >= cvars) return;
  strpncpy(varname[varno], name, sizeof(svarname));
}
//---------------------------------------------------------------------------
int __fastcall LPModell::getVarNameIX(char* name) {
  for (int x = 0; x<cvars; x++) {
    if (!strcmp(varname[x], name)) return x;
  }
  return -1;
}
//---------------------------------------------------------------------------
float __fastcall LPModell::getBV(int resno) {
  if (resno >= cress) return 0.0;
  return bvec[resno];
}
//---------------------------------------------------------------------------
void __fastcall LPModell::setBV(int resno, float value) {
  if (resno >= cress) return;
  bvec[resno] = value;
}
//---------------------------------------------------------------------------
relation __fastcall LPModell::getRel(int resno) {
  if (resno >= cress) return LTEQUAL;
  return rels[resno];
}
//---------------------------------------------------------------------------
void __fastcall LPModell::setRel(int resno, relation value) {
  if (resno >= cress) return;
  rels[resno] = value;
}
//---------------------------------------------------------------------------
void __fastcall LPModell::setMinimize(bool pminimize) {
  minimize = pminimize;
}
//---------------------------------------------------------------------------
bool __fastcall LPModell::getMinimize() {
  return minimize;
}
//---------------------------------------------------------------------------
void __fastcall LPModell::setInteger(int varno, bool value) {
  if (varno >= cvars) return;
  integer[varno] = value;
}
//---------------------------------------------------------------------------
bool __fastcall LPModell::getInteger(int varno) {
  if (varno >= cvars) return false;
  return integer[varno];
}
//---------------------------------------------------------------------------
void __fastcall LPModell::setBounds(int varno, float plbound, float phbound) {
  if (varno >= cvars) return;
  lbound[varno] = plbound;
  hbound[varno] = phbound;
}
//---------------------------------------------------------------------------
float __fastcall LPModell::getLBound(int varno) {
  if (varno >= cvars) return 0.0;
  return lbound[varno];
}
//---------------------------------------------------------------------------
float __fastcall LPModell::getHBound(int varno) {
  if (varno >= cvars) return 0.0;
  return hbound[varno];
}
//---------------------------------------------------------------------------
void __fastcall LPModell::PutError(char* str) {
  Owner->PutError(str);
}
//---------------------------------------------------------------------------
void __fastcall LPModell::clone(LPModell* Clone) {
  Clone->setMinimize(this->getMinimize());
  Clone->setSize(cvars, cress);
  for (int x = 0; x < cress; x++) {
    for (int y = 0; y < cvars; y++) {
       Clone->setRes(y,x, getRes(y,x));
    }
    Clone->setBV(x, getBV(x));
    Clone->setRel(x, getRel(x));
  }
  for (int y = 0; y < cvars; y++) {
    Clone->setZF(y, getZF(y));
    Clone->setBounds(y, getLBound(y), getHBound(y));
    Clone->setInteger(y, getInteger(y));
  }
}
//---------------------------------------------------------------------------
void __fastcall LPModell::delcs(int respos) {
  float* oldptr = res[respos];
  float oldbv = getBV(respos);
  relation oldrel = getRel(respos);
  for (int x = respos; x < cress-1; x++) {
    res[x] = res[x+1];
    setBV(x, getBV(x+1));
    setRel(x, getRel(x+1));
  }
  res[cress-1] = oldptr;
  setBV(cress-1, oldbv);
  setRel(cress-1, oldrel);
  cress--;
}
//---------------------------------------------------------------------------
void __fastcall LPModell::ResToBounds() {
  // Restriktionen durchlaufen, um zu prüfen ob eine Restriktion existiert die
  // nur 1 Variable enthält, bsp X2 < 3 oder X4 > 4, d.h.
  // die Anzahl der Variablen = 1 UND die einzige Variable ist mit 1 gewichtet
  // Dann wird die Restriktion in eine Bounds-Angabe umgewandelt
  // Funktion wird benötigt für Dual-Umwandlung
  for (int y = 0; y<cress; y++) {
    int appearance = 0;
    int var = 0;
    for (int x = 0; x < cvars ;x++) {
      if (getRes(x,y) != 0.0) {
        appearance ++;
        var = x;
      }
    }
    float resvalue = getRes(var, y);
    if (appearance == 1 && (resvalue  == 1.0 || resvalue == -1.0)) {
      if (resvalue == -1.0) { // Restriktion mit -1 durchmultiplizieren
        if (getRel(y) == LTEQUAL) setRel(y, STEQUAL);
        if (getRel(y) == STEQUAL) setRel(y, LTEQUAL);
        setBV(y, -1.0*getBV(y));
      }
      switch (getRel(y)) {
        case LTEQUAL:
          if (getLBound(var) == 0 /*&& getLBound(var) <= getBV(y)*/) {
            setBounds(var, getBV(y), getHBound(var));
            delcs(y);
            y--;
          }
        break;
        case STEQUAL:
          if (getHBound(var) == 0 /*|| getHBound(var) >= getBV(y)*/) {
            setBounds(var, getLBound(var), getBV(y));
            delcs(y);
            y--;
          }
        break;
      }
    }
  }
}

//---------------------------------------------------------------------------
void __fastcall LPModell::RemoveDuplicateRes() {
  bool redcount = 0;
  for (int y = 0; y<cress-1; y++) {
    for (int y2 = y+1; y2 < cress; y2++) {
      bool equal = true;
      bool equalcond = true;
      for (int x = 0; x < cvars ;x++) {
        if (getRes(x,y) != getRes(x,y2)) { equal = false; equalcond = false; break; }
      }
      if (getBV(y) != getBV(y2)) { equal = false; equalcond = false; break; }
      if (getRel(y) != getRel(y2)) { equal = false; }
      if (!((getRel(y) == LTEQUAL && getRel(y2) == STEQUAL) ||
            (getRel(y) == STEQUAL && getRel(y2) == LTEQUAL))) {
        equalcond = false;
      }
      if (equal) {
        delcs(y);
        y--;
        redcount++;
        break;
      }
      if (equalcond) {
        // Gleichungen die sich nur in der Bedingung mit <= und >= unterscheiden
        // entsprechen einer einzigen = Restriktion
        setRel(y2, EQUAL);
        delcs(y);
        y--;
        break;
      }
    }
  }
  if (redcount) {
    char msg[1000];
    sprintf(msg, "Es wurde(n) %i redundante Restriktion(en) gelöscht.",redcount);
    PutError(msg);
  }
}
//---------------------------------------------------------------------------
/**
 *Methode die aus einem Primalmodell ein Dualmodell zu erzeugt
 */
void __fastcall LPModell::getDual(LPModell* Dual) {
  Dual->setMinimize(!this->getMinimize());   // true or false
  float mf = -1.0;                          // Multi - Faktor
  if (this->getMinimize()) mf = 1.0;     // wenn Dual = Minimalisierungs-Problem
  Dual->setSize(cress, cvars);           /* vertauscht !!     DualSize(Restriktionen, Variablen)
                                                              PrimalSize(Variablen, Restriktionen) */
  int nr = 0; // dual var
  for (int x = 0; x < cress; x++) { // Restriktionen durchlaufen
    Dual->setSize(nr+1, cvars);
    switch (this->getRel(x)) {      // Relationen der Restriktionen holen
      case EQUAL:
        // Werte im 2-dim Array gerade vertauschen
        for (int y = 0; y < cvars; y++) Dual->setRes(nr, y, -mf*this->getRes(y,x));
        Dual->setZF(nr, -mf*this->getBV(x));     // b-Werte Primal => ZF-Werte, 'nur' eindimensional
        nr++; Dual->setSize(nr+1, cvars);
        for (int y = 0; y < cvars; y++) Dual->setRes(nr, y, mf*this->getRes(y,x));
        Dual->setZF(nr, mf*this->getBV(x));
      break;
      case LTEQUAL:
      case LARGER:
        for (int y = 0; y < cvars; y++) Dual->setRes(nr, y, mf*this->getRes(y,x));
        Dual->setZF(nr, mf*this->getBV(x));
      break;
      case STEQUAL:
      case SMALLER:
        for (int y = 0; y < cvars; y++) Dual->setRes(nr, y, -mf*this->getRes(y,x));
        Dual->setZF(nr, -mf*this->getBV(x));
      break;
    }
    nr++;
  }

  for (int x = 0; x < cvars; x++) { // Variablen durchlaufen (Primal)
                                    //  für Dual die Restriktionen
    float bv = this->getZF(x);
    // comfort: mit minus 1 multiplizieren falls B-Vector < 0
    if (bv < 0) mf = -1;
    else mf = 1;
    Dual->setBV(x, mf*this->getZF(x)); // b-werte Dual sind  ZF-Wert (Primal)
    for (int y = 0; y < cress; y++) {
      Dual->setRes(y,x, mf*Dual->getRes(y,x));
    }
    int rel = mf;
    if (this->getMinimize()) rel *= -1;
    if (rel < 0) Dual->setRel(x, STEQUAL); // smaller
    else         Dual->setRel(x, LTEQUAL); // larger

  }
  Dual->RemoveDuplicateRes();
}
//---------------------------------------------------------------------------
bool __fastcall LPModell::isInt() { // Ganzzahligkeit
  for (int x = 0; x < cvars; x++) {
    if (getInteger(x)) return true;
  }
  return false;
}
//---------------------------------------------------------------------------
bool __fastcall LPModell::isIntN() { // 0 .. N Ganzzahligkeit
  for (int x = 0; x < cvars; x++) {
    if (getInteger(x) && getHBound(x) != 1) return true;
  }
  return false;
}
//---------------------------------------------------------------------------
bool __fastcall LPModell::isMixedMode() {
  int floatvars = 0;
  int intvars = 0;
  for (int x = 0; x < cvars; x++) {
    if (getInteger(x)) intvars ++;
    else floatvars++;
  }
  if (floatvars > 0 && intvars > 0) return true;
  return false;
}
//---------------------------------------------------------------------------
bool __fastcall LPModell::hasIntNoHBound() {
  for (int x = 0; x < cvars; x++) {
    if (getInteger(x) && getHBound(x) == 0) return true;    // ok
  }
  return false;
}
//---------------------------------------------------------------------------
/**
 *Methode wird aufgerufen, wenn der Solver XA gestartet wird und das LPModell eine oder mehrere Variablen mit 0..N Ganzzahligkeit hat sowie obere Grenzen. 
 *
 *Für die Variablen, die diesen Voraussetzungen entsprechen, wird eine neue Restriktion erstellt, deren neue Variablen nur 0/1 Ganzzahligkeit benötigen.
 */
void __fastcall LPModell::splitIntN() {      // fuer XA
  int ccvars = cvars;           // Anzahl Variablen
  for (int x = 0; x < ccvars; x++) { // Variablen durchlaufen
    if (getInteger(x) && getHBound(x) != 1) {
      // prüfe ganzzahlige Variablen deren obere Grenze > 1
      int addvars = getHBound(x);
      // zusätzliche Variablen
      // Beispiel Variable X1 obere Grenze = 3, d.h. X1 wird aufgeteilt in 3
      // Variablen, X1 = X1_1 + X1_2 + X1_3, wobei X1_1 bis X1_3  0,1-Variablen
      // sind
      bool lbound = false;
      if (getLBound(x) > 0) { // Berücksichtigung der unteren Grenze
        lbound = true;
        addvars -= getLBound(x)-1;     // addvars ist die Differenz zwischen
                                       // oberer und unterer Grenze
        // Die zusätzlichen Variablen reduzieren sich mit der unteren Grenze
        // Beispiel von oben: Untere Grenze = 1, obere Grenze = 3
        // damit Variable X1 = 1 + X1_2 + X1_3
      }
      if (addvars == 0) continue; // zusätzliche Variablen nicht notwendig
                                  // Beispiel Untere / Obere Grenze 2 / 3
      // neue Restriktion + N Variablen
      int z = cvars;
      // zusätzliche Variablen hinzufügen + auf 0 setzen  sowie neue Nebenbedingung
      // Beispiel:
      //   - 1 * X1 + X1_1 + X1_2 + X1_3 = 0
      // bei Beispiel mit unterer Grenze
      //   - X1 + X1_1 +   2    * X1_2 = 0
      //  die untere Grenze wird auch in den Bounds angegeben ( X1 >= 2, X1 <= 3)
      setSize(cvars+addvars, cress+1);              // !!! cvars =  cvars + addvars
                                                    // !!! cress = cress + 1
      for (int y = 0; y < cvars; y++) setRes(y, cress-1, 0);    // neue Restriktion
                                                                // alles loeschen
      for (; z < cvars; z++) {
        setRes(z, cress-1, 1.0); // auf Faktor eins in Nebenbedingung setzen
        setZF(z, 0);             // in ZF mit 0 gewichten
        setInteger(z, true);     // 0,1 Variablen
      }
      setRel(cress-1, EQUAL); // relation
      setRes(x, cress-1, -1.0);  // Ursprüngliche Variable mit -1 in neue Nebenbedingung
      setBV(cress-1, 0);
      if (lbound) setRes(z-1, cress-1, getLBound(x));    // wenn untere Grenze, dann
                                                        //  anderer Multiplikator
      setInteger(x, false);   // ursprüngliche Variable ist jetzt nicht mehr
        // ganzzahlig, da durch die neue Nebenbedingung die 0,N-Ganzzahligkeit
        // gewährleistet ist
    }
  }
}
//==============================================================================
int __fastcall OpenCreateFile(ThrowErrorMsg* emsg, char* filename) {
  int f = open(filename,O_RDWR | O_BINARY | O_CREAT | O_TRUNC, S_IREAD | S_IWRITE);
  if (f < 0) {
    char msg[1000];
    sprintf(msg, "Datei %s konnte nicht geöffnet/erstellt werden",filename);
    emsg->PutError(msg);
  }
  return f;
}
//---------------------------------------------------------------------------
int __fastcall GetLPFromFile(LPModell* LPM, char* filename) {
  int f = open(filename, O_RDONLY | O_BINARY, S_IREAD);  /* O_RDONLY	Open for reading only
                                                           On success, open returns a nonnegative integer
                                                           (the file handle). */
  if (f < 0) {
    LPM->PutError("Datei konnte nicht geoeffnet werden.");
    return -1;
  }
  int format = -1;
  close(f);

  // keine Prüfung auf Dateiformat, denn dann funktioniert es auch
  // wenn man Dateiformate einliesst die gleich sind aber eine andere
  // Format-Endung haben
  // Dateiformat *.lpi
  if (GetLPfromLPIFile(LPM, filename)) {
    format = FILEFORMAT_LPI;
    goto FINISHUP;
  }

  // Dateiformat *.mps
  if (GetLPfromMPSFile(LPM, filename)) { /* hier weiss man noch sicher nicht ob es sich
                                            um ein mps-Format handelt */

    format = FILEFORMAT_MPS;
    goto FINISHUP;          // Funktionsaufruf in der Methode
  }

  // Dateiformat *.lp
  if (GetLPfromXAEQFile(LPM, filename)) {
    format = FILEFORMAT_XA_EQ;
    goto FINISHUP;
  }

  LPM->PutError("Das Format der Datei konnte nicht erkannt werden.");
FINISHUP:
  // Minimalgroesse
  if (LPM->getVarCount() < 2) LPM->setSize(2, LPM->getResCount());
  if (LPM->getResCount() < 2) LPM->setSize(LPM->getVarCount(), 2);
//  LPM->ResToBounds();   // Restriktionen mit nur einer Variablen werden zu Bounds
  LPM->RemoveDuplicateRes(); // Restriktionen die doppelt vorhanden sind
                             // loeschen (z.B. bei Personalplanung)
  return format;
}
//---------------------------------------------------------------------------
bool __fastcall PutLPtoFile(LPModell* LPM, char* filename, int format) {
   // z.B. format = 2  =>  mps
  switch (format) {
    case FILEFORMAT_LPI:  return PutLPToLPIFile(LPM, filename);
    case FILEFORMAT_XA_EQ:return PutLPtoXAEQFile(LPM, filename);
    case FILEFORMAT_MPS:  return PutLPtoMPSFile(LPM, filename);
  }
  return false;
}
#pragma package(smart_init)

