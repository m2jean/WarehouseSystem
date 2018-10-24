package gui.inventory;

import productBL.ProductItem;

public class InventoryPresentLineItem implements InventoryLineItem {
	
	private String id;
	private String name;
	private String type;
	private int number;

	public InventoryPresentLineItem(ProductItem item) {
		id = item.getProductID();
		name = item.getProductName();
		type = item.getProductModel();
		number = item.getNumber();
	}

	@Override
	public String getProductID() {
		return id;
	}

	@Override
	public void setProductID(String productID) {
		id = productID;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public int getNumber() {
		return number;
	}

	@Override
	public void setNumber(int number) {
		this.number = number;
	}

	@Override
	public double getPrice() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setPrice(double price) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getRemarks() {
		return "赠品";
	}

	@Override
	public void setRemarks(String pS) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDate(String date) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getOpt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOpt(String opt) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public int compareTo(InventoryLineItem o) {
		return this.getProductID().compareTo(o.getProductID());
	}

}
