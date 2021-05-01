program Umladeproblem;

(*
//  Basissystem von 1992
//      Entwickler: Erik Jimenez, Bruno Morandell
//      Compiler:   Turbo Pascal 5.5
//
//  Ueberarbeitung (Dez. 1999, Mai. 2000, OTX)
//      Weiterentwicklungen:
//                  - Inidatei
//                  - neue Dialoge (Oeffnen u. Speichern)
//                  - Client-Serverfaehigkeit
//                  - neue Laufzeitfehler 200-stabile CRT-Unit
//      Compiler:   Turbo Pascal 6.0
//
*)



{$M 32768,0,4000}
{ M 20000,0,0}

uses dos, crt,
     SysUtilz,
     FileDlgs,
     Inifilez;

const
     numeros : set of char = ['0'..'9'];

type
  KOST_TYPE      = array[1..15,1..15] of integer;     { Kosten von Ai und Bi }
  KNOT_TYPE      = array[1..15] of char;                         { Knotenart }
  KNME_TYPE      = array[1..15] of integer;         { Bedarfs-/Angebotsmenge }
  temp_type      = array[1..2,1..15] of integer;     { Kosten von Ai und Bi }

var
  transportmenge : KOST_TYPE;
  kosten         : KOST_TYPE;
  temp           : TEMP_TYPE;
  knotenstatus   : KNOT_TYPE;
  knotenmenge    : KNME_TYPE;
  knotenanzahl,knotennr                : integer;
  anbieteranzahl,abnehmeranzahl        : integer;
  von,nach,position ,Ergebniswert,Code :INTEGER;
  umladekosten,max_umlademenge         :INTEGER;
  f,input_file,xa_file,help_file       : text;
  Ch                                   : Char;
  key                                  : char;
  kosten_string,vonnr,nachnr,nb_text        :string;
  zf_text,nbnr,knotnr,knotmenge,akt_pfad    :string;
  XA_Zeile,Ergebnistext,ergebnis            :STRING;
  knoten_activity,knotentransportmenge      :STRING;
  Zeichen                                   :STRING;

  UmladeIni   : TInifile ;

  Execpfad,
  Datenpfad,
  XAPfad,
  Arbeitspfad : string ;


(*--------------------------------------------------------------------------*)
procedure rahmen(x1,y1,x2,y2,art:integer);
(*--------------------------------------------------------------------------*)
var
   i:integer;
   z1,z2,z3,z4,z5,z6: char;
   z: string;
begin
     z:='';
     case art of
          1: begin
                 z1:='⁄'; z2:='ƒ'; z3:='ø'; z4:='≥'; z5:='¿'; z6:='Ÿ';
             end;
          2: begin
                 z1:='…'; z2:='Õ'; z3:='ª'; z4:='∫'; z5:='»'; z6:='º';
             end;
     end;
     gotoxy(x1,y1);
     write(z1);
     for i:=1 to (x2-x1) do
     begin
          z:=z+' ';
          write(z2);
     end;
     writeln(z3);

     for i:=1 to (y2-y1) do
     begin
          gotoxy(x1,y1+i);
          writeln(z4,z,z4);
     end;
     gotoxy(x1,y2);
     write(z5);
     for i:=1 to (x2-x1) do
     begin
          write(z2);
     end;
     write(z6);
     gotoxy(x1+1,y1+1);
end;


(*--------------------------------------------------------------------------*)
procedure farbe(b,t:integer);
(*--------------------------------------------------------------------------*)
begin
     textbackground(b);
     textcolor(t);
end;

(*--------------------------------------------------------------------------*)
procedure error_maske(x,y:integer; message:string);
(*--------------------------------------------------------------------------*)
var ch:char; i,x2:integer;
begin
     if ord(message[0]) < 20 then x2:=x+21 else x2:=x+ord(message[0]);
     farbe(red,white);
     rahmen(x,y,x2,y+2,2);
     write(message);
     gotoxy(x2-19,y+2);
     write(' Weiter mit RETURN ');
     repeat
        ch:=readkey;
     until ch=#13;
     farbe(black,white);
     gotoxy(1,y);
     for i:=1 to 3 do
     begin
         write('                                        ');
         writeln('                                      ');
     end;
end;

(*--------------------------------------------------------------------------*)
PROCEDURE Gesamtkosten;
(*--------------------------------------------------------------------------*)
BEGIN
      Ergebnis:='';
      Ergebnistext:=COPY(XA_ZEILE,47,10);
      FOR Position:=1 TO 10 DO
        BEGIN
          Zeichen:=COPY(Ergebnistext,Position,1);
          IF Zeichen<>' ' THEN
             IF Zeichen <> ',' THEN
               IF Zeichen ='.' THEN  Position:=10
                               ELSE  Ergebnis:=Ergebnis+Zeichen;

        END;
      VAL(Ergebnis,Ergebniswert,code);
      Ergebniswert:=Ergebniswert+Umladekosten;

END;
(*--------------------------------------------------------------------------*)
PROCEDURE x_Werte_auslesen(vs,es:integer);
(*--------------------------------------------------------------------------*)
VAR variable :string;
    i,j  :INTEGER;

BEGIN
   variable:=COPY(xa_zeile,vs,6);
   i:=POS('x',variable);
   j:=POS('_',variable);

   IF j-i = 2 THEN VAL(COPY(variable,i+1,1),von,code)
              ELSE VAL(COPY(variable,i+1,2),von,code);
   IF j = 5 THEN VAL(COPY(variable,6,1),nach,code)
            ELSE VAL(COPY(variable,5,2),nach,code);

   Knotentransportmenge:='';
   Knoten_activity:=COPY(XA_ZEILE,es,10);

   FOR Position:=1 TO 10 DO
     BEGIN
       Zeichen:=COPY(Knoten_activity,Position,1);
          IF Zeichen<>' ' THEN
             IF Zeichen <> ',' THEN
               IF Zeichen ='.' THEN  Position:=10
                  ELSE  Knotentransportmenge:=Knotentransportmenge+Zeichen;

     END;
   VAL(knotentransportmenge,transportmenge[von,nach],code);
   IF von=nach
     THEN transportmenge[von,nach]:=max_umlademenge-transportmenge[von,nach];

END;


(*--------------------------------------------------------------------------*)
PROCEDURE knoten_ergebnisse;
(*--------------------------------------------------------------------------*)
VAR ergebnisspalte,var_spalte :integer;
BEGIN

   (***************************** Ergebnis in 1.ZeilenhÑlfte *************)
   var_spalte:=5;
   ergebnisspalte:=11;
   x_werte_auslesen(var_spalte,ergebnisspalte);

   (***************************** Ergebnis in 2.ZeilenhÑlfte *************)
   var_spalte:=44;
   ergebnisspalte:=50;
   x_werte_auslesen(var_spalte,ergebnisspalte);

END;

