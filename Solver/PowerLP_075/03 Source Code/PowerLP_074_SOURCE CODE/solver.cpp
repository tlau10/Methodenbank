//---------------------------------------------------------------------------
#include <vcl.h>
#pragma hdrstop
#include <io.h>
#include <stdio.h>
#include <sys\stat.h>
#include <fcntl.h>
#include <dir.h>
#include <windows.h>
#include "solver.h"
#include "registry.h"
#include "common.h"
#include "lp_xaeq.h"
#include "lp_mps.h"
#include "lp_lpsolvi.h"

#define TEMPNAME "POWERLP"
#define TEMPNAME2 "ILOG"
//---------------------------------------------------------------------------
/**
 * Erzeugt die Datei PowerLp.clp die vom XA verarbeitet wird
 */
void __fastcall TSolver::PutXA_CLP(char* dir) {
  char filename[1000];
  strpncpy(filename, dir, sizeof(filename));
  strcat(filename, TEMPNAME);
  strcat(filename, ".clp");
  int f = open(filename,O_RDWR | O_BINARY | O_CREAT | O_TRUNC, S_IREAD | S_IWRITE);
  if (f < 0) return;
  char tmp[1000];
  writeStr(f, TEMPNAME);
  writeStr(f, ".lp OUTPUT ");
  writeLF(f, "xa.out");
  writeLF(f, "             PAGESIZE 20");
  writeLF(f, "             LINESIZE 100");
  writeLF(f, "             TMARGINS 0");
  writeLF(f, "             BMARGINS 0");
  writeLF(f, "             FIELDSIZE 11");
  writeLF(f, "             DECIMALS 5");
  writeLF(f, "             EUROPEAN NO");
  writeLF(f, "             LMARGINS 0");
  writeLF(f, "             COPIES 1");
  writeLF(f, "             WAIT NO");
  writeLF(f, "*");
  writeLF(f, "             MUTE NO");
  writeLF(f, "             LISTINPUT YES");
  writeLF(f, "             WARNING YES");
  writeLF(f, "             SOLUTION YES");
  writeLF(f, "             CONSTRAINTS YES");
  writeLF(f, "             COSTANALYSIS YES");
  writeLF(f, "             MARGINANALYSIS YES");
  writeLF(f, "             MATLIST NO");
  writeLF(f, "             DEFAULTS NO");
  close(f);
}

//---------------------------------------------------------------------------
/**
 * Generiert die Datei powerlp.cmd die an den Weidenauer Solver uebergeben werden kann
 */

void __fastcall TSolver::PutWEID_CMD(char* dir, bool min, bool integer) {
  char filename[1000];
  strpncpy(filename, dir, sizeof(filename));
  strcat(filename, TEMPNAME);
  strcat(filename, ".cmd");
  int f = open(filename,O_RDWR | O_BINARY | O_CREAT | O_TRUNC, S_IREAD | S_IWRITE);
  writeStr(f, "LOADMATRIX ");
  writeStr(f, dir);
  writeStr(f, TEMPNAME);
  writeLF(f, ".mat");
  if (min) writeLF(f, "MINIMISE");
  else  writeLF(f, "MAXIMISE");
  if (integer) writeLF(f, "GLOBAL");
  writeLF(f,"SAVESOLUTION");
  writeLF(f,"QUIT");
  close(f);
}
/**
 * Generiert die Stappelverarbeitungsdatei fuer den XA Solver
 */
