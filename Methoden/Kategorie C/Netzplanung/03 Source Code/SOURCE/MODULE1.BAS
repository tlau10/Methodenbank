' Module1
Option Explicit

Sub akt_Datnam ()

Dim i, lange As Integer
Dim pfaddateiname As String

    'IO.StatusDatei.Caption = "Datei: " + UCase$(IO.StdDialog1.Filename)

    ' reinen Dateinamen extrahieren
    pfaddateiname = UCase$(IO.StdDialog1.Filename)
    lange = Len(pfaddateiname)
    For i = lange To 1 Step -1
	If Mid$(pfaddateiname, i, 1) = "\" Then
	   dateiname = Right$(pfaddateiname, (lange - i))
	   Exit For
	End If
    Next i
    
    STARTFORMNETZPLAN.Caption = "Netzplantechnik - [" & dateiname & "]"
    
End Sub

Sub Berechnung ()


    screen.MousePointer = 11
'    Unload XA_Ausgabe     'falls vorhanden
'    'unload andere Ausgabefenster

    '-- Pif ins WorkingDir kopieren + Einträge
    Pif_Eintrag "solver.pif", "solver.bat"      ' siehe global.bas


    '-- Knotenarten ermitteln
    Knotenanalyse

    Select Case solver

       Case "XAeqs"

	  Solver_Batch_Datei_schreiben
	  CLP_Datei_Schreiben
	  EQS_Datei_schreiben   'LP-Datei

	  If Dir(WorkingDir + "\xa.out") <> "" Then
	     Kill WorkingDir + "\xa.out"
	  End If

	  '-- Berechnungslauf
	  x = Shell(WorkingDir + "\solver.pif")
	  Check_and_Wait
	  
	  netzout.Show 0
	  

	' Nachfolgender Case-Zweig ist Realisierung des LP_Solve Aufrufs. WS96/97
	Case "LPSOLVE"
	    
	    Solver_Batch_Datei_schreiben
	    MPS_Datei_schreiben
	    
	    If Dir(WorkingDir + "\lp_solve.out") <> "" Then
		Kill WorkingDir + "\lp_solve.out"
	    End If
	    x = Shell(WorkingDir + "\solver.pif")
	    Check_and_Wait

	    'netzout.Show 0
	    x = Shell("notepad " + WorkingDir + "\lp_solve.out", 1)
	    

'       Case "XAmps"
	  
'          XA_Batch_Datei_schreiben
'          MPS_Datei_schreiben
	  
'          If Dir(XAPath + "\mi.out") <> "" Then
'             Kill XAPath + "\mi.out"
'          End If
	    
	  '-- Berechnungslauf
'          x = Shell(EXEPath + "\mi_xamps.pif")
'          Check_and_Wait
	  
'          XA_Ausgabe.Show 0
		   
'       Case "MOPS"
	  
'          Solver_Batch_Datei_schreiben
'          MPS_Datei_schreiben
	  
'          If Dir(MOPSPath + "\mops.out") <> "" Then
'             Kill MOPSPath + "\mops.out"
'          End If

	  '-- Berechnungslauf
'          x = Shell(EXEPath + "\solver.pif")
'          Check_and_Wait
	  
 '         If Dir(MOPSPath + "\mops.out") = "" Then
 '            MsgBox "Die Berechnungsdatei von MOPS konnte nicht gefunden werden.", 48, "mops.out"
 '         Else
 '           x = Shell("notepad " + MOPSPath + "\mops.out", 1)
 '            While getmoduleusage(x) > 0
 '                warte = DoEvents()
 '            Wend
'          End If
	  
	  
       Case Else

	  MsgBox "Noch nicht implementiert !"

    End Select
    
    screen.MousePointer = 0
    
End Sub

Sub Check_and_Wait ()

' Kontrolle des Solver-Aufrufs mittels SHELL
' und Verzögern des Programmlaufes, bis die
' SHELL abgearbeitet ist.

Dim warte As Variant

If Err Then
   MsgBox ("Der Solver konnte nicht aufgerufen werden !")
Else
   ' Zur Beruhigung der ORler
   wartefenster.Show 0
   
   While getmoduleusage(x) > 0
       warte = DoEvents()
   Wend

   Unload wartefenster
End If

End Sub

Sub CLP_Datei_Schreiben ()

Open WorkingDir + "\netz.clp" For Output As #2

