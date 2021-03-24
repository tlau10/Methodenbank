UNIT zeige;
{$O+,F+}
INTERFACE
USES {$IFDEF msdos} OVERLAY, {$ENDIF}
     {$IFDEF windows} WINCRT, {$ELSE} CRT,{$ENDIF}
     {$IFDEF nager} nager, {$ENDIF} util,screen_u,helpunit;

TYPE mnu_type=OBJECT
                FUNCTION  zeige_menu:INTEGER;
                PROCEDURE init(f_:STRING);
                PROCEDURE done;
                PROCEDURE unhide_all;
                PROCEDURE unhide(s_:STRING);
                PROCEDURE hide_all;
                PROCEDURE hide(s_:STRING);
              END;

IMPLEMENTATION

TYPE zeile=RECORD
             visible:BOOLEAN;
             welcher,nr:INTEGER;
             z_:spp
           END;

CONST maxbaum=130;
      maxteil=30;
      vor=lightgray;
      hinter=black;
      spec=yellow;

VAR baum:ARRAY [0..maxbaum] OF zeile;
    fp:TEXT;
    nm:INTEGER;
    all:BOOLEAN;
    h:help_type;

PROCEDURE mnu_type.hide(s_:STRING);
VAR i:INTEGER;
BEGIN
  FOR i:=1 TO nm DO
  WITH baum[i] DO IF z_.get=s_ THEN
  BEGIN
    visible:=FALSE; EXIT;
  END;
  WRITELN('MENUPUNKT ',s_,' nicht gefunden'); HALT;
END;
{-----------------------------------------------------------------------------}
PROCEDURE mnu_type.unhide(s_:STRING);
VAR i:INTEGER;
BEGIN
  FOR i:=1 TO nm DO WITH baum[i] DO IF z_.get=s_ THEN
  BEGIN
    all:=FALSE; visible:=TRUE; EXIT;
  END;
  WRITELN('MENUPUNKT ',s_,' nicht gefunden'); HALT;
