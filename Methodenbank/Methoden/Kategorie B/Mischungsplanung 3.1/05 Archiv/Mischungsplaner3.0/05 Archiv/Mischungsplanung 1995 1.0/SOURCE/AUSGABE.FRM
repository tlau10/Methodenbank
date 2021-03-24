VERSION 2.00
Begin Form frmAusgabe 
   Caption         =   "Ergebnis"
   ClientHeight    =   5460
   ClientLeft      =   1590
   ClientTop       =   1680
   ClientWidth     =   9405
   Height          =   5865
   Left            =   1530
   LinkTopic       =   "Form1"
   MDIChild        =   -1  'True
   ScaleHeight     =   5460
   ScaleWidth      =   9405
   Top             =   1335
   Width           =   9525
   Begin Grid Gatter1 
      FixedCols       =   0
      Height          =   3255
      Left            =   120
      ScrollBars      =   2  'Vertikal
      TabIndex        =   9
      Top             =   2040
      Width           =   4455
   End
   Begin TextBox SolverName 
      Height          =   285
      Left            =   5280
      TabIndex        =   8
      Text            =   "Text1"
      Top             =   1080
      Width           =   2175
   End
   Begin CommandButton CmdSchliess 
      Caption         =   "Schließen"
      Height          =   375
      Left            =   7800
      TabIndex        =   6
      Top             =   240
      Width           =   1215
   End
   Begin Grid Gatter2 
      FixedCols       =   0
      Height          =   3255
      Left            =   4800
      ScrollBars      =   2  'Vertikal
      TabIndex        =   3
      Top             =   2040
      Width           =   4455
   End
   Begin TextBox ZFWert 
      Height          =   285
      Left            =   1440
      TabIndex        =   0
      Text            =   " "
      Top             =   1080
      Width           =   1935
   End
   Begin Label Bezeichnung6 
      BackStyle       =   0  'Transparent
      Caption         =   "Verwendung der Ausgangsstoffe"
      Height          =   255
      Left            =   4920
      TabIndex        =   10
      Top             =   1680
      Width           =   3495
   End
   Begin Label Bezeichnung5 
      BackStyle       =   0  'Transparent
      Caption         =   "Solver:"
      Height          =   255
      Left            =   4440
      TabIndex        =   7
      Top             =   1080
      Width           =   735
   End
   Begin Label Bezeichnung4 
      BackStyle       =   0  'Transparent
      Caption         =   "Erzeugte Zielmengen"
      Height          =   255
      Left            =   240
      TabIndex        =   5
      Top             =   1680
      Width           =   2655
   End
   Begin Label Bezeichnung3 
      BackStyle       =   0  'Transparent
      Caption         =   "ZF-Wert:"
      Height          =   255
      Left            =   480
      TabIndex        =   4
      Top             =   1080
      Width           =   975
   End
   Begin Label Bezeichnung2 
      BackStyle       =   0  'Transparent
      Caption         =   "Minimum-Modellen erhält man daher einen negativen ZF-Wert."
      FontBold        =   0   'False
      FontItalic      =   0   'False
      FontName        =   "MS Sans Serif"
      FontSize        =   8.25
      FontStrikethru  =   0   'False
      FontUnderline   =   0   'False
      Height          =   255
      Left            =   1800
      TabIndex        =   2
      Top             =   525
      Width           =   5295
   End
   Begin Label Bezeichnung1 
      BackStyle       =   0  'Transparent
      Caption         =   "Allen Modellen liegt die Maximum-Berechnung zugrunde. Bei"
      FontBold        =   0   'False
      FontItalic      =   0   'False
      FontName        =   "MS Sans Serif"
      FontSize        =   8.25
      FontStrikethru  =   0   'False
      FontUnderline   =   0   'False
      Height          =   255
      Left            =   1800
      TabIndex        =   1
      Top             =   240
      Width           =   5295
   End
End
Option Explicit

Sub CmdSchliess_Click ()
    
    Unload frmAusgabe

End Sub

