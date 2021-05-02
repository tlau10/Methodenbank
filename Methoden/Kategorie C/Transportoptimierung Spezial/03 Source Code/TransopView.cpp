// TransopView.cpp : Implementierung der Klasse CTransopView
//

#include "stdafx.h"
#include "Transop.h"

#include "TransopDoc.h"
#include "TransopView.h"

#include "LPOutputFile.h"
#include "TransopModel.h"
#include "LPInputFile.h"

#include "StretchesDlg.h"
#include "ReceiverDlg.h"
#include "ProducerDlg.h"
#include "TransporterDlg.h"
#include "TransopStretch.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif


/////////////////////////////////////////////////////////////////////////////
// CTransopView

IMPLEMENT_DYNCREATE(CTransopView, CFormView)

BEGIN_MESSAGE_MAP(CTransopView, CFormView)
	//{{AFX_MSG_MAP(CTransopView)
	ON_COMMAND(ID_PROP_CONNECTION, OnPropConnection)
	ON_COMMAND(ID_PROP_PRODUCER, OnPropProducer)
	ON_COMMAND(ID_PROP_RECEIVER, OnPropReceiver)
	ON_COMMAND(ID_PROP_TRANSPORTER, OnPropTransporter)
	ON_WM_CREATE()
	ON_WM_SHOWWINDOW()
	ON_WM_SIZE()
	//}}AFX_MSG_MAP
	// Standard-Druckbefehle
	ON_COMMAND(ID_FILE_PRINT, CFormView::OnFilePrint)
	ON_COMMAND(ID_FILE_PRINT_DIRECT, CFormView::OnFilePrint)
	ON_COMMAND(ID_FILE_PRINT_PREVIEW, CFormView::OnFilePrintPreview)
	ON_STN_CLICKED(IDC_PRODPOS, &CTransopView::OnStnClickedProdpos)
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CTransopView Konstruktion/Destruktion

CTransopView::CTransopView()
	: CFormView(CTransopView::IDD)
{
	//{{AFX_DATA_INIT(CTransopView)
	m_csCap1 = _T("");
	m_csCap2 = _T("");
	m_csCosts1 = _T("");
	m_csCosts2 = _T("");
	m_csTransName1 = _T("");
	m_csTransName2 = _T("");
	m_csGK = _T("");
	//}}AFX_DATA_INIT
	// ZU ERLEDIGEN: Hier Code zur Konstruktion einfügen,

}

CTransopView::~CTransopView()
{
}

void CTransopView::DoDataExchange(CDataExchange* pDX)
{
	CFormView::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CTransopView)
	DDX_Control(pDX, IDC_SOLUTIONPOS, m_StaticSolutionPos);
	DDX_Control(pDX, IDC_STRETCHPOS, m_StaticStretchPos);
	DDX_Control(pDX, IDC_RECPOS, m_StaticRecPos);
	DDX_Control(pDX, IDC_PRODPOS, m_StaticProdPos);
	DDX_Text(pDX, IDC_STATIC_CAP1, m_csCap1);
	DDX_Text(pDX, IDC_STATIC_CAP2, m_csCap2);
	DDX_Text(pDX, IDC_STATIC_COSTS1, m_csCosts1);
	DDX_Text(pDX, IDC_STATIC_COSTS2, m_csCosts2);
	DDX_Text(pDX, IDC_STATIC_TRANS1TEXT, m_csTransName1);
	DDX_Text(pDX, IDC_STATIC_TRANS2TEXT, m_csTransName2);
	DDX_Text(pDX, IDC_GK, m_csGK);
	//}}AFX_DATA_MAP
}

BOOL CTransopView::PreCreateWindow(CREATESTRUCT& cs)
{
	// ZU ERLEDIGEN: Ändern Sie hier die Fensterklasse oder das Erscheinungsbild, indem Sie
	//  CREATESTRUCT cs modifizieren.

	return CFormView::PreCreateWindow(cs);
}

