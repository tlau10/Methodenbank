{*****************************************************************************}
{*   Das nachfolgende Programm 'JOB-SHOP' wurde im Rahmen der Vorlesung      *}
{*   OPERATIONS-RESEARCH-II der FH Konstanz im Sommersemester 1992 von       *}
{*   Michael G�z erstellt.                                                   *}
{*                                                                           *}
{*                                                                           *}
{*   WS 97/98    Erweiterungen:                                              *}
{*   OTW         o IniDatei                                                  *}
{*               o Option f�r Win(95)-�ffnen-Dialog                          *}
{*                                                                           *}
{*               Rekompilierung mit TP 5.5                                   *}
{*                                                                           *}
{*                                                                           *}
{*****************************************************************************}
program job_shop;

{$M 16384,0,$8000}

{-----------------------------------------------------------------------------}
 uses crt, dos, graph, drivers, Fonts, Printer, IniDatei;
{uses crt, dos, graph, printer, IniDatei; }


{-----------------------------------------------------------------------------}
{$I mg_graf.prc}                  { Enth�lt den Header des Programmes         }

{-----------------------------------------------------------------------------}
type
     matrix_typ              = array[1..16,1..16] of integer;
                                  { Matrize mit max. Werten 16x16             }
     optimal_typ             = record wert         : longint;
                                      permu        : string;
                               end;
                                  { Matrix die alle optimalen Alternativen
                                    enth�lt (Wert=Minimum und permu=Reihen-
                                    folge der Produkte auf den Maschinen)     }
{-----------------------------------------------------------------------------}

var
     lv5,                         { Laufvariable                              }
     mox,moy,                     { Maximalwerte mox := Anzahl Maschinen      }
                                  {              moy := Anzahl Produkte       }
     dummy1,dummy2,dummy3,
     hpt_auswahl,                 { Enth�lt gew�hlte Option des Hauptmen�s    }
     drucka_auswahl,              { Enth�lt gew�hlte Option des Druckmen�s    }
     eingabe_auswahl,             { Enth�lt gew�hlte Option des Einlesemen�s  }
     ausgabe_auswahl,             { Enth�lt gew�hlte Option des Optionenmen�s }
     druckoption,                 { Enth�lt Nummer des zu druckenden Optimums }
     opt_zaehler                  { Enth�lt Anzahl berechneter Alternativen   }
                                  : integer;
     {------------------------------------------------------------------------}
     OS,                          { Betriebssystem                            }
     ini_datei                    { Ini-Datei (im selben Verzeichnis)         }
                                  : string;
     permu_max,                   {Anzahl sich ergebender Permutationen       }
     zaehler                      : single;
     {------------------------------------------------------------------------}
     druckfolge,                  {Index f�r Reihenfolge der Produkte         }
     produktfarbe                 {Enth�lt Darstellungsfarben f�r Produkte    }
                                  : array[1..16] of integer;
     {------------------------------------------------------------------------}
     optimal                      { siehe Optimal_typ                         }
                                  : array[0..9] of optimal_typ;
     {------------------------------------------------------------------------}
     menuauswahl                  { Array mit den M�glichkeiten eines Men�s   }
                                  : array[1..10] of string[30];
     {------------------------------------------------------------------------}
     auswahlset_kurz              { Enth�lt erlaubte Tasten zur Men�auswahl   }
                                  : set of char;
     {------------------------------------------------------------------------}
     i                            { Laufvariable mit Index                    }
                                  : array[1..16] of integer;
     {------------------------------------------------------------------------}
     matrix_org,                  { Eingelesene Originalmatrix                }
     matrix_org_copy,             { Kopie der eingelesenen Originalmatrix     }
     matrix_aus,                  { Ausgangsmatrix f�r Brechnung; Permutation
                                    der Originalmatrix                        }
     matrix_ber                   { Berechnete Matrix mit �bergangszeiten     }
                                  : matrix_typ;
     {------------------------------------------------------------------------}
     zeile,
     zeile_alt                    { Variablen zur Anzeige von Meldungen       }
                                  : string[78];
     {------------------------------------------------------------------------}
     permu_max_str,               { Permu_max als String                      }
     suchpfad,
     suchpfad_alt,                { Enth�lt den Pfad einer eingelesenen Datei }
     druckvar,                    { Enth�lt zu druckende Optimumsvariation    }
     dummy1_str
                                  : string;
     {------------------------------------------------------------------------}
     break,                       { Abbruch wenn Taste bei Berechnung gedr�ckt}
     optimum_berechnet,           { False wenn Optimum noch nicht berechnet   }
     nur_optimum,                 { False wenn alle Variationen gezeigt werden}
     datei_geladen                { False wenn noch keine Matrix vorhanden ist}
                                  : boolean;

