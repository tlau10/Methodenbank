package SimulationVisuell;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.awt.FileDialog;
import javax.swing.JTextField;
import Simulation.Bus;
import Simulation.BusController;
import Simulation.BusImSystem;
import Simulation.MatrixController;
import Simulation.SimController;
import Simulation.Planquadrat;



public class SimuViewController implements Runnable
{
	private SimuView sV;
	private SimuViewParameter sP;
	private SimuViewNeu sN;
	private SimuViewNeuBeobachter sB;
	private SimuViewBus sBus;
	private SimController simController;
	private MatrixController matrixController;
	private BusController bController;
	private StringTokenizer tokenizer;
	private Planquadrat pQ;
	
	private double aktuelleZeit;
	private double simZeit;
	private SimuStatistikView sm;

	
	public SimuViewController(MatrixController m, SimController s, BusController bController)
	{
		matrixController = m;
		simController = s;
		this.bController = bController;

	}
	
	
	public void erzeugeSimuStatistik(String StatistikName)
	{
		sm = new SimuStatistikView(StatistikName);
		
	}
	
	public SimuStatistikView getStatistikView()
	{
		return this.sm;
	}
	
	public void anzeigenSimuStatistik(boolean b) 
	{
		sm.statistikAnzeigen(b);
	}
	
	
	// SimuView wird definiert
	public void erzeugeSimuView(String Name)
	{
		sV = new SimuView(Name);
	}
	
	public void anzeigenSimuView(boolean Anzeigen)
	{
		sV.anzeigenSimuView(Anzeigen);
	}
	
	public void definiereSimuView(int hoehe, int breite, int xPos, int yPos)
	{
		sV.definiereSimuView(hoehe, breite, xPos, yPos);
	}
	
	public void erzeugeMenu()
	{
		aLDateiSteuerung aLD = new aLDateiSteuerung();
    	sV.erzeugeMenu(aLD);
	}
	
	public void erzeugeMatrix(int x, int y)
	{
		aLButton aL = new aLButton();
		aLNeuerBus aLBus = new aLNeuerBus();
		aLSimuSteuerung aLSim = new aLSimuSteuerung();
		
		//erstellen der Buttons
		sV.erzeugeMatrix(x, y, aL, aLBus, aLSim);
		
		//erstellen der Planquadrate
		simController.erzeugeMatrix(x,y);
	}
	
	public void erzeugeSimuViewBus(String name)
	{
		sBus = new SimuViewBus(name);
	}
	public void aktualisiereMatrix()
	{
		ArrayList matrixButtonListe = sV.holeMatrixButtons();
		Iterator it = matrixButtonListe.iterator();
				
		while(it.hasNext())
		{
			SimuViewMatrixButton sMB = (SimuViewMatrixButton) it.next();
			Planquadrat pQ = matrixController.holePlanquadrat(sMB.holeID()-1);
			
			sMB.zeichneKomponente(pQ);
		}
	}
	// SimuViewParameter wird definiert
	public void erzeugeSimuViewParameter(String Name)
	{
		sP = new SimuViewParameter(Name);
	}
	
	public void anzeigenSimuViewParameter(boolean Anzeigen)
	{
		sP.anzeigenSimuViewParameter(Anzeigen);
	}
	
	public void anzeigenSimuViewBus(boolean Anzeigen)
	{
		sBus.anzeigenSimuViewBus(Anzeigen);
	}
	
	
	public void definiereSimuViewBus(int breite, int hoehe, int xPos, int yPos,
			boolean stretch)
	{
		sBus.definiereSimuViewBus(breite, hoehe, xPos, yPos, stretch);
	}
	
	public void definiereSimuViewParameter(int hoehe, int breite, int xPos, int yPos,
			boolean stretch)
	{
		sP.definiereSimuViewParameter(hoehe, breite, xPos, yPos, stretch);
	}
	
	public boolean istAktivSimuViewParameter()
	{
		return (sP != null);
	}
	
	public boolean istAktivSimuViewBus()
	{
		return (sBus != null);
	}
	
	public void aufrufenParameter(SimuViewMatrixButton b)
	{
		ArrayList pqListe = new ArrayList(matrixController.holePlanquadratListe());
		Planquadrat pQ = (Planquadrat) pqListe.get(b.holeID()-1);
		
		if (istAktivSimuViewParameter())
		{
			sP.loescheParameterPanel();
			sP.erzeugeParameterPanel(pQ,b);
			sP.anzeigenSimuViewParameter(false);
			sP.anzeigenSimuViewParameter(true);
		}
		else
		{
			erzeugeSimuViewParameter(null);
			definiereSimuViewParameter(350,430,200,200,true);
			sP.erzeugeParameterPanel(pQ,b);
			anzeigenSimuViewParameter(true);
		}
	}
	
	
	// SimuViewNeu wird definiert
	public void erzeugeSimuViewNeu(String Name)
	{
		sB = new SimuViewNeuBeobachter();
		sN = new SimuViewNeu(sB);
	}
	
