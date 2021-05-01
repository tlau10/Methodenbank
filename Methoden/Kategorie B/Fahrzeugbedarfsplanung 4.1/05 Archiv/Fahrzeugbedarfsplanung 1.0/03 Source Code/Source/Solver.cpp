// Solver.cpp: Implementierung der Klasse Solver.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include "Solver.h"

#ifdef _DEBUG
#undef THIS_FILE
static char THIS_FILE[]=__FILE__;
#define new DEBUG_NEW
#endif

//////////////////////////////////////////////////////////////////////
// Konstruktion/Destruktion
//////////////////////////////////////////////////////////////////////

Solver::Solver()
{

}

Solver::~Solver()
{

}

void Solver::SetPfad(string& pfad)
{
	mSolverPfad = pfad;
}

string Solver::GetPfad()
{
	return mSolverPfad;
}

void Solver::SetTempFolder(string& folder)
{
	mTempFolder = folder;
}

string Solver::GetTempFolder()
{
	return mTempFolder;
}

string Solver::GetAppPath(void)
{
	return mApplicationPath;
}

void Solver::SetAppPath(string& path)
{
	mApplicationPath = path;
}





int Solver::Solve(EntryVector& touren)
{
	return 0;
}

int Solver::SolveOptimal(EntryVector& touren)
{
	return 0;
}

