UNIT util;

INTERFACE
USES {$IFDEF win32} DIALOGS,CONTROLS,FORMS,SYSUTILS; {$ELSE} DOS,CRT; {$ENDIF}

TYPE buffer=ARRAY [1..6000] OF DOUBLE;
     {$IFOPT N-} SINGLE=REAL;
                 DOUBLE=REAL;
                 EXTENDED=REAL;
                 {$ENDIF}
     STRIN=STRING[255];
     {$IFDEF win32} WORD=INTEGER; {$ENDIF}
     pstring=^STRIN;
     spp=OBJECT
           sp_:pstring;
           CONSTRUCTOR init;
           DESTRUCTOR  done;
           PROCEDURE   put(CONST ein_:STRIN);
           FUNCTION    get:STRIN;
           FUNCTION    get_char(position:BYTE):CHAR;
         END;
{$IFDEF extended}
     double=extended;
     {$endif}
{$IFDEF win32}
TYPE TEXT=TEXTFILE;
{$ENDIF}

PROCEDURE era(s_:STRIN);
PROCEDURE editk(taste:WORD; VAR curs:WORD; curmin,curmax:WORD; VAR dummy_:STRIN; ins:BOOLEAN);
PROCEDURE editl(taste:WORD; VAR curs:WORD; curmin,curmax:WORD; VAR dummy_:STRIN; ins:BOOLEAN);
PROCEDURE zerhacke(VAR z_,rest_:STRIN);
PROCEDURE file_test(t_:STRIN);
PROCEDURE zerha(VAR z_,rest_:STRIN);
PROCEDURE swap_DOUBLE(VAR x1,x2:DOUBLE);
PROCEDURE swap_single(VAR x1,x2:single);
PROCEDURE swap_integer(VAR x1,x2:INTEGER);
PROCEDURE swap_longint(VAR x1,x2:LONGINT);
PROCEDURE swap_boolean(VAR x1,x2:BOOLEAN);
PROCEDURE swap_word(VAR x1,x2:WORD);
PROCEDURE swap_pointer(VAR x1,x2:POINTER);
PROCEDURE my_message(CONST s_:STRIN);
FUNCTION laenge(CONST s_:STRIN; CONST n:BYTE; CONST c:CHAR):STRIN;
FUNCTION laenge_V(CONST s_:STRIN; CONST n:BYTE; CONST c:CHAR):STRIN;
FUNCTION lese_zahl(VAR z_:STRIN):DOUBLE;
FUNCTION lese_int(VAR z_:STRIN):LONGINT;
FUNCTION ftoa(x:DOUBLE):STRIN;
FUNCTION dtoa(x:DOUBLE):STRIN;
FUNCTION ftoa_x(x:DOUBLE; n1,n2:INTEGER):STRIN;
FUNCTION ftoae(x:DOUBLE):STRIN;
FUNCTION arccos(x:DOUBLE):DOUBLE;
FUNCTION arcsin(x:DOUBLE):DOUBLE;
FUNCTION tan(x:DOUBLE):DOUBLE;
FUNCTION log10(x:DOUBLE):DOUBLE;
FUNCTION sgn(x:DOUBLE):DOUBLE;
FUNCTION min(x,y:DOUBLE):DOUBLE;
FUNCTION inte(x:DOUBLE):DOUBLE;
FUNCTION max(x,y:DOUBLE):DOUBLE;
FUNCTION pwr(x,y:DOUBLE):DOUBLE;
FUNCTION ucase(s:STRIN):STRIN;
FUNCTION lcase(c:CHAR):CHAR;
FUNCTION lowcase(s:STRIN):STRIN;
FUNCTION maxi(a,b:LONGINT):LONGINT;
FUNCTION mini(a,b:LONGINT):LONGINT;
FUNCTION ltrim(s:STRIN):STRIN;
FUNCTION rtrim(s:STRIN):STRIN;
FUNCTION ltoa(zahl:LONGINT):STRIN;
FUNCTION ltoa_x(zahl,stellen:LONGINT):STRIN;
FUNCTION wtoa(zahl:WORD):STRIN;
FUNCTION btoa(zahl:BOOLEAN):STRIN;
FUNCTION ltobin(zahl:LONGINT):STRIN;
FUNCTION ltohex(zahl:LONGINT):STRIN;
FUNCTION wie_spaet:DOUBLE;
FUNCTION wieviel_float(VAR s_:STRIN):BYTE;
FUNCTION wieviel_var(VAR s_:STRIN):BYTE;
FUNCTION wieviel_int(VAR s_:STRIN):BYTE;
FUNCTION julia:DOUBLE;
{$IFNDEF win32}
PROCEDURE assignfile(VAR fp:TEXT; s_:STRIN);
PROCEDURE closefile(VAR fp:TEXT);
PROCEDURE wait;
FUNCTION tastatur:INTEGER;
{$ENDIF}
{$IFNDEF win32}
FUNCTION alt_pressed:BOOLEAN;
FUNCTION drucker_test(dest_:STRIN):BOOLEAN;
FUNCTION control_pressed:BOOLEAN;
{$ENDIF}

