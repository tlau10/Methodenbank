��,��  T    
GN_Ausgabe0�� Ausgabeg    �  E   B #����$Form15g  6  7�  8E  �#   InOut ����inout �)   	WunschOut���	wunschout �&   
Problemart��
�Text1 �7   Ansatz�Grid���/
MS Sans Serif  A�
   �&   
	CmdGrafik� Grafik���w	 �&   	
SolverName�H�Text1 �+   CmdSchliess� 	Schlie�enP��w �$   Werte�Grid��0O�   �#   ZFWert���zfwert  �(   Linie1�@@@ �  �  �  �  �-   Bezeichnung2� 
LP-Ansatz:�x ��  �.   Bezeichnung1� Problemart:��
��  �*   Bezeichnung5� Solver:�H��  �-   Bezeichnung4� 
Aktivit�t:��
��  �,   Bezeichnung3� 	Ergebnis:����  ��  �  __	 
�Q�Q�   ���� mi� � ( � Z v � x�   � �K� � !�� T�                  �  i  Zeit1_Timer9 	Form_Load- Zeit1 Intervalc Enabled� halt� �i� x  XAPath� zaehler8 Text1_Change� �dummy��pos/ ZFWert_Change=�ZFWert^ Text& Warnungd Warnung_Change� aZeilen��jC zZeilenc�suchStr� suchr�wertea�altposC�E2�E1�k_pos��E2   Ealt Werte_Change> Bezeichnung1_Click�endeE InitWertegitter� gitter1� Rows� 	maxzeilen Colsf ColWidtht Col Row   werter1��k� Ay AName ZX ZNamem Werte_Click��l{ CmdSchliess_Click� ausgabe� berech� frmQ 
Berechnung� 
XA_Ausgabe��Height��Left�Top��Width�solver� Bezeichnung3_Click(�
SolverNameP�
GN_Ausgabe��zeilenE kante� von8 nach&�EingTab 	kantentyp� SolverName_Change  �satz� bez_var! Befehl1_Clickq Grafik_Click� grafik   CmdGrafik_ClickL�	GN_Grafik   screen� Init_Ansatz_Gitter��ergeb  �du� xagenerieren   xa_datei_schreiben\ EXEPathv anz_var   anz_rest   matrix_v   ziel   matrix_b   max\ operator� b_vektor� auswert��Ansatz Ansatz_Click Ansatz_Gitter   stlegen   Init_Werte_   itter� gitter) Init_Werte_Gitter��
Problemart� minmax  �	WunschOut  �	menge_out   heigt   ergebnis  �help   daten   mult  �InfoFenster   WunschOut_Change   InOut_Change   iput  �InOut  �menge_in  �
WorkingDir  �GN_MDI   length    	  ��������     Ansatz_Gitter0�     X  v       � "   
  �  � 2     �       = ���  \ �   = ��  \ �v  # zeilen + 6     � �  = �> ���  �   �  \  �  M ��       v #   -- LP-Ansatz in Tabelle eintragen    � � = > ��p  (� = �> ��d   (��  \ �   �  \ �   ( �  =   \ �  M ��   M ��      = ��  \ � �  \ � �  �	 Zielfkt.:  \ �    = ��  \ � �  \ � �  � Bounds:  \ �      � � = �> ���  = ��  \ �  � ��  \ �  �  =   \ � v  ( Zielfkt eintragent  = ��  \ � � �  =  & \ � v  ( bounds �  \ �  �  =  E \ �   M ��     v     Max oder Min eintragen1   = ��  \ �   = � \ �   = 2�� E h�  T Max  \ � 2 ~�  n Min  \ �     v     B-Vektor eintragen    = � \ �   � � = > ��   �  \ �  �  =  9 �  =  E2 \ � v 
 6 B-Vektor  M ��   v    auswert.Enabled = True    9 	  ��������2     CmdGrafik_Click0B      X  {          �e �   x   v    Unload Mee    9 	  ��������     CmdSchliess_Click0$      X  C      �     9 	  ��������    	 Form_Load0     X  i        � .  �  (     �   � @ �  '  �        )    �     � p �  � l  �   � �� ��  �� � ��  �   � �� } �  �� � ��  �        v $   -- Problemart & gew�nschter Output     = 2�� I  �   Maximum  � �   2 < �  $ Minimum  � �   8        = \�� I � �  Z Input:   d �   m � �   2 � �  � Output:  d �   � � �   8       v    -- LP-Ansatz f�llen     $   v      v '   -- L�sungswert und Wertegitter f�llenT     �V     � 
 & XAeqs & � 
 < XAmps & �  R STRADA  &     �� 
 r XAeqs � I �  y�  � \xa.out � �i �    �  � XA Equation Style  � �  2   y�  � \xa.out � �i �    �  � XA MPS-Format  � �  8    v    ..da auch f�r Strada !   ��  @ STRADA  � E t�  V Strada MPS-Format  � �      �i  � � �� �i {       v )  Fehler suchen u. ggf. Ausgabe abbrechenn  � �   ?? Error  �  �   � � � I ` �  >	 E R R O R  �  �  C �  8   v   ZF-Wert suchen  � �  �
 SOLUTION (  �  �   � � � I   �  � �  � �  � �� �  �   � �  �  Tv $   ab hier kann weitergesucht werden 8  $ v +   Warnung suchen u. ggf. Ausgabe abbrechen   � � 
 P N O   �  �   � � � I �  �  � � ) � �� �  �  C �  8    v '   Wertegitter einrichten u. vorbelegen. $   �    v (   Werte suchen und ins Gitter eintragen �   � � �> ���      K �   �  =  E4�� 8    v   Erste Spalte  �  K �  �  z      82 K �     v   Zweite Spaltep  � K �   T �  8�  �      � � � I 
 , �  � �  � �� ^ , 8�   > , ^ >�� �   >� � �� ^ D ,v   Tausender-Komma eliminierenm ,� ^�  l , �  i , i� � I � 8 ^� i�� �  c 8 ^ i��  ^�   i� �  r 8 c r2 ^ ,8  0 ,v   altpos = pos + 20+  2 . ,�          0.00000   ^  8      ^ K �    ^4�� �  =  v  : Eintrag in SATZa   ^�  �    !� � �� �         ��      � N ��      � 
 � MOPS  &       :      ��� = 	    9 	  
�������     Init_Werte_Gitter0�      X  �       ���  K � � K �   � ��  K  � � �� K  �  v    Spalten�berschriften  �  K �   �  K � � 
 �   Var.  K �  � K � �  �   Werte   K �  T  9 	  ��������   