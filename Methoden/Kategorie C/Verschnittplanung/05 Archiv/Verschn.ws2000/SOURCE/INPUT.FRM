VERSION 5.00
Object = "{A8B3B723-0B5A-101B-B22E-00AA0037B2FC}#1.0#0"; "GRID32.OCX"
Begin VB.Form Eingabe 
   Appearance      =   0  '2D
   BackColor       =   &H8000000A&
   Caption         =   "Eingabe"
   ClientHeight    =   6465
   ClientLeft      =   600
   ClientTop       =   645
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
   ForeColor       =   &H80000008&
   HelpContextID   =   11
   LinkTopic       =   "Form1"
   MDIChild        =   -1  'True
   PaletteMode     =   1  'ZReihenfolge
   ScaleHeight     =   6465
   ScaleWidth      =   8730
   Begin VB.TextBox maxWerk 
      Height          =   285
      Left            =   4200
      TabIndex        =   12
      Top             =   1680
      Width           =   975
   End
   Begin MSGrid.Grid Gitter2 
      Height          =   255
      Left            =   4200
      TabIndex        =   0
      Top             =   1200
      Width           =   645
      _Version        =   65536
      _ExtentX        =   1138
      _ExtentY        =   450
      _StockProps     =   77
      BackColor       =   -2147483643
      BeginProperty Font {0BE35203-8F91-11CE-9DE3-00AA004BB851} 
         Name            =   "Arial"
         Size            =   9.75
         Charset         =   0
         Weight          =   400
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Rows            =   1
      Cols            =   1
      FixedRows       =   0
      FixedCols       =   0
      ScrollBars      =   0
      GridLines       =   0   'False
   End
   Begin VB.Frame Rahmen1 
      Appearance      =   0  '2D
      BackColor       =   &H8000000A&
      Caption         =   "Art des Problems"
      ForeColor       =   &H80000008&
      Height          =   735
      Left            =   2040
      TabIndex        =   4
      Top             =   240
      Width           =   4455
      Begin VB.OptionButton Option1 
         Caption         =   "Eindimensional"
         Height          =   255
         Index           =   0
         Left            =   120
         TabIndex        =   10
         Top             =   360
         Width           =   2055
      End
      Begin VB.OptionButton Option1 
         Caption         =   "Zweidimensional"
         Height          =   255
         Index           =   1
         Left            =   2400
         TabIndex        =   9
         Top             =   360
         Width           =   1815
      End
   End
   Begin VB.CommandButton CmdBerechnen 
      BackColor       =   &H8000000D&
      Caption         =   "Berechnung &starten"
      Height          =   375
      Left            =   5520
      TabIndex        =   2
      Top             =   5520
      Width           =   2415
   End
   Begin VB.Frame Frame1 
      BackColor       =   &H8000000A&
      Caption         =   "Art der Optimierung"
      Height          =   1455
      Left            =   360
      TabIndex        =   6
      Top             =   4680
      Width           =   7815
      Begin VB.OptionButton Option2 
         BackColor       =   &H8000000A&
         Caption         =   "Minimierung der Anzahl der Werkst�cke"
         Height          =   255
         Index           =   1
         Left            =   240
         TabIndex        =   8
         Top             =   960
         Width           =   3735
      End
      Begin VB.OptionButton Option2 
         BackColor       =   &H8000000A&
         Caption         =   "Minimierung des Verschnitts"
         Height          =   255
         Index           =   0
         Left            =   240
         TabIndex        =   7
         Top             =   360
         Value           =   -1  'True
         Width           =   3615
      End
      Begin VB.Label Label1 
         Caption         =   " (Kann zu einer �berproduktion f�hren)"
         BeginProperty Font 
            Name            =   "MS Sans Serif"
            Size            =   8.25
            Charset         =   0
            Weight          =   400
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   255
         Left            =   480
         TabIndex        =   13
         Top             =   610
         Width           =   3135
      End
   End
   Begin MSGrid.Grid Gitter1 
      Height          =   1695
      Left            =   360
      TabIndex        =   1
      Top             =   2760
      Width           =   7815
      _Version        =   65536
      _ExtentX        =   13785
      _ExtentY        =   2990
      _StockProps     =   77
      BackColor       =   -2147483643
      BeginProperty Font {0BE35203-8F91-11CE-9DE3-00AA004BB851} 
         Name            =   "Arial"
         Size            =   9.75
         Charset         =   0
         Weight          =   400
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Rows            =   4
   End
   Begin VB.Label Label3 
      Caption         =   "<--"
      Height          =   255
      Left            =   5280
      TabIndex        =   15
      Top             =   1680
      Width           =   255
   End
   Begin VB.Label Label2 
      BackStyle       =   0  'Transparent
      Caption         =   "Ist nur bei Minimierung des Verschnitts aktiv ! "
      BeginProperty Font 
         Name            =   "MS Sans Serif"
         Size            =   8.25
         Charset         =   0
         Weight          =   400
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      ForeColor       =   &H000000FF&
      Height          =   495
      Left            =   5640
      TabIndex        =   14
      Top             =   1680
      Width           =   1815
   End
   Begin VB.Label Bezeichnung5 
      BackStyle       =   0  'Transparent
      Caption         =   "max. vorhandene Werkst�cke:"
      Height          =   375
      Left            =   2400
      TabIndex        =   11
      Top             =   1680
      Width           =   1695
   End
   Begin VB.Label Bezeichnung3 
      Appearance      =   0  '2D
      BackColor       =   &H8000000A&
      BackStyle       =   0  'Transparent
      Caption         =   "L�nge bzw. H�he des Werkstoffes:"
      ForeColor       =   &H80000008&
      Height          =   495
      Left            =   2400
      TabIndex        =   3
      Top             =   1200
      Width           =   1695
   End
   Begin VB.Label Bezeichnung4 
      Appearance      =   0  '2D
      BackColor       =   &H8000000A&
      BackStyle       =   0  'Transparent
      Caption         =   "Ben�tigte Produktma�e:"
      ForeColor       =   &H80000008&
      Height          =   255
      Left            =   360
      TabIndex        =   5
      Top             =   2280
      Width           =   2175
   End
