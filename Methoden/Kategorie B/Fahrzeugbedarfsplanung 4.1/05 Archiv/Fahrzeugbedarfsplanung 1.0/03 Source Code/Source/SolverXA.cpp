// SolverXA.cpp: Implementierung der Klasse SolverXA.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include <stdio.h>
#include <conio.h>
#include <process.h>

#include <fstream>
using namespace std;

#include "SolverXA.h"


#ifdef _DEBUG
#undef THIS_FILE
static char THIS_FILE[]=__FILE__;
#define new DEBUG_NEW
#endif


//////////////////////////////////////////////////////////////////////
// Konstruktion/Destruktion
//////////////////////////////////////////////////////////////////////

SolverXA::SolverXA()
{

}

SolverXA::~SolverXA()
{

}



int SolverXA::Solve(EntryVector &touren)
{
	CreateXAFiles(touren, touren.GetCount());

	//  Solveraufruf
	string fileName = GetTempFolder() + "\\Solver.pif";

	//  Solver.pif-Datei kopieren
	string sourceName = GetAppPath() + "\\Solver.dat";
	::CopyFile(sourceName.c_str(), fileName.c_str(), FALSE);

	//  Mit dieser Lösung wird kein Thread benötigt
	//  der Aufruf von '_spawnl' kehrt solange nicht zurück,
	//  bis der neue Prozess beendet wird!

	//  Solver aufrufen
	_spawnl(_P_WAIT,fileName.c_str(),fileName.c_str(),NULL);


	int minAnzahlFahrzeuge = getResult(0,touren );

	//  Negative Anzahl bedeutet Fehler!
	return minAnzahlFahrzeuge;
}


int SolverXA::SolveOptimal(EntryVector &touren)
{
	CreateXAFiles(touren, touren.GetCount());

	//  Solveraufruf
	string fileName = GetTempFolder() + "\\Solver.pif";

	//  Solver.pif-Datei kopieren
	string sourceName = GetAppPath() + "\\Solver.dat";
	::CopyFile(sourceName.c_str(), fileName.c_str(), FALSE);

	//  Mit dieser Lösung wird kein Thread benötigt
	//  der Aufruf von '_spawnl' kehrt solange nicht zurück,
	//  bis der neue Prozess beendet wird!

	//  Solver aufrufen
	_spawnl(_P_WAIT,fileName.c_str(),fileName.c_str(),NULL);


	//  Temporäre Kopie der Touren
	EntryVector tempTouren = touren;
	int minAnzahlFahrzeuge = getResult(0,tempTouren);
	int minAnzahlFahrzeugeOptimum = getResult(1,tempTouren);

	if ( minAnzahlFahrzeugeOptimum < minAnzahlFahrzeuge)
		minAnzahlFahrzeuge = getResult(1,touren);
	else
		minAnzahlFahrzeuge = getResult(0,touren);

	//  Negative Anzahl bedeutet Fehler!
	return minAnzahlFahrzeuge;
}



