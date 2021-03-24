package graph;

import java.awt.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public interface Element {

  public void paintMe(Graphics g);
  public boolean isInPos(int x, int y);
  public int getType();
  public void showPropertyPage();
  public void setMarked(boolean m);

}