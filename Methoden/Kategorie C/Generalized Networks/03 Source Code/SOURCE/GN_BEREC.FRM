��,  Z   U    
Berechnung� 
Berechnung�   �  �  7   B #����$Form15�   6�  7�  87  �V6  �  __	 
P�P�w   ]g � L�� � ic� � ��Z 2�o� � � H� W~ � .� �=���                  w   	Form_Load�  CLP_Datei_schreibenc XA_Datei_schreiben� XAPath� x EXEPath�  i�  j� 	maxzeilen s�  z� erl�se) si kosten   a e� AMenge] ZMenge1! ZMenge2@ dora' D1� T1� aZeilenO zZeilen� gr�klei� arg�ende   D2P T2� D3d T3   MousePointer� Zeit1_Timert zaehler� Zeit1� Enabled� haltI ausgabeT Interval  Berech� XAeqs_Datei_schreibenm solver� XAmps_Datei_schreiben� MPS_Datei_schreiben� EQS_Datei_schreibenQ XAmpsu MOPS� MOPSPath1 NN� NNPath� Rows zeichen# 	schreiben� thenOpen� 
mpsZeichen  	mpsz_endez 
XA_Ausgabe� zahlen� zahl nurCZahl� Start Test2 pos Selectmousepointer� list� XA_Batch_Datei_schreiben} MOPS_Batch_Datei_schreibene mnuFensterEingabe mnuFensterAusgabe� ZF� zfwert� c15 c2 czfwert[ min
 satz" max� anz_varl ziel, bez_var; KommaPos� matrix_b� anz_rest   matrix_v� d] anfangj operatori b_vektor   ergeb� du] XA_Generieren� oben daten   vons nach� ober   unter� mult5 anzahl8 	menge_out� Cols� mgsbox   
GN_Ausgabe� 
EingabeTab   EingTab� der L�sungT Hier_steht_der_Title= zeilen knotenH var   f   k� GRAPHIK   FRM   rest   rest_zf| iGRAPHIK� i_str   	ThenPrint� pirnt� j_strB rest_nr� grafik   	GN_Grafik   Check_and_Wait   Wartefenster   getmoduleusage   warte   byL screen   iput   Solver_Batch_Datei_schreiben   name_solver   LPSOLVEPath   erw   MinMax   restnr   Pif_Eintrag   
WorkingDir   
STRADAPath      ;	  ��������    Check_and_Wait 4h     X  �       v -    Kontrolle des Solver-Aufrufs mittels SHELL   v ,    und Verz�gern des Programmlaufes, bis die  v     SHELL abgearbeitet ist.    � �   
  �    � I �  � 0 � + Der Solver konnte nicht aufgerufen werden ! � � &  2 r v    Zur Beruhigung der ORler   �e �   x    �   �� � Z d ( � Y >    �   8     9 	  ��������     CLP_Datei_schreiben 4N     X  g        v     CLP -> Steuerdatei f�r XA     [�  B  \gn.clp � �i �       �� 
 d  XAeqs � I �  �i [ w x �   �  GN.LP        OUTPUT XA.OUT  u   2 � �i [ w x �   �  GN.MPS       OUTPUT XA.OUT  u    v 3   Aussensteuerung der Minimierung bzw. Maximierung   � ��� I ` �i [ w x �  <             MAXIMIZE yes  u  2 � �i [ w x �  r             MAXIMIZE no u  8   8     �i [ w x �  �              PAGESIZE 24  u   �i [ w x �  �              LINESIZE 79  u   �i [ w x �  
              TMARGINS 0 u   �i [ w x �  8              BMARGINS 0 u   �i [ w x �  f              FIELDSIZE 11 u   �i [ w x �  �              DECIMALS 5 u   �i [ w x �  �              EUROPEAN NO  u   �i [ w x �  �              LMARGINS 0 u   �i [ w x �  "              COPIES 1 u   �i [ w x �  N              WAIT NO  u   �i [ w x �  z * u   �i [ w x �  �              MUTE NO  u   �i [ w x �  �              LISTINPUT NO u   �i [ w x �  �              WARNING YES  u   �i [ w x �                SOLUTION YES u   �i [ w x � " N              CONSTRAINTS YES  u   �i [ w x � " �              COSTANALYSIS YES u   �i [ w x � $ �              MARGINANALYSIS YES u   �i [ w x �  �              MATLIST NO u   �i [ w x �                DEFAULTS NO  u     �i {      9 	  ��������+     EQS_Datei_schreiben 4~     X  )       v    --> LP-Datei f�r XA       � *  �  �     �  � <  �   
       [�  L  \gn.lp  � �i �      �i [ w x �  t  ..TITLE u   �i [ w x �$� ��u v !  Kein Titel vergeben nur ein TABr Tv   wg. Ergebnisausleserei    v     ZF schreiben >-    � �� � I 8 �i [ w x �   ..OBJECTIVE MINIMIZE  u   2 h �i [ w x �  J ..OBJECTIVE MAXIMIZE  u   8     �  r    �    � � � �> ��   � �  �   2 �  �  �2�  �   2 �  �  �4��2�  �  +  2 �    � N ��    v      Letztes Pluszeichen entfernen   � � � �  �� � �� �    v     Komma zu Punkti   � �  v , �  �   �� �  � � �  � � Z �  ���  � .  � �   � �  � , �  �  Y �    �i [ w x  � u      v     Bounds schreiben :-   �i [ w x �  , ..BOUNDS  u      � � � �> ��� �  ^      �  �  �4��2� 
 �  <=   2� �  �  �2 �   � � 
 �  >=   2� �  �  �2 �  �i [ w x  � u    � N ��      v     Restriktionen schreiben 3-  �i [ w x �  2 ..CONSTRAINTS u   �  L      �      � � � �> ���   � � � �> ��0  �  �  �  �� � I �v ) ( Wenn Wert > 0 dann +-Zeichen hinzuf�genu $ �  �  �  �� v  ( Wert in String umwandeln $ �  ( , �  
v  ( Komma zu Punkt $ 
� � E r 
��  h .  �� $ � �  ~ +   2 2�  �   2 �  �  �4��2�  �   2 � v  > mit + Zeichene 8    �  �  �  �� � I &v 6 ) Wenn Wert <0 dann ist das Vorzeichen schon vorhanden ( �  �  �  �� v  ) Wert in String umwandeln ( �  � , �  
v  ) Komma zu Punkt ( 
� � E � 
��  � .  �� ( �  � �  �   2 �  �  �4��2�     2 � v  9 ohne + Zeichen 8   � N ��    �  �  �  � �  T   2 �  �   2 � v & 6 B-Vektor nach jeder Zeile hinzuf�gen �i [ w x  � u  �  �      �    � N ��     �i {      9 	  ��������Q    	 Form_Load 4p     X  Z       v :    Da hier keine Bildschirmausgabe erfolgt, h�tte der Code  v :    auch in einem Modul (.bas) untergebracht werden k�nnen.      � �   
  ,  � �     5      �   � o   � v   falls vorhandenl    �  � 
 solver.pif  �  
 solver.bat  $  Lv  *  gn_glob.bas     �V V   � 
 \ XAeqs &     $   �  $   g   $   )v    LP-Dateii     [�  � \xa.out � ���  �    � I � 0 [�  � \xa.out � �   8     v   -- Berechnungslauf   [�  
 \solver.pif �   �   $   �    �e �   x    � 
 X XAmps &     $   �  $   g   $        [�  � \xa.out � ���  �    � I � 0 [�  � \xa.out � �   8     v   -- Berechnungslauf   [�  � \solver.pif �   �   $   �     �e �   x    � 
 F MOPS  &     $   �  $        [�  v	 \mops.out � ���  �    � I � 0 [�  �	 \mops.out � �   8     v   Berechnungslaufa   [�  � \solver.pif �   �   $   �    v J  MsgBox "Bitte best�tigen Sie nach dem Piepston. ", 16, "MOPS-Berechnung"  �  \ notepad    [� �  t	 \mops.out � �! �   v   MOPS_Ausgabe.Show 0w     �  � STRADA  &     $   �  $        [�  � \xa.out � ���  �    � I ( 0 [�   \xa.out � �   8     v   -- Berechnungslauf   [�  P \solver.pif �   �   $   �     �e �   x      %     �   � Noch nicht implementiert !  � &    :     �  � o    9 	  ��������\     gr�klei4�     @  = H       v   nix oder was  H�  :     � I \  �  N  0  H 8    H�  j  <=  � � � I �  �  �     H�  H C O 8    H�  �  < � � � I �  �  �     H�  H C O 8     H�  �  >=  � � � I & �      H�  H C O 8     H�  4 > � � � I h �  N    H�  H C O 8     H�  v = � � � I � �  �    H�  H C O 8    v '  default, d.h. wenn gar nichts hilft !  �  �  >=  H�  H C O    ��O  H =    9 	  �������(     MPS_Datei_schreiben 4R     X         v ?    Beachten Sie, dass in der Sektion COLUMNS gleiche Variablen    v     aufeinanderfolgen muessen      � �  �  �  �   
  � �  T s �    |  � �     � H   [�  �  \gn.mps � �i �      �i [ w x � ( � " NAME          Generalized Networks  u       v     ROWS schreiben <-   �i [ w x � 
 V ROWS  u    v     Zielfunktion  n  v .   'Print #2, " N  R" & CStr(satz.anz_rest + 1)  �i [ w x �  �  N  ZF  u       v     Restriktionen     � � � �> ��b  � � Tv   Wert "i" in String umwandeln   v    Restriktion: =   �  �  �  d =   � � E ��i [ w x � 
 �  E  R  T2u   v    Restriktion: <=  �  �  �  � <=  � � E ��i [ w x � 
 �  L  R  T2u    v    Restriktion: >=  �  �  �  ( >=  � � E X�i [ w x � 
 F  G  R  T2u    � N ��      v     COLUMNS schreiben 9-  �i [ w x �  � COLUMNS u   �  �     �   �  � v 6  F�r die Zwischenspeicherung von Variablenbezeichnung     � � � �> ���v   Uber alle Variablen     � � sv (  Wert "Variblen-Nr" in String umwandeln �  v +  Fur die Zwischenspeicherung von Var.-Bez.a  �  �  �� � v !  Variablenbez. zwischenspeichernn �  v   Zahlenzaehlerh    � � � �> ���v     Uber alle Restriktionen 0   �  �  �  �� � I � @ , � � |v " ) Wert "Rest-Nr" in Str. umwandeln , �  �  �  �� v  ) Wert in String umwandeln , , ��   T , �� I �v  (  Erste Zahle 8 �  �  �� v ! ( Variablenbez. zwischenspeichernn 8 �  V , �  
v $ ( Vorhandensein von Nachkommastellen ` 8 
� � �  �  �  �  ��� � �  �  �  �  �� � � � I n D 
��  � .  ��v  *  Aus 0, --> 0. D � � 
        �  2�  2      R  2 |2�  L            0  2 2 �  88  ` 8 
� � �  �  �  �  ��� � � I . D 
��  � .  ��v  *  Aus , --> . D � � 
 �       �  2�  �      R  2 |2�               2 2 �  88  ` 8 
� � � I � D � � 
 N       �  2�  f      R  2 |2�  �             2 2�  � . 2 �  88  ` 08  @ 0 �� �  �  � � � I �v  (  Zweite Zahl P < �  � , �  
v % &  Vorhandensein von Nachkommastellenv ` < 
� � �  �  �  �  ��� � �  �  �  �  �� � � � I �	 H 
��  �	 .  ��v  *  Aus 0, --> 0. H � �  �	           R �  |2�  �	        0  2 2 �  <8  ` < 
� � �  �  �  �  ��� � � I �
 H 
��  >
 .  ��v  *  Aus , --> . H � �  f
           R �  |2�  �
         2 2 �  <8  ` < 
� � � I  H � �  �
           R �  |2�  �
         2 2�  �
 . 2 �  <8    <�i [ w x  � u v    Zeile schreiben <�  :     �      <v    Dritte Zahl schreiben ` < �  �  �� v  & Wert in String umwandeln   < �  � , �  
v % &  Vorhandensein von Nachkommastellenv ` < 
� � �  �  �  ��� � �  �  �  �� � � � I � H 
��  > .  ��v  *  Aus 0, --> 0. H � � 
 h       �  2�  �      ZF 2�  �            0  2 2 �  H�i [ w x  � u v    Zeile schreiben H�  �     �  <8  ` < 
� � �  �  �  ��� � � I � H 
��  0 .  ��v  *  Aus , --> .GRAPHIK.FRM - 3   H � � 
 j       �  2�  �      ZF 2�  �             2 2 �  H�i [ w x  � u v    Zeile schreiben H�  �     �  <8  ` < 
� � �  �  �  �� � � � I � H � � 
 *       �  2�  B      ZF 2�  V             2 2�  t . 2 �  H�i [ w x  � u v    Zeile schreiben H�  �     �  <8    (8   8   � N ��    � N ��       v 6    Nichtnullelemente des Vektors B schreiben - RHS -  �i [ w x �  2 RHS u      � � � �> ��@  � � v +  Wert "Restriktion-Nr" in String umwandelnd    �  �   � � � I 4 �  �	     MYRHS �  �       R 2 2�  �           2 �  �   2�   . 2 �  �i [ w x  � u  8      � N ��      v G    Untere/obere Schranken fur einzelne Variablen schreiben - BOUNDS 
-    v (    Keine Elemente --> keine Uberschrift    � � � �> ��J � �  �  �� � � � �  �  �� � � � � I @ �i [ w x �  * BOUNDS  u  < L 8    � N ��     � � � �> ���  �  �  ��    � �  �  �� � I 2v  %  Obergrenze schreiben  �  �  UP MYBOUND      2�  �           2� �  �  �2�   . 2 �  �i [ w x  � u  8    � �  �  �� � I �v  %  Untergrenze schreiben �  t  LO MYBOUND      2�  �           2� �  �  �2�  � . 2 �  �i [ w x  � u  8    � N ��      v     ENDATA schreiben :-   �i [ w x �  " ENDATA  u v '   Ende der Ausgabe in die Datei gn.mps-    �i {        9 	  ���������    
 mpsZeichen4�      @  � H        � $  u     H�  2  > � � � E Z �  J  G  u C �   H�  f  < � � � E � �  ~  L  u C �  v ;   If InStr(arg, "=") > 0 Then zeichen = "E": GoTo mpsz_endel    �  �  E  u C �   ���  u �    9 	  � ������     nurCZahl4�      @  � H     P  � $  �     H�  2  = �  �   �� � E h  ���  ^     H��     H�  v  < �  �   �� � E �  ���  �     H��     H�  �  > �  �   �� � E �  ���  �     H�� P   H4�� �    9 	  ��������     Solver_Batch_Datei_schreiben 4�     X  �       v A    -> wird in den EXEPath geschrieben (alles: Eingabe u. Ausgabe)   v %    keine Eintr�ge in den Solverpfad      [�  �  \solver.bat � �i �      �i [ w x �  � 	 @echo off u      �V �  � 
 �  XAeqs & � 
 �  XAmps &  �i [ w x �    REM Solver: XA  u  �i [ w x �  J set Oldpath=%path%  u  �i [ w x � 
 t path   � � �  � ;%path% � u  �i [ w x �  �	 xa gn.clp u  �i [ w x �  � path %Oldpath%  u  �i [ w x �  � set Oldpath=  u    � 
  MOPS  &  �i [ w x �    REM Solver: MOPS  u   � ��� I � $�i [ w x  Q�  t \mops_pc.exe gn.mps MAX � u  2 � $�i [ w x  Q�  � \mops_pc.exe gn.mps MIN � u  8    v   Case "LPSOLVE" v %     Print #1, "REM Solver: lp_solve"e v G     Print #1, LPSOLVEPath + "\lp_solve.exe -mps <gn.mps >lp_solve.out"    �  t STRADA  &  v ;   --> das Ergebnis wird �ber die XA-Routine eingelesen !!!c �i [ w x �    REM Solver: STRADA  u   � ��� I L  
 G ��         Print #1, STRADAPath + "\strada.exe gn.mps MAX xa.out solve"  2 � $�i [ w x  i� ( # # \strada.exe gn.mps MIN xa.out solve � u  8     :     �i {     9 	  �������,   