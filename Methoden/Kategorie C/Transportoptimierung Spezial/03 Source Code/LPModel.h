// LPModel.h: Schnittstelle für die Klasse CLPModel.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_LPMODEL_H__A9BE9975_C99F_11D3_8F98_525400E72BB3__INCLUDED_)
#define AFX_LPMODEL_H__A9BE9975_C99F_11D3_8F98_525400E72BB3__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "Targetfunction.h"
#include "Restriction.h"

class CLPModel  
{
public:
	CTargetFunction* getTargetFunction();
	CLPModel();
	virtual ~CLPModel();

  virtual bool solve() = 0;
	
  void setTargetFunction(CTargetFunction* pTargetFunction);
	
  CRestriction* getNextRestriction();
	
  CRestriction* getFirstRestriction();
	
  void addRestriction(CRestriction* pRestriction);

private:
	CTargetFunction* m_pTargetFunction;
	CList<CRestriction*, CRestriction*> m_RestrictionList;
  POSITION m_CurrentListPos;
  
};

#endif // !defined(AFX_LPMODEL_H__A9BE9975_C99F_11D3_8F98_525400E72BB3__INCLUDED_)
