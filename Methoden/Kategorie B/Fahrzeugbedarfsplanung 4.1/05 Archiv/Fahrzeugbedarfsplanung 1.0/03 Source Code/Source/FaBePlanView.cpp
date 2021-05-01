// FaBePlanView.cpp : Implementierung der Klasse CFaBePlanView
//

#include "stdafx.h"
#include "FaBePlan.h"

#include "FaBePlanDoc.h"
#include "FaBePlanView.h"

#include "NeueTourDlg.h"


#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CFaBePlanView

IMPLEMENT_DYNCREATE(CFaBePlanView, CFormView)

BEGIN_MESSAGE_MAP(CFaBePlanView, CFormView)
	//{{AFX_MSG_MAP(CFaBePlanView)
	ON_WM_SIZE()
	ON_COMMAND(ID_BEARBEITEN_TOUREINGEBEN, OnBearbeitenTourEingeben)
	ON_COMMAND(ID_BEARBEITEN_TOURLOESCHEN, OnBearbeitenTourLoeschen)
	ON_UPDATE_COMMAND_UI(ID_BEARBEITEN_TOURLOESCHEN, OnUpdateBearbeitenTourLoeschen)
	ON_COMMAND(ID_BEARBEITEN_TOURVERAENDERN, OnBearbeitenTourVeraendern)
	ON_UPDATE_COMMAND_UI(ID_BEARBEITEN_TOURVERAENDERN, OnUpdateBearbeitenTourVeraendern)
	ON_NOTIFY(NM_DBLCLK, IDC_FAHRPLAN, OnDblclkFahrplan)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CFaBePlanView Konstruktion/Destruktion

CFaBePlanView::CFaBePlanView()
	: CFormView(CFaBePlanView::IDD)
{
	//{{AFX_DATA_INIT(CFaBePlanView)
		// HINWEIS: Der Klassenassistent fügt hier Member-Initialisierung ein
	//}}AFX_DATA_INIT
	// ZU ERLEDIGEN: Hier Code zur Konstruktion einfügen,

	mColumnCreated = false;
}

CFaBePlanView::~CFaBePlanView()
{
}

void CFaBePlanView::DoDataExchange(CDataExchange* pDX)
{
	CFormView::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CFaBePlanView)
	DDX_Control(pDX, IDC_FAHRPLAN, mFahrplan);
	//}}AFX_DATA_MAP
}

BOOL CFaBePlanView::PreCreateWindow(CREATESTRUCT& cs)
{
	// ZU ERLEDIGEN: Ändern Sie hier die Fensterklasse oder das Erscheinungsbild, indem Sie
	//  CREATESTRUCT cs modifizieren.

	return CFormView::PreCreateWindow(cs);
}

void CFaBePlanView::OnInitialUpdate()
{
	TRACE("OnInitialUpdate();\n");
	CFormView::OnInitialUpdate();
	GetParentFrame()->RecalcLayout();
	//ResizeParentToFit();

	//  Den Fahrplan-Control initialisieren
	
	//  Spalten in den Fahrplan-Control einfügen
	if ( !HasColumn() )
	{
		mFahrplan.InsertColumn(ColumnTour, _T("Tour"), LVCFMT_LEFT, 250);
		mFahrplan.InsertColumn(ColumnAbfahrt, _T("Abfahrt"), LVCFMT_LEFT, 100);
		mFahrplan.InsertColumn(ColumnAnkunft, _T("Ankunft"), LVCFMT_LEFT, 100);
		mFahrplan.InsertColumn(ColumnPersonen, _T("Personen"), LVCFMT_LEFT, 70);
		mColumnCreated = true;

		//  Image-Liste initialisieren
		mImageList.Create(IDB_FAHRPLAN_IMAGES, 16, 16, RGB(255,0,255));
		mFahrplan.SetImageList(&mImageList, LVSIL_SMALL);
	}

	
	
}


/////////////////////////////////////////////////////////////////////
//  Wird aufgerufen, wenn sich das Dokument geändert hat
//  z.B. nach dem Laden einer Fahrplan-Datei
void CFaBePlanView::OnUpdate(CView* pSender, LPARAM lHint, CObject* pHint) 
{
	TRACE("OnUpdate();\n");

	//  Alte Einträge löschen
	mFahrplan.DeleteAllItems();

	//  Der Fahrplan wird neu aufgebaut
	int anzahlTouren = GetDocument()->GetTourCount();
	for (int i=0; i<anzahlTouren; ++i)
	{
		//  Tour holen
		Entry tour;
		int tourId;
		GetDocument()->GetTourAndId(i, tour, tourId);

		//  Neues (leeres) Item im Fahrplan-Control erzeugen
		int item = mFahrplan.InsertItem(99999, " ", 0);

		//  Das erzeugte Item aktualisieren
		UpdateTourItem(item, tourId, tour);
	}

}



