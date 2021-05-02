Attribute VB_Name = "GLOBALS"
'-- Registierung (Deklarierung) von DLL-Funktionen

' DLL-Funktion zum Aufruf der Hilfe-Funktion (01.11.95)
Declare Sub WinHelp Lib "user" (ByVal hwnd%, ByVal dat$, ByVal cmd%, dwdata As Any)

' DLL-Funktion für die Abfrage, ob ein Modul (Programm) noch läuft
Declare Function getmoduleusage% Lib "kernel" (ByVal hModule%)


Global Const MaxSpalten = 25  ' Maximale Anzahl von Produktvarianten
Global AnzSpalten As Integer  ' Effektive Anzahl eingetragener Produkte
Global AnzZeilen As Integer   ' Effektive Anzahl eingetragener Varianten
Global Const MaxVars = 100    ' Maximale Anzahl von Variablen, die
                              ' vom Solver verarbeitet werden können
                              ' (sollte auf Solver angepaßt werden können !)
Global AnzVars As Integer     ' Effektive Anzahl errechneter Kombinationen

Global Const MaxBreiten = 4

Global Anz_Dimensionen As Integer ' Unterscheidung, ob 1D oder 2D

Type TabellenEintrag          ' entspricht einem Wert im Eingabegitter
    Breite As Variant
    Anzahl As Variant
    Höhe   As Variant
End Type

Global Spalte(MaxSpalten) As TabellenEintrag   ' gespiegelte Eingabetabelle

Type VariantenEintrag                 ' entspricht einem Wert in der Zieltabelle
    Variable As String               ' Variablen-Name
    Produkt(MaxSpalten) As Variant    ' Tupel der Kombination
    Verschnitt As Variant             ' Effektiver Verschnitt je Werkstück
    Anzahl As Variant                 ' Anzahl dieser Kombination in der Lösung
    Ausgang As Variant                ' Nummer des Ausgangsprodukts
End Type

Global Varianten(MaxVars) As VariantenEintrag  ' gespiegelte Zieltabelle

Global Breite(MaxBreiten)   As Variant ' Breite des Werkstoffes
Global Höhe(MaxBreiten)     As Variant ' Höhe des Werkstoffes
Global Zielwert As Variant ' Zielfunktionswert der Lösung
Global maxWerkst As Variant ' Maximal vorhandene Werkstücke
Global maxWerkst1 As Variant
Global maxWerkst2 As Variant
Global maxWerkst3 As Variant

Global Fehler As Integer   ' Flag zur Fehlererkennung

Global EXEPath  As String
Global DBPath   As String
Global XAPath   As String
Global OMPPath  As String
Global MOPSPath As String
Global STRADAPath  As String
Global LPSOLVEPath As String
Global WorkingDir  As String



Global flg_LoesZwang As Integer  'Flag für Lösung erzwingen
Global CanClose As Integer  'Flag für geänderte Dateien
Global IsActive As Integer  'Flag für aktive Datei

Global dateiname As String
Global Solver_var As String
Global VerzTyp   As String
Global flgPara   As Integer

Global X           As Integer  'für die Shellabfrage
Global aktiveZeile As Variant

Sub Abspeichern(p)
    
    Dim i, j

    If p = "u" Or dateiname = "Neu" Then
       Speichern.StdDialog1.InitDir = DBPath
       Speichern.StdDialog1.DefaultExt = "vrs"
       Speichern.StdDialog1.Filter = "Verschnitt-Dateien|*.vrs|Alle Dateien|*.*"
       Speichern.StdDialog1.Action = 2
       pfaddateiname = Speichern.StdDialog1.FileName
    
       'Abbrechen v. "Speichern unter"
       If pfaddateiname = "" Then
          Exit Sub
       End If

       Else: pfaddateiname = DBPath + "\" + dateiname

    End If

    Screen.MousePointer = 11
    Open pfaddateiname For Output As #1
    ' Kennzeichen mitführen
    If Anz_Dimensionen = 1 Then
        Write #1, "1Dn"           'Kennz für eindimensional
    Else
        Write #1, "2Dn"           'Kennz für zweidimensional
    End If
    ' Zählen der Anzahl der Produkte
    AnzSpalten = 0
    For i = 1 To MaxSpalten
        If Spalte(i).Anzahl <> 0 Then
            AnzSpalten = AnzSpalten + 1
        End If
    Next i
    Write #1, AnzSpalten            'Anzahl der Produkte
    Write #1, Breite(0)                'Breite des Werkstückes
    Write #1, Breite(1)
    Write #1, Breite(2)
    Write #1, Breite(3)
    
    ' Im 2-dimensionalen Problem benötigt man die Höhe des Werkstückes
    If Anz_Dimensionen = 2 Then
        Write #1, Höhe(0)
        Write #1, Höhe(1)
        Write #1, Höhe(2)
        Write #1, Höhe(3)
    End If
    
    'Produktdefinitionen
    For i = 1 To AnzSpalten
        Write #1, Spalte(i).Breite
        Write #1, Spalte(i).Anzahl
        If Anz_Dimensionen = 2 Then
            Write #1, Spalte(i).Höhe
        End If
    Next i

    ' Im 2-dimensionalen Modell werden die manuell eingegebenen
    ' Varianten mitabgespeichert.
    If Anz_Dimensionen = 2 Then
        Write #1, AnzZeilen         ' Anzahl der Varianten
        For j = 1 To AnzZeilen
            For i = 1 To AnzSpalten
                Write #1, Varianten(j).Produkt(i)
            Next i
            Write #1, Varianten(j).Verschnitt
        Next j
    End If
    Close #1
    'reinen Dateinamen extrahieren
    länge = Len(pfaddateiname)
    For i = länge To 1 Step -1
        If Mid$(pfaddateiname, i, 1) = "\" Then
           dateiname = Right$(pfaddateiname, (länge - i))
           Exit For
        End If
    Next i

    Screen.MousePointer = 0

    CanClose = True ' Änderungen wurden erfolgreich gespeichert
                    
