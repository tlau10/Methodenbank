VERSION 2.00
Begin Form SuchDemo 
   Caption         =   "Suchen inach Ergebnissen"
   ClientHeight    =   3480
   ClientLeft      =   195
   ClientTop       =   1875
   ClientWidth     =   9150
   Height          =   3885
   Left            =   135
   LinkMode        =   1  'Quelle
   LinkTopic       =   "Form1"
   ScaleHeight     =   3480
   ScaleWidth      =   9150
   Top             =   1530
   Width           =   9270
   Begin CommandButton cmdDrucken 
      Caption         =   "&Drucken"
      Height          =   375
      Left            =   5640
      TabIndex        =   5
      Top             =   3000
      Width           =   1335
   End
   Begin CommandButton Ende 
      Cancel          =   -1  'True
      Caption         =   "Sch&lieﬂen"
      Height          =   375
      Left            =   7440
      TabIndex        =   4
      Top             =   3000
      Width           =   1215
   End
   Begin CommandButton Suchen 
      Caption         =   "&Suchen"
      Default         =   -1  'True
      Height          =   375
      Left            =   3900
      TabIndex        =   3
      Top             =   3000
      Width           =   1335
   End
   Begin TextBox SuchText 
      Height          =   285
      Left            =   1020
      TabIndex        =   2
      Top             =   3060
      Width           =   2715
   End
   Begin TextBox Text1 
      FontBold        =   -1  'True
      FontItalic      =   0   'False
      FontName        =   "Courier New"
      FontSize        =   8.25
      FontStrikethru  =   0   'False
      FontUnderline   =   0   'False
      Height          =   2655
      HideSelection   =   0   'False
      Left            =   120
      MultiLine       =   -1  'True
      ScrollBars      =   2  'Vertikal
      TabIndex        =   0
      Text            =   "Text1"
      Top             =   240
      Width           =   8900
   End
   Begin Label Label2 
      Caption         =   "Such&text:"
      Height          =   195
      Left            =   120
      TabIndex        =   1
      Top             =   3120
      Width           =   855
   End
End
' SUCHDEMO.FRM
Option Explicit

Sub cmdDrucken_Click ()

' Druck: alles oder die selektierten Teile

Dim l0020 As String
Dim l0022 As Integer
 
 If Text1.SelLength Then l0020$ = "Selektierten "
 l0022% = MsgBox(l0020$ + "Text ausdrucken?", 36, "Drucken")
 If l0022% <> 6 Then Exit Sub
 If Text1.SelLength Then
  Printer.Print Text1.SelText
 Else
  Printer.Print Text1.Text
 End If
 
 Printer.EndDoc

End Sub

Sub Ende_Click ()
    Unload SuchDemo
End Sub

Sub Form_Load ()

Dim NL, T, A As String
Dim Ndx As Long
Dim kanal As Integer
 
 NL = Chr$(13) + Chr$(10)
 T = Space$(32000)
 A = Space$(32000)
 Ndx = 1
 
 kanal = FreeFile
 Open WorkingDir + "\xa.out" For Input As #kanal
 
 Do Until EOF(kanal)
    Line Input #kanal, A
     A = A + NL
     If Ndx + Len(A) >= 32000 Then
       Exit Do
    Else
        Mid$(T, Ndx, Len(A)) = A
        Ndx = Ndx + Len(A)
    End If
 Loop
 
 Close #kanal
  
 Text1.Text = T

End Sub

Sub Suchen_Click ()

Dim l003E As Integer
 
 l003E% = InStr(Text1.SelStart + 2, Text1.Text, SuchText.Text)
 If l003E% Then
   Text1.SelStart = l003E% - 1
   Text1.SelLength = Len(SuchText.Text)
 Else
   Beep
 End If
 Text1.SetFocus

End Sub

Sub SuchText_GotFocus ()
Dim l003E As Integer
 SuchText.SelStart = 0
 SuchText.SelLength = 999
End Sub

