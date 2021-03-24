VERSION 5.00
Object = "{A8B3B723-0B5A-101B-B22E-00AA0037B2FC}#1.0#0"; "Grid32.ocx"
Begin VB.Form Ausgabe 
   Caption         =   "Ausgabe"
   ClientHeight    =   6975
   ClientLeft      =   555
   ClientTop       =   2130
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
   ForeColor       =   &H00000000&
   HelpContextID   =   13
   LinkTopic       =   "Form1"
   MDIChild        =   -1  'True
   PaletteMode     =   1  'ZReihenfolge
   ScaleHeight     =   7780.515
   ScaleMode       =   0  'Benutzerdefiniert
   ScaleWidth      =   8730
   Begin VB.CommandButton grafik 
      Caption         =   "Graphische Darstellung"
      Height          =   375
      Left            =   4800
      TabIndex        =   11
      Top             =   360
      Width           =   2295
   End
   Begin MSGrid.Grid Gitter2 
      Height          =   1815
      Left            =   1920
      TabIndex        =   2
      Top             =   4680
      Width           =   6255
      _Version        =   65536
      _ExtentX        =   11033
      _ExtentY        =   3201
      _StockProps     =   77
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
      Left            =   7200
      TabIndex        =   0
      Top             =   360
      Width           =   1215
   End
   Begin MSGrid.Grid Gitter1 
      Height          =   1815
      Left            =   1920
      TabIndex        =   1
      Top             =   2640
      Width           =   6255
      _Version        =   65536
      _ExtentX        =   11033
      _ExtentY        =   3201
      _StockProps     =   77
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
   Begin VB.Label Label6 
      Alignment       =   1  'Rechts
      Appearance      =   0  '2D
      BackColor       =   &H8000000A&
      Caption         =   "A4"
      ForeColor       =   &H80000008&
      Height          =   255
      Left            =   6360
      TabIndex        =   18
      Top             =   2160
      Width           =   375
   End
   Begin VB.Label Label5 
      Alignment       =   1  'Rechts
      Appearance      =   0  '2D
      BackColor       =   &H8000000A&
      Caption         =   "A3"
      ForeColor       =   &H80000008&
      Height          =   255
      Left            =   6360
      TabIndex        =   17
      Top             =   1800
      Width           =   375
   End
   Begin VB.Label Label4 
      Alignment       =   1  'Rechts
      Appearance      =   0  '2D
      BackColor       =   &H8000000A&
      Caption         =   "A2"
      ForeColor       =   &H80000008&
      Height          =   255
      Left            =   6360
      TabIndex        =   16
      Top             =   1440
      Width           =   375
   End
   Begin VB.Label Label3 
      Alignment       =   1  'Rechts
      Appearance      =   0  '2D
      BackColor       =   &H8000000A&
      Caption         =   "A1"
      ForeColor       =   &H80000008&
      Height          =   255
      Left            =   6360
      TabIndex        =   15
      Top             =   1080
      Width           =   375
   End
   Begin VB.Label Anzahl4 
      Alignment       =   1  'Rechts
      Appearance      =   0  '2D
      BackColor       =   &H00E0E0E0&
      BorderStyle     =   1  'Fest Einfach
      Caption         =   "Keine Lösung"
      ForeColor       =   &H80000008&
      Height          =   255
      Left            =   6840
      TabIndex        =   14
      Top             =   2160
      Width           =   1215
   End
   Begin VB.Label Anzahl3 
      Alignment       =   1  'Rechts
      Appearance      =   0  '2D
      BackColor       =   &H00E0E0E0&
      BorderStyle     =   1  'Fest Einfach
      Caption         =   "Keine Lösung"
      ForeColor       =   &H80000008&
      Height          =   255
      Left            =   6840
      TabIndex        =   13
      Top             =   1800
      Width           =   1215
   End
   Begin VB.Label Anzahl2 
      Alignment       =   1  'Rechts
      Appearance      =   0  '2D
      BackColor       =   &H00E0E0E0&
      BorderStyle     =   1  'Fest Einfach
      Caption         =   "Keine Lösung"
      ForeColor       =   &H80000008&
      Height          =   255
      Left            =   6840
      TabIndex        =   12
      Top             =   1440
      Width           =   1215
   End
   Begin VB.Label Anzahl 
      Alignment       =   1  'Rechts
      Appearance      =   0  '2D
      BackColor       =   &H00E0E0E0&
      BorderStyle     =   1  'Fest Einfach
      Caption         =   "Keine Lösung"
      ForeColor       =   &H80000008&
      Height          =   255
      Left            =   6840
      TabIndex        =   10
      Top             =   1080
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
      Top             =   1080
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
      Left            =   3720
      TabIndex        =   7
      Top             =   1080
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
      Top             =   4680
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
      Top             =   2640
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
      Top             =   1080
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

Anzahl2.Visible = False
Anzahl3.Visible = False
Anzahl4.Visible = False
Label4.Visible = False
Label5.Visible = False
Label6.Visible = False

Dim i, j As Integer     'Zähler
Dim Zeilen, Gesamtzahl, Gesamtzahl1, Gesamtzahl2, Gesamtzahl3, Hilfe As Integer
Dim tmpString As String

Unload Infofenster      'falls geöffnet
'Unload Infofenster2
Unload Variante

Ausgabe.WindowState = 0
Height = 7380
Width = 8850

'Hauptfenster.mnuFensterAusgabe.Enabled = True

'Height = 6290
'Width = 9075
'Top = (Hauptfenster.Height - 695 - Height) / 2
'Left = (Hauptfenster.Width - 125 - Width) / 2

'Tabellengröße für Variantenausgabe
Gitter1.Rows = AnzVars + 1
Gitter1.Cols = AnzSpalten + 3

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

