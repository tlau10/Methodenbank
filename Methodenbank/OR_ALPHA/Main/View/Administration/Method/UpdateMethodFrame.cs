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
    public partial class UpdateMethodFrame : MethodAdminFrame
    {

        public UpdateMethodFrame()
        {
            InitializeComponent();
        }

        public UpdateMethodFrame(MethodService methodService): base(methodService)
        {
            InitializeComponent();
            LoadMethodTree();
            FillComboBox();
        }

        private void FillComboBox()
        {
            foreach (string category in methodService.GetAllCategoryNames())
                categoryComboBox.Items.Add(category);
        }

        private void AfterCategoryTreeViewSelect(object sender, TreeViewEventArgs e)
        {
            Method method = methodService.FindMethodByName(e.Node.Text);
            if (method != null)
                MapMethodToForm(method);
            else
                ClearAndDisableForm();
        }

        private void ClearAndDisableForm()
        {
            methodNameTextBox.Text = null;
            executionPathTextBox.Text = null;
            documentationPathTextBox.Text = null;
            htmlFileTextBox.Text = null;
            webtoolCheckbox.Checked = false;
            categoryComboBox.Text = null;
            DisableForm();
        }

        new private void DisableForm()
        {
            base.DisableForm();
            methodNameTextBox.Enabled = false;
            executionPathTextBox.Enabled = false;
            documentationPathTextBox.Enabled = false;
            htmlFileTextBox.Enabled = false;
            webtoolCheckbox.Enabled = false;
            categoryComboBox.Enabled = false;
            saveButton.Enabled = false;
        }

        private void MapMethodToForm(Method method)
        {
            methodNameTextBox.Text = method.Name;
            documentationPathTextBox.Text = method.DocumentationPath;
            htmlFileTextBox.Text = method.HtmlFile;
            executionPathTextBox.Text = method is WebMethod ? ((WebMethod)method).Url : ((NativeMethod)method).ExecutionPath;
            webtoolCheckbox.Checked = (method is WebMethod);
            categoryComboBox.SelectedIndex = categoryComboBox.FindStringExact(categoryTreeView.SelectedNode.Parent.Text);
            EnableForm();
        }

        new private void EnableForm()
        {
            base.EnableForm();
            methodNameTextBox.Enabled = true;
            executionPathTextBox.Enabled = true;
            documentationPathTextBox.Enabled = true;
            htmlFileTextBox.Enabled = true;
            webtoolCheckbox.Enabled = true;
            categoryComboBox.Enabled = true;
            saveButton.Enabled = true;
        }

        private void OnSaveButtonClick(object sender, EventArgs e)
        {
            if (IsValid())
            {
                methodService.DeleteMethod(categoryTreeView.SelectedNode.Text, categoryTreeView.SelectedNode.Parent.Text);
                methodService.CreateNewMethod(MapFormToMethod(), categoryComboBox.Text);
                this.Close();
            }

        }

    }
}
