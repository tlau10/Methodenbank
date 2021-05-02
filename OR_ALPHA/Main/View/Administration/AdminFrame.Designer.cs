namespace OrAlpha.Main.View.Administration
{
    partial class AdminFrame
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(AdminFrame));
            this.addMethodButton = new System.Windows.Forms.Button();
            this.addCategoryButton = new System.Windows.Forms.Button();
            this.deleteCategoryButton = new System.Windows.Forms.Button();
            this.deleteMethodButton = new System.Windows.Forms.Button();
            this.editCategoryButton = new System.Windows.Forms.Button();
            this.editMethodButton = new System.Windows.Forms.Button();
            this.signOffButton = new System.Windows.Forms.Button();
            this.changePasswordButton = new System.Windows.Forms.Button();
            this.deployChangesButton = new System.Windows.Forms.Button();
            this.adminToolTip = new System.Windows.Forms.ToolTip(this.components);
            this.SuspendLayout();
            // 
            // addMethodButton
            // 
            this.addMethodButton.Location = new System.Drawing.Point(57, 84);
            this.addMethodButton.Name = "addMethodButton";
            this.addMethodButton.Size = new System.Drawing.Size(191, 73);
            this.addMethodButton.TabIndex = 0;
            this.addMethodButton.TabStop = false;
            this.addMethodButton.Text = "Methode hinzufügen";
            this.adminToolTip.SetToolTip(this.addMethodButton, "Zeigt den kompletten Kategoriebaum, sowie alle Methoden und ermöglicht es, weiter" +
        "e Methoden hinzuzufügen.");
            this.addMethodButton.UseVisualStyleBackColor = true;
            this.addMethodButton.Click += new System.EventHandler(this.OnAddMethodButtonClick);
            // 
            // addCategoryButton
            // 
            this.addCategoryButton.Location = new System.Drawing.Point(298, 84);
            this.addCategoryButton.Name = "addCategoryButton";
            this.addCategoryButton.Size = new System.Drawing.Size(191, 73);
            this.addCategoryButton.TabIndex = 1;
            this.addCategoryButton.TabStop = false;
            this.addCategoryButton.Text = "Kategorie hinzufügen";
            this.adminToolTip.SetToolTip(this.addCategoryButton, "Zeigt lediglich angelegte Kategorien an und ermöglicht es, weitere Kategorien hin" +
        "zuzufügen.");
            this.addCategoryButton.UseVisualStyleBackColor = true;
            this.addCategoryButton.Click += new System.EventHandler(this.OnAddCategoryButtonClick);
            // 
            // deleteCategoryButton
            // 
            this.deleteCategoryButton.Location = new System.Drawing.Point(299, 206);
            this.deleteCategoryButton.Name = "deleteCategoryButton";
            this.deleteCategoryButton.Size = new System.Drawing.Size(191, 73);
            this.deleteCategoryButton.TabIndex = 3;
            this.deleteCategoryButton.TabStop = false;
            this.deleteCategoryButton.Text = "Kategorie löschen";
            this.adminToolTip.SetToolTip(this.deleteCategoryButton, "Zeigt lediglich angelegte Kategorien an und ermöglicht es Kategorien zu löschen.");
            this.deleteCategoryButton.UseVisualStyleBackColor = true;
            this.deleteCategoryButton.Click += new System.EventHandler(this.OnDeleteCategoryButtonClick);
            // 
            // deleteMethodButton
            // 
            this.deleteMethodButton.Location = new System.Drawing.Point(58, 206);
            this.deleteMethodButton.Name = "deleteMethodButton";
            this.deleteMethodButton.Size = new System.Drawing.Size(191, 73);
            this.deleteMethodButton.TabIndex = 2;
            this.deleteMethodButton.TabStop = false;
            this.deleteMethodButton.Text = "Methode löschen";
            this.adminToolTip.SetToolTip(this.deleteMethodButton, "Zeigt den kompletten Kategoriebaum, sowie alle Methoden und ermöglicht es Methode" +
        "n zu löschen.");
            this.deleteMethodButton.UseVisualStyleBackColor = true;
            this.deleteMethodButton.Click += new System.EventHandler(this.OnDeleteMethodButtonClick);
            // 
            // editCategoryButton
            // 
            this.editCategoryButton.Location = new System.Drawing.Point(299, 327);
            this.editCategoryButton.Name = "editCategoryButton";
            this.editCategoryButton.Size = new System.Drawing.Size(191, 73);
            this.editCategoryButton.TabIndex = 5;
            this.editCategoryButton.TabStop = false;
            this.editCategoryButton.Text = "Kategorie bearbeiten";
            this.adminToolTip.SetToolTip(this.editCategoryButton, "Zeigt lediglich angelegte Kategorien an und ermöglicht es diese zu bearbeiten.");
            this.editCategoryButton.UseVisualStyleBackColor = true;
            this.editCategoryButton.Click += new System.EventHandler(this.OnEditCategoryButtonClick);
            // 
            // editMethodButton
            // 
            this.editMethodButton.Location = new System.Drawing.Point(58, 327);
            this.editMethodButton.Name = "editMethodButton";
            this.editMethodButton.Size = new System.Drawing.Size(191, 73);
            this.editMethodButton.TabIndex = 4;
            this.editMethodButton.TabStop = false;
            this.editMethodButton.Text = "Methode bearbeiten";
            this.adminToolTip.SetToolTip(this.editMethodButton, "Zeigt den kompletten Kategoriebaum, sowie alle Methoden und ermöglicht es Methode" +
        "n zu bearbeiten.");
            this.editMethodButton.UseVisualStyleBackColor = true;
            this.editMethodButton.Click += new System.EventHandler(this.OnEditMethodButtonClick);
            // 
            // signOffButton
            // 
            this.signOffButton.Location = new System.Drawing.Point(433, 5);
            this.signOffButton.Name = "signOffButton";
            this.signOffButton.Size = new System.Drawing.Size(113, 43);
            this.signOffButton.TabIndex = 7;
            this.signOffButton.TabStop = false;
            this.signOffButton.Text = "Abmelden";
            this.signOffButton.UseVisualStyleBackColor = true;
            this.signOffButton.Click += new System.EventHandler(this.OnSignOffButtonClick);
            // 
            // changePasswordButton
            // 
            this.changePasswordButton.Location = new System.Drawing.Point(298, 5);
            this.changePasswordButton.Name = "changePasswordButton";
            this.changePasswordButton.Size = new System.Drawing.Size(113, 43);
            this.changePasswordButton.TabIndex = 8;
            this.changePasswordButton.TabStop = false;
            this.changePasswordButton.Text = "Passwort ändern";
            this.adminToolTip.SetToolTip(this.changePasswordButton, "Hier können Sie das Passwort des Administrator-Accounts ändern.");
            this.changePasswordButton.UseVisualStyleBackColor = true;
            this.changePasswordButton.Click += new System.EventHandler(this.OnChangePasswordButtonClick);
            // 
            // deployChangesButton
            // 
            this.deployChangesButton.Location = new System.Drawing.Point(5, 5);
            this.deployChangesButton.Name = "deployChangesButton";
            this.deployChangesButton.Size = new System.Drawing.Size(113, 43);
            this.deployChangesButton.TabIndex = 9;
            this.deployChangesButton.TabStop = false;
            this.deployChangesButton.Text = "Änderungen übertragen";
            this.adminToolTip.SetToolTip(this.deployChangesButton, "Nach einer Änderung muss diese an den Server übertragen werden.");
            this.deployChangesButton.UseVisualStyleBackColor = true;
            this.deployChangesButton.Click += new System.EventHandler(this.OnDeployChangesButtonClick);
            // 
            // adminToolTip
            // 
            this.adminToolTip.AutoPopDelay = 5000000;
            this.adminToolTip.InitialDelay = 500;
            this.adminToolTip.IsBalloon = true;
            this.adminToolTip.ReshowDelay = 500;
            // 
            // AdminFrame
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(551, 426);
            this.Controls.Add(this.deployChangesButton);
            this.Controls.Add(this.changePasswordButton);
            this.Controls.Add(this.signOffButton);
            this.Controls.Add(this.editCategoryButton);
            this.Controls.Add(this.editMethodButton);
            this.Controls.Add(this.deleteCategoryButton);
            this.Controls.Add(this.deleteMethodButton);
            this.Controls.Add(this.addCategoryButton);
            this.Controls.Add(this.addMethodButton);
            this.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximizeBox = false;
            this.Name = "AdminFrame";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "Administration";
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Button addMethodButton;
        private System.Windows.Forms.Button addCategoryButton;
        private System.Windows.Forms.Button deleteCategoryButton;
        private System.Windows.Forms.Button deleteMethodButton;
        private System.Windows.Forms.Button editCategoryButton;
        private System.Windows.Forms.Button editMethodButton;
        private System.Windows.Forms.Button signOffButton;
        private System.Windows.Forms.Button changePasswordButton;
        private System.Windows.Forms.Button deployChangesButton;
        private System.Windows.Forms.ToolTip adminToolTip;
    }
}