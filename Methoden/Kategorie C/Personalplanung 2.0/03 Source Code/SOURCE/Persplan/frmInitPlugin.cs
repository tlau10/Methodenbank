using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;
using System.Xml;
using System.Reflection;
using System.Runtime.Remoting;
using PersplanSolverInterface;

namespace Persplan
{
	public class sSolver
	{
		public string SolverName;
		public string SolverClass;
		public string Library;
		public string ConfigurationFile;

		public sSolver() {}
		public override string ToString()
		{
			return this.SolverName;
		}
		
		public IPersplanSolver SolverObject = null;
	}

	/// <summary>
	/// Zusammenfassung für frmInitPlugin.
	/// </summary>
	public class frmInitPlugin : System.Windows.Forms.Form
	{
		private System.Windows.Forms.Label lblNumberPlugin;
		private System.Windows.Forms.Label lblNumberPluginCount;
		private System.Windows.Forms.Label lblCurrentAction;
		private System.Windows.Forms.Label lblPleaseWait;
		private System.Collections.ArrayList Solvers;

		/// <summary>
		/// Erforderliche Designervariable.
		/// </summary>
		private System.ComponentModel.Container components = null;

		public frmInitPlugin()
		{
			//
			// Erforderlich für die Windows Form-Designerunterstützung
			//
			InitializeComponent();
			this.Solvers = new ArrayList();
			InitializePlugins();
		}

		/// <summary>
		/// Die verwendeten Ressourcen bereinigen.
		/// </summary>
		protected override void Dispose( bool disposing )
		{
			if( disposing )
			{
				if(components != null)
				{
					components.Dispose();
				}
			}
			base.Dispose( disposing );
		}

		#region Vom Windows Form-Designer generierter Code
		/// <summary>
		/// Erforderliche Methode für die Designerunterstützung. 
		/// Der Inhalt der Methode darf nicht mit dem Code-Editor geändert werden.
		/// </summary>
		private void InitializeComponent()
		{
			this.lblNumberPlugin = new System.Windows.Forms.Label();
			this.lblNumberPluginCount = new System.Windows.Forms.Label();
			this.lblCurrentAction = new System.Windows.Forms.Label();
			this.lblPleaseWait = new System.Windows.Forms.Label();
			this.SuspendLayout();
			// 
			// lblNumberPlugin
			// 
			this.lblNumberPlugin.Location = new System.Drawing.Point(48, 72);
			this.lblNumberPlugin.Name = "lblNumberPlugin";
			this.lblNumberPlugin.Size = new System.Drawing.Size(144, 23);
			this.lblNumberPlugin.TabIndex = 0;
			this.lblNumberPlugin.Text = "Anzahl registrierter Plugins:";
			// 
			// lblNumberPluginCount
			// 
			this.lblNumberPluginCount.Location = new System.Drawing.Point(200, 72);
			this.lblNumberPluginCount.Name = "lblNumberPluginCount";
			this.lblNumberPluginCount.Size = new System.Drawing.Size(32, 24);
			this.lblNumberPluginCount.TabIndex = 1;
			// 
			// lblCurrentAction
			// 
			this.lblCurrentAction.Location = new System.Drawing.Point(48, 104);
			this.lblCurrentAction.Name = "lblCurrentAction";
			this.lblCurrentAction.Size = new System.Drawing.Size(184, 24);
			this.lblCurrentAction.TabIndex = 2;
			// 
			// lblPleaseWait
			// 
			this.lblPleaseWait.Location = new System.Drawing.Point(40, 16);
			this.lblPleaseWait.Name = "lblPleaseWait";
			this.lblPleaseWait.Size = new System.Drawing.Size(192, 32);
			this.lblPleaseWait.TabIndex = 3;
			this.lblPleaseWait.Text = "Bitte warten Sie, während die Solver-Plugins initialisiert werden.";
			// 
			// frmInitPlugin
			// 
			this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
			this.ClientSize = new System.Drawing.Size(272, 165);
			this.Controls.Add(this.lblPleaseWait);
			this.Controls.Add(this.lblCurrentAction);
			this.Controls.Add(this.lblNumberPluginCount);
			this.Controls.Add(this.lblNumberPlugin);
			this.Name = "frmInitPlugin";
			this.Text = "Initialisieren der Solver-Plugins";
			this.ResumeLayout(false);

		}
		#endregion

		void InitializePlugins()
		{
			// read all plugins from App.config
			try
			{
				ReadPlugins();
			}
			catch (Exception ex)
			{
				MessageBox.Show("Beim Einlesen der Solver-Registrierungen ist ein Fehler aufgetreten:\n\n" + ex.ToString(), "Fehler beim Lesen von solver.xml");
				Application.Exit();
			}
			if (this.Solvers.Count > 0)
			{
				this.lblNumberPluginCount.Text = this.Solvers.Count.ToString();
			}
			else
			{
				MessageBox.Show("Es sind keine Solver konfiguriert. Sie können deshalb keine Personalplanungsprobleme lösen.");
				return;
			}

			// initialize each solver
			ArrayList FailedSolvers = new ArrayList();
			foreach (sSolver solver in this.Solvers)
			{
				try
				{
					ObjectHandle hdlObject;
					Assembly SolverAssembly = Assembly.LoadFrom(solver.Library);

					hdlObject = Activator.CreateInstanceFrom(solver.Library, solver.SolverClass);
					IPersplanSolver ConcreteSolver = hdlObject.Unwrap() as IPersplanSolver;

					if (ConcreteSolver == null)
					{
						throw (new ApplicationException("Solver class " + solver.SolverClass + " not found in " + solver.Library));
					}
					if (!ConcreteSolver.InitSolver(solver.ConfigurationFile))
					{
						throw (new ApplicationException("Das Solverplugin " + solver.SolverClass + " konnte nicht initialisiert werden."));
					}
					solver.SolverObject = ConcreteSolver;
				}
				catch (Exception ex)
				{
					MessageBox.Show (ex.ToString(), "Fehler beim Laden eines Solver-Plugins");
					FailedSolvers.Add(solver);
				}
			}
			// remove failed solvers from list
			foreach(sSolver solver in FailedSolvers)
			{
				this.Solvers.Remove(solver);
			}
			if (this.Solvers.Count == 0)
			{
				MessageBox.Show("Es konnten keine Solver initialisiert werden. Sie können kein Personalplanungsproblem lösen, bis das Problem behoben ist.");
			}
			return;
		}

		void ReadPlugins()
		{
			// open informations in an XML file
			string ConfigFile = "solver.xml";
			if (!System.IO.File.Exists(ConfigFile))
			{
				MessageBox.Show("Die Konfigurationsdatei konnte nicht gefunden werden.");
				return;
			}

			XmlTextReader reader = new XmlTextReader(ConfigFile);			
			reader.ReadStartElement("solvers");
			while (reader.IsStartElement("solver"))
			{
				sSolver Solver = new sSolver();
				reader.ReadStartElement("solver");

				reader.ReadStartElement("name");				
				Solver.SolverName = reader.ReadString();
				reader.ReadEndElement();

				reader.ReadStartElement("class");				
				Solver.SolverClass = reader.ReadString();
				reader.ReadEndElement();

				reader.ReadStartElement("library");
				Solver.Library = reader.ReadString();
				reader.ReadEndElement();

				reader.ReadStartElement("configFile");
				Solver.ConfigurationFile = reader.ReadString();
				reader.ReadEndElement();
				reader.ReadEndElement();

				this.Solvers.Add(Solver);
			}
	
			reader.Close();
		}


		public System.Collections.ArrayList GetPlugins()
		{
			return this.Solvers;
		}
	}
}