Print #2, "  netz.lp"
Print #2, "                OUTPUT xa.out    PAGESIZE  24"
Print #2, "                LINESIZE  79     TMARGINS   0"
Print #2, "                BMARGINS   0     FIELDSIZE  11"
Print #2, "                DECIMALS   5     EUROPEAN  NO"
Print #2, "                LMARGINS   0     COPIES     1"
Print #2, "                WAIT      NO     MUTE  NO"
Print #2, "                LISTINPUT NO     WARNING NO"
Print #2, "                SOLUTION  YES    CONSTRAINTS  YES"
Print #2, "                COSTANALYSIS YES MARGINANALYSIS  YES"
Print #2, "                MATLIST  NO      DEFAULTS NO"
'Print #2, "                tomps yes"

Close #2

End Sub

Sub CursorAnzeige ()
	    
       Select Case posx
	    Case 5
	       If posy = Val(AnzahlKnoten) Then
	       GRID_IN.Liste.SelStartRow = 1
	       GRID_IN.Liste.SelEndRow = 1
	       GRID_IN.Liste.SelStartCol = 1
	       GRID_IN.Liste.SelEndCol = 1
	    Else
	       GRID_IN.Liste.SelStartRow = posy + 1
	       GRID_IN.Liste.SelEndRow = posy + 1
	       GRID_IN.Liste.SelStartCol = 1
	       GRID_IN.Liste.SelEndCol = 1
	    End If
	    Case 6
		If posy = Val(AnzahlKnoten) Then
		GRID_IN.Liste.SelStartRow = posy - posy + 1
		GRID_IN.Liste.SelEndRow = posy - posy + 1
		GRID_IN.Liste.SelStartCol = 2
		GRID_IN.Liste.SelEndCol = 2
	    Else
		GRID_IN.Liste.SelStartRow = posy + 1
		GRID_IN.Liste.SelEndRow = posy + 1
		GRID_IN.Liste.SelStartCol = 2
		GRID_IN.Liste.SelEndCol = 2
	    End If
	    Case Else
		GRID_IN.Liste.SelStartRow = posy
		GRID_IN.Liste.SelEndRow = posy
		GRID_IN.Liste.SelStartCol = posx + 1
		GRID_IN.Liste.SelEndCol = posx + 1
       End Select
       GRID_IN.Liste.HighLight = True
End Sub

Sub Datei_oeffnen ()

Dim l002E As Variant
Dim l0036 As Variant
Dim pfaddateiname As String

l002E = 11

IO.StdDialog1.DefaultExt = "ne1"
IO.StdDialog1.InitDir = dbpath
IO.StdDialog1.Filter = "Netzplan-Dateien (*.net)|*.net|Alle Dateien (*.*)|*.*"

IO.StdDialog1.Action = 1
pfaddateiname = IO.StdDialog1.Filename

'Keine Datei gewählt
If pfaddateiname = "" Then
   Exit Sub
