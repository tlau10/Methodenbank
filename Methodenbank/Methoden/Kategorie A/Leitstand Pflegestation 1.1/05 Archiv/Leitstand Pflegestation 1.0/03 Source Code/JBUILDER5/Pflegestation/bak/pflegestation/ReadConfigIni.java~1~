package pflegestation;
import java.io.*;

/**
 * <p>Title: Leitstand Pflegestation</p>
 * <p>Description: Optimiert die Pflegevorgänge einer Krankenhausstation</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: FH Konstanz</p>
 * @author Sebastian Hagen, Jonathan Feuerstein, Birgit Engler
 * @version 1.0
 */

public class ReadConfigIni
{
    private String LPsolvePath="";
    private boolean readIni()
    {
        try
        {
            String theLine=null;
            File  inFile=new File("ini.txt");
            FileReader theReader=new FileReader(inFile);
            BufferedReader in=new BufferedReader(theReader);
            if(in.ready())
            {
                theLine = in.readLine();
            }
            LPsolvePath=theLine.substring(theLine.indexOf("=")+1);
            System.out.println("****** ReadConfigIni ********");
            System.out.println("LpSolvePath: "+LPsolvePath);
            in.close();
        }
        catch(Exception e)
        {
            return false;
        }
        return true;
  }
  public String getLPsolvePath()
  {
    if(this.readIni()==true)
      return LPsolvePath;
    else
      return null;
  }
}