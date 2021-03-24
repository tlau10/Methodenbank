unit About;

interface

uses WinTypes, WinProcs, Classes, Graphics, Forms, Controls, StdCtrls,
  Buttons, ExtCtrls;

type
  TAboutBox = class(TForm)
    Panel1: TPanel;
    OKButton: TBitBtn;
    ProgramIcon: TImage;
    lblVersion: TLabel;
    Copyright: TLabel;
    Comments: TLabel;
    Label1: TLabel;
    Label2: TLabel;
    procedure OKButtonClick(Sender: TObject);
    procedure FormCreate(Sender: TObject);
    procedure FormShow(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
    procedure ShowAboutBox(Sender: TObject);
  end;

var
  AboutBox: TAboutBox;


implementation

uses Main;

{$R *.DFM}

function RGB(R,G,B:Byte): TColor;
begin
  Result:= B;
  Result:= Result Shl 8 Or G;
  Result:= Result Shl 8 Or R;
end;
  
procedure TAboutBox.ShowAboutBox(Sender: TObject);
begin
     AboutBox.Show;
     {TEST}
end;

procedure TAboutBox.OKButtonClick(Sender: TObject);
begin
     AboutBox.Close;
end;

procedure TAboutBox.FormCreate(Sender: TObject);
begin
  AboutBox.Color:= RGB(25,128,192);
  Panel1.Color:= RGB(25,192,255);
end;

procedure TAboutBox.FormShow(Sender: TObject);
begin
  Top := MainForm.Top + round(MainForm.Height/2 - Height/2) - 20;
  Left := MainForm.Left + round(MainForm.Width/2 - Width/2);

  //--
  lblVersion.Caption := 'Version ' + Main.VersionText + '.' + Main.BuildText;
end;

end.

