// StretchesDlg.cpp: Implementierungsdatei
//

#include "stdafx.h"
#include "Transop.h"
#include "StretchesDlg.h"

#include "Stretch.h"
#include "Node.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// Dialogfeld CStretchesDlg 


CStretchesDlg::CStretchesDlg(CList<CStretch*, CStretch*>* pStretchList,
    CList<CSerNode*, CSerNode*>* pProducerList,
    CList<CSerNode*, CSerNode*>* pReceiverList,
    CWnd* pParent /*=NULL*/)
	: CDialog(CStretchesDlg::IDD, pParent)
{
	//{{AFX_DATA_INIT(CStretchesDlg)
	m_dValue = 0.0;
	//}}AFX_DATA_INIT

  m_pProducerList = pProducerList;
  m_pReceiverList = pReceiverList;
  m_pStretchList = pStretchList;
}


void CStretchesDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CStretchesDlg)
	DDX_Control(pDX, IDC_LIST_CONNECTIONS, m_ListCtrlStretches);
	DDX_Control(pDX, IDC_COMBO_SOURCE, m_ComboProducer);
	DDX_Control(pDX, IDC_COMBO_DESTINATION, m_ComboReceiver);
	DDX_Text(pDX, IDC_EDIT_KM, m_dValue);
	DDV_MinMaxDouble(pDX, m_dValue, 0., 1000000.);
	//}}AFX_DATA_MAP
}


BEGIN_MESSAGE_MAP(CStretchesDlg, CDialog)
	//{{AFX_MSG_MAP(CStretchesDlg)
	ON_BN_CLICKED(IDC_BUTTON_ADD, OnButtonAdd)
	ON_BN_CLICKED(IDC_BUTTON_DELETE, OnButtonDelete)
	ON_WM_SHOWWINDOW()
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// Behandlungsroutinen für Nachrichten CStretchesDlg 

void CStretchesDlg::OnOK() 
{
  ASSERT(!(m_pStretchList == NULL));

  POSITION pos;
  CStretch*   pStretch;
  
  pos = m_addedStretchList.GetHeadPosition();

  while (pos)
  {
    pStretch = NULL;

    pStretch = m_addedStretchList.GetNext(pos);

    if (pStretch != NULL)
    {
      m_pStretchList->AddHead(pStretch);
    }
  }

  // now delete the deleted nodes
  pos = m_deletedStretchList.GetHeadPosition();

  while (pos)
  {
    pStretch = NULL;

    pStretch = m_deletedStretchList.GetNext(pos);

    if (pStretch != NULL)
    {
      m_pStretchList->RemoveAt(m_pStretchList->Find(pStretch));
      delete pStretch;
    }
  }

  CDialog::OnOK();
}

void CStretchesDlg::OnCancel() 
{
  POSITION  pos;
  CStretch* pStretch;
  
  pos = m_addedStretchList.GetHeadPosition();

  while (pos)
  {
    pStretch = NULL;

    pStretch = m_addedStretchList.GetNext(pos);

    if (pStretch != NULL)
    {
      delete pStretch;
    }
  }
	
	CDialog::OnCancel();
}

void CStretchesDlg::OnButtonAdd() 
{
  UpdateData();

  if (m_ComboProducer.GetCount() == 0 || m_ComboReceiver.GetCount() == 0)
  {
    return;
  }

  CStretch* pNewStretch = new CStretch();
  CSerNode*    pSourceNode;
  CSerNode*    pDestNode;
  CString   csValue;
  int       nItemCount;

  pSourceNode = NULL;
  pDestNode   = NULL;

  pSourceNode = (CSerNode*)m_ComboProducer.GetItemData(m_ComboProducer.GetCurSel());
  pDestNode   = (CSerNode*)m_ComboReceiver.GetItemData(m_ComboReceiver.GetCurSel());

  if (pSourceNode != NULL && pDestNode != NULL)
  {
    pNewStretch->setSourceNode(pSourceNode);
    pNewStretch->setDestinationNode(pDestNode);
    pNewStretch->setDistance(m_dValue);

    m_addedStretchList.AddHead(pNewStretch);
    
    nItemCount = m_ListCtrlStretches.GetItemCount();
    
    csValue.Format("%.0f",pNewStretch->getDistance());
    m_ListCtrlStretches.InsertItem(nItemCount, pNewStretch->getSourceNode()->getDescription());
    m_ListCtrlStretches.SetItemText(nItemCount, 1, pNewStretch->getDestinationNode()->getDescription());
    m_ListCtrlStretches.SetItemText(nItemCount, 2, csValue);
    m_ListCtrlStretches.SetItemData(nItemCount, (DWORD)pNewStretch);

    m_ComboProducer.SetFocus();
  }

  m_dValue = 0;

  UpdateData(FALSE);
}

