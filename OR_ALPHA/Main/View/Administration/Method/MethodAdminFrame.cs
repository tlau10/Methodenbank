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
    public partial class MethodAdminFrame : TemplateFrame
    {
        public MethodAdminFrame()
        {
            InitializeComponent();
        }

        public MethodAdminFrame(MethodService methodService)
            : base(methodService)
        {
            InitializeComponent();
        }

        private void OnCancelButtonClick(object sender, EventArgs e)
        {
            this.Close();
        }

        private void OnSelectHtmlFileButtonClick(object sender, EventArgs e)
        {
            openFileDialog.InitialDirectory = Properties.Settings.Default.HtmlPath;
            openFileDialog.FileName = null;
            openFileDialog.ShowDialog();
            if (!String.IsNullOrEmpty(openFileDialog.FileName))
               htmlFileTextBox.Text = GetFileName(openFileDialog.FileName);
        }

        private void OnSelectDocumentationPathButtonClick(object sender, EventArgs e)
        {
            folderBrowserDialog.SelectedPath = documentationPathTextBox.Text;
            folderBrowserDialog.ShowDialog();
            if (!String.IsNullOrEmpty(folderBrowserDialog.SelectedPath))
                documentationPathTextBox.Text = folderBrowserDialog.SelectedPath;
        }

        private void OnSelectExecutionPathButtonClick(object sender, EventArgs e)
        {
            openFileDialog.FileName = null;
            openFileDialog.ShowDialog();
            if (!String.IsNullOrEmpty(openFileDialog.FileName))
                executionPathTextBox.Text = openFileDialog.FileName;
        }

        protected Boolean IsValid()
        {
            if (String.IsNullOrEmpty(methodNameTextBox.Text))
            {
                MessageBox.Show("Bitte geben Sie einen Namen für die Methode an.", "Name fehlt");
                return false;
            }
            return true;
        }

        protected Method MapFormToMethod()
        {
            Method method = CreateNewMethodOfCorrectType();
            method.Name = methodNameTextBox.Text;
            method.DocumentationPath = documentationPathTextBox.Text;
            method.HtmlFile = htmlFileTextBox.Text;
            SetExecutionPathOrUrl(method);
            return method;
        }

        private void SetExecutionPathOrUrl(Method method)
        {
            if (method is WebMethod)
                ((WebMethod)method).Url = executionPathTextBox.Text;
            else
                ((NativeMethod)method).ExecutionPath = executionPathTextBox.Text;
        }

        private Method CreateNewMethodOfCorrectType()
        {
            if (webtoolCheckbox.Checked)
                return new WebMethod();
            return new NativeMethod();
        }
 
        protected void DisableForm()
        {
            selectExecutionPathButton.Enabled = false;
            selectDocumentationPathButton.Enabled = false;
            selectHtmlFileButton.Enabled = false;
        }

        protected void EnableForm()
        {
            selectExecutionPathButton.Enabled = true;
            selectDocumentationPathButton.Enabled = true;
            selectHtmlFileButton.Enabled = true;
        }
    }
}