void CTransopView::OnInitialUpdate()
{
	CFormView::OnInitialUpdate();
	ResizeParentToFit();

  GetParent()->ShowWindow(SW_SHOWMAXIMIZED);

  RECT         rPosRect;
  
  if(m_StaticProdPos.m_hWnd != NULL)
  {
    m_StaticProdPos.GetWindowRect(&rPosRect);
    
    ScreenToClient(&rPosRect);
    m_ProducerListCtrl.MoveWindow(&rPosRect);
    m_ProducerListCtrl.SetColumnWidth(0, (rPosRect.right-rPosRect.left)*0.75);
    m_ProducerListCtrl.SetColumnWidth(1, (rPosRect.right-rPosRect.left)*0.25);
  }

  if(m_StaticRecPos.m_hWnd != NULL)
  {
    m_StaticRecPos.GetWindowRect(&rPosRect);
    
    ScreenToClient(&rPosRect);
    m_ReceiverListCtrl.MoveWindow(&rPosRect);
    m_ReceiverListCtrl.SetColumnWidth(0, (rPosRect.right-rPosRect.left)*0.75);
    m_ReceiverListCtrl.SetColumnWidth(1, (rPosRect.right-rPosRect.left)*0.25);
  }

  if(m_StaticStretchPos.m_hWnd != NULL)
  {
    m_StaticStretchPos.GetWindowRect(&rPosRect);
    
    ScreenToClient(&rPosRect);
    m_StretchListCtrl.MoveWindow(&rPosRect);
    m_StretchListCtrl.SetColumnWidth(0, (rPosRect.right-rPosRect.left)*0.45);
    m_StretchListCtrl.SetColumnWidth(1, (rPosRect.right-rPosRect.left)*0.45);
    m_StretchListCtrl.SetColumnWidth(2, (rPosRect.right-rPosRect.left)*0.1);
  }

  if(m_StaticSolutionPos.m_hWnd != NULL)
  {
    m_StaticSolutionPos.GetWindowRect(&rPosRect);
    
    ScreenToClient(&rPosRect);
    m_SolutionCtrl.MoveWindow(&rPosRect);
    m_SolutionCtrl.SetColumnWidth(0, (rPosRect.right-rPosRect.left)/5);
    m_SolutionCtrl.SetColumnWidth(1, (rPosRect.right-rPosRect.left)/5);
    m_SolutionCtrl.SetColumnWidth(2, (rPosRect.right-rPosRect.left)/5);
    m_SolutionCtrl.SetColumnWidth(3, (rPosRect.right-rPosRect.left)/5);
    m_SolutionCtrl.SetColumnWidth(4, (rPosRect.right-rPosRect.left)/5);
  }
}

/////////////////////////////////////////////////////////////////////////////
// CTransopView Drucken

BOOL CTransopView::OnPreparePrinting(CPrintInfo* pInfo)
{
	// Standardvorbereitung
	return DoPreparePrinting(pInfo);
}

void CTransopView::OnBeginPrinting(CDC* /*pDC*/, CPrintInfo* /*pInfo*/)
{
	// ZU ERLEDIGEN: Zusätzliche Initialisierung vor dem Drucken hier einfügen
}

void CTransopView::OnEndPrinting(CDC* /*pDC*/, CPrintInfo* /*pInfo*/)
{
	// ZU ERLEDIGEN: Hier Bereinigungsarbeiten nach dem Drucken einfügen
}

void CTransopView::OnPrint(CDC* pDC, CPrintInfo* /*pInfo*/)
{
	// ZU ERLEDIGEN: Benutzerdefinierten Code zum Ausdrucken hier einfügen
}

/////////////////////////////////////////////////////////////////////////////
// CTransopView Diagnose

#ifdef _DEBUG
void CTransopView::AssertValid() const
{
	CFormView::AssertValid();
}

void CTransopView::Dump(CDumpContext& dc) const
{
	CFormView::Dump(dc);
}

CTransopDoc* CTransopView::GetDocument() // Die endgültige (nicht zur Fehlersuche kompilierte) Version ist Inline
{
	ASSERT(m_pDocument->IsKindOf(RUNTIME_CLASS(CTransopDoc)));
	return (CTransopDoc*)m_pDocument;
}
#endif //_DEBUG