Gitter1.ColWidth(AnzSpalten + 2) = 1850
Gitter1.Col = (AnzSpalten + 2)
Gitter1.Text = "Ausgangs Produkt"

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
    
    Gitter1.Col = (AnzSpalten + 2)
    If Varianten(i).Ausgang = 0 Then
      Gitter1.Text = "A1"
    End If
    If Varianten(i).Ausgang = 1 Then
      Gitter1.Text = "A2"
    End If
    If Varianten(i).Ausgang = 2 Then
      Gitter1.Text = "A3"
    End If
    If Varianten(i).Ausgang = 3 Then
      Gitter1.Text = "A4"
    End If
         
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
Gitter2.Cols = AnzSpalten + 3

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

Gitter2.ColWidth(AnzSpalten + 2) = 1850
Gitter2.Col = (AnzSpalten + 2)
Gitter2.Text = "Ausgangs Produkt"

'Initialisierung
Gesamtzahl = 0
Gesamtzahl1 = 0
Gesamtzahl2 = 0
Gesamtzahl3 = 0

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
        If Varianten(i).Ausgang = 0 Then
        Gesamtzahl = Gesamtzahl + Hilfe
        End If
        If Varianten(i).Ausgang = 1 Then
        Gesamtzahl1 = Gesamtzahl1 + Hilfe
        End If
        If Varianten(i).Ausgang = 2 Then
        Gesamtzahl2 = Gesamtzahl2 + Hilfe
        End If
        If Varianten(i).Ausgang = 3 Then
        Gesamtzahl3 = Gesamtzahl3 + Hilfe
        End If
        Zeilen = Zeilen + 1
        
        Gitter2.Col = (AnzSpalten + 2)
        If Varianten(i).Ausgang = 0 Then
            Gitter2.Text = "A1"
        End If
        If Varianten(i).Ausgang = 1 Then
            Gitter2.Text = "A2"
        End If
        If Varianten(i).Ausgang = 2 Then
            Gitter2.Text = "A3"
        End If
        If Varianten(i).Ausgang = 3 Then
            Gitter2.Text = "A4"
        End If
    End If
Next i


tmpString = Str$(Gesamtzahl)
tmpString = Mid(tmpString, 2, Len(tmpString) - 1) 'Entfernen des Vorzeichens

'Zielwerte eintragen
ZFWert.Caption = Zielwert  ' Ist immer Verschnitt
Anzahl.Caption = tmpString ' Ist immer Anzahl Werkstücke

If Breite(1) <> "0" Then
    tmpString = Str$(Gesamtzahl1)
    tmpString = Mid(tmpString, 2, Len(tmpString) - 1) 'Entfernen des Vorzeichens
    Anzahl2.Caption = tmpString
    Anzahl2.Visible = True
    Label4.Visible = True
End If

If Breite(2) <> "0" Then
    tmpString = Str$(Gesamtzahl2)
    tmpString = Mid(tmpString, 2, Len(tmpString) - 1) 'Entfernen des Vorzeichens
    Anzahl3.Caption = tmpString
    Anzahl3.Visible = True
    Label5.Visible = True
End If

If Breite(3) <> "0" Then
    tmpString = Str$(Gesamtzahl3)
    tmpString = Mid(tmpString, 2, Len(tmpString) - 1) 'Entfernen des Vorzeichens
    Anzahl4.Caption = tmpString
    Anzahl4.Visible = True
    Label6.Visible = True
End If



'Gitter2.Col = 1
'Gitter2.Row = 1

End Sub

Private Sub Gitter2_Click()
   
   aktiveZeile = Gitter2.Row
  

End Sub

Private Sub grafik_Click()
    Hauptfenster.WindowState = 2
    
    Dim variantenanzahl
    Dim produktanzahl
    Dim n As Integer
    Dim z As Integer
    'Dim produktlaengen() As Variant
    Dim produktlaengen() As Integer
    Dim arrData() As Variant
    'Dim temp
    Dim temp As Integer
    
    temp = 0
    
    produktanzahl = Gitter1.Col - 1
    variantenanzahl = Gitter1.Row
    
    Eingabe.Gitter2.Col = 0
    Eingabe.Gitter2.Row = 0
           
    grafverschnitt.Chart1.RowCount = variantenanzahl 'Anzahl der Varianten
    grafverschnitt.Chart1.ColumnCount = 2   'Verschnitt und nicht Verschnitt übereinanderlegen
    
    Gitter1.Col = 2
    Gitter1.Row = 0
    
    ReDim arrData(variantenanzahl - 1, 2)  ' -1 da bei 0 beginnt zu zählen
    ReDim produktlaengen(produktanzahl)
        
    'Speichern der verlangten Schnitte
    Gitter1.Row = 0
    
    For i = 0 To produktanzahl - 1
        Gitter1.Col = i + 1
        produktlaengen(i) = Val(Gitter1.Text)
    Next
    
    'Füllen des Diagramms mit Daten
    For n = 0 To (variantenanzahl - 1)
        temp = 0
        For z = 0 To (produktanzahl - 1)
            Gitter1.Row = n + 1             'auswählen der Datenzeile
            Gitter1.Col = z + 1             'auswählen der Datenspalte
            temp = temp + Gitter1.Text * produktlaengen(z)
        Next
        arrData(n, 0) = "V" & n + 1 ' Set the labels in the first series.
        arrData(n, 1) = temp            ' werte des nichtverschnitts
      
        Gitter1.Col = produktanzahl + 1          'auswählen der Datenspalte für Verschnitt
        
        arrData(n, 2) = Gitter1.Text       'setzen des Verschnitts als 2. wert in einer spalte
    Next
        
    'grafverschnitt.Chart1.ChartData = arrData
    
    grafverschnitt.Show
End Sub
