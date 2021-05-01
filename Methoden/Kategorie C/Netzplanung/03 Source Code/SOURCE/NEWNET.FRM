VERSION 2.00
Begin Form NEWNET 
   BackColor       =   &H00FFFFFF&
   Caption         =   "Neues Netz anlegen"
   ClientHeight    =   2655
   ClientLeft      =   1845
   ClientTop       =   1980
   ClientWidth     =   5850
   Height          =   3060
   Left            =   1785
   LinkTopic       =   "Form1"
   MDIChild        =   -1  'True
   ScaleHeight     =   2655
   ScaleWidth      =   5850
   Top             =   1635
   Width           =   5970
   Begin TextBox MaskeDecision 
      Height          =   285
      Left            =   3840
      TabIndex        =   5
      Top             =   1080
      Width           =   800
   End
   Begin TextBox MaskeDoing 
      Height          =   285
      Left            =   3840
      TabIndex        =   4
      Top             =   360
      Width           =   800
   End
   Begin CommandButton Befehl2 
      Caption         =   "&Abbrechen"
      Height          =   375
      Left            =   3600
      TabIndex        =   3
      Top             =   1920
      Width           =   1095
   End
   Begin CommandButton Befehl1 
      Caption         =   "&OK"
      Height          =   375
      Left            =   1200
      TabIndex        =   2
      Top             =   1920
      Width           =   1095
   End
   Begin Label Bezeichnung4 
      BackColor       =   &H00FFFFFF&
      BackStyle       =   0  'Transparent
      Caption         =   "Projektes pro Tag ?"
      Height          =   255
      Left            =   240
      TabIndex        =   7
      Top             =   1200
      Width           =   3135
   End
   Begin Label Bezeichnung3 
      BackColor       =   &H00FFFFFF&
      BackStyle       =   0  'Transparent
      Caption         =   "(max. 150)"
      Height          =   255
      Left            =   4800
      TabIndex        =   6
      Top             =   360
      Width           =   975
   End
   Begin Label Bezeichnung2 
      BackColor       =   &H00FFFFFF&
      BackStyle       =   0  'Transparent
      Caption         =   "Wie hoch sind die allg. Kosten des"
      Height          =   255
      Left            =   240
      TabIndex        =   1
      Top             =   960
      Width           =   3375
   End
   Begin Label Bezeichnung1 
      BackColor       =   &H00FFFFFF&
      BackStyle       =   0  'Transparent
      Caption         =   "Wieviele Tätigkeiten enthält das Netz ?"
      Height          =   255
      Left            =   240
      TabIndex        =   0
      Top             =   360
      Width           =   3495
   End
End
' NEWNET.FRM
Option Explicit

Sub Befehl1_Click ()

Dim l002A As String
Dim l002C As String
Dim l002E As String

    AnzahlKnoten = MaskeDoing
    KostTag$ = MaskeDecision
    l002A$ = MaskeDoing
    l002C$ = MaskeDecision
    
    If (Val(l002A$) >= 150) Then
        l002E$ = "Bitte beachten Sie die max. Angaben der Felder!!"
        MsgBox l002E$, 48
    Else
        Unload NEWNET
        GRID_IN.Show 0
    End If
End Sub

Sub Befehl2_Click ()
    Unload NEWNET
End Sub

Sub Form_Load ()

    Height = 2900
    Width = 6000
    top = (StartFormNetzplan.Height - 695 - Height) / 2
    left = (StartFormNetzplan.Width - 125 - Width) / 2

    Me.BackColor = RGB(225, 255, 225)

    If datopenflag = 1 Then
        MaskeDoing.Text = AnzahlKnoten
        MaskeDecision.Text = KostTag$
        SendKeys "{tab 2}"
        SendKeys "+{END}"
    Else
        SendKeys "{tab 2}"
    End If
End Sub

Sub MaskeDecision_KeyPress (p1 As Integer)
    If p1 = 13 Then
        SendKeys " {tab}"
    End If
End Sub

Sub MaskeDoing_KeyPress (p1 As Integer)
    If p1 = 13 Then
        SendKeys " {tab}"
    End If
End Sub

