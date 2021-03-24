VERSION 5.00
Begin VB.Form Infofenster2 
   Appearance      =   0  '2D
   BackColor       =   &H00FFFFFF&
   BorderStyle     =   1  'Fest Einfach
   Caption         =   "Hyperinfo über..."
   ClientHeight    =   3375
   ClientLeft      =   4515
   ClientTop       =   525
   ClientWidth     =   4785
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
   LinkTopic       =   "Form1"
   MDIChild        =   -1  'True
   PaletteMode     =   1  'ZReihenfolge
   ScaleHeight     =   3375
   ScaleWidth      =   4785
   Begin VB.PictureBox bildSabine 
      Appearance      =   0  '2D
      BackColor       =   &H80000005&
      ForeColor       =   &H80000008&
      Height          =   1695
      Left            =   3240
      Picture         =   "INFO2.frx":0000
      ScaleHeight     =   30
      ScaleMode       =   0  'Benutzerdefiniert
      ScaleWidth      =   25
      TabIndex        =   0
      Top             =   1560
      Width           =   1455
   End
   Begin VB.PictureBox bildOli 
      Appearance      =   0  '2D
      BackColor       =   &H80000005&
      ForeColor       =   &H80000008&
      Height          =   1695
      Left            =   120
      Picture         =   "INFO2.frx":3324
      ScaleHeight     =   30
      ScaleMode       =   0  'Benutzerdefiniert
      ScaleWidth      =   25
      TabIndex        =   5
      Top             =   1560
      Width           =   1455
   End
   Begin VB.CommandButton cmdOK 
      Appearance      =   0  '2D
      Caption         =   "OK"
      Height          =   495
      Left            =   1800
      TabIndex        =   6
      Top             =   2760
      Width           =   1215
   End
   Begin VB.Label textSabine 
      Alignment       =   2  'Zentriert
      Appearance      =   0  '2D
      BackColor       =   &H0000FFFF&
      Caption         =   "Sabine Thiedmann"
      ForeColor       =   &H80000008&
      Height          =   255
      Left            =   2880
      TabIndex        =   9
      Top             =   1200
      Visible         =   0   'False
      Width           =   1695
   End
   Begin VB.Label textOli 
      Alignment       =   2  'Zentriert
      Appearance      =   0  '2D
      BackColor       =   &H0000FFFF&
      Caption         =   "Oliver Baur"
      ForeColor       =   &H80000008&
      Height          =   255
      Left            =   240
      TabIndex        =   8
      Top             =   1200
      Visible         =   0   'False
      Width           =   1215
   End
   Begin VB.Shape rahmenSabine 
      BackColor       =   &H0000FFFF&
      BackStyle       =   1  'Undurchsichtig
      Height          =   495
      Left            =   2760
      Shape           =   4  'Gerundetes Rechteck
      Top             =   1080
      Visible         =   0   'False
      Width           =   1935
   End
   Begin VB.Shape rahmenOli 
      BackColor       =   &H0000FFFF&
      BackStyle       =   1  'Undurchsichtig
      Height          =   495
      Left            =   120
      Shape           =   4  'Gerundetes Rechteck
      Top             =   1080
      Visible         =   0   'False
      Width           =   1455
   End
   Begin VB.Label Bezeichnung4 
      Alignment       =   2  'Zentriert
      Appearance      =   0  '2D
      BackColor       =   &H00FFFFFF&
      Caption         =   "von"
      ForeColor       =   &H80000008&
      Height          =   255
      Left            =   1680
      TabIndex        =   7
      Top             =   1320
      Width           =   1455
   End
   Begin VB.Label lblVer 
      Alignment       =   2  'Zentriert
      Appearance      =   0  '2D
      BackColor       =   &H00FFFFFF&
      Caption         =   "Version 1.0"
      ForeColor       =   &H80000008&
      Height          =   255
      Left            =   240
      TabIndex        =   4
      Top             =   360
      Width           =   4335
   End
   Begin VB.Label Bezeichnung3 
      Alignment       =   2  'Zentriert
      Appearance      =   0  '2D
      BackColor       =   &H00FFFFFF&
      Caption         =   "an der Fachhochschule Konstanz"
      ForeColor       =   &H80000008&
      Height          =   255
      Left            =   240
      TabIndex        =   3
      Top             =   1080
      Width           =   4335
   End
   Begin VB.Label Bezeichnung2 
      Alignment       =   2  'Zentriert
      Appearance      =   0  '2D
      BackColor       =   &H00FFFFFF&
      Caption         =   "Entwickelt im Wintersemester 1995/96"
      ForeColor       =   &H80000008&
      Height          =   255
      Left            =   240
      TabIndex        =   2
      Top             =   840
      Width           =   4335
   End
   Begin VB.Label Bezeichnung1 
      Alignment       =   2  'Zentriert
      Appearance      =   0  '2D
      BackColor       =   &H00FFFFFF&
      Caption         =   "Verschnitt-Problematik"
      ForeColor       =   &H80000008&
      Height          =   255
      Left            =   240
      TabIndex        =   1
      Top             =   120
      Width           =   4335
   End
End
Attribute VB_Name = "Infofenster2"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Private Sub bildOli_MouseDown(Button As Integer, Shift As Integer, X As Single, Y As Single)

    rahmenOli.Visible = True
    textOli.Visible = True

End Sub

Private Sub bildOli_MouseUp(Button As Integer, Shift As Integer, X As Single, Y As Single)

    rahmenOli.Visible = False
    textOli.Visible = False

End Sub

Private Sub bildSabine_MouseDown(Button As Integer, Shift As Integer, X As Single, Y As Single)

    rahmenSabine.Visible = True
    textSabine.Visible = True

End Sub

Private Sub bildSabine_MouseUp(Button As Integer, Shift As Integer, X As Single, Y As Single)

    rahmenSabine.Visible = False
    textSabine.Visible = False

End Sub

Private Sub cmdOK_Click()

    Unload Infofenster2

End Sub