	public void anzeigenSimuViewNeu(boolean Anzeigen)
	{
		sN.anzeigenSimuViewNeu(Anzeigen);
	}
	
	public void definiereSimuViewNeu(int hoehe, int breite, int xPos, int yPos,
			boolean stretch)
	{
		sN.definiereSimuViewNeu(hoehe, breite, xPos, yPos, stretch);
	}
	
	public boolean istAktivSimuViewNeu()
	{
		return (sN != null);
	}
	
	public void aufrufenSimuViewNeu()
	{
		if (istAktivSimuViewParameter())
		{
			sB = new SimuViewNeuBeobachter();
			aLSimuViewNeu aL = new aLSimuViewNeu();
			sN.erzeugeNeuPanel(aL);
			sN.anzeigenSimuViewNeu(true);
		}
		else
		{
			erzeugeSimuViewNeu(null);
			definiereSimuViewNeu(250,160,200,200,false);
			aLSimuViewNeu aL = new aLSimuViewNeu();
			sN.erzeugeNeuPanel(aL);
			anzeigenSimuViewNeu(true);
		}
	}
	
	
	
	//Busdefiniton
	public void zeigeSimuViewBus(SimuViewBusButton b)
	{
		ArrayList bListe = new ArrayList(bController.getBusListe());
				
		Bus bus = (Bus) bListe.get(b.getId());
		aLBusView aL = new aLBusView();
		if(this.istAktivSimuViewBus())
		{
			sBus.loescheBusPanel();
			sBus.erzeugeBusPanel(bus,aL);
			sBus.anzeigenSimuViewBus(false);
			sBus.anzeigenSimuViewBus(true);
		}
		else
		{
			erzeugeSimuViewBus(null);
			definiereSimuViewBus(350,430,200,200,true);
			sBus.erzeugeBusPanel(bus,aL);
			anzeigenSimuViewBus(true);
		}
	}
	
	public void entferneBus()
	{
		Component[] c = sBus.holePanelBus();
		ArrayList bListe = new ArrayList(bController.getBusListe());
		ArrayList busButtons = new ArrayList(sV.getBusListeButtons());
		JTextField tBusID = new JTextField();
		
		for (int i = 0; i < c.length; i++)
		{
			if(c[i].getName() == "busID")
			{
				tBusID = (JTextField)c[i];
			}
		}
		
		bListe.remove(Integer.parseInt(tBusID.getText()));
		
		/*
		 * Den Button löschen
		 */
		SimuViewBusButton bButton;
		bButton = (SimuViewBusButton)busButtons.get(Integer.parseInt(tBusID.getText()));
		sV.removeButton(bButton);
		
		/*
		 * Listen durchgehen und Indexes neu setzen
		 */
		Iterator it = bListe.iterator();
		Bus bus = new Bus();
		int i = 0;
		
		/*
		 * Id's in der Busliste neu setzen
		 */
		while(it.hasNext()) {
			bus = (Bus) it.next();
			bus.setId(i);
			i++;
		}
		bController.setBusliste(bListe);
	}
	
