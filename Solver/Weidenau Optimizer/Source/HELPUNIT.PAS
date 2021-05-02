{
Format of Help file:
NEW ITEM CHR(1)+ 4 Digit Number
Link CHR(2)+TEXT+CHR(3)+Number of Target if Link is activated
End of Page CHR(4)
}
UNIT helpunit;
{ $DEFINE check}
{$O+,F+}
INTERFACE
USES {$IFDEF msDos} OVERLAY,{$ENDIF}
     DOS,CRT,textfeld,util,screen_u;

CONST maxhist=20;
      maxlabel=1000;

TYPE high_type=ARRAY [1..max_zeilen] OF
               RECORD
                 ja:BOOLEAN;
                 von,bis,entry,von1,bis1,entry1:WORD;
               END;
     high_type_pointer=^high_type;
     hist_type=RECORD
                 eintrag,ze,sp:INTEGER;
               END;
               {$IFDEF check}
     check_type=ARRAY [1..maxlabel] OF RECORD
                                         la,anzahl:INTEGER;
                                         def:BOOLEAN;
                                       END;
               {$ENDIF}
     help_type=OBJECT(editor_type)
                 t1_:t_type;
                 history:ARRAY [1..maxhist] OF hist_type;
                 high:high_type_pointer;
                 bild:bildschirm;
                 {$IFDEF check}
                 nlab:INTEGER;
                 check:^check_type;
                 PROCEDURE suche_lab(nr:INTEGER);
                 PROCEDURE define_lab(nr:INTEGER);
                 {$ENDIF}
                 PROCEDURE help(help_,init_:STRIN; log:INTEGER);
                 PRIVATE
                 CONSTRUCTOR init;
                 DESTRUCTOR done;
                 PROCEDURE zeige_zeile(welche:WORD; du_:STRIN);VIRTUAL;
                 PROCEDURE inithigh(welche:WORD);
                 PROCEDURE hilfe(VAR init_:STRIN; lab:WORD);
                 FUNCTION  fillhigh(welche,v,b,e:WORD):BOOLEAN;
                 FUNCTION  screen_edit:WORD;VIRTUAL;
                 FUNCTION  lies(help_:STRIN):BOOLEAN;
               END;

IMPLEMENTATION

VAR max_breite,nhist,aktzeile,aktspalte,nline:INTEGER;
    search_:STRIN;

CONSTRUCTOR help_type.init;
BEGIN
  {$IFDEF check}
  nlab:=0; NEW(check);
  {$ENDIF}
  bild.init; bild.save; editor_type.init; t1_.init; NEW(high);
END;
{----------------------------------------------------------------------------}
DESTRUCTOR help_type.done;
BEGIN
  t1_.done; editor_type.done; DISPOSE(high); bild.done;
  {$IFDEF check} DISPOSE(check); {$ENDIF}
END;
{----------------------------------------------------------------------------}
{$IFDEF check}
PROCEDURE help_type.suche_lab(nr:INTEGER);
VAR i:INTEGER;
BEGIn
  for i:=1 to nlab DO WITH check^[i] DO IF la=nr THEN
  BEGIN
    INC(anzahl); EXIT;
  END;
  INC(nlab);
  WITH check^[nlab] DO
  BEGIN
    la:=nr; def:=FALSE; anzahl:=1;
  END;
END;
{----------------------------------------------------------------------------}
PROCEDURE help_type.define_lab(nr:INTEGER);
VAR i:INTEGER;
BEGIn
  for i:=1 to nlab DO WITH check^[i] DO IF la=nr THEN
  BEGIN
    IF def THEN
    BEGIN
      WRITELN('LABEL ',nr,' already defined'); wait; halt;
    END;
    def:=TRUE; EXIT;
  END;
  INC(nlab);
  WITH check^[nlab] DO
  BEGIN
    la:=nr; def:=TRUE; anzahl:=0;
  END;
END;
{----------------------------------------------------------------------------}
{$ENDIF}
PROCEDURE help_type.zeige_zeile(welche:WORD; du_:STRIN);
VAR s_:STRIN;
    i:INTEGER;
