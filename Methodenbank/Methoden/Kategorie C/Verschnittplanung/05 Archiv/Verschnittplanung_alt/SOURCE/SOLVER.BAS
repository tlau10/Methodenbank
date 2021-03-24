Attribute VB_Name = "SOLVER"

   Private Type STARTUPINFO
      cb As Long
      lpReserved As String
      lpDesktop As String
      lpTitle As String
      dwX As Long
      dwY As Long
      dwXSize As Long
      dwYSize As Long
      dwXCountChars As Long
      dwYCountChars As Long
      dwFillAttribute As Long
      dwFlags As Long
      wShowWindow As Integer
      cbReserved2 As Integer
      lpReserved2 As Long
      hStdInput As Long
      hStdOutput As Long
      hStdError As Long
   End Type

   Private Type PROCESS_INFORMATION
      hProcess As Long
      hThread As Long
      dwProcessID As Long
      dwThreadID As Long
   End Type

   Private Declare Function WaitForSingleObject Lib "kernel32" (ByVal hHandle As Long, ByVal dwMilliseconds As Long) As Long

   Private Declare Function CreateProcessA Lib "kernel32" (ByVal lpApplicationName As Long, ByVal lpCommandLine As String, ByVal lpProcessAttributes As Long, ByVal lpThreadAttributes As Long, ByVal bInheritHandles As Long, ByVal dwCreationFlags As Long, ByVal lpEnvironment As Long, ByVal lpCurrentDirectory As Long, lpStartupInfo As STARTUPINFO, lpProcessInformation As PROCESS_INFORMATION) As Long

   Private Declare Function CloseHandle Lib "kernel32" (ByVal hObject As Long) As Long

   Private Declare Function GetExitCodeProcess Lib "kernel32" (ByVal hProcess As Long, lpExitCode As Long) As Long

   Private Declare Function ShellExecute Lib "shell32.dll" Alias "ShellExecuteA" (ByVal hwnd As Long, ByVal lpOperation As String, ByVal lpFile As String, ByVal lpParameters As String, ByVal lpDirectory As String, ByVal nShowCmd As Long) As Long

   Private Const NORMAL_PRIORITY_CLASS = &H20&
   Private Const INFINITE = -1&

   Public Function ExecCmd(cmdline$)
      Dim proc As PROCESS_INFORMATION
      Dim start As STARTUPINFO

      ' Initialize the STARTUPINFO structure:
      start.cb = Len(start)
      start.dwFlags = 1
      start.wShowWindow = 2
      
      ' Start the shelled application:
      ret& = CreateProcessA(0&, cmdline$, 0&, 0&, 1&, _
                            NORMAL_PRIORITY_CLASS, 0&, 0&, start, proc)

      ' Wait for the shelled application to finish:
         ret& = WaitForSingleObject(proc.hProcess, INFINITE)
         Call GetExitCodeProcess(proc.hProcess, ret&)
         Call CloseHandle(proc.hThread)
         Call CloseHandle(proc.hProcess)
         ExecCmd = ret&
    End Function








Sub Berechnung()


Screen.MousePointer = 11

' Pifdatei in den WorkingDir kopieren und Einträge vornehmen
Pif_Eintrag "solver.pif", "solver.bat"     ' global.bas
    

Fehler = 0
                       
Select Case Solver_var

        
       Case "XAeqs"
            Solver_Batch_Datei_schreiben
            CLP_Datei_schreiben
            EQS_Datei_schreiben
            If Dir(WorkingDir + "\xa.out") <> "" Then
                Kill WorkingDir + "\xa.out"
            End If
            
            
            retval = ExecCmd(WorkingDir + "\solver.pif")
            'X = Shell(WorkingDir + "\solver.pif") ' Berechnungslauf
            Check_and_Wait
                   
       Case "XAmps"
            Solver_Batch_Datei_schreiben
            CLP_Datei_schreiben
            MPS_Datei_schreiben
            If Dir(WorkingDir + "\xa.out") <> "" Then
                Kill WorkingDir + "\xa.out"
            End If
            
            
            retval = ExecCmd(WorkingDir + "\solver.pif")
            'X = Shell(WorkingDir + "\solver.pif") ' Berechnungslauf
            Check_and_Wait
        
       Case "MOPS"
            Solver_Batch_Datei_schreiben
            MPS_Datei_schreiben
            If Dir(WorkingDir + "\mops.out") <> "" Then
                Kill WorkingDir + "\mops.out"
            End If
            
            
            retval = ExecCmd(WorkingDir + "\solver.pif")
            'X = Shell(WorkingDir + "\solver.pif") ' Berechnungslauf
            Check_and_Wait
        
       Case "LPSOLVE"
            Solver_Batch_Datei_schreiben
            MPS_Datei_schreiben
            If Dir(WorkingDir + "\lp_solve.out") <> "" Then
                Kill WorkingDir + "\lp_solve.out"
            End If
            
            
            retval = ExecCmd(WorkingDir + "\solver.pif")
            'X = Shell(WorkingDir + "\solver.pif")
            Check_and_Wait
  
       Case Else ' übrige Solver (OMP/NN/..)
            MsgBox "Der Solver " + Solver_var + " ist noch nicht implementiert.", 0, "Hinweis"
            Fehler = 1
