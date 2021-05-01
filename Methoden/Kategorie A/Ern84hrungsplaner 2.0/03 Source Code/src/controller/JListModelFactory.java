package controller;

import java.util.Vector;

import org.jdom.Element;

import view.ErrorMessages;

/**
 * <p>
 * Application: Ernaehrungsplaner 2.0
 * </p>
 * <p>
 * Class: JListModelFactory
 * </p>
 * <p>
 * Description: Kopierte Standard-Klasse
 * </p>
 * <p>
 * Copyright: Copyright (c) 2015 (Refactoring)
 * </p>
 * <p>
 * Organisation: HTWG-Konstanz
 * </p>
 * 
 * @author Julien Hespeler, Dusan Spasic
 * @version 2.0
 */

public class JListModelFactory {
	private Vector<Object> Eatables_;
	private int targetGroup_;

	public JListModelFactory(Vector<Object> Eatables) {
		Eatables_ = Eatables;
	}

	public Vector<Object> createGroup(int targetGroup) {
		Vector<Object> jlistModelData = new Vector<Object>();
		targetGroup_ = targetGroup;
		int elemGroup;
		Integer temp;
		String tmpstr;
		for (int i = 0; i < Eatables_.size(); i++) {
			Element myElement = (Element) Eatables_.get(i);
			temp = new Integer(myElement.getChild("GruppenID").getText());
			elemGroup = temp.intValue();

			if (elemGroup == targetGroup_) {
				tmpstr = i + ") ";
				tmpstr += myElement.getChild("Name").getText() + " ";
				tmpstr += myElement.getChild("Menge").getText();
				if ((elemGroup == 3) || (elemGroup == 7) | (elemGroup == 10))
					tmpstr += "ml" + " ";
				else
					tmpstr += "gr" + " ";

				tmpstr += myElement.getChild("Kalorien").getText();
				jlistModelData.add(tmpstr);
			}

		}
		return jlistModelData;
	}

	public Vector<Object> createAll() {
		Vector<Object> jlistModelData = new Vector<Object>();
		int elemGroup;
		Integer temp;
		String tmpstr;
		for (int i = 0; i < Eatables_.size(); i++) {
			Element myElement = (Element) Eatables_.get(i);
			temp = new Integer(myElement.getChild("GruppenID").getText());
			elemGroup = temp.intValue();

			tmpstr = i + ") ";
			tmpstr += myElement.getChild("Name").getText() + " ";
			tmpstr += myElement.getChild("Menge").getText();
			if ((elemGroup == 3) || (elemGroup == 7) | (elemGroup == 10))
				tmpstr += "ml" + " ";
			else
				tmpstr += "gr" + " ";

			tmpstr += myElement.getChild("Kalorien").getText();
			tmpstr += " [Gruppe " + elemGroup + "]";
			jlistModelData.add(tmpstr);
		}
		return jlistModelData;
	}

	public Vector<Object> createMenue(String menue) {
		Vector<Object> jlistModelData = new Vector<Object>();
		String menue_ = menue;
		int elemGroup;
		Integer temp;
		String tmpstr;

		for (int i = 0; i < Eatables_.size(); i++) {
			if (Eatables_.get(i) != null) {
				Element myElement = (Element) Eatables_.get(i);
				temp = new Integer(myElement.getChild("GruppenID").getText());
				elemGroup = temp.intValue();

				if (menue_.equals("Fruehstueck")) {

					if (elemGroup < 4) {
						tmpstr = i + ") ";
						tmpstr += myElement.getChild("Name").getText() + " ";
						tmpstr += myElement.getChild("Menge").getText();
						if ((elemGroup == 3) || (elemGroup == 7)
								| (elemGroup == 10))
							tmpstr += "ml" + " ";
						else
							tmpstr += "gr" + " ";

						tmpstr += myElement.getChild("Kalorien").getText();
						jlistModelData.add(tmpstr);
					}
				} else if (menue_.equals("Mittagessen")) {

					if ((elemGroup > 3) && (elemGroup < 8)) {
						tmpstr = i + ") ";
						tmpstr += myElement.getChild("Name").getText() + " ";
						tmpstr += myElement.getChild("Menge").getText();
						if ((elemGroup == 3) || (elemGroup == 7)
								| (elemGroup == 10))
							tmpstr += "ml" + " ";
						else
							tmpstr += "gr" + " ";

						tmpstr += myElement.getChild("Kalorien").getText();
						jlistModelData.add(tmpstr);
					}
				} else if (menue_.equals("Abendessen")) {

					if (elemGroup > 7) {
						tmpstr = i + ") ";
						tmpstr += myElement.getChild("Name").getText() + " ";
						tmpstr += myElement.getChild("Menge").getText();
						if ((elemGroup == 3) || (elemGroup == 7)
								| (elemGroup == 10))
							tmpstr += "ml" + " ";
						else
							tmpstr += "gr" + " ";

						tmpstr += myElement.getChild("Kalorien").getText();
						jlistModelData.add(tmpstr);
					}
				} else
					ErrorMessages.throwErrorMessage("Kein Menü gewählt!");
			}
		}
		return jlistModelData;
	}

}