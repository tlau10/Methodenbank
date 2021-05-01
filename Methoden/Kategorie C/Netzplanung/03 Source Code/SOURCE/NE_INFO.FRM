VERSION 2.00
Begin Form Infofenster 
   BackColor       =   &H00FFC0C0&
   Caption         =   "Info über..."
   ClientHeight    =   4590
   ClientLeft      =   1785
   ClientTop       =   1725
   ClientWidth     =   4395
   FillColor       =   &H00FFFFFF&
   FillStyle       =   0  'Gefüllt
   Height          =   4995
   Left            =   1725
   LinkTopic       =   "Form1"
   MDIChild        =   -1  'True
   ScaleHeight     =   4590
   ScaleWidth      =   4395
   Top             =   1380
   Width           =   4515
   Begin Frame Rahmen1 
      BackColor       =   &H00FFC0C0&
      Caption         =   "Die Autoren"
      Height          =   2535
      Left            =   1080
      TabIndex        =   4
      Top             =   960
      Width           =   2895
      Begin ListBox Liste1 
         BackColor       =   &H00FFFFFF&
         Height          =   1980
         Left            =   240
         TabIndex        =   2
         Top             =   360
         Width           =   2415
      End
   End
   Begin CommandButton cmdOK 
      Caption         =   "OK"
      Height          =   375
      Left            =   3000
      TabIndex        =   3
      Top             =   3720
      Width           =   975
   End
   Begin Image Anzeige1 
      Height          =   480
      Left            =   240
      Picture         =   NE_INFO.FRX:0000
      Top             =   1080
      Width           =   480
   End
   Begin Label Bezeichnung2 
      BackColor       =   &H00FFFFFF&
      BackStyle       =   0  'Transparent
      Caption         =   "Version 1.4"
      FontBold        =   -1  'True
      FontItalic      =   -1  'True
      FontName        =   "MS Sans Serif"
      FontSize        =   9.75
      FontStrikethru  =   0   'False
      FontUnderline   =   0   'False
      Height          =   255
      Left            =   1320
      TabIndex        =   5
      Top             =   600
      Width           =   1335
   End
   Begin Label lblCopyRight 
      BackColor       =   &H00FFFFFF&
      BackStyle       =   0  'Transparent
      Caption         =   "© 1994 - 1997  Fachhochschule Konstanz"
      FontBold        =   0   'False
      FontItalic      =   0   'False
      FontName        =   "MS Sans Serif"
      FontSize        =   8.25
      FontStrikethru  =   0   'False
      FontUnderline   =   0   'False
      Height          =   255
      Left            =   600
      TabIndex        =   1
      Top             =   4200
      Width           =   3135
   End
   Begin Label Bezeichnung1 
      BackColor       =   &H00FFFFFF&
      BackStyle       =   0  'Transparent
      Caption         =   "Netzplantecknik"
      FontBold        =   -1  'True
      FontItalic      =   0   'False
      FontName        =   "MS Sans Serif"
      FontSize        =   13.5
      FontStrikethru  =   0   'False
      FontUnderline   =   0   'False
      Height          =   375
      Left            =   960
      TabIndex        =   0
      Top             =   120
      Width           =   2415
   End
End
Sub cmdOK_Click ()
    Unload infofenster
End Sub

Sub Form_Load ()

Height = 4920
Width = 4490
top = (StartFormNetzplan.Height - 695 - Height) / 2
left = (StartFormNetzplan.Width - 125 - Width) / 2
                                  
                                  
' die Liste kann beliebig erweitert werden
liste1.AddItem "-----------------  SS94"
liste1.AddItem "Ulrich Kloos"
liste1.AddItem "Clemens Lehmann"
liste1.AddItem "Martin Matuszczyk"
liste1.AddItem "Andreas Schmidt"
liste1.AddItem ""
liste1.AddItem "-----------------  WS95/96"
liste1.AddItem "XV 43"
liste1.AddItem ""
liste1.AddItem "-----------------  SS96"
liste1.AddItem "->  E-Graph Software"
liste1.AddItem "Ralph Becker"
liste1.AddItem "Christoph Bujack"
liste1.AddItem "Ulrich Strobel-Vogt"
liste1.AddItem ""
liste1.AddItem "-----------------  WS96/97"
liste1.AddItem "Sladjana Groll"
liste1.AddItem "Oleg Kryschanowski"
liste1.AddItem ""
liste1.AddItem ""
liste1.AddItem ""

' Bildlauf im Listenfeld durchfuehren
liste1.TopIndex = 9

End Sub

