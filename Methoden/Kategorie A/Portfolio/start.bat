@echo off


:: --------------------------------------

:: Lokales Laufwerk
if exist D:\Neptun\Lehre\JDK1.3\nul  goto LocalJava

:: Global 
goto Weiter



:: --------------------------------------
:LocalJava

:: Pfaderweiterung
path=D:\Neptun\Lehre\JDK1.3\Bin;%path%




:: Start -----------------------------------
:Weiter


java -jar Portfolio.jar