End
Attribute VB_Name = "Eingabe"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Dim HilfsProdukt(MaxSpalten) As Integer  'Hilfs-Array mit Kombinationen

Private Sub CmdBerechnen_Click()
    ' Mouse-Cursor auf Sanduhr (WARTEN) stellen
    Screen.MousePointer = 11

    Fehler = 0
    TabelleStutzen   'entfernt nachfolgende Nullen und Kommata und
                     'bereinigt die Tabelle von leeren Spalten
    TabelleSortieren 'sortiert absteigend nach der Breite
    
    ' Breite des Werkst�ckes
    Gitter2.Col = 0
    Gitter2.Row = 0
    Gitter2.Text = Breite

    ' H�he des Werkst�ckes
    If Anz_Dimensionen = 2 Then
        Gitter2.Col = 1
        Gitter2.Row = 0
        Gitter2.Text = H�he
    End If

    '�bertragen der aufbereiteten Tabelle
    For i = 1 To AnzSpalten  ' Eintragen der neuen Werte
        Gitter1.Col = i
        Gitter1.Row = 1
        Gitter1.Text = Spalte(i).Anzahl
        Gitter1.Row = 2
        Gitter1.Text = Spalte(i).Breite
        If Anz_Dimensionen = 2 Then
            Gitter1.Row = 3
            Gitter1.Text = Spalte(i).H�he
        End If
    Next i
    For i = (AnzSpalten + 1) To MaxSpalten  'L�schen der �brigen Zellen
        Gitter1.Col = i
        Gitter1.Row = 1
        Gitter1.Text = ""
        Gitter1.Row = 2
        Gitter1.Text = ""
        If Anz_Dimensionen = 2 Then
            Gitter1.Row = 3
            Gitter1.Text = ""
        End If
    Next i

    CanClose = False
    Input_Pruefen 'Eingabepr�fungen
    If Fehler = 0 Then
        'Getrennte Verarbeitung je nach Problemdimension
        If Option1(1).Value = True Then
            Variante.Show 0
            Hauptfenster.mnuFensterVariante.Enabled = True
        Else
            AnzVars = 0
            KombinationenBilden ' erstellt die notwendige Zwischentabelle mit
                            ' den m�glichen Kombinationen

            If Fehler = 0 Then
                Berechnung ' Bildet Zielfunktion und Restriktionen und
            End If               ' schreibt die Rohdateien f�r die Solver

            ' Positionieren des Cursors
            Gitter1.Col = 1
            Gitter1.Row = 1
    
            If Fehler = 0 Then
                Ausgabe.Show
            End If
        End If
    End If
    ' Mouse-Cursor auf NORMAL stellen
    Screen.MousePointer = 0
