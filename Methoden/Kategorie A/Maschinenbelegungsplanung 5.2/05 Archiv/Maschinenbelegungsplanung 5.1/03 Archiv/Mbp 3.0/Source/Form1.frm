VERSION 5.00
Object = "{02B5E320-7292-11CF-93D5-0020AF99504A}#1.0#0"; "MSCHART.OCX"
Begin VB.Form Form1 
   Caption         =   "Form1"
   ClientHeight    =   6885
   ClientLeft      =   60
   ClientTop       =   345
   ClientWidth     =   7230
   LinkTopic       =   "Form1"
   ScaleHeight     =   6885
   ScaleWidth      =   7230
   StartUpPosition =   3  'Windows Default
   Begin VB.Frame Frame1 
      Height          =   2775
      Left            =   360
      TabIndex        =   1
      Top             =   240
      Width           =   6255
      Begin VB.CommandButton Command1 
         Caption         =   "Chart zeichnen"
         Height          =   495
         Left            =   4080
         TabIndex        =   18
         Top             =   1680
         Width           =   1695
      End
      Begin VB.TextBox A33 
         Height          =   285
         Left            =   3000
         TabIndex        =   17
         Text            =   "35"
         Top             =   1200
         Width           =   975
      End
      Begin VB.TextBox A23 
         Height          =   285
         Left            =   1920
         TabIndex        =   16
         Text            =   "5"
         Top             =   1200
         Width           =   975
      End
      Begin VB.TextBox A13 
         Height          =   285
         Left            =   840
         TabIndex        =   15
         Text            =   "20"
         Top             =   1200
         Width           =   975
      End
      Begin VB.TextBox A32 
         Height          =   285
         Left            =   3000
         TabIndex        =   14
         Text            =   "0"
         Top             =   840
         Width           =   975
      End
      Begin VB.TextBox A22 
         Height          =   285
         Left            =   1920
         TabIndex        =   13
         Text            =   "20"
         Top             =   840
         Width           =   975
      End
      Begin VB.TextBox A12 
         Height          =   285
         Left            =   840
         TabIndex        =   12
         Text            =   "40"
         Top             =   840
         Width           =   975
      End
      Begin VB.TextBox A31 
         Height          =   285
         Left            =   3000
         TabIndex        =   11
         Text            =   "0"
         Top             =   480
         Width           =   975
      End
      Begin VB.TextBox A21 
         Height          =   285
         Left            =   1920
         TabIndex        =   10
         Text            =   "0"
         Top             =   480
         Width           =   975
      End
      Begin VB.TextBox A11 
         Height          =   285
         Left            =   840
         TabIndex        =   9
         Text            =   "60"
         Top             =   480
         Width           =   975
      End
      Begin VB.Label Label7 
         Caption         =   "Auslastung A3"
         Height          =   255
         Left            =   3000
         TabIndex        =   8
         Top             =   240
         Width           =   1095
      End
      Begin VB.Label Label6 
         Caption         =   "Auslastung A2"
         Height          =   255
         Left            =   1920
         TabIndex        =   7
         Top             =   240
         Width           =   1095
      End
      Begin VB.Label Label5 
         Caption         =   "Auslastung A1"
         Height          =   255
         Left            =   840
         TabIndex        =   6
         Top             =   240
         Width           =   1095
      End
      Begin VB.Label Label3 
         Caption         =   "3"
         Height          =   255
         Left            =   120
         TabIndex        =   5
         Top             =   1200
         Width           =   615
      End
      Begin VB.Label Label2 
         Caption         =   "2"
         Height          =   255
         Left            =   120
         TabIndex        =   4
         Top             =   840
         Width           =   615
      End
      Begin VB.Label Label1 
         Caption         =   "1"
         Height          =   255
         Left            =   120
         TabIndex        =   3
         Top             =   480
         Width           =   615
      End
      Begin VB.Label Periode 
         Caption         =   "Periode"
         Height          =   255
         Left            =   120
         TabIndex        =   2
         Top             =   240
         Width           =   615
      End
   End
   Begin MSChartLib.MSChart grafik 
      Height          =   2775
      HelpContextID   =   60
      Left            =   360
      OleObjectBlob   =   "Form1.frx":0000
      TabIndex        =   0
      Top             =   3240
      Width           =   6375
   End
End
Attribute VB_Name = "Form1"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Private Sub Command1_Click()

summeperiode1 = CInt(A11.Text) + CInt(A21.Text) + CInt(A31.Text)
summeperiode2 = CInt(A12.Text) + CInt(A22.Text) + CInt(A32.Text)
summeperiode3 = CInt(A13.Text) + CInt(A23.Text) + CInt(A33.Text)
'MsgBox summeperiode1
'MsgBox summeperiode2
'MsgBox summeperiode3
If summeperiode1 > 60 Or summeperiode2 > 60 Or summeperiode3 > 60 Then
    MsgBox "summe der perioden kann nicht groesser als 60 minuten sein"
    Exit Sub
End If


j = 1
Do While j < 4
    grafik.Column = j
    i = 1
    Do While i < 4
        grafik.Row = i
        'datenfeld.Name = "a" & i & j
        If i = 1 & j = 1 Then
            grafik.Data = CInt(A11.Text)
        ElseIf i = 2 & j = 1 Then
            grafik.Data = CInt(A21.Text)
        i = i + 1
    Loop
    j = j + 1
Loop


'Dim myclasses As New Collection
'Dim thename, myobject, namelist
'Dim inst As New Class1

'num = num + 1
'thename = "a" & i & j
'inst.instancename = thename
'MsgBox thename
'myclasses.Add Item:=inst, Key:=CStr(num)
'Set inst = Nothing
'For Each myobject In myclasses
'    namelist = myobject.instancename
'Next myobject
'MsgBox namelist
End Sub

