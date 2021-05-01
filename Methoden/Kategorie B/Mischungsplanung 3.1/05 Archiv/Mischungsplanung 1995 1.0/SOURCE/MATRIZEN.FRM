VERSION 2.00
Begin Form frmEingabe 
   Caption         =   "Eingabe - Matrizen"
   ClientHeight    =   5145
   ClientLeft      =   1290
   ClientTop       =   1935
   ClientWidth     =   9210
   Height          =   5550
   Left            =   1230
   LinkTopic       =   "Form1"
   MDIChild        =   -1  'True
   ScaleHeight     =   5145
   ScaleWidth      =   9210
   Top             =   1590
   Width           =   9330
   Begin CommandButton btnLegende 
      Caption         =   "Legende"
      Height          =   375
      Left            =   7920
      TabIndex        =   7
      Top             =   1920
      Width           =   1095
   End
   Begin CommandButton cmdHILFE 
      Caption         =   "Hilfe"
      Height          =   375
      Left            =   7920
      TabIndex        =   6
      Top             =   720
      Width           =   1095
   End
   Begin CommandButton CmdAbbrechen 
      Caption         =   "Abbrechen"
      Height          =   375
      Left            =   7920
      TabIndex        =   5
      Top             =   1200
      Width           =   1095
   End
   Begin CommandButton CmdBerechnen 
      Caption         =   "Berechnen"
      Height          =   375
      Left            =   7920
      TabIndex        =   4
      Top             =   240
      Width           =   1095
   End
   Begin Grid Gitter2 
      Height          =   1935
      Left            =   240
      TabIndex        =   3
      Top             =   3000
      Width           =   7455
   End
   Begin Grid Gitter1 
      Height          =   1935
      Left            =   240
      TabIndex        =   0
      Top             =   480
      Width           =   7455
   End
   Begin Line Linie1 
      BorderColor     =   &H00C0C0C0&
      X1              =   8040
      X2              =   8880
      Y1              =   1750
      Y2              =   1750
   End
   Begin Label Bezeichnung2 
      BackStyle       =   0  'Transparent
      Caption         =   "Zielprodukte:"
      Height          =   255
      Left            =   240
      TabIndex        =   2
      Top             =   2640
      Width           =   1215
   End
   Begin Label Bezeichnung1 
      BackStyle       =   0  'Transparent
      Caption         =   "Ausgangsstoffe:"
      Height          =   255
      Left            =   240
      TabIndex        =   1
      Top             =   120
      Width           =   1575
   End
End
Option Explicit


Sub btnLegende_Click ()
  
  frmLegende.Show

End Sub

Sub CmdAbbrechen_Click ()

    'rueckschritt = True
    Unload frmEingabe

End Sub

Sub CmdBerechnen_Click ()
    
    Werteübergabe    ' Sub. in MI_GLOB.BAS

    'Unload frmEingabe

    ' Aufruf der XA-Generierung und Berechnung
    Load Berechnung
    Unload Berechnung
    
    MDIForm1.mnuFensterEingabe.Enabled = True
    MDIForm1.mnuFensterAusgabe.Enabled = True

End Sub

Sub cmdHILFE_Click ()

    winhelp hWnd, EXEPath + "\" + App.HelpFile, 257, ByVal "Eingabefenster"

End Sub

Sub CmdSpeichern_Click ()

    Werteübergabe

    Auf_Platte ("n")

End Sub

Sub Form_Load ()
                         
    Dim i
                                'bezeichnung3.Caption = getversion()
    Unload InfoFenster  'falls geöffnet

    '-- Größe und Positionierung eines ungeordneten Fensters
    Height = 5540
    Width = 9500
    top = (MDIForm1.Height - 695 - Height) / 2
    Left = (MDIForm1.Width - 125 - Width) / 2
    Backcolor = RGB(230, 240, 250)


    InitTabellen  ' lokale Suppe
    
    'Datei geöffnet: -> Gittergatter2 übergeben
    If geladen = True Then
       FüllenTabellen
    End If

    'Cursorposition
    Gitter1.Row = 1
    Gitter1.Col = 1

    Gitter2.Row = 1
    Gitter2.Col = 1

