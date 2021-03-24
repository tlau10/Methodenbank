// TargetFunction.h: Schnittstelle für die Klasse CTargetFunction.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_TARGETFUNCTION_H__A9BE9974_C99F_11D3_8F98_525400E72BB3__INCLUDED_)
#define AFX_TARGETFUNCTION_H__A9BE9974_C99F_11D3_8F98_525400E72BB3__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "LinearFunction.h"

// Zielfunktionstypen
#define TFT_MIN   0
#define TFT_MAX   1

class CTargetFunction : public CLinearFunction  
{
public:
	bool isMax();
	bool isMin();
	void setMax();
	void setMin();
	CTargetFunction(int nCoefficientCount);
	virtual ~CTargetFunction();

private:
  int m_nType;
};

#endif // !defined(AFX_TARGETFUNCTION_H__A9BE9974_C99F_11D3_8F98_525400E72BB3__INCLUDED_)
