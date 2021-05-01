// TransopModel.h: Schnittstelle für die Klasse CTransopModel.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_TRANSOPMODEL_H__D4EDB761_C9C7_11D3_8F98_525400E72BB3__INCLUDED_)
#define AFX_TRANSOPMODEL_H__D4EDB761_C9C7_11D3_8F98_525400E72BB3__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "TransportModel.h"
#include "Transporter.h"
#include "LPOutputFile.h"
#include "LPInputFile.h"

class CTransopStretch;

class CTransopModel : public CTransportModel  
{
public:
	double getValueOfObjective();
	void setReceiverList(CList<CSerNode*, CSerNode*>* lpList);
	void setProducerList(CList<CSerNode*, CSerNode*>* lpList);
	void setWorkingDir(CString csPath);
	int getTransporterCount();
	CTransporter* getNextTransporter();
	CTransporter* getFirstTransporter();
	void addTransporter(CTransporter* pTransporter);
	CTransopModel();
	virtual ~CTransopModel();
  virtual CList<CTransopStretch*, CTransopStretch*>* createSolution();
  virtual bool solve();

private:
	double m_dValueOfObjective;
	void generateLPInputFile();
  virtual bool generateLPModel();

  POSITION      m_CurrentListPos;
	CLPOutputFile m_lpOutputFile;
  CLPInputFile* m_lpInputFile;

  CList<CTransporter*, CTransporter*> m_TransporterList;
  CList<CSerNode*, CSerNode*> m_ProducerList;
  CList<CSerNode*, CSerNode*> m_ReceiverList;

  int m_nNumberOfVariables;
  int m_nNumberOfRestrictions;
  int m_nNumberOfTransporters;
  int m_nNumberOfStretches;
  int m_nNumberOfNodes;

  CString m_csWorkingDir;

protected:
	void fillLists();
};

#endif // !defined(AFX_TRANSOPMODEL_H__D4EDB761_C9C7_11D3_8F98_525400E72BB3__INCLUDED_)
