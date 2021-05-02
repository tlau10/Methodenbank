package planer;
import java.util.Vector;
import org.jdom.Element;

/**
 * <p>Title: Diaeplaner Interface </p>
 * <p>Description: Diaeplaner Interface zur GUI fuer Armin</p>
 * <p>Copyright: Matthias Siegert Copyright (c) 2003</p>
 * <p>Company: FH Konstanz</p>
 * @author Matthias Siegert
 * @version 1.0
 */

public interface DiaetplanerInterface
{
  //Matrix erzeugen
  //Solver aufrufen
  //Aus Ergebnis Vector menu zurückgeben
  public Vector executeCalculation(Vector choosenEatables, int calories) throws DiaetplanerException;


//Neues Lebensmittel dem Vector hinzufügen und in XMLFile abspeichern
   public void addEatable(Element newEatable) throws DiaetplanerException; //Übergabeparameter JDOM Element

//Die folgende Methode generiert ein neues Element aus Uebergabeparametern (Name, Lebensmittelgruppe
//, Menge, Kalorien
   public Element newEatable(String name, int gruppenID, String amount, String calories);

//Löscht ein Element aus dem Vector und dem XML-File
   public boolean deleteEatable(Element Eatable) throws DiaetplanerException;

//Löscht das Element mit gegebenem Namen und gegebener Gruppennummer aus der Liste und
  //dem XML-File
  public void deleteEatable(String name, int gruppenNummer) throws DiaetplanerException;

  //Diese Methode liefert nach der Berechnung des Menues die ausgerechneten Kalorien zurück
  public double getMenuCalories();

  //Diese Methode liefert das Array zurueck, das den Zusammenhang zwischen GruppenID und Gruppenname liefert
  public String[] getGruppenName();

}