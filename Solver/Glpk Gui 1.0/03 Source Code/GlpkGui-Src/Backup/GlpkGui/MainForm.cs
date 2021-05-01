using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

using System.IO;
using System.Threading;

using GlpkSharp;

namespace GlpkGui
{
    public partial class MainForm : Form
    {
        public MainForm()
        {
            InitializeComponent();
            InitializeComboBoxes();
            solverView1.TableauView.EmptyProblem();
        }


        private void numericUpDown1_ValueChanged(object sender, EventArgs e)
        {

            //solverView1.TableauView._Update = true;
            solverView1.TableauView.SetRestrictionCount((int)numericUpDown1.Value-1);

        }

        private void numericUpDown2_ValueChanged(object sender, EventArgs e)
        {
            //solverView1.TableauView._Update = true;
            solverView1.TableauView.SetVariableCount((int)numericUpDown2.Value);
        }



        private void OpenFile(object sender, EventArgs e)
        {
            DialogResult result;
            if (solverView1.TableauView.NeedsSave)
            {
                result = WantSave();
            }
            else
            {
                result = DialogResult.No;
            }

            if (result == DialogResult.No)
            {
                solverView1.TableauView._Correct = true;
                solverView1.tabControl1.Focus();
                OpenFileDialog openFileDialog = new OpenFileDialog();
                openFileDialog.InitialDirectory = Environment.GetFolderPath(Environment.SpecialFolder.Personal);
                openFileDialog.Filter = global::GlpkGui.Properties.Resources.fileFilter;
                if (openFileDialog.ShowDialog(this) == DialogResult.OK)
                {
                    if(openFileDialog.FileName.EndsWith(".mps",true,null))
                    {
                        LPProblem p = new LPProblem();
                        if (p.ReadMPS(openFileDialog.FileName, MPSFORMAT.FixedMPS) 
                            && p.GetRowsCount() < 102 
                            && p.GetRowsCount() > 1 
                            && p.GetColsCount() < 76 
                            && p.GetColsCount() > 1)
                        {
                            FileName = openFileDialog.FileName;

                            solverView1.TableauView.LPProblem = p;

                            numericUpDown1.Value = p.GetRowsCount();
                            numericUpDown2.Value = p.GetColsCount();

                        }
                        else
                        {
                            MessageBox.Show(global::GlpkGui.Properties.Resources.openError, global::GlpkGui.Properties.Resources.openErrorTitle, MessageBoxButtons.OK, MessageBoxIcon.Error);
                        }
                    }
                    else if (openFileDialog.FileName.EndsWith(".lpi", true, null))
                    {
                        LPProblem p = new LPProblem();
                        if (p.ReadLPI(openFileDialog.FileName)
                            && p.GetRowsCount() < 102
                            && p.GetRowsCount() > 1
                            && p.GetColsCount() < 76
                            && p.GetColsCount() > 1)
                        {
                            FileName = openFileDialog.FileName;

                            solverView1.TableauView.LPProblem = p;

                            numericUpDown1.Value = p.GetRowsCount();
                            numericUpDown2.Value = p.GetColsCount();

                        }
                        else
                        {
                            MessageBox.Show(global::GlpkGui.Properties.Resources.openError, global::GlpkGui.Properties.Resources.openErrorTitle, MessageBoxButtons.OK, MessageBoxIcon.Error);
                        }

                    }
                    else
                    {
                        MessageBox.Show(global::GlpkGui.Properties.Resources.openError, global::GlpkGui.Properties.Resources.openErrorTitle, MessageBoxButtons.OK, MessageBoxIcon.Error);
                    }

                }
            }
            else if (result == DialogResult.Yes)
            {
                Save(null,null);
            }
        }

