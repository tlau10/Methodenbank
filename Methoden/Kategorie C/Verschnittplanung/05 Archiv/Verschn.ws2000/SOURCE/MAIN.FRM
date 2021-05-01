VERSION 5.00
Begin VB.MDIForm Hauptfenster 
   Appearance      =   0  '2D
   BackColor       =   &H8000000C&
   Caption         =   "Verschnitt-Problematik"
   ClientHeight    =   2430
   ClientLeft      =   840
   ClientTop       =   2535
   ClientWidth     =   4965
   HelpContextID   =   6
   Icon            =   "MAIN.frx":0000
   LinkTopic       =   "MDIForm1"
   Begin VB.Menu mnuDatei 
      Caption         =   "&Datei"
      HelpContextID   =   7
      Begin VB.Menu mnuDateiNeu 
         Caption         =   "&Neu..."
         Shortcut        =   ^N
      End
      Begin VB.Menu mnuDatei÷ffnen 
         Caption         =   "&÷ffnen..."
         Shortcut        =   ^O
      End
      Begin VB.Menu mnuDateiSchlieﬂen 
         Caption         =   "&Schlieﬂen"
         Shortcut        =   ^L
      End
      Begin VB.Menu s1 
         Caption         =   "-"
      End
      Begin VB.Menu mnuDateiSpeichern 
         Caption         =   "S&peichern"
         Shortcut        =   ^S
      End
      Begin VB.Menu mnuDateiSpeichernUnter 
         Caption         =   "Speichern &unter..."
         Shortcut        =   ^A
      End
      Begin VB.Menu s2 
         Caption         =   "-"
      End
      Begin VB.Menu mnuDateiBeenden 
         Caption         =   "&Beenden"
      End
   End
   Begin VB.Menu mnuOptionen 
      Caption         =   "&Optionen"
      Begin VB.Menu mnuSolver 
         Caption         =   "&Solver"
         Begin VB.Menu mnuSolverAuswahl 
            Caption         =   "XA Equation Style"
            Index           =   1
         End
         Begin VB.Menu mnuSolverAuswahl 
            Caption         =   "XA MPS-Format"
            Index           =   2
         End
         Begin VB.Menu mnuSolverAuswahl 
            Caption         =   "MOPS MPS-Format"
            Index           =   3
         End
         Begin VB.Menu mnuSolverAuswahl 
            Caption         =   "LP_Solve MPS-Format"
            Index           =   4
         End
         Begin VB.Menu mnuSolverAuswahl 
            Caption         =   "OMP"
            Index           =   5
            Visible         =   0   'False
         End
      End
      Begin VB.Menu mnuVerz 
         Caption         =   "&Verzeichnisse"
         Begin VB.Menu mnuVerzAuswahl 
            Caption         =   "Datenpfad"
            Index           =   1
         End
         Begin VB.Menu mnuVerzAuswahl 
            Caption         =   "XA-Verzeichnis"
            Index           =   2
         End
         Begin VB.Menu mnuVerzAuswahl 
            Caption         =   "MOPS-Verzeichnis"
            Index           =   3
         End
         Begin VB.Menu mnuVerzAuswahl 
            Caption         =   "LP_Solve-Verzeichnis"
            Index           =   4
         End
         Begin VB.Menu mnuVerzAuswahl 
            Caption         =   "OMP-Verzeichnis"
            Enabled         =   0   'False
            Index           =   5
            Visible         =   0   'False
         End
         Begin VB.Menu mnuVerzAuswahl 
            Caption         =   "STRADA-Verzeichnis"
            Enabled         =   0   'False
            Index           =   6
            Visible         =   0   'False
         End
         Begin VB.Menu mnuVerzAuswahl 
            Caption         =   "WorkingDir"
            Index           =   7
         End
      End
      Begin VB.Menu line31 
         Caption         =   "-"
         Visible         =   0   'False
      End
      Begin VB.Menu mnuOptLoesZwang 
         Caption         =   "&Lˆsung erzwingen"
         Enabled         =   0   'False
         Visible         =   0   'False
      End
   End
   Begin VB.Menu mnuFenster 
      Caption         =   "&Fenster"
      Begin VB.Menu mnuFensterEingabe 
         Caption         =   "&Eingabefenster"
         Enabled         =   0   'False
         Visible         =   0   'False
      End
      Begin VB.Menu mnuFensterVariante 
         Caption         =   "&Variantenfenster"
         Enabled         =   0   'False
         Visible         =   0   'False
      End
      Begin VB.Menu mnuFensterAusgabe 
         Caption         =   "&Ausgabefenster"
         Enabled         =   0   'False
         Visible         =   0   'False
      End
      Begin VB.Menu Aktives_Fenster 
         Caption         =   "&Aktives Fenster"
         WindowList      =   -1  'True
      End
   End
   Begin VB.Menu mnuHilfe 
      Caption         =   "&Hilfe"
      Begin VB.Menu mnuHilfeInhalt 
         Caption         =   "&Inhalt"
         HelpContextID   =   1
      End
      Begin VB.Menu line41 
         Caption         =   "-"
      End
      Begin VB.Menu mnuHilfeInfo 
         Caption         =   "I&nfo..."
      End
   End
