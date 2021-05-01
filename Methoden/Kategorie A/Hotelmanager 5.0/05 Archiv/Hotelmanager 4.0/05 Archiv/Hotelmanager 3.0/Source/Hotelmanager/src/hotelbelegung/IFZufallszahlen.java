package hotelbelegung;

public interface IFZufallszahlen {
    /**
    * Gibt die nächste Pseudo-Zufallszahl auf dem Intervall [0,1] zurück.
    * @return double : Zufallszahl (double) auf dem Intervall [0,1]
    */
  public double nextDouble();

}