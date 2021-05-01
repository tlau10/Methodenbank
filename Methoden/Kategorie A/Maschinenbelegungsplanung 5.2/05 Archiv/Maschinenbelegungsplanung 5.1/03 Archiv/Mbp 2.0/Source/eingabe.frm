VERSION 5.00
Object = "{5E9E78A0-531B-11CF-91F6-C2863C385E30}#1.0#0"; "MSFLXGRD.OCX"
Object = "{F9043C88-F6F2-101A-A3C9-08002B2F49FB}#1.1#0"; "COMDLG32.OCX"
Object = "{6B7E6392-850A-101B-AFC0-4210102A8DA7}#1.1#0"; "COMCTL32.OCX"
Begin VB.Form eingabe 
   BorderStyle     =   0  'Kein
   Caption         =   "Eingabefenster"
   ClientHeight    =   8310
   ClientLeft      =   0
   ClientTop       =   570
   ClientWidth     =   11880
   Icon            =   "eingabe.frx":0000
   KeyPreview      =   -1  'True
   MaxButton       =   0   'False
   MDIChild        =   -1  'True
   MinButton       =   0   'False
   ScaleHeight     =   8310
   ScaleWidth      =   11880
   ShowInTaskbar   =   0   'False
   Begin ComctlLib.Toolbar Toolbar1 
      Align           =   1  'Oben ausrichten
      Height          =   420
      Left            =   0
      TabIndex        =   0
      Top             =   0
      Width           =   11880
      _ExtentX        =   20955
      _ExtentY        =   741
      Appearance      =   1
      ImageList       =   "ImageList1"
      _Version        =   327680
      BeginProperty Buttons {0713E452-850A-101B-AFC0-4210102A8DA7} 
         NumButtons      =   15
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
            Key             =   ""
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
            Key             =   ""
            Object.Tag             =   ""
            Style           =   3
            MixedState      =   -1  'True
         EndProperty
         BeginProperty Button7 {0713F354-850A-101B-AFC0-4210102A8DA7} 
            Key             =   "grafik"
            Object.ToolTipText     =   "Graphische Ergebnisausgabe"
            Object.Tag             =   ""
            ImageIndex      =   1
            Style           =   1
            Value           =   1
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
            Key             =   ""
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
            Key             =   ""
            Object.Tag             =   ""
            Style           =   3
         EndProperty
         BeginProperty Button13 {0713F354-850A-101B-AFC0-4210102A8DA7} 
            Key             =   "drucken"
            Object.ToolTipText     =   "Seite drucken"
            Object.Tag             =   ""
            ImageIndex      =   6
         EndProperty
         BeginProperty Button14 {0713F354-850A-101B-AFC0-4210102A8DA7} 
            Key             =   ""
            Object.Tag             =   ""
            Style           =   3
         EndProperty
         BeginProperty Button15 {0713F354-850A-101B-AFC0-4210102A8DA7} 
            Key             =   "hilfe"
            Object.ToolTipText     =   "Hilfedatei öffnen"
            Object.Tag             =   ""
            ImageIndex      =   2
         EndProperty
      EndProperty
      BorderStyle     =   1
      MouseIcon       =   "eingabe.frx":0442
      OLEDropMode     =   1
   End
   Begin VB.TextBox check_1 
      Height          =   285
      Left            =   6000
      TabIndex        =   5
      Text            =   "Text1"
      Top             =   480
      Visible         =   0   'False
      Width           =   735
   End
   Begin VB.TextBox pfad 
      Height          =   285
      Left            =   3600
      TabIndex        =   4
      Text            =   "c:\temp"
      Top             =   480
      Visible         =   0   'False
      Width           =   1095
   End
   Begin VB.Frame Frame1 
      Caption         =   "Auftragsdaten"
      BeginProperty Font 
         Name            =   "MS Sans Serif"
         Size            =   9.75
         Charset         =   0
         Weight          =   400
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Height          =   4455
      Left            =   0
      TabIndex        =   1
      Top             =   840
      Width           =   8055
      Begin MSComDlg.CommonDialog CommonDialog4 
         Left            =   6240
         Top             =   720
         _ExtentX        =   847
         _ExtentY        =   847
         _Version        =   327680
      End
      Begin VB.Timer Timer1 
         Left            =   8160
         Top             =   240
      End
      Begin MSFlexGridLib.MSFlexGrid tabelle_auftragsdaten 
         Height          =   4125
         Left            =   120
         TabIndex        =   2
         Top             =   240
         Width           =   7815
         _ExtentX        =   13785
         _ExtentY        =   7276
         _Version        =   327680
         Rows            =   50
         Cols            =   6
         FixedCols       =   0
         ScrollBars      =   2
      End
   End
   Begin MSComDlg.CommonDialog CommonDialog1 
      Left            =   5520
      Top             =   360
      _ExtentX        =   847
      _ExtentY        =   847
      _Version        =   327680
   End
   Begin MSFlexGridLib.MSFlexGrid tabelle_maschinendaten 
      Height          =   2415
      Left            =   9360
      TabIndex        =   3
      Top             =   840
      Visible         =   0   'False
      Width           =   2175
      _ExtentX        =   3836
      _ExtentY        =   4260
      _Version        =   327680
      Rows            =   50
      Cols            =   3
      FixedCols       =   2
      HighLight       =   0
      ScrollBars      =   0
      Appearance      =   0
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
      _Version        =   327680
      BeginProperty Images {0713E8C2-850A-101B-AFC0-4210102A8DA7} 
         NumListImages   =   10
         BeginProperty ListImage1 {0713E8C3-850A-101B-AFC0-4210102A8DA7} 
            Picture         =   "eingabe.frx":045E
            Key             =   "graphik"
            Object.Tag             =   "/icons/Graph07.ico"
         EndProperty
         BeginProperty ListImage2 {0713E8C3-850A-101B-AFC0-4210102A8DA7} 
            Picture         =   "eingabe.frx":0778
            Key             =   "hilfe"
         EndProperty
         BeginProperty ListImage3 {0713E8C3-850A-101B-AFC0-4210102A8DA7} 
            Picture         =   "eingabe.frx":088A
            Key             =   "numerisch"
         EndProperty
         BeginProperty ListImage4 {0713E8C3-850A-101B-AFC0-4210102A8DA7} 
            Picture         =   "eingabe.frx":0BA4
            Key             =   "neu"
         EndProperty
         BeginProperty ListImage5 {0713E8C3-850A-101B-AFC0-4210102A8DA7} 
            Picture         =   "eingabe.frx":0CB6
            Key             =   "oeffnen"
         EndProperty
         BeginProperty ListImage6 {0713E8C3-850A-101B-AFC0-4210102A8DA7} 
            Picture         =   "eingabe.frx":0DC8
            Key             =   "drucken"
         EndProperty
         BeginProperty ListImage7 {0713E8C3-850A-101B-AFC0-4210102A8DA7} 
            Picture         =   "eingabe.frx":0EDA
            Key             =   "speichern"
         EndProperty
         BeginProperty ListImage8 {0713E8C3-850A-101B-AFC0-4210102A8DA7} 
            Picture         =   "eingabe.frx":0FEC
            Key             =   "zielfunktion"
         EndProperty
         BeginProperty ListImage9 {0713E8C3-850A-101B-AFC0-4210102A8DA7} 
            Picture         =   "eingabe.frx":1306
            Key             =   "berechnung"
         EndProperty
         BeginProperty ListImage10 {0713E8C3-850A-101B-AFC0-4210102A8DA7} 
            Picture         =   "eingabe.frx":1620
            Key             =   "zielverzeichnis"
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
         Checked         =   -1  'True
      End
   End
   Begin VB.Menu Einstellungen 
      Caption         =   "Einstellungen"
      Begin VB.Menu Zielverzeichnis 
         Caption         =   "Zielverzeichnis"
      End
      Begin VB.Menu Zielfunktion 
         Caption         =   "Zielfunktion"
         Enabled         =   0   'False
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

