UNIT lo5;
{$O+f+}
INTERFACE

USES {$ifdef msdos} OVERLAY, {$ENDIF}
     {$IFDEF win32} SYSUTILS,FORMS,DIALOGS,{$ENDIF}
     util,lo4,tty;

TYPE tlp5=OBJECT(tlp4)
            PROCEDURE INTEGER_search(rest:BOOLEAN);
          END;


IMPLEMENTATION

PROCEDURE tlp5.INTEGER_search(rest:BOOLEAN);
VAR such_tiefe_max,such_tiefe_spec,count,minnz,vater_tiefe,last_tiefe,
    zaehler,grund,ne,nonint,navg,
    jj,ii,such_tiefe_act,vater,coll:INTEGER;
    last_neu,last_change,last_log:LONGINT;
    active,restauriert,tausch,ok:BOOLEAN;
    time,ob_start,avg,ob_vater,best_ob:DOUBLE;
    rest_base,dummy,start_basis:tbasis;
    wurzel:stack_type;
    best_wu,wu,p:wstack_pointer;
    sr:stack_row;

TYPE INTEGER_file_type=FILE OF INTEGER;
     single_feld=ARRAY [1..100] OF SINGLE;
     eintr=RECORD
             o:DOUBLE;
             basis_start:LONGINT;
           END;

LABEL 1,2,3,5,6;

PROCEDURE sol_int;
VAR j:INTEGER;
BEGIN
  FOR j:=1 TO r.nrows-1 DO WITH co^[j]^ DO float:=rhs.feld_pointer^[j]
END;
{----------------------------------------------------------------------------}
FUNCTION int_feasible:BOOLEAN;
VAR j:INTEGER;
BEGIN
  int_feasible:=TRUE; sol_int;
  FOR j:=1 TO r.nrows-1 DO WITH co^[j]^ DO
  CASE inttype OF
    1: { INTEGER and SOS-1 }
    IF ABS(ROUND(float)-float)>integer_crit THEN
    BEGIN
      int_feasible:=FALSE; EXIT;
    END;
    2: { Semi }
    IF (ABS(float)>integer_crit) AND (float<1-integer_crit) THEN
    BEGIN
      int_feasible:=FALSE; EXIT;
    END;
  END;
END;
{----------------------------------------------------------------------------}
PROCEDURE zu(VAR f:tint_datei);
BEGIN
  IF NOT f.open THEN EXIT;
  scr.win_out(koosavenodes,'CLOSE               ');
  {$IFDEF win32}
    application.processmessages; system.CLOSEFILE(f.fp_basis);
  {$ELSE}
    system.CLOSE(f.fp_basis);
  {$ENDIF}
  f.open:=FALSE;
END;
{----------------------------------------------------------------------------}
PROCEDURE loeschen(VAR f:tint_datei);
VAR n1,p1:datei_row_pointer;
BEGIN
  IF NOT f.exist THEN EXIT;
  IF f.open THEN zu(f);
  era(COPY(fname_,1,POS('.',fname_))+'FIL');
  WITH f DO
  BEGIN
    exist:=FALSE; p1:=datei_root;
    IF p1<>NIL THEN
    REPEAT
      n1:=p1^.next; DISPOSE(p1); p1:=n1;
    UNTIL n1=NIL;
    datei_root:=NIL; datei_end:=NIL; ndatei:=0;
    basis_length:=0; 
  END;
  int_rec.max_memory:=2 SHL 29;
END;
{----------------------------------------------------------------------------}
PROCEDURE clear_row(VAR zp:lang_INT_feld_pointer; VAR nz:INTEGER);
BEGIN
  FREEMEM(zp,nz*SIZEOF(INTEGER)); zp:=NIL; nz:=0;
END;
{---------------------------------------------------------------------------}
PROCEDURE save_nodes(VAR f:tint_datei);
VAR rec:datei_record;
    rec_array:ARRAY [1..feld_breite] OF INTEGER ABSOLUTE rec;
    p:wstack_pointer;
    dat,p1:datei_row_pointer;
BEGIN
  WITH f DO
  BEGIN
    IF NOT exist THEN
    BEGIN
      scr.WRITELN('OPEN');
      {$IFDEF win32}
        SYSTEM.ASSIGNFILE(fp_basis,COPY(fname_,1,POS('.',fname_))+'FIL');
      {$ELSE}
        SYSTEM.ASSIGN(fp_basis,COPY(fname_,1,POS('.',fname_))+'FIL');
      {$ENDIF}
      SYSTEM.REWRITE(fp_basis,SIZEOF(INTEGER));
      exist:=TRUE; open:=TRUE;
    END;
    scr.GOTOXY(1,scr.WHEREY);
    IF ndatei mod 40=0 THEN
    scr.win_out(koosavenodes,'SAVE '+ltoa(int_rec.wtiefe)+' '+ltoa(ndatei)+' ');
    {$IFDEF win32} application.processmessages; {$ENDIF}
    saved:=TRUE; INC(ndatei); p:=ws_end; NEW(dat);
    WITH dat^ DO
    BEGIN
      o:=p^.st.ob; basis_start:=basis_length;
    END;
    WITH rec DO
    BEGIN
      noni       :=p^.st.non_int;
      nzz        :=p^.st.nz;
      let_inn    :=p^.st.b.let_in;
      ninn       :=p^.st.b.nin;
      nett       :=p^.st.b.net;
      ubass      :=p^.st.b.ubas;
      wee        :=p^.st.we;
      SEEK      (fp_basis,basis_length);
      BLOCKWRITE(fp_basis,rec_array,feld_breite);
      BLOCKWRITE(fp_basis,p^.st.b.bas^,ubass);
      BLOCKWRITE(fp_basis,p^.st.zp^,nzz);
      INC(basis_length,feld_breite+ubass+nzz);
      WITH p^.st DO
      BEGIN
        clear_basis(b); clear_row(zp,nz);
      END;
    END;
    DEC(int_rec.wtiefe);
    IF int_rec.wtiefe=0 THEN
    BEGIN
      ws_root:=NIL; ws_end:=NIL;
    END
    ELSE
    BEGIN
      p^.prev^.next:=NIL; ws_end:=p^.prev;
    END;
    IF p<>NIL THEN DISPOSE(p);
    { in Liste einh„ngen und sortieren }
    IF datei_root=NIL THEN
    BEGIN
      datei_root:=dat; datei_end:=dat; dat^.next:=NIL; dat^.prev:=NIL;
    END
    ELSE
    BEGIN
      p1:=datei_root;
      IF ( stat_maximise) AND (p1^.o<=dat^.o) OR
      (NOT stat_maximise) AND (p1^.o>=dat^.o) THEN
      BEGIN
        { best value so far: insert at start position }
        p1^.prev:=dat; dat^.next:=p1; datei_root:=dat; dat^.prev:=NIL;
      END
      ELSE
      BEGIN
        p1:=datei_end;
        IF ( stat_maximise) AND (p1^.o>=dat^.o) OR
        (NOT stat_maximise) AND (p1^.o<=dat^.o) THEN
        BEGIN
          { worst value so far: append at end position }
          p1^.next:=dat; dat^.prev:=p1; datei_end:=dat; dat^.next:=NIL;
        END
        ELSE { intermediate value }
        BEGIN
          p1:=datei_root;
          WHILE (p1<>NIL) AND (
          ((    stat_maximise) AND (p1^.o>dat^.o))  OR
          ((NOT stat_maximise) AND (p1^.o<dat^.o))) DO p1:=p1^.next;
          { position found }
          p1^.prev^.next:=dat;
          dat^.next:=p1;
          dat^.prev:=p1^.prev;
          p1^.prev:=dat;
        END;
      END;
    END;
  END;
END;
{----------------------------------------------------------------------------}
PROCEDURE flush_stack;
VAR p,n:wstack_pointer;
    p1,n1:datei_row_pointer;
    { in this Routine all waiting nodes with a worse OBJ are
    removed from waiting set }
