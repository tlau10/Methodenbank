procedure mg_grafik_header;
{-----------------------------------------------------------------------------}
type
     matrix_type         = array[1..30,1..4] of real;
{-----------------------------------------------------------------------------}
var  {zeichenmatrix       : array[1..15,1..4] of longint;}
     totalmatrix,
     punktmatrix,
     punktmatrix_alt     : matrix_type;
     anz_pkte,
     l1,l2,l3,l4,
     x_wert,y_wert       : integer;
     xy                  : char;
{-----------------------------------------------------------------------------}
procedure transmatrix_definieren(trans_nr: integer);
var      l1,l2,l3,l4 : integer;
begin
     for l1 := 1 to 4 do
       for l2 := 1 to 4 do
           totalmatrix[l1,l2] := 0;
     totalmatrix[1,1]  :=    1.0005646770;
     totalmatrix[2,2]  :=    1.0005646770;
     totalmatrix[3,3]  :=    1.0000000000;
     totalmatrix[4,4]  :=    1.0000000000;
     if (trans_nr = 0) then
     begin
          totalmatrix[1,2]  :=    0.1764265478;
          totalmatrix[2,1]  :=   -0.1764265478;
          totalmatrix[4,1]  :=   29.8485204841;
          totalmatrix[4,2]  :=  -45.0847647954;
     end
     else
     begin
          totalmatrix[1,2]  :=   -0.1764265478;
          totalmatrix[2,1]  :=    0.1764265478;
          totalmatrix[4,1]  :=  -30.2042670241;
          totalmatrix[4,2]  :=   66.0639603388;
     end;
end;
{-----------------------------------------------------------------------------}
procedure figur1_definieren;
var     l1,l2,l3,l4 : integer;

begin
     punktmatrix[ 1,1] := x_wert   ;             punktmatrix[ 1,2] := y_wert   ;
     punktmatrix[ 1,3] :=         0;             punktmatrix[ 1,4] :=         1;

     punktmatrix[ 2,1] := x_wert   ;             punktmatrix[ 2,2] := y_wert-10;
     punktmatrix[ 2,3] :=         0;             punktmatrix[ 2,4] :=         1;

     punktmatrix[ 3,1] := x_wert+ 5;             punktmatrix[ 3,2] := y_wert- 5;
     punktmatrix[ 3,3] :=         0;             punktmatrix[ 3,4] :=         1;

     punktmatrix[ 4,1] := x_wert+10;             punktmatrix[ 4,2] := y_wert-10;
     punktmatrix[ 4,3] :=         0;             punktmatrix[ 4,4] :=         1;

     punktmatrix[ 5,1] := x_wert+10;             punktmatrix[ 5,2] := y_wert   ;
     punktmatrix[ 5,3] :=         0;             punktmatrix[ 5,4] :=         1;

     punktmatrix[ 6,1] := x_wert+ 8;             punktmatrix[ 6,2] := y_wert   ;
     punktmatrix[ 6,3] :=         0;             punktmatrix[ 6,4] :=         1;

     punktmatrix[ 7,1] := x_wert+ 8;             punktmatrix[ 7,2] := y_wert- 6;
     punktmatrix[ 7,3] :=         0;             punktmatrix[ 7,4] :=         1;

     punktmatrix[ 8,1] := x_wert+ 5;             punktmatrix[ 8,2] := y_wert- 3;
     punktmatrix[ 8,3] :=         0;             punktmatrix[ 8,4] :=         1;

     punktmatrix[ 9,1] := x_wert+ 2;             punktmatrix[ 9,2] := y_wert- 6;
     punktmatrix[ 9,3] :=         0;             punktmatrix[ 9,4] :=         1;

     punktmatrix[10,1] := x_wert+ 2;             punktmatrix[10,2] := y_wert   ;
     punktmatrix[10,3] :=         0;             punktmatrix[10,4] :=         1;

     for l1 := 1 to anz_pkte do
         for l2 := 1 to 4 do punktmatrix_alt[l1,l2] := 0;
