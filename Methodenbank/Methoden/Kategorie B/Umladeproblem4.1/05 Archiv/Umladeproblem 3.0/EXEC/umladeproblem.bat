@ECHO OFF

SET LP_FILE=L:\BESF\Solver\LP_Solve\Exec\LP_SOLVE.EXE
SET TEMP_FOLDER=C:\temp
SET PROGRAM=umladeproblem.swf

:START

@REM Pruefe ob Solver schon vorhanden
IF not exist %TEMP_FOLDER%\%LP_FILE% copy %LP_FILE% %TEMP_FOLDER%

@REM Starte Programm
START explorer %PROGRAM%

:ENDE
