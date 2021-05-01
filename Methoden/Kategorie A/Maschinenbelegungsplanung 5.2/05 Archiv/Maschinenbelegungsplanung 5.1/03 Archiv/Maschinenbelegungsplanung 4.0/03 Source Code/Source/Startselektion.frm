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
      MaskColor       =   &H00000000&
      TabIndex        =   2
      ToolTipText     =   "Programmabbruch"
      Top             =   3840
      Width           =   1215
   End
   Begin VB.CommandButton Weiter 
      Caption         =   "OK"
      Default         =   -1  'True
      Height          =   375
      Left            =   2640
      MaskColor       =   &H00000000&
      TabIndex        =   1
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
      ForeColor       =   &H00000000&
      Height          =   3135
      Left            =   720
      TabIndex        =   0
      Top             =   480
      Width           =   6255
      Begin VB.ComboBox cboAuftraege 
         BeginProperty Font 
            Name            =   "MS Sans Serif"
            Size            =   9.75
            Charset         =   0
            Weight          =   400
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         ForeColor       =   &H00000000&
         Height          =   360
         ItemData        =   "Startselektion.frx":0442
         Left            =   3000
         List            =   "Startselektion.frx":0473
         TabIndex        =   5
         Text            =   "Auftraege"
         Top             =   720
         Width           =   1320
      End
      Begin VB.ComboBox cboPerioden 
         BeginProperty Font 
            Name            =   "MS Sans Serif"
            Size            =   9.75
            Charset         =   0
            Weight          =   400
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         ForeColor       =   &H00000000&
         Height          =   360
         ItemData        =   "Startselektion.frx":04AA
         Left            =   3000
         List            =   "Startselektion.frx":04DB
         TabIndex        =   4
         Text            =   "Perioden"
         ToolTipText     =   "Anzahl der benötigten Perioden ( 60 min = 1 Periode)"
         Top             =   1500
         Width           =   1320
      End
      Begin VB.ComboBox cboMaschinen 
         BeginProperty Font 
            Name            =   "MS Sans Serif"
            Size            =   9.75
            Charset         =   0
            Weight          =   400
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         ForeColor       =   &H00000000&
         Height          =   360
         ItemData        =   "Startselektion.frx":0512
         Left            =   3000
         List            =   "Startselektion.frx":051C
         TabIndex        =   3
         Text            =   "Typen"
         ToolTipText     =   "Anzahl der Maschinentypen (derzeit nur mit 2 Typen realisiert)"
         Top             =   2280
         Width           =   1320
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
         ForeColor       =   &H00000000&
         Height          =   255
         Index           =   0
         Left            =   720
         TabIndex        =   8
         ToolTipText     =   "Anzahl der zu fertigenden Aufträge"
         Top             =   760
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
         ForeColor       =   &H00000000&
         Height          =   255
         Left            =   720
         TabIndex        =   7
         ToolTipText     =   "Anzahl der zur verfügungstehenden Perioden (1 Periode = 60 min)"
         Top             =   1557
         Width           =   1695
         WordWrap        =   -1  'True
      End
      Begin VB.Label AnzMasch 
         Caption         =   "Anzahl Maschinentypen"
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
         Height          =   255
         Left            =   720
         TabIndex        =   6
         ToolTipText     =   "Anzahl der zur verfügungstehenden Maschinen"
         Top             =   2355
         Width           =   2055
      End
   End
End
Attribute VB_Name = "Startselektion"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Private Sub Abbruch_Click()
'//Bei Abbruch Button Variable auf Null setzen//////////////////////////////
    anz_auftr = 0
    anz_masch = 0
    anz_perioden = 0
    MDIForm1.Show
    Unload Startselektion
End Sub

'Auswahl aus der Startselektion in Variablen speichern ***
Private Sub Weiter_Click()
    anz_auftr = Startselektion.cboAuftraege.Text
    anz_masch = Startselektion.cboMaschinen.Text
    anz_perioden = Startselektion.cboPerioden.Text
 '//Abpruefen auf Wert--anz_auftr Menge max=49 ///////////////////////////////////////////////////
 If anz_auftr = "" Then '//leeres Feld
    anz_auftr = 0
 End If
 If anz_auftr = "Auftraege" Then
    anz_auftr = 0
 End If
 If anz_auftr > 49 And anz_auftr <> "Auftraege" Then        '// zahl muss kleiner 49 sein
    Startselektion.cboAuftraege.Text = ""
    MsgBox "Bitte Wert kleiner 50 eingeben !"
 Exit Sub
 End If
 '//Abpruefen auf Wert--anz_perioden Menge max=49/////////////////////////////
 If anz_perioden = "" Then '//leeres Feld
    anz_perioden = 0
 End If
 If anz_perioden = "Perioden" Then
    anz_perioden = 0
 End If
 If anz_perioden > 49 And anz_perioden <> "Perioden" Then        '// zahl muss kleiner 49 sein
    Startselektion.cboPerioden.Text = ""
    MsgBox "Bitte Wert kleiner 50 eingeben !"
 Exit Sub
 End If
'//Abpruefen auf Wert--anz_maschinen ////////////////////////////////////////
If anz_masch = "Typen" Then
    anz_masch = 1
End If
 '***** Weiter zum Hauptprogramm
 MDIForm1.Show
 Unload Startselektion
End Sub