{$IFDEF win32}
CONST black=0;
      green=2;
      cyan=3;
      red=4;
      magenta=5;
      lightgray=7;
      lightmagenta=13;
      yellow=14;
VAR lastmode,windmax,windmin:WORD;
{$ENDIF}

IMPLEMENTATION

{$IFNDEF win32}

PROCEDURE assignfile(VAR fp:TEXT; s_:STRIN);
BEGIN
  ASSIGN(fp,s_);
END;
{---------------------------------------------------------------------------}
PROCEDURE closefile(VAR fp:TEXT);
BEGIN
  CLOSE(fp);
END;
{---------------------------------------------------------------------------}
FUNCTION tastatur:INTEGER;   { Wenn eine normale Taste gerÅckt wird, liefert
tastatur den ASCII-Wert zurÅck. Bei Sonder-
zeichen liefert Scancode +255 zurÅck }
VAR c:CHAR;
    i:INTEGER;
BEGIN
  c:=READKEY; i:=ORD(c);
  IF i=0 THEN i:=256+ORD(READKEY);
  tastatur:=i;
END;
{-----------------------------------------------------------------------------}
PROCEDURE wait;
VAR i:INTEGER;
    x:SINGLE;
BEGIN
  IF WHEREX>1 THEN WRITELN;
  WRITELN('Press any key to continue'); x:=wie_spaet;
  REPEAT
    { wait for a pressed key or until 10 s are over }
  UNTIL KEYPRESSED OR (wie_spaet-x>10) OR (wie_spaet<x-0.1);
  IF KEYPRESSED THEN i:=tastatur;
END;
{$ENDIF}
{--------------------------------------------------------------------------}
PROCEDURE era(s_:STRIN);
VAR fp:TEXT;
BEGIN
  ASSIGNFILE(fp,s_); {$I-} ERASE(fp); {$I+} IF IORESULT<>0 THEN;
END;
{----------------------------------------------------------------------------}
FUNCTION spp.get:STRIN;
BEGIN
  IF sp_=nil THEN get:='' ELSE get:=sp_^;
END;
{-----------------------------------------------------------------------------}
FUNCTION spp.get_char(position:BYTE):CHAR;
BEGIN
  get_char:=sp_^[position];
END;
{-----------------------------------------------------------------------------}
PROCEDURE spp.put(CONST ein_:STRIN);
BEGIN
  IF sp_<>nil THEN FREEMEM(sp_,LENGTH(sp_^)+1);
  GETMEM(sp_,LENGTH(ein_)+1); sp_^:=ein_;
END;
{-----------------------------------------------------------------------------}
DESTRUCTOR spp.done;
BEGIN
  IF sp_<>nil THEN FREEMEM(sp_,LENGTH(sp_^)+1);
  sp_:=NIL;
END;
{-----------------------------------------------------------------------------}
CONSTRUCTOR spp.init;
BEGIN
  sp_:=NIL;
END;
{--------------------------------------------------------------------------}
PROCEDURE my_message(CONST s_:STRIN);
BEGIN
  {$IFDEF win32}
  IF MessageDlg(s_,mtcustom,mbOkCancel,0)=mrOK THEN;
  {$ELSE}
  WRITELN(s_); wait;
  {$ENDIF}
END;
{---------------------------------------------------------------------------}
FUNCTION wieviel_int(VAR s_:STRIN):BYTE;
VAR x,l,code:INTEGER;
BEGIN
  l:=POS('*',s_); IF l=0 THEN l:=LENGTH(s_); {$I-} VAL(s_,x,code);
  IF code<>0 THEN
  WHILE (code<>0) AND (l>1) DO
  BEGIN
    DEC(l); VAL(COPY(s_,1,l),x,code);
  END;
  IF code<>0 THEN l:=0;
  wieviel_int:=l; {$I+}