/////////////////////////////////////////////////////////////////////////////
// CTransopView Nachrichten-Handler

//DEL void CTransopView::OnButton1() 
//DEL {
//DEL   ////////////////////////////////////////////////////////////////////////
//DEL   // OutputFile Test
//DEL   CLPOutputFile file;
//DEL 
//DEL   double val2;
//DEL 
//DEL   file.Open("D:\\Studium\\WI8\\AnwBetrSys\\Transportoptimierung\\LPSolve\\lp_solve.out");
//DEL 
//DEL   double val = file.getValueOfObjective();
//DEL   int nValueCount = file.getSolutionValueCount();
//DEL   if (!file.getSolutionValue(6,&val2))
//DEL   {
//DEL     TRACE("Index nicht gefunden");
//DEL   }
//DEL   file.Close();
//DEL   ////////////////////////////////////////////////////////////////////////
//DEL 
//DEL   ////////////////////////////////////////////////////////////////////////
//DEL   // InputFile Test
//DEL   CTransopModel   lpModel;  
//DEL   CTargetFunction targetFunction(5); 
//DEL   CLPInputFile    inputFile(&lpModel); 
//DEL   CRestriction* pRes1;
//DEL   CRestriction* pRes2;
//DEL   CRestriction* pRes3;
//DEL 
//DEL 
//DEL   targetFunction.setCoefficient(1, 12);
//DEL   targetFunction.setCoefficient(2, 15);
//DEL   targetFunction.setCoefficient(3, 1);
//DEL   targetFunction.setCoefficient(4, -200);
//DEL   targetFunction.setCoefficient(5, 320);
//DEL   targetFunction.setMax();
//DEL 
//DEL   lpModel.setTargetFunction(&targetFunction);
//DEL 
//DEL   pRes1 = new CRestriction(5);
//DEL   pRes2 = new CRestriction(5);
//DEL   pRes3 = new CRestriction(5);
//DEL 
//DEL   pRes1->setCoefficient(1, 15);
//DEL   pRes1->setCoefficient(2, 23);
//DEL   pRes1->setCoefficient(3, -120);
//DEL   pRes1->setCoefficient(4, 333);
//DEL   pRes1->setCoefficient(5, 250);
//DEL   pRes1->setRestriction("=");
//DEL   pRes1->setSolution(630);
//DEL 
//DEL   pRes2->setCoefficient(1, 30);
//DEL   pRes2->setCoefficient(2, -200);
//DEL   pRes2->setCoefficient(3, 500);
//DEL   pRes2->setCoefficient(4, 21);
//DEL   pRes2->setCoefficient(5, 8);
//DEL   pRes2->setRestriction("=");
//DEL   pRes2->setSolution(220);
//DEL 
//DEL   pRes3->setCoefficient(1, 2);
//DEL   pRes3->setCoefficient(2, 12);
//DEL   pRes3->setCoefficient(3, -12);
//DEL   pRes3->setCoefficient(4, 100);
//DEL   pRes3->setCoefficient(5, 75);
//DEL   pRes3->setRestriction("<=");
//DEL   pRes3->setSolution(60);
//DEL 
//DEL   lpModel.addRestriction(pRes1);
//DEL   lpModel.addRestriction(pRes2);
//DEL   lpModel.addRestriction(pRes3);
//DEL 
//DEL   inputFile.setFilename("D:\\Studium\\WI8\\AnwBetrSys\\Transportoptimierung\\LPSolve\\lp_solve.in");
//DEL   inputFile.generate();
//DEL   ////////////////////////////////////////////////////////////////////////
//DEL 
//DEL   ////////////////////////////////////////////////////////////////////////
//DEL   // LPGeneration Test
//DEL   CTransopModel   lpModel2;  
//DEL   CSerNode* pLieferNode1 = new CSerNode();
//DEL   CSerNode* pLieferNode2 = new CSerNode();
//DEL 
//DEL   CSerNode* pEmpfaengerNode1 = new CSerNode();
//DEL   CSerNode* pEmpfaengerNode2 = new CSerNode();
//DEL   CSerNode* pEmpfaengerNode3 = new CSerNode();
//DEL 
//DEL   CStretch* pStretch11 = new CStretch();
//DEL   CStretch* pStretch12 = new CStretch();
//DEL   CStretch* pStretch13 = new CStretch();
//DEL   CStretch* pStretch21 = new CStretch();
//DEL   CStretch* pStretch22 = new CStretch();
//DEL   CStretch* pStretch23 = new CStretch();
//DEL 
//DEL   CTransporter* pTransporter1 = new CTransporter();
//DEL   CTransporter* pTransporter2 = new CTransporter();
//DEL 
//DEL   pTransporter1->setCapacity(300);
//DEL   pTransporter1->setCostsPerKm(0.25);
//DEL 
//DEL   pTransporter2->setCapacity(1000);
//DEL   pTransporter2->setCostsPerKm(0.35);
//DEL 
//DEL   pLieferNode1->setValue(630);
//DEL   pLieferNode2->setValue(370);
//DEL 
//DEL   pEmpfaengerNode1->setValue(270);
//DEL   pEmpfaengerNode2->setValue(450);
//DEL   pEmpfaengerNode3->setValue(280);
//DEL 
//DEL   pStretch11->setSourceNode(pLieferNode1);
//DEL   pStretch11->setDestinationNode(pEmpfaengerNode1);
//DEL   pStretch11->setDistance(200);
//DEL 
//DEL   pStretch12->setSourceNode(pLieferNode1);
//DEL   pStretch12->setDestinationNode(pEmpfaengerNode2);
//DEL   pStretch12->setDistance(150);
//DEL 
//DEL   pStretch13->setSourceNode(pLieferNode1);
//DEL   pStretch13->setDestinationNode(pEmpfaengerNode3);
//DEL   pStretch13->setDistance(500);
//DEL 
//DEL   pStretch21->setSourceNode(pLieferNode2);
//DEL   pStretch21->setDestinationNode(pEmpfaengerNode1);
//DEL   pStretch21->setDistance(300);
//DEL 
//DEL   pStretch22->setSourceNode(pLieferNode2);
//DEL   pStretch22->setDestinationNode(pEmpfaengerNode2);
//DEL   pStretch22->setDistance(450);
//DEL 
//DEL   pStretch23->setSourceNode(pLieferNode2);
//DEL   pStretch23->setDestinationNode(pEmpfaengerNode3);
//DEL   pStretch23->setDistance(400);
//DEL 
//DEL   lpModel2.addTransporter(pTransporter1);
//DEL   lpModel2.addTransporter(pTransporter2);
//DEL 
//DEL   lpModel2.addStretch(pStretch11);
//DEL   lpModel2.addStretch(pStretch12);
//DEL   lpModel2.addStretch(pStretch13);
//DEL   lpModel2.addStretch(pStretch21);
//DEL   lpModel2.addStretch(pStretch22);
//DEL   lpModel2.addStretch(pStretch23);
//DEL 
//DEL   lpModel2.generateLPModel();
//DEL 
//DEL   ////////////////////////////////////////////////////////////////////////
//DEL }

