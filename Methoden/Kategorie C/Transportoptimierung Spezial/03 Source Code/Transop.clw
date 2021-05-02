; CLW-Datei enthält Informationen für den MFC-Klassen-Assistenten

[General Info]
Version=1
LastClass=CTransopView
LastTemplate=CDialog
NewFileInclude1=#include "stdafx.h"
NewFileInclude2=#include "Transop.h"
LastPage=0

ClassCount=12
Class1=CTransopApp
Class2=CTransopDoc
Class3=CTransopView
Class4=CMainFrame

ResourceCount=10
Resource1=IDD_PRODUCER
Resource2=IDR_TRANSOTYPE
Resource3=IDD_PATHPROP
Class5=CChildFrame
Class6=CAboutDlg
Resource4=IDD_RECEIVER
Resource5=IDD_TRANSOP_FORM
Resource6=IDR_MAINFRAME
Class7=CStretchesDlg
Class8=CProducerDlg
Class9=CReceiverDlg
Resource7=IDD_CONECTIONS
Class10=CTransporterDlg
Resource8=IDD_ABOUTBOX
Class11=CPathPropertiesDlg
Resource9=IDD_TRANSPORTER
Class12=CProgressDlg
Resource10=IDD_PROGRESS

[CLS:CTransopApp]
Type=0
HeaderFile=Transop.h
ImplementationFile=Transop.cpp
Filter=N
BaseClass=CWinApp
VirtualFilter=AC
LastObject=CTransopApp

[CLS:CTransopDoc]
Type=0
HeaderFile=TransopDoc.h
ImplementationFile=TransopDoc.cpp
Filter=N
BaseClass=CDocument
VirtualFilter=DC
LastObject=ID_CALCULATE

[CLS:CTransopView]
Type=0
HeaderFile=TransopView.h
ImplementationFile=TransopView.cpp
Filter=D
BaseClass=CFormView
VirtualFilter=VWC
LastObject=IDC_GK


[CLS:CMainFrame]
Type=0
HeaderFile=MainFrm.h
ImplementationFile=MainFrm.cpp
Filter=T
LastObject=CMainFrame
BaseClass=CMDIFrameWnd
VirtualFilter=fWC


[CLS:CChildFrame]
Type=0
HeaderFile=ChildFrm.h
ImplementationFile=ChildFrm.cpp
Filter=M


[CLS:CAboutDlg]
Type=0
HeaderFile=Transop.cpp
ImplementationFile=Transop.cpp
Filter=D
LastObject=CAboutDlg

[DLG:IDD_ABOUTBOX]
Type=1
Class=CAboutDlg
ControlCount=7
Control1=IDC_STATIC,static,1342177283
Control2=IDC_STATIC,static,1342308480
Control3=IDC_STATIC,static,1342308352
Control4=IDOK,button,1342373889
Control5=IDC_STATIC,static,1342308352
Control6=IDC_STATIC,static,1342308352
Control7=IDC_STATIC,static,1342308352

[MNU:IDR_MAINFRAME]
Type=1
Class=CMainFrame
Command1=ID_FILE_NEW
Command2=ID_APP_EXIT
Command3=ID_PROP_WORKINGDIR
Command4=ID_VIEW_TOOLBAR
Command5=ID_VIEW_STATUS_BAR
Command6=ID_APP_ABOUT
CommandCount=6

[TB:IDR_MAINFRAME]
Type=1
Class=CMainFrame
Command1=ID_FILE_NEW
Command2=ID_FILE_SAVE_AS
Command3=ID_FILE_OPEN
CommandCount=3

[MNU:IDR_TRANSOTYPE]
Type=1
Class=CTransopView
Command1=ID_FILE_NEW
Command2=ID_FILE_CLOSE
Command3=ID_APP_EXIT
Command4=ID_PROP_RECEIVER
Command5=ID_PROP_PRODUCER
Command6=ID_PROP_CONNECTION
Command7=ID_PROP_TRANSPORTER
Command8=ID_PROP_WORKINGDIR
Command9=ID_CALCULATE
Command10=ID_VIEW_TOOLBAR
Command11=ID_VIEW_STATUS_BAR
Command12=ID_WINDOW_NEW
Command13=ID_WINDOW_CASCADE
Command14=ID_WINDOW_TILE_HORZ
Command15=ID_WINDOW_ARRANGE
Command16=ID_APP_ABOUT
CommandCount=16

