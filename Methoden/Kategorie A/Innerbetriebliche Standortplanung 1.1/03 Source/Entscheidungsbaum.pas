unit Entscheidungsbaum;

interface

uses Classes, Forms, Dialogs, SysUtils, Windows,
     Matrix, Feld, Transportmodell;

type
    TDakin = class(TThread)
     AnzOrte, AnzPaare: Word;
     AnzBerechnungen: Int64;
     Erfolgreich: Boolean;
     MAX: Cardinal;
     Optimum,
     PruefErgebnis: TIntArray;
     ZMatrix,MMatrix: TIntMatrix;
     procedure Execute(); override;
    private
     function RechneDakin(Fall: Loesung): Loesung;
     function RechneMitModell(x,y: Word; Mit: TIntMatrix): Loesung;
     function PruefeZulaessigkeit(Fall: Loesung): Boolean;
     function Fakult(Zahl: Word): Int64;
    public
     constructor Create(Matrix: TIntMatrix; Orte: Word);
     procedure Ausgabe(); overload;
     procedure Ausgabe(DMatrix: TIntMatrix; DLoesung: Loesung); overload;
    end;

implementation

uses Standort, Status;

constructor TDakin.Create(Matrix: TIntMatrix; Orte: Word);
var i,j: Word;
begin
     inherited Create(True);
     FreeOnTerminate := True;
     Erfolgreich := False;
     AnzOrte := Orte;
     AnzBerechnungen := Fakult(AnzOrte);
     AnzPaare := (AnzOrte*(AnzOrte-1)) div 2;
     MAX := High(Max);

     ZMatrix := TIntMatrix.Dim(Matrix.xDim,Matrix.yDim);
     MMatrix := TIntMatrix.Dim(Matrix.xDim,Matrix.yDim);
     for i := 1 to Matrix.xDim do
         for j:= 1 to Matrix.yDim do
         begin
              ZMatrix.SetValue(i,j,Matrix.GetValue(i,j));
              MMatrix.SetValue(i,j,Matrix.GetValue(i,j));
         end;

     Optimum := TIntArray.Dim(AnzOrte);
     PruefErgebnis := TIntArray.Dim(AnzOrte);

     if Anzeige = nil then Anzeige := TAnzeige.Create(Application);
     Anzeige.FormZentrieren(Formular);
     Anzeige.InitStatus(False,AnzBerechnungen,Self);
end;

procedure TDakin.Execute();
var Modell: TTransport;
    ModellLoesung: Loesung;
    AnfangsloesungZulaessig: Boolean;
    i: Word;
begin
     TThread.Synchronize(Self, @Anzeige.Show);

     Modell := TTransport.Create(AnzOrte, ZMatrix);
     ModellLoesung := Modell.Rechnen();
     AnfangsloesungZulaessig := PruefeZulaessigkeit(ModellLoesung);

     if AnfangsloesungZulaessig then
     begin
          for i := 1 to AnzOrte do
              Optimum.SetValue(i,PruefErgebnis.GetValue(i));
          Erfolgreich := True;
          Anzeige.UpdateStatus(0);
     end
     else
     begin
          if Formular.DetailEnabled then
          begin
               Formular.SetStatusInfo('Unzulässiges optimales Anfangsergebnis mit einem TAW von '
                                       +IntToStr(ModellLoesung.TAW));
               Ausgabe(ZMatrix,ModellLoesung);
               ShowMessage('Weiter');
               Anzeige.Show;
          end;

          // Entscheidungsbaum durchlaufen
          ModellLoesung := RechneDakin(ModellLoesung);

          // Optimales Ergebnis nochmals pruefen und in Optimum speichern
          if PruefeZulaessigkeit(ModellLoesung) then
             for i := 1 to AnzOrte do
                 Optimum.SetValue(i,PruefErgebnis.GetValue(i));
     end;

     QueryPerformanceCounter(Formular.Bis);

     if Erfolgreich then
     begin
          Anzeige.AbbruchButton.Caption := 'Fertig';
          Anzeige.AbbruchButton.Cancel := False;
          Anzeige.AbbruchButton.Default := True;
          MessageBeep(0);
          // Sicherstellen, dass die Anzeige bei erfolgreichem Abschluss auf 100% synchronisiert wird.
          Anzeige.UpdateStatus(Anzeige.MaxStatusValue);

          Ausgabe();
          Formular.SetStatusInfo('Optimales Ergebnis mit einem TAW von '+
                                            IntToStr(ModellLoesung.TAW));
     end
     else
     if Formular.ErgebnisEnabled then
     begin
          if PruefeZulaessigkeit(ModellLoesung) then
          begin
               Anzeige.AbbruchButton.Caption := 'Fertig';
               Anzeige.AbbruchButton.Cancel := False;
               Anzeige.AbbruchButton.Default := True;
               Ausgabe();
               Formular.EingabeChanged := True;
               Formular.SetStatusInfo('Bisher bestes Ergebnis mit einem TAW von '+
                                            IntToStr(ModellLoesung.TAW));
          end
          else
          begin
               Anzeige.AbbruchButton.Caption := 'Fertig';
               Anzeige.AbbruchButton.Cancel := False;
               Anzeige.AbbruchButton.Default := True;
               Formular.SetStatusInfo('Leider konnte noch kein zulässiges Ergebnis gefunden werden');
          end;
     end
     else
         Formular.SetStatusInfo('Berechnungsvorgang wurde abgebrochen');
