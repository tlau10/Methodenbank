package Dakin;
import java.awt.*;
import java.awt.event.*;

class test
{
	public static void main(String[] args)
	{
		TreeDakin td = new TreeDakin();
		matrix ma = new matrix();
		ma.loadMatrix();

		td.setMatrix(ma);
		td.calculate();
		//td.printSolution();
		return;
	}
}
