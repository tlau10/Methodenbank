VERSION 5.00
Begin VB.MDIForm MDIForm1 
   BackColor       =   &H8000000C&
   Caption         =   "Maschinenbelegungsplan"
   ClientHeight    =   4755
   ClientLeft      =   165
   ClientTop       =   450
   ClientWidth     =   5985
   Icon            =   "MDIForm1.frx":0000
   LinkMode        =   1  'Quelle
   LinkTopic       =   "MDIForm1"
   OLEDropMode     =   1  'Manuell
   StartUpPosition =   3  'Windows-Standard
   WhatsThisHelp   =   -1  'True
   WindowState     =   2  'Maximiert
End
Attribute VB_Name = "MDIForm1"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False

Private Sub MDIForm_Load()
    eingabe.Show
End Sub
