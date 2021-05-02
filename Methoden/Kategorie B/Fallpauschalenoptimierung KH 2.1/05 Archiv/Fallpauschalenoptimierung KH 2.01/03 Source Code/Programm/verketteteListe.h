
//-----------------------------------------//
// Implementierung einer verketteten Liste //
//-----------------------------------------//

#ifndef verketteteListeH
#define verketteteListeH

//Sammlung ganzer Zahlen
class Liste
{
public:
	//Initialisiert leere Liste
	Liste();

	//Gibt Speicher der Liste frei
	~Liste();

	// liefert die Anzahl der Listenelemente zurück
	int lenght( );

        //liefert Elemente der Reihe nach
        double getElement( int index );

	//nimmt elem am Listenende in die Liste auf; liefert true wenn erfolgreich
	bool add( double elem );

private:
	//Implementierung einer Liste ganzer Zahlen
	typedef struct node{
		double data;
		struct node *next;
	} Node;
	Node *head;	//Zeiger auf Listenanfang, Zeiger auf Struktur node
};
//---------------------------------------------------------------------------
#endif
