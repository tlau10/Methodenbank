unit Einstellungen;

interface

uses
  Windows, Messages, SysUtils, Classes, Graphics, Controls, Forms, Dialogs,
  StdCtrls;

type
  TEinstellung = class(TForm)
    MaxStandLabel: TLabel;
    MaxStandorte: TEdit;
    OKButton: TButton;
    DetailBox: TCheckBox;
    ErgebnisBox: TCheckBox;
    AbbrechenButton: TButton;
    procedure MaxStandorteKeyPress(Sender: TObject; var Key: Char);
    procedure OKButtonClick(Sender: TObject);
    procedure MaxStandorteChange(Sender: TObject);
    procedure AbbrechenButtonClick(Sender: TObject);
    procedure FormShow(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

var
  Einstellung: TEinstellung;

implementation

uses Standort;
{$R *.DFM}

procedure TEinstellung.MaxStandorteKeyPress(Sender: TObject;
  var Key: Char);
begin
     if not (Key in [#8,#13,#48..#57]) then Key:=#0
end;

procedure TEinstellung.OKButtonClick(Sender: TObject);
begin
     Einstellung.Hide;


     if Formular.AnzOrte.MaxValue >= 4 then
     begin
          Formular.AnzOrte.MaxValue := StrToInt(MaxStandorte.Text);
          if Formular.AnzOrte.Value > Formular.AnzOrte.MaxValue then
             Formular.AnzOrte.Value := Formular.AnzOrte.MaxValue;
     end;
     if DetailBox.Checked then
        Formular.DetailEnabled := True
     else
        Formular.DetailEnabled := False;
     if ErgebnisBox.Checked then
        Formular.ErgebnisEnabled := True
     else
        Formular.ErgebnisEnabled := False;
end;

procedure TEinstellung.MaxStandorteChange(Sender: TObject);
begin
     if MaxStandorte.Text = '' then MaxStandorte.Text := '0';
     if StrToInt(MaxStandorte.Text) > 50 then
        MaxStandorte.Text := '50';

end;

procedure TEinstellung.AbbrechenButtonClick(Sender: TObject);
begin
     Hide;
end;

procedure TEinstellung.FormShow(Sender: TObject);
begin
     DetailBox.Enabled:=false;
     MaxStandorte.Text := IntToStr(Formular.AnzOrte.MaxValue);
     if Formular.DetailEnabled then
        DetailBox.Checked := True
     else
        DetailBox.Checked := False;

     if Formular.ErgebnisEnabled then
        ErgebnisBox.Checked := True
     else
        ErgebnisBox.Checked := False;
     ActiveControl := MaxStandorte;
end;

end.