BEGIN
  WITH int_rec DO
  BEGIN
    nodes_cut:=0; p:=ws_end;
    WHILE wtiefe>0 DO
    IF (     stat_maximise  AND (p^.st.ob<int_rec.lower_limit))
    OR ((NOT stat_maximise) AND (p^.st.ob>int_rec.upper_limit)) THEN
    BEGIN
      DEC(wtiefe); INC(nodes_cut);
      WITH p^.st DO
      BEGIN
        clear_basis(b); clear_row(zp,nz);
      END;
      n:=p^.prev; DISPOSE(p);
      IF n<>NIL THEN n^.next:=NIL;
      ws_end:=n; p:=n;
    END
    ELSE BREAK;
    IF wtiefe=0 THEN
    BEGIN
      ws_root:=NIL; ws_end:=NIL;
    END;
    WITH fil DO IF exist THEN
    BEGIN
      p1:=datei_end;
      WHILE ndatei>0 DO
      BEGIN
        IF   (stat_maximise  AND (p1^.o<int_rec.lower_limit)) OR
        ((NOT stat_maximise) AND (p1^.o>int_rec.upper_limit)) THEN
        BEGIN
          DEC(ndatei); INC(nodes_cut); n1:=p1^.prev; DISPOSE(p1);
          IF n1<>NIL THEN n1^.next:=NIL;
          datei_end:=n1; p1:=n1;
        END
        ELSE BREAK;
      END;
      IF ndatei=0 THEN
      BEGIN
        datei_root:=NIL; datei_end:=NIL; zu(fil); loeschen(fil);
      END;
    END;
    IF nodes_cut>0 THEN
    BEGIN
      scr.win_out(koowtiefe,ltoa_x(wtiefe+fil.ndatei,6));
      scr.WRITELN(ltoa(nodes_cut)+' NODES CUT');
      IF stat_with_log THEN append_log_line(ltoa(nodes_cut)+' NODES CUT');
    END;
  END;
END;
{----------------------------------------------------------------------------}
PROCEDURE clear(VAR s:stack_type_pointer);
BEGIN
  WITH s^ DO
  BEGIN
    clear_basis(b); clear_row(zp,nz);
  END;
  DISPOSE(s); s:=NIL;
END;
{----------------------------------------------------------------------------}
PROCEDURE save_tree;
VAR boundold,i:INTEGER;
    frec:FILE OF eintr;
    p,n:datei_row_pointer;
    en:eintr;
    fp:TEXT;
BEGIN
  IF stat_with_pse THEN save_pse;
  IF int_rec.integer_found THEN int_loesung;
  flush_stack; lp_etha.clear_etha(0,lp_etha.nelemen);
  boundold:=int_rec.save_bounds(fname_);
  WITH int_rec DO
  BEGIN
    IF scr.WHEREX>1 THEN scr.WRITELN('');
    WHILE wtiefe>0 DO save_nodes(fil);
    zu(fil);
  END;
  WITH int_rec DO
  BEGIN
    FOR i:=1 TO obenstack DO IF stack^[i]<>NIL THEN
    BEGIN
      clear(stack^[i]); DISPOSE(stack^[i]);
    END;
    FREEMEM(stack,obenstack*SIZEOF(POINTER)); stack:=NIL;
    {$IFDEF win32}
    ASSIGNFILE(frec,COPY(fname_,1,POS('.',fname_))+'WAI');
    {$ELSE}
    ASSIGN(frec,COPY(fname_,1,POS('.',fname_))+'WAI');
    {$ENDIF}
    REWRITE(frec);
    p:=fil.datei_root;
    FOR i:=1 TO fil.ndatei DO
    BEGIN
      en.o          :=p^.o;
      en.basis_start:=p^.basis_start;
      WRITE(frec,en); n:=p^.next; DISPOSE(p); p:=n;
    END;
    {$IFDEF win32}
    SYSTEM.CLOSEFILE(frec);
    {$ELSE}
    SYSTEM.CLOSE(frec);
    {$ENDIF}
    fil.datei_root:=NIL; fil.datei_end:=NIL;
  END;
  ASSIGNFILE(fp,COPY(fname_,1,POS('.',fname_))+'IFF'); REWRITE(fp);
  WITH int_rec DO WITH fil DO
  BEGIN
    IF stat_maximise THEN WRITELN(fp,'+') ELSE WRITELN(fp,'-');
    WRITELN(fp,upper_limit,' ',lower_limit,' ',lasthit);
    WRITELN(fp,boundold,' ',obenstack,' ',ndatei,' ',
               basis_length,' ',ninversion,' ',node,' ',time_out(julia-t_start+elapsed));
  END;
  CLOSEFILE(fp); elapsed:=0;
END;
{----------------------------------------------------------------------------}
FUNCTION file_miss(t_:STRIN):BOOLEAN;
VAR fp:TEXT;
BEGIN
  file_miss:=FALSE;
  ASSIGNFILE(fp,t_); {$I-} RESET(fp); {$I+}
  IF IORESULT=0 THEN CLOSEFILE(fp) ELSE
  BEGIN
    scr.WRITELN('FILE '+t_+' NOT FOUND');
    file_miss:=TRUE;;
  END;
END;
{----------------------------------------------------------------------------}
PROCEDURE load_tree;
VAR nbo,i:INTEGER;
    frec:FILE OF eintr;
    p:datei_row_pointer;
    en:eintr;
    fp:TEXT;
    time_,s_:STRIN;
