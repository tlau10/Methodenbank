// TransopModel.cpp: Implementierung der Klasse CTransopModel.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include "stdafx.h"
#include "TransopModel.h"
#include "TransopStretch.h"
#include <io.h>

#ifdef _DEBUG
#undef THIS_FILE
static char THIS_FILE[]=__FILE__;
#define new DEBUG_NEW
#endif

//////////////////////////////////////////////////////////////////////
// Konstruktion/Destruktion
//////////////////////////////////////////////////////////////////////

CTransopModel::CTransopModel()
{
  m_lpInputFile = new CLPInputFile(this);
  m_csWorkingDir = ".";
  m_TransporterList.RemoveAll();
  m_ReceiverList.RemoveAll();
  m_ProducerList.RemoveAll();
  m_CurrentListPos = NULL;
}

CTransopModel::~CTransopModel()
{
  delete m_lpInputFile;

  POSITION pos;
  CTransporter* pTrans;

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

  CSerNode* pNode;

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
}

CList<CTransopStretch*, CTransopStretch*>* CTransopModel::createSolution()
{

  CList<CTransopStretch*, CTransopStretch*>* solutionList = new CList<CTransopStretch*, CTransopStretch*>;
  generateLPModel();

  CString csOutputFilePath;
  CString csBatchFilePath;

  csOutputFilePath = m_csWorkingDir + "lp_solve.out";

  if (_access(csOutputFilePath.GetBuffer(csOutputFilePath.GetLength()), 0) == 0)
  {
    csOutputFilePath.ReleaseBuffer(csOutputFilePath.GetLength());
    DeleteFile(csOutputFilePath);
  }

  csBatchFilePath = m_csWorkingDir + "Solver.bat";

  ShellExecute(NULL, "open", csBatchFilePath, NULL, NULL, SW_HIDE);
  MSG msg;
  
  while(::PeekMessage (&msg, NULL, 0, 0, PM_REMOVE))
  {
    ::TranslateMessage(&msg);
    ::DispatchMessage(&msg);
  }
  
  Sleep(5000);

  //while (_access(csOutputFilePath.GetBuffer(csOutputFilePath.GetLength()), 6) != 0);

  m_lpOutputFile.init(csOutputFilePath);

  CTransporter* pTransporter;
  CStretch*     pStretch;
  CTransopStretch* pTransopStretch;
  int           nTransporterCounter;
  int           nStretchCounter;
  double        dValue;


  pTransporter = NULL;
  
  pTransporter = getFirstTransporter();
  
  nTransporterCounter = 0;
  while (pTransporter)
  {
    pStretch = getFirstStretch();

    nStretchCounter = 0;
    while (pStretch)
    {
      nStretchCounter++;

      m_lpOutputFile.getSolutionValue((nTransporterCounter*m_nNumberOfStretches) + m_nNumberOfStretches + nStretchCounter,
        &dValue);

      if (dValue != 0)
      {
        pTransopStretch = new CTransopStretch();
        pTransopStretch->setSourceNode(pStretch->getSourceNode());
        pTransopStretch->setDestinationNode(pStretch->getDestinationNode());
        pTransopStretch->setDistance(pStretch->getDistance());

        int nNumberOfTransporter;

        nNumberOfTransporter = dValue;

        m_lpOutputFile.getSolutionValue(nStretchCounter, &dValue);
        for (int i = 0; i < nNumberOfTransporter; i++)
        {
           if (dValue > pTransporter->getCapacity())
           {
             pTransopStretch->addTransporter(pTransporter->getCapacity(), 
               pTransporter->getDescription(), 
               pTransporter->getCapacity(), 
               pTransporter->getCostsPerKm()); 
  
             dValue -= pTransporter->getCapacity();
           }
           else
           {
            pTransopStretch->addTransporter(pTransporter->getCapacity(), 
              pTransporter->getDescription(), 
              dValue, 
              pTransporter->getCostsPerKm()); 
           }
        }
        solutionList->AddTail(pTransopStretch);
      }

      pStretch = getNextStretch();
    }
    pTransporter = getNextTransporter();
    nTransporterCounter++;
  }
  m_dValueOfObjective = m_lpOutputFile.getValueOfObjective();
  
  return solutionList;
}

