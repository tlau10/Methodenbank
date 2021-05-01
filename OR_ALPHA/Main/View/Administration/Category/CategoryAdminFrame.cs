using OrAlpha.Main.DomainMethodenbank;
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
    public partial class CategoryAdminFrame : TemplateFrame
    {
        public CategoryAdminFrame()
        {
            InitializeComponent();
        }

        public CategoryAdminFrame(MethodService methodService)
            : base(methodService)
        {
            InitializeComponent();
        }


        private void OnSelectHtmlFileButtonClick(object sender, EventArgs e)
        {
            openFileDialog.InitialDirectory = Properties.Settings.Default.HtmlPath;
            openFileDialog.FileName = null;
            openFileDialog.ShowDialog();
            if (!String.IsNullOrEmpty(openFileDialog.FileName))
                htmlFileTextBox.Text = GetFileName(openFileDialog.FileName);
        }

        protected bool IsValid()
        {
            if (String.IsNullOrEmpty(categoryNameTextBox.Text))
            {
                MessageBox.Show("Bitte geben Sie einen Namen für die Kategorie an.", "Name fehlt");
                return false;
            }
            return true;
        }

        private void OnCancelButtonClick(object sender, EventArgs e)
        {
            this.Close();
        }

        protected Category MapFormToCategory()
        {
            Category category = new Category();
            category.Name = categoryNameTextBox.Text;
            category.HtmlFile = htmlFileTextBox.Text;
            return category;
        }


    }
}
