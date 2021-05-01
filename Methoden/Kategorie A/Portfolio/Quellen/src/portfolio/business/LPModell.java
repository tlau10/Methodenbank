/**
 * Title:        Programm zur linearen Portfoliooptimierung
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      FH Konstanz
 * @author Wiebke Bang, Frank Mayer
 * @version 1.0
 */
package portfolio.business;

import java.util.*;

public interface LPModell
{
    public static final long M_VALUE = 10000;

    public static final String RENDITE_POSITIVE = "rendiPos";
    public static final String RENDITE_NEGATIVE = "rendiNeg";
    public static final String RENDITE_VERFEHLT = "rendVerf";
    public static final String RENDITE_NEGATIVE_ZEITPUNKT_T = "rendNegT";

    // muss dann auch an den entsprechenden stellen in TypiModell geändert werden
    public static final String ABS_ABW_W = "vPos";
    public static final String ABS_ABW_V = "vNeg";
    public static final String RENDITE_MINIMAL = "minRendT";

    public Vector erstelleModell(Vector v);
    public void setAnteilsBegrenzung(double b);
    public void setMuMin(double m);
}