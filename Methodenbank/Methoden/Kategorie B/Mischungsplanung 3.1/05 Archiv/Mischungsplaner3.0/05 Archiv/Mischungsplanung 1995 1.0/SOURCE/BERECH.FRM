VERSION 2.00
Begin Form Berechnung 
   Caption         =   "Berechnung"
   ClientHeight    =   1485
   ClientLeft      =   1260
   ClientTop       =   1665
   ClientWidth     =   5190
   Height          =   1890
   Left            =   1200
   LinkTopic       =   "Form1"
   ScaleHeight     =   1485
   ScaleWidth      =   5190
   Top             =   1320
   Width           =   5310
End
'Option Explicit

Sub Check_and_Wait ()

' Kontrolle des Solver-Aufrufs mittels SHELL
' und Verzögern des Programmlaufes, bis die
' SHELL abgearbeitet ist.

Dim warte, ret As Integer

If Err Then
   MsgBox ("Der Solver konnte nicht aufgerufen werden !")
Else
   ' Zur Beruhigung der ORler
   wartefenster.Show 0
   
   While getmoduleusage(x) > 0
       warte = DoEvents()
   Wend

   ' WAV-Datei abspielen
   If Klang = 1 Then
      ret = sndPlaySound(ByVal CStr(EXEPath & "\gear_dn.wav"), 1)
   End If
   
   Unload wartefenster
End If

End Sub

Sub CLP_Datei_schreiben ()
    
' Steuerungsdatei für XA (EQS- u. MPS-Format)


Open WorkingDir + "\mi.clp" For Output As #1

If solver = "XAeqs" Then
   Print #1, "MI.LP        OUTPUT xa.out"
Else
   Print #1, "MI.MPS       OUTPUT xa.out"

   ' Aussensteuerung der Minimierung bzw. Maximierung
   'If satz.MinMax = 1 Then
      Print #1, "            MAXIMIZE yes"
   'Else
   '   Print #1, "            MAXIMIZE no"
   'End If
End If
    
Print #1, "             PAGESIZE 24"
Print #1, "             LINESIZE 79"
Print #1, "             TMARGINS 0"
Print #1, "             BMARGINS 0"
Print #1, "             FIELDSIZE 11"
Print #1, "             DECIMALS 5"
Print #1, "             EUROPEAN NO"
Print #1, "             LMARGINS 0"
Print #1, "             COPIES 1"
Print #1, "             WAIT NO"
Print #1, "*"
Print #1, "             MUTE NO"
Print #1, "             LISTINPUT NO"
Print #1, "             WARNING YES"
Print #1, "             SOLUTION YES"
Print #1, "             CONSTRAINTS YES"
Print #1, "             COSTANALYSIS YES"
Print #1, "             MARGINANALYSIS YES"
Print #1, "             MATLIST NO"
Print #1, "             DEFAULTS NO"

Close #1

End Sub

Sub EQS_Datei_schreiben ()

' XA-Datei (LP-Format)

Dim i, j As Integer
Dim zahl As Long
Dim s, si, cZahl, rSeite As String


Open WorkingDir + "\mi.lp" For Output As #1

Print #1, "..TITLE"
Print #1, "  Die Mischung"


' ZF schreiben ----------------------------------------------------
Print #1, "..OBJECTIVE MAXIMIZE"


' Erlöse
s = ""
For i = 1 To zZeilen
    If z(i).erlöse = "" Then
       z(i).erlöse = "0"
    End If
	 
    s = "+ " + Trim(z(i).erlöse) + " ("
    si = ""
    For j = 1 To aZeilen
	si = si + " + X" + Trim(Str(j)) + Trim(Str(i))
    Next j
    
    'Führendes inneres Plus entfernen
    si = Mid(si, 4, Len(si) - 3)

    s = s + si + ")"

    '1.Zeile: führendes äußeres Plus entfernen
    If i = 1 Then
       s = Mid(s, 3, Len(s) - 2)
    End If

    'Zeile für Zeile schreiben
    s = "  " + s
    Print #1, s
    s = ""
Next i


' Kosten
s = ""
For i = 1 To aZeilen
    If a(i).kosten = "" Then
       a(i).kosten = "0"
    End If
	 
    s = "- " + Trim(a(i).kosten) + " ("
    si = ""
    For j = 1 To zZeilen
	si = si + " + X" + Trim(Str(i)) + Trim(Str(j))
    Next j
    
    'Führendes inneres Plus entfernen
    If Len(si) > 3 Then
       si = Mid(si, 4, Len(si) - 3)
    End If

    s = s + si + ")"

    'Zeile für Zeile schreiben
    s = "  " + s
    Print #1, s
    s = ""
Next i


' Bounds ----------------------------------------------------------
Print #1, "..BOUNDS"

