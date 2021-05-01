// TransopDoc.cpp : Implementierung der Klasse CTransopDoc
//

#include "stdafx.h"
#include "Transop.h"

#include "TransopDoc.h"

#include "Stretch.h"
#include "Transporter.h"
#include "TransopModel.h"
#include "TransopStretch.h"
#include "ProgressDlg.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CTransopDoc

IMPLEMENT_DYNCREATE(CTransopDoc, CDocument)

BEGIN_MESSAGE_MAP(CTransopDoc, CDocument)
	//{{AFX_MSG_MAP(CTransopDoc)
	ON_COMMAND(ID_CALCULATE, OnCalculate)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CTransopDoc Konstruktion/Destruktion

CTransopDoc::CTransopDoc()
{
  CTransporter* pTrans;

  pTrans = new CTransporter();
  m_TransporterList.AddHead(pTrans);

  pTrans = new CTransporter();
  m_TransporterList.AddTail(pTrans);

  m_pSolutionList = NULL;

  //m_pLPModel = new CTransopModel();

}

CTransopDoc::~CTransopDoc()
{
  POSITION pos;
  CSerNode*   pNode;
  CStretch*   pStretch;
  CTransporter* pTrans;

  // delete StretchList
  pos = m_StretchList.GetHeadPosition();

  while (pos)
  {
    pStretch = NULL;

    pStretch = m_StretchList.GetNext(pos);

    if (pStretch != NULL)
    {
      delete pStretch;
    }
  }

  // delete ProducerList
  pos = m_ProducerList.GetHeadPosition();

  while (pos)
  {
    pNode = NULL;

    pNode = m_ProducerList.GetNext(pos);

    if (pNode != NULL)
    {
      delete pNode;
    }
  }

  // delete ReceiverList
  pos = m_ReceiverList.GetHeadPosition();

  while (pos)
  {
    pNode = NULL;

    pNode = m_ReceiverList.GetNext(pos);

    if (pNode != NULL)
    {
      delete pNode;
    }
  }

  // delete TransporterList
  pos = m_TransporterList.GetHeadPosition();

  while (pos)
  {
    pTrans = NULL;

    pTrans = m_TransporterList.GetNext(pos);

    if (pTrans != NULL)
    {
      delete pTrans;
    }
  }

  if (m_pSolutionList != NULL)
  {
    CTransopStretch* pTransopStretch;

    pos = m_pSolutionList->GetHeadPosition();

    while (pos)
    {
      pTransopStretch = NULL;

      pTransopStretch = m_pSolutionList->GetNext(pos);

      if (pTransopStretch != NULL)
      {
        delete pTransopStretch;
      }
    }
    m_pSolutionList->RemoveAll();
    delete m_pSolutionList;
    m_pSolutionList = NULL;
  }
  //delete m_pLPModel;
}

BOOL CTransopDoc::OnNewDocument()
{
	if (!CDocument::OnNewDocument())
		return FALSE;

	// ZU ERLEDIGEN: Hier Code zur Reinitialisierung einfügen
	// (SDI-Dokumente verwenden dieses Dokument)

	return TRUE;
}



/////////////////////////////////////////////////////////////////////////////
// CTransopDoc Serialisierung

void CTransopDoc::Serialize(CArchive& ar)
{
	
	// 	m_ProducerList
	if (ar.IsStoring())
	{
		POSITION p = m_ProducerList.GetHeadPosition();
		int savedCount = 1;
		
		CSerNode** countNode = new CSerNode*();
		*countNode = new CSerNode();
		(*countNode)->setDescription("ProducerCount");
		(*countNode)->setValue((double)m_ProducerList.GetCount());
		
		m_ProducerList.SerializeElements(ar, countNode, savedCount);

		for(int i = 0; i < m_ProducerList.GetCount(); i++)
		{
			CSerNode** pElements = &m_ProducerList.GetNext(p);

			m_ProducerList.SerializeElements(ar, pElements, savedCount);
		}
	}
	else
	{
		int readedCount = 1;

		CSerNode** countNode = new CSerNode*();
		m_ProducerList.SerializeElements(ar, countNode, readedCount);

		readedCount = (int)(*countNode)->getValue();

		CSerNode** pElements2 = new CSerNode*();
		
		m_ProducerList.SerializeElements(ar, pElements2, readedCount);

		for(int i = 0; i < readedCount; i++)
			m_ProducerList.AddTail(pElements2[i]);
	}

	// m_ReceiverList
	if (ar.IsStoring())
	{
		POSITION p = m_ReceiverList.GetHeadPosition();
		int savedCount = 1;

		CSerNode** countNode = new CSerNode*();
		*countNode = new CSerNode();
		(*countNode)->setDescription("ReceiverCount");
		(*countNode)->setValue((double)m_ReceiverList.GetCount());

		m_ProducerList.SerializeElements(ar, countNode, savedCount);

		for(int i = 0; i < m_ReceiverList.GetCount(); i++)
		{
			CSerNode** pElements = &m_ReceiverList.GetNext(p);

			m_ReceiverList.SerializeElements(ar, pElements, savedCount);
		}
	}
	else
	{
		int readedCount = 1;

		CSerNode** countNode = new CSerNode*();
		m_ProducerList.SerializeElements(ar, countNode, readedCount);

		readedCount = (int)(*countNode)->getValue();

		CSerNode** pElements2 = new CSerNode*();
		
		m_ReceiverList.SerializeElements(ar, pElements2, readedCount);

		for(int i = 0; i < readedCount; i++)
			m_ReceiverList.AddTail(pElements2[i]);
	}

	// m_StretchList
	if (ar.IsStoring())
	{
		POSITION p = m_StretchList.GetHeadPosition();
		int savedCount = 1;

		CSerNode** countNode = new CSerNode*();
		*countNode = new CSerNode();
		(*countNode)->setDescription("StretchCount");
		(*countNode)->setValue((double)m_StretchList.GetCount());

		m_ProducerList.SerializeElements(ar, countNode, savedCount);

		for(int i = 0; i < m_StretchList.GetCount(); i++)
		{
			CStretch** pElements = &m_StretchList.GetNext(p);

			m_StretchList.SerializeElements(ar, pElements, savedCount);
		}
	}
	else
	{
		int readedCount = 1;

		CSerNode** countNode = new CSerNode*();
		m_ProducerList.SerializeElements(ar, countNode, readedCount);

		readedCount = (int)(*countNode)->getValue();

		CStretch** pElements2 = new CStretch*();
		
		m_StretchList.SerializeElements(ar, pElements2, readedCount);

		for(int i = 0; i < readedCount; i++)
			m_StretchList.AddTail(pElements2[i]);
	}

	// m_TransporterList
	if (ar.IsStoring())
	{
		POSITION p = m_TransporterList.GetHeadPosition();
		int savedCount = 1;

		for(int i = 0; i < m_TransporterList.GetCount(); i++)
		{
			CTransporter** pElements = &m_TransporterList.GetNext(p);

			m_TransporterList.SerializeElements(ar, pElements, savedCount);
		}
	}
	else
	{
		CTransporter** pElements2 = new CTransporter*();
		int readedCount = 2;
		m_TransporterList.RemoveAll();
		m_TransporterList.SerializeElements(ar, pElements2, readedCount);

		for(int i = 0; i < readedCount; i++)
			m_TransporterList.AddTail(pElements2[i]);
	}
}

