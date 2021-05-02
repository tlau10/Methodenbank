using System.Xml;



namespace OrAlpha.Main.View
{
    partial class OrAlphaMainFrame
    {
        /// <summary>
        /// Erforderliche Designervariable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Verwendete Ressourcen bereinigen.
        /// </summary>
        /// <param name="disposing">True, wenn verwaltete Ressourcen gelöscht werden sollen; andernfalls False.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }



        #region Vom Windows Form-Designer generierter Code

        /// <summary>
        /// Erforderliche Methode für die Designerunterstützung.
        /// Der Inhalt der Methode darf nicht mit dem Code-Editor geändert werden.
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(OrAlphaMainFrame));
            this.menuStrip = new System.Windows.Forms.MenuStrip();
            this.fileMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.refreshMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.printMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.fileMenuToolStripSeparator = new System.Windows.Forms.ToolStripSeparator();
            this.exitMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.optionsMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.setXmlPathMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.setHtmlPathMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.setPlaceholderHtmlFileMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.administrationToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.helpMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.helpfileMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.infoMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.splitContainer = new System.Windows.Forms.SplitContainer();
            this.splitContainer1 = new System.Windows.Forms.SplitContainer();
            this.methodTreeView = new System.Windows.Forms.TreeView();
            this.splitContainer2 = new System.Windows.Forms.SplitContainer();
            this.treeView_visualOR = new System.Windows.Forms.TreeView();
            this.documentationButton = new System.Windows.Forms.Button();
            this.executeButton = new System.Windows.Forms.Button();
            this.webBrowser = new System.Windows.Forms.WebBrowser();
            this.statusLabel = new System.Windows.Forms.ToolStripStatusLabel();
            this.statusStrip = new System.Windows.Forms.StatusStrip();
            this.buttonBackward = new System.Windows.Forms.Button();
            this.buttonForward = new System.Windows.Forms.Button();
            this.menuStrip.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.splitContainer)).BeginInit();
            this.splitContainer.Panel1.SuspendLayout();
            this.splitContainer.Panel2.SuspendLayout();
            this.splitContainer.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.splitContainer1)).BeginInit();
            this.splitContainer1.Panel1.SuspendLayout();
            this.splitContainer1.Panel2.SuspendLayout();
            this.splitContainer1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.splitContainer2)).BeginInit();
            this.splitContainer2.Panel1.SuspendLayout();
            this.splitContainer2.Panel2.SuspendLayout();
            this.splitContainer2.SuspendLayout();
            this.statusStrip.SuspendLayout();
            this.SuspendLayout();
            // 
            // menuStrip
            // 
            this.menuStrip.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.fileMenuItem,
            this.optionsMenuItem,
            this.helpMenuItem});
            this.menuStrip.Location = new System.Drawing.Point(0, 0);
            this.menuStrip.Name = "menuStrip";
            this.menuStrip.Size = new System.Drawing.Size(1117, 24);
            this.menuStrip.TabIndex = 0;
            this.menuStrip.Text = "menuStrip";
            // 
            // fileMenuItem
            // 
            this.fileMenuItem.BackColor = System.Drawing.SystemColors.Control;
            this.fileMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.refreshMenuItem,
            this.printMenuItem,
            this.fileMenuToolStripSeparator,
            this.exitMenuItem});
            this.fileMenuItem.ImageTransparentColor = System.Drawing.Color.Transparent;
            this.fileMenuItem.Name = "fileMenuItem";
            this.fileMenuItem.Size = new System.Drawing.Size(46, 20);
            this.fileMenuItem.Text = "Datei";
            // 
            // refreshMenuItem
            // 
            this.refreshMenuItem.Image = ((System.Drawing.Image)(resources.GetObject("refreshMenuItem.Image")));
            this.refreshMenuItem.Name = "refreshMenuItem";
            this.refreshMenuItem.ShortcutKeys = System.Windows.Forms.Keys.F5;
            this.refreshMenuItem.Size = new System.Drawing.Size(161, 22);
            this.refreshMenuItem.Text = "Aktualisieren";
            this.refreshMenuItem.ToolTipText = "Aktualisierung des Methodentrees.";
            this.refreshMenuItem.Click += new System.EventHandler(this.OnRefreshMenuItemClick);
            // 
            // printMenuItem
            // 
            this.printMenuItem.Image = ((System.Drawing.Image)(resources.GetObject("printMenuItem.Image")));
            this.printMenuItem.ImageTransparentColor = System.Drawing.Color.White;
            this.printMenuItem.Name = "printMenuItem";
            this.printMenuItem.ShortcutKeys = ((System.Windows.Forms.Keys)((System.Windows.Forms.Keys.Control | System.Windows.Forms.Keys.P)));
            this.printMenuItem.Size = new System.Drawing.Size(161, 22);
            this.printMenuItem.Text = "Drucken";
            this.printMenuItem.ToolTipText = "Drucken der rechts angezeigten HTML Seite";
            this.printMenuItem.Click += new System.EventHandler(this.OnPrintMenuItemClick);
            // 
            // fileMenuToolStripSeparator
            // 
            this.fileMenuToolStripSeparator.Name = "fileMenuToolStripSeparator";
            this.fileMenuToolStripSeparator.Size = new System.Drawing.Size(158, 6);
            // 
            // exitMenuItem
            // 
            this.exitMenuItem.Image = ((System.Drawing.Image)(resources.GetObject("exitMenuItem.Image")));
            this.exitMenuItem.Name = "exitMenuItem";
            this.exitMenuItem.Size = new System.Drawing.Size(161, 22);
            this.exitMenuItem.Text = "Beenden";
            this.exitMenuItem.ToolTipText = "Beenden der Applikation";
            this.exitMenuItem.CheckedChanged += new System.EventHandler(this.OnExitMenuItemClick);
            this.exitMenuItem.Click += new System.EventHandler(this.OnExitMenuItemClick);
            // 
            // optionsMenuItem
            // 
            this.optionsMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.setXmlPathMenuItem,
            this.setHtmlPathMenuItem,
            this.setPlaceholderHtmlFileMenuItem,
            this.administrationToolStripMenuItem});
            this.optionsMenuItem.Name = "optionsMenuItem";
            this.optionsMenuItem.Size = new System.Drawing.Size(69, 20);
            this.optionsMenuItem.Text = "Optionen";
            // 
            // setXmlPathMenuItem
            // 
            this.setXmlPathMenuItem.Image = ((System.Drawing.Image)(resources.GetObject("setXmlPathMenuItem.Image")));
            this.setXmlPathMenuItem.Name = "setXmlPathMenuItem";
            this.setXmlPathMenuItem.Size = new System.Drawing.Size(167, 22);
            this.setXmlPathMenuItem.Text = "XML-Pfad";
            this.setXmlPathMenuItem.ToolTipText = "Angabe der einzulesenden XML-Datei ";
            this.setXmlPathMenuItem.Click += new System.EventHandler(this.OnSetXmlPathMenuItemClick);
            // 
            // setHtmlPathMenuItem
            // 
            this.setHtmlPathMenuItem.Image = ((System.Drawing.Image)(resources.GetObject("setHtmlPathMenuItem.Image")));
            this.setHtmlPathMenuItem.Name = "setHtmlPathMenuItem";
            this.setHtmlPathMenuItem.Size = new System.Drawing.Size(167, 22);
            this.setHtmlPathMenuItem.Text = "HTML-Pfad";
            this.setHtmlPathMenuItem.ToolTipText = "Pfad zu den gesammelten HTML-Dateien der Tools";
            this.setHtmlPathMenuItem.Click += new System.EventHandler(this.OnSetHtmlPathMenuItemClick);
            // 
            // setPlaceholderHtmlFileMenuItem
            // 
            this.setPlaceholderHtmlFileMenuItem.Image = ((System.Drawing.Image)(resources.GetObject("setPlaceholderHtmlFileMenuItem.Image")));
            this.setPlaceholderHtmlFileMenuItem.Name = "setPlaceholderHtmlFileMenuItem";
            this.setPlaceholderHtmlFileMenuItem.Size = new System.Drawing.Size(167, 22);
            this.setPlaceholderHtmlFileMenuItem.Text = "Platzhalter-HTML";
            this.setPlaceholderHtmlFileMenuItem.ToolTipText = "Angabe der Ersatz HTML Datei";
            this.setPlaceholderHtmlFileMenuItem.Click += new System.EventHandler(this.OnSetPlaceholderHtmlFileMenuItemClick);
            // 
            // administrationToolStripMenuItem
            // 
            this.administrationToolStripMenuItem.BackColor = System.Drawing.SystemColors.Control;
            this.administrationToolStripMenuItem.Image = ((System.Drawing.Image)(resources.GetObject("administrationToolStripMenuItem.Image")));
            this.administrationToolStripMenuItem.Name = "administrationToolStripMenuItem";
            this.administrationToolStripMenuItem.Size = new System.Drawing.Size(167, 22);
            this.administrationToolStripMenuItem.Text = "Administration";
            this.administrationToolStripMenuItem.Click += new System.EventHandler(this.OnAdministrationToolStripMenuItemClick);
            // 
            // helpMenuItem
            // 
            this.helpMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.helpfileMenuItem,
            this.infoMenuItem});
            this.helpMenuItem.Name = "helpMenuItem";
            this.helpMenuItem.Size = new System.Drawing.Size(44, 20);
            this.helpMenuItem.Text = "Hilfe";
            // 
            // helpfileMenuItem
            // 
            this.helpfileMenuItem.Image = ((System.Drawing.Image)(resources.GetObject("helpfileMenuItem.Image")));
            this.helpfileMenuItem.Name = "helpfileMenuItem";
            this.helpfileMenuItem.Size = new System.Drawing.Size(115, 22);
            this.helpfileMenuItem.Text = "Helpfile";
            this.helpfileMenuItem.ToolTipText = "Aufruf der Hilfedatei";
            this.helpfileMenuItem.Click += new System.EventHandler(this.OnHelpfileMenuItemClick);
            // 
            // infoMenuItem
            // 
            this.infoMenuItem.Image = ((System.Drawing.Image)(resources.GetObject("infoMenuItem.Image")));
            this.infoMenuItem.Name = "infoMenuItem";
            this.infoMenuItem.Size = new System.Drawing.Size(115, 22);
            this.infoMenuItem.Text = "Info";
            this.infoMenuItem.ToolTipText = "Entwicklerinfos";
            this.infoMenuItem.Click += new System.EventHandler(this.OnInfoMenuItemClick);
            // 
            // splitContainer
            // 
            this.splitContainer.Dock = System.Windows.Forms.DockStyle.Fill;
            this.splitContainer.FixedPanel = System.Windows.Forms.FixedPanel.Panel1;
            this.splitContainer.IsSplitterFixed = true;
            this.splitContainer.Location = new System.Drawing.Point(0, 24);
            this.splitContainer.Name = "splitContainer";
            // 
            // splitContainer.Panel1
            // 
            this.splitContainer.Panel1.AutoScroll = true;
            this.splitContainer.Panel1.BackColor = System.Drawing.SystemColors.Control;
            this.splitContainer.Panel1.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Center;
            this.splitContainer.Panel1.Controls.Add(this.splitContainer1);
            // 
            // splitContainer.Panel2
            // 
            this.splitContainer.Panel2.Controls.Add(this.webBrowser);
            this.splitContainer.Size = new System.Drawing.Size(1117, 617);
            this.splitContainer.SplitterDistance = 274;
            this.splitContainer.TabIndex = 2;
            // 
            // splitContainer1
            // 
            this.splitContainer1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.splitContainer1.Location = new System.Drawing.Point(0, 0);
            this.splitContainer1.Name = "splitContainer1";
            this.splitContainer1.Orientation = System.Windows.Forms.Orientation.Horizontal;
            // 
            // splitContainer1.Panel1
            // 
            this.splitContainer1.Panel1.Controls.Add(this.methodTreeView);
            // 
            // splitContainer1.Panel2
            // 
            this.splitContainer1.Panel2.Controls.Add(this.splitContainer2);
            this.splitContainer1.Size = new System.Drawing.Size(274, 617);
            this.splitContainer1.SplitterDistance = 203;
            this.splitContainer1.TabIndex = 3;
            // 
            // methodTreeView
            // 
            this.methodTreeView.BackColor = System.Drawing.Color.WhiteSmoke;
            this.methodTreeView.Dock = System.Windows.Forms.DockStyle.Fill;
            this.methodTreeView.Location = new System.Drawing.Point(0, 0);
            this.methodTreeView.Name = "methodTreeView";
            this.methodTreeView.Size = new System.Drawing.Size(274, 203);
            this.methodTreeView.TabIndex = 0;
            this.methodTreeView.AfterSelect += new System.Windows.Forms.TreeViewEventHandler(this.AfterMethodTreeViewSelect);
            // 
            // splitContainer2
            // 
            this.splitContainer2.Dock = System.Windows.Forms.DockStyle.Fill;
            this.splitContainer2.FixedPanel = System.Windows.Forms.FixedPanel.Panel2;
            this.splitContainer2.IsSplitterFixed = true;
            this.splitContainer2.Location = new System.Drawing.Point(0, 0);
            this.splitContainer2.Name = "splitContainer2";
            this.splitContainer2.Orientation = System.Windows.Forms.Orientation.Horizontal;
            // 
            // splitContainer2.Panel1
            // 
            this.splitContainer2.Panel1.Controls.Add(this.treeView_visualOR);
            // 
            // splitContainer2.Panel2
            // 
            this.splitContainer2.Panel2.Controls.Add(this.documentationButton);
            this.splitContainer2.Panel2.Controls.Add(this.executeButton);
            this.splitContainer2.Size = new System.Drawing.Size(274, 410);
            this.splitContainer2.SplitterDistance = 320;
            this.splitContainer2.TabIndex = 0;
            // 
            // treeView_visualOR
            // 
            this.treeView_visualOR.BackColor = System.Drawing.Color.WhiteSmoke;
            this.treeView_visualOR.Dock = System.Windows.Forms.DockStyle.Fill;
            this.treeView_visualOR.Location = new System.Drawing.Point(0, 0);
            this.treeView_visualOR.Name = "treeView_visualOR";
            this.treeView_visualOR.Size = new System.Drawing.Size(274, 320);
            this.treeView_visualOR.TabIndex = 0;
            this.treeView_visualOR.AfterSelect += new System.Windows.Forms.TreeViewEventHandler(this.treeView_visualOR_AfterSelect);
            // 
            // documentationButton
            // 
            this.documentationButton.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.documentationButton.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.documentationButton.BackColor = System.Drawing.SystemColors.ControlLight;
            this.documentationButton.Enabled = false;
            this.documentationButton.Location = new System.Drawing.Point(142, 19);
            this.documentationButton.MinimumSize = new System.Drawing.Size(124, 35);
            this.documentationButton.Name = "documentationButton";
            this.documentationButton.Size = new System.Drawing.Size(124, 50);
            this.documentationButton.TabIndex = 2;
            this.documentationButton.Text = "Dokumentation öffnen";
            this.documentationButton.UseVisualStyleBackColor = false;
            this.documentationButton.Click += new System.EventHandler(this.OnDocumentationButtonClick);
            // 
            // executeButton
            // 
            this.executeButton.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left)));
            this.executeButton.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.executeButton.BackColor = System.Drawing.SystemColors.ControlLight;
            this.executeButton.Enabled = false;
            this.executeButton.Location = new System.Drawing.Point(12, 19);
            this.executeButton.MinimumSize = new System.Drawing.Size(124, 35);
            this.executeButton.Name = "executeButton";
            this.executeButton.Size = new System.Drawing.Size(124, 50);
            this.executeButton.TabIndex = 1;
            this.executeButton.Text = "Ausführen";
            this.executeButton.UseVisualStyleBackColor = false;
            this.executeButton.Click += new System.EventHandler(this.OnExecuteButtonClick);
            // 
            // webBrowser
            // 
            this.webBrowser.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.webBrowser.Location = new System.Drawing.Point(0, 0);
            this.webBrowser.MinimumSize = new System.Drawing.Size(20, 20);
            this.webBrowser.Name = "webBrowser";
            this.webBrowser.Size = new System.Drawing.Size(852, 617);
            this.webBrowser.TabIndex = 0;
            // 
            // statusLabel
            // 
            this.statusLabel.Name = "statusLabel";
            this.statusLabel.Size = new System.Drawing.Size(0, 17);
            // 
            // statusStrip
            // 
            this.statusStrip.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.statusLabel});
            this.statusStrip.Location = new System.Drawing.Point(0, 641);
            this.statusStrip.Name = "statusStrip";
            this.statusStrip.Size = new System.Drawing.Size(1117, 22);
            this.statusStrip.TabIndex = 1;
            this.statusStrip.Text = "statusStrip";
            // 
            // buttonBackward
            // 
            this.buttonBackward.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.buttonBackward.AutoSize = true;
            this.buttonBackward.BackColor = System.Drawing.Color.White;
            this.buttonBackward.BackgroundImageLayout = System.Windows.Forms.ImageLayout.None;
            this.buttonBackward.Enabled = false;
            this.buttonBackward.ForeColor = System.Drawing.Color.Black;
            this.buttonBackward.Image = ((System.Drawing.Image)(resources.GetObject("buttonBackward.Image")));
            this.buttonBackward.Location = new System.Drawing.Point(278, 0);
            this.buttonBackward.MaximumSize = new System.Drawing.Size(50, 24);
            this.buttonBackward.MinimumSize = new System.Drawing.Size(50, 24);
            this.buttonBackward.Name = "buttonBackward";
            this.buttonBackward.Size = new System.Drawing.Size(50, 24);
            this.buttonBackward.TabIndex = 3;
            this.buttonBackward.UseVisualStyleBackColor = false;
            this.buttonBackward.Click += new System.EventHandler(this.buttonBackward_Click);
            // 
            // buttonForward
            // 
            this.buttonForward.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.buttonForward.AutoSize = true;
            this.buttonForward.BackColor = System.Drawing.Color.White;
            this.buttonForward.Enabled = false;
            this.buttonForward.Image = ((System.Drawing.Image)(resources.GetObject("buttonForward.Image")));
            this.buttonForward.Location = new System.Drawing.Point(334, 0);
            this.buttonForward.MaximumSize = new System.Drawing.Size(50, 24);
            this.buttonForward.MinimumSize = new System.Drawing.Size(50, 24);
            this.buttonForward.Name = "buttonForward";
            this.buttonForward.Size = new System.Drawing.Size(50, 24);
            this.buttonForward.TabIndex = 4;
            this.buttonForward.UseVisualStyleBackColor = false;
            this.buttonForward.Click += new System.EventHandler(this.buttonForward_Click);
            // 
            // OrAlphaMainFrame
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1117, 663);
            this.Controls.Add(this.buttonForward);
            this.Controls.Add(this.buttonBackward);
            this.Controls.Add(this.splitContainer);
            this.Controls.Add(this.statusStrip);
            this.Controls.Add(this.menuStrip);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MainMenuStrip = this.menuStrip;
            this.Name = "OrAlphaMainFrame";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "OR ALPHA 2.0";
            this.menuStrip.ResumeLayout(false);
            this.menuStrip.PerformLayout();
            this.splitContainer.Panel1.ResumeLayout(false);
            this.splitContainer.Panel2.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.splitContainer)).EndInit();
            this.splitContainer.ResumeLayout(false);
            this.splitContainer1.Panel1.ResumeLayout(false);
            this.splitContainer1.Panel2.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.splitContainer1)).EndInit();
            this.splitContainer1.ResumeLayout(false);
            this.splitContainer2.Panel1.ResumeLayout(false);
            this.splitContainer2.Panel2.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.splitContainer2)).EndInit();
            this.splitContainer2.ResumeLayout(false);
            this.statusStrip.ResumeLayout(false);
            this.statusStrip.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.MenuStrip menuStrip;
        private System.Windows.Forms.ToolStripMenuItem fileMenuItem;
        private System.Windows.Forms.ToolStripMenuItem exitMenuItem;
        private System.Windows.Forms.ToolStripMenuItem optionsMenuItem;
        private System.Windows.Forms.ToolStripMenuItem helpMenuItem;
        private System.Windows.Forms.SplitContainer splitContainer;
        private System.Windows.Forms.TreeView methodTreeView;
        private System.Windows.Forms.WebBrowser webBrowser;
        private System.Windows.Forms.Button documentationButton;
        private System.Windows.Forms.Button executeButton;
        private System.Windows.Forms.ToolStripMenuItem setXmlPathMenuItem;
        private System.Windows.Forms.ToolStripStatusLabel statusLabel;
        private System.Windows.Forms.StatusStrip statusStrip;
        private System.Windows.Forms.ToolStripMenuItem setHtmlPathMenuItem;
        private System.Windows.Forms.ToolStripSeparator fileMenuToolStripSeparator;
        private System.Windows.Forms.ToolStripMenuItem helpfileMenuItem;
        private System.Windows.Forms.ToolStripMenuItem infoMenuItem;
        private System.Windows.Forms.ToolStripMenuItem setPlaceholderHtmlFileMenuItem;
        private System.Windows.Forms.ToolStripMenuItem refreshMenuItem;
        private System.Windows.Forms.ToolStripMenuItem printMenuItem;
        private System.Windows.Forms.SplitContainer splitContainer1;
        private System.Windows.Forms.SplitContainer splitContainer2;
        private System.Windows.Forms.TreeView treeView_visualOR;
        private System.Windows.Forms.ToolStripMenuItem administrationToolStripMenuItem;
        private System.Windows.Forms.Button buttonBackward;
        private System.Windows.Forms.Button buttonForward;
    }
    

}

