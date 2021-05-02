package absf1;

import java.lang.String;
import java.io.*;
import java.util.*;
import javax.swing.table.TableModel;

/**
 * <p>Title: Solver Interface</p>
 * <p>Description:Interface  Schnittstelle für die Solverklassen</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Florian Puetz
 * @version 1.0
 */

public interface Solver {
  public void writeToInFile(ArrayList inputList, TableModel pGebinde);
  public int executeSolver(String directory) throws IOException;
  public ArrayList getOutput();
}