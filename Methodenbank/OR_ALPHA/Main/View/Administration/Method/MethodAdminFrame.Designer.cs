namespace OrAlpha.Main.View.Administration
{
    partial class MethodAdminFrame
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
            this.webtoolCheckbox = new System.Windows.Forms.CheckBox();
            this.methodNameTextBox = new System.Windows.Forms.TextBox();
            this.methodNameLabel = new System.Windows.Forms.Label();
            this.selectExecutionPathButton = new System.Windows.Forms.Button();
            this.saveButton = new System.Windows.Forms.Button();
            this.cancelButton = new System.Windows.Forms.Button();
            this.selectHtmlFileButton = new System.Windows.Forms.Button();
            this.selectDocumentationPathButton = new System.Windows.Forms.Button();
            this.htmlFileTextBox = new System.Windows.Forms.TextBox();
            this.htmlFileLabel = new System.Windows.Forms.Label();
            this.documentationPathTextBox = new System.Windows.Forms.TextBox();
            this.documentationPathLabel = new System.Windows.Forms.Label();
            this.executionPathTextBox = new System.Windows.Forms.TextBox();
            this.executionPathLabel = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // categoryTreeView
            // 
            this.categoryTreeView.LineColor = System.Drawing.Color.Black;
            // 
            // webtoolCheckbox
            // 
            this.webtoolCheckbox.AutoSize = true;
            this.webtoolCheckbox.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.webtoolCheckbox.Location = new System.Drawing.Point(242, 365);
            this.webtoolCheckbox.Name = "webtoolCheckbox";
            this.webtoolCheckbox.Size = new System.Drawing.Size(78, 19);
            this.webtoolCheckbox.TabIndex = 29;
            this.webtoolCheckbox.Text = "Webtool";
            this.webtoolCheckbox.UseVisualStyleBackColor = true;
            // 
            // methodNameTextBox
            // 
            this.methodNameTextBox.Location = new System.Drawing.Point(242, 167);
            this.methodNameTextBox.Name = "methodNameTextBox";
            this.methodNameTextBox.Size = new System.Drawing.Size(335, 20);
            this.methodNameTextBox.TabIndex = 22;
            // 
            // methodNameLabel
            // 
            this.methodNameLabel.AutoSize = true;
            this.methodNameLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.methodNameLabel.Location = new System.Drawing.Point(239, 149);
            this.methodNameLabel.Name = "methodNameLabel";
            this.methodNameLabel.Size = new System.Drawing.Size(107, 15);
            this.methodNameLabel.TabIndex = 36;
            this.methodNameLabel.Text = "Methodenname";
            // 
            // selectExecutionPathButton
            // 
            this.selectExecutionPathButton.Location = new System.Drawing.Point(583, 216);
            this.selectExecutionPathButton.Name = "selectExecutionPathButton";
            this.selectExecutionPathButton.Size = new System.Drawing.Size(24, 20);
            this.selectExecutionPathButton.TabIndex = 24;
            this.selectExecutionPathButton.Text = "...";
            this.selectExecutionPathButton.UseVisualStyleBackColor = true;
            this.selectExecutionPathButton.Click += new System.EventHandler(this.OnSelectExecutionPathButtonClick);
            // 
            // saveButton
            // 
            this.saveButton.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.saveButton.Location = new System.Drawing.Point(379, 352);
            this.saveButton.Name = "saveButton";
            this.saveButton.Size = new System.Drawing.Size(113, 43);
            this.saveButton.TabIndex = 30;
            this.saveButton.TabStop = false;
            this.saveButton.Text = "Speichern";
            this.saveButton.UseVisualStyleBackColor = true;
            // 
            // cancelButton
            // 
            this.cancelButton.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.cancelButton.Location = new System.Drawing.Point(506, 352);
            this.cancelButton.Name = "cancelButton";
            this.cancelButton.Size = new System.Drawing.Size(113, 43);
            this.cancelButton.TabIndex = 32;
            this.cancelButton.TabStop = false;
            this.cancelButton.Text = "Abbrechen";
            this.cancelButton.UseVisualStyleBackColor = true;
            this.cancelButton.Click += new System.EventHandler(this.OnCancelButtonClick);
            // 
            // selectHtmlFileButton
            // 
            this.selectHtmlFileButton.Location = new System.Drawing.Point(583, 316);
            this.selectHtmlFileButton.Name = "selectHtmlFileButton";
            this.selectHtmlFileButton.Size = new System.Drawing.Size(24, 21);
            this.selectHtmlFileButton.TabIndex = 28;
            this.selectHtmlFileButton.Text = "...";
            this.selectHtmlFileButton.UseVisualStyleBackColor = true;
            this.selectHtmlFileButton.Click += new System.EventHandler(this.OnSelectHtmlFileButtonClick);
            // 
            // selectDocumentationPathButton
            // 
            this.selectDocumentationPathButton.Location = new System.Drawing.Point(583, 267);
            this.selectDocumentationPathButton.Name = "selectDocumentationPathButton";
            this.selectDocumentationPathButton.Size = new System.Drawing.Size(24, 20);
            this.selectDocumentationPathButton.TabIndex = 26;
            this.selectDocumentationPathButton.Text = "...";
            this.selectDocumentationPathButton.UseVisualStyleBackColor = true;
            this.selectDocumentationPathButton.Click += new System.EventHandler(this.OnSelectDocumentationPathButtonClick);
            // 
            // htmlFileTextBox
            // 
            this.htmlFileTextBox.Location = new System.Drawing.Point(242, 317);
            this.htmlFileTextBox.Name = "htmlFileTextBox";
            this.htmlFileTextBox.Size = new System.Drawing.Size(335, 20);
            this.htmlFileTextBox.TabIndex = 27;
            // 
            // htmlFileLabel
            // 
            this.htmlFileLabel.AutoSize = true;
            this.htmlFileLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.htmlFileLabel.Location = new System.Drawing.Point(239, 299);
            this.htmlFileLabel.Name = "htmlFileLabel";
            this.htmlFileLabel.Size = new System.Drawing.Size(120, 15);
            this.htmlFileLabel.TabIndex = 35;
            this.htmlFileLabel.Text = "HTML - Dateipfad";
            // 
            // documentationPathTextBox
            // 
            this.documentationPathTextBox.Location = new System.Drawing.Point(242, 267);
            this.documentationPathTextBox.Name = "documentationPathTextBox";
            this.documentationPathTextBox.Size = new System.Drawing.Size(335, 20);
            this.documentationPathTextBox.TabIndex = 25;
            // 
            // documentationPathLabel
            // 
            this.documentationPathLabel.AutoSize = true;
            this.documentationPathLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.documentationPathLabel.Location = new System.Drawing.Point(239, 249);
            this.documentationPathLabel.Name = "documentationPathLabel";
            this.documentationPathLabel.Size = new System.Drawing.Size(139, 15);
            this.documentationPathLabel.TabIndex = 34;
            this.documentationPathLabel.Text = "Dokumentationspfad";
            // 
            // executionPathTextBox
            // 
            this.executionPathTextBox.Location = new System.Drawing.Point(242, 217);
            this.executionPathTextBox.Name = "executionPathTextBox";
            this.executionPathTextBox.Size = new System.Drawing.Size(335, 20);
            this.executionPathTextBox.TabIndex = 23;
            // 
            // executionPathLabel
            // 
            this.executionPathLabel.AutoSize = true;
            this.executionPathLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.executionPathLabel.Location = new System.Drawing.Point(239, 199);
            this.executionPathLabel.Name = "executionPathLabel";
            this.executionPathLabel.Size = new System.Drawing.Size(179, 15);
            this.executionPathLabel.TabIndex = 33;
            this.executionPathLabel.Text = "Ausführungspfad bzw. URL";
            // 
            // MethodAdminFrame
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(629, 404);
            this.Controls.Add(this.webtoolCheckbox);
            this.Controls.Add(this.methodNameTextBox);
            this.Controls.Add(this.methodNameLabel);
            this.Controls.Add(this.selectExecutionPathButton);
            this.Controls.Add(this.saveButton);
            this.Controls.Add(this.cancelButton);
            this.Controls.Add(this.selectHtmlFileButton);
            this.Controls.Add(this.selectDocumentationPathButton);
            this.Controls.Add(this.htmlFileTextBox);
            this.Controls.Add(this.htmlFileLabel);
            this.Controls.Add(this.documentationPathTextBox);
            this.Controls.Add(this.documentationPathLabel);
            this.Controls.Add(this.executionPathTextBox);
            this.Controls.Add(this.executionPathLabel);
            this.Name = "MethodAdminFrame";
            this.Text = "MethodAdminFrame";
            this.Controls.SetChildIndex(this.categoryTreeView, 0);
            this.Controls.SetChildIndex(this.executionPathLabel, 0);
            this.Controls.SetChildIndex(this.executionPathTextBox, 0);
            this.Controls.SetChildIndex(this.documentationPathLabel, 0);
            this.Controls.SetChildIndex(this.documentationPathTextBox, 0);
            this.Controls.SetChildIndex(this.htmlFileLabel, 0);
            this.Controls.SetChildIndex(this.htmlFileTextBox, 0);
            this.Controls.SetChildIndex(this.selectDocumentationPathButton, 0);
            this.Controls.SetChildIndex(this.selectHtmlFileButton, 0);
            this.Controls.SetChildIndex(this.cancelButton, 0);
            this.Controls.SetChildIndex(this.saveButton, 0);
            this.Controls.SetChildIndex(this.selectExecutionPathButton, 0);
            this.Controls.SetChildIndex(this.methodNameLabel, 0);
            this.Controls.SetChildIndex(this.methodNameTextBox, 0);
            this.Controls.SetChildIndex(this.webtoolCheckbox, 0);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label methodNameLabel;
        private System.Windows.Forms.Button selectExecutionPathButton;
        private System.Windows.Forms.Button cancelButton;
        private System.Windows.Forms.Button selectHtmlFileButton;
        private System.Windows.Forms.Button selectDocumentationPathButton;
        private System.Windows.Forms.Label htmlFileLabel;
        private System.Windows.Forms.Label documentationPathLabel;
        private System.Windows.Forms.Label executionPathLabel;
        protected System.Windows.Forms.TextBox methodNameTextBox;
        protected System.Windows.Forms.TextBox htmlFileTextBox;
        protected System.Windows.Forms.TextBox documentationPathTextBox;
        protected System.Windows.Forms.TextBox executionPathTextBox;
        protected System.Windows.Forms.Button saveButton;
        protected System.Windows.Forms.CheckBox webtoolCheckbox;
    }
}