{-----------------------------------------------------------------------------}
{$I FUNCTION.ALL}                 { Enth�lt s�mtliche Funktionen und          }
                                  { Prozeduren                                }
{-----------------------------------------------------------------------------}

begin
     (* IniDatei zuweisen *)
     (*   Wird das Prog. von einem anderen Verzeichnis aus gestartet,    *)
     (*   so wird die IniDatei nicht gefunden. F�r diesen Fall kann      *)
     (*   mit der Batch-Datei Start_sv.bat das aktuelle Verzeichnis auf  *)
     (*   das Jobshop-Verzeichnis gesetzt werden.                        *)
     ini_datei := 'job-shop.ini';

     grafik_installieren;
     variablen_initialisieren;
     mg_grafik_header;
     grundmaske(1,0);
     setcolor(14);settextstyle(4,0,5);outtextxy(140,5,'Job-Shop-Problem');
     setviewport(34,66,609,294,true);
     repeat
        menuauswahl[1]    := 'Einlesen von Zeiten';
        menuauswahl[2]    := 'Anzeige / �ndern von Zeiten ';
        menuauswahl[3]    := 'Optimum berechnen';
        menuauswahl[4]    := 'Druckausgabe';
        menuauswahl[5]    := '<ESC>..Anwendung beenden';
        auswahlset_kurz   := [chr(13),chr(27),chr(72),chr(80),'E','A','O','D'];
        hpt_auswahl       := menu_anzeigen('Hauptmen�',5);
        case hpt_auswahl of
          1 : begin
                   menuauswahl[1]    := 'Manuelle Eingabe';
                   menuauswahl[2]    := 'Einlesen aus Datei';
                   menuauswahl[3]    := '<ESC>..Zur�ck ins Hauptmen�';
                   auswahlset_kurz   := [chr(13),chr(27),chr(72),chr(80),'A','E'];
                   repeat
                         eingabe_auswahl := menu_anzeigen('Eingabe-Auswahl-Men�',3);
                         case eingabe_auswahl of
                              1 : begin
                                       manuelle_eingabe;
                                       optimum_berechnet := false;
                                  end;

                              2 : begin
                                       datei_einlesen;
                                       optimum_berechnet := false;
                                  end;

                         end;
                   until (eingabe_auswahl=3);
                   hpt_auswahl := 0;
              end;

          2 : begin
                   if (mox*moy<>0) then
                   begin
                        raster_aufbauen;
                        mat_eingabe(3,15,8);
                        mat_vergleich_und_abfrage;
                        clearviewport;
                   end
                   else begin
                        meldung('Keine Zeitmatrix vorhanden. W�hlen Sie <Einlesen von Zeiten> im Hauptmen�'
                        ,15,4,false,true,false);
                        delay(1500);
                   end;
              end;

          3 :   if (mox*moy>0) then
                begin
                   menuauswahl[1]    := 'Nur Optimum';
                   menuauswahl[2]    := 'Alle Permutationen';
                   menuauswahl[3]    := '<ESC>..Zur�ck ins Hauptmen�';
                   auswahlset_kurz   := [chr(13),chr(27),chr(72),chr(80),'N','A'];
                   repeat
                         ausgabe_auswahl := menu_anzeigen('Ausgabe-Auswahl-Men�',3);
                         case ausgabe_auswahl of
                              1 : begin
                                       break := false;
                                       nur_optimum := true;
                                       matrix_permutation;
                                       optimum_berechnet := true;
                                  end;

                              2 : begin
                                       break := false;
                                       nur_optimum := false;
                                       matrix_permutation;
                                       optimum_berechnet := true;
                                  end;
                         end;
                   until (ausgabe_auswahl=3);
                   hpt_auswahl := 0;
                end else begin
                    meldung('Keine Zeitmatrix vorhanden. W�hlen Sie zuerst <Einlesen von Zeiten>'
                    ,15,4,false,true,false);
                    delay(1500);
                end;

          4 :   if (mox*moy>0) then
                begin
                   repeat
                         menuauswahl[1]    := 'Originalmatrix';
                         menuauswahl[2]    := 'Eine Alternative';
                         menuauswahl[3]    := 'S�mtliche Alternativen';
                         menuauswahl[4]    := '<ESC>..Zur�ck ins Hauptmen�';
                         auswahlset_kurz   := [chr(13),chr(27),chr(72),chr(80),'O','E','S'];
                         drucka_auswahl    := menu_anzeigen('Druckmen�-A',4);
                         case drucka_auswahl of
                              1 : begin
                                       raster_aufbauen;
                                       org_matrix_anzeigen(true);;
                                       {$I-} writeln(lst,''); {$I+}

                                       if (ioresult=0) then
                                       begin
                                            meldung('Ausgangsmatrix wird ausgedruckt. Bitte etwas Geduld...'
                                            ,15,1,true,false,false);
                                            for lv5 := 1 to moy do druckfolge[lv5] := lv5;
                                            drucke_matrix(matrix_org,true);
                                       end
                                       else begin
                                            meldung('Druckausgabe kann nicht durchgef�hrt werden. Drucker ok ?'
                                            ,15,4,false,true,false);
                                            delay(1000);
                                       end;
                                       clearviewport;
                                  end;

                              2 : if (optimum_berechnet = true) then
                                  begin
                                    clearviewport;
                                    {$I-} writeln(lst,''); {$I+}
                                    if (ioresult=0) then
                                    begin
                                      meldung('<ESC>..Abbruch    |    Eingabe der zu druckenden Optimums-Nr.:'
                                      ,15,1,true,false,false);
                                      setviewport(1,1,639,349,true);
                                      setviewport(24,302,609,322,true);
                                      str(opt_zaehler+1,dummy1_str);
                                      dummy2 := opt_zaehler+1;
                                      repeat
                                         druckvar := eingabe(true,2,70,23,11,0);
                                         val(druckvar,dummy1,dummy3);
                                         if((dummy1>dummy2) or ((dummy1<1)and(dummy3=0))) then
                                           meldung('Es sind nur die Alternativen 1 bis '+dummy1_str+' verf�gbar',
                                           15,4,false,true,true);
                                      until((dummy1>0) and (dummy1<=dummy2) or (druckvar=chr(27)));
                                      setviewport(1,1,639,349,true);
                                      setviewport(34,66,609,294,true);
                                      if (dummy1 in [1..dummy2]) then
                                      begin
                                        raster_aufbauen;berechnete_anzeige(true,dummy1-1);
                                        meldung('Optimalmatrix Nr. '+druckvar+' wird ausgedruckt. Bitte etwas Geduld...'
                                        ,15,1,true,false,false);
                                        drucke_matrix(matrix_ber,false);
                                      end;
                                  end
                                  else begin
                                        meldung('Druckausgabe kann nicht durchgef�hrt werden. Drucker ok ?'
                                        ,15,4,false,true,false);
                                        delay(1000);
                                       end;
                                       clearviewport;
                                  end
                                  else begin
                                       meldung('Optimum wurde noch nicht berechnet. W�hlen Sie zuerst <Optimum berechnen>'
                                       ,15,4,false,true,false);
                                       delay(1500);
                                  end;


                              3 : if (optimum_berechnet = true) then
                                  begin
                                    clearviewport;
                                    {$I-} writeln(lst,''); {$I+}
                                    if (ioresult=0) then
                                      for druckoption := 0 to opt_zaehler do
                                      begin
                                       str(druckoption+1,druckvar);
                                       raster_aufbauen;berechnete_anzeige(true,druckoption);
                                       meldung('Optimalmatrix Nr. '+druckvar+' wird ausgedruckt. Bitte etwas Geduld...'
                                       ,15,1,true,false,false);
                                       drucke_matrix(matrix_ber,false);
                                      end
                                     else begin
                                          meldung('Druckausgabe kann nicht durchgef�hrt werden. Drucker ok ?'
                                          ,15,4,false,true,false);
                                          delay(1000);
                                     end;
                                     clearviewport;
                                  end
                                  else begin
                                       meldung('Optimum wurde noch nicht berechnet. W�hlen Sie zuerst <Optimum berechnen>'
                                       ,15,4,false,true,false);
                                       delay(1500);
                                  end;
                         end;
                    until(drucka_auswahl=4);
                end
                else begin
                    meldung('Keine Zeitmatrix vorhanden. W�hlen Sie zuerst <Einlesen von Zeiten>'
                    ,15,4,false,true,false);
                    delay(1500);
                end;
     end;
     until (hpt_auswahl = 5);
     closegraph;clrscr;
end.
