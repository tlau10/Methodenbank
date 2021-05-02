namespace OrAlpha.Main.View.Administration
{
    partial class CategoryAdminFrame
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
            this.saveButton = new System.Windows.Forms.Button();
            this.cancelButton = new System.Windows.Forms.Button();
            this.selectHtmlFileButton = new System.Windows.Forms.Button();
            this.htmlFileTextBox = new System.Windows.Forms.TextBox();
            this.htmlFileLabel = new System.Windows.Forms.Label();
            this.categoryNameTextBox = new System.Windows.Forms.TextBox();
            this.categoryNameLabel = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // categoryTreeView
            // 
            this.categoryTreeView.LineColor = System.Drawing.Color.Black;
            // 
            // saveButton
            // 
            this.saveButton.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.saveButton.Location = new System.Drawing.Point(375, 349);
            this.saveButton.Name = "saveButton";
            this.saveButton.Size = new System.Drawing.Size(113, 43);
            this.saveButton.TabIndex = 26;
            this.saveButton.TabStop = false;
            this.saveButton.Text = "Speichern";
            this.saveButton.UseVisualStyleBackColor = true;
            // 
            // cancelButton
            // 
            this.cancelButton.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.cancelButton.Location = new System.Drawing.Point(502, 349);
            this.cancelButton.Name = "cancelButton";
            this.cancelButton.Size = new System.Drawing.Size(113, 43);
            this.cancelButton.TabIndex = 28;
            this.cancelButton.TabStop = false;
            this.cancelButton.Text = "Abbrechen";
            this.cancelButton.UseVisualStyleBackColor = true;
            this.cancelButton.Click += new System.EventHandler(this.OnCancelButtonClick);
            // 
            // selectHtmlFileButton
            // 
            this.selectHtmlFileButton.Location = new System.Drawing.Point(580, 245);
            this.selectHtmlFileButton.Name = "selectHtmlFileButton";
            this.selectHtmlFileButton.Size = new System.Drawing.Size(24, 21);
            this.selectHtmlFileButton.TabIndex = 25;
            this.selectHtmlFileButton.Text = "...";
            this.selectHtmlFileButton.UseVisualStyleBackColor = true;
            this.selectHtmlFileButton.Click += new System.EventHandler(this.OnSelectHtmlFileButtonClick);
            // 
            // htmlFileTextBox
            // 
            this.htmlFileTextBox.Location = new System.Drawing.Point(239, 246);
            this.htmlFileTextBox.Name = "htmlFileTextBox";
            this.htmlFileTextBox.Size = new System.Drawing.Size(335, 20);
            this.htmlFileTextBox.TabIndex = 23;
            // 
            // htmlFileLabel
            // 
            this.htmlFileLabel.AutoSize = true;
            this.htmlFileLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.htmlFileLabel.Location = new System.Drawing.Point(236, 228);
            this.htmlFileLabel.Name = "htmlFileLabel";
            this.htmlFileLabel.Size = new System.Drawing.Size(120, 15);
            this.htmlFileLabel.TabIndex = 27;
            this.htmlFileLabel.Text = "HTML - Dateipfad";
            // 
            // categoryNameTextBox
            // 
            this.categoryNameTextBox.Location = new System.Drawing.Point(239, 193);
            this.categoryNameTextBox.Name = "categoryNameTextBox";
            this.categoryNameTextBox.Size = new System.Drawing.Size(335, 20);
            this.categoryNameTextBox.TabIndex = 19;
            // 
            // categoryNameLabel
            // 
            this.categoryNameLabel.AutoSize = true;
            this.categoryNameLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.categoryNameLabel.Location = new System.Drawing.Point(236, 175);
            this.categoryNameLabel.Name = "categoryNameLabel";
            this.categoryNameLabel.Size = new System.Drawing.Size(105, 15);
            this.categoryNameLabel.TabIndex = 21;
            this.categoryNameLabel.Text = "Kategoriename";
            // 
            // CategoryAdminFrame
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(629, 404);
            this.Controls.Add(this.saveButton);
            this.Controls.Add(this.cancelButton);
            this.Controls.Add(this.selectHtmlFileButton);
            this.Controls.Add(this.htmlFileTextBox);
            this.Controls.Add(this.htmlFileLabel);
            this.Controls.Add(this.categoryNameTextBox);
            this.Controls.Add(this.categoryNameLabel);
            this.Name = "CategoryAdminFrame";
            this.Text = "CategoryAdminFrame";
            this.Controls.SetChildIndex(this.categoryTreeView, 0);
            this.Controls.SetChildIndex(this.categoryNameLabel, 0);
            this.Controls.SetChildIndex(this.categoryNameTextBox, 0);
            this.Controls.SetChildIndex(this.htmlFileLabel, 0);
            this.Controls.SetChildIndex(this.htmlFileTextBox, 0);
            this.Controls.SetChildIndex(this.selectHtmlFileButton, 0);
            this.Controls.SetChildIndex(this.cancelButton, 0);
            this.Controls.SetChildIndex(this.saveButton, 0);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button cancelButton;
        private System.Windows.Forms.Label htmlFileLabel;
        private System.Windows.Forms.Label categoryNameLabel;
        protected System.Windows.Forms.TextBox categoryNameTextBox;
        protected System.Windows.Forms.TextBox htmlFileTextBox;
        protected System.Windows.Forms.Button saveButton;
        protected System.Windows.Forms.Button selectHtmlFileButton;
    }
}