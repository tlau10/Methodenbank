//----------------------------------------------------------------------------
#ifndef ChildWinH
#define ChildWinH
//----------------------------------------------------------------------------
#include <vcl\Controls.hpp>
#include <vcl\Forms.hpp>
#include <vcl\Graphics.hpp>
#include <vcl\Classes.hpp>
#include <vcl\Windows.hpp>
#include <vcl\System.hpp>
#include <StdCtrls.hpp>
#include <ComCtrls.hpp>
#include <Grids.hpp>
#include <ExtCtrls.hpp>
#include "lpmodell.h"
#include "solver.h"
//----------------------------------------------------------------------------
class EHandler;
/**
 *Klasse die ein Unterfenster im Hauptfenster erzeugt.
 */
class TMDIChild : public TForm {
__published:
TPageControl *PageControl;
TTabSheet *PagePrimal;
TTabSheet *PageDual;
TTabSheet *PageLoesung;
TPageControl *PageControl1;
TTabSheet *TabSheetXA;
TTabSheet *TabSheetMOPS;
TMemo *MemoXA;
TPanel *Panel1;
TPanel *Panel2;
TButton *Button1;
TPanel *Panel3;
TStringGrid *RS;
TPanel *Panel4;
TPanel *Panel5;
TGroupBox *GroupBox1;
TCheckBox *CheckBoxXA;
TCheckBox *CheckBoxMOPS;
TCheckBox *CheckBoxLPSolve;
        TCheckBox *CheckBoxILOG;
TCheckBox *CheckBoxWeidenauer;
TGroupBox *GroupBox3;
TRadioButton *RadioMin;
TRadioButton *RadioMax;
TGroupBox *GroupBox2;
TLabel *Label1;
TLabel *Label2;
TEdit *Edit2;
TEdit *Edit1;
TUpDown *UpDownVars;
TUpDown *UpDownRes;
TPanel *Panel6;
TStringGrid *LP;
TPanel *PanelError;
TSplitter *Splitter1;
TPanel *Panel7;
TTabSheet *TabSheetLPSolve;
TTabSheet *TabSheetILOG;
TTabSheet *TabSheetWeid;
TMemo *MemoMOPS;
TMemo *MemoILOG;
TMemo *MemoWeidenauer;
TMemo *MemoLPSolve;
TListBox *ListBoxError;
TPanel *Panel8;
TButton *Button3;
TLabel *LabelSolution;
TPanel *Panel9;
TPanel *Panel10;
TStringGrid *RSD;
TPanel *Panel11;
TStringGrid *LPD;
TPanel *Panel12;
TButton *Button2;
TGroupBox *GroupBox4;
TLabel *LabelDualType;
TButton *Button4;
	void __fastcall FormClose(TObject *Sender, TCloseAction &Action);
void __fastcall Button1Click(TObject *Sender);
void __fastcall UpDownVarsClick(TObject *Sender, TUDBtnType Button);
void __fastcall RSClick(TObject *Sender);
void __fastcall RadioMaxClick(TObject *Sender);
void __fastcall FormCloseQuery(TObject *Sender, bool &CanClose);
void __fastcall LPSetEditText(TObject *Sender, int ACol, int ARow,
          const AnsiString Value);
void __fastcall PageControlChange(TObject *Sender);
void __fastcall Button4Click(TObject *Sender);
private:
  void __fastcall LabelGrids();
  LPModell* LPM;
  LPModell* LPDual;
  LPModell* CLP;
  TSolver* Solver;
  EHandler* ErrorHandler;
  bool __fastcall TransferToLP();
  void __fastcall TransferFromLP();
  void __fastcall TransferFromLPDual(LPModell* LPM);
  void __fastcall ClearError();
  bool changed;
  int fileformat;
  void __fastcall LoadFromFile(TMemo* Memo, char* filename);
public:
  void __fastcall PutError(char* msg);
  void __fastcall setLP(LPModell* nLP);
  void __fastcall LoadFile(char* filename);
  bool __fastcall StoreFile(char* filename, int format);
	virtual __fastcall TMDIChild(TComponent *Owner);
};
//----------------------------------------------------------------------------
/**
 *Klasse fuer den Umgang mit Events
 */
class EHandler: public ThrowErrorMsg {
  TMDIChild* window;
public:  
  EHandler::EHandler(TMDIChild* pmdi) { window = pmdi; };
  virtual void __fastcall PutError(char* msg) { window->PutError(msg); }
};
//----------------------------------------------------------------------------

#endif
