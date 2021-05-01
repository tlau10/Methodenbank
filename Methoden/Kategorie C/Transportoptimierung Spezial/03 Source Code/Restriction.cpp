// Restriction.cpp: Implementierung der Klasse CRestriction.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include "stdafx.h"
#include "Restriction.h"

#ifdef _DEBUG
#undef THIS_FILE
static char THIS_FILE[]=__FILE__;
#define new DEBUG_NEW
#endif

//////////////////////////////////////////////////////////////////////
// Konstruktion/Destruktion
//////////////////////////////////////////////////////////////////////

CRestriction::CRestriction(int nCoefficientCount)
  :CLinearFunction(nCoefficientCount)
{

}

CRestriction::~CRestriction()
{

}

void CRestriction::setRestriction(CString csRestriction)
{
  m_csRestriction = csRestriction;
}

CString CRestriction::getRestriction()
{
  return m_csRestriction;
}