End Sub

Private Sub Eintragen()

Dim i As Integer 'Z�hler
Dim tmpString As Variant 'f�r Umwandlungen

'Variablen-Name zuweisen
tmpString = Str$(AnzVars) 'umwandeln des Z�hlers
tmpString = Mid(tmpString, 2, Len(tmpString) - 1) 'Vorzeichenstelle abschneiden
Varianten(AnzVars).Variable = "X" & tmpString

'Vektor mit aktueller Kombination belegen
For i = 1 To MaxSpalten
    tmpString = Str$(HilfsProdukt(i)) 'Umwandlung
    tmpString = Mid(tmpString, 2, Len(tmpString) - 1) 'Vorzeichenstelle abschneiden
    Varianten(AnzVars).Produkt(i) = tmpString
Next i

'Verschnitt belegen
tmpSumme = VerschnittBestimmen()
tmpString = Str$(tmpSumme)
tmpString = Mid(tmpString, 2, Len(tmpString) - 1) 'Vorzeichenstelle abschneiden
Varianten(AnzVars).Verschnitt = tmpString
    
'L�sungs-Anzahl vorbelegen
Varianten(AnzVars).Anzahl = "0"

End Sub

Private Sub Form_Load()
                         
    Dim i

    Unload Infofenster   'falls ge�ffnet
  
    Unload Variante

    Height = 6700
    Width = 8790
    Top = (Hauptfenster.Height - 695 - Height) / 2
    Left = (Hauptfenster.Width - 125 - Width) / 2
    
    'Me.BackColor = RGB(220, 160, 100)
    'Rahmen1.BackColor = RGB(,,)
    
                       
    CanClose = True
    IsActive = True
    ' In Abh�ngigkeit der Dimension Wertbelegung
    If Anz_Dimensionen = 1 Then
        Option1(0).Value = True
        Option1(1).Value = False
    Else
        Option1(0).Value = False
        Option1(1).Value = True
    End If
    ' Initialisierung des L�nge/Breite-Feldes
    Gitter2.ColWidth(0) = 800
    Gitter2.Width = (Anz_Dimensionen + 1) * Val(Gitter2.ColWidth(0))
    Gitter2.Cols = Anz_Dimensionen
    Gitter2.Rows = 1
    Gitter2.Col = 0
    Gitter2.Row = 0
    Gitter2.Text = Breite
    
    ' Definition der Tabellengr��e
    Gitter1.Rows = Anz_Dimensionen + 2
    Gitter1.Cols = MaxSpalten + 1
   
    ' Spalten�berschriften
    Gitter1.Row = 0
    Gitter1.ColWidth(0) = 800

    For i = 1 To MaxSpalten
        Gitter1.ColWidth(i) = 1000
        Gitter1.Col = i
        Gitter1.Text = "Produkt" + Str$(i)
    Next i

    ' Zeilen�berschriften
    
    Gitter1.Col = 0
    Gitter1.Row = 1
    Gitter1.Text = "Anzahl"
    Gitter1.Row = 2
    Gitter1.Text = "L�nge"
    If Anz_Dimensionen = 2 Then
        Gitter1.Row = 3
        Gitter1.Text = "H�he"
    End If
    For i = 1 To MaxSpalten
        Gitter1.Col = i
        Gitter1.Row = 1: Gitter1.Text = Spalte(i).Anzahl
        Gitter1.Row = 2: Gitter1.Text = Spalte(i).Breite
        If Anz_Dimensionen = 2 Then
            Gitter1.Row = 3
            Gitter1.Text = Spalte(i).H�he
        End If
    Next i
    'Cursorposition
    Gitter1.Row = 1
    Gitter1.Col = 1
End Sub

Private Sub Form_QueryUnload(Cancel As Integer, UnloadMode As Integer)
    
    ' Datei ge�ndert ?
    If CanClose = False Then
        SicherAbfrage
    End If
    
    If CanClose = False Then
        Cancel = 1
    End If

End Sub

Private Sub Form_Unload(Cancel As Integer)
    
    Dim i

    For i = 1 To MaxSpalten
        Spalte(i).Breite = 0
        Spalte(i).Anzahl = 0
        Spalte(i).H�he = 0
    Next i
    
    Breite = 0
    H�he = 0

    Option1(0).Value = True
    Anz_Dimensionen = 1
    Unload Ausgabe
    IsActive = False
    Hauptfenster.Caption = "Verschnittplanung"

