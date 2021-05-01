package planer;
//import java.math.

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class Energiebedarf
{
  public static int energiebedarf(double gewicht, double groesse, double alter, double taetig, String geschl)
  {
    double gu; // Grundumsatz
    double lu; // Leistungsumsatz
    double eb; // Energiebedarf 0,5=leichte Tätigkeit  0,75= mittelschwer  1= schwer
    double bmi = bmi(gewicht, groesse);

    if ( (bmi > 25) || (bmi < 19))
      gewicht = groesse - 100;

    gu = grundumsatz(gewicht, groesse, alter, geschl);
    lu = gu * taetig;
    eb = gu + lu;
    eb /= 4.18; // KJ in Kcal umrechnen
    return (int) Math.round(eb);

  }


  public static double bmi(double gewicht, double groesse)
  {
    return gewicht / (Math.pow( (groesse / 100), 2));
  }

  private static double grundumsatz(double gewicht, double groesse, double alter, String geschl)
  {
        double gu; // Grundumsatz

        if(geschl.equals("m"))
                gu = 297.9*Math.pow(gewicht,0.75)*(1+0.004*(30-alter)+0.010*(groesse/Math.pow(gewicht,0.333333333)-43.4));
        else
                gu = 275.3*Math.pow(gewicht,0.75)*(1+0.004*(30-alter)+0.018*(groesse/Math.pow(gewicht,0.333333333)-42.1));

        return gu;
}



}