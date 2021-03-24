UNIT go_lp;
{$O+,F+}
INTERFACE

USES {$IFDEF msdos} OVERLAY, {$ENDIF}
     {$IFDEF win32} FORMS, DIALOGS,
     {$ELSE} CRT,GRAPH,grafik,smallfont,egavgadri,DOS,dir,screen_u,zeige,helpunit,
     {$ENDIF}
     tty,lo1,lo2,lo3,lo4,lo5,util;

TYPE tlp6=OBJECT(tlp5)
            {$IFNDEF win32}
            PROCEDURE title;
            {$ENDIF}
            PROCEDURE start_load(s_:STRIN);
            PROCEDURE output_status;
            PROCEDURE EXECUTE(cmd_:STRIN);
            PROCEDURE write_log;
            PROCEDURE go(cmd_:STRIN);
            PROCEDURE set_max;
            PROCEDURE set_min;
            PROCEDURE solve_lp;
            PROCEDURE make_rest;
            PROCEDURE global;
            PROCEDURE load_tree;
          END;

VAR memory:LONGINT;
    driver:INTEGER;

IMPLEMENTATION

{$IFDEF win32} USES lp_form; {$ENDIF}

PROCEDURE tlp6.load_tree;
BEGIN
  IF ninversion=1 THEN crash ELSE inversion;
  t_start:=julia;
  {$IFDEF win32}
  LpForm.edit2.visible:=FALSE;
  {$ELSE}
  title;
  {$ENDIF}
  stat_abbruch:=FALSE; stat_user_abbruch:=FALSE; iterate(FALSE);
  IF NOT stat_abbruch THEN
  BEGIN
    save_basis(TRUE);
    IF (ninf=0) AND (nne=0) AND NOT stat_unbounded THEN INTEGER_search(TRUE);
  END;
  elapsed:=elapsed+julia-t_start;