end;

function TDakin.RechneDakin(Fall: Loesung): Loesung;
var Modell2: TTransport;
    i,j,z,
    x,y,x_old,y_old: Word;
    Min: Cardinal;
    Anzahl,AnzahlOpt: Int64;
    Abbruch: Boolean;
    AktLoesung, OptimalLoesung, Now,
    OhneLoesung,MitLoesung: Loesung;
    OhneMatrix,MitMatrix: TIntMatrix;
    OhneZulaessig,MitZulaessig: Boolean;
begin
     Modell2 := TTransport.Create(AnzOrte, ZMatrix);

     OhneMatrix := TIntMatrix.Dim(AnzPaare,AnzPaare);
     MitMatrix := TIntMatrix.Dim(AnzPaare,AnzPaare);
     SetLength(AktLoesung.Punkte,AnzPaare+1);
     SetLength(OptimalLoesung.Punkte,AnzPaare+1);
     SetLength(OhneLoesung.Punkte,AnzPaare+1);
     SetLength(MitLoesung.Punkte,AnzPaare+1);

     OptimalLoesung.TAW := High(OptimalLoesung.TAW);
     AktLoesung.TAW := Fall.TAW;
     for i := 1 to AnzPaare do
     begin
          OptimalLoesung.Punkte[i].x := Fall.Punkte[i].x;
          OptimalLoesung.Punkte[i].y := Fall.Punkte[i].y;
          AktLoesung.Punkte[i].x := Fall.Punkte[i].x;
          AktLoesung.Punkte[i].y := Fall.Punkte[i].y;
     end;

     AnzahlOpt := 0;
     Abbruch := False;
     x := 0;
     y := 0;
     repeat
           // Suchen des zu betrachtenden Entscheidungsindexes
           x_old := x;
           y_old := y;
           Min := MAX;
           for z := 1 to AnzPaare do
           begin
                i := AktLoesung.Punkte[z].x;
                j := AktLoesung.Punkte[z].y;
                if (MMatrix.GetValue(i,j) <= Min) and
                   ((i <> x_old) and (j <> y_old)) then
                begin
                     Min := MMatrix.GetValue(i,j);
                     x := i;
                     y := j;
                end;
           end;

           // Berechnung OHNE Entscheidungsindex
           for i := 1 to AnzPaare do
               for j := 1 to AnzPaare do
                   OhneMatrix.SetValue(i,j,MMatrix.GetValue(i,j));

           OhneMatrix.SetValue(x,y,MAX);

           Modell2.ReInit(OhneMatrix);
           Now := Modell2.Rechnen();

           OhneLoesung.TAW := Now.TAW;
           for i := 1 to AnzPaare do
           begin
               OhneLoesung.Punkte[i].x := Now.Punkte[i].x;
               OhneLoesung.Punkte[i].y := Now.Punkte[i].y;
           end;

           if Terminated then break;

           // Berechnung MIT Entscheidungsindex
           for i := 1 to AnzPaare do
               for j := 1 to AnzPaare do
                   MitMatrix.SetValue(i,j,MMatrix.GetValue(i,j));

           // MitMatrix konstruieren und rechnen
           Now := RechneMitModell(x,y,MitMatrix);

           MitLoesung.TAW := Now.TAW;
           for i := 1 to AnzPaare do
           begin
               MitLoesung.Punkte[i].x := Now.Punkte[i].x;
               MitLoesung.Punkte[i].y := Now.Punkte[i].y;
           end;

           if Terminated then break;

           // Zulaessigkeitspruefungen
           OhneZulaessig := PruefeZulaessigkeit(OhneLoesung);
           MitZulaessig := PruefeZulaessigkeit(MitLoesung);

           if MitLoesung.TAW > OhneLoesung.TAW then
           begin
                if OhneZulaessig then
                begin
                     if OhneLoesung.TAW < OptimalLoesung.TAW then
                     begin
                          OptimalLoesung.TAW := OhneLoesung.TAW;
                          AktLoesung.TAW := OhneLoesung.TAW;
                          for i := 1 to AnzPaare do
                          begin
                               OptimalLoesung.Punkte[i].x := OhneLoesung.Punkte[i].x;
                               OptimalLoesung.Punkte[i].y := OhneLoesung.Punkte[i].y;
                               AktLoesung.Punkte[i].x := OhneLoesung.Punkte[i].x;
                               AktLoesung.Punkte[i].y := OhneLoesung.Punkte[i].y;
                          end;
                          for i := 1 to AnzPaare do
                              for j:= 1 to AnzPaare do
                                  MMatrix.SetValue(i,j,OhneMatrix.GetValue(i,j));
                     end
                     else Abbruch := True;
                end
                else      (*      MitLoesung.TAW > OhneLoesung.TAW
                                  OhneZulaessig = False                     *)
                begin
                     if MitZulaessig then
                     begin
                          if MitLoesung.TAW < OptimalLoesung.TAW then
                          begin
                               OptimalLoesung.TAW := MitLoesung.TAW;
                               AktLoesung.TAW := OhneLoesung.TAW;
                               for i := 1 to AnzPaare do
                               begin
                                    OptimalLoesung.Punkte[i].x := MitLoesung.Punkte[i].x;
                                    OptimalLoesung.Punkte[i].y := MitLoesung.Punkte[i].y;
                                    AktLoesung.Punkte[i].x := OhneLoesung.Punkte[i].x;
                                    AktLoesung.Punkte[i].y := OhneLoesung.Punkte[i].y;
                               end;
                               AnzahlOpt := AnzahlOpt + (2 * (AnzPaare-AnzOrte));
                               for i := 1 to AnzPaare do
                                   for j:= 1 to AnzPaare do
                                       MMatrix.SetValue(i,j,OhneMatrix.GetValue(i,j));
                          end
                          else if OhneLoesung.TAW < OptimalLoesung.TAW then
                          begin
                               AktLoesung.TAW := OhneLoesung.TAW;
                               for i := 1 to AnzPaare do
                               begin
                                    AktLoesung.Punkte[i].x := OhneLoesung.Punkte[i].x;
                                    AktLoesung.Punkte[i].y := OhneLoesung.Punkte[i].y;
                               end;
                               for i := 1 to AnzPaare do
                                   for j:= 1 to AnzPaare do
                                       MMatrix.SetValue(i,j,OhneMatrix.GetValue(i,j));
                          end
                          else Abbruch := True;
                     end
                     else      (*      MitLoesung.TAW > OhneLoesung.TAW
                                       OhneZulaessig = False, MitZulaessig = False *)
                     begin
                          if OhneLoesung.TAW < OptimalLoesung.TAW then
                          begin
                               AktLoesung.TAW := OhneLoesung.TAW;
                               for i := 1 to AnzPaare do
                               begin
                                    AktLoesung.Punkte[i].x := OhneLoesung.Punkte[i].x;
                                    AktLoesung.Punkte[i].y := OhneLoesung.Punkte[i].y;
                               end;
                               for i := 1 to AnzPaare do
                                   for j:= 1 to AnzPaare do
                                        MMatrix.SetValue(i,j,OhneMatrix.GetValue(i,j));
                          end
                          else Abbruch := True;
                     end;
                end;
           end
           else     (*       MitLoesung.TAW <= OhneLoesung.TAW
                                                                                    *)
           begin
                if MitZulaessig then
                begin
                     if MitLoesung.TAW < OptimalLoesung.TAW then
                     begin
                          OptimalLoesung.TAW := MitLoesung.TAW;
                          AktLoesung.TAW := MitLoesung.TAW;
                          for i := 1 to AnzPaare do
                          begin
                               OptimalLoesung.Punkte[i].x := MitLoesung.Punkte[i].x;
                               OptimalLoesung.Punkte[i].y := MitLoesung.Punkte[i].y;
                               AktLoesung.Punkte[i].x := MitLoesung.Punkte[i].x;
                               AktLoesung.Punkte[i].y := MitLoesung.Punkte[i].y;
                          end;
                          for i := 1 to AnzPaare do
                              for j:= 1 to AnzPaare do
                                  MMatrix.SetValue(i,j,MitMatrix.GetValue(i,j));
                     end
                     else Abbruch := True;
                end
                else      (*      MitLoesung.TAW <= OhneLoesung.TAW
                                  MitZulaessig = False                         *)
                if OhneZulaessig then
                begin
                     if OhneLoesung.TAW < OptimalLoesung.TAW then
                     begin
                          OptimalLoesung.TAW := OhneLoesung.TAW;
                          AktLoesung.TAW := MitLoesung.TAW;
                          for i := 1 to AnzPaare do
                          begin
                               OptimalLoesung.Punkte[i].x := OhneLoesung.Punkte[i].x;
                               OptimalLoesung.Punkte[i].y := OhneLoesung.Punkte[i].y;
                               AktLoesung.Punkte[i].x := MitLoesung.Punkte[i].x;
                               AktLoesung.Punkte[i].y := MitLoesung.Punkte[i].y;
                          end;
                          AnzahlOpt := AnzahlOpt + (2 * (AnzPaare-AnzOrte));
                          for i := 1 to AnzPaare do
                              for j:= 1 to AnzPaare do
                                  MMatrix.SetValue(i,j,MitMatrix.GetValue(i,j));
                     end
                     else if MitLoesung.TAW < OptimalLoesung.TAW then
                     begin
                          AktLoesung.TAW := MitLoesung.TAW;
                          for i := 1 to AnzPaare do
                          begin
                               AktLoesung.Punkte[i].x := MitLoesung.Punkte[i].x;
                               AktLoesung.Punkte[i].y := MitLoesung.Punkte[i].y;
                          end;
                          for i := 1 to AnzPaare do
                              for j:= 1 to AnzPaare do
                                  MMatrix.SetValue(i,j,MitMatrix.GetValue(i,j));
                     end
                     else Abbruch := True;
                end
                else      (*      MitLoesung.TAW <= OhneLoesung.TAW
                                  MitZulaessig = False, OhneZulaessig = False  *)
                begin
                     if MitLoesung.TAW < OptimalLoesung.TAW then
                     begin
                          AktLoesung.TAW := MitLoesung.TAW;
                          for i := 1 to AnzPaare do
                          begin
                               AktLoesung.Punkte[i].x := MitLoesung.Punkte[i].x;
                               AktLoesung.Punkte[i].y := MitLoesung.Punkte[i].y;
                          end;
                          for i := 1 to AnzPaare do
                              for j:= 1 to AnzPaare do
                                  MMatrix.SetValue(i,j,MitMatrix.GetValue(i,j));
                     end
                     else Abbruch := True;
                end;
           end;

           Anzahl := 0;
           for i := 1 to AnzPaare do
               for j := 1 to AnzPaare do
                   if MMatrix.GetValue(i,j) = MAX then Inc(Anzahl);
           Anzahl := Anzahl * (2 * (AnzPaare-AnzOrte));
           Anzahl := Anzahl + AnzahlOpt;
           Anzeige.UpdateStatus(Anzahl);

           if Anzahl > Anzeige.MaxStatusValue then Abbruch := True;
           if Abbruch then Erfolgreich := True;
           if Terminated then break;

     until (Abbruch);

     result := OptimalLoesung;
