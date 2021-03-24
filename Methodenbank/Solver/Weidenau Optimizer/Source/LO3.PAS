UNIT lo3;
{$O+F+}
INTERFACE

USES {$ifdef msdos} OVERLAY, {$ENDIF}
     {$IFNDEF win32} DOS,textfeld,fulledit, {$ENDIF}
     lo2,util,tty,p_settin;

TYPE tlp3=OBJECT(tlp2)
            PROCEDURE save_lp;
            PROCEDURE save_mpsx;
            PROCEDURE save_xpress;
            PROCEDURE loesung;
            PROCEDURE int_loesung;
            PROCEDURE save_infeasible;
            PROCEDURE delete_infeasible;
            PROCEDURE demyst;
            PROCEDURE show_infeasible;
            PROCEDURE define_problem;
            PROCEDURE save_pse;
            PROCEDURE lies_pse;
            PROCEDURE calc_p_m(VAR pseu:SINGLE; we,neu:DOUBLE);
            {$IFNDEF win32}
            PROCEDURE edit_mat(col_:STRIN);
            PROCEDURE select_objective;
            PROCEDURE edit_parameters;
            {$ENDIF}
          END;


IMPLEMENTATION

{$IFDEF win32} USES lp_form,sysutils,GRIDS; {$ENDIF}

PROCEDURE tlp3.calc_p_m(VAR pseu:SINGLE; we,neu:DOUBLE);
VAR s_:STRIN;
    ini:BOOLEAN;
    alt:SINGLE;
BEGIN
  IF NOT stat_abbruch THEN
  BEGIN
    alt:=pseu; pseu:=max(0,(ABS((objf-int_rec.vat)/(neu-we))));
    IF alt=pseu_init THEN
    BEGIN
      ini:=TRUE;
    END
    ELSE
    BEGIN
      pseu:=max(0,(0.01*alt+pseu)/1.01); ini:=FALSE;
    END;
    IF pseu>1e8 THEN s_:=laenge('ì',11,'ì') ELSE s_:=ftoa_x(pseu,11,2);
    IF ini THEN
    BEGIN
      s_[1]:='+'; INC(int_rec.neeu);
      scr.win_out(kooneeu,ltoa_x(int_rec.neeu,5));
    END;
    {$IFDEF win32}
      scr.sc_:=scr.sc_+' '+s_;
    {$ELSE}
      scr.WRITE(' '+s_);
    {$ENDIF}
  END;
END;
{----------------------------------------------------------------------------}
PROCEDURE tlp3.save_pse;
VAR i:INTEGER;
    fp:TEXT;
    s_:STRIN;
BEGIN
  s_:=COPY(fname_,1,POS('.',fname_))+'PSE'; ASSIGNFILE(fp,s_);
  {$I-} REWRITE(fp); {$I+}
  IF IORESULT<>0 THEN
  BEGIN
    CLOSEFILE(fp);
    scr.WRITELN('CANNOT WRITE '+s_);
    stat_abbruch:=TRUE; new_model; EXIT;
  END;
  FOR i:=1 TO nvar DO WITH co^[i]^ DO IF (inttype>0) AND
  ((p_plus<>pseu_init) OR (p_minus<>pseu_init)) THEN
  WRITELN(fp,spa_+' ',ftoa(p_plus),' ',ftoa(p_minus));
  CLOSEFILE(fp);
END;
{----------------------------------------------------------------------------}
PROCEDURE tlp3.lies_pse;
VAR i:INTEGER;
    fp:TEXT;
    s1_,s_:STRIN;
BEGIN
  s_:=COPY(fname_,1,POS('.',fname_))+'PSE'; ASSIGNFILE(fp,s_);
  {$I-} RESET(fp); {$I+}
  IF IORESULT=0 THEN
  BEGIN
    WHILE NOT EOF(fp) DO
    BEGIN
      READLN(fp,s_); zerhacke(s1_,s_); i:=search_column(s1_,-3);
      IF i>0 THEN WITH co^[i]^ DO
      BEGIN
        p_plus:=lese_zahl(s_); p_minus:=lese_zahl(s_);
      END;
    END;
    CLOSEFILE(fp);
  END;
END;
{----------------------------------------------------------------------------}
PROCEDURE tlp3.define_problem;
VAR i:INTEGER;
    {$IFNDEF win32}
    e:edit;
    {$ENDIF}
BEGIN
  {$IFDEF win32}
  WITH LpForm.stringgrid1 DO
  BEGIN
    rowcount:=5; colcount:=2;
    enabled:=TRUE; visible:=TRUE;
    cells[0,0]:='PROBLEM';
    cells[0,1]:='OBJECTIVE'; cells[1,1]:=objf_;
    cells[0,2]:='BOUNDS';    cells[1,2]:=bound_;
    cells[0,3]:='RHS';       cells[1,3]:=rhs_;
    cells[0,4]:='RANGES';    cells[1,4]:=range_;
    options:=[gofixedvertline,gofixedhorzline,gohorzline,govertline,
              gorowmoving,gocolsizing,goediting,gotabs];
  END;
  {$ELSE}
  WITH e DO
  BEGIN
    init;
    set_erlaubt(TRUE); add('DEFINE PROBLEM SETTINGS');
    add(''); add('OBJECTIVE '+objf_); add('BOUNDS    '+bound_);
    add('RHS       '+rhs_); add('RANGES    '+range_);
    WITH inp DO
    BEGIN
      xwinl:=1;   xbreite:=80; ywinl:=1; ybreite:=25;
      smin:=11;    smax:=255;
      sstart:=smin; zstart:=3;
      zmin:=1;    nz:=6; zmax:=nz; ins:=FALSE; frei:=FALSE;
      dest_:=ps.destination_+ps.init_;
    END;
    REPEAT
      i:=screen_edit;
      {$IFNDEF win32}
      IF i=315 THEN he.help(help_,ps.destination_+ps.init_,911);
      {$ENDIF}
    UNTIL i<>315; { F1}
    IF i=13 THEN
    BEGIN
      new_model;
      objf_ :=ucase(rtrim(ltrim(COPY(t_^[3].get,11,255))));
      bound_:=ucase(rtrim(ltrim(COPY(t_^[4].get,11,255))));
      rhs_  :=ucase(rtrim(ltrim(COPY(t_^[5].get,11,255))));
      range_:=ucase(rtrim(ltrim(COPY(t_^[6].get,11,255))));
    END;
    done;
  END;
  {$ENDIF}
END;
{----------------------------------------------------------------------------}
PROCEDURE tlp3.show_infeasible;
VAR n,j,i:INTEGER;
    s_:STRIN;
    w:DOUBLE;
    ch:CHAR;
    {$IFNDEF win32}
    e:edit;
    {$ENDIF}
CONST wid=14;
      nach=5;
