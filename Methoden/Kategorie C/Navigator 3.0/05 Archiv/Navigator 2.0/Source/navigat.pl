###############################################################################
#Autor:		Tarik Varli 					-----------------------------------
#Fach: 		Wirtschaftsinformatik			-----------------------------------
#Semester: 	SS99 /WI8						-----------------------------------
#Vorlesung:	BESF							-----------------------------------
###############################################################################

#Argumente Start und Zielort werden beim Programmaufruf uebergeben
$param = $ARGV[0];				
if ($param eq "") {
	&fehler0;
}
$von = $ARGV[0];				#Start
$nach = $ARGV[1];				#Ziel
$inipath = $ARGV[2];
###############################################################################
#Pfad der Initialisierungsdatei Navigat.ini
#$inipath = "D:\\Or_projekt\\Entwicklung\\Exec\\Navigat.ini";
#$inipath = "C:\\varli\\Entwicklung\\Exec\\Navigat.ini";

#Lese alle Pfaden (Daten und Programm) aus der Navigate.ini
open ( INILINE, $inipath ) or print "INI Datei $inipath kann nicht geoeffnet werden";
while ($inizeile = <INILINE>) 
{
 chop ($inizeile);					#letzte Zeichen Loeschen	
 $zeilebeginn = substr $zeile, 0, 3;	# Auslesen der erste 3 Zeichen einer Zeile
 if(zeilebeginn ne "rem")
 {
   ($path,$value) = split(/\=/,$inizeile,2);
	if ($path eq "dbpath")
	{
		$daten = $value;
	}
	elsif ($path eq "lpsolvepath")
	{
		$solver = $value;
	}
	elsif ($path eq "workingdir")
	{
		$workingdir = $value;
	}
 }
}

###############################################################################

#Benoetigte Dateien nach gegebenen Pfaden:
#; #uebergabe von Modell an Lp-Solver; 
#			


#generierte LP-Modell
$LPfile = $workingdir."\\navigat.lp";	

#BATCH-Datei in der LP-Solver aufgerufen wird,
#sie wird jedes Mal dynamisch in der Workingdir angelegt
$LPsolver = $workingdir."\\lpsolve.bat";

#Ermittelte Resultat von Lp-Solver
$result = $workingdir."\\navigat.out";  	

#Formatoerte ggfls. korrigierte (endgueltige) Resultat von Lp-Solver;
$endresult = $workingdir."\\end_navigat.out"; 

#Die zugrundeliegende Verbindungen und Ortschaften
$verbindungen = $daten."\\DBverbindungen.asc";
$orte = $daten."\\DBorte.asc";

#-----------------------------------------------------------------
#hierfuer zuerst in der Workingdir ein Batch-Datei -lpsolve.bat- erstellen
#in dem die solver-aufruf nach navigat.ini geschrieben wird
$startSolver = "$solver -p < $LPfile > $result";
open ("EINSOLVER", ">$LPsolver") or "Datei $LPsolver kann nicht erstellt werden";
	select EINSOLVER;
	print $startSolver;							#inhalt von Batch datei
close(EINSOLVER);
select STDOUT;	
#--------------------------------------------------------------------

###############################################################################
#Globale Variablen
$zielfunktion = "min:";     #in der Routine wir Zielfunktion gebildet

###############################################################################
#------------------------
#Hier werden der Uebergebenen Orte entsprechenden 
# X-Bezeichnungen gesetzt. Und alle anderen in
# einer Internen Hash-Tabelle gespeichert.
&ladeOrtschaften; 

#Analog zu ladeortschasften
&ladeVerbindungen;

###############################################################################
#Ausgabe der generierten Modell in lp file speichern
#LP Ausgabefile anlegen; Ausgabe auf die LP-File umlenken
#damit wird LP-Modell generierung vollstaendig abgeschlossen...
open ("AUSGABE", ">$LPfile");			
	select AUSGABE;		
	#Rufe Sub-Routine zur LP-Modell generierung
	&createLpModell;	#generiere LP-Modell und schreibe in Datei navigat.lp
close(AUSGABE);
select STDOUT;		#Ausgabe wieder auf Bildschirm zurueckstellen

#generierte LP-Modell an LP-Solver uebergeben

exec ($LPsolver);					#Rufe LPSolver auf

#gebe gelieferten Resultat nach ueberarbeitung aus
&printResult;		#Gebe das Resultat aus

#############################################################################


##########################################################
#-----------  Sub-Routinen      -------------------------#
##########################################################

