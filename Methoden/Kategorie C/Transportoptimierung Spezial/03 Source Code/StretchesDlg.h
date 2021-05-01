#if !defined(AFX_STRETCHESDLG_H__D7AF7F01_D0E4_11D3_8F98_525400E72BB3__INCLUDED_)
#define AFX_STRETCHESDLG_H__D7AF7F01_D0E4_11D3_8F98_525400E72BB3__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
// StretchesDlg.h : Header-Datei
//

/////////////////////////////////////////////////////////////////////////////
// Dialogfeld CStretchesDlg 

class CStretch;
class CSerNode;

class CStretchesDlg : public CDialog
{
// Konstruktion
public:
	CStretchesDlg(CList<CStretch*, CStretch*>* pStretchList,
    CList<CSerNode*, CSerNode*>* pProducerList,
    CList<CSerNode*, CSerNode*>* pReceiverList,
    CWnd* pParent = NULL);   // Standardkonstruktor

// Dialogfelddaten
	//{{AFX_DATA(CStretchesDlg)
	enum { IDD = IDD_CONECTIONS };
	CListCtrl	m_ListCtrlStretches;
	CComboBox	m_ComboProducer;
	CComboBox	m_ComboReceiver;
	double	m_dValue;
	//}}AFX_DATA


// Überschreibungen
	// Vom Klassen-Assistenten generierte virtuelle Funktionsüberschreibungen
	//{{AFX_VIRTUAL(CStretchesDlg)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV-Unterstützung
	//}}AFX_VIRTUAL

// Implementierung
protected:

	// Generierte Nachrichtenzuordnungsfunktionen
	//{{AFX_MSG(CStretchesDlg)
	virtual void OnOK();
	virtual void OnCancel();
	afx_msg void OnButtonAdd();
	afx_msg void OnButtonDelete();
	virtual BOOL OnInitDialog();
	afx_msg void OnShowWindow(BOOL bShow, UINT nStatus);
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()

private:
  CList<CStretch*, CStretch*> m_addedStretchList;
  CList<CStretch*, CStretch*> m_deletedStretchList;
  CList<CStretch*, CStretch*>* m_pStretchList;
  CList<CSerNode*, CSerNode*>* m_pProducerList;
  CList<CSerNode*, CSerNode*>* m_pReceiverList;
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ fügt unmittelbar vor der vorhergehenden Zeile zusätzliche Deklarationen ein.

#endif // AFX_STRETCHESDLG_H__D7AF7F01_D0E4_11D3_8F98_525400E72BB3__INCLUDED_
