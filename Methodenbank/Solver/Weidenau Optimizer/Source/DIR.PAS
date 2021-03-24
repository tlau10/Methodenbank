UNIT dir;
{$O+,F+}
INTERFACE
USES {$IFDEF msDOS }OVERLAY,{$ENDIF}
     DOS,CRT,util,helpunit;
PROCEDURE directory(VAR path_,rueck_:STRIN);

IMPLEMENTATION

CONST maxdat=65000 DIV SIZEOF(SEARCHREC);
      vor=lightgray;
      hinter=black;
      spec=yellow;
      titel=red;

PROCEDURE directory(VAR path_,rueck_:STRIN);
LABEL 1;
TYPE search_feld=ARRAY [1..maxdat] OF SEARCHREC;
     search_pointer=^search_feld;
VAR p,s:SEARCHREC;
    dt:datetime;
    d_,e_,n_,dummy_,altdir_,aktuell_:STRING[80];
    dat:search_pointer;
    oben,offset,taste,curmin,curmax,ineu,a,curz,curzalt,cursalt,i,
    max_lines,j,nfile:INTEGER;
    curs:WORD;
    voriges:BOOLEAN;
    sort:BYTE;
    an:BOOLEAN;
    h:help_type;
    {-------------------------------------------------------------------------}
PROCEDURE qsort_time(l,r:INTEGER);  { Sortiere alphabetisch nach dem Quicksort-Algorithmus }
VAR i,j:INTEGER;
    x:LONGINT;
BEGIN
  i:=l; j:=r; x:=dat^[(l+r) DIV 2].time;
  REPEAT
    WHILE dat^[i].time<x DO INC(i);
    WHILE x<dat^[j].time DO DEC(j);
    IF i<=j THEN
    BEGIN
      p:=dat^[i]; dat^[i]:=dat^[j]; dat^[j]:=p; INC(i); DEC(j);
    END;
  UNTIL i>j;
  IF l<j THEN qsort_time(l,j);
  IF i<r THEN qsort_time(i,r);
END;
{-----------------------------------------------------------------------------}
PROCEDURE qsort_al(l,r:INTEGER);  { Sortiere alphabetisch nach dem Quicksort-Algorithmus }
VAR i,j:INTEGER;
    x:STRING[13];
BEGIN
  i:=l; j:=r; x:=dat^[(l+r) DIV 2].name;
  REPEAT
    WHILE dat^[i].name<x DO INC(i);
    WHILE x<dat^[j].name DO DEC(j);
    IF i<=j THEN
    BEGIN
      p:=dat^[i]; dat^[i]:=dat^[j]; dat^[j]:=p; INC(i); DEC(j);
    END;
  UNTIL i>j;
  IF l<j THEN qsort_al(l,j);
  IF i<r THEN qsort_al(i,r);
END;
{-----------------------------------------------------------------------------}
FUNCTION ext_(fname_:STRIN):STRIN;
BEGIN
  fsplit(fname_,d_,n_,e_); ext_:=e_;
END;
{-----------------------------------------------------------------------------}
PROCEDURE qsort_ext(l,r:INTEGER);  { Sortiere Extension nach dem Quicksort-Algorithmus }
VAR i,j:INTEGER;
    x:STRING[13];
BEGIN
  i:=l; j:=r; x:=ext_(dat^[(l+r) DIV 2].name);
  REPEAT
    WHILE ext_(dat^[i].name)<x DO INC(i);
    WHILE x<ext_(dat^[j].name) DO DEC(j);
    IF i<=j THEN
    BEGIN
      p:=dat^[i]; dat^[i]:=dat^[j]; dat^[j]:=p; INC(i); DEC(j);
    END;
  UNTIL i>j;
  IF l<j THEN qsort_ext(l,j);
  IF i<r THEN qsort_ext(i,r);
END;
{-----------------------------------------------------------------------------}
PROCEDURE sort_ext;
VAR e_:STRIN;
    n1,n2:INTEGER;
