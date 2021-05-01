using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Xml;
using System.Diagnostics;
using OrAlpha.Main.Service;
using OrAlpha.Main.DomainMethodenbank;
using OrAlpha.Main.View.Administration;
using OrAlpha.Main.DomainVisualOR;
using System.Threading;
using OrAlpha.Main.Util;
using OrAlpha.Main.View;
using System.IO;

namespace OrAlpha.Main.View
{

    public partial class OrAlphaMainFrame : Form
    {
        private Solveroutput solverOutput;
        private MethodService methodService;
        private VisualORService visualORservice;
        private String htmlPath = Properties.Settings.Default.HtmlPath;
        private String placeholderHtmlFile = Properties.Settings.Default.PlaceholderHtmlFile;
        private String visualORTreeview = Properties.Settings.Default.VisualORTreeview;
        private String solverSolution = Properties.Settings.Default.SolverSolution;
        private String solverProcess = Properties.Settings.Default.SolverProcess;

        public OrAlphaMainFrame(MethodService methodService, VisualORService visualORservice)
        {
            this.methodService = methodService;
            this.visualORservice = visualORservice;
           
            InitializeComponent();

            LoadMethodTreeView();
            LoadTreeViewVisualOR();

            webBrowser.CanGoBackChanged += new EventHandler(webBrowser_CanGoBackChanged);
            webBrowser.CanGoForwardChanged += new EventHandler(webBrowser_CanGoForwardChanged);  

            webBrowser.ObjectForScripting = new ScriptManager(this);

           
        }

        private void LoadMethodTreeView()
        {
            methodTreeView.Nodes.Clear();
            methodTreeView.Nodes.Add(methodService.GetMethodTree());
        }

        public void LoadTreeViewVisualOR()
        {
            treeView_visualOR.Nodes.Clear();
            treeView_visualOR.Nodes.Add(visualORservice.CreateTreeView());
        }

        private void treeView_visualOR_AfterSelect(object sender, TreeViewEventArgs e)
        {
            IElement element = visualORservice.FindElementByName(e.Node.Text);
            Console.WriteLine(e.Node.Text);
            Console.WriteLine(element.HtmlFile);
            if (!String.IsNullOrEmpty(element.HtmlFile))
                SetBrowserUrl(element.HtmlFile);
            else
                SetBrowserUrl(htmlPath + placeholderHtmlFile);
            DisableButtons();
        }

        private void AfterMethodTreeViewSelect(object sender, TreeViewEventArgs args)
        {
            IMethodbaseElement element = methodService.FindMethodbaseElementByName(args.Node.Text);
            Console.WriteLine(args.Node.Text);
            if (!String.IsNullOrEmpty(element.HtmlFile))
                SetBrowserUrl(htmlPath + element.HtmlFile);
            else
                SetBrowserUrl(htmlPath + placeholderHtmlFile);
            SetButtonStates(element);
        }

        private void SetBrowserUrl(string url)
        {
            try
            {
                webBrowser.Url = new Uri(url);
                statusLabel.Text = url;
            }
            catch (UriFormatException ex)
            { 
                Console.WriteLine("Exception " + ex.Source + " with description: " + ex.Message + " got catched");
                MessageBox.Show("Exception: " + ex.Message, " a placeholder html gets displayed");
                webBrowser.Url = new Uri(placeholderHtmlFile);
            }
        }

        private void SetButtonStates(IMethodbaseElement element)
        {
            if (element is Method)
                SetButtonStates((Method) element);
            else
                DisableButtons();     
        }

        private void SetButtonStates(Method method)
        {
            executeButton.Enabled = true;
            documentationButton.Enabled = false;
            if (!String.IsNullOrEmpty(method.DocumentationPath))
                documentationButton.Enabled = true;
        }

        private void DisableButtons()
        {
            executeButton.Enabled = false;
            documentationButton.Enabled = false;
        }      
 
        private void OnExecuteButtonClick(object sender, EventArgs e)
        {
            try 
            {
                methodService.ExecuteMethod(methodTreeView.SelectedNode.Text);
            }
            catch (Win32Exception exception)
            {
                MessageBox.Show("Die ausführbare Datei konnte nicht gefunden werden!", "Ungültiger Methodenpfad");
            }
           
        }

