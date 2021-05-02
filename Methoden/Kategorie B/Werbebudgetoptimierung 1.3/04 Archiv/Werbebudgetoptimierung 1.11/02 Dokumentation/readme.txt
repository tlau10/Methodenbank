******** WerbeBudgetApplication **********

Das Programm kann direkt von der CD gestartet werden.
Hierzu öffnet man ein Konsolenfenster, wechselt auf
das CD-ROM - Laufwerk und ruft die .bat - Datei
startwba auf.
Voraussetzung hierfür ist, dass das Verzeichnis

	C:\Temp

existiert und freigegeben ist.

Verwerndet wurde die JRE 1.6!
Sollte aber auch ab JRE 1.3 lauffähig sein.


Inhalt der CD:
==============

Datei werbebudget.jar		- Ausfürbare Jar Datei

Datei solver.bat		- startet den Solver, wird vom Programm aufgerufen

Verzeichnis source		- enthält den Quellcode
Verzeichnis source\classes 	- enthält die Binarys (Class Files)

Verzeichnis Solver		- enthält den Solver LP_SOLVE

Verzeichnis Daten		- Beispiel Daten im *.wbo Format.

Installation auf der Festplatte:
================================

1. Kopieren der Dateien im Verzeichnis classes in das
   gewünschte Verzeichnis auf der Festplatte.

2. Kopieren der Datei solver.bat und des Verzeichnisses Solver
   in das gleiche Verzeichnis wie die werbebudget.jar.