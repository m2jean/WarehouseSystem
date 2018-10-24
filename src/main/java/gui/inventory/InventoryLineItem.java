package gui.inventory;


public interface InventoryLineItem extends Comparable<InventoryLineItem>{
	public String getProductID();
	
	public void setProductID(String productID);

	public String getName();

	public void setName(String name);
	
	public String getType();

	public void setType(String type);

	public int getNumber();

	public void setNumber(int number) ;

	public double getPrice();

	public void setPrice(double price);

	public String getRemarks();

	public void setRemarks(String pS);

	public String getDate();

	public void setDate(String date);

	public String getOpt();

	public void setOpt(String opt);

	public String getId();

	public void setId(String id);
	
}