End Select

End Sub

Sub Check_and_Wait()

' Kontrolle des Solver-Aufrufs mittels SHELL
' und Verzögern des Programmlaufes, bis die
' SHELL abgearbeitet ist.

If Err Then
   MsgBox ("Der Solver " + Solver_var + " konnte nicht aufgerufen werden !")
Else
    ' Zur Beruhigung der ORler
    'Wartefenster.Show 0
               
    'While getmoduleusage(X) > 0
    '    warte = DoEvents()
    'Wend
                               
    'Unload Wartefenster
End If

End Sub

Sub CLP_Datei_schreiben()

Dim File_Erweiterung As String

Fehler = 0

' Der Aufruf dieser Funktion macht nur für XAeqs und XAmps Sinn
If Solver_var = "XAeqs" Then
    File_Erweiterung = "LP"
Else
    If Solver_var = "XAmps" Then
        File_Erweiterung = "MPS"
    Else
        MsgBox "Es wurde SUB CLP_Datei_schreiben im falschen Kontext augerufen.", 0, "Hinweis"
        Fehler = 1
        Exit Sub
    End If
End If
Open WorkingDir + "\verschn.clp" For Output As #1
Print #1, "VERSCHN." + File_Erweiterung + " LISTINPUT NO"
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

Sub EQS_Datei_schreiben()

Dim i, j As Integer
Dim Ergebnis As Variant
Dim Ergebnis_int As Variant

' XA-Datei für LP-Format

Open WorkingDir + "\verschn.lp" For Output As #1

'Titel schreiben
Print #1, "..TITLE"
Print #1, "  Verschnittplanung"

If Eingabe.Option2(0).Value = True Then

' Zielfunktion schreiben
Print #1, "..OBJECTIVE MINIMIZE"

Ergebnis = "  "
For i = 1 To AnzVars
    If i > 1 Then
        Ergebnis = Ergebnis + " + "
    End If
    Ergebnis = Ergebnis + Trim(Varianten(i).Verschnitt) 'Gewichtung
    Ergebnis = Ergebnis + " "
    Ergebnis = Ergebnis + Trim(Varianten(i).Variable) 'Name
Next i

Print #1, Ergebnis

'Nicht-Negativitäts-Bedingungen setzen
Print #1, "..BOUNDS"

For i = 1 To AnzVars
    Ergebnis = "  "
    Ergebnis = Ergebnis + Trim(Varianten(i).Variable) 'Name
    Ergebnis = Ergebnis + " >= 0" 'NNB
    Print #1, Ergebnis
Next i

' Restriktionen schreiben
Print #1, "..CONSTRAINTS"

For i = 1 To AnzSpalten 'je Spalte eine Restriktion
    
    'Ergebnis = "  R" + i + ": "
    Ergebnis = "  R"
    Ergebnis_var = Trim(Str(i))
    'Ergebnis = Ergebnis + Str(i)
    
    Ergebnis = Ergebnis + Ergebnis_var
    Ergebnis = Ergebnis + ": "
    
    For j = 1 To AnzVars 'alle Variablen
        Ergebnis = Ergebnis + Trim(Varianten(j).Produkt(i)) + " "
        Ergebnis = Ergebnis + Trim(Varianten(j).Variable)
        If j < AnzVars Then
            Ergebnis = Ergebnis + " + "
        End If
    Next j
    
    'If flg_LoesZwang = 0 Then
    '   Ergebnis = Ergebnis + " = "
    'Else
       Ergebnis = Ergebnis + " >= "
    'End If

    Ergebnis = Ergebnis + Trim(Spalte(i).Anzahl)
    Print #1, Ergebnis
Next i
'Falls eine Einschränkung der Werkstücke angegeben, diese auch noch
'als Restriktion aufnehmen.
If maxWerkst <> "" Then
    Ergebnis = "  R"
    Ergebnis_var = Trim(Str(i))
    Ergebnis = Ergebnis + Ergebnis_var
    Ergebnis = Ergebnis + ": "

    For j = 1 To AnzVars 'alle Variablen dieses Ausgangsstoffes
        If Varianten(j).Ausgang = 0 Then
            Ergebnis = Ergebnis + "1 "
            Ergebnis = Ergebnis + Trim(Varianten(j).Variable)
            If j < AnzVars And Varianten(j + 1).Ausgang = 0 Then
                Ergebnis = Ergebnis + " + "
            End If
        End If
    Next j
    
    Ergebnis = Ergebnis + " <= " + Trim(maxWerkst)

    Print #1, Ergebnis
    
End If
'############################################
If maxWerkst1 <> "" Then
    Ergebnis = "  R"
    Ergebnis_var = Trim(Str(i + 1))
    Ergebnis = Ergebnis + Ergebnis_var
    Ergebnis = Ergebnis + ": "

    For j = 1 To AnzVars 'alle Variablen dieses Ausgangsstoffes
        If Varianten(j).Ausgang = 1 Then
            Ergebnis = Ergebnis + "1 "
            Ergebnis = Ergebnis + Trim(Varianten(j).Variable)
            If j < AnzVars And Varianten(j + 1).Ausgang = 1 Then
                Ergebnis = Ergebnis + " + "
            End If
        End If
    Next j
    
    Ergebnis = Ergebnis + " <= " + Trim(maxWerkst1)

    Print #1, Ergebnis
    
