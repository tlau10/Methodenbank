{************************************************}
{                                                }
{   Datei-™ffnen-Programm                        }
{                                                }
{   Compiler: Turbo Pascal 6.0                   }
{                                                }
{************************************************}

{ X+}

program test6;  (* ..wird als f_open.exe genutzt *)

uses
  Dos, Objects, Drivers, Views, Menus, Dialogs, StdDlg, App;

const
  cmFileOpen  = 100;

type
  { TMyApp }
  PMyApp = ^TMyApp;
  TMyApp = object(TApplication)
    constructor Init;
    procedure HandleEvent(var Event: TEvent); virtual;
  end;


{ TMyApp }

constructor TMyApp.Init;
var
  event: TEvent;
begin
  TApplication.Init;

  event.what := evCommand;
  event.command := cmFileOpen;
  TMyApp.HandleEvent(event);
end;

procedure TMyApp.HandleEvent(var Event: TEvent);

   procedure FileOpen;
   var
     D: PFileDialog;
     FileName: PathStr;
     JS_AktDat: text;
   begin
     (* Auf Datenverzeichnis setzten *)
     ChDir(ParamStr(1));

     D := PFileDialog(ValidView(New(PFileDialog,
          Init('*.*', 'J O B S H O P',
          '~N~ame', fdOpenButton, 100))));

     if D <> nil then
     begin
       (* cmQuit steht nicht zur Auswahl -> nur so funktioniert es ! *)
       if Desktop^.ExecView(D) <> cmQuit then
       begin
         D^.GetFileName(FileName);

         if ParamStr(2) = '' then
           Assign(JS_AktDat, 'c:\temp\js_akt.dat')
         else
           Assign(JS_AktDat, ParamStr(2));

         Rewrite(JS_AktDat);
         writeln(JS_AktDat, '[Dateien]');
         writeln(JS_AktDat, 'Eingabedatei=' + FileName);
         close(JS_AktDat);

       end;
       Dispose(D, Done);
      end;
   end;

begin
  TApplication.HandleEvent(Event);
  case Event.What of
    evCommand:
      begin
        case Event.Command of
          cmFileOpen: FileOpen;
        else
          Exit;
        end;
        ClearEvent(Event);
      end;
  end;
end;


var
  MyApp: TMyApp;

begin
  MyApp.Init;
  MyApp.Run;
  MyApp.Done;
end.