End Sub

Sub Form_Resize ()

   cmdBerechnen.Left = Width - 1380
   cmdHilfe.Left = Width - 1380
   cmdAbbrechen.Left = Width - 1380
 
   Linie1.X1 = Width - 1260
   Linie1.X2 = Width - 420

   btnLegende.Left = Width - 1380


   ' Gatter-Anzeigen: Weite variieren
   Gitter1.Width = Width - 1845
   Gitter2.Width = Width - 1845

End Sub

Sub FüllenTabellen ()
    
' Wird nur beim Öffnen benötigt, da der erste Übertrag auf
' die glob. Tabs. erfolgt

    Dim i As Integer


    For i = 1 To aZeilen
        Gitter1.Row = i

        Gitter1.Col = 1: Gitter1.Text = A(i).AName
        Gitter1.Col = 2: Gitter1.Text = A(i).AMenge1
        Gitter1.Col = 3: Gitter1.Text = A(i).AMenge2
        Gitter1.Col = 4: Gitter1.Text = A(i).kosten
        Gitter1.Col = 5: Gitter1.Text = A(i).D1
        Gitter1.Col = 6: Gitter1.Text = A(i).D2
        Gitter1.Col = 7: Gitter1.Text = A(i).D3
        Gitter1.Col = 8: Gitter1.Text = A(i).D4
        Gitter1.Col = 9: Gitter1.Text = A(i).D5
        Gitter1.Col = 10: Gitter1.Text = A(i).D6
        Gitter1.Col = 11: Gitter1.Text = A(i).D7
        Gitter1.Col = 12: Gitter1.Text = A(i).D8
    Next i
                            
    For i = 1 To zZeilen
        Gitter2.Row = i

        Gitter2.Col = 1: Gitter2.Text = Z(i).ZName
        Gitter2.Col = 2: Gitter2.Text = Z(i).ZMenge1
        Gitter2.Col = 3: Gitter2.Text = Z(i).ZMenge2
        Gitter2.Col = 4: Gitter2.Text = Z(i).erlöse
        Gitter2.Col = 5: Gitter2.Text = Z(i).T1
        Gitter2.Col = 6: Gitter2.Text = Z(i).T2
        Gitter2.Col = 7: Gitter2.Text = Z(i).T3
        Gitter2.Col = 8: Gitter2.Text = Z(i).T4
        Gitter2.Col = 9: Gitter2.Text = Z(i).T5
        Gitter2.Col = 10: Gitter2.Text = Z(i).T6
        Gitter2.Col = 11: Gitter2.Text = Z(i).T7
        Gitter2.Col = 12: Gitter2.Text = Z(i).T8
    Next i

End Sub

Sub Gitter1_KeyDown (keycode As Integer, Shift As Integer)

'Dim X, Y As Integer

'In dieser Routine erfolgt die Dateneingabe in die Tabelle
' daten_geandert = True  'Flag gesetzt, d.h. es werden Daten verändert


' Entfernen-Taste
'If (keycode = 46) Then
'    gitter1.Text = ""   'Eintrag komplett löschen
'End If


' Rückschritt-Taste
'If (keycode = 8 And Len(gitter1.Text) > 0) Then
'   ' => ein Zeichen entfernen
'   gitter1.Text = Mid(gitter1.Text, 1, Len(gitter1.Text) - 1)
'End If


'  verwertbare Taste gedrückt ?
'If ((keycode > 47 And keycode < 58) Or (keycode > 64 And keycode < 91) Or (keycode > 96 And keycode < 123)) Then
'    If gitter1.Text = Chr(48) Then
'       gitter1.Text = Chr(keycode) 'Wenn nur eine "0" in dem Tabellenfeld
'                    'steht, dann einfach die gedrückte Zahl zuweisen
'    Else
'       gitter1.Text = gitter1.Text & Chr(keycode)   'sonst muß das Zeichen
'                    'an die schon verhanden Zeichenkette angefügt werden
'    End If
'End If
'


' Punkt-Komma-Scheiß (wahrlich wg. keybgr)
'If keycode = 188 Then gitter1.Text = gitter1.Text & Chr(44)