For i = 1 To aZeilen
    For j = 1 To zZeilen
	Print #1, "  X" + Trim(Str(i)) + Trim(Str(j)) + " >= 0"
    Next j
Next i


' Contraints ------------------------------------------------------
Print #1, "..CONSTRAINTS"


Print #1, "" 'Leerzeile
Print #1, "  * -> verfuegbare Menge"


' verfügbare Menge 1
s = ""
For i = 1 To aZeilen
    For j = 1 To zZeilen
	s = s + " + X" + Trim(Str(i)) + Trim(Str(j))
    Next j
    
    'Führendes Plus entfernen
    If Len(s) > 3 Then
       s = Mid(s, 4, Len(s) - 3)
    End If

    'Zeile für Zeile schreiben
    If a(i).AMenge1 = "" Then
       a(i).AMenge1 = "0"
    End If
    
    s = "  " + s + " <= " + a(i).AMenge1
    Print #1, s
    s = ""

Next i


' verfügbare Menge 2
s = ""
For i = 1 To aZeilen
    For j = 1 To zZeilen
	s = s + " + X" + Trim(Str(i)) + Trim(Str(j))
    Next j
    
    'Führendes Plus entfernen
    If Len(s) > 3 Then
       s = Mid(s, 4, Len(s) - 3)
    End If

    'Zeile für Zeile schreiben
    If a(i).AMenge2 = "" Then
       a(i).AMenge2 = "0"
    End If
    
    s = "  " + s + " >= " + a(i).AMenge2
    Print #1, s
    s = ""
Next i


Print #1, "" 'Leerzeile
Print #1, "  * -> benoetige Menge"


' benötige Menge 1
s = ""
For i = 1 To zZeilen
    For j = 1 To aZeilen
	s = s + " + X" + Trim(Str(j)) + Trim(Str(i))
	'auf i und j achten
    Next j
    
    'Führendes Plus entfernen
    s = Mid(s, 4, Len(s) - 3)
    
    s = "  " + s + gröklei(z(i).ZMenge1)
    
    Print #1, s
    s = ""
Next i


' benötige Menge 2
s = ""
For i = 1 To zZeilen
    For j = 1 To aZeilen
	s = s + " + X" + Trim(Str(j)) + Trim(Str(i))
	'auf i und j achten
    Next j
    
    'Führendes Plus entfernen
    s = Mid(s, 4, Len(s) - 3)
    
    s = "  " + s + gröklei(z(i).ZMenge2)

    Print #1, s
    s = ""
Next i



'restliche Restriktionen
'es gibt jeweils noch 8 Spalten (D1,D2,D3,.., T1,T2,T3 usw.)
'Neu: AE1, AE2.. für Ausgangseigenschaft usw.
'
' -> den ganzen Spaß könnte man auch eleganter lösen, aber ich weiß
'    immer noch nicht, wie man Variablen-Makros in VB auflöst.

Print #1, "" 'Leerzeile
Print #1, "  * -> restliche Restriktionen"

      
'-- Spalte D1 und T1
Print #1, "" 'Leerzeile
s = ""
For i = 1 To zZeilen
    For j = 1 To aZeilen
	lSeite = a(j).D1
	rSeite = z(i).T1

	If lSeite = "" Then
	   lSeite = "0"
	End If
	
	If zZeilen = 1 Then
	   rSeiteStr = " " + rSeite
	   zahl = Val(lSeite)
	Else
	   rSeiteStr = eqsZeichen(rSeite) + "0"
	   zahl = Val(lSeite) - Val(nurCZahl(rSeite))  ' Auflösung der rechten Seite
	End If

	 cZahl = Trim(Str(zahl))
	s = s + " + " + cZahl + " X" + Trim(Str(j)) + Trim(Str(i))
    Next j
    
    s = Mid(s, 4, Len(s) - 3)    ' Führendes Plus entfernen
    s = "  " + s + rSeiteStr
    Print #1, s     ' Zeile ausgeben
    s = ""
Next i

'-- Spalte D2 und T2
If aZeilen < 2 GoTo EQS_Ende

Print #1, "" 'Leerzeile
s = ""
For i = 1 To zZeilen
    For j = 1 To aZeilen
	lSeite = a(j).D2
	rSeite = z(i).T2

	If lSeite = "" Then
	   lSeite = "0"
	End If
	
	If zZeilen = 1 Then
	   rSeiteStr = " " + rSeite
	   zahl = Val(lSeite)
	Else
	   rSeiteStr = eqsZeichen(rSeite) + "0"
	   zahl = Val(lSeite) - Val(nurCZahl(rSeite))  ' Auflösung der rechten Seite
	End If

	 cZahl = Trim(Str(zahl))
	s = s + " + " + cZahl + " X" + Trim(Str(j)) + Trim(Str(i))
    Next j
    
    s = Mid(s, 4, Len(s) - 3)    ' Führendes Plus entfernen
    s = "  " + s + rSeiteStr
    Print #1, s     ' Zeile ausgeben
    s = ""
