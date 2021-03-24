using System;
using System.Xml;
using System.Xml.XPath;
using System.Xml.Schema;
using System.Text;
using System.Xml.Serialization;
using System.Collections;
using System.Xml.Xsl;
using System.IO;


using System.Data;


namespace Clearview
{
	//Autor: Markus Wiezorrek

	/// <summary>
	/// Klasse mit allen Daten für ein Constraint
	/// </summary>
	public class LPConstraint
	{
		public String Name;	//Name der Constraint
		public String Operator;	//Der Operator: =, <, usw.
		public String B_Value; //Wert des B Vektors
		public String Dual_Value; //Dual Wert der Restriktion
		public String Slack; //Wert, von dem das Ergebnis vom B_value entfernt ist

		public LPConstraint()
		{
			Name="";
			Operator="";
			B_Value="";
			Dual_Value="";
			Slack="";
		}
	}
	/// <summary>
	/// Behandelt alle Daten, die zu einer Variablen gehören.
	/// </summary>
	public class LPColumn
	{
		//Member
		public String Name; //Name der Variablen
		public String ZFValue; //Gewichtung in der ursprünglichen ZF funktion
		public String FinalValue; //Wert der variable
		public ArrayList ConstraintValue; //Liste mit den Werten in den Restriktionen.
		public String BoundMinOperator; //Minimaler Wert Operator
		public String BoundMaxOperator; //Maximaler Wert Operator
		public String BoundMinValue; //Minimaler Wert
		public String BoundMaxValue; //maximaler Wert
		public String ReducedCost; //Reduced Cost
		
		//Konstruktor
		public LPColumn()
		{
			Name="";
			ZFValue="";
			FinalValue="";
			ConstraintValue=null;
			BoundMinOperator="";
			BoundMaxOperator="";
			BoundMinValue="";
			BoundMaxValue="";
			ReducedCost="";
		}
	}
	/// <summary>
	/// SolverData
	/// Manager und PersistenzKlasse.
	/// Übernimmt das Laden, Parsen und Speichern der Daten
	/// Autor: Markus Wiezorrek
	/// </summary>
	public class SolverData
	{
		private XPathDocument XMLDoc; //Das XML Dokument mit den Daten
		private XPathNavigator LPNavigator; //Navigator für die XML Daten
		private String LPDocPath; //name und Pfad zu dem XML Dokument
		private String LPSchemaPath; //Name und Pfad zu dem LP Schema

		//Daten zum LP System
		private String Optimisation;//Optiemierung des Systems, "Max!" oder "Min!"
		private String FunctionValue;//Der entgültige Zielfunktionswert
		private  LPColumn[] Columns; //Alle Variablen mit den Werten in den Restriktionen
		private  LPConstraint[] Constraints; //Alle Restriktionen
		private int AnzahlColumns; //Variablenanzahl
		private int AnzahlConstraints; //Restriktionsanzahl
		private bool bValidated; //wahr, wenn XMLDoc dem LPSchema entspricht
		private String errortext; //Fehlertext, wenn eine Methode versagt
		private String UsedSolver; //der eingesetzte Solver
		private String Message; //eine mögliche Meldung vom Solver
		private DataTable SystemTable;
		private DataTable FullTable;

		/// <summary>
		/// Standardkonstruktor
		/// </summary>
		public SolverData()
		{
			errortext="Kein Fehler vorhanden";
			bValidated=true;
			XMLDoc=null;
			LPNavigator=null;
			LPDocPath="";
			LPSchemaPath="LP-Schema.xsd";
			Columns=null;
			UsedSolver="";
			Message="";
		}
		/// <summary>
		/// Load
		/// Zweck: Einlesen und Prüfen der XML Datei und Parsen der Daten
		/// Parameter:
		/// String path: name und Pfad zu der zu ladenden XML Datei
		/// return bool: wahr, wenn erfolgreich Datei geladen wurde und dem Schema entspricht
		/// </summary>

