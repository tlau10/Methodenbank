// SolverMOPS.cpp: Implementierung der Klasse SolverMOPS.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include <stdio.h>
#include <conio.h>
#include <process.h>

#include <fstream>
using namespace std;

#include "SolverMOPS.h"


#ifdef _DEBUG
#undef THIS_FILE
static char THIS_FILE[]=__FILE__;
#define new DEBUG_NEW
#endif


//////////////////////////////////////////////////////////////////////
// Konstruktion/Destruktion
//////////////////////////////////////////////////////////////////////

SolverMOPS::SolverMOPS()
{

}

SolverMOPS::~SolverMOPS()
{

}



int SolverMOPS::Solve(EntryVector &touren)
{
	//1.Create files
	CreateMOPSFiles(touren);
		
	//2.Copy mops.exe
	CString strLocalMopsEXE = mTempFolder.c_str();
	strLocalMopsEXE	+= "\\mops.exe";
	::CopyFile( mSolverPfad.c_str(), strLocalMopsEXE, FALSE );

	//3.Start
	//  Solver aufrufen
	//  Mit dieser Lösung wird kein Thread benötigt
	//  der Aufruf von '_spawnl' kehrt solange nicht zurück,
	//  bis der neue Prozess beendet wird!
	intptr_t res = _spawnl( _P_WAIT, "C:\\Temp\\start_mops.bat", "C:\\Temp\\start_mops.bat", NULL );

	//4.getResult
	int minAnzahlFahrzeuge = getResult(0,touren );

	//  Negative Anzahl bedeutet Fehler!
	return minAnzahlFahrzeuge;
}


