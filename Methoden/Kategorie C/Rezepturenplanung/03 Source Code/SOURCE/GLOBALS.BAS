�  __	 
��v   x Z � �� 	� f P�� �� �� h� � � O�@/;�� `�%                  v  w Gridlist� getmoduleusageq hModule� winhelp! hWnd dat� cmd dwdata�  	maxzeilen�  A_Satz� AArtikel�  APreis� AWasser AFett} AFE  ABEFFE� ABEn D4� D5R D6� D7 D8E Z_Satz� ZArtikelY ZPreis ZWasser� ZFett\ ZFEj ZBEFFEg ZBE0 T4� T5� T6G T7� T8� A� z EXEPath DBPathe XAPath� OMPPath� MOPSPath� NNPath� pathPIF{ rueckschrittq Geladen6 neuy xN aZeilen= zZeilen� WindowTitle 	dateiname� solver� verztyp� Sorten� Sorte� Preis� MinWert� MaxWert� Sorten_Satz�
 Zutat� 	Ziel_Satz� Zielwert� INI_FileName1 INI_Section, Wasser� Fett BEFFE* BE� FE Read_DataFile0 Write_DataFile� Write_DataArray� i� �ffnen� 
StdDialog1\ 
DefaultExt� InitDir� Filter Action` pfaddateiname� Filename� l�nge� mousepointeru dummy� AName   AMenge1 AMenge2� kosten� D1) D2� D3� ZName$ ZMenge1 ZMenge2@ erl�se8 T1G T2� T3U DataFileNamek Read_DatabaseFilex Write_DatabaseFile 	Read_File� 
Write_File� DatabaseReadOK� DatabaseFileName� FHandle� FDatabaseHandle� 	FDBHandle�
 frmOpen  Msg� DatabaseFile App Path� CounterP DummyLen� endfor� DummyStringE 	DummyPart NewLen\ 	LastDummy� LastDumyv 
SorteLines Sorte_Lines] PartCounter�
 gGeladenA u� p� Werte�bergabe   	Speichern� frmSave% Create_Database Databaset Write_To_Array   den� Namen   des� Arrays3 
pArrayName9 
pArrayLine� pArrayFieldg 	endselect pValue   
Daten_Satz� 
Werte_Satz1	 Werte_Lines   Werte�
 Daten� Daten_Lines� gFileNew�	 gDatabaseReadOK� 
