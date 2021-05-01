VERSION 5.00
Object = "{5E9E78A0-531B-11CF-91F6-C2863C385E30}#1.0#0"; "MSFLXGRD.OCX"
Object = "{F9043C88-F6F2-101A-A3C9-08002B2F49FB}#1.2#0"; "Comdlg32.ocx"
Object = "{6B7E6392-850A-101B-AFC0-4210102A8DA7}#1.3#0"; "COMCTL32.OCX"
Begin VB.Form eingabe 
   BorderStyle     =   0  'Kein
   Caption         =   "Eingabefenster"
   ClientHeight    =   9465
   ClientLeft      =   45
   ClientTop       =   615
   ClientWidth     =   13920
   ForeColor       =   &H00000000&
   Icon            =   "eingabe.frx":0000
   KeyPreview      =   -1  'True
   MaxButton       =   0   'False
   MDIChild        =   -1  'True
   MinButton       =   0   'False
   ScaleHeight     =   9465
   ScaleWidth      =   13920
   ShowInTaskbar   =   0   'False
   Begin ComctlLib.Toolbar Toolbar1 
      Align           =   1  'Oben ausrichten
      Height          =   420
      Left            =   0
      TabIndex        =   0
      Top             =   0
      Width           =   13920
      _ExtentX        =   24553
      _ExtentY        =   741
      ButtonWidth     =   635
      ButtonHeight    =   582
      Appearance      =   1
      ImageList       =   "ImageList1"
      _Version        =   327682
      BeginProperty Buttons {0713E452-850A-101B-AFC0-4210102A8DA7} 
         NumButtons      =   17
         BeginProperty Button1 {0713F354-850A-101B-AFC0-4210102A8DA7} 
            Key             =   "neu"
            Object.ToolTipText     =   "Neue Datei anlegen"
            Object.Tag             =   ""
            ImageIndex      =   4
         EndProperty
         BeginProperty Button2 {0713F354-850A-101B-AFC0-4210102A8DA7} 
            Key             =   "oeffnen"
            Object.Tag             =   ""
            ImageIndex      =   5
         EndProperty
         BeginProperty Button3 {0713F354-850A-101B-AFC0-4210102A8DA7} 
            Key             =   "speichern"
            Object.ToolTipText     =   "Datei speichern unter"
            Object.Tag             =   ""
            ImageIndex      =   7
         EndProperty
         BeginProperty Button4 {0713F354-850A-101B-AFC0-4210102A8DA7} 
            Object.Tag             =   ""
            Style           =   3
            MixedState      =   -1  'True
         EndProperty
         BeginProperty Button5 {0713F354-850A-101B-AFC0-4210102A8DA7} 
            Key             =   "berechnung"
            Object.ToolTipText     =   "Berechnung starten"
            Object.Tag             =   ""
            ImageIndex      =   9
         EndProperty
         BeginProperty Button6 {0713F354-850A-101B-AFC0-4210102A8DA7} 
            Object.Tag             =   ""
            Style           =   3
            MixedState      =   -1  'True
         EndProperty
         BeginProperty Button7 {0713F354-850A-101B-AFC0-4210102A8DA7} 
            Enabled         =   0   'False
            Key             =   "grafik"
            Object.ToolTipText     =   "Graphische Ergebnisausgabe"
            Object.Tag             =   ""
            ImageIndex      =   1
            Style           =   1
         EndProperty
         BeginProperty Button8 {0713F354-850A-101B-AFC0-4210102A8DA7} 
            Key             =   "numerisch"
            Object.ToolTipText     =   "Tabellarische Ergebnisausgabe"
            Object.Tag             =   ""
            ImageIndex      =   3
            Style           =   1
            Value           =   1
         EndProperty
         BeginProperty Button9 {0713F354-850A-101B-AFC0-4210102A8DA7} 
            Object.Tag             =   ""
            Style           =   3
            MixedState      =   -1  'True
         EndProperty
         BeginProperty Button10 {0713F354-850A-101B-AFC0-4210102A8DA7} 
            Key             =   "zielfunktion"
            Object.ToolTipText     =   "Zielfunktionseinstellungen"
            Object.Tag             =   ""
            ImageIndex      =   8
         EndProperty
         BeginProperty Button11 {0713F354-850A-101B-AFC0-4210102A8DA7} 
            Key             =   "zielverzeichnis"
            Object.ToolTipText     =   "Temporäres Verzeichnis wählen"
            Object.Tag             =   ""
            ImageIndex      =   10
         EndProperty
         BeginProperty Button12 {0713F354-850A-101B-AFC0-4210102A8DA7} 
            Object.Tag             =   ""
            Style           =   3
            MixedState      =   -1  'True
         EndProperty
         BeginProperty Button13 {0713F354-850A-101B-AFC0-4210102A8DA7} 
            Key             =   "drucken"
            Object.ToolTipText     =   "Seite drucken"
            Object.Tag             =   ""
            ImageIndex      =   6
         EndProperty
         BeginProperty Button14 {0713F354-850A-101B-AFC0-4210102A8DA7} 
            Object.Tag             =   ""
            Style           =   3
            MixedState      =   -1  'True
         EndProperty
         BeginProperty Button15 {0713F354-850A-101B-AFC0-4210102A8DA7} 
            Key             =   "hilfe"
            Object.ToolTipText     =   "Hilfedatei öffnen"
            Object.Tag             =   ""
            ImageIndex      =   2
         EndProperty
         BeginProperty Button16 {0713F354-850A-101B-AFC0-4210102A8DA7} 
            Object.Tag             =   ""
            Style           =   3
            MixedState      =   -1  'True
         EndProperty
         BeginProperty Button17 {0713F354-850A-101B-AFC0-4210102A8DA7} 
            Key             =   "ende"
            Object.ToolTipText     =   "Programm beenden"
            Object.Tag             =   ""
            ImageIndex      =   11
         EndProperty
      EndProperty
      BorderStyle     =   1
      MouseIcon       =   "eingabe.frx":0442
      OLEDropMode     =   1
   End
   Begin MSComDlg.CommonDialog CommonDialog2 
      Left            =   10800
      Top             =   1320
      _ExtentX        =   847
      _ExtentY        =   847
      _Version        =   393216
   End
   Begin VB.TextBox check_1 
      Height          =   285
      Left            =   6000
      TabIndex        =   3
      Text            =   "Text1"
      Top             =   480
      Visible         =   0   'False
      Width           =   735
   End
   Begin VB.TextBox pfad 
      Height          =   285
      Left            =   3600
      TabIndex        =   2
      Text            =   "c:\temp"
      Top             =   480
      Visible         =   0   'False
      Width           =   1095
   End
   Begin MSComDlg.CommonDialog CommonDialog1 
      Left            =   5520
      Top             =   360
      _ExtentX        =   847
      _ExtentY        =   847
      _Version        =   393216
   End
   Begin MSFlexGridLib.MSFlexGrid MSFlexGrid1 
      Height          =   5895
      Left            =   8040
      TabIndex        =   4
      Top             =   1200
      Width           =   4215
      _ExtentX        =   7435
      _ExtentY        =   10398
      _Version        =   393216
      Rows            =   50
      Cols            =   4
      FixedCols       =   0
      ScrollBars      =   2
   End
   Begin VB.Frame Frame1 
      Caption         =   "Auftragsdaten"
      ForeColor       =   &H00000000&
      Height          =   4335
      Left            =   120
      TabIndex        =   1
      Top             =   960
      Width           =   7212
      Begin MSComDlg.CommonDialog CommonDialog4 
         Left            =   5640
         Top             =   2400
         _ExtentX        =   847
         _ExtentY        =   847
         _Version        =   393216
      End
      Begin VB.Timer Timer1 
         Left            =   8160
         Top             =   240
      End
      Begin MSFlexGridLib.MSFlexGrid tabelle_auftragsdaten 
         Height          =   3975
         Left            =   120
         TabIndex        =   5
         Top             =   240
         Width           =   7005
         _ExtentX        =   12356
         _ExtentY        =   7011
         _Version        =   393216
         Rows            =   50
         Cols            =   8
         FixedCols       =   0
         ScrollBars      =   2
      End
   End
   Begin VB.Frame Frame2 
      Caption         =   "Maschinen"
      ForeColor       =   &H00000000&
      Height          =   6255
      Left            =   7920
      TabIndex        =   6
      Top             =   960
      Width           =   2655
   End
   Begin VB.Label LblHinweis3 
      BackStyle       =   0  'Transparent
      Caption         =   "Beachten Sie bei der Ergebnisdarstellung die Anzahl der verwendeten Maschinen."
      BeginProperty Font 
         Name            =   "Verdana"
         Size            =   8.25
         Charset         =   0
         Weight          =   400
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Height          =   735
      Left            =   1920
      TabIndex        =   10
      Top             =   6480
      Width           =   3975
   End
   Begin VB.Label LblHinweis1 
      BackStyle       =   0  'Transparent
      Caption         =   "Hinweise:"
      BeginProperty Font 
         Name            =   "Verdana"
         Size            =   8.25
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Height          =   255
      Left            =   1800
      TabIndex        =   9
      Top             =   5760
      Width           =   2415
   End
   Begin VB.Label LblHinweis2 
      Alignment       =   2  'Zentriert
      AutoSize        =   -1  'True
      BackStyle       =   0  'Transparent
      Caption         =   "Eine Periode umfaßt 60 Minuten."
      BeginProperty Font 
         Name            =   "Verdana"
         Size            =   8.25
         Charset         =   0
         Weight          =   400
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Height          =   195
      Left            =   1920
      TabIndex        =   8
      Top             =   6120
      Width           =   2805
   End
   Begin VB.Label LblRahmen1 
      Alignment       =   2  'Zentriert
      BackColor       =   &H0080FF80&
      BorderStyle     =   1  'Fest Einfach
      BeginProperty Font 
         Name            =   "MS Sans Serif"
         Size            =   8.25
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      ForeColor       =   &H00000000&
      Height          =   1455
      Left            =   1440
      TabIndex        =   7
      Top             =   5640
      Width           =   4545
      WordWrap        =   -1  'True
   End
   Begin ComctlLib.ImageList ImageList1 
      Left            =   4800
      Top             =   240
      _ExtentX        =   1005
      _ExtentY        =   1005
      BackColor       =   -2147483643
      ImageWidth      =   16
      ImageHeight     =   16
      MaskColor       =   12632256
      _Version        =   327682
      BeginProperty Images {0713E8C2-850A-101B-AFC0-4210102A8DA7} 
         NumListImages   =   11
         BeginProperty ListImage1 {0713E8C3-850A-101B-AFC0-4210102A8DA7} 
            Picture         =   "eingabe.frx":0608
            Key             =   "graphik"
            Object.Tag             =   "/icons/Graph07.ico"
         EndProperty
         BeginProperty ListImage2 {0713E8C3-850A-101B-AFC0-4210102A8DA7} 
            Picture         =   "eingabe.frx":0922
            Key             =   "hilfe"
         EndProperty
         BeginProperty ListImage3 {0713E8C3-850A-101B-AFC0-4210102A8DA7} 
            Picture         =   "eingabe.frx":0A34
            Key             =   "numerisch"
         EndProperty
         BeginProperty ListImage4 {0713E8C3-850A-101B-AFC0-4210102A8DA7} 
            Picture         =   "eingabe.frx":0D4E
            Key             =   "neu"
         EndProperty
         BeginProperty ListImage5 {0713E8C3-850A-101B-AFC0-4210102A8DA7} 
            Picture         =   "eingabe.frx":0E60
            Key             =   "oeffnen"
         EndProperty
         BeginProperty ListImage6 {0713E8C3-850A-101B-AFC0-4210102A8DA7} 
            Picture         =   "eingabe.frx":0F72
            Key             =   "drucken"
         EndProperty
         BeginProperty ListImage7 {0713E8C3-850A-101B-AFC0-4210102A8DA7} 
            Picture         =   "eingabe.frx":1084
            Key             =   "speichern"
         EndProperty
         BeginProperty ListImage8 {0713E8C3-850A-101B-AFC0-4210102A8DA7} 
            Picture         =   "eingabe.frx":1196
            Key             =   "zielfunktion"
         EndProperty
         BeginProperty ListImage9 {0713E8C3-850A-101B-AFC0-4210102A8DA7} 
            Picture         =   "eingabe.frx":14B0
            Key             =   "berechnung"
         EndProperty
         BeginProperty ListImage10 {0713E8C3-850A-101B-AFC0-4210102A8DA7} 
            Picture         =   "eingabe.frx":17CA
            Key             =   "zielverzeichnis"
         EndProperty
         BeginProperty ListImage11 {0713E8C3-850A-101B-AFC0-4210102A8DA7} 
            Picture         =   "eingabe.frx":18DC
            Key             =   "ende"
         EndProperty
      EndProperty
   End
   Begin VB.Menu Datei 
      Caption         =   "Datei"
      WindowList      =   -1  'True
      Begin VB.Menu Neu 
         Caption         =   "Neu ..."
      End
      Begin VB.Menu Öffnen 
         Caption         =   "Öffnen ..."
      End
      Begin VB.Menu Speichern 
         Caption         =   "Speichern ..."
      End
      Begin VB.Menu Drucken 
         Caption         =   "Drucken ..."
      End
      Begin VB.Menu Beenden 
         Caption         =   "Beenden"
      End
   End
   Begin VB.Menu Ausgabeoptionen 
      Caption         =   "Ausgabeoptionen"
      Begin VB.Menu Numerisch 
         Caption         =   "Numerisch"
         Checked         =   -1  'True
      End
      Begin VB.Menu Grafik 
         Caption         =   "Graphisch"
         Enabled         =   0   'False
      End
   End
   Begin VB.Menu Einstellungen 
      Caption         =   "Einstellungen"
      Begin VB.Menu Zielverzeichnis 
         Caption         =   "Zielverzeichnis"
      End
      Begin VB.Menu Zielfunktion 
         Caption         =   "Zielfunktion"
      End
   End
   Begin VB.Menu Hilfe 
      Caption         =   "Hilfe"
      Begin VB.Menu Info 
         Caption         =   "Info"
      End
      Begin VB.Menu Handbuch 
         Caption         =   "Handbuch"
         Shortcut        =   {F1}
      End
   End