BEGIN
  scr.WRITELN('LOAD TREE');
  IF file_miss(COPY(fname_,1,POS('.',fname_))+'IFF')
  OR file_miss(COPY(fname_,1,POS('.',fname_))+'WAI')
  OR file_miss(COPY(fname_,1,POS('.',fname_))+'BOU')
  OR file_miss(COPY(fname_,1,POS('.',fname_))+'FIL') THEN
  BEGIN
    my_message('ONE OF THE NECESARRY FILE .IFF, .WAI, .BOU, .FIL is missing');
    EXIT;
  END;
  ASSIGNFILE(fp,COPY(fname_,1,POS('.',fname_))+'IFF'); RESET(fp);
  WITH int_rec DO
  BEGIN
    READLN(fp,s_); s_:=rtrim(ltrim(s_));
    stat_maximise:=(s_='+'); build_objective_function;
    READLN(fp,upper_limit,lower_limit,lasthit);
    IF lasthit<>0 THEN scr.win_out(koointfound,'INT. SOL. FOUND');
    READLN(fp,nbo,obenstack,fil.ndatei,fil.basis_length,ninversion,node,time_);
    scr.win_out(koonbounds,ltoa(nbo)+' ');
    STR(lasthit:8,hilf_); scr.win_out(koohitnodes,hilf_);
  END;
  time_:=rtrim(ltrim(time_));
  WHILE pos(' ',time_)<>0 DO delete(time_,pos(' ',time_),1);
  elapsed:=0; t_start:=julia;
  IF pos('d',time_)<>0 THEN
  BEGIN
    i:=pos('d',time_);
    s_:=COPY(time_,1,i-1); elapsed:=elapsed+lese_int(s_)*86400;
    time_:=copy(time_,i+1,255);
  END;
  IF pos('h',time_)<>0 THEN
  BEGIN
    i:=pos('h',time_);
    s_:=COPY(time_,1,i-1); elapsed:=elapsed+lese_int(s_)*3600.0;
    time_:=copy(time_,i+1,255);
  END;
  IF pos(#39,time_)<>0 THEN
  BEGIN
    i:=pos(#39,time_);
    s_:=COPY(time_,1,i-1); elapsed:=elapsed+lese_int(s_)*60;
    time_:=copy(time_,i+1,255);
  END;
  IF pos('"',time_)<>0 THEN
  BEGIN
    i:=pos('"',time_);
    s_:=COPY(time_,1,i-1); elapsed:=elapsed+lese_zahl(s_);
  END;
  CLOSEFILE(fp); era(COPY(fname_,1,POS('.',fname_))+'IFF');
  ASSIGNFILE(fp,COPY(fname_,1,POS('.',fname_))+'RHS'); REWRITE(fp);
  WITH co^[0]^ DO
  BEGIN
    WRITELN(fp,canzahl);
    FOR i:=1 TO canzahl DO WITH cm^[i] DO WRITELN(fp,z,' ',wert);
    FREEMEM(cm,SIZEOF(waag)*coben);
  END;
  CLOSEFILE(fp); lp_etha.clear_etha(0,lp_etha.nelemen);
  WITH lp_par DO
  BEGIN
    ASSIGNFILE(fp,'$$$.$$$'); REWRITE(fp); save_parameter(fp); done; CLOSEFILE(fp);
    init;
    ASSIGNFILE(fp,'$$$.$$$'); RESET(fp); read_parameter(fp); CLOSEFILE(fp);
    era('$$$.$$$');
  END;
  ASSIGNFILE(fp,COPY(fname_,1,POS('.',fname_))+'RHS'); RESET(fp);
  WITH co^[0]^ DO
  BEGIN
    READLN(fp,canzahl); coben:=canzahl;
    GETMEM(cm,coben*SIZEOF(waag));
    FOR i:=1 TO canzahl DO WITH cm^[i] DO READLN(fp,z,wert);
  END;
  CLOSEFILE(fp); era(COPY(fname_,1,POS('.',fname_))+'RHS');
  STR(int_rec.lasthit:8,hilf_); scr.win_out(koototnodes,hilf_);
  scr.win_out(koohitnodes,hilf_); inversion;
  GETMEM(stack,int_rec.obenstack*SIZEOF(POINTER));
  FOR i:=1 TO int_rec.obenstack DO stack^[i]:=NIL;
  IF NOT fil.open THEN
  BEGIN
  scr.WRITELN('OPEN');
  {$IFDEF win32}
    SYSTEM.ASSIGNFILE(fil.fp_basis,COPY(fname_,1,POS('.',fname_))+'FIL');
  {$ELSE}
    SYSTEM.ASSIGN(fil.fp_basis,COPY(fname_,1,POS('.',fname_))+'FIL');
  {$ENDIF}
    RESET(fil.fp_basis,SIZEOF(INTEGER)); fil.exist:=TRUE; fil.open:=TRUE;
  END;
  int_rec.read_bounds(nbo,fname_);
  era(COPY(fname_,1,POS('.',fname_))+'BOU');
  s_:=COPY(fname_,1,POS('.',fname_))+'WAI';
  {$IFDEF win32} ASSIGNFILE(frec,s_); {$ELSE} ASSIGN(frec,s_); {$ENDIF}
  RESET(frec);
  WITH fil DO
  BEGIN
    FOR i:=1 TO ndatei DO
    BEGIN
      NEW(p); READ(frec,en);
      p^.o          :=en.o;
      p^.basis_start:=en.basis_start;
      IF datei_root=NIL THEN
      BEGIN
        datei_root:=p; p^.prev:=NIL; datei_end:=p; p^.next:=NIL;
      END
      ELSE
      BEGIN
        p^.prev:=datei_end; p^.next:=NIL;
        datei_end^.next:=p; datei_end:=p;
      END;
    END;
  END;
  {$IFDEF win32}
  CLOSEFILE(frec);
  {$ELSE}
  CLOSE(frec);
  {$ENDIF}
  era(COPY(fname_,1,POS('.',fname_))+'WAI');
END;
{----------------------------------------------------------------------------}
PROCEDURE read_nodes(min_nodes:INTEGER; VAR f:tint_datei);
VAR j:INTEGER;
    rec:datei_record;
    rec_array:ARRAY [1..feld_breite] OF INTEGER ABSOLUTE rec;
    p:wstack_pointer;
    p1:datei_row_pointer;
BEGIN
  WITH int_rec DO FOR j:=1 TO min_nodes-1 DO
  BEGIN
    IF f.ndatei<1 THEN EXIT;
    {$IFDEF msdos}
      IF wtiefe>0 THEN IF MAXAVAIL<64000 THEN EXIT;
    {$ELSE}
      {$IFDEF win32}
        IF wtiefe MOD 10=0 THEN IF wtiefe>0 THEN IF getheapstatus.totalallocated<2e6 THEN EXIT;
      {$ELSE}
        IF wtiefe>0 THEN IF maxavail<2e6 THEN EXIT;
      {$ENDIF}
    {$ENDIF}
    INC(wtiefe);
    IF wtiefe MOD 10=0 THEN scr.win_out(koosavenodes,'READ '+ltoa(wtiefe)+' '+
      ltoa(f.ndatei)+' '{$IFNDEF win32}+ltoa(MAXAVAIL DIV 1024)+' '{$ENDIF});
    NEW(p);
    IF wtiefe=1 THEN { 1. node }
    BEGIN
      ws_end:=p; ws_root:=p; p^.prev:=NIL; p^.next:=NIL;
    END
    ELSE
    BEGIN
      ws_end^.next:=p; p^.prev:=ws_end; ws_end:=p; p^.next:=NIL;
    END;
    WITH p^.st DO
    BEGIN
      SEEK(f.fp_basis,f.datei_root^.basis_start);
      BLOCKREAD(f.fp_basis,rec_array,feld_breite); nz:=rec.nzz; we:=rec.wee;
      ob:=f.datei_root^.o; non_int:=rec.noni;
      WITH b DO
      BEGIN
        let_in:=rec.let_inn; nin:=rec.ninn; net:=rec.nett; ubas:=rec.ubass;
        GETMEM(bas,ubas*SIZEOF(INTEGER)); BLOCKREAD(f.fp_basis,bas^,ubas);
      END;
      GETMEM(zp,nz*SIZEOF(INTEGER)); BLOCKREAD(f.fp_basis,zp^,nz); DEC(f.ndatei);
      p1:=f.datei_root;
      IF f.ndatei=0 THEN
      BEGIN
        DISPOSE(p1); f.datei_root:=NIL; f.datei_end:=NIL; zu(f); loeschen(f);
      END
      ELSE
      BEGIN
        p1^.next^.prev:=NIL; f.datei_root:=p1^.next; DISPOSE(p1);
      END;
    END;
  END;
END;
{----------------------------------------------------------------------------}
PROCEDURE read_last_node;
VAR rec:datei_record;
    rec_array:ARRAY [1..feld_breite] OF INTEGER ABSOLUTE rec;
    p:wstack_pointer;
    p1:datei_row_pointer;
BEGIN
  WITH fil DO
  BEGIN
    IF ndatei<1 THEN EXIT;
    {$IFDEF msdos}
      IF int_rec.wtiefe>0 THEN IF MAXAVAIL<64000 THEN EXIT;
    {$ELSE}
      {$IFDEF win32}
        IF fil.ndatei MOD 10=0 THEN IF int_rec.wtiefe>0 THEN IF getheapstatus.totalallocated<2e6 THEN EXIT;
      {$ELSE}
        IF int_rec.wtiefe>0 THEN IF maxavail<2e6 THEN EXIT;
      {$ENDIF}
    {$ENDIF}
    INC(int_rec.wtiefe);
    scr.win_out(koosavenodes,'READ '+ltoa(int_rec.wtiefe)+' '+
    ltoa(ndatei)+' '{$IFNDEF win32}+ltoa(MAXAVAIL)+' '{$ENDIF});
    { New node at end of waiting set }
    NEW(p);
    IF int_rec.wtiefe=1 THEN { 1. node }
    BEGIN
      ws_end:=p; ws_root:=p; p^.prev:=NIL; p^.next:=NIL;
    END
    ELSE
    BEGIN
      ws_end^.next:=p; p^.prev:=ws_end; ws_end:=p; p^.next:=NIL;
    END;
    WITH p^.st DO
    BEGIN
      SEEK(fp_basis,datei_end^.basis_start);
      BLOCKREAD(fp_basis,rec_array,feld_breite); nz:=rec.nzz; we:=rec.wee;
      ob:=datei_end^.o; non_int:=rec.noni;
      WITH b DO
      BEGIN
        let_in:=rec.let_inn; nin:=rec.ninn; net:=rec.nett; ubas:=rec.ubass;
        GETMEM(bas,ubas*SIZEOF(INTEGER)); BLOCKREAD(fp_basis,bas^,ubas);
      END;
      GETMEM(zp,nz*SIZEOF(INTEGER)); BLOCKREAD(fp_basis,zp^,nz); DEC(ndatei);
      p1:=datei_end;
      IF ndatei=0 THEN
      BEGIN
        DISPOSE(p1); datei_root:=NIL; datei_end:=NIL; zu(fil); loeschen(fil);
      END
      ELSE
      BEGIN
        p1^.prev^.next:=NIL; datei_end:=p1^.prev; DISPOSE(p1);
      END;
    END;
  END;
END;
{----------------------------------------------------------------------------}
PROCEDURE inbound(nz:INTEGER; zp:lang_INT_feld_pointer);
VAR i:INTEGER;
    sr:stack_row;
BEGIN
  FOR i:=1 TO nz DO
  BEGIN
    int_rec.get_bound(zp^[i],sr);
    WITH sr DO WITH co^[hash^[ch]]^ DO IF bo THEN up_bound:=neu ELSE lo_bound:=neu;
  END;
END;
{----------------------------------------------------------------------------}
PROCEDURE unbound(nz:INTEGER; zp:lang_INT_feld_pointer);
VAR i:INTEGER;
    sr:stack_row;
BEGIN
  FOR i:=nz DOWNTO 1 DO
  BEGIN
    int_rec.get_bound(zp^[i],sr);
    WITH sr DO WITH co^[hash^[ch]]^ DO
    IF bo THEN up_bound:=old ELSE lo_bound:=old;
  END;
END;
{----------------------------------------------------------------------------}
PROCEDURE cancel;
VAR i:INTEGER;
    p,n:wstack_pointer;
    p1,n1:datei_row_pointer;
BEGIN
  WITH int_rec DO
  BEGIN
    FOR i:=tiefe DOWNTO 1 DO WITH stack^[i]^ DO
    BEGIN
      unbound(nz,zp); clear(stack^[i]);
    END;
    tiefe:=0; p:=ws_root;
    IF p<>NIL THEN
    REPEAT
      n:=p^.next;
      WITH p^.st DO
      BEGIN
        clear_basis(b); clear_row(zp,nz);
      END;
      DISPOSE(p); p:=n;
    UNTIL n=NIL;
    wtiefe:=0; ws_root:=NIL; ws_end:=NIL; p1:=fil.datei_root;
    IF p1<>NIL THEN
    REPEAT
      n1:=p1^.next; DISPOSE(p1); p1:=n1;
    UNTIL n1=NIL;
    fil.ndatei:=0; fil.datei_root:=NIL; fil.datei_end:=NIL;
    zu(fil); loeschen(fil);
    done;
    scr.win_out(koonbounds,'     ');
    scr.win_out(kooneeu,laenge_v(' ',5,' '));
    IF stat_with_pse THEN save_pse;
  END;
END;
{----------------------------------------------------------------------------}
PROCEDURE make_row(cha,ne,nonint:INTEGER; ol:DOUBLE; upper:BOOLEAN; wert:DOUBLE);
VAR sr:stack_row;
    stt:stapel_pointer;
    i:INTEGER;
BEGIN
  WITH int_rec DO
  BEGIN
    INC(tiefe); INC(node);
    IF tiefe>obenstack THEN
    BEGIN
      GETMEM (stt  ,(1000+obenstack)*SIZEOF(POINTER)); { Neuen Speicher Reservieren }
      MOVE   (stack^,stt^,obenstack *SIZEOF(POINTER)); { Alten Bereich kopieren }
      FREEMEM(stack,      obenstack *SIZEOF(POINTER)); { Alten Bereich freigeben }
      stack:=stt;      { Neuen Bereich setzen }
      FOR i:=obenstack+1 TO obenstack+1000 DO stack^[i]:=NIL;
      INC(obenstack,1000);
    END;
    NEW(stack^[tiefe]);
    WITH stack^[tiefe]^ DO
    BEGIN
      ob:=int_rec.vaterob; we:=wert; b.bas:=NIL; b.ubas:=0; non_int:=nonint;
      nz:=1; GETMEM(zp,SIZEOF(INTEGER));
      WITH sr DO
      BEGIN
        ch:=cha; old:=ol; neu:=ne; bo:=upper;
      END;
      zp^[1]:=search_bound(sr);
    END;
  END;
END;
{----------------------------------------------------------------------------}
PROCEDURE with_grund(gr:INTEGER);
BEGIN
  IF scr.WHEREX>1 THEN scr.WRITELN('');
  grund:=gr; save_nodes(fil);
  IF scr.WHEREX>1 THEN scr.WRITELN('');
END;
{----------------------------------------------------------------------------}
PROCEDURE put_waiting(bis,welche:INTEGER);
VAR j:INTEGER;
    dat,p:wstack_pointer;
BEGIN
  WITH int_rec DO
  BEGIN
    INC(wtiefe); NEW(dat);
    WITH dat^.st DO
    BEGIN
      nz:=wurzel.nz+bis+1;
      GETMEM(zp,nz*SIZEOF(INTEGER));
      FOR j:=1 TO wurzel.nz DO zp^[j          ]:=    wurzel.zp^[j];
      FOR j:=1 TO bis       DO zp^[j+wurzel.nz]:=stack^[j]^.zp^[1];
      zp^[nz]:=stack^[welche]^.zp^[1];
      we     :=stack^[welche]^.we;
      ob     :=stack^[welche]^.ob;
      non_int:=stack^[welche]^.non_int;
      b      :=stack^[welche]^.b;
      WITH stack^[welche]^ DO
      BEGIN
        clear_row(zp,nz); we:=0; ob:=0; b.bas:=NIL; b.ubas:=0;
      END;
      DISPOSE(stack^[welche]); stack^[welche]:=NIL;
    END;
    IF ws_root=NIL THEN
    BEGIN
      ws_root:=dat; ws_end:=dat; dat^.next:=NIL; dat^.prev:=NIL;
    END
    ELSE
    BEGIN
      p:=ws_root;
      IF (ABS(p^.st.ob-dat^.st.ob)<1e-5)
      OR ((    stat_maximise  AND (p^.st.ob<=dat^.st.ob))
      OR ((NOT stat_maximise) AND (p^.st.ob>=dat^.st.ob))) THEN { new best value }
      BEGIN
        p^.prev:=dat; dat^.next:=p; ws_root:=dat; dat^.prev:=NIL;
      END
      ELSE
      BEGIN
        p:=ws_end;
        IF (     stat_maximise  AND (p^.st.ob>=dat^.st.ob))
        OR ((NOT stat_maximise) AND (p^.st.ob<=dat^.st.ob)) THEN { new worst value }
        BEGIN
          p^.next:=dat; dat^.prev:=p; ws_end:=dat; dat^.next:=NIL;
        END
        ELSE { intermediate value }
        BEGIN
          p:=ws_root;
          WHILE (p<>NIL) AND (
          ((    stat_maximise) AND (p^.st.ob>dat^.st.ob))  OR
          ((NOT stat_maximise) AND (p^.st.ob<dat^.st.ob))) DO p:=p^.next;
          { position found }
          p^.prev^.next:=dat; dat^.next:=p; dat^.prev:=p^.prev; p^.prev:=dat;
        END;
      END;
    END;
  END;
  WITH int_rec DO
  REPEAT
    fil.saved:=FALSE;
    IF wtiefe<2 THEN EXIT;
    {$IFDEF win32}
      IF wtiefe>max_memory THEN with_grund(1)
      ELSE IF fil.ndatei>0 THEN IF
      ((    stat_maximise) AND (ws_end^.st.ob<fil.datei_root^.o)) OR
      ((NOT stat_maximise) AND (ws_end^.st.ob>fil.datei_root^.o)) THEN with_grund(3)
      ELSE EXIT;
    {$ELSE}
      {$IFDEF msdos}
        IF MAXAVAIL<maxi(65000,matspace+lp_etha.ethaspace) THEN with_grund(2)
      {$ELSE}
        IF MAXAVAIL<matspace+lp_etha.ethaspace THEN with_grund(2)
      {$ENDIF}
      ELSE IF wtiefe>max_memory THEN with_grund(1)
      ELSE IF fil.ndatei>0 THEN IF
      ((    stat_maximise) AND (ws_end^.st.ob<fil.datei_root^.o)) OR
      ((NOT stat_maximise) AND (ws_end^.st.ob>fil.datei_root^.o)) THEN with_grund(3)
      ELSE EXIT;
    {$ENDIF}
  UNTIL NOT fil.saved;
END;
{----------------------------------------------------------------------------}
PROCEDURE int_status_out;
VAR h_:STRIN;
BEGIN
  WITH int_rec DO
  BEGIN
    IF lower_limit=0 THEN lg:=-1 ELSE lg:=ROUND(INT(log10(ABS(lower_limit))));
    IF lg>0 THEN STR(lower_limit:12:9-lg,h_)
    ELSE         STR(lower_limit:12:9,h_);
    IF nexx=0 THEN lg:=-1 ELSE lg:=ROUND(INT(log10(ABS(nexx))));
    IF lg>0 THEN STR(nexx:12:9-lg,hilf_)
    ELSE         STR(nexx:12:9,hilf_);
    h_:=h_+' '+hilf_;
    IF ngleich>0 THEN
    scr.win_out(koongleich,ltoa(ngleich)+' ') ELSE scr.win_out(koongleich,'    ');
    IF upper_limit=0 THEN lg:=-1 ELSE lg:=ROUND(INT(log10(ABS(upper_limit))));
    IF lg>0 THEN STR(upper_limit:12:9-lg,hilf_)
    ELSE         STR(upper_limit:12:9,hilf_);
    scr.win_out(koointstatus,h_+' '+hilf_);
    STR(node :8,h_); scr.win_out(koototnodes,h_);
  END;
END;
{----------------------------------------------------------------------------}
PROCEDURE write_bounds(nonint:INTEGER; spa_:STRIN; neu:INTEGER; c:CHAR; we:DOUBLE);
BEGIN
  {$IFDEF win32}
  scr.sc_:=ltoa_x(nonint,4)+' '+laenge(spa_,12,' ')+ltoa_x(neu,4)+' '+c+' '+ftoa_x(we,10,5);
  {Form0.addmemo(out_);}
  {$ELSE}
  scr.TEXTCOLOR(13);
  scr.WRITE(ltoa_x(nonint,4)+' '+laenge(spa_,13,' ')+ltoa_X(neu,4)+' '+c+' '+ftoa_x(we,10,5));
  scr.sc_:='';
  SCR.TEXTCOLOR(7);
  {$ENDIF}
END;
{----------------------------------------------------------------------------}
PROCEDURE work_in(w:INTEGER; m:SHORTINT);
VAR sr:stack_row;
BEGIN
  {$IFNDEF win32} IF scr.WHEREX>1 THEN scr.WRITELN(''); {$ENDIF}
  IF w<1 THEN EXIT;
  WITH stack^[w]^ DO
  BEGIN
    scr.win_out(koowtiefe,ltoa_x(int_rec.wtiefe+fil.ndatei,6));
    int_rec.get_bound(zp^[1],sr);
    WITH sr DO
    BEGIN
      WITH co^[hash^[ch]]^ DO
      IF bo THEN
      BEGIN
        up_bound:=neu; write_bounds(nonint,spa_,neu,'U',we);
      END
      ELSE
      BEGIN
        lo_bound:=neu; write_bounds(nonint,spa_,neu,'L',we);
      END;
    END;
    IF m=2 THEN restore_basis(b,ob);
    IF m>-1 THEN
    BEGIN
      calc_rhs;
      IF NOT stat_abbruch THEN
      BEGIN
        iterate(FALSE);
        IF t_inv>10*time THEN
        BEGIN
          int_rec.max_memory:=maxi(min_nodes,LONGINT(int_rec.wtiefe)-10);
          scr.WRITELN('Standard_time='+ftoa(time)+' neu '+ftoa(t_inv));
        END;
      END;
      stack^[w]^.ob:=objf;
      IF ninf>0 THEN
      BEGIN
        IF stat_maximise THEN stack^[w]^.ob:=-1e30 ELSE stack^[w]^.ob:=1e30 ;
      END;
    END;
  END;
END;
{----------------------------------------------------------------------------}
FUNCTION cut_off:BOOLEAN;
BEGIN
  IF ninf>0 THEN
  BEGIN
    cut_off:=TRUE; EXIT;
  END;
  IF stat_maximise THEN
  BEGIN
    IF objf<int_rec.lower_limit THEN { worse than best solution,cut off }
    BEGIN
      cut_off:=TRUE; EXIT;
    END;
  END
  ELSE
  BEGIN
    IF objf>int_rec.upper_limit THEN { worse than best solution,cut off }
    BEGIN
      cut_off:=TRUE; EXIT;
    END;
  END;
  cut_off:=FALSE;
END;
{----------------------------------------------------------------------------}
PROCEDURE assess_pse(row:INTEGER; wert:DOUBLE; VAR up,down:SINGLE);
VAR i,j:INTEGER;
    pivv:DOUBLE;
BEGIN
  calc_rowing(row); up:=pseu_init; down:=pseu_init;
  FOR j:=r.nrows+1 TO nvar DO WITH co^[j]^ DO
  BEGIN
    {$IFDEF win32 }
    IF j MOD 10=0 THEN application.processmessages;
    {$ENDIF}
    pivv:=0;
    FOR i:=1 TO canzahl DO WITH cm^[i] DO pivv:=pivv+wert*pricing.feld_pointer^[z];
    IF on_upper=1 THEN pivv:=-pivv;
    IF ABS(pivv)>tolerance THEN IF on_upper*pivv>0
    THEN up  :=min(up  ,max(1e-5, dj/pivv))
    ELSE down:=min(down,max(1e-5,-dj/pivv));
  END;
END;
{----------------------------------------------------------------------------}
PROCEDURE add_merk(merk,nonint:INTEGER);
VAR w:DOUBLE;
BEGIN
  sol_int;
  WITH co^[merk]^ DO
  BEGIN
    CASE inttype OF
      1:{ INTEGER, SOS-1 }
      IF ABS(ROUND(float)-float)>integer_crit THEN
      BEGIN
        w:=INT(float);
        IF (float-w)*p_minus<(w+1-float)*p_plus THEN {n„her an unterem Limit }
        BEGIN
          make_row(cha,ROUND(w+1),nonint,lo_bound,FALSE,float);
          make_row(cha,ROUND(w  ),nonint,up_bound,TRUE ,float); INC(ne,2);
        END
        ELSE
        BEGIN
          make_row(cha,ROUND(w  ),nonint,up_bound,TRUE ,float);
          make_row(cha,ROUND(w+1),nonint,lo_bound,FALSE,float); INC(ne,2);
        END;
      END;
      2:{ Semi }
      IF (ABS(float)>integer_crit) AND (float<1-integer_crit) THEN
      BEGIN
        w:=float;
        IF w*p_minus<(1-w)*p_plus THEN
        BEGIN
          make_row(cha,1,nonint,lo_bound,FALSE,float);
          make_row(cha,0,nonint,up_bound,TRUE ,float); INC(ne,2);
        END
        ELSE
        BEGIN
          make_row(cha,0,nonint,up_bound,TRUE ,float);
          make_row(cha,1,nonint,lo_bound,FALSE,float); INC(ne,2);
        END;
      END;
    END;
  END;
END;
{----------------------------------------------------------------------------}
FUNCTION collect_stack:INTEGER;
VAR merk_null,merkeins,merk,i:INTEGER;
    deins_min,limit,d_null,d1,d2,d,d_min,w:DOUBLE;
    restore:BOOLEAN;
BEGIN
  ne:=0; nonint:=0; sol_int; restore:=FALSE;
  d_min    :=-1; deins_min:=-1; d_null:=-1;
  merk    :=0;   merkeins:=0;   merk_null:=-1;
  WITH int_rec DO IF stat_maximise THEN limit:=objf-lower_limit ELSE limit:=upper_limit-objf;
  FOR i:=1 TO r.nrows-1 DO WITH co^[i]^ DO
  BEGIN
    CASE inttype OF
      1:{ INTEGER, SOS-1 }
      IF ABS(ROUND(float)-float)>integer_crit THEN
      BEGIN
        IF int_rec.assess THEN IF (p_minus=pseu_init) AND (p_plus=pseu_init) THEN
        BEGIN
          assess_pse(i,trunc(float),p_plus,p_minus); restore:=TRUE;
        END
        ELSE restore:=false;
        w:=INT(float); INC(nonint); d1:=(float-w)*p_minus; d2:=(w+1-float)*p_plus;
        IF d1>d2 THEN swap_double(d1,d2);
        d:=d1*d2;
        IF d1<1e-5 THEN  { at least one of the two nodes is zero }
        BEGIN
          IF d2>d_null THEN
          BEGIN
            d_null:=d2; merk_null:=i;
          END;
        END
        ELSE IF d>d_min THEN
        BEGIN
          merk:=i; d_min:=d;
        END;
        IF d2>limit THEN IF d1>deins_min THEN
        BEGIN
          merkeins:=i; deins_min:=d1;
        END;
        IF restore THEN
        BEGIN
          p_plus:=pseu_init; p_minus:=pseu_init; restore:=FALSE;
        END;
      END;
      2:{ Semi and SOS-1}
      IF (ABS(float)>integer_crit) AND (float<1-integer_crit) THEN
      BEGIN
        IF int_rec.ASSESS THEN IF (p_minus=pseu_init) AND (p_plus=pseu_init) THEN
        BEGIN
          assess_pse(i,trunc(float),p_plus,p_minus); restore:=TRUE;
        END ELSE restore:=FALSE;
        w:=float; INC(nonint); d1:=w*p_minus; d2:=(1-w)*p_plus;
        IF d1>d2 THEN swap_double(d1,d2);
        d:=SQR(SQR(d1))*d2;
        IF d1<1e-5 THEN  { at least one of the two nodes is zero }
        BEGIN
          IF d2>d_null THEN
          BEGIN
            d_null:=d2; merk_null:=i;
          END;
        END
        ELSE IF d>d_min THEN
        BEGIN
          merk:=i; d_min:=d;
        END;
        IF d2>limit THEN IF d1>deins_min THEN
        BEGIN
          merkeins:=i; deins_min:=d1;
        END;
        IF restore THEN
        BEGIN
          p_plus:=pseu_init; p_minus:=pseu_init; restore:=FALSE;
        END;
      END;
    END;
  END;
  IF deins_min>0 THEN add_merk(merkeins,nonint)
  ELSE IF d_min>0 THEN add_merk(merk,nonint) ELSE add_merk(merk_null,nonint);
  collect_stack:=ne;
END;
{----------------------------------------------------------------------------}
PROCEDURE INTEGER_f;
VAR i:INTEGER;
BEGIN
  WITH int_rec DO
  BEGIN
    scr.win_out(koointfound,'INT. SOL. FOUND');
    SCr.noise;
    IF stat_maximise THEN lower_limit:=max(lower_limit,objf+integer_crit)
    ELSE                  upper_limit:=min(upper_limit,objf-integer_crit);
    int_status_out; store_solution; cut:=TRUE;
    STR(node:8,hilf_);
    scr.win_out(koototnodes,hilf_); scr.win_out(koohitnodes,hilf_);
    IF stat_with_log THEN
    BEGIN
      append_log_line('Searched '+ltoa(node)+' Open '+ltoa(int_rec.wtiefe+fil.ndatei)+
      ' time='+time_out(julia-t_start+elapsed)+' '+dtoa(int_rec.lower_limit)+' '+dtoa(nexx)+' '+dtoa(int_rec.upper_limit)+
      ' INT SOLUTION FOUND '+dtoa(objf));
    END;
    FOR i:=1 TO r.nrows DO co^[i]^.dj:=0;
    FOR i:=r.nrows+1 TO nvar DO WITH co^[i]^ DO
    BEGIN
      IF ABS(up_bound-lo_bound)<tolerance THEN on_upper:=-1;
      calc_dj(i);
    END;
    integer_found:=TRUE; lasthit:=node;
    FOR i:=1 TO nvar DO WITH co^[i]^ DO
    BEGIN
      int_value:=float; int_dj:=dj;
    END;
    IF scr.WHEREX>1 THEN scr.WRITELN('');
    flush_stack; scr.WRITELN('INTEGER SOLUTION FOUND');
  END;
END;
{----------------------------------------------------------------------------}
BEGIN
  IF stat_abbruch THEN
  BEGIN
    scr.WRITELN('OPTIMIZATION NOT YET FINISHED TOO MANY ITERATIONS');
    {$IFNDEF win32}
    IF NOT stat_command THEN wait;
    {$ENDIF}
    EXIT;
  END;
  such_tiefe_spec:=lp_par.get_int_par('SUCH-TIEFE');
  such_tiefe_max:=such_tiefe_spec*10; last_tiefe:=1; last_log:=0;
  such_tiefe_act:=such_tiefe_max;
  int_rec.max_memory:=2 SHL 29; time:=t_inv;
  put_basis(start_basis); dummy.ubas:=0; int_rec.node:=0; fil.open:=FALSE; fil.exist:=FALSE;
  scr.int_start;
  ok:=TRUE; last_change:=0; last_neu:=0; navg:=0; avg:=1;
  FOR ii:=1 TO nvar DO WITH co^[ii]^ DO IF inttype=1 THEN
  BEGIN
    IF up_bound>1e20 THEN
    BEGIN
      my_message('INTEGER VARIABLE '+spa_+' HAS NO UPPER BOUND');
      EXIT; ok:=FALSE;
    END
    ELSE
    BEGIN
      { insure that up_bound is INTEGER }
      IF up_bound<>ROUND(up_bound) THEN up_bound:=INT(up_bound);
      IF lo_bound<-1e20 THEN
      BEGIN
        my_message('INTEGER VARIABLE '+spa_+' HAS NO LOWER BOUND');
        ok:=FALSE; EXIT;
      END
      ELSE IF lo_bound<>ROUND(lo_bound) THEN lo_bound:=INT(lo_bound+1);
    END;
  END;
  FOR ii:=1 TO nvar DO WITH co^[ii]^ DO up_bound:=min(up_bound,1e19);
  IF stat_with_pse THEN lies_pse;
  stack:=NIL; fil.ndatei:=0; stat_strat3:=0;
  WITH int_rec DO
  BEGIN
    stat_integer_mode:=TRUE; vaterob:=objf; grund:=0; neeu:=0; obenstack:=0;
    fil.basis_length:=0; nexx:=objf; ngleich:=0; lasthit:=0;
    scr.win_out(kooneeu,'     ');
    IF integer_found THEN scr.win_out(koointfound,'INT. SOL. FOUND');
    IF stat_maximise THEN
    BEGIN
      upper_limit:=objf; lower_limit:=max(lower_limit,lo_limit);
    END
    ELSE
    BEGIN
      lower_limit:=objf; upper_limit:=min(upper_limit,up_limit);
    END;
    WITH wurzel DO
    BEGIN
      ob:=objf; nz:=0; zp:=NIL; b.bas:=NIL; b.ubas:=0;
    END;
    int_status_out; zaehler:=0;
    IF rest THEN GOTO 6;
    REPEAT
      IF stat_abbruch THEN
      BEGIN
        cancel; GOTO 2
      END;
      1:
      IF tiefe=0 THEN ob_vater:=objf;
      ob_start:=OBJF; vater_tiefe:=tiefe; coll:=collect_stack;
      IF coll=0 THEN
      BEGIN
        INTEGER_f; GOTO 5;
      END;
      ii:=tiefe-coll+1; vater:=tiefe-coll; active:=FALSE;
      IF vater=0 THEN
      BEGIN
        rest_base:=start_basis; vat:=objf;
      END
      ELSE WITH stack^[vater]^ DO
      BEGIN
        rest_base:=b; vat:=ob;
      END;
      WHILE ii<=tiefe DO WITH stack^[ii]^ DO
      BEGIN
        work_in(ii,1); cut:=cut_off;
        IF NOT cut THEN IF int_feasible THEN INTEGER_f;
        get_bound(zp^[1],sr);
        WITH sr DO
        BEGIN
          IF iterlog<>0 THEN WITH co^[hash^[ch]]^ DO
          BEGIN
            {$IFNDEF win32} SCR.WRITELN(''); {$ENDIF}
            IF bo THEN write_bounds(nonint,spa_,neu,'U',we)
            ELSE       write_bounds(nonint,spa_,neu,'L',we);
          END;
          { Calc pseudo costs }
          IF ninf=0 THEN
          BEGIN
            IF stat_with_pse THEN WITH co^[hash^[ch]]^ DO
            IF bo THEN calc_p_m(p_minus,we,neu)
                  ELSE calc_p_m(p_plus ,we,neu);
          END
          ELSE
          BEGIN
            WITH co^[hash^[ch]]^ DO
            IF bo THEN p_minus:=min(pseu_inf,max(1e3,p_minus*100.1)) ELSE p_plus:=min(pseu_inf,max(1e3,p_plus*100.1));
            scr.TEXTCOLOR(4);
            {$IFDEF win32}
            scr.sc_:=scr.sc_+' INFEASIBLE'; scr.writeln(scr.sc_);
            {$ELSE}
            scr.WRITELN('     INFEASIBLE');
            {$ENDIF}
            SCR.TEXTCOLOR(7);
          END;
          { END calc pseudo cost }
          IF cut THEN
          BEGIN
            IF ninf=0 THEN
            BEGIN
              scr.TEXTCOLOR(3);
              scr.sc_:=scr.sc_+ltoa_x(wurzel.nz,4)+ltoa_x(ii,4)+' '+ftoa_x(objf,14,6)+'  CUTOFF';
              scr.WRITELN(scr.sc_);
              scr.TEXTCOLOR(7);
            END;
            WITH b DO
            BEGIN
              ubas:=0; bas:=NIL;
            END;
            WITH stack^[ii]^ DO unbound(nz,zp);
            restore_basis(rest_base,vat); clear(stack^[ii]);
            FOR jj:=ii TO tiefe-1 DO stack^[jj]:=stack^[jj+1];
            stack^[tiefe]:=NIL; DEC(tiefe); DEC(coll); active:=FALSE;
          END
          ELSE
          BEGIN
            WITH stack^[ii]^ DO put_basis(b);
            scr.TEXTCOLOR(14);
            {$IFDEF win32}
            scr.sc_:=scr.sc_+ltoa_x(wurzel.nz,4)+ltoa_x(ii,4)+' '+ftoa_x(objf,14,6);
            scr.WRITELN(scr.sc_);
            {$ELSE}
            scr.WRITELN(ltoa_x(wurzel.nz,4)+ltoa_x(ii,4)+' '+ftoa_x(objf,14,6));
            {$ENDIF}
            scr.TEXTCOLOR(7);
            IF ii<tiefe THEN
            BEGIN
              WITH stack^[ii]^ DO unbound(nz,zp);
              restore_basis(rest_base,vat);
            END;
            INC(ii); active:=TRUE;
          END;
        END;
      END;
      IF coll>0 THEN
      BEGIN
        navg:=mini(1000,navg+1);
        avg:=((navg-1)*avg+ABS(objf-ob_start))/navg;
        IF NOT active THEN work_in(tiefe,2);
        best_ob:=stack^[tiefe]^.ob; ii:=tiefe-1; tausch:=FALSE; restauriert:=FALSE;
        WHILE ii>vater DO
        BEGIN
          IF (    stat_maximise) AND (best_ob>stack^[ii]^.ob-integer_crit) OR
          (   NOT stat_maximise) AND (best_ob<stack^[ii]^.ob+integer_crit) THEN
          BEGIN
            { Stack von i ist schlechter und wird ins wartende Set gestellt.}
            put_waiting(vater,ii);
            FOR jj:=ii TO tiefe-1 DO stack^[jj]:=stack^[jj+1];
            stack^[tiefe]:=NIL; DEC(tiefe);
          END
          ELSE
          BEGIN
            IF NOT restauriert THEN
            BEGIN
              WITH stack^[tiefe]^ DO unbound(nz,zp);
              restore_basis(rest_base,vat); restauriert:=TRUE;
            END;
            best_ob:=stack^[ii]^.ob; put_waiting(vater,tiefe);
            DEC(tiefe); tausch:=TRUE;
          END;
          DEC(ii);
        END;
        IF vater_tiefe>0 THEN WITH stack^[vater_tiefe]^ DO clear_basis(b);
        IF tausch THEN
        BEGIN
          INC(ninversion); { force reinversion after restore basis }
          work_in(tiefe,2);
        END;

        IF (such_tiefe_act<tiefe) OR (ABS(objf-ob_vater)>such_tiefe_act*avg) THEN
        BEGIN
          WITH stack^[tiefe]^ DO unbound(nz,zp);
          restore_basis(dummy,vat); put_waiting(vater,tiefe); DEC(tiefe); GOTO 5;
        END;
        
        { Run only until a node <> objf is found }
        {
        IF (ngleich>10) AND ((tiefe>such_tiefe_act) OR (ABS(objf-ob_vater)>1e-5)) THEN
        BEGIN
          WITH stack^[tiefe]^ DO unbound(nz,zp);
          restore_basis(dummy,vat); put_waiting(vater,tiefe); DEC(tiefe); GOTO 5;
        END;
        }
        GOTO 1;
      END;
      { start with a new node }
      5:
      IF (such_tiefe_act=such_tiefe_max) AND (ABS(objf-ob_vater)>1e-5) THEN
      BEGIN
        such_tiefe_act:=such_tiefe_spec; last_change:=node; last_tiefe:=tiefe;
      END
      {5*last_tiefe}
      ELSE IF node-last_change>5*last_tiefe THEN such_tiefe_act:=such_tiefe_max;
      FOR ii:=tiefe DOWNTO 1 DO WITH stack^[ii]^ DO
      BEGIN
        WITH stack^[ii]^ DO unbound(nz,zp);
        clear(stack^[ii]);
      END;
      clear_basis(start_basis); tiefe:=0;
      WITH wurzel DO
      BEGIN
        unbound(nz,zp); clear_row(zp,nz)
      END;
      IF stat_with_log THEN IF node-last_log>10000 THEN
      BEGIN
        last_log:=node;
        append_log_line('Searched '+ltoa(node)+' Open '+ltoa(int_rec.wtiefe+fil.ndatei)+
        ' time='+time_out(julia-t_start+elapsed)+' '+dtoa(int_rec.lower_limit)+' '+
        dtoa(nexx)+' '+dtoa(int_rec.upper_limit));
      END;
      tiefe:=0;
      {$IFNDEF win32}
      scr.win_out(koomaxa,ltoa_x(maxavail DIV 1024,6));
      {$ENDIF}
      IF stat_abbruch THEN
      BEGIN
        cancel; GOTO 2;
      END;
      IF stat_restart THEN IF neeu>20 THEN IF node-last_neu>3000 THEN
      BEGIN
        IF stat_with_pse THEN save_pse;
        neeu:=0; last_neu:=node; cancel; scr.win_out(kooneeu,'     ');
        load_basis(fname_); GOTO 1;
      END;
      IF stat_save THEN
      BEGIN
        stat_save:=FALSE; save_tree; GOTO 3;
      END;
      {$IFNDEF win32 }
      IF (maxavail<2000000) AND (memavail>2*maxavail) THEN
      BEGIN
        save_tree; load_tree; stat_save:=FALSE;
      END;
      {$ENDIF}
      6:
      IF rest THEN
      BEGIN
        load_tree; read_nodes(min_nodes DIV 5,fil); flush_stack; stat_save:=FALSE; rest:=FALSE;
      END;
      IF stat_flush THEN
      BEGIN
        flush_stack; stat_flush:=FALSE;
      END;
      IF (wtiefe<2) AND (fil.ndatei>0) THEN
      BEGIN
        IF scr.WHEREX>1 THEN scr.WRITELN('');
        read_nodes(2,fil);
        IF scr.WHEREX>1 THEN scr.WRITELN('');
      END;
      IF wtiefe>0 THEN
      BEGIN
        ngleich:=0; nexx:=ws_root^.st.ob; p:=ws_root^.next; minnz:=32000;
        WHILE p<>NIL DO
        IF ABS(p^.st.ob-nexx)<1e-3 THEN
        BEGIN
          INC(ngleich); p:=p^.next;
        END
        ELSE p:=NIL;
        DEC(wtiefe); wu:=ws_root; p:=wu; best_wu:=wu; wurzel:=wu^.st;
        INC(zaehler); int_status_out;
        IF {$IFDEF win32} (getheapstatus.totalallocated>maxmemory*1e6) OR {$ENDIF}(fil.ndatei>0)
        THEN stat_strat3:=1  { Abbau aller Nodes von hinten }
        ELSE stat_strat3:=0; { Abbau aller Nodes von vorn }
        IF ngleich>ROUND(wtiefe*0.9) THEN
        BEGIN
          { Zu viele gleiche Nodes: Abbau der gleichen Nodes von hinten}
          stat_strat1:=100; stat_strat2:=100;
        END
        ELSE
        BEGIN
          { Abbau der Nodes von vorn }
          stat_strat1:=0; stat_strat2:=100;
        END;
        IF stat_strat3=1 THEN
        BEGIN
          read_last_node;
          wu:=ws_end; p:=wu; best_wu:=wu; wurzel:=wu^.st;
        END
        ELSE
        IF zaehler<=stat_strat2 THEN
        BEGIN
          count:=0;
          IF ngleich=0 THEN wurzel:=wu^.st
          ELSE
          BEGIN
            IF zaehler<=stat_strat1 THEN
            BEGIN
              scr.WRITELN('Strat-1');
              minnz:=0;
              FOR ii:=1 TO ngleich DO
              BEGIN
                {}
                BEGIN
                  IF wu^.st.nz>minnz THEN
                  BEGIN
                    minnz:=wu^.st.nz; best_wu:=wu; wurzel:=wu^.st; p:=wu; count:=0;
                  END
                  ELSE IF wu^.st.nz=minnz THEN INC(count);
                END;
                wu:=wu^.next;
              END
            END
            ELSE
            BEGIN
              scr.WRITELN('Strat-2');
              minnz:=32000;
              FOR ii:=1 TO ngleich DO
              BEGIN
                IF wu^.st.nz<minnz THEN
                BEGIN
                  minnz:=wu^.st.nz; best_wu:=wu; wurzel:=wu^.st; p:=wu; count:=0;
                END
                ELSE IF wu^.st.nz=minnz THEN INC(count);
                wu:=wu^.next;
              END;
            END;
          END;
        END
        ELSE
        BEGIN
          scr.WRITELN('Strat-3');
          { minimum non-INTEGER }
          minnz:=32000;
          WHILE wu<>NIL DO
          BEGIN
            IF wu^.st.non_int<minnz THEN
            BEGIN
              minnz:=wu^.st.non_int; best_wu:=wu; wurzel:=wu^.st; p:=wu;
            END;
            wu:=wu^.next;
          END;
        END;
        IF zaehler>max_strat THEN zaehler:=0;
        start_basis:=wurzel.b;
        IF (minnz<>32000) AND (ngleich<>0) THEN
        scr.win_out(koominnz,ltoa(minnz)+' '+ltoa(count)+'      ');
        IF wtiefe=0 THEN
        BEGIN
          ws_root:=NIL; ws_end:=NIL;
        END
        ELSE
        BEGIN
          IF best_wu=ws_root THEN
          BEGIN
            ws_root^.next^.prev:=NIL; ws_root:=ws_root^.next;
          END
          ELSE IF best_wu=ws_end THEN
          BEGIN
            ws_end^.prev^.next:=NIL; ws_end:=ws_end^.prev;
          END
          ELSE
          BEGIN
            best_wu^.prev^.next:=best_wu^.next;
            best_wu^.next^.prev:=best_wu^.prev;
          END;
        END;
        IF p<>NIL THEN
        BEGIN
          DISPOSE(p); p:=NIL;
        END;
        WITH wurzel DO
        BEGIN
          inbound(nz,zp); scr.TEXTCOLOR(7);
          INC(ninversion); { force reinversion after restore basis }
          restore_basis(start_basis,ob);
        END;
        IF fil.ndatei>0 THEN IF wtiefe<max_memory DIV 2 THEN
        CASE grund OF
          0,1:
          BEGIN
            IF scr.WHEREX>1 THEN scr.WRITELN('');
            read_nodes(min_nodes DIV 5,fil);
            IF scr.WHEREX>1 THEN scr.WRITELN('');
          END;
          2,3:
          {$IFNDEF win32}
            {$IFDEF msdos} IF MEMAVAIL>64000 {$ELSE} IF MEMAVAIL>2300000 {$ENDIF} THEN
          {$ENDIF}
          BEGIN
            IF scr.WHEREX>1 THEN scr.WRITELN('');
            read_nodes(min_nodes DIV 5,fil);
            IF scr.WHEREX>1 THEN scr.WRITELN('');
          END;
        END;
        GOTO 1;
      END;
    UNTIL tiefe=0;
    2:
    scr.win_out(koowtiefe,ltoa_x(wtiefe+fil.ndatei,6));
    IF integer_found THEN
    BEGIN
      IF scr.WHEREX>1 THEN scr.WRITELN('');
      scr.writeln('INTEGER SOLUTION FOUND: Searched nodes '+ltoa(node));
      scr.writeln('ELAPSED TIME '+time_out(julia-t_start+elapsed)+' s');
      {$IFNDEF win32}
      IF NOT stat_command THEN wait;
      {$ENDIF}
      IF stat_abbruch THEN
      BEGIN
        my_message('OPTIMIZATION Cancelled'); GOTO 3;
      END;
    END
    ELSE
    BEGIN
      scr.WRITELN('INTEGERproblem is infeasible');
      scr.WRITELN('Iteration finished: Searched nodes '+ltoa(node));
      scr.WRITELN('ELAPSED TIME '+time_out(julia-t_start+elapsed)+' s');
      IF stat_abbruch THEN
      BEGIN
        my_message('OPTIMIZATION Cancelled'); GOTO 3;
      END;
    END;
    IF fil.exist THEN
    BEGIN
      zu(fil); loeschen(fil);
    END;
  END;
  3:
  {$IFDEF win32}
  scr.win_out(koo7,''); application.processmessages;
  {$ENDIF}
  IF stat_with_pse THEN save_pse;
  stat_integer_mode:=FALSE; clear_basis(start_basis); calc_rhs;
  WITH int_rec DO
  BEGIN
    done;
    IF stack<>NIL THEN
    BEGIN
      FOR ii:=1 TO obenstack DO IF stack^[ii]<>NIL THEN DISPOSE(stack^[ii]);
      FREEMEM(stack,obenstack*SIZEOF(POINTER)); stack:=NIL; tiefe:=0; obenstack:=0; fil.ndatei:=0;
    END;
  END;
  IF stat_with_log THEN
  BEGIN
    append_log_line('Iteration finished: Searched nodes '+ltoa(int_rec.node)+' time='+time_out(julia-t_start+elapsed));
    IF int_rec.integer_found THEN append_log_line('INT SOLUTION FOUND');
  END;
END;
END.
