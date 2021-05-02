VERSION 2.00
Begin Form GRID_IN 
   BackColor       =   &H00FFFFFF&
   Caption         =   "Eingabe der Tätigkeiten"
   ClientHeight    =   5610
   ClientLeft      =   1200
   ClientTop       =   1605
   ClientWidth     =   7935
   ForeColor       =   &H00000000&
   Height          =   6015
   Left            =   1140
   LinkTopic       =   "Form4"
   MDIChild        =   -1  'True
   ScaleHeight     =   5610
   ScaleWidth      =   7935
   Top             =   1260
   Width           =   8055
   Begin CommandButton Grid_speichern 
      Caption         =   "&Speichern"
      Height          =   375
      Left            =   3360
      TabIndex        =   4
      Top             =   5040
      Width           =   1215
   End
   Begin TextBox txtin 
      Alignment       =   1  'Rechts
      BackColor       =   &H00FFFF00&
      ForeColor       =   &H00000000&
      Height          =   285
      Left            =   3240
      TabIndex        =   3
      Top             =   210
      Width           =   1000
   End
   Begin CommandButton Abbrechen 
      Caption         =   "&Abbrechen"
      Height          =   375
      Left            =   5520
      TabIndex        =   2
      Top             =   5040
      Width           =   1215
   End
   Begin CommandButton grid_in_weiter 
      Caption         =   "Be&rechnen"
      Height          =   375
      Left            =   1200
      TabIndex        =   1
      Top             =   5040
      Width           =   1215
   End
   Begin Grid Liste 
      BackColor       =   &H00FFFFFF&
      Height          =   4095
      Left            =   480
      TabIndex        =   0
      Top             =   720
      Width           =   6975
   End
   Begin Line Linie1 
      X1              =   240
      X2              =   7680
      Y1              =   600
      Y2              =   600
   End
   Begin Label Bezeichnung1 
      BackStyle       =   0  'Transparent
      Caption         =   "ZEILEN - SPALTEN EDITOR:"
      Height          =   255
      Left            =   480
      TabIndex        =   5
      Top             =   240
      Width           =   2655
   End
End
' GRID_IN.FRM
Option Explicit

Sub Abbrechen_Click ()
    Unload Me
    newnet.Show 0      ' nach Verlassen des grid_in wird wieder newnet angezeigt
End Sub

Sub dateneinsetzen ()

Dim RowAnzahl As Integer
Dim dsnr As Integer

    If AnzahlKnoten = "" Then
        RowAnzahl = 10
    Else
        RowAnzahl = Val(AnzahlKnoten) + 1
    End If

    For dsnr = 1 To RowAnzahl - 1
        GRID_IN.Liste.SelStartCol = 1
        GRID_IN.Liste.SelStartRow = dsnr
        GRID_IN.Liste.SelEndCol = 5
        GRID_IN.Liste.SelEndRow = dsnr
        GRID_IN.Liste.Clip = LTrim(Str(U(dsnr).do_i)) + Chr$(9) + LTrim(Str(U(dsnr).do_j)) + Chr$(9) + LTrim(Str(U(dsnr).dnorm)) + Chr$(9) + LTrim(Str(U(dsnr).dmin)) + Chr$(9) + LTrim(Str(U(dsnr).w))
    Next dsnr

End Sub

Sub Form_KeyPress (p1 As Integer)

' zu was ???


Dim l0034 As Variant
Dim l0038 As Variant
Dim l003C As Variant
Dim l0040 As Variant
     
     l0034 = GRID_IN.Liste.ColWidth(0)
     l0038 = GRID_IN.Liste.ColWidth(1)
     l003C = GRID_IN.Liste.RowHeight(0)
     l0040 = GRID_IN.Liste.RowHeight(1)

End Sub

Sub Form_Load ()

' Ausfuehrung wenn Form das erstemal geladen wird

Dim lfdnr As Integer
  
    Height = 6015
    Width = 8080
    top = (StartFormNetzplan.Height - 695 - Height) / 2
    left = (StartFormNetzplan.Width - 125 - Width) / 2

    Me.BackColor = RGB(255, 225, 225)


    lfdnr = 1
    liste_fuellen           ' Aufbau und beschriftung leere Liste

    If status = 1 Then      ' Nur bei einer geoffneten Datei ist es moglich
        dateneinsetzen      ' Daten aus Datenstruktur werden in Tabelle eingesetzt
    End If
    SendKeys "{Tab 3}"      ' Positionieren des Cursors auf Zeilen-Editor
    posx = 1                ' Positionieren der Variablen auf 1,1
    posy = 1                ' Positionieren der Variablen auf 1,1

