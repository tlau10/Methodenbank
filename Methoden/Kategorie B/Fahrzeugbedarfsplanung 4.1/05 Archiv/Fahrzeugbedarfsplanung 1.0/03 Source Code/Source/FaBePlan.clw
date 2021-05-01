; CLW file contains information for the MFC ClassWizard

[General Info]
Version=1
LastClass=CBedarfErgebnisDlg
LastTemplate=CDialog
NewFileInclude1=#include "stdafx.h"
NewFileInclude2=#include "fabeplan.h"
LastPage=0

ClassCount=8
Class1=CBedarfErgebnisDlg
Class2=CEinstellungenDlg
Class3=CFaBePlanApp
Class4=CAboutDlg
Class5=CFaBePlanDoc
Class6=CFaBePlanView
Class7=CMainFrame
Class8=CNeueTourDlg

ResourceCount=6
Resource1=IDD_SOLVER_ERGEBNIS
Resource2=IDD_ABOUTBOX
Resource3=IDD_DIALOG_OPTIONEN
Resource4=IDD_DIALOG_TOUR_EINGEBEN
Resource5=IDD_FABEPLAN_FORM
Resource6=IDR_MAINFRAME

[CLS:CBedarfErgebnisDlg]
Type=0
BaseClass=CDialog
HeaderFile=BedarfErgebnisDlg.h
ImplementationFile=BedarfErgebnisDlg.cpp
Filter=D
VirtualFilter=dWC
LastObject=ID_FILE_OPEN

[CLS:CEinstellungenDlg]
Type=0
BaseClass=CDialog
HeaderFile=EinstellungenDlg.h
ImplementationFile=EinstellungenDlg.cpp

[CLS:CFaBePlanApp]
Type=0
BaseClass=CWinApp
HeaderFile=FaBePlan.h
ImplementationFile=FaBePlan.cpp

[CLS:CAboutDlg]
Type=0
BaseClass=CDialog
HeaderFile=FaBePlan.cpp
ImplementationFile=FaBePlan.cpp
LastObject=CAboutDlg

[CLS:CFaBePlanDoc]
Type=0
BaseClass=CDocument
HeaderFile=FaBePlanDoc.h
ImplementationFile=FaBePlanDoc.cpp
LastObject=CFaBePlanDoc
Filter=N
VirtualFilter=DC

[CLS:CFaBePlanView]
Type=0
BaseClass=CFormView
HeaderFile=FaBePlanView.h
ImplementationFile=FaBePlanView.cpp

[CLS:CMainFrame]
Type=0
BaseClass=CFrameWnd
HeaderFile=MainFrm.h
ImplementationFile=MainFrm.cpp

[CLS:CNeueTourDlg]
Type=0
BaseClass=CDialog
HeaderFile=NeueTourDlg.h
ImplementationFile=NeueTourDlg.cpp

[DLG:IDD_SOLVER_ERGEBNIS]
Type=1
Class=CBedarfErgebnisDlg
ControlCount=4
Control1=IDOK,button,1342242817
Control2=IDC_FAHRZEUG_BEDARF,static,1342177289
Control3=IDC_STATIC,static,1342308352
Control4=IDC_MIN_FAHRZEUGE,static,1342308352

[DLG:IDD_DIALOG_OPTIONEN]
Type=1
Class=CEinstellungenDlg
ControlCount=12
Control1=IDC_STATIC,button,1342177287
Control2=IDC_SOLVER_AUSWAHL,button,1342308361
Control3=IDC_XA_PFAD,edit,1350631552
Control4=IDC_XA_SUCHEN,button,1342242944
Control5=IDC_STATIC,button,1476395017
Control6=IDC_LP_PFAD,edit,1484849280
Control7=IDC_LP_SUCHEN,button,1476460672
Control8=IDC_STATIC,button,1476395017
Control9=IDC_MOPS_PFAD,edit,1484849280
Control10=IDC_MOPS_SUCHEN,button,1476460672
Control11=IDCANCEL,button,1342373888
Control12=IDOK,button,1342242817

[DLG:IDD_ABOUTBOX]
Type=1
Class=CAboutDlg
ControlCount=5
Control1=IDC_STATIC,static,1342181390
Control2=IDC_STATIC,static,1342308480
Control3=IDOK,button,1342373889
Control4=IDC_WEITERE_INFOS,edit,1352730628
Control5=IDC_STATIC,static,1342308352

[DLG:IDD_FABEPLAN_FORM]
Type=1
Class=CFaBePlanView
ControlCount=1
Control1=IDC_FAHRPLAN,SysListView32,1342242821

[DLG:IDD_DIALOG_TOUR_EINGEBEN]
Type=1
Class=CNeueTourDlg
ControlCount=11
Control1=IDC_STATIC,button,1342177287
Control2=IDC_STATIC,static,1342308352
Control3=IDC_ABFAHRT_ORT,edit,1350631552
Control4=IDC_ABFAHRT_ZEIT,SysDateTimePick32,1342242857
Control5=IDC_STATIC,static,1342308352
Control6=IDC_ANKUNFT_ORT,edit,1350631552
Control7=IDC_ANKUNFT_ZEIT,SysDateTimePick32,1342242857
Control8=IDC_STATIC,static,1342308352
Control9=IDC_PERSONEN,edit,1350639744
Control10=IDCANCEL,button,1342242816
Control11=IDOK,button,1342242817

[MNU:IDR_MAINFRAME]
Type=1
Class=?
Command1=ID_FILE_NEW
Command2=ID_FILE_OPEN
Command3=ID_FILE_SAVE
Command4=ID_FILE_SAVE_AS
Command5=ID_FILE_MRU_FILE1
Command6=ID_APP_EXIT
Command7=ID_BEARBEITEN_TOUREINGEBEN
Command8=ID_BEARBEITEN_TOURVERAENDERN
Command9=ID_BEARBEITEN_TOURLOESCHEN
Command10=ID_BEARBEITEN_BEDARFBERECHNEN
Command11=ID_BEARBEITEN_OPTIMUMBERECHNEN
Command12=ID_EINSTELLUNGEN_OPTIONEN
Command13=ID_VIEW_TOOLBAR
Command14=ID_HELP_FINDER
Command15=ID_APP_ABOUT
CommandCount=15

[ACL:IDR_MAINFRAME]
Type=1
Class=?
Command1=ID_FILE_NEW
Command2=ID_FILE_OPEN
Command3=ID_FILE_SAVE
Command4=ID_EDIT_UNDO
Command5=ID_EDIT_CUT
Command6=ID_EDIT_COPY
Command7=ID_EDIT_PASTE
Command8=ID_EDIT_UNDO
Command9=ID_EDIT_CUT
Command10=ID_EDIT_COPY
Command11=ID_EDIT_PASTE
Command12=ID_NEXT_PANE
Command13=ID_PREV_PANE
Command14=ID_CONTEXT_HELP
Command15=ID_HELP
CommandCount=15

[TB:IDR_MAINFRAME]
Type=1
Class=?
Command1=ID_FILE_NEW
Command2=ID_FILE_OPEN
Command3=ID_FILE_SAVE
Command4=ID_BEARBEITEN_TOUREINGEBEN
Command5=ID_BEARBEITEN_TOURVERAENDERN
Command6=ID_BEARBEITEN_TOURLOESCHEN
Command7=ID_BEARBEITEN_BEDARFBERECHNEN
Command8=ID_BEARBEITEN_OPTIMUMBERECHNEN
Command9=ID_APP_ABOUT
CommandCount=9