        private void Solve(object sender, EventArgs e)
        {
            solverView1.tabControl1.Focus();
            if (solverView1.TableauView.IsProper())
            {
                LPProblem p = solverView1.TableauView.LPProblem;

                ApplySimplexMethod(p);
                ApplyPricing(p);

                String dir = Environment.GetEnvironmentVariable("TEMP") + "\\";
                DateTime dt = DateTime.Now; 
                String baseFile = "glpkgui_" + dt.ToFileTime().ToString();
                String lp = "_lp.txt";
                String mip = "_mip.txt";
                String ip = "_ip.txt";
                String sens = "_sens.txt";

                //solverView1.OutputText.Text = "";
                //Builder = new StringBuilder();
                //p.TermHook(new TermHookDelegate(Hook));
                //p.Clear();
                p.SolveSimplex();
                p.SolveInteger();
                p.InteriorPoint();
                //p.TermHook(null);
                //solverView1.OutputText.AppendText(Builder.ToString());


                TextReader tr;

                String file = dir + baseFile + lp;
                if (p.WriteSol(file))
                {
                    tr = new StreamReader(file);
                    solverView1._LPText.Text = tr.ReadToEnd();
                    tr.Close();
                    File.Delete(file);
                }
                else
                {
                    solverView1._LPText.Text = global::GlpkGui.Properties.Resources.lpError.ToUpper();
                }

                file = dir + baseFile + mip;                
                if (p.WriteMIP(file))
                {
                    tr = new StreamReader(file);
                    solverView1._MIPText.Text = tr.ReadToEnd();
                    tr.Close();
                    File.Delete(file);
                }
                else
                {
                    solverView1._MIPText.Text = global::GlpkGui.Properties.Resources.mipError.ToUpper();
                }

                file = dir + baseFile + ip; 
                if (p.WriteIPS(file))
                {
                    tr = new StreamReader(file);
                    solverView1._IPText.Text = tr.ReadToEnd();
                    tr.Close();
                    File.Delete(file);
                }
                else
                {
                    solverView1._MIPText.Text = global::GlpkGui.Properties.Resources.ipError.ToUpper();
                }

                file = dir + baseFile + sens;
                if (p.WriteBoundsSensitivity(file))
                {
                    tr = new StreamReader(file);
                    solverView1._SensText.Text = tr.ReadToEnd();
                    tr.Close();
                    File.Delete(file);
                }
                else
                {
                    solverView1._SensText.Text = global::GlpkGui.Properties.Resources.sensError.ToUpper();
                }


            }
            else
            {
                ShowCorrect();
            }
        }


        private String FileName = null;

        private int Hook(String s)
        {
            //Builder.Append(s);
            
            Console.Write(s);
            return 1;
        }


        private void ShowCorrect()
        {
            //System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(MainForm));
            MessageBox.Show(global::GlpkGui.Properties.Resources.correctWarning, global::GlpkGui.Properties.Resources.correctWarningTitle, MessageBoxButtons.OK, MessageBoxIcon.Warning);
        }

        private  DialogResult WantSave()
        {
            //System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(MainForm));
            return MessageBox.Show(global::GlpkGui.Properties.Resources.saveQuestion, global::GlpkGui.Properties.Resources.saveQuestionTitle, MessageBoxButtons.YesNoCancel, MessageBoxIcon.Question);
        }

        private void New(object sender, EventArgs e)
        {
            DialogResult result;
            if (solverView1.TableauView.NeedsSave)
            {
                result = WantSave();
            }
            else
            {
                result = DialogResult.No;
            }

            if (result == DialogResult.No)
            {
                FileName = null;

                solverView1.TableauView._Correct = true;
                solverView1.tabControl1.Focus();
                solverView1.TableauView.EmptyProblem();


                this.numericUpDown1.Value = 3;//ganz schön unschön
                this.numericUpDown2.Value = 2;//ich habe kein bock mehr

            }
            else if (result == DialogResult.Yes)
            {
                Save(null,null);
            }
        }

        private void Exit(object sender, EventArgs e)
        {
            OnClose(null, null);
        }

        private void SaveAs(object sender, EventArgs e)
        {
            solverView1.tabControl1.Focus();
            if (solverView1.TableauView.IsProper())
            {
                SaveFileDialog saveFileDialog = new SaveFileDialog();
                saveFileDialog.InitialDirectory = Environment.GetFolderPath(Environment.SpecialFolder.Personal);
                //System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(MainForm));
                saveFileDialog.Filter = global::GlpkGui.Properties.Resources.fileFilter;
                if (saveFileDialog.ShowDialog(this) == DialogResult.OK)
                {
                    FileName = saveFileDialog.FileName;
                    if (FileName.EndsWith(".mps", true, null))
                    {
                        WriteMPS(FileName);
                    }
                    else if (FileName.EndsWith(".lpi", true, null))
                    {
                        WriteLPI(FileName);
                    }

                }
            }
            else
            {
                ShowCorrect();
            }
        }

