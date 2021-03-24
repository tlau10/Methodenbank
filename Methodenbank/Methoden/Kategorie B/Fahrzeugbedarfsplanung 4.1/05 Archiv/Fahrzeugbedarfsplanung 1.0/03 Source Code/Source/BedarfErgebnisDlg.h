#if !defined(AFX_BEDARFERGEBNISDLG_H__8FBF5B85_CA89_11D4_AE92_0010A7025D55__INCLUDED_)
#define AFX_BEDARFERGEBNISDLG_H__8FBF5B85_CA89_11D4_AE92_0010A7025D55__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
// BedarfErgebnisDlg.h : Header-Datei
//

#include "EntryVector.h"


/////////////////////////////////////////////////////////////////////////////
// Dialogfeld CBedarfErgebnisDlg 

class CBedarfErgebnisDlg : public CDialog
{
// Konstruktion
public:
	CBedarfErgebnisDlg(CWnd* pParent = NULL);   // Standardkonstruktor

// Dialogfelddaten
	//{{AFX_DATA(CBedarfErgebnisDlg)
	enum { IDD = IDD_SOLVER_ERGEBNIS };
	int	mMinFahrzeuge;
	//}}AFX_DATA

	//  Dialogtitel
	CString mDialogTitel;
	
	//  Dialogvariablen
	EntryVector* mTouren;


// Überschreibungen
	// Vom Klassen-Assistenten generierte virtuelle Funktionsüberschreibungen
	//{{AFX_VIRTUAL(CBedarfErgebnisDlg)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV-Unterstützung
	virtual void PostNcDestroy();
	//}}AFX_VIRTUAL

// Implementierung
protected:

	// Generierte Nachrichtenzuordnungsfunktionen
	//{{AFX_MSG(CBedarfErgebnisDlg)
	afx_msg void OnPaint();
	virtual void OnOK();
	virtual BOOL OnInitDialog();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
private:
	//  Zeichnungsfunktionen
	void PaintBedarf(CPaintDC& dc);
	void PaintAchseX(CPaintDC& dc, CRect& rect);
	void PaintAchseY(CPaintDC& dc, CRect& rect);
	void PaintFahrzeuge(CPaintDC& dc, CRect& rect);
	void DrawTour(CPaintDC& dc, CRect& rect, Entry& tour);

	//  Zeichnungsvariablen
	CRect mGrafikRect;

	//  Raster der Grafik
	int mStundenLength;		
	int mFahrzeugLength;

	//  Farben-Tabelle
	COLORREF mFarben[14];
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ fügt unmittelbar vor der vorhergehenden Zeile zusätzliche Deklarationen ein.

#endif // AFX_BEDARFERGEBNISDLG_H__8FBF5B85_CA89_11D4_AE92_0010A7025D55__INCLUDED_
