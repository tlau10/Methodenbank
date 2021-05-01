namespace GlpkGui
{
    partial class SolverView
    {
        /// <summary> 
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary> 
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Component Designer generated code

        /// <summary> 
        /// Required method for Designer support - do not modify 
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(SolverView));
            this.splitContainer1 = new System.Windows.Forms.SplitContainer();
            this.tableauView = new GlpkGui.TableauView();
            this.tabControl1 = new System.Windows.Forms.TabControl();
            this.tabPage1 = new System.Windows.Forms.TabPage();
            this._LPText = new System.Windows.Forms.TextBox();
            this.tabPage2 = new System.Windows.Forms.TabPage();
            this._MIPText = new System.Windows.Forms.TextBox();
            this.tabPage4 = new System.Windows.Forms.TabPage();
            this._IPText = new System.Windows.Forms.TextBox();
            this.tabPage3 = new System.Windows.Forms.TabPage();
            this._SensText = new System.Windows.Forms.TextBox();
            this.splitContainer1.Panel1.SuspendLayout();
            this.splitContainer1.Panel2.SuspendLayout();
            this.splitContainer1.SuspendLayout();
            this.tabControl1.SuspendLayout();
            this.tabPage1.SuspendLayout();
            this.tabPage2.SuspendLayout();
            this.tabPage4.SuspendLayout();
            this.tabPage3.SuspendLayout();
            this.SuspendLayout();
            // 
            // splitContainer1
            // 
            resources.ApplyResources(this.splitContainer1, "splitContainer1");
            this.splitContainer1.Name = "splitContainer1";
            // 
            // splitContainer1.Panel1
            // 
            this.splitContainer1.Panel1.Controls.Add(this.tableauView);
            // 
            // splitContainer1.Panel2
            // 
            this.splitContainer1.Panel2.Controls.Add(this.tabControl1);
            // 
            // tableauView
            // 
            resources.ApplyResources(this.tableauView, "tableauView");
            this.tableauView.LPProblem = null;
            this.tableauView.Name = "tableauView";
            this.tableauView.NeedsSave = false;
            this.tableauView.SolverView = null;
            // 
            // tabControl1
            // 
            this.tabControl1.Controls.Add(this.tabPage1);
            this.tabControl1.Controls.Add(this.tabPage2);
            this.tabControl1.Controls.Add(this.tabPage4);
            this.tabControl1.Controls.Add(this.tabPage3);
            resources.ApplyResources(this.tabControl1, "tabControl1");
            this.tabControl1.Multiline = true;
            this.tabControl1.Name = "tabControl1";
            this.tabControl1.SelectedIndex = 0;
            // 
            // tabPage1
            // 
            this.tabPage1.Controls.Add(this._LPText);
            resources.ApplyResources(this.tabPage1, "tabPage1");
            this.tabPage1.Name = "tabPage1";
            this.tabPage1.UseVisualStyleBackColor = true;
            // 
            // _LPText
            // 
            resources.ApplyResources(this._LPText, "_LPText");
            this._LPText.Name = "_LPText";
            this._LPText.ReadOnly = true;
            // 
            // tabPage2
            // 
            this.tabPage2.Controls.Add(this._MIPText);
            resources.ApplyResources(this.tabPage2, "tabPage2");
            this.tabPage2.Name = "tabPage2";
            this.tabPage2.UseVisualStyleBackColor = true;
            // 
            // _MIPText
            // 
            resources.ApplyResources(this._MIPText, "_MIPText");
            this._MIPText.Name = "_MIPText";
            this._MIPText.ReadOnly = true;
            // 
            // tabPage4
            // 
            this.tabPage4.Controls.Add(this._IPText);
            resources.ApplyResources(this.tabPage4, "tabPage4");
            this.tabPage4.Name = "tabPage4";
            this.tabPage4.UseVisualStyleBackColor = true;
            // 
            // _IPText
            // 
            resources.ApplyResources(this._IPText, "_IPText");
            this._IPText.Name = "_IPText";
            this._IPText.ReadOnly = true;
            // 
            // tabPage3
            // 
            this.tabPage3.Controls.Add(this._SensText);
            resources.ApplyResources(this.tabPage3, "tabPage3");
            this.tabPage3.Name = "tabPage3";
            this.tabPage3.UseVisualStyleBackColor = true;
            // 
            // _SensText
            // 
            resources.ApplyResources(this._SensText, "_SensText");
            this._SensText.Name = "_SensText";
            this._SensText.ReadOnly = true;
            // 
            // SolverView
            // 
            resources.ApplyResources(this, "$this");
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.splitContainer1);
            this.Name = "SolverView";
            this.splitContainer1.Panel1.ResumeLayout(false);
            this.splitContainer1.Panel2.ResumeLayout(false);
            this.splitContainer1.ResumeLayout(false);
            this.tabControl1.ResumeLayout(false);
            this.tabPage1.ResumeLayout(false);
            this.tabPage1.PerformLayout();
            this.tabPage2.ResumeLayout(false);
            this.tabPage2.PerformLayout();
            this.tabPage4.ResumeLayout(false);
            this.tabPage4.PerformLayout();
            this.tabPage3.ResumeLayout(false);
            this.tabPage3.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.SplitContainer splitContainer1;
        private TableauView tableauView;
        public System.Windows.Forms.TabControl tabControl1;
        private System.Windows.Forms.TabPage tabPage1;
        public System.Windows.Forms.TextBox _LPText;
        private System.Windows.Forms.TabPage tabPage2;
        public System.Windows.Forms.TextBox _MIPText;
        private System.Windows.Forms.TabPage tabPage3;
        public System.Windows.Forms.TextBox _SensText;
        private System.Windows.Forms.TabPage tabPage4;
        public System.Windows.Forms.TextBox _IPText;

        public TableauView TableauView
        {
            get { return tableauView; }
            set { tableauView = value; }
        }

        public void resetTextBoxes()
        {
            _IPText.Text = "";
            _LPText.Text = "";
            _MIPText.Text = "";
            _SensText.Text = "";
        }
    }
}
