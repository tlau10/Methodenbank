// FaBePlan.h : Haupt-Header-Datei für die Anwendung FABEPLAN
//

#if !defined(AFX_FABEPLAN_H__704C40C3_ACC4_11D4_AE92_0010A7025D55__INCLUDED_)
#define AFX_FABEPLAN_H__704C40C3_ACC4_11D4_AE92_0010A7025D55__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#ifndef __AFXWIN_H__
	#error include 'stdafx.h' before including this file for PCH
#endif

#include "resource.h"       // Hauptsymbole

/////////////////////////////////////////////////////////////////////////////
// CFaBePlanApp:
// Siehe FaBePlan.cpp für die Implementierung dieser Klasse
//

class CFaBePlanApp : public CWinApp
{
public:
	CFaBePlanApp();

// Überladungen
	// Vom Klassenassistenten generierte Überladungen virtueller Funktionen
	//{{AFX_VIRTUAL(CFaBePlanApp)
	public:
	virtual BOOL InitInstance();
	//}}AFX_VIRTUAL

// Implementierung
	//{{AFX_MSG(CFaBePlanApp)
	afx_msg void OnAppAbout();
		// HINWEIS - An dieser Stelle werden Member-Funktionen vom Klassen-Assistenten eingefügt und entfernt.
		//    Innerhalb dieser generierten Quelltextabschnitte NICHTS VERÄNDERN!
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};


/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ fügt unmittelbar vor der vorhergehenden Zeile zusätzliche Deklarationen ein.

#endif // !defined(AFX_FABEPLAN_H__704C40C3_ACC4_11D4_AE92_0010A7025D55__INCLUDED_)
