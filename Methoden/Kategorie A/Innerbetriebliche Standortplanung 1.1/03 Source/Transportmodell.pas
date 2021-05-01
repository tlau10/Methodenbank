unit Transportmodell;

interface

uses Matrix, Dialogs, SysUtils;

type Koord = record
     x,y: Word;
     end;

type Loesung = record
     Punkte: array of Koord;
     TAW: Int64;
     end;

type TTransport = class(TObject)
      MN: Integer;
      M,N: Word;
      TAW,UrTAW: Int64;
      D,B,
      Spalte,Weg: array of Integer;
      Ur,A: TIntMatrix;
      Optimal: Loesung;
     private
      procedure Anfangsloesung();
      procedure Iterationen();
     public
      constructor Create(Orte: Word; Matrix: TIntMatrix);
      destructor Kill();
      procedure ReInit(Matrix: TIntMatrix);
      function Rechnen(): Loesung;
     end;

implementation

uses Berechnung;

constructor TTransport.Create(Orte: Word; Matrix: TIntMatrix);
var i,j: Word;
begin
     inherited Create();

     TAW := 0;
     M := (Orte * (Orte-1)) div 2;
     N := M;
     MN := M+N-1;

     SetLength(D,M+1);
     SetLength(B,M+1);
     SetLength(Spalte,M+1);
     SetLength(Weg,M+1);
     SetLength(Optimal.Punkte,M+1);

     A := TIntMatrix.Dim(M,N);
     Ur := Matrix;
     
     for i := 1 to M do
     begin
          D[i] := 1;
          B[i] := 1;
          Spalte[i] := 0;
          Weg[i] := 0;
          for j := 1 to N do
              A.SetValue(i,j,Matrix.GetValue(i,j));
     end;
end;

destructor TTransport.Kill();
begin
     D := nil;
     B := nil;
     Spalte := nil;
     Weg := nil;
     Optimal.Punkte := nil;
     A.Kill();
     inherited Destroy();
end;

procedure TTransport.ReInit(Matrix: TIntMatrix);
var i,j: Word;
begin
     TAW := 0;

     for i := 1 to M do
     begin
          D[i] := 1;
          B[i] := 1;
          Spalte[i] := 0;
          Weg[i] := 0;
          for j := 1 to N do
               A.SetValue(i,j,Matrix.GetValue(i,j));
     end;
end;

function TTransport.Rechnen(): Loesung;
var i,j,OptCount: Word;
begin
     Anfangsloesung;
     Iterationen;

     OptCount := 1;
     for i := 1 to M do
     begin
          if Spalte[i] <> 0 then
               if D[i] = 1 then
               begin
                    Optimal.Punkte[OptCount].x := i;
                    Optimal.Punkte[OptCount].y := Spalte[i];
                    OptCount:= OptCount + 1;
               end;
          for j := 1 to N do
              if Weg[j] = I then
                   if B[j] = 1 then
                   begin
                        Optimal.Punkte[OptCount].x := i;
                        Optimal.Punkte[OptCount].y := j;
                        OptCount:= OptCount + 1;
                   end;
     end;

     UrTAW := 0;
     for i := 1 to M do
         UrTAW := UrTAW + UrMatrix.GetValue(Optimal.Punkte[i].x,Optimal.Punkte[i].y);

     Optimal.TAW := UrTAW;

     result := Optimal;
end;

procedure TTransport.Anfangsloesung();
var IM,JM,i,j: Integer;
    Min,Lauf: Int64;
begin
     IM := 1;
     Lauf := 0;

     while (Lauf < MN) do
     begin
          Min := HIGH(Min);
          for j := 1 to N do
              if (Weg[j] = 0) and (A.GetValue(IM,j) <= Min) then
                 begin
                      Min := A.GetValue(IM,j);
                      JM := j;
                 end;
          while (D[IM] <= B[JM]) do
          begin
               Spalte[IM] := JM;
               B[JM] := B[JM] - D[IM];
               TAW := TAW + A.GetValue(IM,JM)*D[IM];
               Inc(Lauf);
               if Lauf = MN then exit;
               Min := HIGH(Min);
               for i := 1 to M do
                   if (Spalte[i] = 0) and (A.GetValue(i,JM) < Min) then
                   begin
                        Min := A.GetValue(i,JM);
                        IM := i;
                        end;
          end;
          Weg[JM] := IM;
          Inc(Lauf);
     end;
