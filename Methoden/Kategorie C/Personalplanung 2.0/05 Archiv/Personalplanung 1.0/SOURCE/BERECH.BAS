Sub Berechnung ()
    
    ' >>> wird von Solver.Frm  (Button Berechnen) aufgerufen

    'ShellErr = 0
    
    ' Pifdatei: Einträge + Kopieren in den WorkingDir
    Pif_Eintrag "solver.pif", "solver.bat"     ' global.bas


    Select Case SolverName
        
        Case "XAeqs"
            Solver_Batch_Datei_schreiben
            
            CLP_Datei_schreiben
            EQS_Datei_schreiben
            
            If Dir(WorkingDir + "\xa.out") <> "" Then
                Kill WorkingDir + "\xa.out"
            End If
            
            x = Shell(WorkingDir + "\solver.pif") ' Berechnungslauf
            Check_and_Wait
        
        Case "XAmps"
            Solver_Batch_Datei_schreiben
            
            CLP_Datei_schreiben
            MPS_Datei_schreiben
            
            If Dir(WorkingDir + "\xa.out") <> "" Then
                Kill WorkingDir + "\xa.out"
            End If
            
            x = Shell(WorkingDir + "\solver.pif") ' Berechnungslauf
            Check_and_Wait
        
        Case "MOPS"
            Solver_Batch_Datei_schreiben
            
            MPS_Datei_schreiben
            
            If Dir(WorkingDir + "\mops.out") <> "" Then
                Kill WorkingDir + "\mops.out"
            End If
            
            x = Shell(WorkingDir + "\solver.pif") ' Berechnungslauf
            Check_and_Wait
        
        Case "LPSOLVE"
            Solver_Batch_Datei_schreiben
            
            MPS_Datei_schreiben
            
            If Dir(WorkingDir + "\lp_solve.out") <> "" Then
                Kill WorkingDir + "\lp_solve.out"
            End If
            
            x = Shell(WorkingDir + "\solver.pif")
            Check_and_Wait
        
        Case Else ' übrige Solver (OMP/NN/..)
            MsgBox "Der Solver " + SolverName + " ist noch nicht implementiert.", 0, "Hinweis"
            Fehler = 1

    End Select
End Sub

Sub Check_and_Wait ()

' Kontrolle des Solver-Aufrufs mittels SHELL
' und Verzögern des Programmlaufes, bis die
' SHELL abgearbeitet ist.

If Err Then
   MsgBox ("Der Solver " + SolverName + " konnte nicht aufgerufen werden !")
Else
    ' Zur Beruhigung der ORler
    'Wartefenster.Show 1   -> gehr hier nicht !!!!!!
    
    While getmoduleusage(x) > 0
        warte = DoEvents()
    Wend
                               
    Unload Wartefenster
End If

End Sub

Sub CLP_Datei_schreiben ()

Dim File_Erweiterung As String


Fehler = 0

' Der Aufruf dieser Funktion macht nur für XAeqs und XAmps Sinn
If SolverName = "XAeqs" Then
    File_Erweiterung = "LP"
Else
    If SolverName = "XAmps" Then
        File_Erweiterung = "MPS"
    Else
        MsgBox "Es wurde SUB CLP_Datei_schreiben im falschen Kontext augerufen.", 0, "Hinweis"
        Fehler = 1
        Exit Sub
    End If
End If

Open WorkingDir + "\persplan.clp" For Output As #1
Print #1, "persplan." + File_Erweiterung + " LISTINPUT NO"
Print #1, "             OUTPUT xa.out"
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

' aus Modul xa.bas einkopiert


Dim i, j As Integer


Open WorkingDir + "\persplan.lp" For Output As #1

'Titel schreiben
Print #1, "..TITLE"
Print #1, "  Personalplanung"

' Zielfunktion schreiben
Print #1, "..OBJECTIVE MINIMIZE"
s = getXAeqs_Zielfunktion()      'matrix.bas
Print #1, s


'Nicht-Negativitäts-Bedingungen setzen
Print #1, "..BOUNDS"
    i = 1
    Do
        s = get_next_Bound()       'matrix.bas
        If s = "" Then Exit Do
        Print #1, "Z" + Format$(i, "000"); ": "; s
        i = i + 1
    Loop

   
'Restriktionen schreiben
Print #1, "..CONSTRAINTS"
    Do
        s = get_next_Constraint() 'string  matrix.bas
        If s = "" Then Exit Do
        Print #1, "Z" + Format$(i, "000"); ": "; s
        i = i + 1
    Loop


'Datei schließen
Close #1

End Sub

Sub MPS_Datei_schreiben ()

Dim i, j, spalte, zeile As Integer
Dim tmpstr              As String


'>>> Test: Ausgabe der Matrix in eine Datei
'>>> Th. Romer 01/97
'
'Open "c:\temp\matrix.txt" For Output As #2
'
'For i = 1 To MAXZEIL
'    tmpstr = ""
'    For j = 1 To MAXSPALT
'        tmpstr = tmpstr + Format$(matrix(j, i), "00")
'    Next
'    Print #2, tmpstr
'Next
'
'Close #2
'
'Exit Sub


