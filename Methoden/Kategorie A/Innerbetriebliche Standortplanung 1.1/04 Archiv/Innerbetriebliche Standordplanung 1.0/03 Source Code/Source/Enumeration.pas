unit Enumeration;

(*
     Enumerationsverfahren - Unit.

     Unter Verwendung eines Threads wird die Berechnung durchgefuehrt
     und mit Hilfe einer Fortschrittsanzeige wird dem Anwender der
     aktuelle Verlauf der Berechung angezeigt.

     Diese Fortschrittsanzeige befindet sich in der Unit Status.

*)

interface

uses Classes, Forms, Dialogs, SysUtils, Windows,
     Matrix, Feld;

type
    TEnum = class(TThread)
     AnzOrte, AnzPaare: Word;
     AnzBerechnungen: Int64;
     Erfolgreich: Boolean;                  // Berechnung erfolgreich
     Optimum: TIntArray;                    // Optimales Ergebnis
     ZMatrix: TIntMatrix;                   // Zuordnungsmatrix
     procedure Execute; override;
     function  Rechne(Feld: TIntArray): Cardinal;
     function  Fakult(Zahl: Word): Int64;
     procedure Ausgabe();
    public
     constructor Create(Matrix: TIntMatrix; Orte: Word);
    end;

implementation

uses Standort, Status;

constructor TEnum.Create(Matrix: TIntMatrix; Orte: Word);
(*
     Erzeugen des Enumerationsverfahren

     Uebergabe:   Zuordnungsmatrix
                  Anzahl der Standorte
*)
var i,j: Word;
begin
     // Constructor und Eigenschaften des Threads
     inherited Create(True);
     FreeOnTerminate := True;

     // Initialisierung der Variablen
     Erfolgreich := False;
     AnzOrte := Orte;
     AnzBerechnungen := Fakult(AnzOrte);
     AnzPaare := (AnzOrte*(AnzOrte-1)) div 2;
     Optimum := TIntArray.Dim(AnzOrte+1);

     // Zuordnungsmatrix kopieren
     ZMatrix := TIntMatrix.Dim(Matrix.xDim,Matrix.yDim);
     for i := 1 to Matrix.xDim do
         for j:= 1 to Matrix.yDim do
             ZMatrix.SetValue(i,j,Matrix.GetValue(i,j));

     // Fortschrittsanzeige erzeugen und initialisieren
     if Anzeige = nil then Anzeige := TAnzeige.Create(Application);
     Anzeige.FormZentrieren(Formular);
     Anzeige.InitStatus(True,AnzBerechnungen,Self);
end;

procedure TEnum.Execute;
(*
     Hauptmethode des Threads (wird ueberschrieben)

     Wird bei Aufruf von ... Create(True) aufgerufen

*)
var i,j,y: Word;
    Lauf: Int64;
    TAW: Cardinal;
    Ende: Boolean;
    Fall: TIntArray;         // Permutationsfall
