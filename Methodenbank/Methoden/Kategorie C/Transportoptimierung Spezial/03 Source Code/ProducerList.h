// ProducerList.h: Schnittstelle für die Klasse CProducerList.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_PRODUCERLIST_H__EAD94EE9_D354_11D3_8F98_525400E72BB3__INCLUDED_)
#define AFX_PRODUCERLIST_H__EAD94EE9_D354_11D3_8F98_525400E72BB3__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

class CSerNode;

class CProducerList  : public CList<CSerNode*, CSerNode*>
{
public:
  DECLARE_SERIAL(CProducerList)

	CProducerList();
	virtual ~CProducerList();
  void AFXAPI SerializeElements( CArchive& ar, CSerNode** pElements, int& nCount );

};

#endif // !defined(AFX_PRODUCERLIST_H__EAD94EE9_D354_11D3_8F98_525400E72BB3__INCLUDED_)
