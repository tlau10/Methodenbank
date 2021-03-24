program lp;

uses
  Forms,
  lp_form in 'lp_form.pas' {lpForm},
  fernform in '..\..\UNITS\Fernform.pas' {tfernform};

{$R *.RES}

begin
  Application.Initialize;
  Application.HelpFile := 'Lp.hlp';
  Application.CreateForm(TlpForm, lpForm);
  Application.CreateForm(Ttfernform, tfernform);
  Application.Run;
end.