End If
'############################################
If maxWerkst2 <> "" Then
    Ergebnis = "  R"
    Ergebnis_var = Trim(Str(i + 1))
    Ergebnis = Ergebnis + Ergebnis_var
    Ergebnis = Ergebnis + ": "

    For j = 1 To AnzVars 'alle Variablen dieses Ausgangsstoffes
        If Varianten(j).Ausgang = 2 Then
            Ergebnis = Ergebnis + "1 "
            Ergebnis = Ergebnis + Trim(Varianten(j).Variable)
            If j < AnzVars And Varianten(j + 1).Ausgang = 2 Then
                Ergebnis = Ergebnis + " + "
            End If
        End If
    Next j
    
    Ergebnis = Ergebnis + " <= " + Trim(maxWerkst2)

    Print #1, Ergebnis
    
End If
'############################################
If maxWerkst3 <> "" Then
    Ergebnis = "  R"
    Ergebnis_var = Trim(Str(i + 1))
    Ergebnis = Ergebnis + Ergebnis_var
    Ergebnis = Ergebnis + ": "

    For j = 1 To AnzVars 'alle Variablen dieses Ausgangsstoffes
        If Varianten(j).Ausgang = 3 Then
            Ergebnis = Ergebnis + "1 "
            Ergebnis = Ergebnis + Trim(Varianten(j).Variable)
            If j < AnzVars And Varianten(j + 1).Ausgang = 3 Then
                Ergebnis = Ergebnis + " + "
            End If
        End If
    Next j
    
    Ergebnis = Ergebnis + " <= " + Trim(maxWerkst3)

    Print #1, Ergebnis
    
End If

'Datei schließen
Close #1


ElseIf Eingabe.Option2(1).Value = True Then

'hier definition für werkstückoptimierung


' Zielfunktion schreiben
Print #1, "..OBJECTIVE MINIMIZE"

Ergebnis = "  "
For i = 1 To AnzVars
    If i > 1 Then
        Ergebnis = Ergebnis + " + "
    End If
    'Ergebnis = Ergebnis + Trim(Varianten(i).Verschnitt) 'Gewichtung
    'Gewichtung aller Varianten ist 1, da bei jeder Variante
    ' 1 Werkstück benötigt wird
    Ergebnis = Ergebnis + Trim(1)
    Ergebnis = Ergebnis + " "
    Ergebnis = Ergebnis + Trim(Varianten(i).Variable) 'Name
Next i

Print #1, Ergebnis

'Nicht-Negativitäts-Bedingungen setzen
Print #1, "..BOUNDS"

For i = 1 To AnzVars
    Ergebnis = "  "
    Ergebnis = Ergebnis + Trim(Varianten(i).Variable) 'Name
    Ergebnis = Ergebnis + " >= 0" 'NNB
    Print #1, Ergebnis
Next i

' Restriktionen schreiben
Print #1, "..CONSTRAINTS"

For i = 1 To AnzSpalten 'je Spalte eine Restriktion
    
    'Ergebnis = "  R" + i + ": "
    Ergebnis = "  R"
    Ergebnis_var = Trim(Str(i))
    'Ergebnis = Ergebnis + Str(i)
    
    Ergebnis = Ergebnis + Ergebnis_var
    Ergebnis = Ergebnis + ": "
    
    For j = 1 To AnzVars 'alle Variablen
        Ergebnis = Ergebnis + Trim(Varianten(j).Produkt(i)) + " "
        Ergebnis = Ergebnis + Trim(Varianten(j).Variable)
        If j < AnzVars Then
            Ergebnis = Ergebnis + " + "
        End If
    Next j
    
    'If flg_LoesZwang = 0 Then
    '   Ergebnis = Ergebnis + " = "
    'Else
       Ergebnis = Ergebnis + " >= "
    'End If

    Ergebnis = Ergebnis + Trim(Spalte(i).Anzahl)
    Print #1, Ergebnis
Next i

'**** nicht mehr implementiert, da keine max. Werkstücke bei
'**** Werkstoffoptimierung eintragbar
'Falls eine Einschränkung der Werkstücke angegeben, diese auch noch
'als Restriktion aufnehmen.
'If maxWerkst <> "" Then
'    Ergebnis = "  R"
'    Ergebnis_var = Trim(Str(i))
'    Ergebnis = Ergebnis + Ergebnis_var
'    Ergebnis = Ergebnis + ": "
'
'    For j = 1 To AnzVars 'alle Variablen
'        Ergebnis = Ergebnis + "1 "
'        Ergebnis = Ergebnis + Trim(Varianten(j).Variable)
'        If j < AnzVars Then
'            Ergebnis = Ergebnis + " + "
'        End If
'    Next j
'
'    Ergebnis = Ergebnis + " <= " + Trim(maxWerkst)
'
'    Print #1, Ergebnis
'
'End If

