// Stretch.cpp: Implementierung der Klasse CStretch.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include "Stretch.h"

#ifdef _DEBUG
#undef THIS_FILE
static char THIS_FILE[]=__FILE__;
#define new DEBUG_NEW
#endif

//////////////////////////////////////////////////////////////////////
// Konstruktion/Destruktion
//////////////////////////////////////////////////////////////////////

CStretch::CStretch()
{
	m_pSourceNode       = NULL;
	m_pDestinationNode  = NULL;
}

CStretch::~CStretch()
{

}

void CStretch::setSourceNode(CSerNode *pSourceNode)
{
  m_pSourceNode = pSourceNode;
}

CSerNode* CStretch::getSourceNode()
{
  return m_pSourceNode;
}

void CStretch::setDestinationNode(CSerNode *pDestinationNode)
{
  m_pDestinationNode = pDestinationNode; 
}

CSerNode* CStretch::getDestinationNode()
{
  return m_pDestinationNode;
}

void CStretch::setDistance(double dDistance)
{
  m_dDistance = dDistance;
}

double CStretch::getDistance()
{
  return m_dDistance;
}

void CStretch::setCostFactor(double dCosts)
{
  m_dCostFactor = dCosts;
}

double CStretch::getCostFactor()
{
  return m_dCostFactor;
}

void CStretch::Serialize( CArchive& ar )
{
  if (ar.IsStoring())
  {
    ar << m_dCostFactor << m_dDistance << m_pSourceNode << m_pDestinationNode;
  }
  else
  {
    ar >> m_dCostFactor >> m_dDistance >> m_pSourceNode >> m_pDestinationNode;
  }

}
