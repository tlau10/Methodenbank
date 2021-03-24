VERSION 5.00
Object = "{A8B3B723-0B5A-101B-B22E-00AA0037B2FC}#1.0#0"; "GRID32.OCX"
Begin VB.Form Ausgabe 
   Appearance      =   0  '2D
   BackColor       =   &H00C0C0C0&
   BorderStyle     =   1  'Fest Einfach
   Caption         =   "Ausgabe"
   ClientHeight    =   5985
   ClientLeft      =   390
   ClientTop       =   1425
   ClientWidth     =   8730
   BeginProperty Font 
      Name            =   "MS Sans Serif"
      Size            =   8.25
      Charset         =   0
      Weight          =   700
      Underline       =   0   'False
      Italic          =   0   'False
      Strikethrough   =   0   'False
   EndProperty
   ForeColor       =   &H00FFFFFF&
   HelpContextID   =   13
   LinkTopic       =   "Form1"
   MaxButton       =   0   'False
   MDIChild        =   -1  'True
   MinButton       =   0   'False
   PaletteMode     =   1  'ZReihenfolge
   ScaleHeight     =   5985
   ScaleWidth      =   8730
   Begin MSGrid.Grid Gitter2 
      Height          =   1815
      Left            =   1920
      TabIndex        =   2
      Top             =   3840
      Width           =   6255
      _Version        =   65536
      _ExtentX        =   11033
      _ExtentY        =   3201
      _StockProps     =   77
      ForeColor       =   0
      BackColor       =   -2147483643
      BeginProperty Font {0BE35203-8F91-11CE-9DE3-00AA004BB851} 
         Name            =   "Arial"
         Size            =   8.25
         Charset         =   0
         Weight          =   400
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
   End
   Begin VB.CommandButton CmdSchliess 
      Appearance      =   0  '2D
      Caption         =   "Zurück"
      Height          =   375
      Left            =   6240
      TabIndex        =   0
      Top             =   360
      Width           =   1215
   End
   Begin MSGrid.Grid Gitter1 
      Height          =   1815
      Left            =   1920
      TabIndex        =   1
      Top             =   1800
      Width           =   6255
      _Version        =   65536
      _ExtentX        =   11033
      _ExtentY        =   3201
      _StockProps     =   77
      ForeColor       =   0
      BackColor       =   -2147483643
      BeginProperty Font {0BE35203-8F91-11CE-9DE3-00AA004BB851} 
         Name            =   "Arial"
         Size            =   8.25
         Charset         =   0
         Weight          =   400
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      ScrollBars      =   2
   End
   Begin VB.Label Anzahl 
      Alignment       =   1  'Rechts
      Appearance      =   0  '2D
      BackColor       =   &H00E0E0E0&
      BorderStyle     =   1  'Fest Einfach
      Caption         =   "Keine Lösung"
      ForeColor       =   &H80000008&
      Height          =   255
      Left            =   6240
      TabIndex        =   10
      Top             =   1200
      Width           =   1215
   End
   Begin VB.Label ZFWert 
      Alignment       =   1  'Rechts
      Appearance      =   0  '2D
      BackColor       =   &H00E0E0E0&
      BorderStyle     =   1  'Fest Einfach
      Caption         =   "Keine Lösung"
      ForeColor       =   &H80000008&
      Height          =   255
      Left            =   1920
      TabIndex        =   9
      Top             =   1200
      Width           =   1335
   End
   Begin VB.Label SolverName 
      Appearance      =   0  '2D
      BackColor       =   &H00E0E0E0&
      BorderStyle     =   1  'Fest Einfach
      Caption         =   "Keiner"
      ForeColor       =   &H80000008&
      Height          =   255
      Left            =   1920
      TabIndex        =   8
      Top             =   360
      Width           =   2055
   End
   Begin VB.Label Bezeichnung1 
      Alignment       =   1  'Rechts
      Appearance      =   0  '2D
      BackColor       =   &H8000000A&
      Caption         =   "Benötigte Werkstücke:"
      ForeColor       =   &H80000008&
      Height          =   255
      Left            =   3960
      TabIndex        =   7
      Top             =   1200
      Width           =   2055
   End
   Begin VB.Label Bezeichnung6 
      Appearance      =   0  '2D
      BackColor       =   &H8000000A&
      Caption         =   "Verwendete Varianten:"
      ForeColor       =   &H80000008&
      Height          =   375
      Left            =   480
      TabIndex        =   3
      Top             =   3840
      Width           =   1215
   End
   Begin VB.Label Bezeichnung5 
      Appearance      =   0  '2D
      BackColor       =   &H8000000A&
      Caption         =   "Solver:"
      ForeColor       =   &H80000008&
      Height          =   255
      Left            =   480
      TabIndex        =   6
      Top             =   360
      Width           =   1215
   End
   Begin VB.Label Bezeichnung4 
      Appearance      =   0  '2D
      BackColor       =   &H8000000A&
      Caption         =   "Mögliche Varianten:"
      ForeColor       =   &H80000008&
      Height          =   495
      Left            =   480
      TabIndex        =   5
      Top             =   1800
      Width           =   1215
   End
   Begin VB.Label Bezeichnung3 
      Appearance      =   0  '2D
      BackColor       =   &H8000000A&
      Caption         =   "Verschnitt:"
      ForeColor       =   &H80000008&
      Height          =   255
      Left            =   480
      TabIndex        =   4
      Top             =   1200
      Width           =   1215
   End
End
Attribute VB_Name = "Ausgabe"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False


