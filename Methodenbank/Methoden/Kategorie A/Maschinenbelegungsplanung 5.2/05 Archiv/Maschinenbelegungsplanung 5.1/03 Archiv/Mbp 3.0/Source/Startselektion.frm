VERSION 5.00
Begin VB.Form Startselektion 
   BorderStyle     =   1  'Fest Einfach
   Caption         =   "Maschinenbelegung - Startselektion"
   ClientHeight    =   4515
   ClientLeft      =   45
   ClientTop       =   330
   ClientWidth     =   7785
   FillStyle       =   5  'Abwärtsdiagonal
   Icon            =   "Startselektion.frx":0000
   LinkTopic       =   "Startselektion"
   MaxButton       =   0   'False
   MinButton       =   0   'False
   ScaleHeight     =   4515
   ScaleWidth      =   7785
   StartUpPosition =   2  'Bildschirmmitte
   Begin VB.CommandButton Abbruch 
      Cancel          =   -1  'True
      Caption         =   "Cancel"
      Height          =   375
      Left            =   4080
      TabIndex        =   8
      ToolTipText     =   "Programmabbruch"
      Top             =   3840
      Width           =   1215
   End
   Begin VB.CommandButton Weiter 
      Caption         =   "OK"
      Default         =   -1  'True
      Height          =   375
      Left            =   2640
      TabIndex        =   5
      ToolTipText     =   "Weiter zum Hauptprogramm"
      Top             =   3840
      Width           =   1215
   End
   Begin VB.Frame Frame1 
      Caption         =   " Bitte geben Sie die Auftragsdaten an "
      BeginProperty Font 
         Name            =   "MS Sans Serif"
         Size            =   8.25
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      ForeColor       =   &H00800000&
      Height          =   3135
      Left            =   720
      TabIndex        =   0
      Top             =   480
      Width           =   6255
      Begin VB.ComboBox Maschinen 
         BeginProperty Font 
            Name            =   "MS Sans Serif"
            Size            =   9.75
            Charset         =   0
            Weight          =   400
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   360
         ItemData        =   "Startselektion.frx":0442
         Left            =   2520
         List            =   "Startselektion.frx":0449
         TabIndex        =   7
         Text            =   "1"
         ToolTipText     =   "Wieviele Maschinen zur Verfügung stehen (derzeit nur mit 1 Maschine realisiert)"
         Top             =   2280
         Width           =   1200
      End
      Begin VB.ComboBox Periode 
         BeginProperty Font 
            Name            =   "MS Sans Serif"
            Size            =   9.75
            Charset         =   0
            Weight          =   400
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   360
         ItemData        =   "Startselektion.frx":0450
         Left            =   2520
         List            =   "Startselektion.frx":046F
         Style           =   2  'Dropdown-Liste
         TabIndex        =   4
         ToolTipText     =   "Anzahl der benötigten Perioden ( 60 min = 1 Periode)"
         Top             =   1560
         Width           =   1200
      End
      Begin VB.ComboBox Auftraege 
         BeginProperty Font 
            Name            =   "MS Sans Serif"
            Size            =   9.75
            Charset         =   0
            Weight          =   400
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   360
         ItemData        =   "Startselektion.frx":048F
         Left            =   2520
         List            =   "Startselektion.frx":04B1
         Style           =   2  'Dropdown-Liste
         TabIndex        =   1
         Top             =   720
         Width           =   1200
      End
      Begin VB.Label AnzMasch 
         Caption         =   "Anzahl Maschinen"
         BeginProperty Font 
            Name            =   "MS Sans Serif"
            Size            =   8.25
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   255
         Left            =   720
         TabIndex        =   6
         ToolTipText     =   "Anzahl der zur verfügungstehenden Maschinen"
         Top             =   2360
         Width           =   1700
      End
      Begin VB.Label AnzPeri 
         BackStyle       =   0  'Transparent
         Caption         =   "Anzahl Perioden"
         BeginProperty Font 
            Name            =   "MS Sans Serif"
            Size            =   8.25
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   255
         Left            =   720
         TabIndex        =   3
         ToolTipText     =   "Anzahl der zur verfügungstehenden Perioden (1 Periode = 60 min)"
         Top             =   1600
         Width           =   1700
         WordWrap        =   -1  'True
      End
      Begin VB.Label AnzAuftr 
         BackStyle       =   0  'Transparent
         Caption         =   "Anzahl Aufträge"
         BeginProperty Font 
            Name            =   "MS Sans Serif"
            Size            =   8.25
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   255
         Index           =   0
         Left            =   720
         TabIndex        =   2
         ToolTipText     =   "Anzahl der zu fertigenden Aufträge"
         Top             =   760
         Width           =   1700
      End
   End
End
Attribute VB_Name = "Startselektion"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False



Private Sub Abbruch_Click()
    Unload Startselektion
End Sub

'***** Sandra: Auswahl aus der Startselektion in Variablen speichern ***

Private Sub Weiter_Click()
    anz_auftr = Startselektion.Auftraege.Text
    anz_masch = Startselektion.Maschinen.Text
    anz_perioden = Startselektion.Periode.Text
    
   ' res = MsgBox("auftr: " & anz_auftr & "masch: " & anz_masch, vbInformation)
    
    
'***** Weiter zum Hauptprogramm
    MDIForm1.Show
    Unload Startselektion

End Sub
