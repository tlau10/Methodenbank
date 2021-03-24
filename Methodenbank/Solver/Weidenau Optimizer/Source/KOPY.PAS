unit kopy;
{$O+,F+}
INTERFACE
USES {$IFDEF msDOS} OVERLAY, {$ENDIF}
     GRAPH,util;

PROCEDURE hcopy(VAR fp:TEXT; p:STRIN; init,done:BOOLEAN);


IMPLEMENTATION

PROCEDURE hcopy(VAR fp:TEXT; p:STRIN; init,done:BOOLEAN);
VAR b,c,ps:BYTE;
    rand,a,xmax,ymax,x,nbyte,y,i,j:INTEGER;
BEGIN
  xmax:=GETMAXX; ymax:=GETMAXY;
  IF ucase(p)='LASER' THEN
  BEGIN
    rand:=7;
    nbyte:=1+(xmax div 8);
    IF init THEN
    BEGIN
      WRITE(fp,#27'E'); { RESET }
      WRITE(fp,#27'E'#27'*t100R'); { Grafik mit 100 dpi }
    END;
    WRITE(fp,#27'*rA');  { Beginn Rastergrafik an Position 0 }
    FOR y:=0 TO ymax DO
    BEGIN
      WRITE(fp,#27'*b'+ltoa(rand+nbyte)+'W');
      FOR i:=1 TO rand DO WRITE(fp,CHR(0));
      FOR i:=1 TO nbyte DO
      BEGIN
        b:=0;
        FOR j:=7 DOWNTO 0 DO IF getpixel(8*i-j-1,y)<>0 THEN INC(b,1 shl j);
        WRITE(fp,CHR(b));
      END;
    END;
    IF done THEN WRITE(fp,#27'*rB'#12); { End of Graphics }
  END
  ELSE
  IF ucase(p)='NEC' THEN
  BEGIN
    WRITE(fp,#28,'3',#47); rand:=56;
    a:=0;
    WHILE a<ymax DO
    BEGIN
      WRITE(fp,#27,'*',#38,CHR((rand+xmax+1) MOD 256),CHR((rand+xmax+1) DIV 256));
      FOR x:=1 to rand DO FOR b:=0 TO 2 DO WRITE(fp,CHR(0));
      FOR x:=0 TO xmax DO FOR b:=0 TO 2 DO
      BEGIN
        ps:=0;
        FOR c:=0 TO 3 DO IF GETPIXEL(x,a+4*b+c)<>0 THEN INC(ps,(2 SHL (6-c*2)+2 SHL (7-c*2)));
        WRITE(fp,CHR(ps));
      END;
      INC(a,12); WRITELN(fp);
    END;
  END
  ELSE
  IF ucase(p)='STAR' THEN
  BEGIN
    a:=0; WRITE(fp,#27,'A',CHR(8));
    WHILE a<=xmax DO
    BEGIN
      WRITE(fp,#27,'*',#5,CHR((ymax+1) MOD 256),CHR((ymax+1) DIV 256));
      FOR y:=ymax DOWNTO 0 DO
      BEGIN
        ps:=0;
        FOR c:=0 TO 7 DO IF GETPIXEL(a+c,y)<>0 THEN INC(ps,(1 SHL (7-c)));
        WRITE(fp,CHR(ps));
      END;
      INC(a,8); WRITELN(fp);
    END;
  END;
END;
END.