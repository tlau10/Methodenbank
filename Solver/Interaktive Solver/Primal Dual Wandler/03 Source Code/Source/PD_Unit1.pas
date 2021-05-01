unit PD_Unit1;
interface

// comobj hinzugefügt, für OLE Funktionalität

uses
  Windows, Messages, SysUtils, Classes, Graphics, Controls, Forms, Dialogs,
  ComCtrls, StdCtrls, Spin, ExtCtrls, Grids, Menus,
  OleCtrls, OleCtnrs, comobj,ShellApi,
  IniFiles;



type
  TForm1 = class(TForm)
    Ansicht: TPageControl;
    Linearmodell: TTabSheet;
    Dualmodell: TTabSheet;
    OptimierungGroup: TRadioGroup;
    EinstellungBox: TGroupBox;
    AnzVariable: TSpinEdit;
    AnzRestriktionen: TSpinEdit;
    BezAnzVariable: TLabel;
    BezAnzRestriktionen: TLabel;
    UmformButton: TButton;
    MainMenu: TMainMenu;
    Beenden1: TMenuItem;
    Funktionen1: TMenuItem;
    Umformen1: TMenuItem;
    N1: TMenuItem;
    N2: TMenuItem;
    LinearModellBox: TGroupBox;
    LinearGrid: TStringGrid;
    ZFLinearGrid: TStringGrid;
    DualmodellBox: TGroupBox;
    DualGrid: TStringGrid;
    ZFDualGrid: TStringGrid;
    VZBeschraenkungBoxLinear: TGroupBox;
    VorzeichenLinear: TListBox;
    AnzVarHidden: TEdit;
    AnzResHidden: TEdit;
    VZBeschraenkungsBoxDual: TGroupBox;
    VorzeichenDual: TListBox;
    Initialisieren1: TMenuItem;
    N3: TMenuItem;
    ErstelleDokument: TMenuItem;
    Hilfe1: TMenuItem;
    Inhalt1: TMenuItem;
    N4: TMenuItem;
    Ueber1: TMenuItem;
    Button1: TButton;
    OeffnenDatei: TOpenDialog;
    SpeichernDatei: TSaveDialog;
    Datei1: TMenuItem;
    Neu1: TMenuItem;
    Oeffnen1: TMenuItem;
    Speichern1: TMenuItem;
    Speichernunter1: TMenuItem;
    Button2: TButton;

    procedure Oeffnen1Click(Sender: TObject);
    procedure Speichernunter1Click(Sender: TObject);
    procedure Beenden1Click(Sender: TObject);
    procedure FormActivate(Sender: TObject);
    procedure AnzVariableChange(Sender: TObject);
    procedure AnzRestriktionenChange(Sender: TObject);
    procedure ErstelleDokument1Click(Sender: TObject);
    procedure OptimierungGroupClick(Sender: TObject);
    procedure LinearGridTopLeftChanged(Sender: TObject);
    procedure ZFLinearGridTopLeftChanged(Sender: TObject);
    procedure DualGridTopLeftChanged(Sender: TObject);
    procedure ZFDualGridTopLeftChanged(Sender: TObject);
    procedure UmformButtonClick(Sender: TObject);
    procedure InitialisierenClick(Sender: TObject);
    procedure Initialisieren1Click(Sender: TObject);
    procedure Umformen1Click(Sender: TObject);
    procedure Ueber1Click(Sender: TObject);
    procedure Inhalt1Click(Sender: TObject);
    procedure FormCreate(Sender: TObject);
    procedure FormClose(Sender: TObject; var Action: TCloseAction);
    procedure Button1Click(Sender: TObject);
    procedure Button2Click(Sender: TObject);
    procedure Neu1Click(Sender: TObject);
    procedure Speichern1Click(Sender: TObject);
    procedure Button3Click(Sender: TObject);

  private
    { Private-Deklarationen }
    Datenpfad : string ;
    ExecPfad  : string ;
  public
    { Public-Deklarationen }
  end;

const
  cgCaption = 'Primal - Dual - Wandler' ;

var
  Form1: TForm1;
  code, varCount, resCount: integer;

  AnzahlVariableGlobal, AnzahlRestriktionenGlobal: integer;
  AnzahlVariableGlobal_2, AnzahlRestriktionenGlobal_2: integer;
  tmpArray: array[1..40,1..40] of variant;

  VereinfachenBool : Boolean;

  DualErstellt, VereinfachungErledigt_1,
  VereinfachungErledigt_2 : Boolean;



implementation

//uses WordDokument;

{$R *.DFM}
  type myRecord = record
       tmpArray4: array[0..42,0..41] of string[20]  //
       end;

 var f: File of myRecord;
     pfad : string;
     F0    : file;
     tmpRecord: myRecord;

procedure InitialisiereLinearGrid;
var zaehler, index: integer;
begin
     with Form1 do
     begin
          Caption := cgCaption ;
          for zaehler := 0 to AnzRestriktionen.Value do
          begin

               for index := 0 TO AnzVariable.Value + 2 DO
               begin
                   LinearGrid.Cells[index,zaehler] := ' ';

               end;
          end;   

          // Init von ZF Linear Grid
          for zaehler :=0 TO AnzVariable.Value  DO
                   ZFLinearGrid.Cells[zaehler,0] := ' ';

          // Grids nochmal neu darstellen
          LinearGrid.Refresh;
          ZFLinearGrid.Refresh;
          LinearModell.Visible := true;
      end;
      AnzahlVariableGlobal := 0;
      AnzahlRestriktionenGlobal := 0;

      //Umformen1Click.Focus := true;
      //UmformenButtonClick.Focus := true;

end;

procedure InitialisiereDualGrid;
var zaehler, index: integer;
begin
     with Form1 do
     begin
          // Init von Dual Grid
          for zaehler :=0 TO DualGrid.RowCount - 1  DO
          begin
               for index := 0 TO DualGrid.ColCount - 1 DO
               begin
                   DualGrid.Cells[index,zaehler] := ' ';
               end;
          end;

          // Init von ZF Dual Grid
          for zaehler :=0 TO ZFDualGrid.RowCount - 1  DO
                   ZFDualGrid.Cells[zaehler,0] := ' ';

         // Grids nochmal neu darstellen
         DualGrid.Refresh;
         ZFDualGrid.Refresh;
      end;
end;



// Funktion aktualisiert die Listbox
// VorzeichenLinear.
// Funktion kann zum iterativen Verändern
// bei der Variablenanzahl verwendet werden,
// als auch bei der initialisierung der Listbox
//
// Bereits existierende Einträge werden nur gelöscht
// falls sich die Variablenanzahl verringert hat.
// (Löschen erwünscht)
//
// Funktion muss immer vor dem Aktualisieren des
// Lineargrids aufgerufen werden! (Ausnahme:
// beim erstmaligen Aufruf zum initialisieren
procedure AktualisiereVorzeichenLinear;
var
    variablenNeu, variablenAlt, index: integer;
    bezeichnung: string;

begin
     with Form1 do
     begin
         // Anzahl Variablen umladen
         Val(AnzVariable.text, variablenNeu, code);
         Val(AnzVarHidden.text, variablenAlt, code);


         if VorzeichenLinear.Items.Text = ''
         then
             begin
             // Funktion wird zum initialieren aufgerufen
             // -> in der Listbox steht noch nichts drin

                for index :=0 TO variablenNeu - 1 DO
                    begin
                         Str(index + 1, bezeichnung);
                         bezeichnung := 'x' + bezeichnung;
                         VorzeichenLinear.Items.Add(bezeichnung);
                    end;
             end
         else
             begin
             // Funktion wird nur zum erweitern bzw. verringern
             // aufgerufen
                  if variablenNeu > variablenAlt
                  then
                  // Eintrag hinzufügen
                      begin
                         Str(variablenNeu, bezeichnung);
                         bezeichnung := 'x' + bezeichnung;
                         VorzeichenLinear.Items.Add(bezeichnung);
                      end
                  else
                  // Eintrag löschen
                      begin
                         index := variablenNeu;
                         VorzeichenLinear.Items.Delete(index);
                      end;
             end;
     end;
end;


// Funktion füllt in die Listbox VorzeichenDual
// anVar - viele u Variablen
// Bereits existierende Einträge gelöscht!
procedure AktualisiereVorzeichenDual(anzVar:integer);
var index:integer;
    bezeichnung: string;
    tmpBool : Boolean;
begin
     with Form1 do
     begin
         tmpBool := false;
         VorzeichenDual.Clear();
         for index :=1 TO anzVar DO
         begin
               tmpBool := VorzeichenLinear.Selected[index - 1];
               //if tmpBool = true then
               //begin
                Str(index , bezeichnung);
                bezeichnung := 'u' + bezeichnung;
                VorzeichenDual.Items.Add(bezeichnung);
               //end;
          end;
     end
end;



procedure SetzeMaxMin;
var
    variablen: integer;

