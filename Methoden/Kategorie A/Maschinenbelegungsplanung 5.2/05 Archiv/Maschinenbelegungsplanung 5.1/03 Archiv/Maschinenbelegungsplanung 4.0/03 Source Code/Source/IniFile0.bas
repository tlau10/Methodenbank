Attribute VB_Name = "Module2"
'16bit
'Declare Function GetPrivateProfileString Lib "Kernel" (ByVal lpApplicationName As String, ByVal lpKeyName As String, ByVal lpDefault As String, ByVal lpReturnedString As String, ByVal nSize As Integer, ByVal lpFileName As String) As Integer

'32bit
Declare Function GetPrivateProfileString Lib "kernel32" Alias "GetPrivateProfileStringA" (ByVal lpApplicationName As String, ByVal lpKeyName As Any, ByVal lpDefault As String, ByVal lpReturnedString As String, ByVal nSize As Long, ByVal lpFileName As String) As Long

'16bit
Declare Function GetPrivateProfileInt Lib "Kernel" (ByVal lpApplicationName As String, ByVal lpKeyName As String, ByVal nDefault As Integer, ByVal lpFileName As String) As Integer

'16bit
Declare Function WritePrivateProfileString Lib "Kernel" (ByVal lpApplicationName As String, ByVal lpKeyName As String, ByVal lpString As String, ByVal lplFileName As String) As Integer

Function ReadIniInteger(IniFile As String, Section As String, Key As String) As Integer

ReadIniInteger = GetPrivateProfileInt(Section, Key, 0, IniFile)

End Function

Function ReadIniString(IniFile As String, Section As String, Key As String) As String

Dim Wert As String * 100     ' Rückgabewert
Dim n As Integer             ' Wertlänge


n = GetPrivateProfileString(Section, Key, "", Wert, Len(Wert), IniFile)

If n = 0 Then
   ReadIniString = ""
Else
   ReadIniString = Left$(Wert, n)
End If

End Function

Function WriteIniString(IniFile As String, Section As String, Key As String, Wert As String) As Integer

WriteIniString = WritePrivateProfileString(Section, Key, Wert, IniFile)


End Function