End
Attribute VB_Name = "Hauptfenster"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False

Private Sub MDIForm_Load()

' Im SS97 wurden Funktionen der Professional-Version verwendet.
' Um die Standard-Version von VB 'aufzur¸sten' benˆtigt man die
' Datei VB.LIC.
' Eine Beschaffungsmˆglichkeit besteht darin, eine evtl. vorhandene
' VC.LIC (MS C++) zu kopieren.   (..der FH-Untergrund)


   Dim i As Integer
    

   '-- PFADEINSTELLUNG
   ' - zur Entwicklungzeit
   ' - bzw. zur Laufzeit (-> akt. Pfad)
    
   EXEPath = "L:\Besf\OR_MBank\Verschn\EXEC"
   'EXEPath = App.Path                    ' >>> Laufzeitpfad
    

   '--
   App.HelpFile = EXEPath + "\Verschn.Hlp"


   '-- Grˆﬂe und Positionierung des MDI-Fensters
   normhoehe = 8000
   normweite = 9800
    
   Height = IIf(Screen.Height < normhoehe, Screen.Height, normhoehe)
   Width = IIf(Screen.Width < normweite, Screen.Width, normweite)
     
   Top = (Screen.Height - Height) / 2
   Left = (Screen.Width - Width) / 2

                    
    ' Initialisierung
    Anz_Dimensionen = 1
    ' ??
    dateiname = "Neu"
    IsActive = False
    
    Hauptfenster.Caption = "Verschnittplanung"
    
    
    ' Vorbelegungen
    Solver_var = "XAeqs"
    mnuSolverAuswahl(1).Checked = True   ' H‰ckchen anbringen


    ' INI-Datei: Pfade einlesen
    Read_ini "verschn.ini", "VERSCHNITT"
                                     

    ' Initialisieren der internen Tabelle
    For i = 1 To MaxSpalten
        Spalte(i).Breite = 0
        Spalte(i).Anzahl = 0
        Spalte(i).Hˆhe = 0
    Next i
    Breite = 0


    ' Befehlszeilen-Parameter
    flgPara = 0
    If Command <> "" Then
      flgPara = 1
      Einlesen   ' Fkt. in global.bas
      
      Hauptfenster.Caption = "Verschnittplanung - [" & dateiname & "]"
      Eingabe.Show 0
    End If
 
End Sub

Private Sub MDIForm_Unload(Cancel As Integer)
    End
End Sub

Private Sub mnuDateiBeenden_Click()
    
    Unload Hauptfenster

End Sub

