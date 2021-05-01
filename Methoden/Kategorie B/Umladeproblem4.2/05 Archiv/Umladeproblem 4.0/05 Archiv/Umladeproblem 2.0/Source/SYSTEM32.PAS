unit SYSTEM32;
//  SYSTEM32 Unit integration for DELPHI 
//  Copyright (C) 1998 - Pasquale Morvillo
//  Interface to PASW32.DLL

//  Note: in this unit are included only procedures/functions missed in
//        Delphi System unit respect to BP system unit.

interface

const
     DLLname='PASW32.DLL';
     
function  CSeg: Word;
function  DSeg: Word;
function  SSeg: Word;
function  SPtr: Word;
function  MemAvail: Integer;
function  MaxAvail: Integer;
procedure Mark(var p: pointer);
procedure Release(var p: pointer);

implementation

uses Windows;

function  CSeg: Word;
          external DLLname name 'CSeg';
function  DSeg: Word;
          external DLLname name 'DSeg';
function  SSeg: Word;
          external DLLname name 'SSeg';
function  SPtr: Word;
          external DLLname name 'SPtr';
function  MemAvail: Integer;
          external DLLname name 'MemAvail';
function  MaxAvail: Integer;
          external DLLname name 'MaxAvail';
procedure Mark(var p: pointer);
          external DLLname name 'Mark';
procedure Release(var p: pointer);
          external DLLname name 'Release';

function GetMM: TMemoryManager;
          external DLLname name 'GetMM';

// Don't remove the following code!!!
var
     OldMM: TMemoryManager;
initialization
     GetMemoryManager(OldMM);
     SetMemoryManager(GetMM);
finalization
     SetMemoryManager(OldMM);
end.