END;
{---------------------------------------------------------------------------}
FUNCTION wieviel_float(VAR s_:STRIN):BYTE;
VAR l,code:INTEGER;
    x:DOUBLE;
BEGIN
  l:=POS('*',s_); IF l=0 THEN l:=LENGTH(s_); {$I-} VAL(s_,x,code);
  IF code<>0 THEN
  WHILE (code<>0) AND (l>1) DO
  BEGIN
    DEC(l);
    VAL(COPY(s_,1,l),x,code);
  END;
  IF code<>0 THEN l:=0;
  wieviel_float:=l; {$I+}
END;
{---------------------------------------------------------------------------}
FUNCTION wieviel_var(VAR s_:STRIN):BYTE;
VAR l:INTEGER;
BEGIN
  l:=0;
  IF s_[1] IN ['A'..'Z','_','a'..'z','·','Ê'] THEN
  WHILE (l<LENGTH(s_)) AND (s_[l+1] IN ['A'..'Z','a'..'z','_','0'..'9','·','Ê']) DO INC(l);
  wieviel_var:=l;
END;
{---------------------------------------------------------------------------}
PROCEDURE swap_double(VAR x1,x2:DOUBLE);
VAR temp:DOUBLE;
BEGIN
  temp:=x1; x1:=x2; x2:=temp;
END;
{-----------------------------------------------------------------------------}
PROCEDURE swap_pointer(VAR x1,x2:POINTER);
VAR temp:POINTER;
BEGIN
  temp:=x1; x1:=x2; x2:=temp;
END;
{-----------------------------------------------------------------------------}
PROCEDURE swap_single(VAR x1,x2:SINGLE);
VAR temp:SINGLE;
BEGIN
  temp:=x1; x1:=x2; x2:=temp;
END;
{-----------------------------------------------------------------------------}
PROCEDURE swap_longint(VAR x1,x2:LONGINT);
VAR temp:LONGINT;
BEGIN
  temp:=x1; x1:=x2; x2:=temp;
END;
{-----------------------------------------------------------------------------}
PROCEDURE swap_integer(VAR x1,x2:INTEGER);
VAR temp:INTEGER;
BEGIN
  temp:=x1; x1:=x2; x2:=temp;
END;
{-----------------------------------------------------------------------------}
PROCEDURE swap_word(VAR x1,x2:WORD);
VAR temp:WORD;
BEGIN
  temp:=x1; x1:=x2; x2:=temp;
END;
{-----------------------------------------------------------------------------}
PROCEDURE swap_boolean(VAR x1,x2:BOOLEAN);
VAR temp:BOOLEAN;
BEGIN
  temp:=x1; x1:=x2; x2:=temp;
END;
{-----------------------------------------------------------------------------}
PROCEDURE zerha(VAR z_,rest_:STRIN);
VAR a:INTEGER;
BEGIN
  a:=POS(',',rest_);
  IF a<>0 THEN
  BEGIN
    z_:=COPY(rest_,1,a-1); rest_:=ltrim(COPY(rest_,a+1,255));
  END
  ELSE
  BEGIN
    z_:=rest_; rest_:='';
  END;
END;
{-----------------------------------------------------------------------------}
FUNCTION laenge(CONST s_:STRIN; CONST n:BYTE; CONST c:CHAR):STRIN;
VAR t_:STRIN;
BEGIN
  t_:=s_;
  WHILE length(t_)<n DO t_:=t_+c;
  laenge:=t_;
END;
{-----------------------------------------------------------------------------}
FUNCTION laenge_V(CONST s_:STRIN; CONST n:BYTE; CONST c:CHAR):STRIN;
VAR t_:STRIN;
BEGIN
  t_:=s_;
  WHILE length(t_)<n DO t_:=c+t_;
  laenge_v:=t_;
END;
{-----------------------------------------------------------------------------}
FUNCTION ftoa_x(x:DOUBLE; n1,n2:INTEGER):STRIN;
VAR s_:STRIN;
BEGIN
  STR(x:n1:n2,s_); ftoa_x:=s_;
