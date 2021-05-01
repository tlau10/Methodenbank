// ReceiverDlg.cpp: Implementierungsdatei
//

#include "stdafx.h"
#include "Transop.h"
#include "ReceiverDlg.h"
#include "Stretch.h"


#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// Dialogfeld CReceiverDlg 


CReceiverDlg::CReceiverDlg(CList<CSerNode*,CSerNode*>* receiverList, CList<CStretch*, CStretch*>* pStretchList,
                           CWnd* pParent /*=NULL*/)
	: CDialog(CReceiverDlg::IDD, pParent)
{
	//{{AFX_DATA_INIT(CReceiverDlg)
	m_dValue = 0.0;
	m_csReceiverName = _T("");
	//}}AFX_DATA_INIT

  m_pReceiverList = NULL;
  m_pStretchList  = NULL;
  m_pNodeToAdd    = NULL;
  m_pReceiverList = receiverList;
  m_pStretchList  = pStretchList;

  m_bEditingNode = false;
}


void CReceiverDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CReceiverDlg)
	DDX_Control(pDX, IDC_EDIT_RECEIVERNAME, m_EditReceiverName);
	DDX_Control(pDX, IDC_LIST_RECEIVER, m_ListCtrlReceiver);
	DDX_Text(pDX, IDC_EDIT_PIECES, m_dValue);
	DDV_MinMaxDouble(pDX, m_dValue, 0., 1000000.);
	DDX_Text(pDX, IDC_EDIT_RECEIVERNAME, m_csReceiverName);
	//}}AFX_DATA_MAP
}


BEGIN_MESSAGE_MAP(CReceiverDlg, CDialog)
	//{{AFX_MSG_MAP(CReceiverDlg)
	ON_BN_CLICKED(IDC_BUTTON_ADD, OnButtonAdd)
	ON_BN_CLICKED(IDC_BUTTON_DELETE, OnButtonDelete)
	ON_BN_CLICKED(IDC_BUTTON_EDIT, OnButtonEdit)
	ON_WM_SHOWWINDOW()
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// Behandlungsroutinen für Nachrichten CReceiverDlg 

void CReceiverDlg::OnOK() 
{
  ASSERT(!(m_pReceiverList == NULL));

  POSITION pos;
  CSerNode*   pNode;
  
  pos = m_AddedReceiver.GetHeadPosition();

  while (pos)
  {
    pNode = NULL;

    pNode = m_AddedReceiver.GetNext(pos);

    if (pNode != NULL)
    {
      m_pReceiverList->AddHead(pNode);
    }
  }

  // now delete the deleted nodes
  pos = m_DeletedReceiver.GetHeadPosition();

  while (pos)
  {
    pNode = NULL;

    pNode = m_DeletedReceiver.GetNext(pos);

    if (pNode != NULL)
    {
      m_pReceiverList->RemoveAt(m_pReceiverList->Find(pNode));
      delete pNode;
    }
  }

	
	CDialog::OnOK();
}

void CReceiverDlg::OnCancel() 
{
  POSITION pos;
  CSerNode*   pNode;
  
  pos = m_AddedReceiver.GetHeadPosition();

  while (pos)
  {
    pNode = NULL;

    pNode = m_AddedReceiver.GetNext(pos);

    if (pNode != NULL)
    {
      delete pNode;
    }
  }
	
	CDialog::OnCancel();
}

void CReceiverDlg::OnButtonAdd() 
{
  UpdateData();

  if (m_pNodeToAdd == NULL && !m_bEditingNode)
  {
    m_pNodeToAdd  = new CSerNode(); 
  }

  CString csValue;
  int     nItemCount;

  m_pNodeToAdd->setDescription(m_csReceiverName);

  m_pNodeToAdd->setValue(m_dValue);

  if (!m_bEditingNode)
  {
    m_AddedReceiver.AddHead(m_pNodeToAdd);
  }

  nItemCount = m_ListCtrlReceiver.GetItemCount();

  csValue.Format("%.0f",m_pNodeToAdd->getValue());
  m_ListCtrlReceiver.InsertItem(nItemCount, m_pNodeToAdd->getDescription());
  m_ListCtrlReceiver.SetItemText(nItemCount, 1, csValue);
  m_ListCtrlReceiver.SetItemData(nItemCount, (DWORD)m_pNodeToAdd);

  m_csReceiverName = "";
  m_dValue = 0;

  m_pNodeToAdd = NULL;
  m_bEditingNode = false;

  m_EditReceiverName.SetFocus();

  UpdateData(FALSE);
}