Private Sub Berechnen_Click()


'in der Funktion werden die Daten für den LP-Solver in eine
'externe Datei geschrieben

'Deklaration der Datentypen
Dim zielfkt$, z&, s&, i&, zaehler_zeile&, zaehler_spalte&
Dim anz_var_zeile&, beginn&, Ende&, s_var&, z_var&, max_auftragsnr&
Dim perioden_laenge&, merke_anz_RestStr, auf_nr&, faktor_y&
Dim RestStr$, RestStr_2$

Open pfad.Text + "\lpsolve.lp" For Output As #1

'Wahl welche Restriktion genommen werden muss

If check_1.Text = "2" Then
   zielfkt = "min: Y;"
Else
   zielfkt = "min:"
End If

Print #1, zielfkt
Print #1,
zaehler_zeile = 0
zaehler_spalte = 0
s_var = 1
z_var = 1
merke_anz_RestStr = 0
zaehler_rest = 0

'Restriktionen
If check_1.Text = "2" Then
   'Restriktionen für Aufträge
   For z = 1 To 49
       If tabelle_auftragsdaten.TextMatrix(z, 1) <> "" Then
          zaehler_rest = zaehler_rest + 1
          RestStr = "R" & z & ": "
          beginn = tabelle_auftragsdaten.TextMatrix(z, 4)
          Ende = tabelle_auftragsdaten.TextMatrix(z, 5)
          anz_var_zeile = Ende - beginn
          s_var = beginn
       Else
          Exit For
       End If
       For s = 1 To anz_var_zeile + 1
           If s > 1 Then
              RestStr = RestStr & "+ "
           End If
           If z > 9 Then
                RestStr = RestStr & "1x" & z & zaehler_spalte & s_var
           Else
                RestStr = RestStr & "1x" & zaehler_zeile & z & zaehler_spalte & s_var
           End If
           If s_var = 9 Then
              zaehler_spalte = zaehler_spalte + 1
              s_var = 0
           Else
              s_var = s_var + 1
           End If
       Next s
       RestStr = RestStr & " =" + tabelle_auftragsdaten.TextMatrix(z, 3) & ";"
       Print #1, RestStr
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
   zaehler_zeile = 0
   zaehler_spalte = 0
   s_var = 1
   z_var = 1
   perioden_laenge = Find_Periodenlaenge(i)
   
   For z = 1 To perioden_laenge
       merke_anz_RestStr = merke_anz_RestStr + 1
       RestStr = "R" & merke_anz_RestStr & ": "
       For s = 1 To max_auftragsnr
           beginn = Val(tabelle_auftragsdaten.TextMatrix(s, 4))
           Ende = Val(tabelle_auftragsdaten.TextMatrix(s, 5))
           If beginn <= z And Ende >= z Then
              If i > 0 Then
                 RestStr = RestStr & "+"
              End If
              If s_var = -1 Then
                s_var = s_var + 1
              End If
              RestStr = RestStr + "1x" & zaehler_spalte & s_var & zaehler_zeile & z_var
              i = 1
           End If
           If s_var = 9 Then
              zaehler_spalte = zaehler_spalte + 1
              s_var = -1
           ElseIf s_var <> -1 Then
              s_var = s_var + 1
           End If
       Next s
       RestStr = RestStr & "- " & tabelle_maschinendaten.TextMatrix(z, 2) & "Y <= 0;"
       i = 0
       Print #1, RestStr
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
   zaehler_zeile = 0
   zaehler_spalte = 0
   s_var = 1
   z_var = 1
   perioden_laenge = Find_Periodenlaenge(i)
   
   For z = 1 To perioden_laenge
       merke_anz_RestStr = merke_anz_RestStr + 1
       RestStr = "R" & merke_anz_RestStr & ": "
       For s = 1 To max_auftragsnr
           beginn = Val(tabelle_auftragsdaten.TextMatrix(s, 4))
           Ende = Val(tabelle_auftragsdaten.TextMatrix(s, 5))
           If beginn <= z And Ende >= z Then
              If i > 0 Then
                 RestStr = RestStr & "+"
              End If
              If s_var = -1 Then
                 s_var = 0
              End If
              RestStr = RestStr + "1x" & zaehler_spalte & s_var & zaehler_zeile & z_var
              i = 1
           End If
           If s_var = 9 Then
              zaehler_spalte = zaehler_spalte + 1
              s_var = -1
           ElseIf s_var <> -1 Then
              s_var = s_var + 1
           End If
       Next s
       RestStr = RestStr & "<= " & Val(tabelle_maschinendaten.TextMatrix(z, 2)) * 60 & ";"
       Print #1, RestStr
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
   
