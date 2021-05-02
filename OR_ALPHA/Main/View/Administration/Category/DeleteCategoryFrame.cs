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
    public partial class DeleteCategoryFrame : TemplateFrame
    {
        public DeleteCategoryFrame()
        {
            InitializeComponent();
        }

        public DeleteCategoryFrame(MethodService methodService)
            : base(methodService)
        {
            InitializeComponent();
            LoadMethodTree();
        }

        private void AfterCategoryTreeViewSelect(object sender, TreeViewEventArgs e)
        {
            ResetForm();
            if (methodService.FindCategoryByName(categoryTreeView.SelectedNode.Text) != null)
            {
                deleteCategoryButton.Enabled = true;
                selectedCategoryLabel.Text = categoryTreeView.SelectedNode.Text;
            }

        }

        private void ResetForm()
        {
            deleteCategoryButton.Enabled = false;
            selectedCategoryLabel.Text = null;
        }

        private void OnOkButtonClick(object sender, EventArgs e)
        {
            this.Close();
        }

        private void OnDeleteCategoryButtonClick(object sender, EventArgs e)
        {
            DialogResult dialogResult = MessageBox.Show("Wollen Sie diese Kategorie wirklich löschen?", "Bestätigung", MessageBoxButtons.YesNo);
            if (dialogResult == DialogResult.Yes)
            {
                methodService.DeleteCategory(categoryTreeView.SelectedNode.Text, categoryTreeView.SelectedNode.Parent.Text);
                LoadMethodTree();
            }
        }
    }
}
