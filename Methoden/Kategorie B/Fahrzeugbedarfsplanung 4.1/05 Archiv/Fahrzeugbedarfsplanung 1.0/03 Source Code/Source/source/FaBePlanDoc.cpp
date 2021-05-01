// FaBePlanDoc.cpp : Implementierung der Klasse CFaBePlanDoc
//




#include "stdafx.h"
#include "FaBePlan.h"

#include "FaBePlanDoc.h"
#include "FaBePlanView.h"
#include "IniFile.h"

#include "BedarfErgebnisDlg.h"

#include "SolverXA.h"
#include "SolverMOPS.h"

#include "resource.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CFaBePlanDoc

IMPLEMENT_DYNCREATE(CFaBePlanDoc, CDocument)

BEGIN_MESSAGE_MAP(CFaBePlanDoc, CDocument)
	//{{AFX_MSG_MAP(CFaBePlanDoc)
	ON_COMMAND(ID_EINSTELLUNGEN_OPTIONEN, OnEinstellungenOptionen)
	ON_COMMAND(ID_BEARBEITEN_BEDARFBERECHNEN, OnBearbeitenBedarfBerechnen)
	ON_UPDATE_COMMAND_UI(ID_BEARBEITEN_BEDARFBERECHNEN, OnUpdateBearbeitenBedarfBerechnen)
	ON_UPDATE_COMMAND_UI(ID_FILE_SAVE, OnUpdateFileSave)
	ON_UPDATE_COMMAND_UI(ID_FILE_SAVE_AS, OnUpdateFileSaveAs)
	ON_COMMAND(ID_BEARBEITEN_OPTIMUMBERECHNEN, OnBearbeitenOptimumBerechnen)
	ON_UPDATE_COMMAND_UI(ID_BEARBEITEN_OPTIMUMBERECHNEN, OnUpdateBearbeitenOptimumBerechnen)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CFaBePlanDoc Konstruktion/Destruktion

CFaBePlanDoc::CFaBePlanDoc()
: m_pCurrentSolver(NULL), m_iSolverAuswahl(0)
{
	// ZU ERLEDIGEN: Hier Code für One-Time-Konstruktion einfügen

	//  INI-Datei öffnen und Daten einlesen
	IniFile iniFile;
	iniFile.ReadData();

	// Solver-Pfad-Einstellungen
	m_strXASolverPfad			= iniFile.mXASolverPfad;
	m_strMOPSSolverPfad			= iniFile.mMOPSSolverPfad;
	//  Allgemeine Einstellungen
	m_strLocalTempFolder		= iniFile.mLocalTempFolder;
	m_strApplicationDirectory	= iniFile.mApplicationDirectory;

	//  Aktueler Solver MOPS
	m_pCurrentSolver = new SolverMOPS;
	m_pCurrentSolver->SetPfad( m_strMOPSSolverPfad );
	m_pCurrentSolver->SetTempFolder( m_strLocalTempFolder );
	m_pCurrentSolver->SetAppPath( m_strApplicationDirectory );

	m_iSolverAuswahl = 2;
}

CFaBePlanDoc::~CFaBePlanDoc()
{
	//  INI-Datei öffnen und Daten schreiben
	IniFile iniFile;

	//  Allgemeine Einstellungen
	iniFile.mLocalTempFolder		= m_pCurrentSolver->GetTempFolder();
	iniFile.mApplicationDirectory	= m_pCurrentSolver->GetAppPath();
	// Solver-Pfad-Einstellungen
	iniFile.mXASolverPfad		= m_strXASolverPfad;
	iniFile.mMOPSSolverPfad		= m_strMOPSSolverPfad;

	iniFile.WriteData();

	if( m_pCurrentSolver ) {
		delete m_pCurrentSolver;
		m_pCurrentSolver = NULL;
	}
}

BOOL CFaBePlanDoc::OnNewDocument()
{
	if (!CDocument::OnNewDocument())
		return FALSE;

	// ZU ERLEDIGEN: Hier Code zur Reinitialisierung einfügen
	// (SDI-Dokumente verwenden dieses Dokument)

	//  Alle Touren löschen
	mTouren.DeleteAll();
	mTourenOptimum.DeleteAll();

	return TRUE;
}




/////////////////////////////////////////////////////////////////////////////
// CFaBePlanDoc Serialisierung

