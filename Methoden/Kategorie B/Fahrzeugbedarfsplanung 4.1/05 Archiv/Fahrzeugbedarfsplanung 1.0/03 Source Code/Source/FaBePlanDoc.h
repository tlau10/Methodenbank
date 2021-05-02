// FaBePlanDoc.h : Schnittstelle der Klasse CFaBePlanDoc
//
/////////////////////////////////////////////////////////////////////////////

#if !defined(AFX_FABEPLANDOC_H__704C40C9_ACC4_11D4_AE92_0010A7025D55__INCLUDED_)
#define AFX_FABEPLANDOC_H__704C40C9_ACC4_11D4_AE92_0010A7025D55__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "EntryVector.h"
#include "EinstellungenDlg.h"

class Solver;

class CFaBePlanDoc : public CDocument
{
protected: // Nur aus Serialisierung erzeugen
	CFaBePlanDoc();
	DECLARE_DYNCREATE(CFaBePlanDoc)

// Attribute
public:

// Operationen
public:

// Überladungen
	// Vom Klassenassistenten generierte Überladungen virtueller Funktionen
	//{{AFX_VIRTUAL(CFaBePlanDoc)
	public:
	virtual BOOL OnNewDocument();
	virtual void Serialize(CArchive& ar);
	//}}AFX_VIRTUAL

// Implementierung
public:
	void DeleteTour(int tourId);
	void GetTourAndId(int position, Entry& tour, int& tourId);
	int CFaBePlanDoc::GetTourCount();
	void UpdateTour(int tourId, Entry& tour);
	Entry GetTour(int tourId);
	int AddTour(Entry& tour);
	virtual ~CFaBePlanDoc();
#ifdef _DEBUG
	virtual void AssertValid() const;
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:

// Generierte Message-Map-Funktionen
protected:
	//{{AFX_MSG(CFaBePlanDoc)
	afx_msg void OnEinstellungenOptionen();
	afx_msg void OnBearbeitenBedarfBerechnen();
	afx_msg void OnUpdateBearbeitenBedarfBerechnen(CCmdUI* pCmdUI);
	afx_msg void OnUpdateFileSave(CCmdUI* pCmdUI);
	afx_msg void OnUpdateFileSaveAs(CCmdUI* pCmdUI);
	afx_msg void OnBearbeitenOptimumBerechnen();
	afx_msg void OnUpdateBearbeitenOptimumBerechnen(CCmdUI* pCmdUI);
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()

private:
	void ExtractTourFromLine(CString tourText);
	void LoadFahrplan(CArchive& archive);
	void SaveFahrplan(CArchive& archive);
	void AddFahrplanZeiten();

	EntryVector mTouren;	//  Liste aller Touren 
	EntryVector mTourenOptimum;	//  Liste mit optimierten Stunden

	//  Solver
	Solver* m_pCurrentSolver;
	//  Der aktelle aktive Solver
	int m_iSolverAuswahl;

	string m_strXASolverPfad;
	string m_strMOPSSolverPfad;
	string m_strLocalTempFolder;
	string m_strApplicationDirectory;
};

/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ fügt unmittelbar vor der vorhergehenden Zeile zusätzliche Deklarationen ein.

#endif // !defined(AFX_FABEPLANDOC_H__704C40C9_ACC4_11D4_AE92_0010A7025D55__INCLUDED_)
