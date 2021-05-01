// ProducerDlg.cpp: Implementierungsdatei
//

#include "stdafx.h"
#include "Transop.h"
#include "ProducerDlg.h"
#include "Stretch.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// Dialogfeld CProducerDlg 


CProducerDlg::CProducerDlg(CList<CSerNode*,CSerNode*>* producerList, 
                           CList<CStretch*, CStretch*>* pStretchList,
                           CWnd* pParent /*=NULL*/)
	: CDialog(CProducerDlg::IDD, pParent)
{
	//{{AFX_DATA_INIT(CProducerDlg)
	m_csProducerName = _T("");
	m_dValue = 0.0;
	//}}AFX_DATA_INIT

  m_pProducerList = NULL;
  m_pStretchList  = NULL;
  m_pNodeToAdd   = NULL;
  m_pStretchList   = pStretchList;
  m_pProducerList = producerList;

  m_bEditingNode = false;
}


void CProducerDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CProducerDlg)
	DDX_Control(pDX, IDC_EDIT_PRODUCERNAME, m_EditProducerName);
	DDX_Control(pDX, IDC_LIST_PRODUCER, m_ListCtrlProducer);
	DDX_Text(pDX, IDC_EDIT_PRODUCERNAME, m_csProducerName);
	DDX_Text(pDX, IDC_EDIT_PIECES, m_dValue);
	DDV_MinMaxDouble(pDX, m_dValue, 0., 1000000.);
	//}}AFX_DATA_MAP
}


BEGIN_MESSAGE_MAP(CProducerDlg, CDialog)
	//{{AFX_MSG_MAP(CProducerDlg)
	ON_BN_CLICKED(IDC_BUTTON_ADD, OnButtonAdd)
	ON_BN_CLICKED(IDC_BUTTON_DELETE, OnButtonDelete)
	ON_BN_CLICKED(IDC_BUTTON_EDIT, OnButtonEdit)
	ON_WM_SHOWWINDOW()
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// Behandlungsroutinen für Nachrichten CProducerDlg 

void CProducerDlg::OnButtonAdd() 
{
  UpdateData();

  if (m_pNodeToAdd == NULL && !m_bEditingNode)
  {
    m_pNodeToAdd  = new CSerNode(); 
  }

  CString csValue;
  int     nItemCount;

  m_pNodeToAdd ->setDescription(m_csProducerName);

  m_pNodeToAdd ->setValue(m_dValue);


  if (!m_bEditingNode)
  {
    m_AddedProducers.AddHead(m_pNodeToAdd);
  }

  nItemCount = m_ListCtrlProducer.GetItemCount();

  csValue.Format("%.0f",m_pNodeToAdd->getValue());
  m_ListCtrlProducer.InsertItem(nItemCount, m_pNodeToAdd->getDescription());
  m_ListCtrlProducer.SetItemText(nItemCount, 1, csValue);
  m_ListCtrlProducer.SetItemData(nItemCount, (DWORD)m_pNodeToAdd);

  m_csProducerName = "";
  m_dValue = 0;

  m_pNodeToAdd = NULL;
  m_bEditingNode = false;

  m_EditProducerName.SetFocus();

  UpdateData(FALSE);

}

void CProducerDlg::OnButtonDelete() 
{
  int nCurSel = m_ListCtrlProducer.GetNextItem(-1, LVNI_SELECTED);

  if (nCurSel != -1)
  {

    POSITION posStretch;
    CSerNode*    pNode;
    CStretch* pStretch;

    pNode = NULL;


    pNode = (CSerNode*)m_ListCtrlProducer.GetItemData(nCurSel);

    if (pNode != NULL)
    {
	
      // delete Item
      POSITION pos = m_AddedProducers.Find(pNode);
      if (pos != NULL)
      {
        m_AddedProducers.RemoveAt(pos);
        m_ListCtrlProducer.DeleteItem(nCurSel);
        delete pNode;
      }
      else
      {
        pos = m_pProducerList->Find(pNode);
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
              MessageBox("Es besteht eine definierte Strecke mit diesem Knoten. Bitte löschen sie zuerst die Strecke.", 
                "Transop", 
                MB_OK | MB_ICONERROR);
              return;
            }
          }
          m_DeletedProducers.AddHead(pNode);
          m_ListCtrlProducer.DeleteItem(nCurSel);
        }
      }
    }
  }
}

