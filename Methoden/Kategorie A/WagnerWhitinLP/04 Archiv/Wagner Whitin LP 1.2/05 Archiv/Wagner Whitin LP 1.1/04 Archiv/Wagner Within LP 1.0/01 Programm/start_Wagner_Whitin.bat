@echo off

:: --------------------------------------
:: Starten von POLO
:: --------------------------------------


if exist Y:\nul goto Weiter


:: --------------------------------------

if exist D:\Merkur\Lehre\JDK1.2\nul  goto LocalJava

goto Weiter


:: LocalJava ----------------------------

:LocalJava
subst Y: \\Merkur\JDK1.2.2

echo Setze Pfad
path=y:\bin;%path%


:: Start -----------------------------------

:Weiter

java -classpath . plo



:: LW wieder aufheben -----------------------

if exist \\Merkur\JDK1.2.2\nul  subst Y: /D

