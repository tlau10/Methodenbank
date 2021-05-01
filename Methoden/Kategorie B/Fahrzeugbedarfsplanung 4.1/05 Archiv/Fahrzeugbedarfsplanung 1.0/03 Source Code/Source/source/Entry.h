// Entry.h: Schnittstelle für die Klasse Entry.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_ENTRY_H__80C57E85_ACE2_11D4_A43D_000374890932__INCLUDED_)
#define AFX_ENTRY_H__80C57E85_ACE2_11D4_A43D_000374890932__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000


#include <string>
using namespace std;

class Entry  
{
public:
	Entry();
	Entry(string vonOrt, int vonStunde, int vonMinute, 
		  string bisOrt, int bisStunde, int bisMinute, 
		  int personen, int fahrzeugNummer = 0);
	~Entry();

	void Change(string vonOrt, int vonStunde, int vonMinute,
				string bisOrt, int bisStunde, int bisMinute,
				int personen);
	string GetVonOrt(void) const;
	int GetVonStunde(void) const;
	int GetVonMinute(void) const;
	string GetBisOrt(void) const;
	int GetBisStunde(void) const;
	int GetBisMinute(void) const;
	int GetPersonen(void) const;
	
	void SetFahrzeugNummer(int nummer);
	int GetFahrzeugNummer() const;

	//  Operatoren
	void operator = (const Entry& entry);


private:
	string mVonOrt;
	int mVonStunde;
	int mVonMinute;
	string mBisOrt;
	int mBisStunde;
	int mBisMinute;
	int mPersonen;

	int mFahrzeugNummer;

};

#endif // !defined(AFX_ENTRY_H__80C57E85_ACE2_11D4_A43D_000374890932__INCLUDED_)
