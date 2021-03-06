VERSION 5.00
Object = "{A8B3B723-0B5A-101B-B22E-00AA0037B2FC}#1.0#0"; "GRID32.OCX"
Begin VB.Form Variante 
   Appearance      =   0  'Flat
   BackColor       =   &H8000000A&
   Caption         =   "Variante manuell eingeben"
   ClientHeight    =   4260
   ClientLeft      =   180
   ClientTop       =   1260
   ClientWidth     =   5670
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
   HelpContextID   =   12
   LinkTopic       =   "Form1"
   MDIChild        =   -1  'True
   PaletteMode     =   1  'UseZOrder
   ScaleHeight     =   4260
   ScaleWidth      =   5670
   Begin VB.CommandButton Command2 
      Appearance      =   0  'Flat
      Caption         =   "Berechnen"
      Height          =   375
      Left            =   360
      TabIndex        =   2
      Top             =   3720
      Width           =   1215
   End
   Begin VB.CommandButton Command1 
      Appearance      =   0  'Flat
      Caption         =   "Zur�ck"
      Height          =   375
      Left            =   1920
      TabIndex        =   1
      Top             =   3720
      Width           =   1215
   End
   Begin MSGrid.Grid Gitter3 
      Height          =   3150
      Left            =   120
      TabIndex        =   0
      Top             =   240
      Width           =   5415
      _Version        =   65536
      _ExtentX        =   2646
      _ExtentY        =   1323
      _StockProps     =   77
      BackColor       =   -2147483643
      BeginProperty Font {0BE35203-8F91-11CE-9DE3-00AA004BB851} 
         Name            =   "MS Sans Serif"
         Size            =   7.86
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Rows            =   10
   End
   Begin VB.Label Label1 
      Caption         =   "Ausgangsstoff Angeben (1,2,3 oder 4)"
      ForeColor       =   &H000000FF&
      Height          =   495
      Left            =   3240
      TabIndex        =   3
      Top             =   3720
      Width           =   2175
   End
End
Attribute VB_Name = "Variante"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Private Sub Command1_Click()
    Hauptfenster.mnuFensterVariante.Enabled = False
    Eingabe.Show 0
    Unload Variante
End Sub

Private Sub Command2_Click()
    Screen.MousePointer = 11    'Mouse-Cursor auf Sanduhr (WARTEN) stellen
    Varianten_Stutzen           'Tabelle justieren
    Verschnitt_Berechnen        'Verschnittberechnung
    Varianten_2_Gitter3         '�bertragung Tabelle auf Grid
    Verschnitt_Pr�fung          'Fl�chenpr�fung
    ' Wenn kein Fehler, dann Problem in Datei schreiben und Solver aufrufen
    If Fehler = 0 Then
        Berechnung
    End If
    ' Wenn kein Fehler, dann Loesung fuer das Problem anzeigen
    If Fehler = 0 Then
        Ausgabe.Show 0
    End If
    Screen.MousePointer = 0 ' Mouse-Cursor auf NORMAL stellen
End Sub

Private Sub Form_Load()
    Dim i As Integer
    Dim Column_Width As Integer
    
    ' Unload Eingabe   'falls ge�ffnet
    
    Height = 4665
    Width = 7500
    Top = (Hauptfenster.Height - 695 - Height) / 2
    Left = (Hauptfenster.Width - 125 - Width) / 2
    ' Festlegungen
    Column_Width = 1000
    CanClose = True
    IsActive = True
    
    ' Initialisierung des L�nge/Breite-Feldes
    Gitter3.Width = 8750
    Gitter3.ColWidth(0) = Column_Width
    
    ' Definition der Tabellengr��e
    Gitter3.Rows = MaxVars + 1
    Gitter3.Cols = AnzSpalten + 3
   
    ' Spalten�berschriften
    Gitter3.Row = 0
    For i = 1 To AnzSpalten
        Gitter3.ColWidth(i) = Column_Width
        Gitter3.Col = i
        Gitter3.Text = Spalte(i).Breite + " x " + Spalte(i).H�he
    Next i
    ' Spalten�berschrift f�r Variante
    Gitter3.Row = 0
    Gitter3.Col = 0
    Gitter3.ColWidth(AnzSpalten + 1) = Column_Width
    Gitter3.Text = "Variante"

    ' Spalten�berschrift f�r Verschnitt
    Gitter3.Col = AnzSpalten + 1
    Gitter3.ColWidth(AnzSpalten + 1) = Column_Width
    Gitter3.Text = "Verschnitt"
    
    ' Spalten�berschrift f�r Ausgangsprodukt
    Gitter3.Col = AnzSpalten + 2
    Gitter3.ColWidth(AnzSpalten + 2) = 1800
    Gitter3.Text = "Ausgangs Produkt"

    ' Gr��enanpassung Tabellenwert an Tabelle
    If (((2 + AnzSpalten) * Column_Width) + 1800) < Gitter3.Width Then
      Gitter3.Width = Column_Width * (2 + AnzSpalten) + 350 + 1800
    End If

    ' Tabelle initialisieren
    For i = 1 To MaxVars
        tmpString = Str$(i) 'umwandeln des Z�hlers
        tmpString = Mid(tmpString, 2, Len(tmpString) - 1) 'Vorzeichenstelle abschneiden
        Varianten(i).Variable = "X" & tmpString
    Next i
    ' Zeilen�berschriften
    Varianten_2_Gitter3
    'Cursorposition
    Gitter3.Row = 1
    Gitter3.Col = 1
