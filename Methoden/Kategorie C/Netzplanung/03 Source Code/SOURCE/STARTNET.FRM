VERSION 2.00
Begin MDIForm STARTFORMNETZPLAN 
   Caption         =   "Netzplantechnik"
   ClientHeight    =   2430
   ClientLeft      =   600
   ClientTop       =   3075
   ClientWidth     =   6135
   Height          =   3120
   Icon            =   STARTNET.FRX:0000
   Left            =   540
   LinkTopic       =   "MDIForm1"
   Top             =   2445
   Width           =   6255
   Begin Menu MenuDatei 
      Caption         =   "&Datei"
      WindowList      =   -1  'True
      Begin Menu MDateiNeu 
         Caption         =   "&Neu..."
      End
      Begin Menu MDateiÖffnen 
         Caption         =   "Ö&ffnen..."
      End
      Begin Menu MDateiSchließen 
         Caption         =   "S&chließen"
      End
      Begin Menu line10 
         Caption         =   "-"
      End
      Begin Menu MDateiSpeichern 
         Caption         =   "&Speichern"
      End
      Begin Menu MDateiSpeichernUnter 
         Caption         =   "Speichern &unter..."
      End
      Begin Menu line11 
         Caption         =   "-"
      End
      Begin Menu MDateiDrucken 
         Caption         =   "&Drucken..."
         Enabled         =   0   'False
      End
      Begin Menu line12 
         Caption         =   "-"
      End
      Begin Menu MDateiBeenden 
         Caption         =   "&Beenden"
      End
   End
   Begin Menu menuE 
      Caption         =   "&Optionen"
      Begin Menu menuSolver 
         Caption         =   "&Solver"
         Begin Menu menuSolverAuswahl 
            Caption         =   "XA Equation Style"
            Index           =   1
         End
         Begin Menu menuSolverAuswahl 
            Caption         =   "LP_Solve"
            Index           =   2
         End
         Begin Menu menuSolverAuswahl 
            Caption         =   "Mops"
            Enabled         =   0   'False
            Index           =   3
         End
         Begin Menu menuSolverAuswahl 
            Caption         =   "XAmps"
            Enabled         =   0   'False
            Index           =   4
         End
         Begin Menu menuSolverAuswahl 
            Caption         =   "OMP"
            Enabled         =   0   'False
            Index           =   5
         End
         Begin Menu menuSolverAuswahl 
            Caption         =   "Dummy"
            Enabled         =   0   'False
            Index           =   6
            Visible         =   0   'False
         End
      End
      Begin Menu menuV 
         Caption         =   "&Verzeichnisse"
         Begin Menu menuVerzAuswahl 
            Caption         =   "Datenverzeichnis"
            Index           =   1
         End
         Begin Menu menuVerzAuswahl 
            Caption         =   "XA-Verzeichnis"
            Index           =   2
         End
         Begin Menu menuVerzAuswahl 
            Caption         =   "MOPS-Verzeichnis"
            Enabled         =   0   'False
            Index           =   3
         End
         Begin Menu menuVerzAuswahl 
            Caption         =   "LPSOLVE-Verzeichnis"
            Enabled         =   0   'False
            Index           =   4
         End
         Begin Menu menuVerzAuswahl 
            Caption         =   "OMP-Verzeichnis"
            Enabled         =   0   'False
            Index           =   5
         End
         Begin Menu menuVerzAuswahl 
            Caption         =   "WorkingDir"
            Index           =   6
         End
         Begin Menu menuVerzAuswahl 
            Caption         =   "Ereignisgraph-Pfad"
            Index           =   7
            Visible         =   0   'False
         End
      End
   End
   Begin Menu MenuBearbeiten 
      Caption         =   "&Bearbeiten"
      Begin Menu MBearbeitenEditor 
         Caption         =   "&Netzplan editieren"
      End
   End
   Begin Menu MenuHilfe 
      Caption         =   "&Hilfe"
      Begin Menu MHilfeInhalt 
         Caption         =   "&Inhalt"
         Enabled         =   0   'False
      End
      Begin Menu MHilfeSuchen 
         Caption         =   "&Suchen"
         Enabled         =   0   'False
      End
      Begin Menu line40 
         Caption         =   "-"
      End
      Begin Menu MHilfeInfo 
         Caption         =   "I&nfo..."
      End
   End