'Wenn RETURN gedrückt wird, dann Eingabe-Cursor weiterbewegen
'If (keycode = 13) Then
'    X = gitter1.Row + 1
'    Y = 1  'gitter1.Col
'
'    If X > 20 Then X = 1: Y = 1: 'OK.SetFocus
'
'    gitter1.SelStartRow = X    'Mit SelStart.. und SelEnd..
'    gitter1.SelEndRow = X      'Wird ein Bereich markiert
'    gitter1.SelStartCol = Y    'Hier wird also immer nur eine Zelle markiert
'    gitter1.SelEndCol = Y      'd.h. diese kann man editieren
'End If

                               
End Sub

Sub Gitter1_KeyPress (keyansi As Integer)

Dim X, Y As Integer

'In dieser Routine erfolgt die Dateneingabe in die Gitter-Tabelle


' Entfernen-Taste
'If (keyansi = 46) Then
'    gitter1.Text = ""   'Eintrag komplett löschen
'End If

   
'  verwertbare Taste gedrückt ?
Select Case (keyansi)
    Case 8
        ' Rückschritt-Taste
        If Len(Gitter1.Text) > 0 Then
           ' => ein Zeichen entfernen
           Gitter1.Text = Mid(Gitter1.Text, 1, Len(Gitter1.Text) - 1)
        End If

    Case 44
        Gitter1.Text = Gitter1.Text & Chr(46)

    Case 32 To 255
        If Gitter1.Text = Chr(48) Then
           Gitter1.Text = Chr(keyansi)
           'Wenn nur eine "0" in dem Tabellenfeld steht,
           'dann einfach die gedrückte Zahl zuweisen
        Else
            Gitter1.Text = Gitter1.Text & Chr(keyansi)
            'sonst muß das Zeichen an die schon verhanden
            'Zeichenkette angefügt werden
        End If

    Case 13
        'Wenn RETURN gedrückt wird, dann Eingabe-Cursor weiterbewegen
        X = Gitter1.Row + 1
        Y = 1

        If X = MAXZEILEN Then X = 1: Y = 1
    
        Gitter1.SelStartRow = X    'Mit SelStart.. und SelEnd..
        Gitter1.SelEndRow = X      'Wird ein Bereich markiert
        Gitter1.SelStartCol = Y    'Hier wird also immer nur eine Zelle markiert
        Gitter1.SelEndCol = Y      'd.h. diese kann man editieren

        Gitter1.Row = X
        Gitter1.Col = Y

End Select


End Sub

Sub Gitter2_KeyPress (keyansi As Integer)

Dim X, Y As Integer

'In dieser Routine erfolgt die Dateneingabe in die Gitter-Tabelle


' Entfernen-Taste
'If (keyansi = ??) Then
'    gitter2.Text = ""   'Eintrag komplett löschen
'End If

   
'  verwertbare Taste gedrückt ?
Select Case (keyansi)
    Case 8
        ' Rückschritt-Taste
        If Len(Gitter2.Text) > 0 Then
           ' => ein Zeichen entfernen
           Gitter2.Text = Mid(Gitter2.Text, 1, Len(Gitter2.Text) - 1)
        End If

    Case 44
        Gitter2.Text = Gitter2.Text & Chr(46)

    Case 32 To 255
        If Gitter2.Text = Chr(48) Then
           Gitter2.Text = Chr(keyansi)
           'Wenn nur eine "0" in dem Tabellenfeld steht,
           'dann einfach die gedrückte Zahl zuweisen
        Else
            Gitter2.Text = Gitter2.Text & Chr(keyansi)
            'sonst muß das Zeichen an die schon verhanden
            'Zeichenkette angefügt werden
        End If

    Case 13
        'Wenn RETURN gedrückt wird, dann Eingabe-Cursor weiterbewegen
        X = Gitter2.Row + 1
        Y = 1

        If X = MAXZEILEN Then X = 1: Y = 1
    
        Gitter2.SelStartRow = X    'Mit SelStart.. und SelEnd..
        Gitter2.SelEndRow = X      'Wird ein Bereich markiert
        Gitter2.SelStartCol = Y    'Hier wird also immer nur eine Zelle markiert
        Gitter2.SelEndCol = Y      'd.h. diese kann man editieren

        Gitter2.Row = X
        Gitter2.Col = Y

