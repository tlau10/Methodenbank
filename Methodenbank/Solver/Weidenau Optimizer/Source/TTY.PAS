UNIT tty;
INTERFACE
USES {$IFNDEF win32} CRT, {$ENDIF}
     util;
CONST {$IFDEF win32}
        max_zeilen=600; { diese Deklaration sonst in Fulledit }
        max_size=LONGINT(2 SHL 18-1); { erst mal }
      {$ELSE}
        max_size=2 SHL 15-20;
      {$ENDIF}
      version_='3.0 vom 29.1.2000';
      max_length=25;
      min_nodes           =5;
      pseu_init           =1E10;
      pseu_inf            =1E14;
      up_bound_lmt        =1E21;
      inkrem              =5;
      row_lmax    :INTEGER=0;
      col_lmax    :INTEGER=0;
      tol100      :DOUBLE =1E-09;   { tolerance for violation of bounds }
      tolerance           =1E-11;
      max_strat=100;
      nzehn=10;
      {$IFDEF msdos}
        maxcolumn=1500;
        maxrows  = 560;
        hash_max =5001;
      {$ELSE}
        maxcolumn=max_size DIV SIZEOF(POINTER)-3;   {etwa 64000/4=16000}
        maxrows  =max_size DIV SIZEOF(DOUBLE );
        hash_max =max_size DIV SIZEOF(INTEGER)-1;
      {$ENDIF}

TYPE stack_row=RECORD
                 ch,neu:INTEGER;
                 old:DOUBLE;
                 bo:BOOLEAN;
               END;
CONST maxbound=64000 DIV SIZEOF(stack_row);

