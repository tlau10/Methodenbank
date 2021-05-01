unit Standort;

interface

uses
  Windows, Messages, SysUtils, Classes, Graphics, Controls, Forms, Dialogs,
  ComCtrls, ToolWin, Grids, StdCtrls, Spin, Buttons, Menus, Berechnung,
  Matrix, Feld, ExtCtrls, IniFiles, LCLIntf;

type

  { TFormular }

  TFormular = class(TForm)
    Sicht: TPageControl;
    EingabeSicht: TTabSheet;
    AnzOrteLabel: TLabel;
    AnzOrte: TSpinEdit;
    StandorteGrid: TStringGrid;
    BerechnenButton: TBitBtn;
    StandorteLabel: TLabel;
    MaschinenLabel: TLabel;
    MaschinenGrid: TStringGrid;
    ErgebnisSicht: TTabSheet;
    MatrixGrid: TStringGrid;
    MatrixLabel: TLabel;
    ZuordnungGrid: TStringGrid;
    ZuordnungLabel: TLabel;
    StandortMenu: TMainMenu;
    Datei: TMenuItem;
    BeendenItem: TMenuItem;
    StatusBar: TStatusBar;
    VerfahrenGroup: TRadioGroup;
    EnumRadio: TRadioButton;
    EntscheidRadio: TRadioButton;
    OeffnenDialog: TOpenDialog;
    SpeichernDialog: TSaveDialog;
    OeffnenItem: TMenuItem;
    N1: TMenuItem;
    N3: TMenuItem;
    SpeichernItem: TMenuItem;
    NeuItem: TMenuItem;
    SpeichernUnterItem: TMenuItem;
    StandPopupMenu: TPopupMenu;
    StandLeerenItem: TMenuItem;
    MaschPopupMenu: TPopupMenu;
    MaschLeerenItem: TMenuItem;
    Bearbeiten: TMenuItem;
    EinstellungItem: TMenuItem;
    Berechnungsart: TMenuItem;
    Rechenart1: TMenuItem;
    Rechenart2: TMenuItem;
    N2: TMenuItem;
    LeerenItem: TMenuItem;
    Hilfe: TMenuItem;
    Info: TMenuItem;
    procedure FormCreate(Sender: TObject);
    procedure FormResize(Sender: TObject);
    procedure ChangeGrids(Sender: TObject);
    procedure GridSelectCell(Sender: TObject; ACol, ARow: Integer;
      var CanSelect: Boolean);
    procedure GridKeyPress(Sender: TObject; var Key: Char);
    procedure StandorteGridSetEditText(Sender: TObject; ACol,
      ARow: Integer; const Value: String);
    procedure BerechnenButtonClick(Sender: TObject);
    procedure BeendenItemClick(Sender: TObject);
    procedure GridExit(Sender: TObject);
    procedure NeuItemClick(Sender: TObject);
    procedure GroupClick(Sender: TObject);
    procedure OeffnenItemClick(Sender: TObject);
    procedure SpeichernUnterItemClick(Sender: TObject);
    procedure SpeichernItemClick(Sender: TObject);
    procedure MaschLeerenItemClick(Sender: TObject);
    procedure StandLeerenItemClick(Sender: TObject);
    procedure FormClose(Sender: TObject; var StandortCloseAction: TCloseAction);
    procedure Rechenart1Click(Sender: TObject);
    procedure Rechenart2Click(Sender: TObject);
    procedure EinstellungItemClick(Sender: TObject);
    procedure LeerenItemClick(Sender: TObject);
    procedure InfoClick(Sender: TObject);
  private
    { Private declarations }
    procedure Berechnen(N: Integer);
    procedure ChangeCheck;
  public
    { Public declarations }
    Von,Bis,d: TLargeInteger;
    EingabeChanged: Boolean;
    DateiOn: Boolean;
    DetailEnabled: Boolean;
    ErgebnisEnabled: Boolean;
    StandortIni : TInifile ;

    procedure InitEingabeGrids(N: Integer);
    procedure FillEingabeGrids(N: Integer);
    procedure InitErgebnisGrids(N: Integer);
    procedure FillErgebnisGrids(N: Integer; ZMatrix: TIntMatrix; Ergebnis: TIntArray);
    procedure SetStatusInfo(StandortStatusText: String);
  end;

