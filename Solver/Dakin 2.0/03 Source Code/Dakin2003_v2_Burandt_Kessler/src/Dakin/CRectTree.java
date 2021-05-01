package Dakin;
import java.awt.Graphics;
import java.awt.Color;
import java.util.*;
//import java.lang.Math.*;


//Klasse die den Baum TreeDakin graphisch darstellt
class CRectTree
{
	private int iDepth; //Tiefe (Baum)
	private int iX; //XPos (Kasten 1./Wurzel)
	private int iY; //YPos (Kasten 1./Wurzel)
	private int iW; //Breite (Kasten/alle)
	private int iH; //Kasten Hoehe/alle
	private int iLH; //Vertikallinienabstand
	private int iS; //Abstand zwischen Kasten (minimum)
	//private int iMaxWidth; //maximale Breite (deprecated)
	private int iCurrentDepth; //aktuelle Tiefe
	private int iCurrentX; //aktuelle Zeichenpos
	private int iCurrentY; //""          ""
	private int totalW; //Breite total
	private int totalH; //Hoehe total
	private CDakinRect pRoot;  //Wurzel des Baumes (des eigenen)
	private TreeDakin treeDakinRoot; //Wurzel von Henny's baum
	private Graphics myG;

	//standard Konstruktor
	//uebergeben wird die Wurzel eines Baumes vom Typ TreeDakin
	public CRectTree(TreeDakin treeDakin)
	{
		if(treeDakin==null)
			return;

		//Wurzel setzten (zu henny's baum)
		treeDakinRoot=treeDakin;

		//System.out.println("\ninitializing TreeEngine......\n");
		//mal fix gesetzt.......
		iW=50;
		iLH=15;
		iS=50;
		iY=10;

		/*System.out.println("RectWith is fix at: " + iW);
		System.out.println("LineHeight is fix at: " + iLH);
		System.out.println("SpaceBetweenRects is fix at: " + iS);
		System.out.println("YStartPoint is fix at: " + iY);*/

		//hoehe des Rechtecks
		//je nach Anzahl von Variablen
		iH=(treeDakin.getData().size())*17;
		//System.out.println("RectHight set to: " + iH);

		//Tiefe des Baumes errechnen (veraltet)
		/*iDepth=0;
		getDepth();
		System.out.println("TreeDepth set to: " + iDepth);

		//Maximale Breite des Baumes
		iMaxWidth=(int) Math.pow((double) 2,(double) (iDepth-1))*(iW+iS); //maximale Breite
		iMaxWidth-=iS;
		System.out.println("MaximumWith set to: " + iMaxWidth);
		*/

		//erstelle Baum mit Ast Laengen.........ermittle auch Tiefe
		createTree();
		//System.out.println("TreeDepth set to: " + iDepth);

		//Start X Koordinate festlegen........
		//und Weite
		iX=0;
		computeWidthAndStartPoint();
		//System.out.println("XStartPoint set to: " + iX);
		//System.out.println("TotalWidth set to: " + totalW);

		//Hoehe festlegen
		totalH=(iDepth*(iH+(iLH*2)))+iY;

		//aktuelle Zeichenposition..........
		iCurrentY=iY;
		iCurrentX=iX;

		//aktuelle Tiefe
		iCurrentDepth=1;

	}

	//liefert die Breite des Baumes zurueck
	public int getWidth()
	{
		return totalW;
	}

	//liefert die Hoehe des Baumes zurueck
	public int getHeight()
	{
		return totalH;
	}


	//Funktion nur zur internen Nutzung
	//rekursiv, nach links im Baum
	//d fuer Tiefe, um maximale Tiefe zu ermitteln.....
	private int goL(TreeDakin t,int d,CDakinRect tmp)
	{
		d++;
		if(d>iDepth)
			iDepth=d;
		//konten anlegen
		tmp.pLeft=new CDakinRect(tmp);

		if(t.getLeft() == null && t.getRight() == null)
		{
			tmp.pLeft.setBranchWidth((iW/2)+(iS/2));
			return (iW/2)+(iS/2); //Halber Kasten + halber Abstand
		}

		int r=0;
		int l=0;

		if(t.getLeft() != null)
			l=goL(t.getLeft(),d,tmp.pLeft);

		if(t.getRight() != null)
			r=goR(t.getRight(),d,tmp.pLeft);

		int branch=r+l;

		//Knoten setzten (von unten nach oben)
		tmp.pLeft.setBranchWidth(branch);

		return branch;
	}