Private Sub mnuDateiNeu_Click()
    Unload Variante
    Unload Eingabe
    Unload Ausgabe
    dateiname = "Neu"
    Hauptfenster.Caption = "Verschnittplanung - [" & dateiname & "]"
    Eingabe.Show 0
    mnuFensterEingabe.Enabled = True
    mnuFensterVariante.Enabled = False
    mnuFensterAusgabe.Enabled = False
End Sub

Private Sub mnuDatei÷ffnen_Click()

    If IsActive = True Then
        Unload Eingabe
    End If

    If IsActive = False Then
        Einlesen   ' Fkt. in global.bas
        If IsActive = True Then
            Hauptfenster.Caption = "Verschnittplanung - [" & dateiname & "]"
            Eingabe.Show 0
            mnuFensterEingabe.Enabled = True
        End If
    End If
    
    mnuFensterVariante.Enabled = False
    mnuFensterAusgabe.Enabled = False
End Sub

Private Sub mnuDateiSchlieﬂen_Click()
    
    Unload Eingabe

End Sub

Private Sub mnuDateiSpeichern_Click()

    If IsActive = True Then
        Abspeichern ("n")
        Hauptfenster.Caption = "Verschnittplanung - [" & dateiname & "]"
    End If

End Sub

Private Sub mnuDateiSpeichernUnter_Click()

    If IsActive = True Then
        Abspeichern ("u")
        Hauptfenster.Caption = "Verschnittplanung - [" & dateiname & "]"
    End If

End Sub

Private Sub mnuFensterAusgabe_Click()

    Ausgabe.Show 0

End Sub

Private Sub mnuFensterEingabe_Click()
     
    Eingabe.Show 0

End Sub

Private Sub mnuFensterVariante_Click()
    Variante.Show 0
End Sub

Private Sub mnuHilfeInfo_Click()
    Infofenster.Show 0
End Sub

Private Sub mnuHilfeInfo2_Click()
    Infofenster2.Show 0
End Sub

Private Sub mnuHilfeInhalt_Click()
        
Const HELP_KEY = &H101

Hilfefenster.Hilfedialog.HelpFile = App.HelpFile

Hilfefenster.Hilfedialog.HelpCommand = HELP_KEY
Hilfefenster.Hilfedialog.HelpKey = "Inhalt"

Hilfefenster.Hilfedialog.Action = 6


End Sub



Private Sub mnuOptLoesZwang_Click()

If flg_LoesZwang = 0 Then
   flg_LoesZwang = 1
   mnuOptLoesZwang.Checked = True
Else
   flg_LoesZwang = 0
   mnuOptLoesZwang.Checked = False
End If

End Sub

'Private Sub mnuSolverAuswahl_Click(Index As Integer)
Sub mnuSolverAuswahl_Click(Index As Integer)

Select Case Index
   Case 1
      Solver_var = "XAeqs"
   Case 2
      Solver_var = "XAmps"
   Case 3
      Solver_var = "MOPS"
   Case 4
      Solver_var = "LPSOLVE"
   Case 5
      Solver_var = "OMP"
   Case Else
      Solver_var = "NIX"  'DUMMY
End Select

mnuSolverAuswahl(1).Checked = False
mnuSolverAuswahl(2).Checked = False
mnuSolverAuswahl(3).Checked = False
mnuSolverAuswahl(4).Checked = False
mnuSolverAuswahl(5).Checked = False

mnuSolverAuswahl(Index).Checked = True

End Sub

Private Sub mnuVerzAuswahl_Click(Index As Integer)


' Zuordnung
Select Case Index
    Case 1
        VerzTyp = "DB"
    Case 2
        VerzTyp = "XA"
    Case 3
        VerzTyp = "MOPS"
    Case 4
        VerzTyp = "LPSOLVE"
    Case 5
        VerzTyp = "OMP"
    Case 6
        VerzTyp = "STRADA"
    Case 7
        VerzTyp = "Working"
    Case Else
        VerzTyp = "NIX"
End Select

Verzeichnis.Show 1

End Sub

