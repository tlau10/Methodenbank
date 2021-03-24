//---------------------------------------------------------------------------
#ifndef lp_xaeqH
#define lp_xaeqH
//---------------------------------------------------------------------------
#include "lpmodell.h"
//---------------------------------------------------------------------------
typedef enum ptype {ALPHA, NUMERIC, SIGN, OPERATOR} ptype;

bool __fastcall GetLPfromXAEQFile(LPModell* LPM, char* filename);
bool __fastcall PutLPtoXAEQFile(LPModell* LPM, char* filename);
bool __fastcall PutLPtoILOGFile(LPModell* LPM, char* filename);
#endif
 