End Sub

Private Sub Gitter1_KeyPress(KeyAnsi As Integer)
Dim Xalt, Xneu, Yalt, Yneu, Komma, i As Integer
Dim Anz_Zeilen As Integer
'In dieser Routine erfolgt die Dateneingabe in die Gitter-Tabelle
If Option1(0).Value = True Then
  Anz_Zeilen = 3
Else
  Anz_Zeilen = 4
End If

' Speicherung der alten Koordinaten (notwendig bei "Mausarbeit")
Xalt = Gitter1.Col
Yalt = Gitter1.Row

Xneu = Xalt   ' Neue Koordinaten f�r Positionierung
Yneu = Yalt

'  verwertbare Taste gedr�ckt ?
Select Case (KeyAnsi)
    Case 8
        ' R�ckschritt-Taste
        If Len(Gitter1.Text) > 0 Then
           ' => ein Zeichen entfernen
           Gitter1.Text = Mid(Gitter1.Text, 1, Len(Gitter1.Text) - 1)
        End If

    Case 44, 46   ' Punkt und Komma machen nur bei Breite oder H�he Sinn
        If Yalt = 2 Or Yalt = 3 Then
            Komma = 0
            For i = 1 To Len(Gitter1.Text)
                If Mid(Gitter1.Text, i, 1) = "." Then
                    Komma = 1
                End If
            Next i

            If Komma = 0 Then
                Gitter1.Text = Gitter1.Text & Chr(46)
            End If
        Else
            MsgBox "F�r die Anzahl sind nur ganzzahlige Eingaben sinnvoll", 0, "Hinweis"
        End If

    Case 48 To 57  ' Normale Ziffern
        If Gitter1.Text = Chr(48) Then
           Gitter1.Text = Chr(KeyAnsi)
           'Wenn nur eine "0" in dem Tabellenfeld steht,
           'dann einfach die gedr�ckte Zahl zuweisen
        Else
            Gitter1.Text = Gitter1.Text & Chr(KeyAnsi)
            'sonst mu� das Zeichen an die schon verhanden
            'Zeichenkette angef�gt werden
        End If

    Case 13 ' RETURN
        
        ' Weiterschalten des Eingabefeldes
        Yneu = Yneu + 1
        If Yneu = Anz_Zeilen Then Yneu = 1: Xneu = Xneu + 1
        If Xneu > MaxSpalten Then Xneu = Xneu - 1: Yneu = 2 'Kein Umbruch !

End Select

' Eintragen des Wertes in die interne Tabelle
If Anz_Dimensionen = 1 Then
    If Yalt = 1 Then
        Spalte(Xalt).Anzahl = Gitter1.Text
    End If
    If Yalt = 2 Then
        Spalte(Xalt).Breite = Gitter1.Text
    End If
End If
If Anz_Dimensionen = 2 Then
    If Yalt = 1 Then
        Spalte(Xalt).Anzahl = Gitter1.Text
    End If
    If Yalt = 2 Then
        Spalte(Xalt).Breite = Gitter1.Text
    End If
    If Yalt = 3 Then
        Spalte(Xalt).H�he = Gitter1.Text
    End If
End If
' Setzt den Eingabefokus
Gitter1.SelStartCol = Xneu
Gitter1.SelEndCol = Xneu
Gitter1.SelStartRow = Yneu
Gitter1.SelEndRow = Yneu

' Setzt den Tabellencursor
Gitter1.Col = Xneu
Gitter1.Row = Yneu

CanClose = False

End Sub

Private Sub Gitter2_KeyPress(KeyAnsi As Integer)
Dim Xalt, Xneu, Yalt, Yneu, Komma, i As Integer
Dim Anz_Spalten, Anz_Zeilen As Integer
' Bis auf weiteres kann nur ein Werkst�ck eingegeben werden
Anz_Zeilen = 1

'In dieser Routine erfolgt die Dateneingabe in die Gitter-Tabelle
Anz_Spalten = Anz_Dimensionen

' Speicherung der alten Koordinaten (notwendig bei "Mausarbeit")
Xalt = Gitter2.Col
Yalt = Gitter2.Row

Xneu = Xalt   ' Neue Koordinaten f�r Positionierung
Yneu = Yalt

