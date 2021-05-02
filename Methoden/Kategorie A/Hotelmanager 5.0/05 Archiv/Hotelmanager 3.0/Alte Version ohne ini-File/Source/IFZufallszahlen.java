package hotelbelegung;

public interface IFZufallszahlen {
    /**
    * Gibt die n�chste Pseudo-Zufallszahl auf dem Intervall [0,1] zur�ck.
    * @return double : Zufallszahl (double) auf dem Intervall [0,1]
    */
  public double nextDouble();

}