End
Attribute VB_Name = "eingabe"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False

Private Sub Initialize()
    tabelle_auftragsdaten.TextMatrix(0, 0) = "" ' leere spalte 1 pixel breit
    tabelle_auftragsdaten.TextMatrix(0, 1) = "Auftrags-Nr."
    tabelle_auftragsdaten.TextMatrix(0, 2) = "Bezeichnung"
    tabelle_auftragsdaten.TextMatrix(0, 3) = "Dauer"
    tabelle_auftragsdaten.TextMatrix(0, 4) = "Typ 1"
    tabelle_auftragsdaten.TextMatrix(0, 5) = "Typ 2"
    tabelle_auftragsdaten.TextMatrix(0, 6) = "Beginn"
    tabelle_auftragsdaten.TextMatrix(0, 7) = "Ende"
    'tabelle_auftragsdaten.TextMatrix(0, 8) = "Beginn"
    'tabelle_auftragsdaten.TextMatrix(0, 9) = "Ende"
    tabelle_auftragsdaten.ColWidth(0) = 1 ' leere spalte 1 pixel breit
    tabelle_auftragsdaten.ColWidth(1) = 1000
    tabelle_auftragsdaten.ColWidth(2) = 2140
    tabelle_auftragsdaten.ColWidth(3) = 700
    tabelle_auftragsdaten.ColWidth(4) = 700
    tabelle_auftragsdaten.ColWidth(5) = 700
    tabelle_auftragsdaten.ColWidth(6) = 700
    tabelle_auftragsdaten.ColWidth(7) = 700
    'tabelle_auftragsdaten.ColWidth(8) = 600
    'tabelle_auftragsdaten.ColWidth(9) = 600
    'Tabelle.Width = 7000

    MSFlexGrid1.TextMatrix(0, 0) = "" 'leere spalte 1 pixel breit
    MSFlexGrid1.TextMatrix(0, 1) = "Periode"
    MSFlexGrid1.TextMatrix(0, 2) = "Typ"
    MSFlexGrid1.TextMatrix(0, 3) = "Anzahl"
    MSFlexGrid1.ColWidth(0) = 1
    MSFlexGrid1.ColWidth(1) = 700
    MSFlexGrid1.ColWidth(2) = 700
    MSFlexGrid1.ColWidth(3) = 700
    MSFlexGrid1.Width = 2440
End Sub