int SolverXA::CreateXAFiles(EntryVector& touren, int anzBusse)
{
	char clp[] = "tourplan.clp";		//Übergabedatei an XA
	char lp[] = "tourplan.LP";			//Input-Datei mit Restriktionen
	char out[] = "xa.out";				//Output-Datei
	//char lokalTemp[] = "c:\\temp\\";	//Lokales Temp-Verzeichnis

	//  lokales Temp-Verzeichnis bekopmmt man jetzt mit GetTempFolder()
	//  es ist ein string der Form "c:\\temp"


	char c = '\n';
	char p = '\0';
	char pr= '%';

	int anzFahrten = touren.GetCount();	//Max. Anzahl an Variablen: anzFahrten
	
	int *bedarf[48];	//24 * 2 -> Intervalle pro Tag
									//anzFahrten+1: x1...xn
									//xn: Bedarf pro Zeiteinheit-> b-Vector
	
//***********************************************************************************
//********** Initialisieren von xn mit 0 ********************************************

	for(int i=0;i<48;i++){
		bedarf[i]= new int [anzBusse+1];
		for(int j=0;j<=anzBusse;j++)
			bedarf[i][j]=0;

	}
//***********************************************************************************
//******** Ermittelt für jede Zeiteinheit den Bedarf an Fahrzeugen ******************

	Entry tmp;
	int id;
	int zeitspanneVon, zeitspanneBis;
	
	for(i=0;i<anzFahrten;i++)
	{
		touren.GetEntryAndId(i,tmp,id);
		
		//halbstuendliche Intervalle	z.B. 01.00 Uhr -> bedarf[3]=1
		zeitspanneVon = tmp.GetVonStunde()*2;
		zeitspanneBis = tmp.GetBisStunde()*2;
		

		if(tmp.GetVonMinute() >= 30)
			zeitspanneVon++;

		if(tmp.GetBisMinute() >= 30)
			zeitspanneBis++;
	
		for(int j=zeitspanneVon;j<=zeitspanneBis;j++)
			bedarf[j][i] = 1;		//Alternative 1

	}

	// SOLVER.BAT 
	// wird von solver.pif aufgerufen. Muss in lokalem Temp stehen (Schreibschutz)

	string fileName = GetTempFolder() + "\\solver.bat";
	FILE *stream = fopen( fileName.c_str(), "w" );

	//  Solverpfad aus den Einstellungen holen
	int index = GetPfad().rfind("\\");
	string solverPfad = GetPfad().substr(0, index);

	fprintf( stream, "@echo off\n");
	fprintf( stream, "REM >>>>> Solver: XA\n");
	fprintf( stream, "set Oldpath=%%path%%\n");
	fprintf( stream, "path %s;%%path%%\n", solverPfad.c_str());
	fprintf( stream, "xa %s\n",clp); 
	fprintf( stream, "path %%Oldpath%%\n");
	fprintf( stream, "set Oldpath=\n");

	fclose( stream );

//**************************************************************************************
//***************** Tourplan.clp *******************************************************

	// Pfad wird von solver.bat gesetzt und vom XA aufgerufen

	fileName = GetTempFolder() + "\\tourplan.clp";
	stream = fopen( fileName.c_str(), "w" );
	fprintf( stream, "%s LISTINPUT NO%c",lp, c ); //Input-Datei
	fprintf( stream, "             OUTPUT %s%c", out,c ); //Output-Datei angeben
	fprintf( stream, "             PAGESIZE 24%c", c );
	fprintf( stream, "             LINESIZE 79%c", c );
	fprintf( stream, "             TMARGINS 0%c", c );
	fprintf( stream, "             BMARGINS 0%c", c );
	fprintf( stream, "             FIELDSIZE 11%c", c );
	fprintf( stream, "             DECIMALS 5%c", c );
	fprintf( stream, "             EUROPEAN NO%c", c );
	fprintf( stream, "             LMARGINS 0%c", c );
	fprintf( stream, "             COPIES 1%c", c );
	fprintf( stream, "             WAIT NO%c", c );
	fprintf( stream, "             MUTE NO%c", c );
	fprintf( stream, "             LISTINPUT NO%c", c );
	fprintf( stream, "             WARNING YES%c", c );
	fprintf( stream, "             SOLUTION YES%c", c );
	fprintf( stream, "             CONSTRAINTS YES%c", c );
	fprintf( stream, "             COSTANALYSIS YES%c", c );
	fprintf( stream, "             MARGINANALYSIS YES%c", c );
	fprintf( stream, "             MATLIST NO%c", c );
	fprintf( stream, "             DEFAULTS NO%c", c );

	fclose( stream );

//**********************************************************************
//******************* Tourplan.lp **************************************
	// CLP-Datei gibt als Pfad diese Datei an.
	
	fileName = GetTempFolder() + "\\tourplan.lp";
	stream = fopen( fileName.c_str(), "w" );
	fprintf( stream, "..TITLE%c", c );
	fprintf( stream, "  Tourenplanung%c", c );
	fprintf( stream, "..OBJECTIVE MINIMIZE%c", c );//Minimierungsproblem

	//******** Zielfunktion *********************************************
	int za=1;
	for(i=0;i<anzBusse+48;i++)
	{
		if(i<anzBusse)
			fprintf(stream,"0");

		fprintf(stream,"s%03d",i+1);
		
		if(i<anzBusse+47)
			fprintf(stream,"+");
		else
			fprintf(stream,"%c",c);
	}

	//******** Bounds ****************************************************
	int anzRest = 0; 	//Zaehlt die Anzahl an Restriktionen

	// Puzzled zusammen -> BOUNDS\n	Z001: s001>=0 usw.
	fprintf(stream,"..BOUNDS%c",c);

	for(i=0;i<anzBusse+48;i++)
		fprintf(stream,"Z%03d: s%03d>=0%c",++anzRest,i+1,c);
	
	fprintf(stream,"..CONSTRAINTS%c",c);

//*******************************************************************************
//**   Stelle für jeder Zeile die Gleichung auf.*********************************
	
	for(i=0;i<48;i++)
	{
		fprintf(stream,"Z%03d: ",++anzRest);

		for(int j=0;j<anzBusse;j++)
		{
			if(j!=0)
				fprintf(stream,"+");
			
			fprintf(stream,"%ds%03d",bedarf[i][j],j+1);
		}

		fprintf(stream,"-s%03d=0%c",anzBusse+i+1,c);

	}
//********************************************************************************
//**     Setze Variablen = 1    **************************************************

	for(i=0;i<anzBusse;i++)
		fprintf(stream,"Z%03d: s%03d=1%c",++anzRest,i+1,c);
	
//********************************************************************************
	fclose(stream);
	
	//  Speicher freigeben
	for(i=0;i<48;i++)
		delete [] bedarf[i];


	return anzFahrten;//Anzahl an Fahrten
}


