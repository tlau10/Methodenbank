VERSION 2.00
Begin MDIForm MDIForm1 
   Caption         =   "Die Mischung"
   ClientHeight    =   3045
   ClientLeft      =   645
   ClientTop       =   2265
   ClientWidth     =   6975
   Height          =   3735
   Icon            =   MDIFORM1.FRX:0000
   Left            =   585
   LinkTopic       =   "MDIForm1"
   Top             =   1635
   Width           =   7095
   Begin Menu mnuDatei 
      Caption         =   "&Datei"
      Begin Menu mnuDateiNeu 
         Caption         =   "&Neu..."
      End
      Begin Menu mnuDateiÖffnen 
         Caption         =   "&Öffnen..."
      End
      Begin Menu s1 
         Caption         =   "-"
      End
      Begin Menu mnuDateiSpeichern 
         Caption         =   "&Speichern"
      End
      Begin Menu mnuDateiSpeichernunter 
         Caption         =   "Speichern &unter..."
      End
      Begin Menu s2 
         Caption         =   "-"
      End
      Begin Menu mnuDateiBeenden 
         Caption         =   "&Beenden"
      End
   End
   Begin Menu mnuOptionen 
      Caption         =   "&Optionen"
      Begin Menu mnuSolver 
         Caption         =   "&Solver"
         Begin Menu mnuSolverAuswahl 
            Caption         =   "XA Equation Style"
            Index           =   1
         End
         Begin Menu mnuSolverAuswahl 
            Caption         =   "XA MPS-Format"
            Index           =   2
         End
         Begin Menu mnuSolverAuswahl 
            Caption         =   "MOPS MPS-Format"
            Index           =   3
         End
         Begin Menu mnuSolverAuswahl 
            Caption         =   "LP-Solve LP-Format"
            Enabled         =   0   'False
            Index           =   4
         End
         Begin Menu mnuSolverAuswahl 
            Caption         =   "LP-Solve MPS-Format"
            Enabled         =   0   'False
            Index           =   5
         End
         Begin Menu mnuSolverAuswahl 
            Caption         =   "OMP"
            Enabled         =   0   'False
            Index           =   6
            Visible         =   0   'False
         End
         Begin Menu mnuSolverAuswahl 
            Caption         =   "Strada MPS-Format"
            Index           =   7
         End
      End
      Begin Menu mnuVerzeichnis 
         Caption         =   "&Verzeichnisse"
         Begin Menu mnuVerzeichnisAuswahl 
            Caption         =   "Datenpfad"
            Index           =   0
         End
         Begin Menu mnuVerzeichnisAuswahl 
            Caption         =   "XA-Pfad"
            Index           =   1
         End
         Begin Menu mnuVerzeichnisAuswahl 
            Caption         =   "MOPS-Pfad"
            Index           =   2
         End
         Begin Menu mnuVerzeichnisAuswahl 
            Caption         =   "LP_Solve-Pfad"
            Index           =   3
         End
         Begin Menu mnuVerzeichnisAuswahl 
            Caption         =   "STRADA-Pfad"
            Index           =   4
         End
         Begin Menu mnuVerzeichnisAuswahl 
            Caption         =   "WorkingDir"
            Index           =   5
         End
      End
      Begin Menu mnuKlang 
         Caption         =   "&Klang"
      End
   End
   Begin Menu mnuFenster 
      Caption         =   "&Fenster"
      Begin Menu mnuFensterEingabe 
         Caption         =   "&Eingabefenster"
      End
      Begin Menu mnuFensterAusgabe 
         Caption         =   "&Ausgabefenster"
      End
   End
   Begin Menu mnuHilfe 
      Caption         =   "&Hilfe"
      Begin Menu mnuHilfeInhalt 
         Caption         =   "&Inhalt"
      End
      Begin Menu mnuHilfeSuchen 
         Caption         =   "&Suchen"
      End
      Begin Menu linie40 
         Caption         =   "-"
      End
      Begin Menu mnuHilfeInfo 
         Caption         =   "I&nfo..."
      End
   End
End
Sub MDIForm_Load ()


'-- Aufführungspfad zur Entwicklungszeit und zur Laufzeit
    
EXEPath = "l:\besf\or_mbank\mischung\exec"
'EXEPath = App.Path


'-- Größe und Positionierung des MDI-Fensters
normhoehe = 7300
normweite = 10700
    
Height = IIf(screen.Height < normhoehe, screen.Height, normhoehe)
Width = IIf(screen.Width < normweite, screen.Width, normweite)
     
top = (screen.Height - Height) / 2
left = (screen.Width - Width) / 2


dateiname = "Neudatei"
'rueckschritt = false
geladen = False        ' Flag, das anzeigt, ob Datei gelesen wurde

