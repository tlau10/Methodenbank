#if !defined(AFX_RECEIVERDLG_H__D7AF7F03_D0E4_11D3_8F98_525400E72BB3__INCLUDED_)
#define AFX_RECEIVERDLG_H__D7AF7F03_D0E4_11D3_8F98_525400E72BB3__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
// ReceiverDlg.h : Header-Datei
//

/////////////////////////////////////////////////////////////////////////////
// Dialogfeld CReceiverDlg 

class CSerNode;
class CStretch;

class CReceiverDlg : public CDialog
{
// Konstruktion
public:
	CReceiverDlg(CList<CSerNode*,CSerNode*>* receiverList, 
    CList<CStretch*, CStretch*>* pStretchList,
    CWnd* pParent = NULL);   // Standardkonstruktor

// Dialogfelddaten
	//{{AFX_DATA(CReceiverDlg)
	enum { IDD = IDD_RECEIVER };
	CEdit	m_EditReceiverName;
	CListCtrl	m_ListCtrlReceiver;
	double	m_dValue;
	CString	m_csReceiverName;
	//}}AFX_DATA


// Überschreibungen
	// Vom Klassen-Assistenten generierte virtuelle Funktionsüberschreibungen
	//{{AFX_VIRTUAL(CReceiverDlg)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV-Unterstützung
	//}}AFX_VIRTUAL

// Implementierung
protected:

	// Generierte Nachrichtenzuordnungsfunktionen
	//{{AFX_MSG(CReceiverDlg)
	virtual void OnOK();
	virtual void OnCancel();
	afx_msg void OnButtonAdd();
	afx_msg void OnButtonDelete();
	afx_msg void OnButtonEdit();
	virtual BOOL OnInitDialog();
	afx_msg void OnShowWindow(BOOL bShow, UINT nStatus);
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()

protected:
  bool m_bEditingNode;

private:
  CList<CSerNode*, CSerNode*>*       m_pReceiverList;
  CList<CStretch*, CStretch*>* m_pStretchList;
  CList<CSerNode*, CSerNode*>        m_AddedReceiver;
  CList<CSerNode*, CSerNode*>        m_DeletedReceiver;
  CSerNode*                       m_pNodeToAdd;
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ fügt unmittelbar vor der vorhergehenden Zeile zusätzliche Deklarationen ein.

#endif // AFX_RECEIVERDLG_H__D7AF7F03_D0E4_11D3_8F98_525400E72BB3__INCLUDED_