End
' STARTNET.FRM
Option Explicit

Sub MBearbeitenEditor_Click ()

    If datopenflag = 0 Then
        newnet.Show 0
    Else
        Unload GRID_IN    'löscht grid_in vor dem edit. WS 96/97
        newnet.Show 0     'ruft newnet auf. WS 96/97
        'nachfolgende Anweisungen auskommentiert
        'verlangt sonst erst das Anlegen einer Datei WS96/97
        'FEHLER1.Fehlermeldung.Caption = "Es ist keine Datei" + Chr$(13) + "geöffnet"
        'FEHLER1.Show 1
    End If

End Sub

Sub MDateiBeenden_Click ()

'Dim kanal As Variant
'Dim l0044 As Variant
'Dim l004C As Variant

    If datopenflag = 0 Then
       End
    Else
       'l004C = dateiname   'IO.StdDialog1.Filename
       'FEHLER1.Fehlermeldung.Caption = "Die Datei" + Chr$(13) + l004C + Chr$(13) + "ist noch geöffnet"
       'FEHLER1.Show 1
        
       End   'nun ebenso !!!
    End If

End Sub

Sub MDateiDrucken_Click ()
    IO.StdDialog1.Action = 5
End Sub

Sub MDateiNeu_Click ()
Dim l0056 As Variant
    
    Dim a
    If datopenflag = 0 Then
        newnet.Show 0
    Else
        'l0056 = dateiname   'IO.StdDialog1.Filename
        'FEHLER1.Fehlermeldung.Caption = "Die Datei" + Chr$(13) + l0056 + Chr$(13) + "ist noch geöffnet"
        'FEHLER1.Show 1
        Call MDateiSpeichernUnter_Click
        Call MDateiSchließen_Click
        newnet.Show 0
    End If
End Sub

Sub MDateiÖffnen_Click ()

Dim l005A As String

    If datopenflag <> 1 Then

        'IO.StdDialog1.Action = 1
        
        Datei_oeffnen
      
        If IO.StdDialog1.Filename <> "" Then
            akt_Datnam
            datopenflag = 1
            status = 1

            newnet.Show 0
        End If

    Else
        Unload newnet

        l005A = dateiname   'IO.StdDialog1.Filename
        FEHLER1.Fehlermeldung.Caption = "Die Datei" + Chr$(13) + l005A + Chr$(13) + "ist noch geöffnet"
        FEHLER1.Show 1
    End If
End Sub

Sub MDateiSchließen_Click ()

Dim l005E As Integer    ' Variablenname ist absolut Klasse !!
    
    For l005E = 1 To Val(AnzahlKnoten)
        U(l005E).do_i = 0
        U(l005E).do_j = 0
        U(l005E).iTyp = 0
        U(l005E).jTyp = 0
        U(l005E).dnorm = 0
        U(l005E).dmin = 0
        U(l005E).w = 0
    Next l005E

    datopenflag = 0

    STARTFORMNETZPLAN.Caption = "Netzplantechnik - []"

    Unload newnet
    Unload NetzOut
    Unload GRID_IN
    Unload Infofenster

End Sub

Sub MDateiSpeichern_Click ()
     Speichern_HD
End Sub

Sub MDateiSpeichernUnter_Click ()

    If Right(DBPath, 1) = "\" Then
        IO.StdDialog1.InitDir = Left(DBPath, Len(DBPath) - 1)
    Else
        IO.StdDialog1.InitDir = DBPath
    End If
    IO.StdDialog1.Action = 2
    If IO.StdDialog1.Filename <> "" Then
        Speichern_HD
        akt_Datnam
    End If

End Sub

Sub MDIForm_Load ()

