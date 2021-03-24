UNIT screen_u;
{$O+,F+}
INTERFACE
USES {$IFDEF msdos } OVERLAY, {$ENDIF} util,CRT;

TYPE sbild_type=ARRAY [0..7999] OF WORD;
     bildschirm=OBJECT
                  sbild:^sbild_type;
                  wox,woy:INTEGER;
                  winlo,winhi:WORD;
                  CONSTRUCTOR init;
                  DESTRUCTOR  done;
                  PROCEDURE   save;
                  PROCEDURE   restore;
                  PROCEDURE   dump;
                END;

IMPLEMENTATION

CONSTRUCTOR bildschirm.init;
BEGIN
  NEW(sbild);
END;
{----------------------------------------------------------------------------}
DESTRUCTOR bildschirm.done;
BEGIN
  DISPOSE(sbild); sbild:=NIL;
END;
{----------------------------------------------------------------------------}
PROCEDURE bildschirm.save;
VAR i:INTEGER;
BEGIN
  winlo:=windmin; winhi:=windmax; wox:=WHEREX; woy:=WHEREY;
  FOR i:=0 TO 7999 DO sbild^[i]:=memw[segb800:i];
END;
{----------------------------------------------------------------------------}
PROCEDURE bildschirm.dump;
VAR j,i:INTEGER;
    fp:TEXT;
    s_:STRIN;
BEGIN
  WRITE('NAME : ');
  READLN(s_); ASSIGNFILE(fp,s_); REWRITE(fp);
  FOR i:=0 TO 49 DO
  BEGIN
    FOR j:=0 TO 79 DO WRITE(fp,CHR(sbild^[160*i+2*j]));
    WRITELN(fp);
  END;
  CLOSEFILE(fp);
END;
{----------------------------------------------------------------------------}
PROCEDURE bildschirm.restore;
VAR i:INTEGER;
BEGIN
  TEXTMODE(lastmode);
  WINDOW(LO(winlo)+1,hi(winlo)+1,LO(winhi)+1,hi(winhi)+1);
  {winlo:=windmin; winhi:=windmax; } GOTOXY(wox,woy);
  FOR i:=0 TO 7999 DO memw[segb800:i]:=sbild^[i];
END;
{----------------------------------------------------------------------------}
END.