'  verwertbare Taste gedr�ckt ?
Select Case (KeyAnsi)
    Case 8
        ' R�ckschritt-Taste
        If Len(Gitter2.Text) > 0 Then
           ' => ein Zeichen entfernen
           Gitter2.Text = Mid(Gitter2.Text, 1, Len(Gitter2.Text) - 1)
        End If

    Case 44, 46   ' Punkt und Komma
        Komma = 0
        For i = 1 To Len(Gitter2.Text)
            If Mid(Gitter2.Text, i, 1) = "." Then
                Komma = 1
            End If
        Next i

        If Komma = 0 Then
            Gitter2.Text = Gitter2.Text & Chr(46)
        End If

    Case 48 To 57  ' Normale Ziffern
        If Gitter2.Text = Chr(48) Then
           Gitter2.Text = Chr(KeyAnsi)
           'Wenn nur eine "0" in dem Tabellenfeld steht,
           'dann einfach die gedr�ckte Zahl zuweisen
        Else
            'sonst mu� das Zeichen an die schon verhanden
            'Zeichenkette angef�gt werden
            Gitter2.Text = Gitter2.Text & Chr(KeyAnsi)
        End If

    Case 13 ' RETURN
        ' Weiterschalten des Eingabefeldes (Spaltenweise
        Xneu = Xneu + 1
        If Xneu = Anz_Spalten Then
            Xneu = 0
            Yneu = Yneu + 1
        End If
        If Yneu = Anz_Zeilen Then
            Yneu = 0
            Xneu = 0
        End If

End Select
' Eintragung in globale Variablen
Gitter2.Row = 0
For i = 0 To Anz_Spalten - 1
    Gitter2.Col = i
    If i = 0 Then
        Breite = Gitter2.Text
    Else
        H�he = Gitter2.Text
    End If
Next i

' Setzt den Eingabefokus
Gitter2.SelStartCol = Xneu
Gitter2.SelEndCol = Xneu
Gitter2.SelStartRow = Yneu
Gitter2.SelEndRow = Yneu

' Setzt den Tabellencursor
Gitter2.Col = Xneu
Gitter2.Row = Yneu

' Setze Knz fuer Sicherheitsabfrage bei Programmende
CanClose = False
End Sub

Private Sub Input_Pruefen()
Dim i
Dim Lange_Seite_Werkst�ck, Kurze_Seite_Werkst�ck, Lange_Seite_Produkt, Kurze_Seite_Produkt As Variant
' Fehlervorbelegung
Fehler = 0
' Es sind keine Produkte gefordert (Tabelle ist leer)
If AnzSpalten = 0 Then
    MsgBox "Es wurden keine Produkte angegeben!", 0, "Hinweis"
    Fehler = 1
    Exit Sub
End If
' 1D - Geforderte Breite > Breite der Rollen
If Anz_Dimensionen = 1 Then
    If Val(Spalte(1).Breite) > Val(Breite) Then
        MsgBox "Das Produkt " + i + " kann nicht mit der Ausgangsrolle produziert werden!", 0, "Hinweis"
        Fehler = 1
        Exit Sub
    End If
End If
' 2D - Eine H�he mu� eingegeben werden
If Anz_Dimensionen = 2 Then
    If H�he <= 0 Then
            MsgBox "Es mu� eine H�he eingegeben werden.", 0, "Hinweis"
            Fehler = 1
            Exit Sub
    End If
End If

' 2D - Geforderte Breite > (Breite,H�he) Werkst�ck
' Dabei wird die l�ngere Seite des Produkts mit der l�ngeren Seite des Werkst�ckes verglichen.
' �quivalent wird mit der k�rzeren Seite verfahren.

If Anz_Dimensionen = 2 Then
    ' Bestimmungen f�r Werkst�ck
    If Val(Breite) > Val(H�he) Then
        Lange_Seite_Werkst�ck = Breite
        Kurze_Seite_Werkst�ck = H�he
    Else
        Lange_Seite_Werkst�ck = H�he
        Kurze_Seite_Werkst�ck = Breite
    End If
    For i = 1 To AnzSpalten
        ' Bestimmung f�r Produkt
        If Val(Spalte(i).Breite) > Val(Spalte(i).H�he) Then
            Lange_Seite_Produkt = Spalte(i).Breite
            Kurze_Seite_Produkt = Spalte(i).H�he
        Else
            Lange_Seite_Produkt = Spalte(i).H�he
            Kurze_Seite_Produkt = Spalte(i).Breite
        End If
        ' Vergleich
        If Val(Lange_Seite_Produkt) <= Val(Lange_Seite_Werkst�ck) And Val(Kurze_Seite_Produkt) <= Val(Kurze_Seite_Werkst�ck) Then
        Else
            MsgBox "Das Produkt " + i + " pa�t nicht in das Werkst�ck.", 0, "Hinweis"
            Fehler = 1
            Exit Sub
        End If
    Next i
