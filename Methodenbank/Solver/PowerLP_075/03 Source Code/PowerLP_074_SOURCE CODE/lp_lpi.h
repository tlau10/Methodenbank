//---------------------------------------------------------------------------

#ifndef lp_lpiH
#define lp_lpiH
#include "lpmodell.h"
//---------------------------------------------------------------------------
bool __fastcall GetLPfromLPIFile(LPModell* LPM, char* filename);
bool __fastcall PutLPToLPIFile(LPModell* LPM, char* filename);

#endif
 