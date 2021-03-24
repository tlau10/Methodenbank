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


// Überschreibungen
	// Vom Klassen-Assistenten generierte virtuelle Funktionsüberschreibungen
	//{{AFX_VIRTUAL(CPathPropertiesDlg)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV-Unterstützung
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
// Microsoft Visual C++ fügt unmittelbar vor der vorhergehenden Zeile zusätzliche Deklarationen ein.

#endif // AFX_PATHPROPERTIESDLG_H__9AF18C77_D342_11D3_8F98_525400E72BB3__INCLUDED_
