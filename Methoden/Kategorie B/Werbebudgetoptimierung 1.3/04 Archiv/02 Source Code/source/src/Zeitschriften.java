/*
    Klasse Zeitschriften

    Klasse erweitert die abstrakte Klasse Medium -> siehe Klasse Medium
 */


public class Zeitschriften extends Medium {

	public Zeitschriften(WerbeBudgetApplication w) {
		super(w, "Zeitschriftengrunddaten");
	}

	public int getAnzahlMedien() {
		return wb.getSolverDaten().getAnzahlZeitschriften();
	}

	public String getMediumName() {
		return "Zeitschrift";
	}

	public void setMaxBudget(String maxBudget) {
		wb.getSolverDaten().setMaxBudgetZeitschriften(maxBudget);
	}

	public void setMedienKategorien(int i, int x, int y) {
		wb.getSolverDaten().setZeitschriftenKategorien(i, x, y);
	}

	public void setMedienDaten(int i, int x, int y, String value) {
		wb.getSolverDaten().setZeitschriftenDaten(i, x, y, value);
	}

	public int getMyIndex() {
		return WerbeBudgetApplication.ZEITSCHRIFT;
	}

	public boolean setInputs(SolverDaten tmpdata) {
		this.initMedium();
		maxBudgetField.setText("" + tmpdata.getMaxBudgetZeitschriften());
		for (int i = 0; i < tmpdata.getAnzahlZeitschriften(); i++) {
			this.setAnzahlField(i, ""
					+ tmpdata.getAnzahlZeitschriftenKategorien(i + 1));
		}
		this.clickDatenEingegeben();

		int[][][] zeitschriftenDaten = tmpdata.getzeitschriftenDaten();

		for (int i = 0; i < zeitschriftenDaten.length; i++) {
			for (int j = 0; j < zeitschriftenDaten[i].length; j++) {
				for (int k = 0; k < zeitschriftenDaten[i][j].length; k++) {
					this
							.setTableValue(zeitschriftenDaten[i][j][k], i, j,
									k + 1);
				}
			}
		}
		this.clickWeiter();
		return true;
	}
}
