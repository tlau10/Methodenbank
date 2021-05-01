// StretchList.h: Schnittstelle für die Klasse CStretchList.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_STRETCHLIST_H__EAD94EE9_D354_11D3_8F98_525400E72BB3__INCLUDED_)
#define AFX_STRETCHLIST_H__EAD94EE9_D354_11D3_8F98_525400E72BB3__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

class CStretch;

class CStretchList  : public CList<CStretch*, CStretch*>
{
public:
  DECLARE_SERIAL(CStretchList)

	CStretchList();
	virtual ~CStretchList();
  void AFXAPI SerializeElements( CArchive& ar, CStretch** pElements, int& nCount );

};

#endif