void CTransopView::OnPropConnection() 
{
  CTransopDoc* pDoc;

  pDoc = GetDocument();

  ASSERT(!(pDoc == NULL));

  CStretchesDlg	dlg(&pDoc->m_StretchList,
    &pDoc->m_ProducerList,
    &pDoc->m_ReceiverList);

  if (dlg.DoModal() == IDOK)
  {
    GetDocument()->UpdateAllViews(NULL);
  }
}

void CTransopView::OnPropProducer() 
{
  CProducerDlg dlg(&(GetDocument()->m_ProducerList),
    &(GetDocument()->m_StretchList));

  if (dlg.DoModal() == IDOK)
  {
    GetDocument()->UpdateAllViews(NULL);
  }
}

void CTransopView::OnPropReceiver() 
{
  CReceiverDlg dlg(&(GetDocument()->m_ReceiverList), 
    &(GetDocument()->m_StretchList));

  if (dlg.DoModal() == IDOK)
  {
    GetDocument()->UpdateAllViews(NULL);
  }
}

void CTransopView::OnPropTransporter() 
{
  CTransporterDlg dlg(&(GetDocument()->m_TransporterList));

  if (dlg.DoModal() == IDOK)
  {
    GetDocument()->UpdateAllViews(NULL);
  }
}

