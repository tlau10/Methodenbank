// TransopView.h : Schnittstelle der Klasse CTransopView
//
/////////////////////////////////////////////////////////////////////////////

#if !defined(AFX_TRANSOPVIEW_H__D74A398D_C90D_11D3_8F98_525400E72BB3__INCLUDED_)
#define AFX_TRANSOPVIEW_H__D74A398D_C90D_11D3_8F98_525400E72BB3__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000


#define ID_PRODLIST     100
#define ID_RECLIST      101
#define ID_STRETCHLIST  102
#define ID_SOLUTIONLIST 103

class CTransopView : public CFormView
{
protected: // Nur aus Serialisierung erzeugen
	CTransopView();
	DECLARE_DYNCREATE(CTransopView)

public:
	//{{AFX_DATA(CTransopView)
	enum { IDD = IDD_TRANSOP_FORM };
	CStatic	m_StaticSolutionPos;
	CStatic	m_StaticStretchPos;
	CStatic	m_StaticRecPos;
	CStatic	m_StaticProdPos;
	CString	m_csCap1;
	CString	m_csCap2;
	CString	m_csCosts1;
	CString	m_csCosts2;
	CString	m_csTransName1;
	CString	m_csTransName2;
	CString	m_csGK;
	//}}AFX_DATA

// Attribute
public:
	CTransopDoc* GetDocument();

// Operationen
public:

// Überladungen
	// Vom Klassenassistenten generierte Überladungen virtueller Funktionen
	//{{AFX_VIRTUAL(CTransopView)
	public:
	virtual BOOL PreCreateWindow(CREATESTRUCT& cs);
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV-Unterstützung
	virtual void OnInitialUpdate(); // das erste mal nach der Konstruktion aufgerufen
	virtual BOOL OnPreparePrinting(CPrintInfo* pInfo);
	virtual void OnBeginPrinting(CDC* pDC, CPrintInfo* pInfo);
	virtual void OnEndPrinting(CDC* pDC, CPrintInfo* pInfo);
	virtual void OnPrint(CDC* pDC, CPrintInfo* pInfo);
	virtual void OnDraw(CDC* pDC);
	virtual void OnUpdate(CView* pSender, LPARAM lHint, CObject* pHint);
	//}}AFX_VIRTUAL

// Implementierung
public:
	virtual ~CTransopView();
#ifdef _DEBUG
	virtual void AssertValid() const;
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:

// Generierte Message-Map-Funktionen
protected:
	//{{AFX_MSG(CTransopView)
	afx_msg void OnPropConnection();
	afx_msg void OnPropProducer();
	afx_msg void OnPropReceiver();
	afx_msg void OnPropTransporter();
	afx_msg int OnCreate(LPCREATESTRUCT lpCreateStruct);
	afx_msg void OnShowWindow(BOOL bShow, UINT nStatus);
	afx_msg void OnSize(UINT nType, int cx, int cy);
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()

private:
  CListCtrlEx m_ProducerListCtrl;
  CListCtrlEx m_ReceiverListCtrl;
  CListCtrlEx m_StretchListCtrl;
  CListCtrlEx m_SolutionCtrl;

public:
	afx_msg void OnStnClickedProdpos();
};

#ifndef _DEBUG  // Testversion in TransopView.cpp
inline CTransopDoc* CTransopView::GetDocument()
   { return (CTransopDoc*)m_pDocument; }
#endif

/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ fügt unmittelbar vor der vorhergehenden Zeile zusätzliche Deklarationen ein.

#endif // !defined(AFX_TRANSOPVIEW_H__D74A398D_C90D_11D3_8F98_525400E72BB3__INCLUDED_)
