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
    public partial class Solveroutput : Form
    {
 

        public Solveroutput()
        {
            InitializeComponent();
         
        }

        public void showOutput(string output) {
            richTextBox1.Text = output;     
        }

        public void ChangeTitle(string title)
        {
            this.Text = "OR_Alpha Solver Output: " + title;
        }
        private void closeForm(object sender, EventArgs e)
        {
            this.Close();
        }

        private void setTopMostBack(object sender, EventArgs e)
        {
            this.TopMost = false;
        }
    }
}
