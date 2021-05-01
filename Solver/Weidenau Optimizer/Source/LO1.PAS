{ $DEFINE rec }
UNIT lo1;

INTERFACE

USES {$IFNDEF win32} zeige,helpunit,fulledit,screen_u,{$ENDIF}
     tty,textfeld,util,p_settin,paraunit;

TYPE tlp1=OBJECT(tcolumns)
           r:trows;
           {$IFDEF win32}
           help_      :STRING80;
           {$ELSE}
           help_,menu_:STRING80;
           {$ENDIF}
           parameter_ :STRING80;
           objf_          :string_type;
           bound_         :string_type;
           range_         :string_type;
           rhs_           :string_type;
           basis_         :strin;
           fname_         :strin;
           hilf_          :STRIN;
           edit_string_   :STRIN;
           path_,start_path_:STRING80;
           obendurch,npricing,basis_intervall,nne,letzt_inv,ninf,iterlog,last_save:INTEGER;
           inf,objf,integer_crit,maxmemory,lo_limit,elapsed,min_piv,inv_limit,penalty,eps,
           up_limit,pivot_el,t_start,t_inv,minpiv,t_letzt_inv,dj_min:DOUBLE;
           nit,matspace,ninversion,maxiter,max_time:LONGINT;
           lp_par:parameter;
           lp_etha:tetha;
           ps:printer_type;
           stat_unbounded,stat_geladen,stat_user_abbruch,stat_abbruch,
           stat_numerisch,stat_mit_frei,stat_ohne_fahne,stat_maximise,
           stat_restart,stat_integer_mode,stat_from_command_line,
           stat_save,stat_with_log,stat_with_pse,stat_built,stat_pressed,
           stat_grafikm,stat_grafikn,stat_grafiko,stat_keydown,stat_togg,
           stat_compress,stat_flush,stat_command:BOOLEAN;
           with_eps,stat_strat1,stat_strat2,stat_strat3:BYTE;
           stat_help_index:INTEGER;
           lg:INTEGER;
           int_rec:tint_rec;
           zehn:ARRAY [1..nzehn] OF DOUBLE;
           idurch:lang_int_feld_pointer;
           inf_table:int_feld_pointer;
           pricing,vvector,rhs:tfeld;
           stack:stapel_pointer;
           ws_root,ws_end:wstack_pointer;
           fil:tint_datei;
           {$IFNDEF win32}
           he:help_type;
           bild:bildschirm;
           menu:mnu_type;
           {$ENDIF}
           CONSTRUCTOR init;
           DESTRUCTOR done; VIRTUAL;
           PROCEDURE lies_aus_parameter;
           PROCEDURE build_objective_function;
           PROCEDURE swap_rows(z1,z2:INTEGER);
           PROCEDURE delete_matrix_element(row,column:INTEGER);
           PROCEDURE put_matrix(row,column:INTEGER; faktor:DOUBLE);
           PROCEDURE put_range(row:INTEGER; faktor:DOUBLE);
           PROCEDURE new_model;
           PROCEDURE define_entry(row_,col_:STRIN; value:DOUBLE);
           PROCEDURE define_sos1(s_:STRIN);
           PROCEDURE define_rhs(row_:STRIN; value:DOUBLE);
           PROCEDURE define_bound(col_,typ_:STRIN; value:DOUBLE);
           PROCEDURE define_range(row_:STRIN; value:DOUBLE);
           PROCEDURE append_log_line(s_:STRIN);
           PROCEDURE calc_rowing(row:INTEGER);
           PROCEDURE store_solution;
           PROCEDURE toggle_epsilon(soll:BYTE);
           PROCEDURE calc_rhs;
           PROCEDURE calc_pricing;
           PROCEDURE ftran(VAR vec:feld; anfang,ende:INTEGER);
           PROCEDURE check_feasibility_inversion;
           PROCEDURE get_vector(column:INTEGER; VAR vec:feld);
           PROCEDURE store_etha(VAR vec:feld; pivotz,anfang,ende:INTEGER);
           PROCEDURE calc_dj(j:INTEGER);
           PROCEDURE put_hash;
           PROCEDURE reset_problem;
           PROCEDURE crash;
           PROCEDURE inversion;
           PROCEDURE put_basis    (VAR b:tbasis);
           PROCEDURE clear_basis  (VAR b:tbasis);
           PROCEDURE restore_basis(VAR b:tbasis; ob:DOUBLE);
           PROCEDURE mischen;
           PROCEDURE quit;
           FUNCTION  time_out(t:DOUBLE):STRIN;
           FUNCTION  get_status:STRING;
           FUNCTION  define_row(s_:STRIN; ch:CHAR):BOOLEAN;
         END;

IMPLEMENTATION
{$IFDEF win32} USES forms; {$ENDIF}
PROCEDURE tlp1.quit;
BEGIN
  scr.WINDOW(1,1,80,25);
  {$IFNDEF win32}
  menu.done; bild.done;
  {$ENDIF}
  IF inf_table<>NIL THEN DISPOSE(inf_table);
  inf_table:=NIL; new_model;
  tcolumns.done; r.done; lp_par.done;
END;
{---------------------------------------------------------------------------}
PROCEDURE tlp1.inversion;
TYPE ah_type=ARRAY [1..maxrows] OF int_feld_pointer;
VAR col_co,tal_in,tal_ou,ia:int_feld_pointer;
    ah2:^ah_type;
    next,t_max,nt_max,wcol,col_max,null,ns,k,x,jpiv,lg1,j,
    i,ipiv,j1,j2,j3,etha_unten,nsing,size:INTEGER;
    pivot:DOUBLE;
    t0:DOUBLE;

PROCEDURE store_etha_inv(VAR vector:feld; pivotz,anfang,ende:INTEGER);
VAR wieviele,i:INTEGER;
    x,pivot_element:DOUBLE;
    remm:pointer_feld_pointer;
BEGIN
  WITH lp_etha DO
  BEGIN
    INC(netha); wieviele:=0;
    WITH lp_etha.etha^[netha] DO
    BEGIN
      min_piv:=min(min_piv,ABS(vector[pivotz]));
      pivot_element:=1/vector[pivotz]; x:=ABS(tolerance*vector[pivotz]);
      FOR i:=anfang TO ende DO IF ABS(vector[i])>x THEN INC(wieviele);
      INC(nelemen,wieviele); init(wieviele);  coben:=1;
      WITH cm^[1] DO
      BEGIN
        wert:=pivot_element; z:=pivotz;
      END;
      FOR i:=anfang TO ende DO IF (ABS(vector[i])>x) AND (i<>pivotz) THEN
      BEGIN
        INC(coben);
        WITH cm^[coben] DO
        BEGIN
          z:=i; wert:=vector[i];
          WITH r.ro^[z]^ DO
          BEGIN
            INC(reanzahl);
            IF reanzahl>reoben THEN
            BEGIN
              GETMEM( remm,(inkrem+reoben)*SIZEOF(POINTER));
              MOVE(   rem^,remm^,  reoben *SIZEOF(POINTER));
              FREEMEM(rem,         reoben *SIZEOF(POINTER));
              rem:=remm; INC(reoben,inkrem);
            END;
            rem^[reanzahl]:=@cm^[coben].z;
          END;
        END;
      END;
    END;
  END;
END;
{----------------------------------------------------------------------------}
PROCEDURE swap_columns_at_rows(s1,s2:INTEGER);
VAR i,k:INTEGER;
BEGIN
  WITH co^[s1]^ DO FOR i:=1 TO canzahl DO WITH cm^[i] DO WITH r.ro^[z]^ DO
  BEGIN
    k:=ah2^[s1]^[i]; rm^[k].z:=s2; ah2^[s1]^[rm^[k].sp]:=k;
  END;
END;
{---------------------------------------------------------------------------}
PROCEDURE delete_column_at_rows(s1:INTEGER);
VAR k,i:INTEGER;
BEGIN
  WITH co^[s1]^ DO FOR i:=1 TO canzahl DO WITH cm^[i] DO WITH r.ro^[z]^ DO
  BEGIN
    k:=ah2^[s1]^[i]; rm^[k]:=rm^[ranzahl]; DEC(ranzahl);
    ah2^[rm^[k].z]^[rm^[k].sp]:=k;
  END;
END;
{---------------------------------------------------------------------------}
PROCEDURE swap_columns_with(i1,i2:INTEGER);
VAR p:POINTER;
BEGIN
  swap_columns(i1,i2);
  p:=ah2^[i1]; ah2^[i1]:=ah2^[i2]; ah2^[i2]:=p;
