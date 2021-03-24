VERSION 5.00
Begin VB.MDIForm MDIForm1 
   BackColor       =   &H8000000C&
   Caption         =   "Maschinenbelegungsplan"
   ClientHeight    =   3300
   ClientLeft      =   165
   ClientTop       =   390
   ClientWidth     =   3855
   Icon            =   "MDIForm1.frx":0000
   LinkMode        =   1  'Quelle
   LinkTopic       =   "MDIForm1"
   OLEDropMode     =   1  'Manuell
   ScrollBars      =   0   'False
   WhatsThisHelp   =   -1  'True
End
Attribute VB_Name = "MDIForm1"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False

Private Sub MDIForm_Load()
    Height = 8500
    Width = 11500
    Caption = Ueberschrift
    ExecPfad = App.Path
    
    '-- Ini
    DatenPfad = ReadIniString(ExecPfad + "\mbp.ini", "Einstellungen", "Datenpfad")
    LpsolvePfad = ReadIniString(ExecPfad + "\mbp.ini", "Einstellungen", "Lpsolvepfad")
    ArbeitsVerz = ReadIniString(ExecPfad + "\mbp.ini", "Einstellungen", "Arbeitsverz")
    
    eingabe.Show
End Sub