BEGIN
  {$IFDEF win32}
  LpForm.memo1.clear;
  memomaxlines:=1000;
  LpForm.addmemo('INFEASIBLE VECTORS');
  LpForm.addmemo('NAME           KIND        LO-BOUND        UP-BOUND           VALUE       COST');
  LpForm.addmemo('');
  {$ELSE}
  WITH e DO
  BEGIN
    init; set_erlaubt(TRUE);
    add('INFEASIBLE VECTORS');
    add('NAME           KIND        LO-BOUND        UP-BOUND           VALUE       COST');
    add('');
    n:=3;
    {$ENDIF}
    FOR i:=1 TO r.nrows DO WITH co^[i]^ DO
    BEGIN
      w:=rhs.feld_pointer^[i];
      IF on_upper<>0 THEN
      BEGIN
        s_:=laenge(spa_,wid,' ');
        IF (art=0) OR (art=5) THEN s_:=s_+'COL  ' ELSE
        BEGIN
          s_:=s_+'ROW'; j:=r.search_row(spa_);
          CASE r.ro^[j]^.gleich OF
            -1: s_:=s_+' <';
            0:  s_:=s_+' =';
            1:  s_:=s_+' >';
            2:  s_:=s_+' N';
          END;
        END;
        s_:=s_+ftoa_x(lo_bound,wid,nach);
        IF up_bound>1e10 THEN s_:=s_+laenge('         +oo  ',wid,' ') ELSE s_:=s_+ftoa_x(up_bound,wid,nach);
        s_:=s_+ftoa_x(rhs.feld_pointer^[i],wid,nach)+ftoa_x(cost,wid,nach);
        IF rhs.feld_pointer^[i]>up_bound THEN s_:=s_+'+ '+ftoa_x(rhs.feld_pointer^[i]-up_bound,wid,nach)
        ELSE s_:=s_+'- '+ftoa_x(lo_bound-rhs.feld_pointer^[i],wid,nach);
        INC(n);
        {$IFDEF win32}
        LpForm.addmemo(s_);
        {$ELSE}
        add(s_);
        IF n>=max_zeilen THEN
        BEGIN
          my_message('TOO MANY VECTIRS ARE INFEASIBLE'); BREAK;
        END;
        {$ENDIF}
      END
    END;
    {$IFDEF win32}
    memomaxlines:=100;
    {$ELSE}
    WITH inp DO
    BEGIN
      xwinl:=1;   xbreite:=80; ywinl:=1; ybreite:=25;
      smin:=16;    smax:=255;
      sstart:=smin; zstart:=3;
      zmin:=3;    nz:=n; zmax:=nz; ins:=FALSE; frei:=FALSE;
      dest_:=ps.destination_+ps.init_;
    END;
    REPEAT
      i:=screen_edit;
      {$IFNDEF win32}
      IF i=315 THEN he.help(help_,ps.destination_+ps.init_,28);
      {$ENDIF}
    UNTIL i<>315; { F1}
  END;
  {$ENDIF}
END;
{----------------------------------------------------------------------------}
{$IFNDEF win32}
PROCEDURE tlp3.edit_parameters;
VAR n,i:INTEGER;
    s_:STRIN;
    fp:TEXT;
LABEL 1;
BEGIN
  IF lp_par.edit_parameter(4,25,ps.destination_+ps.init_,help_,906)=13 THEN
  BEGIN
    lies_aus_parameter;
    ASSIGNFILE(fp,parameter_); {$I-} REWRITE(fp); {$I+}
    IF IORESULT=0 THEN
    BEGIN
      lp_par.save_parameter(fp); CLOSEFILE(fp);
    END ELSE my_message('CANNOT WRITE '+parameter_);
    make_init(ps);
  END;
END;
{$ENDIF}
{----------------------------------------------------------------------------}
{$IFNDEF win32}
PROCEDURE tlp3.select_objective;
VAR e:edit;
    n,i:INTEGER;
    s_:STRIN;
BEGIN
  WITH e DO
  BEGIN
    init; set_erlaubt(TRUE);
    add('SELECT OBJECTIVE FUNCTION');
    add(''); n:=2;
    swap_rows(r.search_row(objf_),1);
    FOR i:=1 TO r.nrows DO IF r.ro^[i]^.gleich=2 THEN
    BEGIN
      INC(n); add(r.ro^[i]^.row_);
    END;
    WITH inp DO
    BEGIN
      xwinl:=1;   xbreite:=80; ywinl:=1; ybreite:=25;
      smin:=1;   smax:=80;
      sstart:=smin; zstart:=3;
      zmin:=3;    nz:=n; zmax:=nz; ins:=FALSE; frei:=FALSE;
      dest_:=ps.destination_+ps.init_;
    END;
    i:=screen_edit;
    IF i=13 THEN objf_:=rtrim(ltrim(t_^[inp.zstart].get));
    swap_rows(r.search_row(objf_),r.nrows);
    scr.CLRSCR; build_objective_function; done;
  END;
END;
{----------------------------------------------------------------------------}
PROCEDURE tlp3.edit_mat(col_:STRIN);
VAR e:edit;
    nrow,old_col,ncol,obj_row,ro,n1,i:INTEGER;
    r_,s_:STRIN;
    ch:CHAR;
    x:DOUBLE;