begin
     with Form1 do
     begin
         // Anzahl Variablen umladen
         Val(AnzVariable.text, variablen, code);

         // ZF je nach gewählter Optimierungsstrategie beschriften
         if OptimierungGroup.ItemIndex = 0
            then ZFLinearGrid.Cells[variablen + 2, 0] := 'MAX'
            else ZFLinearGrid.Cells[variablen+ 2, 0] := 'MIN';

         ZFLinearGrid.Cells[variablen + 1, 0] := '-->';

     end;
end;

procedure AktualisiereLinearGrid;
var
    zaehlerStr, s: string;
    zaehler, varNeu, varAlt, resNeu, resAlt: integer;

begin
     with Form1 do
     begin
           // Caption := 'Umformung Primal -> Dual';
           Caption := cgCaption + '  [' +  pfad  + ']'; 
   // BESCHRIFTUNG
           // Titel Grid
           LinearGrid.Cells[0,0] := 'Daten';

           // Beschriften der linken Spalte
           for zaehler :=1 TO AnzRestriktionen.Value DO
           begin
                Str(zaehler, zaehlerStr);
                LinearGrid.Cells[0,zaehler] := 'R' + zaehlerStr
           end;

           // Beschriften der obersten Zeile
           for zaehler :=1 TO AnzVariable.Value DO
           begin
                Str(zaehler, zaehlerStr);
                LinearGrid.Cells[zaehler, 0] := 'x' + zaehlerStr
           end;

           LinearGrid.Cells[AnzVariable.Value + 1, 0] := 'Operator';
           LinearGrid.Cells[AnzVariable.Value + 2 , 0] := 'b';

           // Grid von Zielfunktion
           ZFLinearGrid.Cells[0,0] := 'ZF';

      // VERAENDERN DER GROESSE

           LinearGrid.ColCount   := AnzVariable.Value + 3;
           LinearGrid.RowCount   := AnzRestriktionen.Value + 1;
           ZFLinearGrid.ColCount := AnzVariable.Value + 3;

      // LOESCHEN / HINZUFÜGEN EINZELNEN EINTRÄGE

        // Umladen der einzelnen Anzahlen
         Val(AnzVariable.text, varNeu, code);
         Val(AnzVarHidden.text, varAlt, code);
         Val(AnzRestriktionen.text, resNeu, code);
         Val(AnzResHidden.text, resAlt, code);

         if resNeu < resAlt
         then
             // Zeile initialisieren
             begin
                  for zaehler := 1 TO AnzVariable.Value + 2 DO
                      begin
                      LinearGrid.Cells[zaehler, resAlt] := ''
                  end;
            end;

         if  varNeu < varAlt
         then
             // Werte des Operators und b-Vektors umkopieren
             // alte b-Vektor Spalte initialisieren
             begin

                 // Werte umkopieren, die Werte der groessten
                 // Variable verschwinden (x1,x2,x3 -> Werte von
                 // x3 werden gelöscht.
                 // Initialisieren der Spalte die verschwindet
                 for zaehler := 1 TO AnzRestriktionen.Value DO
                 begin
                      LinearGrid.Cells[varNeu + 1 , zaehler] :=
                           LinearGrid.Cells[varNeu + 2, zaehler];
                      LinearGrid.Cells[varNeu + 2 , zaehler] :=
                           LinearGrid.Cells[varNeu + 3, zaehler];
                      LinearGrid.Cells[varNeu + 3, zaehler] := '';
                  end;

                // Bei ZF GRID analog verfahren:

                ZFLinearGrid.Cells[varNeu + 1 , 0] :=
                  ZFLinearGrid.Cells[varNeu + 2, 0];
                ZFLinearGrid.Cells[varNeu + 2 , 0] :=
                  ZFLinearGrid.Cells[varNeu + 3, 0];
                ZFLinearGrid.Cells[varNeu + 3, 0] := '';

             end;
             
         if varNeu > varAlt
         then
             // Operator- und b-Vektor Werte umkopieren
             // groesste VariablenSpalte initialisieren
             begin
                  for zaehler := 1 TO AnzRestriktionen.Value DO
                  begin
                      LinearGrid.Cells[varNeu + 2, zaehler] :=
                           LinearGrid.Cells[varNeu + 1, zaehler];
                      LinearGrid.Cells[varNeu + 1 , zaehler] :=
                           LinearGrid.Cells[varNeu, zaehler];
                      LinearGrid.Cells[varNeu , zaehler] := ''
                  end;

                  // Bei ZF Grid analog verfahren
                  ZFLinearGrid.Cells[varNeu + 2, 0] :=
                    ZFLinearGrid.Cells[varNeu + 1, 0];
                  ZFLinearGrid.Cells[varNeu + 1 , 0] :=
                    ZFLinearGrid.Cells[varNeu, 0];
                  ZFLinearGrid.Cells[varNeu , 0] := ''
            end;

         SetzeMaxMin;            

         // FUELLEN DER HIDDEN VARIABLEN
         Str(AnzVariable.Value, s);
         AnzVarHidden.Text := s;
         Str(AnzRestriktionen.Value, s);
         AnzResHidden.Text := s;

         // Grids nochmal neu darstellen
         LinearGrid.Refresh;
         ZFLinearGrid.Refresh;

      end;
end;

procedure AktualisiereDualGrid(anzVar,anzRes: integer; optimierung:string);
var
    zaehlerStr: string;
    zaehler: integer;

begin
     with Form1 do
     begin

   // BESCHRIFTUNG
           // Titel Grid
           DualGrid.Cells[0,0] := 'Daten';
           Caption := cgCaption + '  [' + pfad + ']' ;
           // Beschriften der linken Spalte
           for zaehler :=1 TO anzRes DO
           begin
                Str(zaehler, zaehlerStr);
                DualGrid.Cells[0,zaehler] := 'R' + zaehlerStr
           end;

           // Beschriften der obersten Zeile
           for zaehler :=1 TO anzVar DO
           begin
                Str(zaehler, zaehlerStr);
                DualGrid.Cells[zaehler, 0] := 'u' + zaehlerStr
           end;

           DualGrid.Cells[anzVar + 1, 0] := 'Operator';
           DualGrid.Cells[anzVar + 2 , 0] := 'c';

           // Grid von Zielfunktion
           ZFDualGrid.Cells[0,0] := 'ZF';
           ZFDualGrid.Cells[anzVar + 1, 0] := '-->';
           ZFDualGrid.Cells[anzVar + 2, 0] := optimierung;

      // VERAENDERN DER GROESSE

           DualGrid.ColCount   := anzVar + 3;
           DualGrid.RowCount   := anzRes + 1;
           ZFDualGrid.ColCount := anzVar + 3;

      // Grids nochmal neu darstellen
           DualGrid.Refresh;
           ZFDualGrid.Refresh;

      end;
end;



procedure TForm1.FormActivate(Sender: TObject);
begin
     // Initialisierung
     Speichern1.Enabled := false;
     AktualisiereVorzeichenLinear;
     AktualisiereLinearGrid;

     // Tabkarte fuers Dualmodell verstecken
     Dualmodell.TabVisible := false;
     Dualmodell.Visible := false;
     Ansicht.ActivePage := Linearmodell;

     // Letzten Eintrag im Funktionenmenü
     // Worddokument erstellen auf inaktiv setzen
     ErstelleDokument.Enabled := false;
     Caption := cgCaption ;
end;

procedure TForm1.AnzVariableChange(Sender: TObject);

begin
  if AnzVariable.Value > 40 then
  begin
       MessageDlg('Die Anzahl der Variablen muß <= 40 sein.', mtWarning, [mbOk], 0);
       AnzVariable.Value := 40
  end;
     // Grid anpassen  Achtung: AktualisiereVorzeichenLinear
     // muss vor AktualisiereLinearGrid aufgerufen werden!
     AktualisiereVorzeichenLinear;
     AktualisiereLinearGrid
end;


procedure TForm1.AnzRestriktionenChange(Sender: TObject);
begin
     // Grid anpassen
     AktualisiereLinearGrid
end;

procedure TForm1.ErstelleDokument1Click(Sender: TObject);
var word : variant;
    wert : Double;
    zaehlerReihe, zaehlerSpalte, index, code: integer;
    zeile, sZaehlerReihe, sZaehlerSpalte, minusZeichen,
    plusZeichen, vorZeichen, sWert, operator, vWert,
    komma, optimierung : string;
    tmpDouble : double;
begin
     try word:= CreateOleObject('Word.Application');
     except
           ShowMessage('WORD konnte nicht gestartet werden !');
           Exit;
     end;
     word.Visible := TRUE;

     // neues Dokument erstellen
     word.Documents.Add;
     if word.ActiveWindow.View.SplitSpecial <> 0
     then word.ActiveWindow.Panes[2].Close;
     if (word.ActiveWindow.ActivePane.View.Type = 1)
     or (word.ActiveWindow.ActivePane.View.Type = 2)
     or (word.ActiveWindow.ActivePane.View.Type = 5)
     then word.ActiveWindow.ActivePane.View.Type := 3;

     // In den Textteil wechseln
     word.ActiveWindow.ActivePane.View.SeekView := 0;

     // Überschrift hinschreiben, Schriftart festlegen
     word.Selection.Font.Name := 'Arial';
     word.Selection.Font.Size := '14';
     word.Selection.Font.Underline := True;

     word.Selection.TypeText(Text := 'Umformung Primalmodell <-> Dualmodell');
     word.Selection.TypeParagraph;
     word.Selection.TypeParagraph;

     word.Selection.Font.Size := '12';
     word.Selection.Font.Underline := False;
     word.Selection.Font.Bold := True;

     minusZeichen := '-';
     plusZeichen  := '+';
     wert := 0;

