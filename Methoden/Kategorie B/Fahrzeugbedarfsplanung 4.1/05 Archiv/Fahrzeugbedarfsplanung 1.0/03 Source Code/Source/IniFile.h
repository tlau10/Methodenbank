// IniFile.h: Schnittstelle für die Klasse IniFile.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_INIFILE_H__29AB2A41_DCBD_11D4_AE92_444553540000__INCLUDED_)
#define AFX_INIFILE_H__29AB2A41_DCBD_11D4_AE92_444553540000__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000


#include <string>
using namespace std;

//  Klasse IniFile verwaltet alle Operationen zum Einlesen
//  uns Speichern der Einstellungen der Software in der INI-Datei.

class IniFile  
{
public:
	IniFile(const char* fileName="FaBePlan.ini");
	virtual ~IniFile();

	//  Daten lesen und schreiben
	bool ReadData();
	bool WriteData();

	//  Daten der Datei
	string mDatenPfad;
	string mXASolverPfad;
	string mMOPSSolverPfad;
	string mLocalTempFolder;
	string mApplicationDirectory;

private:
	//  Name der INI-Datei
	string mIniFileName;

};

#endif // !defined(AFX_INIFILE_H__29AB2A41_DCBD_11D4_AE92_444553540000__INCLUDED_)