bool CTransopModel::generateLPModel()
{
  CTargetFunction* pTargetFunction;
  CRestriction*    pRestriction;
  CTransporter*    pTransporter;
  POSITION         pos;
  CSerNode*           pNode;
  CStretch*        pStretch;
  int              nCounter;

  //fillLists();

  m_nNumberOfTransporters = getTransporterCount(); 
  m_nNumberOfStretches    = getStretchCount(); 

  // Anzahl Variablen ermitteln
  m_nNumberOfVariables = (m_nNumberOfTransporters*m_nNumberOfStretches) + m_nNumberOfStretches;

  // Anzahl Knoten ermitteln
  m_nNumberOfNodes = m_ReceiverList.GetCount() + m_ProducerList.GetCount();

  // Anzahl der Restriktionen ermittln 
  m_nNumberOfRestrictions = m_nNumberOfStretches + m_nNumberOfNodes;

  pTargetFunction = new CTargetFunction(m_nNumberOfVariables);


  int i = 0;
  // generieren der Zielfunktion
  for (i = 1; i <= m_nNumberOfStretches; i++)
  {
    pTargetFunction->setCoefficient(i, 0);
  }

  nCounter = i;

  pTransporter = getFirstTransporter();

  while (pTransporter != NULL)
  {
    for (i = 0; i < m_nNumberOfStretches; i++)
    {
      pTargetFunction->setCoefficient(nCounter, getStretchAt(i)->getDistance()*pTransporter->getCostsPerKm());
      nCounter++;
    }
    pTransporter = getNextTransporter();
  }
  pTargetFunction->setMin();

  setTargetFunction(pTargetFunction);
  delete pTargetFunction;

  // generieren der Restriktionen

  //zuerst Produzenten abhandeln
  pos = m_ProducerList.GetHeadPosition();

  while (pos)
  {
    pRestriction = new CRestriction(m_nNumberOfVariables);

    pNode = NULL;
    pNode = m_ProducerList.GetNext(pos);

    if (pNode != NULL)
    {
      // suchen der Indexe in den Strecken
      pStretch = getFirstStretch();

      nCounter = 1;
      while (pStretch)
      {
        if (pStretch->getSourceNode()->getDescription() == pNode->getDescription())
        {
          pRestriction->setCoefficient(nCounter, 1);
        }
        nCounter++;
        pStretch = getNextStretch();
      }
      pRestriction->setRestriction("=");
      pRestriction->setSolution(pNode->getValue());
      pStretch = getNextStretch();
    }
    addRestriction(pRestriction);
    delete pRestriction;
  }

  //Empfänger abhandeln
  pos = m_ReceiverList.GetHeadPosition();

  while (pos)
  {
    pRestriction = new CRestriction(m_nNumberOfVariables);

    pNode = NULL;
    pNode = m_ReceiverList.GetNext(pos);

    if (pNode != NULL)
    {
      // suchen der Indexe in den Strecken
      pStretch = getFirstStretch();

      nCounter = 1;
      while (pStretch)
      {
        if (pStretch->getDestinationNode()->getDescription() == pNode->getDescription())
        {
          pRestriction->setCoefficient(nCounter, 1);
        }
        nCounter++;
        pStretch = getNextStretch();
      }
      pRestriction->setRestriction("=");
      pRestriction->setSolution(pNode->getValue());
    }
    addRestriction(pRestriction);
    delete pRestriction;
  }

  // Restriktionen für die Strecken einfügen
  pStretch = getFirstStretch();

  nCounter = 1;
  int nTransporteCount;

  while (pStretch)
  {
    pRestriction = new CRestriction(m_nNumberOfVariables);
    pRestriction->setCoefficient(nCounter, 1);

    pTransporter = getFirstTransporter();

    nTransporteCount = 1;
    while(pTransporter)
    {
      pRestriction->setCoefficient(nCounter + (nTransporteCount*m_nNumberOfStretches), pTransporter->getCapacity()*(-1));
      pTransporter = getNextTransporter();
      nTransporteCount++;
    }
    pRestriction->setRestriction("<=");
    pRestriction->setSolution(0);
    addRestriction(pRestriction);
    delete pRestriction;
    pStretch = getNextStretch();
    nCounter++;
  }

  generateLPInputFile();

  return true;
}