// LINEARMODELL zusammenstellen und hinschreiben

     word.Selection.TypeText(Text := 'Primalmodell: ');
     word.Selection.Font.Bold := False;
     word.Selection.TypeParagraph;

     //  Restriktionen zusammenstellen und hinschreiben

     for zaehlerReihe :=1 TO LinearGrid.RowCount - 1  DO
     begin
           Str(zaehlerReihe, sZaehlerReihe);
           zeile := 'R' + sZaehlerReihe + ' :  ';

           // Innerhalb einer Zeile jedes Spalte auslesen
           // und als ein String zusammenfassen
           for zaehlerSpalte := 1 TO LinearGrid.ColCount - 3 DO
           begin
               Str(zaehlerSpalte, sZaehlerSpalte);

               if zaehlerSpalte = 1
               then vorZeichen := ' '
               else vorZeichen := plusZeichen;

               //Val(LinearGrid.Cells[zaehlerSpalte,zaehlerReihe], wert, code);
               wert:=StrToFloat(LinearGrid.Cells[zaehlerSpalte,zaehlerReihe]);
               if wert < 0
               then
               begin
                    wert := wert * (-1);
                    vorZeichen := minusZeichen;
               end;

               //Str(wert:8:3, sWert);
               sWert := FloatToStr(wert);
               zeile := zeile + vorZeichen + sWert + ' x' + sZaehlerSpalte + '  ';

           end;

           // Variablen stehen jetzt alle im string Zeile, jetzt
           // noch den Operator und den Wert des b-Vektors anhängen

           operator := LinearGrid.Cells[zaehlerSpalte ,zaehlerReihe];
           //Val(LinearGrid.Cells[zaehlerSpalte + 1,zaehlerReihe], wert, code);
           wert:= StrToFloat(LinearGrid.Cells[zaehlerSpalte + 1,zaehlerReihe]);
           if wert < 0
           then
           begin
                wert := wert * (-1);
                vorZeichen := minusZeichen;
           end
           else
               vorZeichen := ' ';

           //Str(wert:8:3, vWert);
            vWert:=FloatToStr(wert);

           zeile := zeile + '  ' + operator + '  ' + vorZeichen + vWert;

           // Erstellte Zeile in Worddokument schreiben
           word.Selection.TypeText(Text := zeile);
           word.Selection.TypeParagraph;
     end;

     // Zielfunktionszeile zusammenstellen und hinschreiben
     zeile := 'ZF :  ';

     for zaehlerSpalte := 1 TO ZFLinearGrid.ColCount - 3 DO
     begin
         Str(zaehlerSpalte, sZaehlerSpalte);

         if zaehlerSpalte = 1
         then vorZeichen := ' '
         else vorZeichen := plusZeichen;

         //Val(ZFLinearGrid.Cells[zaehlerSpalte,0], wert, code);
         wert:=StrToFloat(ZFLinearGrid.Cells[zaehlerSpalte,0]);
         if wert < 0
         then
            begin
                wert := wert * (-1);
                vorZeichen := minusZeichen;
            end;

          //Str(wert:8:3, sWert);
          sWert:=FloatToStr(wert);
          zeile := zeile + vorZeichen + sWert + ' x' + sZaehlerSpalte + '  ';

     end;

     // Variablen stehen jetzt alle im string Zeile, jetzt
     // noch den Pfeil und MAX bzw. MIN anhängen

     optimierung := ZFLinearGrid.Cells[zaehlerSpalte +1 ,0];
     Str(wert:8:3, vWert);
     zeile := zeile + '  ' + '-->' + '  ' + optimierung;

     // ZF in Word hinschreiben
     word.Selection.TypeParagraph;
     word.Selection.TypeText(Text := zeile);
     word.Selection.TypeParagraph;




     // Im Vorzeichen unbeschränkte Variablen ermitteln und falls vorhanden
     // ebenfalls hinschreiben

     // Sind markierte Einträge in der Listbox VorzeichenLinear vorhanden?
     if VorzeichenLinear.SelCount > 0
     then
     begin
          word.Selection.TypeParagraph;
          word.Selection.TypeText(Text := 'Vorzeichenunabhängige Variablen: ');
          word.Selection.TypeParagraph;

          zeile := '';

          for index := 0 TO VorzeichenLinear.Items.Count - 1 DO
          begin
               // Eintrag markiert?
               if VorzeichenLinear.selected[index] = true
               then
               begin
                   if zeile = ''
                   then komma := ''
                   else komma := '  ,  ';

                   zeile := zeile + komma + VorzeichenLinear.Items[index];
               end;
          end;

          word.Selection.TypeText(Text := zeile);
          word.Selection.TypeParagraph;
     end;

// DUALMODELL zusammenstellen und hinschreiben (genau analog LINEARMODELL)
   if VereinfachenBool = false then
      begin
          UmformButtonClick(Self);
      end;
     word.Selection.TypeParagraph;
     word.Selection.Font.Bold := True;
     word.Selection.TypeText(Text := 'Entsprechendes Dualmodell: ');
     word.Selection.Font.Bold := False;
     word.Selection.TypeParagraph;

     //  Restriktionen zusammenstellen und hinschreiben

     for zaehlerReihe :=1 TO DualGrid.RowCount - 1  DO
     begin
           Str(zaehlerReihe, sZaehlerReihe);
           zeile := 'R' + sZaehlerReihe + ' :  ';

           // Innerhalb einer Zeile jedes Spalte auslesen
           // und als ein String zusammenfassen
           for zaehlerSpalte := 1 TO DualGrid.ColCount - 3 DO
           begin
               Str(zaehlerSpalte, sZaehlerSpalte);

               if zaehlerSpalte = 1
               then vorZeichen := ' '
               else vorZeichen := plusZeichen;

               //Val(DualGrid.Cells[zaehlerSpalte,zaehlerReihe], wert, code);
               wert:=StrToFloat(DualGrid.Cells[zaehlerSpalte,zaehlerReihe]);
               if wert < 0
               then
               begin
                    wert := wert * (-1);
                    vorZeichen := minusZeichen;
               end;

               //Str(wert:8:3, sWert);
               sWert:=FloatToStr(wert);
               zeile := zeile + vorZeichen + sWert + ' u' + sZaehlerSpalte + '  ';

           end;

           // Variablen stehen jetzt alle im string Zeile, jetzt
           // noch den Operator und den Wert des c-Vektors anhängen

           operator := DualGrid.Cells[zaehlerSpalte ,zaehlerReihe];
           //Val(DualGrid.Cells[zaehlerSpalte + 1,zaehlerReihe], wert, code);
           wert:=StrToFloat(DualGrid.Cells[zaehlerSpalte + 1,zaehlerReihe]);
           if wert < 0
           then
           begin
                wert := wert * (-1);
                vorZeichen := minusZeichen;
           end
           else
               vorZeichen := ' ';

           //Str(wert:8:3, vWert);
           vWert:=FloatToStr(wert);
           zeile := zeile + '  ' + operator + '  ' + vorZeichen + vWert;

           // Erstellte Zeile in Worddokument schreiben
           word.Selection.TypeText(Text := zeile);
           word.Selection.TypeParagraph;
     end;

     // Zielfunktionszeile zusammenstellen und hinschreiben

     zeile := 'ZF :  ';

     for zaehlerSpalte := 1 TO ZFDualGrid.ColCount - 3 DO
     begin
         Str(zaehlerSpalte, sZaehlerSpalte);

         if zaehlerSpalte = 1
         then vorZeichen := ' '
         else vorZeichen := plusZeichen;

         //Val(ZFDualGrid.Cells[zaehlerSpalte,0], wert, code);
         wert:=StrToFloat(ZFDualGrid.Cells[zaehlerSpalte,0]);
         if wert < 0
         then
            begin
                wert := wert * (-1);
                vorZeichen := minusZeichen;
            end;

          //Str(wert:8:3, sWert);
          sWert:=FloatToStr(wert);
          zeile := zeile + vorZeichen + sWert + ' u' + sZaehlerSpalte + '  ';

     end;

     // Variablen stehen jetzt alle im string Zeile, jetzt
     // noch den Pfeil und MAX bzw. MIN anhängen

     optimierung := ZFDualGrid.Cells[zaehlerSpalte +1 ,0];
     Str(wert:8:3, vWert);
     zeile := zeile + '  ' + '-->' + '  ' + optimierung;

     // ZF in Word hinschreiben
     word.Selection.TypeParagraph;
     word.Selection.TypeText(Text := zeile);
     word.Selection.TypeParagraph;

     // Im Vorzeichen unbeschränkte Variablen ermitteln und falls vorhanden
     // ebenfalls hinschreiben

     // Sind markierte Einträge in der Listbox VorzeichenLinear vorhanden?
     if VorzeichenDual.SelCount > 0
     then
     begin
          word.Selection.TypeParagraph;
          word.Selection.TypeText(Text := 'Vorzeichenunabhängige Variablen: ');
          word.Selection.TypeParagraph;

          zeile := '';

          for index := 0 TO VorzeichenDual.Items.Count - 1 DO
          begin
               // Eintrag markiert?
               if VorzeichenDual.selected[index] = true
               then
               begin
                   if zeile = ''
                   then komma := ''
                   else komma := '  ,  ';

                   zeile := zeile + komma + VorzeichenDual.Items[index];
               end;
          end;

          word.Selection.TypeText(Text := zeile);
          word.Selection.TypeParagraph;
     end;

