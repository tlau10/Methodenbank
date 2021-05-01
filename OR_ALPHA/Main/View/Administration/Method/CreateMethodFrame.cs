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
    public partial class CreateMethodFrame : MethodAdminFrame
    {
        public CreateMethodFrame()
        {
            InitializeComponent();
        }

        public CreateMethodFrame(MethodService methodService)
            : base(methodService)
        {
            InitializeComponent();
            LoadCategoryTree();
        }


        new private bool IsValid()
        {
            if (base.IsValid())
            {
                if (methodService.FindCategoryByName(categoryTreeView.SelectedNode.Text) == null)
                {
                    MessageBox.Show("Bitte wählen Sie eine gültige Oberkategorie für die Methode aus!", "Ungültige Oberkategorie");
                    return false;
                }
                return true;
            }
            return false;
        }

        private void OnSaveButtonClick(object sender, EventArgs e)
        {
            if (IsValid())
            {
                methodService.CreateNewMethod(MapFormToMethod(), categoryTreeView.SelectedNode.Text);
                this.Close();
            }
        }

        private void AfterCategoryTreeViewSelect(object sender, TreeViewEventArgs e)
        {
            selectedCategoryLabel.Text = e.Node.Text;
        }

    }
}
