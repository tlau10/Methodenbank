// TransporterDlg.cpp: Implementierungsdatei
//

#include "stdafx.h"
#include "Transop.h"
#include "TransporterDlg.h"
#include "Transporter.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// Dialogfeld CTransporterDlg 


CTransporterDlg::CTransporterDlg(CList<CTransporter*, CTransporter*>* pTransporterList,
                                 CWnd* pParent /*=NULL*/)
	: CDialog(CTransporterDlg::IDD, pParent)
{
	//{{AFX_DATA_INIT(CTransporterDlg)
	m_nCap1 = 0;
	m_nCap2 = 0;
	m_dCosts1 = 0.0;
	m_dCosts2 = 0.0;
	m_csName1 = _T("");
	m_csName2 = _T("");
	//}}AFX_DATA_INIT

  m_pTransporterList = pTransporterList;
}


void CTransporterDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CTransporterDlg)
	DDX_Text(pDX, IDC_EDIT_CAP1, m_nCap1);
	DDX_Text(pDX, IDC_EDIT_CAP2, m_nCap2);
	DDX_Text(pDX, IDC_EDIT_COSTS1, m_dCosts1);
	DDX_Text(pDX, IDC_EDIT_COSTS2, m_dCosts2);
	DDX_Text(pDX, IDC_EDIT_NAME1, m_csName1);
	DDX_Text(pDX, IDC_EDIT_NAME2, m_csName2);
	//}}AFX_DATA_MAP
}


BEGIN_MESSAGE_MAP(CTransporterDlg, CDialog)
	//{{AFX_MSG_MAP(CTransporterDlg)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// Behandlungsroutinen für Nachrichten CTransporterDlg 

void CTransporterDlg::OnOK() 
{
  UpdateData();

  m_pTransporterList->GetHead()->setDescription(m_csName1);
  m_pTransporterList->GetTail()->setDescription(m_csName2 );

  m_pTransporterList->GetHead()->setCostsPerKm(m_dCosts1);
  m_pTransporterList->GetTail()->setCostsPerKm(m_dCosts2);

  m_pTransporterList->GetHead()->setCapacity(m_nCap1);
  m_pTransporterList->GetTail()->setCapacity(m_nCap2);

  UpdateData(FALSE);
	
	CDialog::OnOK();
}

BOOL CTransporterDlg::OnInitDialog() 
{
	CDialog::OnInitDialog();

  ASSERT(!(m_pTransporterList == NULL));	

  m_csName1 = m_pTransporterList->GetHead()->getDescription();
  m_csName2 = m_pTransporterList->GetTail()->getDescription();

  m_dCosts1 = m_pTransporterList->GetHead()->getCostsPerKm();
  m_dCosts2 = m_pTransporterList->GetTail()->getCostsPerKm();

  m_nCap1 = m_pTransporterList->GetHead()->getCapacity();
  m_nCap2 = m_pTransporterList->GetTail()->getCapacity();

  UpdateData(FALSE);
	
	return TRUE;  // return TRUE unless you set the focus to a control
	              // EXCEPTION: OCX-Eigenschaftenseiten sollten FALSE zurückgeben
}
