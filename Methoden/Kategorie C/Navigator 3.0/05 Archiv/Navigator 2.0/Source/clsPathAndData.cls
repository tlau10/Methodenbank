VERSION 1.0 CLASS
BEGIN
  MultiUse = -1  'True
END
Attribute VB_Name = "clsPathAndData"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = True
Attribute VB_PredeclaredId = False
Attribute VB_Exposed = False
Option Explicit

Dim navigatINIDatei As String
Dim perlBatDatei As String

Dim strNavigat As String
Dim strPerlBatDatei As String

Dim strPerlPath As String
Dim strExecPath As String
Dim strDBPath As String

Dim strWorkingDir As String
Dim strEndResult As String
Dim dataVector()
Dim pos As Integer
Dim temp1, temp2 As String
Public Sub Initialize()
Dim DateiNr, Temp
DateiNr = FreeFile  ' neue Datei-Nr.

'Gebe Navigat.ini mit seinem Pfad zurueck
'ini Datei soll immer in dem gleichen Verzeichnis sein wie Navigat.exe ist
'so kann man mit App.Path immer Aktuellen Pfad von INI-Datei finden
navigatINIDatei = App.Path & "\Navigat.ini"

Open navigatINIDatei For Input As #DateiNr
   Do While Not EOF(DateiNr)
       Line Input #DateiNr, Temp
       temp1 = LTrim(Temp)
       temp2 = RTrim(temp1)
       pos = InStr(1, temp2, "=")
       If (pos <> 0) Then
       
          If (Left(temp2, (pos - 1)) = "perlpath") Then
              strPerlPath = Mid(temp2, (pos + 1))
          End If
        
          If (Left(temp2, (pos - 1)) = "execpath") Then
              strExecPath = Mid(temp2, (pos + 1))
          End If
          
          If (Left(temp2, (pos - 1)) = "execpath") Then
              strExecPath = Mid(temp2, (pos + 1))
          End If
          
          If (Left(temp2, (pos - 1)) = "workingdir") Then
              strWorkingDir = Mid(temp2, (pos + 1))
          End If
          
          If (Left(temp2, (pos - 1)) = "dbpath") Then
              strDBPath = Mid(temp2, (pos + 1))
          End If
          
          
       End If
     Loop
     ' Text1.Text = "perl" & strPerlPath & " Exec" & strExecPath
Close DateiNr

'Pfad von Perl Programm navigat.pl
'strNavigat = strExecPath & "\navigat.pl"
strNavigat = App.Path & "\navigat.pl"

strPerlBatDatei = strWorkingDir & "\navigat.bat"
strEndResult = strWorkingDir & "\end_navigat.out"
End Sub
Public Function getPerlBatDatei() As String
    getPerlBatDatei = strPerlBatDatei
End Function

Public Function getPerlPath() As String
    getPerlPath = strPerlPath
End Function

Public Function getNavigat() As String
    getNavigat = strNavigat
End Function
Public Function getOrtName() As String
    'getNavigat = strNavigat
End Function
Public Function getDBPath() As String
    getDBPath = strDBPath
End Function
Public Function getEndResultPath() As String
    getEndResultPath = strEndResult
End Function
Public Function getIniFile() As String
    getIniFile = navigatINIDatei
End Function