//////////////////////////////////////////////////////////////
//  Menüeinträge aktualisieren
//  Aktiv nur, wenn der Fahrplan nicht leer ist
void CFaBePlanDoc::OnUpdateFileSave(CCmdUI* pCmdUI) 
{
	if ( mTouren.GetCount() > 0 )
		pCmdUI->Enable(TRUE);
	else
		pCmdUI->Enable(FALSE);

}

void CFaBePlanDoc::OnUpdateFileSaveAs(CCmdUI* pCmdUI) 
{
	if ( mTouren.GetCount() > 0 )
		pCmdUI->Enable(TRUE);
	else
		pCmdUI->Enable(FALSE);

}


//////////////////////////////////////////////////////////////
//  Die Serialize-Funktion
void CFaBePlanDoc::Serialize(CArchive& ar)
{
	if (ar.IsStoring())
	{
		//  Fahrplan in einer Datei speichern
		SaveFahrplan(ar);
	}
	else
	{
		//  Fahrplan aus einer Datei laden
		LoadFahrplan(ar);
	}
}


////////////////////////////////////////////////////////
//  Den Fahrplan in einer Datei speichern
void CFaBePlanDoc::SaveFahrplan(CArchive &archive)
{
	//  ASCII-Format, Jede Tour in einer Zeile
	for(int i=0; i<mTouren.GetCount(); ++i)
	{
		//  Tourdaten holen
		Entry tour = mTouren.GetEntry(i);

		//  Format
		//  VonOrt;VonStunde;VonMinute;BisOrt;BisStunde;BisMinute;Personen;
		CString textLine;
		textLine.Format("%s;%d;%d;%s;%d;%d;%d;\r\n",
						tour.GetVonOrt().c_str(),
						tour.GetVonStunde(), tour.GetVonMinute(),
						tour.GetBisOrt().c_str(),
						tour.GetBisStunde(), tour.GetBisMinute(),
						tour.GetPersonen());
	
		//  Zeilke schreiben
		archive.WriteString(textLine);
	}
}


////////////////////////////////////////////////////////
//  Den Fahrplan aus einer Datei laden
void CFaBePlanDoc::LoadFahrplan(CArchive &archive)
{
	//  Alle Touren löschen
	mTouren.DeleteAll();
	mTourenOptimum.DeleteAll();
	
	//  Die Datei wird Zeilenweise ausgelesen
	CString textLine;
	while( archive.ReadString(textLine) )
	{
		//  Leerzeichen entfernen
		textLine.TrimLeft();
		
		//  Leere Zeilen überspringen
		if ( textLine.IsEmpty() )
			continue;

		//  Eine Tour aus der Zeile extrahieren
		ExtractTourFromLine(textLine);
	}
}


////////////////////////////////////////////////////////
//  Eine Tour aus der Zeile der Datei extrahieren
void CFaBePlanDoc::ExtractTourFromLine(CString tourText)
{
	//  Format der Zeile:
	//  VonOrt;VonStunde;VonMinute;BisOrt;BisStunde;BisMinute;Personenzahl;
	
	//  Abfahrtsdaten
	int index = tourText.Find(';');
	CString vonOrt = tourText.Left(index);
	tourText = tourText.Mid(index + 1);

	index = tourText.Find(';');
	int vonStunde = ::atoi( tourText.Left(index) );
	tourText = tourText.Mid(index + 1);

	index = tourText.Find(';');
	int vonMinute = ::atoi( tourText.Left(index) );
	tourText = tourText.Mid(index + 1);

	//  Ankunftsdaten
	index = tourText.Find(';');
	CString bisOrt = tourText.Left(index);
	tourText = tourText.Mid(index + 1);

	index = tourText.Find(';');
	int bisStunde = ::atoi( tourText.Left(index) );
	tourText = tourText.Mid(index + 1);

	index = tourText.Find(';');
	int bisMinute = ::atoi( tourText.Left(index) );
	tourText = tourText.Mid(index + 1);

	//  Personenzahl
	index = tourText.Find(';');
	int personen = ::atoi( tourText.Left(index) );
	
	//  Tour in die Liste eintragen
	int tourId = AddTour( Entry(LPCSTR(vonOrt), vonStunde, vonMinute,
								LPCSTR(bisOrt), bisStunde, bisMinute,
								personen ) );
}




/////////////////////////////////////////////////////////////////////////////
// CFaBePlanDoc Diagnose

