VERSION 2.00
Begin Form Infofenster 
   BackColor       =   &H00FFFFFF&
   BorderStyle     =   3  'Nicht änderbar, doppelt
   Caption         =   "Info über..."
   ClientHeight    =   4290
   ClientLeft      =   1785
   ClientTop       =   1725
   ClientWidth     =   5250
   FillColor       =   &H00FFFFFF&
   FillStyle       =   0  'Gefüllt
   ForeColor       =   &H00004080&
   Height          =   4695
   Left            =   1725
   LinkTopic       =   "Form1"
   MDIChild        =   -1  'True
   ScaleHeight     =   4290
   ScaleWidth      =   5250
   Top             =   1380
   Width           =   5370
   Begin Frame Rahmen1 
      BackColor       =   &H00FFFFFF&
      Caption         =   "Autoren"
      Height          =   2415
      Left            =   2040
      TabIndex        =   4
      Top             =   840
      Width           =   2775
      Begin ListBox Liste1 
         BackColor       =   &H00FFFFFF&
         FontBold        =   -1  'True
         FontItalic      =   0   'False
         FontName        =   "Times New Roman"
         FontSize        =   9.75
         FontStrikethru  =   0   'False
         FontUnderline   =   0   'False
         Height          =   1830
         Left            =   240
         TabIndex        =   2
         Top             =   360
         Width           =   2295
      End
   End
   Begin CommandButton cmdOK 
      Caption         =   "OK"
      Height          =   340
      Left            =   3840
      TabIndex        =   3
      Top             =   3480
      Width           =   975
   End
   Begin Label lblCopyRight 
      BackColor       =   &H00E0FFFF&
      BackStyle       =   0  'Transparent
      Caption         =   "© 1996 - 2000 Fachhochschule Konstanz"
      FontBold        =   0   'False
      FontItalic      =   0   'False
      FontName        =   "MS Sans Serif"
      FontSize        =   8.25
      FontStrikethru  =   0   'False
      FontUnderline   =   0   'False
      Height          =   255
      Left            =   2040
      TabIndex        =   1
      Top             =   4005
      Width           =   3135
   End
   Begin Image Anzeige1 
      Height          =   1335
      Left            =   240
      Picture         =   MI_INFO.FRX:0000
      Stretch         =   -1  'True
      Top             =   360
      Width           =   1335
   End
   Begin Label Bezeichnung1 
      BackColor       =   &H00E0FFFF&
      BackStyle       =   0  'Transparent
      Caption         =   "Die Mischung"
      FontBold        =   -1  'True
      FontItalic      =   0   'False
      FontName        =   "MS Sans Serif"
      FontSize        =   18
      FontStrikethru  =   0   'False
      FontUnderline   =   0   'False
      ForeColor       =   &H00C00000&
      Height          =   495
      Left            =   2040
      TabIndex        =   0
      Top             =   240
      Width           =   2535
   End
End
Sub Anzeige1_DblClick ()

    MsgBox "..demnächst in Ihrer Mensa", 64, "Mischungsplanung"

End Sub

Sub cmdOK_Click ()
    Unload Infofenster
End Sub

Sub Form_Load ()

Height = 4700
Width = 5340
top = (MDIForm1.Height - 695 - Height) / 2
left = (MDIForm1.Width - 125 - Width) / 2
                                  
'BackColor = RGB(230, 255, 235)
BackColor = RGB(230, 250, 240)
rahmen1.BackColor = RGB(230, 250, 240)


' die Liste kann beliebig erweitert werden
liste1.AddItem "---------------  WS95/96"
liste1.AddItem "otx"
liste1.AddItem ""
liste1.AddItem ""
liste1.AddItem ""
liste1.AddItem ""
liste1.AddItem ""
liste1.AddItem ""
liste1.AddItem ""
liste1.AddItem ""

End Sub

