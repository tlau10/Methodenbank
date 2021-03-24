// Transporter.cpp: Implementierung der Klasse CTransporter.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include "Transporter.h"

#ifdef _DEBUG
#undef THIS_FILE
static char THIS_FILE[]=__FILE__;
#define new DEBUG_NEW
#endif

//////////////////////////////////////////////////////////////////////
// Konstruktion/Destruktion
//////////////////////////////////////////////////////////////////////

CTransporter::CTransporter()
{
  m_nCapacity   = 0;
  m_dCostsPerKm = 0;
  m_nLoading    = 0;
}

CTransporter::~CTransporter()
{

}

void CTransporter::setCapacity(int nCapacity)
{
  m_nCapacity = nCapacity;
}

int CTransporter::getCapacity()
{
  return m_nCapacity;
}

void CTransporter::setCostsPerKm(double dCosts)
{
  m_dCostsPerKm = dCosts;
}

double CTransporter::getCostsPerKm()
{
  return m_dCostsPerKm;
}

void CTransporter::setDescription(CString csDescription)
{
  m_csDescription = csDescription;
}

CString CTransporter::getDescription()
{
  return m_csDescription;
}

void CTransporter::setLoading(int nLoading)
{
  m_nLoading = nLoading;
}

int CTransporter::getLoading()
{
  return m_nLoading;
}

void CTransporter::Serialize( CArchive& ar )
{
  if (ar.IsStoring())
  {
    ar << m_nLoading << m_csDescription << m_dCostsPerKm << m_nCapacity;
  }
  else
  {
    ar >> m_nLoading >> m_csDescription >> m_dCostsPerKm >> m_nCapacity;
  }

}