TYPE koo=RECORD x,y:INTEGER; END;
     string80=STRING[80];
     {$ifdef EXTENDED}
     DOUBLE=EXTENDED;
     {$endif}
     string_type=STRING[max_length];
     waag=RECORD
            z:INTEGER; wert:DOUBLE;
          END;
     zwaag=RECORD
             sp,z:INTEGER; wert:DOUBLE;
           END;
     down=ARRAY [1..max_size DIV SIZEOF(waag)] OF waag;
     down_pointer=^down;
     zdown=ARRAY [1..max_size DIV SIZEOF(zwaag)] OF zwaag;
     zdown_pointer=^zdown;
     tladder=OBJECT
              cm:down_pointer;
              coben:INTEGER;
              PROCEDURE   longer(number:LONGINT);
              CONSTRUCTOR init  (number:LONGINT);
              DESTRUCTOR  done;
            END;
     etha_ladder=ARRAY [1..max_size DIV SIZEOF(tladder)] OF tladder;
     tet=OBJECT
           etha:^etha_ladder;
           netha:INTEGER;
           PROCEDURE   clear_etha(n:INTEGER; VAR nelemen:LONGINT);
           CONSTRUCTOR init(n:INTEGER);
           DESTRUCTOR  done;
         END;
     tetha=OBJECT(tet)
             maxetha:INTEGER;
             etha_min:DOUBLE;  { minimum element for etha-vector }
             nelemen,ethaspace:LONGINT;
             CONSTRUCTOR init(n:INTEGER);
             DESTRUCTOR  done;
           END;
     zabwaerts=ARRAY [1..max_size DIV SIZEOF(zwaag)] OF zwaag;
     zabwaerts_pointer=^zabwaerts;
     pointer_feld=ARRAY [1..maxcolumn] OF ^INTEGER;
     pointer_feld_pointer=^pointer_Feld;
     lang_int_feld    =ARRAY [1..maxcolumn] OF INTEGER;
     lang_int_feld_pointer    =^lang_int_feld;
     int_feld=ARRAY [1..maxrows] OF INTEGER;
     int_feld_pointer=^int_feld;
     feld=ARRAY [1..maxrows] OF DOUBLE;
     tfeld=OBJECT
             feld_pointer:^feld;
             PROCEDURE   clear(n:INTEGER);
             CONSTRUCTOR init (n:INTEGER);
             DESTRUCTOR  done (n:INTEGER);
           END;
     hash_feld=ARRAY [1..hash_max] OF INTEGER;
     thash=OBJECT
             hash:^hash_feld;
             FUNCTION    gethash(VAR s_:STRIN):INTEGER;
             PROCEDURE   clearhash;
             CONSTRUCTOR init;
             DESTRUCTOR  done;
           END;
     tcolumn=OBJECT(tladder)
               cha,canzahl:INTEGER;
               spa_:string_type;
               lo_bound,up_bound,cost,float,dj:DOUBLE;
               p_plus,p_minus,int_value,int_dj:SINGLE;
               inttype,on_upper,art:SHORTINT;
               allowed:BOOLEAN;
               CONSTRUCTOR init(name_:STRING; kind:SHORTINT);
               DESTRUCTOR  done;
             END;
     tcolumn_array=ARRAY [-1..maxcolumn] OF ^tcolumn;
     tcolumns=OBJECT(thash)
                nvar:INTEGER;
                co:^tcolumn_array;
                PROCEDURE   qsort_dj(li,re:INTEGER);
                PROCEDURE   swap_columns(i1,i2:INTEGER);
                PROCEDURE   eliminate_column(i:INTEGER);
                FUNCTION    get_range(row:INTEGER):DOUBLE;
                FUNCTION    get_entry(row,column:INTEGER):DOUBLE;
                FUNCTION    get_optimal_int_dj(col_:STRIN):SINGLE;
                FUNCTION    get_optimal_int_value(col_:STRIN):SINGLE;
                FUNCTION    get_optimal_dj(col_:STRIN):DOUBLE;
                FUNCTION    get_optimal_value(col_:STRIN):DOUBLE;
                FUNCTION    search_column(s_:STRIN; kind:SHORTINT):INTEGER;
                CONSTRUCTOR init;
                DESTRUCTOR  done;
              END;
     trow=OBJECT
             row_:string_type;
             gleich:SHORTINT;
             { rnum, }
             roben1_alt,roben_alt,reoben,roben,
             rhas,reanzahl,ranzahl,ranzahl1:INTEGER;
             rm:zabwaerts_pointer;
             rem,rm1:pointer_feld_pointer;
             rl:lang_int_feld_pointer;
             CONSTRUCTOR init(c:CHAR; s_:STRING; h:INTEGER);
             DESTRUCTOR  done;
           END;
     trow_array=ARRAY [1..maxrows] OF ^trow;
     trows=OBJECT(thash)
             nrows:INTEGER;
             ro:^trow_array;
             FUNCTION    search_row(s_:STRIN):INTEGER;
             CONSTRUCTOR init;
             DESTRUCTOR  done;
           END;
     stack_row_array=ARRAY [1..maxbound] OF stack_row;
     stack_row_array_pointer=^stack_row_array;
     tbasis=RECORD
              nin:LONGINT;
              let_in,net,ubas:INTEGER;
              bas:^lang_INT_feld;
            END;
     stack_type=RECORD
                  non_int,nz:INTEGER;
                  we,ob:DOUBLE;
                  b:tbasis;
                  zp:lang_INT_feld_pointer;
                END;
     stack_type_pointer=^stack_type;
     wstack_pointer=^wstack;
     wstack=RECORD
              next,prev:wstack_pointer;
              st:stack_type;
            END;
     datei_row_pointer=^datei_row;
     datei_row=RECORD
                   next,prev:datei_row_pointer;
                   o:DOUBLE;
                   basis_start:LONGINT;
                 END;
     datei_record=RECORD
                    noni,ninn:LONGINT;
                    nzz,let_inn,nett,ubass:INTEGER;
                    wee:DOUBLE;
                  END;

CONST max_integer=max_size DIV SIZEOF(POINTER);
      maxtiefe   =max_size DIV SIZEOF(POINTER);
      feld_breite=SIZEOF(datei_record) DIV SIZEOF(INTEGER);

TYPE stapel=ARRAY [1..maxtiefe] OF stack_type_pointer;
     stapel_pointer=^stapel;

CONST { Koordinates for screen output in DOS-Mode }
      koosum         :koo=(x: 0; y:0); { dummy }
      koohead        :koo=(x: 0; y:1); { dummy }
      koomema        :koo=(x:63; y:1);
      koomaxa        :koo=(x:73; y:1);
      koominpiv      :koo=(x:63; y:2);
      koonne         :koo=(x:75; y:2);
      kooninf        :koo=(x: 1; y:3);
      koosavenodes   :koo=(x:17; y:3);
      koonodes       :koo=(x:38; y:3);
      koowtiefe      :koo=(x:49; y:3);
      koohitnodes    :koo=(x:60; y:3);
      koototnodes    :koo=(x:72; y:3);
      koointstatus   :koo=(x: 1; y:4);
      koongleich     :koo=(x:40; y:4);
      koobounds      :koo=(x:41; y:4);
      koominnz       :koo=(x:50; y:4);
      kooneeu        :koo=(x:59; y:4);
      koonbounds     :koo=(x:75; y:4);
      koointfound    :koo=(x:65; y:5);
      koo7           :koo=(x: 7; y:7); { dummy for win32 }