Next i


'-- Spalte D3 und T3
If aZeilen < 3 GoTo EQS_Ende

Print #1, "" 'Leerzeile
s = ""
For i = 1 To zZeilen
    For j = 1 To aZeilen
	lSeite = a(j).D3
	rSeite = z(i).T3

	If lSeite = "" Then
	   lSeite = "0"
	End If
	
	If zZeilen = 1 Then
	   rSeiteStr = " " + rSeite
	   zahl = Val(lSeite)
	Else
	   rSeiteStr = eqsZeichen(rSeite) + "0"
	   zahl = Val(lSeite) - Val(nurCZahl(rSeite))  ' Auflösung der rechten Seite
	End If

	 cZahl = Trim(Str(zahl))
	s = s + " + " + cZahl + " X" + Trim(Str(j)) + Trim(Str(i))
    Next j
    
    s = Mid(s, 4, Len(s) - 3)    ' Führendes Plus entfernen
    s = "  " + s + rSeiteStr
    Print #1, s     ' Zeile ausgeben
    s = ""
Next i

'-- Spalte D4 und T4
If aZeilen < 4 GoTo EQS_Ende

Print #1, "" 'Leerzeile
s = ""
For i = 1 To zZeilen
    For j = 1 To aZeilen
	lSeite = a(j).D4
	rSeite = z(i).T4

	If lSeite = "" Then
	   lSeite = "0"
	End If
	
	If zZeilen = 1 Then
	   rSeiteStr = rSeite
	   zahl = Val(lSeite)
	Else
	   rSeiteStr = eqsZeichen(rSeite) + "0"
	   zahl = Val(lSeite) - Val(nurCZahl(rSeite))  ' Auflösung der rechten Seite
	End If

	 cZahl = Trim(Str(zahl))
	s = s + " + " + cZahl + " X" + Trim(Str(j)) + Trim(Str(i))
    Next j
    
    s = Mid(s, 4, Len(s) - 3)    ' Führendes Plus entfernen
    s = "  " + s + rSeiteStr
    Print #1, s     ' Zeile ausgeben
    s = ""
Next i

'-- Spalte D5 und T5
If aZeilen < 5 GoTo EQS_Ende

Print #1, "" 'Leerzeile
s = ""
For i = 1 To zZeilen
    For j = 1 To aZeilen
	lSeite = a(j).D5
	rSeite = z(i).T5

	If lSeite = "" Then
	   lSeite = "0"
	End If
	
	If zZeilen = 1 Then
	   rSeiteStr = rSeite
	   zahl = Val(lSeite)
	Else
	   rSeiteStr = eqsZeichen(rSeite) + "0"
	   zahl = Val(lSeite) - Val(nurCZahl(rSeite))  ' Auflösung der rechten Seite
	End If

	 cZahl = Trim(Str(zahl))
	s = s + " + " + cZahl + " X" + Trim(Str(j)) + Trim(Str(i))
    Next j
    
    s = Mid(s, 4, Len(s) - 3)    ' Führendes Plus entfernen
    s = "  " + s + rSeiteStr
    Print #1, s     ' Zeile ausgeben
    s = ""
Next i

'-- Spalte D6 und T6
If aZeilen < 6 GoTo EQS_Ende

Print #1, "" 'Leerzeile
s = ""
For i = 1 To zZeilen
    For j = 1 To aZeilen
	lSeite = a(j).D6
	rSeite = z(i).T6

	If lSeite = "" Then
	   lSeite = "0"
	End If
	
	If zZeilen = 1 Then
	   rSeiteStr = rSeite
	   zahl = Val(lSeite)
	Else
	   rSeiteStr = eqsZeichen(rSeite) + "0"
	   zahl = Val(lSeite) - Val(nurCZahl(rSeite))  ' Auflösung der rechten Seite
	End If

	 cZahl = Trim(Str(zahl))
	s = s + " + " + cZahl + " X" + Trim(Str(j)) + Trim(Str(i))
    Next j
    
    s = Mid(s, 4, Len(s) - 3)    ' Führendes Plus entfernen
    s = "  " + s + rSeiteStr
    Print #1, s     ' Zeile ausgeben
    s = ""
Next i

'-- Spalte D7 und T7
If aZeilen < 7 GoTo EQS_Ende