END;
{---------------------------------------------------------------------------}
PROCEDURE swap_rows_short(z1,z2:INTEGER);
VAR j:INTEGER;
    rr:POINTER;
BEGIN
  IF z1=z2 THEN EXIT;
  { tausche MATRIX }
  swap_integer(r.hash^[r.ro^[z1]^.rhas],r.hash^[r.ro^[z2]^.rhas]);
  { Ranges are included) }
  WITH r.ro^[z1]^ DO
  BEGIN
    FOR j:=1 TO ranzahl1 DO rm1^[j]^:=z2;
    { ETHA-Vectoren }
    FOR j:=1 TO reanzahl DO rem^[j]^:=z2;
  END;
  WITH r.ro^[z2]^ DO
  BEGIN
    FOR j:=1 TO ranzahl1 DO rm1^[j]^:=z1;
    { ETHA-Vectoren }
    FOR j:=1 TO reanzahl DO rem^[j]^:=z1;
  END;
  { Tausche Zeile }
  rr:=r.ro^[z1]; r.ro^[z1]:=r.ro^[z2]; r.ro^[z2]:=rr;
END;
{---------------------------------------------------------------------------}
PROCEDURE qsort_ascending(li,re:INTEGER);
VAR x,i,j:INTEGER;
BEGIN
  i:=li; j:=re; x:=co^[(li+re) DIV 2]^.cm^[1].z;
  REPEAT
    WHILE (i<=re) AND (x>co^[i]^.cm^[1].z) DO INC(i);
    WHILE (j>=li) AND (x<co^[j]^.cm^[1].z) DO DEC(j);
    IF i<=j THEN
    BEGIN
      swap_columns(i,j); INC(i); DEC(j);
    END;
  UNTIL i>j;
  IF li<j  THEN qsort_ascending(li,j);
  IF i <re THEN qsort_ascending(i ,re);
END;
{----------------------------------------------------------------------------}
PROCEDURE store_etha_spec(pivotz:INTEGER);
VAR n,i:INTEGER;
    pivot_element:DOUBLE;
BEGIN
  WITH lp_etha DO
  BEGIN
    n:=co^[pivotz]^.canzahl; INC(nelemen,n);
    INC(netha); etha^[netha].init(n);
    WITH etha^[netha] DO
    BEGIN
      MOVE(co^[pivotz]^.cm^,cm^,SIZEOF(waag)*n);
      FOR i:=1 TO n DO IF co^[pivotz]^.cm^[i].z=pivotz THEN
      BEGIN
        pivot_element:=co^[pivotz]^.cm^[i].wert;
        min_piv:=min(min_piv,ABS(pivot_element));
        cm^[i]:=cm^[1];
        WITH cm^[1] DO
        BEGIN
          wert:=1/pivot_element; z:=pivotz;
        END;
        EXIT;
      END;
    END;
  END;
END;
{----------------------------------------------------------------------------}
PROCEDURE perbas(VAR j3:INTEGER);
VAR letzt,j,i:INTEGER;
LABEL 1;
BEGIN
  letzt:=r.nrows+1; nsing:=0;
  IF NOT stat_integer_mode THEN WITH scr DO
  BEGIN
    {$IFDEF win32}
    sc_:=sc_+'PERBAS '; scr.inversion(sc_);
    {$ELSE}
    scr.WRITE('PERBAS ');
    {$ENDIF}
  END;
  { OBJF Row to last position }
  IF r.ro^[r.nrows]^.row_<>objf_ THEN swap_rows(r.search_row(objf_),r.nrows);
  1:
  j3:=r.nrows;
  { Move columns with 1 entry to the end of basis }
  FOR j:=r.nrows DOWNTO 1 DO IF co^[j]^.canzahl=1 THEN
  BEGIN
    IF j<>j3 THEN swap_columns(j,j3);
    DEC(j3);
  END;
  qsort_ascending(j3+1,r.nrows); FILLCHAR(ia^,size,0);
  { FOR j:=1 TO nrows DO ia^[j]:=0; }
  FOR j:=r.nrows DOWNTO j3+1 DO WITH co^[j]^ DO IF ia^[cm^[1].z]<>0 THEN
  BEGIN
    IF NOT stat_integer_mode THEN
    BEGIN
      INC(nsing); IF nsing MOD 10=0 THEN
      scr.WRITE('. ');
    END;
    INC(letzt);
    IF letzt>nvar THEN
    BEGIN
      scr.WRITELN('WHOLE MATRIX SEARCHED');
      stat_abbruch:=TRUE; new_model;
    END;
    IF art=4 THEN { N-ROW }
    BEGIN
      FOR i:=j+1 TO r.nrows DO IF co^[i]^.cm^[1].z=cm^[1].z THEN
      IF co^[i]^.art<>4 THEN { Weiter oben keine N-ROW }
      BEGIN
        swap_columns(letzt,i); BREAK;
      END;
    END
    ELSE swap_columns(letzt,j);
    GOTO 1;
  END
  ELSE ia^[cm^[1].z]:=1;
  FOR i:=1 TO r.nrows DO WITH r.ro^[i]^ DO
  BEGIN
    GETMEM(rm1,LONGINT(roben1_alt)*SIZEOF(POINTER)); ranzahl1:=0;
  END;
  FOR j:=0 TO nvar DO WITH co^[j]^ DO
  BEGIN
  FOR i:=1 TO canzahl DO
  BEGIN
    WITH cm^[i] DO WITH r.ro^[z]^ DO
    BEGIN
      INC(ranzahl1); rm1^[ranzahl1]:=@z;
    END;
  END;
  END;
  letzt:=j3+1; j:=r.nrows;
  WHILE j>j3 DO
  BEGIN
    IF ia^[j]=0 THEN
    BEGIN
      swap_rows_short(j,        co^[letzt]^.cm^[1].z);
      swap_INTEGER  (ia^[j],ia^[co^[letzt]^.cm^[1].z]); INC(letzt);
    END;
    DEC(j);
  END;
  qsort_ascending(j3+1,r.nrows);
END;
{---------------------------------------------------------------------------}
PROCEDURE part1(VAR j1,j3:INTEGER);
VAR ipiv,jpiv,i,j:INTEGER;
    pivot:DOUBLE;
    mm:zabwaerts_pointer;
BEGIN
  IF NOT stat_integer_mode THEN WITH scr DO
  BEGIN
    {$IFDEF win32}
    sc_:=sc_+ltoa(j3)+' PART1 '; scr.inversion(sc_);
    {$ELSE}
    scr.WRITE(ltoa(j3)+' PART1 ');
    {$ENDIF}
  END;
  { store matrix row-wise }
  GETMEM(ah2,j3*SIZEOF(POINTER));
  { put matrix in Zeilen rest }
  FOR j:=1 TO j3 DO WITH co^[j]^ DO
  BEGIN
    GETMEM(ah2^[j],SIZEOF(INTEGER)*canzahl);
    FOR i:=1 TO canzahl DO WITH cm^[i] DO WITH r.ro^[z]^ DO
    BEGIN
      INC(ranzahl);
      IF roben=0 THEN
      BEGIN
        roben:=roben_alt+inkrem; GETMEM(rm,SIZEOF(zwaag)*LONGINT(roben));
      END
      ELSE
      IF ranzahl>roben THEN
      BEGIN
        GETMEM(mm,   SIZEOF(zwaag)*LONGINT(roben+2*inkrem));
        MOVE(rm^,mm^,SIZEOF(zwaag)*LONGINT(roben));
        FREEMEM(rm,  SIZEOF(zwaag)*LONGINT(roben));
        rm:=mm; INC(roben,2*inkrem);
      END;
      WITH rm^[ranzahl] DO
      BEGIN
        z:=j; wert:=co^[j]^.cm^[i].wert; sp:=i;
      END;
      ah2^[j]^[i]:=ranzahl;
    END;
  END;
  FOR i:=1 TO r.nrows DO WITH r.ro^[i]^ DO roben_alt:=ranzahl;
  { end store row-wise }
  { form lower triangular matrix from 1 to j1-1 }
  j1:=1;
  FOR j:=1 TO j3 DO
  BEGIN
    ipiv:=0; pivot:=minpiv;
    FOR i:=j TO j3 DO WITH r.ro^[i]^ DO IF ranzahl=1 THEN { Zeile mit einem Eintrag }
    BEGIN
      WITH rm^[1] DO IF ABS(wert)>pivot THEN
      BEGIN
        pivot:=ABS(wert)+tolerance; ipiv:=i; jpiv:=z;
        IF i=j THEN BREAK;
      END;
    END;
    IF ipiv=0 THEN EXIT;
    delete_column_at_rows(jpiv);
    IF jpiv<>j THEN
    BEGIN
      swap_columns_at_rows(j,jpiv);
      swap_columns_with   (j,jpiv);
    END;
    swap_rows_short(ipiv,j); INC(j1);
  END;