Sub Form_Load ()

    Dim i, j, k, l, k_pos, pos, altpos As Integer
    Dim E, sum_A, sum_Z As Single
    Dim dummy As String * 10000
    Dim suchStr, E_Str, E1_Str, E2_Str As String
    ReDim feld(10, 10) As Single

    On Error Resume Next    ' Zum Testen ausschalten !!!

    Unload Infofenster

    Height = 5860
    Width = 9500
    top = (MDIForm1.Height - 695 - Height) / 2
    left = (MDIForm1.Width - 125 - Width) / 2
    backcolor = RGB(250, 240, 230)


    Select Case solver
       Case "XAeqs"
          Open WorkingDir + "\xa.out" For Binary As #1
          SolverName.Text = " XA Equation Style"
       Case "XAmps"
          Open WorkingDir + "\xa.out" For Binary As #1
          SolverName.Text = " XA MPS-Format"
       Case "STRADA"
          Open WorkingDir + "\strada.out" For Binary As #1
          SolverName.Text = " Strada MPS-Format"
    End Select

    Get #1, , dummy
    Close #1


    altpos = 1   ' Vorbelegung, falls pos Null bleibt

    ' ZF-Wert suchen
    pos = InStr(dummy, "SOLUTION (")
    If pos <> 0 Then
       ZFWert.Text = " " + StrTran(Mid(dummy, pos + 22, 12), ".", ",")
       altpos = pos + 20
    End If
         
    ' Warnung suchen u. ggf. Ausgabe abbrechen
    pos = InStr(dummy, "N O ")
    If pos <> 0 Then
       ZFWert.Text = Mid(dummy, pos, 41)
       Exit Sub     ' >>>>> Sprung aus der Proz.
    End If


    ' Gatter einrichten u. vorbelegen -------------------------
    Initgatter

    sum_A = 0: sum_Z = 0


    '-- Werte suchen und ins Array (Feld) eintragen
                
    For i = 1 To aZeilen
        For j = 1 To zZeilen

            suchStr = "X" + Trim(Str(i)) + Trim(Str(j))
            
            pos = InStr(altpos, dummy, suchStr)
            If pos <> 0 Then
               E_Str = Mid(dummy, pos, 20)
               l = Len(suchStr)
               E_Str = Mid(E_Str, l + 1, 20 - l)
                 
               ' Tausender-Komma eliminieren
               k_pos = InStr(1, E_Str, ",")
               If k_pos <> 0 Then
                  E1_Str = Mid$(E_Str, 1, k_pos - 1)
                  E2_Str = Mid$(E_Str, k_pos + 1, Len(E_Str) - k_pos)
                  E_Str = E1_Str & E2_Str
               End If

               ' altpos = pos + 20
               ' Die Reihenfolge x11, x12 usw. wird nicht eingehalten,
               ' deshalb kein Weiterzählen

            Else
               E_Str = "0"
            End If

            '-- Sonder- u. Fremdzeichen entfernen -> nur beim Solver Strada
            E_Str = StrTran(E_Str, "X", " ")
            E_Str = StrTran(E_Str, Chr$(10), " ")
            E_Str = StrTran(E_Str, Chr$(9), " ")
            
            'E_Str = Trim$(E_Str)  ' Blanks entfernen

            '-- Ins deutsche Zahlenformat konvertieren
            E_Str = StrTran(E_Str, ".", ",")


            E = CSng(E_Str)

    'MsgBox E_Str & Chr(13) & E

            If Err Then
              E = -1
              ZFWert.Text = "Error"
              'MsgBox E_Str & "->" & Asc(Right$(E_Str, 1))
            End If

            feld(i, j) = E

        Next j
    Next i