BEGIN
  qsort_ext(1,nfile); n1:=1; n2:=1;
  WHILE n2<nfile DO
  BEGIN
    e_:=ext_(dat^[n1].name);
    WHILE (n2<nfile) AND (e_=ext_(dat^[n2+1].name)) DO INC(n2);
    qsort_al(n1,n2); n1:=n2+1; n2:=mini(nfile,n1);
  END;
END;
{-----------------------------------------------------------------------------}
PROCEDURE qsort_size(l,r:INTEGER);  { Sortiere alphabetisch nach dem Quicksort-Algorithmus }
VAR i,j:INTEGER;
    x:LONGINT;
BEGIN
  i:=l; j:=r; x:=dat^[(l+r) DIV 2].size;
  REPEAT
    WHILE dat^[i].size<x DO INC(i);
    WHILE x<dat^[j].size DO DEC(j);
    IF i<=j THEN
    BEGIN
      p:=dat^[i]; dat^[i]:=dat^[j]; dat^[j]:=p; INC(i); DEC(j);
    END;
  UNTIL i>j;
  IF l<j THEN qsort_size(l,j);
  IF i<r THEN qsort_size(i,r);
END;
{-----------------------------------------------------------------------------}
PROCEDURE put_rec(x:SEARCHREC);
VAR xx:search_pointer;
BEGIN
  inc(nfile);
  IF nfile>oben THEN
  BEGIN
    GETMEM(xx,(oben+20)*SIZEOF(SEARCHREC));
    IF oben>0 THEN
    BEGIN
      MOVE(dat^,xx^,oben*SIZEOF(SEARCHREC));
      FREEMEM(dat,oben*SIZEOF(SEARCHREC));
    END;
    dat:=xx; INC(oben,20);
  END;
  dat^[nfile]:=s