End Sub

Private Sub Form_Unload(Cancel As Integer)
    Hauptfenster.mnuFensterVariante.Enabled = False
End Sub

Private Sub Gitter3_KeyPress(KeyAnsi As Integer)
Dim Xalt, Xneu, Yalt, Yneu, Komma, i As Integer
'In dieser Routine erfolgt die Dateneingabe in die Gitter-Tabelle

' Speicherung der alten Koordinaten (notwendig bei "Mausarbeit")
Xalt = Gitter3.Col
Yalt = Gitter3.Row

Xneu = Xalt   ' Neue Koordinaten f�r Positionierung
Yneu = Yalt

'  verwertbare Taste gedr�ckt ?
Select Case (KeyAnsi)
    Case 8
        ' R�ckschritt-Taste
        If Len(Gitter3.Text) > 0 Then
           ' => ein Zeichen entfernen
           Gitter3.Text = Mid(Gitter3.Text, 1, Len(Gitter3.Text) - 1)
        End If

    Case 44, 46   ' Punkt und Komma
        If Yalt = 1 Then      ' Nur bei der Breite zul�ssig !
        'MMMMMMMMMMMMMMMMMM Xalt ?????????????????
            Komma = 0
            For i = 1 To Len(Gitter3.Text)
                If Mid(Gitter3.Text, i, 1) = "." Then
                    Komma = 1
                End If
            Next i

            If Komma = 0 Then
                Gitter3.Text = Gitter3.Text & Chr(46)
            End If
        Else
            MsgBox "Bei der Anzahl sind nur ganzzahlige Eingaben erlaubt !", 0, "Hinweis"
        End If

    Case 48 To 57  ' Normale Ziffern
        If Gitter3.Text = Chr(48) Then
           Gitter3.Text = Chr(KeyAnsi)
           'Wenn nur eine "0" in dem Tabellenfeld steht,
           'dann einfach die gedr�ckte Zahl zuweisen
        Else
            Gitter3.Text = Gitter3.Text & Chr(KeyAnsi)
            'sonst mu� das Zeichen an die schon verhanden
            'Zeichenkette angef�gt werden
        End If

    Case 13 ' RETURN
        
        ' Weiterschalten des Eingabefeldes
        Xneu = Xneu + 1
        If Xneu > AnzSpalten Then
            Xneu = 1
            Yneu = Yneu + 1
        End If
        If Yneu > MaxVars Then
            Yneu = Yneu - 1
            Xneu = 1
        End If

End Select

' Eintragen des Wertes in die interne Tabelle
Gitter3.Col = Xalt
Gitter3.Row = Yalt

If Xalt = AnzSpalten + 2 Then
    If Gitter3.Text = 1 Then
        Varianten(Yalt).Ausgang = 0
    End If
    If Gitter3.Text = 2 Then
        Varianten(Yalt).Ausgang = 1
    End If
    If Gitter3.Text = 3 Then
        Varianten(Yalt).Ausgang = 2
    End If
    If Gitter3.Text = 4 Then
        Varianten(Yalt).Ausgang = 3
    End If
Else
    Varianten(Yalt).Produkt(Xalt) = Gitter3.Text
End If

' Setzt den Eingabefokus
Gitter3.SelStartCol = Xneu
Gitter3.SelEndCol = Xneu
Gitter3.SelStartRow = Yneu
Gitter3.SelEndRow = Yneu

' Setzt den Tabellencursor
Gitter3.Col = Xneu
Gitter3.Row = Yneu

' Setze Knz fuer Sicherheitsabfrage bei Programmende
CanClose = False

End Sub

Private Sub Varianten_2_Gitter3()
Dim Zeile, Spalte As Integer
For Zeile = 1 To MaxVars
    ' Variable
    Gitter3.Col = 0
    Gitter3.Row = Zeile
    Gitter3.Text = Varianten(Zeile).Variable
    ' Produktpalatte
    For Spalte = 1 To AnzSpalten
        Gitter3.Col = Spalte
        Gitter3.Row = Zeile
        Gitter3.Text = Str$(Varianten(Zeile).Produkt(Spalte))
    Next Spalte
    ' Verschnitt
    Gitter3.Col = AnzSpalten + 1
    Gitter3.Row = Zeile
    Gitter3.Text = Str$(Varianten(Zeile).Verschnitt)
Next Zeile
End Sub

