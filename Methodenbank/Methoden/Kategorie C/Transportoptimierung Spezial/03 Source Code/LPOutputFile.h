// LPOutputFile.h: Schnittstelle für die Klasse CLPOutputFile.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_LPOUTPUTFILE_H__D74A3995_C90D_11D3_8F98_525400E72BB3__INCLUDED_)
#define AFX_LPOUTPUTFILE_H__D74A3995_C90D_11D3_8F98_525400E72BB3__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

class CLPOutputFile : public CFile  
{
public:
	CString getFilename();
	void setFilename(CString csFilename);

	CLPOutputFile();

  // gibt die Anzahl der Lösungvariablen zurück
	int getSolutionValueCount();
  
  // throw( CMemoryException )
  // gibt den Zielfunktionswert zurück
	double getValueOfObjective();

  // gibt den Lösungswert einer Variable X(nIndex) zurück
	bool getSolutionValue(int nIndex, double* pDoubleValue);

  // throw( CFileException )
  // öffnet die angegebene Datei und initialisiert den FileBuffer
  BOOL init(LPCTSTR lpszFileName);

  virtual ~CLPOutputFile();

private:
	CString m_csFilename;
	CString m_csFileBuffer;
};

#endif // !defined(AFX_LPOUTPUTFILE_H__D74A3995_C90D_11D3_8F98_525400E72BB3__INCLUDED_)
