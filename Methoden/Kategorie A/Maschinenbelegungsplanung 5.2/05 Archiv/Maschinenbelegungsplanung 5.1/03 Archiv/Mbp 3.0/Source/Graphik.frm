VERSION 5.00
Object = "{F9043C88-F6F2-101A-A3C9-08002B2F49FB}#1.2#0"; "COMDLG32.OCX"
Object = "{65E121D4-0C60-11D2-A9FC-0000F8754DA1}#2.0#0"; "MSCHRT20.OCX"
Begin VB.Form Graphik 
   Caption         =   "Graphik"
   ClientHeight    =   8310
   ClientLeft      =   375
   ClientTop       =   480
   ClientWidth     =   11340
   Icon            =   "Graphik.frx":0000
   LinkTopic       =   "Form2"
   MDIChild        =   -1  'True
   ScaleHeight     =   8310
   ScaleWidth      =   11340
   ShowInTaskbar   =   0   'False
   Begin MSChart20Lib.MSChart MSChart1 
      Height          =   3735
      Left            =   240
      OleObjectBlob   =   "Graphik.frx":0442
      TabIndex        =   0
      Top             =   120
      Width           =   5295
   End
   Begin MSComDlg.CommonDialog CommonDialog2 
      Left            =   6840
      Top             =   480
      _ExtentX        =   847
      _ExtentY        =   847
      _Version        =   393216
   End
   Begin VB.Menu datei 
      Caption         =   "Fenster"
      Index           =   1
      WindowList      =   -1  'True
      Begin VB.Menu Grafikbeenden 
         Caption         =   "Grafik schlieﬂen"
      End
   End
   Begin VB.Menu Grafikdrucken 
      Caption         =   "Grafik drucken ..."
   End
End
Attribute VB_Name = "Graphik"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False

Private Sub Form_Resize()
    MSChart1.Move 0, 0, ScaleWidth, ScaleHeight
End Sub



Private Sub Grafikbeenden_Click()
    Unload Graphik
End Sub


Private Sub Grafikdrucken_Click()
    Dim BeginPage, EndPage
    Dim NumCopies, i As Integer
    
    CommonDialog2.CancelError = True
    On Error GoTo ErrHandler
            ' Dialogfeld "Drucken" anzeigen
    CommonDialog2.ShowPrinter
            ' Vom Benutzer ausgew‰hlte Werte vom Dialogfeld
            ' abrufen
    BeginPage = CommonDialog2.FromPage
    EndPage = CommonDialog2.ToPage
    NumCopies = CommonDialog2.Copies
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


