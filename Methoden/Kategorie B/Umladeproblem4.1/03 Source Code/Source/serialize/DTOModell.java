package serialize;

import java.util.Arrays;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


//Klasse wurde von den Studierenden erstellt
@XmlRootElement(name = "umladeproblem")
@XmlAccessorType(XmlAccessType.FIELD)
public class DTOModell {

	
	
    double[] anbieterArray;
    double[] nachfragerArray;
    double[][] kostenmatrix;
    
    public DTOModell(){
    	
    }
    
	public DTOModell(double[] anbieterArray, double[] nachfragerArray, double[][] kostenmatrix) {
		super();
		this.anbieterArray = anbieterArray;
		this.nachfragerArray = nachfragerArray;
		this.kostenmatrix = kostenmatrix;
	}

	public double[] getAnbieterArray() {
		return anbieterArray;
	}

	public void setAnbieterArray(double[] anbieterArray) {
		this.anbieterArray = anbieterArray;
	}

	public double[] getNachfragerArray() {
		return nachfragerArray;
	}

	public void setNachfragerArray(double[] nachfragerArray) {
		this.nachfragerArray = nachfragerArray;
	}

	public double[][] getKostenmatrix() {
		return kostenmatrix;
	}

	public void setKostenmatrix(double[][] kostenmatrix) {
		this.kostenmatrix = kostenmatrix;
	}

	@Override
	public String toString() {
		return "DTOModell [anbieterArray=" + Arrays.toString(anbieterArray)
				+ ", nachfragerArray=" + Arrays.toString(nachfragerArray)
				+ ", kostenmatrix=" + Arrays.toString(kostenmatrix) + "]";
	}

	
	

}
