//---------------------------------------------------------------------------
#include <vcl.h>
#pragma hdrstop
#include <io.h>
#include <stdio.h>
#include <sys\stat.h>
#include <fcntl.h>
#include <dir.h>
#include "lp_lpi.h"
/**
 * Klasse mit deren Daten das LP-Modell erzeugt wird
 *
 */
struct Tlpdata {
  char d1[2];
  bool maximize;      // 4
  char d2[1];
  short restr;        // 6
  short vars;         // 8
  char d3[408];       // 416
  float nb[75][101]; // 30716 (0x77F8)
  float zfb[100];     // 31116 (0x798C)
  char d7[308];       // 31424 (0x7AC0)
  float lbound[76];   // 31728 (0x7BF0)
  float hbound[76];   // 32032 (0x7D20)
  float zf[75];       // 32332
  char d5[1142];      // 33474 (0x82C2)
  short cc[100];      // 33674
  char d6[154];       // 33828 (0x8424)
  short integer[75];  // 33978
  // size 33978
};

typedef enum ccond {LPI_EQUAL = 0x203D, LPI_LTEQUAL = 0x3D3E, LPI_STEQUAL = 0x3D3C,
                    LPI_LARGER = 0x203E, LPI_SMALLER = 0x203C} ccond;
//---------------------------------------------------------------------------
bool __fastcall GetLPfromLPIFile(LPModell* LPM, char* filename) {
  char* file = strrchr(filename, '\\');
  if (!file) return false;
  char* ext = strrchr(file, '.');
  if (!ext) return false;
  strlwr(ext);
  if (strcmp(ext, ".lpi")) return false;   // wenn kein *.lpi dann raus !!!
  int f = open(filename, O_RDONLY | O_BINARY, S_IREAD);
  if (f < 0) return false;
  Tlpdata d;
  memset(&d, 0, sizeof(d));
  int sw = read(f, &d, sizeof(d));
  if (sw < sizeof(d)-2) LPM->PutError("LPI-Format: Warnung, falsche Dateigroesse.");

  LPM->setSize(d.vars, d.restr);
  LPM->setMinimize(!d.maximize);

  for (int x = 0; x < d.vars ;x++) {
    LPM->setZF(x, d.zf[x]);
    LPM->setInteger(x,d.integer[x]);
    LPM->setBounds(x, d.lbound[x], d.hbound[x]);
  }
  for (int x = 0; x<d.restr; x++) {
    for (int y = 0; y<d.vars; y++) {
      LPM->setRes(y, x, d.nb[y][x]);
    }
    LPM->setBV(x, d.zfb[x]);
    relation rel = STEQUAL;
    switch (d.cc[x]) {
      case LPI_EQUAL: rel = EQUAL; break;
      case LPI_LTEQUAL: rel = LTEQUAL; break;
      case LPI_STEQUAL: rel = STEQUAL; break;
      case LPI_LARGER: rel = LARGER; break;
      case LPI_SMALLER: rel = SMALLER; break;
    }
    LPM->setRel(x, rel);
  }
  close(f);
  return true;
}
//---------------------------------------------------------------------------
bool __fastcall PutLPToLPIFile(LPModell* LPM, char* filename) {
  int f = OpenCreateFile(LPM, filename);
  if (f < 0) return false;
  Tlpdata d;
  memset(&d, 0, sizeof(d));

  d.vars = LPM->getVarCount();
  if (d.vars > 75) {
    LPM->PutError("LPI-Dateiformat: Zuviele Variablen. Maximum ist 75.");
    d.vars = 75;
  }
  d.restr = LPM->getResCount();
  if (d.restr > 100) {
    LPM->PutError("LPI-Dateiformat: Zuviele Restriktionen. Maximum ist 100.");
    d.vars = 100;
  }
  d.maximize = !LPM->getMinimize();
  for (int x = 0; x < d.vars ;x++) {
    d.zf[x] = LPM->getZF(x);
    d.integer[x] = LPM->getInteger(x);
    d.lbound[x] = LPM->getLBound(x);
    d.hbound[x] = LPM->getHBound(x);
  }
  for (int x = 0; x<d.restr; x++) {
    for (int y = 0; y<d.vars; y++) {
      d.nb[y][x] = LPM->getRes(y, x);
    }
    d.zfb[x] = LPM->getBV(x);
    ccond rel =LPI_EQUAL;
    switch (LPM->getRel(x)) {
      case EQUAL:   rel = LPI_EQUAL;   break;
      case LTEQUAL: rel = LPI_LTEQUAL; break;
      case STEQUAL: rel = LPI_STEQUAL; break;
      case LARGER:  rel = LPI_LARGER;  break;
      case SMALLER: rel = LPI_SMALLER; break;
    }
    d.cc[x] = rel;
  }

  write(f, &d, sizeof(d));
  close(f);
  return true;
}
//---------------------------------------------------------------------------
#pragma package(smart_init)