BEGIN
  WITH inp DO
  BEGIN
    WITH high^[welche] DO IF ja THEN
    BEGIN
      for i:=1 to length(du_) DO
      BEGIN
        IF ((i>=von) AND (i<=bis)) OR ((i>=von1) AND (i<=bis1))
        THEN TEXTCOLOR(yellow) ELSE TEXTCOLOR(LIGHTgray);
        IF (i<=smin) OR (i-xoffset>smin) THEN WRITE(du_[i]);
        {$IFNDEF WIN32}
        IF WHEREX>=xbreite THEN BREAK;
        {$ENDIF}
      END;
      TEXTCOLOR(LIGHTgray);
    END
    ELSE WRITE(COPY(COPY(du_,1,smin)+COPY(du_,smin+1+xoffset,255),1,xbreite-1));
   {$IFNDEF WIN32}
    IF WHEREX<=xbreite THEN CLREOL;
    {$ENDIF}
  END;
END;
{----------------------------------------------------------------------------}
FUNCTION help_type.screen_edit:WORD;
VAR i,taste:WORD;
    s_,s1_,s2_:STRIN;
LABEL 1,2;
{----------------------------------------------------------------------------}
BEGIN
  {$IFNDEF win32}
  editor_type.init1;
  GOTOXY(8,inp.ybreite+2); TEXTCOLOR(green);
  WRITE(' ALT X EXIT  ^S SEARCH  ^R REPEAT SEARCH  ^P PREVIOUS  F10-PRINT ');
  editor_type.init2;
  WITH inp DO
  BEGIN
    REPEAT
      editor_type.frame;
      GOTOXY(xbreite-9,1); WRITE(zstart:4,sstart:4); TEXTCOLOR(lightgray);
      WINDOW(xwinl-1,ywinl-1,xwinl+xbreite,ywinl+ybreite);
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
          t_^[zstart].done;
        END;
        315: { F1 Help }
        BEGIN
          CLRSCR;
          WRITELN('HELP for HELP'); WRITELN;
          WRITELN('You can move around in the file with the');
          WRITELN('cursorkeys or page up/down, Home oder End');
          WRITELN('Hyperlinks are highlighted in YELLOW. You can jump to the next');
          WRITELN('of these links with TAB and the previous with SHUFT-TAB.');
          WRITELN('You activate the link with ENTER, if you inside a YELLOW area.');
          WRITELN('Search is invoked with ^S. You are prompted for a search string');
          WRITELN('which will be searched in the whole file from the beginning.');
          WRITELN('If you want to serrch further, use REPEAT-SEARCH ^R.');
          WRITELN('F10 prints the help menu.');
          my_MESSAGE('Leave help with ALT-X'); anzeige;
        END;
        9: { TAB   suche n„chsten Eintrag }
        BEGIN
          { vor dem ersten Eintrag }
          IF sstart<high^[zstart].von THEN sstart:=high^[zstart].von
          ELSE { vor dem 2. Eintrag }
          IF sstart<high^[zstart].von1 THEN sstart:=high^[zstart].von1
          ELSE
          for i:=zstart+1 TO nz DO IF high^[i].von>0 THEN
          BEGIN
            sstart:=high^[i].von; verschiebe(i-zstart); BREAK;
          END;
        END;
        271: { shift TAB   suche Eintrag davor }
        BEGIN
          { hinter dem 2. Eintrag }
          IF (sstart>high^[zstart].bis1) AND (high^[zstart].bis1>0) THEN
          BEGIN
            sstart:=high^[zstart].von1; GOTO 2;
          END
          { hinter dem 1. Eintrag }
          ELSE IF (sstart>high^[zstart].bis) AND (high^[zstart].bis>0) THEN
          BEGIN
            sstart:=high^[zstart].von; GOTO 2;
          END
          ELSE
          BEGIN
            for i:=zstart-1 DOWNTO 1 DO WITH high^[i] DO IF von>0 THEN
            BEGIN
              IF von1>0 THEN sstart:=von1 ELSE sstart:=von;
              verschiebe(i-zstart); GOTO 2;
            END;
          END;
          sstart:=1; xoffset:=0; yoffset:=0; verschiebe(-2000);
          2:
        END;
        16,18,19,3,13,27,301,316..323,340..349: { Sondertasten }
        BEGIN
          screen_edit:=taste; GOTO 1;
        END;
        328: verschiebe( -1); { cursor oben   }
        329: verschiebe(-hi(windmax) DIV 2); { page up   }
        336: verschiebe(  1); { cursor unten }
        337: verschiebe( hi(windmax) DIV 2); { page down }
        333,372: { right }
        BEGIN
          editl(taste,sstart,smin,smax,dummy_,TRUE);
          IF sstart-xoffset>=xbreite THEN
          BEGIN
            INC(xoffset); anzeige;
          END;
        END;
        331,371: { left }
        BEGIN
          editl(taste,sstart,smin,smax,dummy_,TRUE);
          IF sstart-xoffset<smin+3 THEN IF xoffset>0 THEN
          BEGIN
            DEC(xoffset); anzeige;
          END;
        END;
        327: { home }
        BEGIN
          editl(taste,sstart,smin,smax,dummy_,TRUE);
          xoffset:=0; anzeige;
        END;
        335: { end }
        BEGIN
          editl(taste,sstart,smin,smax,dummy_,TRUE);
          WHILE sstart-xoffset>xbreite-1 DO INC(xoffset);
          anzeige;
        END;
      END;
    UNTIL FALSE;
    1:
    t_^[zstart].put(rtrim(inp.dummy_));
  END;
  TEXTMODE(lastmode);
  WITH inp do
  BEGIN
    DEC(xwinl,1); DEC(ywinl,1); INC(xbreite,2); INC(ybreite,2);
  END;
  {$ENDIF}