end;

function TDakin.RechneMitModell(x,y: Word; Mit: TIntMatrix): Loesung;
var MitM1, MitM2: TIntMatrix;
    Best,Now,M1Loesung,M2Loesung: Loesung;
    Lauf,Lauf2: Cardinal;
    i,j,s,t,Temp,
    Stand1,Stand2,Masch1,Masch2: Word;
    Modell3: TTransport;
begin
     Modell3 := TTransport.Create(AnzOrte, Mit);

     SetLength(M1Loesung.Punkte,AnzPaare+1);
     SetLength(M2Loesung.Punkte,AnzPaare+1);
     MitM1 := TIntMatrix.Dim(AnzPaare,AnzPaare);
     MitM2 := TIntMatrix.Dim(AnzPaare,AnzPaare);

     // Beide MIT-Matritzen mit aktuellen Werten fuellen
     for i := 1 to AnzPaare do
         for j := 1 to AnzPaare do
         begin
             MitM1.SetValue(i,j,Mit.GetValue(i,j));
             MitM2.SetValue(i,j,Mit.GetValue(i,j));
         end;

     // Die Spalten und Zeilen des Indexes MAXimieren
     for i := 1 to AnzPaare do
     begin
          MitM1.SetValue(i,y,MAX);
          MitM1.SetValue(x,i,MAX);
          MitM2.SetValue(i,y,MAX);
          MitM2.SetValue(x,i,MAX);
     end;

     // Den Index und den gespiegelten Index auf aktuellen Wert setzen
     MitM1.SetValue(x,y,Mit.GetValue(x,y));
     MitM2.SetValue(x,y,Mit.GetValue(x,y));

     // Maschinen und Standorte identifizieren
     Lauf := 0;
     for i := 1 to AnzOrte-1 do
         for j := i+1 to AnzOrte do
         begin
              Inc(Lauf);
              if Lauf = x then
              begin
                   Masch1 := i;
                   Masch2 := j;
              end;
              if Lauf = y then
              begin
                   Stand1 := i;
                   Stand2 := j;
              end;
         end;

     // Fall 1 - Maschinenkombination: Unmoegliche Werte MAXimieren
     Lauf := 0;
     for i := 1 to AnzOrte-1 do
         for j := i+1 to AnzOrte do
             begin
                  Inc(Lauf);
                  Lauf2 := 0;
                  for s := 1 to AnzOrte-1 do
                      for t := s+1 to AnzOrte do
                      begin
                           Inc(Lauf2);
                           if (Stand1 = i) or (Stand1 = j) then
                           begin
                                if (Masch1 <> s) and (Masch1 <> t) then
                                   MitM1.SetValue(Lauf2,Lauf,MAX);
                           end
                           else
                           if (Stand2 = j) or (Stand2 = i) then
                           begin
                                if (Masch2 <> s) and (Masch2 <> t) then
                                   MitM1.SetValue(Lauf2,Lauf,MAX);
                           end
                           else
                           if not (((Masch1 <> s) and (Masch2 <> t)) and
                                   ((Masch2 <> s) and (Masch1 <> t))) then
                              MitM1.SetValue(Lauf2,Lauf,MAX);
                      end;
             end;

     // Fall 2 - Maschinenkombination: Unmoegliche Werte MAXimieren
     Temp := Masch1;
     Masch1 := Masch2;
     Masch2 := Temp;

     Lauf := 0;
     for i := 1 to AnzOrte-1 do
         for j := i+1 to AnzOrte do
             begin
                  Inc(Lauf);
                  Lauf2 := 0;
                  for s := 1 to AnzOrte-1 do
                      for t := s+1 to AnzOrte do
                      begin
                           Inc(Lauf2);
                           if (Stand1 = i) or (Stand1 = j) then
                           begin
                                if (Masch1 <> s) and (Masch1 <> t) then
                                   MitM2.SetValue(Lauf2,Lauf,MAX);
                           end
                           else
                           if (Stand2 = j) or (Stand2 = i) then
                           begin
                                if (Masch2 <> s) and (Masch2 <> t) then
                                   MitM2.SetValue(Lauf2,Lauf,MAX);
                           end
                           else
                           if not (((Masch1 <> s) and (Masch2 <> t)) and
                                   ((Masch2 <> s) and (Masch1 <> t))) then
                              MitM2.SetValue(Lauf2,Lauf,MAX);
                      end;
             end;

     // Fall 1 - Transportmodell: Rechnen und Speichern
     Modell3.ReInit(MitM1);
     Now := Modell3.Rechnen();

     M1Loesung.TAW := Now.TAW;
     for i := 1 to AnzPaare do
     begin
          M1Loesung.Punkte[i].x := Now.Punkte[i].x;
          M1Loesung.Punkte[i].y := Now.Punkte[i].y;
     end;

     // Fall 2 - Transportmodell: Rechnen und Speichern
     Modell3.ReInit(MitM2);
     Now := Modell3.Rechnen();

     M2Loesung.TAW := Now.TAW;
     for i := 1 to AnzPaare do
     begin
          M2Loesung.Punkte[i].x := Now.Punkte[i].x;
          M2Loesung.Punkte[i].y := Now.Punkte[i].y;
     end;

     // Den Fall der beiden MIT-Faelle mit geringerem TAW waehlen
     if M1Loesung.TAW <= M2Loesung.TAW then
     begin
          Best := M1Loesung;
          for i := 1 to AnzPaare do
              for j := 1 to AnzPaare do
                  Mit.SetValue(i,j,MitM1.GetValue(i,j));
     end
     else
     begin
          Best := M2Loesung;
          for i := 1 to AnzPaare do
              for j := 1 to AnzPaare do
                  Mit.SetValue(i,j,MitM2.GetValue(i,j));
     end;

     Modell3.Kill;
     // Rueckgabe der Loesung mit dem geringeren TAW
     result := Best;
