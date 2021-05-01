// LPInputFile.cpp: Implementierung der Klasse CLPInputFile.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include "LPInputFile.h"

#ifdef _DEBUG
#undef THIS_FILE
static char THIS_FILE[]=__FILE__;
#define new DEBUG_NEW
#endif

//////////////////////////////////////////////////////////////////////
// Konstruktion/Destruktion
//////////////////////////////////////////////////////////////////////

CLPInputFile::CLPInputFile(CLPModel* pLPModel)
{
  m_pLPModel = pLPModel;
}

CLPInputFile::~CLPInputFile()
{

}

BOOL CLPInputFile::Open(LPCTSTR lpszFileName)
{
  CString csCause;
  CFileException e;
  if (!CFile::Open(lpszFileName, CFile::modeReadWrite | CFile::modeCreate, &e))
  {
    csCause.Format("%d", e.m_cause);
    TRACE(csCause);
    return FALSE;
  }
  return TRUE;;
}

void CLPInputFile::writeTargetfunctionToFile()
{
  int     nCoefficientCount;
  double  dValue;
  CString csBuffer;
  CString csVariable;
  CString csCoefficient;

  nCoefficientCount = m_pLPModel->getTargetFunction()->getCoefficientCount();

  if (m_pLPModel->getTargetFunction()->isMax())
  {
    csBuffer = "max:  ";
  }
  else
  {
    csBuffer = "min:  ";
  }

  for (int i = 1; i <= nCoefficientCount; i++)
  {
    csVariable.Format("x%d", i);
    m_pLPModel->getTargetFunction()->getCoefficient(i, &dValue);
    csCoefficient.Format("%.2f", dValue);

    if (dValue >= 0 && i > 1)
    {
      csBuffer += " + ";
    }
    else
    {
      csBuffer += " "; 
    }
    csBuffer += csCoefficient;
    csBuffer += " ";
    csBuffer += csVariable;  
    if (i == nCoefficientCount)
    {
      csBuffer += ";\n\n";
    }
  }
  try
  {
    Write(csBuffer.GetBuffer(csBuffer.GetLength()), csBuffer.GetLength());
    csBuffer.ReleaseBuffer(csBuffer.GetLength());
  }
  catch (CFileException* e)
  {
    csBuffer.ReleaseBuffer(csBuffer.GetLength());
    e->Delete();
  }
}

void CLPInputFile::generate()
{
  if (Open(m_csFilename))
  {
    writeTargetfunctionToFile();
    writeRestrictionsToFile();
    writeIntegerInformationToFile();
    Flush();
    CFile::Close();
  }

}

void CLPInputFile::writeRestrictionsToFile()
{
  int           nCoefficientCount;
  int           nResCounter;
  double        dValue;
  CString       csBuffer;
  CString       csVariable;
  CString       csCoefficient;
  CString       csSolution;
  CRestriction* pRestriction;

  pRestriction = NULL;
  nResCounter  = 0;

  pRestriction = m_pLPModel->getFirstRestriction();

  while(pRestriction != NULL)
  {
    nResCounter++;
    nCoefficientCount = pRestriction->getCoefficientCount();
    csBuffer.Format("R%d: ", nResCounter);
    for (int i = 1; i <= nCoefficientCount; i++)
    {
      csVariable.Format("x%d", i);
      pRestriction->getCoefficient(i, &dValue);
      csCoefficient.Format("%.2f", dValue);
      
      if (dValue >= 0 && i > 1)
      {
        csBuffer += " + ";
      }
      else
      {
        csBuffer += " "; 
      }
      csBuffer += csCoefficient;
      csBuffer += " ";
      csBuffer += csVariable;  
      if (i == nCoefficientCount)
      {
        csSolution.Format("%.2f", pRestriction->getSolution());
        csBuffer += " " + pRestriction->getRestriction() + " " + csSolution + ";\n";
      }
    }
    Write(csBuffer.GetBuffer(csBuffer.GetLength()), csBuffer.GetLength());
    csBuffer.ReleaseBuffer(csBuffer.GetLength());
    pRestriction = m_pLPModel->getNextRestriction();
    csBuffer.Empty();
  }
}

void CLPInputFile::writeIntegerInformationToFile()
{
  int nCoefficientCount;
  CString csBuffer;
  CString csVariable;

  nCoefficientCount = m_pLPModel->getTargetFunction()->getCoefficientCount();

  csBuffer = "\nint ";
  for (int i = 1; i <= nCoefficientCount; i++)
  {
    csVariable.Format("x%d", i);

    csBuffer += csVariable;
    if (i == nCoefficientCount)
    {
      csBuffer += ";";
    }
    else
    {
      csBuffer += ",";  
    }
  }
  Write(csBuffer.GetBuffer(csBuffer.GetLength()), csBuffer.GetLength());
  csBuffer.ReleaseBuffer(csBuffer.GetLength());
}

void CLPInputFile::setFilename(CString csFilename)
{
  m_csFilename = csFilename;
}

CString CLPInputFile::getFilename()
{
  return m_csFilename;
}
