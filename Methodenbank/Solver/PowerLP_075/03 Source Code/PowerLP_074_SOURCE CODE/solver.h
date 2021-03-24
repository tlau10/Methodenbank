//---------------------------------------------------------------------------
#ifndef solverH
#define solverH
#include "lpmodell.h"
//---------------------------------------------------------------------------
/**
 * Klasse fuer die Interaktion mit den verschiedenen Solvern
 *
 */

class TSolver {
  void __fastcall PutXA_BAT(char* dir);
  void __fastcall PutXA_CLP(char* dir);
  void __fastcall PutWEID_CMD(char* dir, bool min, bool integer);
  void __fastcall PutWEID_BAT(ThrowErrorMsg* msg, char* dir);
  void __fastcall PutMOPS_BAT(ThrowErrorMsg* msg, char* dir);
  void __fastcall PutXMOPS_PRO(LPModell* LPM);
  void __fastcall PutILOG_BAT(char* dir);
  
public:
  void __fastcall RunXA(LPModell* LPM);
  void __fastcall RunMOPS(LPModell* LPM);
  void __fastcall RunLPSolve(LPModell* LPM);
  void __fastcall RunILOG(LPModell* LPM);
  void __fastcall RunWeidenauer(LPModell* LPM);
 bool __fastcall getXAFile(char* file, int size);
  bool __fastcall getMOPSFile(char* file, int size);
  bool __fastcall getILOGFile(char* file, int size);
  bool __fastcall getWeidenauerFile(char* file, int size);
  bool __fastcall getLPSolveFile(char* file, int size);
};
#endif
