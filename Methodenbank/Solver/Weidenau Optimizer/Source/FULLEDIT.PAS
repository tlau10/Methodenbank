UNIT fulledit;
{$O+,F+}
INTERFACE

USES {$IFDEF msDos } OVERLAY, {$ENDIF}
     {$IFNDEF win32} CRT, {$ENDIF}
     textfeld,util;

TYPE bool_type=ARRAY [1..max_zeilen] OF BOOLEAN;
     edit=OBJECT(editor_type)
            erlaubt:^bool_type;
            PROCEDURE set_erlaubt(wahr:BOOLEAN);
            PROCEDURE delete_line(zstart,nz:WORD; VAR dummy_:STRIN);
            PROCEDURE zeige_zeile(i:WORD; du_:STRIN);virtual;
            FUNCTION  screen_edit:WORD; virtual;
            CONSTRUCTOR init;
            DESTRUCTOR done;
          END;

IMPLEMENTATION

CONSTRUCTOR edit.init;
BEGIN
  NEW(erlaubt); SET_erlaubt(TRUE); editor_type.init;
END;
{-----------------------------------------------------------------------------}
DESTRUCTOR edit.done;
BEGIN
  DISPOSE(erlaubt); editor_type.done;
END;
{-----------------------------------------------------------------------------}
PROCEDURE edit.delete_line(zstart,nz:WORD; VAR dummy_:STRIN);
VAR i:INTEGER;
BEGIN
  t_^[zstart].done;
  for i:=zstart to nz-1 do t_^[i]:=t_^[i+1];
  t_^[nz].init; t_^[nz].put('');
  dummy_:=laenge(get(zstart),254,' '); t_^[zstart].done;
END;
{-----------------------------------------------------------------------------}
PROCEDURE edit.set_erlaubt(wahr:BOOLEAN);
VAR i:WORD;
BEGIN
  FOR i:=1 TO max_zeilen DO erlaubt^[i]:=wahr;
END;
{----------------------------------------------------------------------------}
PROCEDURE edit.zeige_zeile(i:WORD; du_:STRIN);
BEGIN
  {$IFNDEF win32}
  WITH inp DO
  BEGIN
    IF erlaubt^[i] THEN
    WRITE(COPY(COPY(du_,1,smin)+COPY(du_,smin+1+xoffset,255),1,xbreite-1))
    ELSE
    BEGIN
      TEXTCOLOR(0); TEXTBACKGROUND(7);
      WRITE(COPY(COPY(du_,1,smin)+COPY(du_,smin+1+xoffset,255),1,xbreite-1));
    END;
    IF WHEREX<=xbreite THEN CLREOL;
    TEXTCOLOR(7); TEXTBACKGROUND(0);
  END;
  {$ENDIF}
END;
FUNCTION edit.screen_edit:WORD;
VAR i,j,taste:INTEGER;
    merk_,search_,s_,s1_,s2_:STRIN;
    fp:TEXT;
    found:BOOLEAN;
LABEL 1;

