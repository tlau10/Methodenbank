// LinearFunction.cpp: Implementierung der Klasse CLinearFunction.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include "stdafx.h"
#include "LinearFunction.h"

#ifdef _DEBUG
#undef THIS_FILE
static char THIS_FILE[]=__FILE__;
#define new DEBUG_NEW
#endif

//////////////////////////////////////////////////////////////////////
// Konstruktion/Destruktion
//////////////////////////////////////////////////////////////////////

CLinearFunction::CLinearFunction(int nCoefficientCount)
{
  m_dSolution = 0;
  m_dCoefficientList = new double[nCoefficientCount+1]; 

  for (int i = 0; i < nCoefficientCount+1; i++)
  {
    m_dCoefficientList[i] = 0;
  }

  m_nCoefficientCount = nCoefficientCount;
}

CLinearFunction::~CLinearFunction()
{
  delete m_dCoefficientList;
}

bool CLinearFunction::setCoefficient(int nIndex, double dValue)
{
  if (nIndex > m_nCoefficientCount || nIndex < 0)
  {
    TRACE("Fehlerhafter Index!\n");
    return false;
  }

  m_dCoefficientList[nIndex] = dValue;
  return true;
}

bool CLinearFunction::getCoefficient(int nIndex, double *dValue)
{
  if (nIndex > m_nCoefficientCount || nIndex < 0)
  {
    TRACE("Fehlerhafter Index!\n");
    return false;
  }

  *dValue = m_dCoefficientList[nIndex];
  return true;
}

void CLinearFunction::setSolution(double dValue)
{
  m_dSolution = dValue;
}

double CLinearFunction::getSolution()
{
  return m_dSolution;
}

int CLinearFunction::getCoefficientCount()
{
  return m_nCoefficientCount;
}
