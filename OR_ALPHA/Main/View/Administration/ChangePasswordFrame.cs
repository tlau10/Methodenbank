using OrAlpha.Main.Service;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace OrAlpha.Main.View.Administration
{
    public partial class ChangePasswordFrame : Form
    {
        private MethodService methodService;

        public ChangePasswordFrame(MethodService methodService)
        {
            this.methodService = methodService;
            InitializeComponent();
        }

        private void OnSavePasswordButtonClick(object sender, EventArgs e)
        {
            if (!String.IsNullOrEmpty(newPasswordTextBox.Text) && newPasswordTextBox.Text.Equals(newPasswordConfirmationTextBox.Text))
                ChangePasswordAndNotifyUser();
            else
                MessageBox.Show("Die zwei Passwörter müssen übereinstimmen und dürfen nicht leer sein!", "Ungültige Eingabe");
        }

        private void ChangePasswordAndNotifyUser()
        {
            methodService.ChangeAdminPassword(newPasswordTextBox.Text);
            MessageBox.Show("Passwort erfolgreich geändert!");
            this.Close();
        }

        private void OnCancelButtonClick(object sender, EventArgs e)
        {
            this.Close();
        }
    }
}