void CStretchesDlg::OnButtonDelete() 
{
  int nCurSel = m_ListCtrlStretches.GetNextItem(-1, LVNI_SELECTED);

  if (nCurSel != -1)
  {
    CStretch* pStretch;

    pStretch = NULL;

    pStretch = (CStretch*)m_ListCtrlStretches.GetItemData(nCurSel);

    if (pStretch != NULL)
    {
      m_ListCtrlStretches.DeleteItem(nCurSel);
      POSITION pos = m_addedStretchList.Find(pStretch);
      if (pos != NULL)
      {
        m_addedStretchList.RemoveAt(pos);
        delete pStretch;
      }
      else
      {
        pos = m_pStretchList->Find(pStretch);
        if (pos != NULL)
        {
          m_deletedStretchList.AddHead(pStretch);
          //m_pStretchList->RemoveAt(pos);
        }
      }
    }
  }
}

BOOL CStretchesDlg::OnInitDialog() 
{
	CDialog::OnInitDialog();

  POSITION    pos;
  CSerNode*      pNode;
  CStretch*   pStretch;
  int         nCounter;
  CString     csValue;

  // fill ComboBox Producer 
  pos = m_pProducerList->GetHeadPosition();

  nCounter = 0;
  while (pos)
  {
    pNode = NULL;

    pNode = m_pProducerList->GetNext(pos);

    if (pNode != NULL)
    {
      m_ComboProducer.InsertString(nCounter, pNode->getDescription());
      m_ComboProducer.SetItemData(nCounter, (DWORD)pNode);
      nCounter++;
    }
  }
  m_ComboProducer.SetCurSel(0);

  // fill ComboBox Receiver
  pos = m_pReceiverList->GetHeadPosition();

  nCounter = 0;
  while (pos)
  {
    pNode = NULL;

    pNode = m_pReceiverList->GetNext(pos);

    if (pNode != NULL)
    {
      m_ComboReceiver.InsertString(nCounter, pNode->getDescription());
      m_ComboReceiver.SetItemData(nCounter, (DWORD)pNode);
      nCounter++;
    }
  }
  m_ComboReceiver.SetCurSel(0);

  // fill ListCtrl with defiened stretches
  CRect   rListCtrlRect;
  
  m_ListCtrlStretches.GetClientRect(rListCtrlRect);
  
  m_ListCtrlStretches.InsertColumn(0, "Von", LVCFMT_LEFT, (int)rListCtrlRect.Width()*0.45, 0);
  m_ListCtrlStretches.InsertColumn(1, "Nach", LVCFMT_LEFT, (int)rListCtrlRect.Width()*0.45), 0;
  m_ListCtrlStretches.InsertColumn(2, "km", LVCFMT_LEFT, (int)rListCtrlRect.Width()*0.1), 0;

  pos = m_pStretchList->GetHeadPosition();

  nCounter = 0;
  while (pos)
  {
    pStretch = NULL;
    pStretch = m_pStretchList->GetNext(pos);

    if (pNode != NULL)
    {            
      csValue.Format("%.0f",pStretch->getDistance());
      m_ListCtrlStretches.InsertItem(nCounter, pStretch->getSourceNode()->getDescription());
      m_ListCtrlStretches.SetItemText(nCounter, 1, pStretch->getDestinationNode()->getDescription());
      m_ListCtrlStretches.SetItemText(nCounter, 2, csValue);
      m_ListCtrlStretches.SetItemData(nCounter, (DWORD)pStretch);
      nCounter++;
    }
  }
  m_ListCtrlStretches.SetItemState(0, LVIS_SELECTED | LVIS_FOCUSED, LVIS_SELECTED | LVIS_FOCUSED);
  
	return TRUE;  // return TRUE unless you set the focus to a control
	              // EXCEPTION: OCX-Eigenschaftenseiten sollten FALSE zurückgeben
}

void CStretchesDlg::OnShowWindow(BOOL bShow, UINT nStatus) 
{
	CDialog::OnShowWindow(bShow, nStatus);
	
	// TODO: Code für die Behandlungsroutine für Nachrichten hier einfügen
	
}