	//Funktion nur zur internen Nutzung
	//rekursiv, nach rechts im Baum
	//d fuer Tiefe, um maximale Tiefe zu ermitteln.....
	private int goR(TreeDakin t,int d, CDakinRect tmp)
	{
		d++;
		if(d>iDepth)
			iDepth=d;

		//konten anlegen
		tmp.pRight=new CDakinRect(tmp);

		if(t.getLeft() == null && t.getRight()== null)
		{
			tmp.pRight.setBranchWidth((iW/2)+(iS/2));
			return (iW/2)+(iS/2); //Halber Kasten + halber Abstand
		}

		int r=0;
		int l=0;

		if(t.getLeft() != null)
			l=goL(t.getLeft(),d,tmp.pRight);

		if(t.getRight() != null)
			r=goR(t.getRight(),d,tmp.pRight);

		int branch=r+l;

		//Knoten setzten (von unten nach oben)
		tmp.pRight.setBranchWidth(branch);

		return branch;
	}


	//Methode liefert die Tiefe des Baumes
	public int getDepth()
	{
		return iDepth;
	}

	//Methode nur zur internen Verwendung
	//Methode ermittelt die Tiefe und errechnet die Astlaengen
	//die Knoten und Blaetter des Baumes werden erstellt
	private void createTree()
	{

		iDepth=1;
		//die Wurzel
		pRoot=new CDakinRect(null);

		if(treeDakinRoot.getLeft()!=null)
			goL(treeDakinRoot.getLeft(),1,pRoot);
		if(treeDakinRoot.getRight()!=null)
			goR(treeDakinRoot.getRight(),1,pRoot);

	}

	//Methode nur zur internen Verwendung
	//errechnet Startpunkt fuer Wurzel und ermittelt die Weite des Baumes
	private void computeWidthAndStartPoint()
	{
		CDakinRect tmp;
		int tmpW=0;

		tmp=pRoot.pLeft;

		while(tmp != null)
		{
			tmpW+=tmp.getBranchWidth();
			tmp=tmp.pLeft;
		}
		iX=tmpW+(iS/2)+(iW/2);

		tmp=pRoot.pRight;
		tmpW=0;

		while(tmp != null)
		{
			tmpW+=tmp.getBranchWidth();
			tmp=tmp.pRight;
		}
		totalW=iX+tmpW+iW+iS;
	}

	//Methode zeichnet den Baum
	public void drawMe(Graphics g)
	{

          //drawIterativ(treeDakinRoot);

          // So wars ursprünglich
                 myG=g;

		//Wurzel anlegen (grafik-baum)

		pRoot.drawDakinRect(treeDakinRoot.getData(),iX,iY,iW,iH,g,treeDakinRoot.getInfoOpt()); //Head

		//linie nach unten.....
		if(treeDakinRoot.getLeft() != null || treeDakinRoot.getRight() != null)
			myG.drawLine(iCurrentX+iW/2,iCurrentY+iH,iCurrentX+iW/2,iCurrentY+iH+iLH);

		drawLeft(treeDakinRoot.getLeft(),pRoot,0);  //erst nach links runter
		drawRight(treeDakinRoot.getRight(),pRoot,0); //dann nach rechts......

	}
        public void drawIterativ(TreeDakin treeDakin)
        {
          TreeDakin tempTree;
          Vector schlange = new Vector();

          if(treeDakin != null)
            schlange.add(treeDakin);
          while(schlange.size() != 0)
          {
            tempTree = (TreeDakin) schlange.get(0);
            schlange.remove(0);
            System.out.println("Wert: " + tempTree.getData());
            if(tempTree.getLeft() != null)
            {
              schlange.add(tempTree.getLeft());
            }
            if(tempTree.getRight() != null)
            {
              schlange.add(tempTree.getRight());
            }
          }
        }

	//Methode nur zur internen Verwendung
	//zeichnet nach links
	private void drawLeft(TreeDakin treeDakinCurrent,CDakinRect pTmp,int t)
	{
		if(treeDakinCurrent==null)
			return;

		//xOffset/=2;
		t++;

		if(t==iDepth)
		{
			return;
		}

		int xOffset=pTmp.pLeft.getBranchWidth();

		//Linie von nach .........
		int tmpVonX=iCurrentX+iW/2;
		int tmpVonY=iCurrentY+iH+iLH;

		int tmpNachX=(iCurrentX+iW/2)-xOffset;
		int tmpNachY=iCurrentY+iH+iLH;

		//horizontale linie
		myG.drawLine(tmpVonX,tmpVonY,tmpNachX,tmpNachY);
		myG.drawString(treeDakinCurrent.getRestriction(),tmpNachX-5,tmpVonY-2);

		iCurrentY+=(2*iLH+iH); //2* die horizontale Linie + Kastenhoehenach unten
		iCurrentX-=(xOffset); //Breite des Kastens + Abstand


		pTmp.pLeft.drawDakinRect(treeDakinCurrent.getData(),iCurrentX,iCurrentY,iW,iH,myG,treeDakinCurrent.getInfoOpt()); //uebergabe von Parent Rect
		//nach oben
			myG.drawLine(iCurrentX+iW/2,iCurrentY,iCurrentX+iW/2,iCurrentY-iLH);
		//linie nach unten.....
		if((t+1)!=iDepth && (treeDakinCurrent.getLeft() != null || treeDakinCurrent.getRight() != null))
			myG.drawLine(iCurrentX+iW/2,iCurrentY+iH,iCurrentX+iW/2,iCurrentY+iH+iLH);

		drawLeft(treeDakinCurrent.getLeft(),pTmp.pLeft,t);

		drawRight(treeDakinCurrent.getRight(),pTmp.pLeft,t);

		iCurrentY-=(2*iLH+iH); //Positon nach return
		iCurrentX+=(xOffset); //Position nach return
	}