void CTransopView::OnDraw(CDC* pDC) 
{
}

int CTransopView::OnCreate(LPCREATESTRUCT lpCreateStruct) 
{
	if (CFormView::OnCreate(lpCreateStruct) == -1)
		return -1;
	
  m_ProducerListCtrl.Create(WS_VISIBLE | WS_CHILD | LVS_REPORT | WS_BORDER, CRect(0,0,0,0), this, ID_PRODLIST);
  m_ProducerListCtrl.SetHighlightType(HIGHLIGHT_ROW);
  m_ProducerListCtrl.InsertColumn(0, "Name", LVCFMT_LEFT);
  m_ProducerListCtrl.InsertColumn(1, "Produktion", LVCFMT_LEFT);
	
  m_ReceiverListCtrl.Create(WS_VISIBLE | WS_CHILD | LVS_REPORT | WS_BORDER, CRect(0,0,0,0), this, ID_RECLIST);
  m_ReceiverListCtrl.SetHighlightType(HIGHLIGHT_ROW);
  m_ReceiverListCtrl.InsertColumn(0, "Name", LVCFMT_LEFT);
  m_ReceiverListCtrl.InsertColumn(1, "Bedarf", LVCFMT_LEFT);

  m_StretchListCtrl.Create(WS_VISIBLE | WS_CHILD | LVS_REPORT | WS_BORDER, CRect(0,0,0,0), this, ID_STRETCHLIST);
  m_StretchListCtrl.SetHighlightType(HIGHLIGHT_ROW);
  m_StretchListCtrl.InsertColumn(0, "Von", LVCFMT_LEFT);
  m_StretchListCtrl.InsertColumn(1, "Bis", LVCFMT_LEFT);
  m_StretchListCtrl.InsertColumn(2, "km", LVCFMT_LEFT);

  m_SolutionCtrl.Create(WS_VISIBLE | WS_CHILD | LVS_REPORT | WS_BORDER, CRect(0,0,0,0), this, ID_SOLUTIONLIST);
  m_SolutionCtrl.SetHighlightType(HIGHLIGHT_ROW);
  m_SolutionCtrl.InsertColumn(0, "Transporter", LVCFMT_LEFT);
  m_SolutionCtrl.InsertColumn(1, "Ladung", LVCFMT_LEFT);
  m_SolutionCtrl.InsertColumn(2, "Von", LVCFMT_LEFT);
  m_SolutionCtrl.InsertColumn(3, "Bis", LVCFMT_LEFT);
  m_SolutionCtrl.InsertColumn(4, "Kosten", LVCFMT_LEFT);

  return 0;
}

void CTransopView::OnShowWindow(BOOL bShow, UINT nStatus) 
{
  CFormView::OnShowWindow(bShow, nStatus);
}

void CTransopView::OnSize(UINT nType, int cx, int cy) 
{
	CFormView::OnSize(nType, cx, cy);
}

