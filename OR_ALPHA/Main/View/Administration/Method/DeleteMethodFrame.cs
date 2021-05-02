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
    public partial class DeleteMethodFrame : TemplateFrame
    {
        public DeleteMethodFrame()
        {
            InitializeComponent();
        }

        public DeleteMethodFrame(MethodService methodService)
            : base(methodService)
        {
            InitializeComponent();
            LoadMethodTree();
        }

        private void OnDeleteMethodButtonClick(object sender, EventArgs e)
        {
            DialogResult dialogResult = MessageBox.Show("Wollen Sie diese Methode wirklich löschen?", "Bestätigung", MessageBoxButtons.YesNo);
            if (dialogResult == DialogResult.Yes)
            {
                DeleteMethod();
            }
            
        }

        private void DeleteMethod()
        {
                methodService.DeleteMethod(categoryTreeView.SelectedNode.Text,
                    categoryTreeView.SelectedNode.Parent.Text);
            UpdateTreeView();
        }

        private bool IsMethod(string name)
        {
            return (methodService.FindMethodByName(name) != null);
        }

        private void UpdateTreeView()
        {
            categoryTreeView.Nodes.Clear();
            categoryTreeView.Nodes.Add(methodService.GetMethodTree());
            categoryTreeView.ExpandAll();
        }

        private void AfterCategoryTreeViewSelect(object sender, TreeViewEventArgs e)
        {
            ResetForm();
            if (IsMethod(categoryTreeView.SelectedNode.Text))
            {
                selectedMethodLabel.Text = e.Node.Text;
                deleteMethodButton.Enabled = true;
            }
            
        }

        private void ResetForm()
        {
            selectedMethodLabel.Text = null;
            deleteMethodButton.Enabled = false;
        }

        private void OnOkButtonClick(object sender, EventArgs e)
        {
            this.Close();
        }
    }
}