END;
{---------------------------------------------------------------------------}
PROCEDURE part2(j1,j3:INTEGER; VAR j2:INTEGER);
VAR ipiv,jj,jpiv,i,j:INTEGER;
    pivot:DOUBLE;
BEGIN
  IF NOT stat_integer_mode THEN WITH scr DO
  BEGIN
    {$IFDEF win32}
    sc_:=sc_+ltoa(j1)+' PART2 '; scr.inversion(sc_);
    {$ELSE}
    scr.WRITE(ltoa(j1)+' PART2 ');
    {$ENDIF}
  END;
  { FOR j:=j1 TO j3 DO ia^[j]:=0; }
  FILLCHAR(ia^,size,0);
  FOR j:=j1 TO j3 DO WITH co^[j]^ DO FOR i:=1 TO canzahl DO
  WITH cm^[i] DO IF z<=j3 THEN INC(ia^[j]);
  FOR j:=j3 DOWNTO j1 DO
  BEGIN
    jpiv:=0; pivot:=minpiv;
    FOR jj:=j2 DOWNTO j1 DO IF ia^[jj]=1 THEN { column mit einem Zusatzeintrag }
    BEGIN
      WITH co^[jj]^ DO FOR i:=1 TO canzahl DO WITH cm^[i] DO
      IF (z<=j2) AND (ABS(wert)>pivot) THEN 
      BEGIN 
        pivot:=ABS(wert)+tolerance; ipiv:=z; jpiv:=jj;
      END; 
    END; 
    IF jpiv=0 THEN EXIT;
    delete_column_at_rows(jpiv);
    WITH r.ro^[ipiv]^ DO FOR i:=1 TO ranzahl DO DEC(ia^[rm^[i].z]);
    IF jpiv<>j2 THEN 
    BEGIN
      swap_columns_at_rows(    j,     jpiv);
      swap_columns_with   (    j,     jpiv);
      swap_INTEGER        (ia^[j],ia^[jpiv]);
    END; 
    swap_rows_short(ipiv,j2); DEC(j2);
  END;
END;
{---------------------------------------------------------------------------}
FUNCTION search_ns(VAR k,number:INTEGER):INTEGER;
VAR k1,count,j1,i1,ns,i,col_max:INTEGER;
BEGIN
  REPEAT
    { Tally start }
    t_max:=0; nt_max:=0;
    FOR j1:=1 TO number DO
    BEGIN
      count:=0; k1:=tal_in^[j1];
      WITH co^[k1]^ DO FOR i1:=1 TO canzahl DO WITH cm^[i1] DO IF z<=j2 THEN
      WITH r.ro^[z]^ DO IF ranzahl>0 THEN IF ranzahl<=k THEN INC(count);
      IF count>t_max THEN
      BEGIN
       t_max:=count; nt_max:=1; tal_ou^[1]:=k1;
      END
      ELSE IF count=t_max THEN
      BEGIN
        INC(nt_max); tal_ou^[nt_max]:=k1;
      END;
    END;
    { tally end }
    IF (t_max=1) AND (k>r.nrows) THEN
    BEGIN
      FOR i:=1 TO nt_max DO tal_in^[i]:=tal_ou^[i];
      INC(k); number:=nt_max;
    END
    ELSE
    BEGIN
      ns:=tal_ou^[1]; col_max:=col_co^[tal_ou^[1]];
      FOR i:=2 TO nt_max DO IF col_co^[tal_ou^[i]]>col_max THEN
      BEGIN
        col_max:=col_co^[tal_ou^[i]]; ns:=tal_ou^[i];
      END;
      search_ns:=ns; EXIT;
    END;
  UNTIL FALSE;
END;
{---------------------------------------------------------------------------}
PROCEDURE do_swap(c1,c2:INTEGER);
BEGIN
  swap_columns_at_rows(c1,         c2);
  swap_columns_with(   c1,         c2);
  swap_INTEGER   (col_co^[c1],col_co^[c2]);
END;
{---------------------------------------------------------------------------}
PROCEDURE sort_null(spal:INTEGER);
VAR i:INTEGER;
BEGIN
  WITH co^[spal]^ DO FOR i:=1 to canzahl DO WITH cm^[i] DO
  IF z<=j2 THEN IF z>null THEN IF r.ro^[z]^.ranzahl=0 THEN
  BEGIN
    swap_rows_short(null,z); INC(null);
  END;
END;
{---------------------------------------------------------------------------}
PROCEDURE tall;
VAR number,i:INTEGER;
BEGIN
  number:=0;
  FOR i:=next TO j2 DO
  BEGIN
    INC(number); tal_in^[number]:=i;
  END;
  { Enter Spike Column }
  IF number=1 THEN
  BEGIN
    tal_ou^[1]:=tal_in^[1]; k:=1; ns:=tal_in^[1];
  END
  ELSE ns:=search_ns(k,number); delete_column_at_rows(ns);
  IF next<>ns THEN do_swap(next,ns);
  sort_null(next); INC(next);