END;
{-----------------------------------------------------------------------------}
PROCEDURE rahmen(xu,yu,xo,yo:INTEGER);
VAR i:INTEGER;
BEGIN
  GOTOXY(xu,yu); WRITE(#218); { links  oben }
  GOTOXY(xo,yu); WRITE(#191); { rechts oben }
  GOTOXY(xu,yo); WRITE(#192); { links  unten }
  GOTOXY(xo,yo); WRITE(#217); { rechts unten }
  FOR i:=1 TO xo-xu-1 DO
  BEGIN
    GOTOXY(i+xu,yu); WRITE(#196); { Linie oben }
    GOTOXY(i+xu,yo); WRITE(#196); { Linie unten }
  END;
  for i:=1 to yo-yu-1 DO
  BEGIN
    GOTOXY(xu,yu+i); WRITE(#179); { Linie links }
    GOTOXY(xo,yu+i); WRITE(#179); { Linie rechts }
  END;
END;
{-----------------------------------------------------------------------------}
PROCEDURE show_string(x,y,nr:INTEGER; invers,erst:BOOLEAN; s_:STRING);
VAR n:INTEGER;
BEGIN
  GOTOXY(x,y);
  IF invers THEN
  BEGIN
    TEXTCOLOR(hinter); textbackground(vor);
  END
  ELSE
  BEGIN
    TEXTCOLOR(vor); textbackground(hinter);
  END;
  WRITE(COPY(s_,1,nr-1)); TEXTCOLOR(spec); WRITE(COPY(s_,nr,1));
  IF invers THEN
  BEGIN
    TEXTCOLOR(hinter); textbackground(vor);
  END
  ELSE
  BEGIN
    TEXTCOLOR(vor); textbackground(hinter);
  END;
  WRITE(COPY(s_,nr+1,255)); n:=POS(#16,s_);
  IF n<>0 THEN { Pfeil nach rechts fÅr Submenu }
  BEGIN
    TEXTCOLOR(green); GOTOXY(x+n-1,y); WRITE(s_[n]);
  END;
  TEXTCOLOR(vor); textbackground(hinter);
  IF erst THEN CLREOL;
END;
{-----------------------------------------------------------------------------}
FUNCTION anzeige(VAR b; yoffset,ver,a,e:INTEGER):INTEGER;
VAR ml,x,y,taste,i,j,neu,nb,na,aneu,eneu:INTEGER;
    baum:ARRAY [0..maxbaum] OF zeile ABSOLUTE b;
    ba:ARRAY [1..maxteil] OF BYTE;
    bild:bildschirm;
LABEL 1;
BEGIN
  bild.init; bild.save;
  1:
  REPEAT
    anzeige:=0; na:=0; ml:=0;
    i:=a;
    WHILE i<=e DO
    BEGIN
      WITH baum[i] DO
      BEGIN
        IF LENGTH(ltrim(z_.get))+ver=LENGTH(z_.get) THEN IF visible THEN
        BEGIN
          INC(na);
          IF na>maxteil THEN
          BEGIN
            CLRSCR; WRITELN('Anzahl der Unterpunkte ist zu gross');
            WRITELN('In der UNIT ZEIGE maxteil vergroessern und neu starten');
            HALT;
          END;
          ba[na]:=i;
          IF (i<e) AND (LENGTH(ltrim(baum[i+1].z_.get))+ver<LENGTH(baum[i+1].z_.get)) THEN { Submenu }
          BEGIN
            show_string(2+ver*10,1+yoffset+na,welcher-ver,FALSE,TRUE,ltrim(z_.get)+' '+#16);
            ml:=maxi(ml,LENGTH(z_.get)-ver+2);
          END
          ELSE
          BEGIN
            show_string(2+ver*10,1+yoffset+na,welcher-ver,FALSE,TRUE,ltrim(z_.get));
            ml:=maxi(ml,LENGTH(z_.get)-ver);
          END;
        END;
        IF LENGTH(ltrim(z_.get))+ver>LENGTH(z_.get) THEN i:=10000;
      END;
      INC(i);
    END;
    IF na=0 THEN
    BEGIN
      CLRSCR; anzeige:=a-1; bild.restore; bild.done; EXIT;
    END;
    rahmen(1+ver*10,1+yoffset,2+ver*10+ml,2+yoffset+na);
    nb:=0; neu:=1;
    REPEAT
      WITH baum[ba[neu]] DO show_string(2+ver*10,1+yoffset+neu,welcher-ver,TRUE,FALSE,ltrim(z_.get));
      taste:=0; {$IFDEF nager}SHOWMOUSE; {$ENDIF}
      REPEAT UNTIL KEYPRESSED {$IFDEF nager}OR LEFTBUTTON{$ENDIF};
      {$IFDEF nager}
      IF LEFTBUTTON THEN
      BEGIN
        MOUSEPOS(x,y);
        WITH baum[ba[neu]] DO show_string(2+ver*10,1+yoffset+neu,welcher-ver,FALSE,FALSE,ltrim(z_^));
        ml:=y div 8-yoffset;
        IF (ml>0) AND (ml<=na) THEN
        BEGIN
          neu:=ml;
          taste:=13; HIDEMOUSE;
          WITH baum[ba[neu]] DO show_string(2+ver*10,1+yoffset+neu,welcher-ver,TRUE,FALSE,ltrim(z_^));
        END;
        REPEAT UNTIL NOT LEFTBUTTON
      END
      ELSE
      {$ENDIF}
      taste:=tastatur;
      IF taste=315 { F1 HELP } THEN
      BEGIN
        h.help('ZEIGE.WHL','LPT1',1); GOTO 1;
      END;
      IF (taste>96) AND (taste<123) THEN DEC(taste,32); { klein --> gross }
      WITH baum[ba[neu]] DO show_string(2+ver*10,1+yoffset+neu,welcher-ver,FALSE,FALSE,ltrim(z_.get));
      IF (taste>30) AND (taste<91) THEN FOR i:=na DOWNTO 1 DO
      WITH baum[ba[i]] DO IF COPY(ltrim(z_.get),welcher-ver,1)=CHR(taste MOD 256) THEN
      BEGIN
        neu:=i; taste:=13;
      END;
      IF taste=328 THEN DEC(neu); IF neu<1 THEN neu:=na;
      IF taste=336 THEN INC(neu); IF neu>na THEN neu:=1;
      IF taste=329 THEN neu:=maxi(1,neu-10);
      IF taste=337 THEN neu:=mini(na,neu+10);
    UNTIL (taste=13) OR (taste=27);
    IF taste=27 THEN
    BEGIN
      bild.restore; bild.done; EXIT;
    END;
    WITH baum[ba[neu]] DO show_string(2+ver*10,1+yoffset+neu,welcher-ver,TRUE,FALSE,ltrim(z_.get));
    i:=a;
    WHILE i<=e DO WITH baum[i] DO
    BEGIN
      IF LENGTH(ltrim(z_.get))+ver=LENGTH(z_.get) THEN IF Visible THEN INC(nb);
      IF nb=neu THEN
      BEGIN
        aneu:=i+1; eneu:=aneu; j:=aneu;
        WHILE j<=e+1 DO WITH baum[j] DO
        BEGIN
          IF LENGTH(z_.get)-LENGTH(ltrim(z_.get))>ver THEN eneu:=j ELSE
          BEGIN
            j:=anzeige(baum,neu-1,ver+1,aneu,eneu);
            IF j<>0 THEN
            BEGIN
              anzeige:=j; bild.restore; bild.done; EXIT;
            END;
            j:=30000; i:=30000;
          END;
          INC(j);
        END;
      END;
      INC(i);
    END;
  UNTIL FALSE;
  bild.restore; bild.done;
END;
{-----------------------------------------------------------------------------}
PROCEDURE mnu_type.init(f_:STRING);
VAR s_:STRING;
BEGIN
  TEXTCOLOR(7); TEXTBACKGROUND(0); CLRSCR; ASSIGNFILE(fp,f_);
  {$I-}RESET(fp); {$I+}
  IF IORESULT<>0 THEN
  BEGIN
    WRITELN('MENU-FILE ',f_,' NOT FOUND'); HALT;
  END;
  nm:=0;
  WITH baum[0] DO
  BEGIN
    z_.init; z_.put(''); nr:=0;
  END;
  WHILE NOT EOF(fp) DO
  BEGIN
    INC(nm);
    IF nm>maxbaum-1 THEN
    BEGIN
      CLRSCR; WRITELN('Menubaum ist zu gross.');
      WRITELN('In UNIT ZEIGE maxbaum vergrîssern und neu starten');
      HALT;
    END;
    WITH baum[nm] DO
    BEGIN
      READLN(fp,nr,s_); z_.init; welcher:=1; visible:=TRUE;
      s_:=COPY(s_,2,255);
      IF POS('~',s_)<>0 THEN welcher:=POS('~',s_)
      ELSE WHILE (welcher<LENGTH(s_)) AND (s_[WELCHER]=' ') DO INC(welcher);
      WHILE POS('~',s_)<>0 DO delete(s_,POS('~',s_),1);
      z_.put(s_);
    END;
  END;
  CLOSEFILE(fp); baum[nm+1].z_.init; baum[nm+1].z_.put(#255); all:=FALSE;
END;
{-----------------------------------------------------------------------------}
FUNCTION mnu_type.zeige_menu:INTEGER;
BEGIN
  IF all THEN
  BEGIN
    WRITELN('ALLE MENUPUNKITE SIND VERBOTEN'); HALT;
  END;
  zeige_menu:=baum[anzeige(baum,0,0,1,nm)].nr;
END;
{-----------------------------------------------------------------------------}
PROCEDURE mnu_type.done;
VAR i:INTEGER;
BEGIN
  FOR i:=0 TO nm+1 DO baum[i].z_.done;
  TEXTCOLOR(vor); TEXTBACKGROUND(hinter);
END;
{-----------------------------------------------------------------------------}
PROCEDURE mnu_type.unhide_all;
VAR i:INTEGER;
BEGIN
  FOR i:=1 TO nm DO baum[i].visible:=TRUE;
  all:=FALSE;
END;
{-----------------------------------------------------------------------------}
PROCEDURE mnu_type.hide_all;
VAR i:INTEGER;
BEGIN
  FOR i:=1 TO nm DO baum[i].visible:=FALSE;
  all:=TRUE;
END;
{-----------------------------------------------------------------------------}
END.
