unit Paraunit;
{$O+,F+}
interface

USES {$IFDEF msdos} OVERLAY, {$ENDIF}
     {$IFDEF win32} FORMS,DIALOGS,CONTROLS,
     {SysUtils, WinTypes, WinProcs, Messages, Classes, Graphics, Controls,
     Dialogs, StdCtrls, Grids,
     }
     {$ELSE}
     fulledit,helpunit,
     {$ENDIF}
     util;

type parfeld_eintrag=RECORD
                       art:BYTE; {0: DOUBLE; 1:LONGINT; 2:Boolean; 3:String }
                       mi,ma,wert:DOUBLE;
                       {$IFDEF win32} grid1_,grid2_,{$ENDIF}
                       par_,wert_:spp;
                     END;
CONST maxpar=50;

TYPE  par_feld=ARRAY [1..maxpar] OF parfeld_eintrag;
      tparameter=OBJECT
                   npar:LONGINT;
                   p:^par_feld;
                 END;
     parameter=OBJECT(tparameter)
                 FUNCTION  suche_parameter(s_:STRIN; ja:BOOLEAN):LONGINT;
                 FUNCTION  get_FLOAT_PAR  (s_:STRIN):DOUBLE;
                 FUNCTION  get_INT_PAR    (s_:STRIN):LONGINT;
                 FUNCTION  get_BOOL_PAR   (s_:STRIN):BOOLEAN;
                 FUNCTION  get_STRING_PAR (s_:STRIN):STRIN;
                 PROCEDURE put_FLOAT_PAR  (s_:STRIN; w:DOUBLE);
                 PROCEDURE put_INT_PAR    (s_:STRIN; w:LONGINT);
                 PROCEDURE put_BOOL_PAR   (s_:STRIN; w:BOOLEAN);
                 PROCEDURE put_STRING_PAR (s_,w_:STRIN);
                 PROCEDURE READ_parameter (VAR fp:TEXT);
                 PROCEDURE save_parameter (VAR fp:TEXT);
                 {$IFDEF win32}
                 PROCEDURE fill_edit(nach:WORD);
                 FUNCTION  read_edit:BOOLEAN;
                 {$ELSE}
                 FUNCTION  edit_parameter (nach,display_lines:LONGINT;
                                           destination_:STRIN; help_:STRIN; lab:INTEGER):INTEGER;
                 {$ENDIF}
                 PROCEDURE init;
                 PROCEDURE done;
               END;

implementation

PROCEDURE parameter.done;
VAR i:WORD;
BEGIN
  FOR i:=1 TO maxpar DO WITH p^[i] DO
  BEGIN
    par_.done; wert_.done;
    {$IFDEF win32} grid1_.done; grid2_.done; {$ENDIF}
  END;
  DISPOSE(p); P:=nil;
END;
{----------------------------------------------------------------------------}
PROCEDURE parameter.init;
VAR i:WORD;
BEGIN
  NEW(p);
  FOR i:=1 TO maxpar DO WITH p^[i] DO
  BEGIN
    art:=0; mi:=0; ma:=0; wert:=0; par_.init; wert_.init;
    {$IFDEF win32} grid1_.init; grid2_.init; {$ENDIF}
  END;
END;
{----------------------------------------------------------------------------}
FUNCTION parameter.suche_parameter(s_:STRIN; ja:BOOLEAN):LONGINT;
VAR i:LONGINT;
BEGIN
  FOR i:=1 TO npar DO IF p^[i].par_.get=s_ THEN
  BEGIN
    suche_parameter:=i; EXIT;
  END;
  IF ja THEN my_Message('Parameter '+s_+' not found');
  suche_parameter:=0; HALT;
END;
{----------------------------------------------------------------------------}
FUNCTION parameter.get_FLOAT_PAR(s_:STRIN):DOUBLE;
BEGIN
  WITH p^[suche_parameter(s_,TRUE)] DO if art=0 THEN
  get_FLOAT_PAR:=wert ELSE my_message('Wrong Parametertype '+s_);
END;
{----------------------------------------------------------------------------}
FUNCTION parameter.get_INT_PAR(s_:STRIN):LONGINT;
BEGIN
  WITH p^[suche_parameter(s_,TRUE)] DO if art=1 THEN
  get_int_PAR:=ROUND(wert) ELSE my_message('Wrong Parametertype '+s_);