END;
{---------------------------------------------------------------------------}
PROCEDURE part4;
VAR i,j:INTEGER;
LABEL 11,1000;
BEGIN
  IF j2>=j1 THEN
  BEGIN
    { Partitioning }
    GETMEM(tal_in,size); GETMEM(tal_ou,size); GETMEM(col_co,size);
    FILLCHAR(col_co^,size,0);
    FOR j:=j1 TO j2 DO WITH co^[j]^ DO FOR i:=1 TO canzahl DO IF cm^[i].z<=j2 THEN INC(col_co^[j]);
    next:=j1; null:=j1;
    1000:
    k:=maxint;
    FOR i:=null TO j2 DO WITH r.ro^[i]^ DO IF ranzahl>0 THEN k:=mini(k,ranzahl);
    IF k<=j2 THEN
    BEGIN
      {IF j2-j1<25 THEN fahne(j1,j2,j1,j2) ELSE dump_basis;}
      tall;
      REPEAT
        wcol:=maxint; col_max:=0;
        FOR i:=null TO j2 DO WITH r.ro^[i]^ DO IF ranzahl>0 THEN IF ranzahl<wcol THEN
        BEGIN
          wcol:=ranzahl; col_max:=1; x:=i;
        END
        ELSE IF ranzahl=wcol THEN INC(col_max);

        IF wcol>1 THEN GOTO 1000; { Another Spike }
        IF col_max=1 THEN
        BEGIN
          { 4.b Forward Triangle }
          WITH r.ro^[x]^.rm^[1] DO
          BEGIN
            delete_column_at_rows(z);
            IF next<>z THEN do_swap(next,z);
            sort_null(next); INC(next);
          END;
          {dump_basis;}
        END
        ELSE
        BEGIN
          k:=1; tall;
        END;
      UNTIL next>j2;
    END;
    FREEMEM(tal_in,size); FREEMEM(tal_ou,size); FREEMEM(col_co,size);
    { End of Partitioning }
    {IF j2-j1<25 THEN fahne(j1,j2,j1,j2) ELSE dump_basis;}
  END;
  null:=j2;

  FOR i:=1 TO r.nrows DO WITH r.ro^[i]^ DO
  BEGIN
    FREEMEM(rm,roben*SIZEOF(zwaag)); ranzahl:=0; roben:=0; rm:=NIL;
  END;
  FOR i:=1 TO j3 DO FREEMEM(ah2^[i],SIZEOF(INTEGER)*co^[i]^.canzahl);
  FREEMEM(ah2,j3*SIZEOF(POINTER));

  { transform Block from j1 to j2 backward to lower triangle }
  FOR j:=j2 DOWNTO j1 DO
  BEGIN
    11:
    get_vector(j,vvector.feld_pointer^);
    ftran(vvector.feld_pointer^,j1,lp_etha.netha);
    ipiv:=0; pivot:=tolerance;
    {
    IF ABS(vector^[j])>minpiv THEN ipiv:=j ELSE
    }
    FOR i:=j DOWNTO j1 DO IF ABS(vvector.feld_pointer^[i])>pivot THEN
    BEGIN
      pivot:=ABS(vvector.feld_pointer^[i])+tolerance; ipiv:=i;
    END;
    IF ipiv=0 THEN
    BEGIN
      IF NOT stat_integer_mode THEN
      BEGIN
        INC(nsing); IF nsing MOD 10=0 THEN
        scr.WRITE('.'); 
      END;
      swap_columns(j,search_column(r.ro^[j]^.row_,0)); GOTO 11;
    END;
    IF ipiv<>j THEN
    BEGIN
      swap_rows_short(j,ipiv);
      swap_double(vvector.feld_pointer^[j],vvector.feld_pointer^[ipiv]);
    END;
    store_etha_inv(vvector.feld_pointer^,j,j1,j);
  END;
  FOR i:=1 TO r.nrows DO WITH r.ro^[i]^ DO
  BEGIN
    FREEMEM(rm1,roben1_alt*SIZEOF(POINTER)); ranzahl1:=0; rm1:=NIL;
    FREEMEM(rem,reoben    *SIZEOF(POINTER)); reanzahl:=0; reoben:=0; rem:=NIL;
  END;
  IF nsing>0 THEN
  scr.WRITELN(#10#13'MATRIX SINGULAR '+ltoa(nsing));
  IF NOT stat_integer_mode THEN WITH scr DO
  BEGIN
    {$IFDEF win32}
    sc_:=sc_+' PART5'; scr.inversion(sc_);
    {$ELSE}
    scr.WRITE(' PART5');
    {$ENDIF}
  END;
  etha_unten:=lp_etha.netha;
  FOR j:=j1 TO j2 DO
  BEGIN
    get_vector(j,vvector.feld_pointer^);
    ftran(vvector.feld_pointer^,j1,etha_unten-(j-j1));
    store_etha(vvector.feld_pointer^,j,j,r.nrows);
  END;
END;
{---------------------------------------------------------------------------}
BEGIN
  IF NOT stat_integer_mode THEN WITH scr DO
  BEGIN
    {$IFDEF win32}
    sc_:='INVERSION '; scr.inversion(sc_);
    {$ELSE}
    IF scr.WHEREX<>1 THEN SYSTEM.WRITELN;
    scr.WRITE('INVERSION ');
    {$ENDIF}
  END;
  min_piv:=up_bound_lmt; t0:=julia; INC(ninversion);
  lp_etha.clear_etha(0,lp_etha.nelemen); etha_unten:=1;
  size:=SIZEOF(INTEGER)*r.nrows; GETMEM(ia,size);
  perbas(j3); part1(j1,j3); j2:=j3; part2(j1,j3,j2);
  IF NOT stat_integer_mode THEN WITH scr DO
  BEGIN
    {$IFDEF win32}
    sc_:=sc_+ltoa(j2)+' PART3'; scr.inversion(sc_);
    {$ELSE}
    scr.WRITE(ltoa(j2)+' PART3');
    {$ENDIF}
  END;
  etha_unten:=maxi(1,j1); lp_etha.netha:=j1-1; FREEMEM(ia,size); ia:=NIL;
  IF NOT stat_integer_mode THEN WITH scr DO
  BEGIN
    {$IFDEF win32}
    sc_:=sc_+' PART4'; scr.inversion(sc_);
    {$ELSE}
    scr.WRITE(' PART4');
    {$ENDIF}
  END;
  part4;
  { add etha vectors from colums j2+1 to nrows }
  etha_unten:=lp_etha.netha; lp_etha.netha:=0;
  FOR j:=1 TO j1-1 DO store_etha_spec(j);
  lp_etha.netha:=etha_unten;
  IF NOT stat_integer_mode THEN WITH scr DO
  BEGIN
    {$IFDEF win32}
    sc_:=sc_+' PART6'; scr.inversion(sc_);
    {$ELSE}
    scr.WRITE(' PART6');
    {$ENDIF}
  END;
  FOR j:=j2+1 TO j3 DO store_etha_spec(j);
  { Add unic Vectors at the end }
  FOR j:=j3+1 TO r.nrows DO
  BEGIN
    pivot:=co^[j]^.cm^[1].wert;
    IF pivot<>1.0 THEN
    BEGIN
      INC(lp_etha.netha);
      WITH lp_etha.etha^[lp_etha.netha] DO
      BEGIN
        init(1); INC(lp_etha.nelemen);
        WITH cm^[1] DO
        BEGIN
          wert:=1/pivot; z:=j;
        END;
      END;
      min_piv:=min(min_piv,ABS(pivot));
    END;
  END;
  {$IFDEF win32}
  scr.win_out(koomema,laenge(ltoa(getheapstatus.totalallocated DIV 1000),7,' '));
  scr.win_out(koominpiv,laenge(ftoa(min_piv),11,' '));
  {$ELSE}
  scr.win_out(koomema,  laenge(ltoa(MEMAVAIL DIV 1000),7,' '));
  scr.win_out(koominpiv,laenge(ftoa(min_piv),11,' '));
  {$ENDIF}
  IF stat_mit_frei THEN FOR j:=1 TO r.nrows DO WITH co^[j]^ DO IF art=5 THEN
  BEGIN
    up_bound:=up_bound_lmt; lo_bound:=-up_bound_lmt;
  END;
  calc_rhs; objf:=-rhs.feld_pointer^[r.nrows]; calc_pricing;
  t_letzt_inv:=julia; t_inv:=max(t_letzt_inv-t0,1/18); letzt_inv:=0;
  lp_etha.ethaspace:=SIZEOF(waag)*lp_etha.nelemen+3*size;
  IF NOT stat_integer_mode THEN scr.inf_2;
END;
{---------------------------------------------------------------------------}
PROCEDURE tlp1.mischen;
VAR i,j1,j2:INTEGER;
BEGIN
  FOR i:=1 TO 2*nvar DO
  BEGIN
    j1:=ROUND(1+RANDOM(nvar)); j2:=ROUND(1+RANDOM(nvar));
    IF (j1>0) AND (j1<=nvar) AND (co^[j1]^.art<4) AND
    (j2>0) AND (j2<=nvar) AND (co^[j2]^.art<4) THEN swap_columns(j1,j2);
  END;
  inversion;
END;
{----------------------------------------------------------------------------}
PROCEDURE tlp1.clear_basis(VAR b:tbasis);
VAR i:INTEGER;
BEGIN
  WITH b DO
  BEGIN
    IF bas=NIL THEN EXIT;
    FREEMEM(bas,ubas*SIZEOF(INTEGER));
    ubas:=0; bas:=NIL;
  END;
END;
{---------------------------------------------------------------------------}
PROCEDURE tlp1.put_basis(VAR b:tbasis);
VAR i:INTEGER;
BEGIN
  WITH b DO
  BEGIN
    ubas:=r.nrows; nin:=ninversion; net:=lp_etha.netha; let_in:=letzt_inv;
    FOR i:=r.nrows+1 TO nvar DO IF co^[i]^.on_upper=1 THEN INC(ubas);
    GETMEM(bas,ubas*SIZEOF(INTEGER));
    FOR i:=1 TO r.nrows DO bas^[i]:=co^[i]^.cha;
    ubas:=r.nrows;
    FOR i:=r.nrows+1 TO nvar DO WITH co^[i]^ DO IF on_upper=1 THEN
    BEGIN
      INC(ubas); bas^[ubas]:=cha;
    END;
  END;
END;
{---------------------------------------------------------------------------}
PROCEDURE tlp1.restore_basis(VAR b:tbasis; ob:DOUBLE);
VAR n,i:INTEGER;
BEGIN
  IF b.bas=NIL THEN EXIT;
  n:=1;
  WITH b DO
  BEGIN
    FOR i:=1 TO ubas DO
    BEGIN
      swap_columns(n,hash^[bas^[i]]); co^[n]^.on_upper:=0; INC(n);
    END;
    { Alles auf lower Bound }
    FOR i:=r.nrows+1 TO nvar DO WITH co^[i]^ DO on_upper:=-1;
    FOR i:=r.nrows+1 TO ubas DO co^[hash^[bas^[i]]]^.on_upper:=1;
    IF (nin=ninversion) AND (net<=lp_etha.netha) THEN
    BEGIN
      lp_etha.clear_etha(net,lp_etha.nelemen); calc_rhs;
      IF (ninf<>0) OR (ABS(objf-ob)>1e-4) THEN inversion;
      t_letzt_inv:=julia; let_in:=letzt_inv;
    END ELSE inversion;
  END;
END;
{---------------------------------------------------------------------------}
PROCEDURE tlp1.crash;
{ Try to fill Basis with vectors other than rows }
VAR erst:lang_int_feld_pointer;
    spec,akt_erst,n,akt_n,i,j:INTEGER;
PROCEDURE qsort_erst(li,re:INTEGER);
VAR x,i,j:INTEGER;
BEGIN
  i:=li; j:=re; x:=erst^[(li+re) DIV 2];
  REPEAT
    WHILE (i<=re) AND (x>erst^[i]) DO INC(i);
    WHILE (j>=li) AND (x<erst^[j]) DO DEC(j);
    IF i<=j THEN
    BEGIN
      swap_INTEGER(erst^[i],erst^[j]);
      swap_columns(i,j); INC(i); DEC(j);
    END;
  UNTIL i>j;
  IF li<j  THEN qsort_erst(li,j);
  IF i <re THEN qsort_erst(i ,re);
END;
{----------------------------------------------------------------------------}
BEGIN
  NEW(erst); scr.WRITE('CRASH BASIS'); scr.WRITELN('');
  FOR i:=r.nrows+1 TO nvar DO erst^[i]:=r.nrows+2;
  FOR j:=r.nrows+1 TO nvar DO WITH co^[j]^ DO IF (up_bound-lo_bound)>tolerance THEN
  FOR i:=1 TO canzahl DO WITH cm^[i] DO IF ABS(wert)>1e-5 THEN erst^[j]:=mini(erst^[j],z);
  qsort_erst(r.nrows+1,nvar);
  i:=r.nrows+1;
  WHILE i<=nvar DO
  BEGIN
    j:=i; akt_n:=co^[i]^.canzahl; spec:=j; akt_erst:=erst^[i];
    WHILE (j<=nvar) AND (erst^[j]=akt_erst) DO WITH co^[j]^ DO
    BEGIN
      { Freie Variablen auáen vor }
      IF canzahl<akt_n THEN
      BEGIN
        akt_n:=canzahl; spec:=j;
      END;
      INC(j);
    END;
    IF co^[akt_erst]^.art<4 THEN
    { Keine N-ROWs und kein FR-Variablen } swap_columns(spec,akt_erst);
    i:=j;
  END;
  FOR j:=1 TO r.nrows DO co^[j]^.on_upper:=0;
  FOR j:=r.nrows+1 TO nvar DO WITH co^[j]^ DO
  BEGIN
    on_upper:=-1;
    IF art=5 THEN
    BEGIN
      up_bound:=-up_bound_lmt; lo_bound:=-up_bound;
    END;
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
  DISPOSE(erst); inversion;
END;
{---------------------------------------------------------------------------}
PROCEDURE tlp1.reset_problem;
BEGIN
  crash; nit:=0; elapsed:=0; stat_abbruch:=FALSE;
  stat_integer_mode:=FALSE;
  WITH int_rec DO
  BEGIN
    lower_limit:=lo_limit; upper_limit:=up_limit; integer_found:=FALSE;
  END;
END;
{----------------------------------------------------------------------------}
PROCEDURE tlp1.put_hash;
VAR i,h:INTEGER;
LABEL 1,2;
BEGIN
  thash.clearhash;
  r.clearhash;
  FOR i:=1 TO r.nrows DO WITH r.ro^[i]^ DO
  BEGIN
    h:=gethash(row_);
    WHILE h<=hash_max DO
    BEGIN
      IF r.hash^[h]=0 THEN
      BEGIN
        r.hash^[h]:=i; rhas:=h; GOTO 1;
      END
      ELSE h:=1+(LONGINT(h)+10*ORD(row_[1])+ORD(row_[2])) MOD hash_max;
    END;
    1:
  END;
  FOR i:=0 TO nvar DO WITH co^[i]^ DO
  BEGIN
    h:=gethash(spa_);
    WHILE h<=hash_max DO
    BEGIN
      IF hash^[h]=0 THEN
      BEGIN
        hash^[h]:=i; cha:=h; GOTO 2;
      END
      ELSE h:=1+(LONGINT(h)+10*ORD(spa_[1])+ORD(spa_[2])) MOD hash_max;
    END;
    2:
  END;
END;
{---------------------------------------------------------------------------}
PROCEDURE tlp1.get_vector(column:INTEGER; VAR vec:feld);
VAR i:INTEGER;
BEGIN
  FILLCHAR(vec,LONGINT(r.nrows)*SIZEOF(DOUBLE),0);
  WITH co^[column]^ DO FOR i:=1 TO canzahl DO WITH cm^[i] DO vec[z]:=wert;
END;
{---------------------------------------------------------------------------}
PROCEDURE tlp1.define_entry(row_,col_:STRIN; value:DOUBLE);
VAR fp:TEXT;
BEGIN
  {$IFDEF log}
  assignfile(fp,'LP.LOG'); append(fp);
  WRITELN(fp,'ENTRY ',row_,' ',col_,' ',value);
  closefile(fp);
  {$ENDIF}
  IF value<>0 THEN put_matrix(r.search_row(row_),search_column(col_,0), value);
END;
{---------------------------------------------------------------------------}
FUNCTION tlp1.get_status:STRING;
BEGIN
  IF stat_geladen      THEN get_status:='LOADED';
  IF stat_abbruch      THEN
  BEGIN
    get_status:='BREAK'; EXIT;
  END;
  IF stat_user_abbruch THEN
  BEGIN
    get_status:='USER-BREAK'; EXIT;
  END;
  IF stat_unbounded    THEN
  BEGIN
    get_status:='UNBOUNDED'; EXIT;
  END;
  IF ninf>0 THEN get_status:='INFEASIBLE' ELSE get_status:='OPTIMAL';
END;
{---------------------------------------------------------------------------}
PROCEDURE tlp1.define_sos1(s_:STRIN);
VAR s2_:STRIN;
    fp:TEXT;
BEGIN
  {$IFDEF log}
  assignfile(fp,'LP.LOG'); append(fp);
  WRITELN(fp,'SOS1 ',s_);
  closefile(fp);
  {$ENDIF}
  IF NOT define_row(#1+ltoa(r.nrows+1),'E') THEN; define_rhs(#1+ltoa(r.nrows),1);
  WHILE s_<>'' DO
  BEGIN
    zerhacke(s2_,s_); define_entry(#1+ltoa(r.nrows),s2_,1); define_bound(s2_,'BV',1);
  END;
END;
{---------------------------------------------------------------------------}
PROCEDURE tlp1.define_rhs(row_:STRIN; value:DOUBLE);
VAR fp:TEXT;
BEGIN
  {$IFDEF log}
  assignfile(fp,'LP.LOG'); append(fp);
  WRITELN(fp,'RHS ',row_,' ',value);
  closefile(fp);
  {$ENDIF}
  IF value<>0 THEN put_matrix(r.search_row(row_),0,value);
END;
{---------------------------------------------------------------------------}
PROCEDURE tlp1.define_range(row_:STRIN; value:DOUBLE);
VAR fp:TEXT;
BEGIN
  {$IFDEF log}
  assignfile(fp,'LP.LOG'); append(fp);
  WRITELN(fp,'RANGE ',row_,' ',value);
  closefile(fp);
  {$ENDIF}
  put_range(r.search_row(row_),value);
END;
{----------------------------------------------------------------------------}
PROCEDURE tlp1.define_bound(col_,typ_:STRIN; value:DOUBLE);
VAR fp:TEXT;
BEGIN
  {$IFDEF log}
  assignfile(fp,'LP.LOG'); append(fp);
  WRITELN(fp,'BOUND ',col_,' ',typ_,' ',value);
  closefile(fp);
  {$ENDIF}
  WITH co^[search_column(col_,0)]^ DO
  IF      typ_='UP' THEN up_bound:=value
  ELSE IF typ_='LO' THEN lo_bound:=value
  ELSE IF typ_='FR' THEN
  BEGIN
    art:=5; stat_mit_frei:=TRUE;
  END
  ELSE IF typ_='FX' THEN
  BEGIN
    up_bound:=value; lo_bound:=up_bound;
  END
  ELSE IF typ_='MI' THEN { negative }
  BEGIN
    lo_bound:=-up_bound_lmt;
    up_bound:=0;
  END
  ELSE IF typ_='BV' THEN { Binary 0,1 }
  BEGIN
    inttype:=1; p_plus:=pseu_init; p_minus:=pseu_init; up_bound:=1;
  END
  ELSE IF typ_='UI' THEN { up BOUND INTEGER }
  BEGIN
    up_bound:=min(300000,min(up_bound,value));
    inttype:=1; p_plus:=pseu_init; p_minus:=pseu_init;
  END
  ELSE my_message('Unknown Bound Type '+typ_+' at column '+col_);
END;
{----------------------------------------------------------------------------}
FUNCTION tlp1.time_out(t:DOUBLE):STRIN;
VAR d,h,m:INTEGER;
    s_:STRIN;
BEGIN
  d:=ROUND(INT(t)) DIV 86400; s_:='';
  IF d>0 THEN s_:=ltoa(d)+'d';
  t:=t-d*86400; h:=ROUND(INT(t)) DIV 3600;
  if h>0 THEN s_:=s_+ltoa_x(h,2)+'h';
  t:=t-3600.0*h; m:=ROUND(INT(t)) DIV 60;
  IF h>0 THEN s_:=s_+ltoa_x(m,2)+''''
  ELSE        s_:=s_+ltoa(m)+'''';
  t:=t-m*60.0; time_out:=s_+ftoa_x(t,4,1)+'"';
END;
{----------------------------------------------------------------------------}
PROCEDURE tlp1.append_log_line(s_:STRIN);
VAR fp:TEXT;
BEGIN
  ASSIGNFILE(fp,start_path_+'\LP.LOG');
  {$I-} APPEND(fp); {$I+}
  IF IORESULT<>0 THEN { file doesn't exist } REWRITE(fp);
  WRITELN(fp,s_); CLOSEFILE(fp);
END;
{----------------------------------------------------------------------------}
PROCEDURE tlp1.calc_rowing(row:INTEGER);
VAR p_row,i,j:INTEGER;
    som:DOUBLE;
BEGIN
  pricing.clear(r.nrows); pricing.feld_pointer^[row]:=1;
  WITH lp_etha DO FOR j:=netha DOWNTO 1 DO WITH etha^[j] DO
  BEGIN
    som:=0; p_row:=cm^[1].z;
    FOR i:=2 TO coben DO WITH cm^[i] DO som:=som+wert*pricing.feld_pointer^[z];
    pricing.feld_pointer^[p_row]:=cm^[1].wert*(pricing.feld_pointer^[p_row]-som);
  END;
END;
{----------------------------------------------------------------------------}
PROCEDURE tlp1.store_solution;
VAR i:INTEGER;
BEGIN
  FOR i:=1 TO r.nrows DO WITH co^[i]^ DO
  BEGIN
    float:=rhs.feld_pointer^[i]; dj:=0;
  END;
  FOR i:=r.nrows+1 TO nvar DO WITH co^[i]^ DO IF on_upper=1 THEN float:=up_bound ELSE float:=lo_bound;
END;
{---------------------------------------------------------------------------}
PROCEDURE tlp1.toggle_epsilon(soll:BYTE);
VAR wx,i:INTEGER;
    m1:tladder;
BEGIN
  IF (soll=0) AND (with_eps<>0) THEN
  BEGIN
    scr.remove_eps;
    with_eps:=0;
    { Adjust RHS }
    FOR i:=1 TO r.nrows DO IF r.ro^[i]^.gleich=1
    THEN rhs.feld_pointer^[i]:=rhs.feld_pointer^[i]+eps
    ELSE rhs.feld_pointer^[i]:=rhs.feld_pointer^[i]-eps;
    { clear memory for perturbation }
    WITH co^[0]^ DO
    BEGIN
      i:=1;
      WHILE i<=canzahl DO WITH cm^[i] DO
      BEGIN
        IF r.ro^[z]^.gleich=1 THEN wert:=wert+eps ELSE wert:=wert-eps;
        IF ABS(wert)<tolerance THEN
        BEGIN
          cm^[i]:=cm^[canzahl]; DEC(canzahl);
        END
        ELSE INC(i);
      END;
    END;
    { =ROW }
    FOR i:=1 TO nvar DO WITH co^[i]^ DO IF art=3 THEN
    BEGIN
      up_bound:=0; lo_bound:=0;
    END;
    calc_rhs; objf:=-rhs.feld_pointer^[r.nrows]; calc_pricing;
  END;
  IF soll>0 THEN
  BEGIN
    IF with_eps<1 THEN
    BEGIN
      scr.add_eps(1);
      IF r.nrows*LONGINT(SIZEOF(waag))>max_size THEN
      BEGIN
        scr.WRITELN('Field size too small'); EXIT;
      END;
      with_eps:=1; m1.init(r.nrows); { memory for new rhs }
      { Bilde Vector nur mit St”rung }
      FOR i:=1 TO r.nrows DO WITH m1.cm^[i] DO
      BEGIN
        z:=i;
        IF r.ro^[i]^.gleich=1 THEN
        BEGIN
          rhs.feld_pointer^[i]:=rhs.feld_pointer^[i]-eps; wert:=-eps;
        END
        ELSE
        BEGIN
          rhs.feld_pointer^[i]:=rhs.feld_pointer^[i]+eps; wert:= eps;
        END;
      END;
      { Addiere RHS auf St”rungsvector }
      WITH co^[0]^ DO
      BEGIN
        FOR i:=1 TO canzahl DO WITH cm^[i] DO m1.cm^[z].wert:=m1.cm^[z].wert+wert;
        FREEMEM(cm,coben*SIZEOF(waag));
        cm:=m1.cm; canzahl:=r.nrows; coben:=r.nrows;
      END;
    END;
    IF soll=2 THEN { =ROW }
    BEGIN
      scr.add_eps(2); with_eps:=2;
      for i:=1 to nvar DO WITH co^[i]^ DO IF art=3 THEN
      BEGIN
        up_bound:=3*random*eps; lo_bound:=-3*random*eps;
      END;
    END;
    IF ninversion>0 THEN
    BEGIN
      calc_rhs; objf:=-rhs.feld_pointer^[r.nrows]; calc_pricing;
    END;
  END;
END;
{----------------------------------------------------------------------------}
PROCEDURE tlp1.calc_pricing;
VAR p_row,i,j:INTEGER;
    s,som:DOUBLE;
BEGIN
  FOR j:=1 TO r.nrows DO WITH co^[j]^ DO
  CASE on_upper OF
     0: pricing.feld_pointer^[j]:= cost; { feasible }
     1: pricing.feld_pointer^[j]:=-penalty;    { zu hoch }
    -1: pricing.feld_pointer^[j]:= penalty;    { zu tief }
  END;
  WITH lp_etha DO FOR j:=netha DOWNTO 1 DO WITH etha^[j] DO
  BEGIN
    som:=0; p_row:=cm^[1].z;
    FOR i:=2 TO coben DO WITH cm^[i] DO som:=som+wert*pricing.feld_pointer^[z];
    pricing.feld_pointer^[p_row]:=cm^[1].wert*(pricing.feld_pointer^[p_row]-som);
  END;
END;
{----------------------------------------------------------------------------}
PROCEDURE tlp1.ftran(VAR vec:feld; anfang,ende:INTEGER);
VAR p_row,i,j:INTEGER;
    p1:DOUBLE;
BEGIN
  WITH lp_etha DO FOR j:=anfang TO ende DO WITH etha^[j] DO
  BEGIN
    p_row:=cm^[1].z; p1:=vec[p_row]*cm^[1].wert;
    FOR i:=2 TO coben DO WITH cm^[i] DO vec[z]:=vec[z]-p1*wert;
    vec[p_row]:=p1;
  END;
END;
{----------------------------------------------------------------------------}
PROCEDURE tlp1.check_feasibility_inversion;
VAR j:INTEGER;
    bound:DOUBLE;
BEGIN
  { Wenn infeasible, dann werden die Bounds so gesetzt, daá ein Basiswechsel
  nur auf feasible Bounds erfolgen kann. Weiterhin ist eine Infeasible ROW
  niemals als potientielle Pivotrow geeignet, da sie niemals einschr„nkend
  ist
  }
  ninf:=0; inf:=0;
  FOR j:=1 TO r.nrows-1 DO WITH co^[j]^ DO IF art<4 THEN
  BEGIN
    bound:=rhs.feld_pointer^[j]-up_bound;
    IF bound>tolerance THEN { Wert zu hoch }
    BEGIN
      inf:=inf+bound; INC(ninf); inf_table^[ninf]:=j; on_upper:=1;
    END
    ELSE
    BEGIN
      bound:=lo_bound-rhs.feld_pointer^[j];
      IF bound>tolerance THEN { Wert ist zu tief }
      BEGIN
        inf:=inf+bound; INC(ninf); inf_table^[ninf]:=j; on_upper:=-1;
      END
      ELSE on_upper:=0;
    END;
  END;
  IF inf<inv_limit THEN
  BEGIN
    FOR j:=1 TO ninf DO co^[inf_table^[j]]^.on_upper:=0;
    inf:=0; ninf:=0;
  END;
END;
{---------------------------------------------------------------------------}
PROCEDURE tlp1.calc_rhs;
VAR i,j:INTEGER;
    bound:DOUBLE;
BEGIN
  get_vector(0,rhs.feld_pointer^);
  { Adjust RHS with Bounds of non basis variables }
  FOR j:=r.nrows+1 TO nvar DO WITH co^[j]^ DO
  BEGIN
    IF art=5 THEN
    BEGIN
      on_upper:=-1;
      IF lo_bound=-up_bound_lmt THEN lo_bound:=0;
    END;
    IF on_upper=1 THEN
    BEGIN
      IF up_bound>1e10 THEN
      BEGIN
        on_upper:=-1; bound:=lo_bound;
      END
      ELSE bound:=up_bound;
    END ELSE bound:=lo_bound;
    IF ABS(bound)>0 THEN FOR i:=1 TO canzahl DO WITH cm^[i] DO rhs.feld_pointer^[z]:=rhs.feld_pointer^[z]-bound*wert;
  END;
  ftran(rhs.feld_pointer^,1,lp_etha.netha);
  objf:=-rhs.feld_pointer^[r.nrows]; check_feasibility_inversion;
  IF NOT stat_integer_mode THEN
  scr.win_out(kooninf,laenge(ltoa(ninf)+' '+ltoa(lp_etha.netha)+' '+ltoa(lp_etha.nelemen),19,' '));
END;
{----------------------------------------------------------------------------}
PROCEDURE tlp1.store_etha(VAR vec:feld; pivotz,anfang,ende:INTEGER);
VAR wieviele,i:INTEGER;
    x,pivot_element:DOUBLE;
BEGIN
  WITH lp_etha DO
  BEGIN
    INC(netha); wieviele:=0;
    WITH etha^[netha] DO
    BEGIN
      min_piv:=min(min_piv,ABS(vec[pivotz]));
      pivot_element:=1/vec[pivotz]; x:=ABS(etha_min*vec[pivotz]);
      FOR i:=anfang TO ende DO IF ABS(vec[i])>x THEN INC(wieviele);
      INC(nelemen,wieviele); init(wieviele);
      coben:=1;
      WITH cm^[1] DO
      BEGIN
        wert:=pivot_element; z:=pivotz;
      END;
      FOR i:=anfang TO ende DO IF (ABS(vec[i])>x) AND (i<>pivotz) THEN
      BEGIN
        INC(coben);
        WITH cm^[coben] DO
        BEGIN
          z:=i; wert:=vec[i];
        END;
      END;
    END;
  END;
END;
{----------------------------------------------------------------------------}
PROCEDURE tlp1.calc_dj(j:INTEGER);
VAR i:INTEGER;
BEGIN
  WITH co^[j]^ DO
  BEGIN
    dj:=0;
    FOR i:=1 TO canzahl DO WITH cm^[i] DO dj:=dj+pricing.feld_pointer^[z]*wert;
    IF on_upper=1 THEN dj:=cost-dj ELSE dj:=dj-cost;
    IF art=5 THEN IF ABS(dj)>tolerance THEN IF dj<0 THEN dj:=-1e20
  END;
END;
{----------------------------------------------------------------------------}
CONSTRUCTOR tlp1.init;
VAR fp:TEXT;
BEGIN
  IF feld_breite*SIZEOF(INTEGER)<>SIZEOF(datei_record) THEN
  BEGIN
    scr.WRITELN('SIZEOF (feld_breite) is wrong:');
    scr.WRITELN('adapt RECORD rec such that SIZEOF(rec) is a multiple of SIZEOF(INTEGER)');
    HALT;
  END;
  GetDir(0,start_path_);
  {$IFDEF win32}
  help_      :='LP.HLP';
  {$ELSE}
  help_      :='LP.WHL';
  menu_      :='LP.MEN';
  {$ENDIF}
  parameter_ :='LP.PAR';
  file_test(parameter_);
  {$IFNDEF win32}
  file_test(menu_); file_test(help_);
  {$ENDIF}
  stat_unbounded:=FALSE; stat_geladen:=FALSE; stat_user_abbruch:=FALSE;
  stat_abbruch:=FALSE; stat_numerisch:=FALSE; stat_mit_frei:=FALSE;
  stat_ohne_fahne:=FALSE; stat_maximise:=FALSE;
  stat_restart:=FALSE; stat_integer_mode:=FALSE;
  stat_from_command_line:=FALSE; stat_save:=FALSE; stat_with_log:=FALSE;
  stat_with_pse:=FALSE; stat_built:=FALSE; stat_pressed:=FALSE;
  stat_grafikm:=FALSE; stat_grafikn:=FALSE; stat_grafiko:=FALSE;
  stat_keydown:=FALSE; stat_togg:=FALSE; stat_compress:=FALSE;
  stat_flush:=FALSE; stat_command:=FALSE; with_eps:=0; eps:=1e-8;
  penalty:=1e10; min_piv:=1e30; elapsed:=0; letzt_inv:=0; t_inv:=0;
  inv_limit:=1e-9;   { smaller infeasibilities are set to 0 }
  stat_strat1:=0; stat_strat2:=0; stat_strat3:=0; stat_help_index:=0;
  inf:=0; ninversion:=0; nit:=0; 
  ninf:=0; t_letzt_inv:=0; last_save:=0; minpiv:=1e-5; t_start:=0;
  matspace:=0; lg:=0; pivot_el:=0; nne:=0;
  scr.init;
  WITH ps DO
  BEGIN
    pitch:=10; lpi:=6; lpp:=66; rand:=0;
    portrait_:='P'; drucker_:='LASER'; destination_:='LPT1';
    nlq:=FALSE; duplex:=FALSE; upper:=FALSE;
  END;
  ASSIGNFILE(fp,parameter_); {$I-} RESET(fp); {$I+}
  IF IORESULT<>0 THEN
  BEGIN
    my_message('FILE '+parameter_+' not found'); HALT;
  END;
  lp_par.init; lp_par.read_parameter(fp); CLOSEFILE(fp);
  lies_aus_parameter;
  stat_unbounded:=FALSE; stat_geladen:=FALSE; stat_abbruch:=FALSE;
  stat_flush:=FALSE; stat_togg:=FALSE; stat_numerisch:=FALSE; stat_integer_mode:=FALSE;
  stat_from_command_line:=FALSE;
  stat_user_abbruch:=FALSE; stat_built:=FALSE; stat_mit_frei:=FALSE;
  {init;}
  r.init;
  tcolumns.init;
  nvar:=-2; 
  nvar:=search_column('RANGE',0);
  nvar:=search_column('RHS',0);
  int_rec.init;
  FILLCHAR(zehn,SIZEOF(zehn),0); idurch:=NIL; obendurch:=0;
  NEW(inf_table);
  make_init(ps); pricing.init(0); rhs.init(0); vvector.init(0);
  {$IFNDEF win32} menu.init(menu_);
  menu.hide_all;
  bild.init;
  WITH menu DO
  BEGIN
    unhide('OBJECTIVE');
    unhide(' PROBLEM');
    unhide('LOAD');
    unhide('EDIT');
    unhide(' PARAMETER');
    unhide(' LP');
    unhide(' MPSX');
    unhide(' MPSX FORMATTED');
    unhide('OTHER');
    unhide(' STATUS');
    unhide('COMMAND FILE');
    unhide('ABOUT');
    unhide('HELP');
    unhide('EXIT');
  END;
  {$ENDIF}
  GETDIR(0,path_);
  IF path_[LENGTH(path_)]<>'\' THEN path_:=path_+'\';
  IF POS('\',help_     )=0 THEN help_     :=path_+help_;
  {$IFNDEF win32}
  IF POS('\',menu_     )=0 THEN menu_     :=path_+menu_;
  {$ENDIF}
  IF POS('\',parameter_)=0 THEN parameter_:=path_+parameter_;
  objf_:=''; rhs_:=''; bound_:=''; range_:='';
  WITH ps DO
  BEGIN
    drucker_    :=lp_par.get_string_par('DRUCKER');
    destination_:=lp_par.get_string_par('DESTINATION');
    portrait_   :=lp_par.get_string_par('FORMAT');
    rand        :=lp_par.get_int_par('RAND');
    pitch       :=lp_par.get_int_par('PITCH');
    lpp         :=lp_par.get_int_par('LINES-PER-PAGE');
    lpi         :=lp_par.get_int_par('LINES-PER-INCH');
    duplex:=FALSE; upper:=FALSE;
  END;
END;
{---------------------------------------------------------------------------}
PROCEDURE tlp1.new_model;
VAR i,j:INTEGER;
BEGIN
  IF NOT stat_geladen THEN EXIT;
  pricing.done(r.nrows); vvector.done(r.nrows); rhs.done(r.nrows);
  lp_etha.done;
  FOR i:=nvar DOWNTO -1 DO eliminate_column(i);
  FOR i:=1 TO r.nrows DO
  BEGIN
    r.ro^[i]^.done; DISPOSE(r.ro^[i]); r.ro^[i]:=NIL;
  END;
  IF idurch<>NIL THEN
  BEGIN
    FREEMEM(idurch,SIZEOF(INTEGER)*obendurch); idurch:=NIL; obendurch:=0;
  END;
  r.nrows:=0; stat_abbruch:=FALSE; stat_user_abbruch:=FALSE; ninf:=0; inf:=0;
  r.clearhash; clearhash;
  WITH int_rec DO
  BEGIN
    integer_found:=FALSE; upper_limit:=1e30; lower_limit:=-1e30;
  END;
  nvar:=-2;
  nvar:=search_column('RANGE',0);
  nvar:=search_column('RHS',0);
  FILLCHAR(zehn,SIZEOF(zehn),0);
  row_lmax:=0; col_lmax:=0; ninversion:=0; stat_mit_frei:=FALSE;
  nit:=0;
  stat_unbounded:=FALSE; stat_geladen:=FALSE; stat_abbruch:=FALSE;
  stat_built:=FALSE; stat_numerisch:=FALSE; stat_integer_mode:=FALSE;
  stat_flush:=FALSE; stat_togg:=FALSE;
  objf_:=''; rhs_:=''; bound_:=''; range_:='';
END;
{----------------------------------------------------------------------------}
PROCEDURE tlp1.build_objective_function;
VAR faktor,i,j:INTEGER;
BEGIN
  swap_rows(r.search_row(objf_),r.nrows);
  IF stat_maximise THEN faktor:=1 ELSE faktor:=-1;
  FOR j:=1 TO nvar DO WITH co^[j]^ DO IF (art=0) OR (art=5) THEN
  cost:=get_entry(r.nrows,j)*faktor;
END;
{---------------------------------------------------------------------------}
PROCEDURE tlp1.delete_matrix_element(row,column:INTEGER);
VAR i:INTEGER;
BEGIN
  WITH co^[column]^ DO FOR i:=1 TO canzahl DO IF cm^[i].z=row THEN
  BEGIN
    cm^[i]:=cm^[canzahl]; DEC(canzahl); DEC(r.ro^[row]^.roben1_alt);
  END;
END;
{---------------------------------------------------------------------------}
PROCEDURE tlp1.put_range(row:INTEGER; faktor:DOUBLE);
VAR i:INTEGER;
BEGIN
  put_matrix(row,-1,faktor);
END;
{---------------------------------------------------------------------------}
PROCEDURE tlp1.swap_rows(z1,z2:INTEGER);
VAR i,j:INTEGER;
    rr:POINTER;
BEGIN
  IF z1=z2 THEN EXIT;
  swap_integer(r.hash^[r.ro^[z1]^.rhas],r.hash^[r.ro^[z2]^.rhas]);
  rr:=r.ro^[z1]; r.ro^[z1]:=r.ro^[z2]; r.ro^[z2]:=rr;
  FOR j:=-1 TO nvar DO WITH co^[j]^ DO FOR i:=1 TO canzahl DO WITH cm^[i] DO
  IF z=z1 THEN z:=z2 ELSE IF z=z2 THEN z:=z1;
  FOR j:=1 TO lp_etha.netha DO WITH lp_etha.etha^[j] DO FOR i:=1 TO coben DO
  WITH cm^[i] DO IF z=z1 THEN z:=z2 ELSE IF z=z2 THEN z:=z1;
END;
{---------------------------------------------------------------------------}
FUNCTION tlp1.define_row(s_:STRIN; ch:CHAR):BOOLEAN;
VAR h:INTEGER;
    fp:TEXT;
BEGIN
  {$IFDEF log}
  assignfile(fp,'LP.LOG'); append(fp);
  WRITELN(fp,'ROW ',s_,' ',c);
  closefile(fp);
  {$ENDIF}
  h:=r.search_row(s_);
  IF h=0 THEN
  BEGIN
    h:=gethash(s_);
    WHILE h<=hash_max DO
    BEGIN
      IF r.hash^[h]=0 THEN
      BEGIN
        INC(r.nrows);
        IF r.nrows>maxrows THEN
        BEGIN
          DEC(r.nrows);
          my_message('MAXROWS IST ZU KLEIN'); stat_abbruch:=TRUE; new_model;
          define_row:=FALSE; EXIT;
        END
        ELSE
        BEGIN
          r.hash^[h]:=r.nrows; NEW(r.ro^[r.nrows]);
          r.ro^[r.nrows]^.init(ch,s_,h); define_row:=TRUE; EXIT;
        END;
      END
      ELSE IF r.ro^[r.hash^[h]]^.row_=s_ THEN EXIT;
      h:=1+(LONGINT(h)+10*ORD(s_[1])+ORD(s_[2])) MOD hash_max;
    END;
  END
  ELSE
  BEGIN
    my_message('ROW '+s_+' ALREADY DEFINED'); stat_abbruch:=TRUE; new_model;
  END;
END;
{---------------------------------------------------------------------------}
PROCEDURE tlp1.lies_aus_parameter;
BEGIN
  iterlog             :=lp_par.get_int_par  ('ITERLOG');
  basis_intervall     :=lp_par.get_int_par  ('BASIS-INTERVALL');
  dj_min              :=lp_par.get_float_par('DJ-MIN');
  lo_limit            :=lp_par.get_float_par('LO-LIMIT');
  up_limit            :=lp_par.get_float_par('UP-LIMIT');
  integer_crit        :=lp_par.get_float_par('INTEGER-CRIT');
  int_rec.min_nodes   :=lp_par.get_int_par  ('MIN-NODES');
  maxmemory           :=lp_par.get_float_par('MAXMEMORY[MB]');
  maxiter             :=lp_par.get_int_par  ('MAXITER');
  max_time            :=lp_par.get_int_par  ('MAXTIME');
  npricing            :=lp_par.get_int_par  ('PRICING');
  stat_save:=FALSE;   stat_togg:=FALSE;
  stat_restart        :=lp_par.get_bool_par('RESTART');
  stat_with_pse       :=lp_par.get_bool_par('PSE');
  stat_with_log       :=lp_par.get_bool_par('LOGFILE');
  stat_strat1         :=lp_par.get_int_par ('STRAT-1');
  stat_strat2         :=lp_par.get_int_par ('STRAT-2');
  WITH int_rec DO
  BEGIN
    assess         :=lp_par.get_bool_par('ASSESS-PSE');
    IF stat_maximise THEN
    BEGIN
      upper_limit:=objf; lower_limit:=lo_limit;
    END
    ELSE
    BEGIN
      lower_limit:=objf; upper_limit:=up_limit;
    END;
  END;
END;
{---------------------------------------------------------------------------}
DESTRUCTOR tlp1.done;
VAR i:INTEGER;
BEGIN
  IF inf_table<>NIL THEN DISPOSE(inf_table);
  inf_table:=NIL;
  new_model;
  lp_par.done; scr.WINDOW(1,1,80,25); scr.GOTOXY(1,1);
  {$IFNDEF win32}
  menu.done; bild.done;
  {$ENDIF}
  FOR i:=1 TO r.nrows DO
  begin
    DISPOSE(r.ro^[i]); r.ro^[i]:=nil;
  end;
  FOR i:=nvar DOWNTO -1 DO eliminate_column(i);
  r.done; tcolumns.done;
END;
{---------------------------------------------------------------------------}
PROCEDURE tlp1.put_matrix(row,column:INTEGER; faktor:DOUBLE);
VAR i:INTEGER;
    ch:CHAR;
BEGIN
  IF row=0 THEN
  BEGIN
    my_message('Row not defined'); HALT;
  END;
  WITH co^[column]^ DO
  BEGIN
    { Eintrag war vorher<>0 }
    FOR i:=1 TO canzahl DO WITH cm^[i] DO IF z=row THEN
    BEGIN
      IF ABS(faktor)<tolerance THEN
      BEGIN
        cm^[i]:=cm^[canzahl]; DEC(canzahl);
      END
      ELSE wert:=faktor;
      EXIT;
    END;
    INC(canzahl);
    IF canzahl>coben THEN longer(inkrem);
    WITH cm^[canzahl] DO
    BEGIN
      z:=row; wert:=faktor; INC(r.ro^[row]^.roben1_alt);
    END;
  END;
END;
{---------------------------------------------------------------------------}
END.
