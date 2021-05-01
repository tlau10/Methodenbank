unit IniDatei;

(* ..noch nicht fertig  *)


INTERFACE

    function ReadIni (IniDatei, Sektion, Eintrag: string): string;



IMPLEMENTATION

uses Crt, Dos;


function ReadIni (IniDatei, Sektion, Eintrag: string): string;
var
  d: text;
  Dateisatz: string;
  Sektion_gefunden, Eintrag_gefunden: boolean;
  pos01, laenge: integer;

begin
  assign(d,IniDatei);
  reset(d);

  (* Vorbelegungen *)
  Sektion_gefunden := false;
  Eintrag_gefunden := false;
  ReadIni := '';

  while not eof(d) do
    begin
      readln(d,Dateisatz);
      if copy(Dateisatz,1,1) <> ';' then      { ; ist das Kommentarzeichen }
        if Sektion_gefunden = true then
          if pos(Eintrag, Dateisatz) > 0 then
            begin
              Eintrag_gefunden := true;
              pos01 := pos('=', Dateisatz) + 1;
              laenge := length(Dateisatz) - pos01 + 1;

              ReadIni := copy(Dateisatz, pos01, laenge);
            end;

        if pos('['+Sektion+']',Dateisatz) > 0 then
          Sektion_gefunden := true;
    end;

  close(d);
end;



begin

end.