void CTransopView::OnUpdate(CView* pSender, LPARAM lHint, CObject* pHint) 
{
  POSITION     pos;
  CSerNode*       pNode;
  CStretch*    pStretch;
  CTransopDoc* pDoc;
  CTransopStretch* pTransopStretch;
  CTransporter* pTransporter;
  int          nCounter;
  CString      csValue;

  pDoc = GetDocument();

  pos = pDoc->m_ProducerList.GetHeadPosition();
  
  m_ProducerListCtrl.DeleteAllItems();
  nCounter = 0;
  while (pos)
  {
    pNode = NULL;
    
    pNode = pDoc->m_ProducerList.GetNext(pos);
    
    if (pNode != NULL)
    {
      csValue.Format("%.0f", pNode->getValue());
      m_ProducerListCtrl.InsertItem(nCounter, pNode->getDescription());
      m_ProducerListCtrl.SetItemText(nCounter, 1, csValue); 
      m_ProducerListCtrl.SetItemData(nCounter, (DWORD)pNode);
    }
    nCounter++;
  }

  pos = pDoc->m_ReceiverList.GetHeadPosition();
  
  m_ReceiverListCtrl.DeleteAllItems();
  nCounter = 0;
  while (pos)
  {
    pNode = NULL;
    
    pNode = pDoc->m_ReceiverList.GetNext(pos);
    
    if (pNode != NULL)
    {
      csValue.Format("%.0f", pNode->getValue());
      m_ReceiverListCtrl.InsertItem(nCounter, pNode->getDescription());
      m_ReceiverListCtrl.SetItemText(nCounter, 1, csValue); 
      m_ReceiverListCtrl.SetItemData(nCounter, (DWORD)pNode);
    }
    nCounter++;
  }

  pos = pDoc->m_StretchList.GetHeadPosition();
  
  m_StretchListCtrl.DeleteAllItems();
  nCounter = 0;
  while (pos)
  {
    pStretch = NULL;
    
    pStretch = pDoc->m_StretchList.GetNext(pos);
    
    if (pStretch != NULL)
    {
      csValue.Format("%.0f", pStretch->getDistance());
      m_StretchListCtrl.InsertItem(nCounter, pStretch->getSourceNode()->getDescription());
      m_StretchListCtrl.SetItemText(nCounter, 1, pStretch->getDestinationNode()->getDescription()); 
      m_StretchListCtrl.SetItemText(nCounter, 2, csValue); 
      m_StretchListCtrl.SetItemData(nCounter, (DWORD)pStretch);
    }
    nCounter++;
  }

  m_csTransName1 = pDoc->m_TransporterList.GetHead()->getDescription();
  m_csTransName2 = pDoc->m_TransporterList.GetTail()->getDescription();

  m_csCap1.Format("%d", pDoc->m_TransporterList.GetHead()->getCapacity());
  m_csCap2.Format("%d", pDoc->m_TransporterList.GetTail()->getCapacity());

  m_csCosts1.Format("%.2f", pDoc->m_TransporterList.GetHead()->getCostsPerKm());
  m_csCosts2.Format("%.2f", pDoc->m_TransporterList.GetTail()->getCostsPerKm());


  // file solution ListCtrl
  if (pDoc->m_pSolutionList != NULL)
  {
    pos= NULL;
    pos = pDoc->m_pSolutionList->GetHeadPosition();
  
    m_SolutionCtrl.DeleteAllItems();
    nCounter = 0;
    CString nValue;
    while (pos)
    {
      pTransopStretch = NULL;
    
      pTransopStretch = pDoc->m_pSolutionList->GetNext(pos);
    
      if (pTransopStretch != NULL)
      {
        pTransporter = pTransopStretch->getFirstTransporter();

        while (pTransporter)
        {
          csValue.Format("%.2f", pTransopStretch->getDistance()*pTransporter->getCostsPerKm());
          nValue.Format("%d", pTransporter->getLoading());
      
          m_SolutionCtrl.InsertItem(nCounter, pTransporter->getDescription());
          m_SolutionCtrl.SetItemText(nCounter, 1, nValue); 
          m_SolutionCtrl.SetItemText(nCounter, 2, pTransopStretch->getSourceNode()->getDescription()); 
          m_SolutionCtrl.SetItemText(nCounter, 3, pTransopStretch->getDestinationNode()->getDescription()); 
          m_SolutionCtrl.SetItemText(nCounter, 4, csValue); 
          m_SolutionCtrl.SetItemData(nCounter, (DWORD)pTransopStretch);

          pTransporter = pTransopStretch->getNextTransporter();
          nCounter++;

        }
      }
    }
    m_csGK.Format("%.2f", pDoc->m_dValueOfObjective);
  }

  UpdateData(FALSE);
}

void CTransopView::OnStnClickedProdpos()
{
	// TODO: Add your control notification handler code here
}
