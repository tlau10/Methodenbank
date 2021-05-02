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
    public partial class TemplateFrame : Form
    {
        protected MethodService methodService;

        public TemplateFrame() {
            InitializeComponent();
        }

        public TemplateFrame(MethodService methodService)
        {
            this.methodService = methodService;
            InitializeComponent();
        }

        protected void LoadMethodTree()
        {
            categoryTreeView.Nodes.Clear();
            categoryTreeView.Nodes.Add(methodService.GetMethodTree());
            categoryTreeView.ExpandAll();
        }

        
        protected void LoadCategoryTree()
        {
            categoryTreeView.Nodes.Clear();
            categoryTreeView.Nodes.Add(methodService.GetCategoryTree());
            categoryTreeView.ExpandAll();
        }

        protected string GetFileName(string fileName)
        {
            return fileName.Replace(Properties.Settings.Default.HtmlPath, "");
        }

    }
}
