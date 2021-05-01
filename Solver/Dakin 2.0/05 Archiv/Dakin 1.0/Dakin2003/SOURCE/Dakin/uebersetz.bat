:: Uebersetzen und ins class-Dateien ins Exec-Verzeichnis verschieben


if exist Y:\nul goto Weiter

echo Laufwerk Y: setzen

net use Y: \\Neptun\JDK1.2 /yes


:Weiter

path=%path%;Y:\bin



javac CRectTree.java
javac DakinAusgabe.java
javac EingabeTabelle.java
javac matrix.java
javac dakin.java
javac TreeDakin.java
javac LPsolve.java

rem java dakin


del ..\exec\*.class
move *.class ..\exec\