'******************************************

    '-- Gitter Nr. 1 füllen
                
    k = 0
    For i = 1 To zZeilen
        For j = 1 To aZeilen

            k = k + 1  ' Zeilenzähler
            Gatter1.Row = k

            suchStr = "X" + Trim(Str(j)) + Trim(Str(i))

            'Erste Spalte
            Gatter1.Col = 0
            Gatter1.Text = "  " & suchStr

            'Zweite Spalte
            Gatter1.Col = 1
            Gatter1.Text = " " & Z(i).ZName & " <- " & A(j).AName

            'Dritte Spalte
            Gatter1.Col = 2
            
            E = feld(j, i)
            Gatter1.Text = Format$(E, " 0.0000")
            sum_Z = sum_Z + E

        Next j

        '-- Summe der Zielmenge ausgeben
        
        k = k + 1  ' Zeilenzähler
        Gatter1.Row = k

        'Erste Spalte
        Gatter1.Col = 0
        Gatter1.Text = " "

        'Zweite Spalte
        Gatter1.Col = 1
        Gatter1.Text = " Summe " + Z(i).ZName

        'Dritte Spalte
        Gatter1.Col = 2
        Gatter1.Text = Format$(sum_Z, " 0.0000")
        sum_Z = 0

    Next i




    '-- Gitter Nr. 2 füllen
                
    k = 0
    For i = 1 To aZeilen
        For j = 1 To zZeilen

            k = k + 1  ' Zeilenzähler
            Gatter2.Row = k

            suchStr = "X" + Trim(Str(i)) + Trim(Str(j))

            'Erste Spalte
            Gatter2.Col = 0
            Gatter2.Text = "  " & suchStr

            'Zweite Spalte
            Gatter2.Col = 1
            'gatter2.Text = " " & Z(j).ZName & " - " & A(i).AName
            Gatter2.Text = " " & A(i).AName & " -> " & Z(j).ZName
            
            'Dritte Spalte
            Gatter2.Col = 2
            
            E = feld(i, j)
            Gatter2.Text = Format$(E, " 0.0000")
            sum_A = sum_A + E

        Next j

        '-- Summe des verwendeten Ausgangsstoffs ausgeben
        
        k = k + 1  ' Zeilenzähler
        'MsgBox k
        Gatter2.Row = k

        'Erste Spalte
        Gatter2.Col = 0
        Gatter2.Text = " "

        'Zweite Spalte
        Gatter2.Col = 1
        Gatter2.Text = " Summe " + A(i).AName

        'Dritte Spalte
        Gatter2.Col = 2
        Gatter2.Text = Format$(sum_A, " 0.0000")
        sum_A = 0

    Next i


ende:
    Exit Sub

End Sub

Sub Initgatter ()

    '--
    Gatter1.Rows = aZeilen * zZeilen + zZeilen + 1
    Gatter1.Cols = 3

    Gatter1.ColWidth(0) = 640
    Gatter1.ColWidth(1) = 2100
    Gatter1.ColWidth(2) = 2000

    ' Spaltenüberschriften
    Gatter1.Row = 0

    Gatter1.Col = 0
    Gatter1.Text = " Var."
    Gatter1.Col = 1
    Gatter1.Text = " Namen"
    Gatter1.Col = 2
    Gatter1.Text = " Werte"


    '--
    Gatter2.Rows = aZeilen * zZeilen + aZeilen + 1
    Gatter2.Cols = 3

    Gatter2.ColWidth(0) = 640
    Gatter2.ColWidth(1) = 2100
    Gatter2.ColWidth(2) = 2000

    ' Spaltenüberschriften
    Gatter2.Row = 0

    Gatter2.Col = 0
    Gatter2.Text = " Var."
    Gatter2.Col = 1
    Gatter2.Text = " Namen"
    Gatter2.Col = 2
    Gatter2.Text = " Werte"
                     
End Sub

Function StrTran (AusdrZ1, AusdrZ2, AusdrZ3)

'->  in AusdrZ1 AusdrZ2 durch AusdrZ3 ersetzen

Dim pos, l  As Integer
Dim TmpStr As String
Dim VorStr, NachStr As String

While InStr(1, AusdrZ1, AusdrZ2) > 0
   pos = InStr(1, AusdrZ1, AusdrZ2)

   VorStr = Left$(AusdrZ1, pos - 1)
   If pos + Len(AusdrZ2) < Len(AusdrZ1) Then
     NachStr = Mid$(AusdrZ1, pos + Len(AusdrZ2))
   Else
     NachStr = ""
   End If

   AusdrZ1 = VorStr + AusdrZ3 + NachStr

   'delete(AusdrZ1,Pos1,length(AusdrZ2))   >>> aus Pascal
   'insert(AusdrZ3,AusdrZ1,Pos1)
   
   ''If pos > 0 Then
   '  TmpStr = Left$(AusdrZ1, pos - 1) + AusdrZ3
   '  l = Len(AusdrZ1) - pos - Len(AusdrZ2) + 1
   '  If l > 1 Then
   '    AusdrZ1 = TmpStr + Right$(AusdrZ1, l)
   '  Else
   '    'MsgBox l & Chr(13) & AusdrZ1 + Chr(13) + AusdrZ2
   '    AusdrZ1 = TmpStr
   '  End If
   ''End If
Wend

If InStr(1, AusdrZ1, AusdrZ2) Then MsgBox "Fehler -> StrTran(...)" + AusdrZ1
'MsgBox "strtran: " + AusdrZ1 + Chr(13) + AusdrZ2 + Chr(13) + AusdrZ3

StrTran = AusdrZ1

End Function

