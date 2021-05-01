VERSION 2.00
Begin Form NETZOUT 
   BackColor       =   &H00FFFFFF&
   Caption         =   "NETZPLAN"
   ClientHeight    =   5715
   ClientLeft      =   915
   ClientTop       =   1575
   ClientWidth     =   8925
   ForeColor       =   &H00FFFF80&
   Height          =   6120
   Left            =   855
   LinkTopic       =   "Form1"
   MDIChild        =   -1  'True
   ScaleHeight     =   5715
   ScaleWidth      =   8925
   Top             =   1230
   Width           =   9045
   Begin ListBox ListeLaufzeit 
      Height          =   615
      Left            =   6360
      TabIndex        =   13
      Top             =   480
      Width           =   2295
   End
   Begin CommandButton cmdErgebnisdatei 
      Caption         =   "&Ergebnisdatei"
      Height          =   375
      Left            =   960
      TabIndex        =   12
      Top             =   5160
      Width           =   1335
   End
   Begin CommandButton Graph_Button 
      Caption         =   "&Graph"
      Height          =   375
      Left            =   2760
      TabIndex        =   11
      Top             =   5160
      Width           =   1335
   End
   Begin CommandButton Druck_Button 
      Caption         =   "&Drucken"
      Height          =   375
      Left            =   4680
      TabIndex        =   10
      Top             =   5160
      Width           =   1335
   End
   Begin CommandButton cmdZurück 
      Caption         =   "&Zurück"
      Height          =   375
      Left            =   6600
      TabIndex        =   9
      Top             =   5160
      Width           =   1335
   End
   Begin TextBox KostenTag 
      Height          =   285
      Left            =   1515
      TabIndex        =   8
      Text            =   " "
      Top             =   165
      Width           =   1110
   End
   Begin TextBox Text3 
      Height          =   285
      Left            =   4800
      TabIndex        =   1
      Text            =   "Text3"
      Top             =   600
      Width           =   1095
   End
   Begin TextBox Text2 
      Height          =   285
      Left            =   4800
      TabIndex        =   4
      Text            =   "Text2"
      Top             =   120
      Width           =   1095
   End
   Begin TextBox Text1 
      Height          =   285
      Left            =   1530
      TabIndex        =   5
      Text            =   "Text1"
      Top             =   540
      Width           =   1095
   End
   Begin Grid Liste 
      DragIcon        =   NETZOUT.FRX:0000
      Height          =   3495
      Left            =   120
      TabIndex        =   0
      Top             =   1440
      Width           =   8655
   End
   Begin Label BezLaufzeit 
      BackColor       =   &H00FFC0FF&
      Caption         =   "Einzellaufzeiten:"
      Height          =   255
      Left            =   6360
      TabIndex        =   14
      Top             =   120
      Width           =   1575
   End
   Begin Label Bezeichnung1 
      BackColor       =   &H0080FF80&
      Caption         =   "Kosten/Tag:"
      Height          =   225
      Left            =   240
      TabIndex        =   7
      Top             =   180
      Width           =   1095
   End
   Begin Label Bezeichnung6 
      BackColor       =   &H00FFFF80&
      Caption         =   "darin enthaltene Zusatzkosten:"
      Height          =   420
      Left            =   3120
      TabIndex        =   6
      Top             =   555
      Width           =   1455
   End
   Begin Label Bezeichnung4 
      BackColor       =   &H0080FFFF&
      Caption         =   "Gesamtkosten:"
      Height          =   255
      Left            =   3120
      TabIndex        =   3
      Top             =   120
      Width           =   1455
   End
   Begin Label Bezeichnung3 
      BackColor       =   &H0080FF80&
      Caption         =   "Laufzeit:"
      Height          =   255
      Index           =   0
      Left            =   240
      TabIndex        =   2
      Top             =   570
      Width           =   1110
   End
End
' NETZOUT.FRM
Option Explicit

