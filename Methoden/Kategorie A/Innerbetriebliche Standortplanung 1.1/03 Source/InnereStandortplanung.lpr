program InnereStandortplanung;

{$mode objfpc}{$H+}

uses
  {$IFDEF UNIX}{$IFDEF UseCThreads}
  cthreads,
  {$ENDIF}{$ENDIF}
  Interfaces, // this includes the LCL widgetset
  { you can add units after this }
  Forms,
  Standort in 'Standort.pas' {Formular},
  Berechnung in 'Berechnung.pas',
  Matrix in 'Matrix.pas',
  Feld in 'Feld.pas',
  Enumeration in 'Enumeration.pas',
  Status in 'Status.pas' {Anzeige},
  Entscheidungsbaum in 'Entscheidungsbaum.pas',
  Transportmodell in 'Transportmodell.pas',
  InitForm in 'InitForm.pas' {Titel},
  Einstellungen in 'Einstellungen.pas' {Einstellung};
  //InfoForm in 'InfoForm.pas' {Inform};

  
{$R *.res}

begin
  Application.Initialize;
  Titel := TTitel.Create(Application);
  Titel.Show;
  Titel.Update;
  Application.CreateForm(TFormular, Formular);
  Application.CreateForm(TTitel, Titel);
  Application.CreateForm(TEinstellung, Einstellung);
 // Application.CreateForm(TInform, Inform);
  Application.Run;
end.
