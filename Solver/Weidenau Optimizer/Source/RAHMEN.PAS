UNIT rahmen;
{$O+,F+}
INTERFACE
USES {$IFDEF msdos } OVERLAY, {$ENDIF}
     {$IFDEF win32} forms,graphics,
     {$ELSE} GRAPH,
     {$ENDIF}
     zeitrech,util;

PROCEDURE skaliere(VAR low,high,tick:SINGLE);
PROCEDURE frame(xunten,yoben,xoben,yunten,colour:INTEGER; raster:BOOLEAN;
                xlow,xhigh,ylow,yhigh,xtick,ytick:SINGLE;
                titel_,xtext_,ytext_:STRIN; xja,yja,date:BOOLEAN);
IMPLEMENTATION
{$IFDEF win32} USES fernform,fern_s; {$ENDIF}

PROCEDURE skaliere(VAR low,high,tick:SINGLE);
VAR delta,pot:SINGLE;
    wichtig:BOOLEAN;
BEGIN
  delta:=ABS(high-low);
  IF delta<1e-10 THEN
  BEGIN
    delta:=max(1e-10,ABS(low)*0.1); wichtig:=TRUE;
  END
  ELSE wichtig:=FALSE;
  pot:=pwr(10,inte(log10(delta)-0.9999));
  IF wichtig THEN
  BEGIN
    low:=low-pot/2; high:=high+pot/2; pot:=pot*0.1;
  END
  ELSE
  BEGIN
    low:=inte(low/pot)*pot; high:=inte((high+0.99999*pot)/pot)*pot;
  END;
  tick:=(high-low)/pot;
  WHILE tick<7 DO tick:=tick*2; WHILE tick>16 DO tick:=tick/2;
END;
{-----------------------------------------------------------------------------}
PROCEDURE frame(xunten,yoben,xoben,yunten,colour:INTEGER; raster:BOOLEAN;
                xlow,xhigh,ylow,yhigh,xtick,ytick:SINGLE;
                titel_,xtext_,ytext_:STRIN; xja,yja,date:BOOLEAN);
VAR xort,yort,i,n:INTEGER;
    dx,dy,x1:SINGLE;
    zahl_:STRIN;
    s_:ARRAY [0..30] OF STRING[15];

CONST size=4;
      font=2;

PROCEDURE beschrift(low,high,tick:SINGLE; date:BOOLEAN);
VAR nr,i,ef:INTEGER;
    x,faktor,pot:SINGLE;
    z:zeit_type;
BEGIN
  pot:=(high-low)/tick;
  faktor:=0; ef:=ROUND(int(-log10(pot)+0.99999));
  IF ABS(ef)>3 THEN
  BEGIN
    faktor:=pwr(10,ef-1);
    low:=low*faktor; high:=high*faktor; pot:=pot*faktor;
  END;
  IF faktor=0 THEN s_[0]:='' ELSE s_[0]:='10!^'+ltoa(ef-1)+'!n * ';
  nr:=maxi(0,ROUND(INT(-log10(pot)+0.99999)));
  FOR i:=1 TO ROUND(INT(tick)) DO
  BEGIN
    x:=low+i*(high-low)/tick;
    IF ABS(x)<1e-7 THEN x:=0;
    IF date THEN with z DO
    BEGIN
      meridian:=0; sommer:=0; zonenzeit:=0; jd:=x+reference+time_offset;
      buergerlich(z);
      s_[i]:=ltoa(tag)+'.'+ltoa(monat)+'.'+ltoa(jahr mod 100);
      {s_[i]:=ltoa(stunde)+'.'+laenge_V(ltoa(minute),2,' ');}
    END
    ELSE STR(x:0:nr,s_[i]);
  END;