END;
{-----------------------------------------------------------------------------}
FUNCTION wie_spaet:DOUBLE;
{ Wie_spaet liefert die Zeit in Sekunden seit Mitternacht, wenn die Uhr stimmt }
VAR hour,min,sec,sec100:WORD;
BEGIN
  {$IFDEF win32} wie_spaet:=frac(time)*86400;{$ELSE}
  GETTIME(hour,min,sec,sec100); wie_spaet:=hour*3600.0+min*60+sec+sec100/100;
  {$ENDIF}
END;
{-----------------------------------------------------------------------------}
FUNCTION julia:DOUBLE;
VAR m1:INTEGER;
    year,month,day,dayofweek:WORD;
    hour,min,sec,sec100:WORD;
    jd:DOUBLE;
BEGIN
  {$IFDEF win32}
  julia:=86400.0*(now-35000.0);
  {$ELSE}
  getdate(year,month,day,dayofweek); DEC(year,1990);
  GETTIME(hour,min,sec,sec100);
  IF year<0 THEN INC(year);
  IF month>3 THEN m1:=month
  ELSE
  BEGIN
    DEC(year); m1:=month+12;
  END;
  jd:=year DIV 100; jd:=2.0-jd+TRUNC(jd/4);
  IF year<0 THEN jd:=-INT(-365.25*year+0.75)+INT(30.6001*(m1+1))+day
  ELSE jd:=jd+INT(365.25*year)+INT(30.6001*(m1+1))+day;
  julia:=86400*jd+hour*3600.0+min*60+sec+sec100/100;;
  {$ENDIF}
END;
{-----------------------------------------------------------------------------}
FUNCTION log10(x:DOUBLE):DOUBLE;   { Logarithmus zur Basis 10 }
CONST fact=2.302585;
BEGIN
  log10:=LN(x)/fact;
END;
{-----------------------------------------------------------------------------}
FUNCTION arccos(x:DOUBLE):DOUBLE;
BEGIN
  IF x>=1.0 THEN arccos:=0.0
  ELSE IF x<=-1.0 THEN arccos:=PI
  ELSE arccos:=PI/2-ARCTAN(x/SQRT(1-SQR(x)));
END;
{-----------------------------------------------------------------------------}
FUNCTION tan(x:DOUBLE):DOUBLE;
BEGIN
  IF x=pi/2 THEN tan:=1e30 ELSE IF x=-pi/2 THEN tan:=-1e30 ELSE tan:=sin(x)/cos(x);
END;
{-----------------------------------------------------------------------------}
FUNCTION arcsin(x:DOUBLE):DOUBLE;
BEGIN
  IF x>=1 THEN arcsin:=PI/2
  ELSE IF x<=-1 THEN arcsin:=-PI/2
  ELSE arcsin:=ARCTAN(x/SQRT(1-SQR(x)));
END;
{-----------------------------------------------------------------------------}
FUNCTION rtrim(s:STRIN):STRIN; {Entfernt von einem STRIN; hinten Leerzeichen}
VAR l:INTEGER;
BEGIN
  l:=LENGTH(s);
  WHILE (l>0) AND (s[l]=' ') DO DEC(l);
  rtrim:=COPY(s,1,l);
END;
{-----------------------------------------------------------------------------}
FUNCTION min(x,y:DOUBLE):DOUBLE;   { Min leifert die kleinere der beiden Zahlen zuruÅck }
BEGIN
  IF x<y THEN min:=x ELSE min:=y;
END;
{-----------------------------------------------------------------------------}
FUNCTION max(x,y:DOUBLE):DOUBLE;   { Max liefert die grî·ere der beiden Zahlen zurÅck }
BEGIN
  IF x>y THEN max:=x ELSE max:=y;
END;
{-----------------------------------------------------------------------------}
FUNCTION inte(x:DOUBLE):DOUBLE;    { Liefert die grî·te ganze Zahl kleiner=x
zurÅck}
BEGIN
  IF x<0 THEN inte:=INT(x-0.999999999) ELSE inte:=INT(x);
END;
{-----------------------------------------------------------------------------}
FUNCTION pwr(x,y:DOUBLE):DOUBLE;
BEGIN
  IF x=0 THEN
  BEGIN
    pwr:=0; EXIT;
  END;
  pwr:=EXP(y*LN(x));
END;
{-----------------------------------------------------------------------------}
FUNCTION ftoa(x:DOUBLE):STRIN; { Floating-Point to ASCII }
VAR s:STRIN;
    n:INTEGER;
    null:BOOLEAN;
