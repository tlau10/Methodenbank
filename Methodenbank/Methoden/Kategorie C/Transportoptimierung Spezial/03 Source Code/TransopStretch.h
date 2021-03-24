// TransopStretch.h: Schnittstelle für die Klasse CTransopStretch.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_TRANSOPSTRETCH_H__C96A7473_D18C_11D3_8F98_525400E72BB3__INCLUDED_)
#define AFX_TRANSOPSTRETCH_H__C96A7473_D18C_11D3_8F98_525400E72BB3__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "Stretch.h"
#include "Transporter.h"	// Hinzugefügt von der Klassenansicht

class CTransopStretch : public CStretch  
{
public:
	CTransporter* getFirstTransporter();
	CTransporter* getNextTransporter();
	void addTransporter(int nCapacity, CString csDescription, int nLoading, double dCosts);
	CTransopStretch();
	virtual ~CTransopStretch();

private:
	POSITION m_CurPos;
	CList<CTransporter*, CTransporter*> m_TransporterList;
};

#endif // !defined(AFX_TRANSOPSTRETCH_H__C96A7473_D18C_11D3_8F98_525400E72BB3__INCLUDED_)