END;
{-----------------------------------------------------------------------------}
BEGIN
 setcolor(colour);
 {$IFDEF win32}
 WITH tfernform.canvas DO
 BEGIN
 {$ELSE}
  SETVIEWPORT(0,0,GETMAXX,GETMAXY,TRUE);
  SETTEXTJUSTIFY(0,0); SETTEXTSTYLE(font,0,size);
  {$ENDIF}
  dx:=(xoben-xunten)/xtick; dy:=(yunten-yoben)/ytick;
  RECTANGLE(xunten,yoben,xoben,yunten);
  FOR i:=1 TO ROUND(INT(xtick)) DO
  BEGIN
    xort:=ROUND(xunten+i*dx);
    MOVETO(xort,yunten); LINETO(xort,yunten-ROUND(dx/10));
    MOVETO(xort,yoben ); LINETO(xort,yoben+ROUND(dx/10));
  END;
  FOR i:=1 TO ROUND(INT(ytick)) DO
  BEGIN
    yort:=ROUND(yunten-i*dy);
    MOVETO(xunten,yort); LINETO(xunten+ROUND(dx/10),yort);
    MOVETO(xoben,yort ); LINETO(xoben-ROUND(dx/10),yort);
  END;
  IF raster THEN
  BEGIN
    {$IFDEF win32}
     WITH pen DO
     BEGIN
       width:=1; style:=psdot;
     END;
    {$ELSE}
    SETLINESTYLE(1,0,1);
    {$ENDIF}
    FOR i:=1 TO ROUND(xtick) DO
    BEGIN
      xort:=ROUND(xunten+i*dx);
      MOVETO(xort,yunten); LINETO(xort,yoben);
    END;
    FOR i:=1 TO ROUND(ytick) DO
    BEGIN
      yort:=ROUND(yunten-i*dy);
      MOVETO(xunten,yort); LINETO(xoben,yort);
    END;
    {$IFDEF win32}
     WITH pen DO
     BEGIN
       width:=1; style:=pssolid;
     END;
    {$ELSE}
    SETLINESTYLE(0,0,1);
    {$ENDIF}
  END;
  IF xja THEN
  BEGIN
    { Achsenbeschriftung X-Achse }
    {$IFDEF win32}
    {$ELSE}
    SETTEXTSTYLE(font,0,size); OUTTEXTXY(xunten-80,yunten+30,titel_);
    SETTEXTSTYLE(font,0,size); SETTEXTJUSTIFY(1,2);
    {$ENDIF}
    beschrift(xlow,xhigh,xtick,date);
    FOR i:=1 TO ROUND(xtick) DIV 2 DO
    BEGIN
      xort:=ROUND(xunten+(i*2-1)*dx);
      {$IFDEF win32}
       textOUT(xort-textwidth(s_[i*2-1]) DIV 2,yunten+TEXTHEIGHT('A') div 2,s_[i*2-1]);
      {$ELSE}
      OUTTEXTXY(xort,yunten+TEXTHEIGHT('A') div 2,s_[i*2-1]);
      {$ENDIF}
    END;
    { Pfeile an die X-Achse }
    MOVETO(xoben,yunten+25); LINETO(xoben-50,yunten+25);
    MOVETO(xoben,yunten+25); LINETO(xoben-4,yunten+29);
    MOVETO(xoben,yunten+25); LINETO(xoben-4,yunten+21);
    {$IFDEF win32}
    {$ELSE}
    SETTEXTJUSTIFY(2,2);
    {$ENDIF}
    {$IFDEF win32}
    TEXTOUT(xoben-60,yunten+16,s_[0]+xtext_);
    {$ELSE}
    OUTTEXTXY(xoben-60,yunten+16,s_[0]+xtext_);
    {$ENDIF}
  END;
  IF yja THEN
  BEGIN
    { Achsenbeschriftung Y-Achse }
    {$IFNDEF win32} SETTEXTJUSTIFY(2,1); {$ENDIF}
    beschrift(ylow,yhigh,ytick,false);
    FOR i:=1 TO ROUND(ytick) DIV 2 DO
    BEGIN
      yort:=ROUND(yunten-(i*2-1)*dy);
      {$IFDEF win32}
      TEXTOUT(xunten-textwidth(s_[i*2-1])-5,yort-textheight(s_[i*2-1]) DIV 2,s_[i*2-1]);
      {$ELSE}
      OUTTEXTXY(xunten,yort,s_[i*2-1]);
      {$ENDIF}
    END;
    { Pfeile an die Y-Achse }
    MOVETO(xunten-70,yoben); LINETO(xunten-70,yoben+25);
    MOVETO(xunten-70,yoben); LINETO(xunten-64,yoben+4);
    MOVETO(xunten-70,yoben); LINETO(xunten-76,yoben+4);
    {$IFDEF win32}
    {$ELSE}
    SETTEXTJUSTIFY(1,2); SETTEXTSTYLE(font,1,size);
    {$ENDIF}
    {$IFDEF win32}
    TEXTOUT(xunten-70,yoben+30,s_[0]+ytext_);
    {$ELSE}
    OUTTEXTXY(xunten-70,yoben+30,s_[0]+ytext_);
    {$ENDIF}
  END;
  {$IFDEF win32}
  END;
  {$ELSE}
  SETVIEWPORT(xunten,yunten,xoben,yoben,TRUE);
  SETTEXTJUSTIFY(0,0); SETTEXTSTYLE(0,0,0);
  {$ENDIF}
END;
END.