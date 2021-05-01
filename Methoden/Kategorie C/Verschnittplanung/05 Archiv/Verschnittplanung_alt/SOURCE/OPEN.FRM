VERSION 5.00
Object = "{F9043C88-F6F2-101A-A3C9-08002B2F49FB}#1.2#0"; "Comdlg32.ocx"
Begin VB.Form Öffnen 
   Appearance      =   0  '2D
   BackColor       =   &H8000000A&
   Caption         =   "Datei öffnen"
   ClientHeight    =   1245
   ClientLeft      =   1560
   ClientTop       =   1515
   ClientWidth     =   5220
   BeginProperty Font 
      Name            =   "MS Sans Serif"
      Size            =   8.25
      Charset         =   0
      Weight          =   700
      Underline       =   0   'False
      Italic          =   0   'False
      Strikethrough   =   0   'False
   EndProperty
   ForeColor       =   &H80000008&
   HelpContextID   =   7
   LinkTopic       =   "Form1"
   PaletteMode     =   1  'ZReihenfolge
   ScaleHeight     =   1245
   ScaleWidth      =   5220
   Begin MSComDlg.CommonDialog StdDialog1 
      Left            =   4560
      Top             =   720
      _ExtentX        =   847
      _ExtentY        =   847
      _Version        =   393216
      FontSize        =   0
      MaxFileSize     =   256
   End
End
Attribute VB_Name = "Öffnen"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
