' DLL f¸r die Abfrage, ob ein anderes Prog. noch arbeitet -> hier die Solver
Declare Function getmoduleusage% Lib "kernel" (ByVal hModule%)

' DLL f¸r die Hilfe-Aufrufe
'Declare Sub winhelp Lib "user" (ByVal hWnd%, ByVal dat$, ByVal cmd%, dwdata As Any)


Global AnzahlKnoten As String
Global KostTag As String
Global Const MaxSpalten = 150  ' Maximale Anzahl der Knoten (=Spalten)

Type Datensatz
  do_i As Integer
  do_j As Integer
  iTyp As Integer
  jTyp As Integer               '
  dnorm As Integer              ' normale T‰tigkeitsdauer
  dmin As Integer               ' verk¸rzte T‰tigkeitsdauer
  w As Integer                  ' Kostenzuwachs durch T‰tigkeitsverk¸rzung
End Type

Global U(MaxSpalten) As Datensatz

Global EXEPath  As String
Global DBPath   As String
Global XAPath   As String
Global OMPPath  As String
Global MOPSPath As String
Global LPSOLVEPath As String
Global WorkingDir As String

Global GRAPHPath As String
Global pathPIF As String * 63

Global VisuProg As String       ' Visualisierungsprogramm


Global Const MM = 99999         ' groﬂe Zahl


Global start As Variant
Global ende As Variant
Global posx As Integer
Global posy As Integer
Global Text As String
Global datopenflag As Integer
Global status As Integer
Global Fehler As Integer

Global dateiname As String
Global solver    As String
Global verztyp   As String

Global x As Integer

'hinzugef¸gt, im Moment ohne Funktion. Werden benˆtigt f¸r die
'prozeduren MPS_datei_schreiben und MOPS_Datei_lesen. WS96/97

'Global Const MaxSpalten = 25  ' Maximale Anzahl von Produktvarianten
Global AnzSpalten As Integer  ' Effektive Anzahl eingetragener Produkte
Global AnzZeilen As Integer   ' Effektive Anzahl eingetragener Varianten
Global Const MaxVars = 100    ' Maximale Anzahl von Variablen, die
                              ' vom Solver verarbeitet werden kˆnnen
                              ' (sollte auf Solver angepaﬂt werden kˆnnen !)
Global AnzVars As Integer     ' Effektive Anzahl errechneter Kombinationen

Global Anz_Dimensionen As Integer ' Unterscheidung, ob 1D oder 2D

Type TabellenEintrag          ' entspricht einem Wert im Eingabegitter
    Breite As Variant
    Anzahl As Variant
    Hˆhe   As Variant
End Type

'Global U(MaxSpalten) As TabellenEintrag   ' gespiegelte Eingabetabelle

Type VariantenEintrag                 ' entspricht einem Wert in der Zieltabelle
    Variable As Variant               ' Variablen-Name
    Produkt(MaxSpalten) As Variant    ' Tupel der Kombination
    Verschnitt As Variant             ' Effektiver Verschnitt je Werkst¸ck
    Anzahl As Variant                 ' Anzahl dieser Kombination in der Lˆsung
End Type

Global Varianten(MaxVars) As VariantenEintrag  ' gespiegelte Zieltabelle

Global Breite   As Variant ' Breite des Werkstoffes
Global Hˆhe     As Variant ' Breite des Werkstoffes
Global Laenge   As Variant
Global Zielwert As Variant ' Zielfunktionswert der Lˆsung

Sub Pif_Eintrag (pifdatei, batchdatei)
                                  
' -> Aufruf aus Form Verzeichnis u. Modul Berechnung


' Ist das WorkingDir unterschiedlich zum EXEPath, dann enth‰lt
' der WorkingDir i.d.R. keine Pif-Datei.
' Diese wird deshalb aus dem EXEPath in das WD kopiert.

If WorkingDir <> EXEPath Then  'Or Dir(WorkingDir + "" + pifdatei) = "" Then
   FileCopy EXEPath + "\" + pifdatei, WorkingDir + "\" + pifdatei
End If


' Ab Byte 37 steht der Programmdateiname der Batchdatei (meist Solver.Bat)
' Die Variable pathPIF muﬂ vom Typ STRING sein, sonst wird
' nicht richtig in die Datei geschrieben !

FNUM = FreeFile
Open WorkingDir + "\" + pifdatei For Binary As FNUM

'pathPIF = CStr(Trim$(verzeichnis.Text1.Text + "\" + batchdatei) + Chr$(0) + Space$(63 - Len(verzeichnis.Text1.Text) - 7))
pathPIF = CStr(Trim$(batchdatei) + Chr$(0) + Space$(63 - 7))
Put #FNUM, 37, pathPIF


' Ab Byte 102 steht das Anfangsverzeichnis der Batchdatei
'pathPIF = CStr(Trim$(text1.Text) + Chr$(0) + Space$(63 - Len(text1.Text) - 1))
pathPIF = CStr(Trim$(WorkingDir) + Chr$(0) + Space$(63 - Len(WorkingDir) - 1))
Put #FNUM, 102, pathPIF

Close #FNUM


End Sub

