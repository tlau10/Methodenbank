// TransopStretch.cpp: Implementierung der Klasse CTransopStretch.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include "stdafx.h"
#include "TransopStretch.h"

#ifdef _DEBUG
#undef THIS_FILE
static char THIS_FILE[]=__FILE__;
#define new DEBUG_NEW
#endif

//////////////////////////////////////////////////////////////////////
// Konstruktion/Destruktion
//////////////////////////////////////////////////////////////////////

CTransopStretch::CTransopStretch()
{
  m_CurPos = NULL;
}

CTransopStretch::~CTransopStretch()
{
  POSITION pos;
  CTransporter* pTransporter;
  
  pos = m_TransporterList.GetHeadPosition();
  
  while (pos)
  {
    pTransporter = NULL;
    
    pTransporter = m_TransporterList.GetNext(pos);
    
    if (pTransporter != NULL)
    {
      delete pTransporter;
    }
  }
}

void CTransopStretch::addTransporter(int nCapacity, CString csDescription, int nLoading, double dCosts)
{
  CTransporter* pTransporter = new CTransporter();

  pTransporter->setCapacity(nCapacity);
  pTransporter->setDescription(csDescription);
  pTransporter->setLoading(nLoading);
  pTransporter->setCostsPerKm(dCosts);
  
  m_TransporterList.AddTail(pTransporter);
}


CTransporter* CTransopStretch::getFirstTransporter()
{
  m_CurPos = m_TransporterList.GetHeadPosition();

  if (m_CurPos == NULL)
  {
    return NULL;
  }
  return m_TransporterList.GetNext(m_CurPos);
}

CTransporter* CTransopStretch::getNextTransporter()
{
  if (m_CurPos != NULL)
  {
    return m_TransporterList.GetNext(m_CurPos);
  }
  else
  {
    return NULL;
  }
}
