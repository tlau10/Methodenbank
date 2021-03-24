namespace OrAlpha.Main.View.Administration
{
    partial class CreateMethodFrame
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

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            this.placementGroupBox = new System.Windows.Forms.GroupBox();
            this.selectedCategoryLabel = new System.Windows.Forms.Label();
            this.parentCategoryLabel = new System.Windows.Forms.Label();
            this.createMethodToolTip = new System.Windows.Forms.ToolTip(this.components);
            this.placementGroupBox.SuspendLayout();
            this.SuspendLayout();
            // 
            // methodNameTextBox
            // 
            this.createMethodToolTip.SetToolTip(this.methodNameTextBox, "Wählen Sie einen Namen für die anzulegende Methode.");
            // 
            // htmlFileTextBox
            // 
            this.createMethodToolTip.SetToolTip(this.htmlFileTextBox, "Wählen Sie den Pfad zur HTML-Seite der Methode.");
            // 
            // documentationPathTextBox
            // 
            this.createMethodToolTip.SetToolTip(this.documentationPathTextBox, "Wählen Sie den Pfad zur Dokumentation der Methode.");
            // 
            // executionPathTextbox
            // 
            this.createMethodToolTip.SetToolTip(this.executionPathTextBox, "Wählen Sie den Pfad zum ausführen der Methode.");
            // 
            // saveButton
            // 
            this.saveButton.Click += new System.EventHandler(this.OnSaveButtonClick);
            // 
            // webtoolCheckbox
            // 
            this.createMethodToolTip.SetToolTip(this.webtoolCheckbox, "Handelt es sich bei der Methode um eine Web- oder Desktop-Anwendung?");
            // 
            // categoryTreeView
            // 
            this.categoryTreeView.AfterSelect += new System.Windows.Forms.TreeViewEventHandler(this.AfterCategoryTreeViewSelect);
            // 
            // placementGroupBox
            // 
            this.placementGroupBox.Controls.Add(this.selectedCategoryLabel);
            this.placementGroupBox.Controls.Add(this.parentCategoryLabel);
            this.placementGroupBox.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.placementGroupBox.Location = new System.Drawing.Point(336, 10);
            this.placementGroupBox.Name = "placementGroupBox";
            this.placementGroupBox.Size = new System.Drawing.Size(200, 132);
            this.placementGroupBox.TabIndex = 9;
            this.placementGroupBox.TabStop = false;
            this.placementGroupBox.Text = "Einordnung";
            // 
            // selectedCategoryLabel
            // 
            this.selectedCategoryLabel.Location = new System.Drawing.Point(6, 101);
            this.selectedCategoryLabel.Name = "selectedCategoryLabel";
            this.selectedCategoryLabel.Size = new System.Drawing.Size(188, 28);
            this.selectedCategoryLabel.TabIndex = 100;
            this.selectedCategoryLabel.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // parentCategoryLabel
            // 
            this.parentCategoryLabel.Location = new System.Drawing.Point(6, 16);
            this.parentCategoryLabel.Name = "parentCategoryLabel";
            this.parentCategoryLabel.Size = new System.Drawing.Size(188, 81);
            this.parentCategoryLabel.TabIndex = 2;
            this.parentCategoryLabel.Text = "Bitte wählen Sie zum Einordnen die übergeordnete Kategorie aus.";
            this.parentCategoryLabel.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // createMethodToolTip
            // 
            this.createMethodToolTip.IsBalloon = true;
            // 
            // CreateMethodFrame
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(629, 404);
            this.Controls.Add(this.placementGroupBox);
            this.Name = "CreateMethodFrame";
            this.Text = "Methode hinzufügen";
            this.Controls.SetChildIndex(this.executionPathTextBox, 0);
            this.Controls.SetChildIndex(this.documentationPathTextBox, 0);
            this.Controls.SetChildIndex(this.htmlFileTextBox, 0);
            this.Controls.SetChildIndex(this.saveButton, 0);
            this.Controls.SetChildIndex(this.methodNameTextBox, 0);
            this.Controls.SetChildIndex(this.webtoolCheckbox, 0);
            this.Controls.SetChildIndex(this.categoryTreeView, 0);
            this.Controls.SetChildIndex(this.placementGroupBox, 0);
            this.placementGroupBox.ResumeLayout(false);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.GroupBox placementGroupBox;
        private System.Windows.Forms.Label selectedCategoryLabel;
        private System.Windows.Forms.Label parentCategoryLabel;
        private System.Windows.Forms.ToolTip createMethodToolTip;
    }
}