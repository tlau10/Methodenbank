VERSION 5.00
Object = "{65E121D4-0C60-11D2-A9FC-0000F8754DA1}#2.0#0"; "MSCHRT20.OCX"
Begin VB.Form Grafverschnitt 
   AutoRedraw      =   -1  'True
   BorderStyle     =   1  'Fest Einfach
   Caption         =   "Graphische Darstellung"
   ClientHeight    =   8730
   ClientLeft      =   615
   ClientTop       =   2175
   ClientWidth     =   12585
   LinkTopic       =   "Form1"
   MDIChild        =   -1  'True
   ScaleHeight     =   8730
   ScaleWidth      =   12585
   Begin VB.Frame Frame1 
      Caption         =   "Grafik"
      Height          =   8535
      Left            =   120
      TabIndex        =   0
      Top             =   120
      Width           =   12375
      Begin VB.PictureBox Picture2 
         Height          =   255
         Left            =   9600
         Picture         =   "Grafverschnitt.frx":0000
         ScaleHeight     =   195
         ScaleWidth      =   195
         TabIndex        =   5
         Top             =   7680
         Width           =   255
      End
      Begin VB.PictureBox Picture1 
         FillColor       =   &H000000FF&
         FillStyle       =   0  'Ausgefüllt
         ForeColor       =   &H000000FF&
         Height          =   255
         Left            =   7920
         Picture         =   "Grafverschnitt.frx":0582
         ScaleHeight     =   195
         ScaleWidth      =   195
         TabIndex        =   4
         Top             =   7680
         Width           =   255
      End
      Begin VB.CommandButton Command2 
         Caption         =   "2D Ansicht"
         Height          =   375
         Left            =   720
         TabIndex        =   3
         Top             =   7920
         Width           =   1695
      End
      Begin VB.CommandButton Command1 
         Caption         =   "3D Ansicht"
         Height          =   375
         Left            =   2640
         TabIndex        =   2
         Top             =   7920
         Width           =   1695
      End
      Begin MSChart20Lib.MSChart Chart1 
         Height          =   7335
         Left            =   360
         OleObjectBlob   =   "Grafverschnitt.frx":0B04
         TabIndex        =   1
         Top             =   240
         Width           =   11655
      End
      Begin VB.Label Label2 
         Caption         =   "Verbrauch"
         BeginProperty Font 
            Name            =   "MS Sans Serif"
            Size            =   9.75
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   255
         Left            =   8280
         TabIndex        =   7
         Top             =   7680
         Width           =   1455
      End
      Begin VB.Label Label1 
         Caption         =   "Verschnitt"
         BeginProperty Font 
            Name            =   "MS Sans Serif"
            Size            =   9.75
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   255
         Left            =   9960
         TabIndex        =   6
         Top             =   7680
         Width           =   1335
      End
   End
End
Attribute VB_Name = "grafverschnitt"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Private Sub Command1_Click()
    grafverschnitt.Chart1.chartType = VtChChartType3dBar

    grafverschnitt.Refresh
End Sub

Private Sub Command2_Click()
    grafverschnitt.Chart1.chartType = VtChChartType2dBar

    grafverschnitt.Refresh
End Sub

