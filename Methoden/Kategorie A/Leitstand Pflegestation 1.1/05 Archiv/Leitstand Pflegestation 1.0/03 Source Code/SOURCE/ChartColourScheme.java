package pflegestation;

import java.awt.*;
import java.awt.color.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;

class ChartColourScheme
{	private Color chartAreaColor;
        private Color axisColor;
        private Color fontColor;
        private Color areaColor;

        private ColorPanel colPan;

        private Vector barColors;
        private Vector lineColors;

        private int numOfBars;
        private int numOfLines;
        private int numOfAreas;
                private int numOfSeries;

        public ChartColourScheme()
        {
        this(3,2,1);			//default - takes it that there are 6 columns
        }

        public ChartColourScheme(int bars, int lns, int ars)
        {
        setSeriesData(bars, lns, ars);

        chartAreaColor = Color.lightGray;		// here we set the defaults
        axisColor = Color.red;
        fontColor = Color.black;
        areaColor = Color.yellow;

        barColors = new Vector();
        for ( int a = 0; a < 96; a++ )
            barColors.add(Color.red);

        lineColors = new Vector();
        for ( int b = 0; b < 96; b++ )
            lineColors.add(Color.red);
    }

        public void setSeriesData(int bars, int lns, int ars)
        {
        numOfBars = bars;
        numOfLines = lns;
        numOfAreas = ars;
                numOfSeries = (numOfBars + numOfLines + numOfAreas);
        }

        public Color getChartAreaColor()
        {
                return chartAreaColor;
        }
        public void setChartAreaColor(int rgb)
        {
                chartAreaColor = new Color(rgb);
        }

        public Color getFontColor()
        {
                return fontColor;
        }
        public void setFontColor(int r, int g, int b)
        {
                fontColor = new Color(r, g, b);
        }

        public Color getAreaColor()
        {
                return areaColor;
        }
        public void setAreaColor(int r, int g, int b)
        {
                areaColor = new Color(r, g, b);
        }

        public Color getAxisColor()
        {
                return axisColor;
        }
        public void setAxisColor(int r, int g, int b)
        {
                axisColor = new Color(r, g, b);
        }

        public Color getLineColor(int a)
        {
        if (a > (lineColors.size()-1)) a = 0;		// a check to avoid a crash if the Chart instance
        Color colBuf = (Color)(lineColors.get(a));	// asks for a colour beyond the bound of the
        return colBuf;					// array (not likely given the steps taken though)
        }
        public void setLineColor(int lineIndex, int r, int g, int b)
        {
                Color newCol = new Color(r, g, b);
                lineColors.setElementAt(newCol, (lineIndex-1));
        }

        public Color getSeriesColor(int serNum)		// should be called getBarColor().
        {
                if (serNum > (barColors.size()-1)) serNum = 0;
                Color colBuf = (Color)(barColors.get(serNum));
                return colBuf;
        }
        public void setBarColor(int lineIndex, int r, int g, int b)
        {
                Color newCol = new Color(r, g, b);
                barColors.setElementAt(newCol, (lineIndex-1));
        }

        public int getNumOfSeries()
        {
                return numOfSeries;
        }

        public JPanel getPanel()
        {
                colPan = new ColorPanel(this);
                return colPan.getColPan();
        }

        public void updatePanel()
        {
                ColorPanel colPan = new ColorPanel(this);
        }

        public int getNumOfBars()
        {
                return numOfBars;
        }

        public int getNumOfLines()
        {
                return numOfLines;
        }

        public int getNumOfAreas()
        {
                return numOfAreas;
        }

}

class ColorPanel extends JPanel implements ActionListener, ChangeListener
{
// a convenient device for changing the colours stored within the ChartColorSchemem instance.
        private JPanel colPan;
        private ChartColourScheme colScheme;
        private JComboBox chartElements;

        private JSlider greenSlide;
        private JSlider redSlide;
        private JSlider blueSlide;
        private JButton previewColor;

        private JLabel redVal;
        private JLabel greenVal;
        private JLabel blueVal;

        private String comboBarItems[];
        private String comboLineItems[];

