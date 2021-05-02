UNIT lo4;
{$O+F+}
INTERFACE

USES {$ifdef msdos} OVERLAY, {$ENDIF}
     {$IFDEF win32}
       fernform,DIALOGS,FORMS,controls,
     {$ELSE}
        GRAPH,screen_u,helpunit,grafik,kopy,
     {$ENDIF}
     rahmen,fern_s,
     tty,util,lo3;

TYPE tlp4=OBJECT(tlp3)
             PROCEDURE iterate(save:BOOLEAN);
             PROCEDURE make_grafik1;
             PROCEDURE make_grafik(m:INTEGER);
             {$IFNDEF win32}
             PROCEDURE fahne(zeu,zeo,spu,spo:INTEGER);
             PROCEDURE dump_basis;
             {$ENDIF}
           END;

IMPLEMENTATION

{$IFDEF win32} USES lp_form; {$ENDIF}

VAR max_rec,number_rec,lauf,pivotz,pivotss,itype:INTEGER;
    last_nne:INTEGER;
    last_change:LONGINT;
    mittel,best,letzt_opt,minimum:DOUBLE;
    ready,problem:BOOLEAN;
    s_:STRIN;
    tim:DOUBLE;

{$IFNDEF win32}
PROCEDURE tlp4.fahne(zeu,zeo,spu,spo:INTEGER);
CONST font=2;
      size=4;
VAR charw,charh,i,k,su,so,zu,zo,maxx,maxy,nu:INTEGER;
    j:INTEGER;
    ch:CHAR;
    fp:TEXT;
    buf:^buffer;
BEGIN
  grafikm; maxx:=GETMAXX; maxy:=GETMAXY; SETTEXTJUSTIFY(0,0);
  SETTEXTSTYLE(font,0,size); SETVIEWPORT(0,0,maxx,maxy,TRUE); su:=spu;
  charw:=TEXTWIDTH('x'); charh:=TEXTHEIGHT('x');
  WHILE su<=spo DO
  BEGIN
    zu:=zeu; so:=mini(spo,su+maxx DIV charh-1-row_lmax);
    WHILE zu<=zeo DO
    BEGIN
      zo:=mini(zeo,zu+(maxy-col_lmax*charw) DIV charh-2);
      FOR i:=maxi(1,zu) TO zo DO
      BEGIN
        OUTTEXTXY(0                             ,2+charh*(i-zu)+charw*(col_lmax+2),r.ro^[i]^.row_);
        OUTTEXTXY(row_lmax*charw+charh*(so+5-su),2+charh*(i-zu)+charw*(col_lmax+2),ltoa(r.ro^[i]^.ranzahl));
      END;
      FOR j:=su TO so DO
      BEGIN
        IF j>0 THEN
        BEGIN
          SETTEXTSTYLE(font,1,size);
          OUTTEXTXY(charw*row_lmax+charh*(j-su)-charw DIV 2,charw*col_lmax,co^[j]^.spa_);
          SETTEXTSTYLE(font,0,size);
        END;
        FOR i:=zu TO zo DO
        CASE j OF
          -1:
          BEGIN
            IF i>0 THEN
            BEGIN
              IF      r.ro^[i]^.gleich=-1 THEN ch:='<'
              ELSE IF r.ro^[i]^.gleich= 0 THEN ch:='='
              ELSE IF r.ro^[i]^.gleich= 1 THEN ch:='>' ELSE ch:=' ';
              OUTTEXTXY(row_lmax*charw+charw*(j-su),2+charh*(i-zu)+charw*(col_lmax+2),ch);
            END;
          END;
          0:
          BEGIN
            IF i>0 THEN FOR k:=1 TO co^[0]^.canzahl DO IF co^[0]^.cm^[k].z=i THEN
            BEGIN
              IF co^[0]^.cm^[k].wert>0 THEN ch:='+' ELSE ch:='-';
              OUTTEXTXY(row_lmax*charw+charh*(j-su),2+charh*(i-zu)+charw*(col_lmax+2),ch);
            END;
          END;
          ELSE
          BEGIN
            IF i<1 THEN
            BEGIN
              IF co^[j]^.cost<>0 THEN OUTTEXTXY(row_lmax*charw+charh*(j-su),2+charh*i+charw*(col_lmax+2),'*');
            END
            ELSE FOR k:=1 TO co^[j]^.canzahl DO IF co^[j]^.cm^[k].z=i THEN
            BEGIN
              IF co^[j]^.cm^[k].wert>0 THEN ch:='+' ELSE ch:='-';
              OUTTEXTXY(row_lmax*charw+charh*(j-su),2+charh*(i-zu)+charw*(col_lmax+2),ch);
            END;
          END
        END;
      END;
      zu:=zo+1;
      IF zu<=zeo THEN
      BEGIN
        i:=tastatur;
        IF (i=ORD('h')) OR (i=ORD('H')) THEN
        BEGIN
          NEW(buf); ASSIGNFILE(fp,ps.destination_); SETTEXTBUF(fp,buf^);
          hcopy(fp,ps.drucker_,TRUE,TRUE); CLOSEFILE(fp); DISPOSE(buf);
        END;
        IF i=27 THEN
        BEGIN
          textm; EXIT;
        END;
        CLEARDEVICE;
      END;
    END;
    su:=so+1;
    IF so<=spo THEN
    BEGIN
      i:=tastatur;
      IF (i=ORD('h')) OR (i=ORD('H')) THEN
      BEGIN
          NEW(buf); ASSIGNFILE(fp,ps.destination_); SETTEXTBUF(fp,buf^);
          hcopy(fp,ps.drucker_,TRUE,TRUE); CLOSEFILE(fp); DISPOSE(buf);
      END;
      IF i=27 THEN
      BEGIN
        textm; EXIT;
      END;
      CLEARDEVICE;
    END;
  END;
  textm;