END;
{-----------------------------------------------------------------------------}
PROCEDURE anzeige;
VAR i:INTEGER;
BEGIN
  TEXTCOLOR(vor); TEXTBACKGROUND(hinter);
  GOTOXY(1,3);
  FOR i:=1 TO nfile DO if (i>offset) AND (i-offset<=(max_lines-5)*5) THEN WRITE(dat^[i].name:16);
  CLREOL;
  for i:=WHEREY+1 to max_lines DO
  BEGIN
    GOTOXY(1,i); CLREOL;
  END;
  an:=TRUE; TEXTCOLOR(red);
  IF offset>0 THEN
  BEGIN
    GOTOXY(1,3); WRITE(#24);
  END;
  IF nfile-offset>(max_lines-5)*5 THEN
  BEGIN
    GOTOXY(1,max_lines-1); WRITE(#25);
  END;
  TEXTCOLOR(vor);
END;
{-----------------------------------------------------------------------------}
BEGIN
  GOTOXY(1,25); WRITELN;
  IF WHEREY>25 THEN for i:=1 to 25 DO WRITELN;
  max_lines:=WHEREY; sort:=0; oben:=0; dat:=NIL;
  GETDIR(0,altdir_); aktuell_:=altdir_;
  IF (pos('\',path_)<>0) OR (pos(':',path_)<>0) THEN
  BEGIN
    fsplit(path_,aktuell_,dummy_,path_);
    i:=length(aktuell_);
    if aktuell_[i]='\' THEN aktuell_:=COPY(aktuell_,1,i-1);
    path_:=dummy_+path_;
  END;
  1: offset:=0; TEXTCOLOR(vor); TEXTBACKGROUND(hinter);
  {$I-} CHDIR(aktuell_); {$I+} i:=IORESULT;
  IF i<>0 THEN IF i=3 THEN GETDIR(0,aktuell_) ELSE my_message('Error accessing drive '+aktuell_[1]);
  REPEAT
    CLRSCR; path_:=ucase(path_); dummy_:='Files      '+aktuell_+'\'+path_;
    WHILE POS('\\',dummy_)<>0 DO DELETE(dummy_,POS('\\',dummy_),1);
    WRITELN(dummy_,#10#13);
    curmax:=79; curmin:=12; nfile:=0; voriges:=FALSE;
    FINDFIRST('*.*',$10,s);
    IF DOSERROR=0 THEN
    BEGIN
      IF (s.attr AND 16)=16 THEN
      BEGIN
        IF s.name='..' THEN
        voriges:=TRUE
        ELSE IF s.name<>'.' THEN
        BEGIN
          s.name:=s.name+'\'; put_rec(s);
        END;
      END;
      REPEAT
        FINDNEXT(s);
        IF DOSERROR=0 THEN IF (s.attr AND 16)=16 THEN
        IF s.name='..' THEN
        voriges:=TRUE
        ELSE IF s.name<>'.' THEN
        BEGIN
          s.name:=s.name+'\'; put_rec(s);
        END;
      UNTIL (DOSERROR<>0) OR (nfile=maxdat);
    END;
    FINDFIRST(path_,$3F-$08-$10,s);
    IF DOSERROR=0 THEN
    BEGIN
      put_rec(s);
      REPEAT
        FINDNEXT(s);
        IF DOSERROR=0 THEN put_rec(s);
      UNTIL (DOSERROR<>0) OR (nfile=maxdat);
    END;
    IF nfile>1 THEN
    case sort OF
      0: qsort_al  (1,nfile);
      1: qsort_time(1,nfile);
      2: qsort_size(1,nfile);
      3: sort_ext;
    END;
    IF voriges THEN
    BEGIN
      d_:=aktuell_; a:=0;
      WHILE pos('\',d_)<>0 DO
      BEGIN
        INC(a); d_:=COPY(d_,pos('\',d_)+1,255);
      END;
      IF (a>1) OR (length(aktuell_)>3) THEN
      IF nfile<maxdat THEN
      BEGIN
        s.name:='..\'; put_rec(s);
      END ELSE dat^[nfile].name:='..\';
    END;
    anzeige;
    curs:=2; curz:=3; i:=1; cursalt:=curs; curzalt:=curz; ineu:=1;
    REPEAT
      IF nfile>0 THEN
      BEGIN
        GOTOXY(curs,curz); TEXTCOLOR(spec); WRITE(dat^[i].name:15); an:=FALSE;
        UNPACKTIME(dat^[i].time,dt);
        d_:=ltoa(dt.min);          IF LENGTH(d_)< 2 THEN d_:='0'+d_;
        d_:=ltoa(dt.hour) +'.'+d_; IF LENGTH(d_)< 5 THEN d_:='0'+d_;
        d_:=ltoa(dt.year) +' '+d_; IF LENGTH(d_)<10 THEN d_:='0'+d_;
        d_:=ltoa(dt.month)+'.'+d_; IF LENGTH(d_)<13 THEN d_:='0'+d_;
        d_:=ltoa(dt.day)  +'.'+d_; IF LENGTH(d_)<16 THEN d_:='0'+d_;
        TEXTCOLOR(vor);
        GOTOXY(58,1); WRITE('DATE    TIME     SIZE');
        TEXTCOLOR(titel);
        GOTOXY(55,2); WRITE(d_,dat^[i].size:10);
      END;
      GOTOXY(curs+11,curz); j:=tastatur;
      IF j=315 THEN
      BEGIN
        TEXTCOLOR(vor); textbackground(hinter);
        h.help('DIRECTORY.WHL','LPT1',1); goto 1;
      END;
      IF (j>96) AND (j<123) THEN DEC(j,32);
      CASE j OF
        13:;
        1..4: IF control_pressed THEN
        BEGIN
          aktuell_[1]:=CHR(j+64); GOTO 1;
        END;
        301:  { ALT-X }
        BEGIN
          FREEMEM(dat,oben*SIZEOF(SEARCHREC));
          CHDIR(altdir_); TEXTCOLOR(vor); HALT;
        END;
        92:  { \ } GETDIR(0,altdir_);
        287: { ALT S }
        BEGIN
          gotoxy(1,max_lines); WRITE('D-Date  S-Size  N-Name  E-Extension');
          j:=tastatur;
          CASE j OF
            68,100:
            BEGIN
              sort:=1; { Date } qsort_time(1,nfile);
            END;
            69,101:
            BEGIN
              sort:=3; { extension } sort_ext;
            END;
            78,110:
            BEGIN
              sort:=0; { Name } qsort_al(1,nfile);
            END;
            83,115:
            BEGIn
              sort:=2; { Size } qsort_size(1,nfile);
            END;
          END;
          GOTO 1;
        END;
        331: { links }
        BEGIN
          ineu:=maxi(1,i-1);
          IF ineu-offset<1 THEN
          BEGIN
            DEC(offset,5); anzeige;
          END;
        END;
        327: { home }
        BEGIn
          ineu:=1; offset:=0; anzeige;
        END;
        329: { Page up }
        BEGIn
          ineu:=maxi(1,i-50);
          IF offset>0 THEN WHILE ineu-offset<0 DO DEC(offset,5);
          anzeige;
        END;
        337: { Page down }
        BEGIN
          ineu:=mini(nfile,i+50);
          WHILE ineu-offset>(max_lines-5)*5 DO INC(offset,5);
          anzeige;
        END;
        328: { oben }
        BEGIN
          IF offset>0 THEN IF ineu-5-offset<=0 THEN
          BEGIN
            DEC(offset,5); anzeige;
          END;
          IF ineu>5 THEN ineu:=i-5 ELSE
          BEGIN
            { edit header line }
            TEXTCOLOR(green); curs:=curmin;
            REPEAT
              GOTOXY(1,1); WRITE(dummy_); CLREOL; GOTOXY(curs,1);
              taste:=tastatur; editk(taste,curs,curmin,curmax,dummy_,TRUE);
            UNTIL (taste=336) OR (taste=13);
            fsplit(COPY(dummy_,12,255),aktuell_,dummy_,path_);
            i:=length(aktuell_);
            if aktuell_[i]='\' THEN aktuell_:=COPY(aktuell_,1,i-1);
            path_:=dummy_+path_; GOTO 1;
          END;
        END;
        333: { rechts }
        BEGIN
          ineu:=mini(nfile,i+1);
          IF ineu-offset>(max_lines-5)*5 THEN
          BEGIn
            INC(offset,5); anzeige;
          END;
        END;
        335: { end }
        BEGIN
          ineu:=nfile; while ineu-offset>(max_lines-5)*5 DO INC(offset,5);
          anzeige;
        END;
        336: { unten }
        BEGIN
          ineu:=mini(nfile,i+5);
          IF ineu-offset>(max_lines-5)*5 THEN
          BEGIN
            INC(offset,5); anzeige;
          END;
        END;
        ELSE
        BEGIN
          ineu:=1; WHILE (ineu<nfile) AND (ORD(dat^[ineu].name[1])<>j) DO INC(ineu);
          offset:=0; while ineu-offset>(max_lines-5)*5 DO INC(offset,5);
          anzeige;
        END;
      END;
      IF not an THEN
      BEGIN
        GOTOXY(cursalt,curzalt); TEXTCOLOR(vor);
        WRITE(dat^[i].name:15);
      END;
      i:=ineu;
      curz:=(i-offset-1) DIV 5+3; curs:=((i-offset-1) MOD 5)*16+2;
      IF nfile>0 THEN
      BEGIN
        GOTOXY(curs,curz); TEXTCOLOR(spec);
        WRITE(dat^[i].name:15); cursalt:=curs; curzalt:=curz;
      END;
    UNTIL (j=13) OR (j=27);
    TEXTCOLOR(vor);
    IF j=13 THEN rueck_:=dat^[i].name ELSE rueck_:='';
    a:=POS('\',rueck_);
    IF a<>0 THEN
    BEGIN
      {$I-}CHDIR(COPY(rueck_,1,LENGTH(rueck_)-1)); {$I+}
      IF IORESULT=0 THEN
      GETDIR(0,aktuell_); GOTO 1;
    END;
  UNTIL a=0;
  CHDIR(altdir_);
  IF LENGTH(aktuell_)>3 THEN rueck_:=aktuell_+'\'+rueck_
  ELSE IF rueck_<>'' THEN rueck_:=aktuell_+rueck_;
  TEXTCOLOR(vor); TEXTBACKGROUND(hinter);
  FREEMEM(dat,oben*SIZEOF(SEARCHREC));
END;
END.
