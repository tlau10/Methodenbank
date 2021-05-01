VERSION 5.00
Object = "{5E9E78A0-531B-11CF-91F6-C2863C385E30}#1.0#0"; "msflxgrd.ocx"
Object = "{F9043C88-F6F2-101A-A3C9-08002B2F49FB}#1.2#0"; "comdlg32.ocx"
Begin VB.Form Tabelle 
   Caption         =   "Tabelle"
   ClientHeight    =   5625
   ClientLeft      =   2085
   ClientTop       =   3420
   ClientWidth     =   8610
   Icon            =   "Tabelle.frx":0000
   LinkTopic       =   "Form2"
   MDIChild        =   -1  'True
   ScaleHeight     =   5625
   ScaleWidth      =   8610
   ShowInTaskbar   =   0   'False
   Begin MSComDlg.CommonDialog CommonDialog3 
      Left            =   4920
      Top             =   0
      _ExtentX        =   847
      _ExtentY        =   847
      _Version        =   393216
   End
   Begin MSFlexGridLib.MSFlexGrid matthias 
      Height          =   2085
      Left            =   120
      TabIndex        =   0
      Top             =   840
      Width           =   8295
      _ExtentX        =   14631
      _ExtentY        =   3678
      _Version        =   393216
      Rows            =   3
      Cols            =   3
      FixedRows       =   2
      MergeCells      =   1
   End
   Begin VB.Frame Frame4 
      Caption         =   "‹bersicht"
      Height          =   5445
      Left            =   0
      TabIndex        =   1
      Top             =   120
      Width           =   8595
      Begin MSFlexGridLib.MSFlexGrid robert1 
         Height          =   2085
         Left            =   120
         TabIndex        =   2
         Top             =   3000
         Width           =   8295
         _ExtentX        =   14631
         _ExtentY        =   3678
         _Version        =   393216
         Rows            =   3
         Cols            =   3
         FixedRows       =   2
      End
      Begin VB.Label Label1 
         Height          =   375
         Left            =   150
         TabIndex        =   3
         Top             =   330
         Width           =   8055
      End
   End
   Begin VB.Menu datei 
      Caption         =   "Fenster"
      WindowList      =   -1  'True
      Begin VB.Menu Tabelleschlieﬂen 
         Caption         =   "Tabelle schlieﬂen"
      End
   End
   Begin VB.Menu Tabelledrucken 
      Caption         =   "Tabelle drucken ..."
   End
End
Attribute VB_Name = "Tabelle"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False

Private Sub Tabelledrucken_Click()
Dim BeginPage, EndPage
    Dim NumCopies, i As Integer
    CommonDialog3.CancelError = True
    On Error GoTo ErrHandler
            ' Dialogfeld "Drucken" anzeigen
    CommonDialog3.ShowPrinter
            ' Vom Benutzer ausgew‰hlte Werte vom Dialogfeldabrufen
    BeginPage = CommonDialog3.FromPage
    EndPage = CommonDialog3.ToPage
    NumCopies = CommonDialog3.Copies
    If NumCopies < 1 Then
        NumCopies = 1
    End If
    For i = 1 To NumCopies
        PrintForm
    Next i
    CommonDialog1.CancelError = False
    Exit Sub
ErrHandler:
    Exit Sub
    
End Sub

Private Sub Tabelleschlieﬂen_Click()
Unload Tabelle
End Sub
