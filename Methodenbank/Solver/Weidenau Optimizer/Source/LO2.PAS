UNIT lo2;
{$O+F+}
INTERFACE

USES {$ifdef msdos} OVERLAY, {$ENDIF}
     textfeld,util,lo1,tty;

TYPE tlp2=OBJECT(tlp1)
            PROCEDURE lies_lp;
            PROCEDURE lies_mpsx;
            PROCEDURE lies_mpsx_komplett;
            PROCEDURE lies_xpress_solution(f_:STRIN);
            PROCEDURE basis_of_rows;
            PROCEDURE save_basis(out:BOOLEAN);
            PROCEDURE load_basis(basis_name_:STRIN);
            PROCEDURE aufbau;
          END;

IMPLEMENTATION

{$IFDEF win32} USES forms,lp_form; {$ENDIF }

PROCEDURE tlp2.aufbau;
VAR nextx,ersatz,ngr,nkl,ngl,nvaralt,welche,i,j:INTEGER;
    nnull:LONGINT;
    x:DOUBLE;
    new_hash:BOOLEAN;
    {---------------------------------------------------------------------------}
PROCEDURE compress;
VAR nrhs,nloesch,index,spalte,j,i,k:INTEGER;
    getm,new_hash,READY:boolean;
    rhs_entry,coeff:DOUBLE;
    fp:TEXT;
LABEL 1;

PROCEDURE eliminate_row(welche:INTEGER);
VAR j,i:INTEGER;
BEGIN
  FOR j:=-1 TO nvar DO WITH co^[j]^ DO FOR i:=1 TO canzahl DO
  WITH cm^[i] DO IF z=welche THEN
  BEGIN
    cm^[i]:=cm^[canzahl]; DEC(canzahl); BREAK;
  END;
  r.ro^[welche]^.done; swap_rows(welche,r.nrows);
  r.hash^[r.ro^[r.nrows]^.rhas]:=0;
  DISPOSE(r.ro^[r.nrows]); r.ro^[r.nrows]:=NIL; DEC(r.nrows);
END;
{---------------------------------------------------------------------------}
FUNCTION bound_check:BOOLEAN;
VAR obj_row,j,i:INTEGER;
    new_rhs,old_rhs:DOUBLE;
BEGIN
  scr.WRITELN('Checking bounds'); bound_check:=TRUE;
  obj_row:=r.search_row(objf_);
  FOR j:=1 TO nvar DO WITH co^[j]^ DO IF ABS(up_bound-lo_bound)<tolerance THEN IF canzahl>0 THEN { fixed bound --> RHS }
  BEGIN
    {$IFDEF win32 }
    IF j MOD 100=0 THEN
    WITH LpForm.ProgressBar1 DO IF position=max THEN position:=0 ELSE stepit;
    {$ELSE}
    IF j MOD 100=0 THEN scr.WRITE('.');
    {$ENDIF}
    FOR i:=canzahl DOWNTO 1 DO WITH cm^[i] DO IF z<>obj_row THEN
    BEGIN
      WRITELN(fp,'COLUMN ',spa_,' has a fixed bound of ',ftoa(lo_bound));
      bound_check:=FALSE; INC(nloesch);
      old_rhs:=get_entry(z,0); delete_matrix_element(z,0);
      new_rhs:=old_rhs-wert*lo_bound;
      IF ABS(new_rhs)>tolerance THEN
      put_matrix(z,0,new_rhs); delete_matrix_element(z,j);
    END;
  END;
  flush(fp);
END;
{---------------------------------------------------------------------------}
FUNCTION tighten:BOOLEAN;
VAR i,j,k:INTEGER;
    us,ls,rh,bound:DOUBLE;
    plus,ready:BOOLEAN;
    mm:zabwaerts_pointer;
    ii:^lang_int_feld;
    op:LONGINT;
    s_:STRIN;
BEGIN
  scr.WRITELN('Tighten');
  tighten:=TRUE; op:=0;
  FOR i:=1 TO r.nrows-1 DO WITH r.ro^[i]^ DO
  BEGIN
    op:=maxi(op,roben1_alt*LONGINT(SIZEOF(zwaag)));
    IF roben1_alt*LONGINT(SIZEOF(zwaag))>max_size THEN
    BEGIN
      my_message('TIGHTENING OF LP-RELAXATION NOT POSSIBLE BECAUSE OF MEMORY PROBLEM');
      EXIT;
    END;
  END;
  GETMEM(ii,nvar*LONGINT(SIZEOF(INTEGER))); fillchar(ii^,nvar*LONGINT(SIZEOF(INTEGER)),0);
  { Integer OR sos-1 }
  FOR i:=1 TO r.nrows-1 DO WITH r.ro^[i]^ DO
  BEGIN
    ranzahl:=0; roben:=roben1_alt; GETMEM(rm,roben*LONGINT(SIZEOF(zwaag)));
  END;
  FOR i:=1 TO nvar DO WITH co^[i]^ DO IF inttype=1 THEN ii^[i]:=1;
  FOR j:=1 TO nvar DO WITH co^[j]^ DO FOR i:=1 TO canzahl DO WITH cm^[i] DO IF wert<>0 THEN IF z<>r.nrows THEN
  WITH r.ro^[z]^ DO
  BEGIN
    INC(ranzahl);
    IF ranzahl>roben THEN
    BEGIN
      GETMEM(mm,   LONGINT(SIZEOF(zwaag))*(roben+2*inkrem));
      MOVE(rm^,mm^,LONGINT(SIZEOF(zwaag))* roben);
      FREEMEM(rm,  LONGINT(SIZEOF(zwaag))* roben);
      rm:=mm; INC(roben,2*inkrem);
    END;
    WITH rm^[ranzahl] DO
    BEGIN
      z:=j; wert:=co^[j]^.cm^[i].wert;
    END;
  END;
  REPEAT
    ready:=TRUE;
    FOR i:=1 TO r.nrows-1 DO WITH r.ro^[i]^ DO
    BEGIN
      {$IFDEF win32 }
      IF i MOD 100=0 THEN
      WITH LpForm.ProgressBar1 DO IF position=max THEN  position:=0 ELSE stepit;
      {$ELSE}
      IF i MOD 100=0 THEN scr.WRITE('.');
      {$ENDIF}
      rh:=get_entry(i,0);
      IF (gleich=-1) OR (gleich=0) { < or = } THEN
      FOR j:=1 TO ranzahl DO WITH rm^[j] DO
      BEGIN
        WITH co^[z]^ DO
        BEGIN
          us:=up_bound; ls:=lo_bound; bound:=rh;
        END;
        FOR k:=1 TO ranzahl DO IF k<>j THEN WITH rm^[k] DO WITH co^[z]^ DO
        IF wert>0 THEN bound:=bound-lo_bound*wert ELSE bound:=bound-up_bound*wert;
        WITH co^[rm^[j].z]^ DO
        BEGIN
          IF wert>0 THEN
          BEGIN
            up_bound:=max(lo_bound,min(up_bound,bound/wert));
            IF ii^[rm^[j].z]=1 THEN up_bound:=int(up_bound+tolerance);
          END
          ELSE
          BEGIN
            lo_bound:=min(up_bound,max(lo_bound,bound/wert));
            IF ii^[rm^[j].z]=1 THEN IF ABS(frac(lo_bound))>tolerance THEN lo_bound:=1+int(lo_bound+tolerance);
          END;
          IF ABS(ls-lo_bound)>1e-5 THEN
          BEGIN
            s_:=spa_+' LO BOUND increased from '+ftoa(ls)+' to '+ftoa(lo_bound);
            WRITELN(fp,s_); tighten:=FALSE; INC(nloesch); ready:=FALSE; 
          END;
          IF ABS(us-up_bound)>1e-5 THEN
          BEGIN
            s_:=spa_+' UP BOUND reduced from '+ftoa(us)+' to '+ftoa(up_bound);
            WRITELN(fp,s_); tighten:=FALSE; INC(nloesch); ready:=FALSE;
          END;
        END;
      END
      ELSE IF (gleich=1) OR (gleich=0) { > or = } THEN
      FOR j:=1 to ranzahl DO WITH rm^[j] DO IF co^[z]^.art=0 THEN
      BEGIN
        WITH co^[z]^ DO
        BEGIN
          us:=up_bound; ls:=lo_bound; bound:=rh;
        END;
        FOR k:=1 TO ranzahl DO IF k<>j THEN WITH rm^[k] DO with co^[z]^ DO
        IF wert<0 THEN bound:=bound-lo_bound*wert ELSE bound:=bound-up_bound*wert;
        WITH co^[rm^[j].z]^ DO
        BEGIN
          IF wert<0
          THEN
          BEGIN
            up_bound:=min(up_bound,bound/wert);
            IF ii^[rm^[j].z]=1 THEN up_bound:=int(up_bound+tolerance);
          END
          ELSE
          BEGIN
            lo_bound:=max(lo_bound,bound/wert);
            IF ii^[rm^[j].z]=1 THEN IF ABS(frac(lo_bound))>tolerance THEN lo_bound:=1+int(lo_bound+tolerance);
          END;
          IF ABS(ls-lo_bound)>1e-5 THEN
          BEGIN
            s_:=spa_+' LO BOUND changed from '+ftoa(ls)+' to '+ftoa(lo_bound);
            WRITELN(fp,s_); tighten:=FALSE; INC(nloesch); ready:=FALSE;
          END;
          IF ABS(us-up_bound)>1e-5 THEN
          BEGIN
            s_:=spa_+' UP BOUND changed from '+ftoa(us)+' to '+ftoa(up_bound);
            WRITELN(fp,s_); tighten:=FALSE; INC(nloesch); ready:=FALSE;
          END;
        END;
      END;
    END;
  UNTIL ready;
  FREEMEM(ii,nvar*LONGINT(SIZEOF(INTEGER)));
  FOR i:=1 TO r.nrows DO WITH r.ro^[i]^ DO
  BEGIN
    FREEMEM(rm,roben*SIZEOF(zwaag)); ranzahl:=0; roben:=0; rm:=NIL;
  END;
  flush(fp);