Private Sub Berechnen_Click()
'in der Funktion werden die Daten für den LP-Solver in eine
'externe Datei geschrieben
'Deklaration der Datentypen
Dim zielfkt$, z&, s&, i&, zaehler_zeile&, zaehler_spalte&
Dim anz_var_zeile&, beginn As Variant, Ende As Variant, s_var&, z_var&, max_auftragsnr&
Dim perioden_laenge&, merke_anz_RestStr, auf_nr&, faktor_y&
Dim RestStr$, RestStr_2$, Schaltervariablen$
Dim typ1%, typ2%

'//Solver Datei zum Schreiben öffnen
Open ArbeitsVerz + "\mbp.lp" For Output As #1

'//Wahl welche Restriktion genommen werden muss
'// siehe auch Form Frage
If check_1.Text = "2" Then
'// minimierung der Maschinen
   zielfkt = "min: Y;"
Else
'//minimierung mit tatsächlichen Maschinen
   zielfkt = "min:"
End If
'// schreiben der Zielfunktion in die Datei
Print #1, zielfkt
Print #1,
zaehler_zeile = 0  'Hilfszähler für Auftrags-Index -> Für Darstellung von Indizes > 9
zaehler_spalte = 0 'Hilfszähler für Perioden-Index -> Für Darstellung von Indizes > 9
s_var = 1          'Hilfszähler für Perioden-Index -> Für Darstellung von Indizes > 9
z_var = 1          'Hilfszähler für Auftrags-Index -> Für Darstellung von Indizes > 9
merke_anz_RestStr = 0
zaehler_rest = 0 'zählt die Restriktionen

If tabelle_auftragsdaten.TextMatrix(1, 1) = "" Or tabelle_auftragsdaten.TextMatrix(1, 3) = "" Or tabelle_auftragsdaten.TextMatrix(1, 6) = "" Or tabelle_auftragsdaten.TextMatrix(1, 7) = "" Then
    res = MsgBox("Bitte überprüfen Sie die Eingabewerte!", vbCritical)
    Close #1
    Exit Sub
End If
          
