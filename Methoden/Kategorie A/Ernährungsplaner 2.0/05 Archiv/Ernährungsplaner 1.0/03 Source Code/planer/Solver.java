package planer;
public interface Solver
{
    public double[] calcModel(Matrix lpModell, Matrix grenzen);
    public double[] calcModel(Matrix lpModell);

    public String getTempVerzeichnis();
    public void setTempVerzeichnis(String tempVerzeichnis);

    public String getLpSolvVerzeichnis();
    public void setLpSolvVerzeichnis(String lpSolvVerzeichnis);
}

