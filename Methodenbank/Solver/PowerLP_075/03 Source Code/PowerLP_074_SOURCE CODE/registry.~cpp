//---------------------------------------------------------------------------
#include <direct.h>
#include <vcl.h>
#pragma hdrstop
#include "dir.h"
#include "registry.h"
#include "common.h"
#include <string>
#include <windows.h>

char * workdir;

 // Wird beim Start einmal aufgerufen und legt das Verzeichnis des Temp Ordners fest
void __fastcall reg_setworkdir() {
        char result[ MAX_PATH ];
        std::string str = (std::string( result, GetModuleFileName( NULL, result, MAX_PATH ))).c_str(); //Holt sich verzeichnis von powerlp.exe
        str.resize(str.size()-11);         // "powerlp.exe" wir im string entfernt
        str += "Temp\\";                          // Temp wird angehängt

        char *cstr = new char[str.length() + 1];        // Umwandlung von String in Char
        *strcpy(cstr, str.c_str());
        workdir = cstr;

       }
 //---------------------------------------------------------------------------
 // Gibt das Verzeichnis des Tempordners zurück

char* __fastcall reg_getworkdir() {
        char* localworkdir = workdir;

return localworkdir;
}


//---------------------------------------------------------------------------
// Gibt das Verzeichnis der DosBox zurück

char* __fastcall reg_getdosboxdir() {
char result[ MAX_PATH ];
        std::string str = (std::string( result, GetModuleFileName( NULL, result, MAX_PATH ))).c_str();
        str.resize(str.size()-11);
        str += "DOSBox\\";

        char tab2[1024];
        strncpy(tab2, str.c_str(), sizeof(tab2));
        tab2[sizeof(tab2) - 1] = 0;

return tab2;
}


//---------------------------------------------------------------------------
//  Gibt das Verzeichnis des XA-Solvers zurück


char* __fastcall reg_getxadir() {
char result[ MAX_PATH ];
        std::string str = (std::string( result, GetModuleFileName( NULL, result, MAX_PATH ))).c_str();
        str.resize(str.size()-11);
        str += "Solver\\XA\\";

        char tab2[1024];
        strncpy(tab2, str.c_str(), sizeof(tab2));
        tab2[sizeof(tab2) - 1] = 0;

return tab2;
}


//---------------------------------------------------------------------------
//  Gibt das Verzeichnis des Mops-Solvers zurück

char* __fastcall req_getmopsdir() {
 char result[ MAX_PATH ];
        std::string str = (std::string( result, GetModuleFileName( NULL, result, MAX_PATH ))).c_str();
        str.resize(str.size()-11);
        str += "Solver\\MOPS 7.06\\Exec\\";

        char tab2[1024];
        strncpy(tab2, str.c_str(), sizeof(tab2));
        tab2[sizeof(tab2) - 1] = 0;

return tab2;
}


//---------------------------------------------------------------------------
//  Gibt das Verzeichnis des Ilog-Solvers zurück

char* __fastcall reg_getilogdir() {
 char result[ MAX_PATH ];
        std::string str = (std::string( result, GetModuleFileName( NULL, result, MAX_PATH ))).c_str();
        str.resize(str.size()-11);
        str += "Solver\\ILOG\\";

        char tab2[1024];
        strncpy(tab2, str.c_str(), sizeof(tab2));
        tab2[sizeof(tab2) - 1] = 0;

return tab2;
}


//---------------------------------------------------------------------------
//  Gibt das Verzeichnis des Weidenauer-Solvers zurück

char* __fastcall reg_getweidenauerdir() {
  char result[ MAX_PATH ];
        std::string str = (std::string( result, GetModuleFileName( NULL, result, MAX_PATH ))).c_str();
        str.resize(str.size()-11);
        str += "Solver\\Weidenauer\\Exec\\";

        char tab2[1024];
        strncpy(tab2, str.c_str(), sizeof(tab2));
        tab2[sizeof(tab2) - 1] = 0;

return tab2;
}


//---------------------------------------------------------------------------
//  Gibt das Verzeichnis des Lpsolve-Solvers zurück


char* __fastcall req_getlpsolvedir() {
  char result[ MAX_PATH ];
        std::string str = (std::string( result, GetModuleFileName( NULL, result, MAX_PATH ))).c_str();
        str.resize(str.size()-11);
        str += "Solver\\LP_Solve\\Exec\\";

        char tab2[1024];
        strncpy(tab2, str.c_str(), sizeof(tab2));
        tab2[sizeof(tab2) - 1] = 0;

return tab2;
}

//---------------------------------------------------------------------------
// Ruft setworkdir beim Programmstart auf
void __fastcall reg_init(){
        reg_setworkdir();
        }
        
#pragma package(smart_init)
