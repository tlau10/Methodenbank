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
    public partial class SetHtmlPathFrame : Form
    {
        public SetHtmlPathFrame()
        {
            InitializeComponent();
            htmlPathTextBox.Text = Properties.Settings.Default.HtmlPath;
        }

        private void OnOkButtonClick(object sender, EventArgs e)
        {
            Properties.Settings.Default.HtmlPath = htmlPathTextBox.Text;
            Properties.Settings.Default.Save();
            this.Close();
        }

    }
}