'Datei schließen
Close #1

End If


End Sub

Sub LPSOLVE_Ergebnis_lesen()

' ergänzt im Nov. 98 (otw)
'


Dim i, j As Integer             ' Zähler
Dim Position, PosAlt As Integer ' Positionszeiger
Dim Ergebnis As String * 10000
Dim tmpString As Variant
Dim tmpWert As Single     ' wg. Nachkommawerten

Zielwert = 0

Open WorkingDir + "\lp_solve.out" For Binary As #1

'*** Lesen eines Puffers in eine Zwischenvariable
Get #1, , Ergebnis
Close #1

'*** Gibt es keine Lösung ?
Position = InStr(Ergebnis, "Value of objective function:")
If Position = 0 Then
    Fehler = 1
    Exit Sub
End If

'*** Zielfunktionswert suchen
'Angepasst an das lesen der anderen Ergebnisse
Position = InStr(Ergebnis, "function:")
If Position <> 0 Then
    tmpString = Mid(Ergebnis, Position + 10, 16)
    'tmpString = Str$(Val(tmpString)) 'Umwandeln in Standard-Darstellung
    'tmpString = Mid(tmpString, 2, Len(tmpString) - 1)'Entfernen des Vorzeichens
'    Zielwert = tmpString
    PosAlt = Position + 27
End If

'*** Werte suchen und eintragen
For i = 1 To AnzVars
    tmpString = Trim(Varianten(i).Variable)
    Position = InStr(PosAlt, Ergebnis, tmpString) 'Variable suchen
    If Position <> 0 Then
        Laenge = Len(tmpString)
        tmpString = Mid(Ergebnis, Position + 12, 15) 'Zahl extrahieren
        
        ' Entfernen des Tausender-Kommas (Umwandlung sonst nicht möglich)
        For j = 1 To Len(tmpString)
            If Mid(tmpString, j, 1) = "," Then
                tmpString = Mid(tmpString, 1, j - 1) & Mid(tmpString, j + 1, Len(tmpString) - j + 1)
            End If
        Next j
        
        ' Nichtganzzahligen Wert auf den nächsten Ganzzahlwert erhöhen
        ' Grund: LPSolve beherrscht keine 'echte' Ganzzahligkeit
        tmpWert = Val(tmpString)
        If Int(tmpWert) <> tmpWert Then tmpWert = Int(tmpWert) + 1

        tmpString = Str$(Val(tmpWert)) 'Umwandeln in Standard-Darstellung
        tmpString = Mid(tmpString, 2, Len(tmpString) - 1) 'Entfernen des Vorzeichens
        Varianten(i).Anzahl = tmpString
        
        ' Angepasst an das lesen der anderen Ergebnisse
        ' Zielwert errechnet sich aus Gesamtsumme aller Verschnitte
        ' Werkstücke werden in FormLoad von Form Ausgabe berechnet
        ' somit ist man vom eigentlichen Zielfunktionswert unabhängig
        ' ob Werkstück- oder Verschnittoptimierung gewählt wurde, da immer
        ' die Werte errechnet werden und nicht der Zielfunktionswert vom
        ' Ergebnis des Solvers verwendet wird.
        Zielwert = Zielwert + Varianten(i).Verschnitt * tmpString
        
    End If    ' Else nicht nötig, da Anzahl mit 0 vorbelegt
    PosAlt = Position + 27 'Erhöhen des Positionszeigers
Next i

End Sub

Sub MOPS_Ergebnis_lesen()

' Letzte Aktualisierung: 12/98

Dim i, j As Integer              ' Zähler
Dim Position, PosAlt As Integer  ' Positionszeiger
Dim Ergebnis As String * 10000
Dim tmpString As Variant
Dim tmpWert As Single


Open WorkingDir + "\mops.out" For Binary As #1
' Lesen eines Puffers in eine Zwischenvariable
Get #1, , Ergebnis
Close #1


'-- Gibt es keine Lösung ?
Position = InStr(Ergebnis, "SOLUTION: (OPTIMAL)")
If Position = 0 Then
    Fehler = 1
    Exit Sub      ' >>>>> Hops
End If


'-- Zielfunktionswert (Verschnitt) suchen
' Dieser ist bei nichtganzzahligen Variablenwerten falsch.
' Deshalb muß der Zielwert (Verschnitt) errechnet werden.
'
Zielwert = 0  ' Vorbelegung

Position = InStr(Ergebnis, "FUNCTIONAL")
If Position <> 0 Then
    'tmpString = Mid(Ergebnis, Position + 11, 16)
    'tmpString = Str$(Val(tmpString)) ' Umwandeln in Standard-Darstellung
    'tmpString = Mid(tmpString, 2, Len(tmpString) - 1)'Entfernen des Vorzeichens
    'Zielwert = tmpString
    PosAlt = Position + 27
End If


'-- Werte suchen und eintragen