TYPE bound_feld=ARRAY [0..maxbound] OF stack_row;
     bound_type=ARRAY [0..10] OF ^bound_feld;
     tbound=OBJECT
              bounds:ARRAY [0..10] OF ^bound_feld;
              nboundfeld,nbounds:INTEGER;
              FUNCTION    search_bound(VAR sr:stack_row):INTEGER;
              FUNCTION    save_bounds(fname_:STRIN):INTEGER;
              PROCEDURE   get_bound(i:INTEGER; VAR sr:stack_row);
              FUNCTION    add(VAR sr:stack_row):INTEGER;
              PROCEDURE   read_bounds(nbo:INTEGER; fname_:STRIN);
              CONSTRUCTOR init;
              DESTRUCTOR  done;
            END;
     tint_datei=RECORD
                  basis_length,ndatei:LONGINT;
                  datei_root,datei_end:datei_row_pointer;
                  saved,exist,open:BOOLEAN;
                  fp_basis:FILE;
                END;
     tint_rec=OBJECT(tbound)
                wtiefe:LONGINT;
                min_nodes,neeu,obenstack,ngleich,tiefe:INTEGER;
                nexx,vat,vaterob,upper_limit,lower_limit:DOUBLE;
                lasthit,nodes_cut,node,max_memory:LONGINT;
                weight:SINGLE;
                cut,assess,integer_found:BOOLEAN;
                CONSTRUCTOR init;
                DESTRUCTOR  done;
              END;
     sc=OBJECT
          with_output:BOOLEAN;
          sc_:STRIN;
          FUNCTION  keypressed:BOOLEAN;
          FUNCTION  readkey:CHAR;
          FUNCTION  wherex:INTEGER;
          FUNCTION  wherey:INTEGER;
          PROCEDURE delay(ms:LONGINT);
          PROCEDURE int_start;
          PROCEDURE noise;
          PROCEDURE add_eps(n:INTEGER);
          PROCEDURE remove_eps;
          PROCEDURE WRITELN(s_:STRIN);
          PROCEDURE WRITE(s_:STRIN);
          PROCEDURE GOTOXY(x,y:INTEGER);
          PROCEDURE window(x1,x2,x3,x4:INTEGER);
          PROCEDURE clrscr;
          PROCEDURE set_output(s:BOOLEAN);
          PROCEDURE textcolor(c:INTEGER);
          PROCEDURE textbackground(c:INTEGER);
          PROCEDURE win_out(k:koo; s_:STRIN);
          PROCEDURE inversion(s_:STRIN);
          PROCEDURE inf_2;
          CONSTRUCTOR init;
          DESTRUCTOR  done;
        END;

VAR scr:sc;

IMPLEMENTATION

{$IFDEF win32} uses sysutils,forms,lp_form; {$ENDIF}

DESTRUCTOR tfeld.done;
BEGIN
  IF feld_pointer<>NIL THEN FREEMEM(feld_pointer,LONGINT(n)*SIZEOF(DOUBLE));
  feld_pointer:=NIL;
END;
{---------------------------------------------------------------------------}
PROCEDURE tcolumns.swap_columns(i1,i2:INTEGER);
VAR p:POINTER;
BEGIN
  swap_integer(hash^[co^[i1]^.cha],
               hash^[co^[i2]^.cha]);
  p:=co^[i1]; co^[i1]:=co^[i2]; co^[i2]:=p;
END;
{---------------------------------------------------------------------------}
CONSTRUCTOR tladder.init(number:LONGINT);
BEGIN
  coben:=number; GETMEM(cm,SIZEOF(waag)*LONGINT(coben));
END;
{---------------------------------------------------------------------------}
DESTRUCTOR tladder.done;
BEGIN
  FREEMEM(cm,SIZEOF(waag)*coben); cm:=NIL; coben:=0;
END;
{---------------------------------------------------------------------------}
PROCEDURE tladder.longer(number:LONGINT);
VAR m1:tladder;
BEGIN
  m1.init(coben+number);
  MOVE(cm^,m1.cm^,SIZEOF(waag)*coben);
  { FOR i:=1 TO anzahl-1 DO m1^[i]:=m^[i]; }
  done; cm:=m1.cm; coben:=m1.coben;
END;
{---------------------------------------------------------------------------}
CONSTRUCTOR tetha.init(n:INTEGER);
BEGIN
  etha_min:=1e-13;  { minimum element for etha-vector }
  netha:=0; maxetha:=n; nelemen:=0;
  GETMEM(etha,LONGINT(maxetha)*SIZEOF(tet));