/////////////////////////////////////////////////////////////////////////////
// CFaBePlanView Diagnose

#ifdef _DEBUG
void CFaBePlanView::AssertValid() const
{
	CFormView::AssertValid();
}

void CFaBePlanView::Dump(CDumpContext& dc) const
{
	CFormView::Dump(dc);
}

CFaBePlanDoc* CFaBePlanView::GetDocument() // Die endgültige (nicht zur Fehlersuche kompilierte) Version ist Inline
{
	ASSERT(m_pDocument->IsKindOf(RUNTIME_CLASS(CFaBePlanDoc)));
	return (CFaBePlanDoc*)m_pDocument;
}
#endif //_DEBUG


//  true - wenn die Spalten im Fahrplan schon 
//	erzeugt wurden, sonst false
bool CFaBePlanView::HasColumn()
{
	return mColumnCreated;
}



/////////////////////////////////////////////////////////////////////////////
// CFaBePlanView Nachrichten-Handler

//  Wird aufgerufen, wenn sich die Fenstergrösse
//  verändert hat
void CFaBePlanView::OnSize(UINT nType, int cx, int cy) 
{
	//CFormView::OnSize(nType, cx, cy);

	//  Den Fahrplan-Control anpassen
	if ( mFahrplan.m_hWnd != NULL )
		mFahrplan.MoveWindow(0, 0, cx, cy);
}


//  Menüauswahl - Bearbeiten/Tour eingeben
//  Dialog - Neue Tour eingeben wird aufgerufen
void CFaBePlanView::OnBearbeitenTourEingeben() 
{
	//  Dialog 'Neue Tour' anzeigen
	CNeueTourDlg neueTour;
	
	if ( neueTour.DoModal() != IDOK )
		return;		//  Abbruch

	//  Neue Tour erzeugen und in die Listen
	//  (Tour-Liste und Fahrplan-Control) eintragen
	InsertNewTour(Entry( LPCSTR(neueTour.GetAbfahrtOrt()),
						 neueTour.GetAbfahrtStunde(),
						 neueTour.GetAbfahrtMinute(),
						 LPCSTR(neueTour.GetAnkunftOrt()),
						 neueTour.GetAnkunftStunde(),
						 neueTour.GetAnkunftMinute(),
						 neueTour.GetPersonenzahl() ));
}


////////////////////////////////////////////////////
//  Neue Tuor in beide Listen - Tour-Liste
//  und Fahrplan-Control eintragen
void CFaBePlanView::InsertNewTour(Entry& newTour)
{
	//  Die Tour in die Tour-Liste eintragen
	int tourId = GetDocument()->AddTour(newTour);

	//  Neues (leeres) Item im Fahrplan-Control erzeugen
	int item = mFahrplan.InsertItem(99999, " ", 0);

	//  Das erzeugte Item aktualisieren
	UpdateTourItem(item, tourId, newTour);
}


////////////////////////////////////////////////////
//  Daten einer Tour im Fahrplan-Control aktualisieren
void CFaBePlanView::UpdateTourItem(int item, int tourId, Entry& tour)
{	
	CString tourName;
	tourName.Format("%s - %s", tour.GetVonOrt().c_str(), 
							   tour.GetBisOrt().c_str());
	mFahrplan.SetItem(item, ColumnTour, LVIF_TEXT, tourName, 0, 0, 0, NULL);

	CString abfahrtZeit;
	abfahrtZeit.Format("%02d:%02d", tour.GetVonStunde(), tour.GetVonMinute());
	mFahrplan.SetItem(item, ColumnAbfahrt, LVIF_TEXT, abfahrtZeit, 0, 0, 0, NULL);

	CString ankunftZeit;
	ankunftZeit.Format("%02d:%02d", tour.GetBisStunde(), tour.GetBisMinute());
	mFahrplan.SetItem(item, ColumnAnkunft, LVIF_TEXT, ankunftZeit, 0, 0, 0, NULL);

	CString personen;
	personen.Format("%d", tour.GetPersonen());
	mFahrplan.SetItem(item, ColumnPersonen, LVIF_TEXT, personen, 0, 0, 0, NULL);
	
	mFahrplan.SetItemData(item, tourId);
}


