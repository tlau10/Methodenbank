/*
    Klasse SonstigeMedien

    Klasse erweitert die abstrakte Klasse Medium -> siehe Klasse Medium
 */


public class SonstigeMedien extends Medium {

	public SonstigeMedien(WerbeBudgetApplication w) {
		super(w, "Sonstige Medien Grunddaten");
	}

	public int getAnzahlMedien() {
		return wb.getSolverDaten().getAnzahlSonstigeMedien();
	}

	public String getMediumName() {
		return "Medium";
	}

	public void setMaxBudget(String maxBudget) {
		wb.getSolverDaten().setMaxBudgetSonstigeMedien(maxBudget);
	}

	public void setMedienKategorien(int i, int x, int y) {
		wb.getSolverDaten().setSonstigeMedienKategorien(i, x, y);
	}

	public void setMedienDaten(int i, int x, int y, String value) {
		wb.getSolverDaten().setSonstigeMedienDaten(i, x, y, value);
	}

	public int getMyIndex() {
		return WerbeBudgetApplication.SONSTIGE;
	}

	public boolean setInputs(SolverDaten tmpdata) {
		this.initMedium();
		maxBudgetField.setText("" + tmpdata.getMaxBudgetSonstigeMedien());
		for (int i = 0; i < tmpdata.getAnzahlSonstigeMedien(); i++) {
			this.setAnzahlField(i, ""
					+ tmpdata.getAnzahlSonstigeMedienKategorien(i + 1));
		}
		this.clickDatenEingegeben();

		int[][][] sonstigeMedienDaten = tmpdata.getsonstigeMedienDaten();

		for (int i = 0; i < sonstigeMedienDaten.length; i++) {
			for (int j = 0; j < sonstigeMedienDaten[i].length; j++) {
				for (int k = 0; k < sonstigeMedienDaten[i][j].length; k++) {
					this.setTableValue(sonstigeMedienDaten[i][j][k], i, j,
							k + 1);
				}
			}
		}
		this.clickWeiter();
		return true;
	}
}