END;
{---------------------------------------------------------------------------}
DESTRUCTOR tetha.done;
BEGIN
  clear_etha(0,nelemen); FREEMEM(etha,LONGINT(maxetha)*SIZEOF(tet)); maxetha:=0; etha:=NIL;
END;
{---------------------------------------------------------------------------}
CONSTRUCTOR tet.init(n:INTEGER);
BEGIN
  init(n);
END;
{---------------------------------------------------------------------------}
DESTRUCTOR tet.done;
BEGIN
  done;
END;
{---------------------------------------------------------------------------}
PROCEDURE tet.clear_etha(n:INTEGER; VAR nelemen:LONGINT);
VAR i:INTEGER;
BEGIN
  FOR i:=netha DOWNTO n+1 DO
  BEGIN
    DEC(nelemen,etha^[i].coben);
    etha^[i].done; DEC(netha);
  END;
END;
{---------------------------------------------------------------------------}
CONSTRUCTOR tcolumn.init(name_:STRING; kind:SHORTINT);
BEGIN
  tladder.init(inkrem);
  dj:=0; lo_bound:=0; up_bound:=up_bound_lmt; cost:=0; float:=0;
  int_value:=0; canzahl:=0; spa_:=name_; on_upper:=-1; art:=kind;
  allowed:=TRUE; inttype:=0; p_plus:=0; p_minus:=0;
  IF (kind<>0) AND (kind<>5) THEN row_lmax:=maxi(row_lmax,LENGTH(name_));
  col_lmax:=maxi(col_lmax,1+LENGTH(name_)); {cnum:=nvar;}
END;
{---------------------------------------------------------------------------}
DESTRUCTOR tcolumn.done;
BEGIN
  tladder.done;
END;
{---------------------------------------------------------------------------}
CONSTRUCTOR tint_rec.init;
BEGIN
  integer_found:=FALSE; upper_limit:=1e30; lower_limit:=-1e30;
  tiefe:=0; obenstack:=0; weight:=0.001; tbound.init;
END;
{---------------------------------------------------------------------------}
DESTRUCTOR tint_rec.done;
BEGIN
  tbound.done;
END;
{---------------------------------------------------------------------------}
PROCEDURE tfeld.clear;
BEGIN
  FILLCHAR(feld_pointer^,LONGINT(n)*SIZEOF(DOUBLE),0);
END;
{---------------------------------------------------------------------------}
CONSTRUCTOR tfeld.init;
BEGIN
  GETMEM(feld_pointer,LONGINT(n)*SIZEOF(DOUBLE)); clear(n);
END;
{---------------------------------------------------------------------------}
CONSTRUCTOR trow.init(c:CHAR; s_:STRING; h:INTEGER);
BEGIN
  CASE c OF
    'N': gleich:= 2;
    'L': gleich:=-1;
    'E': gleich:= 0;
    'G': gleich:= 1;
  END;
  row_:=s_; rhas:=h; {rnum:=nrows;}
  ranzahl :=0; rm :=NIL; roben :=0; roben_alt:=inkrem;
  ranzahl1:=0; rm1:=NIL; roben1_alt:=1;
  { 1 extra space for EPS-Perturbation of RHS }
  reanzahl:=0; rem:=NIL; reoben:=0;
END;
{---------------------------------------------------------------------------}
DESTRUCTOR trow.done;
BEGIN
  IF rm<>NIL THEN
  BEGIN
    FREEMEM(rm,roben*SIZEOF(zwaag)); rm:=NIL; ranzahl:=0; roben:=0;
  END;
  IF rm1<>NIL THEN
  BEGIN
    FREEMEM(rm1,roben1_alt*SIZEOF(POINTER)); rm1:=NIL; ranzahl1:=0;
  END;
  IF rem<>NIL THEN
  BEGIN
    FREEMEM(rem,reoben*SIZEOF(POINTER)); rem:=NIL; reanzahl:=0;
  END;
END;
{---------------------------------------------------------------------------}
PROCEDURE thash.clearhash;
BEGIN
  FILLCHAR(hash^,SIZEOF(hash_feld),0);
END;
{---------------------------------------------------------------------------}
CONSTRUCTOR thash.init;
BEGIN
  NEW(hash); clearhash;
END;
{---------------------------------------------------------------------------}
DESTRUCTOR thash.done;
BEGIN
  DISPOSE(hash); hash:=NIL;
END;
{---------------------------------------------------------------------------}
FUNCTION thash.gethash(VAR s_:STRIN):INTEGER;
VAR i:INTEGER;
    h1:LONGINT;