BEGIN
  IF x=0 THEN s:='0'
  ELSE
  IF (ABS(x)<1e6) AND (ABS(x)>1e-6) THEN
  BEGIN
    n:=maxi(0,ROUND(log10(ABS(x))));
    STR(x:0:7-n,s); s:=ltrim(s);
    IF POS('.',s)<>0 THEN
    BEGIN
      WHILE COPY(s,LENGTH(s),1)='0' DO s:=COPY(s,1,LENGTH(s)-1);
      IF COPY(s,LENGTH(s),1)='.' THEN s:=COPY(s,1,LENGTH(s)-1);
    END;
  END
  ELSE
  BEGIN
    STR(x:14,s); n:=POS('E',s);
    REPEAT
      IF s[n-1]='0' THEN
      BEGIN
        DELETE(s,n-1,1); DEC(n); null:=TRUE;
      END ELSE null:=FALSE;
    UNTIL null=FALSE;
    IF COPY(s,n-1,1)='.' THEN s:=COPY(s,1,n-2)+COPY(s,n,100);
    n:=POS('E',s);
    WHILE s[n+2]='0' DO DELETE(s,n+2,1);   {  0 nach E }
    IF s[n+1]='+' THEN DELETE(s,n+1,1);
  END;
  ftoa:=ltrim(s);
END;
{-----------------------------------------------------------------------------}
FUNCTION dtoa(x:DOUBLE):STRIN; { Floating-Point to ASCII }
VAR s:STRIN;
    n:INTEGER;
    null:BOOLEAN;
BEGIN
  IF x=0 THEN s:='0'
  ELSE
  IF (ABS(x)<1e10) AND (ABS(x)>1e-10) THEN
  BEGIN
    n:=maxi(0,ROUND(log10(ABS(x))));
    STR(x:0:14-n,s); s:=ltrim(s);
    IF POS('.',s)<>0 THEN
    BEGIN
      WHILE COPY(s,LENGTH(s),1)='0' DO s:=COPY(s,1,LENGTH(s)-1);
      IF COPY(s,LENGTH(s),1)='.' THEN s:=COPY(s,1,LENGTH(s)-1);
    END;
  END
  ELSE
  BEGIN
    STR(x:18,s); n:=POS('E',s);
    REPEAT
      IF s[n-1]='0' THEN
      BEGIN
        DELETE(s,n-1,1); DEC(n); null:=TRUE;
      END ELSE null:=FALSE;
    UNTIL null=FALSE;
    IF COPY(s,n-1,1)='.' THEN s:=COPY(s,1,n-2)+COPY(s,n,100);
    n:=POS('E',s);
    WHILE s[n+2]='0' DO DELETE(s,n+2,1);   {  0 nach E }
    IF s[n+1]='+' THEN DELETE(s,n+1,1);
  END;
  dtoa:=ltrim(s);
END;
{-----------------------------------------------------------------------------}
FUNCTION ftoae(x:DOUBLE):STRIN; { Floating-Point to ASCII }
VAR s:STRIN;
    n:INTEGER;
    null:BOOLEAN;
BEGIN
  STR(x:14,s); n:=POS('E',s);
  REPEAT
    IF s[n-1]='0' THEN
    BEGIN
      DELETE(s,n-1,1); DEC(n); null:=TRUE;
    END ELSE null:=FALSE;
  UNTIL null=FALSE;
  IF COPY(s,n-1,1)='.' THEN s:=COPY(s,1,n-2)+COPY(s,n,100);
  n:=POS('E',s);
  WHILE (n+2<LENGTH(s)) AND (s[n+2]='0') DO DELETE(s,n+2,1);   {  0 nach E }
  IF s[n+1]='+' THEN DELETE(s,n+1,1);
  ftoae:=ltrim(s);
END;
{-----------------------------------------------------------------------------}
FUNCTION maxi(a,b:LONGINT):LONGINT;  { maxi leifert die grî·ere der beiden
Integerzahlen zurÅck }
BEGIN
  IF a>b THEN maxi:=a ELSE maxi:=b;
END;
{-----------------------------------------------------------------------------}
FUNCTION mini(a,b:LONGINT):LONGINT;
BEGIN
  IF a<b THEN mini:=a ELSE mini:=b;
END;
{-----------------------------------------------------------------------------}
FUNCTION ltoa(zahl:LONGINT):STRIN;  { Long to ASCII }
VAR laber:STRIN;
BEGIN
  STR(zahl,laber); ltoa:=laber;