LABEL 1;
BEGIN
  WITH e DO
  BEGIN
    init; bild.save; scr.WINDOW(1,1,80,25); scr.CLRSCR;
    IF col_='' THEN
    BEGIN
      WRITE(#10#10#13,'ENTER VARIABLE : '); READLN(col_); col_:=rtrim(ltrim(col_));
    END;
    1:
    ncol:=search_column(col_,-3); clear_lines;
    IF ncol>0 THEN WITH co^[ncol]^ DO IF (art=0) OR (art=5) THEN { Column }
    BEGIN
      set_erlaubt(TRUE); obj_row:=r.search_row(objf_);
      add('EDIT COLUMN '+col_);
      IF ncol<=r.nrows THEN s_:='BASIC ' ELSE s_:='NON BASIC    dj='+ftoa(dj);
      IF (ncol<=r.nrows) AND (on_upper<>0) THEN s_:=s_+'  INFEASIBLE' ELSE s_:=s_+'  FEASIBLE';
      add(s_);
      add('COST        : '+ftoa(cost));
      add('LOW BOUND   : '+ftoa(lo_bound));
      add('UP  BOUND   : '+ftoa(up_bound));
      IF ncol<=r.nrows   THEN s_:=ftoa(rhs.feld_pointer^[ncol])
      ELSE IF on_upper=1 THEN s_:=ftoa(up_bound) ELSE s_:=ftoa(lo_bound);
      add('WERT        : '+s_);
      n1:=6;
      FOR i:=1 TO canzahl DO WITH cm^[i] DO IF z<>obj_row THEN
      BEGIN
        STR(wert,s_);
        INC(n1); add(laenge(r.ro^[z]^.row_+' ',14,' ')+s_);
        IF n1=max_zeilen THEN BREAK;
      END;
      FOR i:=n1+1 TO max_zeilen DO add('');
      WITH inp DO
      BEGIN
        xwinl:=1;   xbreite:=80; ywinl:=1; ybreite:=25;
        smin:=1;   smax:=80;
        sstart:=smin; zstart:=3;
        zmin:=3;    nz:=max_zeilen; zmax:=nz; ins:=FALSE; frei:=FALSE;
        dest_:=ps.destination_+ps.init_;
      END;
      i:=screen_edit;
      IF i=316 THEN { F2 }
      BEGIN
        s_:=t_^[inp.zstart].get; zerhacke(col_,s_); clear_lines; GOTO 1;
      END;
      IF i=315 THEN { HELP }
      BEGIN
        he.help(help_,ps.destination_+ps.init_,912); GOTO 1;
      END;
    END
    ELSE
    BEGIN
      r_:=col_;
      nrow:=r.search_row(r_);
      IF nrow>0 THEN
      BEGIN
        set_erlaubt(TRUE); add('EDIT ROW '+r_); ncol:=search_column(r_,0);
        WITH co^[ncol]^ DO
        BEGIN
          IF ncol<=r.nrows THEN s_:='BASIC ' ELSE
          BEGIN
            s_:='NON BASIC    dj=';
            IF r.ro^[r.search_row(r_)]^.gleich=0 THEN s_:=s_+ftoa(dj)
            ELSE  s_:=s_+ftoa(dj);
          END;
          IF (ncol<=r.nrows) AND (on_upper<>0) THEN s_:=s_+'  INFEASIBLE' ELSE s_:=s_+'  FEASIBLE';
          add(s_);
          add('COST        : '+ftoa(cost));
          add('LOW BOUND   : '+ftoa(lo_bound));
          add('UP  BOUND   : '+ftoa(up_bound));
          add('RHS         : '+ftoa(get_entry(nrow,0)));
          x:=get_range(nrow); s_:='RANGE       : ';
          IF x=0 THEN s_:=s_+'NO' ELSE s_:=s_+ftoa(x);
          add(s_);
          CASE r.ro^[nrow]^.gleich OF
            -1: s_:='TYPE        : <';
            0: s_:='TYPE        : =';
            1: s_:='TYPE        : >';
            2: s_:='TYPE        : UNLIMITED';
          END;
          add(s_);
          IF ncol<=r.nrows THEN s_:=ftoa(rhs.feld_pointer^[ncol])
          ELSE IF on_upper=1 THEN s_:=ftoa(up_bound) ELSE s_:=ftoa(lo_bound);
          add('WERT        : '+s_);
        END;
        n1:=9;
        FOR i:=1 TO nvar DO WITH co^[i]^ DO IF i<>ncol THEN
        BEGIN
          x:=get_entry(nrow,i);
          IF x<>0 THEN
          BEGIN
            STR(x,s_); INC(n1); add(laenge(spa_+' ',14,' ')+s_);
          END;
          IF n1=max_zeilen THEN BREAK;
        END;
        FOR i:=n1+1 TO max_zeilen DO add('');
        WITH inp DO
        BEGIN
          xwinl:=1;   xbreite:=80; ywinl:=1; ybreite:=25;
          smin:=1;   smax:=80;
          sstart:=smin; zstart:=3;
          zmin:=3;    nz:=max_zeilen; zmax:=nz; ins:=FALSE; frei:=FALSE;
          dest_:=ps.destination_+ps.init_;
        END;
        i:=screen_edit;
        IF i=316 THEN { F2 }
        BEGIN
          s_:=t_^[inp.zstart].get; zerhacke(col_,s_); clear_lines; GOTO 1;
        END;
        IF i=315 THEN { HELP }
        BEGIN
          he.help(help_,ps.destination_+ps.init_,912); GOTO 1;
        END;
        clear_lines;
      END;
    END;
    bild.restore; done;
  END;
END;
{$ENDIF}
{----------------------------------------------------------------------------}
PROCEDURE tlp3.int_loesung;
VAR j,i:INTEGER;
    fpint:TEXT;
    s_:STRIN;
    b:^buffer;
    x:DOUBLE;
BEGIN
  IF NOT stat_geladen THEN EXIT;
  scr.WRITELN('SAVE INTEGER SOLUTION');
  s_:=COPY(fname_,1,POS('.',fname_))+'INT'; b:=NIL;
  {$IFNDEF win32} IF MAXAVAIL>SIZEOF(buffer) THEN {$ENDIF} NEW(b);
  ASSIGNFILE(fpint,s_); IF b<>NIL THEN SETTEXTBUF(fpint,b^);
  {$I-} REWRITE(fpint); {$I+}
  IF IORESULT=0 THEN
  BEGIN
    WRITELN(fpint,'INTEGER SOLUTION');
    IF stat_abbruch THEN WRITELN(fpint,'UNFINISHED');
    WRITELN(fpint);
    IF int_rec.integer_found THEN
    BEGIN
      FOR i:=1 TO nvar DO WITH co^[i]^ DO
      CASE art OF
        0,5:
        BEGIN
          { Structural Column } WRITE(fpint,laenge(spa_,20,' '),int_value:20:10,' ',int_dj:10:5);
          IF inttype>0 THEN WRITELN(fpint,' I') ELSE WRITELN(fpint);
        END;
        1,3: { < row }
        BEGIN
          x:=get_entry(r.search_row(spa_),0); { RHS }
          WRITELN(fpint,laenge(spa_,20,' '),x-int_value:20:10,' ',int_dj:10:5);
        END;
        2: { > row }
        BEGIN
          x:=get_entry(r.search_row(spa_),0); { RHS }
          WRITELN(fpint,laenge(spa_,20,' '),x+int_value:20:10,' ',int_dj:10:5);
        END;
        4: { N-ROW }
        IF spa_=objf_ THEN WRITELN(fpint,laenge(spa_,20,' '),-int_value:20:10,' 0') ELSE
                           WRITELN(fpint,laenge(spa_,20,' '), int_value:20:10,' 0')
      END;
      IF stat_maximise THEN WRITELN(fpint,laenge('COBJF',20,' '),int_rec.upper_limit:16:5,' ',0.0:16:5)
      ELSE                  WRITELN(fpint,laenge('COBJF',20,' '),int_rec.lower_limit:16:5,' ',0.0:16:5);
    END
    ELSE WRITELN(fpint,'INTEGER-PROBLEM-INFEASIBLE');
    CLOSEFILE(fpint);
  END ELSE my_Message('CANNOT WRITE '+s_);
  IF b<>NIL THEN DISPOSE(b);
  b:=NIL;
END;
{---------------------------------------------------------------------------}
PROCEDURE tlp3.delete_infeasible;
BEGIN
  era(COPY(fname_,1,POS('.',fname_))+'INF');
END;
{---------------------------------------------------------------------------}
PROCEDURE tlp3.save_infeasible;
VAR j,i:INTEGER;
    fpint:TEXT;
    s_:STRIN;
    b:^buffer;
BEGIN
  IF NOT stat_geladen THEN EXIT;
  scr.WRITELN('SAVE INTEGER SOLUTION');
  s_:=COPY(fname_,1,POS('.',fname_))+'INF'; b:=NIL;
  {$IFDEF win32} NEW(b); {$ELSE} IF MAXAVAIL>SIZEOF(buffer) THEN NEW(b); {$ENDIF}
  ASSIGNFILE(fpint,s_); IF b<>NIL THEN SETTEXTBUF(fpint,b^);
  {$I-} REWRITE(fpint); {$I+}
  IF IORESULT=0 THEN
  BEGIN
    WRITELN(fpint,'INFEASIBLE VECTORS');
    WRITELN(fpint);
    FOR i:=1 TO r.nrows DO WITH co^[i]^ DO IF on_upper<>0 THEN
    BEGIN
      WRITELN(fpint,spa_,lo_bound:10:5,' ',up_bound:10:5,' ',rhs.feld_pointer^[i]:10:5);
    END;
    CLOSEFILE(fpint);
  END
  ELSE my_Message('CANNOT WRITE '+s_);
  IF b<>NIL THEN DISPOSE(b);
END;
{---------------------------------------------------------------------------}
PROCEDURE tlp3.loesung;
VAR zei,lmax,j,i:INTEGER;
    fp:TEXT;
    solfile_,ind_,s_,s1_:STRIN;
    cox,x,ra:DOUBLE;
    year,month,day,dayofweek:WORD;
    b:^buffer;
BEGIN
  IF NOT stat_geladen THEN EXIT;
  scr.WRITELN('SAVE SOLUTION');
  solfile_:=COPY(fname_,1,POS('.',fname_))+'LOS'; store_solution; b:=NIL;
  {$IFNDEF win32} IF MAXAVAIL>SIZEOF(buffer) THEN {$ENDIF} NEW(b);
  ASSIGNFILE(fp,solfile_);
  IF b<>NIL THEN SETTEXTBUF(fp,b^);
  {$I-} REWRITE(fp); {$I+}
  IF IORESULT=0 THEN
  BEGIN
    IF ninf>0         THEN s_:='PROBLEM IS INFEASIBLE' ELSE
    IF stat_unbounded THEN s_:='UNBOUNDED SOLUTION'  ELSE s_:='OPTIMAL SOLUTION';
    WRITELN(fp,s_);
    {$IFDEF win32}
    s1_:=timetostr(time);
    {$ELSE}
    GETDATE(year,month,day,dayofweek);
    s1_:=laenge_v(ltoa(month)+'.'+ltoa(year),7,'0');
    s1_:=laenge_v(ltoa(day)+'.'+s1_,10,'0');
    {$ENDIF}
    s_:='Weidenauer Optimizer Ver. '+version_+' '+s1_;
    WRITELN(fp,s_);
    s_:=#13#10+'Section 1 - ROWS'+#13#10; WRITELN(fp,s_);
    lmax:=maxi(12,row_lmax);
    s_:=laenge('...ROW..',lmax,' ')
    +'AT  ...ACTIVITY...  SLACK ACTIVITY  ..LOWER LIMIT.  ..UPPER LIMIT.  .DUAL ACTIVITY'+#13#10;
    WRITELN(fp,s_);
    { ROW is in BASIS }
    FOR j:=1 TO r.nrows DO co^[j]^.dj:=0;
    FOR j:=r.nrows+1 TO nvar DO WITH co^[j]^ DO
    BEGIN
      IF ABS(up_bound-lo_bound)<tolerance THEN on_upper:=-1; { fix Bound }
      calc_dj(j);
    END;
    FOR i:=1 TO r.nrows DO WITH co^[i]^ DO IF (art>0) AND (art<5) THEN { ROW }
    BEGIN
      zei:=r.search_row(spa_);
      CASE r.ro^[zei]^.gleich OF
        0: { = row }
        BEGIN
          IF ABS(float)<2*tolerance THEN ind_:='EQ' ELSE ind_:='**';
          s_:=laenge(spa_,lmax,' ')+ind_;
          x:=get_entry(i,0); { RHS }
          IF ind_='**' THEN
          BEGIN
            s_:=s_+ftoa_x(float,16,5);
            FOR j:=1 TO 3 DO s_:=s_+laenge(' ',16,' ');
          END
          ELSE
          BEGIN
            s_:=s_+ftoa_x(x,16,5)+ftoa_x(0.0,16,5)+ftoa_x(x,16,5)+ftoa_x(x,16,5);
          END;
          s_:=s_+ftoa_x(-dj,16,5);
        END;
        -1: { < Row }
        BEGIN
          x:=ABS(get_entry(zei,0)); ind_:='BS'; { RHS }
          IF on_upper<>0 THEN ind_:='**';
          ra:=get_range(zei);
          IF ra<>0 THEN IF float>ra+tolerance THEN ind_:='**';
          s_:=laenge(spa_,lmax,' ')+ind_+ftoa_x(x-float,16,5)+ftoa_x(float,16,5);
          IF ra<>0 THEN s_:=s_+ftoa_x(x-ra,16,5) ELSE s_:=s_+laenge(' ',16,' ');
          s_:=s_+ftoa_x(x,16,5);
        END;
        1: { > Row }
        BEGIN
          x:=ABS(get_entry(zei,0)); ind_:='BS'; { RHS }
          IF on_upper<>0 THEN ind_:='**';
          ra:=get_range(zei);
          IF ra<>0 THEN
          IF float>ra+tolerance THEN ind_:='**';
          s_:=laenge(spa_,lmax,' ')+ind_+ftoa_x(x+float,16,5);
          s_:=s_+ftoa_x(float,16,5)+ftoa_x(x,16,5);
          IF ra<>0 THEN s_:=s_+ftoa_x(ra,16,5) ELSE s_:=s_+laenge(' ',16,' ');
        END;
        2,-2:
        BEGIN
          s_:=laenge(spa_,lmax,' ')+'$$'+ftoa_x(float,16,5);
          s_:=s_+'            NONE            NONE';
        END;
      END;
      WRITELN(fp,s_);
    END;
    {ROW IS non BASIS}
    FOR i:=r.nrows+1 TO nvar DO WITH co^[i]^ DO IF (art>0) AND (art<5) THEN
    BEGIN
      zei:=r.search_row(spa_);
      IF zei>0 THEN
      BEGIN
        CASE r.ro^[zei]^.gleich OF
          0:
          BEGIN
            x:=get_entry(zei,0);
            IF ABS(float)<tolerance THEN ind_:='EQ' ELSE ind_:='**';
            s_:=laenge(spa_,lmax,' ')+ind_;
            s_:=s_+ftoa_x(x,16,5)+ftoa_x(0.0,16,5)+ftoa_x(x,16,5)+ftoa_x(x,16,5);
            s_:=s_+ftoa_x(dj,16,5);
          END;
          -1:
          BEGIN
            x:=get_entry(zei,0);
            IF on_upper=1 THEN ind_:='LL' ELSE ind_:='UL';
            s_:=laenge(spa_,lmax,' ')+ind_;
            IF x<>0 THEN s_:=s_+ftoa_x(x-float,16,5)+ftoa_x(float,16,5)
            ELSE s_:=s_+laenge(' ',16,' ')+laenge(' ',16,' ');
            ra:=get_range(zei);
            IF ra<>0 THEN s_:=s_+ftoa_x(x-ra,16,5) ELSE s_:=s_+'            NONE';
            IF x=0 THEN s_:=s_+laenge(' ',16,' ') ELSE s_:=s_+ftoa_x(x,16,5);
            s_:=s_+ftoa_x(dj,16,5);
          END;
          1:
          BEGIN
            x:=get_entry(zei,0);
            IF on_upper=1 THEN ind_:='UL' ELSE ind_:='LL';
            s_:=laenge(spa_,lmax,' ')+ind_;
            IF x<>0 THEN s_:=s_+ftoa_x(x+float,16,5)+ftoa_x(float,16,5)
            ELSE s_:=s_+laenge(' ',16,' ')+laenge(' ',16,' ');
            s_:=s_+'            NONE';
            IF x=0 THEN s_:=s_+laenge(' ',16,' ') ELSE s_:=s_+ftoa_x(x,16,5);
            s_:=s_+ftoa_x(dj,16,5);
          END;
        END;
        WRITELN(fp,s_);
      END;
    END;
    zei:=r.search_row(objf_); lmax:=maxi(12,col_lmax);
    s_:=#13#10+'Section 2 - COLUMNS'+#13#10; WRITELN(fp,s_);
    s_:=laenge('.COLUMNS',lmax,' ')
    +'AT  ...ACTIVITY...  ..INPUT COST..  ..LOWER LIMIT.  ..UPPER LIMIT.  .REDUCED COST.'+#13#10;
    WRITELN(fp,s_);
    FOR i:=1 TO nvar DO WITH co^[i]^ DO IF (art=0) OR (art=5) THEN
    BEGIN
      s_:=laenge(spa_,lmax,' ');
      IF i<=r.nrows THEN
      BEGIN
        s_:=s_+'BS';
        IF on_upper<>0 THEN s_:=s_+'**';
      END
      ELSE IF on_upper=1 THEN s_:=s_+'UL' ELSE s_:=s_+'LL';
      IF ABS(float)>tolerance THEN s_:=s_+ftoa_x(float,16,5) ELSE s_:=s_+laenge(' ',16,' ');
      cox:=get_entry(zei,i);
      IF cox=0 THEN s_:=s_+laenge(' ',16,' ') ELSE s_:=s_+ftoa_x(cox,16,5);
      IF ABS(lo_bound)>tolerance THEN
      BEGIN
        IF lo_bound<-1e10 THEN  s_:=s_+'            NONE'
        ELSE s_:=s_+ftoa_x(lo_bound,16,5)
      END ELSE s_:=s_+laenge(' ',16,' ');
      IF up_bound<1e10 THEN s_:=s_+ftoa_x(up_bound,16,5) ELSE s_:=s_+'            NONE';
      IF i>r.nrows THEN s_:=s_+ftoa_x(-dj,16,5);
      WRITELN(fp,s_);
    END;
    IF int_rec.integer_found THEN
    BEGIN
      FOR i:=1 TO r.nrows DO WITH co^[i]^ DO
      BEGIN
        on_upper:=0; { in BASIS } int_dj:=0;
      END;
      WRITELN(fp); WRITELN(fp,'INTEGER SOLUTION'); WRITELN(fp);
      FOR i:=1 TO nvar DO WITH co^[i]^ DO IF ABS(int_value)>1e-5 THEN
      WRITELN(fp,laenge(spa_,20,' '),int_value:16:5,' ',int_dj:16:5);
      IF stat_maximise THEN WRITELN(fp,laenge('COBJF',20,' '),int_rec.upper_limit:16:5,' ',0.0:16:5)
      ELSE             WRITELN(fp,laenge('COBJF',20,' '),int_rec.lower_limit:16:5,' ',0.0:16:5);
    END;
    CLOSEFILE(fp);
  END
  ELSE my_Message('CANNOT WRITE '+solfile_);
  IF b<>NIL THEN DISPOSE(b);
END;
{---------------------------------------------------------------------------}
PROCEDURE tlp3.save_lp;
VAR i,j:INTEGER;
    fp:TEXT;
    s2_,s_:STRIN;
    x:DOUBLE;
    mit:BYTE;
LABEL 5;
BEGIN
  IF NOT stat_geladen THEN EXIT;
  stat_help_index:=30;
  fname_:=COPY(fname_,1,POS('.',fname_))+'LP';
  scr.WRITELN('Speichern : '+fname_);
  IF with_eps<>0 THEN
  BEGIN
    mit:=with_eps; toggle_epsilon(0);
  END
  ELSE mit:=0;
  FOR i:=1 TO r.nrows DO co^[i]^.on_upper:=0; { in BASIS }
  ASSIGNFILE(fp,fname_); {$I-} REWRITE(fp); {$I+}
  IF IORESULT<>0 THEN
  BEGIN
    my_message('Cannot write '+fname_); EXIT;
  END;
  swap_rows(1,r.search_row(objf_));
  FOR i:=1 TO r.nrows DO
  BEGIN
    IF i=1 THEN
    BEGIN
      IF stat_maximise THEN s_:='MAX: ' ELSE s_:='MIN: ';
    END
    ELSE s_:=r.ro^[i]^.row_+': ';
    FOR j:=1 TO nvar DO WITH co^[j]^ DO IF (art=0) OR (art=5) THEN
    BEGIN
      x:=get_entry(i,j);
      IF x<>0 THEN
      BEGIN
        IF      x>0  THEN s_:=s_+'+';
        IF      x=1  THEN s_:=s_+spa_
        ELSE IF x=-1 THEN s_:=s_+'-'+spa_
        ELSE              s_:=s_+ftoa(x)+'*'+spa_;
        IF LENGTH(s_)>70 THEN
        BEGIN
          WRITELN(fp,s_); s_:='';
        END;
      END;
    END;
    IF i>1 THEN
    BEGIN
      CASE r.ro^[i]^.gleich OF
        -1: s_:=s_+'<';
        0: s_:=s_+'=';
        1: s_:=s_+'>';
        2: s_:=s_+'^';
      END;
      s_:=s_+ftoa(get_entry(i,0));
    END;
    WRITELN(fp,s_,';'); s_:='';
  END;
  WRITELN(fp,':ENDE');
  WITH co^[-1]^ DO IF canzahl>0 THEN { RANGE COLUMN HAS AN ENTRY }
  BEGIN
    WRITELN(fp,'RANGES');
    FOR i:=1 TO canzahl DO WITH cm^[i] DO
    WRITELN(fp,' RANGE1       ',laenge(r.ro^[z]^.row_+' ',10,' ')+ftoa(wert));
  END;
  WRITELN(fp,'BOUNDS');
  FOR i:=1 TO nvar DO WITH co^[i]^ DO IF (art=0) OR (art=5) THEN
  IF (lo_bound<-1e20) AND (up_bound>1e20) THEN { FR Bound } WRITELN(fp,' FR BOUND     '+spa_)
  ELSE IF (ABS(lo_bound)<>0) OR (up_bound<up_bound_lmt) THEN
  BEGIN
    IF ABS(up_bound-lo_bound)<tolerance THEN { FIX BOUND}
    BEGIN
      s_:=' FX BOUND     '+spa_+' '; STR(lo_bound:0:5,s2_);
      WHILE LENGTH(s_+s2_)<36 DO s2_:=' '+s2_;
      WRITELN(fp,s_+s2_);
    END
    ELSE
    BEGIN
      IF lo_bound<>0 THEN
      BEGIN
        s_:=' LO BOUND     '+spa_+' ';
        STR(lo_bound:0:5,s2_);
        WHILE LENGTH(s_+s2_)<36 DO s2_:=' '+s2_;
        WRITELN(fp,s_+s2_);
      END;
      5:
      IF (up_bound<up_bound_lmt) OR (inttype<>0) THEN
      BEGIN
        CASE inttype OF
          0: s_:=' UP BOUND     '+spa_+' ';
          1: s_:=' UI BOUND     '+spa_+' ';
          2: s_:=' SC BOUND     '+spa_+' ';
        END;
        STR(up_bound:0:5,s2_);
        WHILE LENGTH(s_+s2_)<36 DO s2_:=' '+s2_;
        WRITELN(fp,s_+s2_);
      END;
    END;
  END;
  {$IFDEF rec}
  IF nrec>0 THEN
  BEGIN
    WRITELN(fp,'RECURSION');
    FOR i:=1 TO nrec DO WRITELN(fp,' '+rec_.get(i));
  END;
  {$ENDIF}
  WRITELN(fp,'ENDATA'); CLOSEFILE(fp);
  swap_rows(1,r.nrows);
  j:=1; i:=nvar;
  WHILE i>j DO
  BEGIN
    IF co^[i]^.on_upper=0 THEN
    BEGIN
      swap_columns(i,j); INC(j);
    END ELSE DEC(i);
  END;
  toggle_epsilon(mit); inversion;
END;
{----------------------------------------------------------------------------}
PROCEDURE tlp3.save_mpsx;
VAR eintrag,i,j:INTEGER;
    s_:STRING[80];
    b_,s3_,s2_,s1_:STRIN;
    fp:TEXT;
    setsw:BOOLEAN;
    mit:BYTE;
LABEL 5;
BEGIN
  IF NOT stat_geladen THEN EXIT;
  mit:=with_eps; toggle_epsilon(0);
  IF POS('.',fname_)=0 THEN fname_:=fname_+'.MAT' ELSE
  fname_:=COPY(fname_,1,POS('.',fname_))+'MAT';
  scr.WRITELN('Speichern : '+fname_);
  ASSIGNFILE(fp,fname_); {$I-} REWRITE(fp); {$I+}
  IF IORESULT<>0 THEN
  BEGIN
    my_Message('Cannot write '+fname_); EXIT;
  END;
  WRITELN(fp,'NAME          '+COPY(fname_,1,POS('.',fname_)-1));
  WRITELN(fp,'ROWS');
  swap_rows(r.search_row(objf_),1);
  FOR i:=1 TO r.nrows DO WITH r.ro^[i]^ DO
  BEGIN
    CASE gleich OF
      -1: WRITE(fp,' L  ');
      +0: WRITE(fp,' E  ');
      +1: WRITE(fp,' G  ');
      +2: WRITE(fp,' N  ');
    END;
    WRITELN(fp,row_);
  END;
  WRITELN(fp,'COLUMNS'); eintrag:=0;
  FOR j:=1 TO nvar DO WITH co^[j]^ DO IF (art=0) OR (art=5) THEN
  BEGIN
    IF eintrag>0 THEN
    BEGIN
      WRITELN(fp,s_); s_:=''; eintrag:=0;
    END;
    { s1_ contains col-name s2_ contains value s3_ contains row-name }
    s1_:=laenge('    '+spa_+' ',14,' ');
    FOR i:=1 TO canzahl DO WITH cm^[i] DO
    BEGIN
      s3_:=r.ro^[z]^.row_+' ';
      IF ORD(s3_[1])>3 THEN
      BEGIN
        s2_:=ftoa_x(wert,10,9-maxi(0,ROUND(LOG10(abs(wert)))));
        {STR(wert:0:5,s2_); }s3_:=laenge(s3_,10,' ');
        WHILE LENGTH(s2_+s3_)<22 DO s2_:=' '+s2_;
        IF eintrag=0 THEN
        BEGIN
          s_:=s1_+s3_+s2_; eintrag:=1;
        END ELSE
        BEGIN
          {WRITELN(   s_+'   '+s3_+s2_);}
          WRITELN(fp,s_+'   '+s3_+s2_); eintrag:=0; s_:='';
        END;
      END;
    END;
  END;
  IF s_<>'' THEN WRITELN(fp,s_);
  WRITELN(fp,'RHS'); eintrag:=0; s_:=''; b_:=laenge(rhs_,10,' ');
  WITH co^[0]^ DO { cola[0] contains RHS COLUMN }
  FOR i:=1 TO canzahl DO WITH cm^[i] DO
  BEGIN
    s3_:=r.ro^[z]^.row_;
    IF ORD(s3_[1])>3 THEN
    BEGIN
      IF eintrag=0 THEN
      BEGIN
        s1_:='    '+b_;
        s3_:=laenge(r.ro^[z]^.row_+' ',8,' '); STR(wert:0:5,s2_);
        WHILE LENGTH(s3_+s2_)<22 DO s2_:=' '+s2_;
        s_:=s1_+s3_+s2_; eintrag:=1;
      END
      ELSE
      BEGIN
        s3_:=laenge(r.ro^[z]^.row_+' ',8,' '); STR(wert:0:5,s2_);
        WHILE LENGTH(s2_+s3_)<22 DO s2_:=' '+s2_;
        s_:=s_+'   '+s3_+s2_; eintrag:=0;
        WRITELN(fp,s_); s_:='';
      END;
    END;
  END;
  IF s_<>'' THEN WRITELN(fp,s_);
  WRITELN(fp,'BOUNDS'); b_:=laenge(bound_,10,' ');
  FOR i:=1 TO nvar DO WITH co^[i]^ DO IF (art=0) OR (art=5) THEN
  IF (lo_bound<-1e20) AND (up_bound>1e20) THEN { FR Bound }
  WRITELN(fp,' FR '+b_+spa_)
  ELSE IF (ABS(lo_bound)<>0) OR (up_bound<up_bound_lmt) THEN
  BEGIN
    IF ABS(up_bound-lo_bound)<tolerance THEN { FIX BOUND}
    BEGIN
      s_:=' FX '+b_+spa_+' '; STR(lo_bound:0:5,s2_);
      WHILE LENGTH(s_+s2_)<36 DO s2_:=' '+s2_;
      WRITELN(fp,s_+s2_);
    END
    ELSE
    BEGIN
      IF lo_bound<>0 THEN
      BEGIN
        s_:=' LO '+b_+spa_+' '; STR(lo_bound:0:5,s2_);
        WHILE LENGTH(s_+s2_)<36 DO s2_:=' '+s2_;
        WRITELN(fp,s_+s2_);
      END;
      5:
      IF (up_bound<up_bound_lmt) OR (inttype<>0) THEN
      BEGIN
        CASE inttype OF
          0: s_:=' UP '+b_+spa_+' ';
          1: s_:=' UI '+b_+spa_+' ';
          2: s_:=' SC '+b_+spa_+' ';
        END;
        STR(up_bound:0:5,s2_);
        WHILE LENGTH(s_+s2_)<36 DO s2_:=' '+s2_;
        WRITELN(fp,s_+s2_);
      END;
    END;
  END;
  WITH co^[-1]^ DO IF canzahl>0 THEN { RANGE COLUMN HAS AN ENTRY }
  BEGIN
    WRITELN(fp,'RANGES'); b_:=laenge(range_,10,' ');
    FOR i:=1 TO canzahl DO WITH cm^[i] DO
    WRITELN(fp,'    '+b_,laenge(r.ro^[z]^.row_+' ',10,' ')+ftoa(wert));
  END;
  {$IFDEF rec}
  IF nrec>0 THEN
  BEGIN
    WRITELN(fp,'RECURSION');
    FOR i:=1 TO nrec DO WRITELN(fp,' '+rec_.get(i));
  END;
  {$ENDIF}
  WRITELN(fp,'ENDATA'); CLOSEFILE(fp);
  swap_rows(1,r.nrows);
  j:=1; i:=nvar;
  WHILE i>j DO
  BEGIN
    IF co^[i]^.on_upper=0 THEN
    BEGIN
      swap_columns(i,j); INC(j);
    END ELSE DEC(i);
  END;
  toggle_epsilon(mit); inversion;
END;
{---------------------------------------------------------------------------}
PROCEDURE tlp3.save_xpress;
VAR unten,eintrag,i,j:INTEGER;
    s_:STRING[80];
    s3_,s2_,s1_:STRIN;
    fp:TEXT;
    sos,setsw:BOOLEAN;
    mit:BYTE;
LABEL 1,5;
BEGIN
  IF NOT stat_geladen THEN EXIT;
  mit:=with_eps; toggle_epsilon(0);
  fname_:=COPY(fname_,1,POS('.',fname_))+'MAT';
  WRITELN('Speichern : ',fname_);
  ASSIGNFILE(fp,fname_); {$I-} REWRITE(fp); {$I+}
  IF IORESULT<>0 THEN
  BEGIN
    my_Message('Cannot write '+fname_); EXIT;
  END;
  WRITELN(fp,'NAME          '+COPY(fname_,1,POS('.',fname_)-1));
  WRITELN(fp,'ROWS');
  setsw:=FALSE;
  FOR i:=1 TO r.nrows DO IF ORD(r.ro^[i]^.row_[1])<4 THEN setsw:=TRUE;
  swap_rows(r.search_row(objf_),1);
  FOR i:=1 TO r.nrows DO WITH r.ro^[i]^ DO
  IF ORD(row_[1])>3 THEN
  BEGIN
    CASE gleich OF
      -1: WRITE(fp,' L  ');
      +0: WRITE(fp,' E  ');
      +1: WRITE(fp,' G  ');
      +2: WRITE(fp,' N  ');
    END;
    IF LENGTH(row_)<9 THEN WRITELN(fp,row_)
    ELSE
    BEGIN
      my_Message('ROW '+row_+' too long'); EXIT; GOTO 1;
    END;
  END
  ELSE
  BEGIN
    CASE gleich OF
      -1: WRITE(fp,' L  ');
      +0: WRITE(fp,' E  ');
      +1: WRITE(fp,' G  ');
      +2: WRITE(fp,' N  ');
    END;
    s1_:='SOS'+COPY(row_,2,255);
    IF LENGTH(s1_)<9 THEN WRITELN(fp,s1_)
    ELSE
    BEGIN
      my_message('ROW '+s1_+' too long'); GOTO 1;
    END;
  END;
  IF setsw THEN WRITELN(fp,' G  REFERENC');
  WRITELN(fp,'COLUMNS'); s_:='';
  FOR j:=1 TO nvar DO WITH co^[j]^ DO IF (art=0) OR (art=5) THEN
  BEGIN
    IF LENGTH(spa_)>8 THEN
    BEGIN
      my_Message('COLUMN '+spa_+' too long'); GOTO 1;
    END;

    { s1_ contains col-name s2_ contains value s3_ contains row-name }

    s1_:=laenge('    '+spa_+' ',14,' '); sos:=FALSE;
    FOR i:=1 TO canzahl DO WITH cm^[i] DO
    BEGIN
      s3_:=r.ro^[z]^.row_+' ';
      IF ORD(s3_[1])<4 THEN
      BEGIN
        s3_:='SOS'+COPY(r.ro^[z]^.row_,2,255)+' '; sos:=TRUE;
      END;
      STR(wert:0:5,s2_); s3_:=laenge(s3_,10,' ');
      WHILE LENGTH(s2_+s3_)<22 DO s2_:=' '+s2_;
      IF s_='' THEN s_:=s1_+s3_+s2_
      ELSE
      BEGIN
        WRITELN(fp,s_+'   '+s3_+s2_); s_:='';
      END;
    END;
    IF sos THEN IF s_='' THEN WRITELN(fp,s1_+'REFERENC     '+ltoa(j))
    ELSE s_:=s_+'   REFERENC     '+ltoa(j);
    IF s_<>'' THEN
    BEGIN
      WRITELN(fp,s_); s_:='';
    END;
  END;

  IF setsw THEN
  BEGIN
    WRITELN(fp,'SETS'); s_:='';
    FOR i:=1 TO r.nrows DO WITH r.ro^[i]^ DO IF ORD(row_[1])<4 THEN { SOS }
    BEGIN
      s2_:=COPY(row_,2,255);
      FOR j:=1 TO nvar DO WITH co^[j]^ DO IF (art=0) OR (art=5) THEN IF get_entry(i,j)<>0 THEN
      BEGIN
        unten:=j+1; s1_:=spa_; s_:=''; BREAK;
      END;
      WRITELN(fp,' S1 '+laenge('SOS_'+s2_,10,' ')+laenge('REFERENC ',25,' ')+s1_);
      FOR j:=unten TO nvar DO WITH co^[j]^ DO IF get_entry(i,j)<>0 THEN
      IF s_='' THEN s_:='    '+laenge('SOS_'+s2_,10,' ')+laenge(spa_,25,' ')
      ELSE
      BEGIN
        s_:=s_+laenge(spa_,25,' '); WRITELN(fp,s_); s_:='';
      END;
      IF s_<>'' THEN
      BEGIN
        WRITELN(fp,s_); s_:='';
      END;
    END;
  END;

  WRITELN(fp,'RHS'); eintrag:=0; s_:='';
  WITH co^[0]^ DO { a[0] contains RHS COLUMN }
  FOR i:=1 TO canzahl DO WITH cm^[i] DO IF r.ro^[z]^.gleich<2 THEN
  { only on ROWS WITH TYPE E,L or G but not on TYPE N }
  BEGIN
    s3_:=r.ro^[z]^.row_+' ';
    IF ORD(s3_[1])<4 THEN
    s3_:='SOS'+COPY(r.ro^[z]^.row_,2,255)+' ';
    IF eintrag=0 THEN
    BEGIN
      s1_:='    RHS       ';
      s3_:=laenge(s3_+' ',8,' '); STR(wert:0:5,s2_);
      WHILE LENGTH(s3_+s2_)<22 DO s2_:=' '+s2_;
      s_:=s1_+s3_+s2_; eintrag:=1;
    END
    ELSE
    BEGIN
      s3_:=laenge(s3_+' ',8,' '); STR(wert:0:5,s2_);
      WHILE LENGTH(s2_+s3_)<22 DO s2_:=' '+s2_;
      s_:=s_+'   '+s3_+s2_; eintrag:=0;
      WRITELN(fp,s_); s_:='';
    END;
  END;
  IF s_<>'' THEN WRITELN(fp,s_);
  WITH co^[-1]^ DO IF canzahl>0 THEN { RANGE COLUMN HAS AN ENTRY }
  BEGIN
    WRITELN(fp,'RANGES');
    FOR i:=1 TO canzahl DO WITH cm^[i] DO
    WRITELN(fp,' RANGE1       ',laenge(r.ro^[z]^.row_+' ',10,' ')+ftoa(wert));
  END;
  {$IFDEF rec}
  IF nrec>0 THEN
  BEGIN
    WRITELN(fp,'RECURSION');
    FOR i:=1 TO nrec DO WRITELN(fp,' '+rec_.get(i));
  END;
  {$ENDIF}
  WRITELN(fp,'BOUNDS');
  FOR i:=1 TO nvar DO WITH co^[i]^ DO IF art=0 THEN
  IF (ABS(lo_bound)<>0) OR (up_bound<up_bound_lmt) THEN
  BEGIN
    IF ABS(up_bound-lo_bound)<tolerance THEN { FIX BOUND}
    BEGIN
      s_:=' FX BOUND     '+spa_+' '; STR(lo_bound:0:5,s2_);
      WHILE LENGTH(s_+s2_)<36 DO s2_:=' '+s2_;
      WRITELN(fp,s_+s2_);
    END
    ELSE
    BEGIN
      IF lo_bound<>0 THEN
      BEGIN
        s_:=' LO BOUND     '+spa_+' ';
        STR(lo_bound:0:5,s2_);
        WHILE LENGTH(s_+s2_)<36 DO s2_:=' '+s2_;
        WRITELN(fp,s_+s2_);
      END;
      5:
      IF (up_bound<up_bound_lmt) OR (inttype<>0) THEN
      BEGIN
        CASE inttype OF
          0: s_:=' UP BOUND     '+spa_+' ';
          1: s_:=' UI BOUND     '+spa_+' ';
          2: s_:=' SC BOUND     '+spa_+' ';
        END;
        STR(up_bound:0:5,s2_);
        WHILE LENGTH(s_+s2_)<36 DO s2_:=' '+s2_;
        WRITELN(fp,s_+s2_);
      END;
    END;
  END;
  WRITELN(fp,'ENDATA'); CLOSEFILE(fp);
  1:
  swap_rows(1,r.nrows);
  j:=1; i:=nvar;
  WHILE i>j DO
  BEGIN
    IF co^[i]^.on_upper=0 THEN
    BEGIN
      swap_columns(i,j); INC(j);
    END ELSE DEC(i);
  END;
  toggle_epsilon(mit); inversion;
END;
{---------------------------------------------------------------------------}
PROCEDURE tlp3.demyst;
VAR nzeil,x,i,j,k:INTEGER;
    {$IFNDEF win32}
    e:edit;
    {$ENDIF}
    s1_,s_:STRIN;
    v,sum:DOUBLE;
  {$IFDEF win32}
CONST maxlength=80;
{$ELSE}
CONST maxlength=255;
{$ENDIF}
BEGIN
  {$IFDEF win32}
  LpForm.memo1.clear;
  memomaxlines:=250;
  LpForm.addmemo('D E M Y S T I F I E D  S O L U T I O N');
  LpForm.addmemo('');
  {$ELSE}
  WITH e DO
  BEGIN
    init; nzeil:=1;
    add('D E M Y S T I F I E D  S O L U T I O N');
    INC(nzeil); add(''); set_erlaubt(TRUE);
    {$ENDIF}
    s1_:='';
    FOR i:=1 TO r.nrows DO WITH r.ro^[i]^ DO
    BEGIN
      s_:=laenge(row_+' ',row_lmax,' '); sum:=0;
      FOR j:=1 TO nvar DO WITH co^[j]^ DO
      BEGIN
        IF int_rec.integer_found THEN
        BEGIN
          IF ABS(int_value)>1e-5 THEN IF ABS(get_entry(i,j))>1e-5 THEN
          BEGIN
            v:=int_value*get_entry(i,j); sum:=sum+v; s1_:=spa_;
            WHILE POS('-',s1_)<>0 DO s1_[POS('-',s1_)]:='%';
            WHILE POS('+',s1_)<>0 DO s1_[POS('+',s1_)]:='#';
            s1_:=ftoa(v)+'*'+s1_;
            IF v>0 THEN s1_:='+'+s1_;
          END;
        END
        ELSE
        BEGIN
          IF ABS(float)>1e-5 THEN IF ABS(get_entry(i,j))>1e-5 THEN
          BEGIN
            v:=float*get_entry(i,j); sum:=sum+v;
            s1_:=spa_; WHILE POS('-',s1_)<>0 DO s1_[POS('-',s1_)]:='%';
            s1_:=ftoa(v)+'*'+s1_;
            IF v>0 THEN s1_:='+'+s1_;
          END;
        END;
        IF LENGTH(s_)+LENGTH(s1_)<maxlength-LENGTH(s1_) THEN s_:=s_+s1_
        ELSE
        BEGIN
          INC(nzeil);
          {$IFDEF win32}
          LpForm.addmemo(s_);
          {$ELSE}
          add(s_);
          {$ENDIF}
          s_:=laenge(' ',row_lmax,' ')+s1_;
        END;
        s1_:='';
      END;
      CASE gleich OF
       -1: s_:=s_+'= '+ftoa(sum)+' < '+ftoa(get_entry(i,0));
        0: s_:=s_+'='+ftoa(sum)+' = '+ftoa(get_entry(i,0));
        1: s_:=s_+'='+ftoa(sum)+' > '+ftoa(get_entry(i,0));
        2: s_:=s_+'='+ftoa(sum);
      END;
      INC(nzeil);
      {$IFDEF win32}
      LpForm.addmemo(s_);
      {$ELSE}
      add(s_);
      {$ENDIF}
      s_:=laenge(' ',row_lmax,' ')+s1_;
      {$IFNDEF win32}
      IF nzeil>500 THEN
      WITH inp DO
      BEGIN
        xwinl:=1;   xbreite:=80; ywinl:=1; ybreite:=25;
        smin:=1;    smax:=255;
        sstart:=1; zstart:=3;
        zmin:=1;    nz:=nzeil; zmax:=nz; ins:=FALSE; frei:=TRUE;
        dest_:=ps.destination_+ps.init_;
        k:=screen_edit; clear_lines;
        nzeil:=1; add('D E M Y S T I F I E D  S O L U T I O N');
        INC(nzeil); add('');
      END;
      {$ENDIF}
    END;
    {$IFDEF win32}
    memomaxlines:=100;
    {$ELSE}
    WITH inp DO
    BEGIN
      xwinl:=1;   xbreite:=80; ywinl:=1; ybreite:=25;
      smin:=1;    smax:=255;
      sstart:=1; zstart:=3;
      zmin:=1;    nz:=nzeil; zmax:=nz; ins:=FALSE; frei:=TRUE;
      dest_:=ps.destination_+ps.init_;
      k:=screen_edit; clear_lines;
      nzeil:=1; add('D E M Y S T I F I E D  S O L U T I O N');
      INC(nzeil); add('');
    END;
    done;
  END;
  {$ENDIF}
END;
END.