        private void OnDocumentationButtonClick(object sender, EventArgs e)
        {
            try 
            {
            methodService.OpenDocumentation(methodTreeView.SelectedNode.Text);
            }
            catch (Win32Exception exception)
            {
                MessageBox.Show("Der Dokumentationspfad dieser Methode ist ungültig!","Ungültiger Dokumentationspfad");
            }
        }

        private void OnSetXmlPathMenuItemClick(object sender, EventArgs e)
        {
            new SetXmlConfigurationFileFrame().Show();
        }

        private void OnSetHtmlPathMenuItemClick(object sender, EventArgs e)
        {
            new SetHtmlPathFrame().Show();
        }

        private void OnPrintMenuItemClick(object sender, EventArgs e)
        {
            webBrowser.ShowPrintDialog();
        }

        private void OnInfoMenuItemClick(object sender, EventArgs e)
        {
            new InfoBox().Show();
        }

        private void OnSetPlaceholderHtmlFileMenuItemClick(object sender, EventArgs e)
        {
            new SetPlaceholderHtmlFrame().Show();
        }

        private void OnExitMenuItemClick(object sender, EventArgs e)
        {
            Application.Exit();
        }

        private void OnRefreshMenuItemClick(object sender, EventArgs e)
        {
            LoadMethodTreeView();
            LoadTreeViewVisualOR();
        }

        private void OnHelpfileMenuItemClick(object sender, EventArgs e)
        {
            SetBrowserUrl(Properties.Settings.Default.HelpFile);
        }

        private void OnAdministrationToolStripMenuItemClick(object sender, EventArgs e)
        {
            new AdminPasswordFrame(methodService).Show();
        }


        /// <summary>
        /// Solver-Aufruf vom ScriptManager, startet den Solver und gibt anschließend das Ergebnis aus
        /// </summary>
        /// <param name="fileName">Pfad zur bat-Datei des Solvers</param>
        public void Solve(String fileName)
        {
            StartSolveProcess(fileName);      
            ReadProcessOutput(fileName);
        }

        /// <summary>
        /// Ruft den Solver auf
        /// </summary>
        /// <param name="fileName">Pfad zur bat-Datei des Solvers</param>
        private void StartSolveProcess(String fileName)
        {     
            int exitCode;
            ProcessStartInfo pInfo;
            Process file;
            pInfo = new ProcessStartInfo("cmd.exe", "/c"  + solverProcess + fileName);

            file = Process.Start(pInfo);
            file.WaitForExit();
            exitCode = file.ExitCode;
            file.Close();
        }

        /// <summary>
        /// Gibt das Ergebniss des Solvers aus, die 'solution.out' Datei hinterlässt der Solver im selben Ordner der bat-Datei.
        /// Durch Konkadieren wird die Output-Datei angesteuert.
        /// Der Title des Ausgabefensters wird über auslesen des Dateinamens an den jeweiligen Solver angepasst
        /// </summary>
        /// <param name="filename">Teilpfad zur Ausgabe 'solution.out'</param>
        private void ReadProcessOutput(string filename)
        {
            
            string output;
            string outputFilename = filename.Substring(0, filename.IndexOf("\\"));
            output = System.IO.File.ReadAllText(Path.Combine(solverSolution + outputFilename,"solution.out"));

            solverOutput = new Solveroutput();
            int start = filename.IndexOf("\\") + 1;
            int end = filename.IndexOf(".");
            string title = filename.Substring((start), (end-start));
          
            solverOutput.ChangeTitle(title);
            solverOutput.showOutput(output);
            solverOutput.Show();
        }

        private void buttonForward_Click(object sender, EventArgs e)
        {
            webBrowser.GoForward();
        }

        private void buttonBackward_Click(object sender, EventArgs e)
        {
            webBrowser.GoBack();
        }

        private void webBrowser_CanGoBackChanged(object sender, EventArgs e)
        {
            buttonBackward.Enabled = webBrowser.CanGoBack;
        }

        private void webBrowser_CanGoForwardChanged(object sender, EventArgs e)
        {
            buttonForward.Enabled = webBrowser.CanGoForward;
        }

    }
}