Copy_Array* ing das� ArrayG gWertBE gVorzWasserT gWertWasser� 	gVorzFett� 	gWertFettU 
gVorzBEFFE� 
gWertBEFFEY gVorzBE� derW	 	Zielwertem Write_ValueArray grdWerte�
 Row	 Col�	 Text   frmOptimize8 	cboWasser cboFett� cboBEFFE�	 cboBE�
 	txtWasser�
 txtFett
 txtBEFFE   txtBE�	 DummyStr{
 Success	 
PreisWerte   WasserWerte�	 	FettWerteb 
BEFFEWerte�	 BEWerte� XA_CLP_FileName� XA_LP_FileName   Solution_FileName� XA_EQS_LP_FileName� XA_EQS_Solution_FileName� XA_MPS_LP_FileName�	 XA_MPS_Solution_FileNameE	 Array_Col_Zutat   Array_Col_Preis�
 Array_Col_Wasser�	 Array_Col_Fettj	 Array_Col_BEFFEz	 Array_Col_BE   Array_Col_FE�	 Array_Col_MinWert�	 Array_Col_MaxWert   B
 C�
 Dh
 E   F�	 G(
 H   XA_PIF_FileName�	 MOPS_PIF_FileName?
 MOPS_Solution_FileName   gActiveFile   gActiveFileLocationT
 MOPS_MPS_FileName�
 OMP_PIF_FileName�
 NN_PIF_FileName   frmData   grdDaten   Rows   endfro   frmDaten�
 Comma_To_Point( als   Komma   ansehen? lcSearchh 	lcReplace lcNew   lcOld   tcString2 Pos   	txtZFWert   AltPos   	NewStringI Gloabl   gDataChanged   LPSOLVEPath   
WorkingDir*	     .  f � x     kernel     . , �   �     �     �     �  �    user       v     Globale Konstanten definieren:   �  �  Rezepturen - Optimierung   !   �  �  Rezeptur.DAT   �   �  �  Rezeptur.INI   �   �  �  REZEPTUR   �   �   Rezeptur.CLP   m   �  & Rezeptur.LP  �   �  B Rezeptur.ERG   �   �  ` Rezeptur.MPS   �   �  ~ Rezeptur.OUT   �   �  �
 Rez_XA.PIF   �	   �  � Rez_MOPS.PIF   �	   �  � Out.TXT  �	   �  � Rezeptur.MPS   ?
   �   Rez_OMP.PIF  T
   �  (
 Rez_NN.PIF   h
      v ,   Ein Array f�r die Aufnahme der Datenbasis.   �g  �  
   e  
   �     �  	   �  
   �     �          v D   Hier erzeugen wir eine globale Variable vom Typ Daten_Satz (Array)  v S   Die Dimension wird noch nicht festgelegt. Die Variable Daten_Lines wird verwendett  v ,   um das Array zur Laufzeit zu redefinieren.  � g    �  �    �      v 9   Ein Array f�r die Aufnahme der Werte eines LP-Ansatzes.S   �u  �  
   e  
   �     �  	   �  
   �     �     n     y      ��    v D   Hier erzeugen wir eine globale Variable vom Typ Werte_Satz (Array)  v S   Die Dimension wird noch nicht festgelegt. Die Variable Werte_Lines wird verwendett  v ,   um das Array zur Laufzeit zu redefinieren.  t u    �  �    �      v B   F�r das �ffnen von Dateien ben�tigen wir Variablen zur Aufnahme   v %   der Zielwerte und deren Vorzeichen:n              ,      <    ,  L    9  \    G  l    U  |    �      v S   F�r die Aufnahme der Optimierungs-Ergebnisse (Zutat, Aktivit�t) muss ein weiteresi  v    Array definiert werden.t  v    Type Ziel_Satz  v     Zutat As String  v     Zielwert As Single  v 
   End Type    v G   Jetzt eine Variable mit der Anzahl an Zeilen des Datentyps Ziel_Satz:n  v    Global z(25) As Ziel_Satz      v    Variablen f�r Verzeichnisse:  ��    �  ��    �  ��    �  ��    �  ��    �  ��    Y  ��    h  ���  ?   �      v (   Variablen zur Steuerung der Anwendung:  �    0  �    (
  �    =  �    G  �    �  �    I  	    �  	  	  	  ����t��h    Comma_To_Point0     @  �
        v @  Diese Methode erh�lt einen String als Parameter, pr�ft ob der  v C  String ein Komma enth�lt und ersetzt das Komma durch einen Punkt.  v E  Dies wird n�tig, da die Solver als Dezimaltrennzeichen einen Punkt   v   verwenden.   v E  Wir definieren die beiden Zeichen (zu ersetzend und ersetzend) als   v   Konstanten.r  �  r ,  �
  �  � .  �
   � �    v B  Den R�ckgabewert mit dem Parameter initialisieren (Fehler-Fall):   �
   v 2  Den String nach einem lcOld-Zeichen durchsuchen:   �
�    � � I �  v +  Kein lcOld-Zeichen gefunden.und tsch�ssz  = ( 8  $ v 6  lcOld-Zeichen gefunden. Das Zeichen ersetzen und den v   neuen String zur�ckgeben::   �� � �� �
�   �� � ���  �
    9 	  ��������    
 Copy_Array0�     @  �    v 6  Diese Methode kopiert die Daten aus dem Daten-Array  v 5  in das Werte-Array. Dies geschieht bei einem Aufruf  v   von Datei - Neu.   v "  R�ckgabewert mit Fehler belegen: + �   v J  Erst mal die Anzahl Zeilen feststellen und das Array neu dimensionieren:  � �   �  �    v )  Jetzt kopieren wir Element f�r Element:u  1� �> ���   1  � � 1  � �   1  � e 1  � e   1  � � 1  � �   1  � � 1  � �   1  � � 1  � �   1  � � 1  � �   1  � � 1  � �  �  ~ 0  1  � n  �  � 99   1  � y    1N ��   v "  R�ckgabewert mit Erfolg belegen: , �    9 	  ��������     Create_Database0�      @  ��   v ;  Diese Funktion erzeugt nur eine leere Datenbasis und gibt  v ,  einen Wert �ber Erfolg oder Fehler zur�ck. � �  � � + �  ��  �  \ �  ��  � �  �  � ��    �i {   , �  9 	  ��������     Read_DatabaseFile08     @  @�   v B  Diese Methode soll die Daten aus der Datenbasis auslesen und in  v A  das Array des benutzerdefinierten Datentyps Daten_Satz stellen.l  � �  � � 1 �   v   unload ... < v )  Den R�ckgabewert mit Fehler vorbelegen.a + @    ��   \ �  ��  �    v /  Neuen FileHandle holen und Datenbasis �ffnen:  �  � �   �  � ��  v 	 0 8! Pfado     v H  Wir lesen jetzt ein komplette Zeile und geben den Text aus, bis wir am v   EOF angekommen sind. � "  �  ) 8 �   �   �� 0 �   �i g  �� h   v 3  Msg = "Wert des Durchlaufs: " & Counter & Chr(13)   v   Msg = Msg & Dummy   v /  Msg = Msg & "Datentyp ist: " & VarType(Dummy)   v   MsgBox Msg    v   Array neu dimensionieren:o    ���   �D   ���  �    v    Die einzelnen Felder einlesen:   ��     � O  �  �   1� �? ��� 0 � 1�� �  � ; � I � @ � O 1 O� � �  8 @ 1��  O @ ���  � @v 8  MsgBox "Gefundener Teilstring: " & Chr(13) & DummyPart @v    Teilstring in Array eintragen: @� 
 h Daten   � 8$  � 08    1N ��    � 1 O� � �  8  v 3  MsgBox "Rest des Strings: " & Chr(13) & DummyPart   v B  Jetzt noch den letzten Teilstring in das Array schreiben lassen:  � 
 H Daten   ���  8$  �   ��   J 0     v 0  Die Datenbasis kann wieder geschlossen werden.  �i {   �  �   v H  Jetzt noch den Wert setzen das die Daten aus der Datenbasis ausgelesen v   worden sind: , �  v %  Den R�ckgabewert auf Erfolg setzen.. , @      9 	  ��������G    	 Read_File0@	     @  k   v J  Diese Methode liest Dateien ein, die Daten zu einer Rezeptur-Optimierung v   enthalten. d   � �  1   v )  R�ckgabewert mit Fehler initialisieren:l + k   �  �  rez  � @ N  � � @ \ � 6 � 0 REZ-Dateien (*.rez)|*.rez|Alle Dateien (*.*)|*.*   � @ g   � � @ q  � @ � {   v   Keine Datei gew�hlt|  {�  �    � I � = :
 8    v    reinen Dateinamen feststellen:  {�   �  1 ���� ? ��B   { 1�� �   \ � I 8 , { � 1� � �  0 ,< D  8   1N ��      v O  Wir merken uns f�r ein eventuelles Speichern den genauen Pfad inkl. Dateinameh v   der ge�ffneten Datei:n  { (
     v   Neuen FileHandle holen:t � � � �  � �   �  { ��     v Z  MsgBox "Das Einlesen der Daten des Ansatzes in das Array ist noch nicht implementiert !"   � �  �  ) 8 �   v :  Wir lesen jetzt zuerst die Zeile mit den Zielwerten ein.  �i g  �� h   ��    � O �  �  1� �? ��   � 1�� �  L ; � I  0v -  Teilstring gefunden --> ins Array schreibens 0 � O 1 O� � �  8 0 1��  O 0 ���  � 0 �V � 0�& v   VZ WasserA @ 8  0�& v   Wasser @ 8  0�& v 	  VZ Fett  @ 8  0�& v   Fett @ 8 , 0�& v 
  Vz BEFFE @ 8 9 0�& v   BEFFEF @ 8 G 0�& v   VZ BEF @ 8 U 0:   8   1N ��   � 1 O� � �  8 v )  Jetzt noch den letzten Teilstring (BE):r  8 �    v M  Da das Werte-Array neu aufgebaut werden soll, muss der Z�hler f�r das Arrayl v   auf 0 gesetzt werden.u �  �   v H  Wir lesen jetzt ein komplette Zeile und geben den Text aus, bis wir am v   EOF angekommen sind. �   �� 0 �	   �i g  �� h     v   Array neu dimensionieren::    ���   �D   ���  �    v    Die einzelnen Felder einlesen:   ��     � O  �  �   1� �? ��	 0 � 1�� �  N ; � I 	 @v -  Teilstring gefunden --> ins Array schreibens @ � O 1 O� � �  8 @ 1��  O @ ���  � @� 
 � Werte   � 8$  � 08    1N ��    � 1 O� � �  8  v B  Jetzt noch den letzten Teilstring in das Array schreiben lassen:  � 
 ~	 Werte   ���  8$  �   ��   J l       �i {   �  �   v   gGeladen = Truet  v !  R�ckgabewert auf Erfolg setzen:e , k      9 	  ��������~     Write_DataArray0�     @     v =  Diese Methode �bernimmt die Aufgabe, die Daten aus dem Gridn v ?  des Fensters Datenpflege in das Array des benutzerdefinierten  v )  Datentyps Daten_Satz zur�ckzuschreiben.i v F  Diese Operation muss ausgef�hrt werden, wenn das Fenster Datenpflege v E  verlassen (Deactivate) wird oder wenn der Benutzer die Schaltfl�chee v I  Speichern im Fenster Datenpflege ausl�st, damit das Array anschliessend  v 2  in die Datenbasis zur�ckgeschrieben werden kann.   � � 1 �   v )  R�ckgabewert mit Fehler initialisieren:t +    v ?  Wir m�ssen jetzt durch jede Zeile laufen und die Werte in den  v   Spalten pr�fen.t v A  Eine Zutat wird nur als Datensatz akzeptiert, wenn der Name der� v   Zutat eingetragen ist.  1� {
 �
 �
�� � > ���  v 9  Wenn Zutat leer, muss gemeckert und abgebrochen werden.e  � {
 �
 �   1 {
 �
 �   {
 �
 ��  �    � I z 0v +  Die Zutat hat keinen Namen...und tsch�ss.r 0� : �4 Eine Zutat kann nicht ohne Namen gespeichert werden.  �  � ��� �  � ���  � 0 ��   < �berpr�fen Sie Ihre Daten.  �  � 0 �� & 0= ,  8   1N ��    v 7  Wenn wir hier ankommen --> Die Daten sind konsistent.r v R  i steht jetzt auf der Anzahl an Zeilen im Array --> also Daten_Lines einstellen:  {
 �
 �
��  � v %  Jetzt das Array neu dimensionieren::   �  �     1� {
 �
 �
�� � > ���  v 4  Jetzt die Daten aus dem Grid in das Array stellen.  v @  Die Anzahl der Zeilen, die eingelesen wurde, bestimmt den Wert  v    der Z�hlervariable Daten_Lines  v ,  Die Elemente in die Array-Zeile schreiben.   1 {
 �
 �   � {
 �
 �  {
 �
 � 1  � �  � {
 �
 �  {
 �
 � 1  � e  � {
 �
 �  {
 �
 � 1  � �  � {
 �
 �  {
 �
 � 1  � �  � {
 �
 �  {
 �
 � 1  � �  � {
 �
 �  {
 �
 � 1  � �  � {
 �
 �  {
 �
 � 1  � �    1N ��      v "  R�ckgabewert mit Erfolg belegen: ,     9 	  ��������;     Write_DatabaseFile 0�     X  U    v >  Diese Methode soll die Daten aus dem Array in die Datenbasis v   zur�ckschreiben. v ;  Da wir alle Daten immer auf einmal manipulieren, wird dies v ?  Datenbasis immer komplett mit den neuen Werten �berschrieben.    v I  Zuerst m�ssen wir die Daten aus dem Grid in das Array schreiben lassen.n    +� I �  v A  Die Daten konnten nicht zur�ckgeschrieben werden...und tsch�ss.   = � 8    v 8  Wenn wir hier sind --> aktuelle Daten stehen im Array. v <  Jetzt kann das Array in die Datenbasis geschrieben werden:  v I  Dabei m�ssen die Werte einer Grid-Zeile zu einem String zusammengefasstn v 3  und dann wird der ganze String zur�ckgeschrieben.S � � � �       ��  � \ �  ��  � �  � �   �  � ��      v !  Daten-Array in Datei schreiben:i  1� �> ��h   1  � ��  p ; �  1  � e� �  � ; �  1  � �� �  � ; �      1  � �� �  � ; �  1  � �� �  � ; �      1  � �� �  & ; �  1  � ��    �  �i [ w x  u   1N ��     �i {     � $ � Ihre Daten wurden gespeichert.  � &    9 	  ��������'    
 Write_File 0�     X  x  �      v >  Diese Methode speichert die Daten einer Rezeptur-Optimierung v C  Nur die Daten des Benutzers werden gespeichert, keine Ergebnisse.m  � �  1    v "  Werte aus Grid in Array stellen.    t       ��  �  u �  0�   Neudatei  � � I |  � � @ \ �  @ rez  � @ N � 6 \0 REZ-Dateien (*.rez)|*.rez|Alle Dateien (*.*)|*.*   � @ g � � @ q  � @ � {  v    Abbrechen v. "Speichern unter"  {�  �    � I  (=  8    v   reinen Dateinamen extrahieren"  {�   �  1 ���� ? ��� , { 1�� �  v \ � I � 8 { � 1� � �  0 8< � ,8   1N ��     v <  Wir merken uns den Dateinamen inkl. Pfad in einer globalen  v A  Variable, damit bei einem einfachen Speichern diese Information   v $  sofort wieder zur Verf�gung steht.   { (
  2 8  v >  Wir m�ssen die Daten einer ge�ffneten Datei wieder Speichern  v A  Es muss also der Pfad der ge�ffneten Datei festgestellt werden.n   (
 {v  0 DBPath + "\" + dateiname   8      � F �   �  � �   �  { ��    v   Zielwerte schreiben:  �  � ; �  � �  � ; �  � �  � ; �  ,� �  � ; �     9� �  � ; �  G� �   ; �  U� �   ; �  ��   �  �i [ w x  u      v U  MsgBox "Das Schreiben der Array-Elemente in die Datei ist noch nicht eingerichtet."d v !  Werte-Array in Datei schreiben:)  1� �> ��D   1  � ��  � ; �  1  � e� �   ; �  1  � �� �  : ; �      1  � �� �  f ; �  1  � �� �  � ; �      1  � �� �  � ; �  1  � �� �  � ; �      1  � n� �   ; �  1  � y�    �  �i [ w x  u   1N ��     �i {     v   reinen Dateinamen extrahieren   {�   �  1 ���� ? ���   { 1�� �  � \ � I � , { � 1� � �  0 ,< �  8   1N ��    �  �    9 	  ��������Q     Write_To_Array 0�     X & �  %     3     A     ]      v /  Methode die einen Wert in ein Array schreibt.P v    Als Parameter werden erwartet: v '  pArrayName -   der Name des Arrays.  v I  pArrayLine -   die Zeile des Arrays in die der Eintrag erfolgen soll.o v M  pArrayField -   die Position des Feldes in die der Eintrag erfolgen soll.  v >  pValue	 -   Der Wert der ins Array eingetragen werden soll.  v %  Folgendes gilt f�r das Array Daten:a  %� 
 � Daten � I r   AV �  �& v   Zutat1 0 ] 3  � �  �& v   Preis2 0 ] 3  � e  �& v   Wasser 0 ] 3  � �  �& v   Fett 0 ] 3  � �  �& v   BEFFE5 0 ] 3  � �  �& v   BE 0 ] 3  � �  �& v   FE 0 ] 3  � �  :  8    v #  Jetzt kommt das Array Werte dran:e  %� 
 � Werte � I �   AV �  �& v   Zutatm 0 ] 3  � �  �& v   Preis  0 ] 3  � e  �& v   Wasser 0 ] 3  � �  �& v   Fett 0 ] 3  � �  �& v   BEFFE  0 ] 3  � �  �& v   BE 0 ] 3  � �  �& v   FE 0 ] 3  � �  � & v 	  MinWert 0 ] 3  � n  �$& v 	  MaxWert 0 ] 3  � y  :  8     9 	  ��������5     Write_ValueArray0R     @  t    v 8   Diese Methode schreibt die Daten aus dem Werte-Grid in  v    das Werte-Array.  v @   Das Array hat schon die notwendige Dimension und muss also nur  v    aktualisiert werden.  v G   Auch die Zielwerte des Ansatzes m�ssen noch in die globalen Variablend  v    geschrieben werden.d    � > 1    v )   R�ckgabewert mit Fehler initialisieren:s  + t     1� �> ��r  1 � � �   � � � �  � � � 1  � � � � � �  � � � 1  � n � � � �  � � � 1  � y � � � �  � � � 1  � e � � � �  � � � 1  � � � � � �  � � � 1  � � � � � �  � � � 1  � � �  � � �  � � � 1  � � �$ � � �  � � � 1  � �   1N ��     v A   OK, jetzt noch die Zielwerte in die globalen Variablen stellen.i   � � �    � � �    � � � 9   � � � U   � � �    � � � ,   � � � G   � 
 � �      v "   R�ckgabewert mit Erfolg belegen:  , t    9 	  ��������*   