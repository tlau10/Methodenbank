//---------------------------------------------------------------------------

#include <vcl.h>
#pragma hdrstop
#include "dir.h"
#include "registry.h"
#include "common.h"
/*
HKEY_LOCAL_MACHINE\Software\PowerLP\Solver   // Solver-Verzeichnisse
HKEY_CURRENT_USER\Software\PowerLP\Data     // akutelles Datenverzeichnis
*/
//---------------------------------------------------------------------------
unsigned long disposition;
unsigned long type;
//---------------------------------------------------------------------------
HKEY openPowerLPRegUSER()
{
  HKEY Result;
  RegOpenKeyEx(HKEY_CURRENT_USER, "Software", NULL, KEY_ALL_ACCESS, &Result);
  RegCreateKeyEx(Result, "FallPauschalOptimierer", NULL, NULL,	REG_OPTION_NON_VOLATILE	,
    KEY_ALL_ACCESS,	NULL,  &Result,	&disposition);
  return Result;
}
//---------------------------------------------------------------------------
HKEY openPowerLPReg()
{
  HKEY Result;
  RegOpenKeyEx(HKEY_LOCAL_MACHINE, "Software", NULL, KEY_ALL_ACCESS, &Result);
  RegCreateKeyEx(Result, "FallPauschalOptimierer", NULL, NULL,	REG_OPTION_NON_VOLATILE	,
    KEY_ALL_ACCESS,	NULL,  &Result,	&disposition);
  return Result;
}
//---------------------------------------------------------------------------
HKEY openSolverReg()
{
  HKEY Result;
  Result = openPowerLPReg();
  RegCreateKeyEx(Result, "Solver", NULL, NULL,	REG_OPTION_NON_VOLATILE	,
    KEY_ALL_ACCESS,	NULL,  &Result,	&disposition);
  return Result;
}
//---------------------------------------------------------------------------
HKEY openDataReg()
{
  HKEY Result;
  Result = openPowerLPRegUSER();
  RegCreateKeyEx(Result, "Data", NULL, NULL,	REG_OPTION_NON_VOLATILE	,
    KEY_ALL_ACCESS,	NULL,  &Result,	&disposition);
  return Result;
}
//---------------------------------------------------------------------------
void __fastcall createDirPath(char* newdirpath)
{
  // erstellt einen ganzen pfad neuer unterverzeichnisse
  if (!chdir(newdirpath)) return;
  char dirpath[2000];
  strpncpy(dirpath, newdirpath, sizeof(dirpath));
  char* uplevel = strrchr(dirpath, '\\');
  if (uplevel) {
    uplevel[0] = 0;
    if (chdir(dirpath) && strlen(dirpath) > 3) {
      if (mkdir(dirpath)) {
        createDirPath(dirpath);
        mkdir(dirpath);          // funktioniert nicht richtig
      }
    }
  }
}
//---------------------------------------------------------------------------
char* __fastcall reg_getdatadir()
{
  static char regvalue[2000];
  unsigned long dirsize = sizeof(regvalue);
  HKEY Result = openDataReg();
  if (RegQueryValueEx(Result,"datadir",NULL,&type,regvalue,&dirsize) != ERROR_SUCCESS)
  {
//    RegSetValueEx(Result, "datadir", NULL, REG_SZ, defaultdir, strlen(defaultdir)+1);
    return "L:\\BESF\\OR_MBANK\\LP-INTER\\DATEN\\";
  }
  RegCloseKey(Result);
  CheckSlash(regvalue);
  return regvalue;
//  return ;
}
//---------------------------------------------------------------------------