END;
{-----------------------------------------------------------------------------}
FUNCTION ltoa_x(zahl,stellen:LONGINT):STRIN;  { Long to ASCII }
VAR laber:STRIN;
BEGIN
  STR(zahl:stellen,laber); ltoa_x:=laber;
END;
{-----------------------------------------------------------------------------}
FUNCTION wtoa(zahl:WORD):STRIN;  { Word to ASCII }
VAR laber:STRIN;
BEGIN
  STR(zahl,laber); wtoa:=laber;
END;
{-----------------------------------------------------------------------------}
FUNCTION btoa(zahl:BOOLEAN):STRIN;  { Boolean to ASCII }
BEGIN
  IF zahl THEN btoa:='TRUE'ELSE btoa:='FALSE';
END;
{-----------------------------------------------------------------------------}
FUNCTION ucase(s:STRIN):STRIN;     { Konvertiert einen STRIN; in Gro·buchstaben }
VAR i:INTEGER;
BEGIN
  FOR i:=1 TO LENGTH(s) DO s[i]:=UPCASE(s[i]);
  WHILE POS('Ñ',s)<>0 DO s[POS('Ñ',s)]:='é';
  WHILE POS('î',s)<>0 DO s[POS('î',s)]:='ô';
  WHILE POS('Å',s)<>0 DO s[POS('Å',s)]:='ö';
  ucase:=s;
END;
{-----------------------------------------------------------------------------}
FUNCTION lcase(c:CHAR):CHAR;          { Konvertiert ein Zeichen in kleine Buchstaben }
VAR o:INTEGER;
BEGIN
  o:=ORD(c);
  IF (o>64) AND (o<91) THEN lcase:=CHR(o+32)
  ELSE if c='ö' THEN c:='Å'
  ELSE if c='é' THEN c:='Ñ'
  ELSE if c='ô' THEN c:='î'
  ELSE lcase:=c;
END;
{-----------------------------------------------------------------------------}
FUNCTION lowcase(s:STRIN):STRIN;    { Konvertiert einen STRIN; in KLeinbuchstaben }
VAR i:INTEGER;
BEGIN
  FOR i:=1 TO LENGTH(s) DO s[i]:=lcase(s[i]);
  lowcase:=s;
END;
{-----------------------------------------------------------------------------}
FUNCTION ltobin(zahl:LONGINT):STRIN;  { LONG TO BinÑrformat
10 ---> 1010 }
VAR s_:STRIN;
BEGIN
  s_:='';
  WHILE zahl<>0 DO
  BEGIN
    IF (zahl AND 1)=1 THEN s_:='1'+s_ ELSE s_:='0'+s_;
    zahl:=zahl SHR 1
  END;
  ltobin:=s_;
END;
{-----------------------------------------------------------------------------}
FUNCTION ltohex(zahl:LONGINT):STRIN;   { Long to HEX   100 --> 64 }
VAR s_:STRIN;
    teil:BYTE;
BEGIN
  s_:='';
  WHILE zahl<>0 DO
  BEGIN
    teil:=zahl AND 15;
    if teil>9 THEN s_:=CHR(teil+55)+s_ ELSE s_:=CHR(teil+48)+s_;
    zahl:=zahl SHR 4;
  END;
  ltohex:=s_;