void CTransopModel::addTransporter(CTransporter *pTransporter)
{
  CTransporter* pNewTransporter = new CTransporter();

  pNewTransporter->setCapacity(pTransporter->getCapacity());
  pNewTransporter->setDescription(pTransporter->getDescription());
  pNewTransporter->setLoading(pTransporter->getLoading());
  pNewTransporter->setCostsPerKm(pTransporter->getCostsPerKm());
  
  m_TransporterList.AddTail(pNewTransporter);
}

CTransporter* CTransopModel::getFirstTransporter()
{
  POSITION pos;
  
  pos = m_TransporterList.GetHeadPosition();
  
  m_CurrentListPos = pos;

  if (m_CurrentListPos == NULL)
  {
    return NULL;
  }
  
  return m_TransporterList.GetNext(m_CurrentListPos);
}

CTransporter* CTransopModel::getNextTransporter()
{
  if (m_CurrentListPos == NULL)
  {
    return NULL;
  }
  return m_TransporterList.GetNext(m_CurrentListPos);
}

int CTransopModel::getTransporterCount()
{
  return m_TransporterList.GetCount();
}

void CTransopModel::fillLists()
{
  CStretch* pStretch;

  pStretch = NULL;

  pStretch = getFirstStretch();

  while (pStretch)
  {
    if (m_ProducerList.Find(pStretch->getSourceNode()) == NULL)
    {
      m_ProducerList.AddHead(pStretch->getSourceNode());
    }
    
    if (m_ReceiverList.Find(pStretch->getDestinationNode()) == NULL)
    {
      m_ReceiverList.AddHead(pStretch->getDestinationNode());
    }
    pStretch = getNextStretch();
  }
}

void CTransopModel::generateLPInputFile()
{
  CString csFilename;

  csFilename = m_csWorkingDir + "lp_solve.in";

  if (_access(csFilename.GetBuffer(csFilename.GetLength()), 0) == 0)
  {
    csFilename.ReleaseBuffer(csFilename.GetLength());
    DeleteFile(csFilename);
  }
  m_lpInputFile->setFilename(csFilename);
  m_lpInputFile->generate();
}


void CTransopModel::setWorkingDir(CString csPath)
{
  m_csWorkingDir = csPath;
}

//DEL void CTransopModel::clear()
//DEL {
//DEL   m_TransporterList.RemoveAll();
//DEL   m_ReceiverList.RemoveAll();
//DEL   m_ProducerList.RemoveAll();
//DEL   m_CurrentListPos = NULL;
//DEL   CTransportModel::clear();
//DEL }

bool CTransopModel::solve()
{
  return true;
}

void CTransopModel::setProducerList(CList<CSerNode*, CSerNode*>* lpProducerList)
{
  POSITION pos;
  CSerNode*   pNewNode;
  CSerNode*   pNode;

  pos = lpProducerList->GetHeadPosition();

  while (pos)
  {
    pNode = NULL;

    pNode = lpProducerList->GetNext(pos);

    if (pNode != NULL)
    {
      pNewNode = new CSerNode();
      pNewNode->setDescription(pNode->getDescription());
      pNewNode->setValue(pNode->getValue());

      m_ProducerList.AddTail(pNewNode);
    }
  }
}

void CTransopModel::setReceiverList(CList<CSerNode*, CSerNode*>* lpReceiverList)
{
  POSITION pos;
  CSerNode*   pNewNode;
  CSerNode*   pNode;

  pos = lpReceiverList->GetHeadPosition();

  while (pos)
  {
    pNode = NULL;

    pNode = lpReceiverList->GetNext(pos);

    if (pNode != NULL)
    {
      pNewNode = new CSerNode();
      pNewNode->setDescription(pNode->getDescription());
      pNewNode->setValue(pNode->getValue());

      m_ReceiverList.AddTail(pNewNode);
    }
  }

}

double CTransopModel::getValueOfObjective()
{
  return m_dValueOfObjective;
}
