// Stretch.h: Schnittstelle für die Klasse CStretch.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_STRETCH_H__A9BE9971_C99F_11D3_8F98_525400E72BB3__INCLUDED_)
#define AFX_STRETCH_H__A9BE9971_C99F_11D3_8F98_525400E72BB3__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "Node.h"

class CStretch  
{
public:
	double getCostFactor();
	void setCostFactor(double dCosts);
	double getDistance();
	void setDistance(double dDistance);
	CSerNode* getSourceNode();
	void setSourceNode(CSerNode* pSourceNode);
	CSerNode* getDestinationNode();
	void setDestinationNode(CSerNode* pDestinationNode);
	CStretch();
	virtual ~CStretch();

	  virtual void Serialize( CArchive& ar );

private:
	double m_dCostFactor;
	double m_dDistance;
	CSerNode* m_pSourceNode;
	CSerNode* m_pDestinationNode;
};

#endif // !defined(AFX_STRETCH_H__A9BE9971_C99F_11D3_8F98_525400E72BB3__INCLUDED_)