int SolverMOPS::SolveOptimal(EntryVector &touren)
{
	//1.Create files
	CreateMOPSFiles(touren);
		
	//2.Copy mops.exe
	CString strLocalMopsEXE = mTempFolder.c_str();
	strLocalMopsEXE	+= "\\mops.exe";
	::CopyFile( mSolverPfad.c_str(), strLocalMopsEXE, FALSE );

	//3.Start
	//  Solver aufrufen
	//  Mit dieser Lösung wird kein Thread benötigt
	//  der Aufruf von '_spawnl' kehrt solange nicht zurück,
	//  bis der neue Prozess beendet wird!
	intptr_t res = _spawnl( _P_WAIT, "C:\\Temp\\start_mops.bat", "C:\\Temp\\start_mops.bat", NULL );

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



void SolverMOPS::CreateMOPSFiles( EntryVector& touren )
{
	int anzFahrten = touren.GetCount();	//Max. Anzahl an Variablen: anzFahrten
	
	const int ANZ_ZEIT_INTERVAL = 48;
	int iVars	= anzFahrten + ANZ_ZEIT_INTERVAL;     // Anzahl Variablen 
	int iRest	= anzFahrten + ANZ_ZEIT_INTERVAL;     // Anzahl Restriktionen

	//***********************************************************************************
	//********** Initialisieren von xn mit 0 ********************************************
	int *bedarf[ANZ_ZEIT_INTERVAL];	//24 * 2 -> Intervalle pro Tag
									//anzFahrten+1: x1...xn
									//xn: Bedarf pro Zeiteinheit-> b-Vector
	for(int i=0;i<ANZ_ZEIT_INTERVAL;i++){
		bedarf[i]= new int [anzFahrten+1];
		for(int j=0;j<=anzFahrten;j++)
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

	//***********************************************************************************
	//******** Ermittelt für jede Zeiteinheit den Bedarf an Fahrzeugen ******************
	char str[200];
	char rs[30];

	//Create MPS-File
	string fileMPSName = GetTempFolder() + "\\mops.mps";
	FILE *streamMPS = fopen( fileMPSName.c_str(), "w" );
	if( !streamMPS ){
		//TODO Fehlermeldung
		return;
	}
	fprintf( streamMPS, "NAME          FaBePlan\n"); 

	//  --- ROWS - Section ---
	fprintf( streamMPS, "ROWS\n" );      
	// Zielfunktionszeile
	fprintf( streamMPS, " N  ZF\n");     
	// eine Restriktion nach der anderen 
	for (int x = 0; x<iRest; x++) {
		fprintf( streamMPS, " E  " );
		sprintf( str, "R%i", x+1 );
		fprintf( streamMPS, str );
		fprintf( streamMPS, "\n" );
	}

	// --- COLUMNS - Section ---
	fprintf( streamMPS, "COLUMNS\n" );      
	// geht alle Variablen des LP durch
	for (int x = 0; x<iVars; x++) {         
		// *** Zielfunktion ***
		char cvar[30];
		sprintf( str, "C%i", x+1 );
		sprintf(cvar, "%-010s", str);
		fprintf( streamMPS, "    " );
		fprintf( streamMPS, cvar );
		fprintf( streamMPS, "ZF        " );
		fprintf( streamMPS, "1\n" );

		// für die Variable  ...
		for ( int y = 0; y < ANZ_ZEIT_INTERVAL; y++) {   // alle Restrictions durchlaufen
			//Z054: 0s001+0s002+0s003+1s004+1s005-s006=0
			fprintf( streamMPS, "    " );
			fprintf( streamMPS, cvar );
			sprintf( rs, "R%i", y+1 );
			sprintf( str, "%-010s", rs );
			fprintf( streamMPS, str );
			if( x<anzFahrten ){
				sprintf( str, "%i\n", bedarf[y][x] );
				fprintf( streamMPS, str );
			}
			else{
				if( y+anzFahrten == x )
					fprintf( streamMPS, "-1\n" );
				else
					fprintf( streamMPS, "0\n" );
			}
		}
		for ( y = 0; y < anzFahrten; y++) {   // alle Restrictions durchlaufen
			//Z105: s004=1
			fprintf( streamMPS, "    " );
			fprintf( streamMPS, cvar );
			sprintf(rs, "R%i", y+ANZ_ZEIT_INTERVAL+1 );
			sprintf(str, "%-010s", rs );
			fprintf( streamMPS, str );
			if( x == y )
				fprintf( streamMPS, "1\n" );
			else
				fprintf( streamMPS, "0\n" );
		}
	}	

	// --- RHS-Section (b-Wert) ---
	fprintf( streamMPS, "RHS\n" );     
	for (int x = 0; x<anzFahrten; x++) {      // alle Restriktionen durchlaufen
		sprintf( rs, "R%i", x+ANZ_ZEIT_INTERVAL+1 );
		sprintf( str, "    MYRHS     %-010s", rs );
		fprintf( streamMPS, str );       
		fprintf( streamMPS, "1\n" );
	}

	//BOUNDS
	fprintf( streamMPS, "BOUNDS\n" );     
	for (int y = 0; y < iVars; y++) {   // alle Boundaries durchlaufen
		//Z001: s001>=0
		fprintf( streamMPS, " LI B1234     " );
		sprintf( rs, "C%i", y+1 );
		sprintf( str, "%-010s", rs );
		fprintf( streamMPS, str );
		fprintf( streamMPS, "0\n" );
	}

	// --- ENDDATA - Section ---
	fprintf( streamMPS, "ENDATA\n" );     
  
	fclose( streamMPS );

//********************************************************************************
	//Create MOPS-Profile-File
	string filePROName = GetTempFolder() + "\\xmops.pro";
	FILE *streamPRO = fopen( filePROName.c_str(), "w" );
	if( !streamPRO ){
		//TODO Fehlermeldung
		return;
	}
	fprintf( streamPRO, "xoutsl = 1\n" );
	fprintf( streamPRO, "xlptyp = 0\n" );
	fprintf( streamPRO, "xmxmin = 15.0\n" );
	fprintf( streamPRO, "\n" );
	fprintf( streamPRO, "xminmx = 'min'\n" );
	fprintf( streamPRO, "xfnmps = 'mops.mps'\n" );
	fclose( streamPRO );

	//Create BAT-File
	string fileStartName = GetTempFolder() + "\\start_mops.bat";
	FILE *streamStart = fopen( fileStartName.c_str(), "w" );
	if( !streamStart ){
		//TODO Fehlermeldung
		return;
	}
	fprintf( streamStart, "cd " );
	fprintf( streamStart, GetTempFolder().c_str() );
	fprintf( streamStart, "\n" );
	fprintf( streamStart, GetTempFolder().c_str() );
	fprintf( streamStart, "\\mops.exe\n" );
	//fprintf( streamStart, "pause\n" );
	fclose( streamStart );

	//  Speicher freigeben
	for(i=0;i<ANZ_ZEIT_INTERVAL;i++)
		delete [] bedarf[i];
}


/**********************************************************************************
** Auslesen der Daten aus mops.lps	                  
** Zuteilung der Touren auf Busse. Evtl. Verschiebung einer Tour,
** um einen Bus zu sparen.
** LÖSUNG OHNE LP-ANSATZ
***********************************************************************************/
int SolverMOPS::getResult(int verschiebeStatus, EntryVector& touren)
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

	//Die Datei 'mops.lps' öffnen und auslesen
	string fileName = GetTempFolder() + "\\mops.lps"; 
	ifstream inStream;
	inStream.open( fileName.c_str() );
	if ( !inStream.good() )
	{
		delete [] used;
		return -1;	//  Fehler beim Lesen der Dateien
	}
		
	int result[48];
	int it = 0;
	int z = 48;
	
	char buffer[256];
	bool bSectionColumnsFound = false;
	int	iCurrentVar = 0;
	//string zielFunktionsWert;
	while( !inStream.eof() && z>0 )
	{
		inStream.getline( buffer, 256 );

		//if(buffer == "(Minimized):")
		//	inStream >> zielFunktionsWert;

		//Columns section COLUMNS
		if( buffer[14] == 'C' && 
			buffer[15] == 'O' && 
			buffer[16] == 'L' && 
			buffer[17] == 'U' && 
			buffer[18] == 'M' && 
			buffer[19] == 'N' && 
			buffer[20] == 'S' )
		{
			bSectionColumnsFound = true;
			continue;
		}

		if( bSectionColumnsFound ){
			iCurrentVar++;
			if( iCurrentVar <= numBus + 1 )
				continue;
			else{
				CString strCurrentVarValue;
				for( int i=24; i<33; i++ )
				{
					strCurrentVarValue += buffer[i];
				}
				result[ iCurrentVar-( numBus + 2 )] = ::atoi( strCurrentVarValue );
			}
			z--;
		}

		/* XA
		{
			inStream >> buffer;
			result[it] = ::atoi(buffer.c_str());
			it++;
		}
		*/
	}

	inStream.close();

	//Test
	ofstream outStream;
	string fileNameOut = GetTempFolder() + "\\Mops.res"; 
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