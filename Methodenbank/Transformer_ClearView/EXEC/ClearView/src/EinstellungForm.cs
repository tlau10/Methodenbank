using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;

namespace Clearview
{
	/// <summary>
	/// Zusammenfassung für EinstellungForm.
	/// </summary>
	public class EinstellungForm : System.Windows.Forms.Form
	{
		private System.Windows.Forms.TextBox JavaBox;
		private System.Windows.Forms.Label javaLbl;
		private System.Windows.Forms.Label TransformerLbl;
		private System.Windows.Forms.TextBox TransformBox;
		private System.Windows.Forms.Button OkBtn;
		private System.Windows.Forms.Button CancelBtn;
		private AccessIni theAccessIni;
		/// <summary>
		/// Erforderliche Designervariable.
		/// </summary>
		private System.ComponentModel.Container components = null;

		public EinstellungForm()
		{
			//
			// Erforderlich für die Windows Form-Designerunterstützung
			//
			InitializeComponent();

			//
			// TODO: Fügen Sie den Konstruktorcode nach dem Aufruf von InitializeComponent hinzu
			//
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
			this.JavaBox = new System.Windows.Forms.TextBox();
			this.javaLbl = new System.Windows.Forms.Label();
			this.TransformerLbl = new System.Windows.Forms.Label();
			this.TransformBox = new System.Windows.Forms.TextBox();
			this.OkBtn = new System.Windows.Forms.Button();
			this.CancelBtn = new System.Windows.Forms.Button();
			this.SuspendLayout();
			// 
			// JavaBox
			// 
			this.JavaBox.Location = new System.Drawing.Point(96, 16);
			this.JavaBox.Name = "JavaBox";
			this.JavaBox.Size = new System.Drawing.Size(352, 22);
			this.JavaBox.TabIndex = 0;
			this.JavaBox.Text = "";
			// 
			// javaLbl
			// 
			this.javaLbl.Location = new System.Drawing.Point(8, 16);
			this.javaLbl.Name = "javaLbl";
			this.javaLbl.Size = new System.Drawing.Size(80, 24);
			this.javaLbl.TabIndex = 1;
			this.javaLbl.Text = "Java Pfad ";
			// 
			// TransformerLbl
			// 
			this.TransformerLbl.Location = new System.Drawing.Point(8, 48);
			this.TransformerLbl.Name = "TransformerLbl";
			this.TransformerLbl.Size = new System.Drawing.Size(80, 40);
			this.TransformerLbl.TabIndex = 2;
			this.TransformerLbl.Text = "Pfad zum Transformer";
			// 
			// TransformBox
			// 
			this.TransformBox.Location = new System.Drawing.Point(96, 48);
			this.TransformBox.Name = "TransformBox";
			this.TransformBox.Size = new System.Drawing.Size(352, 22);
			this.TransformBox.TabIndex = 3;
			this.TransformBox.Text = "";
			// 
			// OkBtn
			// 
			this.OkBtn.Location = new System.Drawing.Point(144, 88);
			this.OkBtn.Name = "OkBtn";
			this.OkBtn.Size = new System.Drawing.Size(88, 23);
			this.OkBtn.TabIndex = 4;
			this.OkBtn.Text = "Ok";
			this.OkBtn.Click += new System.EventHandler(this.OkBtn_Click);
			// 
			// CancelBtn
			// 
			this.CancelBtn.Location = new System.Drawing.Point(240, 88);
			this.CancelBtn.Name = "CancelBtn";
			this.CancelBtn.Size = new System.Drawing.Size(88, 23);
			this.CancelBtn.TabIndex = 5;
			this.CancelBtn.Text = "Abbrechen";
			this.CancelBtn.Click += new System.EventHandler(this.CancelBtn_Click);
			// 
			// EinstellungForm
			// 
			this.AutoScaleBaseSize = new System.Drawing.Size(6, 15);
			this.ClientSize = new System.Drawing.Size(454, 126);
			this.Controls.Add(this.CancelBtn);
			this.Controls.Add(this.OkBtn);
			this.Controls.Add(this.TransformBox);
			this.Controls.Add(this.TransformerLbl);
			this.Controls.Add(this.javaLbl);
			this.Controls.Add(this.JavaBox);
			this.Name = "EinstellungForm";
			this.Text = "Einstellungen";
			this.ResumeLayout(false);

		}
		#endregion

		private void OkBtn_Click(object sender, System.EventArgs e)
		{
			if(theAccessIni!=null)
			{
				theAccessIni.setJavaPath(JavaBox.Text);
				theAccessIni.setTransformerPath(TransformBox.Text);
				if (theAccessIni.Save()==false)
					MessageBox.Show("Speicherfehler: " + theAccessIni.getErrorText());
			}
			this.Close();
		}

		public void setAccessIni(AccessIni aAccessIni)
		{
			if(aAccessIni==null)
				return;
			this.theAccessIni=aAccessIni;
			JavaBox.Text=theAccessIni.getJavaPath();
			TransformBox.Text=theAccessIni.getTransformerPath();
		}

		private void CancelBtn_Click(object sender, System.EventArgs e)
		{
			this.Close();
		}


	}
}
