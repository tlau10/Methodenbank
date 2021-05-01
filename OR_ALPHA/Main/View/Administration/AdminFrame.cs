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
    public partial class AdminFrame : Form
    {
        private MethodService methodService;

        public AdminFrame(MethodService methodService)
        {
            this.methodService = methodService;
            InitializeComponent();
        }

        private void OnSignOffButtonClick(object sender, EventArgs e)
        {
            this.Close();
        }

        private void OnAddCategoryButtonClick(object sender, EventArgs e)
        {
            new CreateCategoryFrame(methodService).Show();
        }

        private void OnAddMethodButtonClick(object sender, EventArgs e)
        {
            new CreateMethodFrame(methodService).Show();
        }

        private void OnDeleteMethodButtonClick(object sender, EventArgs e)
        {
            new DeleteMethodFrame(methodService).Show();
        }

        private void OnEditMethodButtonClick(object sender, EventArgs e)
        {
            new UpdateMethodFrame(methodService).Show();
        }

        private void OnDeleteCategoryButtonClick(object sender, EventArgs e)
        {
            new DeleteCategoryFrame(methodService).Show();
        }

        private void OnEditCategoryButtonClick(object sender, EventArgs e)
        {
            new UpdateCategoryFrame(methodService).Show();
        }

        private void OnDeployChangesButtonClick(object sender, EventArgs e)
        {
            new DeployChangesFrame().Show();
        }

        private void OnChangePasswordButtonClick(object sender, EventArgs e)
        {
            new ChangePasswordFrame(methodService).Show();
        }

    }
}