	//Methode nur zur internen Verwendung
	//zeichnet nach rechts
	private void drawRight(TreeDakin treeDakinCurrent,CDakinRect pTmp,int t)
	{
		if(treeDakinCurrent==null)
			return;

		//xOffset/=2;
		t++;
		if(t==iDepth)
		{
			return;
		}

		//Breite des Astes
		int xOffset=pTmp.pRight.getBranchWidth();

		//von ..... nach (Linie)
		int tmpVonX=iCurrentX+iW/2;
		int tmpVonY=iCurrentY+iH+iLH;
		int tmpNachX=(iCurrentX+iW/2)+xOffset;
		int tmpNachY=iCurrentY+iH+iLH;

		//horizontale linie
		myG.drawLine(tmpVonX,tmpVonY,tmpNachX,tmpNachY);
		myG.drawString(treeDakinCurrent.getRestriction(),tmpVonX-5,tmpVonY-2);

		iCurrentY+=(2*iLH+iH); //2* die horizontale Linie nach unten
		iCurrentX+=(xOffset); //Breite des Kastens + Abstand

		pTmp.pRight.drawDakinRect(treeDakinCurrent.getData(),iCurrentX,iCurrentY,iW,iH,myG,treeDakinCurrent.getInfoOpt()); //uebergabe von Parent Rect
		//nach oben
		myG.drawLine(iCurrentX+iW/2,iCurrentY,iCurrentX+iW/2,iCurrentY-iLH);
		//linie nach unten.....
		if((t+1)!=iDepth && (treeDakinCurrent.getLeft() != null || treeDakinCurrent.getRight() != null))
			myG.drawLine(iCurrentX+iW/2,iCurrentY+iH,iCurrentX+iW/2,iCurrentY+iH+iLH);


		drawLeft(treeDakinCurrent.getLeft(),pTmp.pRight,t);

		drawRight(treeDakinCurrent.getRight(),pTmp.pRight,t);

		iCurrentY-=(2*iLH+iH); ////Position nach return
		iCurrentX-=(xOffset); ////Position nach return
	}
}

//Klasse fuer die Baumknoten, bzw Blaetter
class CDakinRect
{
	private int iX; //X-Wert eines Rechtecks
	private int iY; //Y-Wert
	private int iW; //Breite
	private int iH; //Hoehe
	private int branchwidth;  //Astbreite
	private CDakinRect pParent; //Eltern Rect

	public CDakinRect pLeft;   //links
	public CDakinRect pRight;  //rechts


	//Konstruktion, uebegeben wird Elternknoten
	public CDakinRect(CDakinRect p)
	{
		branchwidth=0;
		pParent=p;
		pLeft=null; //noch beim anlegen
		pRight=null; //noch beim anlegen
	}

	//setzten der Astbreite eines Knotens
	public void setBranchWidth(int bw)
	{
		branchwidth=bw;
	}

	//liefert die Astbreite eines Knotens
	public int getBranchWidth()
	{
		return branchwidth;
	}

	//zeichnet das Rechteck eines Knotens oder Blattes
	public void drawDakinRect (Vector v, int x,int y,int w,int h,Graphics g, boolean info) //Konst
	{
		 iX=x;
		 iY=y;
		 iW=w;
		 iH=h; //x,y,width,height


		 if(info==true)
		 	g.setColor(Color.red); //Farbe setzten
		 g.drawRect(iX,iY,iW,iH);

		 g.setColor(Color.black); //Farbe setzten

		 //weiss nicht mehr.......ach doch draw.String pos
		 int tmpX=iX+5;
		 int tmpY=iY+15;
		 int count=0;

		 //alter test scheiss.........
		 /*String[] var;

		 var=new String[3];

		 var[0]="X1:";
		 var[1]="X2:";
		 var[2]="X3:";*/

		 //Zielwert schreiben

		 if((String) v.get(0) != "ERROR")
		 {
		 	g.drawString("Z: " + v.get(0),tmpX,tmpY);

		 	count+=15;

		 	//Vector schreiben.......
		 	for(int i=1;i<v.size();i++)
		 	{

				g.drawString("X" + (i) + ": " + v.get(i),tmpX,tmpY+count);
		 		count+=10;
		 	}
		 }
		 else
		 	g.drawString("i.f.",tmpX,tmpY);

		 //noch redundant vorhanden
		 //g.drawLine((iWidth/2)+x,y+iHeight,(iWidth/2)+x,y+iHeight+iLHeight);  //horizontal 1
		 //y=y+iHeight+iLHeight; //neue y Pos
	}
}
