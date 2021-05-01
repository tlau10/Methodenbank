unit Childwin;
{ Unit der MDI-Kindfenster, TIteratorChild}

interface

uses WinTypes, WinProcs, Classes, Graphics, Forms, Controls, Bruch,
  StdCtrls, Buttons, Grids, ExtCtrls, SysUtils, Dialogs, Mask, Menus;

type
  TIteratorChild = class(TForm)
    Panel1: TPanel;
    Matrix: TStringGrid;
    Anzahl_Zeilen: TMaskEdit;
    Anzahl_Spalten: TMaskEdit;
    Zeilen: TLabel;
    Spalten: TLabel;
    Formular_Neu: TCheckBox;
    Selekt: TLabel;
    MainMenu1: TMainMenu;
    hallo1: TMenuItem;
    SpeichernMnu: TMenuItem;
    SaveDialog: TSaveDialog;
    NeuMnu: TMenuItem;
    OeffnenMnu: TMenuItem;
    BeendenMnu: TMenuItem;
    N1Mnu: TMenuItem;
    Bearbeiten1: TMenuItem;
    PivotelementMnu: TMenuItem;
    IterierenMnu: TMenuItem;
    OptimumMnu: TMenuItem;
    Bevel1: TBevel;
    Bevel2: TBevel;
    procedure FormClose(Sender: TObject; var Action: TCloseAction);
    procedure IterierenMnuClick(Sender: TObject);
    procedure MatrixClick(Sender: TObject);
    procedure PivotClick(Sender: TObject);
    procedure OptimumMnuClick(Sender: TObject);
    procedure Matrix_anpassen(Sender: TObject);
    procedure SpeichernMnuClick(Sender: TObject);
    procedure NeuMnuClick(Sender: TObject);
    procedure Oeffnen1Click(Sender: TObject);
    procedure BeendenMnuClick(Sender: TObject);
  private
    { Private declarations }
    Sel_Zeile  :word;   {Speicher für selektierte Zeile}
    Sel_Spalte :word;   {Speicher für selektierte Spalte}
    procedure Zeilen_addieren(Zielzeile,Addzeile:word);
    procedure Zeile_dividieren(Zeile,Spalte:word);
    procedure Iteriere_Matrix(Spalte,Zeile:word);
    function Pivotzeile(Spalte:word):word;
    function Pivotspalte :word;
    procedure Matrix_initialisieren;
  public
    { Public declarations }
  end;

implementation

uses Main;

{$R *.DFM}


procedure TIteratorChild.FormClose(Sender: TObject; var Action: TCloseAction);
{automatisch von Delphi generiert}
begin
  Action := caFree;
end;

procedure TIteratorChild.Zeilen_addieren(Zielzeile,Addzeile:word);
{addiert zwei Zeilen der Matrix}
var s       :word;
begin
  for s:=1 to Matrix.ColCount-1 do
     Matrix.Cells[s,Zielzeile]:=Bruch_addieren(Matrix.Cells[s,Zielzeile],Matrix.Cells[s,Addzeile]);
end;


procedure TIteratorChild.Zeile_dividieren(Zeile,Spalte:word);
{dividiert zwei Zeilen der Matrix}
var s       :word;
    divisor :string;
begin
  divisor:=Matrix.Cells[Spalte,Zeile];
  for s:=1 to Matrix.ColCount-1 do
     Matrix.Cells[s,Zeile]:=Bruch_dividieren(Matrix.Cells[s,Zeile],divisor);
end;

procedure TIteratorChild.Matrix_anpassen(Sender: TObject);
{erweitert oder verkleinert die Matrix}
var z,s :word;
begin
   Matrix.ColCount:=strtoint(Anzahl_Spalten.Text)+1;
   Matrix.RowCount:=strtoint(Anzahl_Zeilen.Text)+1;
   for z:=1 to Matrix.RowCount-2 do
      Matrix.Cells[0,z]:='R'+inttostr(z);
   Matrix.Cells[0,Matrix.RowCount-1]:='Z';
   for s:=1 to Matrix.ColCount-2 do
      Matrix.Cells[s,0]:='x'+inttostr(s);
   Matrix.Cells[Matrix.ColCount-1,0]:='b';
   Matrix_initialisieren;
