
//-----------------------------------------//
// Implementierung einer verketteten Liste //
//-----------------------------------------//

#pragma hdrstop

#include "verketteteListe.h"

#include <iostream>
using namespace std;

// Konstruktor ----------------------------------------
Liste::Liste()
{
	head = NULL;
}

// Destruktor -----------------------------------------
Liste::~Liste()
{
	while( head != NULL )	//Löschen der gesatmen Liste
	{
		Node *tmp = head;
		head = head->next;
		delete tmp;
	}
}

// get ------------------------------------------------
double Liste::getElement(int index)
{
        Node *tmp = head;
        for( int i = 1; i < index; i++ )
        {
                tmp = tmp->next;
        }
        return tmp->data;
}

// length ----------------------------------------------
int Liste::lenght()
{
	Node *tmp = head;
	int zaehl = 0;
	for( ; tmp != NULL; )
	{
		zaehl++;
		tmp = tmp->next;
	}
	return zaehl;
}

// add ----------------------------------------------------------------------
bool Liste::add( double elem )
{
	//Speicher für ein neues Kästchen anlegen
	Node *app = new Node;
	app->data = elem;	//data den Wert von elem zuweisen

	//kein Element enthalten
	if( head == NULL )
	{
		app->next = head;
		head = app;
		return true;
	}
	else
	{
		Node *tmp = head;
		for( ; tmp != NULL; )	//laufe bis tmp NULL ist
		{
			if( tmp->next == NULL )	//das letzte Element zeigt auf NULL
			{
				//tmp enthält das letzte Elemen
				tmp->next = app;
				app->next = NULL;      
				return true;
			}
			tmp = tmp->next;
		}
	}
	return true;
}
//---------------------------------------------------------------------------

#pragma package(smart_init)