[ACL:IDR_MAINFRAME]
Type=1
Class=CMainFrame
Command1=ID_FILE_NEW
Command2=ID_FILE_OPEN
Command3=ID_FILE_SAVE
Command4=ID_FILE_PRINT
Command5=ID_EDIT_UNDO
Command6=ID_EDIT_CUT
Command7=ID_EDIT_COPY
Command8=ID_EDIT_PASTE
Command9=ID_EDIT_UNDO
Command10=ID_EDIT_CUT
Command11=ID_EDIT_COPY
Command12=ID_EDIT_PASTE
Command13=ID_NEXT_PANE
Command14=ID_PREV_PANE
CommandCount=14

[DLG:IDD_TRANSOP_FORM]
Type=1
Class=CTransopView
ControlCount=21
Control1=IDC_STATIC,button,1342177287
Control2=IDC_STATIC,button,1342177287
Control3=IDC_STATIC,button,1342177287
Control4=IDC_STATIC,button,1342177287
Control5=IDC_STATIC_TRANS1TEXT,static,1342308352
Control6=IDC_STATIC,static,1342308352
Control7=IDC_STATIC_COSTS1,static,1342308352
Control8=IDC_STATIC,static,1342308352
Control9=IDC_STATIC_CAP1,static,1342308352
Control10=IDC_STATIC_TRANS2TEXT,static,1342308352
Control11=IDC_STATIC,static,1342308352
Control12=IDC_STATIC_COSTS2,static,1342308352
Control13=IDC_STATIC,static,1342308352
Control14=IDC_STATIC_CAP2,static,1342308352
Control15=IDC_PRODPOS,static,1342177287
Control16=IDC_RECPOS,static,1342177287
Control17=IDC_STRETCHPOS,static,1342177287
Control18=IDC_STATIC,button,1342177287
Control19=IDC_SOLUTIONPOS,static,1342177287
Control20=IDC_STATIC,static,1342308352
Control21=IDC_GK,static,1342308352

[DLG:IDD_RECEIVER]
Type=1
Class=CReceiverDlg
ControlCount=11
Control1=IDC_EDIT_RECEIVERNAME,edit,1350631552
Control2=IDC_EDIT_PIECES,edit,1350631552
Control3=IDC_BUTTON_ADD,button,1342242816
Control4=IDC_LIST_RECEIVER,SysListView32,1350664205
Control5=IDC_BUTTON_EDIT,button,1342242816
Control6=IDC_BUTTON_DELETE,button,1342242816
Control7=IDOK,button,1342242817
Control8=IDCANCEL,button,1342242816
Control9=IDC_STATIC,static,1342308352
Control10=IDC_STATIC,button,1342177287
Control11=IDC_STATIC,static,1342308352

[DLG:IDD_CONECTIONS]
Type=1
Class=CStretchesDlg
ControlCount=12
Control1=IDC_COMBO_SOURCE,combobox,1344340226
Control2=IDC_COMBO_DESTINATION,combobox,1344340226
Control3=IDC_EDIT_KM,edit,1350631552
Control4=IDC_BUTTON_ADD,button,1342242816
Control5=IDC_LIST_CONNECTIONS,SysListView32,1350631425
Control6=IDC_BUTTON_DELETE,button,1342242816
Control7=IDOK,button,1342242817
Control8=IDCANCEL,button,1342242816
Control9=IDC_STATIC,static,1342308352
Control10=IDC_STATIC,static,1342308352
Control11=IDC_STATIC,button,1342177287
Control12=IDC_STATIC,static,1342308352