END;
{----------------------------------------------------------------------------}
FUNCTION parameter.get_BOOL_PAR(s_:STRIN):BOOLEAN;
VAR x:DOUBLE;
BEGIN
  WITH p^[suche_parameter(s_,TRUE)] DO
  BEGIN
    x:=wert;
    IF art=2 THEN
    BEGIN
      IF x=1 THEN get_bool_par:=TRUE ELSE get_bool_par:=FALSE;
    END ELSE my_message('Wrong Parametertype '+s_);
  END;
END;
{----------------------------------------------------------------------------}
FUNCTION parameter.get_STRING_PAR(s_:STRIN):STRIN;
BEGIN
  WITH p^[suche_parameter(s_,TRUE)] DO
  IF art=3 THEN get_string_PAR:=wert_.get
  ELSE my_message('Wrong Parametertype '+s_);
END;
{----------------------------------------------------------------------------}
PROCEDURE parameter.put_FLOAT_PAR(s_:STRIN; w:DOUBLE);
BEGIN
  WITH p^[suche_parameter(s_,TRUE)] DO
  IF art=0 THEN wert:=w
  ELSE my_message('Wrong Parametertype '+s_);
END;
{----------------------------------------------------------------------------}
PROCEDURE parameter.put_INT_PAR(s_:STRIN; w:LONGINT);
BEGIN
  WITH p^[suche_parameter(s_,TRUE)] DO
  IF art=1 THEN wert:=w
  ELSE my_message('Wrong Parametertype '+s_);
END;
{----------------------------------------------------------------------------}
PROCEDURE parameter.put_BOOL_PAR(s_:STRIN; w:BOOLEAN);
BEGIN
  WITH p^[suche_parameter(s_,TRUE)] DO
  IF art<>2 THEN my_message('Wrong Parametertype '+s_)
  ELSE IF w THEN wert:=1 ELSE wert:=0;
END;
{----------------------------------------------------------------------------}
PROCEDURE parameter.put_STRING_PAR(s_,w_:STRIN);
BEGIN
  WITH p^[suche_parameter(s_,TRUE)] DO
  IF art<>3 THEN my_message('Wrong Parametertype '+s_)
  ELSE IF w_<>wert_.get THEN wert_.put(w_)
END;
{----------------------------------------------------------------------------}
PROCEDURE parameter.READ_parameter(VAR fP:TEXT);
VAR s_,s1_:STRIN;
    a,i:LONGINT;
