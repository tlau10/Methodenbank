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
    public partial class SetXmlConfigurationFileFrame : Form
    {
        public SetXmlConfigurationFileFrame()
        {
            InitializeComponent();
            xmlFilePathTextbox.Text = Properties.Settings.Default.XmlFilePath;        
        }

        private void OnOkButtonClick(object sender, EventArgs e)
        {
            Properties.Settings.Default.XmlFilePath = xmlFilePathTextbox.Text;
            Properties.Settings.Default.Save();
            this.Close();
        }
  
             
      
   }
}
