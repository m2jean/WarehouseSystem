package gui.inventory;

import importBL.ImportLineItem;

public class InventoryImportItem extends ImportLineItem implements InventoryLineItem {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8806312538498099650L;
	private String date;
	private String opt;

	public InventoryImportItem(ImportLineItem item) {
		super(item.getProductID(),item.getName(),item.getModel(),item.getNumber(),item.getPrice(),item.getPS());
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
		return super.getProductID();
	}

	@Override
	public void setId(String id) {
		super.setProductID(id);
	}

	@Override
	public String getType() {
		return super.getModel();
	}

	@Override
	public void setType(String type) {
		super.setModel(type);
	}

	@Override
	public String getRemarks() {
		return super.getPS();
	}

	@Override
	public void setRemarks(String pS) {
		super.setPS(pS);
	}

	public ImportLineItem getLineItem(){
		return new ImportLineItem(getProductID(),getName(),getModel(),getNumber(),getPrice(),getPS());
	}

	@Override
	public int compareTo(InventoryLineItem o) {
		return this.getProductID().compareTo(o.getProductID());
	}

}
