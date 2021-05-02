// EntryVector.cpp: Implementierung der Klasse EntryVector.
//
//////////////////////////////////////////////////////////////////////



#include "stdafx.h"
#include "EntryVector.h"


//////////////////////////////////////////////////////////////////////
// Konstruktion/Destruktion
//////////////////////////////////////////////////////////////////////

EntryVector::EntryVector()
{
	//  ID-Zähler initialiseren
	mCurrentId = 1;
}


EntryVector::~EntryVector()
{

}


//  Einen Eintrag hinzufügen
int EntryVector::AddEntry(Entry &entry)
{
	//  Neuen Eintrag erzeugen und Element hinzufügen
	myVector[mCurrentId] = entry;
	++mCurrentId;

	//  Die eingefügte ID wird zurückgegeben
	return mCurrentId - 1;
}


//  Den Eintrag mit der ID 'id ändern
void EntryVector::ChangeEntry(int id, Entry& entry)
{
	//  Der eintrag wird ersetzt
	myVector[id] = entry;
}

//  Den Eintrag an der Position 'pos' zurückliefern
//  'pos' muss gültig sein !
Entry EntryVector::GetEntry(int pos)
{
	//  Den Eintrag holen und Wert zurückgeben
	map<int, Entry>::value_type entry = EntryAt(pos);
	return entry.second;
}


//  Den Eintrags an der Position 'pos' mit seiner ID zurückliefern
//  'pos' muss gültig sein !
void EntryVector::GetEntryAndId(int pos, Entry& entry, int& id)
{
	//  Den Eintrag holen, Wert und ID zurückgeben	
	map<int, Entry>::value_type entryAsPair = EntryAt(pos);
	
	entry = entryAsPair.second;
	id = entryAsPair.first;
}


//  Den Eintrag (Pair) an der Position 'position' liefern
//  'position' muss gültig sein !
map<int, Entry>::value_type EntryVector::EntryAt(int position)
{
	//  Den Iterator holen und positionieren
	map<int, Entry>::iterator iterator = myVector.begin();
	for (int i=0; i<position; ++i)
		++iterator;

	return *iterator;
}



//  Den Eintrag mit der ID 'id' liefern
//  'id' muss gültig sein !
Entry EntryVector::GetEntryWithId(int id)
{
	return myVector[id];
}


//  Anzahl der Einträge zurückgeben
int EntryVector::GetCount()
{
	return myVector.size();
}


//  Eintrag mit der ID 'id' löschen
void EntryVector::DeleteEntryWithId(int id)
{
	myVector.erase(id);
}


//  Alle Einträge löschen
void EntryVector::DeleteAll(void)
{
	myVector.clear();
}