////////////////////////////////////////////////////
//  Alternativzeiten einer Tour hinzufügen
void CFaBePlanView::AddTourZeiten(int tourId, Entry& oldTour, Entry &newTour)
{
	//  Tour-Item suchen
	int item = -1;
	for (int i=0; i<mFahrplan.GetItemCount(); ++i)
	{
		item = mFahrplan.GetNextItem(item, LVNI_ALL);
		
		if ( mFahrplan.GetItemData(item) == tourId )
			break;	//  Item gefunden
	}

	if ( item < 0 )
		return;		//  kein Item gefunden

	//  Tourzeiten verändern
	CString abfahrtZeit;
	abfahrtZeit.Format("%02d:%02d  (%02d:%02d)", oldTour.GetVonStunde(), oldTour.GetVonMinute(),
												 newTour.GetVonStunde(), newTour.GetVonMinute());
	mFahrplan.SetItem(item, ColumnAbfahrt, LVIF_TEXT, abfahrtZeit, 0, 0, 0, NULL);

	CString ankunftZeit;
	ankunftZeit.Format("%02d:%02d  (%02d:%02d)", oldTour.GetBisStunde(), oldTour.GetBisMinute(),
												 newTour.GetBisStunde(), newTour.GetBisMinute());
	mFahrplan.SetItem(item, ColumnAnkunft, LVIF_TEXT, ankunftZeit, 0, 0, 0, NULL);
}


//  Menüauswahl - Bearbeiten/Tour löschen
//  Die selektierte Tour wird gelöscht
void CFaBePlanView::OnBearbeitenTourLoeschen() 
{
	POSITION position = mFahrplan.GetFirstSelectedItemPosition();
	if (position == NULL)
		return;		//  Keine Tour ist selektiert

	//  Warnung
	if ( ::AfxMessageBox("Soll diese Tour wirklich gelöscht werden ?", MB_ICONQUESTION|MB_YESNO) != IDYES )
		return;

	//  Es ist nur Einzelselektion zugelassen
	//  Selektierte Tour und ihre ID
	int selectedItem = mFahrplan.GetNextSelectedItem(position);
	int tourId = mFahrplan.GetItemData(selectedItem);

	//  Tour aus dem Fahrplan entfernen
	mFahrplan.DeleteItem(selectedItem);

	//  Tour aus der Liste im Dokument entfernen
	GetDocument()->DeleteTour(tourId);

}

void CFaBePlanView::OnUpdateBearbeitenTourLoeschen(CCmdUI* pCmdUI) 
{
	if ( mFahrplan.GetFirstSelectedItemPosition() == NULL )
		pCmdUI->Enable(FALSE);
	else
		pCmdUI->Enable(TRUE);
}



//  Menüauswahl - Bearbeiten/Tour verändern
//  Die selektierte Tour wird bearbeitet
void CFaBePlanView::OnBearbeitenTourVeraendern() 
{
	POSITION position = mFahrplan.GetFirstSelectedItemPosition();
	if (position == NULL)
		return;		//  Keine Tour ist selektiert
	
	//  Es ist nur Einzelselektion zugelassen
	//  Index der selektierten Tour
	int tourIndex = mFahrplan.GetNextSelectedItem(position);

	//  Tourdaten holen
	int tourId = mFahrplan.GetItemData(tourIndex);
	Entry tour = GetDocument()->GetTour(tourId);

	//  Dialogbox vorbereiten und darstellen	
	CNeueTourDlg updateTour;
	updateTour.SetAbfahrt(tour.GetVonOrt().c_str(), tour.GetVonStunde(), tour.GetVonMinute());
	updateTour.SetAnkunft(tour.GetBisOrt().c_str(), tour.GetBisStunde(), tour.GetBisMinute());
	updateTour.SetPersonenzahl(tour.GetPersonen());

	//  Dialogbox darstellen
	if ( updateTour.DoModal() != IDOK )
		return;		//  Abbruch

	//  Tourdaten aktualisieren
	tour.Change( LPCSTR(updateTour.GetAbfahrtOrt()), updateTour.GetAbfahrtStunde(),
				 updateTour.GetAbfahrtMinute(), LPCSTR(updateTour.GetAnkunftOrt()),
				 updateTour.GetAnkunftStunde(), updateTour.GetAnkunftMinute(),
				 updateTour.GetPersonenzahl() );

	//  Tour in der Tourliste im Dokument aktualisieren
	GetDocument()->UpdateTour(tourId, tour);

	//  Tourdaten im Fahrplan-Control aktualisieren
	UpdateTourItem(tourIndex, tourId, tour);
}

void CFaBePlanView::OnUpdateBearbeitenTourVeraendern(CCmdUI* pCmdUI) 
{
	if ( mFahrplan.GetFirstSelectedItemPosition() == NULL )
		pCmdUI->Enable(FALSE);
	else
		pCmdUI->Enable(TRUE);
}




//  Doppelklick im Fahrplan-Control wurde ausgeführt
//  Ist dabei eine Tour selektiert, wird diese bearbeitet 
void CFaBePlanView::OnDblclkFahrplan(NMHDR* pNMHDR, LRESULT* pResult) 
{
	//  Tourdaten evtl. bearbeiten
	OnBearbeitenTourVeraendern();
	
	*pResult = 0;
}