end;

function TDakin.PruefeZulaessigkeit(Fall: Loesung): Boolean;
type
    Mengen = set of Byte;

var Ort: array of Mengen;
    M1,M2: Word;
    i,j,z,Index: Word;
    Paar: array of Koord;
    Abbruch,Ergebnis: Boolean;
begin
     SetLength(Ort,AnzOrte+1);
     SetLength(Paar,AnzPaare+1);

     for i := 1 to AnzOrte do
         Ort[i] := [1..AnzOrte];

     for i := 1 to AnzPaare do
     begin
          Paar[i].x := Fall.Punkte[i].x;
          Paar[i].y := Fall.Punkte[i].y;
     end;

     for z := 1 to AnzPaare do
     begin
          Abbruch := False;
          Index := 0;
          for i := 1 to AnzOrte-1 do
          begin
              for j := i+1 to AnzOrte do
              begin
                   Inc(Index);
                   if Paar[z].x = Index then
                   begin
                        M1 := i;
                        M2 := j;
                        Abbruch := True;
                        break;
                   end;
              end;
              if Abbruch then break;
          end;
          Abbruch := False;
          Index := 0;
          for i := 1 to AnzOrte-1 do
          begin
              for j := i+1 to AnzOrte do
              begin
                   Inc(Index);
                   if Paar[z].y = Index then
                   begin
                        Ort[i] := [M1,M2] * Ort[i];
                        Ort[j] := [M1,M2] * Ort[j];
                        Abbruch := True;
                        break;
                   end;
              end;
              if Abbruch then break;
          end;

          Abbruch := False;
          for i := 1 to AnzOrte do
              if Ort[i] = [] then Abbruch := True;
          if Abbruch then break;
     end;

     if Abbruch then Ergebnis := False
     else
     begin
          Ergebnis := True;
          for i := 1 to AnzOrte do
              for j := 1 to AnzOrte do
              begin
                   if Ort[i] = [j] then
                      PruefErgebnis.SetValue(i,j);
              end;
     end;

     result := Ergebnis;
