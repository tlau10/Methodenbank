// LPOutputFile.cpp: Implementierung der Klasse CLPOutputFile.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include "stdafx.h"
#include "LPOutputFile.h"

#ifdef _DEBUG
#undef THIS_FILE
static char THIS_FILE[]=__FILE__;
#define new DEBUG_NEW
#endif

//////////////////////////////////////////////////////////////////////
// Konstruktion/Destruktion
//////////////////////////////////////////////////////////////////////

CLPOutputFile::CLPOutputFile()
{

}

CLPOutputFile::~CLPOutputFile()
{

}

bool CLPOutputFile::getSolutionValue(int nIndex, double* pDoubleValue)
{
  CString tmp;
  CString csIndex;
  CString csValue;
  int     nPos;
  
  if (nIndex < 1)
  {
    // erster Index liegt bei 1
    ASSERT(false);
    return false;
  }

  tmp = m_csFileBuffer.Right(m_csFileBuffer.GetLength() - (m_csFileBuffer.Find('\n')+1));
  csIndex.Format("%d", nIndex);

  nPos = tmp.Find(csIndex);

  while (nPos != -1)
  {
    if (tmp.GetAt(nPos+csIndex.GetLength()) == ' ' && tmp.GetAt(nPos-1) == 'x')
    {
      csValue = tmp.Mid(nPos+csIndex.GetLength());
      csValue = csValue.Left(csValue.Find('\n')+1);
      csValue.TrimLeft();
      csValue.TrimRight();
      break;
    }
    else
    {
      nPos = tmp.Find(csIndex, nPos+1);
    }
  }

  if (nPos == -1)
  {
    return false;
  }

  *pDoubleValue = atof(csValue.GetBuffer(csValue.GetLength()));
  csValue.ReleaseBuffer(csValue.GetLength());
  return true;
}

BOOL CLPOutputFile::init(LPCTSTR lpszFileName)
{
  if (CFile::Open(lpszFileName, CFile::modeRead))
  {
    try
    {
      LPSTR p = m_csFileBuffer.GetBuffer(4096);
      UINT nLength = Read((void*)p, 4096);
      m_csFileBuffer.ReleaseBuffer(nLength);
      CFile::Close();
      return TRUE;
    }
    catch(CFileException* e)
    {
      CFile::Close();
      throw e;
    }
  }
  return FALSE;
}

double CLPOutputFile::getValueOfObjective()
{
  CString tmp;
  double  dRetValue;
  int     nLength;

  try
  {
	  tmp = m_csFileBuffer.Left(m_csFileBuffer.Find('\n', 27));
	  tmp = tmp.Mid(tmp.Find(":") + 2);

    nLength = tmp.GetLength();
    for (int i = 0; i < nLength; i++)
    {
      if (!(isdigit(tmp.GetAt(0))) && tmp.GetAt(0) != '.') 
      {
        tmp.Delete(0);
      }
    }
    tmp.TrimRight();
    dRetValue = atof(tmp.GetBuffer(tmp.GetLength()));
    tmp.ReleaseBuffer(tmp.GetLength());
  }
  catch (CMemoryException* e)
  {
    throw e;
  }
  return dRetValue;
}

int CLPOutputFile::getSolutionValueCount()
{
  CString csVariable;
  int nIndex;
  int nCounter;

  nCounter = 0;

  csVariable.Format("x%d ",1);

  nIndex = m_csFileBuffer.Find(csVariable);

  while (nIndex != -1)
  {
    nCounter++;
    csVariable.Format("x%d ",nCounter+1);
    nIndex = m_csFileBuffer.Find(csVariable);
  }

  return nCounter;
}

void CLPOutputFile::setFilename(CString csFilename)
{
  m_csFilename = csFilename;
}

CString CLPOutputFile::getFilename()
{
  return m_csFilename;
}