Close #1

solver_laden

' UUUUUUUUUUUUUUUUUUUUUUUWWWWWWWWWWWWWWWWWWWWWWWWEEEEEEEEEEEEEEEEEEEEEEEE


' hier muessen jetzt die daten aus den tabellen als lineares
' modell generiert und zum solver geschickt werden


' fuer den zugriff auf die eingegebenen daten:

' auftragsdatentabelle : objekt tabelle_auftragsdaten 4 (!) Spalten und 100 zeilem
' maschinendatentabelle: objekt tabelle_maschinendaten 3 Spalten und 100 Zeilen

' check_1 hat den wert 1  wenn die option tatsächliche maschinen gewählt wurde
' zugriff darauf mit check_1.text

' check_1 hat den wert 2 minimierung der vorhandenen masch.gewählt ist

' der pfad der angibt wo die datei zum schreiben und lesen liegen steht im objekt pfad
' der zugriff darauf also mit pfad.text

' der gewählte solver ist im objekt solver_choice.  zugriff darauf mit solver_choice.text
' ist der 1. solver gewaehlt dann ist solver_choice.text="Option1"
' beim 2.Solver "Option2" beim 3. Solver "Option3"

' UUUUUUUUUUUUUUUUUUUUUWWWWWWWWWWWWWWWWWWWWWEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE

