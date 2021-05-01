#if !defined(AFX_TRANSPORTERDLG_H__C96A7461_D18C_11D3_8F98_525400E72BB3__INCLUDED_)
#define AFX_TRANSPORTERDLG_H__C96A7461_D18C_11D3_8F98_525400E72BB3__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
// TransporterDlg.h : Header-Datei
//

/////////////////////////////////////////////////////////////////////////////
// Dialogfeld CTransporterDlg 

class CTransporter;

class CTransporterDlg : public CDialog
{
// Konstruktion
public:
	CTransporterDlg(CList<CTransporter*, CTransporter*>* pTransporterList, 
    CWnd* pParent = NULL);   // Standardkonstruktor

// Dialogfelddaten
	//{{AFX_DATA(CTransporterDlg)
	enum { IDD = IDD_TRANSPORTER };
	int		m_nCap1;
	int		m_nCap2;
	double	m_dCosts1;
	double	m_dCosts2;
	CString	m_csName1;
	CString	m_csName2;
	//}}AFX_DATA


// Überschreibungen
	// Vom Klassen-Assistenten generierte virtuelle Funktionsüberschreibungen
	//{{AFX_VIRTUAL(CTransporterDlg)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV-Unterstützung
	//}}AFX_VIRTUAL

// Implementierung
protected:

	// Generierte Nachrichtenzuordnungsfunktionen
	//{{AFX_MSG(CTransporterDlg)
	virtual void OnOK();
	virtual BOOL OnInitDialog();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()

private:
  CList<CTransporter*, CTransporter*>* m_pTransporterList;


};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ fügt unmittelbar vor der vorhergehenden Zeile zusätzliche Deklarationen ein.

#endif // AFX_TRANSPORTERDLG_H__C96A7461_D18C_11D3_8F98_525400E72BB3__INCLUDED_
