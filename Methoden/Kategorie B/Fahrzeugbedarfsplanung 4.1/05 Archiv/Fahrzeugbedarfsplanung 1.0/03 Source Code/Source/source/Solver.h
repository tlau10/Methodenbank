// Solver.h: Schnittstelle für die Klasse Solver.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_SOLVER_H__A0B14341_B580_11D4_AE92_0010A7025D55__INCLUDED_)
#define AFX_SOLVER_H__A0B14341_B580_11D4_AE92_0010A7025D55__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000


#include <string>
using namespace std;

#include "EntryVector.h"

class Solver  
{
public:
	Solver();
	virtual ~Solver();
	string GetPfad(void);
	void SetPfad(string& pfad);
	string GetTempFolder(void);
	void SetTempFolder(string& folder);
	string GetAppPath(void);
	void SetAppPath(string& path);


	//  Lösen ohne Optimierung
	virtual int Solve(EntryVector& touren);
	//  Lösen mit Optimierung
	virtual int SolveOptimal(EntryVector& touren);
	

protected:
	//  Solverpfad incl. den Namen der solver .EXE Datei !
	string mSolverPfad;		

	//  Temp-Verzeichnis - Bsp.: 'c:\temp'
	string mTempFolder;

	//  Pfad der Applikation - Bsp.: 'c:\temp\FaBePlan'
	string mApplicationPath;
};

#endif // !defined(AFX_SOLVER_H__A0B14341_B580_11D4_AE92_0010A7025D55__INCLUDED_)
