// LinearFunction.h: Schnittstelle für die Klasse CLinearFunction.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_LINEARFUNCTION_H__A9BE9972_C99F_11D3_8F98_525400E72BB3__INCLUDED_)
#define AFX_LINEARFUNCTION_H__A9BE9972_C99F_11D3_8F98_525400E72BB3__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

class CLinearFunction  
{
public:
	int getCoefficientCount();
	double getSolution();
	void setSolution(double dValue);
	bool getCoefficient(int nIndex, double* dValue);
	bool setCoefficient(int nIndex, double dValue);
	CLinearFunction(int nCoefficientCount);
	virtual ~CLinearFunction();

private:
	int     m_nCoefficientCount;
	double* m_dCoefficientList;
	double  m_dSolution;
};

#endif // !defined(AFX_LINEARFUNCTION_H__A9BE9972_C99F_11D3_8F98_525400E72BB3__INCLUDED_)
