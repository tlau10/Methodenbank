// LPInputFile.h: Schnittstelle für die Klasse CLPInputFile.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_LPINPUTFILE_H__D4EDB763_C9C7_11D3_8F98_525400E72BB3__INCLUDED_)
#define AFX_LPINPUTFILE_H__D4EDB763_C9C7_11D3_8F98_525400E72BB3__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "LPModel.h"

class CLPInputFile : public CFile  
{
public:
	CString getFilename();
	void setFilename(CString csFilename);
	void writeIntegerInformationToFile();
	void generate();
	CLPInputFile(CLPModel* pLPModel);
	virtual ~CLPInputFile();

  virtual BOOL Open(LPCTSTR lpszFileName);

private:
	CString m_csFilename;
	void writeTargetfunctionToFile();
	void writeRestrictionsToFile();

	CLPModel* m_pLPModel;
};

#endif // !defined(AFX_LPINPUTFILE_H__D4EDB763_C9C7_11D3_8F98_525400E72BB3__INCLUDED_)