END;
{-----------------------------------------------------------------------------}
PROCEDURE editl(taste:WORD; VAR curs:WORD; curmin,curmax:WORD; VAR dummy_:STRIN; ins:BOOLEAN);
VAR i:INTEGER;
BEGIN
  dummy_:=laenge(dummy_,255,' ');
  CASE taste OF
    8: {backspace}
    IF curs>curmin THEN
    BEGIN
      dummy_:=COPY(dummy_,1,curs-2)+COPY(dummy_,curs,255)+' '; DEC(curs);
    END;
    32..126,129,130,132,142,144,148,153,154,160,161,162,163,
    164,165,181,198,199,214,224,225,233:
    BEGIN
      IF ins THEN dummy_:=COPY(COPY(dummy_,1,curs-1)+CHR(taste)+COPY(dummy_,curs,255),1,255)
      ELSE        dummy_:=COPY(COPY(dummy_,1,curs-1)+CHR(taste)+COPY(dummy_,curs+1,255),1,255);
      INC(curs);
    END;
    333: { cursor rechts } IF (curs<curmax) AND (curs<=LENGTH(dummy_)) THEN INC(curs);
    331: { cursor links  } IF curs>curmin THEN DEC(curs);
    339: { delete }        dummy_:=COPY(dummy_,1,curs-1)+COPY(dummy_,curs+1,255)+' ';
    327: { home } curs:=curmin;
    335: { end }
    BEGIN
      curs:=LENGTH(dummy_); WHILE dummy_[curs]=' ' DO DEC(curs);
      curs:=mini(curmax,maxi(curmin,curs+1));
    END;
    372: {^ rechts}
    BEGIN
      FOR i:=curs TO length(rtrim(dummy_)) DO IF dummy_[i] in [' '] THEN
      BEGIN
        curs:=i; break;
      END;
      FOR i:=curs TO length(rtrim(dummy_)) DO IF NOT (dummy_[i] in [' ']) THEN
      BEGIN
        curs:=i; break;
      END;
    END;
    371: {^ links}
    BEGIN
      FOR i:=curs DOWNTO curmin DO IF dummy_[i] in [' '] THEN
      BEGIN
        curs:=i; break;
      END;
      FOR i:=curs downTO curmin DO IF NOT (dummy_[i] in [' ']) THEN
      BEGIN
        curs:=i; break;
      END;
    END;
  END;
END;
{-----------------------------------------------------------------------------}
PROCEDURE editk(taste:WORD; VAR curs:WORD; curmin,curmax:WORD; VAR dummy_:STRIN; ins:BOOLEAN);
VAR i:INTEGER;
BEGIN
  dummy_:=laenge(dummy_,79,' ');
  CASE taste OF
    8: {backspace}
    IF curs>curmin THEN
    BEGIN
      dummy_:=COPY(dummy_,1,curs-2)+COPY(dummy_,curs,79)+' '; DEC(curs);
    END;
    32..126,129,130,132,142,144,148,153,154,160,161,162,163,
    164,165,181,198,199,214,224,225,233:
    BEGIN
      IF ins THEN dummy_:=COPY(COPY(dummy_,1,curs-1)+CHR(taste)+COPY(dummy_,curs,79),1,79)
      ELSE        dummy_:=COPY(COPY(dummy_,1,curs-1)+CHR(taste)+COPY(dummy_,curs+1,79),1,79);
      INC(curs);
    END;
    333: { cursor rechts } IF (curs<curmax) AND (curs<=LENGTH(dummy_)) THEN INC(curs);
    331: { cursor links  } IF curs>curmin THEN DEC(curs);
    339: { delete }        dummy_:=COPY(dummy_,1,curs-1)+COPY(dummy_,curs+1,79)+' ';
    327: { home } curs:=curmin;
    335: { end }
    BEGIN
      curs:=LENGTH(dummy_); WHILE dummy_[curs]=' ' DO DEC(curs);
      curs:=mini(curmax,maxi(curmin,curs+1));
    END;
    372: {^ rechts}
    BEGIN
      FOR i:=curs TO length(rtrim(dummy_)) DO IF dummy_[i] in [' '] THEN
      BEGIN
        curs:=i; break;
      END;
      FOR i:=curs TO length(rtrim(dummy_)) DO IF NOT (dummy_[i] in [' ']) THEN
      BEGIN
        curs:=i; break;
      END;
    END;
    371: {^ links}
    BEGIN
      FOR i:=curs DOWNTO curmin DO IF dummy_[i] in [' '] THEN
      BEGIN
        curs:=i; break;
      END;
      FOR i:=curs downTO curmin DO IF NOT (dummy_[i] in [' ']) THEN
      BEGIN
        curs:=i; break;
      END;
    END;
  END;
END;
{-----------------------------------------------------------------------------}
FUNCTION lese_int(VAR z_:STRIN):LONGINT;
VAR x:LONGINT;
    code:INTEGER;
    s_:STRIN;
BEGIN
  zerhacke(s_,z_); VAL(s_,x,code);
  IF code=0 THEN lese_int:=x ELSE
  BEGIN
    lese_int:=0; my_message('WRONG NUMBER FORMAT '+s_);
  END;
END;
{-----------------------------------------------------------------------------}
FUNCTION sgn(X:DOUBLE):DOUBLE;
BEGIN
  IF x>0 THEN sgn:=1 ELSE if x<0 THEN sgn:=-1 ELSE sgn:=0;
