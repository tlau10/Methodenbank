//---------------------------------------------------------------------------

#ifndef optionsH
#define optionsH
//---------------------------------------------------------------------------
#include <Classes.hpp>
#include <Controls.hpp>
#include <StdCtrls.hpp>
#include <Forms.hpp>
#include <Buttons.hpp>
#include <ExtCtrls.hpp>
//---------------------------------------------------------------------------
/**
 *Klasse in der die Pfade zu den verschiedenen Solvern und Arbeitsverzeichnissen eingestellt werden koennen.
 *Die Pfade werden in der Windows Registrierung gespeichert. 
 *
 */
class TOptionsForm : public TForm
{
__published:	// Von der IDE verwaltete Komponenten
TBitBtn *BitBtn1;
TBitBtn *BitBtn2;
TGroupBox *GroupBox1;
TPanel *Panel3;
TEdit *EditLPSolve;
TPanel *Panel5;
TEdit *EditWeid;
TPanel *Panel6;
TEdit *EditTemp;
void __fastcall BitBtn1Click(TObject *Sender);
void __fastcall FormActivate(TObject *Sender);
private:	// Anwender-Deklarationen
public:		// Anwender-Deklarationen
__fastcall TOptionsForm(TComponent* Owner);
};
//---------------------------------------------------------------------------
extern PACKAGE TOptionsForm *OptionsForm;
//---------------------------------------------------------------------------
#endif