END;
BEGIN
  scr.WRITELN('Compressing Matrix');
  {$IFDEF win32} LpForm.ProgressBar1.show; {$ENDIF}
  IF pricing.feld_pointer<>NIL THEN
  BEGIN
    getm:=TRUE;
    pricing.done(r.nrows); vvector.done(r.nrows); rhs.done(r.nrows);
  END ELSE getm:=FALSE;
  ASSIGNFILE(fp,COPY(fname_,1,POS('.',fname_))+'CMP'); REWRITE(fp); nloesch:=0;
  FOR j:=-1 TO nvar DO WITH co^[j]^ DO FOR i:=canzahl DOWNTO 1 DO WITH cm^[i] DO IF wert=0 THEN
  BEGIN
    WRITELN(fp,'IN COLUMN ',laenge(spa_,10,' '),' Entry at row ',laenge(r.ro^[z]^.row_,10,' '),' was 0');
    delete_matrix_element(z,j); INC(nloesch);
  END;
  new_hash:=FALSE;
  REPEAT
    READY:=true;
    scr.WRITELN('Loop');
    FOR i:=r.nrows-1 DOWNTO 1 DO WITH r.ro^[i]^ DO IF gleich<>2 THEN
    BEGIN
      {$IFDEF win32 }
      IF i MOD 100=0 THEN
      WITH LpForm.ProgressBar1 DO IF position=max THEN  position:=0 ELSE stepit;
      {$ELSE}
      IF i MOD 100=0 THEN scr.WRITE('.');
      {$ENDIF}
      nrhs:=0; rhs_entry:=get_entry(i,0);
      IF rhs_entry<>0       THEN INC(nrhs);  { Entry at RHS   }
      IF get_entry(i,-1)<>0 THEN INC(nrhs);  { Entry at RANGE }
      IF roben1_alt-nrhs=2 THEN  { can be replaced by Bound }
      BEGIN
        ready:=FALSE; new_hash:=TRUE; spalte:=0;
        FOR j:=1 TO nvar DO WITH co^[j]^ DO FOR k:=1 TO canzahl DO IF cm^[k].z=i THEN
        BEGIN
          spalte:=j; coeff:=cm^[k].wert; GOTO 1;
        END;
        1:
        WITH co^[spalte]^ DO
        CASE gleich OF
          -1:   { L < --> Up Bound }
          BEGIN
            IF rhs_entry>0 THEN { positive entry }
            BEGIN
              up_bound:=min(up_bound,max(lo_bound,rhs_entry/coeff));
              IF get_range(i)<>0 THEN lo_bound:=min(up_bound,max(lo_bound,(rhs_entry-get_range(i))/coeff));
              WRITELN(fp,'REPLACE ROW ',laenge(r.ro^[i]^.row_,10,' '),' By a UP Bound on Column ',
              laenge(co^[spalte]^.spa_,10,' '),' ',ftoa(up_bound));
            END
            ELSE IF rhs_entry<0 THEN { negative entry }
            BEGIN
              lo_bound:=max(lo_bound,min(up_bound,rhs_entry/coeff));
              IF get_range(i)<>0 THEN up_bound:=max(lo_bound,max(up_bound,(rhs_entry+get_range(i))/coeff));
              WRITELN(fp,'REPLACE ROW ',laenge(r.ro^[i]^.row_,10,' '),' By a LO Bound on Column ',
              laenge(co^[spalte]^.spa_,10,' '),' ',ftoa(lo_bound));
            END;
            eliminate_row(i); INC(nloesch);
          END;
          0:    { E =  --> Fixed bound }
          BEGIN
            lo_bound:=rhs_entry/coeff; up_bound:=lo_bound;
            WRITELN(fp,'REPLACE ROW ',laenge(r.ro^[i]^.row_,10,' '),' By a LO Bound on Column ',
            laenge(spa_,10,' '),' ',ftoa(lo_bound));
            WRITELN(fp,'REPLACE ROW ',laenge(r.ro^[i]^.row_,10,' '),' By a UP Bound on Column ',
            laenge(spa_,10,' '),' ',ftoa(up_bound)); 
            eliminate_row(i); INC(nloesch);
          END;
          1:    { G > --> Lo Bound  }
          BEGIN
            IF rhs_entry>tolerance THEN { positive entry }
            BEGIN
              lo_bound:=rhs_entry/coeff;
              IF get_range(i)<>0 THEN up_bound:=(rhs_entry-get_range(i))/coeff;
              WRITELN(fp,'REPLACE ROW ',laenge(r.ro^[i]^.row_,10,' '),' By a LO Bound on Column ',
              laenge(co^[spalte]^.spa_,10,' '),' ',ftoa(lo_bound)); 
            END
            ELSE IF rhs_entry<-tolerance THEN { negative entry }
            BEGIN
              up_bound:=rhs_entry/coeff;
              IF get_range(i)<>0 THEN lo_bound:=(rhs_entry+get_range(i))/coeff;
              WRITELN(fp,'REPLACE ROW ',laenge(r.ro^[i]^.row_,10,' '),' By a UP Bound on Column ',
              laenge(co^[spalte]^.spa_,10,' '),' ',ftoa(up_bound)); 
            END;
            eliminate_row(i); INC(nloesch);
          END;
        END;
      END;
    END;
    flush(fp);
    FOR i:=1 TO nvar DO WITH co^[i]^ DO IF inttype=1 THEN
    BEGIN
      IF up_bound>1e20 THEN
      BEGIN
        my_message('INTEGER VARIABLE '+spa_+' HAS NO UPPER BOUND');
      END
      ELSE
      BEGIN
        { insure that up_bound is integer }
        IF up_bound<>ROUND(up_bound) THEN
        BEGIn
          WRITELN(fp,'REPLACE UP Bound on Column ',spa_,' from ',ftoa(up_bound),' by ',ltoa(ROUND(INT(up_bound))));
          up_bound:=INT(up_bound); ready:=FALSE; INC(nloesch); 
        END;
        IF lo_bound<-1e20 THEN
        BEGIN
          my_message('INTEGER VARIABLE '+spa_+' HAS NO LOWER BOUND');
        END
        ELSE IF lo_bound<>ROUND(lo_bound) THEN
        BEGIN
          WRITELN(fp,'REPLACE LO Bound on Column ',spa_,' from ',ftoa(up_bound),' by ',ltoa(ROUND(INT(up_bound))));
          lo_bound:=INT(lo_bound+1); ready:=FALSE; INC(nloesch); 
        END;
      END;
    END;
    IF ready THEN ready:=bound_check;
    IF ready THEN ready:=tighten;
  UNTIL ready;
  CLOSEFILE(fp);
  IF nloesch=0 THEN era(COPY(fname_,1,POS('.',fname_))+'CMP');
  IF new_hash THEN put_hash;
  swap_rows(r.nrows,r.search_row(objf_));
  IF getm THEN
  BEGIN
    pricing.init(r.nrows); vvector.init(r.nrows);
    rhs.init(r.nrows); rhs.clear(r.nrows);
  END;
  {$IFDEF win32}
  LpForm.ProgressBar1.hide;
  {$ENDIF}
END;
{----------------------------------------------------------------------------}
PROCEDURE test_bounds;
VAR j:INTEGER;
    infeasible:BOOLEAN;
BEGIN
  infeasible:=FALSE;
  FOR j:=1 TO nvar DO WITH co^[j]^ DO IF lo_bound>up_bound+tolerance THEN
  BEGIN
    scr.writeln('VECTOR : '+laenge(spa_,14,' ')+ftoa_x(lo_bound,14,10)+ftoa_x(up_bound,14,10)+' **');
    infeasible:=TRUE;
  END ELSE lo_bound:=min(lo_bound,up_bound);
  IF infeasible THEN
  BEGIN
    scr.WRITELN('MODEL IS INFEASIBLE'); SCR.DELAY(1000);
  END;
END;
{----------------------------------------------------------------------------}
PROCEDURE create_etha;
VAR i:INTEGER;
BEGIN
  lp_etha.maxetha:=mini(8*LONGINT(r.nrows),max_size DIV SIZEOF(tet));
  lp_etha.init(lp_etha.maxetha);