END;
{-----------------------------------------------------------------------------}
FUNCTION lese_zahl(VAR z_:STRIN):DOUBLE;
VAR x:DOUBLE;
    code:INTEGER;
    s_:STRIN;
BEGIN
  zerhacke(s_,z_); code:=POS('.',s_);
  IF code>0 THEN IF code=1 THEN s_:='0'+s_ { .334 }
  ELSE IF code=LENGTH(s_) THEN s_:=s_+'0'  {  123. }
  ELSE
  BEGIN
    { -.345 }
    IF NOT (s_[code-1] IN ['0'..'9']) THEN
    BEGIN
      s_:=COPY(s_,1,code-1)+'0'+COPY(s_,code,255); INC(code);
    END;
    {  3.E-4 }
    IF NOT (s_[code+1] IN ['0'..'9']) THEN s_:=COPY(s_,1,code)+'0'+COPY(s_,code+1,255);
  END;
  VAL(s_,x,code);
  IF code=0 THEN lese_zahl:=x ELSE
  BEGIN
    lese_zahl:=0; my_message('WRONG NUMBER FORMAT '+s_);
  END;
END;
{-----------------------------------------------------------------------------}
FUNCTION ltrim(s:STRIN):STRIN; { Entfernt von einem STRIN; vorn Leerzeichen }
VAR i:INTEGER;
BEGIN
  i:=1;
  WHILE (i<=length(s)) AND (s[i]=' ') DO INC(i);
  s:=COPY(s,i,255); ltrim:=s;
END;
{-----------------------------------------------------------------------------}
PROCEDURE zerhacke(VAR z_,rest_:STRIN);
VAR a:INTEGER;
BEGIN
  a:=POS(' ',rest_);
  IF a<>0 THEN
  BEGIN
    z_:=COPY(rest_,1,a-1); rest_:=ltrim(COPY(rest_,a+1,255));
  END
  ELSE
  BEGIN
    z_:=rest_; rest_:='';
  END;
END;
{-----------------------------------------------------------------------------}
PROCEDURE file_test(t_:STRIN);
VAR fp:TEXT;
BEGIN
  ASSIGNFILE(fp,t_); {$I-} RESET(fp); {$I+}
  IF IORESULT=0 THEN CLOSEFILE(fp) ELSE
  BEGIN
    my_message('FILE '+t_+' NOT FOUND');
    {$IFDEF win32} application.terminate; {$ELSE} HALT; {$ENDIF}
  END;
END;
{$IFNDEF win32}
FUNCTION drucker_test(dest_:STRIN):BOOLEAN;  { Testet, ob der angeschlossene Drucken ON-LINE ist }
VAR cpu:registers;
    cp:WORD;
    s_:STRIN;
BEGIN
  s_:=ucase(ltrim(dest_)); drucker_test:=TRUE;
  IF (s_='PRN') OR (s_='PRN:') THEN s_:='LPT1';
  IF COPY(s_,1,3)<>'LPT' THEN
  BEGIN
    drucker_test:=FALSE; EXIT;
  END;
  s_:=COPY(s_,4,255); cpu.ah:=2; cpu.dx:=lese_int(s_)-1; INTR(23,cpu);
  cp:=cpu.ah; drucker_test:=(cp AND 16=16) AND (cp AND 32=0);
END;
{--------------------------------------------------------------------------}
FUNCTION alt_pressed:BOOLEAN;
BEGIN
  alt_pressed:=byte(ptr(seg0040,$17)^) AND $08=$08;
END;
{--------------------------------------------------------------------------}
FUNCTION control_pressed:BOOLEAN;
BEGIN
  control_pressed:=byte(ptr(seg0040,$17)^) AND $04=$04;
END;
{--------------------------------------------------------------------------}
{$ENDIF}
END.
altpressed     :=byte(ptr(seg0040,$17)^) AND $08=$08;
altleftpressed :=byte(ptr(seg0040,$18)^) AND $02=$02;
altrigtpressed :=altpressed AND (not altfeftpressed);

cntlpressed    :=byte(ptr(seg0040,$17)^) AND $04=$04;
cntlleftpressed:=byte(ptr(seg0040,$18)^) AND $01=$01;

shiftleft      :=byte(ptr(seg0040,$17)^) AND $02=$02;
shiftright     :=byte(ptr(seg0040,$17)^) AND $01=$01;

syspressed     :=byte(ptr(seg0040,$18)^) AND $04=$04;


