VERSION 2.00
Begin Form Wartefenster 
   BorderStyle     =   3  'Nicht änderbar, doppelt
   Caption         =   "Berechnungslauf"
   ClientHeight    =   1815
   ClientLeft      =   2385
   ClientTop       =   2430
   ClientWidth     =   4815
   Height          =   2220
   Left            =   2325
   LinkTopic       =   "Form1"
   MDIChild        =   -1  'True
   ScaleHeight     =   1815
   ScaleWidth      =   4815
   Top             =   2085
   Width           =   4935
   Begin TextBox Solver_Text 
      FontBold        =   -1  'True
      FontItalic      =   -1  'True
      FontName        =   "MS Sans Serif"
      FontSize        =   9.75
      FontStrikethru  =   0   'False
      FontUnderline   =   0   'False
      Height          =   375
      Left            =   1800
      TabIndex        =   2
      Text            =   "Text1"
      Top             =   840
      Width           =   2535
   End
   Begin Label Bezeichnung3 
      Caption         =   "bitte warten .."
      FontBold        =   0   'False
      FontItalic      =   0   'False
      FontName        =   "MS Sans Serif"
      FontSize        =   8.25
      FontStrikethru  =   0   'False
      FontUnderline   =   0   'False
      Height          =   255
      Left            =   1800
      TabIndex        =   3
      Top             =   1440
      Width           =   1575
   End
   Begin Label Bezeichnung2 
      Caption         =   "Solver:"
      FontBold        =   -1  'True
      FontItalic      =   0   'False
      FontName        =   "MS Sans Serif"
      FontSize        =   9.75
      FontStrikethru  =   0   'False
      FontUnderline   =   0   'False
      Height          =   255
      Left            =   600
      TabIndex        =   1
      Top             =   840
      Width           =   975
   End
   Begin Label Bezeichnung1 
      Caption         =   "....die Berechnung läuft !"
      FontBold        =   -1  'True
      FontItalic      =   0   'False
      FontName        =   "MS Sans Serif"
      FontSize        =   9.75
      FontStrikethru  =   0   'False
      FontUnderline   =   0   'False
      ForeColor       =   &H000040C0&
      Height          =   255
      Left            =   480
      TabIndex        =   0
      Top             =   240
      Width           =   4095
   End
End
Sub Form_Load ()

    Select Case solver

       Case "XAeqs"
            Solver_Text.Text = "XA Equation Style"

       Case "XAmps"
            Solver_Text.Text = "XA MPS-Format"

       Case "MOPS"
            Solver_Text.Text = "MOPS MPS-Format"

       Case "OMP"
            Solver_Text.Text = "OMP"

       Case "STRADA"
            Solver_Text.Text = "Strada MPS-Format"
       
       Case "NN"
            Solver_Text.Text = "N.N. X-Format"

       Case Else
            Solver_Text.Text = solver

    End Select

End Sub

