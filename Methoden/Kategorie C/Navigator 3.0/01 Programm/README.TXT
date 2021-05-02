Kurze Anmerkungen zum File: Navigat.ini

Über dieses File werden die wichtigsten Einstellungen getätigt. Hier die Einträge
im Einzelnen:

dbpath:
-------
Gibt den Pfad an in dem die Datenfiles für den Solver liegen. Diese Einstellung muss
auf c:\temp stehen.

lpsolvepath:
------------
Der Pfad zum Programm Lp-Solve

workingdir:
-----------
Das Arbeitsverzeichnis, ein Verzeichnis mit Schreibrechten. Defaultwert ist c:\temp. 
Wenn es nicht unbedingt nötig ist, ändern Sie dieses Verzeichnis bitte nicht.

perlpath:
---------
Der Pfad zum Perl-Interpreter

Mit rem beginnende Zeilen sind Kommentare. Leere Zeilen werden ignoriert. Die Bezeichner
(z.B. dbpaht,...) müssen klein geschrieben werden, da der Navigator 3-Parser wie bei Java
üblich Case-Sensitive ist.

Konstanz, Januar 2002

