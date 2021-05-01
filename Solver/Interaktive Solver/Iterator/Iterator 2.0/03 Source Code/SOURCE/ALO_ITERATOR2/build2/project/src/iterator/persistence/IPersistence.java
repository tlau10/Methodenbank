package iterator.persistence;

import iterator.tableau.TableauDTO;

public interface IPersistence {

	public void save(TableauDTO tableau, String path);
	
	public TableauDTO load(String path);

	
}
