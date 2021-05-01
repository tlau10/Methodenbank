unit Feld;

(*
     Feld (Array) - Unit.

     Dieses Unit stellt eine Klasse zur Verfuegung mit dem ein
     eindimensionales Feld von Cardinal-Zahlen verwaltet werden
     kann.

*)

interface

type
    TIntArray = class(TObject)
      // Variablen
      xDim: Word;
      Feld: array of Cardinal;
      // Methoden
      constructor Dim(Size: Word);
      destructor Kill();
      procedure SetValue(Index: Word; Val: Cardinal);
      function  GetValue(Index: Word): Cardinal;
      procedure Switch(One, Two: Word);
    end;

implementation

constructor TIntArray.Dim(Size: Word);
(*
     Erzeugen des eindimensionalen Feldes

     Uebergabe:   Wert mit der Groesse des Feldes
*)
var i: Word;
begin
     inherited Create();
     SetLength(Feld, Size);
     xDim := Size;

     // Fuellen des Feldes mit Nullen *0*
     for i:= 0 to xDim-1 do
         Feld[i] := 0;
end;

destructor TIntArray.Kill();
(*
     Freigeben des eindimensionalen Feldes
*)
begin
     Feld := nil;
     inherited Destroy();
end;

procedure TIntArray.SetValue(Index: Word; Val: Cardinal);
(*
     Methode zum Speichern eines Wertes im Feld

     Uebergabe:   Feldindex
                  Wert
*)
begin
     Feld[Index-1] := Val;
end;

function TIntArray.GetValue(Index: Word): Cardinal;
(*
     Methode zum Lesen eines Wertes im Feld

     Uebergabe:   Feldindex
     Rueckgabe:   Wert
*)
begin
     result := Feld[Index-1];
end;

procedure TIntArray.Switch(One, Two: Word);
(*
     Methode zum Tauschen zweier Feldwerte

     Uebergabe:   Feldindex des einen Wertes
                  Feldindex des anderen Wertes
*)
var Temp: Cardinal;
begin
     Temp := Feld[One-1];
     Feld[One-1] := Feld[Two-1];
     Feld[Two-1] := Temp;
end;

end.
