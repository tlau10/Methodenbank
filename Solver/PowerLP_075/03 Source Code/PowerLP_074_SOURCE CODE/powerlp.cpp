//---------------------------------------------------------------------
#include <vcl.h>
#include "registry.h"
#pragma hdrstop
//---------------------------------------------------------------------
USEFORM("Main.cpp", MainForm);
USEFORM("ChildWin.cpp", MDIChild);
USERES("powerlp.res");
USEFORM("about.cpp", AboutBox);
USEUNIT("registry.cpp");
USEUNIT("lpmodell.cpp");
USEUNIT("common.cpp");
USEUNIT("lp_lpi.cpp");
USEUNIT("lp_xaeq.cpp");
USEUNIT("solver.cpp");
USEUNIT("lp_mps.cpp");
USEUNIT("lp_lpsolvi.cpp");
USEFORM("options.cpp", OptionsForm);
//---------------------------------------------------------------------------
WINAPI WinMain(HINSTANCE, HINSTANCE, LPSTR, int)
{
      
	Application->Initialize();
	Application->Title = "Power-LP";
        reg_init();
     Application->CreateForm(__classid(TMainForm), &MainForm);
  Application->CreateForm(__classid(TAboutBox), &AboutBox);
  Application->CreateForm(__classid(TOptionsForm), &OptionsForm);
  Application->Run();
     
	return 0;
}
//---------------------------------------------------------------------