'Restriktionen
If check_1.Text = "2" Then
   'Restriktionen für Aufträge
   For z = 1 To 49 'z zählt die Zeilen
       If tabelle_auftragsdaten.TextMatrix(z, 3) <> "" Then
          zaehler_rest = zaehler_rest + 1
          RestStr = "R" & zaehler_rest & ": "
          beginn = tabelle_auftragsdaten.TextMatrix(z, 6)
          Ende = tabelle_auftragsdaten.TextMatrix(z, 7)
          anz_var_zeile = Ende - beginn 'Anzahl Variablen pro Zeile (pro Auftrag)
          s_var = beginn
       Else
          Exit For
       End If
       
       If tabelle_auftragsdaten.TextMatrix(z, 4) = "ja" And tabelle_auftragsdaten.TextMatrix(z, 5) = "ja" Then
       'Wenn 2 Typen angegeben -> Schaltervariable
       Schalter = 1
       For s = 1 To anz_var_zeile + 1 's zählt die Spalten
           If s > 1 Then
              RestStr = RestStr & " + "
           End If
           If z > 9 Then
                RestStr = RestStr & "1x" & z & zaehler_spalte & s_var & "1"
           Else
                RestStr = RestStr & "1x" & zaehler_zeile & z & zaehler_spalte & s_var & "1"
           End If
           If s_var = 9 Then
              zaehler_spalte = zaehler_spalte + 1
              s_var = 0
           Else
              s_var = s_var + 1
           End If
       Next s
       SchalterStr = Mid(RestStr, 5, 50)
       s_var = beginn
       RestStr_2 = ""
       For s = 1 To anz_var_zeile + 1 's zählt die Spalten
           If s > 1 Then
              RestStr_2 = RestStr_2 & " + "
           End If
           If z > 9 Then
                RestStr_2 = RestStr_2 & "1x" & z & zaehler_spalte & s_var & "2"
           Else
                RestStr_2 = RestStr_2 & "1x" & zaehler_zeile & z & zaehler_spalte & s_var & "2"
           End If
           If s_var = 9 Then
              zaehler_spalte = zaehler_spalte + 1
              s_var = 0
           Else
              s_var = s_var + 1
           End If
       Next s
       RestStr = RestStr & " + " & RestStr_2
       ElseIf tabelle_auftragsdaten.TextMatrix(z, 4) = "ja" Then
       '"Normalfall" mit Typ1
       For s = 1 To anz_var_zeile + 1 's zählt die Spalten
           If s > 1 Then
              RestStr = RestStr & " + "
           End If
           If z > 9 Then
                RestStr = RestStr & "1x" & z & zaehler_spalte & s_var & "1"
           Else
                RestStr = RestStr & "1x" & zaehler_zeile & z & zaehler_spalte & s_var & "1"
           End If
           If s_var = 9 Then
              zaehler_spalte = zaehler_spalte + 1
              s_var = 0
           Else
              s_var = s_var + 1
           End If
       Next s
       ElseIf tabelle_auftragsdaten.TextMatrix(z, 5) = "ja" Then
       '"Normalfall" mit Typ2
       For s = 1 To anz_var_zeile + 1 's zählt die Spalten
           If s > 1 Then
              RestStr = RestStr & " + "
           End If
           If z > 9 Then
                RestStr = RestStr & "1x" & z & zaehler_spalte & s_var & "2"
           Else
                RestStr = RestStr & "1x" & zaehler_zeile & z & zaehler_spalte & s_var & "2"
           End If
           If s_var = 9 Then
              zaehler_spalte = zaehler_spalte + 1
              s_var = 0
           Else
              s_var = s_var + 1
           End If
       Next s
       Else
       'Für einen Auftrag keinen Maschinentyp angegeben
           res = MsgBox("Für einen Auftrag wurde kein Maschinentyp angegeben! Bitte überprüfen Sie die Eingabewerte.", vbCritical, "Kein Maschinentyp angegeben!")
           flgErgebnis = 0
           Close #1
           Exit Sub
       End If
              
       RestStr = RestStr & " = " + tabelle_auftragsdaten.TextMatrix(z, 3) & ";"
       Print #1, RestStr
       If Schalter = 1 Then
          zaehler_rest = zaehler_rest + 1
          If Schaltervariablen <> "" Then
             Schaltervariablen = Schaltervariablen & ", " & "MS" & z
          Else
             Schaltervariablen = "MS" & z
          End If
          SchalterStr = "R" & zaehler_rest & ": " & SchalterStr & " + 10000 - 10000 * MS" & z & " >= " & tabelle_auftragsdaten.TextMatrix(z, 3) & ";"
          Print #1, SchalterStr
          zaehler_rest = zaehler_rest + 1
          SchalterStr = "R" & zaehler_rest & ": " & RestStr_2 & " + 10000 * MS" & z & " >= " & tabelle_auftragsdaten.TextMatrix(z, 3) & ";"
          Print #1, SchalterStr
          Schalter = 0
       End If
       
       If z_var = 9 Then
           zaehler_zeile = zaehler_zeile + 1
           z_var = 0
       Else
           z_var = z_var + 1
       End If
       s_var = 1
       zaehler_zeile = 0
       zaehler_spalte = 0
       
   Next z
   'Merke um die Aufträge zu zählen und um z zwischenzuspeichern
   merke_anz_RestStr = zaehler_rest
   max_auftragsnr = zaehler_rest
   'Restriktionen für Perioden
   '****************************************************************
   
   Print #1, ""
   zaehler_zeile = 0    'Hilfszähler für Auftrags-Index -> Für Darstellung von Indizes > 9
   zaehler_spalte = 0   'Hilfszähler für Perioden-Index -> Für Darstellung von Indizes > 9
   s_var = 1            'Hilfszähler für Perioden-Index -> Für Darstellung von Indizes > 9
   z_var = 1            'Hilfszähler für Auftrags-Index -> Für Darstellung von Indizes > 9
   k = 1
   
   
   perioden_laenge = Find_Periodenlaenge(i)
   For z = 1 To perioden_laenge
       'merke_anz_RestStr = merke_anz_RestStr + 1
       'RestStr = "R" & merke_anz_RestStr & ": "
       RestStr = ""
    
       For s = 1 To max_auftragsnr
           beginn = Val(tabelle_auftragsdaten.TextMatrix(s, 6))
           Ende = Val(tabelle_auftragsdaten.TextMatrix(s, 7))
           
           If tabelle_auftragsdaten.TextMatrix(s, 4) = "ja" And beginn <= z And Ende >= z Then
              If i > 0 Then
                 RestStr = RestStr & " + "
              End If
              If s_var = -1 Then
                 s_var = s_var + 1
              End If
              RestStr = RestStr + "1x" & zaehler_spalte & s_var & zaehler_zeile & z_var
              'RestStr = RestStr & tabelle_auftragsdaten.TextMatrix(s, 4)
              RestStr = RestStr & "1"
              
              i = 1
           End If
           If s_var = 9 Then
              zaehler_spalte = zaehler_spalte + 1
              s_var = -1
           ElseIf s_var <> -1 Then
              s_var = s_var + 1
           End If
                
       Next s
    
        If RestStr <> "" And MSFlexGrid1.TextMatrix(k, 3) <> "" Then
            merke_anz_RestStr = merke_anz_RestStr + 1
            tmpRestStr = "R" & merke_anz_RestStr & ": "
        
            RestStr = tmpRestStr & RestStr & " - " & MSFlexGrid1.TextMatrix(k, 3) & "Y <= 0;"
            Print #1, RestStr
        End If
       
       
       k = k + 2
       i = 0
       
       
       
       If z_var = 9 Then
           zaehler_zeile = zaehler_zeile + 1
           z_var = 0
       Else
            z_var = z_var + 1
       End If
       s_var = 1
       zaehler_spalte = 0
       
       
       
   Next z
   
   'Forschleife für Maschinentyp 2
   zaehler_zeile = 0    'Hilfszähler für Auftrags-Index -> Für Darstellung von Indizes > 9
   zaehler_spalte = 0   'Hilfszähler für Perioden-Index -> Für Darstellung von Indizes > 9
   s_var = 1            'Hilfszähler für Perioden-Index -> Für Darstellung von Indizes > 9
   z_var = 1            'Hilfszähler für Auftrags-Index -> Für Darstellung von Indizes > 9
   k = 2
   
   For z = 1 To perioden_laenge
        
       'merke_anz_RestStr = merke_anz_RestStr + 1
       'RestStr = "R" & merke_anz_RestStr & ": "
        RestStr = ""
       For s = 1 To max_auftragsnr
       
        beginn = Val(tabelle_auftragsdaten.TextMatrix(s, 6))
        Ende = Val(tabelle_auftragsdaten.TextMatrix(s, 7))
        
        If tabelle_auftragsdaten.TextMatrix(s, 5) = "ja" And beginn <= z And Ende >= z Then
        
              If i > 0 Then
                 RestStr = RestStr & " + "
              End If
              If s_var = -1 Then
                 s_var = s_var + 1
              End If
              RestStr = RestStr + "1x" & zaehler_spalte & s_var & zaehler_zeile & z_var
              
              'RestStr = RestStr & tabelle_auftragsdaten.TextMatrix(s, 5)
              RestStr = RestStr & "2"
              
              i = 1
              
         End If
           If s_var = 9 Then
              zaehler_spalte = zaehler_spalte + 1
              s_var = -1
           ElseIf s_var <> -1 Then
              s_var = s_var + 1
           End If
         
         
       Next s
        
    
       If RestStr <> "" And MSFlexGrid1.TextMatrix(k, 3) <> "" Then
            merke_anz_RestStr = merke_anz_RestStr + 1
            tmpRestStr = "R" & merke_anz_RestStr & ": "
        
            RestStr = tmpRestStr & RestStr & " - " & MSFlexGrid1.TextMatrix(k, 3) & "Y <= 0;"
            Print #1, RestStr
        End If
        
        
        
        
      
       k = k + 2
       i = 0
       
       If z_var = 9 Then
           zaehler_zeile = zaehler_zeile + 1
           z_var = 0
       Else
            z_var = z_var + 1
       End If
       s_var = 1
       zaehler_spalte = 0
    
    
    
   Next z
   
   'Restriktionen im Zusammenhang der maximalen Periodenlaenge
   '******************************************************************
   
   Print #1, ""
   
   zaehler_zeile = 0    'Hilfszähler für Auftrags-Index -> Für Darstellung von Indizes > 9
   zaehler_spalte = 0   'Hilfszähler für Perioden-Index -> Für Darstellung von Indizes > 9
   s_var = 1            'Hilfszähler für Perioden-Index -> Für Darstellung von Indizes > 9
   z_var = 1            'Hilfszähler für Auftrags-Index -> Für Darstellung von Indizes > 9
   k = 1
   
   
   perioden_laenge = Find_Periodenlaenge(i)
   For z = 1 To perioden_laenge
       'merke_anz_RestStr = merke_anz_RestStr + 1
       'RestStr = "R" & merke_anz_RestStr & ": "
       RestStr = ""
    
       For s = 1 To max_auftragsnr
           beginn = Val(tabelle_auftragsdaten.TextMatrix(s, 6))
           Ende = Val(tabelle_auftragsdaten.TextMatrix(s, 7))
           If tabelle_auftragsdaten.TextMatrix(s, 4) = "ja" And beginn <= z And Ende >= z Then
              If i > 0 Then
                 RestStr = RestStr & " + "
              End If
              If s_var = -1 Then
                 s_var = s_var + 1
              End If
              RestStr = RestStr + "1x" & zaehler_spalte & s_var & zaehler_zeile & z_var
              
              'RestStr = RestStr & tabelle_auftragsdaten.TextMatrix(s, 4)
              RestStr = RestStr & "1"
              
              i = 1
           End If
           If s_var = 9 Then
              zaehler_spalte = zaehler_spalte + 1
              s_var = -1
           ElseIf s_var <> -1 Then
              s_var = s_var + 1
           End If
                
       Next s
    
        If RestStr <> "" And MSFlexGrid1.TextMatrix(k, 3) <> "" Then
            merke_anz_RestStr = merke_anz_RestStr + 1
            tmpRestStr = "R" & merke_anz_RestStr & ": "
        
            RestStr = tmpRestStr & RestStr & " <= " & Val(MSFlexGrid1.TextMatrix(k, 3)) * 60 & ";"
            Print #1, RestStr
        End If
       
       
       k = k + 2
       i = 0
       
       
       
       If z_var = 9 Then
           zaehler_zeile = zaehler_zeile + 1
           z_var = 0
       Else
            z_var = z_var + 1
       End If
       s_var = 1
       zaehler_spalte = 0
       
       
       
   Next z
   
   'Forschleife für Maschinentyp 2
   zaehler_zeile = 0    'Hilfszähler für Auftrags-Index -> Für Darstellung von Indizes > 9
   zaehler_spalte = 0   'Hilfszähler für Perioden-Index -> Für Darstellung von Indizes > 9
   s_var = 1            'Hilfszähler für Perioden-Index -> Für Darstellung von Indizes > 9
   z_var = 1            'Hilfszähler für Auftrags-Index -> Für Darstellung von Indizes > 9
   k = 2
   
   For z = 1 To perioden_laenge
        
       'merke_anz_RestStr = merke_anz_RestStr + 1
       'RestStr = "R" & merke_anz_RestStr & ": "
        RestStr = ""
       For s = 1 To max_auftragsnr
       
        beginn = Val(tabelle_auftragsdaten.TextMatrix(s, 6))
        Ende = Val(tabelle_auftragsdaten.TextMatrix(s, 7))
        
        If tabelle_auftragsdaten.TextMatrix(s, 5) = "ja" And beginn <= z And Ende >= z Then
        
              If i > 0 Then
                 RestStr = RestStr & " + "
              End If
              If s_var = -1 Then
                 s_var = s_var + 1
              End If
              RestStr = RestStr + "1x" & zaehler_spalte & s_var & zaehler_zeile & z_var
              
              'RestStr = RestStr & tabelle_auftragsdaten.TextMatrix(s, 5)
              RestStr = RestStr & "2"
              
              
              i = 1
              
         End If
           If s_var = 9 Then
              zaehler_spalte = zaehler_spalte + 1
              s_var = -1
           ElseIf s_var <> -1 Then
              s_var = s_var + 1
           End If
         
         
       Next s
        
    
       If RestStr <> "" And MSFlexGrid1.TextMatrix(k, 3) <> "" Then
            merke_anz_RestStr = merke_anz_RestStr + 1
            tmpRestStr = "R" & merke_anz_RestStr & ": "
        
            RestStr = tmpRestStr & RestStr & " <= " & Val(MSFlexGrid1.TextMatrix(k, 3)) * 60 & ";"
            Print #1, RestStr
        End If
        
      
       k = k + 2
       i = 0
       
       If z_var = 9 Then
           zaehler_zeile = zaehler_zeile + 1
           z_var = 0
       Else
            z_var = z_var + 1
       End If
       s_var = 1
       zaehler_spalte = 0
    
    
    
   Next z
  
