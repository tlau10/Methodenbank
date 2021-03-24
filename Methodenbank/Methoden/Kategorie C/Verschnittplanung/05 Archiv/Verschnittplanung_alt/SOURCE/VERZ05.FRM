VERSION 5.00
Begin VB.Form Verzeichnis 
   Appearance      =   0  '2D
   BackColor       =   &H8000000A&
   Caption         =   "Verzeichnisse"
   ClientHeight    =   3450
   ClientLeft      =   960
   ClientTop       =   2565
   ClientWidth     =   6495
   BeginProperty Font 
      Name            =   "MS Sans Serif"
      Size            =   8.25
      Charset         =   0
      Weight          =   700
      Underline       =   0   'False
      Italic          =   0   'False
      Strikethrough   =   0   'False
   EndProperty
   ForeColor       =   &H80000008&
   LinkTopic       =   "Form3"
   MaxButton       =   0   'False
   MinButton       =   0   'False
   PaletteMode     =   1  'ZReihenfolge
   ScaleHeight     =   3450
   ScaleWidth      =   6495
   Begin VB.CommandButton cmdAbb 
      Appearance      =   0  '2D
      Caption         =   "&Abbrechen"
      Height          =   375
      Left            =   5160
      TabIndex        =   1
      Top             =   720
      Width           =   1155
   End
   Begin VB.CommandButton cmdOK 
      Appearance      =   0  '2D
      Caption         =   "&OK"
      Height          =   375
      Left            =   5160
      TabIndex        =   0
      Top             =   240
      Width           =   1155
   End
   Begin VB.Frame Rahmen1 
      Appearance      =   0  '2D
      BackColor       =   &H8000000A&
      ForeColor       =   &H80000008&
      Height          =   3015
      Left            =   240
      TabIndex        =   2
      Top             =   120
      Width           =   4695
      Begin VB.TextBox Text1 
         Height          =   285
         Left            =   240
         TabIndex        =   5
         Text            =   "Text1"
         Top             =   360
         Width           =   4215
      End
      Begin VB.DirListBox Verzeichnis1 
         Height          =   1665
         Left            =   240
         TabIndex        =   4
         Top             =   720
         Width           =   4215
      End
      Begin VB.DriveListBox Laufwerk1 
         Appearance      =   0  '2D
         Height          =   315
         Left            =   240
         TabIndex        =   3
         Top             =   2520
         Width           =   4215
      End
   End
End
Attribute VB_Name = "Verzeichnis"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Private Sub cmdAbb_Click()

    Unload Me

End Sub

Private Sub cmdOK_Click()
 
' Sowohl die .ini als auch die .pif werden geändert.
' Die Prozedur Pif_Eintrag ist im Modul berechnung
' zu finden.


Select Case VerzTyp

   Case "DB"
      ' nur INI-Eintrag, s.u.

   Case "XA"
      XAPath = Text1.Text
      Pif_Eintrag "solver.pif", "solver.bat"
      
   Case "MOPS"
      MOPSPath = Text1.Text
      Pif_Eintrag "solver.pif", "solver.bat"

   Case "OMP"
      OMPPath = Text1.Text
      Pif_Eintrag "solver.pif", "solver.bat"

   Case "LPSOLVE"
      LPSOLVEPath = Text1.Text
      Pif_Eintrag "solver.pif", "solver.bat"

   Case "STRADA"
      STRADAPath = Text1.Text
      Pif_Eintrag "solver.pif", "solver.bat"

   Case "Working"
      WorkingDir = Text1.Text
      'kein Pif_Eintrag eintrag
                            
End Select


' Aktualisierung der Initialisierungsdatei .ini, die sich im
' EXEC-Pfad des VB-Programmes befindet. Sobald die Programmpfade geändert
' und mit der OK-Taste(Befehl1) bestätigt werden, wird die .ini-Datei
' geöffnet und der richtige Pfad eingetragen. Die Änderungen werden sofort
' gespeichert.

Write_ini "verschn.ini", "VERSCHNITT"


Unload Verzeichnis

End Sub

Private Sub Form_Load()

' verz05:  07/97
'

Dim Path As String


Height = 3900
Width = 6600
Top = (Screen.Height - 295 - Height) / 2
Left = (Screen.Width - 125 - Width) / 2
            
On Error GoTo verz_falsch

Select Case VerzTyp
   Case "DB"
      Rahmen1.Caption = " Daten Verzeichnis "
      Path = DBPath

   Case "XA"
      Rahmen1.Caption = " XA Verzeichnis "
      Path = XAPath

   Case "MOPS"
      Rahmen1.Caption = " MOPS-Verzeichnis "
      Path = MOPSPath

   Case "OMP"
      Rahmen1.Caption = "OMP-Verzeichnis"
      Path = OMPPath

   Case "LPSOLVE"
      Rahmen1.Caption = "LP-Solve Verzeichnis"
      Path = LPSOLVEPath

   Case "STRADA"
      Rahmen1.Caption = "STRADA Verzeichnis"
      Path = STRADAPath

   Case "Working"
      Rahmen1.Caption = "Working Directory"
      Path = WorkingDir

End Select

Text1.Text = Path
Verzeichnis1.Path = Path
Laufwerk1 = Left(Path, 3)

On Error GoTo 0
    
Exit Sub
    
verz_falsch:
    Path = "\"
    Laufwerk1 = "c:\"
    Text1.Text = "c:\"
    Verzeichnis1.Path = "c:\"
    Exit Sub

End Sub

Private Sub Laufwerk1_Change()
    Verzeichnis1.Path = Laufwerk1.Drive
End Sub

Private Sub Verzeichnis1_Change()
    Text1.Text = Verzeichnis1.Path
End Sub

