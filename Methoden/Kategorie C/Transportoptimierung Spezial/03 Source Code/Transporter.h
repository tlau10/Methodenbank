// Transporter.h: Schnittstelle für die Klasse CTransporter.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_TRANSPORTER_H__D4EDB762_C9C7_11D3_8F98_525400E72BB3__INCLUDED_)
#define AFX_TRANSPORTER_H__D4EDB762_C9C7_11D3_8F98_525400E72BB3__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

class CTransporter  
{
public:
	int getLoading();
	void setLoading(int nLoading);
	CString getDescription();
	void setDescription(CString csDescription);
	double getCostsPerKm();
	void setCostsPerKm(double dCosts);
	int getCapacity();
	void setCapacity(int nCapacity);
	CTransporter();
	virtual ~CTransporter();

	virtual void Serialize( CArchive& ar );

private:
	int m_nLoading;
	CString m_csDescription;
	double  m_dCostsPerKm;
	int     m_nCapacity;
};

#endif // !defined(AFX_TRANSPORTER_H__D4EDB762_C9C7_11D3_8F98_525400E72BB3__INCLUDED_)