(*--------------------------------------------------------------------------*)
PROCEDURE auswertung;
(*--------------------------------------------------------------------------*)
var zeile :byte;
BEGIN
   farbe(black,white);
   clrscr;
   farbe(green,white);
   writeln('                                E R G E B N I S                                 ');


    farbe(black,3);
    gotoxy(1,3);
    FOR von:=1 TO knotenanzahl DO
       FOR nach:= 1 TO knotenanzahl DO
          transportmenge[von,nach]:= 0;

    REPEAT
       READLN(XA_File,xa_zeile);
       IF POS('O P T I M A L   S O L U ',XA_zeile) <>0 THEN BEGIN
          WRITELN;
          Gesamtkosten;
          WRITELN;
       END;

       IF (POS('I     x',xa_zeile)<>0) OR
          (POS('I    x',xa_zeile)<>0 ) OR
          (POS('I   x',xa_zeile)<>0  ) OR
          (POS('|     x',xa_zeile)<>0) OR
          (POS('|    x',xa_zeile)<>0 ) OR
          (POS('|   x',xa_zeile)<>0  ) THEN  Knoten_ergebnisse;

       IF POS('N O    F E A S I B L E    S O L',XA_zeile) <>0 THEN BEGIN
          WRITELN('Aufgabe ist unlîsbar (---> NO FEASABLE SOLUTION)');
          WRITELN;
          WRITELN('Sie kînnen das Ergebnis im Textfile XA.OUT anschauen!');
          READLN;
          EXIT;
       END;

       IF POS('U N B O U N D E D   V A R I A',XA_zeile) <>0 THEN BEGIN;
          WRITELN('Lîsungsraum ist unbeschrÑnkt (--> UNBOUNDED VARIABLE');
          WRITELN;
          WRITELN('Sie kînnen das Ergebnis im Textfile XA.OUT anschauen!');
          READLN;
          EXIT;
       END;

    UNTIL EOF(XA_File);

    WRITELN;
    WRITELN('  Gesamtkosten = ',Ergebniswert);
    WRITELN;
    WRITELN('  Transportmenge   Umlademenge');
    WRITELN('  ----------------------------');

    FOR von:=1 TO knotenanzahl DO
      FOR nach:=1 TO knotenanzahl DO
          IF transportmenge[von,nach]<>0 THEN
            IF von=nach THEN
            BEGIN
                zeile:=wherey;
                gotoxy(20,zeile);
                WRITE('x',von,'_',nach,' = ',transportmenge[von,nach])
            END
            ELSE BEGIN
               WRITELN;
               zeile:=wherey;
               WRITE('  x',von,'_',nach);
               gotoxy(9,zeile);
               WRITE(' = ',transportmenge[von,nach]);
            END;

    farbe(3,white);
    gotoxy(1,24);
    write('  Weiter mit beliebiger Taste                                                   ');
    REPEAT
          key:=READKEY;
    UNTIL (key<>#27);
    CLOSE(XA_FILE);
END;

(*--------------------------------------------------------------------------*)
PROCEDURE ZF_generieren;
(*--------------------------------------------------------------------------*)

BEGIN
      WRITELN(input_file,'..title');
      WRITELN(input_file,'  Umladeproblem ');
      WRITELN(input_file);
      WRITELN(input_file,'..objective minimize');
      FOR von:=1 TO knotenanzahl DO BEGIN
         zf_text:='  ';
         FOR nach:=1 TO knotenanzahl DO

           IF (kosten[von,nach] <> 0 ) OR (kosten[nach,von] <> 0) THEN
           BEGIN
             IF kosten[von,nach]=0 THEN kosten[von,nach]:=1000;
                   (*gerichteter Graph*)
             STR(kosten[von,nach],kosten_string);
             STR(von,vonnr);
             STR(nach,nachnr);
             IF von=nach THEN kosten_string := ' -' + kosten_string + 'x'
                         ELSE kosten_string := ' +' + kosten_string + 'x';
             zf_text:=zf_text + kosten_string + vonnr + '_' + nachnr;
           END;

         WRITELN(input_file,zf_text);
      END;
END;


(*--------------------------------------------------------------------------*)
PROCEDURE Bounds_generieren;
(*--------------------------------------------------------------------------*)

BEGIN
      WRITELN(input_file);
      WRITELN(input_file,'..bounds');
      FOR von:=1 TO knotenanzahl DO BEGIN
         FOR nach:=1 TO knotenanzahl DO BEGIN
            IF (kosten[von,nach]<>0) OR (kosten[nach,von]<>0) THEN BEGIN
               STR(von,vonnr);
               STR(nach,nachnr);
               WRITELN(input_file,'  x'+ vonnr + '_'+ nachnr + ' >=0');
            END;
         END;
      END;
      WRITELN(input_file);
END;

(*--------------------------------------------------------------------------*)
PROCEDURE NB_generieren;
(*--------------------------------------------------------------------------*)

BEGIN
      WRITELN(input_file,'..constraints');
      WRITELN(input_file);
      FOR knotennr:=1 TO knotenanzahl DO BEGIN
          STR(knotennr,knotnr);

          (******************* NB fÅr: Knoten verteilt an...... ********)

          STR(2*knotennr-1,NBnr);
          NB_Text:='  R' + NBnr + ':  ';

          FOR nach:= 1 TO knotenanzahl DO BEGIN
              IF Kosten[knotennr,nach] <> 0 THEN BEGIN
                  STR(nach,nachnr);
                  NB_Text:=NB_Text + 'x' + knotnr + '_'+ nachnr + ' + ';
              END;
          END;

          IF knotenstatus[knotennr] = 'A' THEN
             STR(knotenmenge[knotennr]+max_umlademenge,knotmenge)
          ELSE STR(max_umlademenge,knotmenge);

          NB_Text:=COPY(NB_Text,1,LENGTH(NB_Text)-2);
          NB_Text:=NB_Text + ' = ' + knotmenge;
          WRITELN(input_file,NB_Text);

          (******************* NB fÅr: Knoten bekommt von...... ********)

          STR(2*knotennr,NBnr);
          NB_Text:='  R' + NBnr + ':  ';

          FOR von:= 1 TO knotenanzahl DO BEGIN
             IF Kosten[von,knotennr] <> 0 THEN BEGIN
                STR(von,vonnr);
                NB_Text:=NB_Text + 'x' + vonnr + '_' + knotnr + ' + ';
             END;
          END;

          IF knotenstatus[knotennr] = 'B' THEN
             STR(knotenmenge[knotennr]+max_umlademenge,knotmenge)
          ELSE STR(max_umlademenge,knotmenge);

          NB_Text:=COPY(NB_Text,1,LENGTH(NB_Text)-2);
          NB_Text:=NB_Text + ' = ' + knotmenge;
          WRITELN(input_file,NB_Text);
      END;

      WRITELN(input_file);

END;

(*--------------------------------------------------------------------------*)
procedure umladekostenerrechnen;
(*--------------------------------------------------------------------------*)
var i,j     :integer;
begin
    umladekosten:=0;
    for i:=1 to knotenanzahl do
      for j:= 1 to knotenanzahl do
         if i=J then umladekosten:=umladekosten + kosten[i,j];
    umladekosten:=umladekosten*max_umlademenge;
end;

(*--------------------------------------------------------------------------*)
function sumcheck :Boolean;
(*--------------------------------------------------------------------------*)
var summeanbietermenge,summeabnehmermenge,s,z,i :integer;
begin
  sumcheck:=true;
  summeanbietermenge:=0;
  summeabnehmermenge:=0;
  s:=22;
  z:=20;
  for i:=1 to knotenanzahl do
  begin
    if knotenstatus[i]='A' then
    begin
      if knotenmenge[i] = 0 then
      begin
         error_maske(s,z,' Menge ist Null ');
         sumcheck:=false;
         exit;
      end
      else summeanbietermenge:=summeanbietermenge+knotenmenge[i];
    end;

    if knotenstatus[i]='B' then
    begin
      if knotenmenge[i] = 0 then
      begin
         error_maske(s,z,' Menge ist Null ');
         sumcheck:=false;
         exit;
      end
      else summeabnehmermenge:=summeabnehmermenge+knotenmenge[i];
    end;
  end;
  if summeanbietermenge<>summeabnehmermenge then
  begin
    error_maske(s,z,' Angebotsmenge ungleich Bedarfsmenge ');
    sumcheck:=false;
  end
  else max_umlademenge:=summeanbietermenge;
end;


(*--------------------------------------------------------------------------*)
function Korrekt(tab :integer) :Boolean;
(*--------------------------------------------------------------------------*)
var n,m,s,z,j,i  :integer;
BEGIN
  korrekt:=true;
  s:=22;
  z:=20;
  farbe(red,white);
  IF tab = 1 then
  begin
      n:=anbieteranzahl;
  end
  else
  begin
      n:=abnehmeranzahl;
  end;

  For i:=1 to n do
     if (temp[1,i]<1) or (temp[1,i]>knotenanzahl) then
     begin
           korrekt:=false;
           error_maske(s,z,' Unerlaubte Knotennummer ');
           exit;
     end;

  For i:=1 to (n-1) do
     for j:=(i+1) to n do
       if temp[1,i]=temp[1,j] then
       begin
          korrekt:=false;
          error_maske(s,z,' Doppelte Knotennummer ');
          exit;
       end;

  farbe(blue,white);
end;

(*--------------------------------------------------------------------------*)
function eingabecheck :Boolean;
(*--------------------------------------------------------------------------*)
var summeanbietermenge,summeabnehmermenge,s,z,i :integer;
begin
  eingabecheck:=true;
  s:=22;
  z:=20;
  farbe(red,white);
  if knotenanzahl <= 3 then
  begin
      error_maske(s,z,' Knotenanzahl muss groesser als 3 sein ');
      eingabecheck:=false;
      farbe(black,white);
      exit;
  end;
  if knotenanzahl > 15 then
  begin
      error_maske(s,z,' Knotenanzahl muss kleiner als 16 sein ');
      eingabecheck:=false;
      farbe(black,white);
      exit;
  end;
  if anbieteranzahl = 0 then
  begin
      error_maske(s,z,' Mindestens ein Anbieter!!             ');
      eingabecheck:=false;
      farbe(black,white);
      exit;
  end;
  if abnehmeranzahl = 0 then
  begin
      error_maske(s,z,' Mindestens ein Annehmer!!             ');
      eingabecheck:=false;
      farbe(black,white);
      exit;
  end;
  if (knotenanzahl < (abnehmeranzahl + anbieteranzahl)) then
  begin
      error_maske(s,z,' Mehr Anbieter und Abnehmer als Knoten ');
      eingabecheck:=false;
      farbe(black,white);
      exit;
  end;

  farbe(black,white);
end;

(*--------------------------------------------------------------------------*)
function atoi(wort:string):integer;
(*--------------------------------------------------------------------------*)
var
   zahl,i,j,zehn,laenge : integer;
   wort2: string;
begin
   zahl:=0;
   zehn:=1;
   j:=0;
   laenge:=ord(wort[0]);
   for i:=1 to laenge do
         if wort[i] in numeros then
         begin
            j:=j+1;
            wort2[j]:=wort[i];
         end;
   laenge:=j;
   for i:=0 to (laenge-1) do
   begin
         zahl:=zahl + ((ord(wort2[laenge-i]) - ord('0')) * zehn);
         zehn:=zehn*10;
   end;
   atoi:=zahl;
end;

(*--------------------------------------------------------------------------*)
procedure InitVar;        (* Werte initialisieren *)
(*--------------------------------------------------------------------------*)
var
   i,j:integer;
begin
    knotenanzahl:=1;
    anbieteranzahl:=0;
    abnehmeranzahl:=0;
    for i:=1 to 15 do
        for j:=1 to 15 do
          kosten[i,j]:=0;

    for i:=1 to 15 do
        knotenmenge[i]:=0;

    for i:=1 to 15 do
        knotenstatus[i]:='U';
end;


(*--------------------------------------------------------------------------*)
procedure llenar(var puf :string;la:integer);
(*--------------------------------------------------------------------------*)
var
   i: integer;
begin
     for i:=(ord(puf[0])+1) to la do
         puf:=puf + ' ';
end;


(*--------------------------------------------------------------------------*)
procedure schreibe(pos_x,pos_y,zahl:integer);
(*--------------------------------------------------------------------------*)
begin
      gotoxy(pos_x,pos_y);   (* alte Position *)
      farbe(black,3);
      if zahl >= 1000 then
         write(zahl)
      else
         if zahl >= 100 then
            write(' ',zahl)
         else
            if zahl >= 10 then
               write('  ',zahl)
            else
               if zahl > 0 then
                  write('   ',zahl)
               else
                  write('    ');
end;

(*--------------------------------------------------------------------------*)
procedure lese_zahl(x,y: integer; var zahl:integer);
(*--------------------------------------------------------------------------*)
var
   zaehler,zaehler_max,i: integer;
   puffer: string;
begin
     zaehler_max:=4;
     zaehler:=0;
     puffer:='    ';
     gotoxy(x,y);
     farbe(blue,white);
     write(puffer);

     repeat
           farbe(blue,white);
           gotoxy(x+zaehler,y);
           ch:=readkey;
           case ch of
                '0'..'9':
                begin
                     write(ch);
                     puffer[zaehler+1]:=ch;
                     zaehler:=zaehler+1;
                     if zaehler >= zaehler_max then
                        zaehler:=zaehler-1;
                end;
                #8:      (* Backspace *)
                begin
                     zaehler:=zaehler-1;
                     if zaehler < 0 then
                        zaehler:=zaehler+1
                     else
                     begin
                        for i:=(zaehler+1) to (zaehler_max-1) do
                            puffer[i]:=puffer[i+1];
                        puffer[zaehler_max]:=' ';
                        gotoxy(x,y);
                        write(puffer);
                     end;
                end;
           end;
     until (ch=#13);
     zahl:=atoi(puffer);
     schreibe(x,y,zahl);
end;


(*--------------------------------------------------------------------------*)
procedure lese_name(x,y: integer; var name:string);
(*--------------------------------------------------------------------------*)
begin
   (*    repeat
            farbe(black,white);
            rahmen(x,y,x+25,y+2,2);
            gotoxy(x+2,y+1);
            write('Dateiname: ');
            readln(name);
         until(name <> '');
         *)

  (*-- Unit FileOpen *)
  name := OpenDialog(' Umladeproblem ', Datenpfad, '*.*');

end;

(*--------------------------------------------------------------------------*)
procedure matrix_bilden;
(*--------------------------------------------------------------------------*)
var
   B_zl,B2_zl,i_s: string;
   i,j,z1,s1: integer;

begin
   farbe(black,white);
   clrscr;
   B_zl:='   ';
   B2_zl:='';
   z1:=5;
   s1:=10;
   farbe(green,white);
   write('                               KOSTENMATRIX                                     ');

   for i:=1 to knotenanzahl do
   begin
       STR(i,i_s);
       B2_zl:=B2_zl+ 'ƒƒƒƒƒ';
       if (i<10) then
          B_zl:=B_zl + '    ' + i_s
       else
          B_zl:=B_zl + '   ' + i_s;
   end;
   farbe(black,3);
   gotoxy(1,z1-2);
   Writeln(B_zl);
   write('   ⁄',B2_zl,'ø');
   gotoxy(1,z1);

   for i:=1 to knotenanzahl do
   begin
       STR(i,i_s);
       gotoxy(1,i+z1-1);
       if (i<10) then
          Write(' '+ i_s + ' ≥')
       else
          Write(i_s + ' ≥');
       s1:=5+(knotenanzahl*5);
       gotoxy(s1,i+z1-1);
       write('≥');
   end;
   gotoxy(1,knotenanzahl+z1);
   writeln('   ¿',B2_zl,'Ÿ');

   (* Zahlen ausgeben *)

   farbe(black,3);
   for i:=1 to knotenanzahl do
   begin
       s1:=6;
       for j:=1 to knotenanzahl do
       begin
            schreibe(s1,z1,kosten[i,j]);
            s1:=s1+5
       end;
       z1:=z1+1;
   end;

   farbe(3,white);
   gotoxy(1,24);
   write ('  ESC = XA/MenÅ     F2 = Speichern') ;
   ClrEol;
end;

(*--------------------------------------------------------------------------*)
procedure maske_an_ab;
(*--------------------------------------------------------------------------*)
var
   tab,i,j,z,s,summe:integer;
   zaehler,zaehler_max,posx,posy: integer;
   anfang_x,anfang_y,ende_x,ende_y: integer;
   schritt,x,y,x_max,y_max: integer;
   puffer: string;
   geand,einfg: boolean;
begin
   farbe(black,white);
   clrscr;
   farbe(green,white);
   writeln('                                                                                ');
   farbe(black,3);
   write  ('         ANBIETER                          ABNEHMER');
   gotoxy(1,4);
   farbe(black,3);
   z:=7;
   writeln('    ⁄ƒƒƒƒƒƒƒƒ¬ƒƒƒƒƒƒƒƒø');
   writeln('    ≥ Knotnr ≥ Menge  ≥');
   writeln('    √ƒƒƒƒƒƒƒƒ≈ƒƒƒƒƒƒƒƒ¥');
   for i:=1 to anbieteranzahl do
   writeln('    ≥        ≥        ≥');
   writeln('    √ƒƒƒƒƒƒƒƒ≈ƒƒƒƒƒƒƒƒ¥');
   writeln('    ≥ Summe  ≥        ≥');
   writeln('    ¿ƒƒƒƒƒƒƒƒ¡ƒƒƒƒƒƒƒƒŸ');
   z:=4;
   s:=35;
   gotoxy(s,z);
   write  ('    ⁄ƒƒƒƒƒƒƒƒ¬ƒƒƒƒƒƒƒƒø');
   z:=z+1;gotoxy(s,z);
   write  ('    ≥ Knotnr ≥ Menge  ≥');
   z:=z+1;gotoxy(s,z);
   writeln('    √ƒƒƒƒƒƒƒƒ≈ƒƒƒƒƒƒƒƒ¥');
   for i:=1 to abnehmeranzahl do
   begin
        z:=z+1;gotoxy(s,z);
        writeln('    ≥        ≥        ≥');
   end;
   z:=z+1;gotoxy(s,z);
   writeln('    √ƒƒƒƒƒƒƒƒ≈ƒƒƒƒƒƒƒƒ¥');
   z:=z+1;gotoxy(s,z);
   writeln('    ≥ Summe  ≥        ≥');
   z:=z+1;gotoxy(s,z);
   writeln('    ¿ƒƒƒƒƒƒƒƒ¡ƒƒƒƒƒƒƒƒŸ');
   farbe(3,white);
   gotoxy(1,24);
   write('  ESC = Ende/Kostenmatrix      F4 = Anbieter        F5 = Abnehmer               ');

   (* TEMP array initialisieren *)
   for i:=1 to 2 do
       for j:=1 to 15 do
           temp[i,j]:=0;

   summe:=0;
   z:=7;
   for i:=1 to knotenanzahl do
       if knotenstatus[i]='A' then
       begin
          temp[1,z-6]:=i;
          temp[2,z-6]:=knotenmenge[i];
          schreibe(8,z,i);
          schreibe(17,z,knotenmenge[i]);
          z:=z+1;
          summe:=summe+knotenmenge[i];
       end;
   schreibe(17,anbieteranzahl+8,summe);
   summe:=0;
   z:=7;
   for i:=1 to knotenanzahl do
       if knotenstatus[i]='B' then
       begin
          schreibe(42,z,i);
          schreibe(51,z,knotenmenge[i]);
          z:=z+1;
          summe:=summe+knotenmenge[i];
       end;
   schreibe(51,abnehmeranzahl+8,summe);

   tab:=1;
   x:=1;
   y:=1;
   schritt:=9;
   anfang_x:=8;
   anfang_y:=7;
   x_max:=2;
   y_max:=anbieteranzahl;
   ende_x:=anfang_x + schritt*(x_max-1);
   ende_y:=anfang_y + (y_max-1);
   zaehler_max:=4;
   zaehler:=0;
   posx:=anfang_x;
   posy:=anfang_y;
   geand:=false;
   einfg:=false;

   str(temp[x,y],puffer);
   llenar(puffer,zaehler_max);
   gotoxy(anfang_x,anfang_y);
   farbe(blue,white);
   write(puffer);

   repeat
         farbe(black,3);
         j:=0;
         for i:=1 to y_max do
             j:=j+temp[2,i];
         schreibe(ende_x,ende_y+2,j);
         farbe(blue,white);
         gotoxy(posx+zaehler,posy);

         ch:=readkey;
         case ch of
              '0'..'9',' ':
              begin
                      if (zaehler <= zaehler_max) and (einfg=true) then
                      begin
                           geand:=true;
                           for i:=0 to (zaehler_max-zaehler-1) do
                               puffer[zaehler_max-i]:=puffer[zaehler_max-i-1];
                           puffer[zaehler+1]:=' ';
                           gotoxy(posx,posy);
                           write(puffer);
                           gotoxy(posx+zaehler,posy);
                      end;
                   write(ch);
                   geand:=true;
                   puffer[zaehler+1]:=ch;
                   zaehler:=zaehler+1;
                   if zaehler >= zaehler_max then
                      zaehler:=zaehler-1;
              end;
              #77:      (* Cursor nach rechts *)
              begin
                   if zaehler < (zaehler_max-1) then
                      zaehler:=zaehler+1;
              end;
              #75:      (* Cursor nach links *)
              begin
                   if zaehler > 0 then
                      zaehler:=zaehler-1;
              end;
              #8:      (* Backspace *)
              begin
                   zaehler:=zaehler-1;
                   if zaehler < 0 then
                      zaehler:=zaehler+1
                   else
                   begin
                      geand:=true;
                      for i:=(zaehler+1) to (zaehler_max-1) do
                          puffer[i]:=puffer[i+1];
                      puffer[zaehler_max]:=' ';
                      gotoxy(posx,posy);
                      write(puffer);
                   end;
              end;
              #83:      (* Entfuegen *)
              begin
                      geand:=true;
                      for i:=(zaehler+1) to (zaehler_max-1) do
                          puffer[i]:=puffer[i+1];
                      puffer[zaehler_max]:=' ';
                      gotoxy(posx,posy);
                      write(puffer);
              end;
              #82:      (* Einfuegen *)
              begin
                   if einfg=false then
                   begin
                      einfg:=true;
                      gotoxy(75,24);
                      farbe(black,3);
                      write('INS');
                      farbe(blue,white);
                   end
                   else
                   begin
                       einfg:=false;
                      gotoxy(75,24);
                      farbe(3,black);
                      write('   ');
                      farbe(blue,white);
                   end;
              end;
              #13:    (* (TAB=#9) RETURN=#13 nach rechts *)
              begin
                   if geand=true then
                       temp[x,y]:=atoi(puffer);
                   geand:=false;
                   schreibe(posx,posy,temp[x,y]);
                   zaehler:=0;
                   x:=x+1;
                   if x > x_max then
                   begin
                        x:=1;
                        posx:=anfang_x;
                        y:=y+1;
                        if y > y_max then
                        begin
                             y:=1;
                             posy:=anfang_y;
                        end
                        else
                            posy:=posy+1;
                   end
                   else
                       posx:=posx+schritt;

                   str(temp[x,y],puffer);
                   llenar(puffer,zaehler_max);
                   gotoxy(posx,posy);
                   farbe(blue,white);
                   write(puffer);
              end;

              #9:    (* TAB=#9 (RETURN=#13) nach rechts *)
              begin
                   if geand=true then
                       temp[x,y]:=atoi(puffer);
                   geand:=false;
                   schreibe(posx,posy,temp[x,y]);
                   zaehler:=0;
                   x:=x+1;
                   if x > x_max then
                   begin
                        x:=1;
                        posx:=anfang_x;
                   end
                   else
                       posx:=posx+schritt;

                   str(temp[x,y],puffer);
                   llenar(puffer,zaehler_max);
                   gotoxy(posx,posy);
                   farbe(blue,white);
                   write(puffer);
              end;
              #15:  (* (TAB=#15) nach links *)
              begin
                   if geand=true then
                       temp[x,y]:=atoi(puffer);
                   geand:=false;
                   schreibe(posx,posy,temp[x,y]);
                   zaehler:=0;
                   x:=x-1;
                   if x < 1 then
                   begin
                        x:=x_max;
                        posx:=ende_x;
                   end
                   else
                       posx:=posx-schritt;

                   str(temp[x,y],puffer);
                   llenar(puffer,4);
                   gotoxy(posx,posy);
                   farbe(blue,white);
                   write(puffer);
              end;
              #80:    (* nach unten *)
              begin
                   if geand=true then
                       temp[x,y]:=atoi(puffer);
                   geand:=false;
                   schreibe(posx,posy,temp[x,y]);
                   zaehler:=0;
                   y:=y+1;
                   if y > y_max then
                   begin
                        y:=1;
                        posy:=anfang_y;
                   end
                   else
                       posy:=posy+1;

                   str(temp[x,y],puffer);
                   llenar(puffer,4);
                   gotoxy(posx,posy);
                   farbe(blue,white);
                   write(puffer);
              end;
              #72:    (* nach oben *)
              begin
                   if geand=true then
                       temp[x,y]:=atoi(puffer);
                   geand:=false;
                   schreibe(posx,posy,temp[x,y]);
                   zaehler:=0;
                   y:=y-1;
                   if y < 1 then
                   begin
                        y:=y_max;
                        posy:=ende_y;
                   end
                   else
                       posy:=posy-1;

                   str(temp[x,y],puffer);
                   llenar(puffer,4);
                   gotoxy(posx,posy);
                   farbe(blue,white);
                   write(puffer);
              end;
              #62:        (* in die Anbieter-Tabelle springen *)
              begin
                   if geand=true then
                      temp[x,y]:=atoi(puffer);
                   geand:=false;
                   farbe(black,3);
                   j:=0;
                   for i:=1 to y_max do
                       j:=j+temp[2,i];
                   schreibe(ende_x,ende_y+2,j);
                   farbe(blue,white);
                   if (tab <> 1) and (korrekt(tab)=true) then
                   begin
                   schreibe(posx,posy,temp[x,y]);
                        for i:=1 to knotenanzahl do
                            if knotenstatus[i]='B' then
                            begin
                                 knotenstatus[i]:='U';
                                 knotenmenge[i]:=0;
                            end;
                        (* Abnehmerdaten uebernehmen *)
                        for i:=1 to abnehmeranzahl do
                        begin
                             j:=temp[1,i];
                             knotenstatus[j]:='B';
                             knotenmenge[j]:=temp[2,i];
                        end;
                        (* TEMP initialisieren *)
                        for i:=1 to 2 do
                            for j:=1 to 15 do
                              temp[i,j]:=0;
                        z:=7;
                        (* Felder loeschen *)
                        for i:=1 to anbieteranzahl do
                        begin
                             schreibe(8,z,0);
                             schreibe(17,z,0);
                             z:=z+1;
                        end;
                        z:=7;
                        for i:=1 to knotenanzahl do
                            if knotenstatus[i]='A' then
                            begin
                               temp[1,z-6]:=i;
                               temp[2,z-6]:=knotenmenge[i];
                               schreibe(8,z,i);
                               schreibe(17,z,knotenmenge[i]);
                               z:=z+1;
                            end;
                        tab:=1;
                        x:=1;
                        y:=1;
                        schritt:=9;
                        anfang_x:=8;
                        anfang_y:=7;
                        x_max:=2;
                        y_max:=anbieteranzahl;
                        ende_x:=anfang_x + schritt*(x_max-1);
                        ende_y:=anfang_y + (y_max-1);
                        zaehler_max:=4;
                        zaehler:=0;
                        posx:=anfang_x;
                        posy:=anfang_y;
                        geand:=false;

                        str(temp[x,y],puffer);
                        llenar(puffer,zaehler_max);
                        gotoxy(anfang_x,anfang_y);
                        farbe(blue,white);
                        write(puffer);
                   end;
              end;
              #63:       (* in die Abnehmer-Tabelle springen *)
              begin
                   if geand=true then
                      temp[x,y]:=atoi(puffer);
                   geand:=false;
                   farbe(black,3);
                   j:=0;
                   for i:=1 to y_max do
                       j:=j+temp[2,i];
                   schreibe(ende_x,ende_y+2,j);
                   farbe(blue,white);
                   if (tab <> 2) and (korrekt(tab)=true) then
                   begin
                   schreibe(posx,posy,temp[x,y]);
                        for i:=1 to knotenanzahl do
                            if knotenstatus[i]='A' then
                            begin
                                 knotenstatus[i]:='U';
                                 knotenmenge[i]:=0;
                            end;
                        (* Anbieterdaten uebernehmen *)
                        for i:=1 to anbieteranzahl do
                        begin
                             j:=temp[1,i];
                             knotenstatus[j]:='A';
                             knotenmenge[j]:=temp[2,i];
                        end;
                        (* TEMP initialisieren *)
                        for i:=1 to 2 do
                            for j:=1 to 15 do
                              temp[i,j]:=0;
                        z:=7;
                        (* Felder loeschen *)
                        for i:=1 to abnehmeranzahl do
                        begin
                             schreibe(42,z,0);
                             schreibe(51,z,0);
                             z:=z+1;
                        end;
                        z:=7;
                        for i:=1 to knotenanzahl do
                            if knotenstatus[i]='B' then
                            begin
                               temp[1,z-6]:=i;
                               temp[2,z-6]:=knotenmenge[i];
                               schreibe(42,z,i);
                               schreibe(51,z,knotenmenge[i]);
                               z:=z+1;
                            end;
                        tab:=2;
                        x:=1;
                        y:=1;
                        schritt:=9;
                        anfang_x:=42;
                        anfang_y:=7;
                        x_max:=2;
                        y_max:=abnehmeranzahl;
                        ende_x:=anfang_x + schritt*(x_max-1);
                        ende_y:=anfang_y + (y_max-1);
                        zaehler_max:=4;
                        zaehler:=0;
                        posx:=anfang_x;
                        posy:=anfang_y;
                        geand:=false;

                        str(temp[x,y],puffer);
                        llenar(puffer,zaehler_max);
                        gotoxy(anfang_x,anfang_y);
                        farbe(blue,white);
                        write(puffer);
                   end;
              end;
              #27:       (* ESC *)
              begin
                   if geand=true then
                      temp[x,y]:=atoi(puffer);
                   geand:=false;
                   farbe(black,3);
                   j:=0;
                   for i:=1 to y_max do
                       j:=j+temp[2,i];
                   schreibe(ende_x,ende_y+2,j);
                   farbe(blue,white);
                   zaehler:=0;
                   if tab = 1 then
                   begin
                        for i:=1 to knotenanzahl do
                            if knotenstatus[i]='A' then
                            begin
                                 knotenstatus[i]:='U';
                                 knotenmenge[i]:=0;
                            end;
                        (* Anbieterdaten uebernehmen *)
                        for i:=1 to anbieteranzahl do
                        begin
                             j:=temp[1,i];
                             knotenstatus[j]:='A';
                             knotenmenge[j]:=temp[2,i];
                        end;
                   end
                   else
                   begin
                        for i:=1 to knotenanzahl do
                            if knotenstatus[i]='B' then
                            begin
                                 knotenstatus[i]:='U';
                                 knotenmenge[i]:=0;
                            end;
                        (* Abnehmerdaten uebernehmen *)
                        for i:=1 to abnehmeranzahl do
                        begin
                             j:=temp[1,i];
                             knotenstatus[j]:='B';
                             knotenmenge[j]:=temp[2,i];
                        end;
                   end;
                   if (korrekt(tab)=false) or (sumcheck=false) then ch:=' ';
              end;
         end;
   until (ch = #27);
end;


(*--------------------------------------------------------------------------*)
procedure Daten_Speichern (MODUS: boolean; fname: string) ;
(*--------------------------------------------------------------------------*)
(*-- Speichervorgang (Taste F2)
//   Speichern einer
//         - vorhandenen (aber ver‰nderten) Datei
//         - neuen Datei
*)
var
  i, j,
  x, y  : integer;

begin
   if MODUS=false then
   begin
       {$IFDEF MSDOS}
          repeat
              x := 20; y := 18;
              farbe(black, white);
              rahmen(x,y, x+54,y+3, 2);

              farbe(black, lightgreen);
              gotoxy(x+2,y+1);
              write('Ohne Pfadangabe erfolgt die Speicherung im Datenpfad');
              gotoxy(x+2,y+2);
              write('Dateiname:  ');
              readln(fname);
          until (fname <> '');

          (*-- 'fname' um Datenpfad ergaenzen, wenn kein Pfad angegeben wurde *)
          if pos('\', fname) = 0 then
            fname := DatenPfad + '\' + fname;

          MODUS := true;

          gotoxy(x,y);   ClrEol;
          gotoxy(x,y+1); ClrEol;
          gotoxy(x,y+2); ClrEol;
          gotoxy(x,y+3); ClrEol;

       {$ELSE}
          lese_name(50,21,fname);   // Windows-Speichern-Dialog !
       {$ENDIF}
   end;

   gotoxy(5,21);
   Farbe(blue, white);
   write(' Datei ', fname, ' wird gespeichert... ');
   (* sleep u. Geschreibe loeschen -> siehe unten *)


   Assign(f, fname);
   Rewrite(f);

   writeln(f,knotenanzahl);
   for i:=1 to knotenanzahl do
       writeln(f,knotenstatus[i]);
   for i:=1 to knotenanzahl do
       writeln(f,knotenmenge[i]);
   for i:=1 to knotenanzahl do
       for j:=1 to knotenanzahl do
           writeln(f,kosten[i,j]);
   close(f);

   delay(800);
   gotoxy(5,21);
   farbe(black, black);
(*   write('                                     ');
   write('                                     ');   *)
   ClrEol;
   gotoxy(5,21);
   Farbe(black, lightgray + 128);
   write('Weiter mit ESC');
end;


(*--------------------------------------------------------------------------*)
procedure eingabe_maske (MODUS : boolean);
(*--------------------------------------------------------------------------*)
var
   fname      :string;
   buffer     :string;
   Akt_Verz   : string;
   i,j: integer;

   SolverBatch : Text;
   ReturnValue : word;

   zaehler,zaehler_max,posx,posy: integer;
   anfang_x,anfang_y,ende_x,ende_y: integer;
   x,y,x_max,y_max: integer;
   puffer,fehler_text: string;
   geand, einfg: boolean;

begin
    InitVar;

    if MODUS=TRUE Then
       begin
            clrscr;
            lese_name(20,15,fname);
            assign(f,fname);
            {$I-};
            reset(f);
            {$I+};
            if IORESULT <> 0 then
            begin
                 clrscr;
                 fehler_text:=' Datei '+fname+' existiert nicht ';
                 error_maske(15,15,fehler_text);
                 exit;
            end;

            readln(f,buffer);

            knotenanzahl:=atoi(buffer);

            for i:=1 to knotenanzahl do
            begin
                readln(f,buffer);
                knotenstatus[i]:=buffer[1];
            end;
            for i:=1 to knotenanzahl do
            begin
                readln(f,buffer);
                knotenmenge[i]:=atoi(buffer);
            end;
            for i:=1 to knotenanzahl do
                for j:=1 to knotenanzahl do
                begin
                     readln(f,buffer);
                     kosten[i,j]:=atoi(buffer);
                end;
            close(f);
            for i:=1 to knotenanzahl do
                 case knotenstatus[i] of
                      'A': anbieteranzahl:=anbieteranzahl+1;
                      'B': abnehmeranzahl:=abnehmeranzahl+1;
                 end;
       end
    else
       begin
          repeat
              clrscr;
              rahmen(3,3, 33,9, 2);
              gotoxy(4,4);write(' Knotenanzahl (min. 4): ');
              gotoxy(4,6);write(' Wieviele Anbieter    : ');
              gotoxy(4,8);write(' Wieviele Abnehmer    : ');
              lese_zahl(28,4,knotenanzahl);
              lese_zahl(28,6,anbieteranzahl);
              lese_zahl(28,8,abnehmeranzahl);
          until eingabecheck=true;
       end;

    maske_an_ab;
    matrix_bilden;

    x:=1;
    y:=1;
    anfang_x:=6;
    anfang_y:=5;
    x_max:=knotenanzahl;
    y_max:=knotenanzahl;
    ende_x:=anfang_x + 5*(x_max-1);
    ende_y:=anfang_y + (y_max-1);
    zaehler_max:=4;
    zaehler:=0;
    posx:=anfang_x;
    posy:=anfang_y;
    geand:=false;
    einfg:=false;

    str(kosten[y,x],puffer);
    llenar(puffer,zaehler_max);
    gotoxy(anfang_x,anfang_y);
    farbe(blue,white);
    write(puffer);

    repeat
          gotoxy(4,knotenanzahl+6);
          farbe(3,white);
          write(' von: ',y,'  ');
          gotoxy(13,knotenanzahl+6);
          write('nach: ',x,' ');
          farbe(blue,white);
          gotoxy(posx+zaehler,posy);
          ch:=readkey;
          case ch of
               '0'..'9',' ':
               begin
                       if (zaehler <= zaehler_max) and (einfg=true) then
                       begin
                            geand:=true;
                            for i:=0 to (zaehler_max-zaehler-1) do
                                puffer[zaehler_max-i]:=puffer[zaehler_max-i-1];
                            puffer[zaehler+1]:=' ';
                            gotoxy(posx,posy);
                            write(puffer);
                            gotoxy(posx+zaehler,posy);
                       end;
                    write(ch);
                    geand:=TRUE;
                    puffer[zaehler+1]:=ch;
                    zaehler:=zaehler+1;
                    if zaehler >= zaehler_max then
                       zaehler:=zaehler-1;
               end;
               #77:      (* Cursor nach rechts *)
               begin
                    if zaehler < (zaehler_max-1) then
                       zaehler:=zaehler+1;
               end;
               #75:      (* Cursor nach links *)
               begin
                    if zaehler > 0 then
                       zaehler:=zaehler-1;
               end;
               #8:      (* Backspace *)
               begin
                    zaehler:=zaehler-1;
                    if zaehler < 0 then
                       zaehler:=zaehler+1
                    else
                    begin
                       geand:=true;
                       for i:=(zaehler+1) to (zaehler_max-1) do
                           puffer[i]:=puffer[i+1];
                       puffer[zaehler_max]:=' ';
                       gotoxy(posx,posy);
                       write(puffer);
                    end;
               end;
               #83:      (* Entfuegen *)
               begin
                       geand:=true;
                       for i:=(zaehler+1) to (zaehler_max-1) do
                           puffer[i]:=puffer[i+1];
                       puffer[zaehler_max]:=' ';
                       gotoxy(posx,posy);
                       write(puffer);
               end;
               #82:      (* Einfuegen *)
               begin
                    if einfg=false then
                    begin
                       einfg:=true;
                       gotoxy(75,24);
                       farbe(black,3);
                       write('INS');
                       farbe(blue,white);
                    end
                    else
                    begin
                        einfg:=false;
                       gotoxy(75,24);
                       farbe(3,black);
                       write('   ');
                       farbe(blue,white);
                    end;
               end;
               #9:    (* TAB=#9 (RETURN=#13) nach rechts *)
               begin
                    if geand=true then
                        kosten[y,x]:=atoi(puffer);
                    geand:=false;
                    schreibe(posx,posy,kosten[y,x]);
                    zaehler:=0;
                    x:=x+1;
                    if x > x_max then
                    begin
                         x:=1;
                         posx:=anfang_x;
                    end
                    else
                        posx:=posx+5;

                    str(kosten[y,x],puffer);
                    llenar(puffer,zaehler_max);
                    gotoxy(posx,posy);
                    farbe(blue,white);
                    write(puffer);
               end;
               #13:    (* (TAB=#9) RETURN=#13 nach rechts *)
               begin
                    if geand=true then
                        kosten[y,x]:=atoi(puffer);
                    geand:=false;
                    schreibe(posx,posy,kosten[y,x]);
                    zaehler:=0;
                    x:=x+1;
                    if x > x_max then
                    begin
                         x:=1;
                         posx:=anfang_x;
                         y:=y+1;
                         if y > y_max then
                         begin
                              y:=1;
                              posy:=anfang_y;
                         end
                         else
                             posy:=posy+1;
                    end
                    else
                        posx:=posx+5;

                    str(kosten[y,x],puffer);
                    llenar(puffer,zaehler_max);
                    gotoxy(posx,posy);
                    farbe(blue,white);
                    write(puffer);
               end;
               #15:  (* (TAB=#15) nach links *)
               begin
                    if geand=true then
                        kosten[y,x]:=atoi(puffer);
                    geand:=false;
                    schreibe(posx,posy,kosten[y,x]);
                    zaehler:=0;
                    x:=x-1;
                    if x < 1 then
                    begin
                         x:=x_max;
                         posx:=ende_x;
                    end
                    else
                        posx:=posx-5;

                    str(kosten[y,x],puffer);
                    llenar(puffer,4);
                    gotoxy(posx,posy);
                    farbe(blue,white);
                    write(puffer);
               end;
               #80:    (* nach unten *)
               begin
                    if geand=true then
                        kosten[y,x]:=atoi(puffer);
                    geand:=false;
                    schreibe(posx,posy,kosten[y,x]);
                    zaehler:=0;
                    y:=y+1;
                    if y > y_max then
                    begin
                         y:=1;
                         posy:=anfang_y;
                    end
                    else
                        posy:=posy+1;

                    str(kosten[y,x],puffer);
                    llenar(puffer,4);
                    gotoxy(posx,posy);
                    farbe(blue,white);
                    write(puffer);
               end;
               #72:    (* nach oben *)
               begin
                    if geand=true then
                        kosten[y,x]:=atoi(puffer);
                    geand:=false;
                    schreibe(posx,posy,kosten[y,x]);
                    zaehler:=0;
                    y:=y-1;
                    if y < 1 then
                    begin
                         y:=y_max;
                         posy:=ende_y;
                    end
                    else
                        posy:=posy-1;

                    str(kosten[y,x],puffer);
                    llenar(puffer,4);
                    gotoxy(posx,posy);
                    farbe(blue,white);
                    write(puffer);
               end;
               #60 :
                 Daten_Speichern (MODUS, fname);

               #27:       (* ESC *)
               begin
                    if geand=true then
                       kosten[y,x]:=atoi(puffer);
                    geand:=false;
               end;
          end;
       (* gotoxy(25,25);
          writeln('taste: ',ord(ch)); *)
    until ch = #27;


    umladekostenerrechnen;


    (*-- LP-File fuer XA *)
    ASSIGN (input_File, Arbeitspfad + '\Umlade.lp');
    REWRITE (input_File);

    zf_generieren;
    bounds_generieren;
    nb_generieren;
    close(input_file);


    (*-- Batch-Datei f¸r Solveraufruf *)
    Assign (SolverBatch, Arbeitspfad + '\Solver.bat') ;
    rewrite (SolverBatch) ;

    writeln(SolverBatch, '@echo off') ;
    writeln(SolverBatch, ':: Umladeproblem') ;
    writeln(SolverBatch) ;
    writeln(SolverBatch, 'set Oldpath=%path%');
    writeln(SolverBatch, 'path=' + XAPfad + ';%path%') ;
    writeln(SolverBatch, 'xa umlade.lp OUTPUT xa.out');
    writeln(SolverBatch, 'set path=%Oldpath%') ;
    writeln(SolverBatch, 'set OldPath=') ;

    close (SolverBatch) ;


    farbe(black,white);
    clrscr;

    (* Der direkte Aufruf von XA wird durch den Aufruf einer
       Batch-Datei ersetzt. Dies kann allerdings nur unter
       Verwendung des Kommandointerpreters erfolgen.  -> bei DOS
    *)

(*    Akt_Verz := CurDir();  *)
    ChDir(Arbeitspfad);
    Exec(GetEnv('COMSPEC'), '/C Solver.bat');
    ChDir(execpfad);   (* Akt_Verz); *)


(*  for i := 1 to 12 do
    begin
      if FileExists(Arbeitspfad + '\xa.out') then
        break
      else
        delay(500);
    end ;
    *)
    delay(500);  (* zur Sicherheit *)


    CLRSCR;
    ASSIGN (XA_File, Arbeitspfad + '\xa.out') ;
    RESET (XA_File);

    FOR von:=1 TO 8 DO
      FOR nach:= 1 TO 8 DO
        transportmenge[von,nach]:=0;
    auswertung;
    ch:=' ';


{ ******************************************************
    if MODUS=FALSE Then
       begin
          lese_name(50,21,fname);
          assign(f,fname);
       end
    else
       begin
            assign(f,fname);
       end;

    * gotoxy(5,21);
    write('Datei ',fname,' wird gespeichert...'); *

    rewrite(f);
    writeln(f,knotenanzahl);
    for i:=1 to knotenanzahl do
        writeln(f,knotenstatus[i]);
    for i:=1 to knotenanzahl do
        writeln(f,knotenmenge[i]);
    for i:=1 to knotenanzahl do
        for j:=1 to knotenanzahl do
            writeln(f,kosten[i,j]);
    close(f);

    umladekostenerrechnen;

    ASSIGN (input_File,'input.txt');
    REWRITE (input_File);

    zf_generieren;
    bounds_generieren;
    nb_generieren;
    close(input_file);
    farbe(black,white);
    clrscr;

     * Der direkte Aufruf von XA wird durch den Aufruf einer
       Batch-Datei ersetzt. Dies kann allerdings nur unter
       Verwendung des Kommandointerpreters erfolgen.
    *
     * Exec('XA.EXE', 'input.txt OUTPUT output.txt'); *
     * Exec('\command.com', '/C XA_RUN.BAT input.txt OUTPUT output.txt'); *
    Exec(GetEnv('COMSPEC'), '/C XA_RUN.BAT input.txt OUTPUT output.txt');

    CLRSCR;
    ASSIGN (XA_File,'output.txt');
    RESET (XA_File);


    FOR von:=1 TO 8 DO
      FOR nach:= 1 TO 8 DO
        transportmenge[von,nach]:=0;
    auswertung;
    ch:=' ';
******************************************************** }

end;

(*--------------------------------------------------------------------------*)
procedure hilfe;
(*--------------------------------------------------------------------------*)
var z:integer;
    info: string;
begin
   assign(help_file, Execpfad + '\Umladep.hlp');
   reset(help_file);
   repeat
         farbe(black,white);
         clrscr;
         farbe(green,white);
         write('                               I N F O R M A T I O N                            ');
         farbe(3,white);
         gotoxy(1,24);
         write('   Weiter mit RETURN                                                            ');
         gotoxy(1,5);
         farbe(black,white);
         z:=0;
         repeat
            readln(help_file,info);
            writeln(info);
            z:=z+1;
         until ((z = 17) or eof(help_file));
   until ((readkey=#13) and eof(help_file));
   close(help_file);
end;


(*--------------------------------------------------------------------------*)
procedure InitMaske;
(*--------------------------------------------------------------------------*)
begin
     farbe(black,white);
     clrscr;

     Farbe (brown, lightgray);
     gotoxy(01,25);
     write ( ' (c) 1992-2000 Fachhochschule Konstanz' );
     clreol;

     farbe(blue,white);
     rahmen(10,2, 70,8, 2);

     gotoxy(26,4);
     write('Erik Jimenez / Bruno Morandell');
     gotoxy(36,6);
     write('WI8, SS92');

     farbe(magenta, white);
     rahmen(23,11, 56,13, 1);
     gotoxy(28,12);
     write('U M L A D E P R O B L E M');

     farbe(black,white);
     gotoxy(1,16);
     writeln('                           Hilfe................... 1');
     writeln('                           Neues Problem........... 2');
     writeln('                           Alte Datei einladen..... 3');
     writeln('                           Ende.................... 4');
     gotoxy(1,22);
     write  ('                           Geben Sie eine Zahl ein: ');

end;

(*--------------------------------------------------------------------------*)
(*    Beginn Hauptprogramm                                                  *)
(*--------------------------------------------------------------------------*)
begin
  {$IFDEF MSDOS}
     Execpfad := extractFilePath(ParamStr(0));
  {$ELSE}
     Execpfad := extractFilePath(Application.Exename);
  {$ENDIF}


  (*-- IniDatei *)
  (* UmladeIni := TIniFile.Create(Execpfad + '\Umlade.ini'); *)
  UmladeIni.Create(Execpfad + '\Umlade.ini');

  Datenpfad   := UmladeIni.ReadString('Pfade', 'Datenpfad', '');
  XAPfad      := UmladeIni.ReadString('Pfade', 'XAPfad', '');
  Arbeitspfad := UmladeIni.ReadString('Pfade', 'Arbeitspfad', 'c:\temp');

  UmladeIni.Free ;

  repeat
    InitMaske;
    farbe(black,white);
    key:=readkey;
    case key of
         '1':
         begin
              clrscr;
              hilfe;
              clrscr;
         end;
         '2':
         begin
             clrscr;
             eingabe_maske(FALSE);
             clrscr;
         end;
         '3':
         begin
             clrscr;
             eingabe_maske(TRUE);
             clrscr;
         end;
         '4', #27 :
           (*// tue nichts *)
         else
           begin
             Sound(800); delay(30);
             Sound(1400); delay(50);
             NoSound;
           end;
    end;

  until (key='4') or (key=#27);

  textcolor(white);
  textbackground(black);
  clrscr;
  gotoxy(1,1);

end.