For i = 1 To AnzVars
    tmpString = Trim(Varianten(i).Variable)
    Position = InStr(PosAlt, Ergebnis, tmpString) 'Variable suchen
    
    If Position <> 0 Then
        Laenge = Len(tmpString)
        tmpString = Mid(Ergebnis, Position + 12, 15) 'Zahl extrahieren
        
        ' Entfernen des Tausender-Kommas (Umwandlung sonst nicht möglich)
        For j = 1 To Len(tmpString)
            If Mid(tmpString, j, 1) = "," Then
                tmpString = Mid(tmpString, 1, j - 1) & Mid(tmpString, j + 1, Len(tmpString) - j + 1)
            End If
        Next j
        
        ' Nichtganzzahligen Wert auf den nächsten Ganzzahlwert erhöhen
        ' Grund: XA beherrscht keine 'echte' Ganzzahligkeit
        tmpWert = Val(tmpString)
        If Int(tmpWert) <> tmpWert Then tmpWert = Int(tmpWert) + 1
  

        tmpString = Str$(tmpWert)  ' Umwandeln in Standard-Darstellung
        tmpString = Mid(tmpString, 2, Len(tmpString) - 1)  ' Entfernen des Vorzeichens
        Varianten(i).Anzahl = tmpString

        ' Zielwert (Summe Verschnitt)
        Zielwert = Zielwert + Varianten(i).Verschnitt * tmpWert

    End If    ' Else nicht nötig, da Anzahl mit 0 vorbelegt
    
    PosAlt = Position + 27  ' Erhöhen des Positionszeigers
Next i

End Sub

Sub MPS_Datei_schreiben()

Dim Row, Zeile As Integer       ' Indexierungsfunktion
Dim Row_Name As String          ' Aufbereiteter Name der Restriktion
Dim Vergleicher As String       ' = bzw. >= für Constrains (Rows)
Dim ZF_NAME As Variant          ' Name der Zielfunktion

ZF_NAME = "ZIEL"
'--------------------------------------------

Select Case Solver_var
    Case "XAmps"
        Open WorkingDir + "\verschn.mps" For Output As #1
    Case "MOPS"
        Open WorkingDir + "\verschn.mps" For Output As #1
    Case "LPSOLVE"
        Open WorkingDir + "\verschn.mps" For Output As #1
End Select
'--------------------------------------------


'*** Hier kommt der Titel des Modells
Print #1, Tab(1); "NAME"; Tab(15); "VERSCHNITT" & Str$(Anz_Dimensionen)


If Eingabe.Option2(0).Value = True Then
' Verschnittoptimierung

'*** In ROWS werden die Namen der Restriktionen und der der Zielfunktion benannt
'If flg_LoesZwang = 0 Then
'   Vergleicher = "E"
'Else
   Vergleicher = "G"
'End If

Print #1, "ROWS"
Print #1, Tab(2); "N"; Tab(5); ZF_NAME
For Row = 1 To AnzSpalten
    tmpString = Str$(Row) 'umwandeln des Zählers
    tmpString = Mid(tmpString, 2, Len(tmpString) - 1) 'Vorzeichenstelle abschneiden
    Row_Name = "R" & tmpString
    Print #1, Tab(2); Vergleicher; Tab(5); Row_Name
Next Row

'max. vorhanden Werkstücke angegeben
If maxWerkst <> "" Then
    tmpString = Str$(Row) 'umwandeln des Zählers
    tmpString = Mid(tmpString, 2, Len(tmpString) - 1) 'Vorzeichenstelle abschneiden
    Row_Name = "R" & tmpString
    Print #1, Tab(2); "L"; Tab(5); Row_Name
End If
If maxWerkst1 <> "" Then
    tmpString = Str$(Row) 'umwandeln des Zählers
    tmpString = Mid(tmpString, 2, Len(tmpString) - 1) 'Vorzeichenstelle abschneiden
    Row_Name = "R" & tmpString
    Print #1, Tab(2); "L"; Tab(5); Row_Name
End If
If maxWerkst2 <> "" Then
    tmpString = Str$(Row) 'umwandeln des Zählers
    tmpString = Mid(tmpString, 2, Len(tmpString) - 1) 'Vorzeichenstelle abschneiden
    Row_Name = "R" & tmpString
    Print #1, Tab(2); "L"; Tab(5); Row_Name
End If
If maxWerkst3 <> "" Then
    tmpString = Str$(Row) 'umwandeln des Zählers
    tmpString = Mid(tmpString, 2, Len(tmpString) - 1) 'Vorzeichenstelle abschneiden
    Row_Name = "R" & tmpString
    Print #1, Tab(2); "L"; Tab(5); Row_Name
End If


'*** In COLUMNS folgen die Werte der A-Matrix samt Zielfunktion
Print #1, "COLUMNS"

If Solver_var = "LPSOLVE" Then   '--> Ganzzahligkeit (leider nur bei LP-Solve)
   Print #1, "    MARK0001  'MARKER'                 'INTORG'"
End If

