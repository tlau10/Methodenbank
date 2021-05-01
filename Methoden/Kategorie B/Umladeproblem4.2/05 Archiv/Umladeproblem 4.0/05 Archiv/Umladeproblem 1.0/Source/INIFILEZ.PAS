{*******************************************************}
{                                                       }
{       Delphi Visual Component Library                 }
{                                                       }
{       Copyright (c) 1995,97 Borland International     }
{                                                       }
{*******************************************************  jetzt DOS }


unit IniFilez;

{ R-}
{$F+}

interface

uses (*Windows, Classes; *)
     crt, dos;


type
  TIniFile = (* class(TObject) *)
             object
  (* private *)
    FFileName: string;
  (* public *)
    FileName:string;
    constructor Create( IniFileName: string);
    function ReadString( Section, Ident, Default: string): string;
    procedure WriteString( Section, Ident, Value: String);
    function ReadInteger( Section, Ident: string;
      Default: Longint): Longint;
    procedure WriteInteger( Section, Ident: string; Value: Longint);
    function ReadBool( Section, Ident: string;
      Default: Boolean): Boolean;
    procedure WriteBool( Section, Ident: string; Value: Boolean);
    (*
    procedure ReadSection( Section: string; Strings: TStrings);
    procedure ReadSections(Strings: TStrings);
    procedure ReadSectionValues(const Section: string; Strings: TStrings);
    procedure EraseSection(const Section: string);
    procedure DeleteKey(const Section, Ident: String);
      *)
    (* property FileName: string read FFileName;
       ..durch Variable ersetzt (s.o.) *)

    destructor Free;
  end;

implementation

(* uses  SysUtils,  Consts; *)


constructor TIniFile.Create( IniFileName: string);
begin
  FFileName := IniFileName;
  FileName  := IniFileName;
end;


function TIniFile.ReadString( Section, Ident, Default: string): string;
var
  Buffer: array[0..1023] of Char;
var
  d: text;
  Dateisatz: string;
  Sektion_gefunden, Eintrag_gefunden: boolean;
  pos01, laenge: integer;
begin
  (*
  SetString(Result, Buffer, GetPrivateProfileString(PChar(Section),
    PChar(Ident), PChar(Default), Buffer, SizeOf(Buffer), PChar(FFileName)));
    *)

  (*-- Dos-maessig *)
  assign(d,FFileName);
  reset(d);

  (* Vorbelegungen *)
  Sektion_gefunden := false;
  Eintrag_gefunden := false;
  ReadString := '';

  while not eof(d) do
    begin
      readln(d,Dateisatz);
      if copy(Dateisatz,1,1) <> ';' then      { ; ist das Kommentarzeichen }
        if Sektion_gefunden = true then
          if pos(Ident, Dateisatz) > 0 then
            begin
              Eintrag_gefunden := true;
              pos01 := pos('=', Dateisatz) + 1;
              laenge := length(Dateisatz) - pos01 + 1;

              ReadString := copy(Dateisatz, pos01, laenge);
            end;

        if pos('['+Section+']',Dateisatz) > 0 then
          Sektion_gefunden := true;
    end;

  close(d);
end;

procedure TIniFile.WriteString( Section, Ident, Value: string);
begin
(*
  if not WritePrivateProfileString(PChar(Section), PChar(Ident),
    PChar(Value), PChar(FFileName)) then
    raise Exception.CreateFmt(SIniFileWriteError, [FileName]);
    *)
end;

function TIniFile.ReadInteger( Section, Ident: string;
  Default: Longint): Longint;
var
  IntStr: string;
begin
  IntStr := ReadString(Section, Ident, '');
  if (Length(IntStr) > 2) and (IntStr[1] = '0') and
    ((IntStr[2] = 'X') or (IntStr[2] = 'x')) then
    IntStr := '$' + Copy(IntStr, 3, Maxint);
(*  Result := StrToIntDef(IntStr, Default);  *)
end;

procedure TIniFile.WriteInteger( Section, Ident: string; Value: Longint);
begin
(*
  WriteString(Section, Ident, IntToStr(Value));
  *)
end;

function TIniFile.ReadBool( Section, Ident: string;
  Default: Boolean): Boolean;
begin
  ReadBool := ReadInteger(Section, Ident, Ord(Default)) <> 0;
end;

procedure TIniFile.WriteBool( Section, Ident: string; Value: Boolean);
const
  Values: array[Boolean] of string = ('0', '1');
begin
  WriteString(Section, Ident, Values[Value]);
end;

(*
procedure TIniFile.ReadSections(Strings: TStrings);
const
  BufSize = 8192;
var
  Buffer, P: PChar;
begin
  GetMem(Buffer, BufSize);
  try
    Strings.BeginUpdate;
    try
      Strings.Clear;
      if GetPrivateProfileString(nil, nil, nil, Buffer, BufSize,
        PChar(FFileName)) <> 0 then
      begin
        P := Buffer;
        while P^ <> #0 do
        begin
          Strings.Add(P);
          Inc(P, StrLen(P) + 1);
        end;
      end;
    finally
      Strings.EndUpdate;
    end;
  finally
    FreeMem(Buffer, BufSize);
  end;
end;
*)

(*
procedure TIniFile.ReadSection(const Section: string; Strings: TStrings);
const
  BufSize = 8192;
var
  Buffer, P: PChar;
begin
  GetMem(Buffer, BufSize);
  try
    Strings.BeginUpdate;
    try
      Strings.Clear;
      if GetPrivateProfileString(PChar(Section), nil, nil, Buffer, BufSize,
        PChar(FFileName)) <> 0 then
      begin
        P := Buffer;
        while P^ <> #0 do
        begin
          Strings.Add(P);
          Inc(P, StrLen(P) + 1);
        end;
      end;
    finally
      Strings.EndUpdate;
    end;
  finally
    FreeMem(Buffer, BufSize);
  end;
end;   *)

(*
procedure TIniFile.ReadSectionValues(const Section: string; Strings: TStrings);
var
  KeyList: TStringList;
  I: Integer;
begin
  KeyList := TStringList.Create;
  try
    ReadSection(Section, KeyList);
    Strings.BeginUpdate;
    try
      for I := 0 to KeyList.Count - 1 do
        Strings.Values[KeyList[I]] := ReadString(Section, KeyList[I], '');
    finally
      Strings.EndUpdate;
    end;
  finally
    KeyList.Free;
  end;
end;
  *)
  (*
procedure TIniFile.EraseSection(const Section: string);
begin
  if not WritePrivateProfileString(PChar(Section), nil, nil,
    PChar(FFileName)) then
    raise Exception.CreateFmt(SIniFileWriteError, [FileName]);
end;
*)

(*
procedure TIniFile.DeleteKey(const Section, Ident: String);
begin
  WritePrivateProfileString(PChar(Section), PChar(Ident), nil,
     PChar(FFileName));
end;   *)

destructor TIniFile.Free;
begin

end;

end.
