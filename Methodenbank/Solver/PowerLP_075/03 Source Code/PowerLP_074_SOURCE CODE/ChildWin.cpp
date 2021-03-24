//---------------------------------------------------------------------
#include <vcl.h>
#pragma hdrstop
#include <stdio.h>
#include <math.h>
#include "common.h"
#include "ChildWin.h"
#include "main.h"
//---------------------------------------------------------------------
#pragma resource "*.dfm"
//---------------------------------------------------------------------
__fastcall TMDIChild::TMDIChild(TComponent *Owner): TForm(Owner) {  // Konstruktor
  LP->ColWidths[0] = 90;         // Spaltenbreite Grid (Formular)
  LPD->ColWidths[0] = 90;
  ErrorHandler = new EHandler(this);
  LPM = new LPModell(ErrorHandler);     // LPM -> LP-Modell (Businessklasse)
  LPDual = new LPModell(ErrorHandler);
  LPM->setSize(2,2);   //  Anz. Variablen , Anz. Restriktionen
  LPDual->setSize(2,2);
  LP->ColWidths[LP->ColCount-2] = 30;
  LPD->ColWidths[LPD->ColCount-2] = 30;
  PageControl->ActivePage = PagePrimal;
  PanelError->Height = 0;
  TransferFromLP();     // GUI aufbereiten nach LPM 
  changed = false;
  CLP = LPM;         // changed LP
}
//---------------------------------------------------------------------
void __fastcall TMDIChild::FormClose(TObject *Sender, TCloseAction &Action) {
	Action = caFree;                 // wenn Form geschlossen wird (Event)
  delete LPM;
}
//--------------------------------------------------------------------------
void __fastcall TMDIChild::LoadFromFile(TMemo* Memo, char* filename) {
  char msg[2000];
  try {
    Memo->Lines->LoadFromFile(filename);
  } catch (Exception& e) {
    sprintf(msg, "Konnte Datei %s nicht öffnen", filename);
    PutError(msg);
  }
}
//--------------------------------------------------------------------------
void __fastcall TMDIChild::Button1Click(TObject *Sender) {    // Lösen -> Button
  ClearError();
  MemoXA->Lines->Clear();
  MemoMOPS->Lines->Clear();
  MemoLPSolve->Lines->Clear();
  MemoILOG->Lines->Clear();
  MemoWeidenauer->Lines->Clear();

  if (PageControl->ActivePage == PageDual || CLP == LPDual) {
//    CLP = LPDual;
    LabelSolution->Caption = "Lösung Dual";
  } else if (PageControl->ActivePage == PagePrimal || CLP == LPM) {
//    CLP = LPM;
    if (TransferToLP()) {
      PutError("Parser Fehler, Ausführung gestoppt.");
      PageControl->ActivePage = PagePrimal;
      return;
    }
    LabelSolution->Caption = "Lösung Primal";
  } else {
    // Erneut loesen
  }
  char filename[2000];
  if (CheckBoxXA->Checked) {      // nur die Solver benutzen, die 'ausgewählt' sind
    MemoXA->Lines->Clear();
    Solver->RunXA(CLP);
    if (!Solver->getXAFile(filename, sizeof(filename))) {
      LoadFromFile(MemoXA, filename);
    }
  }
  if (CheckBoxMOPS->Checked) {
    Solver->RunMOPS(CLP);
    if (!Solver->getMOPSFile(filename, sizeof(filename))) {
      LoadFromFile(MemoMOPS, filename);
    }
  }
  if (CheckBoxLPSolve->Checked) {
    Solver->RunLPSolve(CLP);
    if (!Solver->getLPSolveFile(filename, sizeof(filename))) {
      LoadFromFile(MemoLPSolve, filename);
    }
  }
  if (CheckBoxILOG->Checked) {
    Solver->RunILOG(CLP);
    if (!Solver->getILOGFile(filename, sizeof(filename))) {
      LoadFromFile(MemoILOG, filename);
    }
  }  
  if (CheckBoxWeidenauer->Checked) {
    Solver->RunWeidenauer(CLP);
    if (!Solver->getWeidenauerFile(filename, sizeof(filename))) {
      LoadFromFile(MemoWeidenauer, filename);
    }
  }

  PageControl->ActivePage = PageLoesung;
}
//---------------------------------------------------------------------------
void __fastcall TMDIChild::LoadFile(char* filename) {
       // LPM (LP-Modell) ist Attribut der Klasse ChildWin
  fileformat = GetLPFromFile(LPM, filename);
  TransferFromLP();
}
//---------------------------------------------------------------------------
bool __fastcall TMDIChild::StoreFile(char* filename, int format) {
  if (TransferToLP()) {        // Formular wird zuerst in LP geschrieben
    PutError("Parser Fehler, Ausführung gestoppt.");
    return false;
  }
  if (format < 0) format = fileformat;
  if (PutLPtoFile(LPM, filename, format)) {  // im LPM ist das (gefüllte) LPModell
    changed = false;
    return true;         //  alles gut gegangen ...
  }
  PutError("Datei konnte nicht gespeichert werden. Wählen Sie Speichern unter...!");
  return false;
}
//---------------------------------------------------------------------------
void __fastcall TMDIChild::LabelGrids() {      // Bezeichnungen der Grids
  LP->Cells[0][1] = "Zielfunktion";
  char tmp[200];
  for (int x = 0; x < LP->RowCount-1;x++) {
    sprintf(tmp, "Restriktion %i", x+1);
    LP->Cells[0][2+x] = tmp;
  }
  int x = 0;
  for (; x < LP->ColCount-3;x++) {
    sprintf(tmp, " X%i", x+1);
    LP->Cells[1+x][0] = tmp;
  }
  LP->Cells[x+1][0] = "";
  LP->Cells[x+2][0] = " b";
  LP->Cells[x+1][1] = "-->";
  if (RadioMin->Checked) {
    LP->Cells[x+2][1] = "min !";
  } else {
    LP->Cells[x+2][1] = "max !";
  }

  RS->Cells[1][0] = "Untere Grenze";
  RS->Cells[2][0] = "Obere Grenze";
  RS->Cells[3][0] = "Ganzzahl";
  RS->ColWidths[0] = 30;
  for (int x = 0; x < RS->RowCount-1; x++) {
    sprintf(tmp, " X%i", x+1);
    RS->Cells[0][x+1] = tmp;
  }
  // Dual
  LPD->Cells[0][1] = "Zielfunktion";
  for (int x = 0; x < LPD->RowCount-1;x++) {
    sprintf(tmp, "Restriktion %i", x+1);
    LPD->Cells[0][2+x] = tmp;
  }
  x = 0;
  for (; x < LPD->ColCount-3;x++) {
    sprintf(tmp, " U%i", x+1);
    LPD->Cells[1+x][0] = tmp;
  }
  LPD->Cells[x+1][0] = "";
  LPD->Cells[x+2][0] = " b";
  LPD->Cells[x+1][1] = "-->";
  if (RadioMin->Checked) {
    LabelDualType->Caption = "Maximierung";
    LPD->Cells[x+2][1] = "max !";
  } else {
    LabelDualType->Caption = "Minimierung";
    LPD->Cells[x+2][1] = "min !";
  }
  RSD->Cells[1][0] = "Untere Grenze";
  RSD->Cells[2][0] = "Obere Grenze";
//  RSD->Cells[3][0] = "Ganzzahl";
  RSD->ColWidths[0] = 30;
  for (int x = 0; x < RSD->RowCount-1; x++) {
    sprintf(tmp, " U%i", x+1);
    RSD->Cells[0][x+1] = tmp;
  }

}
//------------------------------------------------------------------------------
void __fastcall TMDIChild::UpDownVarsClick(TObject *Sender,
      TUDBtnType Button) {
  TransferToLP();
  LPM->setSize(UpDownVars->Position,UpDownRes->Position);
  changed = true;
  TransferFromLP();
}
//---------------------------------------------------------------------------
void __fastcall TMDIChild::RSClick(TObject *Sender) {
  int x = RS->Selection.Left;
  int y = RS->Selection.Top;
  if (y > 0 && x == 3) {
    RS->Options >> goEditing;
    if (RS->Cells[x][y] == "Ja")
      RS->Cells[x][y] = "Nein";
    else
      RS->Cells[x][y] = "Ja";
  } else {
    RS->Options << goEditing;
  }
}
//---------------------------------------------------------------------------
void __fastcall TMDIChild::PutError(char* msg) {
  ListBoxError->Items->Add(msg);
  ListBoxError->SetFocus();
  int cnt = ListBoxError->Items->Count;
  if (cnt > 10) cnt = 10;
  PanelError->Height = cnt * 13 +20;
  ListBoxError->ScrollBy(0,-1000000);
}
//---------------------------------------------------------------------------
void __fastcall TMDIChild::ClearError() {
  ListBoxError->Clear();
  PanelError->Height = 0;
}
//---------------------------------------------------------------------------
float getFloat(AnsiString as) {
  if (as.Length() == 0) return 0;
  char od = DecimalSeparator;
  DecimalSeparator = ',';
  float result;
  try {
    result = as.ToDouble();
  } catch (EConvertError &e) {
    DecimalSeparator = '.';
    try {
      result = as.ToDouble();
    } catch (EConvertError &e) {
      DecimalSeparator = od;
      throw EConvertError(e.Message);
    }
  }
  DecimalSeparator = od;
  return result;
}
//---------------------------------------------------------------------------
bool __fastcall TMDIChild::TransferToLP() {         // to
  bool error = false;
  LPM->setMinimize(RadioMin->Checked);
  ClearError();
  // Zielfunktion
  int vars = LP->ColCount -3;
  int res = LP->RowCount -2;
  AnsiString tmp;
  for (int x = 0; x < vars; x++) {
    float value = 0.0;
    try {
      value = getFloat(LP->Cells[1+x][1]); // .ToDouble();
    } catch (EConvertError &e) {
      tmp.printf( "Parser-Fehler Zielfunktion X%i: %s", x+1, e.Message);
      PutError(tmp.c_str());
      error = true;
    }
    LPM->setZF(x, value);
  }
  // Restriktionen
  for (int x = 0; x < vars;x++) {
    for (int y = 0; y < res;y++) {
      float value = 0.0;
      try {
        value = getFloat(LP->Cells[1+x][2+y]);
      } catch (EConvertError &e) {
        tmp.printf( "Parser-Fehler Restriktion %i Variable X%i: %s",y+1, x+1,
                                                  e.Message);
        PutError(tmp.c_str());
        error = true;
      }
      LPM->setRes(x,y,value);
    }
  }
  // B-Vector & Relation
  for (int y = 0; y < res;y++) {
    float value = 0.0;
    try {
      value = getFloat(LP->Cells[vars+2][2+y]);
    } catch (EConvertError &e) {
      tmp.printf( "Parser-Fehler Zielfunktion X%i: %s", y+1, e.Message);
      PutError(tmp.c_str());
      error = true;
    }
    char relstr[3];
    memcpy(relstr, LP->Cells[vars+1][2+y].c_str(),3);
    relation rel = STEQUAL;
    if (!stricmp(relstr,"<")) rel = SMALLER;
    else if (!stricmp(relstr,">")) rel = LARGER;
    else if (!stricmp(relstr,"=")) rel = EQUAL;
    else if (!stricmp(relstr,"<=")) rel = STEQUAL;
    else if (!stricmp(relstr,">=")) rel = LTEQUAL;
    else {
      tmp.printf( "Parser-Fehler Relation bei Restriktion %i (\"%s\") Möglich sind nur <,>,<=,>=,=", y+1, relstr);
      PutError(tmp.c_str());
      error = true;
    }
    LPM->setBV(y, value);
    LPM->setRel(y, rel);
  }
  // Untere / Obere Grenze / Integer
  for (int x = 0; x < vars; x++) {
    float valueL = 0.0;
    float valueH = 0.0;
    bool integer = false;
    try {
      if (RS->Cells[3][x+1] == "Ja") integer = true;
      valueL = getFloat(RS->Cells[1][x+1]);
      valueH = getFloat(RS->Cells[2][x+1]);
    } catch (EConvertError &e) {
      tmp.printf( "Parser-Fehler Grenze X%i: %s", x+1, e.Message);
      PutError(tmp.c_str());
      error = true;
    }
    LPM->setBounds(x, valueL, valueH);
    LPM->setInteger(x, integer);
  }
  return error;
}
//------------------------------------------------------------------------------
char* floatToStr(char* str, float value) {
  float absv = fabs(value);
  if (absv > 999999) sprintf(str, "%f", value);
  else if (absv > 0 && absv < 0.0001) sprintf(str, "%.20f", value);
  else sprintf(str, "%g", value);
//  sprintf(str, "%g", value);
  strreplace(str, ".", ",");
  return str;
}
//------------------------------------------------------------------------------
void __fastcall TMDIChild::TransferFromLP() {          // from GUI to Model
  // Zielfunktion
  int vars = LPM->getVarCount();    // Anzahl Variablen
  int res = LPM->getResCount();     // Anzahl Restriktionen
  int oldpos  = LP->ColCount-3;
  int relwidth = LP->ColWidths[LP->ColCount-2];
  int bwidth = LP->ColWidths[LP->ColCount-1];
  int lastxw = LP->ColWidths[LP->ColCount-3];
  LP->RowCount = res+2;
  LP->ColCount = vars+3;
  RS->RowCount = vars+1;
  LP->ColWidths[oldpos] = lastxw;
  LP->ColWidths[oldpos+1] = lastxw;
  if (LP->ColCount > oldpos+3) LP->ColWidths[oldpos+2] = lastxw;
  LP->ColWidths[LP->ColCount-2] = relwidth;
  LP->ColWidths[LP->ColCount-1] = bwidth;
  LP->ColWidths[LP->ColCount-3] = lastxw;
  UpDownVars->Position = vars;     // 'Anzahl'- Felder werden mit
  UpDownRes->Position = res;       // Werten belegt
  AnsiString tmp;
  char ftmp[200];
  // Minimize / Maximize
  if (LPM->getMinimize()) RadioMin->Checked = true;
  else RadioMax->Checked = true;
  // Zielfunktion
  for (int x = 0; x < vars; x++) {
    floatToStr(ftmp, LPM->getZF(x));
    LP->Cells[1+x][1] = ftmp;
  }
  // Restriktionen
  for (int x = 0; x < vars;x++) {
    for (int y = 0; y < res;y++) {
      floatToStr(ftmp, LPM->getRes(x,y));
      LP->Cells[1+x][2+y] = ftmp;
    }
  }
  // B-Vector & Relation
  for (int y = 0; y < res;y++) {
    floatToStr(ftmp, LPM->getBV(y));
    LP->Cells[vars+2][2+y] = ftmp;
    char* relstr = "<=";
    switch (LPM->getRel(y)) {
      case SMALLER: relstr = "<";  break;
      case LARGER:  relstr = ">";  break;
      case EQUAL:   relstr = "=";  break;
      case STEQUAL: relstr = "<=";  break;
      case LTEQUAL: relstr = ">=";  break;
    }
    LP->Cells[vars+1][2+y] = relstr;
  }
  // Untere / Obere Grenze / Integer
  for (int x = 0; x < vars; x++) {
    if (LPM->getInteger(x)) RS->Cells[3][x+1] = "Ja";
    else RS->Cells[3][x+1] = "Nein";
    floatToStr(ftmp, LPM->getLBound(x));
    RS->Cells[1][x+1] = ftmp;
    floatToStr(ftmp, LPM->getHBound(x));
    RS->Cells[2][x+1] = ftmp;
  }
  LabelGrids();
}
//------------------------------------------------------------------------------
void __fastcall TMDIChild::TransferFromLPDual(LPModell* LPM) {
  // Zielfunktion
  int vars = LPM->getVarCount();
  int res = LPM->getResCount();
  int oldpos  = LPD->ColCount-3;
  int relwidth = LPD->ColWidths[LPD->ColCount-2];
  int bwidth = LPD->ColWidths[LPD->ColCount-1];
  int lastxw = LPD->ColWidths[LPD->ColCount-3];
  LPD->RowCount = res+2;
  LPD->ColCount = vars+3;
  RSD->RowCount = vars+1;
  LPD->ColWidths[oldpos] = lastxw;
  LPD->ColWidths[oldpos+1] = lastxw;
  if (LPD->ColCount > oldpos+3) LPD->ColWidths[oldpos+2] = lastxw;
  LPD->ColWidths[LPD->ColCount-2] = relwidth;
  LPD->ColWidths[LPD->ColCount-1] = bwidth;
  LPD->ColWidths[LPD->ColCount-3] = lastxw;
//  UpDownVars->Position = vars; // wohl lieber nicht !
//  UpDownRes->Position = res;
  AnsiString tmp;
  char ftmp[200];
  for (int x = 0; x < vars; x++) {
    floatToStr(ftmp, LPM->getZF(x));
    LPD->Cells[1+x][1] = ftmp;
  }
  // Restriktionen
  for (int x = 0; x < vars;x++) {
    for (int y = 0; y < res;y++) {
      floatToStr(ftmp, LPM->getRes(x,y));
      LPD->Cells[1+x][2+y] = ftmp;
    }
  }
  // B-Vector & Relation
  for (int y = 0; y < res;y++) {
    floatToStr(ftmp, LPM->getBV(y));
    LPD->Cells[vars+2][2+y] = ftmp;
    char* relstr = "<=";
    switch (LPM->getRel(y)) {
      case SMALLER: relstr = "<";  break;
      case LARGER:  relstr = ">";  break;
      case EQUAL:   relstr = "=";  break;
      case STEQUAL: relstr = "<=";  break;
      case LTEQUAL: relstr = ">=";  break;
    }
    LPD->Cells[vars+1][2+y] = relstr;
  }
  // Untere / Obere Grenze / Integer
  for (int x = 0; x < vars; x++) {
//    if (LPM->getInteger(x)) RSD->Cells[3][x+1] = "Ja";
//    else RSD->Cells[3][x+1] = "Nein";
    floatToStr(ftmp, LPM->getLBound(x));
    RSD->Cells[1][x+1] = ftmp;
    floatToStr(ftmp, LPM->getHBound(x));
    RSD->Cells[2][x+1] = ftmp;
  }
  LabelGrids();
}
//------------------------------------------------------------------------------
void __fastcall TMDIChild::RadioMaxClick(TObject *Sender) {
  LabelGrids();
  changed = true;
}
//---------------------------------------------------------------------------
void __fastcall TMDIChild::FormCloseQuery(TObject *Sender, bool &CanClose) {
  // modified etc.                               // Event
  if (!changed) return;
  CanClose = false;
  Show();
  ClearError();
  switch (Application->MessageBox("Wollen Sie diese Datei speichern ?",
       Caption.c_str(), MB_YESNOCANCEL)) {
     case IDNO: CanClose = true;
     break;
     case IDYES:
       if (TransferToLP()) {
         PutError("Parser Fehler, Speichern gestoppt.");
         return;
       }
       if (MainForm->FileSave()) CanClose = true;
     break;
     case IDCANCEL: // nix machen.
     break;
  }
}
//---------------------------------------------------------------------------
void __fastcall TMDIChild::LPSetEditText(TObject *Sender, int ACol,
      int ARow, const AnsiString Value) {
  changed = true;
}
//---------------------------------------------------------------------------
void __fastcall TMDIChild::PageControlChange(TObject *Sender) {     // Reiterauswahl
  if (PageControl->ActivePage == PageDual) {            // Dual
    // Dual berechnen
    if (TransferToLP()) {
      PutError("Parser Fehler, Ausführung gestoppt.");
      PageControl->ActivePage == PagePrimal;
      return;
    }
    LPModell* lptemp = new LPModell(ErrorHandler);
    LPM->clone(lptemp);
   // lptemp->BoundsToRes(); // Grenzen in Restriktionen umformen
    LPDual->clear();
    lptemp->getDual(LPDual);
    LPDual->ResToBounds(); // Restriktionen in Grenzen umformen (wenn moeglich)
    TransferFromLPDual(LPDual);
    LabelGrids();
    delete lptemp;
    LabelSolution->Caption = "Lösung Dual";
    CLP = LPDual;
  }
  if (PageControl->ActivePage == PagePrimal) {        // Primal
    LabelSolution->Caption = "Lösung Primal";
    CLP = LPM;
  }
  if (PageControl->ActivePage == PageLoesung) {      // Lösung
    Button1Click(NULL);
  }
}
//---------------------------------------------------------------------------
void __fastcall TMDIChild::setLP(LPModell* nLP) {
  if (CLP == LPM) CLP = nLP;
  delete LPM;
  LPM = nLP;
  TransferFromLP();
}
//---------------------------------------------------------------------------
void __fastcall TMDIChild::Button4Click(TObject *Sender) {     // neues Primal
  TMDIChild* Child = MainForm->CreateMDIChild("");
  LPModell* nLP = new LPModell(Child->ErrorHandler);
  LPDual->clone(nLP);
  Child->setLP(nLP);
}
//---------------------------------------------------------------------------