//// testAusgabe Anfang /////////////////////////
VereinfachungErledigt_1 := false;
VereinfachungErledigt_2 := false;
Button2Click(Self);

// VereinfachungErledigt_1 = true;
// VereinfachungErledigt_2 = false;
Button2Click(Self);


// DUALMODELL zusammenstellen und hinschreiben (genau analog LINEARMODELL)

     word.Selection.TypeParagraph;
     word.Selection.Font.Bold := True;
     word.Selection.TypeText(Text := 'Dualmodell (vereinfacht): ');
     word.Selection.Font.Bold := False;
     word.Selection.TypeParagraph;

     //  Restriktionen zusammenstellen und hinschreiben

     for zaehlerReihe :=1 TO DualGrid.RowCount - 1  DO
     begin
           Str(zaehlerReihe, sZaehlerReihe);
           zeile := 'R' + sZaehlerReihe + ' :  ';

           // Innerhalb einer Zeile jedes Spalte auslesen
           // und als ein String zusammenfassen
           for zaehlerSpalte := 1 TO DualGrid.ColCount - 3 DO
           begin
               Str(zaehlerSpalte, sZaehlerSpalte);

               if zaehlerSpalte = 1
               then vorZeichen := ' '
               else vorZeichen := plusZeichen;

               //Val(DualGrid.Cells[zaehlerSpalte,zaehlerReihe], wert, code);
               wert:=StrToFloat(DualGrid.Cells[zaehlerSpalte,zaehlerReihe]);
               if wert < 0
               then
               begin
                    wert := wert * (-1);
                    vorZeichen := minusZeichen;
               end;

               //Str(wert:8:3, sWert);
               sWErt:=FloatToStr(wert);
               zeile := zeile + vorZeichen + sWert + ' u' + sZaehlerSpalte + '  ';

           end;

           // Variablen stehen jetzt alle im string Zeile, jetzt
           // noch den Operator und den Wert des c-Vektors anhängen

           operator := DualGrid.Cells[zaehlerSpalte ,zaehlerReihe];
           //Val(DualGrid.Cells[zaehlerSpalte + 1,zaehlerReihe], wert, code);
           wert:=StrToFloat(DualGrid.Cells[zaehlerSpalte + 1,zaehlerReihe]);
           if wert < 0
           then
           begin
                wert := wert * (-1);
                vorZeichen := minusZeichen;
           end
           else
               vorZeichen := ' ';

           //Str(wert:8:3, vWert);
           vWert:=FloatToStr(wert);
           zeile := zeile + '  ' + operator + '  ' + vorZeichen + vWert;

           // Erstellte Zeile in Worddokument schreiben
           word.Selection.TypeText(Text := zeile);
           word.Selection.TypeParagraph;
     end;

     // Zielfunktionszeile zusammenstellen und hinschreiben

     zeile := 'ZF :  ';

     for zaehlerSpalte := 1 TO ZFDualGrid.ColCount - 3 DO
     begin
         Str(zaehlerSpalte, sZaehlerSpalte);

         if zaehlerSpalte = 1
         then vorZeichen := ' '
         else vorZeichen := plusZeichen;

         //Val(ZFDualGrid.Cells[zaehlerSpalte,0], wert, code);
         wert:=StrToFloat(ZFDualGrid.Cells[zaehlerSpalte,0]);
         if wert < 0
         then
            begin
                wert := wert * (-1);
                vorZeichen := minusZeichen;
            end;

          //Str(wert:8:3, sWert);
          sWert:=FloatToStr(wert);
          zeile := zeile + vorZeichen + sWert + ' u' + sZaehlerSpalte + '  ';

     end;

     // Variablen stehen jetzt alle im string Zeile, jetzt
     // noch den Pfeil und MAX bzw. MIN anhängen

     optimierung := ZFDualGrid.Cells[zaehlerSpalte +1 ,0];
     Str(wert:8:3, vWert);
     zeile := zeile + '  ' + '-->' + '  ' + optimierung;

     // ZF in Word hinschreiben
     word.Selection.TypeParagraph;
     word.Selection.TypeText(Text := zeile);
     word.Selection.TypeParagraph;

     // Im Vorzeichen unbeschränkte Variablen ermitteln und falls vorhanden
     // ebenfalls hinschreiben

     // Sind markierte Einträge in der Listbox VorzeichenLinear vorhanden?
     if VorzeichenDual.SelCount > 0
     then
     begin
          word.Selection.TypeParagraph;
          word.Selection.TypeText(Text := 'Vorzeichenunabhängige Variablen: ');
          word.Selection.TypeParagraph;

          zeile := '';

          for index := 0 TO VorzeichenDual.Items.Count - 1 DO
          begin
               // Eintrag markiert?
               if VorzeichenDual.selected[index] = true
               then
               begin
                   if zeile = ''
                   then komma := ''
                   else komma := '  ,  ';

                   zeile := zeile + komma + VorzeichenDual.Items[index];
               end;
          end;

          word.Selection.TypeText(Text := zeile);
          word.Selection.TypeParagraph;
     end;

//// testAusgabe Ende /////////////////////////
end;

procedure TForm1.OptimierungGroupClick(Sender: TObject);
begin
     SetzeMaxMin
end;

procedure TForm1.LinearGridTopLeftChanged(Sender: TObject);
begin
     // Synchronisierung der beiden Linear Grids
     ZFLinearGrid.LeftCol := LinearGrid.LeftCol
end;

procedure TForm1.ZFLinearGridTopLeftChanged(Sender: TObject);
begin
     // Synchronisierung der beiden Linear Grids
     LinearGrid.LeftCol := ZFLinearGrid.LeftCol
end;

procedure TForm1.DualGridTopLeftChanged(Sender: TObject);
begin
     // Synchronisierung der beiden Dual Grids
     ZFDualGrid.LeftCol := DualGrid.LeftCol
end;

procedure TForm1.ZFDualGridTopLeftChanged(Sender: TObject);
begin
     // Synchronisierung der beiden Dual Grids
     DualGrid.LeftCol := ZFDualGrid.LeftCol
end;

//*****************************************************************************************************************
//******************************************************************************************************************
procedure UmformModell;
var anzVar, anzRes, zaehler, zaehler2,  tmpInt, tmpArray2Zaehler: integer;
    optimierungDual, tmpString : string;
    // kleinerGleichOperator, groesserGleichOperator , gleicherOperator: Boolean;   // gilt nur für einheitliche Operatoren!
    tmpVariant : Variant;
    //tmpArray,
    tmpArray2, tmpArray3: array[1..40,1..40] of variant;
    NNB , Fehler, Fehler2: Boolean; // true: nicht negativ ! false: neue Variable einführen! ... gilt nur für einheitliche Operatoren!
    tmpBool, nurZifferBool : Boolean;
    tmpDouble : Double;
