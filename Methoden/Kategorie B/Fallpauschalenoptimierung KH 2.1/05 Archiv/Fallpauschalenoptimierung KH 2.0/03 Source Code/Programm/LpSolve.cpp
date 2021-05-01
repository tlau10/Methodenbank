
//-- Nutzen der Datei ------------------------------------------------//
// Datei erledigt folgende Aufgaben                                   //
// - schreiben in eine Datei                                          //
// - liefern der L�sung (d.h. auselesen der Ausgabedatei vom LP-Solve //
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
	//der als Parameter �bergebene String wird in die im Parameter angegebene (Text)Datei geschrieben
	ofstream outDatei(s.c_str());
	outDatei<<funktionen;
	outDatei.close();
}
//------------------------

int LPSolve::lieferLoesung( string stringName )
{
	int anzahl = FormEingabe1->anzahl;

	string zeichen("x");	//f�r substring bildung
	string tmp=stringName;	//tmp; inhalt der datei (ausgabe.txt)

	int stop = 0;			//stop==0 im String steht die erste zeile der Ausgabe vom LP-Solve;
							//stop!=0 es handelt sich um eine zeile, in der der Wert eines xi steht
	bool fund = true;		//f�r Beendigung der while- schleife, wenn x gefunden wurde
	string zahl="";         //f�r subString Aufbau
	int ausgabe=1;          //f�r Positionierung der Ausgabe von Werten in StringGrid


	//ist das Problem nicht l�sbar, wird eine entsprechende Seite ge�ffnet
	if( ! tmp.compare(0, 25, "This problem is unbounded" ) )
	{
		Application->MessageBoxA("Das Problem ist nicht l�sbar", "Nicht l�sbar", MB_OK | MB_ICONINFORMATION);
		return 1;
	}

	while( fund == true )
	{
		//pr�fen: kommt im string tmp das zeichen x vor
		if( tmp.find_first_of( zeichen ) != string::npos )
		{
			//wurde x in tmp gefunden, wird die Position von x in der int-Var. anfang gespeichert
			int anfang = tmp.find_first_of( zeichen );	//position von x in tmp

			string wert = tmp.substr( 0, anfang );	//string enth�lt alle Zeichen bis zum gefundenen x (anfang)

			//nur ziffern und '.' werden bei der Ergebnisausgabe ber�cksichtigt
			for( int i = 1; i < wert.size(); i++ )
			{
				if( wert[i] >= '0' && wert[i] <= '9' || wert[i] == '.' )
						zahl=zahl+wert[i];	//wenn wert[i] zahl ist, wird sie dem string zahl hinzugef�gt
			}
			if( stop == 0 ) //wenn stop immer noch den Wert 0 hat
			{
				FormEingabe1->LabelZf->Caption = zahl.data(); //ZF-Wert ausgeben
			}//stop hat nicht mehr den Wert =, d.h. es handelt sich nicht um die Zeile der LP-Solve Ausgabe in der der ZF-Wert steht
			else if ( stop > 0 && stop <= anzahl )
			{
				//es werden nur die x-Werte f�r die Anzahl fer aufzunehmenden Patienten einer Fallklasse aufgef�hrt
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
			//in der dritt-letzten zeile des Solver-Ergebnisses steht immer die Anzahl der zu besch�ftigenden KS
			else if( stop == anzahl+1 ) //entspricht: FormEingabe1->StringGridTabelle->RowCount+2 )
			{
				//Ausgabe der Anzahl der zu besch�ftigenden Kr.Schwestern
				FormEingabe1->LabelAnzahlKS->Caption = zahl.data();
			}
			//in der zweiletzen zeile des Solver-Ergebnisses steht immer die h�he der durchFremdvermietung des Labors erzielten Ertr�ge
			else if( stop == anzahl+2 ) //entspricht: FormEingabe1->StringGridTabelle->RowCount+3 )
			{
				//Ausgabe des Deckungsbeitrags durch Fremdnutzung des Labors
				FormEingabe1->LabelLaborWert->Caption = zahl.data();
			}
			else
			{
			
			}
			//substring von tmp erstellen, (pos des gefundenen x +1, akt. L�nge von tmp)
			//tmp enth�lt so immer die n�chste zeile des solver-ergebnisses bis zum zeichen 'x'
			tmp = tmp.substr( anfang + 1, tmp.size() );

			stop++;//stop hochz�hlen, damit ausgelesener Variablenwert dem entsprechenden Label f�r die Ergebnisausgabe im Forumular zugewiesen werden kann
			fund = true; // fund wird auf true gesetzt, da ein Wert f�r x gefunden wurde
			zahl="";	//string-Inhalt wieder l�schen
		}
		else
		{
			char* null="0";//f�r Vergleich auf '0'
			if( stop == anzahl+3 )	//stop ist gleich anzahl+3, wenn es bei dem in tmp enthaltenen Inhalt um die letzte
									//Zeile der Ergebnisausgabe vom Solver handelt
			{
				tmp=tmp.substr(tmp.size() - 1, tmp.size()); //substring von letztem Zeichen bilden;
														  //m�glich, da Schaltervariable nur ein Zeichen enthalten kann
				if( ( strcmp( tmp.data(), null ) ) > 0 )	//Schalter -> =, neues Ger�t kaufen
					FormEingabe1->LabelRoentgen->Caption = "Neues R�ntgenger�t kaufen!";
				else	//Schlater -> 1; kein neues Ger�t kaufen
					FormEingabe1->LabelRoentgen->Caption = "Kein neues R�ntgenger�t kaufen!";
			}
			fund = false; //fund auf false setzen, da keine weitere ergebniszeile vorhanden ist
		}
	}

	return 0;
}
//------------------------

string LPSolve::schreibeInString( char *dateiName )
{
	//die als parameter �bergebenen (text)datei wird in einen string geschrieben
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

	//r�ckgabewert ist die Anzahl der in der Datei entahltenen Zeichen	
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