END;
{----------------------------------------------------------------------------}
PROCEDURE tlp6.write_log;
VAR fp:TEXT;
BEGIN
  ASSIGNFILE(fp,start_path_+'\LP.LOG');
  {$I-} APPEND(fp); {$I+}
  IF IORESULT<>0 THEN { file doesn't exist } REWRITE(fp);
  WRITE(fp,laenge(fname_,35,' '),objf,nit:8,laenge_V(time_out(julia-t_start+elapsed),12,' '));
  WRITE(fp,lp_etha.netha:6,lp_etha.nelemen:8);
  IF stat_unbounded THEN WRITE(fp,' UNBOUNDED ');
  IF stat_numerisch THEN WRITE(fp,' NUMERICAL PROBLEMS ');
  IF ninf>0 THEN WRITE(fp,' INFEASIBLE ',ninf,' ',inf);
  WRITELN(fp); CLOSEFILE(fp);
END;
{----------------------------------------------------------------------------}
PROCEDURE tlp6.output_status;
VAR nint,nbin,nsemi,i:INTEGER;
    {$IFNDEF win32}
    s:bildschirm;
    {$ENDIF}
BEGIN
  objf:=-rhs.feld_pointer^[r.nrows];
  {$IFDEF win32}
  LpForm.addmemo('S T A T U S    O F   S O L U T I O N');
  LpForm.addmemo('');
  IF stat_maximise THEN LpForm.addmemo('MAXIMISATION') ELSE LpForm.addmemo('MINIMISATION');
  LpForm.addmemo('NAME OF OBJECTIVE FUNCTION : '+objf_);
  IF stat_unbounded THEN   LpForm.addmemo('UNBOUNDED')
  ELSE IF ninf>0 THEN LpForm.addmemo('INFEASIBLE ninf='+ltoa(ninf)+' SUM OF INFEASIBILITIES='+dtoa(inf))
  ELSE IF nne >0 THEN LpForm.addmemo('FEASIBLE   OBJF='+ftoa_x(objf,14,10))
  ELSE                LpForm.addmemo('OPTIMAL    OBJF='+ftoa_x(objf,14,10));
  IF with_eps>0 THEN LpForm.addmemo('MATRIX CONTAINS EPSILON PERTURBATION ON RHS '+ftoa(eps));
  LpForm.addmemo('INVERTED MATRIX CONTAINS '+ltoa(lp_etha.netha)+' ETHA-VECTORS WITH '+ltoa(lp_etha.nelemen)+' ENTRIES');
  nint:=0; nbin:=0; nsemi:=0;
  FOR i:=1 TO nvar DO WITH co^[i]^ DO
  CASE inttype OF
    1: IF (up_bound=1) AND (up_bound-lo_bound=1) THEN INC(nbin) ELSE INC(nint);
    2: INC(nsemi);
  END;
  LpForm.addmemo('INTEGER='+ltoa(nint)+' BINARY='+ltoa(nbin));
  LpForm.addmemo('SEMI='+ltoa(nsemi));
  LpForm.addmemo('Free Memory='+ltoa(getheapstatus.totalallocated)+' Bytes');
  LpForm.addmemo('Number of Inversions : '+ltoa(ninversion));
  LpForm.addmemo('LAST INVERSION NEEDED '+ftoa(t_inv)+' s');
  LpForm.addmemo('');
  {$ELSE}
  s.init; s.save; WRITELN;
  WRITELN('S T A T U S    O F   S O L U T I O N'#10#13);
  WRITELN;
  IF stat_maximise THEN WRITELN('MAXIMISATION') ELSE WRITELN('MINIMISATION');
  WRITELN('NAME OF OBJECTIVE FUNCTION : ',objf_);
  IF stat_unbounded THEN   WRITELN('UNBOUNDED')
  ELSE IF ninf>0 THEN WRITELN('INFEASIBLE ninf=',ninf,' SUM OF INFEASIBILITIES=',dtoa(inf))
  ELSE IF nne>0 THEN WRITELN('FEASIBLE   OBJF=',objf:14:6)
  ELSE                WRITELN('OPTIMAL    OBJF=',objf:14:11);
  IF with_eps>0 THEN WRITELN('MATRIX CONTAINS EPSILON PERTURBATION ON RHS ',ftoa(eps));
  WRITELN(r.nrows,' ROWS ',nvar,' COLUMNS');
  WRITELN('INVERTED MATRIX CONTAINS ',lp_etha.netha,' ETHA-VECTORS WITH ',lp_etha.nelemen,' ENTRIES');
  WRITELN(r.nrows,' ROWS ',nvar-r.nrows,' STRUCTURAL VARIABLES');
  nint:=0; nbin:=0; nsemi:=0;
  FOR i:=1 TO nvar DO WITH co^[i]^ DO
  CASE inttype OF
    1: IF (up_bound=1) AND (up_bound-lo_bound=1) THEN INC(nbin) ELSE INC(nint);
    2: INC(nsemi);
  END;
  WRITELN('INTEGER=',nint,' BINARY=',nbin);
  WRITELN('Free Memory=',MEMAVAIL,' Bytes');
  WRITELN('Number of Inversions : ',ninversion);
  WRITELN('LAST INVERSION NEEDED ',ftoa(t_inv),' s');
  WRITELN;
  IF NOT stat_command THEN wait;
  s.restore; s.done;
  {$ENDIF}
END;
{----------------------------------------------------------------------------}
PROCEDURE tlp6.make_rest;
BEGIN
  IF stat_geladen THEN
  BEGIN
    aufbau;
    {$IFDEF win32}
    lpform.clear_label;
    scr.writeln('');
    {$ELSE}
    title;
    {$ENDIF}
    scr.WRITELN('Loadingtime : '+ftoa_x(julia-t_start,0,2)+' s');
    output_status;
    {$IFNDEF win32}
    scr.WINDOW(1,6,80,25); scr.GOTOXY(1,1);
    {$ENDIF}
  END;
END;
{----------------------------------------------------------------------------}
PROCEDURE tlp6.start_load(s_:STRIN);
BEGIN
  IF stat_geladen THEN new_model;
  {$IFNDEF win32}
  WINDOW(1,1,80,25); CLRSCR; path_:=s_;
  directory(path_,fname_); CLRSCR; WRITELN(fname_);
  {$ENDIF}
  {$IFNDEF spar}
  IF POS('.LP' , fname_)<>0 THEN lies_lp ELSE
  {$ENDIF}
  IF POS('.MAT', fname_)<>0 THEN lies_mpsx;
  make_rest;
END;
{----------------------------------------------------------------------------}
{$IFNDEF win32}
PROCEDURE tlp6.title;
VAR i:INTEGER;
BEGIN
  i:=maxi(WHEREY,6); WINDOW(1,1,80,25);
  GOTOXY(55,1); WRITE('MEMORY:');
  GOTOXY(55,2); WRITE('MINPIV:'); GOTOXY(1,5);
  WRITELN('    DJ     Vector_in  Vector_out     OBJF       INFEASIBILITY   nit  Time  ninv');
  WINDOW(1,6,80,25); GOTOXY(1,i);
END;
{$ENDIF}
{----------------------------------------------------------------------------}
PROCEDURE tlp6.global;
BEGIN
  IF NOT stat_geladen THEN EXIT;
  IF ninversion=1 THEN crash;
  {$IFDEF win32}
  LpForm.edit2.visible:=FALSE;
  {$ELSE}
  title;
  {$ENDIF}
  t_start:=julia; stat_abbruch:=FALSE; iterate(TRUE);
  IF NOT stat_abbruch THEN
  IF ninf=0 THEN
  BEGIN
    delete_infeasible;
    IF NOT stat_abbruch   THEN save_basis(TRUE);
    IF NOT stat_unbounded THEN integer_search(FALSE);
  END
  ELSE save_infeasible;
  elapsed:=elapsed+julia-t_start;
END;
{----------------------------------------------------------------------------}
PROCEDURE tlp6.EXECUTE(cmd_:STRIN);
VAR i:INTEGER;
    s_,s2_,s3_,s1_:STRIN;
    fp:TEXT;
    ch:CHAR;
LABEL 1;
BEGIN
  IF POS('.',cmd_)=0 THEN cmd_:=cmd_+'.CMD';
  ASSIGNFILE(fp,cmd_); {$I-} RESET(fp); {$I+}
  IF IORESULT=0 THEN
  BEGIN
    stat_command:=TRUE;
    WHILE NOT EOF(fp) DO
    BEGIN
      IF stat_user_abbruch THEN
      BEGIN
        {$IFDEF win32}
          IF MessageDlg('Cancel Batch (Y/N) ',mtCustom,mbOkCancel,0)=1 THEN GOTO 1;
          stat_user_abbruch:=FALSE;
        {$ELSE}
          WRITE('Cancel Batch (Y/N) '); ch:=READKEY;
          IF ch in ['y','Y','j','J'] THEN GOTO 1;
          stat_user_abbruch:=FALSE;
        {$ENDIF}
      END;
      READLN(fp,s_); s_:=ucase(rtrim(ltrim(s_)));
      IF s_<>'' THEN
      BEGIN
        zerhacke(s1_,s_); zerhacke(s2_,s_); zerhacke(s3_,s_);
        IF s1_='OBJF'  THEN
        BEGIN
          IF s2_<>'' THEN objf_ :=s2_;
        END
        ELSE IF s1_='RHS'   THEN
        BEGIN
          IF s2_<>'' THEN rhs_  :=s2_;
        END
        ELSE IF s1_='BOUND' THEN
        BEGIN
          IF s2_<>'' THEN bound_:=s2_;
        END
        ELSE IF s1_='NEW' THEN
        BEGIN
          IF stat_geladen THEN new_model;
        END
        ELSE IF s1_='RANGE' THEN
        BEGIN
          IF s2_<>'' THEN range_:=s2_;
        END
        ELSE IF s1_='LOADMATRIXFORMATTED' THEN
        BEGIN
          fname_:=s2_;
          IF POS('.',fname_)=0 THEN fname_:=fname_+'.MAT';
          {$IFNDEF win32}
          WINDOW(1,1,80,25); CLRSCR;
          {$ENDIF}
          lies_mpsx_komplett;
          make_rest;
          {$IFNDEF win32}
          title; GOTOXY(1,1); bild.save;
          {$ENDIF}
        END
        ELSE IF s1_='LOADMATRIX' THEN
        BEGIN
          fname_:=s2_;
          IF POS('.',fname_)=0 THEN fname_:=fname_+'.MAT';
          {$IFNDEF win32}
          WINDOW(1,1,80,25); CLRSCR;
          {$ENDIF}
          lies_mpsx; make_rest;
          {$IFDEF win32}
          lpform.label1.caption:=fname_;
          {$ELSE}
          title; GOTOXY(1,1); bild.save;
          {$ENDIF}
        END
        {$IFNDEF spar}
        ELSE IF s1_='LOADLP' THEN
        BEGIN
          fname_:=s2_;
          IF fname_='' THEN start_load('*.LP') ELSE
          BEGIN
            IF POS('.',fname_)=0 THEN fname_:=fname_+'.LP';
            {$IFNDEF win32}
            WINDOW(1,1,80,25); GOTOXY(1,5);
            {$ENDIF}
            lies_lp; make_rest;
            {$IFDEF win32}
            lpform.label1.caption:=fname_;
            {$ELSE}
            title; GOTOXY(1,1); bild.save;
            {$ENDIF}
          END;
          {$IFNDEF win32}
          title; GOTOXY(1,1); bild.save;
          {$ENDIF}
        END
        {$ENDIF}
        ELSE IF s1_='LOADBASIS' THEN
        BEGIN
          {$IFNDEF win32}
          WINDOW(1,1,80,25); GOTOXY(1,6); bild.save;
          {$ENDIF}
          IF s2_='' THEN
          BEGIN
            {$IFDEF win32}
            LpForm.opendialog1.filename:='*.BSS';
            IF LpForm.opendialog1.execute THEN s2_:=LpForm.opendialog1.filename;
            {$ELSE}
            path_:='*.BSS'; directory(path_,s2_); CLRSCR;
            {$ENDIF}
          END
          ELSE IF POS('.', s2_)=0 THEN s2_:=s2_+'.BSS';
          IF POS('.BSS', s2_)<>0 THEN load_basis(s2_);
          {$IFNDEF win32}
          WINDOW(1,6,80,25); bild.restore;
          {$ENDIF}
        END
        ELSE IF s1_='LOADTREE' THEN load_tree
        {$IFNDEF spar}
        ELSE IF s1_='SAVELP'     THEN save_lp
        ELSE IF s1_='SAVEXPRESS' THEN save_xpress
        ELSE IF s1_='SAVEMAT'    THEN save_mpsx
        {$ENDIF}
        ELSE IF (s1_='MAXIMISE') OR (s1_='MINIMISE')   THEN
        BEGIN
          IF stat_geladen THEN
          BEGIN
            IF s2_<>'' THEN
            BEGIN
              objf_:=s2_; i:=r.search_row(objf_);
              IF i<>0 THEN swap_rows(i,r.nrows);
              build_objective_function;
            END;
            {$IFNDEF win32}
            bild.save;
            {$ENDIF}
            IF s1_='MAXIMISE' THEN stat_maximise:=TRUE ELSE stat_maximise:=FALSE;
            stat_unbounded:=FALSE; build_objective_function;
            {$IFNDEF win32}
            bild.restore;
            {$ENDIF}
            IF ninversion=1 THEN crash;
            {$IFDEF win32}
            LpForm.edit2.visible:=FALSE;
            lpform.mnuCancel.enabled:=TRUE;
            {$ELSE}
            title;
            {$ENDIF}
            iterate(TRUE); elapsed:=elapsed+julia-t_start;
            IF stat_with_log THEN write_log;
            IF NOT stat_abbruch THEN
            {$IFDEF win32}
            LpForm.addmemo('CONTINEOUS SOLUTION FOUND');
            {$ELSE}
            WRITELN('CONTINEOUS SOLUTION FOUND');
            {$ENDIF}
          END;
        END
        ELSE IF s1_='CUTOFF' THEN
        BEGIN
          IF s2_='' THEN
          BEGIN
            IF stat_maximise THEN lo_limit:=objf-1e20 ELSE up_limit:=objf+1e20;
          END
          ELSE
          BEGIN
            IF stat_maximise THEN
            BEGIN
              lo_limit:=objf-lese_zahl(s2_);
              {$IFDEF win32} with LpForm DO {$ENDIF} lp_par.put_float_par('LO-LIMIT',lo_limit);
            END
            ELSE
            BEGIN
              up_limit:=objf+lese_zahl(s2_);
              {$IFDEF win32} with LpForm DO {$ENDIF} lp_par.put_float_par('UP-LIMIT',lo_limit);
            END;
          END;
        END
        ELSE IF s1_='LIMIT' THEN
        BEGIN
          IF s2_='' THEN
          BEGIN
            lo_limit:=-1e30; up_limit:=1e30;
          END
          ELSE
          BEGIN
            IF stat_maximise THEN
            BEGIN
              lo_limit:=lese_zahl(s2_);
              {$IFDEF win32} with LpForm DO {$ENDIF} lp_par.put_float_par('LO-LIMIT',lo_limit);
            END
            ELSE
            BEGIN
              up_limit:=lese_zahl(s2_);
              {$IFDEF win32} with LpForm DO {$ENDIF} lp_par.put_float_par('UP-LIMIT',up_limit);
            END;
          END;
        END
        ELSE IF s1_='MAXITER' THEN
        BEGIN
          IF (s2_<>'') AND (wieviel_int(s2_)=LENGTH(s2_)) THEN
          BEGIN
            maxiter:=lese_int(s2_);
            {$IFDEF win32} with LpForm DO {$ENDIF} lp_par.put_int_par('MAXITER',maxiter);
          END
          ELSE my_message('ILLEGAL PARAMETER IN COMMAND FILE '+s2_);
        END
        ELSE IF s1_='TIGHT' THEN
        BEGIN
          {$IFDEF win32} with LpForm DO {$ENDIF} lp_par.put_bool_par('TIGHT',TRUE);
        END
        ELSE IF s1_='UNTIGHT' THEN
        BEGIN
          {$IFDEF win32} with LpForm DO {$ENDIF} lp_par.put_bool_par('TIGHT',FALSE);
        END
        ELSE IF s1_='SUCH' THEN
        BEGIN
          IF (s2_<>'') AND (wieviel_int(s2_)=LENGTH(s2_)) THEN
          BEGIN
            max_time:=lese_int(s2_);
            {$IFDEF win32} with LpForm DO {$ENDIF} lp_par.put_int_par('SUCH-TIEFE',max_time);
          END
          ELSE my_message('ILLEGAL PARAMETER IN COMMAND FILE '+s2_);
        END
        ELSE IF s1_='MAXTIME' THEN
        BEGIN
          IF (s2_<>'') AND (wieviel_int(s2_)=LENGTH(s2_)) THEN
          BEGIN
            max_time:=lese_int(s2_);
            {$IFDEF win32} with LpForm DO {$ENDIF} lp_par.put_int_par('MAXTIME',max_time);
          END
          ELSE my_message('ILLEGAL PARAMETER IN COMMAND FILE '+s2_);
        END
        ELSE IF s1_='DJ-MIN' THEN
        BEGIN
          IF (s2_<>'') AND (wieviel_float(s2_)=LENGTH(s2_)) THEN
          BEGIN
            dj_min:=min(-1e-10,lese_zahl(s2_));
            {$IFDEF win32} with LpForm DO {$ENDIF} lp_par.put_float_par('DJ-MIN',dj_min);
          END
          ELSE my_message('ILLEGAL PARAMETER IN COMMAND FILE '+s2_);
        END
        ELSE IF s1_='GLOBAL'       THEN global
        ELSE IF s1_='SAVEBASIS'    THEN save_basis(TRUE)
        ELSE IF s1_='SAVESOLUTION' THEN loesung
        ELSE IF s1_='SAVEINTEGER'  THEN
        int_loesung
        ELSE IF s1_='INVERT'       THEN
        BEGIN
          IF stat_geladen THEN inversion
        END
        ELSE IF s1_='LOG'     THEN
        {$IFDEF win32} with LpForm DO {$ENDIF} lp_par.put_bool_par('LOGFILE',TRUE)
        ELSE IF s1_='UNLOG'   THEN
        {$IFDEF win32} with LpForm DO {$ENDIF} lp_par.put_bool_par('LOGFILE',FALSE)
        ELSE IF s1_='WRITELOG'     THEN
        BEGIN
          IF stat_geladen THEN WRITE_log
        END
        ELSE IF s1_='EXIT'         THEN
        BEGIN
          new_model; CLOSEFILE(fp); stat_command:=FALSE; EXIT;
        END
        ELSE IF s1_='QUIT'         THEN
        BEGIN
          quit; CLOSEFILE(fp); stat_command:=FALSE;
          IF stat_from_command_line THEN
          BEGIN
            {$IFDEF fahne} close_gr; {$ENDIF}
            {$IFDEF win32} Application.Terminate; {$ENDIF}
            HALT;
          END
          ELSE EXIT;
        END
        ELSE my_message('ILLEGAL COMMAND IN COMMAND FILE '+s1_);
      END
    END;
    1: CLOSEFILE(fp); stat_command:=FALSE; stat_from_command_line:=FALSE;
  END;
END;
{----------------------------------------------------------------------------}
PROCEDURE tlp6.set_max;
BEGIN
  IF stat_geladen THEN
  BEGIN
    IF NOT stat_built THEN aufbau;
    stat_maximise:=TRUE; stat_unbounded:=FALSE; build_objective_function;
  END;
END;
{----------------------------------------------------------------------------}
PROCEDURE tlp6.set_min;
BEGIN
  IF stat_geladen THEN
  BEGIN
    IF NOT stat_built THEN aufbau;
    stat_maximise:=FALSE; stat_unbounded:=FALSE; build_objective_function;
  END;
END;
{----------------------------------------------------------------------------}
PROCEDURE tlp6.solve_lp;
BEGIN
  t_start:=julia;
  IF ninversion=1 THEN crash;
  {$IFDEF win32}
  LpForm.edit2.visible:=FALSE;
  {$ELSE}
  title;
  {$ENDIF}
  stat_abbruch:=FALSE; iterate(TRUE);
  elapsed:=elapsed+julia-t_start; stat_abbruch:=FALSE;
  {$IFNDEF win32} IF NOT stat_command THEN wait; {$ENDIF}
END;
{----------------------------------------------------------------------------}
PROCEDURE tlp6.go(cmd_:STRIN);
VAR baum:INTEGER;
BEGIN;
  {$IFNDEF win32}
  IF paramcount>0 THEN stat_from_command_line:=TRUE;
  IF cmd_<>'' THEN EXECUTE(cmd_)
  ELSE
  REPEAT
    bild.save; WINDOW(1,1,80,25);
    baum:=menu.zeige_menu; bild.restore; stat_abbruch:=FALSE;
    CASE baum OF
      1: set_max;
      2: set_min;
      5: { } start_load('*.MAT');
      55: { load formatted mpsx file }
      BEGIN
        IF stat_geladen THEN new_model;
        {$IFDEF win32}
        WRITE('Filename : '); READLN(fname_);
        {$ELSE}
        WINDOW(1,1,80,25); CLRSCR; path_:='*.mat';
        directory(path_,fname_); CLRSCR;
        {$ENDIF}
        WRITELN(fname_); t_start:=julia;
        IF POS('.MAT', fname_)<>0 THEN
        BEGIN
          lies_mpsx_komplett; make_rest;
        END;
      END;
      6: { load basis }
      BEGIN
        bild.save; WINDOW(1,1,80,25); CLRSCR;
        path_:='*.BSS';
        {$IFDEF win32}
        WRITE('BASBS : '); READLN(basis_);
        IF POS('.BSS',basis_)=0 THEN basis_:=basis_+'.BSS';
        {$ELSE}
        directory(path_,basis_);
        {$ENDIF}
        CLRSCR;
        IF POS('.BSS', basis_)<>0 THEN load_basis(basis_);
        bild.restore;
        {$IFDEF win32}
        LpForm.label4.caption:=laenge(ltoa(ninf)+' '+ltoa(netha)+' '+ltoa(nelemen),19,' ');
        {$ELSE}
        scr.win_out(kooninf,laenge(ltoa(ninf)+' '+ltoa(lp_etha.netha)+' '+ltoa(lp_etha.nelemen),19,' '));
        {$ENDIF}
      END;
      7: save_basis(TRUE);
      8: loesung;
      11: solve_lp;
      12: inversion;
      13: toggle_epsilon(NOT with_eps);
      {$IFNDEF spar}
      3: { select objf }
      IF stat_geladen THEN
      BEGIN
        bild.save; WINDOW(1,1,80,25); select_objective;
        bild.restore;
      END;
      4: start_load('*.LP');
      9: save_lp;
      10: save_mpsx;
      18: edit_mat('');
      25: { edit parameter }
      BEGIN
        bild.save; WINDOW(1,1,80,25); CLRSCR; edit_parameters;
        bild.restore;
      END;
      27: crash;
      {$IFNDEF spar}
      29: { show infeasible vectors }
      BEGIN
        bild.save; WINDOW(1,1,80,25); CLRSCR; show_infeasible;
        bild.restore;
      END;
      42: { defin eproblem, OBJF, BOUND, RHS, RANGE}
      BEGIN
        bild.save; WINDOW(1,1,80,25);
        define_problem;     bild.restore;
      END;
      50: save_xpress;
      99: { produce demystified solution }
      BEGIN
        bild.save; WINDOW(1,1,80,25); CLRSCR; demyst;
        bild.restore;
      END;
      {$IFNDEF fahne}
      15: { produce picture of matrix structure }
      BEGIN
        bild.save; fahne(1,r.nrows,-1,nvar); bild.restore;
      END;
      23: dump_basis;
      {$ENDIF}{$ENDIF}{$ENDIF}
      16: { write status of optimization }
      IF stat_geladen THEN
      BEGIN
        bild.save; WINDOW(1,1,80,25); CLRSCR;
        output_status; bild.restore;
      END ELSE my_message('NO FILE LOADED');
      {$IFNDEF win32}
      20: { command file }
      BEGIN
        bild.save; WINDOW(1,1,80,25); CLRSCR;
        path_:='*.CMD';
        directory(path_,cmd_);
        bild.restore;
        IF POS('.CMD', cmd_)<>0 THEN EXECUTE(cmd_);
        WRITELN;
      END;
      {$ENDIF}
      21: { start INTEGER search }
      BEGIN
        IF ninversion=1 THEN crash;
        t_start:=julia; title; stat_abbruch:=FALSE;
        iterate(TRUE);
        IF NOT stat_abbruch THEN
        BEGIN
          save_basis(TRUE);
          IF (ninf=0) AND (nne=0) AND NOT stat_unbounded THEN INTEGER_search(FALSE);
        END;
        elapsed:=elapsed+julia-t_start;
      END;
      51: { start INTEGER search with saved tree } load_tree;
      22: { mix up columns and rows: mainly for check of the inversion routine }
      mischen;
      32: start_load('*.INP');
      35: basis_of_rows;
      43: { recmove model from memory } new_model;
      88: { save INTEGER solution to disk } int_loesung;
      89: reset_problem;
      999: { help }
      BEGIN
        bild.save; WINDOW(1,1,80,25);
        he.help(help_,ps.destination_+ps.init_,1); bild.restore;
      END;
      998: { about }
      BEGIN
        bild.save; WINDOW(1,1,80,25);
        he.help(help_,ps.destination_+ps.init_,1100); bild.restore;
      END;
      1000: { exit }
      BEGIN
        CLRSCR; WRITE('EXIT PROGRAM (Y/N) '); baum:=tastatur;
        IF (baum=ORD('Y')) OR (baum=ORD('y')) THEN
        BEGIN
          WINDOW(1,1,80,25); quit; EXIT;
        END;
      END;
    END;
  UNTIL FALSE;
{$ENDIF}
END;
BEGIN
  {$IFNDEF win32}
  IF REGISTERBGIDRIVER(@egavgadriverproc)<>0 THEN
  IF REGISTERBGIFONT(@smallfontproc)<>0 THEN
  BEGIN
    DETECTGRAPH(driver,grmode);
    INITGRAPH  (driver,grmode,'C:\EIGEN\T\TREIBER'); RESTORECRTMODE;
  END
  ELSE hercules(9,2,'C:\EIGEN\T\TREIBER');
  {$ENDIF}
END.
