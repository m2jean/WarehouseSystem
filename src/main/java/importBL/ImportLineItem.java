package importBL;

import java.io.Serializable;

public class ImportLineItem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6509860589082580870L;
	private String productID;
	private String name;
	private String model;
	private int number;
	private double price;
	private String PS;
	
	public ImportLineItem(String id, String name, String model, int number, double price, String PS){
		productID=id;
		this.name=name;
		this.model=model;
		this.number=number;
		this.price=price;
		this.PS=PS;
	}
	
	public String getProductID() {
		return productID;
	}
	
	public void setProductID(String productID) {
		this.productID = productID;
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

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getPS() {
		return PS;
	}

	public void setPS(String pS) {
		PS = pS;
	}
}
