��, 1  b    Verzeichnis& ' � Verzeichnisse��� �  
  _  z   B #����$Form35�  6
  7_  8z  �!   Verzeichnis1��HwE �'   cmdAbb� 
&Abbrechen(��w �   cmdOK� &OK(� �w  �   Rahmen1�� x W� �   	Laufwerk1�� �	w; �!   Text1�� hwText1 ��  �  __	 
�Q�Q�   � � \�     E  �Z 1'3� If��p +�� �pY�a�                  �  �  Bezeichnung1_Click�  Verzeichnis1_Change�  Befehl1_Click DateiNeu�  VerzeichnisZ Befehl2_Click� text1 Text� Verzeichnis1� Path� Verzeichnis2_Change� Text2> Verzeichnis2� XAPatht DBPath* 	Form_Load� Appo Laufwerk1_Change 	laufwerk1� Drive Laufwerk2_Change� 	laufwerk2� Text1_Change) further� Rahmen1_DragDrop� Source   Control   X YD Rahmen2_DragDrop� batpath
 	aktxapathh 	aktdbpath: FNUMr prgpathO foundA 
aktxadirve 
aktxadrive   	aktxastrg   aktxalen� akxapath� changexadrive� aktdatenpath� InitDir� Text2_Change   EXEPathe XAPIF� Text1_LostFocus   	xa_falsch9 	db_falsch Text1_GotFocus� 
xa_falsch1   GotO text2_lostfocus� 
db_falsch1~ DBPpath� VerzTyp   rahmen1� Caption� 	endselect� MOPSPath   solver   pathPIF� eintrag   NNPath� OMPPath   
durchlauf2� Thenverztyp solver_falsch   Text2_Click   Text2_MouseDown� Button   Shift� Text2_MouseMove   Text2_MouseUpQ cmdOK_Click   pfad   cmdAbb_Click� Pif_Eintrag  verz_falsch   pifdatei   	Write_ini   Height   Width   top   screen� left   
WorkingDir   LPSOLVEPath   der   	GRAPHPath    	  ��������     cmdAbb_Click 4"      X  Q      G     9 	  ��������     cmdOK_Click 4R     X  :      v 5    Sowohl die .ini als auch die .pif werden ge�ndert.   v 4    Die Prozedur Pif_Eintrag ist im Modul berechnung   v     zu finden.u       DV �    �  �  DB  &  v    nur INI-Eintrag, s.u.   �  �  XA  &   �  �   �  
 solver.pif  �  4
 solver.bat  $  a  � 
 d MOPS  &   �  �  r �  �
 solver.pif  �  �
 solver.bat  $  a   �  � OMP &   �  �  � �  �
 solver.pif  �  
 solver.bat  $  a   �  J LPSOLVE &   �  �  � �  t
 solver.pif  �  �
 solver.bat  $  a   �  � Working &   �  �  � v   kein Pif_Eintrag eintrag  � 
  GRAPH &   �  �  � v   kein Pif_Eintrag eintrag    :       v =    Aktualisierung der Initialisierungsdatei .ini, die sich im   v J    EXEC-Pfad des VB-Programmes befindet. Sobald die Programmpfade ge�ndert  v F    und mit der OK-Taste(Befehl1) best�tigt werden, wird die .ini-Datei  v K    ge�ffnet und der richtige Pfad eingetragen. Die �nderungen werden sofort   v     gespeichert.     v 9   Write_ini "netz.ini", "NETZ"   ' * alt - is nich mehr        �      9 	  ��������3    	 Form_Load 4L     X  1       v     verz04:  03/97   v        � D   	  �       � < �  � � �   � �� '�  �� � ��  �   � �� } �  �� � ��  � 0  O p     DV �  �  �  DB  &  �  �   Daten Verzeichnis   O Z  ' �    �   XA  &  �  ,  XA Verzeichnis    O Z   �    � 
 b MOPS  &  �  z  MOPS-Verzeichnis    O Z  r �    �  � OMP &  �  � OMP-Verzeichnis  O Z  � �    �  � LPSOLVE &  �   LP-Solve Verzeichnis   O Z  � �    �  P Working &  �  j Working Directory  O Z  � �    � 
 � GRAPH & v    ..netzplan special �  � Ereignisgraf Verzeichnis   O Z  � �     :      �  �  �    �  �  �    � �� �� Y    O ��   = �  ��p �  ` \  �  �  p c:\  Y �  � c:\  �  �  �  � c:\  �  �  = �    9 	  �������>     Laufwerk1_Change 4*      X  E     Y f �  �   9 	  ��������     Verzeichnis1_Change 4*      X  p      �  �  �  �   9 	  ��������   