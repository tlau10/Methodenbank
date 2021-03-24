package planer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.jaxen.JaxenException;
import org.jaxen.jdom.JDOMXPath;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

/**
 * <p>
 * Title: XmlListHandler
 * </p>
 * <p>
 * Description: handles the XML-file, where the companies are stored
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author unascribedd
 * @version 1.0
 */

public class XmlListHandler {
	private Document doc_;
	private Element root_;
	private long lastModified_;

	/**
	 * XmlListHandler() opens a xml file and builts a saxtree
	 * 
	 * @param filename:
	 *            the name of the file
	 * @throws IOException
	 */
	XmlListHandler(String xmlFilename) throws DiaetplanerException {
		System.out.println("XmlListHandler( " + xmlFilename + ")");
		SAXBuilder saxBuilder = new SAXBuilder();
		try {
			File xmlFile = new File(xmlFilename);
			lastModified_ = xmlFile.lastModified();
			doc_ = saxBuilder.build(xmlFile);
			root_ = doc_.getRootElement();

		} catch (JDOMException e) {
			throw new DiaetplanerException(e.getMessage());
		}
	}

	/**
	 * getList() returns a list of elements
	 */
	public List getList() {
		return root_.getChildren();
	}

	public Document getDocument() {
		return doc_;
	}

	/**
	 * public boolean deleteElement()
	 * 
	 * @param filename:
	 *            the name of the element
	 */
	public boolean deleteElement(Element element) {
		return root_.removeContent(element);
	}

	/**
	 * public boolean addElement()
	 * 
	 * @param element:
	 *            the element which shoud be added
	 */
	public void addElement(Element element) {
		root_.addContent(element);
		root_.addContent("\n");
	}

	/**
	 * public boolean addElement()
	 * 
	 * @param filename:
	 *            the name of the file
	 */
	public void saveXmlList(String filename) throws DiaetplanerException {
		try {
			File outputFile = new File(filename);
			OutputStreamWriter wout = new OutputStreamWriter(
					new FileOutputStream(outputFile), "UTF-8");
			XMLOutputter outputter = new XMLOutputter();
			// set whether the text has leading/trailing whitespace trimmed
			outputter.setTextTrim(true);
			// this will do indentation
			outputter.setIndent(" ");
			// Sets whether newlines be added during output as an attempt to
			// beautify code
			outputter.setNewlines(true);
			outputter.setLineSeparator(System.getProperty("line.separator"));

			outputter.output(doc_, wout);
			wout.close();
			lastModified_ = System.currentTimeMillis();
		} catch (IOException e) {
			throw new DiaetplanerException(e.getMessage());
		}
	}

	public long getLastModified() {
		return lastModified_;
	}

	public void saveXmlList(String filename, List list)
			throws DiaetplanerException {
		try {
			File outputFile = new File(filename);
			OutputStreamWriter wout = new OutputStreamWriter(
					new FileOutputStream(outputFile), "UTF-8");
			XMLOutputter outputter = new XMLOutputter();
			outputter.output(list, wout);
			wout.close();
			lastModified_ = System.currentTimeMillis();
		} catch (IOException e) {
			throw new DiaetplanerException(e.getMessage());
		}

	}

	public List search(SearchElement[] searchArray) throws DiaetplanerException {
		List results;
		try {
			results = this.search(searchArray, false);
		} catch (Exception e) {
			throw new DiaetplanerException(e.getMessage());
		}
		return results;
	}

	public List search(SearchElement[] searchArray, boolean or)
			throws DiaetplanerException {
		String typ = "";
		if (or)
			typ = "or";
		else
			typ = "and";

		String searchString = "company[";
		String search1 = "contains(translate(descendant::";
		String search2 = ",'ABCDEFGHIJKLMNOPQRSTUVWXYZÄÖÜ','abcdefghijklmnopqrstuvwxyzäöü'),\"";

		for (int i = 0; i < searchArray.length; i++) {
			if (i > 0)
				searchString = searchString + " " + typ + " ";
			searchString = searchString + search1
					+ searchArray[i].getElementString() + search2
					+ searchArray[i].getValueString() + "\")";
		}
		searchString = searchString + "]";

		List results;
		try {
			JDOMXPath path = new JDOMXPath(searchString);
			// Ergebnisse werden in einer Liste zurückgegeben
			results = path.selectNodes(root_);
		} catch (JaxenException e) {
			throw new DiaetplanerException(e.getMessage());
		}
		return results;
	}

	public List difference(List minuend, List subtrahend) // Differenz,
															// minuend =
															// {1,2,3,4},subtrahend
															// = {2,4,6},return
															// {1,3}
	{
		List result = new ArrayList();

		// add all elements from minuend to list results
		for (int i = 0; i < minuend.size(); i++)
			result.add(minuend.get(i));

		// delete the elements which are in list2 from list results
		for (int i = 0; i < subtrahend.size(); i++)
			result.remove(subtrahend.get(i));

		return result;
	}

	public List union(List list1, List list2) // Vereinigung, list1 = {1,2,3},
												// list2 = {1,2,4,5}, return =
												// {1,2,3,4,5}
	{
		List result = new ArrayList();

		// add all elements from list1 to list results
		for (int i = 0; i < list1.size(); i++)
			result.add(list1.get(i));

		for (int i = 0; i < list2.size(); i++)
			if (!result.contains(list2.get(i)))
				result.add(list2.get(i));

		return result;
	}

	public List intersection(List list1, List list2) // Durchschnitt list1 =
														// {1,2,3}, list2 =
														// {1,2,4,5}, return =
														// {1,2}
	{
		List result = new ArrayList();

		for (int i = 0; i < list2.size(); i++)
			if (list1.contains(list2.get(i))) // if list1 contains the same
												// element from list2(i)
				result.add(list2.get(i)); // add this to result

		return result;
	}

}