begin
     with Form1 do
     begin

     // test
   {    tmpVariant := '1,23';
       tmpDouble := StrToFloat(tmpVariant);
       tmpDouble := tmpDouble * (-1);
       tmpVariant := FloatToStr(tmpDouble);
       ShowMessage(tmpVariant);



    }



      // test ende
          DualErstellt := true;
          VereinfachungErledigt_1 := false;
          VereinfachungErledigt_2 := false;

          Button2.Enabled := true;
          Button2.Caption := 'Vereinfachen';
          Fehler := false;
          varCount := AnzVariable.Value;
          resCount := AnzRestriktionen.Value;

          //anzVar := AnzVariable.Value;
          //anzRes := AnzRestriktionen.Value;

          // DualGrid initialisieren    *********************************
          for zaehler := 1 to DualGrid.ColCount do
          begin
              for zaehler2 := 1 to DualGrid.RowCount do
              begin
                    DualGrid.Cells[zaehler, zaehler2] := '';
              end;
          end;
          // ZFDualGrid
          for zaehler := 1 to ZFDualGrid.ColCount do
          ZFDualGrid.Cells[zaehler,0] := ' ';

          // Test, ob Ziffern übergeben werden
          for zaehler := 1 to resCount do
              for zaehler2 := 1 to varCount do
              begin
                   if LinearGrid.Cells[zaehler2, zaehler] <> '' then
                   begin
                        try
                           tmpDouble := StrToFloat(LinearGrid.Cells[zaehler2, zaehler]);
                        except
                              Fehler := true;
                              LinearGrid.Cells[zaehler2, zaehler] := 'XXX';
                          end // try
                   end // if
              end; // for

              // ZF testen
              //if Fehler = false then
              for zaehler:=1 to varCount do
              begin
              if ZFLinearGrid.Cells[zaehler, 0] <> '' then
              begin
                   try
                      tmpDouble := StrToFloat(ZFLinearGrid.Cells[zaehler, 0]);
                   except
                      Fehler := true;
                      ZFLinearGrid.Cells[zaehler, 0] := 'XXX';
                     end // try
              end;
              end;

              // rechte Seite der Gleichungen testen
              // if Fehler = false then
              for zaehler := 1 to resCount do
              begin
                   if LinearGrid.Cells[AnzVariable.Value + 2, zaehler] <> '' then
                   begin
                        try
                           tmpDouble := StrToFloat(LinearGrid.Cells[AnzVariable.Value + 2,zaehler]);
                        except
                              Fehler := true;
                              LinearGrid.Cells[AnzVariable.Value + 2,zaehler] := 'XXX';

                        end // try
                   end;
              end;


              // Operatoren testen
              Fehler2 := false;
              for zaehler := 1 to resCount do
              begin
                   tmpVariant := LinearGrid.Cells[AnzVariable.Value + 1, zaehler];
                   if tmpVariant = '<=' then zaehler2 := 1;
                   if tmpVariant = '>=' then zaehler2 := 2;
                   if tmpVariant = '=' then zaehler2 := 3;
                  // if tmpVariant = '' then zaehler2 := 4;
                   case zaehler2 of
                   1:;
                   2:;
                   3:;
                   4:;
                   else
                       Fehler2 := true;
                       LinearGrid.Cells[AnzVariable.Value + 1, zaehler] := 'OP';
                   end;
              end;
          if Fehler = true then
             begin
             ShowMessage('Falsche Eingabe! Bitte nur Zahlen eingeben');
             exit;
             end
          else if Fehler2 = true then
               begin
               ShowMessage('Falscher Operator!');
               exit;
               end;
          // ZF beschriften
          if OptimierungGroup.ItemIndex = 0
          then optimierungDual := 'MIN'        // vom DUAL !!!!!!
          else optimierungDual := 'MAX';


  if Fehler = false then
  if Fehler2 = false then
  begin
          // LinearGrid in tmpArray übernehmen
          for zaehler := 1 to AnzRestriktionen.Value do
              for zaehler2 := 1 to AnzVariable.Value + 2 do
                  tmpArray[zaehler2, zaehler] := LinearGrid.Cells[zaehler2, zaehler];

     resCount := 0;
     // Zeilenweise übernehmen und anpassen
     for zaehler := 1 to AnzRestriktionen.Value do
     begin
          resCount := resCount + 1;
          if resCount = 40 then break;

          // 1. Gleichung in zwei äquivalente Gleichungen umformen
          if tmpArray[AnzVariable.Value + 1, zaehler] = '=' then
          begin
               for zaehler2 := 1 to AnzVariable.Value + 2 do
                   tmpArray2[zaehler2, resCount] := tmpArray[zaehler2, zaehler];

               tmpArray2[AnzVariable.Value + 1, resCount] := '<=';

               resCount := resCount + 1;

               for zaehler2 := 1 to AnzVariable.Value do
                   begin
                   tmpDouble := StrToFloat(tmpArray[zaehler2, zaehler]);
                   tmpDouble := tmpDouble * (-1);
                   tmpArray2[zaehler2, resCount] :=  FloatToStr(tmpDouble) ;

                    end;
               tmpArray2[AnzVariable.Value + 1, resCount] := '<=';
               tmpDouble := StrToFloat(tmpArray[AnzVariable.Value + 2, zaehler]);
               tmpDouble := tmpDouble * (-1);
               tmpArray2[AnzVariable.Value + 2, resCount] := FloatToStr(tmpDouble);
         end           // 1.

         // 2. jede >= - Bedingung wird mit (-1) multipliziert und ergibt eine <= - Bedingung
         else if tmpArray[AnzVariable.Value + 1, zaehler] = '>=' then
         begin
              for zaehler2 := 1 to AnzVariable.Value do  // mit -1 multiplizieren
                  begin
                  tmpDouble := StrToFloat(tmpArray[zaehler2, zaehler]);
                  tmpDouble := tmpDouble * (-1);
                  tmpArray2[zaehler2,resCount] := FloatToStr(tmpDouble);

                  end;
              tmpArray2[AnzVariable.Value + 1, resCount] := '<=';
              tmpDouble := StrToFloat(tmpArray[AnzVariable.Value + 2, zaehler]);
              tmpDouble := tmpDouble * (-1);
              tmpArray2[AnzVariable.Value + 2, resCount] := FloatToStr(tmpDouble);
         end// 2

         // <= kopieren
         else if tmpArray[AnzVariable.Value + 1, zaehler] = '<=' then
              for zaehler2 := 1 to AnzVariable.Value + 2 do
                  tmpArray2[zaehler2, resCount] := tmpArray[zaehler2, zaehler]
         else
         begin
              tmpVariant := zaehler;
              tmpString := tmpVariant;

              if LinearGrid.Cells[zaehler2, zaehler] <> '' then
              begin
                   ShowMessage('Falsche Eingabe im Operatoren-Feld ' + tmpString);
                   Fehler := true;
                   exit;
                   //break;
              end
              else
              begin
                   ShowMessage('Bitte Werte und Operatoren eingeben');
                   Fehler := true;
                   exit;
                   break;
              end;
         end;

     end;

     // tmpArray2 in tmpArray übertragen
     if Fehler <> true then
     begin
     for zaehler := 1 to resCount do
         for zaehler2 := 1 to varCount + 2 do
             tmpArray[zaehler2, zaehler] := tmpArray2[zaehler2, zaehler];


     // MIN / MAX anpassen
     // wenn MIN, dann tmpArray umdrehen
     // wenn MAX, dann nichts tun

    if optimierungDual = 'MAX' then     // == MIN in LinearGrid
    begin
      for zaehler := 1 to resCount do
      begin
            for zaehler2 := 1 to varCount do
                begin
                tmpDouble := StrToFloat(tmpArray[zaehler2, zaehler]);
                tmpDouble := tmpDouble * (-1);
                tmpArray[zaehler2, zaehler] := FloatToStr(tmpDouble);
                end;
            tmpDouble := StrToFloat(tmpArray[varCount + 2, zaehler]);
            tmpDouble := tmpDouble * (-1);
            tmpArray[varCount + 2, zaehler] := FloatToStr(tmpDouble);
      end;
    end;


    // Spaltenweise übernehmen und anpassen  (Vorzeichenunbeschränktheit)
     tmpArray2Zaehler := 0;
     for zaehler := 1 to AnzVariable.Value do
     begin
          tmpArray2Zaehler := tmpArray2Zaehler + 1;
          for zaehler2 := 1 to resCount do
               tmpArray2[tmpArray2Zaehler, zaehler2] := tmpArray[zaehler, zaehler2];

          tmpArray2[tmpArray2Zaehler, resCount + 1] := ZFLinearGrid.Cells[zaehler, 0];
          tmpBool := VorzeichenLinear.Selected[zaehler - 1];
          if tmpBool = true then
               begin
               varCount := varCount + 1;
               tmpArray2Zaehler := tmpArray2Zaehler + 1;
               for zaehler2 := 1 to resCount do
                   begin
                   tmpDouble := StrToFloat(tmpArray[zaehler, zaehler2]);
                   tmpDouble := tmpDouble * (-1);
                   tmpArray2[tmpArray2Zaehler, zaehler2] :=  FloatToStr(tmpDouble);
                   end;
               tmpArray2[tmpArray2Zaehler, resCount + 1] := ZFLinearGrid.Cells[zaehler, 0];
               tmpDouble := StrToFloat(tmpArray2[tmpArray2Zaehler, resCount + 1]);
               tmpDouble := tmpDouble * (-1);
               tmpArray2[tmpArray2Zaehler, resCount + 1] := FloatToStr(tmpDouble);
               end;
          end;
     end;

     // letzte zwei Spalten übertragen
     for zaehler := 1 to resCount do
     begin
          tmpArray2[varCount + 1, zaehler] := tmpArray[AnzVariable.Value + 1, zaehler];
          tmpArray2[varCount + 2, zaehler] := tmpArray[AnzVariable.Value + 2, zaehler];
     end;

         // tmpArray2 in DualGrid übertragen
         for zaehler := 1 to resCount do
              for zaehler2 := 1 to varCount do
                  Dualgrid.Cells[zaehler, zaehler2] := tmpArray2[zaehler2,zaehler];

        // ZFDualGrid schreiben
        for zaehler := 1 to resCount do
            ZFDualGrid.Cells[zaehler,0] := tmpArray2[varCount + 2, zaehler];

        // Operatoren schreiben >=   oder <=
       if optimierungDual = 'MIN' then tmpString := '>='
       else tmpString := '<=';
       for zaehler := 1 to varCount do
            DualGrid.Cells[resCount + 1, zaehler] := tmpString;

       // rechte Spalte schreiben
       for zaehler := 1 to varCount do
           DualGrid.Cells[resCount + 2, zaehler] := tmpArray2[zaehler, resCount + 1];


          AktualisiereDualGrid(resCount, varCount, optimierungDual);
          AktualisiereVorzeichenDual(AnzVariable.Value);

          // Tabkarte fuers Dualmodell sichtbar machen
          Dualmodell.TabVisible := true;
          Dualmodell.Visible := true;
          Ansicht.ActivePage := Dualmodell;

          // Letzten Eintrag im Funktionenmenü
          // Worddokument erstellen auf aktiv setzen
          ErstelleDokument.Enabled := true;


          // Ergebnis wieder im globalen tmpArray abspeichern
          for zaehler := 1 to AnzRestriktionen.Value do
              for zaehler2 := 1 to AnzVariable.Value + 2 do
                  tmpArray[zaehler2, zaehler] := LinearGrid.Cells[zaehler2, zaehler];

          for zaehler := 1 to AnzVariable.Value do
              tmpArray[zaehler, resCount + 1] := ZFLinearGrid.Cells[zaehler, 0];


          // Vorzeichenual aktualisieren
         VorzeichenDual.Clear();
         for zaehler :=1 TO resCount DO
         begin
                Str(zaehler , tmpString);
                tmpString := 'u' + tmpString;
                VorzeichenDual.Items.Add(tmpString);
                //VorzeichenDual.Selected[zaehler-1] := true;
          end;

   end;


    VereinfachenBool := true;
    AnzahlVariableGlobal := varCount;
    AnzahlRestriktionenGlobal := resCount;

   end;

