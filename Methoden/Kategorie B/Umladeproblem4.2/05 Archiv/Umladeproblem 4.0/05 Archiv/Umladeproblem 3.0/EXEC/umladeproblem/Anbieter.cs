using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;
using System.Data;

namespace umladeproblem
{
	/// <summary>
	/// Summary description for Anbieter.
	/// </summary>
	public class Anbieter
	{

		private System.Windows.Forms.PictureBox myPicBox;

		public Anbieter(String color)
		{
			this.myPicBox = new System.Windows.Forms.PictureBox();
			this.myPicBox.BackColor = System.Drawing.Color.FromName(color);
			this.myPicBox.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
			//this.myPicBox.Location = new System.Drawing.Point(8, 16);
			this.myPicBox.Name = "myPicBox";
			this.myPicBox.Size = new System.Drawing.Size(40, 40);
			this.myPicBox.TabIndex = 18;
			this.myPicBox.TabStop = false;
			this.myPicBox.Anchor = AnchorStyles.None;
			//this.myPicBox.MouseDown += new System.Windows.Forms.MouseEventHandler(this.myPicBox_MouseDown);
			 
		}

		public PictureBox getMyPicBox()
		{
			return this.myPicBox;
		}

		public void setLocation(int myX, int myY) 
		{
			this.myPicBox.Location = new System.Drawing.Point(myX, myY);
			Console.WriteLine("Location : " + myX + " - " + myY);
			Console.WriteLine("picBox   : " + this.myPicBox.Location.X.ToString() + " - " + this.myPicBox.Location.Y.ToString());
		}
	}
}
