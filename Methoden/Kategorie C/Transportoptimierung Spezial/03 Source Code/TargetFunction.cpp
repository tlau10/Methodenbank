// TargetFunction.cpp: Implementierung der Klasse CTargetFunction.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include "stdafx.h"
#include "TargetFunction.h"

#ifdef _DEBUG
#undef THIS_FILE
static char THIS_FILE[]=__FILE__;
#define new DEBUG_NEW
#endif

//////////////////////////////////////////////////////////////////////
// Konstruktion/Destruktion
//////////////////////////////////////////////////////////////////////

CTargetFunction::CTargetFunction(int nCoefficientCount)
  :CLinearFunction(nCoefficientCount)
{

}

CTargetFunction::~CTargetFunction()
{

}

void CTargetFunction::setMin()
{
  m_nType = TFT_MIN;
}

void CTargetFunction::setMax()
{
  m_nType = TFT_MAX;
}

bool CTargetFunction::isMin()
{
  if (m_nType == TFT_MIN)
  {
    return true;
  }
  return false;
}

bool CTargetFunction::isMax()
{
  if (m_nType == TFT_MAX)
  {
    return true;
  }
  return false;
}
