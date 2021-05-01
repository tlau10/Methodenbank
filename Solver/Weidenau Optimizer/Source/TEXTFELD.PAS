UNIT textfeld;
INTERFACE
{$O+,F+}
USES {$IFDEF msdos} OVERLAY, {$ENDIF}
     {$IFNDEF win32} CRT, {$ENDIF}util;

CONST max_zeilen=2000;

TYPE text_type=ARRAY [1..max_zeilen] OF spp;
     text_type_ptr=^text_type;
     t_type=OBJECT
              t_:text_type_ptr;
              PRIVATE
              nlines:WORD;
              PUBLIC
              PROCEDURE init;
              PROCEDURE done;
              PROCEDURE put(i:WORD; CONST s_:STRIN);
              PROCEDURE clear_zeile(i:WORD);
              PROCEDURE clear_lines;
              PROCEDURE add(CONST s_:STRIN);
              FUNCTION  lines:WORD;
              FUNCTION  get(i:WORD):STRIN;
            END;
     editor_type=OBJECT(t_type)
                   inp:RECORD
                         xwinl,xbreite,ywinl,ybreite,smin,smax,sstart,
                         xoffset,yoffset,nz,zmin,zmax,zstart:WORD;
                         dummy_,dest_:STRIN;
                         fp:TEXT;
                         frei,ins:BOOLEAN;
                       END;
                   CONSTRUCTOR init;
                   DESTRUCTOR done;
                   PROCEDURE druck(s_:STRIN; nz:WORD);
                   PROCEDURE anzeige;virtual;
                   PROCEDURE zeige_zeile(dummy:WORD; s_:STRIN);VIRTUAL;
                   PROCEDURE verschiebe(wo:INTEGER);
                   {$IFNDEF win32}
                   PROCEDURE init1;
                   PROCEDURE init2;
                   PROCEDURE frame;
                   {$ENDIF}
                   FUNCTION screen_edit:WORD; virtual;
                 END;

IMPLEMENTATION

PROCEDURE t_type.clear_lines;
VAR i:INTEGER;
BEGIN
  FOR i:=1 TO max_zeilen DO t_^[i].done;
  nlines:=0;
END;
{-----------------------------------------------------------------------------}
PROCEDURE t_type.done;
BEGIN
  clear_lines; DISPOSE(t_); t_:=NIL;
END;
{-----------------------------------------------------------------------------}
PROCEDURE t_type.init;
VAR i:INTEGER;
BEGIN
  NEW(t_); nlines:=0;
  FOR i:=1 TO max_zeilen DO t_^[i].init;
END;
{----------------------------------------------------------------------------}
PROCEDURE t_type.add(CONST s_:STRIN);
BEGIN
  INC(nlines);
  IF nlines>max_zeilen THEN
  BEGIN
    my_message('Too many lines'); DEC(nlines); EXIT;
  END;
  t_^[nlines].put(s_);
END;
{----------------------------------------------------------------------------}
FUNCTION t_type.lines;
BEGIN
  lines:=nlines;
END;
{----------------------------------------------------------------------------}
PROCEDURE t_type.put(i:WORD; CONST s_:STRIN);
BEGIN
  t_^[i].put(s_);
END;
{-----------------------------------------------------------------------------}
FUNCTION t_type.get(i:WORD):STRIN;
BEGIN
  get:=t_^[i].get;
END;
{-----------------------------------------------------------------------------}
PROCEDURE t_type.clear_zeile(i:WORD);
BEGIN
  t_^[i].done;
END;
{----------------------------------------------------------------------------}
CONSTRUCTOR editor_type.init;
BEGIN
  t_type.init;
END;
{----------------------------------------------------------------------------}
DESTRUCTOR editor_type.done;
BEGIN
  t_type.done;
END;
{----------------------------------------------------------------------------}
FUNCTION editor_type.screen_edit:WORD;
BEGIN
  screen_edit:=0;