For Zeile = 1 To AnzVars
    tmpString = Str$(Varianten(Zeile).Verschnitt)
    tmpString = Mid(tmpString, 2, Len(tmpString) - 1) 'Vorzeichenstelle abschneiden
    Varianten(Zeile).Verschnitt = tmpString
    Print #1, Tab(5); Mid(Varianten(Zeile).Variable, 1, 10); Tab(15); Mid(ZF_NAME, 1, 10); Tab(25); Varianten(Zeile).Verschnitt
    For Row = 1 To AnzSpalten
        tmpString = Str$(Row) 'umwandeln des Zählers
        tmpString = Mid(tmpString, 2, Len(tmpString) - 1) 'Vorzeichenstelle abschneiden
        Row_Name = "R" & tmpString
        Print #1, Tab(5); Mid(Varianten(Zeile).Variable, 1, 10); Tab(15); Mid(Row_Name, 1, 10); Tab(25); Varianten(Zeile).Produkt(Row)
    Next Row
    'max. vorhanden Werkstücke angegeben
    If maxWerkst <> "" Then
        tmpString = Str$(Row) 'umwandeln des Zählers
        tmpString = Mid(tmpString, 2, Len(tmpString) - 1) 'Vorzeichenstelle abschneiden
        Row_Name = "R" & tmpString
        If Varianten(Zeile).Ausgang = 0 Then
           Print #1, Tab(5); Mid(Varianten(Zeile).Variable, 1, 10); Tab(15); Mid(Row_Name, 1, 10); Tab(25); "1"
        Else
           Print #1, Tab(5); Mid(Varianten(Zeile).Variable, 1, 10); Tab(15); Mid(Row_Name, 1, 10); Tab(25); "0"
        End If
    End If
    If maxWerkst1 <> "" Then
        tmpString = Str$(Row) 'umwandeln des Zählers
        tmpString = Mid(tmpString, 2, Len(tmpString) - 1) 'Vorzeichenstelle abschneiden
        Row_Name = "R" & tmpString
        If Varianten(Zeile).Ausgang = 1 Then
           Print #1, Tab(5); Mid(Varianten(Zeile).Variable, 1, 10); Tab(15); Mid(Row_Name, 1, 10); Tab(25); "1"
        Else
           Print #1, Tab(5); Mid(Varianten(Zeile).Variable, 1, 10); Tab(15); Mid(Row_Name, 1, 10); Tab(25); "0"
        End If
    End If
    If maxWerkst2 <> "" Then
        tmpString = Str$(Row) 'umwandeln des Zählers
        tmpString = Mid(tmpString, 2, Len(tmpString) - 1) 'Vorzeichenstelle abschneiden
        Row_Name = "R" & tmpString
        If Varianten(Zeile).Ausgang = 2 Then
           Print #1, Tab(5); Mid(Varianten(Zeile).Variable, 1, 10); Tab(15); Mid(Row_Name, 1, 10); Tab(25); "1"
        Else
           Print #1, Tab(5); Mid(Varianten(Zeile).Variable, 1, 10); Tab(15); Mid(Row_Name, 1, 10); Tab(25); "0"
        End If
    End If
    If maxWerkst3 <> "" Then
        tmpString = Str$(Row) 'umwandeln des Zählers
        tmpString = Mid(tmpString, 2, Len(tmpString) - 1) 'Vorzeichenstelle abschneiden
        Row_Name = "R" & tmpString
        If Varianten(Zeile).Ausgang = 3 Then
           Print #1, Tab(5); Mid(Varianten(Zeile).Variable, 1, 10); Tab(15); Mid(Row_Name, 1, 10); Tab(25); "1"
        Else
           Print #1, Tab(5); Mid(Varianten(Zeile).Variable, 1, 10); Tab(15); Mid(Row_Name, 1, 10); Tab(25); "0"
        End If
    End If
Next Zeile




If Solver_var = "LPSOLVE" Then   '--> Ganzzahligkeit (leider nur bei LP-Solve)
   Print #1, "    MARK0002  'MARKER'                 'INTEND'"
End If


'*** In RHS kommen Werte des B-Vektors
Print #1, "RHS"
For Row = 1 To AnzSpalten
    tmpString = Str$(Row) 'umwandeln des Zählers
    tmpString = Mid(tmpString, 2, Len(tmpString) - 1) 'Vorzeichenstelle abschneiden
    Row_Name = "R" & tmpString
    Print #1, Tab(5); "MYRHS"; Tab(15); Row_Name; Tab(24); Str$(Spalte(Row).Anzahl)
Next Row

'max. vorhanden Werkstücke angegeben
If maxWerkst <> "" Then
    tmpString = Str$(Row) 'umwandeln des Zählers
    tmpString = Mid(tmpString, 2, Len(tmpString) - 1) 'Vorzeichenstelle abschneiden
    Row_Name = "R" & tmpString
    Print #1, Tab(5); "MYRHS"; Tab(15); Row_Name; Tab(24); Str$(maxWerkst)
End If
If maxWerkst1 <> "" Then
    tmpString = Str$(Row) 'umwandeln des Zählers
    tmpString = Mid(tmpString, 2, Len(tmpString) - 1) 'Vorzeichenstelle abschneiden
    Row_Name = "R" & tmpString
    Print #1, Tab(5); "MYRHS"; Tab(15); Row_Name; Tab(24); Str$(maxWerkst1)
