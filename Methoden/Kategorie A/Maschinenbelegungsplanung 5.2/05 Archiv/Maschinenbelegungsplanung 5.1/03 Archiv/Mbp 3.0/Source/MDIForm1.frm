VERSION 5.00
Begin VB.MDIForm MDIForm1 
   BackColor       =   &H8000000C&
   Caption         =   "Maschinenbelegungsplan"
   ClientHeight    =   1590
   ClientLeft      =   165
   ClientTop       =   450
   ClientWidth     =   1875
   Icon            =   "MDIForm1.frx":0000
   LinkMode        =   1  'Quelle
   LinkTopic       =   "MDIForm1"
   OLEDropMode     =   1  'Manuell
   StartUpPosition =   2  'Bildschirmmitte
   WhatsThisHelp   =   -1  'True
End
Attribute VB_Name = "MDIForm1"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False

Private Sub MDIForm_Load()
           
    Height = 8000
    Width = 11000
    
   
    Caption = Ueberschrift
    ExecPfad = App.Path

    '-- Ini
    DatenPfad = ReadIniString(ExecPfad + "\mbp.ini", "Einstellungen", "Datenpfad")
    LpsolvePfad = ReadIniString(ExecPfad + "\mbp.ini", "Einstellungen", "Lpsolvepfad")
    ArbeitsVerz = ReadIniString(ExecPfad + "\mbp.ini", "Einstellungen", "Arbeitsverz")
    
    'MsgBox (App.EXEName + App.LegalCopyright + ExecPfad)
    'MsgBox DatenPfad + Chr(13) + LpsolvePfad + Chr(13) + ArbeitsVerz

           
    eingabe.Show
    
End Sub
