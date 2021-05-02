'-- Registierung (Deklarierung) von DLL-Funktionen

' DLL-Funktion für die Abfrage, ob ein Modul (Programm) noch läuft
Declare Function getmoduleusage% Lib "kernel" (ByVal hModule%)

' DLL-Funktion für den Aufruf des Hilfesystems
Declare Sub winhelp Lib "user" (ByVal hWnd%, ByVal dat$, ByVal cmd%, dwdata As Any)

' DLL-Funktionen für das Sound-System
Declare Function sndPlaySound Lib "MMSystem" (lpsound As Any, ByVal flag As Integer) As Integer
Declare Function waveOutGetNumDevs Lib "MMSystem" () As Integer


'-- Konstanten
Global Const MAXZEILEN = 25


Type A_Satz
    AName As String
    AMenge1 As String
    AMenge2 As String
    kosten As String
    D1 As String
    D2 As String
    D3 As String
    D4 As String
    D5 As String
    D6 As String
    D7 As String
    D8 As String
End Type

Type Z_Satz
    ZName As String
    ZMenge1 As String
    ZMenge2 As String
    erlöse As String
    T1 As String
    T2 As String
    T3 As String
    T4 As String
    T5 As String
    T6 As String
    T7 As String
    T8 As String
End Type

Global A(25) As A_Satz
Global z(25) As Z_Satz


Global EXEPath  As String
Global DBPath   As String
Global XAPath   As String
Global OMPPath  As String
Global MOPSPath As String
Global STRADAPath  As String
Global LPSOLVEPath As String
Global WorkingDir  As String

Global pathPIF   As String * 63

'Global rueckschritt As integer
Global geladen As Integer
'Global neu As Variant
Global x     As Integer     ' für ModulUsage
Global Klang As Integer
Global flgPara As Integer   ' Parameter-Flag

Global aZeilen As Integer   ' Anzahl der Ausgangsstoffe
Global zZeilen As Integer

Global Legende As String    ' Lengende (Beschreibung) des Modells

Global dateiname As String
Global solver    As String
Global verztyp   As String


Sub Auf_Platte (p)
    
    Dim i As Integer

    Werteübergabe

    If p = "u" Or dateiname = "Neudatei" Then
       Speichern.StdDialog1.InitDir = DBPath
       Speichern.StdDialog1.DefaultExt = "mi"
       Speichern.StdDialog1.Filter = "MI-Dateien (*.mi)|*.mi|Alle Dateien (*.*)|*.*"
       Speichern.StdDialog1.Action = 2
       pfaddateiname = Speichern.StdDialog1.Filename
    
       'Abbrechen v. "Speichern unter"
       If pfaddateiname = "" Then
          Exit Sub
       End If

       'reinen Dateinamen extrahieren
       länge = Len(pfaddateiname)
       For i = länge To 1 Step -1
           If Mid$(pfaddateiname, i, 1) = "\" Then
              dateiname = Right$(pfaddateiname, (länge - i))
              Exit For
           End If
       Next i
       
    Else pfaddateiname = DBPath + "\" + dateiname

    End If

    mousepointer = 11
    Open pfaddateiname For Output As #1
    
    '-- Dateikopf schreiben:
    ' Legende und Anzahl der Ausgangsprodukte u. Zielprodukte
    If Legende <> "" Then Write #1, "A" + Legende
    Write #1, CStr(aZeilen)
    Write #1, CStr(zZeilen)


    'Ausgangsprodukte
    For i = 1 To aZeilen
        Write #1, A(i).AName
        Write #1, A(i).AMenge1
        Write #1, A(i).AMenge2
        Write #1, A(i).kosten
        Write #1, A(i).D1
        Write #1, A(i).D2
        Write #1, A(i).D3
        Write #1, A(i).D4
        Write #1, A(i).D5
        Write #1, A(i).D6
        Write #1, A(i).D7
        Write #1, A(i).D8
    Next i

    'Zielprodukte
    For i = 1 To zZeilen
        Write #1, z(i).ZName
        Write #1, z(i).ZMenge1
        Write #1, z(i).ZMenge2
        Write #1, z(i).erlöse
        Write #1, z(i).T1
        Write #1, z(i).T2
        Write #1, z(i).T3
        Write #1, z(i).T4
        Write #1, z(i).T5
        Write #1, z(i).T6
        Write #1, z(i).T7
        Write #1, z(i).T8
    Next i

    Close #1

    'reinen Dateinamen extrahieren
    länge = Len(pfaddateiname)
    For i = länge To 1 Step -1
        If Mid$(pfaddateiname, i, 1) = "\" Then
           dateiname = Right$(pfaddateiname, (länge - i))
           Exit For
        End If
    Next i

    mousepointer = 0
                    
