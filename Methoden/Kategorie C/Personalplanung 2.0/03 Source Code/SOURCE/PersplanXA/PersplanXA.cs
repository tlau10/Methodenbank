using System;
using PersplanSolverInterface;
using PersplanLibrary;
using System.Windows.Forms;
using System.IO;
using System.Collections;
using System.Xml;
//using System;
using System.Diagnostics;

namespace PersplanXA
{
	/// <summary>
	/// Zusammenfassung für Class1.
	/// </summary>
	public class PersplanXA : IPersplanSolver
	{
		private string tempPath;
		private string XAPath;
		private ExtendedTreeNode ShiftNode;
		private int ShiftNumbers;
		private int BreakNumbers;

		public PersplanXA()
		{
			this.tempPath = Path.GetTempPath() + "\\XA";
		}
		
		public bool InitSolver (string ConfigurationFile)
		{
			XmlTextReader reader = new XmlTextReader(ConfigurationFile);			
			reader.ReadStartElement("XA");
			reader.ReadStartElement("XAPath");
			this.XAPath = reader.ReadString();
			reader.ReadEndElement();
			reader.ReadEndElement();
			reader.Close();
			return true;
		}

		public bool Solve(ExtendedTreeNode RootNode)
		{
			ExtendedTreeNode RequirementsNode = null;
			//ExtendedTreeNode ShiftNode = null;
			
			//obtain nodes
			foreach (ExtendedTreeNode node in RootNode.Nodes)
			{
				if (node.ID == TreeNodeID.TreeNodeIDRequirements)
					RequirementsNode = node;
				else if (node.ID == TreeNodeID.TreeNodeIDShift)
					ShiftNode = node;

			}

			// delete files if necessary and create directory
			if (Directory.Exists(this.tempPath))
				Directory.Delete(this.tempPath, true);
			Directory.CreateDirectory(this.tempPath);

			// create clp file
			StreamWriter sw = new StreamWriter(this.tempPath + "\\persplan.clp");
			sw.WriteLine("persplan.LP LISTINPUT NO");
			sw.WriteLine("  OUTPUT xa.out");
			sw.WriteLine("  PAGESIZE 24");
			sw.WriteLine("  LINESIZE 79");
			sw.WriteLine("  TMARGINS 0");
			sw.WriteLine("  BMARGINS 0");
			sw.WriteLine("  FIELDSIZE 11");
			sw.WriteLine("  DECIMALS 5");
			sw.WriteLine("  EUROPEAN NO");
			sw.WriteLine("  LMARGINS 0");
			sw.WriteLine("  COPIES 1");
			sw.WriteLine("  WAIT NO");
			sw.WriteLine("  MUTE NO");
			sw.WriteLine("  LISTINPUT NO");
			sw.WriteLine("  WARNING YES");
			sw.WriteLine("  SOLUTION YES");
			sw.WriteLine("  CONSTRAINTS YES");
			sw.WriteLine("  COSTANALYSIS YES");
			sw.WriteLine("  MARGINANALYSIS YES");
			sw.WriteLine("  MATLIST NO");
			sw.WriteLine("  DEFAULTS NO");
			sw.Close();

			// create lp file
			StreamWriter swlp = new StreamWriter(this.tempPath + "\\persplan.lp");
			swlp.WriteLine("..TITLE");
			swlp.WriteLine("  Personalplanung");
			swlp.WriteLine("..OBJECTIVE MINIMIZE");
			
			// build the Matrix
			ArrayList Matrix = this.BuildMatrix(RequirementsNode, ShiftNode);
			
			#region build cost function
			// build cost function
			string ZF = "";
			bool AddPlus = false;
			int ShiftCounter = 0;
			for (; ShiftCounter < ((ArrayList)Matrix[0]).Count - 2; ShiftCounter++)		// dimension 1 contains the shifts, and the break and requirement information
			{
				if (AddPlus)
					ZF += "+";
				else
					AddPlus = true;
				ZF += ((ArrayList)Matrix[48])[ShiftCounter].ToString() + "s" + ShiftCounter.ToString().PadLeft(2, '0');
			}
			
			// add the breaks
			ZF += "+0(";
			AddPlus = false;
			int BreakCounter = 0;
			int BreakIndex = ((ArrayList)Matrix[0]).Count - 2;

			for (int TimeIntervals = 0; TimeIntervals < 48; TimeIntervals++)
			{				
				if (Convert.ToInt32(((ArrayList)Matrix[TimeIntervals])[BreakIndex]) == 1)
				{
					if (AddPlus)
						ZF += "+";
					else
						AddPlus = true;
					ZF += "p" + (ShiftCounter+BreakCounter).ToString().PadLeft(2, '0');
					
					BreakCounter++;
				}
			}
			ZF += ")";
			swlp.WriteLine(ZF);
			this.ShiftNumbers = ShiftCounter;
			this.BreakNumbers = BreakCounter;
			#endregion

			#region bounds
			// write bounds
			swlp.WriteLine("..BOUNDS");
			int ConstraintCounter = 0;
			for (; ConstraintCounter < ShiftCounter; ConstraintCounter++)
				swlp.WriteLine("Z" + ConstraintCounter.ToString().PadLeft(3, '0') + ": s" + ConstraintCounter.ToString().PadLeft(2, '0') + ">=0");
			for (; ConstraintCounter < ShiftCounter+BreakCounter; ConstraintCounter++)
				swlp.WriteLine("Z" + ConstraintCounter.ToString().PadLeft(3, '0') + ": p" + ConstraintCounter.ToString().PadLeft(2, '0') + ">=0");
			#endregion

			#region constraints
			// write constraints
			swlp.WriteLine("..CONSTRAINTS");
			int Breaks = 0;
			for (int IntervalCounter = 0; IntervalCounter < 48; IntervalCounter++)
			{
				// build a constraint					
				string constraint = "";
				AddPlus = false;
				for (int i = 0; i < ShiftCounter; i++)
				{					
					if (Convert.ToInt32(((ArrayList)Matrix[IntervalCounter])[i]) == 1)
					{
						if (AddPlus)
							constraint += "+";
						else
							AddPlus = true;
						constraint += "s" + i.ToString().PadLeft(2, '0');
					}
				}
				if (Convert.ToInt32(((ArrayList)Matrix[IntervalCounter])[BreakIndex]) == 1)
				{
					constraint += "-p" + (ShiftCounter + Breaks).ToString().PadLeft(2, '0');
					Breaks++;
				}
				if (constraint.Length > 0)
				{
					constraint += ">=" + ((int)((ArrayList)Matrix[IntervalCounter])[BreakIndex+1]).ToString();
					swlp.WriteLine("Z" + ConstraintCounter.ToString().PadLeft(3, '0') + ":" + constraint);
					ConstraintCounter++;
				}
			}

			// write additional constraints: shifts minus breaks must equal or be greater than 0!
			ArrayList BreakMatrix = this.BuildBreakMatrix(ShiftNode);
			for (int Intervals = 0; Intervals < 48; Intervals++)
			{
				string constraint = "";				
				AddPlus = false;
				for (int sCounter = 0; sCounter < ShiftCounter; sCounter++)
				{
					if ((int)((ArrayList)Matrix[Intervals])[sCounter] == 1)
					{
						// shift is during this Interval; but is the shift's break period in it?
						if (BreakMatrix[sCounter] != null)
						{
							for (int x = 0; x < ((ArrayList)BreakMatrix[sCounter]).Count; x++)
							{
								if (((BreakIndexAndTime)((ArrayList)BreakMatrix[sCounter])[x]).counter == Intervals)
								{
									// add shift to constraint
									if (AddPlus)
										constraint += "+";
									else
										AddPlus = true;
									constraint += "s" + sCounter.ToString().PadLeft(2, '0');
								
									if (BreakMatrix[sCounter] != null)
									{
										for (int y = 0; y < ((ArrayList)BreakMatrix[sCounter]).Count; y++)
										{					
											int i = ((BreakIndexAndTime) ((ArrayList)BreakMatrix[sCounter])[y]).counter;
											if (constraint.IndexOf("p" + i.ToString().PadLeft(2, '0')) == -1)
											{
												// add break to constraint							
												constraint += "-p" + i.ToString().PadLeft(2, '0');
											}
										}
									}
									break;
								}
							}
						}
					}
				}
				if (constraint.Length > 0)
				{
					constraint += ">=0";
					// write constraint
					swlp.WriteLine("Z" + ConstraintCounter.ToString().PadLeft(3, '0') + ":" + constraint);
					ConstraintCounter++;
				}				
			}
					


			swlp.Close();
			#endregion
		

			#region write cmd file
			StreamWriter scmd = new StreamWriter(this.tempPath + "\\call_xa.cmd");
			scmd.WriteLine("@echo off");
			scmd.WriteLine("set Oldpath=%path%");
			scmd.WriteLine("path=" + this.XAPath + ";%path%");
			scmd.WriteLine("xa persplan.clp");
			scmd.WriteLine("set path=%Oldpath%");
			scmd.Close();
			#endregion

			#region call cmd file
			//Process p = Process.Start("cmd.exe");
			ProcessStartInfo pi = new ProcessStartInfo();
			pi.UseShellExecute = true;
			pi.FileName = "call_xa.cmd";
			pi.WorkingDirectory = this.tempPath;
			//Process p = Process.Start(@"cmd.exe " + this.tempPath + "\\call_xa.cmd");
			Process p = Process.Start(pi);
			p.WaitForExit();
			#endregion


			
			return true;
		}

