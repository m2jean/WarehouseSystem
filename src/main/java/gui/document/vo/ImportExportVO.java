package gui.document.vo;

import importBL.ImportLineItem;

import java.util.ArrayList;

public interface ImportExportVO extends DocumentVO{

	public ArrayList<ImportLineItem> getProductList();
	
	public void setProductList(ArrayList<ImportLineItem> productList);

	public double getTotal();

	public void setTotal(double total);

	public String getCustomer();

	public void setCustomer(String customer);

	public String getWarehouse();

	public void setWarehouse(String warehouse);

	public String getCustomerID();

	public void setCustomerID(String customerID);
	
}