end;


procedure TForm1.UmformButtonClick(Sender: TObject);
begin
     UmformModell;
end;


procedure TForm1.Umformen1Click(Sender: TObject);
begin
     UmformModell;
end;

procedure InitialisiereProgramm;
var anzVar, anzRes:integer;
begin
     with Form1 do
     begin
          anzVar := AnzVariable.Value;
          anzRes := AnzRestriktionen.Value;
          Caption := cgCaption ;
          // Dualmodell initialisieren und mit default
          // Beschriftung / Einstellung fuellen
          InitialisiereDualGrid;
          AktualisiereDualGrid(anzVar, anzRes, 'MIN');
          AktualisiereVorzeichenDual(anzVar);

          // Linearmodell initialisieren und mit default Beschriftung / Einstellung
          // fuellen
          InitialisiereLinearGrid;
          OptimierungGroup.ItemIndex := 0;
          //AnzVariable.Value := 2;
          //AnzRestriktionen.Value := 2;
          AktualisiereLinearGrid;
          VorzeichenLinear.Clear;
          AktualisiereVorzeichenLinear;

          // Dualmodell Tabkarte verstecken
          Dualmodell.TabVisible := false;
          Dualmodell.Visible := false;
          Ansicht.ActivePage := Linearmodell;

          // Letzten Eintrag im Funktionenmenü
          // Worddokument erstellen auf inaktiv setzen
          ErstelleDokument.Enabled := false;

          // Dateihandling
          SpeichernDatei.DefaultExt := 'pdm';
          OeffnenDatei.DefaultExt := 'pdm';
      end;
end;

procedure TForm1.InitialisierenClick(Sender: TObject);
begin
     InitialisiereProgramm;
end;

procedure TForm1.Initialisieren1Click(Sender: TObject);
begin
     InitialisiereProgramm;

     //if pfad <> '' then Caption := pfad;
end;


procedure TForm1.Ueber1Click(Sender: TObject);
begin
     MessageDlg('Umformung Primal -> Dualmodell' + #13
                 + 'Version 1.0' + #13 + #13
                 + 'Erstellt von:' + #13 + #13
                 + 'Markus Wittekindt' + #13
                 + 'Stephanie Föhrenbach' + #13
                 + 'WS 2000 / 2001'
                  , mtInformation, [mbOk], 0)
end;

procedure TForm1.Inhalt1Click(Sender: TObject);
   var
  ap_pfad : String;
begin
  //ap_pfad := ExtractFilePath(Application.ExeName); // Pfad zur Anwendung
  ap_pfad := 'C:\Methodenbank\Solver\Interaktive Solver\Primal Dual Wandler\02 Dokumentation\Benutzerhandbuch Primal Dual Wandler.pdf'; // Pfad zur Hilfe anhängen
  ShellExecute(Handle, 'open', pchar(ap_pfad), nil, nil,SW_SHOW);
end;


procedure TForm1.FormCreate(Sender: TObject);
var
  PDIniFile : TIniFile ;
begin
     // Execpfad ermitteln
     Execpfad := extractFilePath(Application.ExeName) ;

     // Hilfe Ordner gel...
     Application.HelpFile := Execpfad + 'LD.hlp' ;

     // Die Ini
     PDIniFile:= TIniFile.Create(Execpfad + '\PrimalDual.INI');

     // Datenpfad über Ini einlesen
     // oder einfacher ..\Daten
     Datenpfad := PDIniFile.ReadString('Pfade','Datenpfad','') ;


     // ohne garantie !
     if length(Datenpfad) = 0 then
        Datenpfad := ExpandFileName (Execpfad + '..\Daten') ;

   //showmessage(datenpfad);

     PDIniFile.Free ;

end;

procedure TForm1.FormClose(Sender: TObject; var Action: TCloseAction);
begin
     Application.HelpCommand(Help_Quit, 0)
end;

procedure TForm1.Button1Click(Sender: TObject);
begin

InitialisiereLinearGrid;

InitialisiereProgramm;

if Speichern1.Enabled = True
   then Caption := cgCaption + '  [' + pfad + ']'
   else Caption := cgCaption;

end;


procedure TForm1.Button2Click(Sender: TObject);                        // Vereinfachen
var
  tmpInt, zaehler, zaehler2, tmpArrayZaehler,
  RestriktionenCount, VariableCount           : integer;
tmpArray6: array[1..42,1..41] of variant;
//VorzeichenDualSelect: array[1..40] of integer;
testBool : Boolean;
tmpVariant1, tmpVariant2 : variant;
tmpInteger1, tmpInteger2 : integer;
optimierungDual, tmpString : string;
tmpVariant : variant;
tmpDouble, tmpDouble1, tmpDouble2: double;

