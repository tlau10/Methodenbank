// BedarfErgebnisDlg.cpp: Implementierungsdatei
//

#include "stdafx.h"
#include "FaBePlan.h"
#include "BedarfErgebnisDlg.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// Dialogfeld CBedarfErgebnisDlg 


CBedarfErgebnisDlg::CBedarfErgebnisDlg(CWnd* pParent /*=NULL*/)
	: CDialog(CBedarfErgebnisDlg::IDD, pParent)
{
	//{{AFX_DATA_INIT(CBedarfErgebnisDlg)
	mMinFahrzeuge = 0;
	//}}AFX_DATA_INIT

	//  Defaulttitel
	mDialogTitel = _T("Fahrzeugbedarf");

	//  VAriablen initialisieren
	mTouren = NULL;

	//  Farbenrefernezen initialisieren
	mFarben[0] = RGB(192, 192, 192);
	mFarben[1] = RGB(192, 0, 0);
	mFarben[2] = RGB(0, 0, 192);
	mFarben[3] = RGB(0, 192, 0);
	mFarben[4] = RGB(0, 192, 192);
	mFarben[5] = RGB(192, 0, 192);
	mFarben[6] = RGB(192, 192, 0);
	mFarben[7] = RGB(128, 128, 128);
	mFarben[8] = RGB(255, 0, 0);
	mFarben[9] = RGB(0, 0, 255);
	mFarben[10] = RGB(0, 255, 0);
	mFarben[11] = RGB(0, 255, 255);
	mFarben[12] = RGB(255, 0, 255);
	mFarben[13] = RGB(255, 255, 0);
	
}


void CBedarfErgebnisDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CBedarfErgebnisDlg)
	DDX_Text(pDX, IDC_MIN_FAHRZEUGE, mMinFahrzeuge);
	//}}AFX_DATA_MAP
}


BEGIN_MESSAGE_MAP(CBedarfErgebnisDlg, CDialog)
	//{{AFX_MSG_MAP(CBedarfErgebnisDlg)
	ON_WM_PAINT()
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// Behandlungsroutinen für Nachrichten CBedarfErgebnisDlg 

BOOL CBedarfErgebnisDlg::OnInitDialog() 
{
	CDialog::OnInitDialog();
	
	//  Titel des Dialoges setzen
	SetWindowText(mDialogTitel);
	
	return TRUE;  // return TRUE unless you set the focus to a control
	              // EXCEPTION: OCX-Eigenschaftenseiten sollten FALSE zurückgeben
}

void CBedarfErgebnisDlg::OnPaint() 
{
	CPaintDC dc(this); // device context for painting
	
	// TODO: Code für die Behandlungsroutine für Nachrichten hier einfügen
	
	// Kein Aufruf von CDialog::OnPaint() für Zeichnungsnachrichten

	//  Die Bedarfgrafik zeichnen
	PaintBedarf(dc);
}


//  Den Farzeugbedarf als Grafik zeichnen
void CBedarfErgebnisDlg::PaintBedarf(CPaintDC& dc)
{
	//  Rechteck zum Zeichnen bestimmen
	CWnd* bedarfElement = GetDlgItem(IDC_FAHRZEUG_BEDARF);
	
	bedarfElement->GetWindowRect(mGrafikRect);
	ScreenToClient(mGrafikRect);

	//  Hintergrund weiss füllen
	dc.FillSolidRect(mGrafikRect, COLORREF(RGB(255,255,255)));

	//  Grösse der Ausgabegrafik bestimmen
	mGrafikRect.left += 20;
	mGrafikRect.right -= 10;
	mGrafikRect.top += 10;
	mGrafikRect.bottom -= 20;

	//  Achsen zeichnen
	//  Y-Achse zeichnen
	PaintAchseY(dc, mGrafikRect);

	//  X-Achse zeichnen
	PaintAchseX(dc, mGrafikRect);
	
	//  Fahrzeuge zeichnen
	PaintFahrzeuge(dc, mGrafikRect);

}


