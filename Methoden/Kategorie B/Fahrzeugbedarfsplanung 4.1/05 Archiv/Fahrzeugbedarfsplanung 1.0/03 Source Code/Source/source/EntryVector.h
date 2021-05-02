// EntryVector.h: Schnittstelle f�r die Klasse EntryVector.
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

	int AddEntry(Entry &entry);		//  Einen Eintrag hinzuf�gen
	void ChangeEntry(int id, Entry& entry);
									//  Den Eintrag mit der ID 'id' �ndern
	Entry GetEntry(int pos);		//  Den Eintrag an der Position 'pos' zur�ckliefern
	void GetEntryAndId(int pos, Entry& entry, int& id);	
									//  Den Eintrags an der Position 'pos' mit seiner ID zur�ckliefern
	Entry GetEntryWithId(int id);	//  Den Eintrag mit der ID 'id' liefern
	void DeleteEntryWithId(int id);	//  Eintrag mit der ID 'id' l�schen
	void DeleteAll(void);			//  Alle Eintr�ge l�schen
	int GetCount(void);				//  Anzahl der Eintr�ge zur�ckgeben

private:
	//  Die Liste wird als Map<ID, Eintrag> realisiert
	map<int,Entry> myVector;

	int mCurrentId;	//  Z�hler f�r die IDs

	//  Den Eintrag (Pair) an der Position 'position' liefern
	map<int, Entry>::value_type EntryAt(int position);
};

#endif // !defined(AFX_ENTRYVECTOR_H__80C57E86_ACE2_11D4_A43D_000374890932__INCLUDED_)
