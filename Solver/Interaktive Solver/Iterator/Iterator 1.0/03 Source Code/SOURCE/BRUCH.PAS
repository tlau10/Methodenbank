unit Bruch;

{Implementation verschiedener Bruchoperationen
die Brüche müssen als Strings in der Form z.B.'48/7' vorliegen}

interface
function Zaehler(Bruch:string):longint;
function Nenner(Bruch:string):longint;
function Bruch_dividieren(Bruch1,Bruch2:string):string;
function Bruch_multiplizieren(Bruch1,Bruch2:string):string;
function Bruch_addieren(Bruch1,Bruch2:string):string;
function Bruch_subtrahieren(Bruch1,Bruch2:string):string;
function Bruch_kuerzen(Bruch:string):string;
function ggt(a,b:longint):longint;
function kgv(a,b:longint):longint;
function FloatinBruchUmwandeln(Float:string):string;


implementation

uses SysUtils,Dialogs;

function Zaehler(Bruch:string):longint;
var a     :byte;
begin
   a:=Pos('/',Bruch);
   if a=0 then
      a:=length(Bruch)+1;
   Zaehler:=strtoint(copy(Bruch,1,a-1));
end;


function Nenner(Bruch:string):longint;
var a     :byte;
begin
   a:=Pos('/',Bruch);
   if a=0 then
      Nenner:=1
   else
      Nenner:=strtoint(copy(Bruch,a+1,length(Bruch)));
end;

function Bruch_dividieren(Bruch1,Bruch2:string):string;
var
   s_zaehler,s_nenner :string;
   i_nenner,i_zaehler :longint;
begin
   if Zaehler(Bruch2)=0 then
      begin
      MessageDlg('Division durch Null!!!', mtInformation,[mbOk], 0);
      Bruch_dividieren:=Bruch1;
      end
   else
      begin
      i_zaehler:=Zaehler(Bruch1)*Nenner(Bruch2);
      i_nenner:=Nenner(Bruch1)*Zaehler(Bruch2);
      if (i_nenner<0) and (i_zaehler<0) then
         begin
         s_zaehler:=inttostr(i_zaehler*-1);
         s_nenner:=inttostr(i_nenner*-1);
         end
      else
         begin
         if (i_nenner<0) and (i_zaehler>=0) then
            begin
            s_zaehler:=inttostr(i_zaehler*-1);
            s_nenner:=inttostr(i_nenner*-1);
            end
         else
            begin
            s_zaehler:=inttostr(i_zaehler);
            s_nenner:=inttostr(i_nenner);
            end;
         end;
      Bruch_dividieren:=Bruch_kuerzen(s_zaehler+'/'+s_nenner);
      end;
end;

function Bruch_multiplizieren(Bruch1,Bruch2:string):string;
var
   s_zaehler,s_nenner :string;
begin
   s_zaehler:=inttostr(Zaehler(Bruch1)*Zaehler(Bruch2));
   s_nenner:=inttostr(Nenner(Bruch1)*Nenner(Bruch2));
   Bruch_multiplizieren:=Bruch_kuerzen(s_zaehler+'/'+s_nenner);
end;

function Bruch_addieren(Bruch1,Bruch2:string):string;
var
   s_zaehler,s_nenner :string;
   i_nenner:longint;
begin
   i_nenner:=kgv(Nenner(Bruch1),Nenner(Bruch2));
   s_zaehler:=inttostr(Zaehler(Bruch1)*i_nenner div Nenner(Bruch1)+Zaehler(Bruch2)*i_nenner div Nenner(Bruch2));
   s_nenner:=inttostr(i_nenner);
   Bruch_addieren:=Bruch_kuerzen(s_zaehler+'/'+s_nenner);
end;

function Bruch_subtrahieren(Bruch1,Bruch2:string):string;
var
   s_zaehler,s_nenner :string;
   i_nenner:longint;
begin
   i_nenner:=kgv(Nenner(Bruch1),Nenner(Bruch2));
   s_zaehler:=inttostr(Zaehler(Bruch1)*i_nenner div Nenner(Bruch1)-Zaehler(Bruch2)*i_nenner div Nenner(Bruch2));
   s_nenner:=inttostr(i_nenner);
   Bruch_subtrahieren:=Bruch_kuerzen(s_zaehler+'/'+s_nenner);
end;

function Bruch_kuerzen(Bruch:string):string;
var
   divisor     :longint;
   n,z         :longint;
begin
   if Zaehler(Bruch)<0 then
      divisor:=ggt(Zaehler(Bruch)*-1,Nenner(Bruch))
   else
      divisor:=ggt(Zaehler(Bruch),Nenner(Bruch));
   n:=Nenner(Bruch) div divisor;
   z:=Zaehler(Bruch) div divisor;
   if (n=1) or (z=0) then
      Bruch_kuerzen:=inttostr(z)
   else
      Bruch_kuerzen:=inttostr(z)+'/'+inttostr(n);
end;


function ggt(a,b:longint):longint;
var r :longint;
begin
  repeat
     r:=a mod b;
     a:=b;
     b:=r;
  until r=0;
  ggt:=a;
end;

function kgv(a,b:longint):longint;
var  za,zb:longint;
begin
   za:=a;
   zb:=b;
   while (za<>zb) do
      if (za<zb) then
         inc(za,a)
      else
         inc(zb,b);
   kgv:=za;
end;


function FloatinBruchUmwandeln(Float:string):string;
{Wandelt eine Realzahl die als String vorliegt (z.B. '1.1' oder '1,1')
in einen gültigen Bruch um, der gleichzeitig gekürzt wird}
var a,b,c               : word;
    x,y,z               : longint;
    l_zaehler,l_nenner  : longint;
begin
   a:=pos(',',Float);
   if a=0 then
      a:=pos('.',Float);
   if a>0 then
      begin
      x:=strtoint(copy(Float,1,a-1));
      y:=strtoint(copy(Float,a+1,length(Float)));
      b:=length(Float)-a;
      l_nenner:=1;
      for c:=1 to b do
         l_nenner:=l_nenner*10;
      if Float[1]='-' then            {Falls negativer Bruch}
         l_zaehler:=x*l_nenner-y
      else
         l_zaehler:=x*l_nenner+y;
      FloatinBruchUmwandeln:=Bruch_kuerzen(inttostr(l_zaehler)+'/'+inttostr(l_nenner));
      end
   else
      FloatinBruchUmwandeln:=Float;
end;

end.
