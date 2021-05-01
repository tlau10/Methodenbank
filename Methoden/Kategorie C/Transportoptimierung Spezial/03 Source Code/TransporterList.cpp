// ProducerList.cpp: Implementierung der Klasse CProducerList.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include "Transporter.h"
#include "TransporterList.h"

#ifdef _DEBUG
#undef THIS_FILE
static char THIS_FILE[]=__FILE__;
#define new DEBUG_NEW
#endif

IMPLEMENT_SERIAL( CTransporterList, CObject, 1 )
//////////////////////////////////////////////////////////////////////
// Konstruktion/Destruktion
//////////////////////////////////////////////////////////////////////

CTransporterList::CTransporterList()
{

}

CTransporterList::~CTransporterList()
{

}

void AFXAPI CTransporterList::SerializeElements( CArchive& ar, CTransporter** pElements, int& nCount )
{
	int i = 0;

	  for ( i = 0; i < nCount; i++, pElements++ )
	  {
		if (ar.IsStoring())
		{
		  (*pElements)->Serialize( ar );
		}
		else
		{
			CTransporter* pNewNode = new CTransporter();
			pNewNode->Serialize(ar);
			*pElements = pNewNode;

			if(ar.IsBufferEmpty())
				continue;
		}
	  }

	nCount = i;
}


