package iterator.persistence;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="header")
@XmlAccessorType(XmlAccessType.FIELD)
public class PersistenceHeader {
	
	private String createdProgramm;
	private String createdVersion;
	
	private String createdDate;
	private String createdUser;
	
	
	public PersistenceHeader(){ 
		
		this.createdDate = new SimpleDateFormat("yyyy-MM-dd / HH:mm:ss").format(new Date());
		this.createdUser = System.getProperty("user.name");
		this.createdProgramm = "Iterator II";
		this.createdVersion = PersistenceFunctions.readProperty("iterator.version");
		
	}
	
	
	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public String getCreatedVersion() {
		return createdVersion;
	}

	public void setCreatedVersion(String createdVersion) {
		this.createdVersion = createdVersion;
	}


	public String getCreatedProgramm() {
		return createdProgramm;
	}


	public void setCreatedProgramm(String createdProgramm) {
		this.createdProgramm = createdProgramm;
	}
	
	
	
}