End If
If Schaltervariablen <> "" Then
   Print #1, ""
   Schaltervariablen = "int " & Schaltervariablen & ";"
   Print #1, Schaltervariablen
End If
'//Solvr Datei schliessen
Close #1
solver_laden

' /////////////////////////////////////////////////////////////////////////////////
' hier muessen jetzt die daten aus den tabellen als lineares modell generiert
' und zum solver geschickt werden fuer den zugriff auf die eingegebenen daten:
' auftragsdatentabelle : objekt tabelle_auftragsdaten 4 (!) Spalten und 100 Zeilen
' maschinendatentabelle: objekt MSFlexGrid1 3 Spalten und 100 Zeilen
' check_1 hat den wert 1  wenn die option tatsächliche maschinen gewählt wurde
' zugriff darauf mit check_1.text
' check_1 hat den wert 2 wenn minimierung der vorhandenen maschinen gewählt ist
' der pfad der angibt wo die dateien zum schreiben und lesen liegen
' steht im objekt pfad der zugriff darauf also mit pfad.text
' der gewählte solver ist im objekt solver_choice.
' zugriff darauf mit solver_choice.text ist der 1. solver gewaehlt
' dann ist solver_choice.text="Option1" beim 2.Solver "Option2" beim 3. Solver "Option3"
' //////////////////////////////////////////////////////////////////////////////////
End Sub
'// Laden der Eingabe Form //////////////////////////////////////////

Private Sub Form_Load()
'Height = eingabe.Height - 1500
'Width = eingabe.Width - 1400
Toolbar1.ImageList = ImageList1

'Laden der Form
Initialize

'// Zeilen der Tabellen ausgeben und fuellen/////////////
'If anz_auftr = 0 Then
'For k = 1 To 15
'   tabelle_auftragsdaten.TextMatrix(k, 1) = k
'Next k
'End If
If anz_auftr > 0 Then
    For k = 1 To anz_auftr
        tabelle_auftragsdaten.TextMatrix(k, 1) = k
        tabelle_auftragsdaten.TextMatrix(k, 4) = "nein"
        tabelle_auftragsdaten.TextMatrix(k, 5) = "nein"
    Next k
End If

MSFlexGrid1.MergeCells = 1 'Zellen für gleiche Perioden zusammenfassen
MSFlexGrid1.MergeCol(1) = True

If anz_perioden <> 0 Then
    Call Fuell_maschinen_tab(1, 1, 1)
End If

check_1.Visible = False
'// check_1 auf 2 gesetzt---muss noch geaendert werden
check_1.Text = "2"
grafik_anz = 0
'Grafik.Checked = False
tabelle_anz = 1
End Sub

'Rekursive Methode zum Füllen der Maschinentabelle je nach Anzahl Perioden und Maschinentypen
Private Sub Fuell_maschinen_tab(i As Integer, j As Integer, k As Integer)
    MSFlexGrid1.TextMatrix(j, 1) = i
    MSFlexGrid1.TextMatrix(j, 2) = k
    MSFlexGrid1.TextMatrix(j, 3) = 0
    If k <> anz_masch Then
        Call Fuell_maschinen_tab(i, j + 1, k + 1)
    End If
    If i < anz_perioden Then
        i = i + 1
        j = j + 1
        k = 1
        Call Fuell_maschinen_tab(i, j, k)
    End If
End Sub

Private Sub tabelle_auftragsdaten_Click()
res = tabelle_auftragsdaten.Col
If res = 4 Or res = 5 Then
  If tabelle_auftragsdaten.TextMatrix(tabelle_auftragsdaten.Row, 1) <> "" Then
    If tabelle_auftragsdaten.Text = "nein" Then
      tabelle_auftragsdaten.Text = "ja"
    Else
      tabelle_auftragsdaten.Text = "nein"
    End If
  Else
    tabelle_auftragsdaten.Text = ""
  End If
End If
End Sub

'//Tabelle Auftragsdaten-- Farben fuer aktive Zelle////////////////////////////////////
Private Sub tabelle_auftragsdaten_EnterCell()
tabelle_auftragsdaten.CellBackColor = vb3DHighlight
End Sub
'//Tabelle Maschinendaten--Farben fuer aktive Zelle///////////////////////////////////
Private Sub MSFlexGrid1_EnterCell()
MSFlexGrid1.CellBackColor = vb3DHighlight
End Sub
'//Tabelle Auftragsdaten--Farben zuruecksetzen///////////////////////////////////
Private Sub tabelle_auftragsdaten_LeaveCell()
tabelle_auftragsdaten.CellBackColor = &HFFFFFF ' verlassene zelle wieder weiss
res = tabelle_auftragsdaten.Col
res2 = tabelle_auftragsdaten.Col
res3 = tabelle_auftragsdaten.Row
Dim plaenge&, plaengeneu&, i&


If res = 1 Then
res = tabelle_auftragsdaten.Row

If tabelle_auftragsdaten.TextMatrix(res, 1) = "" Then
  
  For i = 1 To 7
    tabelle_auftragsdaten.TextMatrix(res, i) = ""
  Next i
  plaenge = Find_Periodenlaenge(i)
  plaenge = plaenge + 1
  For k = (plaenge + (plaenge - 1)) To 49
    MSFlexGrid1.TextMatrix(k, 1) = ""
    MSFlexGrid1.TextMatrix(k, 2) = ""
    MSFlexGrid1.TextMatrix(k, 3) = ""
   Next
ElseIf tabelle_auftragsdaten.TextMatrix(res, 1) <> "" Then
  If tabelle_auftragsdaten.TextMatrix(res, 4) = "" Then
    tabelle_auftragsdaten.TextMatrix(res, 4) = "nein"
    tabelle_auftragsdaten.TextMatrix(res, 5) = "nein"
    
  End If
End If
End If
If res2 = 7 Then
    
        
    If tabelle_auftragsdaten.TextMatrix(res3, 7) <> "" Then
    plaenge = Find_Periodenlaenge(i)
    
    For j = 1 To plaenge
    MSFlexGrid1.TextMatrix(j + (j - 1), 1) = j
    MSFlexGrid1.TextMatrix(j * 2, 1) = j
    MSFlexGrid1.TextMatrix(j + (j - 1), 2) = "1"
    MSFlexGrid1.TextMatrix(j * 2, 2) = "2"
    If MSFlexGrid1.TextMatrix(j + (j - 1), 3) = "" Then
       MSFlexGrid1.TextMatrix(j + (j - 1), 3) = "0"
    End If
    If MSFlexGrid1.TextMatrix(j * 2, 3) = "" Then
       MSFlexGrid1.TextMatrix(j * 2, 3) = "0"
    End If
    
    Next
    plaenge = plaenge + 1
    For k = (plaenge + (plaenge - 1)) To 49
        MSFlexGrid1.TextMatrix(k, 1) = ""
        MSFlexGrid1.TextMatrix(k, 2) = ""
        MSFlexGrid1.TextMatrix(k, 3) = ""
        Next
        
    End If
    
End If

    
End Sub
'//Tabelle Maschinendaten--Farben zuruecksetzen///////////////////////////////////
Private Sub MSFlexGrid1_LeaveCell()
    MSFlexGrid1.CellBackColor = &HFFFFFF ' verlassene zelle wieder weiss
    res = MSFlexGrid1.Row
    If MSFlexGrid1.TextMatrix(res, 1) <> "" And MSFlexGrid1.TextMatrix(res, 3) = "" Then
       MSFlexGrid1.TextMatrix(res, 3) = "0"
    End If
End Sub

'//Sub zum Abfangen von Tastatureingaben in der Tabelle MSFlexGrid1(Maschinendaten)///////
Private Sub MSFlexGrid1_KeyPress(KeyAscii As Integer)
'MsgBox KeyAscii
res = MSFlexGrid1.Col
If res = 1 Or res = 2 Then
  Exit Sub
End If
'Bei "Zeichenlöschen-taste" wird ein Zeichen aus dem Inhalt der Zelle gelöscht
If KeyAscii = 8 Then
    wortmylen = Len(MSFlexGrid1.Text)
    If wortmylen = 0 Then
        Exit Sub
    End If
    newword = Mid(MSFlexGrid1.Text, 1, wortmylen - 1)
    'MsgBox newword
    MSFlexGrid1.Text = newword
    Exit Sub
'Bei "Escape" passiert nichts
ElseIf KeyAscii = 27 Then
    Exit Sub