/**********************************************************************************
** Auslesen der Daten aus Xa.out	                  
** Zuteilung der Touren auf Busse. Evtl. Verschiebung einer Tour,
** um einen Bus zu sparen.
** LÖSUNG OHNE LP-ANSATZ
***********************************************************************************/
int SolverXA::getResult(int verschiebeStatus, EntryVector& touren)
{
	//Speichert, ob eine Tour schon bearbeitet wurde. 0:nein -> 1:ja
	//Notwendig, wenn Touren zur gleichen Zeit anfangen!
	int *used = new int[touren.GetCount()];	
	//Initialisierung
	for(int l=0;l<touren.GetCount();l++)
		used[l]=0;

	/*********************************************************************
	** Auslesen der Lösung aus Xa.out
	**********************************************************************/
	string var;
	int numBus = touren.GetCount();
	for(int i=numBus+1;i<=48+numBus;i++)
	{
		char buffer[6];
		::sprintf(buffer,"s%03d", i);

		var += buffer;
	}

	//  Die Datei 'Xa.out' öffnen und auslesen
	ifstream inStream;
	string fileName = GetTempFolder() + "\\Xa.out"; 
	inStream.open(fileName.c_str());
	
	if ( !inStream.good() )
	{
		delete [] used;
		return -1;	//  Fehler beim Lesen der Dateien
	}
		
	int result[48];
	int it = 0;
	int z = 48;
	
	string buffer;
	string zielFunktionsWert;
	while( !inStream.eof() && z>0 )
	{
		inStream >> buffer;

		if(buffer == "(Minimized):")
			inStream >> zielFunktionsWert;
		
		if(var.find(buffer) % 4 == 0)
		{
			inStream >> buffer;
			result[it] = ::atoi(buffer.c_str());
			z--;
			it++;
		}
	}

	inStream.close();

	//Test
	ofstream outStream;
	string fileNameOut = GetTempFolder() + "\\Xa.res"; 
	outStream.open(fileNameOut.c_str());
	for(int i=0; i<48; i++ )
		outStream << result[i] << "\n";
	outStream.close();


	/**************************************************************************
	**  numBus = Max. Anzahl zur Verfügung stehender Busse
	**  Logic:	XA liefert die Anzahl an benötigten Bussen pro Zeiteinheit.
	**			Wenn sich irgendwann der Bedarf erhöht, bedeutet das,
	**			dass eine neue Tour anfängt. Diese Tour holt sich das Programm.
	**			Ist gleichzeitig die Anzahl an benötigten Touren der nächsten Periode
	**			kleiner, heißt das, dass eine Tour beendet wurde und die jetzige Tour
	**			um eine Periode nach hinten verschoben wird.
	****************************************************************************/

	int zfWert = 0;		//Return: Anzahl an benötigten Bussen
	//Suche die Periode mit dem höchsten Bedarf
	for(i=numBus; i>0;i--)
	{
		//48: Anzahl an Perioden
		for(int j=0;j<48;j++)
		{
			if(result[j]==i)	//Wird nur aufgerufen, wenn eine Tour 
			{					//in dieser Periode anfängt.
				int zaehler = 0;
				Entry tmp;
				int id;
				int vstd;
				int vmin;
				int bstd;
				int bmin;
				int pers;
				string vOrt;
				string bOrt;
				int periodeVon;
				int periodeBis;
				
				do	//Suche die Tour, die zur Periode j anfängt
				{
					touren.GetEntryAndId(zaehler,tmp,id);

					vstd = tmp.GetVonStunde();
					vmin = tmp.GetVonMinute();
					vOrt = tmp.GetVonOrt();

					//halbstündige Intervalle
					periodeVon = vstd * 2;
					
					if(vmin>=30)
						periodeVon++;
				
					zaehler++;
					//Anfangszeit der Periode != derz. Periode j 
					//und used[]==1 --> falsche Tour
				}while(periodeVon != j || used[zaehler-1]==1);

				bstd = tmp.GetBisStunde();
				bmin = tmp.GetBisMinute();
				vOrt = tmp.GetVonOrt();
				bOrt = tmp.GetBisOrt();
				pers = tmp.GetPersonen();

				periodeBis = bstd * 2;
				if(bmin>=30)
					periodeBis++;

				
				if(verschiebeStatus==1 && result[j+1] < i && periodeVon < periodeBis && periodeBis < 47)
				{						
					//In der dieser Periode endet auch eine Tour. Also verschiebe
					//die jetzt anfangende in die nächste.

					if(vmin >= 30){
						vmin-=30;
						vstd++;
					}
					else
						vmin+=30;

					if(bmin >= 30){
						bmin-=30;
						bstd++;
					}
					else
						bmin+=30;
					
					touren.ChangeEntry(id,Entry(vOrt,vstd,vmin,bOrt,bstd,bmin,pers,i));

					for(int z=periodeVon;z<=periodeBis;z++)
						result[z]--;
					for(z=periodeVon+1;z<=periodeBis+1;z++)
						result[z]++;
				}
				else		//Die Tour lässt sich nicht ändern.
				{
					used[zaehler-1] = 1; //markiert die Tour als schon bearbeitet
					
					if( zfWert < result[j])	//Speichert die höchste Busnummer
						zfWert = result[j];

					for(int z=periodeVon;z<=periodeBis;z++)
						result[z]--;
					
					touren.ChangeEntry(id,Entry(vOrt,vstd,vmin,bOrt,bstd,bmin,pers,i));
				}
			}
			
		}
	}
	

	//  Speicher freigeben
	delete [] used;	
	
	return zfWert;
}