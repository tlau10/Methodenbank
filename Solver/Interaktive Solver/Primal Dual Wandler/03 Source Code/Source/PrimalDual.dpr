program PrimalDual;

uses
  Forms,
  PD_Unit1 in 'PD_Unit1.pas' {Form1};

{$R *.RES}

begin
  Application.Initialize;
  Application.CreateForm(TForm1, Form1);
  Application.Run;
end.