end; (*  EOP anfangsloesung    *)

procedure TTransport.Iterationen();
var FB,L,i,j,II,JJ,LA: Word;
    IM,JM: Word;
    AMM,AE,IQ: Integer;
    Min,CA: Int64;
label  T680, T1040, T1280, T1330, T1350, T1360;
begin

 T680:
     FB := 0;
     while (FB < MN) do
     begin
          for i := 1 to M do
              if Spalte[i] > 0 then
                 if Weg[Spalte[i]] <= 0 then
                 begin
                      CA := A.GetValue(i,Spalte[i]);
                      for j := 1 to N do
                          A.SetValue(i,j,(A.GetValue(i,j)-CA));
                      Inc(FB);
                      Spalte[i] := -Spalte[i];
                 end;
          if FB = MN then break;
          for j := 1 to N do
              if Weg[j] > 0 then
                 if Spalte[Weg[j]] <= 0 then
                 begin
                      CA := A.GetValue(Weg[j],j);
                      for i := 1 to M do
                          A.SetValue(i,j,(A.GetValue(i,j)-CA));
                          Inc(FB);
                          Weg[j] := -Weg[j];
                 end;
     end;

     for i := 1 to M do
         Spalte[i] := -Spalte[i];
     for j := 1 to N do
         Weg[j] := -Weg[j];

     Min := 0;
     for i := 1 to M do
         for j := 1 to N do
             if A.GetValue(i,j) <= Min then
             begin
                  IM := i;
                  JM := j;
                  Min := A.GetValue(i,j);
             end;
     if Min = 0 then exit;
     L := 1;
     I:= IM;

     while (true) do
     begin
          if Spalte[I] = 0 then break;
          J := ABS(Spalte[I]);
          Spalte[I] := -Spalte[I];
 T1040:
          if Weg[J] = 0 then break;
          I := ABS(Weg[J]);
          Weg[J] := -Weg[J];
     end;
     if L = 1 then
     begin
          L := 2;
          J := JM;
          GOTO T1040;
     end;

     I := IM;
     AMM := HIGH(AMM);
     L := 1;

     repeat
           if Spalte[I] >= 0 then break;
           J := -Spalte[I];
           if D[I] < AMM then
           begin
                AMM := D[I];
                II := I;
                JJ := J;
           end;
           if Weg[J] < 0 then
                I := -Weg[J];
     until (Weg[J] >= 0);

     J := JM;

     repeat
           if Weg[J] >= 0 then break;
           I := -Weg[J];
           if B[J] < AMM then
           begin
                AMM := B[J];
                II := I;
                JJ := J;
                L := 2;
           end;
           if Spalte[I] < 0 then
                J := -Spalte[I];
     until (Spalte[I] >= 0);

     LA := 1;
     TAW := TAW + AMM*A.GetValue(IM,JM);

     if L <> 2 then
     begin
          I := IM;
          IQ := JM;
          AE := AMM;
 T1280:
          J := -Spalte[I];
          Spalte[I] := IQ;
          IQ := D[I];
          D[I] := round(AE);
          AE := IQ - AMM;
          IQ := I;
          if II = I then GOTO T1330;
     end
     else
     begin
          J := JM;
          IQ := IM;
          AE := AMM;
          AMM := -AMM;
     end;
     I := -Weg[J];
     Weg[J] := IQ;
     IQ := B[J];
     B[J] := round(AE);
     AE := IQ + AMM;
     IQ := J;
     if JJ = J then GOTO T1350;
     GOTO T1280;
 T1330:
     if Weg[J] >= 0 then GOTO T1360;
     I := -Weg[J];
     Weg[J] := I;
     B[J] := B[J] + round(AMM);
 T1350:
     if Spalte[I] < 0 then
     begin
          J := -Spalte[I];
          Spalte[I] := J;
          D[I] := D[I] - round(AMM);
          GOTO T1330;
     end;
 T1360:
     if LA = 2 then GOTO T680;
     LA := 2;
     AMM := -AMM;
     if L = 1 then
     begin
          J := JM;
          GOTO T1330;
     end;
     I := IM;
     GOTO T1350;

end; (* EOP iterationen      *)

end.
