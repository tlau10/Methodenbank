// Entry.cpp: Implementierung der Klasse Entry.
//
//////////////////////////////////////////////////////////////////////


#include "stdafx.h"
#include "Entry.h"

//////////////////////////////////////////////////////////////////////
// Konstruktion/Destruktion
//////////////////////////////////////////////////////////////////////

Entry::Entry()
{
	mFahrzeugNummer = 0;
}

Entry::Entry(string vonOrt, int vonStunde, int vonMinute, 
			 string bisOrt, int bisStunde, int bisMinute, 
			 int personen, int fahrzeugNummer/*= 0*/)
{
	Change(vonOrt,vonStunde,vonMinute,
		   bisOrt,bisStunde,bisMinute,personen);

	mFahrzeugNummer = fahrzeugNummer;
}

Entry::~Entry()
{

}

//////////////////////////////////////////////////////////////////////
// Operatoren
//////////////////////////////////////////////////////////////////////
void Entry::operator = (const Entry& entry)
{
	Change(entry.GetVonOrt(), entry.GetVonStunde(), entry.GetVonMinute(),
		   entry.GetBisOrt(), entry.GetBisStunde(), entry.GetBisMinute(),
		   entry.GetPersonen());

	mFahrzeugNummer = entry.mFahrzeugNummer;
}



void Entry::Change(string vonOrt, int vonStunde, int vonMinute,
				   string bisOrt, int bisStunde, int bisMinute,
				   int personen)
{
	mVonOrt = vonOrt;
	mVonStunde = vonStunde;
	mVonMinute = vonMinute;
	mBisOrt = bisOrt;
	mBisStunde = bisStunde;
	mBisMinute = bisMinute;
	mPersonen = personen;
	mFahrzeugNummer = 0;
}


string Entry::GetVonOrt() const
{
	return mVonOrt;
}

int Entry::GetVonStunde() const
{
	return mVonStunde;
}

int Entry::GetVonMinute() const
{
	return mVonMinute;
}


string Entry::GetBisOrt() const
{
	return mBisOrt;
}

int Entry::GetBisStunde() const
{
	return mBisStunde;
}

int Entry::GetBisMinute() const
{
	return mBisMinute;
}


int Entry::GetPersonen() const
{
	return mPersonen;
}




void Entry::SetFahrzeugNummer(int nummer)
{
	mFahrzeugNummer = nummer;
}

int Entry::GetFahrzeugNummer() const
{
	return mFahrzeugNummer;
}




