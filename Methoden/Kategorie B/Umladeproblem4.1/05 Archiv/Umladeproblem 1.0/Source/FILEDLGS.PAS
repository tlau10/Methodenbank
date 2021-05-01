unit FileDlgs;

{  FileDialogs

   Unit fuer Dateidialoge:  Oeffnen und Speichern von Dateien

   Compiler: Turbo Pascal 6.0
   Autor:    Wolfgang Ott    (otw@gmx.de)
   Stand:    21.01.2000

   was fehlt noch:
   - keine Unterscheidung zw. Dateien u. Verzeichnissen
   - kein dynamischer Anzeigebereich
   - SPEICHERN

}

INTERFACE

  function OpenDialog(Title, InitialDir, Extention: string): string;


IMPLEMENTATION

uses
  {$IFDEF MSDOS}
    crt, dos ;       (* mit neuer Laufzeitfehler 200-stabilen CRT *)
  {$ELSE}
    Crt32, Windows,
    SysUtils;
  {$ENDIF}


procedure RAHMEN (x1,y1,x2,y2 : byte; r : char);
var
  i : byte;
begin
     if r = 'e' then           { einfacher rahmen }
     begin
        gotoxy(x1,y1); for i := x1 to x2 do  write(#196);
        gotoxy(x1,y2); for i := x1 to x2 do  write(#196);
        for i := y1 to y2 do  begin gotoxy(x1,i); write(#179) end;
        for i := y1 to y2 do  begin gotoxy(x2,i); write(#179) end;
        gotoxy(x1,y1); write(#218);
        gotoxy(x2,y1); write(#191);
        gotoxy(x2,y2); write(#217);
        gotoxy(x1,y2); write(#192);
     end;
     if r = 'd' then           { doppelter rahmen }
     begin
        gotoxy(x1,y1); for i := x1 to x2 do  write(#205);
        gotoxy(x1,y2); for i := x1 to x2 do  write(#205);
        for i := y1 to y2 do  begin gotoxy(x1,i); write(#186) end;
        for i := y1 to y2 do  begin gotoxy(x2,i); write(#186) end;
        gotoxy(x1,y1); write(#201);
        gotoxy(x2,y1); write(#187);
        gotoxy(x2,y2); write(#188);
        gotoxy(x1,y2); write(#200);
     end;
     gotoxy(x1,y1);
end;



function OpenDialog(Title, InitialDir, Extention: string): string;
const
  Textfarbe   = lightgray;
  Hintergrund = blue;
  MaxEintraege = 100;

var
  DateiArray    : array[1..MaxEintraege] of string[12];
  AktVerz,
  Suchpfad,
  Erweiterung   : string;
  Zaehler, Wahl : byte;
  WahlStr       : string;
  Fehlercode    : integer;

  {$IFDEF MSDOS}
     Ergebnis : SearchRec;
  {$ELSE}
     Ergebnis : TSearchRec;
     AnyFile  : integer;
     DosError : integer;
     Ch       : Char;
     //Fehlercode : integer;
  {$ENDIF}


  {-- inline-Prozedur }
  procedure Dateien_Suchen;
  begin
    (*-- Verzeichniswechsel + Pruefung auf Existenz *)
    {$I-}
    ChDir(Suchpfad);

    if IOResult <> 0 then
    begin
      ChDir(AktVerz);
      gotoxy(03,17);
      write('Verzeichnis existiert nicht !!!   ');
      sound(700); delay(400); nosound;
      delay(800);
      exit;
    end;
    {$I+}

    {$IFDEF MSDOS}
       FindFirst(Erweiterung, AnyFile, Ergebnis);
    {$ELSE}
       DosError := FindFirst(Erweiterung, AnyFile, Ergebnis);
    {$ENDIF}

    Zaehler := 0;

    while (DosError = 0) and (Zaehler <= MaxEintraege) do
    begin
      if (Ergebnis.name <> '.') and (Ergebnis.name <> '..') then
      begin
        inc(Zaehler);
        DateiArray[zaehler] := Ergebnis.name;
      end;
      {$IFDEF MSDOS}
         FindNext(Ergebnis);
      {$ELSE}
         DosError := FindNext(Ergebnis);
      {$ENDIF}
    end;

    {$IFDEF WINDOWS}
       FindClose(Ergebnis);
    {$ENDIF}
  end;


  {-- inline-Prozedur }
  procedure Dateien_Anzeigen;
  var
    Spalte, Zeile,
    i, j            : byte;
  begin
    Spalte:= 4;
    Zeile:=  4;
    j:= 1;

  (*
    for i:= 1 to Zaehler do
    begin
      if Zaehler > 24 then i := Zaehler;

      if j > 12 then
      begin
        j := 1;
        Zeile := 3;
        Spalte := Spalte + 19;
      end;

      gotoxy(Spalte,Zeile);
      textcolor(yellow);
      write(i:3);

      gotoxy(Spalte + 4,Zeile);
      textcolor(white);
      write(DateiArray[i]);

      inc(Zeile);
      inc(j);
    end;
   *)

    (*-- Anzeige des Suchpfades inkl. Erweiterung *)
    gotoxy(4,2);
    write(Suchpfad + '\' + Erweiterung);

    i := 1;
    while (i <= Zaehler) and (i < 24) do
    begin
      if j > 12 then
      begin
        j := 1;
        Zeile := 4;
        Spalte := Spalte + 19;
      end;

      gotoxy(Spalte,Zeile);
      textcolor(yellow);
      write(i:3);

      gotoxy(Spalte + 4,Zeile);
      textcolor(white);
      write(DateiArray[i]);

      inc(Zeile);
      inc(j);
      inc(i);
    end;
  end;

begin  { }
  Suchpfad := InitialDir;
  Erweiterung := Extention;

  {-- Aktuellen Pfad sichern }
  GetDir(0,AktVerz);

  repeat

    {-- Dialogfenster }
    window (10,03,53,21);
    textbackground (Hintergrund);
    textcolor (Textfarbe);
    ClrScr;

    Rahmen (1,1,43,19,'d');

    gotoxy(18,01);
    write(Title);

    Rahmen (3,3,41,16,'e');

    {  }

    Dateien_Suchen;
    Dateien_Anzeigen;

    gotoxy(03,17);
    write('Geben Sie bitte die Dateinummer ein:');

    gotoxy(03,18);
    textbackground(lightgray);
    textcolor(yellow);
    write(' 0');
    textcolor(white);
    write(' -> Abbruch  ');

    textcolor(yellow);
    write(' p');
    textcolor(white);
    write(' -> Pfad Ñndern ');


    {-- Abfrage der Auswahl }
    textbackground(Hintergrund);
    textcolor(yellow);

    gotoxy(40,17);

    {$IFDEF MSDOS}
       readln(WahlStr);
    {$ELSE}
       Ch:= readkey;
       write(Ch);          //showmessage(ch);
       delay(100);
       val(Ch, Wahl, FehlerCode);
    {$ENDIF}

    if WahlStr = 'p' then
      WahlStr := 'P';

    if WahlStr = 'P' then
    begin
      (*-- Pfad aendern *)
      Zaehler := 0;
      gotoxy(03,17);  write('Neuer Pfad (0 = Abbruch):              ');
      gotoxy(03,18);  write('                                       ');
      gotoxy(03,18);  readln(Suchpfad);

      if Suchpfad = '0' then
      begin
        WahlStr := 'N';  (* -> fÅhrt zum Schleifenabbruch *)
        Wahl := 0;
      end;
    end
    else
    begin
       val(WahlStr, Wahl, FehlerCode);
       if Fehlercode <> 0 then
         Wahl := 0;
    end;

  until WahlStr <> 'P';


  if (Wahl > 0) and (Wahl <= Zaehler) then
    OpenDialog := Suchpfad + '\' + DateiArray[Wahl]
  else
    OpenDialog := '';

  ChDir(AktVerz);

  window (01,01, 80,25);

  (*
  TextBackGround(AltHintergrund);
  TextColor(AltTextfarbe);
  ClrScr;
  *)

end;

end.
