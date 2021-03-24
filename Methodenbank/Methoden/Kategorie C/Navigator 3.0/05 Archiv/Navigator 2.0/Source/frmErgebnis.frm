VERSION 5.00
Begin VB.Form frmErgebnis 
   Caption         =   "Navigat - Ergebnis"
   ClientHeight    =   5130
   ClientLeft      =   60
   ClientTop       =   345
   ClientWidth     =   8940
   LinkTopic       =   "Form2"
   MDIChild        =   -1  'True
   ScaleHeight     =   5130
   ScaleWidth      =   8940
   Begin VB.ListBox lstKM 
      BeginProperty Font 
         Name            =   "MS Sans Serif"
         Size            =   9.75
         Charset         =   0
         Weight          =   400
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Height          =   3660
      Left            =   6600
      TabIndex        =   1
      Top             =   840
      Width           =   1815
   End
   Begin VB.ListBox lstErgebnis 
      BeginProperty Font 
         Name            =   "MS Sans Serif"
         Size            =   9.75
         Charset         =   0
         Weight          =   400
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Height          =   3660
      Left            =   600
      TabIndex        =   0
      Top             =   840
      Width           =   5895
   End
End
Attribute VB_Name = "frmErgebnis"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
