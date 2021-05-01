@ECHO OFF

SET LP_FILE="C:\Methodenbank\Methoden\Kategorie A\Fallpauschalenoptimierung KH 2.0\01 Programm\LP_SOLVE.EXE"
SET TEMP_FOLDER="C:\Temp\FOP"
:SET PROGRAM="C:\Methodenbank\Methoden\Kategorie A\Fallpauschalenoptimierung KH 2.0\01 Programm\Fallpauschale_KH.exe"

:START

@REM Pruefe ob Solver schon vorhanden
IF not exist %TEMP_FOLDER% mkdir %TEMP_FOLDER%
IF not exist %TEMP_FOLDER%\%LP_FILE% copy %LP_FILE% %TEMP_FOLDER%

@REM Starte Programm
START "" "C:\Methodenbank\Methoden\Kategorie A\Fallpauschalenoptimierung KH 2.0\01 Programm\Fallpauschale_KH.exe"

:ENDE
