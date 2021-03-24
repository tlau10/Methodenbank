VERSION 5.00
Object = "{F9043C88-F6F2-101A-A3C9-08002B2F49FB}#1.2#0"; "Comdlg32.ocx"
Begin VB.Form Hilfefenster 
   Appearance      =   0  '2D
   BackColor       =   &H8000000A&
   Caption         =   "Form1"
   ClientHeight    =   2160
   ClientLeft      =   1095
   ClientTop       =   1485
   ClientWidth     =   2685
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
   LinkTopic       =   "Form1"
   PaletteMode     =   1  'ZReihenfolge
   ScaleHeight     =   2160
   ScaleWidth      =   2685
   Begin MSComDlg.CommonDialog Hilfedialog 
      Left            =   1080
      Top             =   720
      _ExtentX        =   847
      _ExtentY        =   847
      _Version        =   393216
      FontSize        =   0
      MaxFileSize     =   256
   End
End
Attribute VB_Name = "Hilfefenster"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
