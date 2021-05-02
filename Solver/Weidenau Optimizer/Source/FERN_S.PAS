UNIT fern_s;
{$O+,F+}
INTERFACE
USES {$IFDEF msdos } OVERLAY, {$ENDIF}
     {$IFDEF win32}
       forms,graphics,windows,sysutils,
     {$ELSE}
       CRT,GRAPH,kopy,grafik,
     {$ENDIF}
       rahmen,util;

TYPE fernseh_type=RECORD
                    quad,anfang,ende:INTEGER;
                    xlow,xhigh,ylow,yhigh,xtick,ytick,integral:SINGLE;
                    desti_,titel_,xtext_,ytext_,kontrol_:STRIN;
                  END;
{$IFOPT N+}
CONST maxn=16000;
{$ELSE}
CONST maxn=10600;
{$ENDIF}
PROCEDURE fernseh(VAR x2,y2; VAR fern:fernseh_type);
{$IFDEF win32} PROCEDURE setcolor(colour:LONGINT); {$ENDIF}
IMPLEMENTATION

{$IFDEF win32} USES fernform;

PROCEDURE setcolor(colour:LONGINT);
BEGIN
  tfernform.caption:='Color='+ltoa(colour);
  WITH tfernform.canvas.pen DO
  CASE colour OF
    0: color:=clwhite;
    1: color:=clblue;
    2: color:=clgreen;
    3: color:=clnavy;
    4: color:=clred;
    5: color:=clfuchsia;
    6: color:=clteal;
    7,8: color:=clgray;
    9: color:=clmaroon;
    10:color:=cllime;
    11:color:=claqua;
    12:color:=clpurple;
    13:color:=clolive;
    14:color:=clsilver;
    15:color:=clblack;
  END;
  WITH tfernform.paintbox1.canvas.pen DO
  CASE colour OF
    0: color:=clwhite;
    1: color:=clblue;
    2: color:=clgreen;
    3: color:=clnavy;
    4: color:=clred;
    5: color:=clfuchsia;
    6: color:=clteal;
    7,8: color:=clgray;
    9: color:=clmaroon;
    10:color:=cllime;
    11:color:=claqua;
    12:color:=clpurple;
    13:color:=clolive;
    14:color:=clsilver;
    15:color:=clblack;
  END;
  WITH tfernform.canvas.font DO
  CASE colour OF
    0: color:=clwhite;
    1: color:=clblue;
    2: color:=clgreen;
    3: color:=clnavy;
    4: color:=clred;
    5: color:=clfuchsia;
    6: color:=clteal;
    7,8: color:=clgray;
    9: color:=clmaroon;
    10:color:=cllime;
    11:color:=claqua;
    12:color:=clpurple;
    13:color:=clolive;
    14:color:=clsilver;
    15:color:=clblack;
  END;
END;
{$ENDIF}
PROCEDURE fernseh;

CONST be=3;

VAR xu,xo,yu,yo,i,xunten,xoben,yunten,yoben,lt,smode,code:INTEGER;
    colour:LONGINT;
    xlog,ylog,raster:BOOLEAN;
    xort,yort:INTEGER;
    x1,y1,xbereich,ybereich,xfaktor,yfaktor:SINGLE;
    z:DOUBLE;
    s_:STRING[10];
    c:CHAR;
    buf:POINTER;
    x:ARRAY [0..maxn] OF SINGLE ABSOLUTE x2;
    y:ARRAY [0..maxn] OF SINGLE ABSOLUTE y2;
    buff:^buffer;
    fp:TEXT;
    {-----------------------------------------------------------------------------}
{$IFNDEF win32}
PROCEDURE kreuz(ak:INTEGER; y:SINGLE);
BEGIN
  WITH fern DO
  BEGIN
    SETFILLSTYLE(0,0); BAR(0,0,80,20);
    OUTTEXTXY(0,10,ftoa(x[ak])); OUTTEXTXY(0,20,ftoa(y));
    xort:=ROUND(max(xunten+be,min(GETMAXX-be,(x[ak]-xlow)*xfaktor)+xunten));
    yort:=ROUND(max(yoben+be,min(yunten-be,(yhigh-y)*yfaktor)+yoben));
    GETIMAGE(xort-be,yort-be,xort+be,yort+be,buf^);
    LINE(xort-be,yort,xort+be,yort); LINE(xort,yort-be,xort,yort+be);
  END;
