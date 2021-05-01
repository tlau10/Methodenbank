
//-- Nutzen der Datei ------------------------------------------------//
// Datei erledigt folgende Aufgaben                                   //
// - schreiben in eine Datei                                          //
// - liefern der Lösung (d.h. auselesen der Ausgabedatei vom LP-Solve //
// - schreiben in einen String                                        //
// - lese aus einer Datei                                             //
//--------------------------------------------------------------------//

#pragma hdrstop
#include "LpSolve.h"
#include "ProblemForm.h"
#include "MainFormMDI.h"

//----------------------------
#include <string>
#include <iostream>
#include <vector>
#include <fstream>
#include <cstring>
using namespace std;
//----------------------------
#include <stdlib.h>
#include <fstream.h>
//----------------------------

//LPSolve::LPSolve( string lpPfad )
LPSolve::LPSolve()
{
}
//------------------------

void LPSolve::schreibeInDatei(char* dateiName, string funktionen )
{
	string s = MainForm->workSpacePath;
	s = s + dateiName;
	//der als Parameter übergebene String wird in die im Parameter angegebene (Text)Datei geschrieben
	ofstream outDatei(s.c_str());
	outDatei<<funktionen;
	outDatei.close();
}
//------------------------

int LPSolve::lieferLoesung( string stringName )
{
	int anzahl = FormEingabe1->anzahl;

	string zeichen("x");	//für substring bildung
	string tmp=stringName;	//tmp; inhalt der datei (ausgabe.txt)

	int stop = 0;			//stop==0 im String steht die erste zeile der Ausgabe vom LP-Solve;
							//stop!=0 es handelt sich um eine zeile, in der der Wert eines xi steht
	bool fund = true;		//für Beendigung der while- schleife, wenn x gefunden wurde
	string zahl="";         //für subString Aufbau
	int ausgabe=1;          //für Positionierung der Ausgabe von Werten in StringGrid


	//ist das Problem nicht lösbar, wird eine entsprechende Seite geöffnet
	if( ! tmp.compare(0, 25, "This problem is unbounded" ) )
	{
		Application->MessageBoxA("Das Problem ist nicht lösbar", "Nicht lösbar", MB_OK | MB_ICONINFORMATION);
		return 1;
	}

	while( fund == true )
	{
		//prüfen: kommt im string tmp das zeichen x vor
		if( tmp.find_first_of( zeichen ) != string::npos )
		{
			//wurde x in tmp gefunden, wird die Position von x in der int-Var. anfang gespeichert
			int anfang = tmp.find_first_of( zeichen );	//position von x in tmp

			string wert = tmp.substr( 0, anfang );	//string enthält alle Zeichen bis zum gefundenen x (anfang)

			//nur ziffern und '.' werden bei der Ergebnisausgabe berücksichtigt
			for( int i = 1; i < wert.size(); i++ )
			{
				if( wert[i] >= '0' && wert[i] <= '9' || wert[i] == '.' )
						zahl=zahl+wert[i];	//wenn wert[i] zahl ist, wird sie dem string zahl hinzugefügt
			}
			if( stop == 0 ) //wenn stop immer noch den Wert 0 hat
			{
				FormEingabe1->LabelZf->Caption = zahl.data(); //ZF-Wert ausgeben
			}//stop hat nicht mehr den Wert =, d.h. es handelt sich nicht um die Zeile der LP-Solve Ausgabe in der der ZF-Wert steht
			else if ( stop > 0 && stop <= anzahl )
			{
				//es werden nur die x-Werte für die Anzahl fer aufzunehmenden Patienten einer Fallklasse aufgeführt
				//die einen wert != 0 haben, deswegen wird hier vergleichen, ob der wert null ist oder nicht
				char* null="0";
				if( ( strcmp( zahl.data(), null ) ) > 0 )
				{
					//ist der wert nicht null, wird die Anzahl der aufzunehmenden Patienten diese Fallklasse ausgegeben
					FormEingabe1->StringTabelleErgebnis->Cells[0][ausgabe] = stop;
					FormEingabe1->StringTabelleErgebnis->Cells[1][ausgabe] = zahl.data();
					ausgabe++;
				}
			}
			//in der dritt-letzten zeile des Solver-Ergebnisses steht immer die Anzahl der zu beschäftigenden KS
			else if( stop == anzahl+1 ) //entspricht: FormEingabe1->StringGridTabelle->RowCount+2 )
			{
				//Ausgabe der Anzahl der zu beschäftigenden Kr.Schwestern
				FormEingabe1->LabelAnzahlKS->Caption = zahl.data();
			}
			//in der zweiletzen zeile des Solver-Ergebnisses steht immer die höhe der durchFremdvermietung des Labors erzielten Erträge
			else if( stop == anzahl+2 ) //entspricht: FormEingabe1->StringGridTabelle->RowCount+3 )
			{
				//Ausgabe des Deckungsbeitrags durch Fremdnutzung des Labors
				FormEingabe1->LabelLaborWert->Caption = zahl.data();
			}
			else
			{
			
			}
			//substring von tmp erstellen, (pos des gefundenen x +1, akt. Länge von tmp)
			//tmp enthält so immer die nächste zeile des solver-ergebnisses bis zum zeichen 'x'
			tmp = tmp.substr( anfang + 1, tmp.size() );

			stop++;//stop hochzählen, damit ausgelesener Variablenwert dem entsprechenden Label für die Ergebnisausgabe im Forumular zugewiesen werden kann
			fund = true; // fund wird auf true gesetzt, da ein Wert für x gefunden wurde
			zahl="";	//string-Inhalt wieder löschen
		}
		else
		{
			char* null="0";//für Vergleich auf '0'
			if( stop == anzahl+3 )	//stop ist gleich anzahl+3, wenn es bei dem in tmp enthaltenen Inhalt um die letzte
									//Zeile der Ergebnisausgabe vom Solver handelt
			{
				tmp=tmp.substr(tmp.size() - 1, tmp.size()); //substring von letztem Zeichen bilden;
														  //möglich, da Schaltervariable nur ein Zeichen enthalten kann
				if( ( strcmp( tmp.data(), null ) ) > 0 )	//Schalter -> =, neues Gerät kaufen
					FormEingabe1->LabelRoentgen->Caption = "Neues Röntgengerät kaufen!";
				else	//Schlater -> 1; kein neues Gerät kaufen
					FormEingabe1->LabelRoentgen->Caption = "Kein neues Röntgengerät kaufen!";
			}
			fund = false; //fund auf false setzen, da keine weitere ergebniszeile vorhanden ist
		}
	}

	return 0;
}
//------------------------

string LPSolve::schreibeInString( char *dateiName )
{
	//die als parameter übergebenen (text)datei wird in einen string geschrieben
	string s = MainForm->workSpacePath;
	s = s + dateiName;

	ifstream ausgabeDatei( s.c_str() );

	char ein[160];
	string merk="";

	while( ausgabeDatei.good() )
	{
		ausgabeDatei.getline( ein, 160 );
		for( int i = 0; ein[i]; i++ )
		{
			merk = merk + ein[i];
		}
	}
	return merk;
}
//------------------------

int LPSolve::leseAusDatei( char* dateiName )
{
	string s = MainForm->workSpacePath;
	s = s + dateiName;

	//rückgabewert ist die Anzahl der in der Datei entahltenen Zeichen	
	ifstream ausgabeDatei( s.c_str() );

	char ein[160];
	string merk="";

	if( ausgabeDatei.good() )
	{
		ausgabeDatei.getline( ein, 160 );
		for( int i = 0; ein[i]; i++ )
		{
			if( ein[i] != ';' )
				merk = merk + ein[i];
			else
				break;				
		}
	}
	int b = atoi( ein );
	return b;
}

//---------------------------------------------------------------------------

#pragma package(smart_init)

