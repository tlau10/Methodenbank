using System;
using System.Data;
using System.Drawing;
using System.Drawing.Printing;
using System.Windows.Forms;
using System.Runtime.InteropServices;

namespace Maschinenbelegungsplanung
{
	/// <summary>
	/// Summary description for DataGridPrinter.
	/// </summary>
	public class DataGridPrinter
	{
		private DataGrid dataGrid;
		private DataTable dataTable;
		private PrintDocument printDocument;

		private int linesPerPage;
		private int pageCounter = 0;
		private int rowCounter = 1;

//		private IntPtr hdc;
//		private MarginInfo mi;
		private float leftMargin;
		private float rightMargin;
		private float topMargin;
		private float bottomMargin;
		private float pageHeight;
		private float bodyRectHeight;
		private int   linesPerBodyRow = 4;
		private float pageWidth;
		private float columnWidth;

		private float header1Height;
		private float header2Height;
		private float bodyHeight;
		private float footerHeight;

		private Font header1Font = new Font("Arial", 14);
		private Font header2Font;
		private Font bodyFont;
		private Font footerFont  = new Font("Arial", 12);

		public DataGridPrinter(DataGrid dataGrid, PrintDocument printDocument)
		{
			this.dataGrid = dataGrid;
			this.dataTable = (DataTable) dataGrid.DataSource;
			this.printDocument = printDocument;
						
			header2Font = this.dataGrid.HeaderFont;
			bodyFont = this.dataGrid.Font;
		}
		
		public bool Draw(Graphics g, PrintPageEventArgs e)
		{
			bool result;

//			hdc = g.GetHdc();
//			try
//			{
//				mi = new MarginInfo(hdc.ToInt32());
//			}
//			finally
//			{
//				g.ReleaseHdc(hdc);
//			}
//
//			g.PageUnit = GraphicsUnit.Inch;
//			g.PageScale = .01f;
//			g.TranslateTransform(-mi.Left, -mi.Top);
			
			RectangleF rectf = new RectangleF(
				e.MarginBounds.Left - (e.PageBounds.Width - g.VisibleClipBounds.Width)/2,
				e.MarginBounds.Top - (e.PageBounds.Height - g.VisibleClipBounds.Height)/2,
				e.MarginBounds.Width,
				e.MarginBounds.Height);

//			leftMargin   = e.MarginBounds.Left;
//			rightMargin  = e.MarginBounds.Right;
//			topMargin    = e.MarginBounds.Top;
//			bottomMargin = e.MarginBounds.Bottom;
//			pageHeight   = bottomMargin - topMargin;
//			pageWidth    = rightMargin - leftMargin;

			leftMargin   = rectf.Left - e.MarginBounds.Left;
			rightMargin  = rectf.Right;
			topMargin    = rectf.Top;
			bottomMargin = rectf.Bottom;
			pageHeight   = bottomMargin - topMargin;
			pageWidth    = rightMargin - leftMargin;
			
			g.TranslateTransform((e.PageBounds.Width - g.VisibleClipBounds.Width)/2, (e.PageBounds.Height - g.VisibleClipBounds.Height)/2);
			columnWidth = pageWidth/dataTable.Columns.Count;

			header1Height  = header1Font.GetHeight(e.Graphics);
			header2Height  = header2Font.GetHeight(e.Graphics);
			bodyHeight     = bodyFont.GetHeight(e.Graphics);
			bodyRectHeight = bodyHeight * linesPerBodyRow;
			footerHeight   = bodyFont.GetHeight(e.Graphics);

			RectangleF reportHeader1R = new RectangleF(leftMargin, topMargin, pageWidth, header1Height);
			RectangleF reportHeader2R = new RectangleF(leftMargin, topMargin+header1Height, columnWidth, header2Height);
			RectangleF reportBodyR    = new RectangleF(leftMargin, topMargin+header1Height+header2Height, columnWidth, bodyRectHeight);
			RectangleF reportFooterR  = new RectangleF(leftMargin, bottomMargin-footerHeight, pageWidth, footerHeight);
			
			//linesPerPage = Convert.ToInt32((pageHeight - 3*header1Height - footerHeight)/bodyHeight) - 1;
			linesPerPage = Convert.ToInt32((pageHeight-header1Height-header2Height-footerHeight)/(bodyRectHeight+.5f*bodyHeight)) - 1;
			DrawHeader(reportHeader1R, reportHeader2R, g);
			
			result = DrawBody(reportBodyR, g);
			DrawFooter(reportFooterR, g);
			return result;
		}
		
