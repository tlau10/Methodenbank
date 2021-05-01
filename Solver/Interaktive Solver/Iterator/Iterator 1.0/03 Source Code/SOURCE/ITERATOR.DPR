program Iterator;

uses
  Forms,
  Main in 'MAIN.PAS' {IteratorMain},
  Childwin in 'CHILDWIN.PAS' {IteratorChild},
  Bruch in 'BRUCH.PAS',
  Ueber in 'UEBER.PAS' {AboutBox};

{$R *.RES}

begin
  Application.Title := 'Operations Research Iterator ';
  Application.CreateForm(TIteratorMain, IteratorMain);
  Application.CreateForm(TAboutBox, AboutBox);
  Application.Run;
end.