End Sub


Private Sub Form_Load()

Toolbar1.ImageList = ImageList1

tabelle_auftragsdaten.TextMatrix(0, 0) = "" ' leere spalte 1 pixel breit
tabelle_auftragsdaten.TextMatrix(0, 1) = "Auftragsnummer"
tabelle_auftragsdaten.TextMatrix(0, 2) = "Bezeichnung"
tabelle_auftragsdaten.TextMatrix(0, 3) = "Dauer"
tabelle_auftragsdaten.TextMatrix(0, 4) = "Beginn"
tabelle_auftragsdaten.TextMatrix(0, 5) = "Ende"
tabelle_auftragsdaten.ColWidth(0) = 1 ' leere spalte 1 pixel breit
tabelle_auftragsdaten.ColWidth(1) = 1800
tabelle_auftragsdaten.ColWidth(2) = 2900
tabelle_auftragsdaten.ColWidth(3) = 800
tabelle_auftragsdaten.ColWidth(4) = 800
tabelle_auftragsdaten.ColWidth(5) = 800
tabelle_auftragsdaten.Width = 7500

tabelle_maschinendaten.ColWidth(0) = 1
tabelle_maschinendaten.TextMatrix(0, 0) = "" 'leere spalte 1 pixel breit
tabelle_maschinendaten.TextMatrix(0, 1) = "Periode"
tabelle_maschinendaten.TextMatrix(0, 2) = "Anzahl"

For k = 1 To 49

tabelle_maschinendaten.TextMatrix(k, 1) = k
tabelle_maschinendaten.TextMatrix(k, 2) = 1

Next k


check_1.Visible = False
check_1.Text = "2"
grafik_anz = 1
tabelle_anz = 1


End Sub



Private Sub tabelle_auftragsdaten_EnterCell()

tabelle_auftragsdaten.CellBackColor = &HFFFF00  ' aktive zelle wird blau

End Sub



'Sub zum Abfangen von Tastatureingaben in der Tabelle Auftragsdaten
Private Sub tabelle_auftragsdaten_KeyPress(KeyAscii As Integer)

'MsgBox KeyAscii

'Bei "Zeichenlöschen-taste" wird ein Zeichen aus dem Inhalt der Zelle gelöscht
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
Else
    MyChar = Chr(KeyAscii)
    tabelle_auftragsdaten.Text = tabelle_auftragsdaten.Text & MyChar
End If
End Sub

Private Sub tabelle_auftragsdaten_LeaveCell()

tabelle_auftragsdaten.CellBackColor = &HFFFFFF ' verlassene zelle wieder weiss

End Sub

Private Sub Hilfstext_Change()

tabelle_auftragsdaten.Text = Hilfstext.Text  ' aenderungen in text3 sofort in tabelle kopieren

End Sub

Private Sub tabelle_auftragsdaten_MouseUp(Button As Integer, Shift As Integer, X As Single, Y As Single)
If tabelle_auftragsdaten.Col <> 3 Then
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
            If erg_flag = 1 Then
                graf_anzeigen = 1
                Berechnen_Click
            End If
        Else
            Grafik.Checked = False
            Graphik.Hide
        End If
        '
    Case "numerisch"
        
        If Button.Value = 1 Then
            Numerisch.Checked = True
            If erg_flag = 1 Then
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

If erg_flag = 0 Then
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
Private Sub Handbuch_click()
       CommonDialog1.HelpFile = "C:/Or-Projekt/Hilfetxt.hlp"
       CommonDialog1.HelpCommand = cdlHelpContents
       CommonDialog1.ShowHelp
