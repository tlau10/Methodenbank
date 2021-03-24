namespace OrAlpha.Main.View
{
    partial class SetHtmlPathFrame
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(SetHtmlPathFrame));
            this.htmlPathLabel = new System.Windows.Forms.Label();
            this.htmlPathTextBox = new System.Windows.Forms.TextBox();
            this.okButton = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // htmlPathLabel
            // 
            this.htmlPathLabel.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.htmlPathLabel.AutoSize = true;
            this.htmlPathLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.htmlPathLabel.Location = new System.Drawing.Point(3, 10);
            this.htmlPathLabel.Name = "htmlPathLabel";
            this.htmlPathLabel.Size = new System.Drawing.Size(238, 13);
            this.htmlPathLabel.TabIndex = 0;
            this.htmlPathLabel.Text = "Pfad zu den gesammelten HTML-Dateien";
            // 
            // htmlPathTextBox
            // 
            this.htmlPathTextBox.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.htmlPathTextBox.Location = new System.Drawing.Point(6, 26);
            this.htmlPathTextBox.Name = "htmlPathTextBox";
            this.htmlPathTextBox.Size = new System.Drawing.Size(371, 20);
            this.htmlPathTextBox.TabIndex = 1;
            // 
            // okButton
            // 
            this.okButton.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.okButton.Location = new System.Drawing.Point(302, 52);
            this.okButton.Name = "okButton";
            this.okButton.Size = new System.Drawing.Size(75, 23);
            this.okButton.TabIndex = 2;
            this.okButton.Text = "OK";
            this.okButton.UseVisualStyleBackColor = true;
            this.okButton.Click += new System.EventHandler(this.OnOkButtonClick);
            // 
            // SetHtmlPathFrame
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(386, 92);
            this.Controls.Add(this.okButton);
            this.Controls.Add(this.htmlPathTextBox);
            this.Controls.Add(this.htmlPathLabel);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedToolWindow;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "SetHtmlPathFrame";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "HTML-Pfad ändern";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label htmlPathLabel;
        private System.Windows.Forms.TextBox htmlPathTextBox;
        private System.Windows.Forms.Button okButton;
    }
}