// ProducerList.cpp: Implementierung der Klasse CProducerList.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include "StretchList.h"
#include "Stretch.h"

#ifdef _DEBUG
#undef THIS_FILE
static char THIS_FILE[]=__FILE__;
#define new DEBUG_NEW
#endif

IMPLEMENT_SERIAL( CStretchList, CObject, 1 )
//////////////////////////////////////////////////////////////////////
// Konstruktion/Destruktion
//////////////////////////////////////////////////////////////////////

CStretchList::CStretchList()
{

}

CStretchList::~CStretchList()
{

}

void AFXAPI CStretchList::SerializeElements( CArchive& ar, CStretch** pElements, int& nCount )
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
			CStretch* pNewNode = new CStretch();
			pNewNode->Serialize(ar);
			*pElements = pNewNode;

			if(ar.IsBufferEmpty())
				continue;
		}
	  }

	nCount = i;
}

