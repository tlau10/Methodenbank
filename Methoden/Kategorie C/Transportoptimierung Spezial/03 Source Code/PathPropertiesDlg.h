#if !defined(AFX_PATHPROPERTIESDLG_H__9AF18C77_D342_11D3_8F98_525400E72BB3__INCLUDED_)
#define AFX_PATHPROPERTIESDLG_H__9AF18C77_D342_11D3_8F98_525400E72BB3__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
// PathPropertiesDlg.h : Header-Datei
//

/////////////////////////////////////////////////////////////////////////////
// Dialogfeld CPathPropertiesDlg 

class CPathPropertiesDlg : public CDialog
{
// Konstruktion
public:
	CPathPropertiesDlg(CWnd* pParent = NULL);   // Standardkonstruktor

// Dialogfelddaten
	//{{AFX_DATA(CPathPropertiesDlg)
	enum { IDD = IDD_PATHPROP };
	CString	m_csSolverPath;
	CString	m_csWorkingDir;
	//}}AFX_DATA


// �berschreibungen
	// Vom Klassen-Assistenten generierte virtuelle Funktions�berschreibungen
	//{{AFX_VIRTUAL(CPathPropertiesDlg)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV-Unterst�tzung
	//}}AFX_VIRTUAL

// Implementierung
protected:

	// Generierte Nachrichtenzuordnungsfunktionen
	//{{AFX_MSG(CPathPropertiesDlg)
	afx_msg void OnButtonChangeWorkingdir();
	afx_msg void OnButtonChangeSolverPath();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ f�gt unmittelbar vor der vorhergehenden Zeile zus�tzliche Deklarationen ein.

#endif // AFX_PATHPROPERTIESDLG_H__9AF18C77_D342_11D3_8F98_525400E72BB3__INCLUDED_
