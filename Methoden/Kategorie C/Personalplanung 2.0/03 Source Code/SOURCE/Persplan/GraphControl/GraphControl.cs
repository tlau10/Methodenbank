// created on 3/11/2003 at 12:42 PM
using System;
using System.Windows.Forms;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Collections;
namespace UserControls{
	//this struct will be used to initialize the graph Control
	public struct GraphStruct
	{
		public int PointsOnXAxis; //number of points on X axis
		public int PointsOnYAxis; //number of points on Y axis
		public float StartValueXAxis; //start value for X axis
		public float StartValueYAxis; //start value for Y axis
		public int StepXAxis; // increments in which values should be laid out on X-Axis
		public int StepYAxis;//increments in which values should be laid out on Y-Axis
		public GraphStruct(int pX,int pY,float startValX,float startValY,int stepX,int stepY)
		{
			PointsOnXAxis = pX;
			PointsOnYAxis = pY;
			StartValueXAxis = startValX;
			StartValueYAxis = startValY;
			StepXAxis     = stepX;
			StepYAxis     = stepY;
		}
	}
	//this strcut will be used to store a point's coordinates in float
	public struct FloatPoint
	{
		public float X; //x coordinate
		public float Y; //y coordinate
		public float Value; //display value of this point;
		public FloatPoint(float x, float y)
		{
			X = x;
			Y = y;
			Value = 0;
		}
	}
	//enumeration for style of graph LineGraph or BarGraph
	public enum GraphType
	{
		Line,Bar
	};
	public class GraphControl : System.Windows.Forms.UserControl
	{
		public GraphStruct GRAPHSTRUCT;
		Region graphRegion;  //region to draw the graph on
		FloatPoint origin,xAxis,yAxis;  
		string xTitle,yTitle; // titles for X and Y axes
		ArrayList Points;   //list to store Display points
		ArrayList graphPointsX,graphPointsY; //list to store X,Y coordinates of graph
		GraphType graphType; //type of graph
		bool bShowPoints = false;  //show the display points on graph
		Color textColor,axesColor,graphColor; //setthe display ,axes or graph color
		public GraphControl()
		{
			//set the defualt values
			xTitle = "None";
			yTitle = "None";
			graphType = GraphType.Line;
			textColor  = Color.Blue;
			axesColor  = Color.Red;
		    graphColor = Color.Green;
			BackColor  = Color.AliceBlue ;
			//GraphStruct is set to have 10 pts on each axes starting value is 1 
			//and step increment is also 1 by defualt.
			GRAPHSTRUCT = new GraphStruct(10,10,1,1,1,1);
			Points = new ArrayList();
			graphPointsX = new ArrayList();
			graphPointsY = new ArrayList();
		}
		public Color TextColor
		{
			get
			{
				return textColor;
			}
			set
			{
			    textColor = value;
			}
		}

		public Color AxesColor
		{
			get
			{
				return axesColor;
			}
			set
			{
			    axesColor = value;
			}
		}		
						
		public Color GraphColor
		{
			get
			{
				return graphColor;
			}
			set
			{
			    graphColor = value;
			}
		}				
		public string TitleXAxis
		{
			get
			{
				return xTitle;
			}
			set
			{
				xTitle = value;
			}
		}
		
		public string TitleYAxis
		{
			get
			{
				return yTitle;
			}
			set
			{
				yTitle = value;
			}
		}
		
		public bool ShowPointsOnGraph
		{
		   get
		   {
		   	  return bShowPoints;
		   }
		   set
		   {
		   	  bShowPoints = value;
		   }
		}
		
