/*
    Klasse Fernsehanstalten

    Klasse erweitert die abstrakte Klasse Medium -> siehe Klasse Medium
 */

public class Fernsehanstalten extends Medium {

	public Fernsehanstalten(WerbeBudgetApplication w) {
		super(w, "Fernsehgrunddaten");    // Namen in der Maske Anzahl MaxBudget und Kategorie
	}

	public int getAnzahlMedien() {
		return wb.getSolverDaten().getAnzahlFernsehanstalten();
	}

	public String getMediumName() {
		return "Fernsehanstalt";			// Namen bei der Tabelle
	}

	public void setMaxBudget(String maxBudget) {
		wb.getSolverDaten().setMaxBudgetFernsehanstalten(maxBudget);
	}

	public void setMedienKategorien(int i, int x, int y) {
		wb.getSolverDaten().setFernsehanstaltenKategorien(i, x, y);
	}

	public void setMedienDaten(int i, int x, int y, String value) {
		wb.getSolverDaten().setFernsehanstaltenDaten(i, x, y, value);
	}

	public int getMyIndex() {
		return WerbeBudgetApplication.FERNSEHEN;
	}

	public boolean setInputs(SolverDaten tmpdata) {
		this.initMedium();
		maxBudgetField.setText("" + tmpdata.getMaxBudgetFernsehanstalten());
		for (int i = 0; i < tmpdata.getAnzahlFernsehanstalten(); i++) {
			this.setAnzahlField(i, ""
					+ tmpdata.getAnzahlFernsehanstaltenKategorien(i + 1));
		}
		this.clickDatenEingegeben();

		int[][][] fernsehanstaltenDaten = tmpdata.getfernsehanstaltenDaten();

		for (int i = 0; i < fernsehanstaltenDaten.length; i++) {
			for (int j = 0; j < fernsehanstaltenDaten[i].length; j++) {
				for (int k = 0; k < fernsehanstaltenDaten[i][j].length; k++) {
					this.setTableValue(fernsehanstaltenDaten[i][j][k], i, j,
							k + 1);
				}
			}
		}
		this.clickWeiter();
		return true;
	}
}