[DLG:IDD_PRODUCER]
Type=1
Class=CProducerDlg
ControlCount=11
Control1=IDC_EDIT_PRODUCERNAME,edit,1350631552
Control2=IDC_EDIT_PIECES,edit,1350631552
Control3=IDC_BUTTON_ADD,button,1342242816
Control4=IDC_LIST_PRODUCER,SysListView32,1350664205
Control5=IDC_BUTTON_EDIT,button,1342242816
Control6=IDC_BUTTON_DELETE,button,1342242816
Control7=IDOK,button,1342242817
Control8=IDCANCEL,button,1342242816
Control9=IDC_STATIC,static,1342308352
Control10=IDC_STATIC,button,1342177287
Control11=IDC_STATIC,static,1342308352

[CLS:CStretchesDlg]
Type=0
HeaderFile=StretchesDlg.h
ImplementationFile=StretchesDlg.cpp
BaseClass=CDialog
Filter=D
LastObject=CStretchesDlg
VirtualFilter=dWC

[CLS:CProducerDlg]
Type=0
HeaderFile=ProducerDlg.h
ImplementationFile=ProducerDlg.cpp
BaseClass=CDialog
Filter=D
LastObject=IDC_EDIT_PRODUCERNAME
VirtualFilter=dWC

[CLS:CReceiverDlg]
Type=0
HeaderFile=ReceiverDlg.h
ImplementationFile=ReceiverDlg.cpp
BaseClass=CDialog
Filter=D
LastObject=CReceiverDlg
VirtualFilter=dWC

[DLG:IDD_TRANSPORTER]
Type=1
Class=CTransporterDlg
ControlCount=16
Control1=IDC_EDIT_NAME1,edit,1350631552
Control2=IDC_EDIT_CAP1,edit,1350631552
Control3=IDC_EDIT_COSTS1,edit,1350631552
Control4=IDC_EDIT_NAME2,edit,1350631552
Control5=IDC_EDIT_CAP2,edit,1350631552
Control6=IDC_EDIT_COSTS2,edit,1350631552
Control7=IDOK,button,1342242817
Control8=IDCANCEL,button,1342242816
Control9=IDC_STATIC,button,1342177287
Control10=IDC_STATIC,static,1342308352
Control11=IDC_STATIC,static,1342308352
Control12=IDC_STATIC,static,1342308352
Control13=IDC_STATIC,button,1342177287
Control14=IDC_STATIC,static,1342308352
Control15=IDC_STATIC,static,1342308352
Control16=IDC_STATIC,static,1342308352

[CLS:CTransporterDlg]
Type=0
HeaderFile=TransporterDlg.h
ImplementationFile=TransporterDlg.cpp
BaseClass=CDialog
Filter=D
VirtualFilter=dWC
LastObject=CTransporterDlg

[DLG:IDD_PATHPROP]
Type=1
Class=CPathPropertiesDlg
ControlCount=9
Control1=IDOK,button,1342242817
Control2=IDC_STATIC,static,1342308352
Control3=IDC_STATIC,static,1342308352
Control4=IDC_EDIT_SOLVERPATH,edit,1350631552
Control5=IDC_EDIT_WORKINGDIR,edit,1350631552
Control6=IDC_BUTTONCHANGESOLVERPATH,button,1342242816
Control7=IDC_BUTTON_CHANGE_WORKINGDIR,button,1342242816
Control8=IDC_STATIC,button,1342177287
Control9=IDCANCEL,button,1342242816

[CLS:CPathPropertiesDlg]
Type=0
HeaderFile=PathPropertiesDlg.h
ImplementationFile=PathPropertiesDlg.cpp
BaseClass=CDialog
Filter=D
LastObject=CPathPropertiesDlg
VirtualFilter=dWC

[CLS:CProgressDlg]
Type=0
HeaderFile=ProgressDlg.h
ImplementationFile=ProgressDlg.cpp
BaseClass=CDialog
Filter=D
LastObject=CProgressDlg

[DLG:IDD_PROGRESS]
Type=1
Class=CProgressDlg
ControlCount=1
Control1=IDC_STATIC,static,1342308352