begin
     // Initialisierung der privaten Variablen
     Lauf := 0;
     Ende := False;
     Fall := TIntArray.Dim(AnzOrte);
     for y := 1 to AnzOrte do
         Fall.SetValue(y,y);

     // Setzt optimales Ergebnis auf hoechstmoeglichem Wert
     Optimum.SetValue(Optimum.xDim,High(Cardinal));

     // Synchronisierte Anzeige der Fortschrittsanzeige
     synchronize(Anzeige.Show);

     // Permutation der Zuordnungsfaelle
     while (not Ende) do
     begin
          // Vergleiche Transportaufwandswert mit Optimum
          TAW := Rechne(Fall);
          Inc(Lauf);
          if Optimum.GetValue(Optimum.xDim) > TAW then
          begin
               for y := 1 to AnzOrte do
                   Optimum.SetValue(y,Fall.GetValue(y));
               Optimum.SetValue(Optimum.xDim,TAW);
          end;

          // Bei Unterbrechung des Threads, Ende einleiten
          if Terminated then break;

          // Fortschrittsanzeige aktualisieren
          Anzeige.UpdateStatus(Lauf);

          // Permutationsverfahren
          i := AnzOrte-1;
          while (Fall.GetValue(i) > Fall.GetValue(i+1)) do
               Dec(i);

          if i = 0 then
          begin
               Erfolgreich := True;
               break;
          end;

          j := AnzOrte;
          while (Fall.GetValue(j) < Fall.GetValue(i)) do
                Dec(j);

          Fall.Switch(i,j);

          Inc(i);
          j := AnzOrte;
          while (i<j) do
          begin
               Fall.Switch(i,j);
               Inc(i); Dec(j);
          end;
     end;

     QueryPerformanceCounter(Formular.Bis);

     // Wenn Berechung erfolgreich, Ausgabe des Ergebnisses
     if Erfolgreich then
     begin
          Anzeige.AbbruchButton.Caption := 'Fertig';
          Anzeige.AbbruchButton.Cancel := False;
          Anzeige.AbbruchButton.Default := True;
          MessageBeep(0);
          Ausgabe();
          Formular.SetStatusInfo('Optimales Ergebnis mit einem TAW von '+
                                            IntToStr(Optimum.GetValue(Optimum.xDim)));
     end
     else
     if Formular.ErgebnisEnabled then
     begin
          Anzeige.AbbruchButton.Caption := 'Fertig';
          Anzeige.AbbruchButton.Cancel := False;
          Anzeige.AbbruchButton.Default := True;
          Ausgabe();
          Formular.EingabeChanged := True;
          Formular.SetStatusInfo('Bisher bestes Ergebnis mit einem TAW von '+
                                  IntToStr(Optimum.GetValue(Optimum.xDim)));
     end
     else
         Formular.SetStatusInfo('Berechnungsvorgang wurde abgebrochen');
end;

function TEnum.Rechne(Feld: TIntArray): Cardinal;
(*
     Funktion zum Berechnen des TAWs eines Falls

     Uebergabe:   Feld mit einer Permutation
     Rueckgabe:   Transportaufwandswert dieses Falls

*)
var TAW: Cardinal;
    temp,temp2,i,j,y,z: Word;
    x1, x2: Word;               // x1 = Zeile, x2 = Spalte
    Abbruch: Boolean;
begin
     TAW := 0;
     x1 := 0;                                  // Zaehlt die aktuelle Zeile
     for i := 1 to AnzOrte-1 do
          for j := i+1 to AnzOrte do
          begin
               Inc(x1);
               temp := Feld.GetValue(i);
               temp2 := Feld.GetValue(j);
               x2 := 0;                        // Zaehlt die aktuelle Spalte
               Abbruch := False;
               for y := 1 to AnzOrte-1 do
               begin
                   for z := y+1 to AnzOrte do
                   begin
                        Inc(x2);
                        // Wenn richtige Spalte der Zeile erreicht
                        // Berechnung durchfuehren
                        if (((temp = y) and (temp2 = z)) or
                           ((temp2 = y) and (temp = z))) then
                        begin
                             TAW := TAW + ZMatrix.GetValue(x2,x1);
                             Abbruch := True;
                             break;
                        end;
                   end;
                   if Abbruch then break;
               end;
          end;
     // Rueckagbe des Ergebnisses
     result := TAW;
end;

function TEnum.Fakult(Zahl: Word): Int64;
(*
     Funktion zur Berechnung der Fakultaet

     Uebergabe:   Zu fakultierender Wert
     Rueckgabe:   Fakultaetsergebnis

*)
var Ergebnis: Int64;
    i: Word;
begin
     Ergebnis := 1;
     for i := 2 to Zahl do
          Ergebnis := i * Ergebnis;
     result := Ergebnis;
end;

procedure TEnum.Ausgabe();
(*
     Methode zur Ausgabe des Ergebnisses

*)
begin
     // Fortschrittsanzeige schliessen
     Formular.EingabeChanged := False;
     Formular.ErgebnisSicht.TabVisible := True;

     // Ergebnisanzeige initialisieren und fuellen
     Formular.InitErgebnisGrids(AnzOrte);
     Formular.FillErgebnisGrids(AnzOrte,ZMatrix,Optimum);
end;

end.
