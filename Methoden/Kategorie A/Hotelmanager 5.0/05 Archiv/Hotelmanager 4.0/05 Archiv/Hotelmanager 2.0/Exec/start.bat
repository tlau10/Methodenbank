@echo off

if exist D:\Neptun\Lehre\JDK1.3\nul  goto LocalJava

goto Weiter



:: --------------------------------------

:LocalJava

path=D:\Neptun\Lehre\JDK1.3\bin;%path%



:: Start -----------------------------------

:Weiter

echo Hotelmanager V 1.0
echo --------------------------
echo .
echo Entwickelt von:
echo .
echo Volker Wohlleber
echo Oliver Schraag
echo Florian Raiber
echo .

rem java -jar %Hotelmanagerlw%:\Hotelmanager\Hotelmanager.jar

rem :temp
java -jar Hotelmanager.jar