End Sub
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
Private Sub Zielverzeichnis_Click()

    Verzeichniswahl.Show vbModal

End Sub


Private Sub Öffnen_Click()

erg_flag = 0
CommonDialog1.CancelError = True
CommonDialog1.DialogTitle = "Datei öffnen"
On Error Resume Next
CommonDialog1.ShowOpen
If Err = 0 Then

    Open CommonDialog1.filename For Input As #1
    
    For j = 1 To 5
    For i = 0 To 49
    
    Line Input #1, wert
    
    tabelle_auftragsdaten.TextMatrix(i, j) = wert
    
    Next i
    
    Next j
    
    Close #1
    End If
    
End Sub


Private Sub Neu_Click()

erg_flag = 0
tabelle_auftragsdaten.Clear

' einmalig
' Tabellenbeschriftung beider tabellen

tabelle_auftragsdaten.TextMatrix(0, 0) = "" ' leere spalte 1 pixel breit
tabelle_auftragsdaten.TextMatrix(0, 1) = "Auftragsnummer"
tabelle_auftragsdaten.TextMatrix(0, 2) = "Bezeichnung"
tabelle_auftragsdaten.TextMatrix(0, 3) = "Dauer"
tabelle_auftragsdaten.TextMatrix(0, 4) = "Beginn"
tabelle_auftragsdaten.TextMatrix(0, 5) = "Ende"

End Sub

Private Sub Zielfunktion_Click()
    Frage.Show 'vbModal

End Sub

Private Sub Info_Click()
    frmAbout.Show vbModal

End Sub

Private Sub Speichern_Click()

CommonDialog1.CancelError = True
CommonDialog1.DialogTitle = "Datei speichern"
On Error Resume Next
CommonDialog1.ShowSave
If Err = 0 Then

    Open CommonDialog1.filename For Output As #1
    
    For j = 1 To 5
        For i = 0 To 50
            Print #1, tabelle_auftragsdaten.TextMatrix(i, j)
    
        Next i
    Next j
    
    Close #1

End If
End Sub

Sub solver_laden()
   Dim Pfad_solver$, Arbeitsverz$
   
   Open pfad.Text + "\lpsolve.bat" For Output As #1
   Print #1, "lp_solve.exe" + " < "; pfad.Text + "\lpsolve.lp" + " > " + pfad.Text + "\lpsolve.out"
   Close #1

   ExecCmd (pfad.Text + "\lpsolve.bat")
   
   
    
   Dim p_auslast(50)
  
    lp_read
  
    'periodenauslastung p_auslast()
   
   
End Sub

Private Sub periodenauslastung(p_auslastung())
  Dim z As Integer
  Dim s As Integer
  Dim Periode As Integer
  
  z = 1
  s = 0
 
  z = Val(MSFlexGrid5.TextMatrix(0, 0))
  While (MSFlexGrid5.TextMatrix(z, s) <> "")
    Periode = Val(MSFlexGrid5.TextMatrix(z, 1))
    p_auslastung(Periode) = p_auslastung(Periode) + Val(MSFlexGrid5.TextMatrix(z, 2))
    z = z + 1
  Wend
  
  
End Sub


Function Find_Periodenlaenge(X&)
Dim z&, s&, kleinste_periode&, groesste_periode&, tmp_beginn&, tmp_ende&, diff&


kleinste_periode = 50
groesste_periode = 0

For z = 1 To 49
    tmp_beginn = Val(tabelle_auftragsdaten.TextMatrix(z, 4))
    tmp_ende = Val(tabelle_auftragsdaten.TextMatrix(z, 5))
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

Dim pos, zaehler As Integer    'Positionszeiger
Dim zeile, x_var As String
Dim minuten, Spaltenbez1, Spaltenbez2, Format As String
Dim vorkomma, nachkomma, auftragsnr, periodennr, zusatz_masch As String
Dim WorkingDir As String

                    ' Datei öffnen
Open pfad.Text + "\lpsolve.out" For Input As #1
On Error Resume Next
Line Input #1, zeile  ' Zeile mit Zielfunktionswert

If Err = 62 Or zeile = "This problem is infeasible" Then
    res = MsgBox("Das Problem ist nicht lösbar! Bitte überprüfen Sie die Eingabewerte.", vbCritical)
    erg_flag = 0
    Close #1
    Exit Sub
