//---------------------------------------------------------------------------

#include <vcl.h>
#include "MainFormMDI.h"
#include "ProblemForm.h"

#pragma hdrstop
//---------------------------------------------------------------------------
USEFORM("ProblemForm.cpp", FormEingabe1);
USEFORM("MainFormMDI.cpp", MainForm);
USEFORM("Hilfe\About.cpp", AboutBox);
USEFORM("options.cpp", OptionsForm);
//---------------------------------------------------------------------------
WINAPI WinMain(HINSTANCE, HINSTANCE, LPSTR, int)
{
	try
	{
		Application->Initialize();
		Application->HelpFile = "FALLPAUSCHALENHILFE.HLP";
		Application->CreateForm(__classid(TMainForm), &MainForm);
		Application->CreateForm(__classid(TAboutBox), &AboutBox);
		Application->Run();
	}
	catch (Exception &exception)
	{
		Application->ShowException(&exception);
	}
	catch (...)
	{
		try
		{
			throw Exception("");
		}
		catch (Exception &exception)
		{
			Application->ShowException(&exception);
		}
	}
	return 0;
}
//---------------------------------------------------------------------------
