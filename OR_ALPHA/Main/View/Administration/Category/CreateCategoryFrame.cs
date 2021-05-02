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
    public partial class CreateCategoryFrame : CategoryAdminFrame
    {

        public CreateCategoryFrame() { }

        public CreateCategoryFrame(MethodService methodService) : base(methodService)
        {
            InitializeComponent();
            LoadCategoryTree();
        }

        private void AfterCategoryTreeViewSelect(object sender, TreeViewEventArgs e)
        {
            selectedCategoryLabel.Text = e.Node.Text;
        }

        private void OnSaveButtonClick(object sender, EventArgs e)
        {
            CreateNewCategoryFromForm();
            this.Close();
        }

        private void CreateNewCategoryFromForm()
        {
            if (IsValid())
                methodService.CreateNewCategory(MapFormToCategory(), categoryTreeView.SelectedNode.Text);
        }
    }
}
