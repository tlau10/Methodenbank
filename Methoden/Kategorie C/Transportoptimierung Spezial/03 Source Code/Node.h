// Node.h: Schnittstelle für die Klasse CSerNode.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_NODE_H__A9BE9970_C99F_11D3_8F98_525400E72BB3__INCLUDED_)
#define AFX_NODE_H__A9BE9970_C99F_11D3_8F98_525400E72BB3__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

class CSerNode : public CObject 
{
public:
  DECLARE_SERIAL(CSerNode)

	CString getDescription();
	void setDescription(CString csDesc);
	double getValue();
	void setValue(double dValue);
	CSerNode();
	virtual ~CSerNode();

  virtual void Serialize( CArchive& ar );


private:
	CString m_csDescription;
	double m_dValue;
};

#endif // !defined(AFX_NODE_H__A9BE9970_C99F_11D3_8F98_525400E72BB3__INCLUDED_)