/////////////////////////////////////////////////////////////////////////////
// CTransopDoc Diagnose

#ifdef _DEBUG
void CTransopDoc::AssertValid() const
{
	CDocument::AssertValid();
}

void CTransopDoc::Dump(CDumpContext& dc) const
{
	CDocument::Dump(dc);
}
#endif //_DEBUG

/////////////////////////////////////////////////////////////////////////////
// CTransopDoc Befehle

void CTransopDoc::OnCalculate() 
{
	try
	{
		  POSITION        pos;
		  CStretch*       pStretch;
		  CTransporter*   pTransporter;
		  CTransopModel   LPModel;  
		  CString         csWorkingDir;
		  CString         csSolverPath;
		  CString         csBatchFileText;
		  CFileException  e;
		  CFile           fBatchFile;

		  CProgressDlg progressWindow;

		  progressWindow.Create(CProgressDlg::IDD);

		  progressWindow.ShowWindow(SW_SHOWNORMAL);

		  csWorkingDir = ((CTransopApp*)AfxGetApp())->m_csWorkingDir + "\\";
		  csSolverPath = ((CTransopApp*)AfxGetApp())->m_csSolverPath + "\\";

		  // create batch file for solver

		  if (!fBatchFile.Open(csWorkingDir + "Solver.bat", CFile::modeReadWrite | CFile::modeCreate, &e))
		  {
			ASSERT(false);
		  }

		  csBatchFileText = "@echo off\r\nREM >>>>> LP-SOLVER\r\n";
		  csBatchFileText += csSolverPath + "lp_solve.exe";
		  csBatchFileText += " -p <" + csWorkingDir + "lp_solve.in >" + csWorkingDir + "lp_solve.out\r\n";

		  fBatchFile.Write(csBatchFileText.GetBuffer(csBatchFileText.GetLength()), csBatchFileText.GetLength());
		  csBatchFileText.ReleaseBuffer(csBatchFileText.GetLength());
		  fBatchFile.Close();

		  LPModel.setProducerList(&m_ProducerList);
		  LPModel.setReceiverList(&m_ReceiverList);
		  LPModel.setWorkingDir(csWorkingDir);

		  pos = m_StretchList.GetHeadPosition();

		  while (pos)
		  {
			pStretch = NULL;

			pStretch = m_StretchList.GetNext(pos);

			if (pStretch != NULL)
			{
			  LPModel.addStretch(pStretch);
			}
		  }

		  pos = m_TransporterList.GetHeadPosition();

		  while (pos)
		  {
			pTransporter = NULL;

			pTransporter = m_TransporterList.GetNext(pos);

			if (pTransporter != NULL)
			{
			  LPModel.addTransporter(pTransporter);
			}
		  }


		  m_pSolutionList = LPModel.createSolution();

		  m_dValueOfObjective = LPModel.getValueOfObjective();

		  UpdateAllViews(NULL);

		  progressWindow.DestroyWindow();

		  // delete previous solution
		  if (m_pSolutionList != NULL)
		  {
			CTransopStretch* pTransopStretch;

			pos = m_pSolutionList->GetHeadPosition();

			while (pos)
			{
			  pTransopStretch = NULL;

			  pTransopStretch = m_pSolutionList->GetNext(pos);

			  if (pTransopStretch != NULL)
			  {
				delete pTransopStretch;
			  }
			}
			m_pSolutionList->RemoveAll();
			delete m_pSolutionList;
			m_pSolutionList = NULL;
		  }
	}
	catch(char * str)
	{
		MessageBoxA(NULL, str, "TransOP", MB_OK);
	}
}
