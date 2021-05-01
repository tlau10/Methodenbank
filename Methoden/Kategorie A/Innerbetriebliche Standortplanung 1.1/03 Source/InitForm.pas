unit InitForm;

interface

uses
  Windows, Messages, SysUtils, Classes, Graphics, Controls, Forms, Dialogs,
  ExtCtrls;

type
  TTitel = class(TForm)
    TitelImage: TImage;
    TitelTimer: TTimer;
    procedure TitelTimerTimer(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

var
  Titel: TTitel;

implementation

uses Standort;

{$R *.DFM}

procedure TTitel.TitelTimerTimer(Sender: TObject);
begin
     Formular.Enabled := True;
     Hide;
     Formular.Sicht.Visible := True;
     Formular.ActiveControl := Formular.StandorteGrid;
     Free;
end;

end.
