
Beschreibung der Klassen 

(zu lesen als Package(Ordner)\Java-Datei)

- createLp\CreateLP.java				-> erzeugen des LP-Ansatzes für den LP-solve
- createLP\CreateXML.java				-> erzeugen der XML-Datei für die MightyMightyLP
- de\htwg_konstanz\tpsolver\web\solver\WebSolver.java 	-> zugriff auf MightyMightyLP
- facade\Facade.java 					-> "Verbindung" Backend und GUI (siehe Swar-Vorlesung)
- lpSolver\SolverPath.java 				-> Pfad des Solvers einlesen
- lpSolver\LPSolve.java 				-> zugriff auf Solver
- serialize\DTOModell.java 				-> Objekt für das Speichern des vom Benuter eingegebenen Ansatzes
- serialize\SerializeModel.java 			-> Marshall und Unmarshall der DTOModell-Objekte
- webService\* 						-> aus XSD generierte Klassen für die Erzeugung des XML-Objekts -> wird von CreateXML.java verwendet
- gui\*							-> Gui-Klassen

Abgesehen von den Packages webservice und de\*, sowie der Klasse LPSolve, wurden alle Klassen von den Studierenden erstellt!