end;

procedure TIteratorChild.Matrix_initialisieren;
{füllt Matrix mit 0-Werten, aber nur falls Element leer}
var z,s :word;
begin
   for z:=1 to Matrix.RowCount-1 do
      for s:=1 to Matrix.ColCount-1 do
         if Matrix.Cells[s,z]='' then
            Matrix.Cells[s,z]:='0';
end;

procedure TIteratorChild.MatrixClick(Sender: TObject);
{wird aufgerufen sobald auf Matrix geklickt wird oder wenn die Matrix
den Focus verliert, sowie wenn in eine andere Zelle mit TAB gesprungen wird}
begin
     Matrix.Cells[Sel_Spalte,Sel_Zeile]:=FloatinBruchUmwandeln(Matrix.Cells[Sel_Spalte,Sel_Zeile]);
     Sel_Zeile:=Matrix.Row;
     Sel_Spalte:=Matrix.Col;
     Selekt.Caption:='selekt. Element '+inttostr(Sel_Zeile)+'/'+inttostr(Sel_Spalte);
end;


function TIteratorChild.Pivotzeile(Spalte:word):word;
{bestimmt Pivotzeile der Matrix nach übergebener Spalte}
var   z,p_zeile,b_spalte  : word;
      wert_a_vektor,
      wert_b_vektor,
      p_wert,
      p_min               : real;
begin
   p_zeile:=0;
   p_wert:=0;
   p_min:=1000000;
   wert_a_vektor:=0;
   wert_b_vektor:=0;
   b_spalte:=Matrix.ColCount-1;
   for z:=1 to Matrix.RowCount-2 do
      begin
      wert_a_vektor:=Zaehler(Matrix.Cells[Spalte,z])/Nenner(Matrix.Cells[Spalte,z]);
      if (wert_a_vektor>0) then
         begin
         wert_b_vektor:=Zaehler(Matrix.Cells[b_spalte,z])/Nenner(Matrix.Cells[b_spalte,z]);
         if ((wert_b_vektor/wert_a_vektor)<p_min) then
            begin
            p_min:=wert_b_vektor/wert_a_vektor;
            p_zeile:=z;
            end;
         end;
      end;
   Pivotzeile:=p_zeile;
end;


function TIteratorChild.Pivotspalte :word;
{bestimmt Pivotspalte nach der Methode des negativsten Z-Elements}
var  s,
     z_zeile,
     p_spalte     : word;
     s_wert,s_neg : real;
begin
  p_spalte:=0;
  s_wert:=0;
  s_neg:=0;
  z_zeile:=Matrix.RowCount-1;
  for s:=1 to Matrix.ColCount-2 do
     begin
     s_wert:=Zaehler(Matrix.Cells[s,z_zeile])/
             Nenner(Matrix.Cells[s,z_zeile]);
     if (s_wert<s_neg) then
        begin
        s_neg:=s_wert;
        p_spalte:=s;
        end;
     end;
  Pivotspalte:=p_spalte;
end;


procedure TIteratorChild.Iteriere_Matrix(Spalte,Zeile:word);
{iteriert Matrix mit dem übergebenen Pivotelement im aktuellen Kindfenster}
var z,s            : word;
    gueltige_zeile : boolean;
    multiplikator  : string;
begin
if (Spalte=Matrix.ColCount-1) then
   begin
   MessageDlg('b-Vektor kann nicht in die Basis gelangen!!!', mtInformation,[mbOk], 0);
   exit;
   end;
if (Zeile=Matrix.RowCount-1) then
   begin
   MessageDlg('Z-Element kann nicht in die Basis gelangen!!!', mtInformation,[mbOk], 0);
   exit;
   end;