Private Sub Varianten_Stutzen()
Dim i, j, Zeile, Spalte, Knz_Belegt As Integer
Dim tmpString As Variant
'Umwandeln in g�ltige Zahlen (existente Vorkommastelle, keine abschlie�enden
'Nullen und kein abschlie�endes Dezimaltrennzeichen)
' -------------------------------------------------
' Tabellenteil Produktpalette
' -------------------------------------------------
For Zeile = 1 To MaxVars
    For j = 1 To AnzSpalten
        tmpString = Str$(Val(Varianten(Zeile).Produkt(j)))
        tmpString = Mid(tmpString, 2, Len(tmpString) - 1) 'Entfernen des Vorzeichens
        'Setzen einer f�hrenden Null
        If (Mid(tmpString, 1, 1) = ",") Or (Mid(tmpString, 1, 1) = ".") Then
            tmpString = "0" & tmpString
        End If
        For i = 1 To Len(tmpString) 'R�ckersetzung des Dezimalkommas durch Punkt
            If Mid(tmpString, i, 1) = "," Then
                Varianten(Zeile).Produkt(j) = Mid(tmpString, 1, i - 1) & "." & Mid(tmpString, i + 1, Len(tmpString) - i + 1)
            Else
                Varianten(Zeile).Produkt(j) = tmpString
            End If
        Next i
    Next j
Next Zeile
' -------------------------------------------------
' Entfernen von Zeilen ohne Eintr�ge
' -------------------------------------------------
For j = 1 To MaxVars - 1
    For Zeile = j To MaxVars - 1
        ' Pr�fung, ob ein Wert in einer Zelle vorhanden
        Knz_Belegt = 0
        For Spalte = 1 To AnzSpalten
            If Val(Varianten(Zeile).Produkt(Spalte)) > 0 Then
                Knz_Belegt = 1
                Exit For
            End If
        Next Spalte
        'Wenn kein Wert in der Zeile vorhand -> Zeile l�schen
        If Knz_Belegt = 0 Then
            ' Verschieben von allen Spalten um eine Zeile nach oben
             Varianten(Zeile).Anzahl = Varianten(Zeile + 1).Anzahl
             Varianten(Zeile + 1).Anzahl = 0
             Varianten(Zeile).Verschnitt = Varianten(Zeile + 1).Verschnitt
             Varianten(Zeile + 1).Verschnitt = 0
             For Spalte = 1 To AnzSpalten
                Varianten(Zeile).Produkt(Spalte) = Varianten(Zeile + 1).Produkt(Spalte)
                Varianten(Zeile + 1).Produkt(Spalte) = 0
             Next Spalte
        End If
    Next Zeile
    ' Wenn einmal getauscht wurde, dann Anzahl Zeilen - 1
Next j
' -------------------------------------------------
AnzZeilen = 0
For Zeile = 1 To MaxVars
    For Spalte = 1 To AnzSpalten
        If Val(Varianten(Zeile).Produkt(Spalte)) > 0 Then
            AnzZeilen = AnzZeilen + 1
            Exit For
        End If
    Next Spalte
Next Zeile
AnzVars = AnzZeilen
' -------------------------------------------------
End Sub

Private Sub Verschnitt_Berechnen()
Dim Zeile As Integer
Dim Row As Integer
Dim Fl�che_Zelle As Variant

For Zeile = 1 To AnzZeilen
    nu = Varianten(Zeile).Ausgang
    Varianten(Zeile).Verschnitt = H�he(nu) * Breite(nu)
    For Row = 1 To AnzSpalten
        Fl�che_Zelle = Spalte(Row).H�he * Spalte(Row).Breite * Varianten(Zeile).Produkt(Row)
        Varianten(Zeile).Verschnitt = Varianten(Zeile).Verschnitt - Fl�che_Zelle
    Next Row
Next Zeile
End Sub

Private Sub Verschnitt_Pr�fung()
Dim Zeile, Prd, i As Integer
' Fehlervorbelegung
Fehler = 0
' Es sind keine Varianten da (Tabelle ist leer)
If AnzVars = 0 Then
    MsgBox "Es wurden keine Varianten angegeben!", 0, "Hinweis"
    Fehler = 1
    Exit Sub
End If
' Wenn ein Produkt geschnitten werden soll, dann sollten dafuer auch
' Zahlen da sein.
For Prd = 1 To AnzSpalten
    i = 0
    For Zeile = 1 To AnzVars
        If Varianten(Zeile).Produkt(Prd) <> 0 Then
            i = i + 1
        End If
    Next Zeile
    If i = 0 Then
        MsgBox "Es soll ein Produkt " + Str(Prd) + " geschnitten werden. Es sind aber keine Daten in den Zuschnittsvarianten daf�r zu finden.", 0, "Hinweis"
        Fehler = 1
        Exit Sub
    End If
Next Prd
' Es darf keinen negativen Verschnitt geben
For Zeile = 1 To MaxVars
    If Varianten(Zeile).Verschnitt < 0 Then
        MsgBox "Die Fl�che der Produkte f�r die Variante " + Str$(Zeile) + " ist zu gro� f�r das Werkst�ck.", 0, "Hinweis"
        Fehler = 1
        Exit Sub
    End If
Next Zeile
' Ende der Routine
End Sub

