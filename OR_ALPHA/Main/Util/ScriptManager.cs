using OrAlpha.Main.View;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;

namespace OrAlpha.Main.Util
{
    [ComVisible(true)]
    public class ScriptManager
    {

        private OrAlphaMainFrame mainFrame;

        public ScriptManager(OrAlphaMainFrame form)
        {
            mainFrame = form;
        }

        public void CallSolver(String fileName)
        {
            mainFrame.Solve(fileName);
        }


    }
}