end;

function TDakin.Fakult(Zahl: Word): Int64;
var Ergebnis: Int64;
    i: Word;
begin
     Ergebnis := 1;
     for i := 2 to Zahl do
          Ergebnis := i * Ergebnis;
     result := Ergebnis;
end;

procedure TDakin.Ausgabe();
(*
     Methode zur Ausgabe des Ergebnisses

*)

begin
     // Fortschrittsanzeige schliessen
     Formular.EingabeChanged := False;

     // Ergebnisanzeige initialisieren und fuellen
     Formular.InitErgebnisGrids(AnzOrte);
     Formular.FillErgebnisGrids(AnzOrte,ZMatrix,Optimum);

     Formular.MatrixGrid.Visible := True;
     Formular.ZuordnungGrid.Visible := True;
     Formular.ErgebnisSicht.Visible := True;
     Formular.ErgebnisSicht.Show();
     Formular.ErgebnisSicht.TabVisible := True;
end;

procedure TDakin.Ausgabe(DMatrix: TIntMatrix; DLoesung: Loesung);
var Ergebnis: TIntArray;
begin
     PruefeZulaessigkeit(DLoesung);
     Ergebnis := PruefErgebnis;
     Formular.InitErgebnisGrids(AnzOrte);
     Formular.FillErgebnisGrids(AnzOrte,DMatrix,Ergebnis);

     Formular.MatrixGrid.Visible := True;
     Formular.ZuordnungGrid.Visible := True;
     Formular.ErgebnisSicht.Visible := True;
     Formular.ErgebnisSicht.Show();

     Formular.ErgebnisSicht.TabVisible := True;

     Anzeige.Hide;
end;
end.
