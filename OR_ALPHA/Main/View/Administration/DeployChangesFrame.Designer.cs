namespace OrAlpha.Main.View.Administration
{
    partial class DeployChangesFrame
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(DeployChangesFrame));
            this.deployChangesButton = new System.Windows.Forms.Button();
            this.cancelButton = new System.Windows.Forms.Button();
            this.deploymentProgressBar = new System.Windows.Forms.ProgressBar();
            this.SuspendLayout();
            // 
            // deployChangesButton
            // 
            this.deployChangesButton.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.deployChangesButton.Location = new System.Drawing.Point(62, 88);
            this.deployChangesButton.Name = "deployChangesButton";
            this.deployChangesButton.Size = new System.Drawing.Size(113, 43);
            this.deployChangesButton.TabIndex = 1;
            this.deployChangesButton.TabStop = false;
            this.deployChangesButton.Text = "Änderungen übertragen";
            this.deployChangesButton.UseVisualStyleBackColor = true;
            this.deployChangesButton.Click += new System.EventHandler(this.OnDeployChangesButtonClick);
            // 
            // cancelButton
            // 
            this.cancelButton.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.cancelButton.Location = new System.Drawing.Point(253, 88);
            this.cancelButton.Name = "cancelButton";
            this.cancelButton.Size = new System.Drawing.Size(113, 43);
            this.cancelButton.TabIndex = 2;
            this.cancelButton.TabStop = false;
            this.cancelButton.Text = "Abbrechen";
            this.cancelButton.UseVisualStyleBackColor = true;
            this.cancelButton.Click += new System.EventHandler(this.OnCancelButtonClick);
            // 
            // deploymentProgressBar
            // 
            this.deploymentProgressBar.Location = new System.Drawing.Point(24, 35);
            this.deploymentProgressBar.Name = "deploymentProgressBar";
            this.deploymentProgressBar.Size = new System.Drawing.Size(389, 23);
            this.deploymentProgressBar.TabIndex = 4;
            // 
            // DeployChangesFrame
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(438, 165);
            this.Controls.Add(this.deploymentProgressBar);
            this.Controls.Add(this.cancelButton);
            this.Controls.Add(this.deployChangesButton);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximizeBox = false;
            this.Name = "DeployChangesFrame";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "Änderungen übertragen";
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Button deployChangesButton;
        private System.Windows.Forms.Button cancelButton;
        private System.Windows.Forms.ProgressBar deploymentProgressBar;
    }
}