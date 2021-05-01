@echo off
rem ***************************************************************
rem 
rem Projekt "Verbesserung von TransOp" zu TR-Optimizer
rem im Rahmen der Vorlesung "Anwendung der betrieblichen Systemforschung"
rem unter Betreuung von Herrn Professor Michael Grütz
rem
rem copyright by Ekkehard Will, Oliver Baumann, Gunther Koch
rem version 1.0, WS 02/03, WI 8
rem Konstanz im Januar 2003
rem
rem ***************************************************************
rem
rem
rem /**
rem Bekanntmachen der jdk-Libraries und des Path für die Binaries (.exe)-Dateien
rem z.B. für java.exe
rem
rem **/


set CLASSPATH=jdk1.4\jre\lib\;jdk1.4\lib\;jdk1.4\beandt.jar;jdk1.4\dx.jar;jdk1.4\jbcl.jar;TR-Optimizer.jar


set PATH=%PATH%;jdk1.4\bin\

rem /**
rem starten der Anwendung
rem
rem **/

start javaw -classpath %CLASSPATH%  TROptimizer