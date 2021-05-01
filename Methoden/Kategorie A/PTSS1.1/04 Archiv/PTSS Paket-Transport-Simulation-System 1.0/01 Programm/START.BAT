@echo off
::
::  Das Programm benötigt ein nicht schreibgeschütztes
::  Verzeichnis.
::  ..könnte man schon eleganger lösen!



::  Kopieren nach ...
xcopy C:\Methodendatenbank\Methoden\Kategorie_A\PTSS\daten\*.* c:\temp\ptss\daten\*.*
xcopy C:\Methodendatenbank\Methoden\Kategorie_A\PTSS\bin\*.*   c:\temp\ptss\bin\*.*
xcopy C:\Methodendatenbank\Methoden\Kategorie_A\PTSS\temp\*.*  c:\temp\ptss\temp\*.*

copy C:\Methodendatenbank\Methoden\Kategorie_A\PTSS\ptssw.exe c:\temp\ptss


::  Aktuelles Verzeichnis "setzen"
C:
cd\
cd temp\ptss


::  STARTEN des (Windows)-Programms
ptssw.exe

