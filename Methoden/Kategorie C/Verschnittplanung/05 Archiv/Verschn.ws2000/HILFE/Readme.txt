Die Hilfe

Die Hilfe sit mit dem Tool Hilfeworkshop von Microsoft Visual STudio 6 
erstellt. 
Für die Ertellung benötigt man folgende dateien.

	Verscnhitt.RTF :- Ein WORD Dokument, welches die Inhalte 
                          (Beschreibungen ) der gesamten Hilfe beinhaltet.

	Verschn.cnt :- Diese datei beschreibt die Sturktur und Anordnung 
                       der Hilfesystems und wird mit Hilfe Workshop erstellt.

	Verscnitt.h :- Ein Header Datei zum definiton aller Stukturen 
                       (Inhaltsverzeichnis). Diese datei kann in alle C++ 
                       Programmen erstellt werden.
	
	Verscnitt.hpi :- beinhaltet aller der obengenannten dateien und ist
                         das notwndigste datei zur Erstellung der Hilfe Datei. 	
                         Es enthält ausserdem andere optionen (z.B. Hintergrund 
                         Einstellungen, Bilder dateien usw. ), welche in die
                         Hilfedatei eingebunden werden können. Diese datei wird mit dem Hilfeworkshop erstellt

	Bilderdateien. Verschiedene Bilderdateien (Bitmap Dateien) können zusätzlich erstellt werden und ensprechend 		eingefügt werden. Dabei müssen diese Bilderdateien von Bitmap in WMF Format umgewandelt werden. (einfach das suffix 		in WMF umschreiben)

Nachdem Erstellung dieser dateien kann die .hpi datei in HilfeWorkshop geöffnet und compiliert werden. So hat man dann die Hilfe DAtei erstellt.

Viel Spass.

Degu Dagne 20.1.2001
