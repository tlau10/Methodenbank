// Node.cpp: Implementierung der Klasse CSerNode.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include "stdafx.h"
#include "Node.h"

#ifdef _DEBUG
#undef THIS_FILE
static char THIS_FILE[]=__FILE__;
#define new DEBUG_NEW
#endif

IMPLEMENT_SERIAL( CSerNode, CObject, 1 )
//////////////////////////////////////////////////////////////////////
// Konstruktion/Destruktion
//////////////////////////////////////////////////////////////////////

CSerNode::CSerNode()
{
  m_dValue = 0;
}

CSerNode::~CSerNode()
{

}

void CSerNode::setValue(double dValue)
{
  m_dValue = dValue;
}

double CSerNode::getValue()
{
  return m_dValue;
}

void CSerNode::setDescription(CString csDesc)
{
  m_csDescription = csDesc;
}

CString CSerNode::getDescription()
{
  return m_csDescription;
}

void CSerNode::Serialize( CArchive& ar )
{
  if (ar.IsStoring())
  {
    ar << m_csDescription << m_dValue;
  }
  else
  {
    ar >> m_csDescription >> m_dValue;

  }

}