begin

    if  VereinfachungErledigt_1 = true then
    begin
         if VereinfachungErledigt_2 = true then
         begin
              UmformModell;
              exit
         end
         else
         begin     // zweite Vereinfachung :::::::::::::::::::::::::::::::::::::::::::::::
           //    AnzahlVariableGlobal_2 , AnzahlRestriktionenGlobal_2
             for zaehler := 1 to AnzahlRestriktionenGlobal_2 do
             begin
                  tmpVariant :=  DualGrid.Cells[AnzahlVariableGlobal_2 + 2, zaehler];
                  tmpDouble := StrToFloat(DualGrid.Cells[AnzahlVariableGlobal_2 + 2, zaehler]);
                  if tmpDouble < 0 then
                  if tmpVariant <> '0' then
                  begin  // Zeile umdrehen
                         for zaehler2 := 1 to  AnzahlVariableGlobal_2 do
                             begin
                                  tmpDouble := StrToFloat(DualGrid.Cells[zaehler2, zaehler]);
                                  tmpDouble := tmpDouble * (-1);
                                  DualGrid.Cells[zaehler2, zaehler] := FloatToStr(tmpDouble);

                             end;
                         if DualGrid.Cells[AnzahlVariableGlobal_2 + 1, zaehler] = '<=' then
                                DualGrid.Cells[AnzahlVariableGlobal_2 + 1, zaehler] := '>='
                         else   DualGrid.Cells[AnzahlVariableGlobal_2 + 1, zaehler] := '<=';

                         tmpDouble := StrToFloat(DualGrid.Cells[AnzahlVariableGlobal_2 + 2, zaehler]);
                         tmpDouble := tmpDouble * (-1);
                         DualGrid.Cells[AnzahlVariableGlobal_2 + 2, zaehler] := FloatToStr(tmpDouble);

                  end;

             end;
             VereinfachungErledigt_2 := true;
             Button2.Caption := 'Zurück';
             exit
         end   // zweite Vereinfachung :::::::::::::::::::::::::::::::::::::::::::::::
    end;



 //    for zaehler := 1 to 40 do
 //           VorzeichenDualSelect[zaehler]:= 0;

    RestriktionenCount := varCount;    // Restriktionen im Dual-System
     VariableCount   := resCount;



    tmpArrayZaehler := 0;
    zaehler := 1;
    while zaehler < varCount + 1 do// varCount = Restriktionen!
      begin
      tmpArrayZaehler := tmpArrayZaehler + 1;
      testBool := true;
      // testen, ob Zeile mit der Nachfolgenden übereinstimmt (* -1), wenn ja, dann nicht kopieren
      if zaehler < varCount then // RestriktionenCount then
      begin
        for zaehler2 := 1 to VariableCount do
          if testBool = true then
             begin
                  tmpDouble1 := StrToFloat(DualGrid.Cells[zaehler2,zaehler]);
                  tmpDouble2 := StrToFloat(DualGrid.Cells[zaehler2,zaehler + 1]);
                  tmpDouble2 := tmpDouble2  * (-1);
                  if tmpDouble1 = tmpDouble2 then
                     testBool := true
                  else testBool := false;
             end;


           if testBool = true then      // letzte Spalte testen
              begin
                        tmpDouble1 := StrToFloat(DualGrid.Cells[VariableCount + 2,zaehler]);
                        tmpDouble2 := StrToFloat(DualGrid.Cells[VariableCount + 2,zaehler + 1]);
                        tmpDouble2 := tmpDouble2  * (-1);

                        if tmpDouble1 = tmpDouble2 then
                           testBool := true
                        else testBool := false;
              end;
           if testBool = true then             // Operatoren testen <= mit  >=
              begin
                   if DualGrid.Cells[VariableCount + 1,zaehler] = '<=' then
                      begin
                      if DualGrid.Cells[VariableCount + 1,zaehler+1] = '>=' then  testBool := true
                      end
                   else if DualGrid.Cells[VariableCount + 1,zaehler] = '>=' then
                      begin
                      if DualGrid.Cells[VariableCount + 1,zaehler+1] = '<=' then  testBool := true
                      end
                   else testBool := false;


              end;

       end
       else testBool := false; // testen ende, ... wenn testBool = false, dann kopieren sonst

       if testBool = true then  // nachfolgende Zeile = aktuelle (zaehler) Zeile ... kopiere zweite Zeile  aus DualGrid
       begin    // Wenn nach Operator positives Zeichen, dann diese Zeile   sonst die Nachfolgende
                tmpDouble := StrToFloat(DualGrid.Cells[VariableCount + 2, zaehler]);
                tmpVariant := DualGrid.Cells[VariableCount + 2, zaehler];
             //   if tmpVariant <> '0' then
             //   begin
                     if tmpDouble > 0 then
                     begin                     // >0
                      for zaehler2 := 1 to VariableCount + 2 do
                          tmpArray6[zaehler2, tmpArrayZaehler] := DualGrid.Cells[zaehler2, zaehler];
                      tmpArray6[VariableCount + 1, tmpArrayZaehler] := '=';
                      zaehler := zaehler + 2;
                      RestriktionenCount := RestriktionenCount -1;
                     end
                     else        // <0
                     begin
                      for zaehler2 := 1 to VariableCount + 2 do
                          tmpArray6[zaehler2, tmpArrayZaehler] := DualGrid.Cells[zaehler2, zaehler + 1];
                      tmpArray6[VariableCount + 1, tmpArrayZaehler] := '=';
                      zaehler := zaehler + 2;
                      RestriktionenCount := RestriktionenCount -1;
                     end;
              //   end;
              //   else      // == 0
              //   begin
              //        for zaehler2 := 1 to VariableCount + 2 do
              //            tmpArray6[zaehler2, tmpArrayZaehler] := DualGrid.Cells[zaehler2, zaehler + 1];
              //        tmpArray6[VariableCount + 1, tmpArrayZaehler] := '=';
              //        zaehler := zaehler + 2;
              //        RestriktionenCount := RestriktionenCount -1;
              //    end;
       end
       else                 //kopiere aktuelle Zeile aus DualGrid
       begin
            for zaehler2 := 1 to VariableCount + 2 do
                tmpArray6[zaehler2, tmpArrayZaehler] := DualGrid.Cells[zaehler2, zaehler];
            zaehler := zaehler + 1;
       end;

     end;


      // tmpArray6 in DualGrid übetragen

   for zaehler := 1 to RestriktionenCount do
       for zaehler2 := 1 to VariableCount + 2 do
           DualGrid.Cells[zaehler2, zaehler] := tmpArray6[zaehler2, zaehler];




////////////////////////////////////////////////
         // Zusammenfassung von Spalten zu unbeschränkten Vektoren
   VorzeichenDual.Clear();
    tmpArrayZaehler := 0;
    zaehler := 1;
    while zaehler < resCount + 1 do// resCount = Variable!  ... von links nach rechts durchlaufen
      begin
      tmpArrayZaehler := tmpArrayZaehler + 1;
      testBool := true;
      // testen, ob Zeile mit der Nachfolgenden übereinstimmt (* -1), wenn ja, dann nicht kopieren
      if zaehler < resCount then //VariableCount then
      begin
        for zaehler2 := 1 to RestriktionenCount do
          if testBool = true then
             begin
                  tmpDouble1 := StrToFloat(DualGrid.Cells[zaehler,zaehler2]);
                  tmpDouble2 := StrToFloat(DualGrid.Cells[zaehler+1,zaehler2]);
                  tmpDouble2 := tmpDouble2  * (-1);
                  if tmpDouble1 = tmpDouble2 then
                     testBool := true
                  else testBool := false;
             end;


           if testBool = true then      // ZFDualGrid
              begin
                        tmpDouble1 := StrToFloat(ZFDualGrid.Cells[zaehler,0]);
                        tmpDouble2 := StrToFloat(ZFDualGrid.Cells[zaehler + 1,0]);
                        tmpDouble2 := tmpDouble2  * (-1);

                        if tmpDouble1 = tmpDouble2 then
                           testBool := true
                        else testBool := false;
              end;
       end
       else testBool := false; // testen ende, ... wenn testBool = false, dann kopieren sonst

       if testBool = true then  // nachfolgende Zeile = aktuelle (zaehler) Zeile ... kopiere zweite Spalte  aus DualGrid
       begin

            for zaehler2 := 1 to RestriktionenCount do
                tmpArray6[tmpArrayZaehler, zaehler2] := DualGrid.Cells[zaehler+1, zaehler2];
            tmpArray6[tmpArrayZaehler, RestriktionenCount + 1] := ZFDualGrid.Cells[zaehler+1, 0];

            Str(tmpArrayZaehler , tmpString);
            tmpString := 'u' + tmpString;
            VorzeichenDual.Items.Add(tmpString);
            VorzeichenDual.Selected[tmpArrayZaehler-1] := true;

//// %%%%%%%%%%%
            zaehler := zaehler + 2;
            VariableCount := VariableCount -1;
       end
       else                 //kopiere aktuelle Zeile aus DualGrid
       begin
            for zaehler2 := 1 to RestriktionenCount do
                 tmpArray6[tmpArrayZaehler, zaehler2] := DualGrid.Cells[zaehler, zaehler2];
            tmpArray6[tmpArrayZaehler, RestriktionenCount + 1] := ZFDualGrid.Cells[zaehler, 0];

            Str(tmpArrayZaehler , tmpString);
            tmpString := 'u' + tmpString;
            VorzeichenDual.Items.Add(tmpString);


            zaehler := zaehler + 1;
       end;

     end;

////////////////////////////


     // tmpArray6 in DualGrid und ZFDualGrid übetragen
   InitialisiereDualGrid;

   for zaehler := 1 to RestriktionenCount do
       for zaehler2 := 1 to VariableCount + 2 do
           DualGrid.Cells[zaehler2, zaehler] := tmpArray6[zaehler2, zaehler];

   for zaehler := 1 to varCount do
   begin
       DualGrid.Cells[VariableCount + 1, zaehler] := tmpArray6[resCount + 1, zaehler];
       DualGrid.Cells[VariableCount + 2, zaehler] := tmpArray6[resCount + 2, zaehler];

   end;
   for zaehler2 := 1 to VariableCount do
         ZFDualGrid.Cells[zaehler2, 0] := tmpArray6[zaehler2, RestriktionenCount + 1];



   AnzahlVariableGlobal_2 := VariableCount;
   AnzahlRestriktionenGlobal_2 := RestriktionenCount;



          // Tabkarte fuers Dualmodell sichtbar machen
          Dualmodell.TabVisible := true;
          Dualmodell.Visible := true;
          Ansicht.ActivePage := Dualmodell;
               // ZF beschriften
          if OptimierungGroup.ItemIndex = 0
          then optimierungDual := 'MIN'        // vom DUAL !!!!!!
          else optimierungDual := 'MAX';


     // DualGrid mit tmpArrayZaehler = resCount und VariableCount aktualisieren
     AktualisiereDualGrid(VariableCount, RestriktionenCount, optimierungDual);
  //   AktualisiereVorzeichenDual(AnzVariable.Value);


