// LPModel.cpp: Implementierung der Klasse CLPModel.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include "stdafx.h"
#include "LPModel.h"

#ifdef _DEBUG
#undef THIS_FILE
static char THIS_FILE[]=__FILE__;
#define new DEBUG_NEW
#endif

//////////////////////////////////////////////////////////////////////
// Konstruktion/Destruktion
//////////////////////////////////////////////////////////////////////

CLPModel::CLPModel()
{
  m_pTargetFunction = NULL;
  m_CurrentListPos  = NULL;
}

CLPModel::~CLPModel()
{
  POSITION pos;
  CRestriction* pRes;

  if (m_pTargetFunction != NULL)
  {
    delete m_pTargetFunction;
  }

  pos = m_RestrictionList.GetHeadPosition();

  while (pos)
  {
    pRes = NULL;

    pRes = m_RestrictionList.GetNext(pos);

    if (pRes != NULL)
    {
      delete pRes;
    }
  }
}

void CLPModel::addRestriction(CRestriction *lpRestriction)
{

  CRestriction* pRestriction = new CRestriction(lpRestriction->getCoefficientCount());
  int nCount;
  double dValue;

  pRestriction->setRestriction(lpRestriction->getRestriction());
  pRestriction->setSolution(lpRestriction->getSolution());

  nCount = lpRestriction->getCoefficientCount();

  for (int i = 1; i <= nCount; i++)
  {
    lpRestriction->getCoefficient(i, &dValue);
    pRestriction->setCoefficient(i, dValue);
    
  }
  m_RestrictionList.AddTail(pRestriction);
}

CRestriction* CLPModel::getFirstRestriction()
{
  POSITION pos;

  pos = m_RestrictionList.GetHeadPosition();

  m_CurrentListPos = pos;

  if (m_CurrentListPos == NULL)
  {
    return NULL;
  }

  return m_RestrictionList.GetNext(m_CurrentListPos);
}

CRestriction* CLPModel::getNextRestriction()
{
  if (m_CurrentListPos == NULL)
  {
    return NULL;
  }
  return m_RestrictionList.GetNext(m_CurrentListPos);
}

void CLPModel::setTargetFunction(CTargetFunction *lpTargetFunction)
{ 
  m_pTargetFunction = new CTargetFunction(lpTargetFunction->getCoefficientCount());
  int nCount;
  double dValue;

  nCount = lpTargetFunction->getCoefficientCount();

  for (int i = 1; i <= nCount; i++)
  {
    lpTargetFunction->getCoefficient(i, &dValue);
    m_pTargetFunction->setCoefficient(i, dValue);
  }
}

CTargetFunction* CLPModel::getTargetFunction()
{
  return m_pTargetFunction;
}

//void CLPModel::clear()
//DEL {
//DEL   POSITION pos;
//DEL   CRestriction* pRes;
//DEL 
//DEL   if (m_pTargetFunction != NULL)
//DEL   {
//DEL     delete m_pTargetFunction;
//DEL   }
//DEL 
//DEL   pos = m_RestrictionList.GetHeadPosition();
//DEL 
//DEL   while (pos)
//DEL   {
//DEL     pRes = NULL;
//DEL 
//DEL     pRes = m_RestrictionList.GetNext(pos);
//DEL 
//DEL     if (pRes != NULL)
//DEL     {
//DEL       delete pRes;
//DEL     }
//DEL   }
//DEL }
//DEL 