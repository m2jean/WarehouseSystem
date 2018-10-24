package PO;

import importBL.ImportLineItem;

import java.io.Serializable;
import java.util.ArrayList;

import enums.Status;
import VO.ImportVO;

public class ImportPO implements Serializable, Comparable<ImportPO> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3130666369500963128L;
	private String date;
	private String customerID;
	private String provider;
	private String warehouse;
	private String operator;
	private double total;
	private String PS;
	private String id;
	private ArrayList<ImportLineItem> productList;
	private Status status;
	private boolean isHong;
	
	public ImportPO(String date, String id, String customerID, String provider, String warehouse,
			String operator, ArrayList<ImportLineItem> list, String PS, Status status, boolean isHong){
		this.date=date;
		this.id=id;
		this.customerID=customerID;
		this.provider=provider;
		this.warehouse=warehouse;
		this.operator=operator;
		productList=list;
		this.PS=PS;
		total=0;
		if(list!=null){
			for(int i=0;i<list.size();i++){
				total+=productList.get(i).getPrice()*productList.get(i).getNumber();
			}
		}
		this.status=status;
		this.isHong = isHong;
	}
	
	public ImportPO(ImportPO po){
		date=po.getDate();
		id=po.getID();
		customerID=po.getCustomerID();
		provider=po.getProvider();
		warehouse=po.getWarehouse();
		operator=po.getOperator();
		productList=po.getProductList();
		PS=po.getPS();
		status=po.getStatus();
		total=po.getTotal();
		isHong = po.isHong;
	}
	
	public ImportPO(String id){
		this.id=id;
		date=null;
		customerID=null;
		provider=null;
		warehouse=null;
		operator=null;
		productList=null;
		PS=null;
		status=null;
		total=0;
	}
	
	public ImportVO toVO(){
		return new ImportVO(date, id, customerID, provider, warehouse, operator, productList, PS, status, isHong);
	}

	public String getID() {
		return id;
	}

	public void setID(String id) {
		this.id = id;
	}

	public ArrayList<ImportLineItem> getProductList() {
		return productList;
	}

	public void setProductList(ArrayList<ImportLineItem> productList) {
		this.productList = productList;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String getPS() {
		return PS;
	}

	public void setPS(String pS) {
		PS = pS;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public int compareTo(ImportPO o) {
		return id.compareTo(o.getID());
	}

}
