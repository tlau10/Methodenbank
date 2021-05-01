Attribute VB_Name = "Module1"
Sub Read_ini(inidat, section)

' Default-Pfad-Einstellung

DBPath = "\"
XAPath = "\"
OMPPath = "\"
MOPSPath = "\"
LPSOLVEPath = "\"
STRADAPath = "\"
WorkingDir = "\"


' INI-Datei oeffnen und Pfade einlesen
FNUM = FreeFile
Open EXEPath + "\" + inidat For Input As FNUM
found = False

Do While Not EOF(FNUM)
Input #FNUM, temp$
If found = False Then
    If InStr(1, temp$, "[" + section + "]", 1) > 0 Then
        found = True
    End If
Else
    If InStr(1, temp$, "dbpath=", 1) > 0 Then
        DBPath = RTrim(Mid$(temp$, 8))
    End If
    If InStr(1, temp$, "xapath=", 1) > 0 Then
        XAPath = RTrim(Mid$(temp$, 8))
    End If
    If InStr(1, temp$, "mopspath=", 1) > 0 Then
        MOPSPath = RTrim(Mid$(temp$, 10))
    End If
    If InStr(1, temp$, "omppath=", 1) > 0 Then
        OMPPath = RTrim(Mid$(temp$, 9))
    End If
    If InStr(1, temp$, "lpsolvepath=", 1) > 0 Then
        LPSOLVEPath = RTrim(Mid$(temp$, 13))
    End If
    If InStr(1, temp$, "stradapath=", 1) > 0 Then
        STRADAPath = RTrim(Mid$(temp$, 12))
    End If

    If InStr(1, temp$, "WorkingDir=", 1) > 0 Then
        WorkingDir = RTrim(Mid$(temp$, 12))
    End If
End If
Loop
Close FNUM

If Right(DBPath, 1) = "\" Then
   DBPath = Left(DBPath, Len(DBPath) - 1)
End If
If Right(XAPath, 1) = "\" Then
   XAPath = Left(XAPath, Len(XAPath) - 1)
End If
If Right(MOPSPath, 1) = "\" Then
   MOPSPath = Left(MOPSPath, Len(MOPSPath) - 1)
End If
If Right(LPSOLVEPath, 1) = "\" Then
   LPSOLVEPath = Left(LPSOLVEPath, Len(LPSOLVEPath) - 1)
End If
If Right(OMPPath, 1) = "\" Then
   OMPPath = Left(OMPPath, Len(OMPPath) - 1)
End If
If Right(STRADAPath, 1) = "\" Then
   STRADAPath = Left(STRADAPath, Len(STRADAPath) - 1)
End If

If Right(WorkingDir, 1) = "\" Then
   WorkingDir = Left(WorkingDir, Len(WorkingDir) - 1)
End If
If WorkingDir = "" Then
   WorkingDir = EXEPath     'default
End If

End Sub

Sub Write_ini(inidat, section)
                             
FNUM = FreeFile
Open EXEPath + "\" + inidat For Output As FNUM
Print #FNUM, "[" + section + "]"
Print #FNUM, "dbpath=" & DBPath
Print #FNUM, "xapath=" & XAPath
Print #FNUM, "mopspath=" & MOPSPath
Print #FNUM, "lpsolvepath=" & LPSOLVEPath
Print #FNUM, "omppath=" & OMPPath
Print #FNUM, "stradapath=" & STRADAPath
Print #FNUM, "WorkingDir=" & WorkingDir
Close #FNUM

End Sub

