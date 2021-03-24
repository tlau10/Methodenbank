namespace OrAlpha.Main.View.Administration
{
    partial class DeleteMethodFrame
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
            this.okButton = new System.Windows.Forms.Button();
            this.placementGroupBox = new System.Windows.Forms.GroupBox();
            this.selectedMethodLabel = new System.Windows.Forms.Label();
            this.methodLabel = new System.Windows.Forms.Label();
            this.deleteMethodButton = new System.Windows.Forms.Button();
            this.placementGroupBox.SuspendLayout();
            this.SuspendLayout();
            // 
            // categoryTreeView
            // 
            this.categoryTreeView.LineColor = System.Drawing.Color.Black;
            this.categoryTreeView.Size = new System.Drawing.Size(296, 404);
            this.categoryTreeView.AfterSelect += new System.Windows.Forms.TreeViewEventHandler(this.AfterCategoryTreeViewSelect);
            // 
            // okButton
            // 
            this.okButton.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.okButton.Location = new System.Drawing.Point(505, 352);
            this.okButton.Name = "okButton";
            this.okButton.Size = new System.Drawing.Size(113, 43);
            this.okButton.TabIndex = 13;
            this.okButton.TabStop = false;
            this.okButton.Text = "OK";
            this.okButton.UseVisualStyleBackColor = true;
            this.okButton.Click += new System.EventHandler(this.OnOkButtonClick);
            // 
            // placementGroupBox
            // 
            this.placementGroupBox.Controls.Add(this.selectedMethodLabel);
            this.placementGroupBox.Controls.Add(this.methodLabel);
            this.placementGroupBox.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.placementGroupBox.Location = new System.Drawing.Point(342, 50);
            this.placementGroupBox.Name = "placementGroupBox";
            this.placementGroupBox.Size = new System.Drawing.Size(241, 132);
            this.placementGroupBox.TabIndex = 12;
            this.placementGroupBox.TabStop = false;
            this.placementGroupBox.Text = "Einordnung";
            // 
            // selectedMethodLabel
            // 
            this.selectedMethodLabel.Location = new System.Drawing.Point(6, 80);
            this.selectedMethodLabel.Name = "selectedMethodLabel";
            this.selectedMethodLabel.Size = new System.Drawing.Size(229, 49);
            this.selectedMethodLabel.TabIndex = 100;
            this.selectedMethodLabel.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // methodLabel
            // 
            this.methodLabel.Location = new System.Drawing.Point(6, 16);
            this.methodLabel.Name = "methodLabel";
            this.methodLabel.Size = new System.Drawing.Size(229, 64);
            this.methodLabel.TabIndex = 2;
            this.methodLabel.Text = "Bitte wählen Sie die zu löschende Methode aus";
            this.methodLabel.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // deleteMethodButton
            // 
            this.deleteMethodButton.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.deleteMethodButton.Location = new System.Drawing.Point(407, 207);
            this.deleteMethodButton.Name = "deleteMethodButton";
            this.deleteMethodButton.Size = new System.Drawing.Size(113, 43);
            this.deleteMethodButton.TabIndex = 14;
            this.deleteMethodButton.TabStop = false;
            this.deleteMethodButton.Text = "Methode Löschen";
            this.deleteMethodButton.UseVisualStyleBackColor = true;
            this.deleteMethodButton.Click += new System.EventHandler(this.OnDeleteMethodButtonClick);
            // 
            // DeleteMethodFrame
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(629, 404);
            this.Controls.Add(this.deleteMethodButton);
            this.Controls.Add(this.okButton);
            this.Controls.Add(this.placementGroupBox);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
            this.Name = "DeleteMethodFrame";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "Methode löschen";
            this.Controls.SetChildIndex(this.categoryTreeView, 0);
            this.Controls.SetChildIndex(this.placementGroupBox, 0);
            this.Controls.SetChildIndex(this.okButton, 0);
            this.Controls.SetChildIndex(this.deleteMethodButton, 0);
            this.placementGroupBox.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Button okButton;
        private System.Windows.Forms.GroupBox placementGroupBox;
        private System.Windows.Forms.Label selectedMethodLabel;
        private System.Windows.Forms.Label methodLabel;
        private System.Windows.Forms.Button deleteMethodButton;
    }
}