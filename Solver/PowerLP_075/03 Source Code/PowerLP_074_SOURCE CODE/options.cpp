//---------------------------------------------------------------------------

#include <vcl.h>
#pragma hdrstop

#include "options.h"
#include "registry.h"
//---------------------------------------------------------------------------
#pragma package(smart_init)
#pragma resource "*.dfm"
TOptionsForm *OptionsForm;
//---------------------------------------------------------------------------
__fastcall TOptionsForm::TOptionsForm(TComponent* Owner): TForm(Owner) {
}
//---------------------------------------------------------------------------
void __fastcall TOptionsForm::BitBtn1Click(TObject *Sender) {
// save
  
  
}
//---------------------------------------------------------------------------
void __fastcall TOptionsForm::FormActivate(TObject *Sender) {
// EditDosbox->Text = reg_getdosboxdir();
//  EditXA->Text = reg_getxadir();
//  EditMOPS->Text = req_getmopsdir();
  EditLPSolve->Text = req_getlpsolvedir();
//  EditILOG->Text = reg_getilogdir();
  EditWeid->Text = reg_getweidenauerdir();
  EditTemp->Text = reg_getworkdir();
}
//---------------------------------------------------------------------------

