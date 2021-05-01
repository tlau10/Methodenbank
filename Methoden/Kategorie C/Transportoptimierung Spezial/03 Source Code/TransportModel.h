// TransportModel.h: Schnittstelle für die Klasse CTransportModel.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_TRANSPORTMODEL_H__A9BE9978_C99F_11D3_8F98_525400E72BB3__INCLUDED_)
#define AFX_TRANSPORTMODEL_H__A9BE9978_C99F_11D3_8F98_525400E72BB3__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "LPModel.h"
#include "Stretch.h"

class CTransportModel : public CLPModel  
{
public:
	CStretch* getStretchAt(int nIndex);
	int getStretchCount();
	CStretch* getNextStretch();
	CStretch* getFirstStretch();
	void addStretch(CStretch* pStretch);
	CTransportModel();
	virtual ~CTransportModel();

  // überschreiben, zum Lösen des LP-Modells
 	virtual bool solve() = 0;
	
  // überschreiben, zum Erzeugen eines Transportmodells
  virtual bool generateLPModel() = 0;

private:
	CList<CStretch*, CStretch*> m_StretchList;
  POSITION m_CurrentListPos;
};

#endif // !defined(AFX_TRANSPORTMODEL_H__A9BE9978_C99F_11D3_8F98_525400E72BB3__INCLUDED_)
