//---------------------------------------------------------------------------

#ifndef registryH
#define registryH
//---------------------------------------------------------------------------
void __fastcall reg_setdatadir(char* dir);
char* __fastcall reg_getdatadir();
void __fastcall reg_setworkdir(char* dir);
char* __fastcall reg_getworkdir();
void __fastcall reg_setxadir(char* dir);
char* __fastcall reg_getxadir();
void __fastcall reg_setxadir(char* dir);
char* __fastcall req_getmopsdir();
void __fastcall reg_setmopsdir(char* dir);
char* __fastcall reg_getstradadir();
void __fastcall reg_setstradadir(char* dir);
char* __fastcall reg_getweidenauerdir();
void __fastcall reg_setweidenauerdir(char* dir);
char* __fastcall req_getlpsolvedir();
void __fastcall reg_setlpsolvedir(char* dir);

#endif
