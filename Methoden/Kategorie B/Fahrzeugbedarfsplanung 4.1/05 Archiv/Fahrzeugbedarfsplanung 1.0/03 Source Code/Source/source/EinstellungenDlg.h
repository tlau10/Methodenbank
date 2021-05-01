#if !defined(AFX_EINSTELLUNGENDLG_H__19E45836_B083_11D4_8AD9_006008492CAF__INCLUDED_)
#define AFX_EINSTELLUNGENDLG_H__19E45836_B083_11D4_8AD9_006008492CAF__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
// EinstellungenDlg.h : Header-Datei
//

/////////////////////////////////////////////////////////////////////////////
// Dialogfeld CEinstellungenDlg 

class CEinstellungenDlg : public CDialog
{
// Konstruktion
public:
	CEinstellungenDlg(CWnd* pParent = NULL);   // Standardkonstruktor

// Dialogfelddaten
	//{{AFX_DATA(CEinstellungenDlg)
	enum { IDD = IDD_DIALOG_OPTIONEN };
	CString	mLPPfad;
	CString	mMOPSPfad;
	CString	mXAPfad;
	int		mSolverAuswahl;
	//}}AFX_DATA


// Überschreibungen
	// Vom Klassen-Assistenten generierte virtuelle Funktionsüberschreibungen
	//{{AFX_VIRTUAL(CEinstellungenDlg)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV-Unterstützung
	//}}AFX_VIRTUAL

// Implementierung
protected:

	// Generierte Nachrichtenzuordnungsfunktionen
	//{{AFX_MSG(CEinstellungenDlg)
	afx_msg void OnXASuchen();
	afx_msg void OnMOPSSuchen();
	afx_msg void OnLPSolveSuchen();
	virtual BOOL OnInitDialog();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
private:
	bool ShowSolverFileDialog(CString& solverPfad, CString boxTitle);

	CBitmap mFolderBitmap;
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ fügt unmittelbar vor der vorhergehenden Zeile zusätzliche Deklarationen ein.

#endif // AFX_EINSTELLUNGENDLG_H__19E45836_B083_11D4_8AD9_006008492CAF__INCLUDED_
