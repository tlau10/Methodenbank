// SolverXA.h: Schnittstelle f�r die Klasse SolverXA.
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
	//  L�sen ohne Optimierung
	int Solve(EntryVector& touren);
	//  L�sen mit Optimierung
	int SolveOptimal(EntryVector& touren);

	
private:
	int CreateXAFiles(EntryVector& touren, int anzBusse);
	int getResult(int verschiebeStatus, EntryVector& touren);

};

#endif // !defined(AFX_SOLVERXA_H__66E95E00_B971_11D4_A43D_000374890932__INCLUDED_)
