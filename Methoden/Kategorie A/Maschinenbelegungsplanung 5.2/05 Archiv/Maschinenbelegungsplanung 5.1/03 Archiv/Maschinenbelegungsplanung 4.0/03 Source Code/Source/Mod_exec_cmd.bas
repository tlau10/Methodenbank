Attribute VB_Name = "Module1"
Global Const Ueberschrift = "Maschinenbelegungsplanung 2002"

'-- Globale Varialen
Global grafik_anz, tabelle_anz
Global anz_masch As Variant
Global anz_perioden As Variant
Global anz_auftr As Variant
Global flgErgebnis As Boolean
Global graf_anzeigen As Integer
Global ExecPfad, DatenPfad, LpsolvePfad, ArbeitsVerz As String
 
Option Explicit
      Declare Sub Sleep Lib "kernel32" (ByVal dwMilliseconds As Long)
      Private Type STARTUPINFO
        

         cb As Long
         lpReserved As String
         lpDesktop As String
         lpTitle As String
         dwX As Long
         dwY As Long
         dwXSize As Long
         dwYSize As Long
         dwXCountChars As Long
         dwYCountChars As Long
         dwFillAttribute As Long
         dwFlags As Long
         wShowWindow As Integer
         cbReserved2 As Integer
         lpReserved2 As Long
         hStdInput As Long
         hStdOutput As Long
         hStdError As Long
      End Type

      Private Type PROCESS_INFORMATION
         hProcess As Long
         hThread As Long
         dwProcessID As Long
         dwThreadID As Long
      End Type

      Private Declare Function WaitForSingleObject Lib "kernel32" (ByVal _
         hHandle As Long, ByVal dwMilliseconds As Long) As Long

      Private Declare Function CreateProcessA Lib "kernel32" (ByVal _
         lpApplicationName As Long, ByVal lpCommandLine As String, ByVal _
         lpProcessAttributes As Long, ByVal lpThreadAttributes As Long, _
         ByVal bInheritHandles As Long, ByVal dwCreationFlags As Long, _
         ByVal lpEnvironment As Long, ByVal lpCurrentDirectory As Long, _
         lpStartupInfo As STARTUPINFO, lpProcessInformation As _
         PROCESS_INFORMATION) As Long

      Private Declare Function CloseHandle Lib "kernel32" (ByVal _
         hObject As Long) As Long

      Private Const NORMAL_PRIORITY_CLASS = &H20&
      Private Const INFINITE = -1&

      Public Sub ExecCmd(cmdline$)
         Dim proc As PROCESS_INFORMATION
         Dim start As STARTUPINFO
         Dim ret As Long
         ' Initialize the STARTUPINFO structure:
         start.cb = Len(start)
         ' Start the shelled application:
         ret = CreateProcessA(0&, cmdline$, 0&, 0&, 1&, _
            NORMAL_PRIORITY_CLASS, 0&, 0&, start, proc)
         ' Wait for the shelled application to finish:
         ret = WaitForSingleObject(proc.hProcess, INFINITE)
         ret = CloseHandle(proc.hProcess)
      End Sub