End If
' Keine Grenze f�r die Variablen eingetragen
If MaxVars < 1 Then
    MsgBox "Im Programm k�nnen keine Varianten gebildet werden. Wert MaxVars korrigieren.", 0, "Hinweis"
    Fehler = 1
    Exit Sub
End If
End Sub

Private Sub KombinationenBilden()
Dim i As Integer 'Z�hlvariable
AnzVars = 0      'Z�hlvariable
'Kreieren der Ausgangsbedingung (Vorbelegung des Arrays)
For i = 1 To MaxSpalten 'Sicher ist sicher !
    HilfsProdukt(i) = 0
Next i
'Bilden der Varianten beginnend beim 1. Element
VariantenBilden (1)
End Sub
Private Sub maxWerk_Change()
        maxWerkst = maxWerk
End Sub

Private Sub Option1_Click(Index As Integer)
    Gitter1.Cols = MaxSpalten + 1
    If Option1(0).Value = True Then
        ' Anpassung Tabelle BENOETIGTE WERKSTUECKE
        Anz_Dimensionen = 1
        Gitter1.Rows = Anz_Dimensionen + 2
        Gitter1.Height = 1185
        ' Anpassung Button
        CmdBerechnen.Caption = "Berechnung starten"
    Else
        ' Anpassung Tabelle BENOETIGTE WERKSTUECKE
        Anz_Dimensionen = 2
        Gitter1.Rows = Anz_Dimensionen + 2
        Gitter1.Height = 1440
        ' Einzelne Zeile hinzufuegen und initialisieren
        Gitter1.Row = 3
        Gitter1.Col = 0
        Gitter1.Text = "H�he"
        For i = 1 To MaxSpalten
            Gitter1.Row = 3
            Gitter1.Col = i
            Gitter1.Text = Spalte(i).H�he
        Next i
        ' Anpassung Button
        CmdBerechnen.Caption = "Varianten eingeben"

End If
' Anpassung Tabelle AUSGANGSWERKSTUECK
Gitter2.Rows = 1
Gitter2.Cols = Anz_Dimensionen
For i = 0 To Anz_Dimensionen - 1
    Gitter2.ColWidth(i) = 800
Next i
Gitter2.Width = (Anz_Dimensionen + 1) * Val(Gitter2.ColWidth(0))
' �bertragen Globale Variablen
Gitter2.Row = 0
Gitter2.Col = 0
Gitter2.Text = Breite
If Anz_Dimensionen = 2 Then
    Gitter2.Row = 0
    Gitter2.Col = 1
    Gitter2.Text = H�he
End If
End Sub

Private Sub SicherAbfrage()
    
    ' Wird aufgerufen, wenn eine Datei oder das Programm
    ' geschlossen werden sollen und die gemachten �nderungen
    ' noch nicht gespeichert sind (CanClose = False)
    
    wert = MsgBox("�nderungen in Datei speichern ?", 35, "ACHTUNG")
    
    If wert = 6 Then     ' Ja
        Abspeichern ("n")
    ElseIf wert = 7 Then 'Nein
        CanClose = True
    End If

End Sub

Private Sub TabelleSortieren()

'Verwendetes Verfahren: Insertion Sort

Dim i, j, tmpPos As Integer
Dim tmpMax As Double
Dim tmpAnzahl, tmpBreite, tmpH�he As Variant

For i = 1 To AnzSpalten - 1 'pr�ft jedes Element bis auf das Letzte
    tmpMax = Val(Spalte(i).Breite) 'Umwandeln in eine Zahl
    tmpPos = i
    
    'Ermitteln von Position und Wert des gr��ten verbleibenden Elementes
    For j = (i + 1) To AnzSpalten
        If Val(Spalte(j).Breite) > tmpMax Then
            tmpMax = Val(Spalte(j).Breite)
            tmpPos = j
        End If
    Next j

    'Zwischenspeichern
    tmpAnzahl = Spalte(tmpPos).Anzahl
    tmpBreite = Spalte(tmpPos).Breite
    tmpH�he = Spalte(tmpPos).H�he
    'Verschieben der �brigen Elemente
    For j = (tmpPos - 1) To i Step -1
        Spalte(j + 1).Anzahl = Spalte(j).Anzahl
        Spalte(j + 1).Breite = Spalte(j).Breite
        Spalte(j + 1).H�he = Spalte(j).H�he
    Next j

    'Einf�gen des gefundenen Elementes
    Spalte(i).Anzahl = tmpAnzahl
    Spalte(i).Breite = tmpBreite
    Spalte(i).H�he = tmpH�he
