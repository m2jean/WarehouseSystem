package productBL;

import java.io.Serializable;

public class CategoryLineItem implements Serializable{
	private String productID;
	private String productName;
	private String productModel;
	
	public CategoryLineItem(String id, String name, String model){
		productID=id;
		productName=name;
		productModel=model;
	}
	
	public boolean equals(CategoryLineItem item){
		if(item.getProductID().equals(productID)&&item.getProductName().equals(productName)
				&&item.getProductModel().equals(productModel)){
			return true;
		}else{
			return false;
		}
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
	
}
