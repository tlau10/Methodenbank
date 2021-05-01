VERSION 5.00
Begin VB.Form Frage 
   Caption         =   "Zielfunktion"
   ClientHeight    =   2595
   ClientLeft      =   3630
   ClientTop       =   2595
   ClientWidth     =   5835
   ForeColor       =   &H00000000&
   Icon            =   "Frage.frx":0000
   LinkTopic       =   "Form2"
   ScaleHeight     =   2595
   ScaleWidth      =   5835
   Begin VB.CommandButton Command1 
      Caption         =   "OK"
      BeginProperty Font 
         Name            =   "MS Sans Serif"
         Size            =   8.25
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Height          =   375
      Left            =   2160
      MaskColor       =   &H00808080&
      TabIndex        =   3
      Top             =   2040
      Width           =   1095
   End
   Begin VB.Frame Frame1 
      Caption         =   "Bestimmung der Zielfunktion mittels"
      BeginProperty Font 
         Name            =   "MS Sans Serif"
         Size            =   8.25
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      ForeColor       =   &H00000000&
      Height          =   1695
      Left            =   1440
      TabIndex        =   0
      Top             =   240
      Width           =   4095
      Begin VB.OptionButton check_2 
         Caption         =   "Berechnung mit Minimierung der Maschinen"
         ForeColor       =   &H00000000&
         Height          =   375
         Left            =   360
         TabIndex        =   2
         Top             =   360
         Value           =   -1  'True
         Width           =   3615
      End
      Begin VB.OptionButton check_1 
         Caption         =   "Berechnung tatsächlicher Maschinen "
         Enabled         =   0   'False
         ForeColor       =   &H00000000&
         Height          =   375
         Left            =   360
         TabIndex        =   1
         Top             =   840
         Width           =   3375
      End
   End
   Begin VB.Image Image1 
      Height          =   1560
      Left            =   120
      Picture         =   "Frage.frx":0442
      Stretch         =   -1  'True
      Top             =   360
      Width           =   1320
   End
End
Attribute VB_Name = "Frage"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False

Private Sub Command1_Click()
If check_1.Value = True Then
    eingabe.check_1.Text = "1"
End If
If check_2.Value = True Then
    eingabe.check_1.Text = "2"
End If
Frage.Hide
End Sub