End Sub

Sub Pif_Eintrag (pifdatei, batchdatei)
                                  
' -> Aufruf aus Form Verzeichnis u. Modul Berechnung


' Ist das WorkingDir unterschiedlich zum EXEPath, dann enthält
' der WorkingDir i.d.R. keine Pif-Datei.
' Diese wird deshalb aus dem EXEPath in das WD kopiert.

If WorkingDir <> EXEPath Then  'Or Dir(WorkingDir + "" + pifdatei) = "" Then
   FileCopy EXEPath + "\" + pifdatei, WorkingDir + "\" + pifdatei
End If


' Ab Byte 37 steht der Programmdateiname der Batchdatei (meist Solver.Bat)
' Die Variable pathPIF muß vom Typ STRING sein, sonst wird
' nicht richtig in die Datei geschrieben !

FNUM = FreeFile
Open WorkingDir + "\" + pifdatei For Binary As FNUM

'pathPIF = CStr(Trim$(verzeichnis.Text1.Text + "\" + batchdatei) + Chr$(0) + Space$(63 - Len(verzeichnis.Text1.Text) - 7))
pathPIF = CStr(Trim$(batchdatei) + Chr$(0) + Space$(63 - 7))
Put #FNUM, 37, pathPIF


' Ab Byte 102 steht das Anfangsverzeichnis der Batchdatei
'pathPIF = CStr(Trim$(text1.Text) + Chr$(0) + Space$(63 - Len(text1.Text) - 1))
pathPIF = CStr(Trim$(WorkingDir) + Chr$(0) + Space$(63 - Len(WorkingDir) - 1))
Put #FNUM, 102, pathPIF

Close #FNUM


End Sub

Sub Von_Platte ()

    Dim i As Integer

    'unload ...
               
If flgPara = 1 Then
   flgPara = 0
   pfaddateiname = dateiname
   GoTo ParaHandler
End If

    Öffnen.StdDialog1.DefaultExt = "mi"
    Öffnen.StdDialog1.InitDir = DBPath
    Öffnen.StdDialog1.Filter = "MI-Dateien (*.mi)|*.mi|Alle Dateien (*.*)|*.*"

    Öffnen.StdDialog1.Action = 1
    pfaddateiname = Öffnen.StdDialog1.Filename

    'Keine Datei gewählt
    If pfaddateiname = "" Then
       Exit Sub
    End If

ParaHandler:

    'reinen Dateinamen extrahieren
    länge = Len(pfaddateiname)
    For i = länge To 1 Step -1
        If Mid$(pfaddateiname, i, 1) = "\" Then
           dateiname = Right$(pfaddateiname, (länge - i))
           Exit For
        End If
    Next i


    mousepointer = 11
    Open pfaddateiname For Input As #1    '8! Pfad


    '-- Einlesen des Dateikopfes:
    '   Legende, Anzahl der Ausgangsstoffe u. Zielprodukte
    Input #1, dummy
    If Left$(dummy, 1) = "A" Then
      Legende = Right$(dummy, Len(dummy) - 1)
      'MsgBox (Legende)  ' >>> zum Testen

      Input #1, dummy: aZeilen = dummy
    Else
      Legende = ""
      aZeilen = dummy
    End If
    Input #1, dummy: zZeilen = dummy
                        

    'Ausgangsprodukte
    For i = 1 To aZeilen
        Input #1, A(i).AName
        Input #1, A(i).AMenge1
        Input #1, A(i).AMenge2
        Input #1, A(i).kosten
        Input #1, A(i).D1
        Input #1, A(i).D2
        Input #1, A(i).D3
        Input #1, A(i).D4
        Input #1, A(i).D5
        Input #1, A(i).D6
        Input #1, A(i).D7
        Input #1, A(i).D8
    Next i

    'Zielprodukte
    For i = 1 To zZeilen
        Input #1, z(i).ZName
        Input #1, z(i).ZMenge1
        Input #1, z(i).ZMenge2
        Input #1, z(i).erlöse
        Input #1, z(i).T1
        Input #1, z(i).T2
        Input #1, z(i).T3
        Input #1, z(i).T4
        Input #1, z(i).T5
        Input #1, z(i).T6
        Input #1, z(i).T7
        Input #1, z(i).T8
    Next i

    Close #1
    mousepointer = 0

    geladen = True

