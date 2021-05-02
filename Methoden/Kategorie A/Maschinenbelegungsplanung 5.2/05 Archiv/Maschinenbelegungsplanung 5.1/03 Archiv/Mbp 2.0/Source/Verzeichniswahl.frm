VERSION 5.00
Begin VB.Form Verzeichniswahl 
   BorderStyle     =   1  'Fest Einfach
   Caption         =   "Temporäres Verzeichnis wählen"
   ClientHeight    =   3195
   ClientLeft      =   3660
   ClientTop       =   3000
   ClientWidth     =   3855
   Icon            =   "Verzeichniswahl.frx":0000
   LinkTopic       =   "Form2"
   MaxButton       =   0   'False
   MinButton       =   0   'False
   ScaleHeight     =   3195
   ScaleWidth      =   3855
   StartUpPosition =   2  'Bildschirmmitte
   Begin VB.CommandButton Cancel 
      Cancel          =   -1  'True
      Caption         =   "Cancel"
      Height          =   375
      Left            =   2040
      TabIndex        =   3
      Top             =   2640
      Width           =   1095
   End
   Begin VB.CommandButton OK 
      Caption         =   "OK"
      Default         =   -1  'True
      Height          =   375
      Left            =   720
      TabIndex        =   2
      Top             =   2640
      Width           =   1095
   End
   Begin VB.DirListBox Dir1 
      Height          =   1890
      Left            =   600
      TabIndex        =   1
      Top             =   600
      Width           =   2655
   End
   Begin VB.DriveListBox Drive1 
      Height          =   315
      Left            =   600
      TabIndex        =   0
      Top             =   240
      Width           =   2655
   End
End
Attribute VB_Name = "Verzeichniswahl"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False


Private Sub Form_Load()
Drive1.Drive = "c:\"
Dir1.Path = "c:\temp"
End Sub


Private Sub OK_Click()
eingabe.pfad.Text = Dir1.Path
Me.Hide
End Sub
Private Sub Cancel_Click()
    Unload Verzeichniswahl
End Sub

Private Sub Drive1_Change()
   Dir1.Path = Drive1.Drive    ' Bei Laufwerksänderung
                               ' Verzeichnispfad setzen.
End Sub

