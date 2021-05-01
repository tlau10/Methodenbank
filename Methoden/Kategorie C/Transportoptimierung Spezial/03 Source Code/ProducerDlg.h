#if !defined(AFX_PRODUCERDLG_H__D7AF7F02_D0E4_11D3_8F98_525400E72BB3__INCLUDED_)
#define AFX_PRODUCERDLG_H__D7AF7F02_D0E4_11D3_8F98_525400E72BB3__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
// ProducerDlg.h : Header-Datei
//

/////////////////////////////////////////////////////////////////////////////
// Dialogfeld CProducerDlg 

class CSerNode;
class CStretch;

class CProducerDlg : public CDialog
{
// Konstruktion
public:
	CProducerDlg(CList<CSerNode*,CSerNode*>* producerList, 
    CList<CStretch*, CStretch*>* pStretchList,
    CWnd* pParent = NULL);   // Standardkonstruktor

// Dialogfelddaten
	//{{AFX_DATA(CProducerDlg)
	enum { IDD = IDD_PRODUCER };
	CEdit	m_EditProducerName;
	CListCtrl	m_ListCtrlProducer;
	CString	m_csProducerName;
	double	m_dValue;
	//}}AFX_DATA


// Überschreibungen
	// Vom Klassen-Assistenten generierte virtuelle Funktionsüberschreibungen
	//{{AFX_VIRTUAL(CProducerDlg)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV-Unterstützung
	//}}AFX_VIRTUAL

// Implementierung
protected:

	// Generierte Nachrichtenzuordnungsfunktionen
	//{{AFX_MSG(CProducerDlg)
	afx_msg void OnButtonAdd();
	afx_msg void OnButtonDelete();
	afx_msg void OnButtonEdit();
	virtual void OnCancel();
	virtual BOOL OnInitDialog();
	afx_msg void OnShowWindow(BOOL bShow, UINT nStatus);
	virtual void OnOK();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()

protected:
  bool m_bEditingNode;

private:
  CList<CStretch*, CStretch*>*			m_pStretchList;
  CList<CSerNode*, CSerNode*>*			m_pProducerList;
  CList<CSerNode*, CSerNode*>			m_AddedProducers;
  CList<CSerNode*, CSerNode*>			m_DeletedProducers;
  CSerNode*								m_pNodeToAdd;
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ fügt unmittelbar vor der vorhergehenden Zeile zusätzliche Deklarationen ein.

#endif // AFX_PRODUCERDLG_H__D7AF7F02_D0E4_11D3_8F98_525400E72BB3__INCLUDED_
