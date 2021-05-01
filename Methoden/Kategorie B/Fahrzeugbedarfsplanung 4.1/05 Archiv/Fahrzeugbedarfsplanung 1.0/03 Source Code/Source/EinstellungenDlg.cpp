// EinstellungenDlg.cpp: Implementierungsdatei
//

#include "stdafx.h"
#include "FaBePlan.h"
#include "EinstellungenDlg.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// Dialogfeld CEinstellungenDlg 


CEinstellungenDlg::CEinstellungenDlg(CWnd* pParent /*=NULL*/)
	: CDialog(CEinstellungenDlg::IDD, pParent)
{
	//{{AFX_DATA_INIT(CEinstellungenDlg)
	mLPPfad = _T("");
	mMOPSPfad = _T("");
	mXAPfad = _T("");
	mSolverAuswahl = -1;
	//}}AFX_DATA_INIT
}


void CEinstellungenDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CEinstellungenDlg)
	DDX_Text(pDX, IDC_LP_PFAD, mLPPfad);
	DDX_Text(pDX, IDC_MOPS_PFAD, mMOPSPfad);
	DDX_Text(pDX, IDC_XA_PFAD, mXAPfad);
	DDX_Radio(pDX, IDC_SOLVER_AUSWAHL, mSolverAuswahl);
	//}}AFX_DATA_MAP
}


BEGIN_MESSAGE_MAP(CEinstellungenDlg, CDialog)
	//{{AFX_MSG_MAP(CEinstellungenDlg)
	ON_BN_CLICKED(IDC_XA_SUCHEN, OnXASuchen)
	ON_BN_CLICKED(IDC_MOPS_SUCHEN, OnMOPSSuchen)
	ON_BN_CLICKED(IDC_LP_SUCHEN, OnLPSolveSuchen)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// Behandlungsroutinen für Nachrichten CEinstellungenDlg 
BOOL CEinstellungenDlg::OnInitDialog() 
{
	CDialog::OnInitDialog();
	
	//  Bitmaps an die Buttons anbringen
	mFolderBitmap.LoadBitmap(IDB_OPEN_FOLDER);

	CButton* button = (CButton*)GetDlgItem(IDC_XA_SUCHEN);
	button->SetBitmap(HBITMAP(mFolderBitmap));

	button = (CButton*)GetDlgItem(IDC_LP_SUCHEN);
	button->SetBitmap(HBITMAP(mFolderBitmap));

	button = (CButton*)GetDlgItem(IDC_MOPS_SUCHEN);
	button->SetBitmap(HBITMAP(mFolderBitmap));

	return TRUE;  // return TRUE unless you set the focus to a control
	              // EXCEPTION: OCX-Eigenschaftenseiten sollten FALSE zurückgeben
}


//////////////////////////////////////////////////////////////////
//  Button zum Suchen des XA-Pfades wurde gewählt
void CEinstellungenDlg::OnXASuchen() 
{
	//  Dialog darstellen und
	//  Pfad aktualisieren
	if ( !ShowSolverFileDialog(mXAPfad, "XA Solver wählen") )
		return;	//  Abbruch wurde gewählt
	
	UpdateData(FALSE);
}


//////////////////////////////////////////////////////////////////
//  Button zum Suchen des LP Solve-Pfades wurde gewählt
void CEinstellungenDlg::OnLPSolveSuchen() 
{
	//  Dialog darstellen und
	//  Pfad aktualisieren
	if ( !ShowSolverFileDialog(mLPPfad, "LP-Solve Solver wählen") )
		return;	//  Abbruch wurde gewählt
	
	UpdateData(FALSE);
}


//////////////////////////////////////////////////////////////////
//  Button zum Suchen des MOPS-Pfades wurde gewählt
void CEinstellungenDlg::OnMOPSSuchen() 
{
	//  Dialog darstellen und
	//  Pfad aktualisieren
	if ( !ShowSolverFileDialog(mMOPSPfad, "MOPS Solver wählen") )
		return;	//  Abbruch wurde gewählt
	
	UpdateData(FALSE);
}


//////////////////////////////////////////////////////////////////
//  Dialogbox zum Auswählen eines Solverpfades darstellen
//  Liefert true, wenn das Dialog mit 'OK' beendet wurde,
//  sonst false.
//  Im 'solverPfad' wird der gewählte Pfad zurückgeliefert
bool CEinstellungenDlg::ShowSolverFileDialog(CString& solverPfad, CString boxTitle)
{
	//  Dateiauswahldialog darstellen
	CString fileFilter = "Ausführbare Dateien (*.exe)|*.exe|Alle Dateien (*.*)|*.*||";
	
	CFileDialog fileDialog(TRUE,NULL,NULL /*"xa.exe"*/,
		OFN_HIDEREADONLY|OFN_OVERWRITEPROMPT|OFN_NOCHANGEDIR, fileFilter);
	
	fileDialog.m_ofn.lpstrTitle = boxTitle;

	//  Dialog darstellen
	if ( fileDialog.DoModal() != IDOK )
		return false;
	
	solverPfad = fileDialog.GetPathName();
	return true;
}