End Sub

Sub Werteübergabe ()

    Dim i As Integer


    ' Eingabecheck  -> fehlt noch


    ' Übergabe der Gitter-gatter2 an die Tabellen (glob. Tabs.)
    For i = 1 To MAXZEILEN - 1
        frmEingabe.Gitter1.Row = i

        frmEingabe.Gitter1.Col = 1: A(i).AName = frmEingabe.Gitter1.Text
        frmEingabe.Gitter1.Col = 2: A(i).AMenge1 = frmEingabe.Gitter1.Text
        frmEingabe.Gitter1.Col = 3: A(i).AMenge2 = frmEingabe.Gitter1.Text
        frmEingabe.Gitter1.Col = 4: A(i).kosten = frmEingabe.Gitter1.Text
        frmEingabe.Gitter1.Col = 5: A(i).D1 = frmEingabe.Gitter1.Text
        frmEingabe.Gitter1.Col = 6: A(i).D2 = frmEingabe.Gitter1.Text
        frmEingabe.Gitter1.Col = 7: A(i).D3 = frmEingabe.Gitter1.Text
        frmEingabe.Gitter1.Col = 8: A(i).D4 = frmEingabe.Gitter1.Text
        frmEingabe.Gitter1.Col = 9: A(i).D5 = frmEingabe.Gitter1.Text
        frmEingabe.Gitter1.Col = 10: A(i).D6 = frmEingabe.Gitter1.Text
        frmEingabe.Gitter1.Col = 11: A(i).D7 = frmEingabe.Gitter1.Text
        frmEingabe.Gitter1.Col = 12: A(i).D8 = frmEingabe.Gitter1.Text

        If A(i).AName = "" Then
           Exit For
        End If
        aZeilen = i
    Next i
    
    For i = 1 To MAXZEILEN - 1
        frmEingabe.Gitter2.Row = i

        frmEingabe.Gitter2.Col = 1: z(i).ZName = frmEingabe.Gitter2.Text
        frmEingabe.Gitter2.Col = 2: z(i).ZMenge1 = frmEingabe.Gitter2.Text
        frmEingabe.Gitter2.Col = 3: z(i).ZMenge2 = frmEingabe.Gitter2.Text
        frmEingabe.Gitter2.Col = 4: z(i).erlöse = frmEingabe.Gitter2.Text
        frmEingabe.Gitter2.Col = 5: z(i).T1 = frmEingabe.Gitter2.Text
        frmEingabe.Gitter2.Col = 6: z(i).T2 = frmEingabe.Gitter2.Text
        frmEingabe.Gitter2.Col = 7: z(i).T3 = frmEingabe.Gitter2.Text
        frmEingabe.Gitter2.Col = 8: z(i).T4 = frmEingabe.Gitter2.Text
        frmEingabe.Gitter2.Col = 9: z(i).T5 = frmEingabe.Gitter2.Text
        frmEingabe.Gitter2.Col = 10: z(i).T6 = frmEingabe.Gitter2.Text
        frmEingabe.Gitter2.Col = 11: z(i).T7 = frmEingabe.Gitter2.Text
        frmEingabe.Gitter2.Col = 12: z(i).T8 = frmEingabe.Gitter2.Text
        
'MsgBox "wertü" & z(i).T1 & z(i).T2

        If z(i).ZName = "" Then
           Exit For
        End If
        zZeilen = i
    Next i

End Sub