End Select

End Sub

Sub InitTabellen ()

    Dim i As Integer

    ' Ausgangsstoffe ----------------------------------------------

    Gitter1.Rows = MAXZEILEN
    Gitter1.Cols = 13

    Gitter1.ColWidth(0) = 250
    Gitter1.ColWidth(1) = 900
    Gitter1.ColWidth(2) = 1200
    Gitter1.ColWidth(3) = 1200
    Gitter1.ColWidth(4) = 1000
    Gitter1.ColWidth(5) = 900
    Gitter1.ColWidth(6) = 900
    Gitter1.ColWidth(7) = 900
    Gitter1.ColWidth(8) = 900
    Gitter1.ColWidth(9) = 900
    Gitter1.ColWidth(10) = 900
    Gitter1.ColWidth(11) = 900
    Gitter1.ColWidth(12) = 900

    Gitter1.Col = 0
    For i = 1 To MAXZEILEN - 1
        Gitter1.Row = i
        Gitter1.Text = i
    Next i

    ' Spaltenüberschriften
    Gitter1.Row = 0

    Gitter1.Col = 1
    Gitter1.Text = "Name"
    Gitter1.Col = 2
    Gitter1.Text = "Obergrenze"
    Gitter1.Col = 3
    Gitter1.Text = "Untergrenze"
    Gitter1.Col = 4
    Gitter1.Text = "Kosten"
    Gitter1.Col = 5
    Gitter1.Text = "EA 1"
    Gitter1.Col = 6
    Gitter1.Text = "EA 2"
    Gitter1.Col = 7
    Gitter1.Text = "EA 3"
    Gitter1.Col = 8
    Gitter1.Text = "EA 4"
    Gitter1.Col = 9
    Gitter1.Text = "EA 5"
    Gitter1.Col = 10
    Gitter1.Text = "EA 6"
    Gitter1.Col = 11
    Gitter1.Text = "EA 7"
    Gitter1.Col = 12
    Gitter1.Text = "EA 8"

           
    ' Zielprodukte ------------------------------------------------
    
    Gitter2.Rows = MAXZEILEN
    Gitter2.Cols = 13
    
    Gitter2.ColWidth(0) = 250
    Gitter2.ColWidth(1) = 900
    Gitter2.ColWidth(2) = 1200
    Gitter2.ColWidth(3) = 1200
    Gitter2.ColWidth(4) = 1000
    Gitter2.ColWidth(5) = 900
    Gitter2.ColWidth(6) = 900
    Gitter2.ColWidth(7) = 900
    Gitter2.ColWidth(8) = 900
    Gitter2.ColWidth(9) = 900
    Gitter2.ColWidth(10) = 900
    Gitter2.ColWidth(11) = 900
    Gitter2.ColWidth(12) = 900


    Gitter2.Col = 0
    For i = 1 To MAXZEILEN - 1
        Gitter2.Row = i
        Gitter2.Text = i
    Next i

    ' Spaltenüberschriften
    Gitter2.Row = 0

    Gitter2.Col = 1
    Gitter2.Text = "Name"
    Gitter2.Col = 2
    Gitter2.Text = "Zielmenge1"
    Gitter2.Col = 3
    Gitter2.Text = "Zielmenge2"
    Gitter2.Col = 4
    Gitter2.Text = "Erlöse"
    Gitter2.Col = 5
    Gitter2.Text = "EZ 1"
    Gitter2.Col = 6
    Gitter2.Text = "EZ 2"
    Gitter2.Col = 7
    Gitter2.Text = "EZ 3"
    Gitter2.Col = 8
    Gitter2.Text = "EZ 4"
    Gitter2.Col = 9
    Gitter2.Text = "EZ 5"
    Gitter2.Col = 10
    Gitter2.Text = "EZ 6"
    Gitter2.Col = 11
    Gitter2.Text = "EZ 7"
    Gitter2.Col = 12
    Gitter2.Text = "EZ 8"

End Sub

