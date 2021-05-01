VERSION 5.00
Begin VB.Form frmNavigat 
   Caption         =   "Navigat"
   ClientHeight    =   4488
   ClientLeft      =   60
   ClientTop       =   348
   ClientWidth     =   8808
   LinkTopic       =   "Form1"
   MDIChild        =   -1  'True
   ScaleHeight     =   4488
   ScaleWidth      =   8808
   Begin VB.CommandButton cmdVollbild 
      Caption         =   "&Vollbild"
      Height          =   375
      Left            =   5640
      TabIndex        =   7
      Top             =   3840
      Width           =   1815
   End
   Begin VB.ComboBox cmbNach 
      Height          =   315
      Left            =   1440
      Sorted          =   -1  'True
      TabIndex        =   1
      Top             =   1560
      Width           =   2175
   End
   Begin VB.ComboBox cmbVon 
      Height          =   315
      Left            =   1440
      Sorted          =   -1  'True
      TabIndex        =   0
      Top             =   720
      Width           =   2175
   End
   Begin VB.CommandButton cmdBerechne 
      Caption         =   "&Berechnen"
      Height          =   495
      Left            =   1440
      TabIndex        =   2
      Top             =   2880
      Width           =   1935
   End
   Begin VB.Image Image1 
      Height          =   2772
      Left            =   4080
      Picture         =   "frmNavigat.frx":0000
      Top             =   240
      Width           =   3648
   End
   Begin VB.Label lblStatus 
      Caption         =   "Berechnung läuft..."
      BeginProperty Font 
         Name            =   "MS Sans Serif"
         Size            =   9.6
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Height          =   375
      Left            =   120
      TabIndex        =   6
      Top             =   3960
      Visible         =   0   'False
      Width           =   3495
   End
   Begin VB.Label Label3 
      Caption         =   "Wählen Sie Ihre Start- und Zielort aus"
      Height          =   495
      Left            =   240
      TabIndex        =   5
      Top             =   240
      Width           =   3615
   End
   Begin VB.Label Label2 
      Caption         =   "Nach"
      Height          =   375
      Left            =   240
      TabIndex        =   4
      Top             =   1560
      Width           =   1095
   End
   Begin VB.Label Label1 
      Caption         =   "Von"
      Height          =   375
      Left            =   240
      TabIndex        =   3
      Top             =   720
      Width           =   1095
   End
End
Attribute VB_Name = "frmNavigat"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Dim myNavigat As New clsPathAndData     'Globale Objekt von Klasse deklarieren
Dim strErgebnisPath As String
Private Sub cmdVollbild_Click()
    frmImage.Show
End Sub

Private Sub Form_Load()
    myNavigat.Initialize        'Objekt beim Laden von Form initialisieren
    Dim DateiNr, pos, line
Dim i As Integer
i = 1
Dim index As Integer
DateiNr = FreeFile
strDBPath = myNavigat.getDBPath        'gebe perl programm Navigator mit Pfad zurueck

ORTSCHAFTEN = strDBPath & "\DBorte.asc"
  Open ORTSCHAFTEN For Input As #DateiNr
   Do While Not EOF(DateiNr)
       Line Input #DateiNr, line
       pos = InStr(1, line, ":")
       If (pos <> 0) Then
           cmbVon.AddItem LTrim(Mid(line, (pos + 1)))
           cmbNach.AddItem LTrim(Mid(line, (pos + 1)))
       End If
   Loop
  Close DateiNr
End Sub

Private Sub cmdBerechne_Click()

   'strErgebnisPath = myNavigat.getEndResultPath
   strErgebnisPath = myNavigat.getEndResultPath
    If (fileExists(strErgebnisPath) = True) Then
       Kill strErgebnisPath
    End If
    
If cmbVon.Text = cmbNach.Text Then
    MsgBox ("Start- und Zielort gleich!!")
Exit Sub
End If

If (cmbVon.Text = "" Or cmbNach.Text = "") Then
    MsgBox ("Beide Orte Von und Nach muss angegeben werden!!")
    Exit Sub
Exit Sub
End If

If frmErgebnis.Visible = True Then
    frmErgebnis.Visible = False
    Unload frmErgebnis
End If

    lblStatus.Visible = True
    Me.Refresh
    Dim Ergebnis, strPerlBatDatei, strPerlPath, strNavigat, Dateinummer
    strPerlBatDatei = myNavigat.getPerlBatDatei 'gebe das Verzeichnis wo Batch - Datei zur Ausfuehrung angelegt wird
    strPerlPath = myNavigat.getPerlPath         'gebe Perl - Pfad zurueck
    strNavigat = myNavigat.getNavigat           'gebe perl programm Navigator mit Pfad zurueck
    Dateinummer = FreeFile  ' neue Datei-Nr.

    Open strPerlBatDatei For Output As #Dateinummer
      Print #Dateinummer, strPerlPath & " " & strNavigat & " " & cmbVon.Text & " " & cmbNach.Text & " " & myNavigat.getIniFile
    Close Dateinummer
      
    Ergebnis = Shell(strPerlBatDatei, vbHide)
 
   ' Sleep (5000)
    Sleep (50)
    showErgebnis
    lblStatus.Visible = False
    
End Sub
Private Sub showErgebnis()
   'Dim myErgebnis As frmErgebnis
   'Set myErgebnis = New frmErgebnis
   'Dim strErgebnisPath, FileNr
   Dim i As Integer
   Dim FileNr
   FileNr = FreeFile
   For i = 0 To 8000
     
    If (fileExists(strErgebnisPath) = True) Then
         i = 8000
         Open strErgebnisPath For Input As #FileNr
         Do While Not EOF(FileNr)
           Line Input #FileNr, zeile
           pos = InStr(1, zeile, ":")
           If (pos <> 0) Then
              frmErgebnis.lstErgebnis.AddItem Trim(Left(zeile, (pos)))
              frmErgebnis.lstKM.AddItem Trim(Mid(zeile, (pos + 1)))
            Else
              frmErgebnis.lstErgebnis.AddItem zeile
              frmErgebnis.lstKM.AddItem " "
            End If
         Loop
         Close FileNr
     Else
        Sleep (300)
    End If
    Next i
     
     If (fileExists(strErgebnisPath) = True) Then
         frmErgebnis.Show
     Else
         MsgBox "Error: Datei " & strErgebnisPath & " nicht vorhanden!"
     End If
      
End Sub

Public Function fileExists(fileName As String) As Boolean
On Error GoTo Handle
    fileExists = False
    If (Dir(fileName) <> "") And (Trim$(fileName) <> "") Then
        fileExists = True
    End If
    Exit Function
Handle:
    Exit Function
End Function


