// IniFile.cpp: Implementierung der Klasse IniFile.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"

#include "IniFile.h"

#include <fstream>
using namespace std;




#ifdef _DEBUG
#undef THIS_FILE
static char THIS_FILE[]=__FILE__;
#define new DEBUG_NEW
#endif


//////////////////////////////////////////////////////////////////////
// Konstruktion/Destruktion
//////////////////////////////////////////////////////////////////////
IniFile::IniFile(const char* fileName/*="FeBePlan.ini"*/)
{
	mIniFileName = fileName;
}

IniFile::~IniFile()
{
}



//////////////////////////////////////////////////////////////////////
//  Daten lesen und schreiben
//////////////////////////////////////////////////////////////////////
bool IniFile::ReadData()
{
	//  Verzeichnis der Applikation feststellen
	char buffer[256];
	::GetCurrentDirectory(256, buffer);
	mApplicationDirectory = buffer;

	//  Die Datei öffnen
	ifstream iniFile(mIniFileName.c_str());
	
	if ( !iniFile.good() )
		return false;	//  Datei konnte nicht geöffnet werden

	//  Daten einlesen
	while( !iniFile.eof() )
	{
		string fileLine;
		iniFile >> fileLine;

		//  Daten-Pfad
		if (fileLine == "[DATEN]")
		{
			iniFile >> fileLine;
			mDatenPfad = fileLine;
		}

		//  XA Solver-Pfad
		if (fileLine == "[XA_SOLVER]")
		{
			iniFile >> fileLine;
			mXASolverPfad = fileLine;
		}

		// MOPS Solver-Pfad
		if (fileLine == "[MOPS_SOLVER]")
		{
			//iniFile >> fileLine;

			char buffer[512];
			//next line
			iniFile.getline( buffer, 512 );
			//Mops pfad
			iniFile.getline( buffer, 512 );
			mMOPSSolverPfad = buffer;
		}

		//  Lokales Temp-Verzeichnis
		if (fileLine == "[LOCAL_TEMP]")
		{
			iniFile >> fileLine;
			mLocalTempFolder = fileLine;
		}

		//  TODO:  weitere Daten
	}

	return true;
}

bool IniFile::WriteData()
{
	//  Die Datei öffnen oder erzeugen
	string fileName = mApplicationDirectory + "\\" + mIniFileName;
	ofstream iniFile(fileName.c_str());
	
	if ( !iniFile.good() )
		return false;	//  Datei konnte nicht geöffnet werden

	//  Daten schreiben

	//  Leerzeilen
	iniFile << endl << endl;


	//  Daten-Pfad
	iniFile << "[DATEN]\n" << mDatenPfad << endl << endl;

	//  XA Solver-Pfad
	iniFile << "[XA_SOLVER]\n" << mXASolverPfad << endl << endl;

	//  MOPS Solver-Pfad
	iniFile << "[MOPS_SOLVER]\n" << mMOPSSolverPfad << endl << endl;
	
	//  Lokales Temp-Verzeichnis
	iniFile << "[LOCAL_TEMP]\n" << mLocalTempFolder << endl << endl;
	

	//  TODO:  weitere Daten

	return true;
}
