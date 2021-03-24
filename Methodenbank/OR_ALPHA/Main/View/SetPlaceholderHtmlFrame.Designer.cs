namespace OrAlpha.Main.View
{
    partial class SetPlaceholderHtmlFrame
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(SetPlaceholderHtmlFrame));
            this.htmlFileTextbox = new System.Windows.Forms.TextBox();
            this.placeholderHtmlFileButton = new System.Windows.Forms.Label();
            this.okButton = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // htmlFileTextbox
            // 
            this.htmlFileTextbox.Location = new System.Drawing.Point(12, 35);
            this.htmlFileTextbox.Name = "htmlFileTextbox";
            this.htmlFileTextbox.Size = new System.Drawing.Size(375, 20);
            this.htmlFileTextbox.TabIndex = 3;
            // 
            // placeholderHtmlFileButton
            // 
            this.placeholderHtmlFileButton.AutoSize = true;
            this.placeholderHtmlFileButton.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.placeholderHtmlFileButton.Location = new System.Drawing.Point(12, 19);
            this.placeholderHtmlFileButton.Name = "placeholderHtmlFileButton";
            this.placeholderHtmlFileButton.Size = new System.Drawing.Size(190, 13);
            this.placeholderHtmlFileButton.TabIndex = 4;
            this.placeholderHtmlFileButton.Text = "Pfad zur Platzhalter-HTML-Datei";
            // 
            // okButton
            // 
            this.okButton.Location = new System.Drawing.Point(312, 61);
            this.okButton.Name = "okButton";
            this.okButton.Size = new System.Drawing.Size(75, 23);
            this.okButton.TabIndex = 5;
            this.okButton.Text = "OK";
            this.okButton.UseVisualStyleBackColor = true;
            this.okButton.Click += new System.EventHandler(this.OnOkButtonClick);
            // 
            // SetPlaceholderHtmlFrame
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(394, 93);
            this.Controls.Add(this.okButton);
            this.Controls.Add(this.placeholderHtmlFileButton);
            this.Controls.Add(this.htmlFileTextbox);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedToolWindow;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "SetPlaceholderHtmlFrame";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "Platzhalter-HTML-Datei ändern";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.TextBox htmlFileTextbox;
        private System.Windows.Forms.Label placeholderHtmlFileButton;
        private System.Windows.Forms.Button okButton;
    }
}