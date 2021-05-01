@echo off
::
::  Das Programm ist nur in einem nicht schreibgeschützten
::  Verzeichnis lauffähig.

::  Kopieren nach ...
xcopy *.* c:\temp\sensiOR\*.*

::  Aktuelles Verzeichnis "setzen"
C:
cd \
cd temp\sensiOR


::  STARTEN des Programms
sensiOR.exe

