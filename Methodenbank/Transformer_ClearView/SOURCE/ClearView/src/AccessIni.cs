using System;
using System.Xml;
using System.Xml.XPath;
using System.IO;

namespace Clearview
{
	/// <summary>
	/// Klasse zum Speichern von Pfaden
	/// </summary>
	public class AccessIni
	{
		private XmlDocument IniDoc;//Die XML Datei "CVConfig.xml"
		private String IniDocPath;//Pfad zur obrigen Datei 
		private String JavaPath; //Pfad zur java.exe
		private String TransformerPath; //Pfad zum Transformer
		private String errortext;//Fehlerausgabe
		/// <summary>
		/// Konstruktor
		/// </summary>
		public AccessIni()
		{
			IniDoc=null;
			JavaPath="C:\\Programme\\Java\\j2re1.4.2_03\\bin";
			TransformerPath="";
			IniDocPath="";
			errortext="Kein Fehler vorgekommen";			
		}
		/// <summary>
		/// laden der Einstellungsdatei
		/// </summary>
		/// <param name="path">Pfad und Name der Datei</param>
		/// <returns>true, wenn erfolgreich, bei Fehler false</returns>
		public bool Load(String path)
		{
			IniDocPath=path;
			try
			{
				if(IniDoc!=null)
				{
					this.Save();
					IniDoc=null;
				}
				IniDoc=new XmlDocument();
				IniDoc.Load(path);				
				XPathNavigator cfgNav=IniDoc.CreateNavigator();
				
				//Laden des Java Pfades
				XPathNodeIterator cfgIter=cfgNav.Select("//Java");
				if(cfgIter.Count>0)
				{
					cfgIter.MoveNext();
					JavaPath=cfgIter.Current.Value;
				}
				else
					JavaPath="C:\\Programme\\Java\\j2re1.4.2_03\\bin";
				
				//Laden des Transformer Pfades
				cfgIter=cfgNav.Select("//Transformer");			
				if(cfgIter.Count>0)
				{
					cfgIter.MoveNext();
					TransformerPath=cfgIter.Current.Value;
				}
				else
					TransformerPath="";
			}
			catch(Exception e)
			{
				errortext=e.Message;
				return false;
			}
			return true;
		}
		/// <summary>
		/// Dient zum Speichern der Einstellungen
		/// </summary>
		/// <returns>true wenn erfolgreich, bei Fehler false</returns>
		public bool Save()
		{
			if(IniDoc==null)
				return false;
			try
			{
				StreamWriter textWriter = new StreamWriter(IniDocPath);
				XmlTextWriter xw = new XmlTextWriter(textWriter);
				xw.WriteStartDocument(true);				
				xw.WriteStartElement("ClearView");
				xw.WriteStartElement("Java");
				xw.WriteString(this.JavaPath);
				xw.WriteEndElement();
				xw.WriteStartElement("Transformer");
				xw.WriteString(this.TransformerPath);
				xw.WriteEndElement();
				xw.WriteEndElement();
				xw.WriteEndDocument();	
				xw.Close();				
			}
			catch(Exception e)
			{
				errortext=e.Message; 
				return false;
			}			
			return true;
		}
		/// <summary>
		/// Methode zum Schlieﬂen der aktuellen Datei
		/// </summary>
		/// <returns>true wenn erfolgreich, bei Fehler false</returns>
		
		public bool Close()
		{
			try
			{
				this.Save();
				IniDoc=null;
				JavaPath="C:\\Programme\\Java\\j2re1.4.2_03\\bin";
				TransformerPath="";
				IniDocPath="";
				errortext="kein Fehler vorgekommen";
			}
			catch(Exception e)
			{	errortext=e.Message;
				return false;}
			return true;
		}
		public void setJavaPath(String path)
		{
			this.JavaPath=path;
		}

		public void setTransformerPath(String path)
		{
			this.TransformerPath=path;
		}

		public String getJavaPath()
		{
			return this.JavaPath;
		}

		public String getTransformerPath()
		{
			return this.TransformerPath;
		}
		public String getErrorText()
		{
			return this.errortext;
		}
	}
}
