@echo off
::
::  Das Programm ist nur in einem nicht schreibgeschützten
::  Verzeichnis lauffähig. Deshalb die Umkopiererei.



::  Kopieren nach ...
xcopy verschn*.* c:\temp\verschnitt\*.*

copy *.vrs c:\temp\verschnitt


::  Aktuelles Verzeichnis "setzen"
C:
cd\
cd temp\verschnitt


::  STARTEN des Programms
verschnitt2002.exe