'Bei "RETURN" wird in der Tabelle ein Feld weitergegangen
ElseIf KeyAscii = 13 Then
    On Error Resume Next
    MSFlexGrid1.Row = MSFlexGrid1.Row + 1
    If Err = 30009 Then
        MSFlexGrid1.Row = 1
        On Error Resume Next
        MSFlexGrid1.Col = MSFlexGrid1.Col + 1
        If Err = 30010 Then
            MSFlexGrid1.Row = 1
            MSFlexGrid1.Col = 1
        End If
    End If
    Call MSFlexGrid1_LeaveCell
    Call MSFlexGrid1_EnterCell
'Ansosnten wird das entsprechende Zeichen in die Tabelle eingetragen
ElseIf res <> 2 And KeyAscii <> 48 And KeyAscii <> 49 And KeyAscii <> 50 And KeyAscii <> 51 And KeyAscii <> 52 And KeyAscii <> 53 And KeyAscii <> 54 And KeyAscii <> 55 And KeyAscii <> 56 And KeyAscii <> 57 Then
    Exit Sub
ElseIf res <> 1 And MSFlexGrid1.TextMatrix(MSFlexGrid1.Row, 1) = "" Then
    Exit Sub
Else
    MyChar = Chr(KeyAscii)
    MSFlexGrid1.Text = MSFlexGrid1.Text & MyChar
End If
End Sub

'Sub zum Abfangen von Tastatureingaben in der Tabelle Auftragsdaten
Private Sub tabelle_auftragsdaten_KeyPress(KeyAscii As Integer)
'MsgBox KeyAscii
res = tabelle_auftragsdaten.Col
'Bei "Zeichenlöschen-taste" wird ein Zeichen aus dem Inhalt der Zelle gelöscht
If res = 4 Or res = 5 Then
  Exit Sub
End If
If KeyAscii = 8 Then
    wortmylen = Len(tabelle_auftragsdaten.Text)
    If wortmylen = 0 Then
        Exit Sub
    End If
    newword = Mid(tabelle_auftragsdaten.Text, 1, wortmylen - 1)
    'MsgBox newword
    tabelle_auftragsdaten.Text = newword
    Exit Sub
'Bei "Escape" passiert nichts
ElseIf KeyAscii = 27 Then
    Exit Sub
'Bei "RETURN" wird in der Tabelle ein Feld weitergegangen
ElseIf KeyAscii = 13 Then
    On Error Resume Next
    tabelle_auftragsdaten.Row = tabelle_auftragsdaten.Row + 1
    If Err = 30009 Then
        tabelle_auftragsdaten.Row = 1
        On Error Resume Next
        tabelle_auftragsdaten.Col = tabelle_auftragsdaten.Col + 1
        If Err = 30010 Then
            tabelle_auftragsdaten.Row = 1
            tabelle_auftragsdaten.Col = 1
        End If
    End If
    Call tabelle_auftragsdaten_LeaveCell
    Call tabelle_auftragsdaten_EnterCell
'Ansosnten wird das entsprechende Zeichen in die Tabelle eingetragen
ElseIf res <> 2 And KeyAscii <> 48 And KeyAscii <> 49 And KeyAscii <> 50 And KeyAscii <> 51 And KeyAscii <> 52 And KeyAscii <> 53 And KeyAscii <> 54 And KeyAscii <> 55 And KeyAscii <> 56 And KeyAscii <> 57 Then
    Exit Sub
ElseIf res <> 1 And tabelle_auftragsdaten.TextMatrix(tabelle_auftragsdaten.Row, 1) = "" Then
    Exit Sub
Else
    MyChar = Chr(KeyAscii)
    tabelle_auftragsdaten.Text = tabelle_auftragsdaten.Text & MyChar
End If
End Sub

Private Sub Hilfstext_Change()
tabelle_auftragsdaten.Text = Hilfstext.Text  ' aenderungen in text3 sofort in tabelle kopieren
End Sub
'//Tool Tip fuer Tabelle Auftragsdaten//////////////////////////////////////////
Private Sub tabelle_auftragsdaten_MouseUp(Button As Integer, shift As Integer, X As Single, Y As Single)
If tabelle_auftragsdaten.Col <> 2 Then
   tabelle_auftragsdaten.ToolTipText = "Zahl"
Else
   tabelle_auftragsdaten.ToolTipText = "String"
End If
End Sub

Private Sub Command1_Click()
    Dim X(1 To 7, 1 To 7) As Variant
    Dim rowLabelCount As Integer
    Dim columnLabelCount As Integer
    Dim rowCount As Integer
    Dim columnCount As Integer
    Set DataGrid = Graphik.MSChart1.DataGrid
    Graphik.MSChart1.chartType = VtChChartType2dStep
    'With MSChart1.DataGrid
    ' Set Chart parameters using methods.
    '    rowLabelCount = 7
    '    columnLabelCount = 7
    '    rowCount = 1
    '    columnCount = 1
    '    .SetSize rowLabelCount, RolumnLabelCount, _
    '    rowCount, columnCount
    'MsgBox "1"
' Randomly fill in the data.
    '    .RandomDataFill
        ' Then assign labels to second Level.
    '    labelIndex = 2
    'End With
    colanzahl = Tabelle.matthias.Cols
    rowanzahl = Tabelle.matthias.Rows
    'MsgBox tabelle.matthias.Cols
    'MsgBox "colanzahl: " & colanzahl
    'msgBox "rowanzahl: " & rowanzahl
    Graphik.MSChart1.columnCount = colanzahl
    'Graphik.MSChart1.RowLabel = ""
    i = 1
    j = 1
    Do While i < rowanzahl
        j = 1
        Tabelle.matthias.Row = i
        total = 0
        Do While j < colanzahl
        Tabelle.matthias.Col = j
        X(i, j) = Tabelle.matthias.TextMatrix(i, j)
        If X(i, j) = "" Then
            X(i, j) = 0
        End If
        total = total + X(i, j)
        X(i, j) = total
        Graphik.MSChart1.ChartData = X
        j = j + 1
        Loop
        i = i + 1
    Loop
    Graphik.MSChart1.rowCount = rowanzahl - 1
    Graphik.MSChart1.columnCount = colanzahl - 1
    label_i = 1
    Do While label_i <= Graphik.MSChart1.rowCount
        Graphik.MSChart1.RowLabel = "Periode " & label_i
        On Error Resume Next
        Graphik.MSChart1.Row = Graphik.MSChart1.Row + 1
        label_i = label_i + 1
    Loop
    label_i = 1
    Do While label_i <= Graphik.MSChart1.columnCount
        Graphik.MSChart1.ColumnLabel = "A " & label_i
        On Error Resume Next
        Graphik.MSChart1.Column = Graphik.MSChart1.Column + 1
        label_i = label_i + 1
    Loop
    Graphik.Show
End Sub
'// Sub fuer die Toolbar--- Verweis zu den Menuefunktionen
Private Sub Toolbar1_ButtonClick(ByVal Button As ComctlLib.Button)

Dim anzahl

Select Case Button.Key
    Case "berechnung"
        Berechnen_Click
    Case "oeffnen"
        Öffnen_Click
    Case "speichern"
        Speichern_Click
    Case "neu"
        Neu_Click
    Case "zielfunktion"
        Zielfunktion_Click
    Case "zielverzeichnis"
        Zielverzeichnis_Click
    Case "grafik"
        If Button.Value = 1 Then
            Grafik.Checked = True
            If flgErgebnis = 1 Then
                graf_anzeigen = 1
                Berechnen_Click
            End If
        Else
            Grafik.Checked = False
            Graphik.Hide
        End If
    Case "numerisch"
        If Button.Value = 1 Then
            Numerisch.Checked = True
            If flgErgebnis = 1 Then
                Berechnen_Click
            End If
        Else
            Numerisch.Checked = False
            Tabelle.Hide
        End If
    Case "hilfe"
        Handbuch_click
    Case "drucken"
        Drucken_Click
    Case "ende"
        End
        
End Select
End Sub

Private Sub Drucken_Click()
    Dim BeginPage, EndPage
    Dim NumCopies, i As Integer
    ' Cancel auf True setzen
    CommonDialog4.CancelError = True
    On Error GoTo ErrHandler
    ' Dialogfeld "Drucken" anzeigen
    CommonDialog4.ShowPrinter
    ' Vom Benutzer ausgewählte Werte vom Dialogfeld
    ' abrufen
    BeginPage = CommonDialog4.FromPage
    EndPage = CommonDialog4.ToPage
    NumCopies = CommonDialog4.Copies
    If NumCopies < 1 Then
        NumCopies = 1
    End If
    For i = 1 To NumCopies
        PrintForm
    Next i
    CommonDialog4.CancelError = False
    Exit Sub
