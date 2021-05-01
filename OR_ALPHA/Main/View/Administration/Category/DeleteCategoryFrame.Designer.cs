namespace OrAlpha.Main.View.Administration
{
    partial class DeleteCategoryFrame
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
            this.deleteCategoryButton = new System.Windows.Forms.Button();
            this.okButton = new System.Windows.Forms.Button();
            this.placementGroupBox = new System.Windows.Forms.GroupBox();
            this.selectedCategoryLabel = new System.Windows.Forms.Label();
            this.parentCategoryLabel = new System.Windows.Forms.Label();
            this.placementGroupBox.SuspendLayout();
            this.SuspendLayout();
            // 
            // categoryTreeView
            // 
            this.categoryTreeView.LineColor = System.Drawing.Color.Black;
            this.categoryTreeView.Size = new System.Drawing.Size(296, 404);
            this.categoryTreeView.AfterSelect += new System.Windows.Forms.TreeViewEventHandler(this.AfterCategoryTreeViewSelect);
            // 
            // deleteCategoryButton
            // 
            this.deleteCategoryButton.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.deleteCategoryButton.Location = new System.Drawing.Point(404, 201);
            this.deleteCategoryButton.Name = "deleteCategoryButton";
            this.deleteCategoryButton.Size = new System.Drawing.Size(113, 43);
            this.deleteCategoryButton.TabIndex = 18;
            this.deleteCategoryButton.TabStop = false;
            this.deleteCategoryButton.Text = "Kategorie Löschen";
            this.deleteCategoryButton.UseVisualStyleBackColor = true;
            this.deleteCategoryButton.Click += new System.EventHandler(this.OnDeleteCategoryButtonClick);
            // 
            // okButton
            // 
            this.okButton.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.okButton.Location = new System.Drawing.Point(501, 346);
            this.okButton.Name = "okButton";
            this.okButton.Size = new System.Drawing.Size(113, 43);
            this.okButton.TabIndex = 17;
            this.okButton.TabStop = false;
            this.okButton.Text = "OK";
            this.okButton.UseVisualStyleBackColor = true;
            this.okButton.Click += new System.EventHandler(this.OnOkButtonClick);
            // 
            // placementGroupBox
            // 
            this.placementGroupBox.Controls.Add(this.selectedCategoryLabel);
            this.placementGroupBox.Controls.Add(this.parentCategoryLabel);
            this.placementGroupBox.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.placementGroupBox.Location = new System.Drawing.Point(339, 44);
            this.placementGroupBox.Name = "placementGroupBox";
            this.placementGroupBox.Size = new System.Drawing.Size(241, 132);
            this.placementGroupBox.TabIndex = 16;
            this.placementGroupBox.TabStop = false;
            this.placementGroupBox.Text = "Einordnung";
            // 
            // selectedCategoryLabel
            // 
            this.selectedCategoryLabel.Location = new System.Drawing.Point(6, 80);
            this.selectedCategoryLabel.Name = "selectedCategoryLabel";
            this.selectedCategoryLabel.Size = new System.Drawing.Size(229, 49);
            this.selectedCategoryLabel.TabIndex = 100;
            this.selectedCategoryLabel.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // parentCategoryLabel
            // 
            this.parentCategoryLabel.Location = new System.Drawing.Point(6, 16);
            this.parentCategoryLabel.Name = "parentCategoryLabel";
            this.parentCategoryLabel.Size = new System.Drawing.Size(229, 64);
            this.parentCategoryLabel.TabIndex = 2;
            this.parentCategoryLabel.Text = "Bitte wählen Sie die zu löschende Kategorie aus";
            this.parentCategoryLabel.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // DeleteCategoryFrame
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(629, 404);
            this.Controls.Add(this.deleteCategoryButton);
            this.Controls.Add(this.okButton);
            this.Controls.Add(this.placementGroupBox);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
            this.Name = "DeleteCategoryFrame";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "Kategorie löschen";
            this.Controls.SetChildIndex(this.categoryTreeView, 0);
            this.Controls.SetChildIndex(this.placementGroupBox, 0);
            this.Controls.SetChildIndex(this.okButton, 0);
            this.Controls.SetChildIndex(this.deleteCategoryButton, 0);
            this.placementGroupBox.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Button deleteCategoryButton;
        private System.Windows.Forms.Button okButton;
        private System.Windows.Forms.GroupBox placementGroupBox;
        private System.Windows.Forms.Label selectedCategoryLabel;
        private System.Windows.Forms.Label parentCategoryLabel;
    }
}