package iterator.persistence;

import iterator.tableau.TableauDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="iterator")
@XmlAccessorType(XmlAccessType.FIELD)
public class PersistenceFileXmlRoot {

	private PersistenceHeader header = new PersistenceHeader();
	private TableauDTO tableau =null;
	
	
	
	public PersistenceFileXmlRoot() {
		super();
	}
	
	public PersistenceFileXmlRoot(TableauDTO tableau) {
		super();
		this.tableau = tableau;
	}
	public PersistenceHeader getHeader() {
		return header;
	}
	public void setHeader(PersistenceHeader header) {
		this.header = header;
	}
	public TableauDTO getTableau() {
		return tableau;
	}
	public void setTableau(TableauDTO tableau) {
		this.tableau = tableau;
	}

	
	
}
