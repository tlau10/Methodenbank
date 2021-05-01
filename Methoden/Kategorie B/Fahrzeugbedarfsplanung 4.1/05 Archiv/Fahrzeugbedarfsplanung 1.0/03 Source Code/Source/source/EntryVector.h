// EntryVector.h: Schnittstelle für die Klasse EntryVector.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_ENTRYVECTOR_H__80C57E86_ACE2_11D4_A43D_000374890932__INCLUDED_)
#define AFX_ENTRYVECTOR_H__80C57E86_ACE2_11D4_A43D_000374890932__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include <map>
#include "Entry.h"


class EntryVector  
{
public:
	EntryVector();
	~EntryVector();

	int AddEntry(Entry &entry);		//  Einen Eintrag hinzufügen
	void ChangeEntry(int id, Entry& entry);
									//  Den Eintrag mit der ID 'id' ändern
	Entry GetEntry(int pos);		//  Den Eintrag an der Position 'pos' zurückliefern
	void GetEntryAndId(int pos, Entry& entry, int& id);	
									//  Den Eintrags an der Position 'pos' mit seiner ID zurückliefern
	Entry GetEntryWithId(int id);	//  Den Eintrag mit der ID 'id' liefern
	void DeleteEntryWithId(int id);	//  Eintrag mit der ID 'id' löschen
	void DeleteAll(void);			//  Alle Einträge löschen
	int GetCount(void);				//  Anzahl der Einträge zurückgeben

private:
	//  Die Liste wird als Map<ID, Eintrag> realisiert
	map<int,Entry> myVector;

	int mCurrentId;	//  Zähler für die IDs

	//  Den Eintrag (Pair) an der Position 'position' liefern
	map<int, Entry>::value_type EntryAt(int position);
};

#endif // !defined(AFX_ENTRYVECTOR_H__80C57E86_ACE2_11D4_A43D_000374890932__INCLUDED_)