ErrHandler:
    Exit Sub
End Sub

Private Sub Grafik_click()
'***GRAPHIK***
If graf_anzeigen = 0 Then
    If Grafik.Checked = True Then
        Toolbar1.Buttons(7).Value = tbrUnpressed
        Grafik.Checked = False
        Graphik.Hide
        Exit Sub
    ElseIf Grafik.Checked = False Then
        Toolbar1.Buttons(7).Value = tbrPressed
        Grafik.Checked = True
    End If
Else
    graf_anzeigen = 0
End If

If flgErgebnis = 0 Then
    Exit Sub
End If

Dim X(1 To 49, 1 To 10) As Variant
    Dim rowLabelCount As Integer
    Dim columnLabelCount As Integer
    Dim rowCount As Integer
    Dim columnCount As Integer
    Set DataGrid = Graphik.MSChart1.DataGrid
    Graphik.MSChart1.chartType = VtChChartType2dStep
    
    colanzahl = Tabelle.matthias.Cols
    rowanzahl = Tabelle.matthias.Rows
    'MsgBox tabelle.matthias.Cols
    'MsgBox "colanzahl: " & colanzahl
    'msgBox "rowanzahl: " & rowanzahl
    Graphik.MSChart1.rowCount = rowanzahl - 1
    Graphik.MSChart1.columnCount = colanzahl - 1
    Max_i = rowanzahl - 1
    Max_j = colanzahl - 1
    'Graphik.MSChart1.RowLabel = ""
    i = 1
    j = 1
    Do While i < rowanzahl
        j = 1
        Tabelle.matthias.Row = i
        total = 0
        Do While j < colanzahl
        Tabelle.matthias.Col = j
        
        On Error Resume Next
        X(i, j) = Tabelle.matthias.TextMatrix(i, j)
        
        If X(i, j) = "" Then
            X(i, j) = 0
        End If
        total = total + X(i, j)
        X(i, j) = total
        Graphik.MSChart1.ChartData = X
                    
        j = j + 1
        Loop
        
        i = i + 1
    Loop
    Graphik.MSChart1.rowCount = rowanzahl - 1
    Graphik.MSChart1.columnCount = colanzahl - 1
    label_i = 1
    Graphik.MSChart1.Row = 1
    Do While label_i <= Graphik.MSChart1.rowCount
        Graphik.MSChart1.RowLabel = "Periode " & label_i
        On Error Resume Next
        Graphik.MSChart1.Row = Graphik.MSChart1.Row + 1
        label_i = label_i + 1
    Loop
    label_i = 1
    Graphik.MSChart1.Column = 1
    Do While label_i <= Graphik.MSChart1.columnCount
        Graphik.MSChart1.ColumnLabel = "A " & label_i
        On Error Resume Next
        Graphik.MSChart1.Column = Graphik.MSChart1.Column + 1
        label_i = label_i + 1
    Loop
Graphik.Show
End Sub
'// Hilfe anzeigen///////////////////////////////////////////////////////////////
Private Sub Handbuch_click()
       'CommonDialog1.HelpFile = "\Hilfe\MBP_HILFE.HLP"
       CommonDialog1.HelpFile = "hilfetxt.HLP"
       CommonDialog1.HelpCommand = cdlHelpContents
       CommonDialog1.ShowHelp
End Sub
'//Beenden des Programms /////////////////////////////////////////////////////////
Private Sub Beenden_Click()
    Unload Graphik
    Unload Tabelle
    Unload eingabe
    Unload Frage
    Unload frmAbout
    Unload Startselektion
    Unload Verzeichniswahl
    Unload MDIForm1
End Sub
'// Zielverzeichnis auswaehlen ////////////////////////////////////////////////////
Private Sub Zielverzeichnis_Click()
    Verzeichniswahl.Show vbModal
End Sub
'//Datei öffnen
Private Sub Öffnen_Click()
flgErgebnis = 0
CommonDialog1.CancelError = True
CommonDialog1.InitDir = DatenPfad   ' glob. Variable
CommonDialog1.Filter = "MBP-Dateien (*.mbp)|*.mbp|" & "Alle Dateien (*.*)|*.*|"
CommonDialog1.DialogTitle = "Datei öffnen"
On Error Resume Next
CommonDialog1.ShowOpen
If Err = 0 Then
    Open CommonDialog1.FileName For Input As #1
    For j = 1 To 7
        For i = 1 To 49
            Line Input #1, Wert
            tabelle_auftragsdaten.TextMatrix(i, j) = Wert
        Next i
    Next j
    
    For j = 1 To 3
        For i = 1 To 49
            Line Input #1, Wert
            MSFlexGrid1.TextMatrix(i, j) = Wert
        Next i
    Next j
    Close #1
    MDIForm1.Caption = Ueberschrift + " - [" + CommonDialog1.FileName + "]"
End If
End Sub
'// Neue Datei--Tabellen werden neu geladen//////////////////////////////////////////
Private Sub Neu_Click()
    flgErgebnis = 0
    tabelle_auftragsdaten.Clear
    MSFlexGrid1.Clear
    'Startselektion.Show
    'Laden der Eingabe-Form
    'Form_Load
    Initialize
End Sub
'// Aufruf zur wahl der Zielfunktion -- siehe Form Frage
Private Sub Zielfunktion_Click()
    Frage.Show vbModal
End Sub
'// Aufruf der Aboutbox
Private Sub Info_Click()
    frmAbout.Show vbModal
End Sub
'// Speichern der Daten aus den Tabellen in ein File mit Suffix mbp
Private Sub Speichern_Click()
CommonDialog1.CancelError = True
CommonDialog1.Filter = "MBP-Dateien (*.mbp)|*.mbp|" & "Alle Dateien (*.*)|*.*|"
CommonDialog1.DialogTitle = "Datei speichern"
On Error Resume Next
CommonDialog1.ShowSave
If Err = 0 Then
    Open CommonDialog1.FileName For Output As #1
    For j = 1 To 7
        For i = 1 To 49
            Print #1, tabelle_auftragsdaten.TextMatrix(i, j)
        Next i
    Next j
    
    For j = 1 To 3
        For i = 1 To 49
            Print #1, MSFlexGrid1.TextMatrix(i, j)
        Next i
    Next j
    Close #1
End If
End Sub
'// Solver laden um Datei einzulesen und zu berechnen
Sub solver_laden()
'// Wichtig um bei jedem Aufruf die Solver.bat neu zu schreiben
   Open ArbeitsVerz + "\Solver.bat" For Output As #1

'// Text für die Solver.bat Datei-Pfade muessen richtig sein
   Print #1, LpsolvePfad + "\lp_solve.exe <" + ArbeitsVerz + "\mbp.lp >" + ArbeitsVerz + "\lpsolve.out"
   Close #1
   
   
   ' Pif-Datei in Arbeitsverz. kopieren
   ' -> Dos-Namen verwenden !!!
  FileCopy App.Path + "\SOLVER.PIF", ArbeitsVerz + "\SOLVER.PIF"
  
  
'//Ausführen der Solver.bat
   ExecCmd (ArbeitsVerz + "\Solver.bat")
   Dim p_auslast(50)
   lp_read    ' Einlesen der Ergebnisse
   'periodenauslastung p_auslast()
End Sub

Private Sub periodenauslastung(p_auslastung())
  Dim z As Integer
  Dim s As Integer
  Dim periode As Integer
  z = 1
  s = 0
  z = Val(MSFlexGrid5.TextMatrix(0, 0))
  While (MSFlexGrid5.TextMatrix(z, s) <> "")
    periode = Val(MSFlexGrid5.TextMatrix(z, 1))
    p_auslastung(periode) = p_auslastung(periode) + Val(MSFlexGrid5.TextMatrix(z, 2))
    z = z + 1
  Wend
End Sub

Function Find_Periodenlaenge(X&)
Dim z&, s&, kleinste_periode&, groesste_periode&, tmp_beginn&, tmp_ende&, diff&
kleinste_periode = 50
groesste_periode = 0
For z = 1 To 49
    tmp_beginn = Val(tabelle_auftragsdaten.TextMatrix(z, 6))
    tmp_ende = Val(tabelle_auftragsdaten.TextMatrix(z, 7))
    If kleinste_periode > tmp_beginn Then
       kleinste_periode = tmp_beginn
    End If
    If groesste_periode < tmp_ende Then
       groesste_periode = tmp_ende
    End If
