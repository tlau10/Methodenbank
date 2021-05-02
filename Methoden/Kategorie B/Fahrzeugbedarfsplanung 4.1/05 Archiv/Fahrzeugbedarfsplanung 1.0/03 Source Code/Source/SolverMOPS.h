// SolverMOPS.h: Schnittstelle f�r die Klasse SolverMOPS.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_SolverMOPS_H__66E95E00_B971_11D4_A43D_000374890932__INCLUDED_)
#define AFX_SolverMOPS_H__66E95E00_B971_11D4_A43D_000374890932__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "Solver.h"
#include <string>

class SolverMOPS : public Solver  
{
public:
	SolverMOPS();
	~SolverMOPS();
	//  L�sen ohne Optimierung
	int Solve(EntryVector& touren);
	//  L�sen mit Optimierung
	int SolveOptimal(EntryVector& touren);

	
private:
	void CreateMOPSFiles(EntryVector& touren);
	int getResult(int verschiebeStatus, EntryVector& touren);
};

#endif // !defined(AFX_SolverMOPS_H__66E95E00_B971_11D4_A43D_000374890932__INCLUDED_)