Print #1, "" 'Leerzeile
s = ""
For i = 1 To zZeilen
    For j = 1 To aZeilen
	lSeite = a(j).D7
	rSeite = z(i).T7

	If lSeite = "" Then
	   lSeite = "0"
	End If
	
	If zZeilen = 1 Then
	   rSeiteStr = rSeite
	   zahl = Val(lSeite)
	Else
	   rSeiteStr = eqsZeichen(rSeite) + "0"
	   zahl = Val(lSeite) - Val(nurCZahl(rSeite))  ' Auflösung der rechten Seite
	End If

	 cZahl = Trim(Str(zahl))
	s = s + " + " + cZahl + " X" + Trim(Str(j)) + Trim(Str(i))
    Next j
    
    s = Mid(s, 4, Len(s) - 3)    ' Führendes Plus entfernen
    s = "  " + s + rSeiteStr
    Print #1, s     ' Zeile ausgeben
    s = ""
Next i

'-- Spalte D8 und T8
If aZeilen < 8 GoTo EQS_Ende

Print #1, "" 'Leerzeile
s = ""
For i = 1 To zZeilen
    For j = 1 To aZeilen
	lSeite = a(j).D8
	rSeite = z(i).T8

	If lSeite = "" Then
	   lSeite = "0"
	End If
	
	If zZeilen = 1 Then
	   rSeiteStr = rSeite
	   zahl = Val(lSeite)
	Else
	   rSeiteStr = eqsZeichen(rSeite) + "0"
	   zahl = Val(lSeite) - Val(nurCZahl(rSeite))  ' Auflösung der rechten Seite
	End If

	 cZahl = Trim(Str(zahl))
	s = s + " + " + cZahl + " X" + Trim(Str(j)) + Trim(Str(i))
    Next j
    
    s = Mid(s, 4, Len(s) - 3)    ' Führendes Plus entfernen
    s = "  " + s + rSeiteStr
    Print #1, s     ' Zeile ausgeben
    s = ""
Next i

EQS_Ende:

Close #1

End Sub

Function eqsZeichen (ByVal arg) As String

If InStr(arg, ">=") > 0 Then eqsZeichen = " >= ": GoTo eqsz_ende
If InStr(arg, "<=") > 0 Then eqsZeichen = " <= ": GoTo eqsz_ende
If InStr(arg, ">") > 0 Then eqsZeichen = " > ": GoTo eqsz_ende
If InStr(arg, "<") > 0 Then eqsZeichen = " < ": GoTo eqsz_ende
If InStr(arg, "=") > 0 Then eqsZeichen = " = ": GoTo eqsz_ende

eqsZeichen = " >= "

eqsz_ende:
     'eqsZeichen = zeichen



End Function



Sub Form_Load ()
    
' dieser ganze Quark (Load-Ereignis ist sowieso unnötig) würde
' besser in einem Modul untergebracht werden.
' ..Erkenntnis des Autors am nächsten Morgen !

Dim warte As Integer

screen.MousePointer = 11
Unload frmAusgabe     ' falls vorhanden
'unload andere Ausgabefenster

' Einträge der Pif-Datei aktualisieren, und anschließend in
' das Arbeitsverzeichnis kopieren.
Pif_Eintrag "solver.pif", "solver.bat"     ' in mi_glob.bas

' WAV-Datei abspielen
If Klang = 1 Then
   ret = sndPlaySound(ByVal CStr(EXEPath & "\prop.wav"), 1)
End If
   
   

Select Case solver

       Case "XAeqs"
	  
	  Solver_Batch_Datei_schreiben
	  CLP_Datei_schreiben
	  EQS_Datei_schreiben   'LP-Datei

	  If Dir(WorkingDir + "\xa.out") <> "" Then
	     Kill WorkingDir + "\xa.out"
	  End If

	  '-- Berechnungslauf -> 8! Aufruf der PIF
	  x = Shell(WorkingDir + "\solver.pif")
	  Check_and_Wait
	  
	  frmAusgabe.Show 0
		   
       Case "XAmps"
	  
	  Solver_Batch_Datei_schreiben
	  CLP_Datei_schreiben
	  MPS_Datei_schreiben
	  
	  If Dir(WorkingDir + "\xa.out") <> "" Then
	     Kill WorkingDir + "\xa.out"
	  End If
	    
	  '-- Berechnungslauf
	  x = Shell(WorkingDir + "\solver.pif")
	  Check_and_Wait
	  
	  frmAusgabe.Show 0
		   
       Case "MOPS"
	  
	  Solver_Batch_Datei_schreiben
	  MPS_Datei_schreiben
	  
	  If Dir(WorkingDir + "\mops.out") <> "" Then
	     Kill WorkingDir + "\mops.out"
	  End If

	  '-- Berechnungslauf
	  x = Shell(WorkingDir + "\solver.pif")
	  Check_and_Wait
	  
	  If Dir(WorkingDir + "\mops.out") = "" Then
	     MsgBox "Die Berechnungsdatei von MOPS konnte nicht gefunden werden.", 48, "MOPS.OUT"
	  Else
	     x = Shell("notepad " + WorkingDir + "\mops.out", 1)
	     While getmoduleusage(x) > 0
		 warte = DoEvents()
	     Wend
	  End If
	  
       Case "STRADA"
	  
	  Solver_Batch_Datei_schreiben
	  MPS_Datei_schreiben
	  
	  If Dir(WorkingDir + "\strada.out") <> "" Then
	     Kill WorkingDir + "\strada.out"
	  End If
	    
	  '-- Berechnungslauf
	  x = Shell(WorkingDir + "\solver.pif")
	  Check_and_Wait
	  
	  frmAusgabe.Show 0
		   
	  
       Case "NN"
	  
	  'NN_Batch_Datei_schreiben
	  MPS_Datei_schreiben
	  
	  'If Dir(NNPath + "\nn.out") <> "" Then
	  '   Kill NNPath + "\nn.out"
	  'End If

	  '-- Berechnungslauf
	  x = Shell(WorkingDir + "\solver.pif")
	  Check_and_Wait
	  
	  'x = Shell("notepad " + WorkingDir + "\nn.out", 1)
	  
       Case Else

	  MsgBox "Noch nicht implementiert !"