Sub cmdErgebnisdatei_Click ()

Select Case solver
   Case "XAeqs"
      SuchDemo.Show 1
   Case "LPSOLVE"
      MsgBox "nix LP-Solve"
End Select

End Sub

Sub cmdZurück_Click ()
    
' Zurück/Abbrechen-Button

    Unload netzout   ' verläßt NETZOUT
    grid_in.Show 0   ' springt zurück zum Edit (GRID_IN) Fenster

End Sub

Sub dateneinsetzen ()

' Sisyphus läßt grüßen
' -> die Variablenname (und -typen) wurden durch die
'    teilweise Rekompilierung automatisch erzeugt (otw)

Dim RowAnzahl As Integer
Dim dsnr As Variant
Dim l007C As String
Dim l007E As String
Dim l0080 As String
Dim l0082 As Long
Dim kanal As Integer

Dim l0088 As Variant
Dim l008C As Variant
Dim l0090 As Variant
Dim l0094 As Variant
Dim l0098 As Variant
Dim l009C As Variant
Dim l00A0 As Variant
Dim l00A4 As Variant
Dim l00A8 As Variant
Dim l00AC As Variant
Dim l00B0 As Variant
Dim l00B4 As Variant
Dim l00B8 As Variant
Dim l00BC As Variant
Dim l00C0 As Variant
Dim l00C4 As Variant
Dim l00C8 As Variant
Dim l00CC As Variant
Dim l00D0 As Variant
Dim l00D4 As Variant
Dim l00D8 As Variant
Dim l00DC As Variant
Dim l00E0 As Variant
Dim l00E4 As Variant
Dim l00EA As Variant
Dim l00EE As Variant
Dim l00F2 As Variant
Dim l00F6 As Variant
Dim l00FA As Variant
Dim l00FE As Variant

    If AnzahlKnoten = "" Then
        RowAnzahl = 10
    Else
        RowAnzahl = Val(AnzahlKnoten) + 1
    End If

    For dsnr = 1 To RowAnzahl - 1
        netzout.Liste.SelStartCol = 1
        netzout.Liste.SelStartRow = dsnr
        netzout.Liste.SelEndCol = 5
        netzout.Liste.SelEndRow = dsnr
        netzout.Liste.Clip = Str(U(dsnr).do_i) + Chr$(9) + Str(U(dsnr).do_j) + Chr$(9) + Str(U(dsnr).dnorm) + Chr$(9) + Str(U(dsnr).dmin) + Chr$(9) + Str(U(dsnr).w) + Chr$(9)
    Next dsnr

 ' Auswertung der XA-Datei

 l007C$ = Chr$(13) + Chr$(10)
 l007E$ = Space$(32000)
 l0080$ = Space$(32000)
 l0082& = 1

 kanal = FreeFile
 Open WorkingDir + "\xa.out" For Input As #kanal
 
 Do Until EOF(kanal)
    Line Input #kanal, l0080$
    l0080$ = l0080$ + l007C$
    If l0082& + Len(l0080$) >= 32000 Then
 Exit Do
    Else
        Mid$(l007E$, l0082&, Len(l0080$)) = l0080$
        l0082& = l0082& + Len(l0080$)
    End If
 Loop
 Close #kanal

 l0088 = 0
 For dsnr = 1 To RowAnzahl - 1
   l008C = "Y" & LTrim$(Str(U(dsnr).do_i)) & LTrim$(Str(U(dsnr).do_j))
   l0090 = InStr(1, l007E$, l008C)
   If l0090 <> 0 Then
     l0094 = l0090 + 16
     l0098 = l0094 - l0090
     l009C = Mid(l007E$, l0090, l0098)
     l00A0 = Right(l009C, 9)
     l00A4 = Val(l00A0)
     If l00A4 > 0 Then
       netzout.Liste.Col = 6
       netzout.Liste.Row = dsnr
       netzout.Liste.Text = U(dsnr).dnorm - l00A4
       netzout.Liste.Col = 8
       netzout.Liste.Row = dsnr
       netzout.Liste.Text = l00A4
       netzout.Liste.Col = 9
       netzout.Liste.Row = dsnr
       netzout.Liste.Text = U(dsnr).w * l00A4
       l0088 = U(dsnr).w * l00A4 + l0088
     Else
       netzout.Liste.Col = 6
       netzout.Liste.Row = dsnr
       netzout.Liste.Text = U(dsnr).dnorm
       netzout.Liste.Col = 8
       netzout.Liste.Row = dsnr
       netzout.Liste.Text = l00A4
       netzout.Liste.Col = 9
       netzout.Liste.Row = dsnr
       netzout.Liste.Text = 0
       netzout.Text3.Text = 0
     End If
   Else
     netzout.Liste.Col = 6
     netzout.Liste.Row = dsnr
     netzout.Liste.Text = U(dsnr).dnorm
   End If