#ifdef _DEBUG
void CFaBePlanDoc::AssertValid() const
{
	CDocument::AssertValid();
}

void CFaBePlanDoc::Dump(CDumpContext& dc) const
{
	CDocument::Dump(dc);
}
#endif //_DEBUG

/////////////////////////////////////////////////////////////////////////////
// CFaBePlanDoc Befehle


////////////////////////////////////////////////////
//  Neue Tuor in die Tour-Liste eintragen
//  Zurückgegeben wird die ID der Tour
int CFaBePlanDoc::AddTour(Entry &tour)
{
	SetModifiedFlag();
	return mTouren.AddEntry(tour);
}


////////////////////////////////////////////////////
//  Gibt die Tour mit der ID 'tourId' zurück
Entry CFaBePlanDoc::GetTour(int tourId)
{
	return mTouren.GetEntryWithId(tourId);
}


////////////////////////////////////////////////////
//  Tuordaten der Tour mit der ID 'tourId' aktualisieren
void CFaBePlanDoc::UpdateTour(int tourId, Entry &tour)
{
	SetModifiedFlag();
	mTouren.ChangeEntry(tourId, tour);
}


////////////////////////////////////////////////////
//  Gibt die Anzahl der Touren zurück
int CFaBePlanDoc::GetTourCount()
{
	return mTouren.GetCount();
}


////////////////////////////////////////////////////
//  Gibt die Tour an der Position 'position'
//  und ihre ID zurück
void CFaBePlanDoc::GetTourAndId(int position, Entry& tour, int& tourId)
{
	mTouren.GetEntryAndId(position, tour, tourId);
}


////////////////////////////////////////////////////
//  Die Tour mit der ID 'tourId aus der Tour-Liste
//  entfernen
void CFaBePlanDoc::DeleteTour(int tourId)
{
	SetModifiedFlag();
	mTouren.DeleteEntryWithId(tourId);
}





//  Menüauswahl - Einstellungen/Optionen
//  Programmeinstellungen vornehmen
void CFaBePlanDoc::OnEinstellungenOptionen()
{
	//  Dialogbox initialisieren
	CEinstellungenDlg dlgEinstellungen;

	//  Solver Auswahl
	dlgEinstellungen.mSolverAuswahl = m_iSolverAuswahl;	
	//Pfade
	dlgEinstellungen.mXAPfad	= m_strXASolverPfad.c_str();
	dlgEinstellungen.mMOPSPfad	= m_strMOPSSolverPfad.c_str();

	//  Dialogbox darstellen
	if ( dlgEinstellungen.DoModal() != IDOK )
		return;		//  Abbruch gewählt

	m_strXASolverPfad = string(LPCTSTR(dlgEinstellungen.mXAPfad));
	m_strMOPSSolverPfad = string(LPCTSTR(dlgEinstellungen.mMOPSPfad));

	//  Aktiven Solver festlegen
	switch( dlgEinstellungen.mSolverAuswahl )
	{
	case 0:	//  XA
		if( m_pCurrentSolver ) {
			delete m_pCurrentSolver;
			m_pCurrentSolver = NULL;
		}
		m_pCurrentSolver = new SolverXA;
		//  Pfad aktualisieren
		m_pCurrentSolver->SetPfad( m_strXASolverPfad );
		break;
	case 2:	//  MOPS
		if( m_pCurrentSolver ) {
			delete m_pCurrentSolver;
			m_pCurrentSolver = NULL;
		}
		m_pCurrentSolver = new SolverMOPS;
		//  Pfad aktualisieren
		m_pCurrentSolver->SetPfad( m_strMOPSSolverPfad );
		break;
	}

	//  Solver Auswahl
	m_iSolverAuswahl = dlgEinstellungen.mSolverAuswahl;	
	

	m_pCurrentSolver->SetTempFolder( m_strLocalTempFolder );
	m_pCurrentSolver->SetAppPath( m_strApplicationDirectory );
}


