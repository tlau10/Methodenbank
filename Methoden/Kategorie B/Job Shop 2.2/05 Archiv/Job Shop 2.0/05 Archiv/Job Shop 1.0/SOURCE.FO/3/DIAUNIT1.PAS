unit diaUnit1;

interface

uses
  Windows, Messages, SysUtils, Classes, Graphics, Controls, Forms, Dialogs,
  StdCtrls;

type
  TForm1 = class(TForm)
    OpenDialog1: TOpenDialog;
    procedure FormCreate(Sender: TObject);
    procedure FormShow(Sender: TObject);
  private
    { Private-Deklarationen }
  public
    { Public-Deklarationen }
  end;

var
  Form1: TForm1;

implementation

{$R *.DFM}


procedure MsgBox(const TextString, Ueberschrift: string; id: integer);
var
  T_pChar : array[0..255] of char;
  U_pChar : array[0..255] of char;
begin
  strpcopy(T_pChar,TextString);
  strpcopy(U_pChar,Ueberschrift);
  application.MessageBox(T_pChar, U_pChar, id);
end;


procedure TForm1.FormCreate(Sender: TObject);
var
  Suchpfad : string;
  AktAblageDat : string;
  d : System.text;

begin
  //MsgBox(ParamStr(1), '1. Parameter', mb_OK);
  //MsgBox(ParamStr(2), '2. Parameter', mb_OK);

  {1. Parameter -> Suchpfad}
  if ParamStr(1) <> '' then
    Suchpfad := ParamStr(1)
  else
    Suchpfad := ExtractFilePath(Paramstr(0));

  {2. Parameter -> Aktuelle Ablagedatei für den gewählten Datendateinamen}
  if ParamStr(2) <> '' then
    AktAblageDat := ParamStr(2)
  else
    AktAblageDat := 'c:\temp\js_akt.dat';

  OpenDialog1.InitialDir := Suchpfad;
  OpenDialog1.Title := 'J O B S H O P';
  OpenDialog1.Filter := 'JobShop-Dateien (*.   )|*|Alle Dateien (*.*)|*.*';
  OpenDialog1.Execute;

  AssignFile(d,AktAblageDat);
  Rewrite(d);
  writeln(d,'[Dateien]');
  writeln(d,'Eingabedatei=' + OpenDialog1.Filename);
  CloseFile(d);
end;

{ wird über Ereignis TForm1.OnShow aufgerufen }
procedure TForm1.FormShow(Sender: TObject);
begin
  close;
end;


end.