void __fastcall TSolver::PutXA_BAT(char* dir) {

  // xa.exe in Temp Ordner kopieren da DosBox nicht mit Umgebungsvariablen arbeiten kann
  char sourcefile[1000]; //Variable fuer Quelldatei
  char destfile[1000];   //Variable fuer Zieldatei
  strpncpy(sourcefile, reg_getxadir(), sizeof(sourcefile)); //Quellfad
  strcat(sourcefile, "xa.exe"); //zu XA.exe
  strpncpy(destfile, reg_getworkdir(), sizeof(destfile));  //Zielpfad
  strcat(destfile, "xa.exe"); //zu XA.exe
  CopyFileA(sourcefile, destfile,1); //Datei aus Quellpfad in Zielpfad kopieren
  char tempdir[1000]="";
  //Entfernen des letzten Backslash aus dem Work Directory für Aufbau der .bat Datei
  strcpy(tempdir, reg_getworkdir());
  int index = strlen(tempdir);
        if (--index >= 0)
        {
	tempdir[index] = '\0';
        }
  char filename[1000]; //Variable fuer XA.bat
  strpncpy(filename, dir, sizeof(filename));
  strcat(filename, "xa.bat");
  //XA.bat oeffnen und in Datei schreiben
  int f = open(filename,O_RDWR | O_BINARY | O_CREAT | O_TRUNC, S_IREAD | S_IWRITE);
  if (f < 0) return;
  //Text fuer XA und DosBox in XA.bat einfuegen
  writeLF(f,"@echo off");
  writeStr(f, "cd " );
  writeLF(f, reg_getdosboxdir());
  writeStr(f, "DOSBox.exe -c \"mount c ");
  writeStr(f, tempdir);
  writeStr(f, "\" -c \"C:\" -c \"XA.EXE C:\\");
  writeStr(f, TEMPNAME);
  writeStr(f, ".clp");
  writeLF(f, "\" -c \"exit\"");
  close(f); //Stream schliessen
}

/**
 *  Generiert die Stappelverarbeitungsdatei fuer den ILOG Solver
 */
void __fastcall TSolver::PutILOG_BAT(char* dir) {

   // cplex.exe in Temp Ordner kopieren
  char sourcefile[1000];
  char destfile[1000];
  strpncpy(sourcefile, reg_getilogdir(), sizeof(sourcefile));
  strcat(sourcefile, "cplex.exe");
  strpncpy(destfile, reg_getworkdir(), sizeof(destfile));
  strcat(destfile, "cplex.exe");
  CopyFileA(sourcefile, destfile,1);

  //Entfernen des letzten Backslash aus dem WorK Directory für Aufbau der .bat Datei
  char tempdir[1000]="";
  strcpy(tempdir, reg_getworkdir());
  int index = strlen(tempdir);
        if (--index >= 0)
        {
	tempdir[index] = '\0';
        }
   //Erstellen der der Stappelverarbeitungsdatei
  char filename[1000];
  char tempStr[1000];
  strpncpy(filename, dir, sizeof(filename));
  strcat(filename, "ILOG.bat");
  int f = open(filename,O_RDWR | O_BINARY | O_CREAT | O_TRUNC, S_IREAD | S_IWRITE);
  writeLF(f,"@echo off");
  writeLF(f,"");
  writeLF(f,"\cplex.exe -c \"read ILOG.lp \" \"optimize\" \"display solution variables -\" \"write cplex.exe\" \"quit\" ");
  close(f);
}




/**
 * Fuehrt eine Stappelverarbeitungsdatei mit Hilfe der Kommandozeile aus
 */

bool __fastcall runbatfile(char* file, ThrowErrorMsg* err) {
  STARTUPINFO stup;
  PROCESS_INFORMATION pinf;
  memset(&stup, 0, sizeof(STARTUPINFO));
  stup.wShowWindow = SW_HIDE;
  stup.dwFlags = STARTF_USESHOWWINDOW;
  stup.cb = sizeof(STARTUPINFO);
  char cmd[1000];
  char* comspec = getenv("COMSPEC");
  if (comspec) strcpy(cmd, comspec);
  else {
    comspec = "C:\\WINNT\\system32\\CMD.EXE";
    if (!FileExists(comspec)) comspec = "C:\\WINDOWS\\system32\\CMD.EXE";
    if (!FileExists(comspec)) comspec = "C:\\WINDOWS\\COMMAND.COM";
  }
  if (!FileExists(comspec)) return false;
  char batfile[1000];
  strcpy(batfile, "/C ");
  strcat(batfile, file);
  if (!CreateProcess(comspec,	batfile, NULL, NULL, FALSE,
    NORMAL_PRIORITY_CLASS, NULL, reg_getworkdir(), &stup,&pinf)) return false;
  HANDLE ProcessHandle = OpenProcess(SYNCHRONIZE, false, pinf.dwProcessId);
  int rval = WaitForSingleObject(ProcessHandle, 5000);
  if (rval == WAIT_TIMEOUT) {
    char msg[1000];
    sprintf(msg, "5000 ms Execution-Timeout: CMD.EXE %s", batfile);
    err->PutError("Batfile Fehler");
  }
  return true;
}
/**
 * Fuehrt den XA Solver mit Hilfe der DosBox aus
 */