gueltige_zeile:=true;

if (Zaehler(Matrix.Cells[Spalte,Zeile])= 0) then   { Falls Pivotelement =0}
   begin
   z:=1;
   gueltige_zeile:=false;
   repeat
      if (Zaehler(Matrix.Cells[Spalte,z]) <> 0) then
         begin
         gueltige_zeile:=true;
         Zeilen_addieren(Zeile,z);
         end;
      inc(z);
   until (z=Matrix.RowCount) or (gueltige_zeile=true);
   end;

if (gueltige_zeile=false) then
   MessageDlg('Spalte kann nicht in die Basis gelangen!!!', mtInformation,[mbOk], 0)
else
   begin
   Zeile_dividieren(Zeile,Spalte); { Erzeuge 1 im Pivotelement }
   for z:= 1 to Matrix.RowCount-1 do
      begin
      if z<>Zeile then
         begin
         if (Zaehler(Matrix.Cells[Spalte,z])<> 0) then
            begin
            multiplikator:=Matrix.Cells[Spalte,z];
            for s:=1 to Matrix.ColCount-1 do
                Matrix.Cells[s,z]:=Bruch_subtrahieren(Matrix.Cells[s,z],
                    Bruch_multiplizieren(Matrix.Cells[s,Zeile],multiplikator));
            end;
         end;
      end;
   end;
end;



procedure TIteratorChild.IterierenMnuClick(Sender: TObject);
{führt Iteration aus und erstellt ggf. neue Instanz des Kindfensters}
var Child          : TIteratorChild;
    z,s            : word;
begin
if Formular_Neu.Checked=true then
   begin
   Child:=TIteratorChild.Create(Application);
   Child.Caption:='Tableau ' + IntToStr(IteratorMain.MDIChildCount);
   { Werte in neuem Formular übernehmen}
   Child.Formular_Neu.Checked:=true;
   Child.Anzahl_Zeilen.Text:=Anzahl_Zeilen.Text;
   Child.Anzahl_Spalten.Text:=Anzahl_Spalten.Text;
   Child.Sel_Zeile:=Sel_Zeile;
   Child.Sel_Spalte:=Sel_Spalte;
   Child.Selekt.Caption:='selekt. Element '+inttostr(Sel_Zeile)+'/'+inttostr(Sel_Spalte);
   Child.Matrix_anpassen(Application);
   { Neue Matrix mit aktuellen Werten füllen }
   for z:=1 to Matrix.RowCount-1 do
      for s:=1 to Matrix.ColCount-1 do
         Child.Matrix.Cells[s,z]:=Matrix.Cells[s,z];
   Child.Iteriere_Matrix(Sel_Spalte,Sel_Zeile);
   end
else
   begin
   Iteriere_Matrix(Sel_Spalte,Sel_Zeile);
   end;
end;


procedure TIteratorChild.PivotClick(Sender: TObject);
{Bestimmt Pivotelement}
var  z,s      : word;
begin
  s:=Pivotspalte;
  if (s<>0) then     { falls s=0 kann keine Pivotspalte festgelegt werden }
     begin
     z:=Pivotzeile(s);
     if (z<>0) then
        begin
        Sel_Zeile:=z;
        Sel_Spalte:=s;
        Selekt.Caption:='selekt. Element '+inttostr(Sel_Zeile)+'/'+inttostr(Sel_Spalte);
        Matrix.Row:=z;
        Matrix.Col:=s;
        end
     else
        MessageDlg('Kann keine Pivotzeile festlegen!!! Alle Elemente der Pivotspalte'+
                   ' verstoßen gegen die Nichtnegativitätsbedingung.', mtInformation,[mbOk], 0);
     end
  else
     MessageDlg('Optimum!!! Kein weiteres Pivotelement nach der Simplexmethode selektierbar.', mtInformation,[mbOk], 0);
end;


