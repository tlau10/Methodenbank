@echo off

::  Die benötigten Dateien werden nach c:\temp\Krankenhausanalyse kopiert!
::

xcopy C:\Methodendatenbank\Methoden\Kategorie_A\KH_Analyse\*.exe c:\temp\Krankenhausanalyse\
xcopy C:\Methodendatenbank\Methoden\Kategorie_A\KH_Analyse\*.xls c:\temp\Krankenhausanalyse\

::  Aktuelles Verzeichnis "setzen"
C:
cd\
cd temp\Krankenhausanalyse
start explorer c:\temp\Krankenhausanalyse\

::  STARTEN des Programms
Prototyp2.xls