void __fastcall TSolver::RunXA(LPModell* LPM) {
 LPModell* LPTEMP = NULL;
  if (LPM->isIntN()) {   // 0 ... N - Ganzzahligkeit
    LPTEMP = new LPModell(LPM);
    LPM->clone(LPTEMP);
    LPM = LPTEMP;
    if (LPM->hasIntNoHBound())
      LPM->PutError("WARNUNG: XA unterstützt nur 0,1 Ganzzahligkeit. Geben Sie für alle ganzzahligen Variablen eine obere Grenze an!");
    LPM->splitIntN();   //  hat obere Grenze
        //    LPM->PutError("WARNUNG: XA unterstützt nur 0,1 Ganzzahligkeit. Lösung evtl. nicht korrekt.");
  }
  char* dir = reg_getworkdir();
  char filename[1000];
  char xafileworkdir[1000];
  strcpy(filename, reg_getxadir());
  strcat(filename,"xa.exe");
  //xa.exe in workdir loeschen wenn xa folder fehlerhaft
  strcpy(xafileworkdir, reg_getworkdir());
  strcat(xafileworkdir,"xa.exe");
  if (!FileExists(filename)) DeleteFile(xafileworkdir);
  if (!FileExists(filename)) LPM->PutError("Solver XA konnte nicht gestartet werden!");
  strpncpy(filename, dir, sizeof(filename));
  strcat(filename, TEMPNAME);
  strcat(filename, ".lp");
  PutLPtoXAEQFile(LPM, filename);       // zurückparsen
  PutXA_CLP(dir);
  PutXA_BAT(dir);
  strcpy(filename, dir);
  strcat(filename, "xa.bat");
  AnsiString dfout = dir;
  dfout += "xa.out";
  DeleteFile(dfout);
  char dosboxfile[1000];
  strcpy(dosboxfile, reg_getdosboxdir());
  strcat(dosboxfile,"DOSBox.exe");
  if (!FileExists(dosboxfile)) LPM->PutError("DOSBox konnte für XA nicht gestartet werden!");
  if (!runbatfile(filename, LPM)) LPM->PutError("Solver XA konnte nicht gestartet werden!");
  if (LPTEMP) delete LPTEMP;
}

//---------------------------------------------------------------------------
/**
 *Fuehrt den MOPS Solver aus
 *
 *Uebergabeparamter: Das zu loesende LP-Modell
 */
void __fastcall TSolver::RunMOPS(LPModell* LPM) {
  char* dir = reg_getworkdir();
  char filename[1000];
  char mopsFilename[1000];

  strcpy(filename, req_getmopsdir());
  strcat(filename,"mops.exe");
  if (!FileExists(filename)) LPM->PutError("Solver MOPS konnte nicht gestartet werden!");

  strcpy(mopsFilename, reg_getworkdir());
  strcat(mopsFilename,"mops.exe");

  // copy mops.exe to workdir;
  CopyFile(filename,mopsFilename,false);

  strpncpy(filename, dir, sizeof(filename));
  strcat(filename, TEMPNAME);
  strcat(filename, ".mps");
  PutLPtoMPSFile(LPM, filename);      // !!!! zurückparsen
  PutMOPS_BAT(LPM, reg_getworkdir());
  PutXMOPS_PRO(LPM);

  strcpy(filename, "mops.bat");
  if (!runbatfile(filename, LPM)) LPM->PutError("Solver MOPS konnte nicht gestartet werden!");
}

//---------------------------------------------------------------------------
/**
 *Fuehrt den LPSolve Solver aus
 *
 *Uebergabeparamter: Das zu loesende LP-Modell
 */

