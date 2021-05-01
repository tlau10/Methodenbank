// TransopDoc.h : Schnittstelle der Klasse CTransopDoc
//
/////////////////////////////////////////////////////////////////////////////

#if !defined(AFX_TRANSOPDOC_H__D74A398B_C90D_11D3_8F98_525400E72BB3__INCLUDED_)
#define AFX_TRANSOPDOC_H__D74A398B_C90D_11D3_8F98_525400E72BB3__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "ProducerList.h"
#include "ReceiverList.h"
#include "StretchList.h"
#include "TransporterList.h"

class CTransporter;
class CStretch;
class CSerNode;
class CTransopStretch;
class CTransopModel;

class CTransopDoc : public CDocument
{
protected: // Nur aus Serialisierung erzeugen
	CTransopDoc();
	DECLARE_DYNCREATE(CTransopDoc)

// Attribute
public:

// Operationen
public:

// Überladungen
	// Vom Klassenassistenten generierte Überladungen virtueller Funktionen
	//{{AFX_VIRTUAL(CTransopDoc)
	public:
	virtual BOOL OnNewDocument();
	virtual void Serialize(CArchive& ar);
	//}}AFX_VIRTUAL

// Implementierung
public:
	virtual ~CTransopDoc();
#ifdef _DEBUG
	virtual void AssertValid() const;
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:

// Generierte Message-Map-Funktionen
protected:
	//{{AFX_MSG(CTransopDoc)
	afx_msg void OnCalculate();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()


//Lists
public:
	double m_dValueOfObjective;
  CStretchList									m_StretchList;
  CProducerList									m_ProducerList;
  CReceiverList									m_ReceiverList;
  CTransporterList								m_TransporterList;  // CList<CTransporter*, CTransporter*>
  CList<CTransopStretch*, CTransopStretch*>*	m_pSolutionList;
//  CTransopModel*             m_pLPModel;  
};

/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ fügt unmittelbar vor der vorhergehenden Zeile zusätzliche Deklarationen ein.

#endif // !defined(AFX_TRANSOPDOC_H__D74A398B_C90D_11D3_8F98_525400E72BB3__INCLUDED_)