void CProducerDlg::OnButtonEdit() 
{
  int nCurSel = m_ListCtrlProducer.GetNextItem(-1, LVNI_SELECTED);

  if (nCurSel != -1)
  {
    CSerNode* pNode;
    pNode = NULL;

    pNode = (CSerNode*)m_ListCtrlProducer.GetItemData(nCurSel);

    if (pNode != NULL)
    {
      // set values
      m_csProducerName = pNode->getDescription();
      m_dValue = pNode->getValue();
      UpdateData(FALSE);

      // delete Item
      m_ListCtrlProducer.DeleteItem(nCurSel);

      m_bEditingNode = true;
      m_pNodeToAdd = pNode;
      m_EditProducerName.SetFocus();
    }
  }
}


void CProducerDlg::OnCancel() 
{
  POSITION pos;
  CSerNode*   pNode;
  
  pos = m_AddedProducers.GetHeadPosition();

  while (pos)
  {
    pNode = NULL;

    pNode = m_AddedProducers.GetNext(pos);

    if (pNode != NULL)
    {
      delete pNode;
    }
  }
	CDialog::OnCancel();
}

BOOL CProducerDlg::OnInitDialog() 
{
	CDialog::OnInitDialog();
	
  ASSERT(!(m_pProducerList == NULL));

  CSerNode*  pNode;
  int     nCounter;
  CString csValue;
  CRect   rListCtrlRect;

  m_ListCtrlProducer.GetClientRect(rListCtrlRect);

  m_ListCtrlProducer.InsertColumn(0, "Name", LVCFMT_LEFT, rListCtrlRect.Width()*0.75, 0);
  m_ListCtrlProducer.InsertColumn(1, "Stückzahl", LVCFMT_LEFT, rListCtrlRect.Width()*0.25), 0;

  
  POSITION pos;

  pos = NULL;

  pos = m_pProducerList->GetHeadPosition();

  nCounter = 0;
  while (pos)
  {
    pNode = NULL;
    pNode = m_pProducerList->GetNext(pos);

    if (pNode != NULL)
    {            
      csValue.Format("%.0f",pNode->getValue());
      m_ListCtrlProducer.InsertItem(nCounter, pNode->getDescription());
      m_ListCtrlProducer.SetItemText(nCounter, 1, csValue);
      m_ListCtrlProducer.SetItemData(nCounter, (DWORD)pNode);
      nCounter++;
    }
  }
  m_ListCtrlProducer.SetItemState(0, LVIS_SELECTED | LVIS_FOCUSED, LVIS_SELECTED | LVIS_FOCUSED);

	return TRUE;  // return TRUE unless you set the focus to a control
	              // EXCEPTION: OCX-Eigenschaftenseiten sollten FALSE zurückgeben
}

void CProducerDlg::OnShowWindow(BOOL bShow, UINT nStatus) 
{
	CDialog::OnShowWindow(bShow, nStatus);
	
  if (m_ListCtrlProducer.GetItemCount() != 0)
  {
    m_ListCtrlProducer.SetFocus();
  }
}

void CProducerDlg::OnOK() 
{
  ASSERT(!(m_pProducerList == NULL));

  POSITION pos;
  CSerNode*   pNode;
  
  pos = m_AddedProducers.GetHeadPosition();

  while (pos)
  {
    pNode = NULL;

    pNode = m_AddedProducers.GetNext(pos);

    if (pNode != NULL)
    {
      m_pProducerList->AddHead(pNode);
    }
  }

  // now delete the deleted nodes
  pos = m_DeletedProducers.GetHeadPosition();

  while (pos)
  {
    pNode = NULL;

    pNode = m_DeletedProducers.GetNext(pos);

    if (pNode != NULL)
    {
      m_pProducerList->RemoveAt(m_pProducerList->Find(pNode));
      delete pNode;
    }
  }
	
	CDialog::OnOK();
}