void CReceiverDlg::OnButtonDelete() 
{
  int nCurSel = m_ListCtrlReceiver.GetNextItem(-1, LVNI_SELECTED);

  if (nCurSel != -1)
  {
    POSITION posStretch;
    CSerNode*    pNode;
    CStretch* pStretch;

    pNode = NULL;

    pNode = (CSerNode*)m_ListCtrlReceiver.GetItemData(nCurSel);

    if (pNode != NULL)
    {
	
      // delete Item
      POSITION pos = m_AddedReceiver.Find(pNode);
      if (pos != NULL)
      {
        m_AddedReceiver.RemoveAt(pos);
        m_ListCtrlReceiver.DeleteItem(nCurSel);
        delete pNode;
      }
      else
      {
        pos = m_pReceiverList->Find(pNode);
        if (pos != NULL)
        {
          // check if there is  stretch with this node definied
          posStretch = m_pStretchList->GetHeadPosition();

          while (posStretch)
          {
            pStretch = NULL;
            pStretch = m_pStretchList->GetNext(posStretch);

            if (pStretch->getSourceNode() == pNode || pStretch->getDestinationNode() == pNode)
            {
              MessageBox("Es besteht eine definiert Strecke mit diesem Knoten. Bitte löschen sie zuerst die Strecke.", 
                "Transop", 
                MB_OK | MB_ICONERROR);
              return;
            }
          }
          m_DeletedReceiver.AddHead(pNode);
          m_ListCtrlReceiver.DeleteItem(nCurSel);
        }
      }
    }
  }
}

void CReceiverDlg::OnButtonEdit() 
{
  int nCurSel = m_ListCtrlReceiver.GetNextItem(-1, LVNI_SELECTED);

  if (nCurSel != -1)
  {
    CSerNode* pNode;
    pNode = NULL;

    pNode = (CSerNode*)m_ListCtrlReceiver.GetItemData(nCurSel);

    if (pNode != NULL)
    {
      // set values
      m_csReceiverName = pNode->getDescription();
      m_dValue = pNode->getValue();
      UpdateData(FALSE);

      // delete Item
      m_ListCtrlReceiver.DeleteItem(nCurSel);
      
      m_bEditingNode = true;
      m_pNodeToAdd = pNode;

      m_EditReceiverName.SetFocus();
    }
  }
}

BOOL CReceiverDlg::OnInitDialog() 
{
	CDialog::OnInitDialog();
	
  ASSERT(!(m_pReceiverList == NULL));

  CSerNode*  pNode;
  int     nCounter;
  CString csValue;
  CRect   rListCtrlRect;

  m_ListCtrlReceiver.GetClientRect(rListCtrlRect);

  m_ListCtrlReceiver.InsertColumn(0, "Name", LVCFMT_LEFT, rListCtrlRect.Width()*0.75, 0);
  m_ListCtrlReceiver.InsertColumn(1, "Stückzahl", LVCFMT_LEFT, rListCtrlRect.Width()*0.25), 0;

  
  POSITION pos;

  pos = NULL;

  pos = m_pReceiverList->GetHeadPosition();

  nCounter = 0;
  while (pos)
  {
    pNode = NULL;
    pNode = m_pReceiverList->GetNext(pos);

    if (pNode != NULL)
    {            
      csValue.Format("%.0f",pNode->getValue());
      m_ListCtrlReceiver.InsertItem(nCounter, pNode->getDescription());
      m_ListCtrlReceiver.SetItemText(nCounter, 1, csValue);
      m_ListCtrlReceiver.SetItemData(nCounter, (DWORD)pNode);
      nCounter++;
    }
  }
  m_ListCtrlReceiver.SetItemState(0, LVIS_SELECTED | LVIS_FOCUSED, LVIS_SELECTED | LVIS_FOCUSED);
	
	return TRUE;  // return TRUE unless you set the focus to a control
	              // EXCEPTION: OCX-Eigenschaftenseiten sollten FALSE zurückgeben
}

void CReceiverDlg::OnShowWindow(BOOL bShow, UINT nStatus) 
{
	CDialog::OnShowWindow(bShow, nStatus);
	
  if (m_ListCtrlReceiver.GetItemCount() != 0)
  {
    m_ListCtrlReceiver.SetFocus();
  }
}
