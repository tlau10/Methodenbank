// Transop.h : Haupt-Header-Datei f�r die Anwendung TRANSOP
//

#if !defined(AFX_TRANSOP_H__D74A3983_C90D_11D3_8F98_525400E72BB3__INCLUDED_)
#define AFX_TRANSOP_H__D74A3983_C90D_11D3_8F98_525400E72BB3__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#ifndef __AFXWIN_H__
	#error include 'stdafx.h' before including this file for PCH
#endif

#include "resource.h"       // Hauptsymbole

/////////////////////////////////////////////////////////////////////////////
// CTransopApp:
// Siehe Transop.cpp f�r die Implementierung dieser Klasse
//

class CTransopApp : public CWinApp
{
public:
	CString m_csSolverPath;
	CString m_csWorkingDir;
	CTransopApp();

// �berladungen
	// Vom Klassenassistenten generierte �berladungen virtueller Funktionen
	//{{AFX_VIRTUAL(CTransopApp)
	public:
	virtual BOOL InitInstance();
	//}}AFX_VIRTUAL

// Implementierung
	//{{AFX_MSG(CTransopApp)
	afx_msg void OnAppAbout();
	afx_msg void OnPropWorkingdir();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};


/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ f�gt unmittelbar vor der vorhergehenden Zeile zus�tzliche Deklarationen ein.

#endif // !defined(AFX_TRANSOP_H__D74A3983_C90D_11D3_8F98_525400E72BB3__INCLUDED_)
