UNIT lp_form;

INTERFACE

USES
  Windows, Messages, SysUtils, Classes, Graphics,Controls, Forms,
  Dialogs,Menus,util,StdCtrls, Grids, ComCtrls, ExtCtrls,go_lp,tty;

TYPE
  TlpForm = CLASS(TForm)
    MainMenu1: TMainMenu;
    mnuObjective: TMenuItem;
    mnuMaximise1: TMenuItem;
    mnuMinimise1: TMenuItem;
    mnuSelectObjective1: TMenuItem;
    mnuCrash1: TMenuItem;
    NEW1: TMenuItem;
    mnuLoad: TMenuItem;
    mnuloadMPSX: TMenuItem;
    MPSXFormatted1: TMenuItem;
    mnuLoadLP: TMenuItem;
    Basis1: TMenuItem;
    Tree1: TMenuItem;
    mnuSave: TMenuItem;
    Solution1: TMenuItem;
    mnuGo: TMenuItem;
    IntSolution1: TMenuItem;
    LP2: TMenuItem;
    MPSX2: TMenuItem;
    mnuSaveBasis: TMenuItem;
    mnuGoInteger: TMenuItem;
    mnuCommand: TMenuItem;
    mnuOther: TMenuItem;
    mnuInvert: TMenuItem;
    mnuEpsilonClick: TMenuItem;
    Status1: TMenuItem;
    MixColumns1: TMenuItem;
    BasiswithSlacks1: TMenuItem;
    Edit1: TMenuItem;
    Vector1: TMenuItem;
    Parameter1: TMenuItem;
    Infeasible1: TMenuItem;
    Demystified1: TMenuItem;
    mnuCancel: TMenuItem;
    Help1: TMenuItem;
    mnuExit: TMenuItem;
    Memo1: TMemo;
    StringGrid1: TStringGrid;
    OpenDialog1: TOpenDialog;
    SaveDialog1: TSaveDialog;
    Label1: TLabel;
    Label2: TLabel;
    Label3: TLabel;
    Label4: TLabel;
    Label5: TLabel;
    Label6: TLabel;
    Label7: TLabel;
    Label8: TLabel;
    Label9: TLabel;
    Label10: TLabel;
    Label11: TLabel;
    Label12: TLabel;
    Label13: TLabel;
    Label14: TLabel;
    Label15: TLabel;
    Label16: TLabel;
    Label17: TLabel;
    Label18: TLabel;
    Label19: TLabel;
    Label20: TLabel;
    Label21: TLabel;
    Label22: TLabel;
    Label23: TLabel;
    Label25: TLabel;
    Label26: TLabel;
    Label27: TLabel;
    ProgressBar1: TProgressBar;
    Edit2: TEdit;
    Timer1: TTimer;
    About11: TMenuItem;
    procedure mnuMaximise(SENDer: TObject);
    procedure mnuMinimise(SENDer: TObject);
    procedure mnuGoClick(SENDer: TObject);
    procedure mnuGoIntegerClick(SENDer: TObject);
    procedure mnuCrash1Click(SENDer: TObject);
    procedure keyevent(SENDer: TObject; var Key: Word; Shift: TShiftState);
    procedure mnuloadMPSXClick(SENDer: TObject);
    procedure mnuSaveBasisClick(SENDer: TObject);
    procedure mnuCommandClick(SENDer: TObject);
    procedure mnuEpsilonClickClick(SENDer: TObject);
    procedure mnuInvertClick(SENDer: TObject);
    procedure IntSolution1Click(SENDer: TObject);
    procedure NEW1Click(SENDer: TObject);
    procedure Solution1Click(SENDer: TObject);
    procedure Basis1Click(SENDer: TObject);
    procedure Tree1Click(SENDer: TObject);
    procedure MPSX2Click(SENDer: TObject);
    procedure LP2Click(SENDer: TObject);
    procedure Demystified1Click(SENDer: TObject);
    procedure Infeasible1Click(SENDer: TObject);
    procedure BasiswithSlacks1Click(SENDer: TObject);
    procedure mnuLoadLPClick(SENDer: TObject);
    procedure mnuExitClick(SENDer: TObject);
    procedure mnuCancelClick(SENDer: TObject);
    procedure Status1Click(SENDer: TObject);
    procedure MixColumns1Click(SENDer: TObject);
    procedure Parameter1Click(SENDer: TObject);
    procedure StringGrid1KeyUp(SENDer: TObject; var Key: Word; Shift: TShiftState);
    procedure tlpformshow(SENDer: TObject);
    procedure MPSXFormatted1Click(SENDer: TObject);
    procedure edit2_key(SENDer: TObject; var Key: Word;
       Shift: TShiftState);
    procedure prog_quit(SENDer: TObject; var Action: TCloseAction);
    procedure Timer1Timer(SENDer: TObject);
    procedure LP_create(SENDer: TObject);
    procedure Help1Click(SENDer: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
    PROCEDURE addmemo(s_:STRIN);
    PROCEDURE appENDmemo(s_:STRIN);
    PROCEDURE clear_label;
  END;

var lpForm: TlpForm;
    memomaxlines:WORD;
    edit_ready,edit_valid:BOOLEAN;
    lp_solver:tlp6;
    implementation

{$R *.DFM}

PROCEDURE tlpForm.addmemo(s_:STRIN);
BEGIN
  WITH LpForm.memo1 DO
  BEGIN
    IF lines.count>memomaxlines THEN clear;
    IF lines.add(s_)<10000 THEN;
  END;
END;
{-----------------------------------------------------------}
PROCEDURE tlpForm.appENDmemo(s_:STRIN);
VAR s2_:STRIN;
BEGIN
  WITH LpForm.memo1 DO
  BEGIN
    {lines[lines.count-1]:=lines[lines.count-1]+s_;}
    s2_:=lines[lines.count-1]+s_;
    lines.delete(lines.count-1);
    LpForm.addmemo(s2_);
  END;
END;
{-----------------------------------------------------------}
procedure TlpForm.mnuMaximise(SENDer: TObject);
BEGIN
  lp_solver.set_max;
END;
{-----------------------------------------------------------}
procedure TlpForm.mnuMinimise(SENDer: TObject);
BEGIN
  lp_solver.set_min;
END;
{-----------------------------------------------------------}
procedure TlpForm.mnuGoClick(SENDer: TObject);
BEGIN
  WITH LpForm DO WITH lp_solver DO 
  BEGIN
    mnuObjective.enabled:=FALSE;
    mnuCancel.enabled:=TRUE;
    mnuExit.enabled:=FALSE;
    mnuGo.enabled:=FALSE;
    mnuGoInteger.enabled:=FALSE;
    mnuLoad.enabled:=FALSE;
    mnuSave.enabled:=FALSE;
    mnuCommand.enabled:=FALSE;
    mnuOther.enabled:=FALSE;
    setfocus; keypreview:=TRUE;
    solve_lp; keypreview:=FALSE;
    IF stat_with_log THEN write_log;
    mnuCommand.enabled:=TRUE;
    mnuOther.enabled:=TRUE;
    mnuLoad.enabled:=TRUE;
    mnuSave.enabled:=TRUE;
    mnuGo.enabled:=TRUE;
    mnuGoInteger.enabled:=TRUE;
    mnuExit.enabled:=TRUE;
    mnuCancel.enabled:=FALSE;
    mnuObjective.enabled:=TRUE;
  END;
END;
{-----------------------------------------------------------}
procedure TlpForm.mnuGoIntegerClick(SENDer: TObject);
BEGIN
  WITH LpForm DO WITH lp_solver DO 
  BEGIN
    mnuObjective.enabled:=FALSE;
    mnuCancel.enabled:=TRUE;
    mnuGo.enabled:=FALSE;
    mnuGoInteger.enabled:=FALSE;
    mnuCommand.enabled:=FALSE;
    mnuExit.enabled:=FALSE;
    mnuLoad.enabled:=FALSE;
    mnuSave.enabled:=FALSE;
    mnuCommand.enabled:=FALSE;
    mnuOther.enabled:=FALSE;
    label8.caption:='';
    setfocus; keypreview:=TRUE; global; keypreview:=FALSE;
    mnuCommand.enabled:=TRUE;
    mnuOther.enabled:=TRUE;
    mnuLoad.enabled:=TRUE;
    mnuSave.enabled:=TRUE;
    mnuCOmmand.enabled:=TRUE;
    mnuGo.enabled:=TRUE;
    mnuGointeger.enabled:=TRUE;
    mnuExit.enabled:=TRUE;
    mnuCancel.enabled:=FALSE;
    mnuObjective.enabled:=TRUE;
  END;
END;
{-----------------------------------------------------------}
procedure TlpForm.mnuCrash1Click(SENDer: TObject);
BEGIN
  lp_Solver.crash;
END;
{-----------------------------------------------------------}
procedure TlpForm.keyevent(SENDer:TObject; var Key:Word; Shift:TShiftState);
BEGIN
  WITH lp_Solver DO
  BEGIN
    stat_pressed:=TRUE;
    CASE key OF
      187: { + }
      BEGIN
        npricing:=mini(30000,longint(npricing)*2); nne:=0;
      END;
      189: { - } npricing:=maxi(1,npricing div 2);
      68,100: { D, d }
      IF iterlog=0 THEN iterlog:=1
      ELSE IF iterlog=1 THEN iterlog:=10000
      ELSE                   iterlog:=0;
      76,108: { l, L } stat_strat3:=1-stat_strat3;
      77,109: { m, M } IF stat_integer_mode THEN stat_grafikm:=TRUE;
      78,110: { n, N } IF stat_integer_mode THEN stat_grafikn:=TRUE;
      79,111: { o, O } IF stat_integer_mode THEN stat_grafiko:=TRUE;
      83,115: { s,S  }
      stat_save:=TRUE;
      84,116: { T, t } stat_togg:=TRUE;
      87,119: { w,W }
      IF stat_integer_mode THEN
      WITH int_rec DO max_memory:=maxi(mini(max_memory,wtiefe)-100,10);
      88,120: { x,X }
      IF stat_integer_mode THEN WITH int_rec DO max_memory:=2*wtiefe+100;
      55: { 7 : ERHOEHE STRATEGIE 1 }
      BEGIN
        stat_strat1:=mini(max_strat,stat_strat1+2);
        stat_strat1:=mini(max_strat,maxi(stat_strat1,stat_strat2));
        lpform.addmemo(ltoa(stat_strat1)+' '+ltoa(stat_strat2));
      END;
      56: { 8 : ERHOEHE STRATEGIE 2 }
      BEGIN
        stat_strat2:=mini(max_strat,stat_strat2+1);
        stat_strat1:=maxi(0,mini(stat_strat1-1,stat_strat2));
        lpform.addmemo(ltoa(stat_strat1)+' '+ltoa(stat_strat2));
      END;
      57: { 9 : ERHOEHE STRATEGIE 3 }
      BEGIN
        stat_strat2:=maxi(0,stat_strat2-1);
        stat_strat1:=maxi(0,mini(stat_strat1,stat_strat2));
        lpform.addmemo(ltoa(stat_strat1)+' '+ltoa(stat_strat2));
      END;
    END;
  END;
END;
{-----------------------------------------------------------}
procedure TlpForm.mnuloadMPSXClick(SENDer: TObject);
VAR s_:STRIN;
BEGIN
  WITH lp_solver DO
  BEGIN
    Opendialog1.filename:='*.MAT'; stat_help_index:=31;
    IF opENDialog1.execute THEN WITH LpForm DO
    BEGIN
      s_:=ucase(opENDialog1.filename);
      IF POS('.MAT',s_)<>0 THEN
      BEGIN
        lp_solver.fname_:=s_; lies_mpsx; make_rest;
        IF stat_geladen THEN mnuOther.enabled:=TRUE;
        mnuObjective.enabled:=TRUE;
        mnuSave.enabled:=TRUE;
        mnuCOmmand.enabled:=TRUE;
        mnuGo.enabled:=TRUE;
        mnuGoInteger.enabled:=TRUE;
        mnuExit.enabled:=TRUE;
        label1.caption:=fname_;
        {$IFNDEF win32} label2.caption:=laenge(ltoa(getheapstatus.totalallocated DIV 1000),7,' ');{$ENDIF}
      END;
    END;
  END;
END;
{-----------------------------------------------------------}
PROCEDURE TlpForm.clear_label;
BEGIN
  WITH LpForm DO
  BEGIN
    label1.caption:='';  { File }
    label2.caption:='';  { Memavail }
    label3.caption:='';  { minpiv }
    label4.caption:='';  { ninf, netha, nelemen }
    {label5.caption:=''};  { Matrix Dimension }
    {label6.caption:=''};  { Titel of Memo }
    label7.caption:='';  { read save nodes }
    label8.caption:='';  { Inversion }
    label9.caption:='';  { non negative dj NNE }
    label10.caption:='';  { new pseudocosts neeu }
    label11.caption:='';
    label13.caption:='';
    label15.caption:='';  { Int Status }
    label16.caption:='';  { total Nodes searched }
    label18.caption:='';  { ngleich }
    label19.caption:='';  { INT SOL FOUND}
    label21.caption:='';  { Hit }
    label22.caption:='nDj';  { maxavail }
    label23.caption:='';  { count minnz }
  END;
END;
{-----------------------------------------------------------}
procedure TlpForm.mnuSaveBasisClick(SENDer: TObject);
BEGIN
  lp_solver.save_basis(TRUE);
END;
{-----------------------------------------------------------}
procedure TlpForm.mnuCommandClick(SENDer: TObject);
BEGIN
  WITH lp_Solver DO
  BEGIN
    opENDialog1.filename:='*.CMD'; stat_help_index:=36;
    IF opENDialog1.execute THEN
    IF POS('.CMD',ucase(opENDialog1.filename))<>0 THEN
    BEGIN
      mnuObjective.enabled:=FALSE;
      mnuGoInteger.enabled:=FALSE;
      mnuGo.enabled       :=FALSE;
      mnuExit.enabled     :=FALSE;
      mnuLoad.enabled     :=FALSE;
      mnuSave.enabled     :=FALSE;
      mnuCommand.enabled  :=FALSE;
      mnuOther.enabled    :=FALSE;
      mnuCancel.enabled   :=TRUE;
      setfocus; keypreview:=TRUE;
      execute(opENDialog1.filename); keypreview:=FALSE;
      mnuGoInteger.enabled:=TRUE;
      mnuGo.enabled       :=TRUE;
      mnuCommand.enabled  :=TRUE;
      mnuOther.enabled    :=TRUE;
      mnuLoad.enabled     :=TRUE;
      mnuSave.enabled     :=TRUE;
      mnuCommand.enabled  :=TRUE;
      mnuExit.enabled     :=TRUE;
      mnuCancel.enabled   :=FALSE;
      mnuObjective.enabled:=TRUE;
    END;
  END;
END;
{-----------------------------------------------------------}
procedure TlpForm.mnuEpsilonClickClick(SENDer: TObject);
BEGIN
  WITH lp_solver DO CASE with_eps OF
    0: toggle_epsilon(1);
    1: toggle_epsilon(2);
    2: toggle_epsilon(0);
  END;
END;
{-----------------------------------------------------------}
procedure TlpForm.mnuInvertClick(SENDer: TObject);
BEGIN
  lp_solver.inversion;
END;
{-----------------------------------------------------------}
procedure TlpForm.IntSolution1Click(SENDer: TObject);
BEGIN
  lp_solver.int_loesung;
END;
{-----------------------------------------------------------}
procedure TlpForm.NEW1Click(SENDer: TObject);
BEGIN
  WITH lp_solver DO
  BEGIN
    new_model;
    WITH LpForm DO
    BEGIN
      mnuObjective.enabled:=FALSE;
      mnuCancel.enabled:=FALSE;
      mnuGoInteger.enabled:=FALSE;
      mnuGo.enabled:=FALSE;
      mnuExit.enabled:=TRUE;
      mnuLoad.enabled:=TRUE;
      mnuSave.enabled:=FALSE;
    END;
    {$IFDEF win32}
    LpForm.label2.caption:=laenge(ltoa(getheapstatus.totalallocated DIV 1000),7,' ');
    LpForm.label3.caption:=laenge(ftoa(min_piv),11,' ');
    {$ELSE}
    win_out(koomema,  laenge(ltoa(MEMAVAIL DIV 1000),7,' '));
    win_out(koominpiv,laenge(ftoa(min_piv),11,' '));
    {$ENDIF}
  END;
END;
{-----------------------------------------------------------}
procedure TlpForm.Solution1Click(SENDer: TObject);
BEGIN
  lp_solver.loesung;
END;
{-----------------------------------------------------------}
procedure TlpForm.Basis1Click(SENDer: TObject);
BEGIN
  WITH lp_solver DO
  BEGIN
    opENDialog1.filename:='*.BSS'; stat_help_index:=15;
    IF opENDialog1.execute THEN
    IF POS('.BSS',ucase(opENDialog1.filename))<>0 THEN
    load_basis(opENDialog1.filename);
  END;
END;
{-----------------------------------------------------------}
procedure TlpForm.Tree1Click(SENDer: TObject);
BEGIN
  WITH LpForm DO WITH lp_Solver DO IF stat_geladen THEN
  BEGIN
    mnuObjective.enabled:=FALSE;
    mnuCancel.enabled:=TRUE;
    mnuGoInteger.enabled:=FALSE;
    mnuGo.enabled:=FALSE;
    mnuExit.enabled:=FALSE;
    mnuLoad.enabled:=FALSE;
    mnuSave.enabled:=FALSE;
    mnuCommand.enabled:=FALSE;
    mnuOther.enabled:=FALSE;
    setfocus; keypreview:=TRUE;
    load_tree; keypreview:=FALSE;
    mnuCommand.enabled:=TRUE;
    mnuOther.enabled:=TRUE;
    mnuLoad.enabled:=TRUE;
    mnuSave.enabled:=TRUE;
    mnuGo.enabled:=TRUE;
    mnuGoInteger.enabled:=TRUE;
    mnuExit.enabled:=TRUE;
    mnuCancel.enabled:=FALSE;
    mnuObjective.enabled:=TRUE;
  END;
END;
{-----------------------------------------------------------}
procedure TlpForm.MPSX2Click(SENDer: TObject);
BEGIN
  lp_solver.save_mpsx;
END;
{-----------------------------------------------------------}
procedure TlpForm.LP2Click(SENDer: TObject);
BEGIN
  lp_solver.save_lp;
END;
{-----------------------------------------------------------}
procedure TlpForm.Demystified1Click(SENDer: TObject);
BEGIN
  lp_solver.demyst; memomaxlines:=24;
END;
{-----------------------------------------------------------}
procedure TlpForm.Infeasible1Click(SENDer: TObject);
BEGIN
  lp_solver.show_infeasible; memomaxlines:=24;
END;
{-----------------------------------------------------------}
procedure TlpForm.BasiswithSlacks1Click(SENDer: TObject);
BEGIN
  lp_solver.basis_of_rows;
END;
{-----------------------------------------------------------}
procedure TlpForm.mnuLoadLPClick(SENDer: TObject);
BEGIN
  WITH lp_solver DO
  BEGIN
    opENDialog1.filename:='*.LP'; stat_help_index:=30;
    IF opENDialog1.execute THEN WITH LpForm DO
    BEGIN
      IF POS('.LP',ucase(opENDialog1.filename))<>0 THEN
      BEGIN
        fname_:=ucase(opENDialog1.filename); lies_lp; make_rest;
        mnuObjective.enabled:=TRUE;
        mnuSave.enabled:=TRUE;
        mnuGo.enabled:=TRUE;
        mnuGoInteger.enabled:=TRUE;
        mnuExit.enabled:=TRUE;
        label1.caption:=fname_;
        label2.caption:=laenge(ltoa(getheapstatus.totalallocated DIV 1000),7,' ');
      END;
    END;
  END;
END;
{-----------------------------------------------------------}
procedure TlpForm.mnuExitClick(SENDer: TObject);
BEGIN
  lp_solver.quit;
  IF application.helpcommand(help_quit,0) THEN;
  application.terminate;
END;
{-----------------------------------------------------------}
procedure TlpForm.mnuCancelClick(SENDer: TObject);
BEGIN
  IF MessageDlg('Cancel (Y/N) ',mtCustom,mbOkCancel,0)=mrok THEN
  BEGIN
    lp_solver.stat_abbruch:=TRUE; lp_solver.stat_user_abbruch:=TRUE;
    mnuCancel.enabled:=FALSE;
  END;
END;
{-----------------------------------------------------------}
procedure TlpForm.Status1Click(SENDer: TObject);
BEGIN
  lp_solver.output_status;
END;
{-----------------------------------------------------------}
PROCEDURE tlpform.MixColumns1Click(SENDer: TObject);
BEGIN
  lp_solver.mischen;
END;
{-----------------------------------------------------------}
procedure TlpForm.Parameter1Click(SENDer: TObject);
VAR i:INTEGER;
BEGIN
  WITH LpForm.stringgrid1 DO WITH lp_solver DO 
  BEGIN
    label1.caption:='EDIT PARAMETER';
    defaultrowheight:=15; fixedcols:=1;
    rowcount:=lp_par.npar-1; enabled:=TRUE; colcount:=2;
    lp_par.fill_edit(3);
    WITH lp_par DO FOR i:=1 TO npar DO
    BEGIN
      cells[0,i-1]:=p^[i].grid1_.get;
      cells[1,i-1]:=p^[i].grid2_.get;
    END;
    visible:=TRUE; enabled:=TRUE; setfocus; memo1.hide;
    options:=[gofixedvertline,gofixedhorzline,gohorzline,govertline,
    gorowmoving,gocolsizing,goediting,gotabs];
  END;
END;
{-----------------------------------------------------------}
procedure TlpForm.StringGrid1KeyUp(SENDer: TObject; var Key: Word; Shift: TShiftState);
VAR fp:TEXTFILE;
    i:INTEGER;
BEGIN
  { Escape }
  IF key=27 THEN WITH LpForm.stringgrid1 DO WITH lp_Solver DO 
  BEGIN
    visible:=FALSE; enabled:=FALSE; memo1.show;
    LpForm.label1.caption:=fname_; lpform.setfocus;
    WITH lp_par DO FOR i:=1 TO npar DO WITH p^[i] DO
    BEGIN
      grid1_.done; grid2_.done;
    END;
  END;
  IF key=13 THEN WITH lp_solver DO WITH lp_par DO
  BEGIN
    memo1.show;
    WITH lp_par DO FOR i:=1 TO npar DO WITH p^[i] DO
    BEGIN
      grid1_.done; grid2_.done;
    END;
    FOR i:=1 TO npar DO p^[i].grid2_.put(LpForm.stringgrid1.cells[1,i-1]);
    IF read_edit THEN
    BEGIN
      LpForm.stringgrid1.visible:=FALSE;
      LpForm.stringgrid1.enabled:=FALSE;
      setfocus; lies_aus_parameter;
      ASSIGNFILE(fp,parameter_); {$I-} REWRITE(fp); {$I+}
      IF IORESULT=0 THEN
      BEGIN
        save_parameter(fp); CLOSEFILE(fp);
      END ELSE my_message('CANNOT WRITE '+parameter_);
      LpForm.label1.caption:=fname_; setfocus;
      WITH lp_par DO FOR i:=1 TO npar DO WITH p^[i] DO
      BEGIN
        grid1_.done; grid2_.done;
      END;
    END;
  END;
END;
{-----------------------------------------------------------}
procedure TlpForm.tlpformshow(SENDer: TObject);
BEGIN
  WITH LpForm DO
  BEGIN
    memomaxlines:=24;
    clear_label;
    LpForm.stringgrid1.enabled:=FALSE;
    memo1.clear; memo1.readonly:=TRUE;
    caption:='LP-SOLVER';
    top:=0;
    left:=0;
    mnuExit.enabled:=TRUE;
    mnusave.enabled:=FALSE;
    mnuGo.enabled:=FALSE;
    mnuCancel.enabled:=FALSE;
    mnuObjective.enabled:=FALSE;
    mnuCommand.enabled:=TRUE;
    mnuGoInteger.enabled:=FALSE;
    mnuOther.enabled:=FALSE;
    edit2.enabled:=FALSE;
    edit2.visible:=FALSE;
    WITH stringgrid1 DO
    BEGIN
      enabled:=FALSE; visible:=FALSE; fixedrows:=0; colcount:=2;
      defaultcolwidth:=140; top:=80; left:=10; height:=200;
    END;
  END;
END;
{-----------------------------------------------------------}
procedure TlpForm.MPSXFormatted1Click(SENDer: TObject);
BEGIN
  WITH lp_Solver DO
  BEGIN
    opENDialog1.filename:='*.MAT'; stat_help_index:=40;
    IF opENDialog1.execute THEN WITH LpForm DO
    BEGIN
      IF POS('.MAT',ucase(opENDialog1.filename))<>0 THEN
      BEGIN
        fname_:=ucase(opENDialog1.filename); lies_mpsx_komplett; make_rest;
        IF stat_geladen THEN mnuOther.enabled:=TRUE;
        mnuObjective.enabled:=TRUE;
        mnuSave.enabled:=TRUE;
        mnuCOmmand.enabled:=TRUE;
        mnuGo.enabled:=TRUE;
        mnuGoInteger.enabled:=TRUE;
        mnuExit.enabled:=TRUE;
        label1.caption:=fname_;
        {$IFNDEF win32} label2.caption:=laenge(ltoa(getheapstatus.totalallocated),7,' ');{$ENDIF}
      END;
    END;
  END;
END;
{-----------------------------------------------------------}
procedure TlpForm.edit2_key(SENDer: TObject; var Key: Word;
  Shift: TShiftState);
BEGIN
  IF key=13 THEN
  BEGIN
    edit_ready:=TRUE; edit_valid:=TRUE; lpform.setfocus; edit2.hide;
  END
  ELSE IF key=27 THEN
  BEGIN
    edit_ready:=TRUE; lpform.setfocus; edit2.hide;
  END;
END;
{-----------------------------------------------------------}
procedure TlpForm.prog_quit(SENDer: TObject; var Action: TCloseAction);
BEGIN
  lp_solver.stat_abbruch:=TRUE; lp_solver.quit;
END;
{-----------------------------------------------------------}
PROCEDURE TlpForm.Timer1Timer(SENDer: TObject);
BEGIN
  timer1.enabled:=FALSE;
  IF paramcount>0 THEN
  BEGIN
    mnuObjective.enabled:=FALSE;
    mnuGoInteger.enabled:=FALSE;
    mnuGo.enabled       :=FALSE;
    mnuExit.enabled     :=FALSE;
    mnuLoad.enabled     :=FALSE;
    mnuSave.enabled     :=FALSE;
    mnuCommand.enabled  :=FALSE;
    mnuOther.enabled    :=FALSE;
    mnuCancel.enabled   :=TRUE;
    setfocus;
    lp_solver.stat_from_command_line:=TRUE;
    lp_solver.execute(ucase(paramstr(1)));
    mnuGoInteger.enabled:=TRUE;
    mnuGo.enabled       :=TRUE;
    mnuCommand.enabled  :=TRUE;
    mnuOther.enabled    :=TRUE;
    mnuLoad.enabled     :=TRUE;
    mnuSave.enabled     :=TRUE;
    mnuCommand.enabled  :=TRUE;
    mnuExit.enabled     :=TRUE;
    mnuCancel.enabled   :=FALSE;
    mnuObjective.enabled:=TRUE;
  END;
END;
{-----------------------------------------------------------}
procedure TlpForm.LP_create(SENDer: TObject);
BEGIN
  lp_solver.init;
  chdir(lp_solver.start_path_); application.helpfile:=lp_solver.start_path_+'\LP.HLP';
END;
{-----------------------------------------------------------}
procedure TlpForm.Help1Click(SENDer: TObject);
BEGIN
  IF application.helpjump('main') THEN;
END;
{-----------------------------------------------------------}
END.