End Sub

Sub Grid_Graphik_Click ()
    GRAPH_Datei_schreiben
End Sub

Sub grid_in_weiter_Click ()

' Berechnung starten

Dim x As Integer

    If datopenflag = 0 Then

        io.StdDialog1.Filename = "neu.net"
        io.StdDialog1.InitDir = dbpath
        io.StdDialog1.Action = 2
        
        If io.StdDialog1.Filename <> "neu.net" Then
           Speichern_HD
           datopenflag = 1
           akt_Datnam
        End If
        
    End If

    status = 1
    
    ' Berechnung starten
    Berechnung

    Unload GRID_IN
    'XCONTROL.Show 1

End Sub

Sub Grid_speichern_Click ()

    If Right(dbpath, 1) = "\" Then
        io.StdDialog1.InitDir = Left(dbpath, Len(dbpath) - 1)
    Else
        io.StdDialog1.InitDir = dbpath
    End If

    io.StdDialog1.Action = 2
    
    If io.StdDialog1.Filename <> "" Then
        Speichern_HD
        akt_Datnam
        datopenflag = 1
    End If

End Sub

Function holen (x As Integer, y As Integer) As String
    
    Select Case x
        Case 1
            holen = Str(U(y).do_i)
        Case 2
            holen = Str(U(y).do_j)
        Case 3
            holen = Str(U(y).dnorm)
        Case 4
            holen = Str(U(y).dmin)
        Case 5
            holen = Str(U(y).w)
      End Select

End Function

Sub Liste_Click ()
    posy = GRID_IN.Liste.Row
    posx = GRID_IN.Liste.Col
    GRID_IN.Liste.SelStartRow = posy
    GRID_IN.Liste.SelEndRow = posy
    GRID_IN.Liste.SelStartCol = posx
    GRID_IN.Liste.SelEndCol = posx
    GRID_IN.Liste.HighLight = True
    GRID_IN.txtin.Text = Trim$(holen(posx, posy))
    SendKeys "{tab 3}"
    SendKeys "+{END}"
End Sub

Sub Liste_DblClick ()
    Steuerung
End Sub

Sub liste_fuellen ()

Dim RowAnzahl As Integer
Dim dsnr As Variant

    screen.MousePointer = 11
    
    If AnzahlKnoten = "" Then
        AnzahlKnoten = "10"
        RowAnzahl = Val(AnzahlKnoten) + 1
    Else
        RowAnzahl = Val(AnzahlKnoten) + 1
    End If
    
    GRID_IN.Liste.Cols = 6
    GRID_IN.Liste.Rows = RowAnzahl
    GRID_IN.Liste.FixedAlignment(0) = 2
    GRID_IN.Liste.ColWidth(0) = 1000
    GRID_IN.Liste.Row = 0
    GRID_IN.Liste.Col = 1
    GRID_IN.Liste.ColWidth(1) = 1000
    GRID_IN.Liste.Text = "von"
    GRID_IN.Liste.Col = 2
    GRID_IN.Liste.ColWidth(2) = 1000
    GRID_IN.Liste.Text = "nach"
    GRID_IN.Liste.Col = 3
    GRID_IN.Liste.ColWidth(3) = 1000
    GRID_IN.Liste.Text = "Dnorm"
    GRID_IN.Liste.Col = 4
    GRID_IN.Liste.ColWidth(4) = 1000
    GRID_IN.Liste.Text = "Dmin"
    GRID_IN.Liste.Col = 5
    GRID_IN.Liste.ColWidth(5) = 1000
    GRID_IN.Liste.Text = "V-Kosten"
    
    For dsnr = 1 To RowAnzahl
        GRID_IN.Liste.Col = 0
        GRID_IN.Liste.Row = dsnr - 1
        If Not (dsnr - 1) = 0 Then
            GRID_IN.Liste.Text = "Kante" + (dsnr - 1)
        End If
    Next dsnr

    screen.MousePointer = 0

End Sub

Sub txtin_KeyPress (p1 As Integer)
    If p1 = 13 Then
        Steuerung
    End If
End Sub