//  Menüauswahl - Bearbeiten/Bedarf berechnen
//  Der Fahrzeugbedarf wird berechnet
void CFaBePlanDoc::OnBearbeitenBedarfBerechnen() 
{
	//  Den Fahrzeugbedarf berechnen (ohne Optimierung)
	int maxFahrzeuge = m_pCurrentSolver->Solve(mTouren);

	//  Fehlerbehandlung
	if ( maxFahrzeuge < 0 )
	{
		::AfxMessageBox("Fehler bei der Berechnung!\nDie Solverdateien konnten nicht\ngeöffnet/gelesen werden.\n");
		return;
	}

	if ( maxFahrzeuge == 0 )
	{
		::AfxMessageBox("Zu diesem Problem konnte leider\nkeine Lösung gefunden werden!\n");
		return;
	}

	//  Ergebnis der Berechnung ausgeben
	CBedarfErgebnisDlg* ergebnis = new CBedarfErgebnisDlg;
	ergebnis->mDialogTitel = "Fahrzeugbedarf ohne Fahrzeitoptimierung";
	ergebnis->mMinFahrzeuge = maxFahrzeuge;
	ergebnis->mTouren = &mTouren;

	//  Dialog wir nicht modal dargestellt
	ergebnis->Create(IDD_SOLVER_ERGEBNIS);
	ergebnis->ShowWindow(SW_SHOW);
    
}

void CFaBePlanDoc::OnUpdateBearbeitenBedarfBerechnen(CCmdUI* pCmdUI) 
{
	//  Menü nur aktivieren wenn der Fahrplan nicht leer ist
	if ( mTouren.GetCount() > 0 )
		pCmdUI->Enable(TRUE);
	else
		pCmdUI->Enable(FALSE);

}



//  Menüauswahl - Bearbeiten/Optimum berechnen
//  Der Fahrzeugbedarf (mit Stundenverschiebung) wird berechnet
void CFaBePlanDoc::OnBearbeitenOptimumBerechnen() 
{
	//  Tourenplan kopieren
	mTourenOptimum = mTouren;

	//  Den Fahrzeugbedarf berechnen
	int minFahrzeuge = m_pCurrentSolver->SolveOptimal(mTourenOptimum);

	//  Fehlerbehandlung
	if ( minFahrzeuge < 0 )
	{
		::AfxMessageBox("Fehler bei der Berechnung!\nDie Solverdateien konnten nicht\ngeöffnet/gelesen werden.\n");
		return;
	}

	if ( minFahrzeuge == 0 )
	{
		::AfxMessageBox("Zu diesem Problem konnte leider\nkeine Lösung gefunden werden!\n");
		return;
	}

	//  Ergebnis der Berechnung ausgeben
	CBedarfErgebnisDlg* ergebnis = new CBedarfErgebnisDlg;
	ergebnis->mDialogTitel = "Fahrzeugbedarf mit Fahrzeitoptimierung";
	ergebnis->mMinFahrzeuge = minFahrzeuge;
 
	ergebnis->mTouren = &mTourenOptimum;
	//  Zeiten im View ergänzen
	AddFahrplanZeiten();
	
	//  Dialog wir nicht modal dargestellt
	ergebnis->Create(IDD_SOLVER_ERGEBNIS);
	ergebnis->ShowWindow(SW_SHOW);
}

void CFaBePlanDoc::OnUpdateBearbeitenOptimumBerechnen(CCmdUI* pCmdUI) 
{
	//  Menü nur aktivieren wenn der Fahrplan nicht leer ist
	if ( mTouren.GetCount() > 0 )
		pCmdUI->Enable(TRUE);
	else
		pCmdUI->Enable(FALSE);
}



//  Alternativzeiten im View (im Fahrplan) hinzufügen
void CFaBePlanDoc::AddFahrplanZeiten()
{
	//  View bestimmen
	POSITION position = GetFirstViewPosition();
	CFaBePlanView* fahrplanView = (CFaBePlanView*)GetNextView(position);
	
	//  Die Zeiten hinzufügen
	int tourCount = mTourenOptimum.GetCount();
	for (int i=0; i<tourCount; ++i)
	{
		int tourId;
		Entry oldTour;
		Entry newTour;

		mTouren.GetEntryAndId(i, oldTour, tourId);
		newTour = mTourenOptimum.GetEntryWithId(tourId);

		//  Zeiten hinzufügen nur, wenn sie sich geändert haben
		#if 1
		if (oldTour.GetVonStunde() != newTour.GetVonStunde() ||
			oldTour.GetVonMinute() != newTour.GetVonMinute() ||
			oldTour.GetBisStunde() != newTour.GetBisStunde() ||
			oldTour.GetBisMinute() != newTour.GetBisMinute())
		#endif
			fahrplanView->AddTourZeiten(tourId, oldTour, newTour);
	}
}



