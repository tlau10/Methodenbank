/*
    Klasse Radiosender

    Klasse erweitert die abstrakte Klasse Medium -> siehe Klasse Medium
 */


public class Radiosender extends Medium {

	public Radiosender(WerbeBudgetApplication w) {
		super(w, "Radiogrunddaten");
	}

	public int getAnzahlMedien() {
		return wb.getSolverDaten().getAnzahlRadiosender();
	}

	public String getMediumName() {
		return "Radiosender";
	}

	public void setMaxBudget(String maxBudget) {
		wb.getSolverDaten().setMaxBudgetRadiosender(maxBudget);
	}

	public void setMedienKategorien(int i, int x, int y) {
		wb.getSolverDaten().setRadiosenderKategorien(i, x, y);
	}

	public void setMedienDaten(int i, int x, int y, String value) {
		wb.getSolverDaten().setRadiosenderDaten(i, x, y, value);
	}

	public int getMyIndex() {
		return WerbeBudgetApplication.RADIO;
	}

	public boolean setInputs(SolverDaten tmpdata) {
		this.initMedium();
		maxBudgetField.setText("" + tmpdata.getMaxBudgetRadiosender());
		for (int i = 0; i < tmpdata.getAnzahlRadiosender(); i++) {
			this.setAnzahlField(i, ""
					+ tmpdata.getAnzahlRadiosenderKategorien(i + 1));
		}
		this.clickDatenEingegeben();

		int[][][] radiosenderDaten = tmpdata.getradiosenderDaten();

		for (int i = 0; i < radiosenderDaten.length; i++) {
			for (int j = 0; j < radiosenderDaten[i].length; j++) {
				for (int k = 0; k < radiosenderDaten[i][j].length; k++) {
					this.setTableValue(radiosenderDaten[i][j][k], i, j, k + 1);
				}
			}
		}
		this.clickWeiter();
		return true;
	}
}
