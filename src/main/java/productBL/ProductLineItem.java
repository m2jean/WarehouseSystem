package productBL;

import java.io.Serializable;

public class ProductLineItem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2258307832668126437L;
	private String id;
	private String name;
	private String model;
	
	public ProductLineItem(String id, String name, String model){
		this.id=id;
		this.name=name;
		this.model=model;
	}

	public String getID() {
		return id;
	}

	public void setID(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
}
