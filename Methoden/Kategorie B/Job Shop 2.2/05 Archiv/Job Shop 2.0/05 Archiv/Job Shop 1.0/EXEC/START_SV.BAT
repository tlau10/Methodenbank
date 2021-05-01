@echo off
::
::  Wird das Programm von einem anderen Verzeichnis aus gestartet,
::  z.B. ueber OR-Info, so wird die IniDatei nicht mehr gefunden.
::
::  Abhilfe:
::  Start mit dieser Batchdatei. Das Jobshop-Verzeichnis wird so
::  zum aktuellen Verzeichnis.


::  Aktuelles Verzeichnis "setzen"
L:
cd\
cd Besf\Or_Mbank\jobshop\exec

::  STARTEN des Programms
job-shop.exe