        public ColorPanel(ChartColourScheme colSch)
        {
        colScheme = colSch;

        colPan = new JPanel();					// 4 items (see below) are added to this
                colPan.setLayout(new BorderLayout(10, 10));	// JPanel

                JPanel sliderLabPan = new JPanel();	// added to east of colPan
                        sliderLabPan.setLayout(new GridLayout(3,1,5,5));
                                redVal = new JLabel("  Red value = 0   ");

                                greenVal = new JLabel("Green value = 0   ");
                                blueVal = new JLabel(" Blue value = 0   ");

                        sliderLabPan.add(redVal);
                        sliderLabPan.add(greenVal);
                        sliderLabPan.add(blueVal);


                JPanel sliderPan = new JPanel();	// added to center of colPan
                        sliderPan.setLayout(new GridLayout(3,1,5,5));

                                redSlide = new JSlider(0,255,0);
                                        redSlide.setMajorTickSpacing(64);
                                        redSlide.setPaintTicks(true);
                                        redSlide.setPaintLabels(true);
                                        redSlide.setSnapToTicks(false);
                                        redSlide.addChangeListener(this);

                                greenSlide = new JSlider(0,255,0);
                                        greenSlide.setMajorTickSpacing(64);
                                        greenSlide.setPaintTicks(true);
                                        greenSlide.setPaintLabels(true);
                                        greenSlide.setSnapToTicks(false);
                                        greenSlide.addChangeListener(this);

                                blueSlide = new JSlider(0,255,0);
                                        blueSlide.setMajorTickSpacing(64);
                                        blueSlide.setPaintTicks(true);
                                        blueSlide.setPaintLabels(true);
                                        blueSlide.setSnapToTicks(false);
                                        blueSlide.addChangeListener(this);

                        sliderPan.add(redSlide);
                        sliderPan.add(greenSlide);
                        sliderPan.add(blueSlide);

                JPanel southPan = new JPanel();		// added to South of colPan
                        southPan.setLayout(new GridLayout(1,2,5,5));

                                JButton setElementColor = new JButton("Set element");
                                        setElementColor.addActionListener(this);

                                previewColor = new JButton("");
                                        previewColor.setBackground(new Color(0,0,0));

                        southPan.add(previewColor);
                        southPan.add(setElementColor);

                chartElements = new JComboBox();	// added to the North of colPan
                        populateChartElements();

        colPan.add(chartElements, BorderLayout.NORTH);
        colPan.add(sliderPan, BorderLayout.CENTER);
        colPan.add(sliderLabPan, BorderLayout.EAST);
        colPan.add(southPan, BorderLayout.SOUTH);
        }

        public void populateChartElements()
        {
        int numOfBars = colScheme.getNumOfBars();
        int numOfLines = colScheme.getNumOfLines();

        chartElements.addItem("Chart Area");
        chartElements.addItem("Font");
        chartElements.addItem("Axes");

        comboBarItems = new String[numOfBars];
                for (int a = 0; a < numOfBars; a++)
                {
                comboBarItems[a] = ("Bar: " + (a+1));
                chartElements.addItem(comboBarItems[a]);
                }

        comboLineItems = new String[numOfLines];
                for (int a = 0; a < numOfLines; a++)
                {
                comboLineItems[a] = ("Line: " + (a+1));
                chartElements.addItem(comboLineItems[a]);
                }

        if(colScheme.getNumOfAreas() == 1) chartElements.addItem("Area");
        }

        public JPanel getColPan()
        {
        return colPan;
        }

        public void actionPerformed(ActionEvent ae)
        {
        int rd = redSlide.getValue();
        int gr = greenSlide.getValue();
        int bl = blueSlide.getValue();
        String elementChosen = (String)(chartElements.getSelectedItem());
//	String elementChosen = chartElements.getSelectedItem().toString();

        previewColor.setBackground(new Color(rd,gr,bl));

        if (elementChosen == "Axes") colScheme.setAxisColor(rd, gr, bl);
                else
                {
                if (elementChosen == "Chart Area") colScheme.setChartAreaColor(SystemColor.window.getRGB());
                        else
                        {
                        if (elementChosen == "Font") colScheme.setFontColor(rd, gr, bl);
                                else
                                {
                                if (elementChosen == "Area") colScheme.setAreaColor(rd, gr, bl);
                                        else
                                        {

                                        for (int g = 0; g < colScheme.getNumOfBars(); g++)
                                                {
                                                if (elementChosen == comboBarItems[g])
                                                        {
                                                        colScheme.setBarColor((g+1), rd, gr, bl);
                                                        }
                                                }

                                        for (int g = 0; g < colScheme.getNumOfLines(); g++)
                                                {
                                                if (elementChosen == comboLineItems[g])
                                                        {
                                                        colScheme.setLineColor((g+1), rd, gr, bl);
                                                        }
                                                }
                                        }
                                }
                        }
                }
        }

        public void stateChanged(ChangeEvent ch)
        {
        int redS = redSlide.getValue();
        int greenS =  greenSlide.getValue();
        int blueS = blueSlide.getValue();

        redVal.setText("  Red Value = " + redS);
        greenVal.setText("Green Value = " + greenS);
        blueVal.setText(" Blue Value = " + blueS);

        previewColor.setBackground(new Color(redS,greenS,blueS));
        }
}