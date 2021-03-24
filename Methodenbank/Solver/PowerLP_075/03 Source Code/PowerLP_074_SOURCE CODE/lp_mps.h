//---------------------------------------------------------------------------

#ifndef lp_mpsH
#define lp_mpsH
#include "lpmodell.h"
bool __fastcall GetLPfromMPSFile(LPModell* LPM, char* filename);
bool __fastcall PutLPtoMPSFile(LPModell* LPM, char* filename, bool boundssupport = true);
//---------------------------------------------------------------------------
#endif
