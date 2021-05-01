package opsa;
import java.io.Serializable;
/**
 * Class Work Data
 *
 * Speichert alle Daten welche im GUI eingegeben werden.
 * Wird genutzt um das Speichern der eingegebenen Daten
 * zu realisieren.
 *
 * @author Stefan Brecht
 * @version 0.1
 */
public class WorkData implements Serializable {

	/**
	 * Serial ID für Serialisierung
	 */
	private static final long serialVersionUID = 3682065120681430305L;

	private int anzOp = 0;
	private int anzPer = 0;
	private int anzSaal = 0;
	private Object[][] tableOpArray;

	/**
	 * Constructor
	 *
	 * @return Class WorkData
	 */
	public WorkData() {}

	/**
	 * Speichert die Werte der derzeitigen Applikation
	 *
	 * @param FmOpSa frame		Derzeitige Applikation
	 */
	public void setWorkData(FmOpSa frame){
		this.anzOp = frame.getOperationsAnz();
		this.anzPer = frame.getPeriodeAnz();
		this.anzSaal = frame.getSaalAnz();
		this.tableOpArray = new Object[this.anzOp][frame.tbOp_columnNames.length];
		for (int i = 0; i < this.anzOp; i++){
			for(int j = 0; j < frame.tbOp_columnNames.length; j++){
				if(j!=4)this.tableOpArray[i][j] = frame.tbOpModel.getValueAt(i, j);
				else this.tableOpArray[i][j] = frame.tbOpModel.getValueAt(i, j);
			}
		}
	}

	/**
	 * Schreibt die Werte in die derzeitige Applikation
	 *
	 * @param FmOpSa frame		Derzeitige Applikation
	 */
	public void setFmOpSaInputs(FmOpSa frame){
		frame.setOperationsAnz(this.anzOp);
		frame.setPeriodeAnz(this.anzPer);
		frame.setSaalAnz(this.anzSaal);
		frame.textAreaInfo.setText("");
		frame.tbOpModel.setRowCount(this.anzOp);
		for (int i = 0; i < this.anzOp; i++){
			for(int j = 0; j < frame.tbOp_columnNames.length; j++){
				frame.tbOpModel.setValueAt("" + this.tableOpArray[i][j], i, j);
			}
		}
	}

}