		private void DrawHeader(RectangleF rectHeader1, RectangleF rectHeader2, Graphics g)
		{
			// Header1 (Kennzeichnung des Dokuments)
			StringFormat header1Format = new StringFormat();
			header1Format.Alignment    = StringAlignment.Center;
			header1Format.Trimming     = StringTrimming.EllipsisCharacter;
			header1Format.FormatFlags  = StringFormatFlags.NoWrap | StringFormatFlags.LineLimit;
			g.DrawString("Firmenliste", header1Font, Brushes.Black, rectHeader1, header1Format);

			//Header2 (Spaltenüberschriften)
			SolidBrush foreBrush = new SolidBrush(dataGrid.HeaderForeColor);
			SolidBrush backBrush = new SolidBrush(dataGrid.HeaderBackColor);
			StringFormat header2Format = new StringFormat();
			header2Format.Alignment    = StringAlignment.Near;
			header2Format.Trimming     = StringTrimming.EllipsisCharacter;
			//header2Format.FormatFlags  = StringFormatFlags.NoWrap | StringFormatFlags.LineLimit;

			for (int i=0; i<dataTable.Columns.Count; i++)
			{
				rectHeader2.X += columnWidth;
				g.FillRectangle(backBrush, rectHeader2);
				g.DrawString(dataTable.Columns[i].ToString(), header2Font, foreBrush, rectHeader2, header2Format);
			}
		}

		private void DrawFooter(RectangleF rectFooter, Graphics g)
		{
			StringFormat footerFormat = new StringFormat();
			footerFormat.Alignment    = StringAlignment.Far;
			footerFormat.Trimming     = StringTrimming.EllipsisCharacter;
			footerFormat.FormatFlags  = StringFormatFlags.NoClip | StringFormatFlags.LineLimit;
			g.DrawString("Seite " +pageCounter, footerFont, Brushes.Black, rectFooter, footerFormat);

		}

		private bool DrawBody(RectangleF rectBody, Graphics g)
		{
			SolidBrush foreBrush = new SolidBrush(dataGrid.ForeColor);
			SolidBrush backBrush = new SolidBrush(dataGrid.BackColor);
			SolidBrush alternatingBackBrush = new SolidBrush(dataGrid.AlternatingBackColor);

			StringFormat bodyFormat = new StringFormat();
			bodyFormat.Alignment    = StringAlignment.Near;
			bodyFormat.Trimming     = StringTrimming.EllipsisCharacter;
		
			//bodyFormat.FormatFlags  = StringFormatFlags.NoWrap | StringFormatFlags.LineLimit;
			
			int tempRowCount = 0;

			for (; rowCounter <= dataTable.Rows.Count; rowCounter++)
			{
				if (tempRowCount > 0)
					rectBody.Y += (bodyRectHeight+0.5f*bodyHeight);

				for (int i=0; i<dataTable.Columns.Count; i++)
				{
					rectBody.X += columnWidth;
					if (rowCounter % 2 == 0)
						g.FillRectangle(backBrush, rectBody);
					else
						g.FillRectangle(alternatingBackBrush, rectBody);
					g.DrawString((dataTable.Rows[rowCounter-1][i]).ToString(), bodyFont, foreBrush, rectBody, bodyFormat);
				}
				rectBody.X = leftMargin;
				tempRowCount++;
				
				if (rowCounter % 2 == 0)
					g.FillRectangle(backBrush, new RectangleF(leftMargin+columnWidth, rectBody.Y + bodyRectHeight, pageWidth, 0.5f*bodyHeight));
				else
					g.FillRectangle(alternatingBackBrush, new RectangleF(leftMargin+columnWidth, rectBody.Y + bodyRectHeight, pageWidth, 0.5f*bodyHeight));
				if (rowCounter < dataTable.Rows.Count && tempRowCount == linesPerPage)
					return true;
			}
			
			return false;
		}

		public int PageCounter
		{
			get {return pageCounter;}
			set {pageCounter = value;}
		}

		public void ResetCounters()
		{
			pageCounter = 0;
			rowCounter = 1;
		}
	}
}
