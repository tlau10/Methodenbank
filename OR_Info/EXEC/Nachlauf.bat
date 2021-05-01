@echo off

:: Abschlussbehandlung
::
:: Akt. werden die temp. Dateien der OR-Methodenbankprogramme 
:: im Arbeitsverzeichnis geloescht


del c:\temp\*.lp
del c:\temp\*.clp
del c:\temp\*.mps
del c:\temp\*.sav
del c:\temp\*.out
del c:\temp\*.bat
del c:\temp\*.dat



del c:\temp\*.pdm

del c:\temp\solver*.*
