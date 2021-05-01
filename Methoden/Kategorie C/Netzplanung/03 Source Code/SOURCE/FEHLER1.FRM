VERSION 2.00
Begin Form FEHLER1 
   Caption         =   "Fehlermeldung"
   ClientHeight    =   2475
   ClientLeft      =   2610
   ClientTop       =   2040
   ClientWidth     =   3105
   Height          =   2880
   Left            =   2550
   LinkTopic       =   "Form1"
   MaxButton       =   0   'False
   MinButton       =   0   'False
   ScaleHeight     =   2475
   ScaleWidth      =   3105
   Top             =   1695
   Width           =   3225
   Begin PictureBox Bild1 
      Height          =   495
      Left            =   1200
      Picture         =   FEHLER1.FRX:0000
      ScaleHeight     =   465
      ScaleWidth      =   585
      TabIndex        =   2
      Top             =   1080
      Width           =   615
   End
   Begin CommandButton Befehl1 
      Caption         =   "&Ok"
      Height          =   495
      Left            =   960
      TabIndex        =   1
      Top             =   1680
      Width           =   1215
   End
   Begin Label Fehlermeldung 
      Alignment       =   2  'Mitte
      AutoSize        =   -1  'True
      Caption         =   " "
      Height          =   195
      Left            =   1515
      TabIndex        =   0
      Top             =   360
      Width           =   105
   End
End
' FEHLER1.FRM
Option Explicit

Sub Befehl1_Click ()
    Unload FEHLER1
End Sub

Sub Befehl2_Click ()
Dim l001E As Variant
    l001E = 1
End Sub

