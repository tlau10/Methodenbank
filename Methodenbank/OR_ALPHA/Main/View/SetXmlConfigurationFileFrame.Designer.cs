namespace OrAlpha.Main.View
{
    partial class SetXmlConfigurationFileFrame
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(SetXmlConfigurationFileFrame));
            this.xmlFilePathTextbox = new System.Windows.Forms.TextBox();
            this.xmlFilePathLabel = new System.Windows.Forms.Label();
            this.okButton = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // xmlFilePathTextbox
            // 
            this.xmlFilePathTextbox.Location = new System.Drawing.Point(12, 30);
            this.xmlFilePathTextbox.Name = "xmlFilePathTextbox";
            this.xmlFilePathTextbox.Size = new System.Drawing.Size(375, 20);
            this.xmlFilePathTextbox.TabIndex = 0;
            // 
            // xmlFilePathLabel
            // 
            this.xmlFilePathLabel.AutoSize = true;
            this.xmlFilePathLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.xmlFilePathLabel.Location = new System.Drawing.Point(9, 14);
            this.xmlFilePathLabel.Name = "xmlFilePathLabel";
            this.xmlFilePathLabel.Size = new System.Drawing.Size(196, 13);
            this.xmlFilePathLabel.TabIndex = 1;
            this.xmlFilePathLabel.Text = "Pfad zur XML-Konfigurationsdatei";
            // 
            // okButton
            // 
            this.okButton.Location = new System.Drawing.Point(312, 56);
            this.okButton.Name = "okButton";
            this.okButton.Size = new System.Drawing.Size(75, 23);
            this.okButton.TabIndex = 2;
            this.okButton.Text = "OK";
            this.okButton.UseVisualStyleBackColor = true;
            this.okButton.Click += new System.EventHandler(this.OnOkButtonClick);
            // 
            // SetXmlConfigurationFileFrame
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(393, 84);
            this.Controls.Add(this.okButton);
            this.Controls.Add(this.xmlFilePathLabel);
            this.Controls.Add(this.xmlFilePathTextbox);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedToolWindow;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "SetXmlConfigurationFileFrame";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "XML-Pfad ändern";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.TextBox xmlFilePathTextbox;
        private System.Windows.Forms.Label xmlFilePathLabel;
        private System.Windows.Forms.Button okButton;
    }
}