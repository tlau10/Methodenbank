unit fernform;

interface

uses
  Windows, Messages, SysUtils, Classes, Graphics, Controls, Forms, Dialogs,
  ExtCtrls, StdCtrls,fern_s,ComCtrls,util;

type
  Ttfernform = class(TForm)
    PaintBox1: TPaintBox;
    procedure init(Sender: TObject);
    procedure keyaction(Sender: TObject; var Key: Word;
      Shift: TShiftState);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

var tfernform: Ttfernform;
    pressed:BOOLEAN;

    implementation

{$R *.DFM}

procedure Ttfernform.init(Sender: TObject);
begin
  tfernform.windowstate:=wsmaximized; tfernform.show; tfernform.visible:=FALSE; setcolor(0);
end;
procedure Ttfernform.keyaction(Sender: TObject; var Key: Word;
  Shift: TShiftState);
begin
  pressed:=TRUE;
end;
end.

