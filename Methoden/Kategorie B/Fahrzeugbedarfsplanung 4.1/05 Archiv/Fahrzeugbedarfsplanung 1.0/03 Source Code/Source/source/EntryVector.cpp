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
	//  ID-Z�hler initialiseren
	mCurrentId = 1;
}


EntryVector::~EntryVector()
{

}


//  Einen Eintrag hinzuf�gen
int EntryVector::AddEntry(Entry &entry)
{
	//  Neuen Eintrag erzeugen und Element hinzuf�gen
	myVector[mCurrentId] = entry;
	++mCurrentId;

	//  Die eingef�gte ID wird zur�ckgegeben
	return mCurrentId - 1;
}


//  Den Eintrag mit der ID 'id �ndern
void EntryVector::ChangeEntry(int id, Entry& entry)
{
	//  Der eintrag wird ersetzt
	myVector[id] = entry;
}

//  Den Eintrag an der Position 'pos' zur�ckliefern
//  'pos' muss g�ltig sein !
Entry EntryVector::GetEntry(int pos)
{
	//  Den Eintrag holen und Wert zur�ckgeben
	map<int, Entry>::value_type entry = EntryAt(pos);
	return entry.second;
}


//  Den Eintrags an der Position 'pos' mit seiner ID zur�ckliefern
//  'pos' muss g�ltig sein !
void EntryVector::GetEntryAndId(int pos, Entry& entry, int& id)
{
	//  Den Eintrag holen, Wert und ID zur�ckgeben	
	map<int, Entry>::value_type entryAsPair = EntryAt(pos);
	
	entry = entryAsPair.second;
	id = entryAsPair.first;
}


//  Den Eintrag (Pair) an der Position 'position' liefern
//  'position' muss g�ltig sein !
map<int, Entry>::value_type EntryVector::EntryAt(int position)
{
	//  Den Iterator holen und positionieren
	map<int, Entry>::iterator iterator = myVector.begin();
	for (int i=0; i<position; ++i)
		++iterator;

	return *iterator;
}



//  Den Eintrag mit der ID 'id' liefern
//  'id' muss g�ltig sein !
Entry EntryVector::GetEntryWithId(int id)
{
	return myVector[id];
}


//  Anzahl der Eintr�ge zur�ckgeben
int EntryVector::GetCount()
{
	return myVector.size();
}


//  Eintrag mit der ID 'id' l�schen
void EntryVector::DeleteEntryWithId(int id)
{
	myVector.erase(id);
}


//  Alle Eintr�ge l�schen
void EntryVector::DeleteAll(void)
{
	myVector.clear();
}


