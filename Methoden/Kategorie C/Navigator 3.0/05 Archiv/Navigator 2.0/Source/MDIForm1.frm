VERSION 5.00
Begin VB.MDIForm MDIForm1 
   BackColor       =   &H8000000C&
   Caption         =   "Navigat"
   ClientHeight    =   7620
   ClientLeft      =   165
   ClientTop       =   735
   ClientWidth     =   11760
   LinkTopic       =   "MDIForm1"
   StartUpPosition =   3  'Windows-Standard
   Begin VB.Menu menBeenden 
      Caption         =   "&Programm"
      Begin VB.Menu itemStarten 
         Caption         =   "&Starten"
      End
      Begin VB.Menu itemBeenden 
         Caption         =   "&Beenden"
      End
   End
   Begin VB.Menu menHilfe 
      Caption         =   "&Hilfe"
      Begin VB.Menu itemHelp 
         Caption         =   "Über &Navigator"
      End
      Begin VB.Menu itemInfo 
         Caption         =   "&Info"
      End
   End
End
Attribute VB_Name = "MDIForm1"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Private Sub itemBeenden_Click()
       'Schliesse alle offene Formulare
       If frmImage.Visible = True Then
          Unload frmImage
       End If
       If frmHelp.Visible = True Then
          Unload frmHelp
       End If
       
       Unload Me
End Sub

Private Sub itemHelp_Click()
    frmHelp.Show
End Sub

Private Sub itemInfo_Click()
    frmInfo.Show vbModal
End Sub

Private Sub itemStarten_Click()
    frmNavigat.Show
End Sub


Private Sub MDIForm_Load()
    frmNavigat.Show
End Sub

Private Sub MDIForm_Unload(Cancel As Integer)
       'Beim Schliessen der MDIForm sollen alle Forms geschlossen
       If frmImage.Visible = True Then
          Unload frmImage
       End If
       
       If frmHelp.Visible = True Then
          Unload frmHelp
       End If
       
       If frmErgebnis.Visible = True Then
         frmErgebnis.Visible = False
         Unload frmErgebnis
    End If
End Sub
