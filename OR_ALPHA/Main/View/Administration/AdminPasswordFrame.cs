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
    public partial class AdminPasswordFrame : Form
    {
        private MethodService methodService;

        public AdminPasswordFrame(MethodService methodService)
        {
            this.methodService = methodService;
            InitializeComponent();
        }

        private void OnCancelButtonClick(object sender, EventArgs e)
        {
            this.Close();
        }

        private void OnSignOnButtonClick(object sender, EventArgs e)
        {
            if (methodService.AuthenticateAdminUser(usernameTextBox.Text, passwordTextBox.Text))
                ShowAdminFrame();
            else
                MessageBox.Show("Falschen Benutzernamen oder falsches Passwort eingegeben!", "Falsche Anmeldeinformationen!");
        }

        private void ShowAdminFrame()
        {
            new AdminFrame(methodService).Show();
            this.Close();
        }

        private void OnPasswordTextBoxKeyDown(object sender, KeyEventArgs e)
        {
            if (e.KeyCode == Keys.Enter)
                OnSignOnButtonClick(null, null);
        }
    }
}
