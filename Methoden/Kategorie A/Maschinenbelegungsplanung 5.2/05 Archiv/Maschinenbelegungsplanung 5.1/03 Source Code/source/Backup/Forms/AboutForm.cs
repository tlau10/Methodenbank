using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;

namespace Maschinenbelegungsplanung
{
	/// <summary>
	/// Summary description for aboutForm.
	/// </summary>
	public class AboutForm : System.Windows.Forms.Form
	{
		private System.Windows.Forms.RichTextBox richTextBox1;
		private System.ComponentModel.Container components = null;


		public AboutForm(Size inSize)
		{
			inSize.Height -= 400;
			this.ClientSize = inSize;
			InitializeComponent();

		}

		/// <summary>
		/// Clean up any resources being used.
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

		#region Windows Form Designer generated code
		/// <summary>
		/// Required method for Designer support - do not modify
		/// the contents of this method with the code editor.
		/// </summary>
		private void InitializeComponent()
		{
			this.richTextBox1 = new System.Windows.Forms.RichTextBox();
			this.SuspendLayout();
			// 
			// richTextBox1
			// 
			this.richTextBox1.BackColor = System.Drawing.SystemColors.Control;
			this.richTextBox1.Font = new System.Drawing.Font("Verdana", 8.95F);
			this.richTextBox1.Location = new System.Drawing.Point(8, 8);
			this.richTextBox1.Name = "richTextBox1";
			this.richTextBox1.ReadOnly = true;
			this.richTextBox1.Size = new System.Drawing.Size(292, 266);
			this.richTextBox1.TabIndex = 0;
			this.richTextBox1.Text = "�ber Maschinenbelegungsplan\nDiese Version WS02/03 des Maschinenbelegungsplan wurd" +
				"e entwickelt von:\nMike Fr�hlich   ( m.froehlich@fh-konstanz.de )\nDirk Anderhuber" +
				" ( anderhub@fh-konstanz.de )\nSven Bohnenstengel ( svol@fh-konstanz.de )\n\nDiese V" +
				"ersion SS03 wurde weiterentwickelt von:\nFr�d�ric R. Bluth (sams@fh-konstanz.de)\nJean-Pierre Koenig (jpkoenig@fh-konstanz.de)\n\n\nDie Maschinenanzahl\nDiese Version sieh" +
				"t nur die Berechnung f�r eine Maschine vor. \nF�r folgende Gruppen, die an diesem" +
				" Projekt weiterarbeiten, haben wir bereits die \"Berechnung f�r mehrere Maschinen" +
				"\" vorbereitet.\n\nDatei �ffnen (Ctrl + O)\nBereits abgespeicherte XML Dateien k�nne" +
				"n hier wieder ge�ffnet werden. Als Startordner dient das eingestellte Arbeitsver" +
				"zeichnis.\n\nDatei speichern (Ctrl + S)\nUm eingegebene Daten dauerhaft zu sichern," +
				" k�nnen diese im XML Format abgespeichert werden. \n\nDatei drucken (Ctrl + D)\nAus" +
				" zeitlichen Gr�nden wurde diese Funktion nicht implementiert. \n\nDie Auftragsdate" +
				"n\nHier k�nnen einzelne Aufr�ge angelegt werden.\n\n \n \nNr:\tJedem Auftrag mu� eine " +
				"eindeutige Auftragsnummer zugewiesen werden\t   \nBezeichnung: \tHier kann dem ausg" +
				"ew�hltem Auftrag ein Name gegeben werden\t   \nDauer: \tLegt die Abarbeitungsdauer " +
				"des Auftrags fest\t   \nPerioden: \tDurch die Perioden wird festgelegt, in welcher " +
				"Zeitspanne die Abarbeitung des Auftrags erfolgen mu�\t \n\nDie Maschinendaten\nF�r j" +
				"ede Periode kann hier festgelegt werden, wieviele Maschinen verf�gbar sind \n\n \n " +
				"\nPeriodenNr:\tLegt die Periode fest\t   \nMachinen: \tEntspricht der Anzahl der in d" +
				"ieser Periode verf�gbaren Maschinen\t \n\nAusgabeoption numerisch / graphisch\nIm Au" +
				"genblick k�nnen Ergebnisse nur \"numerisch\" ausgegeben werden. F�r Nachfolgeproje" +
				"kte haben wir aber die M�glichkeit vorgesehen, das erhaltene Ergebnis auch graph" +
				"isch darzustellen\n\nBerechnen (F5)\nNach Eingabe der Auftrags- und Maschinendaten " +
				"kann das Ergebnis vom ausgew�hlten Solver berechnet werden lassen. Die vom Solve" +
				"r errechneten Werte, werden auf der Programmoberfl�che dargestellt.\n\nDas Arbeits" +
				"verzeichnis \nHier erfolgt die Einstellung des Arbeitsverzeichnisses. Voreingeste" +
				"llt ist \"C:\\Temp\".\n\n\nDas Arbeitsverzeichnis \nHier erfolgt die Einstellung des So" +
				"verpfades, damit das Programm wei�, wo sich der zu verwendende Solver befindet.\n" +
				"\n\nDie Solver\nIn dieser Version kann nur der LP-Solve als Solver verwendet werden" +
				". Unsere Klassen sind aber so ausgelegt, da� ohne viel Aufwand auch noch andere " +
				"Solver hinzugef�gt werden k�nnten.\nIm Programm kann daher noch nichts anderes au" +
				"sgew�hlt werden. \n\n";
			// 
			// aboutForm
			// 
			this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
			this.ClientSize = new System.Drawing.Size(292, 266);
			this.Controls.AddRange(new System.Windows.Forms.Control[] {
																		  this.richTextBox1});
			this.Name = "aboutForm";
			this.Text = "aboutForm";
			this.Resize += new System.EventHandler(this.aboutForm_Resize);
			this.ResumeLayout(false);

		}
		#endregion


		private void aboutForm_Resize(object sender, System.EventArgs e)
		{
			Size mSize=new Size(this.ClientSize.Width-8,this.ClientSize.Height-8);
			richTextBox1.ClientSize=mSize;		
		}
	}
}