		public void GetSolution()
		{
			#region check for solution file
			if (!File.Exists(this.tempPath + "\\XA.out"))
				return;
			#endregion

			#region create array
			ArrayList Shifts = new ArrayList(ShiftNumbers);
			for (int i = 0; i < ShiftNumbers; i++)
				Shifts.Add(0.0);
			ArrayList Breaks = new ArrayList(BreakNumbers);
			for (int i = 0; i < BreakNumbers; i++)
				Breaks.Add(0.0);
			#endregion

			#region read solution file
			StreamReader sSol = new StreamReader(this.tempPath + "\\XA.out");
			string line = "";
			int ReadShifts = 0;
			int ReadBreaks = 0;
			while ((line = sSol.ReadLine()) != null)
			{
				// check for variable names sXX and pXX
				if (ReadShifts < ShiftNumbers)
				{
					for (int ShiftCounter = 0; ShiftCounter < ShiftNumbers; ShiftCounter++)
					{
						if (line.Length >= 23 && line.Substring(7, 3) == "s" + ShiftCounter.ToString().PadLeft(2, '0'))
						{
							// get activity
							Shifts[ShiftCounter] = Convert.ToDouble(line.Substring(11, 12).Trim().Replace('.', ','));
							ReadShifts++;
						}
						if (line.Length >= 62 && line.Substring(46, 3) == "s" + ShiftCounter.ToString().PadLeft(2, '0'))
						{
							Shifts[ShiftCounter] = Convert.ToDouble(line.Substring(50, 12).Trim().Replace('.', ','));
							ReadShifts++;
						}
					}
				}
				if (ReadBreaks < BreakNumbers)
				{
					for (int BreakCounter = ShiftNumbers; BreakCounter < ShiftNumbers + BreakNumbers; BreakCounter++)
					{
						if (line.Length >= 23 && line.Substring(7, 3) == "p" + BreakCounter.ToString().PadLeft(2, '0'))
						{
							// get activity
							Breaks[BreakCounter - ShiftNumbers] = Convert.ToDouble(line.Substring(11, 12).Trim().Replace('.', ','));
							ReadBreaks++;
						}
						if (line.Length >= 62 && line.Substring(46, 3) == "p" + BreakCounter.ToString().PadLeft(2, '0'))
						{
							Breaks[BreakCounter - ShiftNumbers] = Convert.ToDouble(line.Substring(50, 12).Trim().Replace('.', ','));
							ReadBreaks++;
						}
					}
				}
			}
			sSol.Close();
			#endregion

			#region Write information to ShiftNode
			int myShiftCounter = 0;
			ArrayList BreakArray = this.BuildBreakMatrix(ShiftNode);
			ArrayList CoveredBreakActivity = new ArrayList(BreakNumbers);
			for (int i = 0; i < BreakNumbers; i++)
				CoveredBreakActivity.Add(0.0);

			foreach (ExtendedTreeNode node in ShiftNode.Nodes)
			{
				ShiftTreeNode shiftNode = node as ShiftTreeNode;
				shiftNode.ShiftDetails.Activity = (double)Shifts[myShiftCounter];
				// set up breaks
				if (BreakArray[myShiftCounter] != null)
				{
					// shift has breaks
					for (int b = 0; b < ((ArrayList)BreakArray[myShiftCounter]).Count; b++)
					{
						int BreakIndex = ((BreakIndexAndTime)((ArrayList)BreakArray[myShiftCounter])[b]).counter - ShiftNumbers;
						if ((double)Breaks[BreakIndex] > (double)CoveredBreakActivity[BreakIndex])
						{
							// check if this shift has enough staff to send them to break
							if (shiftNode.ShiftDetails.Activity >= (double)Breaks[BreakIndex] - (double)CoveredBreakActivity[BreakIndex])
							{
								// yes, this shift can cover the breaks
								// create a break entry
								Break ABreak = new Break();
								ABreak.Start = new DateTime(2004, 1, 1, 0, 0, 0, 0);
								ABreak.Start = ((BreakIndexAndTime)((ArrayList)BreakArray[myShiftCounter])[b]).BreakStart;
								ABreak.End = ABreak.Start.AddMinutes(30.0);
								ABreak.Activity = (double)Breaks[BreakIndex] - (double)CoveredBreakActivity[BreakIndex];
								double NewCovered = (double)CoveredBreakActivity[BreakIndex] + (double)ABreak.Activity;
								CoveredBreakActivity.RemoveAt(BreakIndex);
								CoveredBreakActivity.Insert(BreakIndex, NewCovered);														
								shiftNode.ShiftDetails.Breaks.Add(ABreak);
							}
							else
							{
								// no, this shift can not (fully) cover the breaks
								// can it cover some of them?
								if (shiftNode.ShiftDetails.Activity > 0)
								{
									// yes, this shift can partially cover the breaks
									// create a break entry
									Break ABreak = new Break();
									ABreak.Start = new DateTime(2004, 1, 1, 0, 0, 0, 0);
									ABreak.Start = ((BreakIndexAndTime)((ArrayList)BreakArray[myShiftCounter])[b]).BreakStart;
									ABreak.End = ABreak.Start.AddMinutes(30.0);
									ABreak.Activity = shiftNode.ShiftDetails.Activity;
									double NewCovered = (double)CoveredBreakActivity[BreakIndex] + (double)ABreak.Activity;
									CoveredBreakActivity.RemoveAt(BreakIndex);
									CoveredBreakActivity.Insert(BreakIndex, NewCovered);
									shiftNode.ShiftDetails.Breaks.Add(ABreak);									
								}								
							}
						}
					}
				}
				myShiftCounter++;
			}
			#endregion
			return;
		}

