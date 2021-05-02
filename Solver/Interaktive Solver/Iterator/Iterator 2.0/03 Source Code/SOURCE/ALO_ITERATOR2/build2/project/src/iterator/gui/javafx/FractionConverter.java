package iterator.gui.javafx;

public class FractionConverter {

	public String convertToFraction(double dValue) {
		String frac = "";

		try {
			if (dValue > 2147483647 && dValue < 1.0 / 2147483647)
				frac = "not possible";
			if (dValue % 1 == 0) // if whole number
				frac = "" + dValue;
			else {
				double dTemp = dValue;
				int iMultiple = 1;
				String strTemp = "" + dValue;
				int i = 0;
				while (strTemp.toCharArray()[i] != '.')
					i++;
				int iDigitsAfterDecimal = strTemp.length() - i - 1;
				while (dTemp * 10 < 2147483647 && iMultiple * 10 < 2147483647 && iDigitsAfterDecimal > 0) {
					dTemp *= 10;
					iMultiple *= 10;
					iDigitsAfterDecimal--;
				}
				int temp1 = (int) Math.round(dTemp);		// numerator
				int temp2 = iMultiple;						// denominator
				frac = ReduceFraction(temp1, temp2);
			}
			return frac;
		} catch (Exception e) {
			return frac = "oops";
		}
	}

	public static String ReduceFraction(int numerator, int denominator) {
		try {
			if (numerator == 0) {
				denominator = 1;
				return numerator + "/" + denominator;
			}

			int iGCD = gcd(numerator, denominator);
			numerator /= iGCD;
			denominator /= iGCD;

			if (denominator < 0) 						// if -ve sign in denominator
			{
														// pass -ve sign to numerator
				numerator *= -1;
				denominator *= -1;
			}		
			
			String temp1 = "" + numerator;
			String temp2 = "" + denominator;
			String frac = temp1 + "/" + temp2;
			return frac;

		} catch (Exception exp) {
			return "Cannot reduce Fraction";
		}
	}

	private static int gcd(int iNo1, int iNo2) {
		// take absolute values
		if (iNo1 < 0)
			iNo1 = -iNo1;
		if (iNo2 < 0)
			iNo2 = -iNo2;

		do {
			if (iNo1 < iNo2) {
				int tmp = iNo1; // swap the two operands
				iNo1 = iNo2;
				iNo2 = tmp;
			}
			iNo1 = iNo1 % iNo2;
		} while (iNo1 != 0);
		return iNo2;
	}

}