END;
{----------------------------------------------------------------------------}
PROCEDURE tlp4.dump_basis;
VAR unten,delta,ma,f,i,j:INTEGER;
    ch:CHAR;
BEGIN
  bild.save; grafikm; unten:=0; ma:=mini(r.nrows,GETMAXY);
  delta:=ROUND(r.nrows/ma*GETMAXX);
  REPEAT
    FOR j:=1 TO mini(delta,nvar-unten) DO WITH co^[j+unten]^ DO
    FOR i:=1 TO canzahl DO WITH cm^[i] DO
    BEGIN
      IF wert=0 THEN f:=1 ELSE f:=ROUND(log10(ABS(wert*1e6)));
      PUTPIXEL(ROUND(j/r.nrows*ma),ROUND(z/r.nrows*ma),f);
    END;
    INC(unten,delta); ch:=scr.READKEY; CLEARDEVICE;
  UNTIL (ch=#27) OR (unten>nvar);
  textm; bild.restore;
END;
{---------------------------------------------------------------------------}
{$ENDIF}
PROCEDURE tlp4.make_grafik1;
CONST ma=10000;
TYPE feld=array [0..ma] OF SINGLE;
     feld_pointer=^feld;
VAR i:INTEGER;
    x,y:feld_pointer;
    f:fernseh_type;
    {$IFNDEF win32}
    s:bildschirm;
    {$ENDIF}
    pss:wstack_pointer;
    pd:datei_row_pointer;
    z:DOUBLE;
    m:LONGINT;
BEGIN
  {$R+}
  IF stat_ohne_fahne THEN EXIT;
  WITH int_rec DO
  BEGIN
    IF wtiefe+fil.ndatei=0 THEN EXIT;
    {$IFDEF win32}
    tfernform.visible:=TRUE;
    {$ELSE}
    s.init; s.save; grafikm;
    {$ENDIF}
    GETMEM(x,LONGINT(ma+1)*SIZEOF(x^[1])); GETMEM(y,LONGINT(ma+1)*SIZEOF(y^[1]));
    WITH f DO
    BEGIN
      anfang:=1; xlow:=0; xhigh:=wtiefe+fil.ndatei; skaliere(xlow,xhigh,xtick);
      ylow:=ws_root^.st.ob;
      IF fil.ndatei=0 THEN yhigh:=ws_end^.st.ob ELSE yhigh:=fil.datei_end^.o;
      IF yhigh<ylow THEN swap_single(ylow,yhigh);
      skaliere(ylow,yhigh,ytick); quad:=0;
      xtext_:='Tiefe'; ytext_:='OBJF OF WAITING NODES'; titel_:='Open Nodes to search';
    END;
    f.kontrol_:='F'; pss:=ws_root; pd:=fil.datei_root; m:=0;
    REPEAT
      f.ende:=0;
      WHILE (f.ende<ma) AND (m<fil.ndatei+wtiefe) DO
      BEGIN
        INC(m); INC(f.ende); x^[f.ende]:=m;
        IF pss<>NIL THEN
        BEGIN
          y^[f.ende]:=pss^.st.ob; pss:=pss^.NEXT;
        END
        ELSE
        BEGIN
          y^[f.ende]:=pd^.o; pd:=pd^.NEXT;
        END;
      END;
      fernseh(x^,y^,f); f.kontrol_:='';
    UNTIL m>=fil.ndatei+wtiefe;
    z:=julia;
    {$IFDEF win32}
    fernform.pressed:=FALSE;
    REPEAT application.processmessages UNTIL(julia-z>10) OR fernform.pressed;
    {$ELSE}
    REPEAT UNTIL scr.KEYPRESSED OR (julia-z>10);
    IF scr.KEYPRESSED THEN i:=tastatur;
    textm;
    {$ENDIF}
    FREEMEM(x,LONGINT(ma+1)*SIZEOF(x^[1])); FREEMEM(y,LONGINT(ma+1)*SIZEOF(y^[1]));
    {$IFDEF win32}
    tfernform.visible:=FALSE;
    {$ELSE}
    s.restore; s.done;
    {$ENDIF}
    {$R-}
  END;
END;
{----------------------------------------------------------------------------}
PROCEDURE tlp4.make_grafik(m:INTEGER);
CONST {$IFDEF win32} maxgrafik=100000; {$ELSE} maxgrafik=16000; {$ENDIF}
TYPE feld=array [0..maxgrafik] OF SINGLE;
     feld_pointer=^feld;
VAR I:INTEGER;
    x,y:feld_pointer;
    f:fernseh_type;
    {$IFNDEF win32}
    s:bildschirm;
    {$ENDIF}
    p:wstack_pointer;
    z:DOUBLE;
BEGIN
  m:=LONGINT(mini(m,int_rec.wtiefe));
  IF m=0 THEN EXIT;
  IF stat_ohne_fahne THEN EXIT;
  {$IFDEF win32}
  tfernform.visible:=TRUE;
  {$ELSE}
  s.init; s.save;
  {$ENDIF}
  GETMEM(x,SIZEOF(x^[1])*LONGINT(m+1)); GETMEM(y,SIZEOF(y^[1])*LONGINT(m+1));
  {$IFNDEF win32}
  grafikm;
  {$ENDIF}
  WITH f DO
  BEGIN
    anfang:=0; ende:=m; quad:=0;
    xtext_:='Tiefe'; ytext_:='OBJF OF WAITING NODES'; titel_:='Open Nodes to search';
  END;
  f.kontrol_:='FA';
  p:=ws_root; y^[0]:=p^.st.ob; x^[0]:=0;
  FOR i:=1 TO m DO
  BEGIN
    x^[i]:=i; y^[i]:=p^.st.ob;
    IF p^.next<>NIL THEN p:=p^.next ELSE
    BEGIN
      m:=i; BREAK;
    END;
  END;
  fernseh(x^,y^,f);
  p:=ws_root; y^[0]:=p^.st.nz; x^[0]:=0;
  FOR i:=1 TO m DO
  BEGIn
    x^[i]:=i; y^[i]:=p^.st.nz;
    IF p^.next<>NIL THEN p:=p^.next ELSE
    BEGIN
      m:=i; BREAK;
    END;
  END;
  f.kontrol_:='AN3S1'; fernseh(x^,y^,f);
  {$IFNDEF win32}
  outtextxy(600,400,ftoa(f.ylow)); outtextxy(100,50,ftoa(f.yhigh));
  {$ENDIF}
  p:=ws_root; y^[0]:=p^.st.non_int; x^[0]:=0;
  FOR i:=1 TO m DO
  BEGIN
    x^[i]:=i; y^[i]:=p^.st.non_int;
    IF p^.next<>NIL THEN p:=p^.next ELSE
    BEGIN
      m:=i; BREAK;
    END;
  END;
  f.kontrol_:='WAN10S1'; fernseh(x^,y^,f);
  {$IFNDEF win32}
  outtextxy(600,410,ftoa(f.ylow)); outtextxy(100,40,ftoa(f.yhigh));
  {$ENDIF}
  {$IFDEF win32}
  tfernform.visible:=FALSE;
  {$ELSE}
  textm;
  {$ENDIF}
  FREEMEM(x,sizeof(x^[1])*LONGINT(m+1)); FREEMEM(y,sizeof(y^[1])*LONGINT(m+1));
  {$IFNDEF win32}
  s.restore; s.done;
  {$ENDIF}
END;
{----------------------------------------------------------------------------}
PROCEDURE tlp4.iterate(save:BOOLEAN);
CONST miniter=1e-6;   { lower bound for pivot element of a good iteration }
VAR cycling,ende,ngut,ndurch,i,j,pivots:INTEGER;
    fertig:BOOLEAN;
    z,best_pivot:DOUBLE;
    test:lang_int_feld_pointer;
    ch:CHAR;
    log_line_:string[79];
LABEL 1,4;
PROCEDURE prepare_log_line;
VAR lg1:INTEGER;
BEGIN
  scr.TEXTCOLOR(lightgray);
  IF LENGTH(log_line_)<50 THEN
  BEGIN
    IF objf=0 THEN lg:=0 ELSE
    lg:=maxi(0,ROUND(INT(log10(ABS(objf)))));
    IF inf=0 THEN lg1:=0 ELSE
    lg1:=maxi(0,ROUND(INT(log10(ABS(inf)))));
    CASE itype OF
      1,2: log_line_:=log_line_+' '+COPY(laenge(co^[pivotz ]^.spa_,11,' '),1,11)+
           COPY(laenge(co^[pivotss]^.spa_,11,' '),1,11);
      3:   log_line_:=log_line_+' '+COPY(laenge(co^[pivotss]^.spa_,11,' '),1,11)+laenge('to upper',11,' ');
      4:   log_line_:=log_line_+' '+COPY(laenge(co^[pivotss]^.spa_,11,' '),1,11)+laenge('to lower',11,' ');
    END;
    log_line_:=log_line_+ftoa_x(objf,14,14-3-lg)
    +ftoa_x(inf,14,14-3-lg1)+ltoa_x(nit,6)
    +ftoa_x(julia-t_start+elapsed,8,1);
    log_line_:=log_line_+ltoa_x(ninversion,4);
    scr.win_out(koominpiv,laenge(ftoa(min_piv),11,' '));
    scr.win_out(kooninf  ,laenge(ltoa(ninf)+' '+ltoa(lp_etha.netha)+' '+ltoa(lp_etha.nelemen),19,' '));
  END;
  {$IFDEF win32}
  IF stat_INTEGER_mode THEN
  BEGIN
    IF iterlog>0 THEN IF nit MOD iterlog=0 THEN scr.writeln(log_line_);
  END
  ELSE scr.writeln(log_line_);
  {$ELSE}
  scr.GOTOXY(1,scr.WHEREY); scr.WRITE(log_line_); scr.GOTOXY(2,scr.WHEREY);
  IF iterlog>0 THEN
  IF nit MOD iterlog=0 THEN scr.WRITELN('');
  {$ENDIF}
END;
{---------------------------------------------------------------------------}
PROCEDURE show_dj(dj:DOUBLE);
VAR lg:INTEGER;
BEGIN
  IF dj=0 THEN lg:=-1 ELSE lg:=ROUND(INT(log10(ABS(dj))));
  IF lg>0 THEN STR(dj:10:7-lg,log_line_) ELSE STR(dj:10:7,log_line_);
END;
{----------------------------------------------------------------------------}
FUNCTION check_feasibility:BOOLEAN;
VAR i,j:INTEGER;
    bound:DOUBLE;
BEGIN
  inf:=0; check_Feasibility:=FALSE;
  FOR i:=ninf DOWNTO 1 DO
  BEGIN
    j:=inf_table^[i];
    WITH co^[j]^ DO
    BEGIN
      bound:=rhs.feld_pointer^[j]-up_bound;
      IF bound>tolerance THEN { Wert zu hoch } inf:=inf+bound
      ELSE
      BEGIN
        bound:=lo_bound-rhs.feld_pointer^[j];
        IF bound>tolerance THEN { Wert ist zu tief } inf:=inf+bound
        ELSE
        BEGIN
          on_upper:=0; inf_table^[i]:=inf_table^[ninf]; DEC(ninf); check_Feasibility:=TRUE;
        END;
      END;
    END;
  END;
  IF inf<inv_limit THEN
  BEGIN
    FOR j:=1 TO ninf DO co^[inf_table^[j]]^.on_upper:=0;
    inf:=0; ninf:=0;
  END;
END;
{---------------------------------------------------------------------------}
FUNCTION oben(o,u:DOUBLE; on_upper:SHORTINT):DOUBLE;
BEGIN
  CASE on_upper OF
    0: { feasible }
    BEGIN
      oben:=o; EXIT;
    END;
    1: { zu hoch }
    BEGIN
      oben:=1e20; EXIT;
    END;
    -1: { zu tief } IF o<1e20 THEN oben:=o ELSE oben:=u;
  END;
END;
{----------------------------------------------------------------------------}
FUNCTION unten(u:DOUBLE; on_upper:INTEGER):DOUBLE;
BEGIN
  IF on_upper=-1 THEN unten:=-1e20 ELSE unten:=u;
END;
{----------------------------------------------------------------------------}
PROCEDURE react(j:INTEGER);
VAR l1,i,k:INTEGER;
    s_:STRIN;
BEGIN
  CASE itype OF
    1,2: { Basischange }
    WITH co^[pivotz]^ DO
    BEGIN
      INC(letzt_inv);
      IF itype=1 THEN
      BEGIN
        best:=rhs.feld_pointer^[pivotz]-unten(lo_bound,on_upper);
        { best:=rhs^[pivotz]-cola^[pivotz]^.lo_bound; }
        co^[pivotz]^.on_upper:=-1; { Neue Nicht-Basis-Variable on lower Bound }
      END
      ELSE
      BEGIN
        best:=rhs.feld_pointer^[pivotz]-oben(up_bound,lo_bound,on_upper);
        WITH co^[pivotz]^ DO
        BEGIN
          on_upper:=1;  { Neue Nicht-Basis-Variable on upper Bound }
          IF up_bound>1e20 THEN on_upper:=-1;
        END;
      END;
      IF ABS(best)<tolerance THEN rhs.feld_pointer^[pivotz]:=0 ELSE
      BEGIN
        best:=best/vvector.feld_pointer^[pivotz];
        FOR i:=1 TO r.nrows DO rhs.feld_pointer^[i]:=rhs.feld_pointer^[i]-best*vvector.feld_pointer^[i];
        rhs.feld_pointer^[pivotz]:=best;
      END;
      { correct RHS for values of entering variable }
      WITH co^[j]^ DO
      IF on_upper=1 THEN rhs.feld_pointer^[pivotz]:=rhs.feld_pointer^[pivotz]+up_bound
      ELSE               rhs.feld_pointer^[pivotz]:=rhs.feld_pointer^[pivotz]+lo_bound;
      { bei sehr kleinem Pivot element dann check Feasibility }
      IF ABS(pivot_el)<1e-6 THEN
      BEGIN
        ninf:=0;
        FOR i:=1 TO r.nrows-1 DO IF co^[i]^.art<4 THEN
        BEGIN
          INC(ninf); inf_table^[ninf]:=i;
        END;
      END;
      objf:=-rhs.feld_pointer^[r.nrows]; store_etha(vvector.feld_pointer^,pivotz,1,r.nrows);
      { an dieser Stelle calc rowing }
      swap_columns(j,pivotz);
      { neue basis Variable }
      WITH co^[pivotz]^ DO
      BEGIN
        on_upper:=0; dj:=0;
        IF art=5 THEN
        BEGIN
          up_bound:=up_bound_lmt; lo_bound:=-up_bound_lmt;
        END;
      END;
    END;
    3,4: WITH co^[j]^ DO { only boundchange }
    BEGIN
      IF itype=3 THEN
      BEGIN
        best:=up_bound-lo_bound; on_upper:=1;
      END
      ELSE
      BEGIN
        best:=lo_bound-up_bound; on_upper:=-1;
      END;
      FOR i:=1 TO r.nrows DO rhs.feld_pointer^[i]:=rhs.feld_pointer^[i]-best*vvector.feld_pointer^[i];
      objf:=-rhs.feld_pointer^[r.nrows];
    END;
  END;
  IF ABS(best)>tolerance THEN IF ABS(vvector.feld_pointer^[r.nrows])>tol100 THEN
  BEGIN
    last_change:=nit; lauf:=0;
  END
  ELSE FOR i:=1 TO ninf DO IF ABS(vvector.feld_pointer^[inf_table^[i]])>tol100 THEN
  BEGIN
    last_change:=nit; lauf:=0;
  END;
  { At numerical problems small Pivot-Element }
  IF ABS(pivot_el)<1e-6 THEN
  BEGIN
    ninf:=0;
    FOR i:=1 TO r.nrows-1 DO IF co^[i]^.art<4 THEN
    BEGIN
      INC(ninf); inf_table^[ninf]:=i;
    END;
  END;
  IF check_feasibility OR (itype<3) THEN calc_pricing;
  IF ready THEN stat_abbruch:=TRUE;
  IF iterlog>0 THEN prepare_log_line;
  tim:=tim-t_letzt_inv;
  IF ((letzt_inv>=50) AND (tim>3*t_inv))
  {$IFNDEF win32} OR (SIZEOF(waag)*LONGINT(r.nrows)>MAXAVAIL) {$ENDIF}
  OR (lp_etha.netha>=lp_etha.maxetha) THEN
  BEGIN
    IF iterlog=0 THEN
    BEGIN
      IF NOT stat_integer_mode THEN prepare_log_line;
    END;
    inversion; nne:=0;
    IF basis_intervall>0 THEN IF nit-last_save>basis_intervall THEN save_basis(FALSE);
    {$IFNDEF win32}
    IF NOT stat_integer_mode THEN scr.GOTOXY(1,scr.WHEREY);
    {$ENDIF}
  END;
END;
{----------------------------------------------------------------------------}
PROCEDURE select_pivot_row(VAR j:INTEGER);
VAR nbe,i,k:INTEGER;
    FAC:SHORTINT;
    piv,x:DOUBLE;
    ch:CHAR;
    s_:STRIN;
BEGIN
  WITH co^[j]^ DO
  BEGIN
    IF on_upper=1 THEN
    BEGIN
      fac:=-1; itype:=4; pivot_el:=1;
    END
    ELSE
    BEGIN
      fac:=1; itype:=3; pivot_el:=1;
    END;
    best:=up_bound-lo_bound; pivotz:=0; piv:=0;
  END;
  FOR i:=1 TO r.nrows-1 DO WITH co^[i]^ DO IF art<4 THEN IF ABS(vvector.feld_pointer^[i])>tolerance THEN
  IF fac*vvector.feld_pointer^[i]>0 THEN
  BEGIN
    x:=fac*(rhs.feld_pointer^[i]-unten(lo_bound,on_upper))/vvector.feld_pointer^[i];
    IF ABS(ABS(x)-best)<tolerance THEN { gleich gut }
    BEGIN
      IF ABS(vvector.feld_pointer^[i])>piv THEN
      BEGIN
        pivotz:=i; itype:=1; piv:=ABS(vvector.feld_pointer^[i]);
      END;
    END
    ELSE IF ABS(x)<best+tolerance THEN
    BEGIN
      best:=ABS(x); piv:=ABS(vvector.feld_pointer^[i]); pivotz:=i; itype:=1;
    END
  END
  ELSE
  BEGIN
    x:=fac*(rhs.feld_pointer^[i]-oben(up_bound,lo_bound,on_upper))/vvector.feld_pointer^[i];
    IF ABS(ABS(x)-best)<tolerance THEN { gleich gut }
    BEGIN
      IF ABS(vvector.feld_pointer^[i])>piv THEN
      BEGIN
        pivotz:=i; itype:=2; piv:=ABS(vvector.feld_pointer^[i]);
      END;
    END
    ELSE IF ABS(x)<best+tolerance THEN
    BEGIN
      best:=ABS(x); piv:=ABS(vvector.feld_pointer^[i]); pivotz:=i; itype:=2;
    END
  END;
  WITH co^[j]^ DO
  BEGIN
    IF best>1e20 THEN { Keine Bregrenzung durch Bound oder ROW: unbounded }
    BEGIN
      IF on_upper=1 THEN { To lower bound }
      BEGIN
        itype:=4; pivot_el:=1; ch:=' ';
        IF lo_bound<-1e20 THEN
        BEGIN
          scr.WRITE('VARIABLE '+spa_+' is unbounded !');
          IF stat_with_log THEN append_log_line('Variable '+spa_+' is unbounded');
          IF stat_command THEN
          BEGIN
            stat_unbounded:=TRUE; itype:=5;
          END
          ELSE
          BEGIN
            {$IFDEF win32}
            IF MessageDlg('VARIABLE '+spa_+' is unbounded ! CHANGE lower Bound ? (Y/N) ',mtcustom,mbOkCancel,0)=mrOK THEN
            ch:='Y' ELSE ch:='N';
            {$ELSE}
            WRITE('VARIABLE ',spa_,' is unbounded ! CHANGE lower Bound ? (Y/N) ');
            ch:=scr.READKEY;
            {$ENDIF}
            IF (ch='Y') OR (ch='y') THEN
            BEGIN
              {$IFDEF win32}
              WITH LpForm DO
              BEGIN
                memo1.hide;
                edit2.enabled:=TRUE; edit2.visible:=TRUE; label27.enabled:=TRUE;
                label27.caption:='Enter Lo-Bound of Variable '+spa_;
                edit2.setfocus; edit2.text:='';
                edit_ready:=FALSE; edit_valid:=FALSE;
              END;
              REPEAT application.processmessages; UNTIL edit_ready;
              lpform.memo1.show; s_:=lpform.edit2.text;
              IF edit_valid THEN
              BEGIN
                lo_bound:=lese_zahl(s_); stat_unbounded:=FALSE; itype:=4;
              END;
              LpForm.label27.enabled:=FALSE;
              {$ELSE}
              WRITELN; WRITE('  LOWER bound '); READLN(lo_bound);
              stat_unbounded:=FALSE; itype:=4;
              {$ENDIF}
            END
            ELSE
            BEGIN
              stat_unbounded:=TRUE; itype:=5;
            END;
          END;
        END;
      END
      ELSE { To upper bound }
      BEGIN
        itype:=3; pivot_el:=1; ch:=' ';
        IF up_bound>1e20 THEN
        BEGIN
          scr.WRITE('VARIABLE '+spa_+' is unbounded !');
          IF stat_with_log THEN append_log_line('Variable '+spa_+' is unbounded');
          IF stat_command THEN
          BEGIN
            stat_unbounded:=TRUE; itype:=5;
          END
          ELSE
          BEGIN
            {$IFDEF win32}
            IF MessageDlg('VARIABLE '+spa_+' is unbounded ! CHANGE upper Bound ? (Y/N) ',mtcustom,mbOkCancel,0)=mrOK THEN
            ch:='Y' ELSE ch:='N';
            {$ELSE}
            WRITE('VARIABLE ',spa_,' is unbounded ! CHANGE upper Bound ? (Y/N) ');
            ch:=scr.READKEY;
            {$ENDIF}
            IF (ch='Y') OR (ch='y') THEN
            BEGIN
              {$IFDEF win32}
              WITH LpForm DO
              BEGIN
                memo1.hide;
                edit2.enabled:=TRUE; edit2.visible:=TRUE; label27.enabled:=TRUE;
                label27.caption:='Enter Up-Bound of Variable '+spa_;
                edit2.setfocus; edit2.text:='';
                edit_ready:=FALSE; edit_valid:=FALSE;
              END;
              REPEAT application.processmessages; UNTIL edit_ready;
              s_:=lpform.edit2.text;
              IF edit_valid THEN
              BEGIN
                up_bound:=lese_zahl(s_); stat_unbounded:=FALSE; itype:=3;
              END;
              LpForm.label27.enabled:=FALSE; lpform.memo1.show;
              {$ELSE}
              WRITELN; WRITE('  UPPER bound '); READLN(up_bound);
              stat_unbounded:=FALSE; itype:=3;
              {$ENDIF}
            END
            ELSE
            BEGIN
              stat_unbounded:=TRUE; itype:=5;
            END;
          END;
        END
      END;
    END;
    IF pivotz>0 THEN pivot_el:=ABS(vvector.feld_pointer^[pivotz]);
  END;
END;
{----------------------------------------------------------------------------}
PROCEDURE search_negativ(anfang:INTEGER; VAR ende:INTEGER);
VAR o,j:INTEGER;
BEGIN
  nne:=0; minimum:=0; j:=anfang;
  WHILE (j<=nvar) AND (nne<npricing) DO
  BEGIN
    WITH co^[j]^ DO IF (up_bound-lo_bound)>tolerance THEN
    BEGIN
      calc_dj(j);
      IF allowed THEN minimum:=min(dj,minimum);
      IF dj<dj_min THEN
      BEGIN
        swap_columns(j,anfang+nne); INC(nne);
      END;
    END;
    INC(j);
  END;
  ende:=j; mittel:=0;
  FILLCHAR(zehn,SIZEOF(zehn),0);
  IF nne>0 THEN
  BEGIN
    {
    IF nit-last_change<30 THEN
    BEGIN
      IF intervall>0 THEN IF ninversion MOD intervall<>0 THEN
      FOR j:=anfang TO anfang+nne-1 DO WITH cola^[j]^ DO IF dj<dj_min THEN dj:=dj/canzahl;
    END;
    }
    qsort_dj(anfang,anfang+nne-1);
    o:=mini(nzehn,nne);
    FOR j:=1 TO o DO WITH co^[j-1+anfang]^ DO
    BEGIN
      IF allowed THEN zehn[j]:=dj;
      mittel:=mittel+zehn[j]/o;
    END;
    IF stat_integer_mode THEN
    BEGIN
      IF nit-last_nne>20 THEN
      BEGIN
        scr.win_out(koonne,laenge(ltoa(nne),5,' '));
        last_nne:=nit;
      END;
    END
    ELSE scr.win_out(koonne,laenge(ltoa(nne),5,' '));
  END;
END;
{----------------------------------------------------------------------------}
BEGIN
  IF NOT stat_geladen THEN EXIT;
  calc_pricing; mittel:=-1e8; problem:=FALSE; pivotz:=0; pivotss:=0;
  letzt_opt:=1e30; ngut:=0; ready:=FALSE; cycling:=0; log_line_:='';
  number_rec:=0; lauf:=0; last_change:=nit; itype:=0; last_nne:=nit; stat_help_index:=5;
  1:
  REPEAT
    fertig:=TRUE; ndurch:=0; pivots:=r.nrows+1;
    WHILE pivots<=nvar DO
    BEGIN
      search_negativ(pivots,ende);
      IF pivots=r.nrows+1 THEN IF nne<npricing THEN IF co^[pivots]^.dj>-100 THEN
      IF ninf>0 THEN IF with_eps=0 THEN GOTO 4;
      IF nne>0 THEN
      BEGIN
        j:=pivots;
        WHILE j<=pivots+nne-1 DO WITH co^[j]^ DO
        BEGIN
          IF (up_bound-lo_bound)>tolerance THEN
          BEGIN
            calc_dj(j); pivotss:=j;
            IF dj<dj_min THEN IF dj<mittel*0.95 THEN
            BEGIN
              get_vector(j,vvector.feld_pointer^);
              ftran(vvector.feld_pointer^,1,lp_etha.netha); show_dj(dj);
              select_pivot_row(j); INC(nit);
              tim:=julia;
              IF (nit>maxiter) OR (tim-t_start+elapsed>max_time) THEN ready:=TRUE;
              IF ABS(pivot_el)>=miniter THEN
              BEGIN
                MOVE(zehn[2],zehn[1],(nzehn-1)*SIZEOF(zehn[1]));
                zehn[nzehn]:=dj/canzahl; mittel:=0; INC(ngut);
                IF ngut>50 THEN
                BEGIN
                  IF problem THEN FOR i:=1 TO nvar DO co^[i]^.allowed:=TRUE;
                  ngut:=0; problem:=FALSE;
                END;
                FOR i:=1 TO nzehn DO mittel:=mittel+0.95*zehn[i]/nzehn;
                react(j); fertig:=FALSE; ndurch:=0;
                IF stat_unbounded THEN EXIT;
              END
              ELSE
              BEGIN
                allowed:=FALSE; INC(ndurch);
                IF NOT stat_integer_mode THEN
                {$IFDEF win32}
                lpform.appendmemo('.');
                {$ELSE}
                scr.WRITE('.');
                {$ENDIF}
                IF ndurch>obendurch THEN
                BEGIN
                  GETMEM(test,(obendurch+10)  *SIZEOF(INTEGER));
                  MOVE(idurch^,test^,obendurch*SIZEOF(INTEGER));
                  FREEMEM(idurch,    obendurch*SIZEOF(INTEGER));
                  idurch:=test; INC(obendurch,10);
                END;
                idurch^[ndurch]:=j; problem:=TRUE;
              END;
            END;
            IF nit MOD 10=0 THEN
            {$IFDEF win32}
            application.processmessages;
            IF stat_togg THEN
            BEGIN
              CASE with_eps OF
                0: toggle_epsilon(1);
                1: toggle_epsilon(2);
                2: toggle_epsilon(0);
              END;
              stat_togg:=FALSE;
            END;
            IF stat_grafikm THEN
            BEGIN
              make_grafik(100); stat_grafikm:=FALSE;
            END;
            IF stat_grafikn THEN
            BEGIN
              make_grafik(100000); stat_grafikn:=FALSE;
            END;
            IF stat_grafiko THEN
            BEGIN
              make_grafik1; stat_grafiko:=FALSE;
            END;
            IF stat_abbruch THEN
            BEGIN
              IF stat_with_log THEN append_log_line('Solution cancelled');
              EXIT;
            END;
            {$ELSE}
            WHILE scr.KEYPRESSED DO
            BEGIN
              itype:=tastatur;
              CASE itype OF
                27: { ESC }
                BEGIN
                  z:=julia; ch:=' '; WRITE('BREAK (Y/N)');
                  REPEAT
                    IF scr.keypressed THEN ch:=scr.READKEY;
                  UNTIL (ch IN ['n','N','y','Y']) OR (julia-z>10);
                  IF (ch='y') OR (ch='Y') THEN
                  BEGIN
                    stat_abbruch:=TRUE; stat_user_abbruch:=FALSE;
                    IF stat_with_log THEN append_log_line('SOLUTION CANCELLED');
                    EXIT;
                  END;
                END;
                { c,C }{ 67,99:  IF stat_integer_mode THEN IF int_rec.ndatei>0 THEN stat_compress:=TRUE; }
                68,100: { d,D } IF      iterlog=0 THEN iterlog:=1
                                ELSE IF iterlog=1 THEN iterlog:=10000
                                ELSE                   iterlog:=0;
                {$IFNDEF spar}
                55: { 7 :ERHOEHE STRATEGIE 1 }
                BEGIN
                  stat_strat1:=mini(max_strat,stat_strat1+2);
                  stat_strat2:=mini(max_strat,maxi(stat_strat1,stat_strat2));
                  WRITELN(stat_strat1,' ',stat_strat2);
                END;
                56: { 8 :ERHOEHE STRATEGIE 2 }
                BEGIN
                  stat_strat2:=mini(max_strat,stat_strat2+1);
                  stat_strat1:=maxi( 0,mini(stat_strat1-1,stat_strat2));
                  WRITELN(stat_strat1,' ',stat_strat2);
                END;
                57: { 9 :ERHOEHE STRATEGIE 3 }
                BEGIN
                  stat_strat2:=maxi(0,stat_strat2-1);
                  stat_strat1:=maxi(0,mini(stat_strat1,stat_strat2));
                  WRITELN(stat_strat1,' ',stat_strat2);
                END;
                45: { - } npricing:=maxi(1,npricing div 2);
                43: { + }
                BEGIN
                  npricing:=mini(30000,LONGINT(npricing)*2); nne:=0;
                END;
                69,101: { e,E }
                BEGIN
                  bild.save; scr.WINDOW(1,1,80,25);
                  scr.CLRSCR; edit_parameters; bild.restore; stat_flush:=TRUE;
                END;
                70,102: { f,F } stat_flush:=TRUE;
                76,108: { l,L } stat_strat3:=1-stat_strat3;
                77,109: { m,M } IF stat_integer_mode THEN make_grafik(100);
                78,110: { n,N } IF stat_integer_mode THEN make_grafik(16000);
                79,111: { o,O } IF stat_integer_mode THEN make_grafik1;
                {$ENDIF}
                83,115: { s,S  } stat_save:=TRUE;
                84,116: { t,T  }
                CASE with_eps OF
                  0: toggle_epsilon(1);
                  1: toggle_epsilon(2);
                  2: toggle_epsilon(0);
                END;
                87,119: { w,W }
                IF stat_integer_mode THEN WITH int_rec DO
                IF wtiefe>110 THEN max_memory:=wtiefe-100 ELSE max_memory:=10;
                88,120: { x,X }
                IF stat_integer_mode THEN INC(int_rec.max_memory,100);
                89,121: { y,Y }
                WITH scr DO set_output(NOT with_output);
                {$IFNDEF win32}
                315: { F1 }
                BEGIN
                  bild.save; he.help(help_,ps.destination_+ps.init_,29);
                  scr.WINDOW(1,6,80,25); bild.restore;
                END;
                {$ENDIF}
              END;
            END;
            {$ENDIF}
          END;
          INC(j);
        END;
      END;
      pivots:=ende;
    END;
    IF nit-last_change>30 THEN
    BEGIn
      INC(lauf); last_change:=nit;
    END;
    IF lauf>2 THEN
    BEGIN
      IF cycling>10 THEN
      BEGIN
        scr.WRITELN('Numerical Problems'); stat_numerisch:=TRUE; GOTO 4;
      END;
      INC(cycling);
      CASE with_eps OF
        0,1:
        BEGIN
          toggle_epsilon(2); npricing:=32;
        END;
        2: toggle_epsilon(0);
      END;
      lauf:=0;
    END;
    IF stat_integer_mode THEN
    BEGIN
      search_negativ(r.nrows+1,ende);
      IF nne>0 then if nne<10 THEN IF inf>1e-5 THEN
      IF co^[r.nrows+1]^.dj>-100 THEN
      IF with_eps<>0 THEN
      BEGIN
        toggle_epsilon(0); GOTO 1;
      END
      ELSE IF stat_integer_mode THEN GOTO 4;
    END;
  UNTIL fertig OR stat_abbruch;
  IF stat_abbruch THEN exit;
  IF ndurch>0 THEN { keine column mit gutem Pivotelement gefunden }
  BEGIN
    best_pivot:=0;
    FOR i:=1 TO ndurch DO WITH co^[idurch^[i]]^ DO
    BEGIN
      get_vector(idurch^[i],vvector.feld_pointer^);
      ftran(vvector.feld_pointer^,1,lp_etha.netha);
      select_pivot_row(idurch^[i]);
      IF ABS(pivot_el)>best_pivot THEN
      BEGIN
        best_pivot:=ABS(pivot_el); pivots:=idurch^[i];
        IF NOT stat_integer_mode THEN
        BEGIN
          IF scr.WHEREX>1 THEN scr.WRITELN('');
          scr.WRITELN('PIVOT PROBLEM: BEST PIVOT '+spa_+' '+ftoa(best_pivot));
        END;
      END;
    END;
    IF best_pivot>0 THEN WITH co^[pivots]^ DO
    BEGIN
      pivotss:=pivots; calc_dj(pivots);
      get_vector(pivots,vvector.feld_pointer^);
      ftran(vvector.feld_pointer^,1,lp_etha.netha);
      show_dj(dj); select_pivot_row(pivots);
      react(pivots);
      IF stat_unbounded THEN EXIT;
      GOTO 1;
    END
    ELSE
    BEGIN
      IF letzt_inv>3 THEN
      BEGIN
        inversion; nne:=0;
        IF basis_intervall>0 THEN IF nit-last_save>basis_intervall THEN save_basis(FALSE);
        GOTO 1;
      END
      ELSE
      BEGIN
        scr.WRITELN('Numerical Problems: Iteration stopped.'); stat_numerisch:=TRUE;
        {$IFNDEF win32}
        IF NOT stat_command THEN wait;
        {$ENDIF}
        EXIT;
      END;
    END;
  END;
  IF (mittel<dj_min) OR (nne>0) THEN
  BEGIN
    IF problem THEN mittel:=dj_min ELSE mittel:=min(dj_min,minimum/(max(1.01,2*0.95)));
    FOR i:=1 TO nzehn DO zehn[i]:=mittel;
    GOTO 1;
  END;
  IF with_eps>0 THEN
  BEGIN
    toggle_epsilon(0); GOTO 1;
  END;
  IF letzt_inv>1 THEN IF (ninf>0) OR (min_piv<miniter) THEN
  BEGIN
    inversion; nne:=0;
    IF basis_intervall>0 THEN IF nit-last_save>basis_intervall THEN save_basis(FALSE);
    GOTO 1;
  END;
  fertig:=TRUE;
  IF stat_mit_frei THEN FOR j:=r.nrows+1 TO nvar DO WITH co^[j]^ DO
  IF art=5 THEN IF (dj>tolerance) AND (on_upper=-1) THEN
  BEGIN
    fertig:=FALSE;
    IF lo_bound=0 THEN lo_bound:=-100 ELSE lo_bound:=-100*ABS(lo_bound);
  END;
  IF NOT fertig THEN
  BEGIN
    calc_rhs; goto 1;
  END;
  4:
  IF iterlog=0 THEN
  BEGIN
    IF NOT stat_integer_mode THEN prepare_log_line;
  END;
  scr.win_out(koonne,laenge(ltoa(nne),5,' '));
  check_feasibility_inversion; calc_pricing;
  IF NOT stat_integer_mode THEN
  BEGIN
    IF ninf>0 THEN
    BEGIN
      scr.TEXTCOLOR(red); s_:='PROBLEM IS INFEASIBLE'
    END
    ELSE
    IF stat_unbounded THEN
    BEGIN
      scr.TEXTCOLOR(magenta); s_:='PROBLEM IS UNBOUNDED'
    END
    ELSE
    BEGIN
      scr.TEXTCOLOR(yellow); s_:='OPTIMAL SOLUTION';
    END;
    IF scr.WHEREX>1 THEN scr.WRITELN('');
    IF ninf>0 THEN scr.WRITELN(s_+' nINF='+ltoa(ninf)+' INF='+ftoa(inf))
    ELSE scr.WRITELN(s_+' '+ftoa_x(objf,14,6));
    scr.WRITELN('Elapsed Time ' +time_out(julia-t_start+elapsed));
    IF stat_abbruch THEN scr.WRITELN('UNFINISHED');
  END;
  IF save THEN store_solution;
  scr.TEXTCOLOR(lightgray);
END;
{----------------------------------------------------------------------------}
END.
UNIT male_fahne;
{$F+,O+}
INTERFACE
USES {$IFDEF msdos } OVERLAY, {$ENDIF}
     {$IFNDEF win32}
       CRT,GRAPH,screen_u,egavgadri,smallfont,kopy,
     {$ENDIF}
     util,grafik,lp_varia;

PROCEDURE fahne(zeu,zeo,spu,spo:INTEGER);
PROCEDURE dump_basis;
PROCEDURE close_gr;

IMPLEMENTATION

VAR driver:INTEGER;

END.