end;
{-----------------------------------------------------------------------------}
procedure figur2_definieren;
var     l1,l2,l3,l4 : integer;
begin
     punktmatrix[ 1,1] := x_wert   ;             punktmatrix[ 1,2] := y_wert   ;
     punktmatrix[ 1,3] :=         0;             punktmatrix[ 1,4] :=         1;

     punktmatrix[ 2,1] := x_wert   ;             punktmatrix[ 2,2] := y_wert-10;
     punktmatrix[ 2,3] :=         0;             punktmatrix[ 2,4] :=         1;

     punktmatrix[ 3,1] := x_wert+10;             punktmatrix[ 3,2] := y_wert-10;
     punktmatrix[ 3,3] :=         0;             punktmatrix[ 3,4] :=         1;

     punktmatrix[ 4,1] := x_wert+10;             punktmatrix[ 4,2] := y_wert- 7;
     punktmatrix[ 4,3] :=         0;             punktmatrix[ 4,4] :=         1;

     punktmatrix[ 5,1] := x_wert+ 8;             punktmatrix[ 5,2] := y_wert- 7;
     punktmatrix[ 5,3] :=         0;             punktmatrix[ 5,4] :=         1;

     punktmatrix[ 6,1] := x_wert+ 8;             punktmatrix[ 6,2] := y_wert- 8;
     punktmatrix[ 6,3] :=         0;             punktmatrix[ 6,4] :=         1;

     punktmatrix[ 7,1] := x_wert+ 2;             punktmatrix[ 7,2] := y_wert- 8;
     punktmatrix[ 7,3] :=         0;             punktmatrix[ 7,4] :=         1;

     punktmatrix[ 8,1] := x_wert+ 2;             punktmatrix[ 8,2] := y_wert- 2;
     punktmatrix[ 8,3] :=         0;             punktmatrix[ 8,4] :=         1;

     punktmatrix[ 9,1] := x_wert+ 8;             punktmatrix[ 9,2] := y_wert- 2;
     punktmatrix[ 9,3] :=         0;             punktmatrix[ 9,4] :=         1;

     punktmatrix[10,1] := x_wert+ 8;             punktmatrix[10,2] := y_wert- 4;
     punktmatrix[10,3] :=         0;             punktmatrix[10,4] :=         1;

     punktmatrix[11,1] := x_wert+ 6;             punktmatrix[11,2] := y_wert- 4;
     punktmatrix[11,3] :=         0;             punktmatrix[11,4] :=         1;

     punktmatrix[12,1] := x_wert+ 6;             punktmatrix[12,2] := y_wert- 3;
     punktmatrix[12,3] :=         0;             punktmatrix[12,4] :=         1;

     punktmatrix[13,1] := x_wert+ 4;             punktmatrix[13,2] := y_wert- 3;
     punktmatrix[13,3] :=         0;             punktmatrix[13,4] :=         1;

     punktmatrix[14,1] := x_wert+ 4;             punktmatrix[14,2] := y_wert- 5;
     punktmatrix[14,3] :=         0;             punktmatrix[14,4] :=         1;

     punktmatrix[15,1] := x_wert+10;             punktmatrix[15,2] := y_wert- 5;
     punktmatrix[15,3] :=         0;             punktmatrix[15,4] :=         1;

     punktmatrix[16,1] := x_wert+10;             punktmatrix[16,2] := y_wert   ;
     punktmatrix[16,3] :=         0;             punktmatrix[16,4] :=         1;

     for l1 := 1 to anz_pkte do
         for l2 := 1 to 4 do punktmatrix_alt[l1,l2] := 0;
end;
{-----------------------------------------------------------------------------}
procedure zeichnen(matrix : matrix_type;farbe1,farbe2 : integer);
var     l1,l2,l3,l4         : integer;
        koordinaten_array   :array[1..30] of pointtype;
begin
     setcolor(farbe2);
     setfillstyle(2,farbe2);
     for l1 := 1 to anz_pkte do
     begin
          koordinaten_array[l1].x := round(matrix[l1,1]);
          koordinaten_array[l1].y := round(matrix[l1,2]);
     end;
     koordinaten_array[anz_pkte+1].x := round(matrix[1,1]);
     koordinaten_array[anz_pkte+1].y := round(matrix[1,2]);
     fillpoly(anz_pkte+1,koordinaten_array);
end;
{-----------------------------------------------------------------------------}
procedure total_transformation;
var     l1,l2,l3,l4 : integer;
        matrix_neu  : matrix_type;

begin
     for l1 := 1 to anz_pkte do
         for l2 := 1 to 4 do matrix_neu[l1,l2] := 0;
     for l3 := 1 to anz_pkte do
         for l1 := 1 to 4 do
             for l2 := 1 to 4 do
             begin
                  matrix_neu[l3,l1] := matrix_neu[l3,l1] + totalmatrix[l2,l1] * punktmatrix[l3,l2];
             end;
     for l1 := 1 to anz_pkte do
         for l2 := 1 to 4 do punktmatrix[l1,l2] := matrix_neu[l1,l2];
end;

{-----------------------------------------------------------------------------}
begin
     setbkcolor(0);    setcolor(14);     setlinestyle(0,0,3);
     rectangle(100,50,520,300);          setlinestyle(0,0,1);
     x_wert := 250;    y_wert := 160;    anz_pkte := 10;
     transmatrix_definieren(0);          figur1_definieren;
     for l4 := 1 to 108 do
     begin
         if (keypressed) then exit;
         total_transformation;
         if (l4<> 1) then zeichnen(punktmatrix_alt,0,9);
             zeichnen(punktmatrix,14,14);
         for l1 := 1 to anz_pkte do for l2 := 1 to 4 do
             punktmatrix_alt[l1,l2] := punktmatrix[l1,l2];
     end;
     x_wert := 370;    y_wert := 160;    anz_pkte := 16;
     transmatrix_definieren(1);          figur2_definieren;
     for l4 := 1 to 108 do
     begin
         if (keypressed) then exit;
         total_transformation;
         if (l4 <> 1) then zeichnen(punktmatrix_alt,0,9);
             zeichnen(punktmatrix,14,14);
         for l1 := 1 to anz_pkte do for l2 := 1 to 4 do
             punktmatrix_alt[l1,l2] := punktmatrix[l1,l2];
     end;
     settextstyle(4,0,4);    outtextxy(262,145,'presents');
     settextstyle(4,0,8);    outtextxy(140,190,'Job-Shop');
     repeat until keypressed; xy := readkey;
end;

