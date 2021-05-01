// PathPropertiesDlg.cpp: Implementierungsdatei
//

#include "stdafx.h"
#include "Transop.h"
#include "PathPropertiesDlg.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// Dialogfeld CPathPropertiesDlg 


CPathPropertiesDlg::CPathPropertiesDlg(CWnd* pParent /*=NULL*/)
	: CDialog(CPathPropertiesDlg::IDD, pParent)
{
	//{{AFX_DATA_INIT(CPathPropertiesDlg)
	m_csSolverPath = _T("");
	m_csWorkingDir = _T("");
	//}}AFX_DATA_INIT
}


void CPathPropertiesDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CPathPropertiesDlg)
	DDX_Text(pDX, IDC_EDIT_SOLVERPATH, m_csSolverPath);
	DDX_Text(pDX, IDC_EDIT_WORKINGDIR, m_csWorkingDir);
	//}}AFX_DATA_MAP
}


BEGIN_MESSAGE_MAP(CPathPropertiesDlg, CDialog)
	//{{AFX_MSG_MAP(CPathPropertiesDlg)
	ON_BN_CLICKED(IDC_BUTTON_CHANGE_WORKINGDIR, OnButtonChangeWorkingdir)
	ON_BN_CLICKED(IDC_BUTTONCHANGESOLVERPATH, OnButtonChangeSolverPath)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// Behandlungsroutinen für Nachrichten CPathPropertiesDlg 

void CPathPropertiesDlg::OnButtonChangeWorkingdir() 
{
  CHAR         Buffer[MAX_PATH];
  LPITEMIDLIST tmp;
  BROWSEINFO   bi;

  bi.hwndOwner      = this->m_hWnd;
  bi.pidlRoot       = NULL;
  bi.pszDisplayName = Buffer;
  bi.lpszTitle      = "Wählen sie das gewünschte Arbeitsverzeichnis:";
  bi.ulFlags        = 0;//BIF_EDITBOX;
  bi.lpfn           = NULL;
  bi.lParam         = 0;
  
  tmp = SHBrowseForFolder(&bi);

  if (tmp == NULL)
  {
    return;   
  } 

  SHGetPathFromIDList(tmp, Buffer);

  m_csWorkingDir = Buffer;

  UpdateData(FALSE);
}

void CPathPropertiesDlg::OnButtonChangeSolverPath() 
{
  CHAR         Buffer[MAX_PATH];
  LPITEMIDLIST tmp;
  BROWSEINFO   bi;

  bi.hwndOwner      = this->m_hWnd;
  bi.pidlRoot       = NULL;
  bi.pszDisplayName = Buffer;
  bi.lpszTitle      = "Wählen sie den LP-Solve Programmpfad:";
  bi.ulFlags        = 0;//BIF_EDITBOX;
  bi.lpfn           = NULL;
  bi.lParam         = 0;
  
  tmp = SHBrowseForFolder(&bi);

  if (tmp == NULL)
  {
    return;   
  } 

  SHGetPathFromIDList(tmp, Buffer);

  m_csSolverPath = Buffer;
	
  UpdateData(FALSE);
}
