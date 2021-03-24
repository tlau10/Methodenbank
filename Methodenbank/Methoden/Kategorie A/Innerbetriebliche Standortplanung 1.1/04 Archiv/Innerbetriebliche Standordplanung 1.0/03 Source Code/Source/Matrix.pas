unit Matrix;

(*
     Matrix - Unit.

     Dieses Unit stellt eine Klasse zur Verfuegung mit dem ein
     zweidimensionales Feld von Cardinal-Zahlen verwaltet werden
     kann.

*)

interface

type
    TIntMatrix = class(TObject)
      // Variablen
      xDim, yDim: Word;
      Matrix: array of array of Int64;
      // Methoden
      constructor Dim(xSize, ySize: Word);
      destructor Kill();
      procedure SetValue(xIndex, yIndex: Word; Val: Int64);
      function  GetValue(xIndex, yIndex: Word): Int64;
    end;

implementation

constructor TIntMatrix.Dim(xSize, ySize: Word);
(*
     Erzeugen des zweidimensionalen Feldes

     Uebergabe:   Wert mit der Groesse des Feldes
*)
var i,j: Word;
begin
     inherited Create();
     SetLength(Matrix, xSize, ySize);
     xDim := xSize;
     yDim := ySize;

     // Fuellen des Feldes mit Nullen *0*
     for i:= 0 to xDim-1 do
         for j:= 0 to yDim-1 do
              Matrix[i,j] := 0;
end;

destructor TIntMatrix.Kill();
(*
     Freigeben des zweidimensionalen Feldes
*)
begin
     Matrix := nil;
     inherited Destroy();
end;

procedure TIntMatrix.SetValue(xIndex, yIndex: Word; Val: Int64);
(*
     Methode zum Speichern eines Wertes im Feld

     Uebergabe:   Feldindex x-Achse
                  Feldindex y-Achse
                  Wert
*)
begin
     Matrix[xIndex-1, yIndex-1] := Val;
end;

function TIntMatrix.GetValue(xIndex, yIndex: Word): Int64;
(*
     Methode zum Lesen eines Wertes im Feld

     Uebergabe:   Feldindex x-Achse
                  Feldindex y-Achse
     Rueckgabe:   Wert
*)
begin
     result := Matrix[xIndex-1, yIndex-1];
end;

end.
