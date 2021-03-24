{ EXTERNAL COMPILER OPTIONS
REC fÅr RECURSION IN ITERATE
Diese Option verlangt das Einbinden des Interpreters
}
PROGRAM lp;

{DEFINITION VARIABLEN INDIKATOR art
0 structural column
1 < ROW
2 > ROW
3 = ROW
4 N ROW
5 FR-COL Column with free Bound
}
USES {$ifdef msdos} OVERLAY,lp_oinit, {$ELSE} dpmi_limit, {$ENDIF}
     CRT,go_lp,tty,lo1;
     {$O lo2}
     {$O lo3}
     {$O lo4}
     {$O lo5}

VAR lp_solver:tlp6;
    memory:LONGINT;
BEGIN
  memory:=memavail; TEXTMODE(lastmode AND 255);
  WITH lp_solver DO
  BEGIN
    init;
    IF PARAMCOUNT>0 THEN go(PARAMSTR(1)) ELSE go('');
    IF lp_solver.co<>NIL THEN quit;
  END;
  CLRSCR;
  WRITE(memory-memavail);
END.
