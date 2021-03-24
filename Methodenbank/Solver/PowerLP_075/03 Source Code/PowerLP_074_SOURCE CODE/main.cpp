//---------------------------------------------------------------------------
#include <vcl.h>
#pragma hdrstop
#include <dir.h>

#include "Main.h"
#include "About.h"
#include "registry.h"
#include "common.h"
#include "options.h"
//---------------------------------------------------------------------------
#pragma resource "*.dfm"
TMainForm *MainForm;
//---------------------------------------------------------------------------
__fastcall TMainForm::TMainForm(TComponent *Owner): TForm(Owner) {      // Konstruktor
}
//---------------------------------------------------------------------------
TMDIChild* __fastcall TMainForm::CreateMDIChild(String pName) {
                                // erzeugt das 'Unter'-fenster
	TMDIChild *Child;

  AnsiString Name = pName;
    if (Name.Length() == 0) {
    Name = "NEU";
    Name += IntToStr(MDIChildCount + 1);
  }
	//--- Neues untergeordnetes MDI-Fenster erzeugen ----
	Child = new TMDIChild(Application);    // Child->TransferFromLP()
	Child->Caption = Name;
	if (FileExists (Name)) Child->LoadFile(Name.c_str());   // weiter geht's ....
  return Child;        // das file wird eingelesen  -> Parser einsetzen !!!
}
//---------------------------------------------------------------------------
void __fastcall TMainForm::FileNew1Execute(TObject *Sender) {
	CreateMDIChild("");                         // Datei -> Neu
}
//---------------------------------------------------------------------------
void __fastcall TMainForm::FileOpen1Execute(TObject *Sender) {
  //OpenDialog->InitialDir = reg_getdatadir();          // Datei -> Oeffnen
	if (OpenDialog->Execute()) {       // open the dialog box
    char dir[200];
    getcwd(dir,sizeof(dir));       // gets current working directory
    //reg_setdatadir(dir);          // das letze Verz. wird in der Registry gespeichert
		CreateMDIChild(OpenDialog->FileName);
  }       //  returns the name and path of the most recently selected file
}
//---------------------------------------------------------------------------
void __fastcall TMainForm::HelpAbout1Execute(TObject *Sender) {
	AboutBox->ShowModal();        // Hilfe -> Info
}
//---------------------------------------------------------------------------
void __fastcall TMainForm::FileExit1Execute(TObject *Sender) {
	Close();                          // Datei -> Beenden
}
//---------------------------------------------------------------------------
void __fastcall TMainForm::FormActivate(TObject *Sender) {
  static bool firstshow = false;
  if (firstshow) return;
  firstshow = true;                            // Start
  FileNew1Execute(Sender);
}
//---------------------------------------------------------------------------
bool __fastcall TMainForm::FileSave() {
  TMDIChild* Child = (TMDIChild*)ActiveMDIChild;
  if (FileExists(Child->Caption))
    return Child->StoreFile(Child->Caption.c_str(),-1);   // LP speichern
  else {
    return FileSaveAs();
  }
}
char* supported_filelist[3] = { "lpi","lp", "mps" };
//---------------------------------------------------------------------------
bool __fastcall TMainForm::FileSaveAs() {
    TMDIChild* Child = (TMDIChild*)ActiveMDIChild;
    //SaveDialog1->InitialDir = reg_getdatadir();
    SaveDialog1->FileName = Child->Caption;
    if (SaveDialog1->Execute()) {
      char dir[200];
      getcwd(dir,sizeof(dir));
      //reg_setdatadir(dir);
      char file[1024];
      strpncpy(file,SaveDialog1->Files->Strings[0].c_str(), sizeof(file));
      char* dotpos = strrchr(file, '.');
      if (dotpos == NULL) {
        strcat(file, ".");
        strcat(file, supported_filelist[SaveDialog1->FilterIndex-1]);
      } else {
        dotpos[1] = 0;
        strcat(file, supported_filelist[SaveDialog1->FilterIndex-1]);
      }
      Child->Caption = file;
      return Child->StoreFile(file, SaveDialog1->FilterIndex-1);  // LP speichern
    } else return false;
}
//---------------------------------------------------------------------------
void __fastcall TMainForm::FileSave1Execute(TObject *Sender) {
  FileSave();                                      // Datei -> Speichern
}
//---------------------------------------------------------------------------
void __fastcall TMainForm::FileSaveAs1Execute(TObject *Sender) {
  FileSaveAs();                    // Datei -> Speichern unter
}
//---------------------------------------------------------------------------
void __fastcall TMainForm::FileSaveAsItemClick(TObject *Sender) {
  SaveDialog1->Execute();
}
//---------------------------------------------------------------------------


void __fastcall TMainForm::Einstellungen1Click(TObject *Sender) {
  OptionsForm->ShowModal();           // Datei -> Einstellungen
}
//---------------------------------------------------------------------------