l00A8 = "X" & LTrim$(Str(U(dsnr).do_i)) & LTrim$(Str(U(dsnr).do_j))
l00AC = InStr(1, l007E$, l00A8)
   If l00AC <> 0 Then
        l00B0 = l00AC + 16
        l00B4 = l00B0 - l00AC
        l00B8 = Mid(l007E$, l00AC, l00B4)
        l00BC = Right(l00B8, 9)
        l00C0 = Val(l00BC)
        If l00C0 = 1 Then
                 netzout.Liste.Col = 7
                 netzout.Liste.Row = dsnr
                 netzout.Liste.Text = l00C0
                 If l00A4 > 0 Then
                    netzout.Liste.Col = 6
                    netzout.Liste.Row = dsnr
                    netzout.Liste.Text = U(dsnr).dmin
                 Else
                    netzout.Liste.Col = 6
                    netzout.Liste.Row = dsnr
                    netzout.Liste.Text = U(dsnr).dnorm
                 End If
        Else
                 netzout.Liste.Col = 6
                 netzout.Liste.Row = dsnr
                 netzout.Liste.Text = 0
                 netzout.Liste.Col = 7
                 netzout.Liste.Row = dsnr
                 netzout.Liste.Text = 0
        End If
   End If
 Next dsnr

For l00C4 = 1 To RowAnzahl - 1
l00C8 = "U" & LTrim$(l00C4)
l00CC = InStr(1, l007E$, l00C8)
   If l00CC <> 0 Then
     l00D0 = l00CC + 16
     l00D4 = l00D0 - l00CC
     l00D8 = Mid(l007E$, l00CC, l00D4)
     l00DC = Right(l00D8, 9)
     l00E0 = Val(l00DC)
     If l00E0 > 0 Then
       netzout.Text1.Text = l00E0
       l00E4 = "zum Knoten " & l00C4 & "    " & l00E0 & " ZE"
       ListeLaufzeit.AddItem l00E4    ' Laufzeitliste füllen
     End If
   End If
Next l00C4

netzout.KostenTag.Text = KostTag$
netzout.Text3.Text = l0088
l00EA = "SOLUTION (Minimized)"
l00EE = InStr(1, l007E$, l00EA)
If l00EE <> 0 Then
     l00F2 = l00EE + 35
     l00F6 = l00F2 - l00EE
     l00FA = Mid(l007E$, l00EE, l00F6)
     l00FE = Right(l00FA, 14)
     netzout.Text2.Text = l00FE
Else
     netzout.Text2.Text = 0
End If

End Sub

Sub Druck_Button_Click ()

    ' einfache Form-Druck Funktion. WS96/97

Dim Msg                         ' Variablen deklarieren.
    On Error GoTo ErrorHandler  ' Fehlerbehandlung.
    PrintForm                   ' Form drucken.
    Exit Sub