BEGIN
  {$IFOPT R+} {$DEFINE mit} {$ENDIf}
  {$R-}
  h1:=0;
  FOR i:=1 TO LENGTH(s_) DO
  BEGIN
    IF ODD(i) THEN h1:=h1+LONGINT(ORD(s_[i])) SHL i
    ELSE           h1:=h1+LONGINT(ORD(s_[i])) SHL (i+1);
  END;
  i:=h1 MOD hash_max+1;
  IF i<0 THEN i:=i+hash_max;
  gethash:=i;
  {$IFDEF mit} {$R+} {$ENDIF}
  {$UNDEF mit}
END;
{---------------------------------------------------------------------------}
CONSTRUCTOR tcolumns.init;
BEGIN
  NEW(co); thash.init;
END;
{---------------------------------------------------------------------------}
DESTRUCTOR tcolumns.done;
VAR i:INTEGER;
BEGIN
  FOR i:=nvar DOWNTO -1 DO eliminate_column(i);
  DISPOSE(co); co:=NIL; thash.done;
END;
{---------------------------------------------------------------------------}
CONSTRUCTOR trows.init;
BEGIN
  NEW(ro); thash.init; nrows:=0;
END;
{---------------------------------------------------------------------------}
DESTRUCTOR trows.done;
VAR i:INTEGER;
BEGIN
  FOR i:=1 TO nrows DO
  BEGIN
    DISPOSE(ro^[i]); ro^[i]:=nil;
  END;
  DISPOSE(ro); ro:=NIL; thash.done;
END;
{---------------------------------------------------------------------------}
PROCEDURE tcolumns.eliminate_column(i:INTEGER);
BEGIN
  swap_columns(i,nvar); hash^[co^[nvar]^.cha]:=0; co^[nvar]^.done;
  DISPOSE(co^[nvar]); co^[nvar]:=NIL; DEC(nvar);
END;
{---------------------------------------------------------------------------}
FUNCTION tcolumns.search_column(s_:STRIN; kind:SHORTINT):INTEGER;
VAR i:INTEGER;
    h:INTEGER;

