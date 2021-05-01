VERSION 2.00
Begin Form Verzeichnis 
   BackColor       =   &H00E0FFFF&
   Caption         =   "Verzeichnisse"
   ClientHeight    =   3450
   ClientLeft      =   960
   ClientTop       =   2565
   ClientWidth     =   6495
   Height          =   3855
   Left            =   900
   LinkTopic       =   "Form3"
   MaxButton       =   0   'False
   MinButton       =   0   'False
   ScaleHeight     =   3450
   ScaleWidth      =   6495
   Top             =   2220
   Width           =   6615
   Begin DirListBox Verzeichnis1 
      Height          =   1605
      Left            =   480
      TabIndex        =   2
      Top             =   840
      Width           =   4215
   End
   Begin CommandButton cmdAbb 
      Caption         =   "&Abbrechen"
      Height          =   375
      Left            =   5160
      TabIndex        =   1
      Top             =   720
      Width           =   1155
   End
   Begin CommandButton cmdOK 
      Caption         =   "&OK"
      Height          =   375
      Left            =   5160
      TabIndex        =   0
      Top             =   240
      Width           =   1155
   End
   Begin Frame Rahmen1 
      Height          =   3015
      Left            =   240
      TabIndex        =   3
      Top             =   120
      Width           =   4695
      Begin DriveListBox Laufwerk1 
         Height          =   315
         Left            =   240
         TabIndex        =   5
         Top             =   2520
         Width           =   4215
      End
      Begin TextBox Text1 
         Height          =   285
         Left            =   240
         TabIndex        =   4
         Text            =   "Text1"
         Top             =   360
         Width           =   4215
      End
   End
End
Sub cmdAbb_Click ()

    Unload Me

End Sub

Sub cmdOK_Click ()
 
' Sowohl die .ini als auch die .pif werden geändert.
' Die Prozedur Pif_Eintrag ist im Modul berechnung
' zu finden.


Select Case VerzTyp

   Case "DB"
      ' nur INI-Eintrag, s.u.

   Case "XA"
      XAPath = text1.Text
      Pif_Eintrag "solver.pif", "solver.bat"
      
   Case "MOPS"
      MOPSPath = text1.Text
      Pif_Eintrag "solver.pif", "solver.bat"

   Case "OMP"
      OMPPath = text1.Text
      Pif_Eintrag "solver.pif", "solver.bat"

   Case "LPSOLVE"
      LPSOLVEPath = text1.Text
      Pif_Eintrag "solver.pif", "solver.bat"

   Case "STRADA"
      STRADAPath = text1.Text
      Pif_Eintrag "solver.pif", "solver.bat"

   Case "Working"
      WorkingDir = text1.Text
      'kein Pif_Eintrag eintrag
                            
End Select


' Aktualisierung der Initialisierungsdatei .ini, die sich im
' EXEC-Pfad des VB-Programmes befindet. Sobald die Programmpfade geändert
' und mit der OK-Taste(Befehl1) bestätigt werden, wird die .ini-Datei
' geöffnet und der richtige Pfad eingetragen. Die Änderungen werden sofort
' gespeichert.

Write_ini "mischung.ini", "MISCHUNG"


Unload Verzeichnis

End Sub

Sub Form_Load ()

' verz05:  07/97
'

Dim Path As String


Height = 3900
Width = 6600
top = (screen.Height - 295 - Height) / 2
left = (screen.Width - 125 - Width) / 2
            
On Error GoTo verz_falsch

Select Case VerzTyp
   Case "DB"
      rahmen1.Caption = " Daten Verzeichnis "
      Path = DBPath

   Case "XA"
      rahmen1.Caption = " XA Verzeichnis "
      Path = XAPath

   Case "MOPS"
      rahmen1.Caption = " MOPS-Verzeichnis "
      Path = MOPSPath

   Case "OMP"
      rahmen1.Caption = "OMP-Verzeichnis"
      Path = OMPPath

   Case "LPSOLVE"
      rahmen1.Caption = "LP-Solve Verzeichnis"
      Path = LPSOLVEPath

   Case "STRADA"
      rahmen1.Caption = "STRADA Verzeichnis"
      Path = STRADAPath

   Case "Working"
      rahmen1.Caption = "Working Directory"
      Path = WorkingDir

End Select

text1.Text = Path
Verzeichnis1.Path = Path
laufwerk1 = Left(Path, 3)

On Error GoTo 0
    
Exit Sub
    
verz_falsch:
    Path = "\"
    laufwerk1 = "c:\"
    text1.Text = "c:\"
    Verzeichnis1.Path = "c:\"
    Exit Sub

End Sub

Sub Laufwerk1_Change ()
    Verzeichnis1.Path = laufwerk1.Drive
End Sub

Sub Verzeichnis1_Change ()
    text1.Text = Verzeichnis1.Path
End Sub