ErrorHandler:
     Msg = "Der Auftrag konnte nicht gedruckt werden."
     MsgBox Msg                  ' Meldung anzeigen.
     Resume Next

End Sub

Function DStr_ein (X As Integer, y As Integer, wert) As Variant

Dim l010C As Variant

Select Case X
        Case 1
            U(y).do_i = Val(wert)
            l010C = 1
        Case 2
            U(y).do_j = Val(wert)
            l010C = 1
        Case 3
            U(y).dnorm = Val(wert)
            l010C = 1
        Case 4
            U(y).dmin = Val(wert)
            l010C = 1
        Case 5
            U(y).w = Val(wert)
            l010C = 1
    End Select
End Function

Sub Form_Load ()

Dim lfdnr As Integer

    Height = 6100
    Width = 9040
    top = (StartFormNetzplan.Height - 695 - Height) / 2
    left = (StartFormNetzplan.Width - 125 - Width) / 2

    Me.BackColor = RGB(225, 225, 225)

    lfdnr = 1
    liste_fuellen

    If status = 1 Then
       dateneinsetzen         ' lokale Proz.
    End If

    netzout.Liste.Col = 0
    netzout.Liste.Row = 1
    netzout.Liste.Col = 1
    netzout.Liste.Row = 0

    posx = 1
    posy = 1

End Sub

Sub Graph_Button_Click ()
    
    GRAPH_Datei_schreiben

End Sub

Sub Liste_Click ()
    posy = netzout.Liste.Row
    posx = netzout.Liste.Col
End Sub

Sub liste_fuellen ()

Dim RowAnzahl As Integer
Dim l0128 As Integer

    If AnzahlKnoten = "" Then
        RowAnzahl = 11
    Else
        RowAnzahl = Val(AnzahlKnoten) + 1
    End If

    screen.MousePointer = 11

    netzout.Liste.Cols = 10
    netzout.Liste.Rows = RowAnzahl
    netzout.Liste.FixedAlignment(0) = 2

    ' Höhe von Zeile 0
    netzout.Liste.RowHeight(0) = 480

    netzout.Liste.Col = 0
    netzout.Liste.ColWidth(0) = 650
    netzout.Liste.Row = 0
    netzout.Liste.Text = "Kante"
    netzout.Liste.Col = 1
    netzout.Liste.ColWidth(1) = 800
    netzout.Liste.Text = "von Knoten"
    netzout.Liste.Col = 2
    netzout.Liste.ColWidth(2) = 800
    netzout.Liste.Text = "nach Knoten"
    netzout.Liste.Col = 3
    netzout.Liste.ColWidth(3) = 800
    netzout.Liste.Text = "Dauer norm."
    netzout.Liste.Col = 4
    netzout.Liste.ColWidth(4) = 800
    netzout.Liste.Text = "Dauer min."
    netzout.Liste.Col = 5
    netzout.Liste.ColWidth(5) = 950
    netzout.Liste.Text = "V-Kosten / ZE"
    netzout.Liste.Col = 6
    netzout.Liste.ColWidth(6) = 830
    netzout.Liste.Text = "Dauer tatsächl."
    netzout.Liste.Col = 7
    netzout.Liste.ColWidth(7) = 820
    netzout.Liste.Text = "Schalt- variable"
    netzout.Liste.Col = 8
    netzout.Liste.ColWidth(8) = 900
    netzout.Liste.Text = "Verkürz.- Dauer"
    netzout.Liste.Col = 9
    netzout.Liste.ColWidth(9) = 900
    netzout.Liste.Text = "Verkürz.- Kosten"

    For l0128 = 1 To RowAnzahl
        netzout.Liste.Col = 0
        netzout.Liste.Row = l0128 - 1
        If Not (l0128 - 1) = 0 Then
            netzout.Liste.Text = (l0128 - 1)
        End If
    Next l0128

    screen.MousePointer = 0

End Sub

