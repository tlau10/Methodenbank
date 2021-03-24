@echo off


if "%JAVA_HOME%" == "" goto :SetzeVar

goto :Start


:: --------------------------------------
:SetzeVar

if exist D:\Neptun\Lehre\JDK1.3\nul  set JAVA_HOME=D:\Neptun\Lehre\JDK1.3



echo %JAVA_HOME%

:: --------------------------------------
:Start

%JAVA_HOME%\bin\java -jar Pflegestation.jar