var
  Formular: TFormular;
  AFile: TFileStream;
  //-- Pfade
  Execpfad,
  Datenpfad : string ;
  Pfad: String;   // eigentlicher dateiname

const
     MinWidth = 640;
     MinHeight = 480;

implementation

uses Status, Einstellungen;

{$R *.DFM}

procedure TFormular.FormCreate(Sender: TObject);
begin
     Width := MinWidth;
     Height := MinHeight;
     EingabeChanged := True;
     DateiOn := False;
     SpeichernItem.Enabled := False;
     InitEingabeGrids(4);
     Formular.Enabled := False;
     Sicht.Visible := False;
     Sicht.ActivePage := EingabeSicht;
     DetailEnabled := False;
     ErgebnisEnabled := False;

     Execpfad := extractFilePath(application.exename) ;

     //-- Ini-Daten
     StandortIni := TInifile.Create ( Execpfad + 'Standort.Ini' ) ;
     Datenpfad := StandortIni.ReadString ( 'Einstellungen','Datenpfad','' ) ;
     StandortIni.Free ;
end;

procedure TFormular.FormResize(Sender: TObject);
var dWidth, dHeight: Integer;
begin
     dWidth := Width - MinWidth;
     dHeight := Height - MinHeight;
     if dHeight < 0 then dHeight:=0;
     if dWidth < 0 then dWidth:=0;

     // Fenstergroesse mit PageControl synchronisieren
     Height := MinHeight + dHeight;
     Width := MinWidth + dWidth;
     Sicht.Height := 417 + dHeight;
     Sicht.Width := 633 + dWidth;

     // Komponentenposition anpassen
     AnzOrte.Left := 495 + dWidth;
     AnzOrteLabel.Left := 495 + dWidth;
     VerfahrenGroup.Left := 465 + dWidth;
     VerfahrenGroup.Top := 205 + dHeight;
     EnumRadio.Left := 480 + dWidth;
     EnumRadio.Top := 230 + dHeight;
     EntscheidRadio.Left := 480 + dWidth;
     EntscheidRadio.Top := 250 + dHeight;
     BerechnenButton.Left := 470 + dWidth;
     BerechnenButton.Top := 330 + dHeight;
     MaschinenLabel.Top := 206 + (dHeight div 2);
     MaschinenGrid.Top := 204 + (dHeight div 2);
     ZuordnungLabel.Top := 302 + dHeight;
     ZuordnungGrid.Top := 317 + dHeight;

     // Komponentengroesse anpassen
     StandorteGrid.Height := 178 + (dHeight div 2);
     StandorteGrid.Width := 360 + dWidth;
     MaschinenGrid.Height := 178 + (dHeight div 2);
     MaschinenGrid.Width := 360 + dWidth;
     MatrixGrid.Height := 278 + dHeight;
     MatrixGrid.Width := 564 + dWidth;
end;

procedure TFormular.ChangeGrids(Sender: TObject);
begin
     ChangeCheck();
     StandorteGrid.RowCount := AnzOrte.Value + 1;
     StandorteGrid.ColCount := StandorteGrid.RowCount;
     MaschinenGrid.RowCount := AnzOrte.Value + 1;
     MaschinenGrid.ColCount := MaschinenGrid.RowCount;
     InitEingabeGrids(AnzOrte.Value);
end;

procedure TFormular.InitEingabeGrids(N: Integer);
var i: Integer;
    Fill: Char;
begin
     with StandorteGrid do
     begin
          Fill := #64;
          for i := 1 to N do
          begin
               Inc(Fill);
               Cells[i,0] := ' '+Fill;
               Cells[0,i] := ' '+Fill;
               Cells[i,i] := ' -';
          end;
          Col := 2;
     end;
     with MaschinenGrid do
     begin
          for i := 1 to N do
          begin
               Cells[i,0] := ' '+IntToStr(i);
               Cells[0,i] := ' '+Cells[i,0];
               Cells[i,i] := ' -';
          end;
          Col := 2;
     end;
     FillEingabeGrids(N);