END;
{-----------------------------------------------------------------------------}
PROCEDURE inkrement(VAR a:INTEGER; VAR yk:SINGLE);
VAR taste:INTEGER;
BEGIN
  kreuz(a,y[a]); yk:=y[a];
  WITH fern DO
  REPEAT
    taste:=tastatur;
    CASE taste OF 
      333: BEGIN   { nach rechts } 
        PUTIMAGE(xort-be,yort-be,buf^,0); INC(a);
        IF a>ende THEN a:=anfang; 
        yk:=y[a]; kreuz(a,yk); 
      END;
      331: BEGIN   { nach links }
        PUTIMAGE(xort-be,yort-be,buf^,0); DEC(a);
        IF a<anfang THEN a:=ende; 
        yk:=y[a]; kreuz(a,yk);
      END; 
      372: BEGIN   { 10 nach rechts } 
        PUTIMAGE(xort-be,yort-be,buf^,0); INC(a,10);
        IF a>ende THEN a:=anfang;
        yk:=y[a]; kreuz(a,yk); 
      END; 
      371: BEGIN   { 10 nach links } 
        PUTIMAGE(xort-be,yort-be,buf^,0); DEC(a,10);
        IF a<anfang THEN a:=ende; 
        yk:=y[a]; kreuz(a,yk); 
      END; 
      328: BEGIN   { nach oben } 
        PUTIMAGE(xort-be,yort-be,buf^,0);
        yk:=yk+(yhigh-ylow)/200; kreuz(a,yk); 
      END; 
      336: BEGIN   { nach unten } 
        PUTIMAGE(xort-be,yort-be,buf^,0);
        yk:=yk-(yhigh-ylow)/200; kreuz(a,yk); 
      END;
    END; 
  UNTIL taste=13; 
END; 
{-----------------------------------------------------------------------------} 
PROCEDURE integriere;
VAR a,e,i:INTEGER; 
    hilf,ya,ye,ya1,ya2:SINGLE; 
    siz:WORD;
BEGIN 
  siz:=IMAGESIZE(xort-be,yort-be,xort+be,yort+be); GETMEM(buf,siz);
  a:=fern.anfang; inkrement(a,ya); e:=a; inkrement(e,ye);
  IF a>e THEN
  BEGIN
    hilf:=e; e:=a; a:=ROUND(hilf); hilf:=ya; ya:=ye; ye:=hilf;
  END;
  IF a<>e THEN
  BEGIN
    SETVIEWPORT(xunten,yoben,xoben,yunten,TRUE);
    FOR i:=a TO e-1 DO
    BEGIN
      ya1:=ya+(ye-ya)*(x[i]-x[a])/(x[e]-x[a]);
      ya2:=ya+(ye-ya)*(x[i+1]-x[a])/(x[e]-x[a]);
      fern.integral:=fern.integral+(y[i]+y[i+1]-ya1-ya2)*(x[i+1]-x[i])*0.5;
      LINE(ROUND((x[i]-fern.xlow)*xfaktor),ROUND((fern.yhigh-ya1)*yfaktor),
      ROUND((x[i]-fern.xlow)*xfaktor),ROUND((fern.yhigh-y[i])*yfaktor));
    END;
    LINE(ROUND((x[e]-fern.xlow)*xfaktor),ROUND((fern.yhigh-ya2)*yfaktor),
    ROUND((x[e]-fern.xlow)*xfaktor),ROUND((fern.yhigh-y[e])*yfaktor));
  END;
  FREEMEM(buf,siz);
END;
{$ENDIF}
{-----------------------------------------------------------------------------}
PROCEDURE male;
BEGIN
  {$IFDEF win32}
  WITH tfernform.paintbox1.canvas DO
  BEGIN
    tfernform.caption:='Color='+ltoa(pen.color);
  {$ENDIF}
  WITH fern DO
  CASE smode OF
    0: IF i=anfang THEN MOVETO(xort,yort) ELSE LINETO(xort,yort);
    1:{$IFDEF win32}
       pixels[xort,yort]:=colour;
      {$ELSE}
        PUTPIXEL(xort,yort,colour);
      {$ENDIF}
    2:
    BEGIN
      MOVETO(xort-2,yort); LINETO(xort+2,yort); MOVETO(xort,yort-2); LINETO(xort,yort+2);
    END;
    3: RECTANGLE(xort-2,yort+2,xort+2,yort-2);
    4:
    BEGIN
      MOVETO(xort-3,yort+1); LINETO(xort+3,yort+1); MOVETO(xort+3,yort+1); LINETO(xort,yort-3);
      MOVETO(xort,yort-4); LINETO(xort-3,yort+1);
    END;
    5: {$IFDEF win32} ellipse(xort-3,yort-3,xort+3,yort+3); {$ELSE} CIRCLE(xort,yort,3); {$ENDIF}
  END;
  {$IFDEF win32} END; {$ENDIF} { fernform.canvas }
