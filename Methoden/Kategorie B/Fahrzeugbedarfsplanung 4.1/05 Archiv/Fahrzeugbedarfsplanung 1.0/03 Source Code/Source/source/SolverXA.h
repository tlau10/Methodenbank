// SolverXA.h: Schnittstelle für die Klasse SolverXA.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_SOLVERXA_H__66E95E00_B971_11D4_A43D_000374890932__INCLUDED_)
#define AFX_SOLVERXA_H__66E95E00_B971_11D4_A43D_000374890932__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "Solver.h"
#include <string>

class SolverXA : public Solver  
{
public:
	SolverXA();
	~SolverXA();
	//  Lösen ohne Optimierung
	int Solve(EntryVector& touren);
	//  Lösen mit Optimierung
	int SolveOptimal(EntryVector& touren);

	
private:
	int CreateXAFiles(EntryVector& touren, int anzBusse);
	int getResult(int verschiebeStatus, EntryVector& touren);

};

#endif // !defined(AFX_SOLVERXA_H__66E95E00_B971_11D4_A43D_000374890932__INCLUDED_)
