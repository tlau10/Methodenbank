package Simulation.eduni.simdiag;
import java.awt.event.*;

/** Displays graph. Required to update the display after
 * a set of <a href="eduni.simdiag.GraphData.html">GraphData</a> commands.
 * see also <a href="../design_doc/index.html#twodgraphs">the design document</a>.
 */
public class GraphDisplay extends GraphEventObject {
  public GraphDisplay(Object src) { super(src); }
  public void doit(GraphDiagram  d) {  d.display(); }
}
