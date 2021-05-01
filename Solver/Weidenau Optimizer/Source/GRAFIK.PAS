UNIT grafik;
INTERFACE

USES GRAPH,CRT;

VAR grmode:INTEGER;

PROCEDURE myexit;
PROCEDURE GRAFIKM;
PROCEDURE TEXTM;
PROCEDURE HERCULES(a,b:INTEGER; PATH_:STRING);
IMPLEMENTATION
VAR exitsave:POINTER;
    lmode:INTEGER;
{-----------------------------------------------------------------------------}
{$F+} PROCEDURE myexit; {$F-}
BEGIN
  CLOSEGRAPH; EXITPROC:=exitsave;
END;
{-----------------------------------------------------------------------------}
PROCEDURE GRAFIKM;
BEGIN
  lmode:=lastmode;
  SETGRAPHMODE(grmode); exitsave:=EXITPROC; EXITPROC:=@myexit;
END;
{-----------------------------------------------------------------------------}
PROCEDURE TEXTM;
BEGIN
  RESTORECRTMODE; EXITPROC:=exitsave;
  IF lmode=3 THEN TEXTMODE(lmode AND 255) ELSE TEXTMODE(lmode OR 255);
END;
{-----------------------------------------------------------------------------}
PROCEDURE HERCULES(a,b:INTEGER; path_:STRING);
VAR c:CHAR;
    a1:INTEGER;
BEGIN
  DETECTGRAPH(a1,grmode);
  if a=0 THEN
  BEGIN
    a:=a1; b:=grmode;
  END;
  IF a1<>a THEN
  BEGIN
    WRITELN(a1,' ',grmode,' Other graphic-card found ! Continue anyway ?  (Y/N)');
    c:=READKEY; IF (c<>'y') AND (c<>'Y') THEN HALT;
  END;
  INITGRAPH(a,b,path_);
  IF GRAPHRESULT<>0 THEN
  BEGIN
    RESTORECRTMODE;
    WRITE('Error in graphic'#13#10'Press any key to continue');
    c:=READKEY; HALT;
  END;
  RESTORECRTMODE;
END;
END.