End If


      
Open IO.StdDialog1.Filename For Input As #1
If Input$(6, #1) <> "&&#ne1" Then
    MsgBox "Datei konnte nicht geladen werden!" + Chr$(13) + "Unzulässiges Dateiformat", 48
    Close #1
    Exit Sub
End If
Input #1, AnzahlKnoten
Input #1, KostTag$
    For l0036 = 1 To Val(AnzahlKnoten)
	Input #1, U(l0036).do_i
	Input #1, U(l0036).do_j
	Input #1, U(l0036).dnorm
	Input #1, U(l0036).dmin
	Input #1, U(l0036).w
    Next l0036
Close #1

l002E = 0

End Sub

Sub DStr_ein (p003A As Integer, p003C As Integer, p003E)
Dim l0040 As Variant
Select Case p003A
	Case 1
	    U(p003C).do_i = Val(p003E)
	    l0040 = 1
	Case 2
	    U(p003C).do_j = Val(p003E)
	    l0040 = 1
	Case 3
	    U(p003C).dnorm = Val(p003E)
	    l0040 = 1
	Case 4
	    U(p003C).dmin = Val(p003E)
	    l0040 = 1
	Case 5
	    U(p003C).w = Val(p003E)
	    l0040 = 1
    End Select
End Sub

Sub einsetzen (p, p2)
	GRID_IN.Liste.Col = posx
	GRID_IN.Liste.Row = posy
	If (GRID_IN.Liste.Text <> "") And (GRID_IN.txtin.Text = "") Then
	    Text = GRID_IN.Liste.Text
	Else
	    Text = GRID_IN.txtin.Text
	End If
	GRID_IN.Liste.Text = Text
	GRID_IN.txtin.Text = ""
End Sub

Sub EQS_Datei_schreiben ()

Dim i, j As Integer
Dim merke As Variant
Dim ZF As Variant
Dim i_hamascho As Variant
Dim j_hamascho As Variant
Dim LTeilx1, LTeil1, LTeil2, LTeil3, LTeil4 As String
Dim RTeil2 As Variant
Dim T2 As Variant
Dim Kenz As Variant
Dim VOp As Variant

On Error Resume Next


Open WorkingDir + "\netz.lp" For Output As #1

Print #1, "..TITLE"
Print #1, "   Netzplan"
Print #1, "..OBJECTIVE MINIMIZE"


'-- Zielfunktion
merke = 0
ZF = ""

For i = 1 To Val(AnzahlKnoten)
 If U(i).iTyp <> 7 Then
    'Auswirkung von stetiger Zeitverkurzung
    If (U(i).dnorm - U(i).dmin) <> 0 Then
       ZF = ZF + Trim(Str(U(i).w)) + " Y" + Trim(Str(U(i).do_i)) + Trim(Str(U(i).do_j)) + " + "
    End If
    ' Auswirkung von diskreter Zeitverkurzung bei Entscheidungsknoten
    If U(i).iTyp = 3 And U(i).dnorm = U(i).dmin Then
       ZF = ZF + Trim(Str(U(i).w)) + " [X" + Trim(Str(U(i).do_i)) + Trim(Str(U(i).do_j)) + "]" + " + "
      Else
	'Alle weiteren X's (Schaltervariablen) werden (mit 0 gewichtet) bekanntgemacht
	If U(i).iTyp = 3 Then
	  ZF = ZF + "0 [X" + Trim(Str(U(i).do_i)) + Trim(Str(U(i).do_j)) + "]" + " + "
	End If
    End If
    'Alle U's werden der Zielfunktion (mit 0 gewichtet) bekanntgemacht
    i_hamascho = 0
    j_hamascho = 0
    For j = 1 To i - 1
	If U(i).do_j = U(j).do_j Or U(i).do_j = U(j).do_i Then j_hamascho = 1
	If U(i).do_i = U(j).do_i Or U(i).do_i = U(j).do_j Then i_hamascho = 1
    Next j
    If i_hamascho = 0 And U(i).iTyp <> 5 Then
	ZF = ZF + "0 U" + Trim(Str(U(i).do_i)) + " + "
    End If
    If j_hamascho = 0 And U(i).jTyp <> 4 And U(i).jTyp <> 5 Then
	 ZF = ZF + "0 U" + Trim(Str(U(i).do_j)) + " + "
    End If
    
    'Die gesamte Projektdauer (U des Endknotens) wird mit den
    'Gesamtprojektkosten je Tag bewertet
    If U(i).do_j = ende And merke = 0 Then
       ZF = ZF + Trim(KostTag) + " U" + Trim(Str(U(i).do_j)) + " + "
       merke = 1
    End If
 End If
Next i

Print #1, Left(ZF, Len(ZF) - 3)


' Beginn: Code zur Erzeugung der Nebenbedingungen (..Constraints),
' auf (..Bounds) wird verzichtet
Print #1, "..BOUNDS"
Print #1, "..CONSTRAINTS"
For i = 1 To Val(AnzahlKnoten)
 If U(i).iTyp <> 7 Then
    LTeilx1 = ""
    LTeil1 = ""
    LTeil2 = ""
    LTeil3 = ""
    LTeil4 = ""
    RTeil2 = 0
    If U(i).jTyp <> 4 Then
	LTeil1 = "U" + Trim(Str(U(i).do_j))
    Else
       For j = 1 To Val(AnzahlKnoten)
	    If U(j).do_i = U(i).do_i And i <> j Then
		 LTeil1 = "U" + Trim(Str(U(j).do_j))
		 LTeilx1 = " X" + Trim(Str(U(i).do_i)) + Trim(Str(U(i).do_j))
	    End If
       Next j
       RTeil2 = (-MM)
    End If


    T2 = U(i).iTyp
    If T2 = 0 Then T2 = 2
    Select Case T2
	Case 1
	Case 2
	    LTeil2 = " - U" + Trim(Str(U(i).do_i))
	Case 3
	    LTeil2 = " - U" + Trim(Str(U(i).do_i))
	    If U(i).jTyp = 4 Then
		LTeil4 = " - " + Trim(Str(MM)) + LTeilx1
	    Else
		LTeilx1 = " X" + Trim(Str(U(i).do_i)) + Trim(Str(U(i).do_j))
		LTeil4 = " - " + Trim(Str(MM)) + LTeilx1
		RTeil2 = (-MM)
		'Binares Schalten der beiden Alternativwege:
		j = 1
		For j = 1 To Val(AnzahlKnoten)
		    If U(j).do_i = U(i).do_i And i <> j Then
			Print #1, "X" + Trim(Str(U(i).do_i)) + Trim(Str(U(j).do_j)) + " + X" + Trim(Str(U(i).do_i)) + Trim(Str(U(i).do_j)) + " = 1"
		    End If
		Next j

	    End If
	Case 4
	    Kenz = "xx4"
    End Select
    
    '--------------------------------------------------------------
    ' Einbau von stetiger Zeitersparnis
    If U(i).dnorm <> 0 And U(i).dmin <> 0 And ((U(i).dnorm - U(i).dmin) <> 0) Then
	LTeil3 = " + Y" + Trim(Str(U(i).do_i)) + Trim(Str(U(i).do_j))
    Else
	LTeil3 = ""
    End If
    VOp = " >= "
    RTeil2 = RTeil2 + U(i).dnorm
    
    'Beginn: Uli's neuer Code fur Schranken aller Art (Bounds)
    If U(i).dmin < U(i).dnorm Then
	'Festlegen der technischen Grenzen fur die _Zeiteinsparung
	Print #1, "Y" + Trim(Str(U(i).do_i)) + Trim(Str(U(i).do_j)) + " <= " + Trim(Str(U(i).dnorm - U(i).dmin))
	Print #1, "Y" + Trim(Str(U(i).do_i)) + Trim(Str(U(i).do_j)) + " >= 0"
    End If
    i_hamascho = 0
    j_hamascho = 0
    For j = 1 To i - 1
	If U(i).do_j = U(j).do_j Then j_hamascho = 1
	If U(i).do_i = U(j).do_i Then i_hamascho = 1
    Next j
    If i_hamascho = 0 Then
	Print #1, "U" + Trim(Str(U(i).do_i)) + " >= 0"
    End If
    If j_hamascho = 0 And U(i).jTyp <> 4 Then
	Print #1, "U" + Trim(Str(U(i).do_j)) + " >= 0"
    End If
    
    'Ende Uli's neuer Code fur Schranken aller Art (Bounds)
    '---------------------------------------------------------
    
    'Schreiben der XA LP-Modell Nebenbedingungen
    If Kenz <> "xx4" Then
	Print #1, Kenz;
	Print #1, LTeil1;
	Print #1, LTeil2;
	Print #1, LTeil3;
	Print #1, LTeil4;
	Print #1, VOp;
	Print #1, RTeil2
    End If
 End If
Next i

Close #1

'Open progpath + "xa.bat" For Output As #3
'Print #3, Left(XAPath, 2)
'Print #3, "CD " + Mid(XAPath, 3, Len(XAPath) - 3)
'Print #3, "XA NETZ.CLP"
'Close #3
'l0094 = Shell(progpath + "xa.bat", 1)

       
End Sub

Function FormDrucken (name_frm As String)

End Function

Sub GRAPH_Datei_schreiben ()

Dim i As Integer
'Dim CurPath, Drive, NewDrive
'Dim X

'Die Graphik-Ausgabe erfolgt ueber das Programm e-graph.
'Dazu werden die Daten in die Datei netzplan.grf im Daten-
'Verzeichnis von e-graph geschrieben, und e-graph aufgerufen.
'Inhalt der Datei netzplan.grf:
'Jede Zeile enthaelt die Beschreibung einer Kante (Taetigkeit).
'Es wird jeweils angegeben, welcher Knoten mit welchem anderen
'Knoten verbunden ist und von welchem Typ die jeweiligen Knoten
'sind.
'    Typ 0 = normaler Knoten
'    Typ 1 = Startknoten
'    Typ 2 =
'    Typ 3 = Entscheidungsknoten
'    Typ 4 = Scheintaetigkeit
'    Typ 5 = Endknoten
'    Typ 6 =
'    Typ 7 =
'Die Kosten werden als Kantenbeschriftung in folgender
'Weise abgebildet:
'                    norm(min)
'                 -------------->
'                     Zusatz
'
'_____________________________________________________________
'Knoten        Knoten          Kosten
'von Typ      nach Typ      norm min Zusatz
'Beispiel:
'[Beginn der Graphenbeschreibung]  Diese Zeile nicht Loeschen!
' 1,             5
' 1,  1,          2,  0,          2(2),  0
' 1,  1,          3,  0,          5(4),  100
' 2,  0,          3,  0,          4(2),  200
' 2,  0,          5,  5,          3(1),  50
' 3,  3,          4,  4,          7(6),  150
' 3,  3,          5,  5,          4(4),  450


'-- Datei beschreiben
Open WorkingDir + "\netzplan.grf" For Output As #5
Print #5, ""
Print #5, "[Beginn der Graphenbeschreibung]  Diese Zeile nicht Loeschen!"
Print #5, start; ",", ende
For i = 1 To Val(AnzahlKnoten)
    Print #5, U(i).do_i; ","; U(i).iTyp; ",", U(i).do_j; ","; U(i).jTyp; ",", U(i).dnorm; "("; U(i).dmin; "),"; U(i).w
Next i
Close #5


'-- E-Graph starten

'CurPath = CurDir    ' Get current path.
'Drive = Left(CurPath, 2)    ' Get current drive letter.
'NewDrive = Left(GRAPHPath, 2)    ' Get new drive letter.
'ChDrive NewDrive    ' Change drive.
'ChDir (GRAPHPath) ' Change directory
'x = Shell(GRAPHPath + "\e-graph.exe " + WorkingDir + "\netzplan.grf", 3)

' Drive + Directory nicht zurückwechseln, da SHELL asynchron

'*** neu ***
x = Shell(VisuProg + " " + WorkingDir + "\netzplan.grf", 3)


End Sub

Sub Knotenanalyse ()

'Insgesamt werden folgende Knotenarten ermittelt:
'
'start : bekommt Startknoten zugewiesen (nur auf linker Seite der Kanten)
'ende  : bekommt Endknoten zugewiesen   (nur auf rechter Seite der Kanten und dabei hochste Nr.)
'Knotentypen werden jeweils aus der Sicht der entsprechenden Kante gesehen, d.h. ein
'Knoten kann in bezug auf unterschiedliche Kanten auch von unterschiedlichem Typ sein!
'Knotentyp     0 = Hundsnormaler Knoten
'Knotentyp     3 = Entscheidungsknoten (EK)   (vor Scheinknoten - auf zwei Kanten!)
'Knotentyp     4 = Scheinknoten (nur auf rechter Seite der Kanten und nicht hochste Nr.)
'Knotentyp     5 = Endknoten (ENDK)
'Knotentyp     6 = Ruckkehrpunkt nach einer getroffenen Entscheidung  (hinter EK und <> Scheinknoten)
'Knotentyp     7 = Scheißknoten, resultiert aus erkannten Fehleingaben (etwa eines DAUs)


Dim i, j As Integer
Dim Schein As Variant
Dim StartTest As Variant


' Initialisieren und Scheißknoten suchen!
For i = 1 To Val(AnzahlKnoten)
    U(i).iTyp = 0
    U(i).jTyp = 0
    If U(i).dnorm = 0 Then
	U(i).iTyp = 7
	U(i).jTyp = 7
	U(i).do_i = -1
	U(i).do_j = -1
    End If
Next i


' Beginn Endknoten suchen
ende = 0
For i = 1 To Val(AnzahlKnoten)         'Endknoten suchen
    If U(i).do_j > ende Then ende = U(i).do_j
Next i
For i = 1 To Val(AnzahlKnoten)         'Endknoten Kennzeichen
    If U(i).do_j = ende Then U(i).jTyp = 5
Next i


'Beginn Weitere Klassifizierung der Knoten
'Suche nach knotenTypen
For i = 1 To Val(AnzahlKnoten)
 If U(i).iTyp <> 7 Then
    Schein = 1
    StartTest = 1
    For j = 1 To Val(AnzahlKnoten)
	If U(i).do_j = U(j).do_i Or U(i).do_j = ende Then Schein = 0
	If U(i).do_i = U(j).do_j Then StartTest = 0        ' Startknoten
    Next j
    If Schein = 1 Then
	U(i).jTyp = 4
	U(i).iTyp = 3
	j = 1
	While (U(i).do_i <> U(j).do_i Or i = j) 'Suche der 2. Gabel des EntschKnotens (<> Sackgasse)
	    j = j + 1
	Wend
	U(j).iTyp = 3                           'Entscheidungsknoten ermitteln
	If U(j).jTyp <> 5 Then U(j).jTyp = 6    'Rückkehrknoten ermitteln
    End If
    If StartTest = 1 Then start = U(i).do_i
 End If
Next i


' Ausgabe d. ermittelten Knotentypen zu Kontrollzwecken
Open WorkingDir + "\netz.ktn" For Output As #4
Print #4, "Ausg. d. ermittelten Knotentypen zu Kontrollzwecken"
Print #4, start, ende
Print #4, ""
For i = 1 To Val(AnzahlKnoten)
    Print #4, U(i).do_i; U(i).iTyp, U(i).do_j; U(i).jTyp, U(i).dnorm; U(i).dmin; U(i).w
Next i
Close #4


End Sub

Sub MOPS_Ergebnis_lesen ()

'Prozedur eingefügt, noch nicht realisiert. WS96/97


Dim i, j As Integer                         'Zähler
Dim Position, PosAlt As Integer             'Positionszeiger
Dim Ergebnis As String * 10000
Dim tmpString As Variant

Open WorkingDir + "\mops.out" For Input As #1
' Lesen eines Puffers in eine Zwischenvariable
Get #1, , Ergebnis
Close #1

' Gibt es keine Lösung ?
Position = InStr(Ergebnis, "SOLUTION: (OPTIMAL)")
If Position = 0 Then
    Fehler = 1
    Exit Sub
End If

'Zielfunktionswert suchen
Position = InStr(Ergebnis, "FUNCTIONAL")
If Position <> 0 Then
    tmpString = Mid(Ergebnis, Position + 11, 16)
    tmpString = Str$(Val(tmpString)) 'Umwandeln in Standard-Darstellung
    tmpString = Mid(tmpString, 2, Len(tmpString) - 1)'Entfernen des Vorzeichens
    Zielwert = tmpString
    PosAlt = Position + 27
End If

'Werte suchen und eintragen
For i = 1 To AnzVars
    tmpString = Trim(Varianten(i).Variable)
    Position = InStr(PosAlt, Ergebnis, tmpString) 'Variable suchen
    If Position <> 0 Then
	Laenge = Len(tmpString)
	tmpString = Mid(Ergebnis, Position + 12, 15) 'Zahl extrahieren
	
	'Entfernen des Tausender-Kommas (Umwandlung sonst nicht möglich)
	For j = 1 To Len(tmpString)
	    If Mid(tmpString, j, 1) = "," Then
		tmpString = Mid(tmpString, 1, j - 1) & Mid(tmpString, j + 1, Len(tmpString) - j + 1)
	    End If
	Next j
	
	tmpString = Str$(Val(tmpString)) 'Umwandeln in Standard-Darstellung
	tmpString = Mid(tmpString, 2, Len(tmpString) - 1)'Entfernen des Vorzeichens
	Varianten(i).Anzahl = tmpString
    End If    'Else nicht nötig, da Anzahl mit 0 vorbelegt
    PosAlt = Position + 27 'Erhöhen des Positionszeigers
Next i


End Sub

Sub MPS_Datei_schreiben ()

' Einige Teile wurden aus der Verschnittplanung uebernommen
' -> muessen also noch angepasst werden


Dim Row, Zeile As Integer       ' Indizierungsfunktion
Dim R_NAME As String            ' Aufbereiteter Name der Restriktion
Dim ZF_NAME As String          ' Name der Zielfunktion
Dim tmpString As Variant

Open WorkingDir + "\netzplan.mps" For Output As #1


'-- Titel des Modells
Print #1, Tab(1); "NAME"; Tab(15); "NETZPLAN" & Str$(Anz_Dimensionen)


' In ROWS werden die Namen der Restriktionen und der der Zielfunktion benannt
ZF_NAME = "ZIEL"

Print #1, "ROWS"
Print #1, Tab(2); "N"; Tab(5); ZF_NAME
For Row = 1 To AnzahlKnoten
    tmpString = Str$(Row)                           'umwandeln des Zählers
    tmpString = Mid(tmpString, 2, Len(tmpString) - 1) 'Vorzeichenstelle abschneiden
    R_NAME = "R" & tmpString
    Print #1, Tab(2); "E"; Tab(5); R_NAME           'schreibt eine ROWS-Zeile in Form: " E R1"; E=Equal, R1=Restriktion1
Next Row

' In COLUMNS folgen die Werte der A-Matrix samt Zielfunktion
Print #1, "COLUMNS"
For Zeile = 1 To AnzVars
    tmpString = Str$(Varianten(Zeile).Verschnitt)
    tmpString = Mid(tmpString, 2, Len(tmpString) - 1)   'Vorzeichenstelle abschneiden
    Varianten(Zeile).Verschnitt = tmpString
    Print #1, Tab(5); Mid(Varianten(Zeile).Variable, 1, 10); Tab(15); Mid(ZF_NAME, 1, 10); Tab(25); Varianten(Zeile).Verschnitt
    For Row = 1 To AnzahlKnoten
	tmpString = Str$(Row)                           'umwandeln des Zählers
	tmpString = Mid(tmpString, 2, Len(tmpString) - 1) 'Vorzeichenstelle abschneiden
	R_NAME = "R" & tmpString
	Print #1, Tab(5); Mid(Varianten(Zeile).Variable, 1, 10); Tab(15); Mid(R_NAME, 1, 10); Tab(25); Varianten(Zeile).Produkt(Row)
    Next Row
Next Zeile


' In RHS kommen Werte des B-Vektors

Print #1, "RHS"
For Row = 1 To AnzahlKnoten
    tmpString = Str$(Row)                               'umwandeln des Zählers
    tmpString = Mid(tmpString, 2, Len(tmpString) - 1)   'Vorzeichenstelle abschneiden
    R_NAME = "R" & tmpString
    Print #1, Tab(5); "MYRHS"; Tab(15); R_NAME; Tab(24); Str$(U(Row).w)
Next Row

' Endemarkierung
Print #1, "ENDATA"
Close #1

End Sub

Sub Solver_Batch_Datei_schreiben ()

' -> wird in den EXEPath geschrieben (alles: Eingabe u. Ausgabe)
'    keine Einträge in den Solverpfad

Open WorkingDir + "\solver.bat" For Output As #1

Print #1, "@echo off"

Select Case solver
   Case "XAeqs", "XAmps"
      Print #1, "REM Solver: XA"
      Print #1, "set Oldpath=%path%"
      Print #1, "path=" + XAPath + ";%path%"
      Print #1, "xa netz.clp"
      Print #1, "set path=%Oldpath%"
      Print #1, "set Oldpath="

   Case "MOPS"
      Print #1, "REM Solver: MOPS"
      Print #1, MOPSPath + "\mops_pc.exe netzplan.mps MIN"
						      
   Case "LPSOLVE"
      Print #1, "REM Solver: lp_solve"
      Print #1, LPSOLVEPath + "\lp_solve.exe -mps <netzplan.mps >lp_solve.out"
End Select

Close #1

End Sub

Sub Speichern_HD ()

Dim i As Integer

    screen.MousePointer = 11
    
    Open IO.StdDialog1.Filename For Output As #1
    Print #1, "&&#ne1";
    Print #1, AnzahlKnoten
    Print #1, KostTag$
    For i = 1 To Val(AnzahlKnoten)
	Print #1, U(i).do_i
	Print #1, U(i).do_j
	Print #1, U(i).dnorm
	Print #1, U(i).dmin
	Print #1, U(i).w
    Next i
    Close #1

    'akt_Datnam
    screen.MousePointer = 0

End Sub

Function SpeichernUnter ()

    If Right(dbpath, 1) = "\" Then
	IO.StdDialog1.InitDir = Left(dbpath, Len(dbpath) - 1)
    Else
	IO.StdDialog1.InitDir = dbpath
    End If
    IO.StdDialog1.Action = 2
    If IO.StdDialog1.Filename <> "" Then
	Speichern_HD
	akt_Datnam
    End If

End Function

Sub Steuerung ()
	
	CursorAnzeige
	If posx <= 5 Then
	   einsetzen posx, posy
	   DStr_ein posx, posy, Text
	   posy = posy
	Else
	   posx = 1
	   If posy = Val(AnzahlKnoten) Then
	      posy = 1
	       einsetzen posx, posy
	      DStr_ein posx, posy, Text
	   Else
	      posy = posy + 1
	      einsetzen posx, posy
	      DStr_ein posx, posy, Text
	   End If
	End If
	posx = posx + 1

End Sub