#------------  Erzeuge LP-Modell   ----------------------#
sub createLpModell
{
$zaehler = -1;

$kontstart = "0";
$kontziel = "0";

while (($key,$value) = each %knotenOrte)
{

	$i = $i + 1;
	$zaehler = $zaehler + 1; 
	$constraint[$zaehler] = "R".$i.":";     #Restriktionanfang
		
	while (($keyVer,$valueVer) = each %knotenVerbindungen)
	{
		$valueAndKey = $valueVer.$keyVer;			#zur Zielfunktion fortschreibung
		($tmpVon,$tmpNach) = split(/\_/,$keyVer,2); #aktuelle Knote in Form von (von -> nach)
		
		if ($von eq $key)		 #Startknote
		{
		    if ($kontstart eq 0)		#zur spaeteren unterscheidung, da bei Ziel- bzw.
			{						# startknote mus = 1 und alle anderen Restr. = 0
				$constraint[$zaehler] ="st".$constraint[$zaehler];
				$kontstart = "1";
			}
			if ($tmpVon eq $key)       				
			{
				$constraint[$zaehler] = $constraint[$zaehler]."+".$keyVer;
				$match = $zielfunktion =~ m/$valueAndKey/;
				if(!$match)				#falls nicht schon vorhanden
				{
						$zielfunktion = $zielfunktion."+".$valueAndKey; #anhaenge 
				}
			}
		}
		
		elsif ($nach eq $key)			#Zielknote
		{
			if ($kontziel eq "0")
			{
				$constraint[$zaehler] ="zi".$constraint[$zaehler];
				$kontziel = "1";
			}
			if ($tmpNach eq $key)       				
			{
				$constraint[$zaehler] = $constraint[$zaehler]."+".$keyVer;
				$match = $zielfunktion =~ m/$valueAndKey/;
				if(!$match)				#falls nicht schon vorhanden
				{
					$zielfunktion = $zielfunktion."+".$valueAndKey; #anhaenge 
				}
			}
		}
		
		else			   #alle anderen Faellen
		{
		   	 if ($tmpVon ne $nach && $tmpNach ne $von)   #Inputpfade zur Startpunkt und
			 {											 #outputpfade zur Zielpunkt nicht beruecksichtigen 
			 	
				if ($tmpNach eq $key)	#Also input
				{
					$constraint[$zaehler] = $constraint[$zaehler]."+".$keyVer;
					$match = $zielfunktion =~ m/$valueAndKey/;
					if(!$match)				#falls nicht schon vorhanden
					{
						$zielfunktion = $zielfunktion."+".$valueAndKey; #anhaenge 
					}
				}
				if ($tmpVon eq $key)	#Also output
				{
					$constraint[$zaehler] = $constraint[$zaehler]."-".$keyVer;
					$match = $zielfunktion =~ m/$valueAndKey/;
					if(!$match)				#falls nicht schon vorhanden
					{
						$zielfunktion = $zielfunktion."+".$valueAndKey; #anhaenge 
					}
					
				}
			 }
		}
		
	}

}
	#Schreibe in die Datei
	print $zielfunktion.";\n";
	foreach $in (@constraint)
	{
    	if ($in =~ s/zi// || $in =~ s/st//)
		{
			print $in."=1;\n";
		}
		else
		{
			print $in."=0;\n";
		}
    }
	
}#ende createLpModell


#-----------------------------------------------------------

sub printResult
{

open (ENDERGEBNIS, ">$endresult") or print "Datei $endresult kann nicht geoeffnet werden";
$optimum = &getOptResult;
$optanfang = substr $optimum, 0, 1;	# Auslesen der ersten Zeichen einer Zeile)
	if($optanfang eq "T")
	{
		&gebeKeineVerbindungAus;	     #Keine Verbindung 
	}
	else	#Also eine Loesung moeglicherweise gegeben
	{
		 @startAndZiel = &getSolutionVector;			#liefert den Losungvektor zurueck
		 $anzahlElem = @startAndZiel;
		  if ($anzahlElem == 0)			#Ist Vector 0 keine Nicht Loesbar
		  {
		 	&gebeKeineVerbindungAus;	     #Keine Verbindung 
		  }
  		  else
  		  {
  		 		$check = &checkSolutionVector(@startAndZiel);
				if ($check eq "true")
				{
					&gebeOptimaleLoesungAus;
				}
				else
				{
					&gebeKeineVerbindungAus;	     #Keine Verbindung 
				}
	
		  }
	}
}

#--------------------------------------------------------------
#Lese optimale Loesung aus der ersten Zeile der .out Datei ein

sub getOptResult
{
open ( ERGEBNIS, $result ) or print "Datei $result kann nicht geoeffnet werden";
	while ($line = <ERGEBNIS>)
	{
			chop ($line);	
			$lineanfang = substr $line, 0, 1; #Auslesen der ersten Zeichen einer Zeile
			if (($lineanfang eq "V"))
			{
				$line =~ s/\ //g;
				($text,$val) = split(/\:/,$line,2);
				return $val;
				close (ERGEBNIS);
			}
			if (($lineanfang eq "T"))
						{
							return $line;
							close (ERGEBNIS);
			}
	}
	
}#Ende  getOptResult

#lese Loesungvektor ein; dabei lasse die Duale-Variablen und
#die in der Loesung nicht vorhandenen Knoten weg.

sub getSolutionVector
{
open ( ERGEBNIS, $result ) or print "Datei $result kann nicht geoeffnet werden";

	while ($line = <ERGEBNIS>)
	{
		chop ($line);	
		$lineanfang = substr $line, 0, 1;	# Auslesen der ersten Zeichen einer Zeile
		if ($lineanfang eq "x")
		{		
			$_ = $line;						#ins aktuellen Puffer schreiben
			if ( /(.*?)(1)$/ ) 				# 1 kommt am Ende vor; also gehoert zu Loesung,
			{
				$line =~ s/\ //g;			#entferne alle Leerzeichen dann
				chop ($line);				#loesche das letzte Zeichen in dem Fall 1
				$startAndZiel[$index] = $line; #trage in die Vector ein
				$index = $index + 1;
			}
		}
	}
close (ERGEBNIS);
return @startAndZiel;  #gebe Vector zurueck
}#Ende getResult

#-----------------------------------------------------------------
#prufe das Ergebniss auf die Richtigkeit;
#LP-Solver liefert beim Sackgassen (bei dem keine Verbindung gibt)
#auch ein Ergebnis. Beim sortieren fehlt es auf indem die Zyklen
#auftreten (start und ziel ist gleich); eine Verbindung 
#ist dann gegeben, wenn von Start zu Ziel eine fluss anhand variablennamen
#besteht: z.B. fuer  start=x1  und  ziel= x4  muss geprueft werden, ob folgender 
#Fluss gegeben ist:	 x1_x2 -> x2->x3 ->x3_x4

sub checkSolutionVector
{
	@solutionVector = @_;
	$startvorh = "false";
	$zielvorh = "false";
	$checkValue = "true";


	#Prüfe ob Start- und Ziel in der Loesungvector vorhanden ist
	foreach $inhalt (@solutionVector)
	{
	  ($tmpStart,$tmpZiel) = split(/\_/,$inhalt,2);
		  if ($tmpStart eq $von)
		  {
		  	 $startvorh = "true";
			 Last;
		  }
	}

	foreach $inhalt (@solutionVector)
	{
	  ($tmpStart,$tmpZiel) = split(/\_/,$inhalt,2);
		  if ($tmpZiel eq $nach)
		  {
			 $zielvorh = "true";
			 Last;
		  }
	}

	if ($startvorh eq "true" && $zielvorh eq "true")	#Wenn Ziel und Start vorhanden sind
	{													#pruefe nach Sackgassen
		foreach $inhalt (@solutionVector)
		{
			($tmpStart,$tmpZiel) = split(/\_/,$inhalt,2);
			$reverse = $tmpZiel."_".$tmpStart;
			foreach $inhalt (@solutionVector)
			{
				if($inhalt eq $reverse)
				{
					$checkValue = "false";	
					return $checkValue;
					Last;
				}
			}
		}
		return $checkValue;
	}
		
	else			#Start und Ziel beim Ergebnis nicht vorhanden
	{
		$checkValue = "false";	
		return $checkValue;
	}
}

#-----------------------------------------------------


#Uebergabe Startknote und die Rueckgabewert ist  
#und dazugehoerige StartZielknote
sub getStartAndZiel					 
{									   
	$returnValue = "false";
	$anz = @startAndZiel;
	foreach $inhalt (@startAndZiel)
	{
		 ($tmpStart,$tmpZiel) = split(/\_/,$inhalt,2);
		 if ($_[0] eq $tmpStart)
		 {
		 	$returnValue = $inhalt;
		 	#return $inhalt;
			Last;
		 }
	}
	return $returnValue;
}

#----------------------------------------------------------------
#Diese Routine liest alle Vorhanden Verbindungen in Vector ein
sub ladeVerbindungen
{
	open ( EINGABE, $verbindungen ) or print "Datei $verbindungen kann nicht geoeffnet werden";
	
	while ($zeile = <EINGABE>) 
	{
		chop $zeile;
		$zeilenanfang = substr $zeile, 0, 1;	# Auslesen der ersten Zeichen einer Zeile
		$zeilenanfang2 = substr $zeile, 5, 4;	# 
		
		if($zeilenanfang ne "#" ) 
		{
		  $zeile =~ s/\ //g;
		  ($vari,$wert) = split(/:/, $zeile,2);     #Knote und die Entfernung
		  $knotenVerbindungen{$vari} = $wert;	   #Speicherung als Mappe um bei der endresultat zu verwenden
		}
	}
	close (EINGABE);
	
}

#----------------------------------------------------------------
#Diese Routine liest alle Vorhanden Verbindungen in Vector ein
sub ladeOrtschaften
{
	open ( EINGABE, $orte ) or print "Datei $orte kann nicht geoeffnet werden";
		
			while ($zeile = <EINGABE>) 
			{
				#$zeile =~ s/\ //;              		# einleitendes Leerzeichen entfernen
				chop $zeile;							#enter am Ende der Satz loeschen
				$zeilenanfang = substr $zeile, 0, 1;	# Auslesen der ersten Zeichen einer Zeile
				$zeilenanfang2 = substr $zeile, 5, 4;	# 
							
				if($zeilenanfang ne "#" ) 
				{
					  $zeile =~ s/\ //g;
		              ($vari,$wert) = split(/:/, $zeile,2);  #Ortbezeicnung und name
					  #Speicherung als Mappe um bei der endresultat zu verwenden
					  $knotenOrte{$vari} = $wert;	   
					  
					  if ($von eq $wert)		#Start
					  {
					  		$von = $vari;		#entsprechende X - Bezeichnung
					  }
					  if($nach eq $wert)		#Ziel
					  {
					  	  $nach = $vari;		#entsprechende X - Bezeichnung
					  }
					  
		    	}
				
			}
	close (EINGABE);
}


#----------------------------------------------------------------
#Diese Sub-Routine gibt den optimalen Loesung aus
sub gebeOptimaleLoesungAus
{
	  	 @sortStartAndZiel = &sortSolutionVector(@startAndZiel);	 #sortiere das Ergebnis
	 	 $anzahlElem1 = @sortStartAndZiel;
	 	 select ENDERGEBNIS;
	 	 print "Kuerzeste Weg von $knotenOrte{$von} nach $knotenOrte{$nach} ist:     $optimum km.\n";
	 	 print "\n";
	 	 print "Wegstrecke:- - - - -\n";
	 	 foreach $contain (@sortStartAndZiel)
	 	 {
	 		($tmpVon,$tmpNach) = split(/_/,$contain);
			print "von $knotenOrte{$tmpVon} nach   $knotenOrte{$tmpNach} : $knotenVerbindungen{$contain} km. \n";
     	 }
	 	 close (ENDERGEBNIS);
    	 select STDOUT;
}

#-------------------------------------------------------------------
sub gebeKeineVerbindungAus
{
	select ENDERGEBNIS;
 		print "Es gibt Keine Verbindung!!!";
	close (ENDERGEBNIS);
	select STDOUT;
}

#-------------------------------------------------------------
#Sortiere die Loesung ( von -> Zwischenstationen ... -> nach )
sub sortSolutionVector
{
$newStart = $von;
$newZiel = $nach;
@startAndZiel = @_;
 	    #Sortiere den Vectorinhalt von Start zu Ziel;
		# Wenn nicht sortierbar gebe die Meldung "Keine Verbindung"
		while ($newStart ne $newZiel) 
		{
			$tmpStartAndZiel = &getStartAndZiel($newStart);
			if (tmpStartAndZiel ne "false")
			{
				$sortStartAndZiel[$z] = $tmpStartAndZiel;
				$z = $z + 1;
				($tmpStart,$tmpZiel) = split(/\_/,$tmpStartAndZiel,2);
				$newStart  = $tmpZiel; 
	    	}								
		}
  return @sortStartAndZiel;		sortierte Vector zurueck
}#Ende 




sub fehler0 
{
	print "Fehler 0:\n";
	print "Beim Programmaufruf muss Start und Zielort uebergeben werden";
	exit 0;
} # ENDE sub fehler0

#Programm Ende
#------------------------------------