procedure TIteratorChild.OptimumMnuClick(Sender: TObject);
{Bestimmt das Optimum der Matrix nach dem Gauss'schen Algorithmus
und legt ggf. neue Kindfenster an}
var  z,s      : word;
     z1,s1    : word;
     Child    : TIteratorChild;
begin
repeat
  s:=Pivotspalte;
  if (s<>0) then    { falls s=0 kann keine Pivotspalte festgelegt werden }
     begin
     z:=Pivotzeile(s);
     if (z<>0) then
        begin
        Sel_Zeile:=z;
        Sel_Spalte:=s;
        Selekt.Caption:='selekt. Element '+inttostr(Sel_Zeile)+'/'+inttostr(Sel_Spalte);
        Matrix.Row:=z;
        Matrix.Col:=s;
        if (Formular_Neu.Checked=true) then  {neues Kindfenster?}
           begin
           Child:=TIteratorChild.Create(Application);
           { Werte in neuem Kindfenster übernehmen}
           Child.Formular_Neu.Checked:=true;
           Child.Anzahl_Zeilen.Text:=Anzahl_Zeilen.Text;
           Child.Anzahl_Spalten.Text:=Anzahl_Spalten.Text;
           Child.Matrix_anpassen(Application);
           { Neue Matrix mit aktuellen Werten füllen }
           for z1:=1 to Matrix.RowCount-1 do
              for s1:=1 to Matrix.ColCount-1 do
                 Child.Matrix.Cells[s1,z1]:=Matrix.Cells[s1,z1];
           Child.Caption:='Tableau ' + IntToStr(IteratorMain.MDIChildCount);
           { Iteration im neuen Kindfenster ausführen }
           Child.Iteriere_Matrix(s,z);
           { Quasi rekursiver Aufruf der Optimum-Funktion im neuen Kindfenster }
           Child.OptimumMnuClick(Application);
           Child.Sel_Zeile:=z;
           Child.Sel_Spalte:=s;
           Child.Selekt.Caption:='selekt. Element '+inttostr(z)+'/'+inttostr(s);
           end
        else
           Iteriere_Matrix(s,z);
        end
     else
        begin
        MessageDlg('Kann keine Pivotzeile festlegen!!! Alle Elemente der Pivotspalte'+
                   ' verstoßen gegen die Nichtnegativitätsbedingung.', mtInformation,[mbOk], 0);
        exit;
        end;
     end
  else
     begin
     MessageDlg('Optimum!!! Kein weiteres Pivotelement nach der Simplexmethode selektierbar.', mtInformation,[mbOk], 0);
     end;
until (s=0) or (Formular_Neu.Checked=true); {Kein weiteres Pivotelement}
end;


procedure TIteratorChild.SpeichernMnuClick(Sender: TObject);
{ Speichern der Matrix-Werte des aktuellen Kindfensters }
var z,s  : word;
    f    : TextFile;
begin
  if SaveDialog.Execute then
     begin
     AssignFile(f, SaveDialog.Filename);
     Rewrite(f);
     writeln(f,Anzahl_Zeilen.Text);
     writeln(f,Anzahl_Spalten.Text);
     for z:=1 to Matrix.RowCount-1 do
        for s:=1 to Matrix.ColCount-1 do
           writeln(f,Matrix.Cells[s,z]);
     CloseFile(F);
     Caption:=SaveDialog.Filename;
     end;
end;

procedure TIteratorChild.NeuMnuClick(Sender: TObject);
{Ruft die Methode des Elterfensters auf }
begin
   IteratorMain.FileNewItemClick(Sender);
end;

procedure TIteratorChild.Oeffnen1Click(Sender: TObject);
{Ruft die Methode des Elterfensters auf }
begin
  IteratorMain.FileOpenItemClick(Sender);
end;

procedure TIteratorChild.BeendenMnuClick(Sender: TObject);
{Ruft die Methode des Elterfensters auf }
begin
  IteratorMain.FileExitItemClick(Sender);
end;


end.
