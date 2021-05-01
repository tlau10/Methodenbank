package Simulation.eduni.simdiag;
import java.awt.event.*;

/** Clears graph */
public class GraphClearObject extends GraphEventObject {
  public GraphClearObject(Object src) { super(src); }
  public void doit(GraphDiagram  d) {  d.clear(); }
}