	public void sendeAenderungenBus()
	{
		Component[] c = sBus.holePanelBus();
		ArrayList bListe = new ArrayList(bController.getBusListe());
		ArrayList pqListe = new ArrayList(matrixController.holePlanquadratListe());
		
		JTextField tLinie = new JTextField();
		JTextField tRoute = new JTextField();
		JTextField tHaltestellen = new JTextField();
		JTextField tBusID = new JTextField();
		JTextField tIntervall = new JTextField();
		
		for (int i = 0; i < c.length; i++)
		{
			if (c[i].getName() == "LinieTF")
			{
				tLinie = (JTextField) c[i];
			}
			else if(c[i].getName() == "RouteTF")
			{
				tRoute = (JTextField) c[i];
			}
			else if(c[i].getName() == "HaltestellenTF")
			{
				tHaltestellen = (JTextField) c[i];
			}
			else if(c[i].getName() == "busID")
			{
				tBusID = (JTextField)c[i];
			}
			else if(c[i].getName() == "IntervallTF")
			{
				tIntervall = (JTextField)c[i];
			}
		}
		
		Bus bus = (Bus) bListe.get(Integer.parseInt(tBusID.getText()));
		
		bus.setLinieID(Integer.parseInt(tLinie.getText()));
		bus.setInterval(Integer.parseInt(tIntervall.getText()));
		
		bus.deleteRoute();
		bus.deleteHaltestellen();
		
		tokenizer = new StringTokenizer(tRoute.getText(),",");
		while(tokenizer.hasMoreTokens())
		{
			pQ = (Planquadrat) pqListe.get(Integer.parseInt(tokenizer.nextToken())-1);
			bus.addRoute(pQ);
		}
		
		tokenizer = new StringTokenizer(tHaltestellen.getText(),",");
		while(tokenizer.hasMoreTokens())
		{
			pQ = (Planquadrat) pqListe.get(Integer.parseInt(tokenizer.nextToken())-1);
			bus.addHaltestelle(pQ);
		}
		ArrayList busButtons = new ArrayList(sV.getBusListeButtons());
		SimuViewBusButton bButton;
		bButton = (SimuViewBusButton)busButtons.get(Integer.parseInt(tBusID.getText()));
		sV.editBusButtonName(bButton,Integer.parseInt(tLinie.getText()));
	}
	
	//Menubaraktionen
	public void erstelleNeueSimulation()
	{
		aufrufenSimuViewNeu();
	}
	