End Select
    
screen.MousePointer = 0

End Sub

Function gröklei (ByVal arg)

    'nix oder was
    If arg = "" Then
       arg = "0"
    End If
    
    If InStr(arg, "<=") > 0 Then
       arg = " " + arg
       GoTo ENDE
    End If
    
    If InStr(arg, "<") > 0 Then
       arg = " " + arg
       GoTo ENDE
    End If

    If InStr(arg, ">=") > 0 Then
       arg = " " + arg
       GoTo ENDE
    End If

    If InStr(arg, ">") > 0 Then
       arg = " " + arg
       GoTo ENDE
    End If

    If InStr(arg, "=") > 0 Then
       arg = " " + arg
       GoTo ENDE
    End If

    'default, d.h. wenn gar nichts hilft !
    arg = " >=" + arg
    GoTo ENDE:

ENDE:
    gröklei = arg

End Function

Sub MPS_Datei_schreiben ()

' Beachten Sie, dass in der Sektion COLUMNS gleiche Variablen
' aufeinanderfolgen muessen.
'
' >>> Achtung !
' Leider weist die Mps-Aufbereitung noch Fehler auf. Das "Verrechnen"
' beider Gleichungsseiten bei den Restriktionen KANN zu Fehlern führen.

			   
Dim i, j, rows, pos As Integer
Dim s, zeichen, c1, c2 As String
Dim zfwert As Double
Dim czfwert As String


Open WorkingDir + "\mi.mps" For Output As #2

Print #2, "NAME          Mischungsproblem"


' ROWS schreiben --------------------------------------------------
Print #2, "ROWS"
rows = 0

' ZF
Print #2, " N  ZF"

' Restrikt. -> VM1
For i = 1 To aZeilen
    rows = rows + 1
    
    'zeichen = mpsZeichen(A(i).AMenge1)
    ' da verfügb. Menge nur mit lower vernünftige Ergebnisse liefert
    zeichen = "L"
    Print #2, " " & zeichen & "  VM1_" & Trim(Str(i))
Next i

' Restrikt. -> VM2
For i = 1 To aZeilen
    rows = rows + 1
    
    'zeichen = mpsZeichen(A(i).AMenge)
    ' hier greater für Untergrenze    ...neu seit 02.01.96
    zeichen = "G"
    Print #2, " " & zeichen & "  VM2_" & Trim(Str(i))
Next i

' Restrikt. -> BM1
For i = 1 To zZeilen
    rows = rows + 1

    zeichen = mpsZeichen(z(i).ZMenge1)
    Print #2, " " & zeichen & "  BM1_" & Trim(Str(i))
Next i

' Restrikt. -> BM2
For i = 1 To zZeilen
    rows = rows + 1

    zeichen = mpsZeichen(z(i).ZMenge2)
    Print #2, " " & zeichen & "  BM2_" & Trim(Str(i))
Next i

' Restrikt. -> D1 und T1
For i = 1 To zZeilen
    rows = rows + 1

    zeichen = mpsZeichen(z(i).T1)
    Print #2, " " & zeichen & "  D1T1_" & Trim(Str(i))
Next i

' Restrikt. -> D2 und T2
For i = 1 To zZeilen
    rows = rows + 1

    zeichen = mpsZeichen(z(i).T2)
    Print #2, " " & zeichen & "  D2T2_" & Trim(Str(i))
Next i

