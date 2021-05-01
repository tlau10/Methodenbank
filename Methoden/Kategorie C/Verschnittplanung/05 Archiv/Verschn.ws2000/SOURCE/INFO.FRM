VERSION 5.00
Begin VB.Form Infofenster 
   Appearance      =   0  '2D
   BackColor       =   &H8000000A&
   BorderStyle     =   1  'Fest Einfach
   Caption         =   "Info über..."
   ClientHeight    =   4170
   ClientLeft      =   870
   ClientTop       =   1560
   ClientWidth     =   4290
   FillColor       =   &H00FFFFFF&
   FillStyle       =   0  'Ausgefüllt
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
   MaxButton       =   0   'False
   MDIChild        =   -1  'True
   MinButton       =   0   'False
   PaletteMode     =   1  'ZReihenfolge
   ScaleHeight     =   4170
   ScaleWidth      =   4290
   Begin VB.Frame Rahmen1 
      Appearance      =   0  '2D
      BackColor       =   &H8000000A&
      Caption         =   "Die Autoren"
      ForeColor       =   &H80000008&
      Height          =   2175
      Left            =   1080
      TabIndex        =   4
      Top             =   960
      Width           =   2775
      Begin VB.ListBox Liste1 
         Appearance      =   0  '2D
         BackColor       =   &H00FFFFFF&
         Height          =   1590
         Left            =   240
         TabIndex        =   2
         Top             =   360
         Width           =   2295
      End
   End
   Begin VB.CommandButton cmdOK 
      Appearance      =   0  '2D
      Caption         =   "OK"
      Height          =   360
      Left            =   2880
      TabIndex        =   3
      Top             =   3360
      Width           =   975
   End
   Begin VB.Label lblCopyRight 
      Appearance      =   0  '2D
      BackColor       =   &H8000000A&
      BackStyle       =   0  'Transparent
      Caption         =   "© 1996 - 2001 Fachhochschule Konstanz"
      BeginProperty Font 
         Name            =   "MS Sans Serif"
         Size            =   8.25
         Charset         =   0
         Weight          =   400
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      ForeColor       =   &H80000008&
      Height          =   255
      Left            =   600
      TabIndex        =   1
      Top             =   3840
      Width           =   3255
   End
   Begin VB.Image Anzeige1 
      Appearance      =   0  '2D
      Height          =   480
      Left            =   240
      Picture         =   "INFO.frx":0000
      Top             =   1080
      Width           =   480
   End
   Begin VB.Label Bezeichnung1 
      Appearance      =   0  '2D
      BackColor       =   &H8000000A&
      BackStyle       =   0  'Transparent
      Caption         =   "Verschnittplanung"
      BeginProperty Font 
         Name            =   "MS Sans Serif"
         Size            =   18
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      ForeColor       =   &H80000008&
      Height          =   495
      Left            =   480
      TabIndex        =   0
      Top             =   240
      Width           =   3255
   End
End
Attribute VB_Name = "Infofenster"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False

Private Sub cmdOK_Click()
    Unload Infofenster
End Sub

Private Sub Form_Load()
'
Height = 4580
Width = 4400
Top = (Hauptfenster.Height - 695 - Height) / 2
Left = (Hauptfenster.Width - 125 - Width) / 2

Me.BackColor = RGB(100, 160, 220)
Rahmen1.BackColor = RGB(100, 160, 220)

' die Liste kann beliebig erweitert werden

Liste1.AddItem "---------------- WS95/96"
Liste1.AddItem "Oliver Baur"
Liste1.AddItem "Sabine Thiedmann"
Liste1.AddItem ""
Liste1.AddItem "---------------- SS96"
Liste1.AddItem "Tobias Keller"
Liste1.AddItem "Claudia Zimmermann"
Liste1.AddItem "Peter Weber"
Liste1.AddItem ""
' WS 96/97 MBM
Liste1.AddItem "---------------- SS97"
Liste1.AddItem "Christoph Kreß"
Liste1.AddItem ""
' WS 2000
Liste1.AddItem "---------------- WS2000"
Liste1.AddItem "Degu Dagne"
Liste1.AddItem "Herbert Hartmann"
Liste1.AddItem "Thomas Rathmann"
Liste1.AddItem ""
Liste1.AddItem ""
Liste1.AddItem ""
Liste1.AddItem ""
 

Liste1.TopIndex = 15

End Sub

