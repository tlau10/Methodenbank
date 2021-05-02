// Restriction.h: Schnittstelle für die Klasse CRestriction.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_RESTRICTION_H__A9BE9973_C99F_11D3_8F98_525400E72BB3__INCLUDED_)
#define AFX_RESTRICTION_H__A9BE9973_C99F_11D3_8F98_525400E72BB3__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "LinearFunction.h"

class CRestriction : public CLinearFunction  
{
public:
	CRestriction(int nCoefficientCount);
	virtual ~CRestriction();

  CString getRestriction();
	void setRestriction(CString csRestriction);

private:
	CString m_csRestriction;
};

#endif // !defined(AFX_RESTRICTION_H__A9BE9973_C99F_11D3_8F98_525400E72BB3__INCLUDED_)