Open WorkingDir + "\persplan.mps" For Output As #1


'---------------------------------------------------------
' Titel des Modells
Print #1, "NAME        PERSONALPLANUNG"


'---------------------------------------------------------
' Zielfunktion
Print #1, "ROWS"
Print #1, " N  ZF"


'---------------------------------------------------------
' Restriktionen
For zeile = 1 To MAXZEIL
    Select Case matrix(MAXSPALT - 1, zeile)
        Case 1
           tmpstr = " L  "
        Case 2
           tmpstr = " E  "
        Case 3
           tmpstr = " G  "
        Case Else
           tmpstr = "nix"
    End Select
    
    ' Abbrechen des Spasses: wenn Operator UND b-Vektor Null
    If (matrix(MAXSPALT - 1, zeile) > 0 And matrix(MAXSPALT, zeile) > 0) Then
       Print #1, tmpstr + "R" + Format$(zeile, "000")
    End If
Next


'---------------------------------------------------------
' in COLUMNS folgen die Werte der Matrix samt Zielfunktionswerten
' ..also spaltenweise

Print #1, "COLUMNS"

Dim s As tagSCHICHT

' ZF und Schichten einer Spalte
For spalte = 1 To MAXSCHICHT - 2
    
    ' Zielfunktion:     ..noch ohne Pausenbereich
    If get_Schicht(spalte, s) = True Then
       If s.nr <> 0 Then
          tmpstr = "    S" + Format$(spalte, "000") + "      ZF"
          tmpstr = tmpstr + "        " + Format$(Fix(s.prev))
          Print #1, tmpstr
       End If
    End If
    
    ' Variablenwerte einer Spalte
    For zeile = 1 To MAXZEIL
        If matrix(spalte, zeile) = 1 Then
           tmpstr = "    S" + Format$(spalte, "000") + "      R"
           tmpstr = tmpstr + Format$(zeile, "000")
           tmpstr = tmpstr + "     " + Str(matrix(spalte, zeile))
    
           ' Abbruch wenn Operator (=,<,>) ODER b-Vektor Null sind
           If matrix(MAXSPALT - 1, zeile) = 0 Or matrix(MAXSPALT, zeile) = 0 Then
              Exit For
           End If

           Print #1, tmpstr
        End If
    Next
Next

' Pausenwerte einer Spalte
For spalte = MAXSCHICHT + 1 To MAXSCHICHT + MAXINTERVALL
    For zeile = 1 To MAXZEIL
        If matrix(spalte, zeile) = -1 Then
           tmpstr = "    P" + Format$(spalte - MAXSCHICHT, "000")
           tmpstr = tmpstr + "      R" + Format$(zeile, "000")
           tmpstr = tmpstr + "     " + Str(matrix(spalte, zeile))
    
           ' Abbruch wenn kein Vergleichszeichen (=,<,>) oder
           ' b-Vektor = 0
           If matrix(MAXSPALT - 1, zeile) = 0 Or matrix(MAXSPALT, zeile) = 0 Then
              Exit For
           End If

           Print #1, tmpstr
        End If
    Next
Next


'---------------------------------------------------------
' In RHS kommen die Werte des B-Vektors

Print #1, "RHS"

For zeile = 1 To MAXZEIL
    If matrix(MAXSPALT, zeile) <> 0 Then
       tmpstr = "R" + Format$(zeile, "000")
       Print #1, Tab(5); "MYRHS"; Tab(15); tmpstr; Tab(25); Format$(matrix(MAXSPALT, zeile))
    End If
Next


'---------------------------------------------------------
' Endemarkierung

Print #1, "ENDATA"
Close #1

End Sub

Sub Solver_Batch_Datei_schreiben ()

' Erzeugung einer Batch-Datei, die den Solver aufruft.
' Dieses Konzept wird in erster Linie für XA benötigt, um
' eine temporäre Pfadergänzung vornehmen zu können, was
' für den Start vom Server (Laufwerk h:) Vorausetzung ist.


Open WorkingDir + "\solver.bat" For Output As #1

Print #1, "@echo off"

Select Case SolverName

   Case "XAeqs", "XAmps"
      Print #1, "REM  Solver: XA"
      Print #1,
      Print #1, "set Oldpath=%path%"
      Print #1, "path=" + XAPath + ";%path%"
      Print #1, "xa persplan.clp"
      Print #1, "set path=%Oldpath%"
      Print #1, "Oldpath="

   Case "MOPS"
      Print #1, "REM  Solver: MOPS"
      Print #1,
      Print #1, MOPSPath + "\mops_pc persplan.mps MIN"
                                                      
   Case "LPSOLVE"     '..kann eh nur minimieren !
      Print #1, "REM  Solver: lp_solve"
      Print #1,
      Print #1, LPSOLVEPath + "\lp_solve -mps <persplan.mps >lp_solve.out"
End Select

Close #1
  
End Sub

