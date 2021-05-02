unit CRT;
//  CRT Unit for DELPHI 32 bits
//  Copyright (C) 1998 - Pasquale Morvillo
//  Interface to PASW32.DLL
interface

uses System32;

{$I CRT.INC}

const
     CheckBreak : Boolean = True;     // Ctrl-C / Ctrl-break checking
     CheckEOF   : Boolean = False;    // Ctrl-Z checking
     DirectVideo: Boolean = True;     // dummy
     CheckSnow  : Boolean = True;     // dummy

var
     LastMode      : Word;            // Last screen text mode
     TextAttr      : Byte;            // Current text attributes
     WindMin,                         // Left-top corner
     WindMax       : Word;            // Right-bottom corner

procedure AssignCrt(var f: Text);
procedure ClrScr;
procedure ClrEol;
procedure Delay(MS: Cardinal);
procedure DelLine;
procedure GotoXY(X,Y : byte);
procedure HighVideo;
procedure InsLine;
function  KeyPressed: boolean;
procedure LowVideo;
procedure NormVideo;
procedure NoSound;
function  ReadKey: char;
procedure Sound(Hz: integer);
procedure TextBackground(Color: byte);
procedure TextColor(Color: byte);
procedure TextMode(Mode: smallint);
function  WhereX: byte;
function  WhereY: byte;
procedure Window(x1,y1,x2,y2: byte);

procedure SetCursorSize(Size: byte);
function  GetCursorSize: byte;
procedure ShowCursor;
procedure HideCursor;

implementation

procedure AssignCrt(var f: Text);
          external DLLname name 'AssignCrt';
procedure ClrScr;
          external DLLname name 'ClrScr';
procedure ClrEol;
          external DLLname name 'ClrEol';
procedure Delay(MS: Cardinal);
          external DLLname name 'Delay';
procedure DelLine;
          external DLLname name 'DelLine';
procedure GotoXY(X,Y : byte);
          external DLLname name 'GotoXY';
procedure HighVideo;
          external DLLname name 'HighVideo';
procedure InsLine;
          external DLLname name 'InsLine';
function  KeyPressed: boolean;
          external DLLname name 'KeyPressed';
procedure LowVideo;
          external DLLname name 'LowVideo';
procedure NormVideo;
          external DLLname name 'NormVideo';
procedure NoSound;
          external DLLname name 'NoSound';
function  ReadKey: char;
          external DLLname name 'ReadKey';
procedure Sound(Hz: integer);
          external DLLname name 'Sound';
procedure TextBackground(Color: byte);
          external DLLname name 'TextBackground';
procedure TextColor(Color: byte);
          external DLLname name 'TextColor';
procedure TextMode(Mode: smallint);
          external DLLname name 'TextMode';
function  WhereX: byte;
          external DLLname name 'WhereX';
function  WhereY: byte;
          external DLLname name 'WhereY';
procedure Window(x1,y1,x2,y2: byte);
          external DLLname name 'Window';

procedure SetCursorSize(Size: byte);
          external DLLname name 'SetCursorSize';
function  GetCursorSize: byte;
          external DLLname name 'GetCursorSize';
procedure ShowCursor;
          external DLLname name 'ShowCursor';
procedure HideCursor;
          external DLLname name 'HideCursor';
procedure InitCrtW32(pCB,pCE,pLM,pTA,pWM1,pWM2: pointer);
          external DLLname name 'InitCrtW32';

initialization
     AssignCrt(Input);  Reset(Input);
     AssignCrt(Output); ReWrite(Output);
     InitCrtW32(@CheckBreak,@CheckEOF,@LastMode,@TextAttr,@WindMin,@WindMax);
finalization
     NormVideo;
end.
