unit SysUtilz;

(*  Pedant "fuer" Delphi (mit Z geschrieben!)  --> noch nicht fertig


    Compiler:       Turbo Pascal 6.0
    Letzter Stand:  09.04.1999
*)

INTERFACE

    function ExtractFilePath(FileName: string): string;
    function ExtractFileName(FileName: string): string;

    function UpperCase(S: string): string;
    function LowerCase(S: string): string;
(*

*)

IMPLEMENTATION

uses Crt, Dos;


function ExtractFilePath(FileName: string): string;
var
  D: DirStr;
  N: NameStr;
  E: ExtStr;
begin
  FSplit(FileName, D, N, E);
  if D[length(D)] = '\' then
    ExtractFilePath := copy(D, 1, length(D)-1)
  else
    ExtractFilePath := D;
end;


function ExtractFileName(FileName: string): string;
var
  D: DirStr;
  N: NameStr;
  E: ExtStr;
begin
  FSplit(FileName,D,N,E);
  ExtractFileName := N + E;
end;


function UpperCase(S: string): string;
var
  i      : byte;
begin
  UpperCase := S;

  for i := 1 to length(S) do
  begin
    if S[i] = '�' then UpperCase[i] := '�'
    else
    if S[i] = '�' then UpperCase[i] := '�'
    else
    if S[i] = '�' then UpperCase[i] := '�'
    else
      UpperCase[i] := UpCase(S[i]);
  end;
end;


function LowerCase(S: string): string;
var
  OrdZahl, i : byte;
begin
  LowerCase := S;

  for i := 1 to length(S) do
  begin
    OrdZahl := ord(S[i]);

    if OrdZahl in [65..90] then
      LowerCase[i] := Chr(OrdZahl + 32)
    else
    if S[i] = '�' then LowerCase[i] := '�'
    else
    if S[i] = '�' then LowerCase[i] := '�'
    else
    if S[i] = '�' then LowerCase[i] := '�';
  end;
end;


end.