Next i
End Sub

Private Sub TabelleStutzen()

Dim i, j As Integer
Dim tmpString As Variant

'Umwandeln in g�ltige Zahlen (existente Vorkommastelle, keine abschlie�enden
'Nullen und kein abschlie�endes Dezimaltrennzeichen)

' -------------------------------------------------
' f�r die Breite des Werkst�ckes
' -------------------------------------------------
tmpString = Str$(Val(Breite))
tmpString = Mid(tmpString, 2, Len(tmpString) - 1) 'Entfernen des Vorzeichens

'Setzen einer f�hrenden Null
If (Mid(tmpString, 1, 1) = ",") Or (Mid(tmpString, 1, 1) = ".") Then
    tmpString = "0" & tmpString
End If

For i = 1 To Len(tmpString)  'R�ckersetzung des Dezimalkommas durch Punkt
    If Mid(tmpString, i, 1) = "," Then
        Breite = Mid(tmpString, 1, i - 1) & "." & Mid(tmpString, i + 1, Len(tmpString) - i + 1)
    Else
        Breite = tmpString
    End If
Next i
' -------------------------------------------------
' f�r die H�he des Werkst�ckes
' -------------------------------------------------
tmpString = Str$(Val(H�he))
tmpString = Mid(tmpString, 2, Len(tmpString) - 1) 'Entfernen des Vorzeichens

'Setzen einer f�hrenden Null
If (Mid(tmpString, 1, 1) = ",") Or (Mid(tmpString, 1, 1) = ".") Then
    tmpString = "0" & tmpString
End If

For i = 1 To Len(tmpString)  'R�ckersetzung des Dezimalkommas durch Punkt
    If Mid(tmpString, i, 1) = "," Then
        H�he = Mid(tmpString, 1, i - 1) & "." & Mid(tmpString, i + 1, Len(tmpString) - i + 1)
    Else
        H�he = tmpString
    End If
Next i
' -------------------------------------------------
' Tabellenteil Breite
' -------------------------------------------------
For j = 1 To MaxSpalten
    tmpString = Str$(Val(Spalte(j).Breite))
    tmpString = Mid(tmpString, 2, Len(tmpString) - 1) 'Entfernen des Vorzeichens
    
    'Setzen einer f�hrenden Null
    If (Mid(tmpString, 1, 1) = ",") Or (Mid(tmpString, 1, 1) = ".") Then
        tmpString = "0" & tmpString
    End If
    
    For i = 1 To Len(tmpString) 'R�ckersetzung des Dezimalkommas durch Punkt
        If Mid(tmpString, i, 1) = "," Then
            Spalte(j).Breite = Mid(tmpString, 1, i - 1) & "." & Mid(tmpString, i + 1, Len(tmpString) - i + 1)
        Else
            Spalte(j).Breite = tmpString
        End If
    Next i
Next j
' -------------------------------------------------
' Tabellenteil H�he
' -------------------------------------------------
If Anz_Dimensionen = 2 Then
    For j = 1 To MaxSpalten
        tmpString = Str$(Val(Spalte(j).H�he))
        tmpString = Mid(tmpString, 2, Len(tmpString) - 1) 'Entfernen des Vorzeichens
        'Setzen einer f�hrenden Null
        If (Mid(tmpString, 1, 1) = ",") Or (Mid(tmpString, 1, 1) = ".") Then
            tmpString = "0" & tmpString
        End If
        For i = 1 To Len(tmpString) 'R�ckersetzung des Dezimalkommas durch Punkt
            If Mid(tmpString, i, 1) = "," Then
                Spalte(j).H�he = Mid(tmpString, 1, i - 1) & "." & Mid(tmpString, i + 1, Len(tmpString) - i + 1)
            Else
                Spalte(j).H�he = tmpString
            End If
        Next i
    Next j