{
   VorzeichenDual.Clear();                     // ????????????????????.....
    for zaehler :=1 TO AnzVariable.Value DO
         begin

                Str(zaehler , tmpString);
                tmpString := 'u' + tmpString;
                VorzeichenDual.Items.Add(tmpString);
                VorzeichenDual.Selected[zaehler-1] := true

         end;
 }
    // VorzeichenDual.Refresh;



     VereinfachenBool := false;
     Button2.Caption := 'Weiter vereinfachen';
     // Button2.Enabled := false;

     VereinfachungErledigt_1 := true;
     VereinfachungErledigt_2 := false;
end;

procedure TForm1.Neu1Click(Sender: TObject);
var zaehler : integer;
begin
// neue Datei anlegen

   if Speichern1.Enabled = true then
      if MessageDlg('Werte speichern in ' + pfad + ' ?', mtInformation, [mbYes, mbNo],0) = mrYes
           then
           begin
           Caption := cgCaption ;
           Speichern1Click(Self);
           end;
   Speichern1.Enabled := false;
   
   InitialisiereLinearGrid;
   AktualisiereLinearGrid;
   for zaehler := 0 to AnzVariable.Value - 1 do
       VorzeichenLinear.Selected[zaehler] := false;

   InitialisiereDualGrid;
   AktualisiereDualGrid(4,4,'NOCH NIX');
   AktualisiereVorzeichenDual(0);

   Linearmodell.TabVisible := true;
   Dualmodell.TabVisible := false;
   Linearmodell.Visible := true;
   Ansicht.ActivePage := Linearmodell;
   Caption := cgCaption ;
end;

procedure TForm1.Beenden1Click(Sender: TObject);
var tmpVariant: word;
begin
     if MessageDlg('Programm beenden?', mtInformation, [mbYes, mbNo],0) = mrYes then
            Close

end;



procedure TForm1.Oeffnen1Click(Sender: TObject);
var i : integer;
    tmpVariant : variant;
    zaehler, zaehler2: integer;
begin
     //OeffnenDatei.InitialDir := 'C:\Stephanie';
     OeffnenDatei.InitialDir := 'C:\Methodenbank\Solver\Interaktive Solver\Primal Dual Wandler\01 Programm\Daten' ;


     //OeffnenDatei.Filter := '*.doc';
     //OeffnenDatei.Execute

      //SpeichernDatei.Options := [ofAllowMultiSelect, ofFileMustExist];
       OeffnenDatei.Filter := 'PrimalDual-Dateien (*.pdm)|*.pdm|Alle Dateien (*.*)|*.*';
      //SpeichernDatei.FilterIndex := 3; { Beim Start des Dialogs alle Dateien anzeigen }
       OeffnenDatei.DefaultExt := 'pdm';

     if OeffnenDatei.Execute then
     begin
       pfad := OeffnenDatei.FileName;


       AssignFile(f, pfad);          // Zuordnen des Dateipfades zur Dateivariablen f
       {$I-} Reset(f); {$I+} // Versuch, die Datei zu öffnen

       if IOResult <> 0 then
          begin
            MessageBeep(0);
            //Application.MessageBox('Die Datei konnte nicht geöffnet werden!','Fehler',52);
            //Exit
            showmessage('IOResult-Fehler  -> wir versuchen es trotzdem weiter');

          end;
       //else

           Read(f, tmpRecord);

       Caption := cgCaption + '  [' + pfad + ']' ;
       AnzVariable.Value := StrToInt(tmpRecord.tmpArray4[0,0]);
       AnzRestriktionen.Value := StrToInt(tmpRecord.tmpArray4[1,0]);
       InitialisiereLinearGrid;


           for zaehler := 1 to AnzRestriktionen.Value do
               for zaehler2:= 1 to AnzVariable.Value + 2 do
                   LinearGrid.Cells[zaehler2, zaehler] := tmpRecord.tmpArray4[zaehler2, zaehler];
           for zaehler := 1 to AnzVariable.Value do
               ZFLinearGrid.Cells[zaehler,0] := tmpRecord.tmpArray4[zaehler, AnzRestriktionen.Value + 1];

            // MAX oder MIN
      if  tmpRecord.tmpArray4[2,0] = '0' then
          OptimierungGroup.ItemIndex := 0 // LinearGrid = MAX
      else OptimierungGroup.ItemIndex := 1;

      // Vorzeichenunbeschränktheit
      // VorzeichenLinear.Selected[zaehler - 1];
      // 1: ja, 0: nein
      VorzeichenLinear.Clear;
      AktualisiereVorzeichenLinear;
      for zaehler := 1 to AnzVariable.Value do
          if  tmpRecord.tmpArray4[0,zaehler] = '1' then
               VorzeichenLinear.Selected[zaehler - 1] := true
           else  VorzeichenLinear.Selected[zaehler - 1] := false;

      AktualisiereVorzeichenLinear;

      Speichern1.Enabled := true;
      AktualisiereLinearGrid;
         Linearmodell.TabVisible := true;
   Linearmodell.Visible := true;
   Ansicht.ActivePage := Linearmodell;
     end
end;

procedure TForm1.Speichernunter1Click(Sender: TObject);              // Speichern unter
begin
     // TSaveDialog : SpeichernDatei

          //SpeichernDatei.Options := [ofAllowMultiSelect, ofFileMustExist];
          //SpeichernDatei.Filter := 'Textdateien (*.txt)|*.txt|Alle Dateien (*.*)|*.*|PrimalDual Dateien (*.pdm*)|*.pdm*';
          //SpeichernDatei.FilterIndex := 3; { Beim Start des Dialogs alle Dateien anzeigen }
          SpeichernDatei.InitialDir := Datenpfad ;

          SpeichernDatei.DefaultExt := 'pdm';

     if SpeichernDatei.Execute then
     begin

          pfad := SpeichernDatei.FileName;
          AssignFile(f, pfad);
          {$I-} Rewrite(f); {$I+}
          if IOResult <> 0 then ShowMessage('Fehler beim Erzeugen der neuen Datei!');

          Speichern1Click(Self);
          Caption := cgCaption + '  [' + pfad + ']' ;
          Speichern1.Enabled := True;


     end;
end;

procedure TForm1.Speichern1Click(Sender: TObject);
var zaehler, zaehler2 : integer;
    tmpVariant : variant;
begin
//
{
      Datei speichern
      - tmpRecord-Array speichert in 40x40-Matrix
        das LinearGrid sowie
      - unterhalb: ZFLinearGrid.
      auf Position
          - 0,0: AnzVariable.Value
          - 1,0: ResVariable.Value abgelegt
          - 2,0: MAX oder MIN        ... OptimierungGroup.ItemIndex = 0 == MAX
      auf Positionen 0,1 bis 0,AnzRestriktionen.Value: 1:Vorzeichenunbeschränkt, 0:Vorzeichenbeschränkt

}
{$I-} Reset(f); {$I+}

     //SpeichernDatei.Options := [ofAllowMultiSelect, ofFileMustExist];
     //SpeichernDatei.Filter := 'Textdateien (*.txt)|*.txt|Alle Dateien (*.*)|*.*|PrimalDual Dateien (*.pdm*)|*.pdm*';
     //SpeichernDatei.FilterIndex := 3; { Beim Start des Dialogs alle Dateien anzeigen }
     SpeichernDatei.InitialDir := Datenpfad ;

     SpeichernDatei.DefaultExt := 'pdm';



      if IOResult <> 0 then
         ShowMessage('Fehler beim Speichern!')
      else
          Caption := cgCaption + '  [' + pfad + ']' ;
          for zaehler := 1 to AnzRestriktionen.Value do   // in tmpRecord übertragen
              for zaehler2 := 1 to AnzVariable.Value + 2 do
                   tmpRecord.tmpArray4[zaehler2, zaehler] := LinearGrid.Cells[zaehler2, zaehler];
          for zaehler := 1 to AnzVariable.Value do
              tmpRecord.tmpArray4[zaehler,AnzRestriktionen.Value + 1] := ZFLinearGrid.Cells[zaehler, 0];

          tmpRecord.tmpArray4[0,0] := IntToStr(AnzVariable.Value);
          tmpRecord.tmpArray4[1,0] := IntToStr(AnzRestriktionen.Value);

          // MAX oder MIN
          if OptimierungGroup.ItemIndex = 0  then
             tmpRecord.tmpArray4[2,0] := '0' // LinearGrid = MAX
          else tmpRecord.tmpArray4[2,0] := '1';

          // Vorzeichenunbeschränktheit
          // VorzeichenLinear.Selected[zaehler - 1];
          // 1: ja, 0: nein
          for zaehler := 1 to AnzVariable.Value do
              if  VorzeichenLinear.Selected[zaehler - 1] = true then
                  tmpRecord.tmpArray4[0,zaehler] := '1'
              else tmpRecord.tmpArray4[0,zaehler] := '0';


          Write(f, tmpRecord)
end;

procedure TForm1.Button3Click(Sender: TObject);
begin
// test
end;

end.