End Sub

Sub Einlesen()

' Einlesen aus Datendatei

    Dim i As Integer
    Dim knz_dim As Variant
    Dim Knz_Old_Version As Integer


    ' Befehlsparameter !
    If flgPara = 1 Then
       flgPara = 0
       pfaddateiname = Command$
       GoTo ParaStart
    End If


    ' Einleseparameter setzen
    Öffnen.StdDialog1.DefaultExt = "vrs"
    Öffnen.StdDialog1.InitDir = DBPath
    Öffnen.StdDialog1.Filter = "Verschnitt-Dateien|*.vrs|Alle Dateien|*.*"
    Öffnen.StdDialog1.Action = 1
    pfaddateiname = Öffnen.StdDialog1.FileName


ParaStart:

    'Keine Datei gewählt
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
    Screen.MousePointer = 11
    
    
    ' Das erste Mal wird die Datei nur gelesen, um zwischen
    ' - alter Version
    ' - Art des Verschnittproblems
    ' zu unterscheiden   ..dann wieder geschlossen

    Open pfaddateiname For Input As #1

    ' Fallunterscheidung nach Anzahl Dimensionen hin
    Input #1, knz_dim
    Anz_Dimensionen = 1      ' Vorbelegung
    Knz_Old_Version = 0      ' Vorbelegung
    If knz_dim = "1D" Or knz_dim = "1Dn" Then            'Abprüfen auf Dim-Kennzeichen
        Anz_Dimensionen = 1
    Else
        If knz_dim = "2D" Or knz_dim = "2Dn" Then
            Anz_Dimensionen = 2
        Else
            ' falls eine alte Version des Verschnittproblems vorliegt,
            ' dann gilt: Dimension=1 und striktes Lesen
            Knz_Old_Version = 1
            AnzSpalten = 25
        End If
    End If
    Close #1


    ' Das zweite Mal werden aus der Datei die Daten entsprechend
    ' dem gültigen Aufbau gelesen

    Open pfaddateiname For Input As #1

    ' Die neue Version des Dateiaufbaus wird verarbeit
    
    If Knz_Old_Version = 0 Then
        Input #1, knz_dim
        Input #1, AnzSpalten
    End If
    'Breite bzw. Höhe des Werkstückes
    Input #1, Breite(0)
    If knz_dim = "1Dn" Then
        Input #1, Breite(1)
        Input #1, Breite(2)
        Input #1, Breite(3)
    End If
    
    If Anz_Dimensionen = 2 Then
        Input #1, Höhe(0)
        If knz_dim = "2Dn" Then
         Input #1, Höhe(1)
         Input #1, Höhe(2)
         Input #1, Höhe(3)
        End If
    End If
    'Produktdefinitionen einlesen
    For i = 1 To AnzSpalten
        Input #1, Spalte(i).Breite
        Input #1, Spalte(i).Anzahl
        On Error GoTo FehlerBeimLesen
        If Anz_Dimensionen = 2 Then
            Input #1, Spalte(i).Höhe      'bei 2D zus. Höhe einlesen
            On Error GoTo FehlerBeimLesen
        End If
    Next i
    'Im 2-dimensionalen Modell muessen auch die Varianten eingelesen werden
    If Anz_Dimensionen = 2 Then
        Input #1, AnzZeilen
        For j = 1 To AnzZeilen
            For i = 1 To AnzSpalten
                Input #1, Varianten(j).Produkt(i)
                On Error GoTo FehlerBeimLesen
            Next i
            Input #1, Varianten(j).Verschnitt
            On Error GoTo FehlerBeimLesen
        Next j
    End If
    
    Close #1
    Screen.MousePointer = 0
    CanClose = True ' Datei wurde noch nicht verändert
    IsActive = True ' Datei wurde erfolgreich eingelesen


FehlerBeimLesen:
    i = MaxSpalten + 1
    j = MaxVars + 1
    Resume Next
End Sub

Sub Pif_Eintrag(pifdatei, batchdatei)
                                  
' -> Aufruf aus Form Verzeichnis u. Modul Berechnung


' Ist das WorkingDir unterschiedlich zum EXEPath, dann enthält
' der WorkingDir i.d.R. keine Pif-Datei.
' Diese wird deshalb aus dem EXEPath in das WD kopiert.

Dim pathPIF  As String * 63


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