		/// <summary>
		/// This method builds up a matrix in the following form (example data):
		/// Index: 0		1		2		3		4		5		6 ...	47		48
		/// Time:  0:00		0:30	1:00	1:30	2:00	2:30	3:00 ..	23:30	Preference
		/// ----------------------------------------------------------------------------------
		/// Shift1	0		1		1		1		0		0		0		0		40
		/// Shift2	0		0		1		1		1		0		0		0		60
		/// Shift3	0		0		0		1		1		1		0		0		100
		/// ...
		/// Break			1		1		0		1		1		0		0		0 (const.)
		/// Req.	0		5		7		6		4		1		3		0		0 (const.)
		/// 
		/// Therefore, every half-an-hour interval is represented by an index in the array;
		/// every shift is an entry and has a 0 if it's not during an interval, a 1 if it is,
		/// column 48 contains the preference value of the shift.
		/// in the break row, there is the information whether there can be a break in an interval,
		/// column 48 is constantly 0;
		/// the requirements row contains information about how many people are required in an interval,
		/// column 48 is contantly 0.
		/// </summary>
		/// <param name="RequirementsNode"></param>
		/// <param name="ShiftNode"></param>
		/// <returns></returns>
		public ArrayList BuildMatrix(ExtendedTreeNode RequirementsNode, ExtendedTreeNode ShiftNode)
		{
			#region setup matrix
			// set up the matrix in the format [time][restriction/break/requirement]
			ArrayList Matrix = new ArrayList(48);
			int dimension2 = ShiftNode.Nodes.Count + 2;
			for (int i = 0; i < 49; i++)
			{
				Matrix.Add(new ArrayList(dimension2));
				for (int j = 0; j < dimension2; j++)
					((ArrayList)Matrix[i]).Add(0);
			}
			#endregion

			#region write shifts to matrix
			int shiftCounter = 0;
			foreach (ExtendedTreeNode node in ShiftNode.Nodes)
			{
				ShiftTreeNode shiftNode = node as ShiftTreeNode;
				if (shiftNode != null)
				{
					bool IsOverMidnight = false;
					int startIndex = shiftNode.ShiftDetails.Start.Hour * 2 + Convert.ToInt32(shiftNode.ShiftDetails.Start.Minute / 30);
					int endIndex = shiftNode.ShiftDetails.End.Hour * 2 + Convert.ToInt32(shiftNode.ShiftDetails.End.Minute / 30);
					if (startIndex > endIndex)
						IsOverMidnight = true;
					while (startIndex < endIndex || IsOverMidnight)
					{
						((ArrayList)Matrix[startIndex])[shiftCounter] = 1;
						startIndex++;
						if (startIndex > 47)			// 47 is the max possible index: 23:30h
						{
							IsOverMidnight = false;
							startIndex = 0;
						}
					}
					// write shift preference
					((ArrayList)Matrix[48])[shiftCounter] = shiftNode.ShiftDetails.Preference;
				}				
				shiftCounter++;
			}
			#endregion

			#region write breaks to matrix
			foreach (ExtendedTreeNode node in ShiftNode.Nodes)
			{
				ShiftTreeNode shiftNode = node as ShiftTreeNode;
				if (shiftNode != null)
				{
					bool IsOverMidnight = false;
					int breakStartIndex = shiftNode.ShiftDetails.BreakStart.Hour * 2 + Convert.ToInt32(shiftNode.ShiftDetails.BreakStart.Minute / 30);
					int breakEndIndex = shiftNode.ShiftDetails.BreakEnd.Hour * 2 + Convert.ToInt32(shiftNode.ShiftDetails.BreakEnd.Minute / 30);
					if (breakStartIndex > breakEndIndex)
						IsOverMidnight = true;
					while (breakStartIndex < breakEndIndex || IsOverMidnight)
					{
						((ArrayList)Matrix[breakStartIndex])[shiftCounter] = 1;
						breakStartIndex++;
						if (breakStartIndex > 47)
						{
							IsOverMidnight = false;
							breakStartIndex = 0;
						}
					}
				}
			}
			shiftCounter++;
			#endregion

			#region write requirements to matrix
			foreach (ExtendedTreeNode node in RequirementsNode.Nodes)
			{
				RequirementTreeNode reqNode = node as RequirementTreeNode;
				if (reqNode != null)
				{
					int RequirementIndex = reqNode.RequirementDetails.From.Hour * 2 + Convert.ToInt32(reqNode.RequirementDetails.From.Minute / 30);
					((ArrayList)Matrix[RequirementIndex])[shiftCounter] = reqNode.RequirementDetails.RequiredPersons;
				}
			}
			#endregion


			#region write matrix to file (debug)
			StreamWriter w = new StreamWriter(@"c:\text.txt");
			for (int i = 0; i < 49; i++)
			{
				for (int j = 0; j < ((ArrayList)Matrix[0]).Count; j++)
					w.Write(((ArrayList)Matrix[i])[j].ToString());
				w.WriteLine("");
			}
			w.Close();
			#endregion
			return Matrix;
		}

