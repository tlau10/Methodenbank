using OrAlpha.Main.Repository;
using OrAlpha.Main.Service;
using OrAlpha.Main.View;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Windows.Forms;
using System.Xml;


namespace OrAlpha.Main.App
{
    static class Program
    {
        /// <summary>
        /// Der Haupteinstiegspunkt für die Anwendung.
        /// </summary>
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(createOrAlphaMainFrame());
        }

        private static OrAlphaMainFrame createOrAlphaMainFrame()
        {
            return new OrAlphaMainFrame(MethodService.NewInstance(), VisualORService.NewInstance());
        }
    }
}
