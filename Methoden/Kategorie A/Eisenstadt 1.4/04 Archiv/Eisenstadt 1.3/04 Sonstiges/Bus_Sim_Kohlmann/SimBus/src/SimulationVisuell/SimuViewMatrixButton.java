package SimulationVisuell;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import Simulation.*;

public class SimuViewMatrixButton extends JButton
{
	private int id;
	private int xKoordinate;
	private int yKoordinate;
	private Image img = null;

	private boolean Haltestelle;

	private ImageIcon darstellung;

	public SimuViewMatrixButton(int _id,int x,int y)
	{
		super();

		id = _id;
		xKoordinate = x;
		yKoordinate = y;

		initButton();
		setLayout(null);
	}

	private void initButton()
	{
		darstellung = new ImageIcon("Bilder/InitBild.jpg");
		setIcon(darstellung);
		setSize(50,50);
	}

	public int holeID()
	{
		return id;
	}

	public int holeX()
	{
		return xKoordinate;
	}

	public int holeY()
	{
		return yKoordinate;
	}

	//GrafikKomponenten
	public boolean holeHaltestelle()
	{
		return Haltestelle;
	}

	public void setzeHaltestelle(boolean h)
	{
		Haltestelle = h;
	}

	public void zeichneKomponente(Planquadrat pQ)
	{
		String bildDatei = "";

		if (pQ.holeAbstandNord() > 0)
		{
			if (bildDatei == "")
			{
				bildDatei = bildDatei + "1";
			}
			else
			{
				bildDatei = bildDatei + "_1";
			}
		}

		if (pQ.holeAbstandNordOst() > 0)
		{
			if (bildDatei == "")
			{
				bildDatei = bildDatei + "2";
			}
			else
			{
				bildDatei = bildDatei + "_2";
			}
		}

		if (pQ.holeAbstandOst() > 0)
		{
			if (bildDatei == "")
			{
				bildDatei = bildDatei + "3";
			}
			else
			{
				bildDatei = bildDatei + "_3";
			}
		}

		if (pQ.holeAbstandSuedOst() > 0)
		{
			if (bildDatei == "")
			{
				bildDatei = bildDatei + "4";
			}
			else
			{
				bildDatei = bildDatei + "_4";
			}
		}

		if (pQ.holeAbstandSued() > 0)
		{
			if (bildDatei == "")
			{
				bildDatei = bildDatei + "5";
			}
			else
			{
				bildDatei = bildDatei + "_5";
			}
		}

		if (pQ.holeAbstandSuedWest() > 0)
		{
			if (bildDatei == "")
			{
				bildDatei = bildDatei + "6";
			}
			else
			{
				bildDatei = bildDatei + "_6";
			}
		}

		if (pQ.holeAbstandWest() > 0)
		{
			if (bildDatei == "")
			{
				bildDatei = bildDatei + "7";
			}
			else
			{
				bildDatei = bildDatei + "_7";
			}
		}

		if (pQ.holeAbstandNordWest() > 0)
		{
			if (bildDatei == "")
			{
				bildDatei = bildDatei + "8";
			}
			else
			{
				bildDatei = bildDatei + "_8";
			}
		}

		if (pQ.holeHaltestelle())
		{
			if (bildDatei != "")
			{
				bildDatei = bildDatei + "_H";
			}
		}

		if (bildDatei != "")
		{
			bildDatei = bildDatei + ".jpg";
			darstellung = new ImageIcon("Bilder/" + bildDatei);
			setIcon(darstellung);
			setSize(50,50);
		}
		else
		{
			darstellung = new ImageIcon("Bilder/InitBild.jpg");
			setIcon(darstellung);
			setSize(50,50);
		}
	}
}
