package planer;
import java.util.Vector;
import org.jdom.Element;
import javax.swing.JOptionPane;

public class JListModelFactory {
  Vector Eatables_;
  int zielGruppe_;

  public JListModelFactory(Vector Eatables)
  {
    Eatables_=Eatables;
  }

  public Vector createGroup(int zielGruppe)
  {
    Vector jlistModelData= new Vector();
    zielGruppe_=zielGruppe;
    int elemGruppe;
    Integer temp;
    String tmpstr;
    for (int i = 0; i < Eatables_.size(); i++)
    {
      Element myElement = (Element) Eatables_.get(i);
      temp = new Integer(myElement.getChild("GruppenID").getText());
      elemGruppe = temp.intValue();

      if (elemGruppe == zielGruppe_)
      {
        tmpstr = i + ") ";
        tmpstr += myElement.getChild("Name").getText() + " ";
        tmpstr += myElement.getChild("Menge").getText();
        if ( (elemGruppe == 3) || (elemGruppe == 7) | (elemGruppe == 10))
        {
          tmpstr += "ml" + " ";
        }
        else
        {
          tmpstr += "gr" + " ";
        }
        tmpstr += myElement.getChild("Kalorien").getText();
        //tmpstr += myElement.getChild("Kalorien").getText() + "kcal";
        jlistModelData.add(tmpstr);
      }

    }
  return jlistModelData;
  }

  public Vector createAll()
  {
    Vector jlistModelData= new Vector();
    int elemGruppe;
    Integer temp;
    String tmpstr;
    for (int i = 0; i < Eatables_.size(); i++)
    {
      Element myElement = (Element) Eatables_.get(i);
      temp = new Integer(myElement.getChild("GruppenID").getText());
      elemGruppe = temp.intValue();

      tmpstr = i + ") ";
      tmpstr += myElement.getChild("Name").getText() + " ";
      tmpstr += myElement.getChild("Menge").getText();
      if ( (elemGruppe == 3) || (elemGruppe == 7) | (elemGruppe == 10)) {
        tmpstr += "ml" + " ";
      }
      else {
        tmpstr += "gr" + " ";
      }
      tmpstr += myElement.getChild("Kalorien").getText();
      tmpstr += " [Gruppe " + elemGruppe + "]";
      //tmpstr += myElement.getChild("Kalorien").getText() + "kcal";
      jlistModelData.add(tmpstr);
    }
  return jlistModelData;
  }

  public Vector createMenue(String menue)
  {
   Vector jlistModelData= new Vector();
   String  menue_=menue;
   int elemGruppe;
   Integer temp;
   String tmpstr;



   for (int i = 0; i < Eatables_.size(); i++)
    {
      if(Eatables_.get(i)!=null)
      {
        Element myElement = (Element) Eatables_.get(i);
        temp = new Integer(myElement.getChild("GruppenID").getText());
        elemGruppe = temp.intValue();

        if(menue_.equals("Fruehstueck"))
        {

          if ( elemGruppe < 4 )
          {
            tmpstr = i + ") ";
            tmpstr += myElement.getChild("Name").getText() + " ";
            tmpstr += myElement.getChild("Menge").getText();
            if ( (elemGruppe == 3) || (elemGruppe == 7) | (elemGruppe == 10))
            {
              tmpstr += "ml" + " ";
            }
            else
            {
              tmpstr += "gr" + " ";
            }
            tmpstr += myElement.getChild("Kalorien").getText();
            jlistModelData.add(tmpstr);
          }
        }
        else if(menue_.equals("Mittagessen"))
        {

          if ( ( elemGruppe > 3) && ( elemGruppe <8) )
          {
            tmpstr = i + ") ";
            tmpstr += myElement.getChild("Name").getText() + " ";
            tmpstr += myElement.getChild("Menge").getText();
            if ( (elemGruppe == 3) || (elemGruppe == 7) | (elemGruppe == 10))
            {
              tmpstr += "ml" + " ";
            }
            else
            {
              tmpstr += "gr" + " ";
            }
            tmpstr += myElement.getChild("Kalorien").getText();
            jlistModelData.add(tmpstr);
          }
        }
        else if(menue_.equals("Abendessen"))
        {

          if ( elemGruppe > 7)
          {
            tmpstr = i + ") ";
            tmpstr += myElement.getChild("Name").getText() + " ";
            tmpstr += myElement.getChild("Menge").getText();
            if ( (elemGruppe == 3) || (elemGruppe == 7) | (elemGruppe == 10))
            {
              tmpstr += "ml" + " ";
            }
            else
            {
              tmpstr += "gr" + " ";
            }
            tmpstr += myElement.getChild("Kalorien").getText();
            jlistModelData.add(tmpstr);
          }
        }
        else
        {
          JOptionPane.showMessageDialog(null,"Kein Menü gewählt!");
        }

      }
    }
   return jlistModelData;
  }


}