END;
{----------------------------------------------------------------------------}
{$IFNDEF win32}
PROCEDURE editor_type.frame;
BEGIN
  WITH inp DO
  BEGIN
    WINDOW(xwinl-1,ywinl-1,xwinl+xbreite,ywinl+ybreite);
    { Pfeil nach oben }
    gotoxy(2+xbreite,2+zmin);
    IF yoffset>0 THEN
    BEGIN
      TEXTCOLOR(3); WRITE(#24); TEXTCOLOR(7);
    END ELSE write(#179);
    { Pfeil nach unten }
    gotoxy(2+xbreite,1+ybreite);
    IF zmax-yoffset>ybreite THEN
    BEGIN
      TEXTCOLOR(3); WRITE(#25); TEXTCOLOR(7);
    END ELSE write(#179);
    { Pfeil nach rechts }
    gotoxy(2+xbreite-4,2+ybreite);
    IF smax-xoffset>xbreite THEN
    BEGIN
      TEXTCOLOR(3); write(' '#196#26,' '); TEXTCOLOR(7);
    END
    ELSE write(#196#196#196#196);
    { Pfeil nach links }
    gotoxy(2,2+ybreite);
    IF xoffset>0 THEN
    BEGIN
      TEXTCOLOR(3); WRITE(' '#27#196' '); TEXTCOLOR(7);
    END ELSE write(#196#196#196#196);
    TEXTCOLOR(3);
  END;
END;
{-----------------------------------------------------------------------------}
PROCEDURE editor_type.init2;
BEGIN
  WITH inp DO
  BEGIN
    TEXTCOLOR(lightgray);
    WINDOW(xwinl,ywinl,xwinl+xbreite-1,ywinl+ybreite-1);
    xoffset:=0; yoffset:=0; zstart:=maxi(zmin,mini(zmax,zstart));
    WHILE zstart-yoffset>ybreite DO INC(yoffset);
    sstart:=maxi(smin,mini(smax,sstart));
    anzeige;
    dummy_:=laenge(get(zstart),254,' '); t_^[zstart].done;
  END;
END;
{----------------------------------------------------------------------------}
PROCEDURE editor_type.init1;
VAR i:WORD;
BEGIN
  TEXTCOLOR(7); TEXTBACKGROUND(0);
  WITH inp DO
  BEGIN
    nz:=mini(nz,max_zeilen); ybreite:=78; ybreite:=mini(HI(windmax)-ywinl+1,ybreite);
    WINDOW(xwinl,ywinl,xwinl+xbreite-1,ywinl+ybreite-1); CLRSCR;
    GOTOXY(1,ybreite-1); WRITE(CHR(192));
    FOR i:=2 TO xbreite-1 DO WRITE(CHR(196));
    WRITE(CHR(217)); GOTOXY(1,1); INSLINE;
    GOTOXY(1,1); WRITE(CHR(218));
    FOR i:=2 TO xbreite-1 DO WRITE(CHR(196));
    WRITE(CHR(191));
    FOR i:=2 TO ybreite-1 DO
    BEGIN
      GOTOXY(1,i); WRITE(CHR(179)); GOTOXY(xbreite,i); WRITE(CHR(179));
    END;
    INC(xwinl,1); INC(ywinl,1); DEC(xbreite,2); DEC(ybreite,2);
    GOTOXY(xbreite DIV 2-10,ybreite+2); TEXTCOLOR(green);
  END;
END;
{$ENDIF}
{----------------------------------------------------------------------------}
PROCEDURE editor_type.verschiebe(wo:INTEGER);
BEGIN
  WITH inp DO
  BEGIN
    t_^[zstart].put(rtrim(dummy_));
    IF wo<0 THEN
    BEGIN
      zstart:=maxi(zmin,zstart+wo);
      WHILE LONGINT(zstart)-LONGINT(yoffset)<LONGINT(zmin) DO DEC(yoffset);
      anzeige;
    END
    ELSE
    BEGIN
      zstart:=mini(zmax,zstart+wo);
      WHILE zstart>ybreite+yoffset DO INC(yoffset);
      anzeige;
    END;
    dummy_:=laenge(get(zstart),254,' '); t_^[zstart].done;
  END;
END;
{----------------------------------------------------------------------------}
PROCEDURE editor_type.zeige_zeile(dummy:WORD; s_:STRIN);
BEGIN
END;
{----------------------------------------------------------------------------}
PROCEDURE editor_type.anzeige;
VAR wo,i:INTEGER;
BEGIN
  {$IFNDEF win32}
  WITH inp DO
  BEGIN
    FOR i:=1 TO zmin-1 DO IF i<>zstart THEN IF i<=ybreite THEN
    BEGIN
      GOTOXY(1,i); zeige_zeile(i,get(i));
    END;
    FOR i:=zmin TO nz DO IF i<>zstart THEN
    BEGIN
      wo:=i-yoffset;
      IF wo>=zmin THEN IF wo<=ybreite THEN
      BEGIN
        GOTOXY(1,wo); zeige_zeile(i,get(i));
      END;
    END;
    FOR i:=wo+1 TO ybreite DO
    BEGIN
      GOTOXY(1,i); CLREOL;
    END;
  END;
  {$ENDIF}
END;
{----------------------------------------------------------------------------}
PROCEDURE editor_type.druck(s_:STRIN; nz:WORD);
VAR i:INTEGER;
    fp:TEXT;
    s1_:STRIN;
BEGIN
  {$IFNDEF win32}
  { Wenn s_ ein ESC enth„lt, dann ist es die Drucker-Initialisierung }
  s1_:='';
  FOR i:=1 TO length(s_) DO IF ORD(s_[i])<=29 THEN
  BEGIN
    s1_:=COPY(s_,i,255); s_:=COPY(s_,1,i-1); BREAK;
  END;
  ASSIGNFILE(fp,s_); REWRITE(fp);
  {$I-} WRITE(fp,s1_); {$I+}
  IF IORESULT=0 THEN
  BEGIN
    WHILE (get(nz)='') AND (nz>0) DO DEC(nz);
    FOR i:=1 TO nz DO WRITELN(fp,get(i));
  END
  ELSE
  BEGIN
    CLRSCR; my_message('PRINTER NOT READY');
  END;
  CLOSEFILE(fp);
  {$ENDIF}
END;
{-----------------------------------------------------------------------------}
END.
