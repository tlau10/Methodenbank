// FaBePlanView.h : Schnittstelle der Klasse CFaBePlanView
//
/////////////////////////////////////////////////////////////////////////////

#if !defined(AFX_FABEPLANVIEW_H__704C40CB_ACC4_11D4_AE92_0010A7025D55__INCLUDED_)
#define AFX_FABEPLANVIEW_H__704C40CB_ACC4_11D4_AE92_0010A7025D55__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "Entry.h"


class CFaBePlanView : public CFormView
{
protected: // Nur aus Serialisierung erzeugen
	CFaBePlanView();
	DECLARE_DYNCREATE(CFaBePlanView)

public:
	//{{AFX_DATA(CFaBePlanView)
	enum { IDD = IDD_FABEPLAN_FORM };
	CListCtrl	mFahrplan;
	//}}AFX_DATA

// Attribute
public:
	CFaBePlanDoc* GetDocument();

// Operationen
public:

// Überladungen
	// Vom Klassenassistenten generierte Überladungen virtueller Funktionen
	//{{AFX_VIRTUAL(CFaBePlanView)
	public:
	virtual BOOL PreCreateWindow(CREATESTRUCT& cs);
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV-Unterstützung
	virtual void OnInitialUpdate(); // das erste mal nach der Konstruktion aufgerufen
	virtual void OnUpdate(CView* pSender, LPARAM lHint, CObject* pHint);
	//}}AFX_VIRTUAL

// Implementierung
public:
	void AddTourZeiten(int tourID, Entry& oldTour, Entry &newTour);

	bool HasColumn(void);
	virtual ~CFaBePlanView();
#ifdef _DEBUG
	virtual void AssertValid() const;
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:
	//  Indizies der Spalten im Fahrplan-Control
	enum
	{	ColumnTour = 0,
		ColumnAbfahrt,
		ColumnAnkunft,
		ColumnPersonen
	};

	//  Image-List für Icons
	CImageList mImageList;


// Generierte Message-Map-Funktionen
protected:
	//{{AFX_MSG(CFaBePlanView)
	afx_msg void OnSize(UINT nType, int cx, int cy);
	afx_msg void OnBearbeitenTourEingeben();
	afx_msg void OnBearbeitenTourLoeschen();
	afx_msg void OnUpdateBearbeitenTourLoeschen(CCmdUI* pCmdUI);
	afx_msg void OnBearbeitenTourVeraendern();
	afx_msg void OnUpdateBearbeitenTourVeraendern(CCmdUI* pCmdUI);
	afx_msg void OnDblclkFahrplan(NMHDR* pNMHDR, LRESULT* pResult);
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
private:
	void UpdateTourItem(int item, int tourId, Entry& tour);
	void InsertNewTour(Entry& newTour);
	bool mColumnCreated;	//  Zeigt, ob die Spalten schoe erzeugt wurden

};

#ifndef _DEBUG  // Testversion in FaBePlanView.cpp
inline CFaBePlanDoc* CFaBePlanView::GetDocument()
   { return (CFaBePlanDoc*)m_pDocument; }
#endif

/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ fügt unmittelbar vor der vorhergehenden Zeile zusätzliche Deklarationen ein.

#endif // !defined(AFX_FABEPLANVIEW_H__704C40CB_ACC4_11D4_AE92_0010A7025D55__INCLUDED_)