END;
{----------------------------------------------------------------------------}
BEGIN
  IF lp_par.get_bool_par('TIGHT') THEN compress;
  new_hash:=FALSE; penalty:=1e10; nnull:=0; ngr:=0; nkl:=0; ngl:=0;
  with_eps:=0; npricing:=30000; test_bounds;
  FOR j:=1 TO nvar DO WITH co^[j]^ DO INC(nnull,canzahl);
  matspace:=(nnull+r.nrows)*SIZEOF(waag)*2;
  IF NOT stat_geladen THEN EXIT;
  {$IFNDEF win32}
  scr.CLRSCR; scr.WRITELN('FILE : '+fname_);
  scr.WRITE('R '+ltoa(r.nrows)+' C '+ltoa(nvar)+' <>0 '+ltoa(nnull));
  scr.WRITE(' '+ftoa_x(100.0*nnull/r.nrows/nvar,5,2)+' %'); nextx:=scr.WHEREX;
  scr.WINDOW(1,6,80,25); scr.GOTOXY(1,1);
  {$ENDIF}
  IF nvar+r.nrows>maxcolumn THEN
  BEGIN
    my_message('FATAL ERROR'#10#13'MAXcolumn TOO SMALL'); quit;
    {$IFDEF win32} application.terminate; {$ENDIF}
  END;
  FOR i:=1 TO nvar DO WITH co^[i]^ DO IF pos(' ',spa_)<>0 THEN my_message('Var '+spa_+' Has Spaces');
  FOR i:=1 TO r.nrows DO WITH r.ro^[i]^ DO
  BEGIN
    CASE gleich OF
      -2,2: { unlimited row }
      BEGIN
        nvaralt:=nvar; welche:=search_column(row_,4);
        IF nvar=nvaralt THEN
        BEGIN
          IF row_=objf_ THEN objf_:=#255+objf_;
          row_:=#255+row_; welche:=search_column(row_,4); new_hash:=TRUE;
        END;
        put_matrix(i,welche,1); co^[welche]^.lo_bound:=-up_bound_lmt;
      END;
      -1: { < }
      BEGIN
        nvaralt:=nvar; welche:=search_column(row_,1);
        IF nvar=nvaralt THEN
        BEGIN
          row_:=#255+row_; new_hash:=TRUE; welche:=search_column(row_,1);
        END;
        put_matrix(i,welche,1); INC(nkl);
        { RANGE }
        x:=get_range(i);
        IF x<>0 THEN co^[welche]^.up_bound:=x;
      END;
      0: { = }
      BEGIN
        nvaralt:=nvar; welche:=search_column(row_,3);
        IF nvar=nvaralt THEN
        BEGIN
          row_:=#255+row_; new_hash:=TRUE; welche:=search_column(row_,3);
        END;
        put_matrix(i,welche,1); INC(ngl); co^[welche]^.up_bound:=0;
      END;
      1: { > }
      BEGIN
        nvaralt:=nvar; welche:=search_column(row_,2); INC(ngr);
        IF nvar=nvaralt THEN
        BEGIN
          row_:=#255+row_; new_hash:=TRUE; welche:=search_column(row_,2);
        END;
        put_matrix(i,welche,-1);
        { RANGE }
        x:=get_range(i);
        IF x<>0 THEN co^[welche]^.up_bound:=x;
      END;
    END;
  END;
  IF new_hash THEN put_hash;
  { Rows in die Basis }
  FOR i:=1 TO r.nrows DO swap_columns(i,nvar-r.nrows+i);
  {
  Tausche Zeilen so, dass am Ende der Matrix die N-ROWS stehen.
  Diese Rows muessen niemals mehr vertauscht werden.
  }
  j:=r.nrows; i:=j;
  WHILE i>0 DO
  BEGIN
    IF ABS(r.ro^[i]^.gleich)=2 THEN
    BEGIN
      swap_rows(i,j); DEC(j);
    END;
    DEC(i);
  END;
  swap_rows(r.search_row(objf_),r.nrows);

  IF stat_mit_frei THEN { Variables with FR BOUNDS }
  FOR j:=r.nrows+1 TO nvar DO WITH co^[j]^ DO IF art=5 THEN
  BEGIN
    welche:=r.nrows;
    FOR i:=1 TO canzahl DO welche:=mini(cm^[i].z,welche);
    ersatz:=search_column(co^[welche]^.spa_,-3);
    IF ersatz<r.nrows THEN swap_columns(j,ersatz);
  END;
  { in Basis }
  FOR i:=1 TO r.nrows DO co^[i]^.on_upper:=0;
  { on lower bound }
  FOR i:=r.nrows+1 TO nvar DO co^[i]^.on_upper:=-1;
  {$IFDEF win32}
  scr.win_out(koosum,'R '+ltoa(r.nrows)+' C '+ltoa(nvar-r.nrows)+' <>0 '+ltoa(nnull)+
  ' '+ftoa_x(100.0*nnull/r.nrows/nvar,5,2)+' %  > '+ltoa(ngr)+' = '+ltoa(ngl)+' < '+ltoa(nkl));
  {$ELSE}
  scr.WINDOW(1,1,80,25); scr.GOTOXY(nextx,2); scr.WRITE(' > '+ltoa(ngr)+' = '+ltoa(ngl)+' < '+ltoa(nkl));
  menu.unhide_all;
  {$ENDIF}
  {$IFNDEF win32}
  IF stat_ohne_fahne THEN WITH menu DO
  BEGIN
    hide(' CHART');
    hide(' DUMP MATRIX');
  END;
  {$IFDEF spar}
  WITH menu DO
  BEGIN
    hide('EDIT');
    hide(' CHART');
    hide(' DUMP MATRIX');
    hide(' CRASH');
    hide(' BASIS OUT OF SLACKS AND ARTIFICIALS');
    hide(' VECTOR');
    hide(' INFEASIBLE VECTORS');
    hide(' DEMYSTIFIED SOLUTION');
  END;
  {$ENDIF}
  scr.WINDOW(1,6,80,25); scr.CLRSCR;
  {$ENDIF}
  pricing.init(r.nrows); vvector.init(r.nrows); rhs.init(r.nrows);
  toggle_epsilon(1); create_etha; inversion; stat_built:=TRUE;
END;
{----------------------------------------------------------------------------}
PROCEDURE tlp2.lies_xpress_solution(f_:STRIN);
VAR s1_,col_,s_:STRIN;
    fp:TEXT;
    o,ncol:INTEGER;
    status:string[3];
    indicator:CHAR;
BEGIN
  IF pos('.',f_)<>0 THEN f_:=COPY(f_,1,pos('.',f_))+'ASC'
  ELSE f_:=f_+'.ASC';
  ASSIGNFILE(fp,f_); {$I-} RESET(fp); {$I+} o:=r.search_row(objf_);
  IF IORESULT<>0 THEN
  BEGIN
    my_message('FILE '+fname_+' not found'); EXIT;
  END;
  WHILE NOT EOF(fp) DO
  BEGIN
    READLN(fp,s_);
    col_:=rtrim(COPY(s_,9,8)); indicator:=s_[20];
    IF indicator='C' THEN
    BEGIN
      ncol:=search_column(col_,-3);
      WITH co^[ncol]^ DO
      BEGIN
        s1_:=ltrim(copy(s_,28,12)); float:=lese_zahl(s1_);
        IF (float<lo_bound) OR (float>up_bound) THEN
        my_message(col_+' Value outside bounds '+ftoa(lo_bound)+' '+ftoa(float)+' '+ftoa(up_bound));
        s1_:=ltrim(copy(s_,41,12)); float:=lese_zahl(s1_);
        IF abs(float-get_entry(o,ncol))>1e-4 THEN my_message(col_+' OBJF falsch '+ftoa(float)+' '+ftoa(get_entry(o,ncol)));
        s1_:=ltrim(copy(s_,54,12)); float:=lese_zahl(s1_);
        IF abs(float-lo_bound)>1e-4 THEN my_message(col_+' LO_bound falsch '+ftoa(float)+' '+ftoa(lo_bound));
        s1_:=ltrim(copy(s_,67,12)); float:=lese_zahl(s1_);
        IF abs(float-up_bound)>1e-4 THEN my_message(col_+' UP_bound falsch '+ftoa(float)+' '+ftoa(up_bound));
      END;
    END;
  END;
  CLOSEFILE(fp);
END;
{---------------------------------------------------------------------------}
PROCEDURE tlp2.lies_mpsx;
VAR nl,nvaralt,welche,ro,i,j:INTEGER;
    col_old,bou_,s2_,s1_,s_,row,col_:STRIN;
    ch:CHAR;
    bou:DOUBLE;
    buf:^buffer;
    in_int:BOOLEAN;
    fp:TEXT;
    te_:t_type;
LABEL 0,1,2,3,4,5,6;
{---------------------------------------------------------------------------}
PROCEDURE stop_read(cl:BOOLEAN);
BEGIN
  IF buf<>NIL THEN DISPOSE(buf); buf:=NIL;
{$IFDEF win32} LpForm.ProgressBar1.hide; {$ENDIF}  te_.done; new_model;
  IF cl THEN CLOSEFILE(fp);
END;
{---------------------------------------------------------------------------}
PROCEDURE readline;
BEGIN
  REPEAT
    READLN(fp,s_);
    {$IFDEF win32 }INC(nl);
    IF nl MOD 100=0 THEN
    WITH LpForm.ProgressBar1 DO IF position=max THEN  position:=0 ELSE stepit;
    {$ENDIF}
    IF s_[1]='*' THEN s_:=''; { Zeilen mit * in 1. Spalte sind Kommentar }
  UNTIL (s_<>'') OR EOF(fp);
  {
  s_:=ucase(s_);
  }
END;
{---------------------------------------------------------------------------}
BEGIN
  {$IFDEF win32}
    WITH LpForm DO
    BEGIN
      memo1.clear; nl:=0;
      WITH ProgressBar1 DO
      BEGIN
        show; position:=0; step:=1;
      END;
    END;
  {$ENDIF}
  IF stat_geladen THEN new_model;
  buf:=NIL; t_start:=julia; elapsed:=0;
  {$IFNDEF win32} IF MAXAVAIL>3*SIZEOF(buffer) THEN {$ENDIF} NEW(buf);
  stat_geladen:=TRUE; stat_maximise:=TRUE;
  IF POS('.',fname_)=0 THEN fname_:=fname_+'.MAT';
  ASSIGNFILE(fp,fname_);
  IF buf<>NIL THEN SETTEXTBUF(fp,buf^);
  te_.init;
  {$I-}RESET(fp); {$I+}
  IF IORESULT<>0 THEN
  BEGIN
    my_message('MATRIX FILE '+fname_+' NOT FOUND');
    stat_abbruch:=TRUE; stop_Read(FALSE); EXIT;
  END;
  if stat_with_log THEN APPEND_log_line('LOADING MPSX '+fname_);
  scr.CLRSCR; scr.WRITELN('LOADING MATRIX '+fname_);
  WHILE NOT EOF(fp) DO
  BEGIN
    0:
    readline;
    1:
    IF rtrim(s_)='ROWS' THEN
    BEGIN
      s_:='';
      scr.WRITELN('Reading ROWS');
      REPEAT
        readline;
        IF s_[1]<>' ' THEN GOTO 1;
        s_:=rtrim(ltrim(s_)); ch:=s_[1]; zerhacke(row,s_);
        IF NOT define_row(s_,ch) THEN
        BEGIN
          stop_Read(TRUE); EXIT;
        END;
        IF ch='N' THEN IF objf_='' THEN objf_:=s_;
        IF r.nrows MOD 100=0 THEN
        {$IFNDEF win32} scr.WRITE('.'); {$ENDIF}
      UNTIL FALSE;
    END
    ELSE IF rtrim(s_)='COLUMNS' THEN
    BEGIN
      {$IFDEF win32}
      scr.writeln(' '+ltoa(r.nrows));
      scr.writeln('Reading COLUMNS');
      {$ELSE}
      IF scr.WHEREX<>1 THEN scr.WRITELN('');
      scr.WRITELN('Reading COLUMNS');
      {$ENDIF}
      col_old:=''; in_int:=FALSE;
      IF objf_='' THEN
      BEGIN
        my_message('NO UNLIMITED ROW IN MATRIX'); stop_Read(TRUE); EXIT;
      END
      ELSE IF r.search_row(objf_)=0 THEN
      BEGIN
        my_message('DEFINED OBJF '+objf_+' NOT IN MATRIX'); stop_Read(TRUE); EXIT;
      END;
      REPEAT
        readline;
        IF s_[1]<>' ' THEN GOTO 1;
        IF POS(#39,s_)<>0 THEN
        BEGIN
          IF COPY(s_,41,6)='INTORG' THEN
          BEGIN
            IF NOT in_int THEN in_int:=TRUE ELSE
            BEGIN
              my_message('INTORG CARD FOUND AND STILL IN INTEGERSECTION');
              stat_abbruch:=TRUE; NEW_model;
            END;
          END
          ELSE
          IF COPY(s_,41,6)='INTEND' THEN
          BEGIN
            IF in_int THEN in_int:=FALSE ELSE
            BEGIN
              my_message('INTEND CARD FOUND AND NOT   IN INTEGERSECTION');
              stat_abbruch:=TRUE; new_model;
            END;
          END
        END
        ELSE
        BEGIN
          s_:=rtrim(ltrim(s_)); zerhacke(col_,s_);
          IF col_<>col_old THEN
          BEGIN
            welche:=search_column(col_,0); col_old:=col_;
            {$IFNDEF win32}
              IF nvar MOD 100=0 THEN scr.WRITE('.');
            {$ENDIF}
            IF in_int THEN WITH co^[welche]^ DO
            BEGIN
              inttype:=1; up_bound:=30000; p_plus:=pseu_init; p_minus:=pseu_init;
            END;
          END;
          WHILE s_<>'' DO
          BEGIN
            zerhacke(row,s_); ro:=r.search_row(row);
            IF ro=0 THEN
            BEGIN
              my_message('ROW '+row+' NOT DEFINED'); stop_Read(TRUE); EXIT;
            END
            ELSE put_matrix(ro,welche,lese_zahl(s_));
          END;
        END;
      UNTIL FALSE;
    END
    ELSE IF rtrim(s_)='RHS' THEN
    BEGIN
      IF scr.WHEREX<>1 THEN scr.WRITELN('');
      scr.WRITELN('Reading RHS');
      REPEAT
        readline;
        IF s_[1]<>' ' THEN GOTO 1;
        s1_:=ltrim(rtrim(COPY(s_,5,9)));
        IF rhs_='' THEN rhs_:=s1_ ELSE IF s1_<>rhs_ THEN
        s_:='';
        s_:=ltrim(COPY(s_,15,255));
        WHILE s_<>'' DO
        BEGIN
          zerhacke(row,s_); ro:=r.search_row(row);
          IF ro=0 THEN
          BEGIN
            my_message('ROW '+row+' NOT DEFINED'); stop_read(TRUE); EXIT;
          END
          ELSE
          BEGIN
            bou:=lese_zahl(s_);
            IF bou<>0 THEN put_matrix(ro,0,bou);
          END;
        END;
      UNTIL FALSE;
    END
    ELSE IF rtrim(s_)='BOUNDS' THEN
    BEGIN
      IF scr.WHEREX<>1 THEN scr.WRITELN('');
      scr.WRITELN('Reading BOUNDS');
      nvaralt:=nvar;
      REPEAT
        5:
        readline;
        IF s_[1]<>' ' THEN GOTO 1;
        s1_:=rtrim(ltrim(COPY(s_,5,10)));
        IF bound_='' THEN bound_:=s1_ ELSE IF s1_<>bound_ THEN
        BEGIN
          s_:=''; GOTO 5;
        END;
        bou_:=COPY(s_,2,2); s_:=ltrim(COPY(s_,15,255)); zerhacke(col_,s_);
        welche:=search_column(col_,0);
        IF welche>nvaralt THEN
        BEGIN
          my_message('BOUND SPECIFIED FOR NOT DEFINED COLUMN '+col_); stop_Read(TRUE); EXIT;
        END
        ELSE WITH co^[welche]^ DO
        BEGIN
          IF bou_='LO' THEN lo_bound:=lese_zahl(s_)
          ELSE IF bou_='UP' THEN up_bound:=lese_zahl(s_)
          ELSE IF bou_='FX' THEN
          BEGIN
            up_bound:=lese_zahl(s_); lo_bound:=up_bound;
          END
          ELSE IF bou_='FR' THEN { unlimited Variable is forced to be in Basis }
          BEGIN
            art:=5; stat_mit_frei:=TRUE;
          END
          ELSE IF bou_='MI' THEN { negative }
          BEGIN
            lo_bound:=-up_bound_lmt;
            up_bound:=0;
          END
          ELSE IF bou_='SC' THEN { semi continuous 0 or > 1 }
          BEGIN
            inttype:=2; p_plus:=pseu_init; p_minus:=pseu_init;
            IF s_<>'' THEN lo_bound:=lese_zahl(s_);
            IF s_<>'' THEN up_bound:=lese_zahl(s_);
          END
          ELSE IF bou_='BV' THEN { Binary 0,1 }
          BEGIN
            inttype:=1; p_plus:=pseu_init; p_minus:=pseu_init; up_bound:=1;
          END
          ELSE IF bou_='UI' THEN { up BOUND INTEGER }
          BEGIN
            up_bound:=min(300000,min(up_bound,lese_zahl(s_)));
            inttype:=1; p_plus:=pseu_init; p_minus:=pseu_init;
          END
          ELSE IF bou_='PL' THEN lo_bound:=max(lo_bound,0)
          { SPECIAL ORDER SET TYPE SOS 1 }
          ELSE IF bou_='S1' THEN
          BEGIN
            j:=search_column(s_,0);
            IF j>nvaralt THEN
            BEGIN
              scr.WRITELN('ELEMENT OF SOS1 not defined in COLUMNS SECTION '+s_);
              stop_Read(TRUE); EXIT;
            END;
            IF NOT define_row(#1+ltoa(r.nrows+1),'E') THEN
            BEGIN
              stop_Read(TRUE); EXIT;
            END;
            put_matrix(r.nrows,0,1); { RHS=1}
            FOR i:=welche TO j DO WITH co^[i]^ DO
            BEGIN
              up_bound:=1; inttype:=1; p_plus:=pseu_init; p_minus:=pseu_init;
              put_matrix(r.nrows,i,1);
            END;
          END
          ELSE
          BEGIN
            my_message('BOUND INDICATOR '+s_+' NOT KNOWN'); stop_Read(TRUE); EXIT;
          END;
        END;
      UNTIL FALSE;
    END
    ELSE IF rtrim(s_)='RANGES' THEN
    BEGIN
      IF scr.WHEREX<>1 THEN scr.WRITELN('');
      scr.WRITELN('Reading RANGES');
      REPEAT
        6:
        readline;
        IF s_[1]<>' ' THEN GOTO 1;
        s1_:=ltrim(rtrim(COPY(s_,5,10)));
        IF range_='' THEN range_:=s1_ ELSE IF s1_<>range_ THEN
        BEGIN
          my_message('OTHER RANGE CARD '+s1_+' FOUND: CARD SKIPPED'); GOTO 6;
        END;
        s_:=ltrim(COPY(s_,15,255));
        WHILE s_<>'' DO
        BEGIN
          zerhacke(row,s_);
          ro:=r.search_row(row);
          IF ro=0 THEN
          BEGIN
            my_message('Row '+row+' not found in rows-section'); stop_Read(TRUE); EXIT;
          END;
          IF ABS(r.ro^[ro]^.gleich)<2 THEN put_range(ro,lese_zahl(s_))
          ELSE
          BEGIN
            my_message('RANGE ON UNLIMITED ROW: IGNORED'); stat_abbruch:=TRUE;
          END;
        END;
      UNTIL FALSE;
    END
    ELSE IF rtrim(s_)='SETSW' THEN
    BEGIN
      scr.WRITELN('Reading SETS');
      REPEAT
        readline;
        3:
        IF s_[1]<>' ' THEN GOTO 1;
        s_:=ltrim(s_); zerhacke(bou_,s_);
        IF bou_='S1' THEN { SPECIAL ORDER SET TYPE 1 }
        BEGIN
          IF NOT define_row(#1+ltoa(r.nrows+1),'E') THEN
          BEGIN
            stop_Read(TRUE); EXIT;
          END;
          put_matrix(r.nrows,0,1); { RHS=1}
          WHILE s_<>'' DO
          BEGIN
            zerhacke(col_,s_);
            j:=search_column(col_,0);
            WITH co^[j]^ DO
            BEGIN
              lo_bound:=0; up_bound:=1; inttype:=1; p_plus:=pseu_init;
              p_minus:=pseu_init; put_matrix(r.nrows,j,1);
            END;
          END;
          readline;
          GOTO 3;
        END;
      UNTIL FALSE;
    END
    ELSE IF rtrim(s_)='ENDATA' THEN
    BEGIN
      IF buf<>NIL THEN DISPOSE(buf);
      te_.done;
      {$IFDEF win32}
      scr.win_out(koohead,'    DJ     Vector_in  Vector_out     OBJF       INFEASIBILITY   nit  Time  ninv');
      {$ENDIF}
      scr.WRITELN('END MATRIX');
      CLOSEFILE(fp); build_objective_function;
      {$IFDEF win32} LpForm.ProgressBar1.hide; {$ENDIF}
      s_:=''; EXIT;
    END
    ELSE IF COPY(s_,1,4)='NAME' THEN GOTO 0
    ELSE
    BEGIN
      my_message('Wrong row in file '+s_); stop_read(TRUE); EXIT;
    END;
  END;
END;
{---------------------------------------------------------------------------}
PROCEDURE tlp2.lies_mpsx_komplett;
VAR nl,nvaralt,welche,ro,i,j:INTEGER;
    fp:TEXT;
    col_old,bou_,s2_,s1_,s_,row,col_:STRIN;
    ch:CHAR;
    bou:DOUBLE;
    buf:^buffer;
    in_int:BOOLEAN;
LABEL 0,1,2,3,4,5,6;
{---------------------------------------------------------------------------}
FUNCTION check(s_:STRIN):STRIN;
BEGIN
  s_:=rtrim(s_);
  WHILE POS(' ',s_)<>0 DO s_[POS(' ',s_)]:=CHR(255);
  check:=s_;
END;
{---------------------------------------------------------------------------}
PROCEDURE readline;
BEGIN
  REPEAT
    READLN(fp,s_);
    {$IFDEF win32 }INC(nl);
    IF nl MOD 100=0 THEN
    WITH LpForm.ProgressBar1 DO IF position=max THEN  position:=0 ELSE stepit;
    {$ENDIF}
    IF s_[1]='*' THEN s_:=''; { Zeilen mit * in 1. column sind Kommentar }
  UNTIL (s_<>'') OR EOF(fp);
END;
{---------------------------------------------------------------------------}
PROCEDURE stop_read(cl:BOOLEAN);
BEGIN
  IF buf<>NIL THEN DISPOSE(buf); buf:=NIL;
  {$IFDEF win32} LpForm.ProgressBar1.hide; {$ENDIF}
  new_model;
  IF cl THEN CLOSEFILE(fp);
END;
{---------------------------------------------------------------------------}
BEGIN
  {$IFDEF win32}
  LpForm.memo1.clear; nl:=0;
  {$ENDIF}
  IF stat_geladen THEN new_model;
  buf:=NIL; t_start:=julia; elapsed:=0;
  {$IFNDEF win32} IF MAXAVAIL>SIZEOF(buffer) THEN {$ENDIF} NEW(buf);
  stat_geladen:=TRUE; stat_maximise:=TRUE;
  IF POS('.',fname_)=0 THEN fname_:=fname_+'.MAT';
  ASSIGNFILE(fp,fname_);
  IF buf<>NIL THEN SETTEXTBUF(fp,buf^);
  {$I-} RESET(fp);  {$I+}
  IF IORESULT<>0 THEN
  BEGIN
    my_message('MATRIX FILE '+fname_+' NOT FOUND');
    stop_read(FALSE); EXIT;
  END;
  scr.CLRSCR; scr.WRITELN('LOADING MATRIX '+fname_); scr.GOTOXY(1,4);
  scr.WRITE(laenge('',79,'o'));
  IF stat_with_log THEN append_log_line('LOADING MPSX '+fname_);
  WHILE NOT EOF(fp) DO
  BEGIN
    0:
    readline;
    1:
    IF s_='ROWS' THEN
    BEGIN
      s_:='';
      scr.WRITELN('Reading ROWS');
      REPEAT
        readline;
        IF s_[1]<>' ' THEN GOTO 1;
        ch:=s_[2]; row:=check(COPY(s_,5,8));
        IF NOT define_row(row,ch)  THEN
        BEGIN
          stop_Read(TRUE); EXIT;
        END;
        IF ch='N' THEN IF objf_='' THEN objf_:=row;
        {$IFNDEF win32}
          IF r.nrows MOD 100=0 THEN scr.WRITE('.');
        {$ENDIF}
      UNTIL FALSE;
    END
    ELSE IF s_='COLUMNS' THEN
    BEGIN
      IF scr.WHEREX<>1 THEN scr.WRITELN('');
      scr.WRITELN('Reading COLUMNS');
      col_old:=''; in_int:=FALSE;
      IF objf_='' THEN
      BEGIN
        my_message('NO UNLIMITED ROW IN MATRIX'); stop_read(TRUE); EXIT;
      END
      ELSE IF r.search_row(objf_)=0 THEN
      BEGIN
        my_message('DEFINED OBJF '+objf_+' NOT IN MATRIX'); stop_read(TRUE); EXIT;
      END;
      REPEAT
        readline;
        IF s_[1]<>' ' THEN GOTO 1;
        IF POS(#39,s_)<>0 THEN
        BEGIN
          IF COPY(s_,41,6)='INTORG' THEN
          BEGIN
            IF NOT in_int THEN in_int:=TRUE ELSE
            BEGIN
              my_message('INTORG CARD FOUND AND STILL IN INTEGERSECTION');
              stop_read(TRUE); EXIT;
            END;
          END
          ELSE
          IF COPY(s_,41,6)='INTEND' THEN
          BEGIN
            IF in_int THEN in_int:=FALSE ELSE
            BEGIN
              my_message('INTEND CARD FOUND AND NOT IN INTEGERSECTION');
              stop_Read(TRUE); EXIT;
            END;
          END
        END
        ELSE
        BEGIN
          col_:=check(COPY(s_,5,8)); s1_:=COPY(s_,15,8);
          {$IFDEF win32}
          LpForm.addmemo(col_);
          {$ENDIF}
          IF col_<>col_old THEN
          BEGIN
            welche:=search_column(col_,0); col_old:=col_;
            {$IFNDEF win32}
              IF nvar MOD 100=0 THEN scr.WRITE('.');
            {$ENDIF}
            IF in_int THEN WITH co^[welche]^ DO
            BEGIN
              inttype:=1; up_bound:=30000; p_plus:=pseu_init; p_minus:=pseu_init; 
            END;
          END;
          row:=check(COPY(s_,15,8)); s1_:=ltrim(COPY(s_,25,255));
          ro:=r.search_row(row);
          IF ro=0 THEN
          BEGIN
            my_message('row '+row+' not found in rows-section'); stop_Read(TRUE); EXIT;
          END
          ELSE put_matrix(ro,welche,lese_zahl(s1_));
          row:=check(COPY(s_,40,8)); s1_:=ltrim(COPY(s_,50,255));
          IF ltrim(row)<>'' THEN
          BEGIN
            ro:=r.search_row(row);
            IF ro=0 THEN
            BEGIN
              my_message('row '+row+' not found in rows-section');
              stop_read(TRUE); EXIT;
            END
            ELSE put_matrix(ro,welche,lese_zahl(s1_));
          END;
        END;
      UNTIL FALSE;
    END
    ELSE IF s_='RHS' THEN
    BEGIN
      IF scr.WHEREX<>1 THEN scr.WRITELN('');
      scr.WRITELN('Reading RHS');
      REPEAT
        readline;
        IF s_[1]<>' ' THEN GOTO 1;
        s1_:=check(rtrim(COPY(s_,5,8)));
        IF rhs_='' THEN rhs_:=s1_ ELSE IF s1_<>rhs_ THEN
        BEGIN
          my_message('DEFINED RHS '+rhs_+' NOT IN MATRIX'); s_:=''; stop_read(TRUE); EXIT;
        END;
        row:=check(COPY(s_,15,8)); ro:=r.search_row(row); s1_:=ltrim(COPY(s_,25,255));
        IF ro=0 THEN
        BEGIN
          my_message('ROW '+row+' NOT FOUND'); stop_Read(TRUE); EXIT;
        END
        ELSE
        BEGIN
          bou:=lese_zahl(s1_);
          IF bou<>0 THEN put_matrix(ro,0,bou);
        END;
        row:=check(COPY(s_,40,8));
        IF ltrim(row)<>'' THEN
        BEGIN
          ro:=r.search_row(row); s1_:=ltrim(COPY(s_,50,255));
          IF ro=0 THEN
          BEGIN
            my_message('ROW '+row+' NOT FOUND'); stop_Read(TRUE); EXIT;
          END
          ELSE
          BEGIN
            bou:=lese_zahl(s1_);
            IF bou<>0 THEN put_matrix(ro,0,bou);
          END;
        END;
      UNTIL FALSE;
    END
    ELSE IF s_='BOUNDS' THEN
    BEGIN
      IF scr.WHEREX<>1 THEN scr.WRITELN('');
      scr.WRITELN('READING BOUNDS');
      nvaralt:=nvar;
      REPEAT
        5:
        readline;
        IF s_[1]<>' ' THEN GOTO 1;
        s1_:=check(rtrim(COPY(s_,5,8)));
        IF bound_='' THEN bound_:=s1_ ELSE IF s1_<>bound_ THEN
        BEGIN
          my_message('OTHER BOUND CARD ! FOUND: CARD SKIPPED'); GOTO 5;
        END;
        bou_:=COPY(s_,2,2);
        col_:=check(COPY(s_,15,8)); s1_:=ltrim(COPY(s_,25,255));
        welche:=search_column(col_,0);
        IF welche>nvaralt THEN
        BEGIN
          my_message('BOUND SPECIFIED FOR NOT DEFINED COLUMN '+col_);
          stop_Read(TRUE); EXIT;
        END
        ELSE WITH co^[welche]^ DO
        BEGIN
          IF      bou_='LO' THEN lo_bound:=lese_zahl(s1_)
          ELSE IF bou_='UP' THEN up_bound:=lese_zahl(s1_)
          ELSE IF bou_='FX' THEN
          BEGIN
            up_bound:=lese_zahl(s1_);
            lo_bound:=up_bound;
          END
          ELSE IF bou_='FR' THEN { unlimited Variable is forced to be in Basis }
          BEGIN
            art:=5; lo_bound:=0; stat_mit_frei:=TRUE;
          END
          ELSE IF bou_='MI' THEN { negative }
          BEGIN
            lo_bound:=-up_bound_lmt;
            up_bound:=0;
          END
          ELSE IF bou_='SC' THEN { semi continuous 0 or > 1 }
          BEGIN
            inttype:=2; p_plus:=pseu_init; p_minus:=pseu_init;
            IF s_<>'' THEN lo_bound:=lese_zahl(s1_);
            IF s_<>'' THEN up_bound:=lese_zahl(s1_);
          END
          ELSE IF bou_='BV' THEN { Binary 0,1 }
          BEGIN
            up_bound:=1; inttype:=1; p_plus:=pseu_init; p_minus:=pseu_init;
          END
          ELSE IF bou_='UI' THEN { up BOUND INTEGER }
          BEGIN
            up_bound:=lese_zahl(s1_);
            inttype:=1; p_plus:=pseu_init; p_minus:=pseu_init;
          END
          ELSE IF bou_='PL' THEN lo_bound:=max(lo_bound,0)
          { SPECIAL ORDER SET TYPE 1 }
          ELSE IF bou_='S1' THEN
          BEGIN
            j:=search_column(s_,0);
            IF j>nvaralt THEN
            BEGIN
              my_message('ELEMENT OF SOS1 not defined in COLUMNS SECTION '+s_);
              stop_Read(TRUE); EXIT;
            END;
            IF NOT define_row(#1+ltoa(r.nrows+1),'E') THEN
            BEGIN
              stop_Read(TRUE); EXIT;
            END;
            r.ro^[r.nrows]^.gleich:=0; { E-ROW }
            put_matrix(r.nrows,0,1); { RHS=1}
            FOR i:=welche TO j DO WITH co^[i]^ DO
            BEGIN
              inttype:=1; p_plus:=pseu_init; p_minus:=pseu_init;
              up_bound:=1; put_matrix(r.nrows,i,1);
            END;
          END
          ELSE
          BEGIN
            my_message('BOUND INDICATOR '+s_+' NOT KNOWN'); stop_Read(TRUE); EXIT;
          END;
        END;
      UNTIL FALSE;
    END
    ELSE IF s_='RANGES' THEN
    BEGIN
      IF scr.WHEREX<>1 THEN scr.WRITELN('');
      scr.WRITELN('Reading RANGES');
      REPEAT
        6:
        readline;
        IF s_[1]<>' ' THEN GOTO 1;
        s1_:=check(rtrim(COPY(s_,5,8)));
        IF range_='' THEN range_:=s1_ ELSE IF s1_<>range_ THEN
        BEGIN
          my_message('OTHER RANGE CARD '+s1_+' FOUND: CARD SKIPPED');
          GOTO 6;
        END;
        row:=check(COPY(s_,15,8)); s1_:=ltrim(COPY(s_,25,255));
        ro:=r.search_row(row);
        IF ro=0 THEN
        BEGIN
          my_message('ROW '+row+' NOT FOUND'); stop_Read(TRUE); EXIT;
        END;
        IF ABS(r.ro^[ro]^.gleich)<2 THEN put_range(ro,lese_zahl(s1_))
        ELSE my_message('RANGE ON UNLIMITED ROW: IGNORED');
        row:=check(COPY(s_,40,8));
        IF row<>'' THEN
        BEGIN
          s1_:=ltrim(COPY(s_,50,255));
          ro:=r.search_row(row);
          IF ro=0 THEN
          BEGIN
            my_message('ROW '+row+' NOT FOUND'); stop_Read(TRUE); EXIT;
          END;
          IF ABS(r.ro^[ro]^.gleich)<2 THEN put_range(ro,lese_zahl(s1_))
          ELSE my_message('RANGE ON UNLIMITED ROW: IGNORED');
        END;
      UNTIL FALSE;
    END
    ELSE IF s_='ENDATA' THEN
    BEGIN
      IF buf<>NIL THEN DISPOSE(buf);
      s_:=''; CLOSEFILE(fp); build_objective_function; buf:=NIL;
      {$IFDEF win32}
      LpForm.ProgressBar1.hide;
      scr.win_out(koohead,'    DJ     Vector_in  Vector_out     OBJF       INFEASIBILITY   nit  Time  ninv');
      {$ENDIF}
      EXIT;
    END
    ELSE IF COPY(s_,1,4)='NAME' THEN GOTO 0
    ELSE
    BEGIN
      my_message('Wrong row in file '+s_); stop_Read(TRUE);
      {$IFDEF Win32 } LpForm.ProgressBar1.hide; {$ENDIF} EXIT;
    END;
  END;
  {$IFDEF Win32 }LpForm.ProgressBar1.hide; {$ENDIF}
END;
{----------------------------------------------------------------------------}
PROCEDURE tlp2.lies_lp;
VAR zeile_,row,col_,bou_,s_,s1_:STRIN;
    fp:TEXT;
    ro,j,nvaralt,wx,wy,canzahl,welche,ndigi,i:INTEGER;
    faktor:DOUBLE;
    buf:^buffer;
    defined,mit_buffer:BOOLEAN;
    m:tladder;
LABEL 1,2,5,6;

FUNCTION check_for_bounds:BOOLEAN;
VAR welcher,not_rhs,i:INTEGER;
    rhs_val:DOUBLE;
BEGIN
  check_for_bounds:=FALSE;
  IF r.nrows=1 THEN EXIT;
  not_rhs:=0; rhs_val:=0;
  FOR i:=1 TO canzahl DO WITH m.cm^[i] DO IF z=0 THEN rhs_val:=wert ELSE
  BEGIN
    INC(not_rhs); welcher:=i;
  END;
  IF not_rhs=1 THEN
  BEGIN
    check_for_bounds:=TRUE;
    FOR i:=1 TO canzahl DO WITH m.cm^[i] DO IF z<>0 THEN
    WITH co^[z]^ DO CASE r.ro^[r.nrows]^.gleich OF
      -1:
      up_bound:=rhs_val/wert;
      0:
      BEGIN
        up_bound:=rhs_val/wert; lo_bound:=up_bound;
      END;
      1:
      lo_bound:=rhs_val/wert;
    END;
    DEC(r.nrows);
  END;
END;
{---------------------------------------------------------------------------}
PROCEDURE fill_zeile_in_matrix;
VAR i:INTEGER;
BEGIN
  IF NOT check_for_bounds THEN FOR i:=1 TO canzahl DO WITH m.cm^[i] DO put_matrix(r.nrows,z,wert);
  canzahl:=0;
END;
{---------------------------------------------------------------------------}
PROCEDURE put_zeile(spalte:INTEGER; faktor:DOUBLE);
VAR i:INTEGER;
BEGIN
  INC(canzahl);
  IF canzahl>m.coben THEN
  BEGIN
    IF LONGINT(m.coben+inkrem)*LONGINT(SIZEOF(waag))>max_size THEN
    BEGIN
      my_MESSAGE('TOO MANY ENTRIES PER ROW'); HALT;
    END;
    m.longer(inkrem)
  END;
  WITH m.cm^[canzahl] DO
  BEGIN
    z:=spalte; wert:=faktor;
  END;
END;
{---------------------------------------------------------------------------}
PROCEDURE stop_read;
BEGIN
  IF mit_buffer THEN DISPOSE(buf);
  new_model; CLOSEFILE(fp);
END;
{---------------------------------------------------------------------------}
BEGIN
  {$IFDEF win32}
    LpForm.memo1.clear;
  {$ENDIF}
  IF stat_geladen THEN new_model;
  mit_buffer:=FALSE;
  {$IFNDEF win32} IF MAXAVAIL>SIZEOF(buffer)+10000 THEN {$ENDIF}
  BEGIN
    NEW(buf); mit_buffer:=TRUE;
  END;
  scr.CLRSCR; scr.WRITE('LOADING LP-FILE '+fname_);
  stat_maximise:=TRUE; m.init(inkrem); canzahl:=0; t_start:=julia; elapsed:=0;
  scr.WRITE('Reading row     : '); wx:=scr.WHEREX; wy:=scr.WHEREY;
  IF POS('.',fname_)=0 THEN fname_:=fname_+'.LP';
  ASSIGNFILE(fp,fname_); {$I-} RESET(fp); {$I+}
  IF IORESULT<>0 THEN
  BEGIN
    scr.WRITE('File not found : '+fname_);
    EXIT;
  END;
  stat_geladen:=TRUE;
  IF mit_buffer THEN SETTEXTBUF(fp,buf^);
  s_:=''; r.nrows:=0; defined:=FALSE;
  IF stat_with_log THEN append_log_line('LOADING LP '+fname_);
  WHILE NOT EOF(fp) DO
  BEGIN
    2:
    READLN(fp,s_); zeile_:=s_;
    IF s_='' THEN GOTO 2;
    s_:=rtrim(ltrim(s_)); zerhacke(s1_,s_);
    IF s1_[1]='#' THEN
    BEGIN
      s_:=''; { Kommentar Zeile } s1_:=''; GOTO 2;
    END;
    IF s1_[LENGTH(s1_)]=':' THEN { : am Ende des 1. Strings name of row }
    BEGIN
      s1_:=COPY(s1_,1,LENGTH(s1_)-1);
      IF r.nrows=0 THEN
      BEGIN
        IF s1_='MAX' THEN
        BEGIN
          IF NOT define_row(s1_,'N')  THEN
          BEGIN
            stop_read; EXIT;
          END;
          stat_maximise:=TRUE; objf_:=s1_; defined:=TRUE;
        END
        ELSE
        IF s1_='MIN' THEN
        BEGIN
          IF NOT define_row(s1_,'N') THEN
          BEGIN
            stop_Read; EXIT;
          END;
          stat_maximise:=FALSE; objf_:=s1_; defined:=TRUE;
        END
      END
      ELSE
      BEGIN
        IF NOT define_row(s1_,'N') THEN
        BEGIN
        END;
      END;
      s1_:=''; defined:=TRUE;
    END;
    IF s1_=':ENDE' THEN
    BEGIN
      READLN(fp,s_);
      s_:=ucase(rtrim(s_));
      1:
      IF s_='BOUNDS' THEN
      BEGIN
        scr.WRITELN('BOUNDS found');
        REPEAT
          5:
          READLN(fp,s_); s_:=rtrim(s_); nvaralt:=nvar;
          IF s_[1]<>' ' THEN GOTO 1;
          s1_:=rtrim(ltrim(COPY(s_,5,10)));
          IF bound_='' THEN bound_:=s1_ ELSE IF s1_<>bound_ THEN
          BEGIN
            s_:=''; GOTO 5;
          END;
          bou_:=COPY(s_,2,2); s_:=ltrim(COPY(s_,15,255)); zerhacke(col_,s_);
          welche:=search_column(col_,0);
          IF welche>nvaralt THEN
          BEGIN
            {$IFDEF win32}
            LpForm.addmemo('BOUND SPECIFIED FOR NOT DEFINED COLUMN '+col_);
            {$ELSE}
            my_message('BOUND SPECIFIED FOR NOT DEFINED COLUMN '+col_);
            {$ENDIF}
            stop_Read; EXIT;
          END
          ELSE WITH co^[welche]^ DO
          BEGIN
            IF      bou_='LO' THEN lo_bound:=lese_zahl(s_)
            ELSE IF bou_='UP' THEN up_bound:=lese_zahl(s_)
            ELSE IF bou_='FX' THEN
            BEGIN
              up_bound:=lese_zahl(s_); lo_bound:=up_bound;
            END
            ELSE IF bou_='FR' THEN { unlimited Variable is forced to be in Basis }
            BEGIN
              art:=5; lo_bound:=0; stat_mit_frei:=TRUE;
            END
            ELSE IF bou_='MI' THEN { negative }
            BEGIN
              lo_bound:=-up_bound_lmt; up_bound:=0;
            END
            ELSE IF bou_='SC' THEN { semi continuous 0 or > 1 }
            BEGIN
              lo_bound:=0; inttype:=2; p_plus:=pseu_init; p_minus:=pseu_init;
              IF s_<>'' THEN lo_bound:=lese_zahl(s_);
              IF s_<>'' THEN up_bound:=lese_zahl(s_);
            END
            ELSE IF bou_='BV' THEN { Binary 0,1 }
            BEGIN
              lo_bound:=0; up_bound:=1; inttype:=1; p_plus:=pseu_init; p_minus:=pseu_init;
            END
            ELSE IF bou_='UI' THEN { up BOUND INTEGER }
            BEGIN
              up_bound:=lese_zahl(s_); inttype:=1; p_plus:=pseu_init; p_minus:=pseu_init;
            END
            ELSE IF bou_='PL' THEN lo_bound:=max(lo_bound,0)
            { SPECIAL ORDER SET TYPE 1 }
            ELSE IF bou_='S1' THEN
            BEGIN
              j:=search_column(s_,0);
              IF j>nvaralt THEN
              BEGIN
                {$IFDEF win32}
                LpForm.addmemo('ELEMENT OF SOS1 not defined in COLUMNS SECTION '+s_);
                {$ELSE}
                my_message('ELEMENT OF SOS1 not defined in COLUMNS SECTION '+s_);
                {$ENDIF}
                stop_Read; EXIT;
              END;
              IF NOT define_row(#1+ltoa(r.nrows+1),'E') THEN
              BEGIN
                stop_Read; EXIT;
              END;
              put_matrix(r.nrows,0,1); { RHS=1}
              FOR i:=j TO welche DO WITH co^[i]^ DO
              BEGIN
                inttype:=1; up_bound:=1; p_plus:=pseu_init; p_minus:=pseu_init;
                put_matrix(r.nrows,i,1);
              END;
            END
            ELSE
            BEGIN
              {$IFDEF win32}
                LpForm.addmemo('Bound indicator no known '+s_);
              {$ELSE}
                my_message('BOUND INDICATOR '+s_+' NOT KNOWN');
              {$ENDIF}
              stop_Read; EXIT;
            END;
          END;
        UNTIL FALSE;
      END;
      IF s_='RANGES' THEN
      BEGIN
        scr.GOTOXY(1,5);
        scr.WRITELN(laenge('READING RANGES',79,' '));
        REPEAT
          6:
          READLN(fp,s_); s_:=rtrim(s_);
          IF s_[1]<>' ' THEN GOTO 1;
          s1_:=ltrim(rtrim(COPY(s_,5,10)));
          IF range_='' THEN range_:=s1_ ELSE IF s1_<>range_ THEN
          BEGIN
            {$IFDEF win32}
              LpForm.addmemo('OTHER RANGE CARD '+s1_+' FOUND: CARD SKIPPED');
            {$ELSE}
            my_message('OTHER RANGE CARD '+s1_+' FOUND: CARD SKIPPED');
            {$ENDIF}
            GOTO 6;
          END;
          s_:=ltrim(COPY(s_,15,255));
          WHILE s_<>'' DO
          BEGIN
            zerhacke(row,s_); ro:=r.search_row(row);
            IF ro=0 THEN
            BEGIN
              {$IFDEF win32}
                LpForm.addmemo('ROW '+row+' NOT FOUND');
              {$ELSE}
              my_message('ROW '+row+' NOT FOUND');
              {$ENDIF}
              stop_Read; EXIT;
            END;
            IF ABS(r.ro^[ro]^.gleich)<2 THEN put_range(ro,lese_zahl(s_))
            ELSE
            BEGIN
              {$IFDEF win32}
                LpForm.addmemo('RANGE ON UNLIMITED ROW: IGNORED');
              {$ELSE}
              my_message('RANGE ON UNLIMITED ROW: IGNORED');
              {$ENDIF}
            END;
          END;
        UNTIL FALSE;
      END;
      IF s_='ENDATA' THEN
      BEGIN
        scr.writeln('END MATRIX');
        {$IFDEF win32}
          scr.win_out(koohead,'    DJ     Vector_in  Vector_out     OBJF       INFEASIBILITY   nit  Time  ninv');
        {$ELSE}
          scr.WRITELN('');
        {$ENDIF}
        IF mit_buffer THEN DISPOSE(buf);
        s_:=''; CLOSEFILE(fp); build_objective_function; EXIT;
      END;
      EXIT;
    END;
    IF NOT DEFINED THEN
    BEGIN
      IF NOT define_row('R'+ltoa(r.nrows),'N') THEN
      BEGIN
        stop_Read; EXIT;
      END;
    END;
    s_:=s1_+s_; WHILE POS(' ',s_)<>0 DO DELETE(s_,POS(' ',s_),1);
    REPEAT
      faktor:=1;
      REPEAT
        IF s_[1]='+' THEN s_:=COPY(s_,2,255);
        IF s_[1]='-' THEN
        BEGIN
          s_:=COPY(s_,2,255); faktor:=-faktor;
        END;
      UNTIL (s_='') OR ((s_[1]<>'+') AND (s_[1]<>'-'));
      ndigi:=wieviel_float(s_);
      IF ndigi<>0 THEN { Zeile beginnt mit einer Zahl }
      BEGIN
        s1_:=COPY(s_,1,ndigi); s_:=COPY(s_,ndigi+1,255);
        faktor:=faktor*lese_zahl(s1_);
        IF s_[1]<>'*' THEN s_:='*'+s_;
        s_:=COPY(s_,2,255); ndigi:=wieviel_var(s_);
        IF ndigi=0 THEN
        BEGIN
          {$IFDEF win32}
            LpForm.addmemo('* without variable in row '+ltoa(r.nrows));
            LpForm.addmemo(zeile_);
          {$ELSE}
          my_message('* without variable in row '+ltoa(r.nrows)+#10#13+zeile_);
          {$ENDIF}
          stop_Read; EXIT;
        END;
        s1_:=COPY(s_,1,ndigi); s_:=COPY(s_,ndigi+1,255);
        put_zeile(search_column(s1_,0),faktor);
      END
      ELSE
      BEGIN
        ndigi:=wieviel_var(s_);
        IF ndigi<>0 THEN { Zeile beginnt mit einer Variablen }
        BEGIN
          s1_:=COPY(s_,1,ndigi); s_:=COPY(s_,ndigi+1,255);
          welche:=search_column(s1_,0);
          IF s_[1]='*' THEN
          BEGIN
            s_:=COPY(s_,2,255); ndigi:=wieviel_float(s_);
            IF ndigi=0 THEN
            BEGIN
              {$IFDEF win32}
              LpForm.addmemo('* WITHOUT NUMBER');
              {$ELSE}
              my_message('* without Number');
              {$ENDIF}
              stop_Read; EXIT;
            END;
            s1_:=COPY(s_,1,ndigi); s_:=COPY(s_,ndigi+1,255);
            faktor:=faktor*lese_zahl(s1_);
          END;
          put_zeile(welche,faktor);
        END
        ELSE
        BEGIN
          IF r.nrows>1 THEN IF s_[1] IN ['=','<','>'] THEN
          BEGIN
            s1_:=s_[1]; s_:=COPY(s_,2,255);
            CASE s1_[1] OF
              '<': r.ro^[r.nrows]^.gleich:=-1;
              '=': r.ro^[r.nrows]^.gleich:=0;
              '>': r.ro^[r.nrows]^.gleich:=1;
            END;
            ndigi:=wieviel_float(s_);
            IF ndigi<>0 THEN
            BEGIN
              s1_:=COPY(s_,1,ndigi); s_:=COPY(s_,ndigi+1,255);
              faktor:=lese_zahl(s1_);
              IF faktor<>0 THEN put_zeile(0,faktor);
            END
            ELSE
            BEGIN
              {$IFDEF win32}
              LpForm.addmemo('After <=> no number');
              LpForm.addmemo(zeile_);
              {$ELSE}
              my_message('After <=> no number'#10#13+zeile_);
              {$ENDIF}
              stop_Read; EXIT;
            END;
          END;
          IF s_<>';' THEN
          BEGIN
            {$IFDEF win32}
            LpForm.addmemo('ERROR IN ROW '+ltoa(r.nrows));
            LpForm.addmemo(zeile_);
            {$ELSE}
            my_message('Error in row '+ltoa(r.nrows)+#10#13+zeile_);
            {$ENDIF}
            stop_Read; EXIT;
          END
          ELSE
          BEGIN
            fill_zeile_in_matrix; s_:=''; defined:=FALSE;
          END;
        END;
      END;
    UNTIL s_='';
  END;
END;
{----------------------------------------------------------------------------}
PROCEDURE tlp2.save_basis(out:BOOLEAN);
VAR i:INTEGER;
    fp:TEXT;
    basis_name_,s_:STRIN;
BEGIN
  IF NOT stat_geladen THEN EXIT;
  basis_name_:=COPY(fname_,1,POS('.',fname_))+'BSS';
  ASSIGNFILE(fp,basis_name_);
  {$I-} REWRITE(fp); {$I+}
  IF IORESULT<>0 THEN
  BEGIN
    scr.WRITELN('CANNOT WRITE BASISFILE');
    stat_abbruch:=TRUE; NEW_model; EXIT;
  END;
  IF out THEN
  BEGIN
    scr.WRITE('SAVE BASIS '+basis_name_); scr.WRITELN('');
  END;
  WRITELN(fp,fname_); s_:='';
  FOR i:=1 TO r.nrows DO WITH co^[i]^ DO IF art<>4 THEN
  BEGIN
    s_:=s_+' X '+spa_;
    IF LENGTH(s_)>200 THEN
    BEGIN
      WRITELN(fp,s_); s_:='';
    END;
  END;
  FOR i:=r.nrows+1 TO nvar DO WITH co^[i]^ DO IF on_upper=1 THEN
  BEGIN
    s_:=s_+' U '+spa_;
    IF LENGTH(s_)>200 THEN
    BEGIN
      WRITELN(fp,s_); s_:='';
    END;
  END;
  IF s_<>'' THEN WRITELN(fp,s_);
  WRITELN(fp,'ENDATA'); last_save:=nit; CLOSEFILE(fp);
END;
{---------------------------------------------------------------------------}
PROCEDURE tlp2.load_basis(basis_name_:STRIN);
VAR nbas,i:INTEGER;
    fp:TEXT;
    col_,s1_,s_:STRIN;
BEGIN
  IF NOT stat_geladen THEN EXIT;
  basis_name_:=COPY(basis_name_,1,POS('.',basis_name_))+'BSS';
  ASSIGNFILE(fp,basis_name_);
  {$I-} RESET(fp); {$I+}
  IF IORESULT<>0 THEN
  BEGIN
    scr.WRITELN('BASIS FILE '+basis_name_+' NOT FOUND');
    EXIT;
  END;
  toggle_epsilon(0); READLN(fp);
  scr.WRITE('LOADING BASIS '+basis_name_); scr.WRITELN('');
  FOR i:=1 TO nvar DO WITH co^[i]^ DO on_upper:=-1;
  nbas:=0; i:=1;
  WHILE i<=nvar DO
  BEGIN
    IF co^[i]^.art>3 THEN
    BEGIN
      IF i<>nbas THEN
      BEGIN
        INC(nbas); swap_columns(i,nbas); co^[nbas]^.on_upper:=0;
      END ELSE INC(i);
    END
    ELSE INC(i);
  END;
  nit:=0;
  WHILE NOT EOF(fp) DO
  BEGIN
    READLN(fp,s_);
    IF s_[1]=' ' THEN
    BEGIN
      REPEAT
        s_:=ltrim(s_); zerhacke(s1_,s_); zerhacke(col_,s_);
        IF s1_='X' THEN
        BEGIN
          i:=search_column(col_,-3);
          IF i>nbas THEN co^[i]^.on_upper:=0;
        END
        ELSE IF s1_='U' THEN
        BEGIN
          i:=search_column(col_,-3);
          IF i>0 THEN IF co^[i]^.up_bound<1e20 THEN co^[i]^.on_upper:=1;
        END;
      UNTIL s_='';
    END;
  END;
  CLOSEFILE(fp);
  i:=nvar;
  WHILE i>nbas DO
  BEGIN
    IF co^[i]^.on_upper=0 THEN
    BEGIN
      INC(nbas); swap_columns(i,nbas);
    END
    ELSE DEC(i);
  END;
  FOR i:=r.nrows+1 TO nvar DO WITH co^[i]^ DO
  IF on_upper=1 THEN
  BEGIN
    IF up_bound>1e10 THEN on_upper:=-1;
  END
  ELSE
  BEGIN
    IF lo_bound<-1e10 THEN on_upper:=1;
  END;
  FOR i:=r.nrows+1 TO nvar DO WITH co^[i]^ DO IF art=5 THEN { FR-Vars }
  BEGIN
    IF on_upper=-1 THEN
    BEGIN
      up_bound:=up_bound_lmt; lo_bound:=0;
    END
    ELSE
    BEGIN
      lo_bound:=-up_bound_lmt; up_bound:=0;
    END;
  END;
  inversion;
  IF ninf>0 THEN toggle_epsilon(1);
END;
{---------------------------------------------------------------------------}
PROCEDURE tlp2.basis_of_rows;
VAR i:INTEGER;
BEGIN
  FOR i:=1 TO r.nrows DO swap_columns(i,search_column(r.ro^[i]^.row_,0));
  FOR i:=r.nrows+1 TO nvar DO WITH co^[i]^ DO
  BEGIN
    on_upper:=-1; { on lower Bound }
    IF art=5 THEN { FR-Variables }
    BEGIN
      up_bound:=up_bound_lmt; lo_bound:=0;
    END;
    IF lo_bound<-1e10 THEN on_upper:=1;
  END;
  inversion;
END;
{---------------------------------------------------------------------------}
END.
