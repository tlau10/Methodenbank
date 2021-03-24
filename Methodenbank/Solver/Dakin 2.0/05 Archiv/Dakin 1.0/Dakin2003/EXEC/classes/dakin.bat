@echo off

if exist D:\Neptun\Lehre\JDK1.3\nul  goto LocalJava

goto Weiter



:: --------------------------------------

:LocalJava

path=\Neptun\jdk1.3.1_02\bin;%path%



:: Start -----------------------------------

:Weiter


echo Dakin - graphische Ausgabe
echo --------------------------
echo .
echo Entwickelt von:
echo .
echo Rainer Faller
echo Henrik Feidner
echo Andreas Gossmann
echo Harald Woelfle
echo .
echo Verena Becker
echo Tobias Mueller


java -classpath "L:\Besf\methodenbank\solver\Dakin2003\EXEC\classes;L:\Besf\methodenbank\solver\Dakin2003\EXEC\classes\jcommon-0.7.1.jar;L:\Besf\methodenbank\solver\Dakin2003\EXEC\classes\jfreechart-0.9.4.jar" Dakin.dakin
