using System;
using System.Collections.Generic;
using System.Linq;
using System.Windows.Forms;

using System.Reflection;
using System.Resources;
using System.Globalization;

namespace GlpkGui
{
    static class Program
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            MainForm Form  = new MainForm();
            Application.Run(Form);
            //ResourceManager resmgr = new ResourceManager("WindowsFormsApplication2.resources",
             //                 Assembly.GetEntryAssembly());
            //Console.WriteLine(resmgr.GetString("test1"));
        }
    }
}
