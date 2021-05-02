package graph;

import java.io.*;
import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class ConfigFile {

  private String sFilename;
  private Vector sLines = new Vector();
  private Vector oValues = new Vector();

  public ConfigFile() {
  }

  public ConfigFile(String s) {
    sFilename = s;
    ReadFile();
    parseVector();
  }

  private boolean ReadFile() {
    String line;
    boolean resultvalue=true;
    try {
      BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(sFilename)));
      while(input.ready())
      {
          line = input.readLine();
          if (line == null)
               break;
            else
              sLines.addElement(line.trim());
      }
    }
    catch (Exception e) {
      e.printStackTrace() ;
      resultvalue=false;
    }
    return resultvalue;
  }

  private void parseVector() {
    int posTrennung=0;
    for (int i=0; i<sLines.size(); i++) {
      if (((String)sLines.get(i)).length() > 3) {
        if (((String)sLines.get(i)).substring(0,3).equals("rem")==true) {
          // kommentareintrag
        }
        else {
          // key=value - eintrag
          posTrennung=((String)sLines.get(i)).indexOf("=");
          oValues.addElement(new Property( ((String)sLines.get(i)).substring(0            ,posTrennung),
                                           ((String)sLines.get(i)).substring(posTrennung+1,((String)sLines.get(i)).length() )) );
        }
      }
    }
  }

  public String getValue(String mKey) {
    for (int i=0; i<oValues.size(); i++) {
      if ( (((Property)oValues.get(i)).getKey()).equals(mKey)==true) {
        return ((((Property)oValues.get(i)).getValue()));
      }
    }
    return "";
  }
}