End If
If maxWerkst2 <> "" Then
    tmpString = Str$(Row) 'umwandeln des Zählers
    tmpString = Mid(tmpString, 2, Len(tmpString) - 1) 'Vorzeichenstelle abschneiden
    Row_Name = "R" & tmpString
    Print #1, Tab(5); "MYRHS"; Tab(15); Row_Name; Tab(24); Str$(maxWerkst2)
End If
If maxWerkst3 <> "" Then
    tmpString = Str$(Row) 'umwandeln des Zählers
    tmpString = Mid(tmpString, 2, Len(tmpString) - 1) 'Vorzeichenstelle abschneiden
    Row_Name = "R" & tmpString
    Print #1, Tab(5); "MYRHS"; Tab(15); Row_Name; Tab(24); Str$(maxWerkst3)
End If




ElseIf Eingabe.Option2(1).Value = True Then
' Werkstückoptimierung

'*** In ROWS werden die Namen der Restriktionen und der der Zielfunktion benannt
'If flg_LoesZwang = 0 Then
'   Vergleicher = "E"
'Else
   Vergleicher = "G"
'End If

Print #1, "ROWS"
Print #1, Tab(2); "N"; Tab(5); ZF_NAME
For Row = 1 To AnzSpalten
    tmpString = Str$(Row) 'umwandeln des Zählers
    tmpString = Mid(tmpString, 2, Len(tmpString) - 1) 'Vorzeichenstelle abschneiden
    Row_Name = "R" & tmpString
    Print #1, Tab(2); Vergleicher; Tab(5); Row_Name
Next Row

'**** nicht mehr implementiert, da keine max. Werkstücke bei
'**** Werkstoffoptimierung eintragbar
'max. vorhanden Werkstücke angegeben
'If maxWerkst <> "" Then
'    tmpString = Str$(Row) 'umwandeln des Zählers
'    tmpString = Mid(tmpString, 2, Len(tmpString) - 1) 'Vorzeichenstelle abschneiden
'    Row_Name = "R" & tmpString
'    Print #1, Tab(2); "L"; Tab(5); Row_Name
'End If

'*** In COLUMNS folgen die Werte der A-Matrix samt Zielfunktion
Print #1, "COLUMNS"

If Solver_var = "LPSOLVE" Then   '--> Ganzzahligkeit (leider nur bei LP-Solve)
   Print #1, "    MARK0001  'MARKER'                 'INTORG'"
End If

For Zeile = 1 To AnzVars
    tmpString = Str$(Varianten(Zeile).Verschnitt)
    tmpString = Mid(tmpString, 2, Len(tmpString) - 1) 'Vorzeichenstelle abschneiden
    Varianten(Zeile).Verschnitt = tmpString
    Print #1, Tab(5); Mid(Varianten(Zeile).Variable, 1, 10); Tab(15); Mid(ZF_NAME, 1, 10); Tab(25); "1" 'Varianten(Zeile).Verschnitt
    For Row = 1 To AnzSpalten
        tmpString = Str$(Row) 'umwandeln des Zählers
        tmpString = Mid(tmpString, 2, Len(tmpString) - 1) 'Vorzeichenstelle abschneiden
        Row_Name = "R" & tmpString
        Print #1, Tab(5); Mid(Varianten(Zeile).Variable, 1, 10); Tab(15); Mid(Row_Name, 1, 10); Tab(25); Varianten(Zeile).Produkt(Row)
    Next Row
    
'**** nicht mehr implementiert, da keine max. Werkstücke bei
'**** Werkstoffoptimierung eintragbar
    'max. vorhanden Werkstücke angegeben
'    If maxWerkst <> "" Then
'        tmpString = Str$(Row) 'umwandeln des Zählers
'        tmpString = Mid(tmpString, 2, Len(tmpString) - 1) 'Vorzeichenstelle abschneiden
'        Row_Name = "R" & tmpString
'        Print #1, Tab(5); Mid(Varianten(Zeile).Variable, 1, 10); Tab(15); Mid(Row_Name, 1, 10); Tab(25); "1"
'    End If
    
Next Zeile

If Solver_var = "LPSOLVE" Then   '--> Ganzzahligkeit (leider nur bei LP-Solve)
   Print #1, "    MARK0002  'MARKER'                 'INTEND'"
End If


'*** In RHS kommen Werte des B-Vektors
Print #1, "RHS"
For Row = 1 To AnzSpalten
    tmpString = Str$(Row) 'umwandeln des Zählers
    tmpString = Mid(tmpString, 2, Len(tmpString) - 1) 'Vorzeichenstelle abschneiden
    Row_Name = "R" & tmpString
    Print #1, Tab(5); "MYRHS"; Tab(15); Row_Name; Tab(24); Str$(Spalte(Row).Anzahl)
Next Row

'**** nicht mehr implementiert, da keine max. Werkstücke bei
'**** Werkstoffoptimierung eintragbar
'max. vorhanden Werkstücke angegeben
'If maxWerkst <> "" Then
'    tmpString = Str$(Row) 'umwandeln des Zählers
'    tmpString = Mid(tmpString, 2, Len(tmpString) - 1) 'Vorzeichenstelle abschneiden
'    Row_Name = "R" & tmpString
'    Print #1, Tab(5); "MYRHS"; Tab(15); Row_Name; Tab(24); Str$(maxWerkst)
'End If


