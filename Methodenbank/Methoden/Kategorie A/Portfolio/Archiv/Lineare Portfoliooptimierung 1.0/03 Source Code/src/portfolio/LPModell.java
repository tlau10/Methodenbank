package portfolio;

/**
 * Title:        Programm zur linearen Portfoliooptimierung
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      FH Konstanz
 * @author Wiebke Bang, Frank Mayer
 * @version 1.0
 */
import java.util.*;
public interface LPModell
{
    public Vector erstelleModell(Vector v);
    public void setAnteilsBegrenzung(double b);
    public void setMuMin(double m);
}