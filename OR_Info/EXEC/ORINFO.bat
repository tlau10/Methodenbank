@ECHO OFF

SET TEMP_FOLDER=C:\Temp
SET TEMP_POOL=C:\Users\public\documents\MethodenbankDaten
SET METHODENBANK_POOL=L:\BESF\methodenbank\MethodenbankDaten
SET PROGRAM=L:\BESF\OR_Info\EXEC\OR_INFO.EXE

:START

@REM Pruefe ob Verzeichnis schon vorhanden
IF not exist %TEMP_POOL% xcopy %METHODENBANK_POOL%\*.* %TEMP_POOL%\ /E

@REM Starte Programm
START %PROGRAM%

:ENDE

