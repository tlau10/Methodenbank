// ProducerList.cpp: Implementierung der Klasse CProducerList.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include "ReceiverList.h"
#include "Node.h"

#ifdef _DEBUG
#undef THIS_FILE
static char THIS_FILE[]=__FILE__;
#define new DEBUG_NEW
#endif

IMPLEMENT_SERIAL( CReceiverList, CObject, 1 )
//////////////////////////////////////////////////////////////////////
// Konstruktion/Destruktion
//////////////////////////////////////////////////////////////////////

CReceiverList::CReceiverList()
{

}

CReceiverList::~CReceiverList()
{

}

void AFXAPI CReceiverList::SerializeElements( CArchive& ar, CSerNode** pElements, int& nCount )
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
			CSerNode* pNewNode = new CSerNode();
			pNewNode->Serialize(ar);
			*pElements = pNewNode;

			if(ar.IsBufferEmpty())
			{
				continue;
			}
		}
	  }

	nCount = i;
}