		public bool Load(String path)
		{
			errortext="";

			//Prüfen, ob XML Doc dem Schema entspricht
			if(this.ValidateLPDoc(path)==false)
			{
				//Donderfall: eigene angelegte Datei wird nicht gefunden:
				if(path.Equals("C:\\TEMP\\clearview.xml")==true)
				{
					errortext="Temporäre Datei kann nicht gefunden werden!";
					return false;
				}
				errortext="XML Datei " +path+ " ist nicht gültig:\n" +errortext;
				return false;
			}
			//Datei vorhanden und ok, nun kann sie geladen werden.
			try
			{
				XMLDoc = new XPathDocument(path);			
				LPNavigator=XMLDoc.CreateNavigator();
			}
			catch(Exception e)
			{
				errortext="Fehler beim laden der XML Datei:" +e.ToString();
				return false;
			}
			this.LPDocPath=path;

			//Einlesen der Daten
			this.setInput();					
			this.setOutput();						
			this.createDataTables();
			return true;
		}

		//Prüfen des XML Dokuments anhand des Schemas
		
		//Das Event
		/// <summary>
		/// Diese Exception wird ausgelöst, wenn die nach dem LP Schema zu prüfende Datei
		/// nicht dem Schema entspricht
		/// </summary>
		/// <param name="sender"></param>
		/// <param name="args"></param>
		public void ValidationSink(object sender, ValidationEventArgs args)
		{
			if(args.Severity==XmlSeverityType.Error)			
			{
				bValidated=false;
				errortext="Validation Fehler:\n" +args.Message;
			}
		}
		/// <summary>
		/// Prüft, ob die angegebene Datei dem Lp Schema entspricht
		/// </summary>
		/// <param name="path">Name und Pfad der zu prüfenden Datei</param>
		/// <returns>bool: wahr wenn Datei in ordnung ist 
		/// false: Datei existiert nicht oder erlaubt keinen Zugriff</returns>
		public bool ValidateLPDoc(String path)
		{
			bValidated=true;			
			XmlSchemaCollection xsc=new XmlSchemaCollection();
			XmlTextReader txtrdr=null;
			XmlValidatingReader vrdr=null;
			try
			{		
				xsc.Add("",LPSchemaPath);			
				txtrdr =new XmlTextReader(path);
				vrdr=new XmlValidatingReader(txtrdr);						
				vrdr.ValidationType=ValidationType.Schema;
				vrdr.Schemas.Add(xsc);
				vrdr.ValidationEventHandler+=new ValidationEventHandler(this.ValidationSink);
				while(vrdr.Read()){}
				vrdr.Close();
				return bValidated;
			}
			catch(Exception e)
			{
				errortext="Validation Fehler:\n"+e.ToString();
				vrdr.Close();
				return false;
			}
		}
		/// <summary>
		/// Variable wird nach seinem Namen gesucht
		/// </summary>
		/// <param name="name">Name der Variablen</param>
		/// <returns>Indexwert der Variablen
		/// -1: Variable wurde nicht gefunden</returns>
		public int GetColumnByName(String name)
		{
			for(int i=0;i<Columns.Length;i++)
			{
				if(Columns[i].Name.Equals(name)==true)
					return i;
			}
			return -1;
		}
		/// <summary>
		/// Restriktion wird nach ihrem Namen gesucht
		/// </summary>
		/// <param name="name">Name der Restriktion</param>
		/// <returns>Indexwert der Restriktion
		/// -1: Restriktion wurde nicht gefunden</returns>
		public int GetConstraintByName(String name)
		{
			for(int i=0;i<Constraints.Length;i++)
			{
				if(Constraints[i].Name.Equals(name)==true)					
					return i;
			}
			return -1;
		}
		
