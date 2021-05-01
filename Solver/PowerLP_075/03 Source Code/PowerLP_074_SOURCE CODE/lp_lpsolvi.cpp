//---------------------------------------------------------------------------
#include <vcl.h>
#pragma hdrstop
#include <io.h>
#include <stdio.h>
#include <sys\stat.h>
#include <fcntl.h>
#include <dir.h>
#include "common.h"
#include "lp_xaeq.h"

#include "lp_lpsolvi.h"

//---------------------------------------------------------------------------
char* floatToStrEN(char* str, float value);
//------------------------------------------------------------------------------
bool __fastcall PutLPtoLPSOLVFile(LPModell* LPM, char* filename) {
  int f = OpenCreateFile(LPM, filename);
  if (f < 0) return false;

  if (LPM->getMinimize()) writeStr(f, "min: ");
  else writeStr(f, "max: ");

  int vars= LPM->getVarCount();
  int res = LPM->getResCount();
  AnsiString zf;
  char str[200];
  int colwidth = 2;
  for (int x = 0; x < vars ;x++) {
    float zfvalue = LPM->getZF(x);
    if (zfvalue < 0) {
      writeStr(f, "-"); zfvalue = -zfvalue;
    } else {
      writeStr(f, "+");
    }
    floatToStrEN(str, zfvalue);
    strcat(str, " ");
    colwidth += write(f, str, strlen(str));
    sprintf(str, "x%i", x+1);
    strcat(str, " ");
    colwidth += write(f, str, strlen(str));
  }
  writeLF(f, ";");
  // restrictions
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
    strcat(str, ";");
    writeLF(f, str);
  }
  // Bounds
  for (int x = 0; x < vars ;x++) {
    sprintf(str, "x%i", x+1);
    float lbound = LPM->getLBound(x);
    if (lbound != 0.0) {
      strcat(str, " >= ");
      writeStr(f, str);
      floatToStrEN(str, lbound);
      strcat(str, ";");
      writeLF(f, str);
    }
    float hbound = LPM->getHBound(x);
    if (hbound > 0) {
      sprintf(str, "x%i", x+1);
      writeStr(f, str);
      write(f," <= ",4);
      floatToStrEN(str, hbound);
      strcat(str, ";");
      writeLF(f, str);
    }
  }
  // Integer
  int intfound = false;
  for (int x = 0; x < vars ;x++) {
    if (LPM->getInteger(x)) {
      if (!intfound) writeStr(f, "int");
      intfound = true;
      sprintf(str, " x%i", x+1);
      writeStr(f,str);
    }
  }
  if (intfound) writeLF(f,";");
  close(f);
  return true;
}
//---------------------------------------------------------------------------
#pragma package(smart_init)