void __fastcall TSolver::RunLPSolve(LPModell* LPM) {
  char* dir = reg_getworkdir();
  char filename[1000];
  strcpy(filename, req_getlpsolvedir());
  strcat(filename,"lp_solve.exe");
  if (!FileExists(filename)) LPM->PutError("Solver LPSolve konnte nicht gestartet werden!");
  strpncpy(filename, dir, sizeof(filename));
  strcat(filename, TEMPNAME);

  strcat(filename, ".lin");
  PutLPtoLPSOLVFile(LPM, filename);   // zurückparsen
  dir = req_getlpsolvedir();
  strpncpy(filename, dir, sizeof(filename));
  strcat(filename, "lp_solve.exe ");
  //Im Rahmen des Teamprojektes SS13 um den PAramter -S6 erweitert
  strcat(filename, "-p -S6< ");
  //------------------------------------------------------------
  strcat(filename, TEMPNAME);
  strcat(filename, ".lin");
  /*
  strcat(filename, ".mps");
  PutLPtoMPSFile(LPM, filename);
  dir = req_getlpsolvedir();
  strpncpy(filename, dir, sizeof(filename));
  strcat(filename, "lp_solve.exe ");
  strcat(filename, "-mps < ");
  strcat(filename, TEMPNAME);
  strcat(filename, ".mps");
  */
  strcat(filename, " >lp_solve.out");
  if (!runbatfile(filename, LPM)) LPM->PutError("Solver LP-Solve konnte nicht gestartet werden!");
}
//---------------------------------------------------------------------------
/**
 *Fuehrt den ILOG Solver aus
 */

 void __fastcall TSolver::RunILOG(LPModell* LPM) {

  char* dir = reg_getworkdir();
  char filename[1000];
  strcpy(filename, reg_getilogdir());
  //Überprüft ob sich der ILOG Solver im angegebenen Pfad befindet
  strcat(filename,"cplex.exe");
  //Es wir überprüft ob die cplex.exe im  angegebenen Pfad vorhanden ist
  if (!FileExists(filename)) LPM->PutError("Solver ILOG konnte nicht gestartet werden!");

  strpncpy(filename, dir, sizeof(filename));
  strcat(filename, TEMPNAME2);           //TEMPNAME2 = ILOG
  strcat(filename, ".lp");
  PutLPtoILOGFile(LPM, filename);        //zurückparsen
  PutILOG_BAT(dir);
  strcpy(filename,dir);
  strcat(filename, "ILOG.bat");
  // Vorherige Lösungsdateien/Logdateien des ILOG Solvers werden gelöscht
  AnsiString dfout = dir;
  AnsiString dfout1 = dir;
  dfout += "ILOG.out";
  dfout1 += "cplex.log";
  DeleteFile(dfout);
  DeleteFile(dfout1);
  //Es wird überprüft ob die ILOG.bat Datei vorhanden ist
  if (!runbatfile(filename, LPM)) LPM->PutError("Solver ILOG konnte nicht gestartet werden!");

}



//---------------------------------------------------------------------------
/**
 *Erzeugt Stapelverarbeitungsdatei fuer den Weidenauer Solver
 */

void __fastcall TSolver::PutWEID_BAT(ThrowErrorMsg* emsg, char* dir) {
  char filename[1000];
  strpncpy(filename, dir, sizeof(filename));
  strcat(filename, "weid.bat");
  int f = open(filename,O_RDWR | O_BINARY | O_CREAT | O_TRUNC, S_IREAD | S_IWRITE);
  if (f < 0) {
    char msg[1000];
    sprintf(msg, "Datei %s konnte nicht erstellt werden",filename);
    emsg->PutError(msg);
  }
  writeLF(f,"@echo off");
  char* tdir = reg_getweidenauerdir();
  //write(f, tdir, 2);      // Gibt C: wegen der 2 aus, wird vllt nicht benötigt
  writeLF(f,"");
  writeStr(f, "cd ");
  writeLF(f, tdir);
  writeStr(f, "lp.exe ");
  writeStr(f, dir);
  writeStr(f, TEMPNAME);
  writeLF(f, ".cmd");
  close(f);
}
//---------------------------------------------------------------------------
/**
 *Erzeugt Stapelverarbeitungsdatei fuer den MOPS Solver
 */

