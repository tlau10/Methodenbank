#if !defined(AFX_NEUETOURDLG_H__704C40DE_ACC4_11D4_AE92_0010A7025D55__INCLUDED_)
#define AFX_NEUETOURDLG_H__704C40DE_ACC4_11D4_AE92_0010A7025D55__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
// NeueTourDlg.h : Header-Datei
//

/////////////////////////////////////////////////////////////////////////////
// Dialogfeld CNeueTourDlg 

class CNeueTourDlg : public CDialog
{
// Konstruktion
public:
	int GetPersonenzahl(void);
	int GetAnkunftMinute(void) const;
	int GetAnkunftStunde(void) const;
	int GetAbfahrtStunde(void) const;
	int GetAbfahrtMinute(void) const;
	CString GetAnkunftOrt(void) const;
	CString GetAbfahrtOrt(void) const;
	void SetPersonenzahl(int personen);
	void SetAnkunft(const CString& ort, int stunde, int minute);
	void SetAbfahrt(const CString& ort, int stunde, int minute);
	CNeueTourDlg(CWnd* pParent = NULL);   // Standardkonstruktor

// Dialogfelddaten
	//{{AFX_DATA(CNeueTourDlg)
	enum { IDD = IDD_DIALOG_TOUR_EINGEBEN };
	CDateTimeCtrl	mAnkunftZeit;
	CDateTimeCtrl	mAbfahrtZeit;
	CString	mAbfahrtOrt;
	CString	mAnkunftOrt;
	int		mPersonen;
	//}}AFX_DATA


// Überschreibungen
	// Vom Klassen-Assistenten generierte virtuelle Funktionsüberschreibungen
	//{{AFX_VIRTUAL(CNeueTourDlg)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV-Unterstützung
	//}}AFX_VIRTUAL

// Implementierung
protected:

	// Generierte Nachrichtenzuordnungsfunktionen
	//{{AFX_MSG(CNeueTourDlg)
	virtual BOOL OnInitDialog();
	virtual void OnOK();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
private:
	CTime mAnkunftZeitAsTime;
	CTime mAbfahrtZeitAsTime;
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ fügt unmittelbar vor der vorhergehenden Zeile zusätzliche Deklarationen ein.

#endif // AFX_NEUETOURDLG_H__704C40DE_ACC4_11D4_AE92_0010A7025D55__INCLUDED_
