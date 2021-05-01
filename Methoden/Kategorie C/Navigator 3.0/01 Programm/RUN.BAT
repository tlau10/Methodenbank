@echo off

if exist D:\Neptun\Lehre\JDK1.3\nul goto SetzeJava

goto Weiter



:SetzeJava

path=D:\Neptun\Lehre\JDK1.3\bin;%path%



:Weiter

echo Navigator 3
echo -----------
echo .
echo Entwickelt von:
echo .
echo Christian Oswald
echo Tobias Berlinger
echo und verbessert von Ahmed Ould Mohamed Lemine
echo .


echo Naviagator 3 wird gestartet

java -jar navigator3.jar
