package gui.inventory;

import saleBL.SaleLineItem;

public class InventorySaleItem extends SaleLineItem implements
		InventoryLineItem{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3721361148077020633L;
	private String date;
	private String opt;
	
	public InventorySaleItem(SaleLineItem item){
		super(item.getID(), item.getName(),item.getSize(),item.getNumber(),item.getPrice(),item.getMoney(), item.getRemark());
		
	}

	@Override
	public String getProductID() {
		return super.getID();
	}

	@Override
	public void setProductID(String productID) {
		super.setID(productID);
	}

	@Override
	public String getType() {
		return super.getSize();
	}

	@Override
	public void setType(String type) {
		super.setSize(type);
	}

	@Override
	public String getRemarks() {
		return super.getRemark();
	}

	@Override
	public void setRemarks(String pS) {
		super.setRemark(pS);
	}

	@Override
	public String getDate() {
		return date;
	}

	@Override
	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String getOpt() {
		return opt;
	}

	@Override
	public void setOpt(String opt) {
		this.opt = opt;
	}

	@Override
	public String getId() {
		return super.getID();
	}

	@Override
	public void setId(String id) {
		super.setID(id);
	}

	public SaleLineItem getLineItem() {
		return new SaleLineItem(getID(),getName(),getSize(),getNumber(),getPrice(),getMoney(),getRemark());
	}
	
	@Override
	public int compareTo(InventoryLineItem o) {
		return this.getProductID().compareTo(o.getProductID());
	}
}
