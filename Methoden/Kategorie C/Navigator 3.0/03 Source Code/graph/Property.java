package graph;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class Property {

  private String sValue = new String();
  private String sKey = new String();

  public Property(String mKey, String mValue) {
    sValue = mValue;
    sKey = mKey;
  }

  public String getValue() {
    return sValue;
  }

  public String getKey() {
    return sKey;
  }

}