		/// <summary>
		/// Methode dient zum Erstellen der Tables für die View Klasse
		/// </summary>
		/// <returns></returns>
		private bool createDataTables()
		{
			//Neue Tables erstellen
			SystemTable=new DataTable("SystemData");		
			FullTable=new DataTable("FullData");
			
			//Tabelle Das System
			DataColumn SystemColumn;
			DataRow SystemRow;
			
			//Tabelle Alles
			DataColumn FullColumn;
			DataRow FullRow;
			
			//Spalte: ZF/ Constraint name:
			SystemColumn=new DataColumn();
			FullColumn=new DataColumn();			
			FullColumn.DataType = System.Type.GetType("System.String");
			FullColumn.ColumnName = "Name";			
			FullColumn.ReadOnly = true;			
			FullColumn.Unique = false;
			FullTable.Columns.Add(FullColumn);
			SystemColumn.DataType = System.Type.GetType("System.String");
			SystemColumn.ColumnName = "Name";			
			SystemColumn.ReadOnly = true;			
			SystemColumn.Unique = false;
			SystemTable.Columns.Add(SystemColumn);
			
			//Variablen Spalte
			for (int i = 0; i<AnzahlColumns; i++)
			{
				SystemColumn=new DataColumn();
				FullColumn=new DataColumn();
				SystemColumn.DataType = System.Type.GetType("System.String");
				FullColumn.DataType = System.Type.GetType("System.String");
				SystemColumn.ColumnName = Columns[i].Name;
				FullColumn.ColumnName = Columns[i].Name;
				SystemColumn.ReadOnly = true;
				FullColumn.ReadOnly = true;
				SystemColumn.Unique = false;
				FullColumn.Unique = false;
				SystemTable.Columns.Add(SystemColumn);
				FullTable.Columns.Add(FullColumn);
			}

			//Spalte für Zeichen/Operatoren
			SystemColumn=new DataColumn();
			FullColumn=new DataColumn();
			SystemColumn.DataType = System.Type.GetType("System.String");
			FullColumn.DataType = System.Type.GetType("System.String");
			SystemColumn.ColumnName = "Operator";
			FullColumn.ColumnName = "Operator";
			SystemColumn.ReadOnly = true;
			FullColumn.ReadOnly = true;
			SystemColumn.Unique = false;
			FullColumn.Unique = false;
			SystemTable.Columns.Add(SystemColumn);
			FullTable.Columns.Add(FullColumn);
			
			//Spalte für B- Vektor
			SystemColumn=new DataColumn();
			FullColumn=new DataColumn();
			SystemColumn.DataType = System.Type.GetType("System.String");
			FullColumn.DataType = System.Type.GetType("System.String");
			SystemColumn.ColumnName = "B";
			FullColumn.ColumnName = "B";
			SystemColumn.ReadOnly = true;
			FullColumn.ReadOnly = true;
			SystemColumn.Unique = false;
			FullColumn.Unique = false;
			SystemTable.Columns.Add(SystemColumn);
			FullTable.Columns.Add(FullColumn);

			//Spalte für Dualwerte:
			FullColumn=new DataColumn();
			FullColumn.DataType = System.Type.GetType("System.String");
			FullColumn.ColumnName = "Dualwert";
			FullColumn.ReadOnly = true;
			FullColumn.Unique = false;
			FullTable.Columns.Add(FullColumn);

			//Spalte für den Slack
			FullColumn=new DataColumn();
			FullColumn.DataType = System.Type.GetType("System.String");
			FullColumn.ColumnName = "Slack";
			FullColumn.ReadOnly = true;
			FullColumn.Unique = false;
			FullTable.Columns.Add(FullColumn);

			//nun werden die Zeilen erstellt
			
			//ZielFunktion
			SystemRow = SystemTable.NewRow();
			FullRow=FullTable.NewRow();
			FullRow["Name"]="ZF:";
			SystemRow["Name"]="ZF:";
			for (int i = 0; i<AnzahlColumns; i++)
			{
				SystemRow[Columns[i].Name] = Columns[i].ZFValue;
				FullRow[Columns[i].Name] = Columns[i].ZFValue;
			}
			SystemRow["Operator"] = "->";
			FullRow["Operator"] = this.Optimisation;
			SystemRow["B"] = this.Optimisation;
			FullRow["B"] = this.FunctionValue;
			SystemTable.Rows.Add(SystemRow);
					
			FullRow["Dualwert"] = "";
			FullRow["Slack"] = "";
			FullTable.Rows.Add(FullRow);
						
			//Restriktionen
			int j=0;
			for (int i = 0; i<AnzahlConstraints; i++)
			{
				SystemRow = SystemTable.NewRow();
				FullRow = FullTable.NewRow();
				FullRow["Name"]=Constraints[i].Name;
				SystemRow["Name"]=Constraints[i].Name;
				for (j = 0; j<AnzahlColumns; j++)
				{
					SystemRow[Columns[j].Name] = Columns[j].ConstraintValue[i];
					FullRow[Columns[j].Name] = Columns[j].ConstraintValue[i];
				}
				SystemRow["Operator"] = Constraints[i].Operator;
				FullRow["Operator"] = Constraints[i].Operator;
				SystemRow["B"] = Constraints[i].B_Value;
				FullRow["B"] = Constraints[i].B_Value;				
				SystemTable.Rows.Add(SystemRow);
				FullRow["Dualwert"] = Constraints[i].Dual_Value;
				FullRow["Slack"] = Constraints[i].Slack;
				FullTable.Rows.Add(FullRow);
			}

			//Bounds Minimum
			SystemRow = SystemTable.NewRow();
			FullRow = FullTable.NewRow();
			String bound="";
			bool hasBound=false;
			for (j = 0; j<AnzahlColumns; j++)
			{
				bound=Columns[j].BoundMinOperator + " " + Columns[j].BoundMinValue;
				if(bound.Equals(" ")==false)
				{
					hasBound=true;
				}
				SystemRow[Columns[j].Name] = bound;
				SystemRow["Operator"] = "";
				SystemRow["B"] = "";
				FullRow["Name"] = "Min:";
				SystemRow["Name"] = "Min:";
				FullRow[Columns[j].Name] = bound;
				FullRow["Operator"] = "";
				FullRow["B"] = "";
				FullRow["Dualwert"] = "";
				FullRow["Slack"] = "";				
			}
			//Bound werden nur ausgegeben, wenn diese existieren
			if(hasBound==true)
			{
				SystemTable.Rows.Add(SystemRow);
				FullTable.Rows.Add(FullRow);
			}
			
			//Bounds Maximum
			SystemRow = SystemTable.NewRow();
			FullRow = FullTable.NewRow();
		    bound="";
			hasBound=false;
			for (j = 0; j<AnzahlColumns; j++)
			{
				bound=Columns[j].BoundMaxOperator + " " + Columns[j].BoundMaxValue;
				if(bound.Equals(" ")==false)
				{
					hasBound=true;
				}
				SystemRow[Columns[j].Name] = bound;
				SystemRow["Operator"] = "";
				SystemRow["B"] = "";
				FullRow["Name"] = "Max:";
				SystemRow["Name"] = "Max:";
				FullRow[Columns[j].Name] = bound;
				FullRow["Operator"] = "";
				FullRow["B"] = "";
				FullRow["Dualwert"] = "";
				FullRow["Slack"] = "";
			}
			//Bound werden nur ausgegeben, wenn diese existieren
			if(hasBound==true)
			{
				SystemTable.Rows.Add(SystemRow);
				FullTable.Rows.Add(FullRow);
			}

			//Reduced costs
			FullRow = FullTable.NewRow();
			FullRow["Name"]="Reduced Cost: ";
			for (j = 0; j<AnzahlColumns; j++)
			{
				FullRow[Columns[j].Name] = Columns[j].ReducedCost;
			}
			FullRow["Operator"] ="";
			FullRow["B"] = "";
			FullRow["Dualwert"] = "";
			FullRow["Slack"] = "";
			FullTable.Rows.Add(FullRow);

			//Ergebnisse der Variablen:
			FullRow = FullTable.NewRow();
			FullRow["Name"]="Variablenwerte: ";
			for (j = 0; j<AnzahlColumns; j++)
			{
				FullRow[Columns[j].Name] = Columns[j].FinalValue;
			}
			FullRow["Operator"] ="";
			FullRow["B"] = "";
			FullRow["Dualwert"] = "";
			FullRow["Slack"] = "";
			FullTable.Rows.Add(FullRow);
			return true;
		}
		/// <summary>
		/// Liefert die SystemTable für den View
		/// </summary>
		/// <returns>Datatable: die SystemTable</returns>
		public DataTable getSystemTable()
		{
			return SystemTable;
		}
		/// <summary>
		/// Liefert die FullTable für den View
		/// </summary>
		/// <returns>Datatable: die FullTable</returns>
		public DataTable getFullTable()
		{
			return FullTable;
		}
		/**Private methode setInput
		 * Nicht alle Systeme haben einen Input, muss deswegen beachtet werden
		 * */


		
		/// <summary>
		/// Zur Übersichtlichkeit wurde das parsen der Daten aufgeteilt. Diese Methode
		/// parst den Input Tag, holt sich aber auch Daten vom Output, falls es nötig ist.
		/// </summary>
		/// 
		/// <returns>true falls erfolgreich</returns>
		private bool setInput()
		{
			int i=0;
			int j=0;
			int Conidx=0;
			String temp="";
			XPathNodeIterator LPIter=null;
			XPathNodeIterator LPIter2=null;
			XPathNodeIterator LPIterSwap=null;
			bool noFunction=false;
			bool OutputGeraterInput=false;
			try
			{				
				//Constraint oder Variablenunabhängige Daten

				//Max- oder Minimierung
				LPIter=LPNavigator.Select("//Input/ObjectiveFunction/Optimization");
				if(LPIter.Count>0)
				{
					LPIter.MoveNext();					
					if (LPIter.Current.Value.Equals("MAXIMIZE")==true)
						Optimisation="MAX!";
					else
						Optimisation="MIN!";				
				}
				else
					Optimisation="n/a";

				//genutzter Solver
				LPNavigator.MoveToRoot();
				LPNavigator.MoveToFirstChild();
				if(LPNavigator.MoveToAttribute("sourceFormat","")==true)
					this.UsedSolver=LPNavigator.Value;
				else
					this.UsedSolver="Unbekannt";	
									
				//Variablen				
				LPIter=LPNavigator.Select("//Input/ObjectiveFunction/Variable");
				LPIter2=LPNavigator.Select("//Output/Variable-Value");
							
				if(LPIter.Count<=0 && LPIter2.Count<=0)
				{
					errortext="keine Variablen da!";
					return false;
				}
				//bei einigen Solvern werden beim Input keine variablen angezeigt,
				//deren ZF Wert bei null liegt. jedoch haben auch diese ein ergebnis
				//Deswegen wird geprüft, ob die Outputanzahl größer als die Inputanzahl ist.
				if(LPIter.Count<LPIter2.Count)
				{
					if(LPIter.Count<=0)
						noFunction=true;
					LPIterSwap=LPIter;
					LPIter=LPIter2;
					LPIter2=LPIterSwap;
					OutputGeraterInput=true;
				}
				Columns= new LPColumn[LPIter.Count];
				for(i=0;i<LPIter.Count;i++)
				{
					Columns[i]=new LPColumn();
				}
				i=0;
				while (LPIter.MoveNext())
				{
					//Sicherung/Fallback: sollte eine Variable mehrfach genannt werden
					//so erhält die 2. variable einen anderen namen.
					//Diese Methode ist eigentlich überflüssig, weil die XML Daten
					//dies nicht mehr zulassen sollten.
					temp=LPIter.Current.GetAttribute("name","");
					if(this.GetColumnByName(temp)!=-1)
					{						
						Columns[i].Name="Fehler" + i;						
					}
					else
						Columns[i].Name=temp;
					if(noFunction==true)
						Columns[i].ZFValue="n/a";
					else if(OutputGeraterInput==true)
					{
						LPIter2=LPNavigator.Select("//Input/ObjectiveFunction/Variable");
						while(LPIter2.MoveNext())
						{
							if(Columns[i].Name.Equals(LPIter2.Current.GetAttribute("name",""))==true)
							{
								Columns[i].ZFValue=LPIter2.Current.Value;
								break;
							}
						}
					}						
					else
						Columns[i].ZFValue=LPIter.Current.Value;						
					i++;
				}
				AnzahlColumns=i;
			}
			catch(Exception e)
			{
				errortext="[SolverData][setInput] Einlesen Variable:\n" +e.ToString();
				return false;
			}
			i=0;
			
			//Restriktionen
			try
			{				
				//Restriktionen erstellen und vorbelegen.
				AnzahlConstraints=0;
				LPIter=LPNavigator.Select("//Input/Constraint");
				if(LPIter.Count<=0)
				{
					//Fallback: LPSolve-kurz impliziert Restriktionen 
					//durch Anzahl Dual-Value.
					LPIter=LPNavigator.Select("//Output/Dual-Value");
				}
				if(LPIter.Count<=0)
				{
					//Fallback2: Weidenauer impliziert Restriktionen 
					//durch Anzahl Slack.
					LPIter=LPNavigator.Select("//Output/Slack");
				}
				if(LPIter.Count>0)
				{
					Constraints=new LPConstraint[LPIter.Count];
					AnzahlConstraints=LPIter.Count;
					for(i=0;i<AnzahlConstraints;i++)
					{
						Constraints[i]=new LPConstraint();
						LPIter.MoveNext();
						Constraints[i].Name=LPIter.Current.GetAttribute("name","");
					}
					for(i=0;i<AnzahlColumns;i++)
					{
						Columns[i].ConstraintValue= new ArrayList(LPIter.Count);
						for(j=0;j<LPIter.Count;j++)
						{
							Columns[i].ConstraintValue.Add("");
						}
					}				
					i=0;

					//Restriktionen eintragen
					for(i=0;i<AnzahlConstraints;i++)
					{
						LPIter=LPNavigator.Select("//Input/Constraint["+ (i+1) +"]/*");
						if(LPIter.Count>0)
						{

							while (LPIter.MoveNext())
							{								
								if(LPIter.Current.Name=="Variable")
								{
									Conidx=this.GetColumnByName(LPIter.Current.GetAttribute("name",""));
									Columns[Conidx].ConstraintValue[i]=LPIter.Current.Value;
								}
								else if(LPIter.Current.Name=="Operator")
								{
									Constraints[i].Operator=LPIter.Current.Value;
								}
								else if(LPIter.Current.Name=="b-Value")
								{
									Constraints[i].B_Value=LPIter.Current.Value;
								}									
							}//ende while
						}//end If count>0
					}//ende for
				}//ende if
			}
			catch(Exception e)
			{
				errortext="[SolverData][setInput]: Einlesen Restriktionen:\n" +e.ToString();
				return false;
			}
			//Bounds
			try
			{
				LPIter=LPNavigator.Select("//Input/Bound");
				if(LPIter.Count>0)
				{
					j=LPIter.Count;
					for(i=1; i<=j;i++) 
					{
						LPIter=LPNavigator.Select("//Input/Bound[" + i + "]");
						LPIter.MoveNext();
						Conidx=this.GetColumnByName(LPIter.Current.GetAttribute("name",""));
						if(Conidx!=-1)
						{
							LPIter=LPNavigator.Select("//Input/Bound[" + i + "]/*");
							LPIter.MoveNext();
					
							if(LPIter.Current.Value.Equals(">=")==true || LPIter.Current.Value.Equals(">")==true)
							{		
								Columns[Conidx].BoundMinOperator=LPIter.Current.Value;
								LPIter.MoveNext();						
								Columns[Conidx].BoundMinValue=LPIter.Current.Value;
							}
							else
							{
								Columns[Conidx].BoundMaxOperator=LPIter.Current.Value;
								LPIter.MoveNext();						
								Columns[Conidx].BoundMaxValue=LPIter.Current.Value;
							}
						}			
					}
				}
			}
			catch(Exception e)
			{
				errortext="[SolverData][setInput] Einlesen Bounds:\n" +e.ToString();
				return false;
			}
			return true;
		}
		/// <summary>
		/// Diese methode kapselt das parsen nach den Output 
		/// </summary>
		/// <returns>true, wenn erfolgreich</returns>
		private bool setOutput()
		{
			int Conidx=0;
			XPathNodeIterator LPIter=null;
			try
			{
				//Message
				LPIter=LPNavigator.Select("//Output/Message");
				if(LPIter.Count>0)
				{
					LPIter.MoveNext();
					this.Message=LPIter.Current.Value;
				}

				//Variablenergebnis 
				LPIter=LPNavigator.Select("//Output/Variable-Value");
				if(LPIter.Count>0)
				{
					while (LPIter.MoveNext())
					{
						Conidx=this.GetColumnByName(LPIter.Current.GetAttribute("name",""));
						if(Conidx!=-1)
							Columns[Conidx].FinalValue=LPIter.Current.Value;
					}
				}

				//Wert der Zielfunktion
				LPIter=LPNavigator.Select("//Output/ObjectiveFunction-Value");
				if(LPIter.Count>0)
				{									
					LPIter.MoveNext();
					FunctionValue=LPIter.Current.Value;										
				}
				else
					FunctionValue="n/a";

				//Dual Werte
				LPIter=LPNavigator.Select("//Output/Dual-Value");
				if(LPIter.Count>0)
				{
					while (LPIter.MoveNext())
					{
						Conidx=this.GetConstraintByName(LPIter.Current.GetAttribute("name",""));
						if(Conidx!=-1)
							Constraints[Conidx].Dual_Value=LPIter.Current.Value;
					}
				}
				//Reduced Costs
				LPIter=LPNavigator.Select("//Output/ReducedCost");
				if(LPIter.Count>0)
				{
					while (LPIter.MoveNext())
					{
						Conidx=this.GetColumnByName(LPIter.Current.GetAttribute("name",""));
						if(Conidx!=-1)
							Columns[Conidx].ReducedCost=LPIter.Current.Value;
					}
				}

				
				//Dual Werte
				LPIter=LPNavigator.Select("//Output/Slack");
				if(LPIter.Count>0)
				{
					while (LPIter.MoveNext())
					{
						Conidx=this.GetConstraintByName(LPIter.Current.GetAttribute("name",""));
						if(Conidx!=-1)
							Constraints[Conidx].Slack=LPIter.Current.Value;
					}
				}
				else
				{
					//Slack: ist es nicht in der XML Datei, 
					//kann es aber durch vorhandene Daten ermittelt werden
					double sum=0.0;
					double slack_double=0.0;
					double sum1=0.0;
					double sum2=0.0;
					String sum_string="";

					for (int i=0; i<this.Constraints.Length; i++)
					{
						for (int j=0; j<this.Columns.Length; j++)
						{
							sum_string="";
							if(Columns[j].FinalValue.Equals("")==true)
								sum1=0.0;
							else
								sum1=Convert.ToDouble(Columns[j].FinalValue)/100000;					
							sum_string=Columns[j].ConstraintValue[i].ToString();
							if(sum_string.Equals("")==true)
								sum_string="0";
							if(sum_string.StartsWith(".")==true)
								sum_string="0" + sum_string;
							sum_string=sum_string.Replace('.',',');
							sum2=Convert.ToDouble(sum_string);
							sum= sum+(sum1*sum2);						
						}
						slack_double=Convert.ToDouble(Constraints[i].B_Value)-sum;
						if(slack_double<0.0001)
							slack_double=0.0;

						if(Constraints[i].Operator.Equals(">")==true || Constraints[i].Operator.Equals(">=")==true)
							slack_double=0.0;
						Constraints[i].Slack= Convert.ToString(slack_double);
						sum=0.0;
					}
				}
			}
			catch(Exception e)
			{
				errortext="[SolverData][setOutput] Fehler:\n"+e.ToString();
				return false;
			}
			return true;
		}
		/// <summary>
		/// Alle Daten werden gelöscht.
		/// </summary>
		public void ClearData()
		{
			errortext="Kein Fehler vorhanden";
			bValidated=true;			
			if(XMLDoc!=null)			
			{							
				XMLDoc=null;
			}
			LPNavigator=null;
			LPDocPath="";
			LPSchemaPath="LP-Schema.xsd";
			Columns=null;
			Constraints=null;
			AnzahlColumns=0;
			AnzahlConstraints=0;
			bValidated=false;
			this.UsedSolver="";
			this.Message="";
			if(SystemTable!=null)
				SystemTable.Clear();
			if(FullTable!=null)
				FullTable.Clear();
		}
		/// <summary>
		/// Methode, welche die HTML Statistik per XSLT erstellt
		/// </summary>
		/// <param name="path">Pfad und Name der zu erstellenen HTML Datei</param>
		/// <returns></returns>
		public bool generateHTMLStatistic(String path)
		{
			try
			{
				XslTransform xslt = new XslTransform();
				xslt.Load(@"genhtml.xslt");
				XPathDocument document = new XPathDocument(this.LPDocPath);
				XmlTextWriter writer = new XmlTextWriter(path, System.Text.Encoding.UTF8);
				xslt.Transform(document, null, writer);
				writer.Close();
			}
			catch(Exception f)
			{
				errortext="Fehler bei Erstellung der HTML Datei:\n" +f.ToString();
				return false;
			}
			return true;
		}

		//Get Methoden
		public String getErrorText()
		{return errortext;}

		public int getAnzahlColumns()
		{return AnzahlColumns;}

		public LPColumn getColumn(int index)
		{
			if(index<0 ||(index+1)>Columns.Length)
				return null;
			return Columns[index];
		}
		public String getFunktionValue()
		{return FunctionValue;}
		public String getUsedSolver()
		{return UsedSolver;}
		public String getMessage()
		{return Message;}
		public String getLPDocPath()
		{return LPDocPath;}





	}//Ende SolverData
}//Ende namespace
