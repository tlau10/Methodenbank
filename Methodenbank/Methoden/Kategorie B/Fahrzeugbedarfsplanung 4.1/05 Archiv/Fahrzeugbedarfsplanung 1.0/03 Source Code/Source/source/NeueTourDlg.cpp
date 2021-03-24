// NeueTourDlg.cpp: Implementierungsdatei
//

#include "stdafx.h"
#include "FaBePlan.h"
#include "NeueTourDlg.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// Dialogfeld CNeueTourDlg 


CNeueTourDlg::CNeueTourDlg(CWnd* pParent /*=NULL*/)
	: CDialog(CNeueTourDlg::IDD, pParent)
{
	//{{AFX_DATA_INIT(CNeueTourDlg)
	mAbfahrtOrt = _T("");
	mAnkunftOrt = _T("");
	mPersonen = 0;
	//}}AFX_DATA_INIT

	//  Zeiten initialisieren
	mAbfahrtZeitAsTime = CTime(2000, 11, 11, 0, 0, 0);
	mAnkunftZeitAsTime = CTime(2000, 11, 11, 0, 0, 0);
}


void CNeueTourDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CNeueTourDlg)
	DDX_Control(pDX, IDC_ANKUNFT_ZEIT, mAnkunftZeit);
	DDX_Control(pDX, IDC_ABFAHRT_ZEIT, mAbfahrtZeit);
	DDX_Text(pDX, IDC_ABFAHRT_ORT, mAbfahrtOrt);
	DDX_Text(pDX, IDC_ANKUNFT_ORT, mAnkunftOrt);
	DDX_Text(pDX, IDC_PERSONEN, mPersonen);
	DDV_MinMaxInt(pDX, mPersonen, 0, 99999);
	//}}AFX_DATA_MAP
}


BEGIN_MESSAGE_MAP(CNeueTourDlg, CDialog)
	//{{AFX_MSG_MAP(CNeueTourDlg)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()



/////////////////////////////////////////////////////////////////////////////
// Behandlungsroutinen für Nachrichten CNeueTourDlg 

BOOL CNeueTourDlg::OnInitDialog() 
{
	CDialog::OnInitDialog();
	
	//  Zeit-Controls initialisieren
	mAbfahrtZeit.SetFormat( _T("HH:mm") );
	mAnkunftZeit.SetFormat( _T("HH:mm") );

	//  Zeiten initialisieren
	mAbfahrtZeit.SetTime( &mAbfahrtZeitAsTime );
	mAnkunftZeit.SetTime( &mAnkunftZeitAsTime );


	return TRUE;  // return TRUE unless you set the focus to a control
	              // EXCEPTION: OCX-Eigenschaftenseiten sollten FALSE zurückgeben
}



//  Die Abfahrtsdaten setzen
void CNeueTourDlg::SetAbfahrt(const CString &ort, int stunde, int minute)
{
	mAbfahrtOrt = ort;
	mAbfahrtZeitAsTime = CTime(2000, 11, 11, stunde, minute, 0);

}


//  Die Ankunftsdaten setzen
void CNeueTourDlg::SetAnkunft(const CString &ort, int stunde, int minute)
{
	mAnkunftOrt = ort;
	mAnkunftZeitAsTime = CTime(2000, 11, 11, stunde, minute, 0);
}


//  Anzahl der Personen setzen 
void CNeueTourDlg::SetPersonenzahl(int personen)
{
	mPersonen = personen;
}




//  Die Abfahrtsdaten auslesen
CString CNeueTourDlg::GetAbfahrtOrt() const
{
	return mAbfahrtOrt;
}

int CNeueTourDlg::GetAbfahrtStunde() const
{
	return mAbfahrtZeitAsTime.GetHour();
}

int CNeueTourDlg::GetAbfahrtMinute() const
{
	return mAbfahrtZeitAsTime.GetMinute();
}


//  Die Ankunftsdaten auslesen
CString CNeueTourDlg::GetAnkunftOrt() const
{
	return mAnkunftOrt;
}

int CNeueTourDlg::GetAnkunftStunde() const
{
	return mAnkunftZeitAsTime.GetHour();
}

int CNeueTourDlg::GetAnkunftMinute() const
{
	return mAnkunftZeitAsTime.GetMinute();
}


//  Anzahl der Personen auslesen
int CNeueTourDlg::GetPersonenzahl()
{
	return mPersonen;

}


//  Dialog wurde mit OK verlassen
void CNeueTourDlg::OnOK() 
{
	//  Zeiten aktualisieren
	mAbfahrtZeit.GetTime( mAbfahrtZeitAsTime );
	mAnkunftZeit.GetTime( mAnkunftZeitAsTime );
	
	//  Überprüfen, ob Ankunft später erfolgt
	//  als die Abfahrt
	if ( mAnkunftZeitAsTime <= mAbfahrtZeitAsTime )
	{
		::AfxMessageBox("Die Abfahrt muss vor der Ankunft erfolgen!\nBitte die Zeiten überprüfen.\n");
		return;
	}

	CDialog::OnOK();
}