		public GraphType GraphStyle
		{
			get
			{
				return graphType;
			}
			set
			{
				graphType = value;
			}
		}
        //overloaded methods to AddPoints to graph 
		public void AddPoints(int x,int y)
		{
			Points.Add(new Point(x,y));
		}
		public void AddPoints(int[]x,int[]y)
		{
			for(int i=0;i<x.Length;i++)
			{
			    Points.Add(new Point(x[i],y[i]));
			}
		}
		public void AddPoints(int[,]pts)
		{
			for(int i=0;i<pts.GetLength(0);i++)
			{
			    Points.Add(new Point(pts[i,0],pts[i,1]));
			}
		}
		public void AddPoints(Point pt)
		{
			Points.Add(pt);
		}
		public void AddPoints(Point[] pts)
		{
			for(int i=0;i<pts.Length;i++)
			{
			    Points.Add(pts[i]);
			}
		}
		//draws the X and Y axes and also set the origin and x and y axes points
		//This method should be called before any other drawing method.
		void DrawAxes(Graphics g)
		{
			//get the bounds of our region.We will draw within the region
			RectangleF rect = Region.GetBounds(g);
	        float xOrigin =  rect.Left  + 20;
			float yOrigin =  rect.Bottom - 70;
			
			origin = new FloatPoint(xOrigin,yOrigin);
			xAxis = new FloatPoint(rect.Right - 20,origin.Y);
			yAxis = new FloatPoint(origin.X ,rect.Top);
			
			Pen axisPen = new Pen(axesColor);
				
			g.DrawLine(axisPen,origin.X,origin.Y,xAxis.X,xAxis.Y);
			g.DrawLine(axisPen,origin.X,origin.Y,yAxis.X,yAxis.Y);
						
			axisPen.Dispose();
		}
		//draws the co-ordinates on x and y axes.
		void DrawPoints(Graphics g)
		{
			float xDiff = xAxis.X - origin.X;
			float yDiff = origin.Y - yAxis.Y;
			float xStep = xDiff/GRAPHSTRUCT.PointsOnXAxis;
			float yStep = yDiff/GRAPHSTRUCT.PointsOnYAxis;
			
			FloatPoint fpt = new FloatPoint(origin.X,origin.Y);
			fpt.Value = 0;
			graphPointsX.Add(fpt);
			graphPointsY.Add(fpt);
			
			Pen   p = new Pen(textColor);
			Brush b = new SolidBrush(textColor);
			Font  f = new Font(Font.FontFamily,Font.Size);
			for(int i = 1; i<= GRAPHSTRUCT.PointsOnXAxis; i++)
			{
				float xAxisX = origin.X + (i * xStep);
				float xAxisY = origin.Y;
				
				g.DrawLine(p,xAxisX,xAxisY - 2,xAxisX,xAxisY + 2);
				float val = GRAPHSTRUCT.StartValueXAxis + ((i-1) * GRAPHSTRUCT.StepXAxis) ;	
				g.DrawString(val.ToString(),f,b,xAxisX-5,xAxisY + 3);
				
				fpt.X = xAxisX;
				fpt.Y = 0;
				fpt.Value = val;
				graphPointsX.Add(fpt);
			}
			for(int j = 1; j<= GRAPHSTRUCT.PointsOnYAxis; j++)
			{
				float yAxisX = origin.X;
				float yAxisY = origin.Y - (j * yStep);
				
				g.DrawLine(p,yAxisX -2 ,yAxisY,yAxisX + 2,yAxisY);
				float val = GRAPHSTRUCT.StartValueYAxis + ((j-1) * GRAPHSTRUCT.StepYAxis) ;	
				g.DrawString(val.ToString(),f,b,yAxisX-15,yAxisY);
				
				fpt.X = 0;
				fpt.Y = yAxisY;
				fpt.Value = val;
				graphPointsY.Add(fpt);
			}
	        f.Dispose();
			b.Dispose();
			p.Dispose();
		}
		//draws the actual graph, Line style.
		void DrawLineGraph(Graphics g)
		{
			Pen p = new Pen(graphColor);
			Point start = (Point)Points[0];
			FloatPoint prev,current;
			prev = FindLocationOnGraph(start);
			for(int i=1 ; i<Points.Count ;i++)
			{
				Point pt = (Point)Points[i];
			    current = FindLocationOnGraph(pt);	
				g.DrawLine(p,prev.X,prev.Y,current.X,current.Y);
				if(bShowPoints)
				{
					Brush b = new SolidBrush(textColor);
					Font f = new Font(Font.FontFamily,Font.Size);	
				    string title = "(" + pt.X + "," + pt.Y + ")";
					if(prev.Y > current.Y)
				    	g.DrawString(title,f,b,current.X - 25, current.Y - 10);
					else
						g.DrawString(title,f,b,current.X - 25, current.Y + 2);
					if(i ==1)
					{
						title = "(" + start.X + "," + start.Y + ")";
				        g.DrawString(title,f,b,prev.X - 10, prev.Y - 15);
					}
 	                f.Dispose();
					b.Dispose();
				}
				prev = current;
			}
			p.Dispose();
		}
		//draws the actual graph ,Bar style
		void DrawBarGraph(Graphics g)
		{
			Pen p = new Pen(graphColor);
			FloatPoint current;
			for(int i=0 ; i<Points.Count ;i++)
			{
				Point pt = (Point)Points[i];
			    current = FindLocationOnGraph(pt);	
				g.DrawLine(p,current.X -1,origin.Y,current.X -1,current.Y);
				g.DrawLine(p,current.X -1,current.Y,current.X + 1,current.Y);
				g.DrawLine(p,current.X +1,current.Y,current.X +1,origin.Y);
				if(bShowPoints)
				{
					Brush b = new SolidBrush(textColor);
					Font f = new Font(Font.FontFamily,Font.Size);	
				    string title = "(" + pt.X + "," + pt.Y + ")";
				    g.DrawString(title,f,b,current.X - 25, current.Y - 15);
 	                f.Dispose();
					b.Dispose();
				}
			}
			p.Dispose();
		}
		//draws titles for X and Y axes.
		void DrawTitles(Graphics g)
		{
			Brush b = new SolidBrush(textColor);
			Font f = new Font(Font.FontFamily,Font.Size);
			string title = "X-Axis = " + xTitle + "    " + "Y-Axis = " + yTitle;
			g.DrawString(title ,f,b,origin.X , origin.Y + 15);
			f.Dispose();
			b.Dispose();
		}
		//Given a display point, this function will point the actual co-ordinates
		//for this point on our graph. It acts like the ScreenToClient function in Windows
		FloatPoint FindLocationOnGraph(Point pt)
		{
			 float diffX,diffY,diffValue,finalXValue,finalYValue;
			 diffX = diffY = -1;
			 finalXValue = finalYValue = 0;
		     for(int i=0;i<graphPointsX.Count;i++)
		     {
		     	//store the current point
		     	FloatPoint current = (FloatPoint)graphPointsX[i];
		     	//if points X is lesser that current points Value
		     	if((float)pt.X < current.Value)
		     	{
		     		FloatPoint previous = (FloatPoint)graphPointsX[i-1];
		     		//store diff between current X and previous X coordinate
		     		diffX = current.X - previous.X;
		     		//store difference between values of current and prev points
		     		diffValue = current.Value - previous.Value;
		     		float unitsPerCoordinate = diffValue/diffX;
		     		finalXValue = ((pt.X - previous.Value)/unitsPerCoordinate) + previous.X;
		     		break;
		     	}
		     	else if((float)pt.X == current.Value)
		     	{
		     		finalXValue = current.X;
		     	}
		     }
		     for(int j=0;j<graphPointsY.Count;j++)
		     {
		     	FloatPoint current = (FloatPoint)graphPointsY[j];
		     	if((float)pt.Y < current.Value)
		     	{
		     		FloatPoint previous = (FloatPoint)graphPointsY[j-1];
		     		diffY     = current.Y     - previous.Y;
		     		diffValue = current.Value - previous.Value;
		     		float unitsPerCoordinate = diffValue/diffY;
		     		finalYValue = ((pt.Y - previous.Value)/unitsPerCoordinate) + previous.Y;
		     		break;
		     	}
		     	else if((float)pt.Y == current.Value)
		     	{
		     		finalYValue = current.Y;
		     	}
		     }
		     FloatPoint fpNew = new FloatPoint(finalXValue,finalYValue);
			 return fpNew;
		}
		//overridden to paint our graph
		protected override void OnPaint(PaintEventArgs pe)
		{
			Rectangle bound = new Rectangle(new Point(0,0),Size);
			graphRegion = new Region(bound);
			Region = graphRegion;
			DrawAxes(pe.Graphics);
			DrawPoints(pe.Graphics);
			DrawTitles(pe.Graphics);
			if(graphType == GraphType.Bar)
			   DrawBarGraph(pe.Graphics);
			else
			   DrawLineGraph(pe.Graphics);
			//drawAxes is called twice becuase the axes get overwwiten sometimes by other lines
			DrawAxes(pe.Graphics);
		}
	}
}
