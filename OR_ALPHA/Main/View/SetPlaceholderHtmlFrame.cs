using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace OrAlpha.Main.View
{
    public partial class SetPlaceholderHtmlFrame : Form
    {
        public SetPlaceholderHtmlFrame()
        {
            InitializeComponent();
            htmlFileTextbox.Text = Properties.Settings.Default.PlaceholderHtmlFile;    
        }

        private void OnOkButtonClick(object sender, EventArgs e)
        {
            Properties.Settings.Default.PlaceholderHtmlFile = htmlFileTextbox.Text;
            Properties.Settings.Default.Save();
            this.Close();
        }

    }
}
