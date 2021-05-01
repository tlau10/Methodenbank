namespace OrAlpha.Main.View.Administration
{
    partial class UpdateCategoryFrame
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
            this.categoryContainerComboBox = new System.Windows.Forms.ComboBox();
            this.parentCategoryLabel = new System.Windows.Forms.Label();
            this.updateCategoryToolTip = new System.Windows.Forms.ToolTip(this.components);
            this.placementGroupBox.SuspendLayout();
            this.SuspendLayout();
            // 
            // categoryNameTextBox
            // 
            this.updateCategoryToolTip.SetToolTip(this.categoryNameTextBox, "Hier können Sie den Namen der  Kategorie ändern.");
            // 
            // htmlFileTextBox
            // 
            this.updateCategoryToolTip.SetToolTip(this.htmlFileTextBox, "Hier können Sie den Pfad zur HTML-Seite der Kategorie ändern.");
            // 
            // saveButton
            // 
            this.saveButton.Click += new System.EventHandler(this.OnSaveButtonClick);
            // 
            // categoryTreeView
            // 
            this.categoryTreeView.AfterSelect += new System.Windows.Forms.TreeViewEventHandler(this.AfterCategoryTreeViewSelect);
            // 
            // placementGroupBox
            // 
            this.placementGroupBox.Controls.Add(this.categoryContainerComboBox);
            this.placementGroupBox.Controls.Add(this.parentCategoryLabel);
            this.placementGroupBox.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.placementGroupBox.Location = new System.Drawing.Point(288, 12);
            this.placementGroupBox.Name = "placementGroupBox";
            this.placementGroupBox.Size = new System.Drawing.Size(291, 132);
            this.placementGroupBox.TabIndex = 9;
            this.placementGroupBox.TabStop = false;
            this.placementGroupBox.Text = "Einordnung";
            // 
            // categoryContainerComboBox
            // 
            this.categoryContainerComboBox.FormattingEnabled = true;
            this.categoryContainerComboBox.Location = new System.Drawing.Point(6, 103);
            this.categoryContainerComboBox.Name = "categoryContainerComboBox";
            this.categoryContainerComboBox.Size = new System.Drawing.Size(279, 23);
            this.categoryContainerComboBox.TabIndex = 3;
            // 
            // parentCategoryLabel
            // 
            this.parentCategoryLabel.Location = new System.Drawing.Point(6, 16);
            this.parentCategoryLabel.Name = "parentCategoryLabel";
            this.parentCategoryLabel.Size = new System.Drawing.Size(279, 81);
            this.parentCategoryLabel.TabIndex = 2;
            this.parentCategoryLabel.Text = "Bitte wählen Sie zum Verschieben die neue übergeordnete Kategorie aus.";
            this.parentCategoryLabel.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // updateCategoryToolTip
            // 
            this.updateCategoryToolTip.IsBalloon = true;
            // 
            // UpdateCategoryFrame
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(629, 404);
            this.Controls.Add(this.placementGroupBox);
            this.Name = "UpdateCategoryFrame";
            this.Text = "Kategorie bearbeiten";
            this.Controls.SetChildIndex(this.selectHtmlFileButton, 0);
            this.Controls.SetChildIndex(this.categoryNameTextBox, 0);
            this.Controls.SetChildIndex(this.htmlFileTextBox, 0);
            this.Controls.SetChildIndex(this.saveButton, 0);
            this.Controls.SetChildIndex(this.categoryTreeView, 0);
            this.Controls.SetChildIndex(this.placementGroupBox, 0);
            this.placementGroupBox.ResumeLayout(false);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.GroupBox placementGroupBox;
        private System.Windows.Forms.Label parentCategoryLabel;
        private System.Windows.Forms.ComboBox categoryContainerComboBox;
        private System.Windows.Forms.ToolTip updateCategoryToolTip;
    }
}