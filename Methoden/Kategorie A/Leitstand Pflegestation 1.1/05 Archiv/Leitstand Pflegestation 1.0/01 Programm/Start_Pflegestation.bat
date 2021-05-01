@echo off

:: Das Programm wird z.Z. einfacherweise nach c:\temp kopiert, 
:: da es die LP-Datei in das Programmverzeichnis schreibt.
:: Siehe unten!



:: --------------------------------------

:: Lokales Laufwerk
if exist D:\Neptun\Lehre\JDK1.3\nul  goto LocalJava

:: Global 
goto Weiter



:: --------------------------------------
:LocalJava

:: Pfaderweiterung
path=D:\Neptun\Lehre\JDK1.3\Bin;%path%

goto Weiter2



:: Start -----------------------------------

:Weiter

copy C:\Methodendatenbank\Methoden\Kategorie_A\Leitstand_Pflegestation\EXEC\*.jar          c:\temp
copy C:\Methodendatenbank\Methoden\Kategorie_A\Leitstand_Pflegestation\EXEC\*.exe          c:\temp
copy C:\Methodendatenbank\Methoden\Kategorie_A\Leitstand_Pflegestation\EXEC\ini.txt        c:\temp
copy C:\Methodendatenbank\Methoden\Kategorie_A\Leitstand_Pflegestation\EXEC\runLPSolve.bat c:\temp

c:
cd\
cd temp


:Weiter2

java -jar Pflegestation.jar