BEGIN
  READLN(fp,npar);
  IF npar>maxpar THEN
  BEGIN
    my_message('IN UNIT PARAUNIT the variable MAXPAR is too small'#10#13'increase MAXPAR and recompile');
    HALT;
  END;
  FOR i:=1 TO npar DO
  BEGIN
    READLN(fp,s_); s_:=ucase(rtrim(ltrim(s_)));
    IF s_<>'' THEN
    BEGIN
      WITH p^[i] DO
      BEGIN
        art:=lese_int(s_); par_.init; wert_.init; a:=POS('=',s_);
        IF a=0 THEN my_message('Error in File in row '+s_);
        s1_:=rtrim(COPY(s_,1,a-1)); s_:=ltrim(COPY(s_,a+1,255));
        par_.put(s1_);
        CASE art OF
          0:
          BEGIN
            wert:=lese_zahl(s_); mi:=lese_zahl(s_); ma:=lese_zahl(s_);
          END;
          1:
          BEGIN
            wert:=lese_zahl(s_); mi:=lese_zahl(s_); ma:=lese_zahl(s_);
            IF wert<>ROUND(wert) THEN my_message('Value for PARAMETER '+par_.get+' is not LONGINT');
          END;
          2:
          BEGIN
            IF (s_='Y') OR (s_='J') THEN wert:=1
            ELSE IF (s_='N') THEN wert:=0
            ELSE my_message('Value for PARAMETER '+par_.get+' is not BOOLEAN');
          END;
          3: wert_.put(s_);
        END;
      END;
    END;
  END;
END;
{----------------------------------------------------------------------------}
PROCEDURE parameter.save_parameter(VAR fp:TEXT);
VAR i:LONGINT;
BEGIN
  WRITELN(fp,npar);
  FOR i:=1 TO npar DO WITH p^[i] DO
  BEGIN
    WRITE(fp,art,' ',par_.get,'=');
    CASE art OF
      0..1: WRITELN(fp,ftoa(wert),' ',ftoa(mi),' ',ftoa(ma));
      2: IF wert=0 THEN WRITELN(fp,'N') ELSE WRITELN(fp,'Y');
      3: WRITELN(fp,wert_.get);
    END;
  END;
END;
{----------------------------------------------------------------------------}
{$IFDEF win32}
PROCEDURE parameter.fill_edit(nach:WORD);
VAR lmax,i:INTEGER;
    s_:STRIN;
BEGIN
  lmax:=0;
  FOR i:=1 TO npar DO lmax:=maxi(lmax,length(p^[i].par_.get));
  FOR i:=1 TO npar DO WITH p^[i] DO
  BEGIN
    {$IFDEF win32} grid1_.put(laenge(par_.get,lmax,' '));
    {$ELSE}
    grid1_.put(laenge(par_.get,lmax,' ')+'=');
    {$ENDIF}
    CASE art OF
      0: IF (ABS(wert)<1e8) AND (ABS(wert)>pwr(10,-nach)) THEN s_:=ftoa_x(wert,0,nach) ELSE s_:=ftoa(wert);
      1: s_:=ltoa(round(wert));
      2: IF wert=1 THEN s_:='Y' ELSE s_:='N';
      3: s_:=wert_.get;
    END;
    grid2_.put(s_);
  END;
END;
{-----------------------------------------------------------------------}
FUNCTION parameter.read_edit:BOOLEAN;
VAR i:INTEGER;
    s_:STRIN;
BEGIN
  read_edit:=TRUE;
  FOR i:=1 TO npar DO WITH p^[i] DO
  BEGIN
    s_:=ucase(rtrim(ltrim(grid2_.get)));
    IF s_='' THEN
    BEGIN
      my_Message('In row '+ltoa(i)+' input is required'); read_edit:=FALSE;
    END;
    CASE art OF
      0: { Float }
      BEGIN
        IF wieviel_float(s_)<>length(s_) THEN
        BEGIN
          grid2_.put('In this row a number is required'); read_edit:=FALSE;
        END;
        wert:=lese_zahl(s_);
        IF (wert<mi) OR (wert>ma) THEN
        BEGIN
          IF wert<mi THEN my_Message('PARAMETER '+grid1_.get+' is below lower limit '+ftoa(mi))
          ELSE            my_Message('PARAMETER '+grid1_.get+' is above upper limit '+ftoa(ma));
          read_edit:=FALSE;
        END ELSE
        BEGIN
          grid1_.done; grid2_.done;
        END;
      END;
      1: { Integer }
      BEGIN
        IF wieviel_int(s_)<>length(s_) THEN
        BEGIN
          my_message('In row '+ltoa(i)+' a number is required');
          read_edit:=FALSE; EXIT;
        END;
        wert:=lese_zahl(s_);
        IF wert<>round(wert) THEN
        BEGIN
          my_Message('Parameter '+grid1_.get+' Must be Integer'); read_edit:=FALSE;
        END;
        IF (wert<mi) OR (wert>ma) THEN
        BEGIN
          IF wert<mi THEN my_Message('PARAMETER '+grid1_.get+' is below lower limit '+ftoa(mi))
          ELSE            my_Message('PARAMETER '+grid1_.get+' is above upper limit '+ftoa(ma));
          read_edit:=FALSE;
        END ELSE
        BEGIN
          grid1_.done; grid2_.done;
        END;
      END;
      2: { Boolean }
      BEGIN
        IF (s_='Y') OR (s_='J') THEN
        BEGIN
          wert:=1; grid1_.done; grid2_.done;
        END ELSE
        IF (s_='N') THEN
        BEGIN
          wert:=0; grid1_.done; grid2_.done;
        END
        ELSE
        BEGIN
          my_Message('Parameter '+grid1_.get+' Must be Y or N'); read_edit:=FALSE;
        END;
      END;
      3: { String } wert_.put(s_);
    END;
  END;
END;
{-----------------------------------------------------------------------}
{$ELSE}
FUNCTION parameter.edit_parameter(nach,display_lines:LONGINT;
                                  destination_:STRIN; help_:STRIN; lab:INTEGER):INTEGER;
VAR lmax,n,i:LONGINT;
    s_:STRIN;
    te_:edit;
    h:help_type;
LABEL 1;
BEGIN
  WITH te_ DO
  BEGIN
    init; add('EDIT PARAMETERS');
    add(''); lmax:=0;
    FOR i:=1 TO npar DO lmax:=maxi(lmax,length(p^[i].par_.get));
    FOR i:=1 TO npar DO WITH p^[i] DO
    BEGIN
      s_:=laenge(par_.get,lmax,' ')+'=';
      CASE art OF
        0: IF (ABS(wert)<1e8) AND (ABS(wert)>pwr(10,-nach)) THEN s_:=s_+ftoa_x(wert,0,nach) ELSE s_:=s_+ftoa(wert);
        1: s_:=s_+ltoa(round(wert));
        2: IF wert=1 THEN s_:=s_+'Y' ELSE s_:=s_+'N';
        3: s_:=s_+wert_.get;
      END;
      add(s_);
    END;
    WITH inp DO
    BEGIN
      xwinl:=1;   xbreite:=80; ywinl:=1; ybreite:=display_lines;
      smin:=lmax+2;   smax:=80;
      sstart:=smin; zstart:=3;
      zmin:=3;    nz:=2+npar; zmax:=nz; ins:=FALSE; frei:=FALSE;
      dest_:=destination_;
    END;
    1:
    i:=screen_edit;
    {$IFNDEF win32}
    IF i=315 THEN { F1 } IF help_<>'' THEN
    BEGIN
      h.help(help_,inp.dest_,lab); goto 1;
    END
    ELSE
    BEGIN
      my_message('No help available'); GOTO 1;
    END;
    {$ENDIF}
    IF i=13 THEN FOR i:=1 TO npar DO WITH p^[i] DO
    BEGIN
      edit_parameter:=13;
      s_:=ucase(rtrim(ltrim(COPY(get(i+2),lmax+2,255))));
      CASE art OF
        0:
        BEGIN
          IF s_='' THEN
          BEGIN
            my_message('In row '+ltoa(i+2)+' input is required');
            inp.sstart:=lmax+1; inp.zstart:=2+i; goto 1;
          END;
          IF wieviel_float(s_)<>length(s_) THEN
          BEGIN
            my_message('In row '+ltoa(i+2)+' is a number required');
            inp.sstart:=lmax+1; inp.zstart:=2+i; goto 1;
          END;
          wert:=lese_zahl(s_);
          IF (wert<mi) OR (wert>ma) THEN
          BEGIN
            IF wert<mi THEN my_message('PARAMETER '+par_.get+' is below lower limit '+ftoa(mi))
            ELSE            my_message('PARAMETER '+par_.get+' is above upper limit '+ftoa(ma));
            inp.sstart:=lmax+1; inp.zstart:=2+i; goto 1;
          END;
        END;
        1:
        BEGIN
          IF s_='' THEN
          BEGIN
            my_message('In row '+ltoa(i+2)+' input is required');
            inp.sstart:=lmax+1; inp.zstart:=2+i; goto 1;
          END;
          IF wieviel_float(s_)<>length(s_) THEN
          BEGIN
            my_message('In row '+ltoa(i+2)+' is a number required');
            inp.sstart:=lmax+1; inp.zstart:=2+i; goto 1;
          END;
          wert:=lese_zahl(s_);
          IF wert<>round(wert) THEN
          BEGIN
            my_message('PARAMETER '+par_.get+' must be LONGINT');
            inp.sstart:=lmax+1; inp.zstart:=2+i; goto 1;
          END;
          IF (wert<mi) OR (wert>ma) THEN
          BEGIN
            IF wert<mi THEN my_message('PARAMETER '+par_.get+' is below lower limit '+ftoa(mi))
            ELSE            my_message('PARAMETER '+par_.get+' is above upper limit '+ftoa(ma));
            inp.sstart:=lmax+1; inp.zstart:=2+i; goto 1;
          END;
        END;
        2:
        BEGIN
          IF (s_='Y') OR (s_='J') THEN wert:=1 ELSE
          IF (s_='N') THEN wert:=0
          ELSE
          BEGIN
            my_message('PARAMETER '+par_.get+' must be Y OR N');
            inp.sstart:=lmax+1; inp.zstart:=2+i; goto 1;
          END;
        END;
        3: wert_.put(s_);
      END;
    END ELSE edit_parameter:=0;
    done;
  END;
END;
{$ENDIF}
{----------------------------------------------------------------------------}
END.

