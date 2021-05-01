using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using ZedGraph;

namespace Maschinenbelegungsplanung.Forms
{
    public partial class Graphics1 : Form{

        double[,,] results;
        double[,] resultsone;
        double [] ausgabe = new double[100];
        double[] ausgabe2 = new double[100];
        private AusgabeDaten ausgabeDaten;



        public Graphics1()
        {
            ausgabeDaten = new AusgabeDaten();
            InitializeComponent();
        }

        //Liniendiagramm eine Maschine
        public void CreateChart2(ZedGraphControl zgc)
        {
            GraphPane myPane = zgc.GraphPane;
            int counter = 0;
            // Set the title and axis labels
            myPane.Title.Text = "Auslastung 1 Maschine";
            myPane.XAxis.Title.Text = "Perioden";
            myPane.YAxis.Title.Text = "Zeit (in min)";
            int anzPerioden = resultsone.GetLength(0);
            int anzAuftraege = resultsone.GetLength(1);

            int anzVariablen = anzPerioden * anzAuftraege;

            int periodenzähler = 1;
            int zaehler = 0;
            double yWert = 0;

            PointPairList list = new PointPairList();

            for (int j = 1; j < anzPerioden; j++)
            {
                for (int i = 1; i < anzAuftraege; i++)
                {
                    ausgabe[counter] = resultsone[j, i];
                    counter++;
                }
            }

            for (int i = 0; i < anzPerioden; i++)
            {
                for (int j = 1; j < anzAuftraege; j++)

                    if (j < anzAuftraege - 1)
                    {
                        yWert += ausgabe[zaehler];
                        zaehler++;
                    }
                    else
                    {
                        yWert += ausgabe[zaehler];
                        double x = (double)periodenzähler;
                        double y = yWert;
                        list.Add(x, y);
                        yWert = 0;
                        periodenzähler++;
                        zaehler++;
                    }
            }
            // Generate a blue curve, symbols,the legend
            LineItem myCurve = myPane.AddCurve("Auslastung", list, Color.Blue, SymbolType.Circle);
            myPane.Chart.Fill.Color = System.Drawing.Color.Lavender;
            //Grid-Lines sichtbar machen
            myPane.YAxis.MajorGrid.IsVisible = true;
            myPane.YAxis.MinorGrid.IsVisible = true;
            myPane.YAxis.MajorGrid.Color = Color.Gray;
            myPane.YAxis.MinorGrid.Color = Color.LightGray;
            // Calculate the Axis Scale Ranges
            zgc.AxisChange();
            zedGraphControl2.Enabled = false;
            zedGraphControl2.Visible = false;
        }

        // Liniendiagramm 2 Maschinen
        public void CreateChart(ZedGraphControl zgc)
        {
            GraphPane myPane = zgc.GraphPane;
            GraphPane myPane2 = zedGraphControl2.GraphPane;
  
            int counter = 0;
            // Set the title and axis labels
            myPane.Title.Text = "Auslastung erste Maschine";
            myPane.XAxis.Title.Text = "Auftrag";
            myPane.YAxis.Title.Text = "Zeit (in min)";
            myPane2.Title.Text = "Auslastung 2 Maschine";
            myPane2.XAxis.Title.Text = "Auftrag";
            myPane2.YAxis.Title.Text = "Zeit (in min)";
            int anzPerioden = results.GetLength(0);
            int anzAuftraege = results.GetLength(1);
            int anzMaschi = results.GetLength(2);

            int zaehler = 0;
         

            double ywertMa1 = 0;
            double ywertMa2 = 0;

            int anzVariablen = anzPerioden * anzAuftraege;

            PointPairList list = new PointPairList();
            PointPairList list2 = new PointPairList();

            for (int j = 1; j < anzPerioden; j++)
            {
                for (int i = 1; i < anzAuftraege; i++)
                {
                            ausgabe[counter] = results[j, i, 1];
                            ausgabe2[counter] = results[j, i, 2];
                        
                        counter++;
                    
                }
            }

            for (int i = 0; i < anzPerioden; i++)
            {
                for (int j = 1; j < anzAuftraege; j++)
                
                 if (j < anzAuftraege-1)
                {
                    ywertMa1 += ausgabe[zaehler];
                    ywertMa2 += ausgabe2[zaehler];
                    zaehler++;
                }
                else
                {
                    ywertMa1 += ausgabe[zaehler];
                    ywertMa2 += ausgabe2[zaehler];
                    zaehler++;
                    double x = (double)i+1;
                    double y = ywertMa1;
                    double z = ywertMa2;
                    list.Add(x, y); 
                    list2.Add(x, z);
                    ywertMa1 = 0;
                    ywertMa2 = 0;
                }
               
               
            }
            // Generate a red curve with circle
            LineItem myCurve = myPane.AddCurve("Auslastung Maschine 1", list, Color.Blue, SymbolType.Circle);
            LineItem myCurve2 = myPane2.AddCurve("Auslastung Machschine 2", list2, Color.Green, SymbolType.Square);
            myPane.Chart.Fill.Color = System.Drawing.Color.Lavender;
            myPane2.Chart.Fill.Color = System.Drawing.Color.Beige;
            //Grid-Lines sichtbar machen
            myPane.YAxis.MajorGrid.IsVisible = true; 
            myPane.YAxis.MinorGrid.IsVisible = true; 
            myPane.YAxis.MajorGrid.Color = Color.Gray; 
            myPane.YAxis.MinorGrid.Color = Color.LightGray;
            myPane2.YAxis.MajorGrid.IsVisible = true;
            myPane2.YAxis.MinorGrid.IsVisible = true;
            myPane2.YAxis.MajorGrid.Color = Color.Gray;
            myPane2.YAxis.MinorGrid.Color = Color.LightGray; 


            // Calculate the Axis Scale Ranges
               zgc.AxisChange();
               zedGraphControl2.AxisChange();

        }

        public void erstelleGraphic(double[,,] results)
        {
            this.results = results;
            CreateChart(zedGraphControl1);
        }

        public void erstelleGraphic2(double[,] resultsone)
        {
            this.resultsone = resultsone;
            CreateChart2(zedGraphControl1);
        }

        private void button1_Click(object sender, EventArgs e)
        {
            this.Close();
        }

    }
}
