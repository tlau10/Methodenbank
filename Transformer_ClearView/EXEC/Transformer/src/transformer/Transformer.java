/*
 * Created on 14.04.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package transformer;

import interfaces.Parser;
import org.dom4j.Document;

import util.RegularExpressionHelper;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;

/**
 * @author Raetz
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Transformer implements ClipboardOwner{

	public static final int XA_MPS = 0;
	public static final int XA_LP = 1;
	public static final int STRADA = 2;
	public static final int WEIDENAUER = 3;
	public static final int LP_SOLVE = 4;
	public static final int LP_SOLVE_LONG = 5;
	
	public static final int RC_OK = 0;
	public static final int RC_PARAMETERS = 1;
	public static final int RC_EXCEPTION = 2;
	public static final int RC_FORMAT = 3;

	private Parser parser;

	public static void main(String[] args) {
		
		Transformer transformer = new Transformer();
		transformer.process(args);
		
	}
	
	private void process(String [] args) {
		if(args.length != 2)
			System.exit(RC_PARAMETERS);

		String data = "";
		
		try {	
			
			if(args[0].toUpperCase().equals("CLIPBOARD")) {
				data = getClipboardContents();
			}
			else {
				
				Reader fileReader = new FileReader(args[0]);
				
				for ( int c; ( c = fileReader.read() ) != -1; )
					data += (char) c;				
				fileReader.close();
			}
			
			if(data == null || data.equals(""))
				System.exit(RC_FORMAT);
				
			// Format bestimmen	  			  		
			int format = getFormat(data);
	  		
			Parser parser = null;
	  		
			// Entsprechen Parser benutzen
			switch(format) {
				case 0:	parser = new XA_MPS_Parser();
						break;
				case 1:	parser = new XA_LP_Parser();
						break;
				case 2:	parser = new Strada_Parser();
						break;
				case 3:	parser = new Weidenauer_Parser();
						break;
				case 4:	parser = new LP_SHORT_Parser();
						break;
				case 5:	parser = new LP_LONG_Parser();
						break;
				default: System.exit(RC_FORMAT);
			}
	  		
			Document result = parser.parse(data);
	  		
			if(args[1].toUpperCase().equals("CLIPBOARD")) {
				setClipboardContents(result.asXML());
			}
			else {		  		
				Writer fileWriter = new FileWriter(args[1]);
		  			  		
				fileWriter.write(result.asXML());	  		
				fileWriter.close();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			System.exit(RC_EXCEPTION);
		}
		
		System.exit(RC_OK);
	}
	
		
	private int getFormat(String data) {
		
		if(data.startsWith("XA")) {
			
			if(RegularExpressionHelper.contains(data, "MPS FORMAT"))
				return XA_MPS;
			else
				return XA_LP;
		}
		else if(RegularExpressionHelper.contains(data, "Value of objective function:")) {
			if(RegularExpressionHelper.contains(data, "**********Data read**********"))
				return LP_SOLVE_LONG;
			else
				return LP_SOLVE;	
		}			
		else if(RegularExpressionHelper.contains(data, "Weidenauer Optimizer"))
			return WEIDENAUER;
		else if(RegularExpressionHelper.contains(data, "NAME OF LP-MODELL"))
			return STRADA;
		else	
			return -1;
	}

	private void setClipboardContents(String text) {
		StringSelection selection = new StringSelection(text);
		Clipboard c = Toolkit.getDefaultToolkit().getSystemSelection();
		//System.out.print(c.);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(selection, this);
	}
	
	private String getClipboardContents() {
		String result = "";
		
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		
		Transferable contents = clipboard.getContents(null);
		boolean hasTransferableText = (contents != null) && contents.isDataFlavorSupported(DataFlavor.stringFlavor);
		
		if(hasTransferableText) {
			try {
				result = (String) contents.getTransferData(DataFlavor.stringFlavor);
			}
			catch(Exception e) {
				e.printStackTrace();
				System.exit(2);
			}
		}
		
		return result;
	}
	
	public void lostOwnership(Clipboard clipboard, Transferable contents) {
	}
}