End If

' Endemarkierung
Print #1, "ENDATA"
Close #1
'--------------------------------------------
End Sub

Sub Solver_Batch_Datei_schreiben()

' -> wird in den WorkingDir geschrieben (alles: Eingabe u. Ausgabe)


Open WorkingDir + "\solver.bat" For Output As #1
Print #1, "@echo off"

Select Case Solver_var
   Case "XAeqs", "XAmps"
      Print #1, "REM Solver: XA"
      Print #1, "set Oldpath=%path%"
      Print #1, "path=" + XAPath + ";%path%"
      Print #1, "xa verschn.clp"
      Print #1, "set path=%Oldpath%"
      Print #1, "set Oldpath="

   Case "MOPS"
      Print #1, "REM Solver: MOPS"
      Print #1, MOPSPath + "\mops_pc verschn.mps MIN"
                                                      
   Case "LPSOLVE"
      Print #1, "REM Solver: LP_Solve"
      Print #1, LPSOLVEPath + "\lp_solve.exe -mps <verschn.mps >lp_solve.out"

  Case "STRADA"
      Print #1, "@echo off"
      Print #1, "REM Solver: STRADA"
      Print #1, STRADAPath + "\strada.exe verschn.mps MIN >strada.out"

End Select

Close #1
  
End Sub

Sub XA_Ergebnis_lesen()

' Letzte Aktualisierung: 12/98

Dim i, j As Integer       ' Zähler
Dim Position, PosAlt, tmpPos As Integer  ' Positionszeiger
Dim Ergebnis As String * 10000
Dim tmpString As String
Dim tmpWert As Single     ' wg. Nachkommawerten

'Ergebnis = " "  'wurde von thomas eingeführt
Open WorkingDir + "\xa.out" For Binary As #1
Get #1, , Ergebnis
Close #1


'-- Lösung vorhanden ?
Position = InStr(Ergebnis, "N O ")
If Position <> 0 Then
    Fehler = 1
    Exit Sub      ' >>>>> Sprung aus der Proz.
End If



'-- Zielfunktionswert suchen
' Dieser ist bei nichtganzzahligen Variablenwerten falsch.
' Deshalb muß der Zielwert (Verschnitt) errechnet werden.
'
Zielwert = 0  ' Vorbelegung

Position = InStr(Ergebnis, "SOLUTION (")
If Position <> 0 Then
   'tmpString = Mid(Ergebnis, Position + 22, 10)

   ' Entfernen des Tausender-KOMMAS (am. Darstell.)
   'For j = 1 To Len(tmpString)
   '   If Mid(tmpString, j, 1) = "," Then
   '      tmpString = Mid(tmpString, 1, j - 1) + Mid(tmpString, j + 1, Len(tmpString) - j + 1)
   '   End If
   'Next j
       
   ' Ganzzahl darstellen ! (ab PUNKT)
   'tmpPos = InStr(tmpString, ".")
   'If tmpPos > 0 Then
   '   tmpString = Mid(tmpString, 1, tmpPos - 1)
   'End If
                                            
   'Zielwert = tmpString
 
   PosAlt = Position + 20

Else
    'Falls XA irgendwelche Fehler ausgibt anstatt der normalen Ausgabedatei
    Position = InStr(Ergebnis, "?? Error")
    If Position <> 0 Then
        Fehler = 2
        Exit Sub      ' >>>>> Sprung aus der Proz.
    End If
End If


'-- Variablenwerte suchen und eintragen
For i = 1 To AnzVars
    tmpString = Trim(Varianten(i).Variable)
    Position = InStr(PosAlt, Ergebnis, tmpString) ' Variable suchen
    If Position <> 0 Then
        Laenge = Len(tmpString)
        tmpString = Mid(Ergebnis, Position + Laenge, 17 - Laenge) 'Zahl extrahieren
        
        ' Entfernen des Tausender-Kommas (Umwandlung sonst nicht möglich)
        For j = 1 To Len(tmpString)
            If Mid(tmpString, j, 1) = "," Then
                tmpString = Mid(tmpString, 1, j - 1) & Mid(tmpString, j + 1, Len(tmpString) - j + 1)
            End If
        Next j

        ' Nichtganzzahligen Wert auf den nächsten Ganzzahlwert erhöhen
        ' Grund: XA beherrscht keine 'echte' Ganzzahligkeit
        tmpWert = Val(tmpString)
        If Int(tmpWert) <> tmpWert Then tmpWert = Int(tmpWert) + 1
   
        tmpString = Str$(tmpWert)     ' Umwandeln in Standard-Darstellung
        tmpString = Mid(tmpString, 2, Len(tmpString) - 1)  ' Entfernen des Vorzeichens
        Varianten(i).Anzahl = tmpString

        ' Zielwert (Summe Verschnitt)
        Zielwert = Zielwert + Varianten(i).Verschnitt * tmpWert

    End If    ' Else nicht nötig, da Anzahl mit 0 vorbelegt
    
    PosAlt = Position + 20 'Erhöhen des Positionszeigers
Next i

End Sub

