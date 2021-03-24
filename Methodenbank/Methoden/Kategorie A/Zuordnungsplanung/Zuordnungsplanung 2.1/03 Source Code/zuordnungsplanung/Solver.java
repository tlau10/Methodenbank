


/**
 * <p>Title: Zuordnungsplanung V 1.0</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Bettina Pfeiffer
 * @version 1.0
 */

package zuordnungsplanung;

public interface Solver
{
  public void starteSolver(DataModel dataModel);
  public String[] getErgebnis();
  public String getZielfunktionswert();
  public long getZeit();

}