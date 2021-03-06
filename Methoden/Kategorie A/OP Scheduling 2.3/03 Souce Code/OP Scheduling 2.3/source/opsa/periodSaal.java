package opsa;
/**
 * Title: Operationssleplanung Description: Copyright: Copyright (c) 2002
 * Company: FHKN
 *
 * @author Arne Bittermann, Liwei Lu
 * @version 1.0
 */

public class periodSaal {
	private int anzSaal = 0;
	private int periodeID = 0;

	public periodSaal(int in_periodeID, int in_anzSaal) {
		periodeID = in_periodeID;
		anzSaal = in_anzSaal;
	}

	public int getAnzSaal() {
		return anzSaal;
	}

	public int getPeriodeID() {
		return periodeID;
	}

	public void setAnzSaal(int in_anzSaal) {
		anzSaal = in_anzSaal;
	}

	public void setPeriode(int in_periodeID) {
		periodeID = in_periodeID;
	}
}