BEGIN
  search_:=''; edit.init1;
  GOTOXY(inp.xbreite DIV 2-10,inp.ybreite+2); TEXTCOLOR(green);
  WRITE(' ESC EXIT      F10-PRINT ');
  WITH inp DO
  BEGIN
    edit.init2;
    REPEAT
      edit.frame;
      GOTOXY(2+xbreite-13,1); WRITE(zstart:4,sstart:4);
      IF ins THEN WRITE(' ins') ELSE WRITE(' ovw'); TEXTCOLOR(7);
      WINDOW(xwinl,  ywinl,  xwinl+xbreite-1,ywinl+ybreite-1);
      GOTOXY(1,zstart-yoffset); zeige_zeile(zstart,dummy_);
      WHILE sstart-xoffset>xbreite DO INC(xoffset);
      WHILE sstart-xoffset<smin DO DEC(xoffset);
      GOTOXY(sstart-xoffset,zstart-yoffset); taste:=tastatur;
      CASE taste OF
        324: { F10 }
        BEGIN
          t_^[zstart].put(rtrim(dummy_));
          druck(inp.dest_,inp.nz);
          clear_zeile(zstart); verschiebe(0);
        END;
        13,27,315..323,340..349: { Sondertasten }
        BEGIN
          screen_edit:=taste; GOTO 1;
        END;
        4: { ^D   delete line }
        if frei then if erlaubt^[zstart] THEN
        BEGIN
          delete_line(zstart,nz,dummy_); anzeige;
        END;
        9: { ^I   insert line }
        if frei then if erlaubt^[zstart] THEN
        BEGIN
          IF nz=max_zeilen THEN write(#7)
          ELSE
          BEGIN
            INC(nz);
            t_^[zstart].put(rtrim(dummy_));
            for i:=nz downto zstart+1 do t_^[i]:=t_^[i-1];
            t_^[zstart].init; dummy_:=laenge('',254,' ');
            anzeige;
          END;
        END;
        10: { ^J join line }
        if frei then if erlaubt^[zstart] THEN
        BEGIN
          t_^[zstart].put(rtrim(dummy_)+get(zstart+1));
          clear_zeile(zstart+1);
          for i:=zstart+1 to nz do t_^[i]:=t_^[i+1];
          dummy_:=laenge(get(zstart),254,' '); clear_zeile(zstart);
          t_^[nz].init; t_^[nz].put('');
          anzeige;
        END;
        3: { ^C Cut Row }
        BEGIN
          merk_:=rtrim(dummy_); clear_zeile(zstart);
          for i:=zstart to nz do t_^[i]:=t_^[i+1];
          t_^[nz].init; t_^[nz].put('');
          dummy_:=laenge(get(zstart),254,' '); clear_zeile(zstart);
          anzeige;
        END;
        16: { ^P Paste Row }
        IF nz=max_zeilen THEN write(#7)
        ELSE
        BEGIN
          INC(nz); t_^[zstart].put(rtrim(dummy_));
          for i:=nz downto zstart+1 do t_^[i]:=t_^[i-1];
          dummy_:=merk_; t_^[zstart].init; anzeige;
        END;
        19: { ^S }
        BEGIN
          j:=sstart; t_^[zstart].put(rtrim(dummy_)); found:=FALSE;
          GOTOXY(1,9); WRITELN('GEBEN SIE DEN SUCHTEXT EIN');
          sstart:=1; GOTOXY(sstart,10); WRITE(search_); CLREOL;
          REPEAT
            GOTOXY(1,10); WRITE(search_); GOTOXY(sstart,10); taste:=tastatur;
            editk(taste,sstart,1,79,search_,TRUE);
          UNTIL (taste=27) OR (taste=13);
          search_:=ucase(rtrim(ltrim(search_))); sstart:=j;
          IF taste=13 THEN
          BEGIN
            j:=sstart;
            FOR i:=zstart to nz DO
            BEGIN
              s_:=get(i);
              IF POS(search_,COPY(ucase(s_),sstart,255))<>0 THEN
              BEGIN
                zstart:=i;
                sstart:=sstart-1+maxi(smin,POS(search_,COPY(ucase(s_),sstart,255)));
                dummy_:=laenge(get(zstart),254,' '); t_^[zstart].done;
                found:=TRUE; break;
              END;
              sstart:=1;
            END;
            IF NOT found THEN sstart:=j;
            verschiebe(0);
          END;
        END;
        20: { ^T  Teile Zeile line }
        if frei then if erlaubt^[zstart] THEN
        BEGIN
          INC(nz); t_^[zstart].put(rtrim(dummy_));
          for i:=nz downto zstart+1 do t_^[i]:=t_^[i-1];
          t_^[zstart].init;
          t_^[zstart]  .put(COPY(get(zstart+1),1,sstart-1));
          t_^[zstart+1].put(COPY(get(zstart+1),sstart,255));
          dummy_:=laenge(get(zstart),254,' '); clear_zeile(zstart);
          anzeige;
        END;
        328: verschiebe( -1); { cursor oben   }
        329: verschiebe(-hi(windmax) DIV 2); { page up   }
        336: verschiebe(  1); { cursor unten }
        337: verschiebe( hi(windmax) DIV 2); { page down }
        333,372: { right, ^right }
        BEGIN
          editl(taste,sstart,smin,smax,dummy_,ins);
          WHILE sstart-xoffset>=xbreite DO INC(xoffset);
          anzeige;
        END;
        331,371: { left, ^left }
        BEGIN
          editl(taste,sstart,smin,smax,dummy_,ins);
          WHILE (LONGINT(sstart)-LONGINT(xoffset)<LONGINT(smin)+3) AND (xoffset>0) DO DEC(xoffset);
          anzeige;
        END;
        373: { ^END }
        BEGIN
          sstart:=255;
          WHILE sstart-xoffset>=xbreite DO INC(xoffset);
          verschiebe(2000); anzeige;
        END;
        375: { ^HOME }
        BEGIN
          sstart:=smin; xoffset:=0;
          verschiebe(-2000); anzeige;
        END;
        327: { home }
        BEGIN
          editl(taste,sstart,smin,smax,dummy_,ins);
          xoffset:=0; anzeige;
        END;
        335: { end }
        BEGIN
          editl(taste,sstart,smin,smax,dummy_,ins);
          WHILE sstart-xoffset>xbreite-1 DO INC(xoffset);
          anzeige;
        END;
        338: ins:=NOT ins;
        ELSE IF erlaubt^[zstart] THEN editl(taste,sstart,smin,smax,dummy_,ins) ELSE WRITE(#7);
      END;
    UNTIL FALSE;
    1:
    t_^[zstart].put(rtrim(dummy_));
  END;
  TEXTMODE(lastmode); TEXTCOLOR(7); TEXTBACKGROUND(0);
  WITH inp do
  BEGIN
    DEC(xwinl,1); DEC(ywinl,1); INC(xbreite,2); INC(ybreite,2);
  END;
END;
{-----------------------------------------------------------------------------}
END.