END;
{-----------------------------------------------------------------------------}
{$IFDEF win32}
FUNCTION getmaxx:INTEGER;
BEGIN
  getmaxx:=ROUND(tfernform.width);
END;
FUNCTION getmaxy:INTEGER;
BEGIN
  getmaxy:=ROUND(tfernform.height);
END;
PROCEDURE cleardevice;
BEGIN
  tfernform.canvas.refresh;
END;
{$ENDIF}
BEGIN
  {$IFDEF win32}
  tfernform.windowstate:=WsMaximized;
  {$ENDIF}
  WITH fern DO
  BEGIN
    CASE quad OF
      0:
      BEGIN
        xu:=0; xo:=GETMAXX-10;
        yu:=GETMAXY-30; yo:=0;
      END;
      1:
      BEGIN
        xu:=0; xo:=GETMAXX DIV 2-1;
        yu:=GETMAXY DIV 2-1; yo:=0;
      END;
      2:
      BEGIN
        xu:=GETMAXX DIV 2+1; xo:=GETMAXX;
        yu:=GETMAXY DIV 2-1; yo:=0;
      END;
      3:
      BEGIN
        xu:=0; xo:=GETMAXX DIV 2-1;
        yo:=GETMAXY DIV 2+1; yu:=GETMAXY;
      END;
      4:
      BEGIN
        xu:=GETMAXX DIV 2+1; xo:=GETMAXX;
        yo:=GETMAXY DIV 2+1; yu:=GETMAXY;
      END;
      ELSE
      BEGIN
        {$IFNDEF win32}textm; {$ENDIF}
        my_message('QUAD set to '+ltoa(quad));
       {$IFNDEF win32}grafikm; {$ENDIF}
      END;
    END;
    xunten:=xu+80; xoben:=xo-2; yoben:=yo+2; yunten:=yu-30;
    kontrol_:=ucase(kontrol_);
    IF POS('C',kontrol_)<>0  THEN CLEARDEVICE;
    {$IFDEF win32}
    {
    WITH tfernform DO
    BEGIN
      left:=0; top:=0; width:=xo-xu; height:=yu-yo;
    END;
    }
    WITH tfernform.paintbox1 DO
    BEGIN
      left:=xunten; top:=yoben; width:=xoben-xunten; height:=yunten-yoben;
    END;
    {$ELSE}
    SETVIEWPORT(xu,yo,xo,yu,TRUE);
    {$ENDIF}
    xlog:=POS('X',kontrol_)<>0; ylog:=POS('Y',kontrol_)<>0;
    raster:=POS('R',kontrol_)<>0; smode:=POS('S',kontrol_);
    IF smode<>0 THEN
    BEGIN
      VAL(COPY(kontrol_,smode+1,1),smode,code);
      IF code<>0 THEN smode:=0;
      smode:=mini(smode,5);
    END;
    lt:=POS('L',kontrol_);
    IF lt<>0 THEN
    BEGIN
      VAL(COPY(kontrol_,lt+1,1),lt,code);
      IF code<>0 THEN lt:=0;
      lt:=mini(lt,3);
    END;
    IF xlog THEN xtext_:='log('+xtext_+')';
    IF ylog THEN ytext_:='log('+ytext_+')';
    IF POS('A',kontrol_)<>0 THEN
    BEGIN
      xlow:=1.0e38; ylow:=1.0e38; xhigh:=-1.0e38; yhigh:=-1.0e38;
      FOR i:=anfang TO ende DO
      BEGIN
        IF xlog THEN
        BEGIN
          xlow:=min(xlow,log10(x[i])); xhigh:=max(xhigh,log10(x[i]));
        END
        ELSE
        BEGIN
          xlow:=min(xlow,x[i]); xhigh:=max(xhigh,x[i]);
        END;
        IF ylog THEN
        BEGIN
          ylow:=min(ylow,log10(y[i])); yhigh:=max(yhigh,log10(y[i]));
        END
        ELSE
        BEGIN
          ylow:=min(ylow,y[i]); yhigh:=max(yhigh,y[i]);
        END;
      END;
      skaliere(xlow,xhigh,xtick); skaliere(ylow,yhigh,ytick);
    END
    ELSE
    BEGIN
      IF xlog THEN
      BEGIN
        xlow:=log10(xlow); xhigh:=log10(xhigh);
      END;
      IF ylog THEN
      BEGIN
        ylow:=log10(ylow); yhigh:=log10(yhigh);
      END;
    END;
    xbereich:=xhigh-xlow; ybereich:=yhigh-ylow;
    xfaktor:=(xoben-xunten)/xbereich; yfaktor:=(yunten-yoben)/ybereich;
    IF POS('F',kontrol_)<>0 THEN { Rahmen zeichnen }
    BEGIN
      colour:=15; i:=POS('F',kontrol_);
      IF i>0 THEN
      BEGIN
        INC(i); s_:='';
        WHILE (i<=LENGTH(kontrol_)) AND (kontrol_[i] IN ['0'..'9']) DO
        BEGIN
          s_:=s_+kontrol_[i]; INC(i);
        END;
        IF s_<>'' THEN colour:=lese_int(s_);
      END;
      setcolor(colour);
      frame(xunten,yoben,xoben,yunten,colour,raster,xlow,xhigh,ylow,yhigh,xtick,ytick,
      titel_,xtext_,ytext_,TRUE,TRUE,POS('D',kontrol_)<>0);
    END;
    {$IFDEF win32}
    WITH tfernform.canvas.pen DO
    BEGIN
      width:=1;
      CASE lt OF
        0: style:=pssolid;
        1: style:=PsDash;
        2: style:=PsDot;
        3: style:=PsDashDot;
        4: style:=psClear;
      END;
    END;
    {$ELSE}
    SETLINESTYLE(lt,0,1);
    {$ENDIF}
    colour:=15;
    IF POS('N',kontrol_)<>0 THEN
    BEGIN
      i:=POS('N',kontrol_);
      IF i>0 THEN
      BEGIN
        INC(i); s_:='';
        WHILE (i<=LENGTH(kontrol_)) AND (kontrol_[i] IN ['0'..'9']) DO
        BEGIN
          s_:=s_+kontrol_[i]; INC(i);
        END;
        IF s_<>'' THEN colour:=lese_int(s_);
      END;
    END;
    SETCOLOR(colour);
    { Clipping auf den Plotbereich }
    {$IFDEF win32}
    colour:=tfernform.paintbox1.canvas.pen.color;
    {$ELSE}
    SETVIEWPORT(xunten,yoben,xoben,yunten,TRUE);
    {$ENDIF}
    FOR i:=anfang TO ende DO
    BEGIN
      IF xlog THEN x1:=log10(x[i]) ELSE x1:=x[i];
      IF ylog THEN y1:=log10(y[i]) ELSE y1:=y[i];
      xort:=ROUND((x1-xlow)*xfaktor);
      yort:=ROUND((yhigh-y1)*yfaktor);
      male;
    END;
    {$IFNDEF win32}
    SETLINESTYLE(0,0,1); SETVIEWPORT(0,0,GETMAXX,GETMAXY,TRUE);
    IF (NOT xlog) AND (NOT ylog) AND (POS('I',kontrol_)<>0) THEN integriere;
    {$ENDIF}
    IF xlog THEN
    BEGIN
      xlow:=pwr(10,xlow); xhigh:=pwr(10,xhigh);
    END;
    IF ylog THEN
    BEGIN
      ylow:=pwr(10,ylow); yhigh:=pwr(10,yhigh);
    END;
    IF POS('W',kontrol_)<>0 THEN
    BEGIN
    {$IFDEF win32}
      pressed:=FALSE; z:=julia;
      REPEAT application.processmessages; UNTIL pressed OR (julia-z>10);
    {$ELSE}
      c:=READKEY;
    {$ENDIF}
    END;
  END;
END;
END.
    {$IFNDEF win32}
    IF POS('H',kontrol_)<>0 THEN
    BEGIN
      NEW(buff); ASSIGNFILE(fp,'LPT3'); SETTEXTBUF(fp,buff^); REWRITE(fp);
      hcopy(fp,'LASER',TRUE,TRUE); CLOSEFILE(fp); DISPOSE(buff);
    END;
    IF POS('W',kontrol_)<>0 THEN c:=READKEY;
    {$ENDIF}