' Der hier vorliegende Code ist das Ergebnis einer Dekompilierung
' und anschließender Nachbearbeitung. Der Original-Code (bis auf
' startnet.frm) ist in der Datei netz.prn abgelegt. Dort sind auch
' noch etwaige Kommentare zu finden, deren zu Übertragen ich zu
' faul war.
'
' Die Hauptform wurde in eine MDI-Form umgewandelt.
' -> Vorgehen: Abspeichern in Textform, manuelle Eingriffe
'    (.._form ändern, Anzeigen entfernen) usw.
'
' Jan. 96


Dim Normhoehe, Normweite As Integer


'-- Pfadeinstellung
EXEPath = "l:\besf\or_mbank\netzplan\exec"  ' >> Entwicklung
'EXEPath = app.Path                       ' zur Laufzeit
 

'-- Größe und Positionierung des MDI-Fensters
Normhoehe = 8000
Normweite = 11000
    
Height = IIf(screen.Height < Normhoehe, screen.Height, Normhoehe)
Width = IIf(screen.Width < Normweite, screen.Width, Normweite)
     
top = (screen.Height - Height) / 2
left = (screen.Width - Width) / 2


dateiname = "Neudatei"
'rueckschritt = "NEIN"
   
datopenflag = 0    ' Flag: 1=Datei geladen, 0=nicht geladen
status = 0


STARTFORMNETZPLAN.Caption = "Netzplantechnik - [" & dateiname & "]"

solver = "XAeqs"       ' Vorbelegung
menuSolverAuswahl(1).Checked = True
'menuFensterEingabe.Enabled = False
'menuFensterAusgabe.Enabled = False


'-- INI-Datei: Pfade sowie Name des Visu-Programms einlesen
'Read_ini "netz.ini", "NETZ"

' Default-Einstellung
VisuProg = "\"

DBPath = "\"
XAPath = "\"
OMPPath = "\"
MOPSPath = "\"
LPSOLVEPath = "\"
'STRADAPath = "\"
WorkingDir = "\"


VisuProg = ReadString(EXEPath + "\netz.ini", "Einstellungen", "VisuProg")

DBPath = ReadString(EXEPath + "\netz.ini", "Pfade", "dbpath")
XAPath = ReadString(EXEPath + "\netz.ini", "Pfade", "xapath")
MOPSPath = ReadString(EXEPath + "\netz.ini", "Pfade", "mopspath")
LPSOLVEPath = ReadString(EXEPath + "\netz.ini", "Pfade", "lpsolvepath")
'STRADAPath = ReadString(EXEPath + "\netz.ini", "Pfade", "stradapath")

WorkingDir = ReadString(EXEPath + "\netz.ini", "Pfade", "workingdir")





End Sub

Sub menuSolverAuswahl_Click (index As Integer)


Select Case index
   Case 1
      solver = "XAeqs"
   Case 2
      solver = "LPSOLVE"
   Case 3
      solver = "MOPS"
   Case 4
      solver = "XAmps"
   Case 5
      solver = "OMP"
   Case Else
      solver = "NIX"  'DUMMY
End Select

menuSolverAuswahl(1).Checked = False
menuSolverAuswahl(2).Checked = False
menuSolverAuswahl(3).Checked = False
menuSolverAuswahl(4).Checked = False
menuSolverAuswahl(5).Checked = False

menuSolverAuswahl(index).Checked = True

End Sub

Sub menuV_Click ()
    'Verzeichnis.Show 1
End Sub

Sub menuVerzAuswahl_Click (index As Integer)

Select Case index
   Case 1
      verztyp = "DB"
   Case 2
      verztyp = "XA"
   Case 3
      verztyp = "MOPS"
   Case 4
      verztyp = "LPSOLVE"
   Case 5
      verztyp = "OMP"
   Case 6
      verztyp = "Working"
   Case 7
      verztyp = "GRAPH"
   Case Else
      verztyp = "NIX"
End Select

Verzeichnis.Show 1

End Sub

Sub MHilfeInfo_Click ()

    Infofenster.Show 0

End Sub

