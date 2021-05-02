
package zuordnungsplanung;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Elmar Erhart
 * @version 1.0
 */

public class FileHandler
{

  public FileHandler()
  {
  }

  public void writeZuoData(DataModel dataModel,String filename) throws Exception
    {
      Object columnVector[] = dataModel.getColumnVector();
      Object rowVector[] = dataModel.getRowVector();
      boolean maximierung = dataModel.getMaximize();


      try
      {
        //Datei mit ZuoDaten erstellen

        PrintWriter myZuoFile = new PrintWriter(new BufferedWriter(new FileWriter(filename)));

        /**********************Datenstrom bilden*************/
        String SerialTab= "";
        int i = 0;
        int j = 0;

        myZuoFile.println(Variables.toolName+";"+Variables.toolVersion+";");
        myZuoFile.println("columns="+";"+columnVector.length+";");
        myZuoFile.println("rows="+";"+rowVector.length+";");
        myZuoFile.println("max="+";"+ dataModel.getMaximize() +";");

        for (i = 0; i < rowVector.length; i++){
          for (j = 0; j < columnVector.length; j++){
            SerialTab = SerialTab + (String) dataModel.getValueAt(i,j) + ";";
          }
          myZuoFile.println(SerialTab);
          SerialTab = "";
        }
        myZuoFile.close();
        }

        catch (Exception e)
        {
          throw e;
        }
        return;
    }

    public DataModel readZuoData(String filename) throws Exception
      {

        DataModel dataModel = new DataModel(0,0,true);;
        try
        {
          /****************Auslesen aus dem Solver**************/

          int z=0;
          File myFileName = new File (filename);
          BufferedReader outputDatei = new BufferedReader(new FileReader(myFileName));

          //init Bereich *****************************************

          //System.out.println(outputDatei.readLine());
          outputDatei.readLine();

          int rowSize=0;
          int columnSize=0;
          int max =0;

          String columnsline = outputDatei.readLine();
          //columnSize = Integer.parseInt(columnsline.substring(9,columnsline.indexOf(";",9)));
          columnSize = convertStringToInt(columnsline.substring(9,columnsline.indexOf(";",9)),false);


          String rowline = outputDatei.readLine();
          //rowSize = Integer.parseInt(rowline.substring(6,rowline.indexOf(";",6)));
          rowSize = convertStringToInt(rowline.substring(6,rowline.indexOf(";",6)),false);

          String maxline = outputDatei.readLine();
          String maxi ="";
          maxi = maxline.substring(5,maxline.indexOf(";",5));
          //System.out.println("maxi "+maxi);
          //System.out.print("maxi.compareTo(true)==0 ");
          //System.out.println(maxi.compareTo("true")==0);
          boolean maximize;
          if(maxi.compareTo("true")==0)//if true
            maximize=true;//set to true
          else if(maxi.compareTo("false")==0)//else if false
            maximize=false;//set to false
          else//else (file must be corrupt)
            throw new Exception();//then throw a new Exception
          dataModel = new DataModel(rowSize,columnSize,maximize);

          for (int i = 0; i < rowSize; i++)
          {
            String Ergebniswert = outputDatei.readLine();
            char lastChar = Ergebniswert.charAt(Ergebniswert.length()-1);
            if(lastChar != ';')
              Ergebniswert= Ergebniswert+";";

            int posY1=0;
            int posY2=0;
            for (int j = 0; j < columnSize; j++)
            {
              if ((i==0)&&(j==0)){}
              posY2=Ergebniswert.indexOf(";",posY1);
              //dataModel.setValueAt(Ergebniswert.substring(posY1,posY2),i,j);
              if(i!=0 && j!=0)
                convertStringToInt(Ergebniswert.substring(posY1,posY2),true);
              dataModel.setValueAt(Ergebniswert.substring(posY1,posY2),i,j);
              posY1=posY2+1;
            }
          }
          outputDatei.close();

          }
          catch (Exception e)
          {
            throw e;
          }
          return dataModel;
      }

      /**
      * readFile opens and reads myFilename and returns the content as String
      * @param s: the name of the String
      * @param nullValueValid: true, if an nullValue is an valid Integer; false, if an nullValue is not an Integer
      * @return the integer Value of String s
      * @throws Exception when the String value can not be converted to Integer
      */
      private int convertStringToInt(String s, boolean nullValueValid) throws Exception
      {
        if(nullValueValid && s.equals(""))
          return 0;
        try
        {
          int i = Integer.parseInt(s);
          return i;
        }
        catch (Exception e)
        {
          throw new Exception();
        }
      }
}
