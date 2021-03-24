// ReceiverList.h: Schnittstelle für die Klasse CReceiverList.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_RECEIVERLIST_H__EAD94EE9_D354_11D3_8F98_525400E72BB3__INCLUDED_)
#define AFX_RECEIVERLIST_H__EAD94EE9_D354_11D3_8F98_525400E72BB3__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

class CSerNode;

class CReceiverList  : public CList<CSerNode*, CSerNode*>
{
public:
  DECLARE_SERIAL(CReceiverList)

	CReceiverList();
	virtual ~CReceiverList();
  void AFXAPI SerializeElements( CArchive& ar, CSerNode** pElements, int& nCount );

};

#endif // !defined(AFX_PRODUCERLIST_H__EAD94EE9_D354_11D3_8F98_525400E72BB3__INCLUDED_)