END;
{-----------------------------------------------------------------------------}
FUNCTION help_type.lies(help_:STRIN):BOOLEAN;
VAR fp:TEXT;
    s_:STRIN;
    fp1:FILE OF BYTE;
    {$IFDEF check}
    e,i,j,k:INTEGER;
    s1_,s2_:STRIN;
    {$ENDIF}
BEGIN
  nline:=0; max_breite:=0; lies:=TRUE;
  ASSIGN(fp1,HELP_);
  {$I-} RESET(fp1); {$I+}
  IF IORESULT<>0 THEN
  BEGIN
    lies:=FALSE; EXIT;
  END;
  IF maxavail<2000+filesize(fp1) THEN
  BEGIN
    my_message('Too little free memory'); lies:=FALSE; CLOSE(fp1); EXIT;
  END;
  CLOSE(fp1); ASSIGNFILE(fp,HELP_); RESET(fp);
  WHILE NOT EOF (fp) DO
  BEGIN
    READLN(fp,s_); t1_.add(s_);
    max_breite:=maxi(max_breite,length(s_));
  END;
  CLOSEFILE(fp); INC(max_breite); nline:=t1_.lines;
  {$IFDEF check}
  for i:=1 to nline DO
  BEGIN
    s_:=t1_.get(i); s2_:='';
    IF s_[1]<>#4 THEN IF s_[1]=#1 THEN
    BEGIN
      s_:=COPY(s_,2,255); define_lab(lese_int(s_));
    END
    ELSE
    WHILE POS(#2,s_)<>0 DO
    BEGIN
      s_:=COPY(s_,POS(#2,s_)+1,255);
      s_:=COPY(s_,POS(#3,s_)+1,255);
      zerhacke(s1_,s_);
      IF (wieviel_int(s1_)=length(s1_)) AND (s1_<>'') THEN suche_lab(lese_int(s1_))
      ELSE
      BEGIN
        WRITELN('Item wrong in row ',i,' at statement ',t1_.get(i));
        wait; HALT;
      END;
    END;
  END;
  for i:=1 to nlab DO WITH check^[i] DO
  BEGIN
    WRITE(la:4,anzahl:6);
    IF def THEN WRITELN ELSE WRITELN(' Not defined');
    IF i MOD 20=0 THEN wait;
  END;
  wait; HALT;
  {$ENDIF}
END;
{---------------------------------------------------------------------------}
PROCEDURE help_type.inithigh(welche:WORD);
BEGIN
  WITH high^[welche] DO
  BEGIN
    ja:=FALSE; von:=0; bis:=0; entry:=0; von1:=0; bis1:=0; entry1:=0;
  END;
END;
{---------------------------------------------------------------------------}
FUNCTION extract(s_:STRIN):STRIN;
VAR s1_,s2_:STRIN;
    j:INTEGER;
BEGIN
  WHILE POS(#2,s_)<>0 DO { EINTRAG FšR SUBMENU }
  BEGIN
    j:=POS(#2,s_); s_:=COPY(s_,1,j-1)+COPY(s_,j+1,255);
    { Ende des Eintrags }
    j:=POS(#3,s_); s1_:=COPY(s_,j+1,255); s_:=COPY(s_,1,j-1);
    j:=lese_int(s1_); s_:=s_+' '+s1_;
  END;
  extract:=s_;
END;
{---------------------------------------------------------------------------}
FUNCTION help_type.fillhigh(welche,v,b,e:WORD):BOOLEAN;
BEGIN
  fillhigh:=FALSE;
  WITH high^[welche] DO
  BEGIN
    ja:=TRUE;
    IF von=0 THEN
    BEGIN
      von:=v; bis:=b; entry:=e;
    END
    ELSE IF von1=0 THEN
    BEGIN
      von1:=v+bis+1; bis1:=b+bis+1; entry1:=e;
    END
    ELSE fillhigh:=TRUE;
  END;
END;
{---------------------------------------------------------------------------}
PROCEDURE help_type.hilfe(VAR init_:STRIN; lab:WORD);
VAR startzeile,aktlabel,taste,sstart,nzeilen,i:INTEGER;
    s2_,s1_,s_:STRIN;

PROCEDURE  show_lab(VAR lab:WORD; zeilestart,spaltestart:WORD);
VAR l1,e,j,j1,k,l,i:INTEGER;
    s_,s1_:STRIN;
PROCEDURE do_history;
VAR i:INTEGER;
BEGIN
  INC(nhist);
  IF nhist>maxhist THEN
  BEGIN
    for i:=1 to maxhist-1 DO history[i]:=history[i+1];
    nhist:=maxhist;
  END;
  WITH history[nhist] DO eintrag:=lab;
END;
BEGIN
  do_history;
  s_:=#1+laenge_v(ltoa(lab),4,'0'); { ^A und 4 Digits Label }
  for i:=1 to nline DO IF t1_.get(i)=s_ THEN
  BEGIN
    INC(i);
    for j:=i TO nline DO IF t1_.get(j)=#4 THEN break; { ^D }
    DEC(j); nzeilen:=0;
    for l:=i TO j DO
    BEGIN
      INC(nzeilen); s_:=t1_.get(l); inithigh(nzeilen); s2_:='';
      WHILE POS(#2,s_)<>0 DO
      BEGIN
        j1:=POS(#2,s_); { ^B }
        s2_:=s2_+COPY(s_,1,j1-1);
        s_:=COPY(s_,j1+1,255); k:=POS(#3,s_); { ^C }
        s2_:=s2_+COPY(s_,1,k-1)+' '; s_:=COPY(s_,k+1,255)+' ';
        l1:=POS(' ',s_); s1_:=COPY(s_,1,l1-1); s_:=COPY(s_,l1,255);
        IF (wieviel_int(s1_)=length(s1_)) AND (s1_<>'') THEN e:=lese_int(s1_)
        ELSE
        BEGIN
          my_message('Item wrong in row '+ltoa(l)+' at statement '+get(l));
          HALT;
        END;
        IF fillhigh(nzeilen,j1,j1+k-2,e) THEN
        BEGIN
          my_message('Too many entries per row at strin '+s2_); halt;
        END;
      END;
      s_:=s2_+s_; t_^[nzeilen].put(s_);
    END;
    WITH inp DO
    BEGIN
      xwinl:=1;   xbreite:=80; ywinl:=1;
      IF lastmode=3 THEN ybreite:=25 ELSE ybreite:=50;
      smin:=1;    smax:=max_breite;
      zmin:=1;    nz:=nzeilen; zmax:=nz;
      sstart:=spaltestart; zstart:=zeilestart;
      dest_:=init_;
    END;
    {$IFNDEF win32}
    j:=screen_edit;
    CASE j OF
      13: WITH inp DO WITH high^[zstart] DO IF ja THEN
      BEGIN
        IF      (sstart>=von ) AND (sstart<=bis ) THEN lab:=entry
        ELSE IF (sstart>=von1) AND (sstart<=bis1) THEN lab:=entry1;
        WITH history[nhist] DO
        BEGIN
          ze:=inp.zstart; sp:=inp.sstart;
        END;
        aktspalte:=1; aktzeile:=1;
      END;
      301: { ALT-X } lab:=0;
      16: { ^P } IF nhist>1 THEN
      BEGIN
        WITH history[nhist-1] DO
        BEGIN
          lab:=eintrag; aktzeile:=ze; aktspalte:=sp;
        END;
        dec(nhist,2); exit;
      END;
      19: { ^S }
      BEGIN
        GOTOXY(1,9); WRITELN('Enter search strin');
        sstart:=1; GOTOXY(sstart,10); WRITE(search_);
        REPEAT
          GOTOXY(1,10); WRITE(search_);
          GOTOXY(inp.sstart,10); taste:=tastatur;
          editk(taste, inp.sstart, 1, 79,search_,TRUE);
        UNTIL (taste=27) OR (taste=13);
        search_:=ucase(rtrim(ltrim(search_)));
        IF taste=13 THEN
        FOR i:=1 to nline DO IF t1_.t_^[i].get_char(1)<>#4 THEN { nicht Ende eines Menus }
        BEGIN
          s_:=t1_.get(i);
          IF s_[1]=#1 THEN
          BEGIN
            s_:=copy(s_,2,255); aktlabel:=lese_int(s_); startzeile:=i;
          END
          ELSE IF POS(search_,ucase(extract(s_)))<>0 THEN
          BEGIN
            aktzeile:=i-startzeile; aktspalte:=POS(search_,ucase(extract(s_)));
            lab:=aktlabel; EXIT;
          END;
        END;
      END;
      18: { ^R }
      BEGIN
        FOR i:=startzeile+inp.zstart+1 to nline DO IF t1_.t_^[i].get_char(1)<>#4 THEN
        BEGIN
          s_:=t1_.get(i);
          IF s_[1]=#1 THEN
          BEGIN
            s_:=copy(s_,2,255); aktlabel:=lese_int(s_); startzeile:=i;
          END
          ELSE IF POS(search_,ucase(extract(s_)))<>0 THEN
          BEGIN
            lab:=aktlabel;
            aktzeile:=i-startzeile; aktspalte:=POS(search_,ucase(extract(s_)));
            EXIT;
          END;
        END;
        IF startzeile<>1 THEN startzeile:=1;
      END;
    END;
    EXIT;
    {$ENDIF}
  END;
  my_message('Item '+ltoa(lab)+' not found'); HALT;
END;
{---------------------------------------------------------------------------}
BEGIN
  {$IFNDEF Win32 }CLRSCR; {$ENDIF}
  aktzeile:=1; aktspalte:=1;
  REPEAT
    show_lab(lab,aktzeile,aktspalte);
  UNTIL lab=0;
END;
{---------------------------------------------------------------------------}
PROCEDURE help_type.help(help_,init_:STRIN; log:INTEGER);
VAR h_:STRING[80];
BEGIN
  init;
  {$IFDEF win32} h_:=''; {$ELSE} h_:=GETENV('HELP'); {$ENDIF}
  IF h_[length(h_)]<>'\' THEN h_:=h_+'\';
  IF lies(help_) OR lies(h_+help_) THEN
  BEGIN
    search_:=''; aktzeile:=1; nhist:=0; hilfe(init_,log);
  END;
  done;
END;
END.