//  X-Achse zeichnen
void CBedarfErgebnisDlg::PaintAchseX(CPaintDC& dc, CRect& rect)
{
	//  Immer 24 Stunden zeichnen
	dc.MoveTo(rect.left, rect.bottom);
	dc.LineTo(rect.right, rect.bottom);

	//  Font wählen
	CFont textFont;
	textFont.CreatePointFont(80, "Arial");
	CFont* oldFont = dc.SelectObject(&textFont);

	//  es wird in 48 Intervallen gearbeitet !
	//  Achsenbeschriftung
	mStundenLength = rect.Width() / 48;
	int nummerPosition = rect.left - 4;
	for (int i=0; i<=24; ++i)
	{
		CString nummer;
		nummer.Format("%d", i);
		dc.TextOut(nummerPosition, rect.bottom + 2, nummer);

		nummerPosition += mStundenLength * 2;
	}
	
	//  Font zurücksetzen
	dc.SelectObject(oldFont);
}


//  Y-Achse zeichnen
void CBedarfErgebnisDlg::PaintAchseY(CPaintDC& dc, CRect& rect)
{
	//  Maximale Anzahl Fahrzeuge zeichnen
	dc.MoveTo(rect.left, rect.top);
	dc.LineTo(rect.left, rect.bottom);

	//  Font wählen
	CFont textFont;
	textFont.CreatePointFont(100, "Arial");
	CFont* oldFont = dc.SelectObject(&textFont);

	//  Achsenbeschriftung
	mFahrzeugLength = rect.Height() / mMinFahrzeuge;
	int nummerPosition = rect.bottom - (mFahrzeugLength / 2) - 3;
	for (int i=0; i<mMinFahrzeuge; ++i)
	{
		CString nummer;
		nummer.Format("%d", i + 1);
		dc.TextOut(rect.left - 18, nummerPosition, nummer);

		nummerPosition -= mFahrzeugLength;
	}

	//  Font zurücksetzen
	dc.SelectObject(oldFont);
}


//  Fahrzeuge zeichnen
void CBedarfErgebnisDlg::PaintFahrzeuge(CPaintDC& dc, CRect& rect)
{
	if ( mTouren == NULL)
		return;

	int tourCount = mTouren->GetCount();
	for (int i=0; i<tourCount; ++i)
	{
		//  Auslesen der Daten
		Entry tour = mTouren->GetEntry(i);

		//  Fahrzeug zeichnen
		DrawTour(dc,rect,tour);
	}
}


//  Eine Tour zeichnen
void CBedarfErgebnisDlg::DrawTour(CPaintDC& dc, CRect& rect, Entry& tour)
{
	//  Zeiten bestimmen (30 Minuten-Intervalle)
	int von = tour.GetVonStunde() * 2;
	if ( tour.GetVonMinute() > 29 )
		von += 1;
	int bis = tour.GetBisStunde() * 2;
	if ( tour.GetBisMinute() > 0 )
		bis += 1;

	if ( tour.GetBisMinute() > 29 )
		bis *= 1;

	int tourLength = bis - von;

	CRect fahrzeugRect;
	fahrzeugRect.left = rect.left + mStundenLength * von;
	fahrzeugRect.right = fahrzeugRect.left + mStundenLength * tourLength;
	fahrzeugRect.top = rect.bottom - mFahrzeugLength * tour.GetFahrzeugNummer();
	fahrzeugRect.bottom = fahrzeugRect.top + mFahrzeugLength;

	//  Rechteck zeichnen
	dc.FillSolidRect(fahrzeugRect, mFarben[tour.GetFahrzeugNummer() % 14]);

	//  Umrandung zeichnen
	dc.DrawEdge(fahrzeugRect, EDGE_RAISED, BF_RECT);

	//  Tourbezeichnung
	CFont textFont;
	textFont.CreatePointFont(80, "Arial");
	CFont* oldFont = dc.SelectObject(&textFont);

	//  Tournamen schreiben 
	dc.TextOut(fahrzeugRect.left + 5, fahrzeugRect.top + 5, tour.GetVonOrt().c_str());
	dc.TextOut(fahrzeugRect.left + 5, fahrzeugRect.top + 17, tour.GetBisOrt().c_str());


	//  Font zurücksetzen
	dc.SelectObject(oldFont);
}



void CBedarfErgebnisDlg::OnOK() 
{
	// TODO: Zusätzliche Prüfung hier einfügen
	
	CDialog::EndDialog(IDOK);
}

void CBedarfErgebnisDlg::PostNcDestroy() 
{
	// TODO: Speziellen Code hier einfügen und/oder Basisklasse aufrufen
	
	CDialog::PostNcDestroy();
	//  Nichtmodales Dialog freigeben
	delete this;
}

