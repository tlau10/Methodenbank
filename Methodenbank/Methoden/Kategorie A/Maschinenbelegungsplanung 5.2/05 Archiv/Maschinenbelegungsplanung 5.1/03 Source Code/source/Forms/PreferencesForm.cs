using System;
using System.Drawing;
using System.Collections;
using System.Collections.Specialized;
using System.ComponentModel;
using System.Windows.Forms;
using System.Data;


public delegate string GetPath( );


namespace Maschinenbelegungsplanung
{
	/// <summary>
	/// Summary description for preferencesForm.
	/// </summary>
	public class PreferencesForm : System.Windows.Forms.Form
	{
		private System.Windows.Forms.Label label;
		private System.Windows.Forms.TextBox textBox;
		private System.Windows.Forms.Button okButton;
		private System.Windows.Forms.Button button2;
		private System.ComponentModel.Container components = null;
		private string labeltext;
		private string textBoxtext;
		private string whatToDo;

		public PreferencesForm()
		{
			InitializeComponent();
		}

		public PreferencesForm( string labelText, string textBoxText, string what )
		{
			this.labeltext = labelText;
			this.textBoxtext = textBoxText;
			this.whatToDo = what;
			InitializeComponent();

			// set input params 
			this.label.Text = labeltext;
			this.textBox.Text = textBoxtext;
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
			this.label = new System.Windows.Forms.Label();
			this.textBox = new System.Windows.Forms.TextBox();
			this.okButton = new System.Windows.Forms.Button();
			this.button2 = new System.Windows.Forms.Button();
			this.SuspendLayout();
			// 
			// label
			// 
			this.label.Location = new System.Drawing.Point(8, 24);
			this.label.Name = "label";
			this.label.Size = new System.Drawing.Size(400, 48);
			this.label.TabIndex = 0;
			// 
			// textBox
			// 
			this.textBox.Location = new System.Drawing.Point(8, 85);
			this.textBox.Name = "textBox";
			this.textBox.Size = new System.Drawing.Size(400, 20);
			this.textBox.TabIndex = 1;
			this.textBox.Text = "";
			// 
			// okButton
			// 
			this.okButton.Location = new System.Drawing.Point(144, 112);
			this.okButton.Name = "okButton";
			this.okButton.Size = new System.Drawing.Size(128, 32);
			this.okButton.TabIndex = 2;
			this.okButton.Text = "OK";
			this.okButton.Click += new System.EventHandler(this.okButton_Click);
			// 
			// button2
			// 
			this.button2.Location = new System.Drawing.Point(280, 112);
			this.button2.Name = "button2";
			this.button2.Size = new System.Drawing.Size(128, 32);
			this.button2.TabIndex = 3;
			this.button2.Text = "Abbrechen";
			this.button2.Click += new System.EventHandler(this.button2_Click);
			// 
			// preferencesForm
			// 
			this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
			this.ClientSize = new System.Drawing.Size(416, 158);
			this.Controls.AddRange(new System.Windows.Forms.Control[] {
																		  this.button2,
																		  this.okButton,
																		  this.textBox,
																		  this.label});
			this.MaximizeBox = false;
			this.MaximumSize = new System.Drawing.Size(424, 192);
			this.MinimizeBox = false;
			this.MinimumSize = new System.Drawing.Size(424, 192);
			this.Name = "preferencesForm";
			this.Text = "Einstellungen";
			this.ResumeLayout(false);

		}
		#endregion

		private void okButton_Click(object sender, System.EventArgs e)
		{
			this.labeltext = label.Text;
			if( this.whatToDo.Equals("solverPath") )
			{
				Form1.solverPath = textBox.Text;
			}

			if( this.whatToDo.Equals("workingPath") )
			{
				Form1.workingDir = textBox.Text;
			}
			this.Dispose( );
			this.Close( );
		}

		private void button2_Click(object sender, System.EventArgs e)
		{
			this.Dispose( );
			this.Close( );
		}
	}
}