        private void Save(object sender, EventArgs e)
        {
            if (FileName == null)
            {
                SaveAs(null, null);
            }
            else
            {
                solverView1.tabControl1.Focus();
                if (solverView1.TableauView.IsProper())
                {
                    if (FileName.EndsWith(".mps", true, null))
                    {
                        WriteMPS(FileName);
                    }
                    else if (FileName.EndsWith(".lpi", true, null))
                    {
                        WriteLPI(FileName);
                    }
                }
                else
                {
                    ShowCorrect();
                }
            }


        }

        private void WriteMPS(String file)
        {
            LPProblem p = solverView1.TableauView.LPProblem;

            if (p.WriteMPS(file, MPSFORMAT.FixedMPS))
            {
                solverView1.TableauView.NeedsSave = false;
            }
            else
            {
                //System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(MainForm));
                MessageBox.Show(global::GlpkGui.Properties.Resources.saveError, global::GlpkGui.Properties.Resources.saveErrorTitle,MessageBoxButtons.OK, MessageBoxIcon.Error);

            }
        }

        private void WriteLPI(String file)
        {
            LPProblem p = solverView1.TableauView.LPProblem;

            if (p.WriteLPI(file))
            {
                solverView1.TableauView.NeedsSave = false;
            }
            else
            {
                //System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(MainForm));
                MessageBox.Show(global::GlpkGui.Properties.Resources.saveError, global::GlpkGui.Properties.Resources.saveErrorTitle, MessageBoxButtons.OK, MessageBoxIcon.Error);

            }
        }

        private void ApplySimplexMethod(LPProblem p)
        {
            SMCP conf = p.SimplexControlParams;
            //conf.presolve = 1;
            //Console.WriteLine("SMCP.presolve: " + conf.presolve);

            switch (comboBox1.SelectedIndex)
            {
                case 0: conf.meth = SimplexMethod.Primal; break;

                case 1: conf.meth = SimplexMethod.DualPrimal; break;

                default: conf.meth = SimplexMethod.Dual; break;
            }
            //Console.WriteLine("combo1: " + comboBox1.SelectedIndex);
            p.SimplexControlParams = conf;
        }

        private void ApplyPricing(LPProblem p)
        {
            SMCP conf = p.SimplexControlParams;


            switch (comboBox2.SelectedIndex)
            {
                case 0: conf.pricing = Pricing.Standard; break;
                default: conf.pricing = Pricing.ProjectedSteepest; break;
            }
            p.SimplexControlParams = conf;
        }


        private void InitializeComboBoxes()
        {
            //comboBox2.
            comboBox1.Items.Add(global::GlpkGui.Properties.Resources.methodPrimal);
            comboBox1.Items.Add(global::GlpkGui.Properties.Resources.methodDualPrimal);
            comboBox1.Items.Add(global::GlpkGui.Properties.Resources.methodDual);
            comboBox1.SelectedIndex = 0;

            comboBox2.Items.Add(global::GlpkGui.Properties.Resources.pricingStandard);
            comboBox2.Items.Add(global::GlpkGui.Properties.Resources.pricingSteepest);
            comboBox2.SelectedIndex = 0;
        }

        private void OnClose(object sender, FormClosingEventArgs e)
        {
            //Console.WriteLine("hä");
            DialogResult result;
            if (solverView1.TableauView.NeedsSave)
            {
                result = WantSave();
            }
            else
            {
                result = DialogResult.No;
            }

            if (result == DialogResult.No)
            {
                //this.FormClosing -= null;
                Environment.Exit(1);
                //this.Close();
            }
            else if (result == DialogResult.Yes)
            {
                Save(null, null);
            }
            else
            {
                e.Cancel = true;
            }
        }


    }
}