' Restrikt. -> D3 und T3
For i = 1 To zZeilen
    rows = rows + 1

    zeichen = mpsZeichen(z(i).T3)
    Print #2, " " & zeichen & "  D3T3_" & Trim(Str(i))
Next i

' Restrikt. -> D4 und T4
For i = 1 To zZeilen
    rows = rows + 1

    zeichen = mpsZeichen(z(i).T4)
    Print #2, " " & zeichen & "  D4T4_" & Trim(Str(i))
Next i

' Restrikt. -> D5 und T5
For i = 1 To zZeilen
    rows = rows + 1

    zeichen = mpsZeichen(z(i).T5)
    Print #2, " " & zeichen & "  D5T5_" & Trim(Str(i))
Next i

' Restrikt. -> D6 und T6
For i = 1 To zZeilen
    rows = rows + 1

    zeichen = mpsZeichen(z(i).T6)
    Print #2, " " & zeichen & "  D6T6_" & Trim(Str(i))
Next i

' Restrikt. -> D7 und T7
For i = 1 To zZeilen
    rows = rows + 1

    zeichen = mpsZeichen(z(i).T7)
    Print #2, " " & zeichen & "  D7T7_" & Trim(Str(i))
Next i

' Restrikt. -> D8 und T8
For i = 1 To zZeilen
    rows = rows + 1

    zeichen = mpsZeichen(z(i).T8)
    Print #2, " " & zeichen & "  D8T8_" & Trim(Str(i))
Next i



' COLUMS schreiben-------------------------------------------------
Print #2, "COLUMNS"

For i = 1 To aZeilen

    For j = 1 To zZeilen

	' ZF -Erlöse - ZF - Kosten
	s = "    X" + Trim(Str(i)) + Trim(Str(j)) + "                    "
	s = Mid(s, 1, 14) + "ZF                   "
	
	c1 = z(j).erlöse
	c2 = a(i).kosten

	pos = InStr(c1, ".")

	If pos > 0 Then Mid(c1, pos, 1) = ","
	pos = InStr(c2, ".")
	If pos > 0 Then Mid(c2, pos, 1) = ","
	zfwert = CDbl(c1) - CDbl(c2)

	czfwert = Str(zfwert)
	pos = InStr(czfwert, ",")
	If pos > 0 Then Mid(czfwert, pos, 1) = "."
	
	s = Mid(s, 1, 24) + Trim(czfwert)
					     
	Print #2, s
    
	
	' Columns VM1
	s = "    X" + Trim(Str(i)) + Trim(Str(j)) + "                    "
	s = Mid(s, 1, 14) + "VM1_" + Trim(Str(i)) + "                    "
	s = Mid(s, 1, 24) + "1.0"
					     
	Print #2, s
	

	' Columns VM2
	s = "    X" + Trim(Str(i)) + Trim(Str(j)) + "                    "
	s = Mid(s, 1, 14) + "VM2_" + Trim(Str(i)) + "                    "
	s = Mid(s, 1, 24) + "1.0"
					     
	Print #2, s
	

	' Columns BM1
	s = "    X" + Trim(Str(i)) + Trim(Str(j)) + "                    "
	s = Mid(s, 1, 14) + "BM1_" + Trim(Str(j)) + "                    "
	s = Mid(s, 1, 24) + "1.0"
					     
	Print #2, s
    

	' Columns BM2
	s = "    X" + Trim(Str(i)) + Trim(Str(j)) + "                    "
	s = Mid(s, 1, 14) + "BM2_" + Trim(Str(j)) + "                    "
	s = Mid(s, 1, 24) + "1.0"
					     
	Print #2, s
    
	
	' Columns D1T1
	s = "    X" + Trim(Str(i)) + Trim(Str(j)) + "                    "
	s = Mid(s, 1, 14) + "D1T1_" + Trim(Str(j)) + "                    "
	zahl1 = Val(nurCZahl(a(i).D1))
	zahl2 = Val(nurCZahl(z(j).T1))
	zahl = Trim(Str(zahl1 - zahl2))
	s = Mid(s, 1, 24) + zahl
					     
	If Val(zahl) <> 0 Then Print #2, s
    

	' Columns D2T2
	s = "    X" + Trim(Str(i)) + Trim(Str(j)) + "                    "
	s = Mid(s, 1, 14) + "D2T2_" + Trim(Str(j)) + "                    "
	zahl1 = Val(nurCZahl(a(i).D2))
	zahl2 = Val(nurCZahl(z(j).T2))
	zahl = Trim(Str(zahl1 - zahl2))
	s = Mid(s, 1, 24) + zahl
					
	If Val(zahl) <> 0 Then Print #2, s
    

	' Columns D3T3
	s = "    X" + Trim(Str(i)) + Trim(Str(j)) + "                    "
	s = Mid(s, 1, 14) + "D3T3_" + Trim(Str(j)) + "                    "
	zahl1 = Val(nurCZahl(a(i).D3))
	zahl2 = Val(nurCZahl(z(j).T3))
	zahl = Trim(Str(zahl1 - zahl2))
	s = Mid(s, 1, 24) + zahl

	If Val(zahl) <> 0 Then Print #2, s


	' Columns D4T4
	s = "    X" + Trim(Str(i)) + Trim(Str(j)) + "                    "
	s = Mid(s, 1, 14) + "D4T4_" + Trim(Str(j)) + "                    "
	zahl1 = Val(nurCZahl(a(i).D4))
	zahl2 = Val(nurCZahl(z(j).T4))
	zahl = Trim(Str(zahl1 - zahl2))
	s = Mid(s, 1, 24) + zahl
					
	If Val(zahl) <> 0 Then Print #2, s


	' Columns D5T5
	s = "    X" + Trim(Str(i)) + Trim(Str(j)) + "                    "
	s = Mid(s, 1, 14) + "D5T5_" + Trim(Str(j)) + "                    "
	zahl1 = Val(nurCZahl(a(i).D5))
	zahl2 = Val(nurCZahl(z(j).T5))
	zahl = Trim(Str(zahl1 - zahl2))
	s = Mid(s, 1, 24) + zahl
					
	If Val(zahl) <> 0 Then Print #2, s


	' Columns D6T6
	s = "    X" + Trim(Str(i)) + Trim(Str(j)) + "                    "
	s = Mid(s, 1, 14) + "D6T6_" + Trim(Str(j)) + "                    "
	zahl1 = Val(nurCZahl(a(i).D6))
	zahl2 = Val(nurCZahl(z(j).T6))
	zahl = Trim(Str(zahl1 - zahl2))
	s = Mid(s, 1, 24) + zahl
					
	If Val(zahl) <> 0 Then Print #2, s


	' Columns D7T7
	s = "    X" + Trim(Str(i)) + Trim(Str(j)) + "                    "
	s = Mid(s, 1, 14) + "D7T7_" + Trim(Str(j)) + "                    "
	zahl1 = Val(nurCZahl(a(i).D7))
	zahl2 = Val(nurCZahl(z(j).T7))
	zahl = Trim(Str(zahl1 - zahl2))
	s = Mid(s, 1, 24) + zahl
					
	If Val(zahl) <> 0 Then Print #2, s


	' Columns D8T8
	s = "    X" + Trim(Str(i)) + Trim(Str(j)) + "                    "
	s = Mid(s, 1, 14) + "D8T8_" + Trim(Str(j)) + "                    "
	zahl1 = Val(nurCZahl(a(i).D8))
	zahl2 = Val(nurCZahl(z(j).T8))
	zahl = Trim(Str(zahl1 - zahl2))
	s = Mid(s, 1, 24) + zahl
					
	If Val(zahl) <> 0 Then Print #2, s

    Next j

