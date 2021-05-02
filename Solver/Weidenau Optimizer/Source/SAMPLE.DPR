program sample;

uses
  Forms,
  sample_U in 'sample_U.pas' {Form1},
  lp_form in 'lp_form.pas' {lpForm};

{$R *.RES}

begin
  Application.Initialize;
  Application.CreateForm( TForm1, Form1 );
  Application.CreateForm(TlpForm, lpForm);
  Application.Run;
end.