end;

procedure TFormular.FillEingabeGrids(N: Integer);
var i,j: Integer;
begin
     for i:=1 to N do
         for j:=1 to N do
         begin
             if i=j then Continue;
             if StandorteGrid.Cells[i,j] = '' then StandorteGrid.Cells[i,j] := '0';
             if MaschinenGrid.Cells[i,j] = '' then MaschinenGrid.Cells[i,j] := '0';
         end;
end;

procedure TFormular.GridSelectCell(Sender: TObject; ACol,
  ARow: Integer; var CanSelect: Boolean);
var Temp: TStringGrid;
begin
     if ACol = ARow then
     begin
          if Sender = MaschinenGrid then
                    Temp := MaschinenGrid
          else      Temp := StandorteGrid;
          with Temp do
          begin
               if ACol = ColCount-1 then
               begin
                    if Temp = StandorteGrid then
                       ActiveControl := MaschinenGrid
                    else
                        ActiveControl := BerechnenButton;
               end
               else begin
                    Col := ACol + 1;
                    Row := ARow;
               end;
          end;
          CanSelect := False;
     end;
end;

procedure TFormular.GridKeyPress(Sender: TObject; var Key: Char);
begin
     ChangeCheck();
     if not (Key in [#8,#13,#48..#57]) then Key:=#0
end;

procedure TFormular.StandorteGridSetEditText(Sender: TObject; ACol,
  ARow: Integer; const Value: String);
begin
     StandorteGrid.Cells[ARow,ACol] := Value
end;

procedure TFormular.BerechnenButtonClick(Sender: TObject);
begin
     if EingabeChanged = True then
     begin
          Berechnen(AnzOrte.Value);
     end
     else
          Sicht.ActivePage := ErgebnisSicht;
end;

procedure TFormular.Berechnen(N: Integer);
var Daten: TDaten;
begin
     Daten := TDaten.Init(N);
     Daten.EingabeDaten(StandorteGrid, MaschinenGrid);
     Daten.BerechneMatrix();
     Formular.Enabled := False;

     QueryPerformanceFrequency(d);
     QueryPerformanceCounter(Von);

     if EnumRadio.Checked then
        Daten.RechneEnumeration()
     else
        Daten.RechneEntscheidungsbaum();

     Formular.Enabled := True;
     Formular.ErgebnisSicht.TabVisible := True;
end;

procedure TFormular.InitErgebnisGrids(N: Integer);
var i,j,z,Breite: Integer;
    FillOne, FillTwo: Char;
    One, Two: String;
begin
     ZuordnungGrid.ColCount := N + 1;
     if (Formular.Width-40) >= (80 + (N * 41)) then
        ZuordnungGrid.Width := 80 + (N * 41)
     else
     begin
          Breite := (Formular.Width-40-80) div 41;
          Breite := 80 + (Breite * 41);
          ZuordnungGrid.Width := Breite;
     end;
     MatrixGrid.ColCount := 1 + ((N * (N-1)) div 2);
     MatrixGrid.RowCount := MatrixGrid.ColCount;
     // TODO: ...
     with MatrixGrid do
     begin
          z := 0;
          FillOne := #64;
          for i := 1 to N-1 do
          begin
               One := IntToStr(i);
               Inc(FillOne);
               FillTwo := FillOne;
               for j := i+1 to N do
               begin
                    Inc(z);
                    Two := IntToStr(j);
                    Inc(FillTwo);
                    Cells[z,0] := ' '+One+'-'+Two+'  '+Two+'-'+One;
                    Cells[0,z] := ' '+FillOne+' - '+FillTwo;
               end;
          end;
     end;
     with ZuordnungGrid do
     begin
          FillOne := #64;
          for i := 1 to N do
          begin
               Inc(FillOne);
               Cells[i,0] := ' '+FillOne;
          end;
          ColWidths[0] := 60;
          Cells[0,0] := 'Standort';
          Cells[0,1] := 'Maschine';
     end;
end;

procedure TFormular.FillErgebnisGrids(N: Integer; ZMatrix: TIntMatrix; Ergebnis: TIntArray);
var i,j: Word;
begin
     for i := 1 to MatrixGrid.ColCount-1 do
         for j := 1 to MatrixGrid.RowCount-1 do
             MatrixGrid.Cells[i,j] := IntToStr(ZMatrix.GetValue(i,j));

     for i := 1 to N do
         ZuordnungGrid.Cells[i,1] := IntToStr(Ergebnis.GetValue(i));
end;

procedure TFormular.ChangeCheck();
begin
     if EingabeChanged = False then
     begin
          SetStatusInfo('');
          StatusBar.Panels[0].Text := '';
//          ErgebnisSicht.TabVisible := False;
          EingabeChanged := True;
     end;
     if DateiOn then
        SpeichernItem.Enabled := True;
end;

procedure TFormular.BeendenItemClick(Sender: TObject);
begin
     Close;
     Free;
end;

procedure TFormular.GridExit(Sender: TObject);
var i,j: Word;
    Grid: TStringGrid;
begin
     if Sender = StandorteGrid then
        Grid := StandorteGrid
     else
        Grid := MaschinenGrid;

     // -1 hinzugefügt
     for i := 1 to Grid.ColCount -1 do
            for j := 1 to Grid.RowCount -1 do
                if Grid.Cells[i,j] = '' then
                   Grid.Cells[i,j] := '0'
end;

procedure TFormular.NeuItemClick(Sender: TObject);
var i,j,N: Word;
begin
     N := 4;
     AnzOrte.Value := N;
     InitEingabeGrids(N);

     for i:=1 to N do
         for j:=1 to N do
         begin
             if i=j then Continue;
             StandorteGrid.Cells[i,j] := '0';
             MaschinenGrid.Cells[i,j] := '0';
         end;

     MaschinenGrid.Row := 1;
     MaschinenGrid.Col := 2;
     StandorteGrid.Row := 1;
     StandorteGrid.Col := 2;
     ActiveControl := StandorteGrid;
     SpeichernItem.Enabled := False;
     DateiOn := False;
     Formular.Caption := 'Innerbetriebliche Standortplanung';
end;

procedure TFormular.GroupClick(Sender: TObject);
begin
     if Rechenart1.Checked then
        Rechenart2.Checked := True
     else
         Rechenart1.Checked := True;
     ChangeCheck();
end;

procedure TFormular.OeffnenItemClick(Sender: TObject);
var AnzahlOrte: Word;
    i,j: Word;
    Wert: Cardinal;
begin
     OeffnenDialog.InitialDir := 'C:\Methodenbank\Methoden\Kategorie A\Innerbetriebliche Standortplanung 1.1\01 Programm\Daten' ;
     if OeffnenDialog.Execute then
     begin
          Pfad := OeffnenDialog.FileName;
          try
             AFile := TFileStream.Create(Pfad, fmOpenRead);
          except
               MessageBeep(0);
               Application.MessageBox('Datei konnte nicht geöffnet werden!', 'Fehler', 52);
               Exit;
          end;

          AFile.ReadBuffer(AnzahlOrte,SizeOf(AnzahlOrte));

          AnzOrte.Value := AnzahlOrte;
          ChangeGrids(Self);

          for i := 1 to AnzahlOrte-1 do
              for j := i+1 to AnzahlOrte do
              begin
                   AFile.ReadBuffer(Wert,SizeOf(Wert));
                   StandorteGrid.Cells[j,i] := IntToStr(Wert);
                   StandorteGrid.Cells[i,j] := IntToStr(Wert);
              end;

          for i := 1 to AnzahlOrte-1 do
              for j := i+1 to AnzahlOrte do
              begin
                   AFile.ReadBuffer(Wert,SizeOf(Wert));
                   MaschinenGrid.Cells[j,i] := IntToStr(Wert);
                   AFile.ReadBuffer(Wert,SizeOf(Wert));
                   MaschinenGrid.Cells[i,j] := IntToStr(Wert);
              end;
          EingabeChanged := True;
          DateiOn := True;
          SpeichernItem.Enabled := False;
          Formular.Caption := ExtractFileName(Pfad) + ' - Standortplanung';

          AFile.Free;
     end;
end;

procedure TFormular.SpeichernUnterItemClick(Sender: TObject);
begin
     SpeichernDialog.InitialDir := Datenpfad ;
     if SpeichernDialog.Execute then
     begin
          Pfad := SpeichernDialog.Filename;
          try
             AFile := TFileStream.Create(Pfad, fmCreate);
          except
               MessageBeep(0);
               Application.MessageBox('Datei konnte nicht erzeugt werden!', 'Fehler', 52);
               Exit;
          end;
          AFile.Free;
          SpeichernItemClick(Self);
          DateiOn := True;
          Formular.Caption := ExtractFileName(Pfad) + ' - Standortplanung'
     end;
end;

procedure TFormular.SpeichernItemClick(Sender: TObject);
var i,j,AnzahlOrte: Word;
    Wert: Cardinal;
begin
     try
        AFile := TFileStream.Create(Pfad, fmOpenWrite);
     except
        MessageBeep(0);
        Application.MessageBox('Daten konnten nicht gespeichert werden!', 'Fehler', 52);
        Exit;
     end;

     AnzahlOrte := AnzOrte.Value;

     AFile.WriteBuffer(AnzahlOrte,SizeOf(AnzahlOrte));

     for i := 1 to AnzahlOrte-1 do
         for j := i+1 to AnzahlOrte do
         begin
              Wert := StrToInt(StandorteGrid.Cells[j,i]);
              AFile.WriteBuffer(Wert,SizeOf(Wert));
         end;

     for i := 1 to AnzahlOrte-1 do
         for j := i+1 to AnzahlOrte do
         begin
              Wert := StrToInt(MaschinenGrid.Cells[j,i]);
              AFile.WriteBuffer(Wert,SizeOf(Wert));
              Wert := StrToInt(MaschinenGrid.Cells[i,j]);
              AFile.WriteBuffer(Wert,SizeOf(Wert));
         end;

     SpeichernItem.Enabled := False;
     AFile.Free;
end;

procedure TFormular.MaschLeerenItemClick(Sender: TObject);
var i,j,N: Word;
begin
     N := AnzOrte.Value;
     for i:=1 to N do
         for j:=1 to N do
         begin
             if i=j then Continue;
             MaschinenGrid.Cells[i,j] := '0';
         end;
     EingabeChanged := True;
end;

procedure TFormular.StandLeerenItemClick(Sender: TObject);
var i,j,N: Word;
begin
     N := AnzOrte.Value;
     for i:=1 to N do
         for j:=1 to N do
         begin
             if i=j then Continue;
             StandorteGrid.Cells[i,j] := '0';
         end;
     EingabeChanged := True;
end;

procedure TFormular.FormClose(Sender: TObject; var StandortCloseAction: TCloseAction);
begin
     if DateiOn then
     begin
          if EingabeChanged then
             if MessageDlg('Aenderungen speichern?',mtConfirmation,[mbYes,mbNo],0) = mrYes then
                SpeichernItemClick(Self);
     end;
end;

procedure TFormular.Rechenart1Click(Sender: TObject);
begin
     EnumRadio.Checked := True;
     ChangeCheck;
end;

procedure TFormular.Rechenart2Click(Sender: TObject);
begin
     EntscheidRadio.Checked := True;
     ChangeCheck;
end;

procedure TFormular.EinstellungItemClick(Sender: TObject);
begin
     Einstellung.ShowModal;
end;

procedure TFormular.SetStatusInfo(StandortStatusText: String);
begin
     StatusBar.Font.Color := clBlue;
     StatusBar.Panels[1].Text := StandortStatusText;
end;

procedure TFormular.LeerenItemClick(Sender: TObject);
begin
     MaschLeerenItemClick(Self);
     StandLeerenItemClick(Self);
end;

procedure TFormular.InfoClick(Sender: TObject);
begin
//   Vorher:Inform.ShowModal;
//   Neue Änderung ruft die Hilfe im Web auf
     OpenDocument('Hilfe\Hilfe.html');
end;


end.