Next i



' RHS schreiben --------------------------------------------------
Print #2, "RHS"


' RHS VM1
For i = 1 To aZeilen
    s = "    MYRHS                    "
    s = Mid(s, 1, 14) + "VM1_" + Trim(Str(i)) + "                    "
    zahl = nurCZahl(a(i).AMenge1)
    s = Mid(s, 1, 24) + zahl
					     
    If Val(zahl) <> 0 Then Print #2, s
Next i

' RHS VM2
For i = 1 To aZeilen
    s = "    MYRHS                    "
    s = Mid(s, 1, 14) + "VM2_" + Trim(Str(i)) + "                    "
    zahl = nurCZahl(a(i).AMenge2)
    s = Mid(s, 1, 24) + zahl
					     
    If Val(zahl) <> 0 Then Print #2, s
Next i

' RHS BM1
For i = 1 To zZeilen
    s = "    MYRHS                    "
    s = Mid(s, 1, 14) + "BM1_" + Trim(Str(i)) + "                    "
    zahl = nurCZahl(z(i).ZMenge1)
    s = Mid(s, 1, 24) + zahl
    
    If Val(zahl) <> 0 Then Print #2, s
Next i

' RHS BM2
For i = 1 To zZeilen
    s = "    MYRHS                    "
    s = Mid(s, 1, 14) + "BM2_" + Trim(Str(i)) + "                    "
    zahl = nurCZahl(z(i).ZMenge2)
    s = Mid(s, 1, 24) + zahl
					     
    If Val(zahl) <> 0 Then Print #2, s
Next i