Next z

diff = groesste_periode - kleinste_periode
diff = diff
Find_Periodenlaenge = diff
End Function

Public Sub lp_read()
Tabelle.matthias.Clear
Tabelle.robert1.Clear

Dim pos, zaehler As Integer    'Positionszeiger
Dim zeile, x_var As String
Dim minuten, Spaltenbez1, Spaltenbez2, Format As String
Dim vorkomma, nachkomma, auftragsnr, periodennr, typnr, auftragZaehler, zusatz_masch As String
Dim WorkingDir As String

' Datei öffnen
Open ArbeitsVerz + "\lpsolve.out" For Input As #1
Line Input #1, zeile
If Err = 62 Or zeile = "This problem is infeasible" Then
    res = MsgBox("Das Problem ist nicht lösbar! Bitte überprüfen Sie die Eingabewerte.", vbCritical)
    flgErgebnis = 0
    Close #1
    Exit Sub
Else
    flgErgebnis = 1
End If
On Error Resume Next

Tabelle.matthias.MergeCells = 1
Tabelle.matthias.MergeRow(0) = True
Tabelle.robert1.MergeCells = 1
Tabelle.robert1.MergeRow(0) = True

auftragZaehler = 1
auftrag_highest = 1
periode_highest = 1
periodenZaehler = 0

Line Input #1, zeile  ' Zeile mit Zielfunktionswert

    Do Until EOF(1)
        Line Input #1, zeile
        x_var = Left(zeile, 6)
        If (Mid(x_var, 1, 1)) <> "Y" And Mid(x_var, 1, 1) <> "M" Then
            auftragsnr = Mid(x_var, 2, 2)
            auftragsnr = Val(auftragsnr)
            periodennr = Mid(x_var, 4, 2)
            periodennr = Val(periodennr)
            If auftragsnr > auftrag_highest Then
                auftrag_highest = auftragsnr
            End If
            If periodennr > periode_highest Then
                periode_highest = periodennr
            End If
            
        End If
            
    Loop
Close #1

Tabelle.matthias.Cols = (auftrag_highest * 2) + 1
Tabelle.matthias.Rows = periode_highest + 2
Tabelle.robert1.Cols = (periode_highest * 2) + 1
Tabelle.robert1.Rows = auftrag_highest + 2

p_h = 1
zaehlerp_h = 1
tmpAnr = 1

Do While zaehlerp_h <= auftrag_highest

    Tabelle.matthias.TextMatrix(0, p_h) = "Auftrag " & tmpAnr
    Tabelle.matthias.TextMatrix(0, p_h + 1) = "Auftrag " & tmpAnr
    Tabelle.matthias.TextMatrix(1, p_h) = "Typ 1"
    Tabelle.matthias.TextMatrix(1, p_h + 1) = "Typ 2"
    Tabelle.matthias.ColWidth(p_h) = 770
    Tabelle.matthias.ColWidth(p_h + 1) = 770
    zaehlerp_h = zaehlerp_h + 1
    tmpAnr = tmpAnr + 1
    
    p_h = p_h + 2
Loop


p_h = 1
zaehlerp_h = 1
tmpAnr = 1

Do While zaehlerp_h <= periode_highest

    Tabelle.robert1.TextMatrix(0, p_h) = "Periode " & tmpAnr
    Tabelle.robert1.TextMatrix(0, p_h + 1) = "Periode " & tmpAnr
    Tabelle.robert1.TextMatrix(1, p_h) = "Typ 1"
    Tabelle.robert1.TextMatrix(1, p_h + 1) = "Typ 2"
    Tabelle.robert1.ColWidth(p_h) = 770
    Tabelle.robert1.ColWidth(p_h + 1) = 770
    zaehlerp_h = zaehlerp_h + 1
    tmpAnr = tmpAnr + 1
    
    p_h = p_h + 2
Loop

Tabelle.matthias.TextMatrix(1, 0) = "Periodennummer"
Tabelle.robert1.TextMatrix(1, 0) = "Auftragsnummer"


Open ArbeitsVerz + "\lpsolve.out" For Input As #1
Line Input #1, zeile  ' Zeile mit Zielfunktionswert
Tabelle.Label1.Caption = zeile

'Tabelle.robert1.Cols = 2
'Tabelle.robert1.Col = 0
'Tabelle.robert1.Rows = 2
'Tabelle.robert1.Row = 1
'Tabelle.matthias.Cols = 4
'Tabelle.matthias.Col = 0
'Tabelle.matthias.Rows = 4
'Tabelle.matthias.Row = 2


zaehler = 0
'Robert2.Col = 1
'Robert2.Row = 1
' Datei sequentiell lesen
row_aktuell = 2
period_highest = 1
row_akt_matthias = 2


Do Until EOF(1)
  Line Input #1, zeile
  x_var = Left(zeile, 6)
  minuten = Mid$(zeile, 8)
  ' Punkte durch Kommata ersetzen
  If InStr(minuten, ".") <> 0 Then
    minuten = Replace(minuten, ".", ",")
  End If
  ' Seltsamerweise erst an dieser Stelle fehlerlos funktionsfähig !!!
  minuten = Trim$(minuten)
'MsgBox zeile + Chr(13) + minuten + Chr(13)
  ' Y-Werte = Auslastung pro Periode
  If (Mid(x_var, 1, 1)) <> "Y" And Mid(x_var, 1, 1) <> "M" Then
    'Tabelle.robert1.Col = 0
    Tabelle.matthias.Col = 0
            ' X-Werte = Zuteilung Auftrag-Periode
    auftragsnr = Mid(x_var, 2, 2)
    periodennr = Mid(x_var, 4, 2)
    typnr = Mid(x_var, 6, 1)
    
    auftragsnr = Val(auftragsnr)
    periodennr = Val(periodennr)
    typnr = Val(typnr)
    
    'MsgBox "anr: " & auftragsnr
    'MsgBox "pnr: " & periodennr
    If typnr = 1 Then
        Tabelle.matthias.TextMatrix(periodennr + 1, 0) = periodennr
        Tabelle.robert1.TextMatrix(auftragsnr + 1, 0) = auftragsnr
        If minuten = 0 Then
        
            Tabelle.matthias.TextMatrix(periodennr + 1, auftragsnr + (auftragsnr - 1)) = ""
            Tabelle.robert1.TextMatrix(auftragsnr + 1, periodennr + (periodennr - 1)) = ""
        Else
            Tabelle.matthias.TextMatrix(periodennr + 1, auftragsnr + (auftragsnr - 1)) = minuten
            Tabelle.robert1.TextMatrix(auftragsnr + 1, periodennr + (periodennr - 1)) = minuten
        End If
        
    Else
    
        Tabelle.matthias.TextMatrix(periodennr + 1, 0) = periodennr
        Tabelle.robert1.TextMatrix(auftragsnr + 1, 0) = auftragsnr
        If minuten = 0 Then
        
        Tabelle.matthias.TextMatrix(periodennr + 1, auftragsnr * 2) = ""
        Tabelle.robert1.TextMatrix(auftragsnr + 1, periodennr * 2) = ""
        
        Else
            Tabelle.matthias.TextMatrix(periodennr + 1, auftragsnr * 2) = minuten
            Tabelle.robert1.TextMatrix(auftragsnr + 1, periodennr * 2) = minuten
        End If
        
    End If
    
End If

    
Loop

'Tabelle.matthias.TextMatrix(1, 0) = "Maschinentyp"

Tabelle.robert1.ColWidth(0) = 1300
Tabelle.matthias.ColWidth(0) = 1300
p_h = 1
Do While p_h <= period_highest
    Tabelle.robert1.TextMatrix(0, p_h) = "Periode " & p_h
    Tabelle.robert1.ColWidth(p_h) = 770
    p_h = p_h + 1
Loop

Close #1
kapazitaet_frei = 100 - auslastung_schnitt
If Numerisch.Checked = True Then
    Tabelle.Show
End If
If Grafik.Checked = True Then
    graf_anzeigen = 1
    Grafik_click
Else
    graf_anzeigen = 0
End If

Tabelle.SetFocus

End Sub

Private Sub Numerisch_Click()
    If Numerisch.Checked = True Then
        Toolbar1.Buttons(8).Value = tbrUnpressed
        Numerisch.Checked = False
        Tabelle.Hide
    Else
        Toolbar1.Buttons(8).Value = tbrPressed
        Numerisch.Checked = True
        If flgErgebnis = 1 Then
            Tabelle.Show
        End If
    End If
End Sub