void __fastcall reg_setdatadir(char* dir)
{
  HKEY Result  = openDataReg();;
  RegSetValueEx(Result, "datadir", NULL, REG_SZ, dir, strlen(dir)+1);
  RegCloseKey(Result);
}
//---------------------------------------------------------------------------
char* __fastcall reg_getworkdir()
{
  static char regvalue[2000];
  unsigned long dirsize = sizeof(regvalue);
  HKEY Result = openDataReg();
  if (RegQueryValueEx(Result,"tempdir",NULL,&type,regvalue,&dirsize) != ERROR_SUCCESS) {
//    RegSetValueEx(Result, "datadir", NULL, REG_SZ, defaultdir, strlen(defaultdir)+1);
    strcpy(regvalue,"C:\\TEMP\\FOP\\");
  } else {
    RegCloseKey(Result);
  }
  CheckSlash(regvalue);
  createDirPath(regvalue);
  return regvalue;
}
//---------------------------------------------------------------------------
void __fastcall reg_setworkdir(char* dir)
{
  HKEY Result = openDataReg();
  RegSetValueEx(Result, "tempdir", NULL, REG_SZ, dir, strlen(dir)+1);
  RegCloseKey(Result);
}
//---------------------------------------------------------------------------
char* __fastcall reg_getxadir()
{
  static char regvalue[2000];
  unsigned long dirsize = sizeof(regvalue);
  HKEY Result = openSolverReg();
  if (RegQueryValueEx(Result,"XA", NULL,	&type,	regvalue,	&dirsize) != ERROR_SUCCESS) {
    return "L:\\Besf\\solver\\xa\\";
  }
  CheckSlash(regvalue);
  return regvalue;
}
//---------------------------------------------------------------------------
void __fastcall reg_setxadir(char* dir)
{
  HKEY Result = openSolverReg();
  RegSetValueEx(Result, "XA", NULL, REG_SZ, dir, strlen(dir)+1);
  RegCloseKey(Result);
}
//---------------------------------------------------------------------------
char* __fastcall req_getmopsdir()
{
  static char regvalue[2000];
  unsigned long dirsize = sizeof(regvalue);
  HKEY Result = openSolverReg();
  if (RegQueryValueEx(Result,"MOPS", NULL,	&type,	regvalue,	&dirsize) != ERROR_SUCCESS) {
    return "L:\\Besf\\solver\\mops 7.06\\exec\\";
  }
  CheckSlash(regvalue);
  return regvalue;
}
//---------------------------------------------------------------------------
void __fastcall reg_setmopsdir(char* dir)
{
  HKEY Result = openSolverReg();
  RegSetValueEx(Result, "MOPS", NULL, REG_SZ, dir, strlen(dir)+1);
  RegCloseKey(Result);
}
//---------------------------------------------------------------------------
char* __fastcall reg_getstradadir()
{
  static char regvalue[2000];
  unsigned long dirsize = sizeof(regvalue);
  HKEY Result = openSolverReg();
  if (RegQueryValueEx(Result,"STRADA", NULL,	&type,	regvalue,	&dirsize) != ERROR_SUCCESS) {
    return "L:\\Besf\\solver\\strada\\exec\\";
  }
  CheckSlash(regvalue);
  return regvalue;
}
//---------------------------------------------------------------------------
void __fastcall reg_setstradadir(char* dir)
{
  HKEY Result = openSolverReg();
  RegSetValueEx(Result, "STRADA", NULL, REG_SZ, dir, strlen(dir)+1);
  RegCloseKey(Result);
}
//---------------------------------------------------------------------------
char* __fastcall reg_getweidenauerdir()
{
  static char regvalue[2000];
  unsigned long dirsize = sizeof(regvalue);
  HKEY Result = openSolverReg();
  if (RegQueryValueEx(Result,"WEIDENAUER", NULL,	&type,	regvalue,	&dirsize) != ERROR_SUCCESS) {
    return "L:\\Besf\\solver\\Weidenau Optimizer\\exec\\";
  }
  CheckSlash(regvalue);
  return regvalue;
}
//---------------------------------------------------------------------------
void __fastcall reg_setweidenauerdir(char* dir) {
  HKEY Result = openSolverReg();
  RegSetValueEx(Result, "WEIDENAUER", NULL, REG_SZ, dir, strlen(dir)+1);
  RegCloseKey(Result);
}
//---------------------------------------------------------------------------
char* __fastcall req_getlpsolvedir()
{
  static char regvalue[2000];
  unsigned long dirsize = sizeof(regvalue);
  HKEY Result = openSolverReg();
  if (RegQueryValueEx(Result,"LPSOLVE", NULL,	&type,	regvalue,	&dirsize) != ERROR_SUCCESS) {
    return "L:\\Besf\\solver\\LP_Solve\\exec\\";
  }
  CheckSlash(regvalue);
  return regvalue;
}
//---------------------------------------------------------------------------
void __fastcall reg_setlpsolvedir(char* dir)
{
  HKEY Result = openSolverReg();
  RegSetValueEx(Result, "LPSOLVE", NULL, REG_SZ, dir, strlen(dir)+1);
  RegCloseKey(Result);
}
//---------------------------------------------------------------------------
#pragma package(smart_init)