void __fastcall TSolver::PutMOPS_BAT(ThrowErrorMsg* emsg, char* dir) {
  char filename[1000];
  char tempStr[1000];
  strpncpy(filename, dir, sizeof(filename));
  strcat(filename, "mops.bat");
  int f = open(filename,O_RDWR | O_BINARY | O_CREAT | O_TRUNC, S_IREAD | S_IWRITE);
  if (f < 0) {
    char msg[1000];
    sprintf(msg, "Datei %s konnte nicht erstellt werden",filename);
    emsg->PutError(msg);
  }
  writeLF(f,"@echo off");
  writeLF(f,"REM Solver: MOPS Win32-Version");
  writeLF(f,"");

  strcpy(tempStr, reg_getworkdir());               //Vorher: "cd temp\\"
  writeLF(f,tempStr);

  //Loeschen der alten MOPS-Ergebnisdatei
  writeLF(f,"echo Kein Ergebnis > powerlp.lps");

  writeStr(f,"path ");
  strcpy(tempStr, reg_getworkdir());
  strcat(tempStr, ";%path%");
  writeLF(f,tempStr);
  writeLF(f,"mops.exe");
  close(f);

  strcat(filename, TEMPNAME);
}

//---------------------------------------------------------------------------
/**
 *Erzeugt Pro-Datei fuer den Weidenauer Solver
 *
 *Uebergabeparamter: Das zu loesende LP-Modell
 */

void __fastcall TSolver::PutXMOPS_PRO(LPModell* LPM) {
  char filename[1000];

  strcpy(filename, reg_getworkdir());
  strcat(filename, "xmops.pro");
  int f = open(filename,O_RDWR | O_BINARY | O_CREAT | O_TRUNC, S_IREAD | S_IWRITE);
  if (f < 0) {
    char msg[1000];
    sprintf(msg, "Datei %s konnte nicht erstellt werden",filename);
    LPM->PutError(msg);
  }

  writeLF(f,"xoutsl = 1");
  writeLF(f,"xlptyp = 0");
  writeLF(f,"xmxmin = 15.0");
  writeLF(f,"");
  writeLF(f,"xfnmps = 'powerlp.mps'");  //Dateiname fuer den Speicherort des MOPS Ergebnisses

  if (LPM->getMinimize())
    writeLF(f,"xminmx='min'");
  else
    writeLF(f,"xminmx='max'");

  close(f);
}
//---------------------------------------------------------------------------
bool testwrite(char* filename) {
  int f = open(filename,O_RDWR | O_BINARY, S_IREAD);
  if (f <= 0) return false;
  close(f);
  return true;
}
//---------------------------------------------------------------------------
/**
 *Fuehrt den Weidenauer Solver aus
 *
 *Uebergabeparamter: Das zu loesende LP-Modell
 */

void __fastcall TSolver::RunWeidenauer(LPModell* LPM) {
  char* dir = reg_getworkdir();
  char filename[1000];
  strcpy(filename, reg_getweidenauerdir());
  strcat(filename,"lp.exe");
  if (!FileExists(filename)) LPM->PutError("Solver Weidenauer konnte nicht gestartet werden!");
  strpncpy(filename, dir, sizeof(filename));
  strcat(filename, TEMPNAME);
  strcat(filename, ".mat");
  PutLPtoMPSFile(LPM, filename);     // !!! zurückparsen
  bool integer = false;
  for (int x = 0; x < LPM->getVarCount(); x++) {
    if (LPM->getInteger(x)) { integer = true; break; }
  }
  PutWEID_CMD(dir, LPM->getMinimize(), integer);
  PutWEID_BAT(LPM, dir);
  strpncpy(filename, dir, sizeof(filename));
  strcat(filename, "weid.bat");
/*  strpncpy(filename, reg_getweidenauerdir(), sizeof(filename));
  strcat(filename, "lp.exe");
  AnsiString cmd;
  cmd = dir;
  cmd += TEMPNAME;
  cmd += ".cmd";
  if (!runfile(filename, cmd.c_str(), reg_getweidenauerdir()))
    LPM->PutError("Solver Weidenauer konnte nicht gestartet werden!");*/
  if (LPM->isMixedMode()) LPM->PutError("WARNUNG: Weidenauer unterstützt keinen Mixed-Mode (teilweise Ganzzahligkeit der Variablen).");
  if (!runbatfile(filename, LPM))
    LPM->PutError("Solver Weidenauer konnte nicht gestartet werden!");
}
//---------------------------------------------------------------------------
/**
 *Hilfsmethode die auf eine Datei wartet während der Solver die Berechnung durchfuehrt
 *
 *Uebergabeparamter: Die Datei auf die gewartet wird
 */

