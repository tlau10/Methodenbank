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
    public partial class UpdateCategoryFrame : CategoryAdminFrame
    {
        public UpdateCategoryFrame()
        {
            InitializeComponent();
        }

        public UpdateCategoryFrame(MethodService methodService)
            : base(methodService)
        {
            InitializeComponent();
            LoadCategoryTree();
            FillComboBox();
        }

        private void FillComboBox()
        {
            foreach (string categoryContainer in methodService.GetAllCategoryContainerNames())
                categoryContainerComboBox.Items.Add(categoryContainer);
        }

        private void AfterCategoryTreeViewSelect(object sender, TreeViewEventArgs e)
        {
            DisableForm();
            Category category = methodService.FindCategoryByName(categoryTreeView.SelectedNode.Text);
            if (category != null)
                MapCategoryToForm(category);
        }

        private void MapCategoryToForm(Category category)
        {
            categoryContainerComboBox.SelectedIndex = categoryContainerComboBox.FindStringExact(categoryTreeView.SelectedNode.Parent.Text);
            categoryContainerComboBox.Enabled = true;
            categoryNameTextBox.Text = category.Name;
            htmlFileTextBox.Text = category.HtmlFile;
            categoryNameTextBox.ReadOnly = false;
            htmlFileTextBox.ReadOnly = false;
            selectHtmlFileButton.Enabled = true;
            saveButton.Enabled = true;
        }

        private void DisableForm()
        {
            categoryContainerComboBox.Text = null;
            categoryContainerComboBox.Enabled = false;
            categoryNameTextBox.Text = null;
            htmlFileTextBox.Text = null;
            categoryNameTextBox.ReadOnly = true;
            htmlFileTextBox.ReadOnly = true;
            selectHtmlFileButton.Enabled = false;
            saveButton.Enabled = false;
        }

        private void OnSaveButtonClick(object sender, EventArgs e)
        {
            if (IsValid())
            {
                UpdateAndSaveCategory();
                this.Close();
            }
               
        }

        private void UpdateAndSaveCategory()
        {
            Category category = MapFormToCategory();
            methodService.UpdateCategory(categoryTreeView.SelectedNode.Text, category);
            if(ShouldBeMoved())
                 methodService.MoveCategory(category.Name, categoryTreeView.SelectedNode.Parent.Text, categoryContainerComboBox.Text);
        }

        private bool ShouldBeMoved()
        {
            return !categoryTreeView.SelectedNode.Parent.Text.Equals(categoryContainerComboBox.Text);
        }

    }
}