	public void speichernSimulation()
	{
     	FileDialog save = new FileDialog(sV,"Matrix Speichern",FileDialog.SAVE);
     	save.setVisible(true);
     	ArrayList busListe = new ArrayList();
     	               	     
     	String file = save.getDirectory()+save.getFile();     
     	if(save.getFile()!=null)     
     	{       
     		BufferedWriter out =null;
     		try       
			{         
     			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));         
     			
     			//Grösse schreiben
     			int xKoordinate = matrixController.holeXKoordinate();
     			int yKoordinate = matrixController.holeYKoordinate();
     			int gesamtAnzahl = xKoordinate * yKoordinate;
     			out.write(xKoordinate + ";" + yKoordinate);
     			                 			                			
     			for (int i = 0; i < gesamtAnzahl; i++)
     			{
     				out.newLine();
     				Planquadrat mPQ = matrixController.holePlanquadrat(i);
     				out.write(	mPQ.holeID() + ";" +
     							mPQ.holeX() + ";" +
								mPQ.holeY() + ";" +
								mPQ.holeAbstandNord() + ";" +
								mPQ.holeAbstandOst() + ";" +
								mPQ.holeAbstandSued() + ";" +
								mPQ.holeAbstandWest() + ";" +
								mPQ.holeAbstandNordWest() + ";" +
								mPQ.holeAbstandNordOst() + ";" +
								mPQ.holeAbstandSuedWest() + ";" +
								mPQ.holeAbstandSuedOst() + ";" +
								mPQ.holeEWPersonenKommen() + ";" +
								mPQ.holeEWPersonenZiel() + ";" +
								mPQ.holeSAPersonenKommen() + ";" +
								mPQ.holeSAPersonenZiel() + ";" +
								mPQ.holeHaltestelle() + ";"
								);
     			}
     			
     			
     			
     			busListe=bController.getBusListe();
     			
     			Iterator busIterator = busListe.iterator();
     			int busanzahl = 0;
     			
     			busanzahl=busListe.size();
     			
     			out.write("\n"+busanzahl+";");
 				
     			while(busIterator.hasNext())
     			{
     				Bus bus = new Bus();
     				bus =(Bus)busIterator.next();
     				out.write(bus.getId()+";");
     				out.write(bus.getLinieID()+";");
     				out.write(bus.getInterval()+";");
     				
     				
     				ArrayList routeListe = bus.getRoute();       			
         			Iterator routeIterator = routeListe.iterator();
         			
         			int routeanzahl=0;
         			routeanzahl = routeListe.size();
         			out.write(routeanzahl+";");
         			
         			while(routeIterator.hasNext())
         			{
         				Planquadrat p = new Planquadrat(0,0,0);
         				p = (Planquadrat)routeIterator.next();
         				out.write(p.holeID()+";");
         			}
         			ArrayList HSListe = bus.getBusHaltestellenListe();
         			 
         			int anzahlHaltestellen = 0;
         				
         			anzahlHaltestellen=HSListe.size();
         			
         			out.write(anzahlHaltestellen+";");
         			Iterator HSIterator = HSListe.iterator();
         			while(HSIterator.hasNext())
         			{
         				Planquadrat p = new Planquadrat(0,0,0);
         				p = (Planquadrat)HSIterator.next();
         				out.write(p.holeID()+";"); 
         			}
     			}
     			out.close();       
     		}       
     		catch(FileNotFoundException err)       
			{         
     			System.out.println("Fehler beim Speichern"+err);       
     		}       
     		catch(IOException err)       
			{         
     			System.out.println("Schreibfehler..."+err);       
     		}     
         }	
	}
	
	public void oeffneSimulation()
	{
		FileDialog fd = new FileDialog(sV,"Öffnen",FileDialog.LOAD);      
		fd.setVisible(true);
		String file = fd.getDirectory()+fd.getFile();      
		if(fd.getFile()!=null)      
		{         
			BufferedReader in =null;         
			
			try         
			{           
				String zeile = null;           
				in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
				zeile = in.readLine();
				
				//erste Zeile mit Koordinaten
				int semikolon = 0; 
				
				for (int i = 0;i < zeile.length();i++)
				{
					if (zeile.charAt(i) == ';')
					{
						semikolon = i;
						break;
					}
				}
				String x = zeile.substring(0,semikolon);
				String y = zeile.substring(semikolon+1);
				       					
				//matrixViewAnzeige anlegen
				erzeugeMatrix(Integer.parseInt(x), Integer.parseInt(y));
				
				int gesamtAnzahl = Integer.parseInt(x) * Integer.parseInt(y);
				
				for (int i = 1;i <= gesamtAnzahl; i++)
				{
					zeile = in.readLine();
					ArrayList werte = new ArrayList();
					int zaehler = 0;
					int groesse = 0;
					int vonStelle = -1;
					for (int j = 0; j < zeile.length(); j++)
					{
						if (vonStelle == -1)
						{
							vonStelle = j;
						}
						
						groesse++;
						
						if (zeile.charAt(j) == ';')
						{
							zaehler++;
							String zTemp = zeile.substring(vonStelle,vonStelle+groesse-1);
							       								
							werte.add(zTemp);
							groesse = 0;
							vonStelle = -1;
						}
					}
					matrixController.setzePlanquadrate(werte);
				}
				
				zeile = in.readLine();
				int anzahlbusse = 0;
				ArrayList busListe= new ArrayList();
				Bus bustmp = new Bus();
				int busid=0;
				int routeanzahl=0;
				int anzahlHS=0;
				ArrayList buslinien = new ArrayList();
				int routePQid=0;
				int hsPQId =0;
				Planquadrat p;
				
				
				//##########################################################################################
				
				StringTokenizer tokenizer = new StringTokenizer( zeile, ";" );
				while ( tokenizer.hasMoreTokens() )
				{
					anzahlbusse = Integer.parseInt(tokenizer.nextToken());
					
					for(int i =0 ; i<anzahlbusse ; i++)
					{
						
						busid = Integer.parseInt(tokenizer.nextToken());
						bustmp = new Bus(busid);
						bustmp.setLinieID(Integer.parseInt(tokenizer.nextToken()));
						bustmp.setInterval(Integer.parseInt(tokenizer.nextToken()));
						routeanzahl = Integer.parseInt(tokenizer.nextToken());
						
						for(int j=0 ; j<routeanzahl; j++)
						{
							
							routePQid = Integer.parseInt(tokenizer.nextToken());
							p = matrixController.holePlanquadrat(routePQid-1);
							bustmp.addRoute(p);
							
							if(j ==(routeanzahl-1))
							{
								anzahlHS = Integer.parseInt(tokenizer.nextToken());
								for(int k=0 ; k<anzahlHS; k++)
								{
									hsPQId = Integer.parseInt(tokenizer.nextToken());
									
									p = matrixController.holePlanquadrat(hsPQId-1);
									
									bustmp.addHaltestelle(p);
								}
							}
						}
						buslinien.add(bustmp);
					}
					bController.setBusseimSystemAusView(buslinien);
				}
				aktualisiereMatrix();
				        					        					
				in.close();         
				this.aktualisiereBusButton();
			}         
			catch(FileNotFoundException err)         
			{           
				System.out.println("Datei nicht gefunden"+err);         
			}         
			catch(IOException err)
			{           
				System.out.println("Lesefehler"+err);         
			}      
		}
	}
	
	public void starteSimulation() 
	{
		simController.vorbereitenSimulation();
		simZeit = sV.holeSimZeit();
		
		Thread th = new Thread(this);
		th.start();
	}
	
	public void zeigeStatistik()
	{
	}
	
	public void zeichneBusse()
	{
		ArrayList busListe = bController.getBusseImSystemListe();
		
		if (busListe.size() > 0)
		{
			sV.zeichneBusse(busListe);
		}
	}
	
	public void erzeugeNeuenBus(ActionListener aL) 
	{
		int neueID = bController.erzeugeBus();
		
		SimuViewBusButton neuerBus = new SimuViewBusButton(neueID);
		neuerBus.addActionListener(aL);
		neuerBus.setText("Linie 0");
		
		sV.addBusButton(neuerBus);
	}
	
	public void aktualisiereBusButton()
	{
		ArrayList busListe = new ArrayList(bController.getBusListe());
		Iterator itbusListe = busListe.iterator();
		
		while(itbusListe.hasNext())
		{
			Bus tmpBus;
			
			tmpBus = (Bus) itbusListe.next();
			
			int id = tmpBus.getId();
			
			SimuViewBusButton neuerBus = new SimuViewBusButton(id);
			aLBus aL = new aLBus();
			neuerBus.addActionListener(aL);
			neuerBus.setText("Linie "+ tmpBus.getLinieID());
			
			sV.addBusButton(neuerBus);
		}
	}
	
	
	// LISTENER KLASSEN
	class aLButton implements ActionListener
    {
            public void actionPerformed(ActionEvent arg0)
            {
            		SimuViewMatrixButton b = (SimuViewMatrixButton)arg0.getSource();	
            		aufrufenParameter(b);
            }
    }
	
	class aLNeuerBus implements ActionListener
    {
			public void actionPerformed(ActionEvent arg0)
	        {
				aLBus aL = new aLBus();
	        	erzeugeNeuenBus(aL);
	        }
    }
	
	class aLBus implements ActionListener
    {
            public void actionPerformed(ActionEvent arg0)
            {
            	SimuViewBusButton bButton = (SimuViewBusButton) arg0.getSource();
            	zeigeSimuViewBus(bButton);	
            }
    }
	
	class aLSimuViewNeu implements ActionListener
    {
            public void actionPerformed(ActionEvent arg0)
            {
            	if (arg0.getActionCommand() == "Neu")
            	{
            		erzeugeMatrix(sB.holeX(),sB.holeY());
            		definiereSimuView(1000,700,10,100);
            		anzeigenSimuView(true);
            		
            		anzeigenSimuViewNeu(false);
            		sB = null;
            		sN = null;
            	}
            	if (arg0.getActionCommand() == "Abbrechen")
            	{
            		anzeigenSimuViewNeu(false);
            		sB = null;
            		sN = null;
            	}
            }
    }

	class aLSimuSteuerung implements ActionListener
    {
            public void actionPerformed(ActionEvent arg0)
            {
            	if (arg0.getActionCommand() == "Start")
            	{
           			starteSimulation();
            	}
            	
            	if (arg0.getActionCommand() == "Stop")
            	{
            	}
            	
            	if (arg0.getActionCommand() == "Pause")
            	{
            	}
            }
    }
	
	class aLBusView implements ActionListener
    {
            public void actionPerformed(ActionEvent arg0)
            {
            	if (arg0.getActionCommand() == "Löschen")
            	{
            		entferneBus();
            		sBus.setVisible(false);
            		sBus = null;
            	}
            	
            	if (arg0.getActionCommand() == "Speichern")
            	{
            		sendeAenderungenBus();
            		sBus.setVisible(false);
            	}
            }
    }
	
	class aLDateiSteuerung implements ActionListener
    {
    	public void actionPerformed(ActionEvent arg0) 
    	{
    		String s = (String)arg0.getActionCommand(); 
    		
    		//Neue Simulation
    		if (s == "Neu")
    		{
    			erstelleNeueSimulation();
    		}
    		
    		//Simulation speichern
    		if (s == "Speichern")
    		{
    			speichernSimulation();
    		}
    		
    		//Simulation öffnen
    		if (s == "Öffnen")
    		{
    			oeffneSimulation();
    		}
    		
    		//Simulation starten
    		if (s == "Starten")
    		{
    			//starteSimulationView();
    		}
    		
    		//Simulationstatistik
    		if (s == "Statistik")
    		{
    			//zeigeStatistik();
    		}
    	}
    }
	
	
	public void zeigeZeitpunktStatistik(String meldung)
	{
		sm.gebeEventsAus(meldung);
	}
	
	public void run()
	{
		while (simZeit > aktuelleZeit) 
	    {
			aktuelleZeit++;
				
			sV.setzeAktuelleZeit(aktuelleZeit);
			simController.Simulation(aktuelleZeit);
									
			try
			{
	    		Thread.sleep(100);
			} 
	    	catch (InterruptedException e) 
			{
	        //nichts
			}
	    }
	}
}