		public ArrayList BuildBreakMatrix(ExtendedTreeNode ShiftNode)
		{
			#region setup matrix
			ArrayList matrix = new ArrayList();
			foreach (ExtendedTreeNode node in ShiftNode.Nodes)
			{
				ShiftTreeNode myShiftNode = node as ShiftTreeNode;
				if (myShiftNode != null && 
					((myShiftNode.ShiftDetails.BreakStart.Hour > 0 || myShiftNode.ShiftDetails.BreakStart.Minute > 0) ||
					(myShiftNode.ShiftDetails.BreakEnd.Hour > 0 || myShiftNode.ShiftDetails.BreakEnd.Minute > 0)))
				{
					// shift contains break
					ArrayList matrixElement = new ArrayList();
					int AnotherCounter = 0;
					for (int counter = myShiftNode.ShiftDetails.BreakStart.Hour * 2 + Convert.ToInt32(myShiftNode.ShiftDetails.BreakStart.Minute/30); counter < myShiftNode.ShiftDetails.BreakEnd.Hour * 2 + Convert.ToInt32(myShiftNode.ShiftDetails.BreakEnd.Minute / 30); counter++)
					{
						BreakIndexAndTime element;
						element.counter = counter;
						element.BreakStart = myShiftNode.ShiftDetails.BreakStart.AddMinutes(AnotherCounter*30.0);
						//matrixElement.Add(counter);
						matrixElement.Add(element);
						AnotherCounter++;
					}
					matrix.Add(matrixElement);
				}
				else
					matrix.Add(null);
			}
			#endregion
			return matrix;
		}

	}
	struct BreakIndexAndTime
	{
		public int counter;
		public DateTime BreakStart;
	}
}