bool __fastcall waitForFile(char* file) {
  int timeout = 50;
  while (timeout && !testwrite(file)) {
    Sleep(10);
    timeout--;
  }
  return timeout == 0;
}
//---------------------------------------------------------------------------
/**
 * Holt sich die Output/Ergebnis Datei des XA Solvers (xa.out)
 */

bool __fastcall TSolver::getXAFile(char* file, int size) {
  char* dir = reg_getworkdir();
  char filename[1000];
  strpncpy(filename, dir, sizeof(filename));
  strcat(filename, "xa.out");
  strpncpy(file, filename, size);
  return waitForFile(filename);
}

//------------------------------------------------------------------------------
/**
 *Holt sich die Output/Ergebnis Datei des MOPS Solvers (powerlp.lps)
 */

bool __fastcall TSolver::getMOPSFile(char* file, int size) {
  char* dir = reg_getworkdir();
  char filename[1000];
  strpncpy(filename, dir, sizeof(filename));
  strcat(filename, TEMPNAME);
  strcat(filename, ".lps");
  strpncpy(file, filename, size);
  if (!FileExists(file)) {
    strpncpy(filename, dir, sizeof(filename));
    strcat(filename, "mops.log");
    strpncpy(file, filename, size);
  }
  return waitForFile(filename);
}

//------------------------------------------------------------------------------
/**
 * Holt sich die Output/Ergebnis Datei des ILOG Solvers (ILOG.out)
 * Falls diese nicht vorhanden ist, sprich der ILOG solver das Problem nicht lösen kann,
 * wird dei cplex.log Datei eingelesen, in der der Grund beschrieben ist warum es keine Lösung gibt
 */
bool __fastcall TSolver::getILOGFile(char* file, int size) {

  char* dir = reg_getworkdir();
  char filename[1000];

  //Es wir überprüft ob die ILOG.out Datei Vorhanden ist
  if (FileExists("ILOG.out")) {
    strpncpy(filename, dir, sizeof(filename));
    strcat(filename, "ILOG.Out");
    strpncpy(file, filename, size);
  }
  // Wenn die ILOG.out Datei nicht vorhanden ist, wird die cplex.log Datei eingelesen
  else{
     strpncpy(filename, dir, sizeof(filename));
     strcat(filename, "cplex.log");
     strpncpy(file, filename, size);
  }

  return waitForFile(filename);
}

//------------------------------------------------------------------------------
/**
 *Holt sich die Output/Ergebnis Datei des Weidenauer Solvers (powerlp.los)
 */

bool __fastcall TSolver::getWeidenauerFile(char* file, int size) {
  char* dir = reg_getworkdir();
  char filename[1000];
  strpncpy(filename, dir, sizeof(filename));
  strcat(filename, TEMPNAME);
  strcat(filename, ".los");
  strpncpy(file, filename, size);
  return waitForFile(filename);
}
//------------------------------------------------------------------------------
/**
 *Holt sich die Output/Ergebnis Datei des LPSolve Solvers (lp_solve.out)
 */

bool __fastcall TSolver::getLPSolveFile(char* file, int size) {
  char* dir = reg_getworkdir();
  char filename[1000];
  strpncpy(filename, dir, sizeof(filename));
  strcat(filename, "lp_solve.out");
  strpncpy(file, filename, size);
  return waitForFile(filename);
}

#pragma package(smart_init)