' RHS D1T1
For i = 1 To zZeilen
    s = "    MYRHS                    "
    s = Mid(s, 1, 14) + "D1T1_" + Trim(Str(i)) + "                    "
    zahl = nurCZahl(z(i).T1)
    s = Mid(s, 1, 24) + zahl
					     
    '''If Val(zahl) <> 0 Then Print #2, s
Next i

' RHS D2T2
For i = 1 To zZeilen
    s = "    MYRHS                    "
    s = Mid(s, 1, 14) + "D2T2_" + Trim(Str(i)) + "                    "
    zahl = nurCZahl(z(i).T2)
    s = Mid(s, 1, 24) + zahl
					     
    '''If Val(zahl) <> 0 Then Print #2, s
Next i

' RHS D3T3
For i = 1 To zZeilen
    s = "    MYRHS                    "
    s = Mid(s, 1, 14) + "D3T3_" + Trim(Str(i)) + "                    "
    zahl = nurCZahl(z(i).T3)
    s = Mid(s, 1, 24) + zahl
					     
    '''If Val(zahl) <> 0 Then Print #2, s
Next i

' RHS D4T4
For i = 1 To zZeilen
    s = "    MYRHS                    "
    s = Mid(s, 1, 14) + "D4T4_" + Trim(Str(i)) + "                    "
    zahl = nurCZahl(z(i).T4)
    s = Mid(s, 1, 24) + zahl
					     
    '''If Val(zahl) <> 0 Then Print #2, s
Next i

' RHS D5T5
For i = 1 To zZeilen
    s = "    MYRHS                    "
    s = Mid(s, 1, 14) + "D5T5_" + Trim(Str(i)) + "                    "
    zahl = nurCZahl(z(i).T5)
    s = Mid(s, 1, 24) + zahl
					     
    '''If Val(zahl) <> 0 Then Print #2, s
Next i

' RHS D6T6
For i = 1 To zZeilen
    s = "    MYRHS                    "
    s = Mid(s, 1, 14) + "D6T6_" + Trim(Str(i)) + "                    "
    zahl = nurCZahl(z(i).T6)
    s = Mid(s, 1, 24) + zahl
					     
    '''If Val(zahl) <> 0 Then Print #2, s
Next i

' RHS D7T7
For i = 1 To zZeilen
    s = "    MYRHS                    "
    s = Mid(s, 1, 14) + "D7T7_" + Trim(Str(i)) + "                    "
    zahl = nurCZahl(z(i).T7)
    s = Mid(s, 1, 24) + zahl
					     
    '''If Val(zahl) <> 0 Then Print #2, s
Next i

' RHS D8T8
For i = 1 To zZeilen
    s = "    MYRHS                    "
    s = Mid(s, 1, 14) + "D8T8_" + Trim(Str(i)) + "                    "
    zahl = nurCZahl(z(i).T8)
    s = Mid(s, 1, 24) + zahl
					     
    '''If Val(zahl) <> 0 Then Print #2, s
Next i



' BOUNDS schreiben -----------------------------------------------
'Print #2, "BOUNDS"



' ENDATA schreiben -----------------------------------------------
Print #2, "ENDATA"

		 

Close #2

End Sub

Function mpsZeichen (ByVal arg)

If InStr(arg, ">") > 0 Then zeichen = "G": GoTo mpsz_ende
If InStr(arg, "<") > 0 Then zeichen = "L": GoTo mpsz_ende
If InStr(arg, "=") > 0 Then zeichen = "E": GoTo mpsz_ende

zeichen = "G": GoTo mpsz_ende

mpsz_ende:
     mpsZeichen = zeichen

End Function

Function nurCZahl (ByVal arg) As String
		    
pos = InStr(arg, "=")
If pos > 0 Then Mid(arg, pos, 1) = " "

pos = InStr(arg, "<")
If pos > 0 Then Mid(arg, pos, 1) = " "

pos = InStr(arg, ">")
If pos > 0 Then Mid(arg, pos, 1) = " "
		    
nurCZahl = Trim(arg)

End Function

Sub Solver_Batch_Datei_schreiben ()

Open WorkingDir + "\solver.bat" For Output As #1

Select Case solver

   Case "XAeqs", "XAmps"
      Print #1, "@echo off"
      Print #1, "REM Solver: XA"
      Print #1, "set Oldpath=%path%"
      Print #1, "path " + XAPath + ";%path%"
      Print #1, "xa mi.clp"
      Print #1, "path %Oldpath%"
      Print #1, "set Oldpath="

   Case "MOPS"
      Print #1, "@echo off"
      Print #1, "REM Solver: MOPS"
      Print #1, MOPSPath + "\mops_pc.exe mi.mps MAX"
   
   Case "STRADA"
      Print #1, "@echo off"
      Print #1, "REM Solver: STRADA"
      Print #1, STRADAPath + "\strada.exe mi.mps MAX strada.out solve"

End Select

Close #1

End Sub

