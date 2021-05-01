{ Sample for Borland-Pascal / DOS }
PROGRAM sample;

USES go_lp;

VAR lp_solver:tlp6;
BEGIN
  WITH lp_solver DO
  BEGIN
    init;
    fname_:='buecher';
    IF define_row('OBJF','N') THEN;
    IF define_row('r_2','L') THEN;

    define_entry('OBJF','B1' ,23);
    define_entry('OBJF','B2' ,14);
    define_entry('OBJF','B3' ,13);
    define_entry('OBJF','B4' ,32);
    define_entry('OBJF','B5' ,14);
    define_entry('OBJF','B6' ,31);
    define_entry('OBJF','B7' ,18);
    define_entry('OBJF','B8' ,21);
    define_entry('OBJF','B9' ,10);
    define_entry('OBJF','B10',22);

    define_entry('r_2','B1' ,55);
    define_entry('r_2','B2' ,32);
    define_entry('r_2','B3' ,19);
    define_entry('r_2','B4' ,67);
    define_entry('r_2','B5' ,21);
    define_entry('r_2','B6' ,72);
    define_entry('r_2','B7' ,39);
    define_entry('r_2','B8' ,48);
    define_entry('r_2','B9' ,23);
    define_entry('r_2','B10',45);

    define_rhs('r_2',200);

    define_bound( 'B1','BV',1);
    define_bound( 'B2','BV',1);
    define_bound( 'B3','BV',1);
    define_bound( 'B4','BV',1);
    define_bound( 'B5','BV',1);
    define_bound( 'B6','BV',1);
    define_bound( 'B7','BV',1);
    define_bound( 'B8','BV',1);
    define_bound( 'B9','BV',1);
    define_bound('B10','BV',1);

    stat_geladen:=TRUE; elapsed:=0;
    objf_:='OBJF'; set_max;
    save_mpsx; stat_command:=TRUE;
    global;
    int_loesung;
    WRITELN('Optimal Integer Solution');
    WRITELN('B1  ',get_optimal_int_value('B1'),' ',get_optimal_int_value('B1'));
    WRITELN('B2  ',get_optimal_int_value('B2'),' ',get_optimal_int_value('B2'));
    WRITELN('B3  ',get_optimal_int_value('B3'),' ',get_optimal_int_value('B3'));
    WRITELN('B4  ',get_optimal_int_value('B4'),' ',get_optimal_int_value('B4'));
    WRITELN('B5  ',get_optimal_int_value('B5'),' ',get_optimal_int_value('B5'));
    WRITELN('B6  ',get_optimal_int_value('B6'),' ',get_optimal_int_value('B6'));
    WRITELN('B7  ',get_optimal_int_value('B7'),' ',get_optimal_int_value('B7'));
    WRITELN('B8  ',get_optimal_int_value('B8'),' ',get_optimal_int_value('B8'));
    WRITELN('B9  ',get_optimal_int_value('B9'),' ',get_optimal_int_value('B9'));
    WRITELN('B10 ',get_optimal_int_value('B10'),' ',get_optimal_int_value('B10'));
    done;
  END;
END.
Zugehuerige MPS DATEI

NAME          BUECHER
ROWS
 N  OBJF
 L  OBJF
COLUMNS
    B1        OBJF          23.00000   r_2           55.00000
    B2        OBJF          14.00000   r_2           32.00000
    B3        OBJF          13.00000   r_2           19.00000
    B4        OBJF          32.00000   r_2           67.00000
    B5        OBJF          14.00000   r_2           21.00000
    B6        OBJF          31.00000   r_2           72.00000
    B7        OBJF          18.00000   r_2           39.00000
    B8        OBJF          21.00000   r_2           48.00000
    B9        OBJF          10.00000   r_2           23.00000
    B10       OBJF          22.00000   r_2           45.00000
RHS
    RHS       r_2          200.00000
BOUNDS
 BV BOUND     B1
 BV BOUND     B2
 BV BOUND     B3
 BV BOUND     B4
 BV BOUND     B5
 BV BOUND     B6
 BV BOUND     B7
 BV BOUND     B8
 BV BOUND     B9
 BV BOUND     B10
ENDATA
