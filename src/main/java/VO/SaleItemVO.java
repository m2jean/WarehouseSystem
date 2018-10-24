package VO;

public interface SaleItemVO extends Comparable<SaleItemVO>{
	
	public String getID();
	
	public String getName();
	
	public String getModel();

	public double getPrice();
	
	public String getPS();
	
}
