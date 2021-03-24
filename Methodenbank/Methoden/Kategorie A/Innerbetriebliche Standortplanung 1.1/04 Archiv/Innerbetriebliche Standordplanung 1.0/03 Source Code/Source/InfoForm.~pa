unit InfoForm;

interface

uses
  Windows, Messages, SysUtils, Classes, Graphics, Controls, Forms, Dialogs,
  StdCtrls;

type
  TInform = class(TForm)
    OKButton: TButton;
    InfoLabel: TLabel;
    procedure FormCreate(Sender: TObject);
    procedure OKButtonClick(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

var
  Inform: TInform;

implementation

{$R *.DFM}

procedure TInform.FormCreate(Sender: TObject);
begin
     InfoLabel.Caption :=
         #13 +
         #13 + '  Innerbetriebliche Standortplanung' +
         #13 +
         #13 +
         #13 + '  Author:    Marc Junghänel' +
         #13 + '  Fach:      Operation Research' +
         #13 +
         #13 + '  Datum:     04. Oktober 1999' +
         #13 + '  Version:   1.0' +
         #13 +
         #13 + '  eM@il:     marc.junghaenel@gmx.de' +
         #13 +
         #13;
     InfoLabel.Width := 260;
end;

procedure TInform.OKButtonClick(Sender: TObject);
begin
     Hide;
end;

end.