FUNCTION allocate(VAR s_:STRIN; kind:SHORTINT):INTEGER;
BEGIN
  INC(nvar); allocate:=0;
  IF nvar>=maxcolumn THEN
  BEGIN
    my_message('FATAL ERROR'#10#13'MAXcolumn TOO SMALL'); HALT;
  END;
  NEW(co^[nvar]); co^[nvar]^.init(s_,kind); allocate:=nvar;
END;
{---------------------------------------------------------------------------}
BEGIN
  h:=gethash(s_);
  WHILE h<=hash_max DO
  BEGIN
    IF hash^[h]=0 THEN
    BEGIN
      IF kind=-3 THEN
      BEGIN
        search_column:=0; EXIT;
      END;
      i:=allocate(s_,kind); hash^[h]:=i; search_column:=i; co^[i]^.cha:=h;
      EXIT;
    END
    ELSE IF co^[hash^[h]]^.spa_=s_ THEN
    BEGIN
      search_column:=hash^[h]; EXIT;
    END;
    h:=1+(LONGINT(h)+10*ORD(s_[1])+ORD(s_[2])) MOD hash_max;
  END;
END;
{---------------------------------------------------------------------------}
FUNCTION trows.search_row(s_:STRIN):INTEGER;
VAR h:INTEGER;
BEGIN
  h:=gethash(s_);
  WHILE h<=hash_max DO
  BEGIN
    IF hash^[h]=0 THEN
    BEGIN
      search_row:=0; EXIT;
    END
    ELSE IF ro^[hash^[h]]^.row_=s_ THEN
    BEGIN
      search_row:=hash^[h]; EXIT;
    END;
    h:=1+(LONGINT(h)+10*ORD(s_[1])+ORD(s_[2])) MOD hash_max;
  END;
  search_row:=0;
END;
{---------------------------------------------------------------------------}
FUNCTION tcolumns.get_optimal_value(col_:STRIN):DOUBLE;
BEGIN
  get_optimal_value:=co^[search_column(col_,0)]^.float;
END;
{---------------------------------------------------------------------------}
FUNCTION tcolumns.get_optimal_dj(col_:STRIN):DOUBLE;
BEGIN
  get_optimal_dj:=co^[search_column(col_,0)]^.dj;
END;
{---------------------------------------------------------------------------}
FUNCTION tcolumns.get_optimal_int_value(col_:STRIN):SINGLE;
BEGIN
  get_optimal_int_value:=co^[search_column(col_,0)]^.int_value;
END;
{---------------------------------------------------------------------------}
FUNCTION tcolumns.get_optimal_int_dj(col_:STRIN):SINGLE;
BEGIN
  get_optimal_int_dj:=co^[search_column(col_,0)]^.int_dj;
END;
{---------------------------------------------------------------------------}
FUNCTION tcolumns.get_entry(row,column:INTEGER):DOUBLE;
VAR i:INTEGER;
BEGIN
  WITH co^[column]^ DO FOR i:=1 TO canzahl DO WITH cm^[i] DO IF z=row THEN
  BEGIN
    get_entry:=wert; EXIT;
  END;
  get_entry:=0;
END;
{---------------------------------------------------------------------------}
FUNCTION tcolumns.get_range(row:INTEGER):DOUBLE;
VAR i:INTEGER;
BEGIN
  WITH co^[-1]^ DO FOR i:=1 TO canzahl DO WITH cm^[i] DO IF z=row THEN
  BEGIN
    get_range:=wert; EXIT;
  END;
  get_range:=0;
END;
{---------------------------------------------------------------------------}
PROCEDURE tcolumns.qsort_dj(li,re:INTEGER);
VAR i,j:INTEGER;
    d:DOUBLE;
BEGIN
  i:=li; j:=re; d:=co^[(li+re) DIV 2]^.dj;
  REPEAT
    WHILE (i<=re) AND (d>co^[i]^.dj) DO INC(i);
    WHILE (j>=li) AND (d<co^[j]^.dj) DO DEC(j);
    IF i<=j THEN
    BEGIN
      swap_columns(i,j); INC(i); DEC(j);
    END;
  UNTIL i>j;
  IF li<j THEN qsort_dj(li,j);
  IF i<re THEN qsort_dj(i,re);
END;
{----------------------------------------------------------------------------}
FUNCTION tbound.add(VAR sr:stack_row):INTEGER;
VAR hilf_:STRIN;
BEGIN
  INC(nbounds); STR(nbounds:5,hilf_);
  scr.win_out(koonbounds,hilf_);
  IF nbounds>=maxbound*(nboundfeld+1) THEN
  BEGIN
    INC(nboundfeld); NEW(bounds[nboundfeld]);
  END;
  bounds[nbounds DIV maxbound]^[nbounds MOD maxbound]:=sr;
  add:=nbounds;
END;
{----------------------------------------------------------------------------}
FUNCTION tbound.search_bound(VAR sr:stack_row):INTEGER;
VAR i:INTEGER;
BEGIN
  FOR i:=1 TO nbounds DO WITH bounds[i DIV maxbound]^[i MOD maxbound] DO
  IF (ch=sr.ch) AND (neu=sr.neu) AND (bo=sr.bo) AND (old=sr.old) AND (ch=sr.ch) THEN
  BEGIN
    search_bound:=i; EXIT;
  END;
  search_bound:=add(sr); ;
END;
{----------------------------------------------------------------------------}
FUNCTION tbound.save_bounds(fname_:STRIN):INTEGER;
VAR bound_file:FILE OF stack_row;
    i:INTEGER;
BEGIN
  {$IFDEF win32}
  SYSTEM.ASSIGNFILE(bound_file,COPY(fname_,1,POS('.',fname_))+'BOU');
  {$ELSE}
  SYSTEM.ASSIGN(bound_file,COPY(fname_,1,POS('.',fname_))+'BOU');
  {$ENDIF}
  REWRITE(bound_file);
  {$IFDEF win32}
    LpForm.addmemo('SAVE TREE');
  {$ELSE}
    WRITELN('SAVE TREE');
  {$ENDIF}
  save_bounds:=nbounds;
  FOR i:=1 TO nbounds DO WRITE(bound_file,bounds[i DIV maxbound]^[i MOD maxbound]);
  done;
  {$IFDEF win32}
  SYSTEM.CLOSEFILE(bound_file);
  {$ELSE}
  SYSTEM.CLOSE(bound_file);
  {$ENDIF}
END;
{----------------------------------------------------------------------------}
DESTRUCTOR tbound.done;
VAR i:INTEGER;
BEGIN
  FOR i:=0 TO nboundfeld DO
  BEGIN
    DISPOSE(bounds[i]); bounds[i]:=NIL;
  END;
  nboundfeld:=-1; nbounds:=0;
END;
{----------------------------------------------------------------------------}
CONSTRUCTOR tbound.init;
BEGIN
  nbounds:=0; nboundfeld:=-1;
END;
{----------------------------------------------------------------------------}
PROCEDURE tbound.read_bounds(nbo:INTEGER; fname_:STRIN);
VAR bound_file:FILE OF stack_row;
    i:INTEGER;
BEGIN
  {$IFDEF win32}
  ASSIGNFILE(bound_file,COPY(fname_,1,POS('.',fname_))+'BOU');
  {$ELSE}
  ASSIGN(bound_file,COPY(fname_,1,POS('.',fname_))+'BOU');
  {$ENDIF}
  RESET(bound_file);
  nbounds:=nbo;
  nboundfeld:=nbounds DIV maxbound;
  FOR i:=0 TO nboundfeld DO NEW(bounds[i]);
  FOR i:=1 TO nbounds DO READ(bound_file,bounds[i DIV maxbound]^[i MOD maxbound]);
  {$IFDEF win32}
  CLOSEFILE(bound_file);
  {$ELSE}
  CLOSE(bound_file);
  {$ENDIF}
END;
{----------------------------------------------------------------------------}
PROCEDURE tbound.get_bound(i:INTEGER; VAR sr:stack_row);
BEGIN
  IF i>nbounds THEN
  BEGIN
    WRITELN('I > nbounds ',i,' ',nbounds); HALT;
  END;
  sr:=bounds[i DIV maxbound]^[i MOD maxbound];
END;
{----------------------------------------------------------------------------}
PROCEDURE sc.textcolor(c:INTEGER);
BEGIN
  IF NOT with_output THEN EXIT;
  {$IFDEF win32}
  LpForm.memo1.font.color:=C;
  {$ELSE}
  CRT.TEXTCOLOR(c);
  {$ENDIF}
END;
{----------------------------------------------------------------------------}
PROCEDURE sc.textbackground(c:INTEGER);
BEGIN
  IF NOT with_output THEN EXIT;
  {$IFDEF win32}
  LpForm.memo1.color:=C;
  {$ELSE}
  CRT.textbackground(c);
  {$ENDIF}
END;
{----------------------------------------------------------------------------}
CONSTRUCTOR sc.init;
BEGIN
  with_output:=TRUE;
  {$IFNDEF win32} NOSOUND; CHECKBREAK:=FALSE; {$ENDIF}
END;
{----------------------------------------------------------------------------}
PROCEDURE sc.set_output(s:BOOLEAN);
BEGIN
  with_output:=s;
END;
{----------------------------------------------------------------------------}
DESTRUCTOR sc.done;
BEGIN
END;
{----------------------------------------------------------------------------}
PROCEDURE sc.clrscr;
BEGIN
  IF NOT with_output THEN EXIT;
  {$IFDEF win32}
  LpForm.memo1.clear;
  {$ELSE}
  CRT.CLRSCR;
  {$ENDIF}
END;
{----------------------------------------------------------------------------}
PROCEDURE sc.window(x1,x2,x3,x4:INTEGER);
BEGIN
  IF NOT with_output THEN EXIT;
  {$IFNDEF win32}
  CRT.WINDOW(x1,x2,x3,x4);
  {$ENDIF}
END;
{----------------------------------------------------------------------------}
PROCEDURE sc.GOTOXY(x,y:INTEGER);
BEGIN
  IF NOT with_output THEN EXIT;
  {$IFNDEF win32}
  CRT.GOTOXY(x,y);
  {$ENDIF}
END;
{----------------------------------------------------------------------------}
FUNCTION sc.wherex:INTEGER;
BEGIN
  IF NOT with_output THEN EXIT;
  {$IFNDEF win32}
  wherex:=CRT.wherex;
  {$ENDIF}
END;
{----------------------------------------------------------------------------}
FUNCTION sc.wherey:INTEGER;
BEGIN
  IF NOT with_output THEN EXIT;
  {$IFNDEF win32}
  wherey:=CRT.wherey;
  {$ENDIF}
END;
{----------------------------------------------------------------------------}
PROCEDURE sc.WRITE(s_:STRIN);
BEGIN
  IF NOT with_output THEN EXIT;
  {$IFDEF win32}
  LpForm.addmemo(s_);
  {$ELSE}
  SYSTEM.WRITE(s_);
  {$ENDIF}
END;
{----------------------------------------------------------------------------}
PROCEDURE sc.WRITELN(s_:STRIN);
BEGIN
  IF NOT with_output THEN EXIT;
  {$IFDEF win32}
  LpForm.addmemo(s_);
  {$ELSE}
  SYSTEM.WRITELN(s_);
  {$ENDIF}
END;
{----------------------------------------------------------------------------}
PROCEDURE sc.inversion(s_:STRIN);
BEGIN
  IF NOT with_output THEN EXIT;
  {$IFDEF win32}
  LpForm.label8.caption:=s_; application.processmessages;
  {$ENDIF}
END;
{----------------------------------------------------------------------------}
PROCEDURE sc.inf_2;
BEGIN
  IF NOT with_output THEN EXIT;
  {$IFDEF win32}
    LpForm.label8.caption:=''; application.processmessages;
  {$ELSE}
    GOTOXY(1,WHEREY); CLREOL;
  {$ENDIF}
END;
{----------------------------------------------------------------------------}
PROCEDURE sc.remove_eps;
VAR wx:WORD;
BEGIN
  IF NOT with_output THEN EXIT;
  {$IFDEF win32}
  LpForm.appendmemo('REMOVE EPS ');
  {$ELSE}
  wx:=WHEREX; WRITE('REMOVE EPS '); CLREOL; GOTOXY(wx,WHEREY);
  {$ENDIF}
END;
{----------------------------------------------------------------------------}
PROCEDURE sc.add_eps(n:INTEGER);
VAR wx:WORD;
BEGIN
  IF NOT with_output THEN EXIT;
  {$IFDEF win32}
  LpForm.appendmemo('EPS '+ltoa(n));
  {$ELSE}
  wx:=WHEREX; WRITE('EPS '+ltoa(n)); CLREOL; GOTOXY(wx,WHEREY);
  {$ENDIF}
END;
{----------------------------------------------------------------------------}
PROCEDURE sc.noise;
BEGIN
  IF NOT with_output THEN EXIT;
  {$IFDEF win32}
    BEEP; BEEP; BEEP;
  {$ELSE}
    SOUND(1000); DELAY(10); NOSOUND;
  {$ENDIF}
END;
{----------------------------------------------------------------------------}
PROCEDURE sc.int_start;
VAR ii:INTEGER;
BEGIN
  IF NOT with_output THEN EXIT;
  {$IFDEF win32}
    LpForm.label6.caption:='NONINT  VECTOR      NEW     OLD       PSEUDO            OBJF';
  {$ELSE}
    scr.win_out(koointstatus,laenge(' ',79,' '));
    ii:=maxi(6,WHEREY); WINDOW(1,1,80,25); GOTOXY(1,5);
    WRITE('NONINT  VECTOR      NEW     OLD       PSEUDO         OBJF');
    CLREOL; WINDOW(1,6,80,25); GOTOXY(1,ii);
    win_out(koonodes,'NODES OPEN         HIT         TOT');
    win_out(koobounds,'                          BOUNDS');
  {$ENDIF}
END;
{----------------------------------------------------------------------------}
PROCEDURE sc.delay(ms:LONGINT);
BEGIN
  {$IFNDEF win32}
  CRT.DELAY(ms);
  {$ENDIF}
END;
{----------------------------------------------------------------------------}
FUNCTION sc.readkey:CHAR;
BEGIN
  {$IFNDEF win32}
  readkey:=CRT.READKEY;
  {$ENDIF}
END;
{----------------------------------------------------------------------------}
FUNCTION sc.keypressed:BOOLEAN;
BEGIN
  {$IFNDEF win32}
  keypressed:=CRT.KEYPRESSED;
  {$ENDIF}
END;
{----------------------------------------------------------------------------}
PROCEDURE sc.win_out(k:koo; s_:STRIN);
VAR wx,wy:INTEGER;
BEGIN
  IF NOT with_output THEN EXIT;
  {$IFDEF win32}
  CASE k.y OF
    0: CASE k.x OF
         0: LpForm.label5.caption :=s_;
       END;
    1: CASE k.x OF
         0:  LpForm.label6.caption :=s_;
         63: LpForm.label2.caption :=s_;
       END;
    2: CASE k.x OF
         63: LpForm.label3.caption :=s_;
         75: LpForm.label9.caption :=s_;
       END;
    3: CASE k.x OF
         1:  LpForm.label4.caption :=s_;
         17: LpForm.label7.caption :=s_;
         49: LpForm.label13.caption:=s_;
         60: LpForm.label21.caption:=s_;
         72: LpForm.label16.caption:=s_;
       END;
    4: CASE k.x OF
          1: LpForm.label15.caption:=s_;
         40: LpForm.label18.caption:=s_;
         50: LpForm.label23.caption:=s_;
         59: LpForm.label10.caption:=s_;
         75: LpForm.label11.caption:=s_;
       END;
    5: LpForm.label19.caption:=s_;
    7: LpForm.label7.caption :=s_;
  END;
  {$ELSE}
  wx:=WHEREX; wy:=WHEREY; WINDOW(1,1,80,25);
  GOTOXY(k.x,k.y); WRITE(s_);
  WINDOW(1,6,80,25); GOTOXY(wx,wy);
  {$ENDIF}
END;
{----------------------------------------------------------------------------}
END.
