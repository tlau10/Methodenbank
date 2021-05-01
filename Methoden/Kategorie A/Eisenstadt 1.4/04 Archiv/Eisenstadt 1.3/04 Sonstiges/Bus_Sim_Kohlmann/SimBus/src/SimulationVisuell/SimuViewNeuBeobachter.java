package SimulationVisuell;

public class SimuViewNeuBeobachter
{
        private int x;
        private int y;
	
        public SimuViewNeuBeobachter()
        {
        	x = 30;
        	y = 30;
        }
        
		public void setzeWerte(int _x, int _y)
		{
			x = _x;
			y = _y;
		}
		
		public int holeX()
		{
			return x;
		}
		
		public int holeY()
		{
			return y;
		}
}