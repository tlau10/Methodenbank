// TransporterList.h: Schnittstelle für die Klasse CProducerList.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_TRANSPORTERLIST_H__EAD94EE9_D354_11D3_8F98_525400E72BB3__INCLUDED_)
#define AFX_TRANSPORTERLIST_H__EAD94EE9_D354_11D3_8F98_525400E72BB3__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

class CTransporter;

class CTransporterList  : public CList<CTransporter*, CTransporter*>
{
public:
  DECLARE_SERIAL(CTransporterList)

	CTransporterList();
	virtual ~CTransporterList();
  void AFXAPI SerializeElements( CArchive& ar, CTransporter** pElements, int& nCount );

};

#endif // !defined(AFX_PRODUCERLIST_H__EAD94EE9_D354_11D3_8F98_525400E72BB3__INCLUDED_)