MDIFORM1.Caption = "Die Mischung - [" & dateiname & "]"


solver = "XAeqs"       ' Vorbelegung
'solver = "STRADA"
mnuSolverAuswahl(1).Checked = True

mnuFensterEingabe.Enabled = False
mnuFensterAusgabe.Enabled = False

'-- Klang-System
Klang = 0
mnuKlang.Checked = False


'-- INI-Datei: Pfade einlesen
Read_ini "mischung.ini", "MISCHUNG"


'-- Parameter
flgPara = 0
If Command <> "" Then
   flgPara = 1
   dateiname = Command$

   Von_Platte        'Sub in global.bas
   MDIFORM1.Caption = "Die Mischung - [" & Command$ & "]"
   mnuFensterEingabe.Enabled = True
   'mnuFensterAusgabe.Enabled = False
   frmEingabe.Show 0
End If
     

End Sub

Sub mnuDateiBeenden_Click ()
    End
End Sub

Sub mnuDateiNeu_Click ()

    Unload frmEingabe
    Unload frmAusgabe

    geladen = False
    dateiname = "Neudatei"
    MDIFORM1.Caption = "Die Mischung - [" & dateiname & "]"
    
    mnuFensterEingabe.Enabled = True
    mnuFensterAusgabe.Enabled = False

    Legende = ""  ' leere Modellbeschreibung

    frmEingabe.Show 0

End Sub

Sub mnuDateiÖffnen_Click ()

    Unload frmEingabe
    Unload frmAusgabe

    Von_Platte        ' Sub in global.bas

    MDIFORM1.Caption = "Die Mischung - [" & dateiname & "]"

    mnuFensterEingabe.Enabled = True
    mnuFensterAusgabe.Enabled = False

    frmEingabe.Show 0
    
End Sub

Sub mnuDateiSpeichern_Click ()

    Auf_Platte ("n")

    MDIFORM1.Caption = "Die Mischung - [" & dateiname & "]"

End Sub

Sub mnuDateiSpeichernunter_Click ()

    Auf_Platte ("u")    'Sub in mi_global
    
    MDIFORM1.Caption = "Die Mischung - [" & dateiname & "]"

End Sub

Sub mnuFensterAusgabe_Click ()
    
Select Case solver
   Case "XAeqs"
      frmAusgabe.Show 0
   Case "XAmps"
      frmAusgabe.Show 0
   Case Else
      MsgBox ("Is nich !")
End Select

End Sub

Sub mnuFensterEingabe_Click ()

    frmEingabe.Show 0

End Sub

Sub mnuHilfeInfo_Click ()
    Infofenster.Show 0
End Sub

Sub mnuHilfeInhalt_Click ()

    winhelp hWnd, EXEPath + "\" + App.HelpFile, 3, Null

End Sub

Sub mnuHilfeSuchen_Click ()
    
    winhelp hWnd, EXEPath + "\" + App.HelpFile, 261, ByVal Chr$(0)

End Sub

Sub mnuKlang_Click ()

If Klang = 0 Then
   If waveOutGetNumDevs() = 0 Then
      MsgBox "Es ist kein Klangausgabegerät vorhanden !"
   Else
      Klang = 1
      mnuKlang.Checked = True
   End If
Else
   Klang = 0
   mnuKlang.Checked = False
End If

End Sub

Sub mnuSolverAuswahl_Click (index As Integer)

Select Case index
   Case 1
      solver = "XAeqs"
   Case 2
      solver = "XAmps"
   Case 3
      solver = "MOPS"
   Case 4
      solver = "LPSOLVEeqs"
   Case 5
      solver = "LPSOLVEmps"
   Case 6
      solver = "OMP"
   Case 7
      solver = "STRADA"
   Case Else
      solver = "NIX"
End Select

mnuSolverAuswahl(1).Checked = False
mnuSolverAuswahl(2).Checked = False
mnuSolverAuswahl(3).Checked = False
mnuSolverAuswahl(4).Checked = False
mnuSolverAuswahl(5).Checked = False
mnuSolverAuswahl(6).Checked = False
mnuSolverAuswahl(7).Checked = False

'If index = 5 Then
'   index = 1
'   MsgBox "Noch nicht implementiert !"
'End If

mnuSolverAuswahl(index).Checked = True

End Sub

Sub mnuVerzeichnisAuswahl_Click (index As Integer)

Select Case index
   Case 0
      verztyp = "DB"
   Case 1
      verztyp = "XA"
   Case 2
      verztyp = "MOPS"
   Case 3
      verztyp = "LPSOLVE"
   Case 4
      verztyp = "STRADA"
   Case 5
      verztyp = "Working"
   Case Else
      verztyp = "NIX"
End Select

verzeichnis.Show 1

End Sub