Else
    erg_flag = 1
End If
Tabelle.robert1.Cols = 2
Tabelle.robert1.Col = 0
Tabelle.robert1.Rows = 2
Tabelle.robert1.Row = 1
Tabelle.matthias.Cols = 2
Tabelle.matthias.Col = 0
Tabelle.matthias.Rows = 2
Tabelle.matthias.Row = 1

zaehler = 0

'Robert2.Col = 1
'Robert2.Row = 1
                    ' Datei sequentiell lesen
row_aktuell = 1
period_highest = 1
row_akt_matthias = 1
auftrag_highest = 1
Do Until EOF(1)
  Line Input #1, zeile
  x_var = Left(zeile, 5)
  minuten = LTrim$(Right$(zeile, 20))
  pos = InStr(minuten, ".")
  If pos = 0 Then
    minuten = minuten
  Else
    'nachkomma = Mid(minuten, pos + 1, 2)   ' 2 Nachkomma + 2 Vorkomma
    vorkomma = Mid(minuten, pos - 2, 2)
    minuten = vorkomma
  End If
                ' Y-Werte = Auslastung pro Periode
  If (Mid(x_var, 1, 1)) <> "Y" Then
    Tabelle.robert1.Col = 0
    Tabelle.matthias.Col = 0
            ' X-Werte = Zuteilung Auftrag-Periode
    auftragsnr = Mid(x_var, 2, 2)
    periodennr = Mid(x_var, 4, 2)
    auftragsnr = Val(auftragsnr)
    periodennr = Val(periodennr)
    'MsgBox "anr: " & auftragsnr
    'MsgBox "pnr: " & periodennr
    
    If periodennr > period_highest Then
        Tabelle.robert1.Cols = periodennr + 1
        period_highest = periodennr
    End If
    If auftragsnr > auftrag_highest Then
        Tabelle.matthias.Cols = auftragsnr + 1
        auftrag_highest = auftragsnr
    End If
    
    
    If auftragsnr <> row_aktuell Then
        If auftragsnr >= Tabelle.robert1.Rows Then
            Tabelle.robert1.Rows = auftragsnr + 1
        End If
        Tabelle.robert1.Row = auftragsnr
        row_aktuell = auftragsnr
    End If
    If periodennr > row_akt_matthias Then
        Tabelle.matthias.Rows = periodennr + 1
        row_akt_matthias = periodennr
        'MsgBox "rows:" & tabelle.matthias.Rows
    End If
    'MsgBox "row:" & tabelle.robert1.Row
    'MsgBox "col:" & tabelle.robert1.Col
    'Tabelle.robert1.Row = auftragsnr
    Tabelle.matthias.Row = periodennr

    Tabelle.robert1.TextMatrix(Tabelle.robert1.Row, 0) = auftragsnr
    Tabelle.robert1.TextMatrix(Tabelle.robert1.Row, periodennr) = minuten
    'row_aktuell = auftragsnr
    Tabelle.matthias.TextMatrix(Tabelle.matthias.Row, 0) = periodennr
    Tabelle.matthias.TextMatrix(Tabelle.matthias.Row, auftragsnr) = minuten
    
  End If
Loop

Tabelle.robert1.TextMatrix(0, 0) = "Auftragsnummer"
Tabelle.matthias.TextMatrix(0, 0) = "Periodennummer"
Tabelle.robert1.ColWidth(0) = 1300
Tabelle.matthias.ColWidth(0) = 1300

p_h = 1
Do While p_h <= period_highest
    Tabelle.robert1.TextMatrix(0, p_h) = "Periode " & p_h
    Tabelle.robert1.ColWidth(p_h) = 770
    p_h = p_h + 1
Loop

p_h = 1

Do While p_h <= auftrag_highest
    Tabelle.matthias.TextMatrix(0, p_h) = "Auftrag " & p_h
    Tabelle.matthias.ColWidth(p_h) = 770
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

End Sub

Private Sub Numerisch_Click()

    If Numerisch.Checked = True Then
        Toolbar1.Buttons(8).Value = tbrUnpressed
        Numerisch.Checked = False
        Tabelle.Hide
    Else
        Toolbar1.Buttons(8).Value = tbrPressed
        Numerisch.Checked = True
        If erg_flag = 1 Then
            Tabelle.Show
        End If
    End If
  
        
End Sub