End If
' -------------------------------------------------
' Tabellenteil Anzahl
' -------------------------------------------------
For j = 1 To MaxSpalten
    tmpString = Str$(Val(Spalte(j).Anzahl))
    tmpString = Mid(tmpString, 2, Len(tmpString) - 1) 'Entfernen des Vorzeichens
    
    'Setzen einer f�hrenden Null
    If (Mid(tmpString, 1, 1) = ",") Or (Mid(tmpString, 1, 1) = ".") Then
        tmpString = "0" & tmpString
    End If
    
    For i = 1 To Len(tmpString) 'R�ckersetzung des Dezimalkommas durch Punkt
        If Mid(tmpString, i, 1) = "," Then
            Spalte(j).Anzahl = Mid(tmpString, 1, i - 1) & "." & Mid(tmpString, i + 1, Len(tmpString) - i + 1)
        Else
            Spalte(j).Anzahl = tmpString
        End If
    Next i
Next j
' -------------------------------------------------
' Entfernen von Spalten ohne Eintr�ge
' -------------------------------------------------
AnzSpalten = MaxSpalten
i = 1
While i <= AnzSpalten
    'Breite, Anzahl oder H�he sind kleiner oder gleich 0 -> Spalte l�schen
    If (Val(Spalte(i).Breite) <= 0) Or (Val(Spalte(i).Anzahl) <= 0) Or (Val(Spalte(i).H�he) <= 0 And AnzDimensionen = 2) Then
        For j = i To (AnzSpalten - 1)
            Spalte(j).Breite = Spalte(j + 1).Breite
            Spalte(j).Anzahl = Spalte(j + 1).Anzahl
            If Anz_Dimensionen = 2 Then
                Spalte(j).H�he = Spalte(j + 1).H�he
            End If
        Next j
        AnzSpalten = AnzSpalten - 1
    Else
        i = i + 1  'eine Spalte weiterschalten
    End If
Wend
' -------------------------------------------------
End Sub

Private Sub VariantenBilden(Position As Integer)

'Rekursive Routine zur Bildung der m�glichen Kombinationen

Dim MaxAnz As Integer 'Maximal fassbare Anzahl, die noch reinpa�t
Dim i As Integer 'Z�hler

If Position > AnzSpalten Then 'Routine wurde falsch aufgerufen
    MsgBox "Falscher Aufruf der Kombinationsroutine !", 0, "Hinweis"
    Fehler = 1
    Exit Sub
End If

MaxAnz = VerschnittBestimmen() \ Val(Spalte(Position).Breite)

If Position = AnzSpalten Then 'Kleinstes Element
    AnzVars = AnzVars + 1 'Erh�hen des Variablenz�hlers

    'Solver kann nicht mehr Kombinationen verarbeiten
    If AnzVars > MaxVars Then
        MsgBox "Die Anzahl der verwendeten Kombinationen von " + Str$(AnzVars) + " ist gr��er als die Anzahl der verarbeitbaren Kombinationen von " + Str$(MaxVars) + ". Das Programm bricht hier ab.", 0, "Hinweis"
        Fehler = 1
        Unload Hauptfenster
        Exit Sub
    End If

    'auff�llen des restlichen Verschnittes
    HilfsProdukt(Position) = MaxAnz
    Eintragen '�bertr�gt die Werte in die Kombinations-Tabelle
Else
    For i = MaxAnz To 0 Step -1
        HilfsProdukt(Position) = i 'Belegung mit aktuellem Wert
        VariantenBilden (Position + 1)
    Next i
End If

'Belegung l�schen
HilfsProdukt(Position) = 0

End Sub

Private Function VerschnittBestimmen() As Double

Dim tmpSumme As Double
Dim i As Integer 'Z�hler

tmpSumme = 0
For i = 1 To MaxSpalten
    If HilfsProdukt(i) > 0 Then
        tmpSumme = tmpSumme + (HilfsProdukt(i) * Val(Spalte(i).Breite))
    End If
Next i
tmpSumme = Val(Breite) - tmpSumme

VerschnittBestimmen = tmpSumme

End Function

Private Sub Option2_Click(Index As Integer)
    If Option2(0).Value = True Then
        'Bezeichnung5.Visible = True
        'maxWerk.Visible = True
        maxWerk.Enabled = True
        maxWerk.BackColor = RGB(250, 250, 250)
    Else
        'Bezeichnung5.Visible = False
        'maxWerk.Visible = False
        maxWerk.Enabled = False
        maxWerk.BackColor = RGB(150, 150, 150)
        maxWerk = ""
    End If
End Sub

