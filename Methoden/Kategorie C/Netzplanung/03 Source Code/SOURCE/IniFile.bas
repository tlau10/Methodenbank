Option Explicit

Function ReadString (IniFile, Section, Ident) As String

Dim found As Integer
Dim FNum As Integer
Dim temp As String
Dim Value As String

' INI-Datei oeffnen
FNum = FreeFile
Open IniFile For Input As FNum
found = False


Do While Not EOF(FNum)
Input #FNum, temp$
If found = False Then
    If InStr(1, temp$, "[" + Section + "]", 1) > 0 Then
        found = True
    End If
Else
    If InStr(1, temp$, Ident + "=", 1) > 0 Then
        Value = RTrim(Mid$(temp$, Len(Ident) + 2))
    End If
End If
Loop

Close FNum

ReadString = Value


End Function

Sub WriteString ()

' ..kommt noch

End Sub

