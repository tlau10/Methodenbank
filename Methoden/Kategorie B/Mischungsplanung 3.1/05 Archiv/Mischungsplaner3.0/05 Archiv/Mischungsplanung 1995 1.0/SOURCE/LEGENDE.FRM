VERSION 2.00
Begin Form frmLegende 
   Caption         =   "Beschreibung des Modells"
   ClientHeight    =   4050
   ClientLeft      =   2220
   ClientTop       =   2445
   ClientWidth     =   5685
   Height          =   4455
   Left            =   2160
   LinkTopic       =   "Form1"
   ScaleHeight     =   4050
   ScaleWidth      =   5685
   Top             =   2100
   Width           =   5805
   Begin CommandButton btnAbbrechen 
      Caption         =   "Abbrechen"
      Height          =   375
      Left            =   3000
      TabIndex        =   2
      Top             =   3480
      Width           =   1455
   End
   Begin CommandButton btnÜbernehmen 
      Caption         =   "Übernehmen"
      Height          =   375
      Left            =   1080
      TabIndex        =   1
      Top             =   3480
      Width           =   1455
   End
   Begin TextBox txtLegende 
      Height          =   2535
      Left            =   360
      MultiLine       =   -1  'True
      ScrollBars      =   2  'Vertikal
      TabIndex        =   0
      Top             =   360
      Width           =   4935
   End
   Begin Label Bezeichnung1 
      BackStyle       =   0  'Transparent
      Caption         =   "Erst das Speichern des Modells übernimmt die Eingaben/Änderungen in die Datendatei."
      FontBold        =   0   'False
      FontItalic      =   0   'False
      FontName        =   "MS Sans Serif"
      FontSize        =   8.25
      FontStrikethru  =   0   'False
      FontUnderline   =   0   'False
      Height          =   495
      Left            =   360
      TabIndex        =   3
      Top             =   3000
      Width           =   4935
   End
End
Sub Befehl2_Click ()

  Unload Me

End Sub

Sub btnAbbrechen_Click ()

   Unload Me

End Sub

Sub btnÜbernehmen_Click ()

    Legende = txtLegende.Text
    Unload Me

End Sub

Sub Form_Load ()

   backcolor = RGB(230, 250, 240)
   txtLegende.Text = Legende

End Sub

