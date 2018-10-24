package productBL;

import java.io.Serializable;

public class ProductItem implements Serializable, Comparable<ProductItem>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3277598889837280263L;
	private String productID;
	private String productName;
	private String productModel;
	private int number;
	
	public ProductItem(String id, String name, String model, int number){
		productID=id;
		productName=name;
		productModel=model;
		this.number=number;
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductModel() {
		return productModel;
	}

	public void setProductModel(String productModel) {
		this.productModel = productModel;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int compareTo(ProductItem o) {
		return productID.compareTo(o.getProductID());
	}
	
	
}