Private Sub CmdSchliess_Click()
    
    Hauptfenster.mnuFensterAusgabe.Enabled = False

    Unload Ausgabe

End Sub

Private Sub Form_Load()

Dim i, j As Integer     'Zähler
Dim Zeilen, Gesamtzahl, Hilfe As Integer
Dim tmpString As String

Unload Infofenster      'falls geöffnet
'Unload Infofenster2
Unload Variante



Hauptfenster.mnuFensterAusgabe.Enabled = True

Height = 6290
Width = 9075
Top = (Hauptfenster.Height - 695 - Height) / 2
Left = (Hauptfenster.Width - 125 - Width) / 2

'Tabellengröße für Variantenausgabe
Gitter1.Rows = AnzVars + 1
Gitter1.Cols = AnzSpalten + 2

'Spaltenüberschriften
Gitter1.Row = 0

For i = 1 To AnzSpalten
    Gitter1.ColWidth(i) = 800
    Gitter1.Col = i
    If Anz_Dimensionen = 1 Then
        Gitter1.Text = Spalte(i).Breite
    Else
        Gitter1.Text = Spalte(i).Breite + " x " + Spalte(i).Höhe
    End If
Next i

Gitter1.ColWidth(AnzSpalten + 1) = 1000
Gitter1.Col = (AnzSpalten + 1)
Gitter1.Text = "Verschnitt"

'Tabelle füllen
For i = 1 To AnzVars
    Gitter1.Row = i
    Gitter1.Col = 0
    Gitter1.Text = Varianten(i).Variable
    
    For j = 1 To AnzSpalten
        Gitter1.Col = j
        Gitter1.Text = Varianten(i).Produkt(j)
    Next j

    Gitter1.Col = (AnzSpalten + 1)
    Gitter1.Text = Varianten(i).Verschnitt
Next i

'Gitter1.Col = 1
'Gitter1.Row = 1

Fehler = 0

'Solver-Name zuweisen und Ergebnisse suchen

Select Case Solver_var

Case "XAeqs"
    SolverName.Caption = " XA Equation Style"
    XA_Ergebnis_lesen

Case "XAmps"
    SolverName.Caption = " XA MPS Format"
    XA_Ergebnis_lesen

Case "MOPS"
    SolverName.Caption = " MOPS MPS Format"
    MOPS_Ergebnis_lesen

Case "LPSOLVE"    '--> NOCH ZU IMPLEMENTIEREN
'    x = Shell("notepad " + WorkingDir + "\lp_solve.out", 1)
             
'    ' solange Notepad aktiv ist
'    While getmoduleusage(x) > 0
'        warte = DoEvents()
'    Wend
    
    SolverName.Caption = " LP-Solve MPS Format"
    LPSOLVE_Ergebnis_lesen

Case Else 'übrige Solver
    MsgBox "Der Solver " + Solver_var + " ist noch nicht implementiert.", 0, "Hinweis"
End Select

If Fehler = 1 Then
    MsgBox "Es wurde keine gültige Lösung gefunden !", 0, "Hinweis"
    Exit Sub
ElseIf Fehler = 2 Then
    MsgBox "Error bei der Berechnung, bitte Ausgabedatei des Solvers prüfen!", 0, "Fehler"
End If


'Tabellengröße bestimmen
Zeilen = 0
For i = 1 To AnzVars
    If Val(Varianten(i).Anzahl) > 0 Then
        Zeilen = Zeilen + 1
    End If
Next i

'Tabellengröße für Lösungsausgabe festlegen
Gitter2.Rows = Zeilen + 1
Gitter2.Cols = AnzSpalten + 2

'Spaltenüberschriften
Gitter2.Row = 0

For i = 1 To AnzSpalten
    Gitter2.ColWidth(i) = 800
    Gitter2.Col = i
    If Anz_Dimensionen = 1 Then
        'Gitter2.Text = "P" & i & ": " & Spalte(i).Breite & "m"
        Gitter2.Text = "Produkt" & i
    Else
        'Gitter2.Text = "P" & i & ": " & Spalte(i).Breite + " x " + Spalte(i).Höhe & "m"
        Gitter2.Text = "Produkt" & i
    End If
Next i

Gitter2.ColWidth(AnzSpalten + 1) = 1000
Gitter2.Col = (AnzSpalten + 1)
Gitter2.Text = "Anzahl WS"

'Initialisierung
Gesamtzahl = 0
Zeilen = 1

'Tabelle füllen
For i = 1 To AnzVars
    If Val(Varianten(i).Anzahl) > 0 Then
        Gitter2.Row = Zeilen
        Gitter2.Col = 0
        Gitter2.Text = Varianten(i).Variable
    
        For j = 1 To AnzSpalten
            Gitter2.Col = j
            Gitter2.Text = Varianten(i).Produkt(j)
        Next j

        Gitter2.Col = (AnzSpalten + 1)
        Hilfe = Val(Varianten(i).Anzahl)
        Gitter2.Text = Hilfe
        Gesamtzahl = Gesamtzahl + Hilfe
        Zeilen = Zeilen + 1
    End If
Next i


tmpString = Str$(Gesamtzahl)
tmpString = Mid(tmpString, 2, Len(tmpString) - 1) 'Entfernen des Vorzeichens

'Zielwerte eintragen
ZFWert.Caption = Zielwert  ' Ist immer Verschnitt
Anzahl.Caption = tmpString ' Ist immer Anzahl Werkstücke

'Gitter2.Col = 1
'Gitter2.Row = 1

End Sub

Private Sub Gitter2_Click()
   
   aktiveZeile = Gitter2.Row
  

End Sub

