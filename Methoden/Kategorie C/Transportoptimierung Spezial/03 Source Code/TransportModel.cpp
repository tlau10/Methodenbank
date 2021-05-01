// TransportModel.cpp: Implementierung der Klasse CTransportModel.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include "stdafx.h"
#include "TransportModel.h"

#ifdef _DEBUG
#undef THIS_FILE
static char THIS_FILE[]=__FILE__;
#define new DEBUG_NEW
#endif

//////////////////////////////////////////////////////////////////////
// Konstruktion/Destruktion
//////////////////////////////////////////////////////////////////////

CTransportModel::CTransportModel()
{
  m_CurrentListPos = NULL;
}

CTransportModel::~CTransportModel()
{
  POSITION pos;
  CStretch* pStretch;
  
  pos = m_StretchList.GetHeadPosition();
  
  while (pos)
  {
    pStretch = NULL;
    
    pStretch = m_StretchList.GetNext(pos);
    
    if (pStretch != NULL)
    {
      delete pStretch->getSourceNode();
      delete pStretch->getDestinationNode();
      delete pStretch;
    }
  }
}

void CTransportModel::addStretch(CStretch *pStretch)
{
  CStretch* pNewStretch = new CStretch();
  CSerNode* pNode;

  pNewStretch->setCostFactor(pStretch->getCostFactor());
  pNewStretch->setDistance(pStretch->getDistance());

  pNode = new CSerNode();
  pNode->setDescription(pStretch->getSourceNode()->getDescription());
  pNode->setValue(pStretch->getSourceNode()->getValue());
  pNewStretch->setSourceNode(pNode);

  pNode = new CSerNode();
  pNode->setDescription(pStretch->getDestinationNode()->getDescription());
  pNode->setValue(pStretch->getDestinationNode()->getValue());
  pNewStretch->setDestinationNode(pNode);

  m_StretchList.AddTail(pNewStretch);
}

CStretch* CTransportModel::getFirstStretch()
{
  POSITION pos;
  
  pos = m_StretchList.GetHeadPosition();
  
  m_CurrentListPos = pos;

  if (m_CurrentListPos == NULL)
  {
    return NULL;
  }
  
  return m_StretchList.GetNext(m_CurrentListPos);
}

CStretch* CTransportModel::getNextStretch()
{
  if (m_CurrentListPos == NULL)
  {
    return NULL;
  }
  return m_StretchList.GetNext(m_CurrentListPos);
}

int CTransportModel::getStretchCount()
{
  return m_StretchList.GetCount();
}

CStretch* CTransportModel::getStretchAt(int nIndex)
{
  POSITION pos;

  pos = m_StretchList.FindIndex(nIndex);

  if (pos == NULL)
  {
    return NULL;
  }

  return m_StretchList.GetAt(pos);
}

//DEL void CTransportModel::clear()
//DEL {
//DEL   m_CurrentListPos = NULL;
//DEL   m_StretchList.RemoveAll();
//DEL   CLPModel::clear();
//DEL }
