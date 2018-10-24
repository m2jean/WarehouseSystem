package VO;

import gui.document.vo.ImportExportVO;
import importBL.ImportLineItem;
import java.util.*;
import PO.ExportPO;
import enums.Status;

public class ExportVO implements ImportExportVO{
	private String date;
	private String id;
	private String customerID;
	private String customer;
	private String warehouse;
	private String operator;
	private double total;
	private String PS;
	private String importID;
	private ArrayList<ImportLineItem> productList;
	private Status status;
	private boolean isHong;
	
	public ExportVO(String date, String id, String customerID, String customer,
			String warehouse, String importID, String operator,
			ArrayList<ImportLineItem> list, String PS, Status status, boolean isHong){
		this.date=date;
		this.id=id;
		this.customerID=customerID;
		this.customer=customer;
		this.warehouse=warehouse;
		this.importID=importID;
		this.operator=operator;
		productList=list==null?new ArrayList<ImportLineItem>():list;
		this.PS=PS;
		this.status=status;
		total=0;
		if(list!=null){
			for(int i=0;i<productList.size();i++){
				total+=productList.get(i).getPrice()*productList.get(i).getNumber();
			}
		}
		this.isHong = isHong;
	}
	
	public ExportVO(String date, String id, String customerID, String customer,
			String warehouse, String importID, String operator,
			ArrayList<ImportLineItem> list, String PS, Status status){
		this(date, id, customerID, customer, warehouse, importID, operator, list, PS, status, false);
	}
	
	public ExportVO(ImportVO imp,String operator){
		this("","",imp.getCustomerID(),imp.getCustomer(),imp.getWarehouse(),imp.getID(),operator,imp.getProductList(),"",null,imp.isHong());
	}
	
	public ExportVO(String id){
		date=null;
		this.id=id;
		customerID=null;
		customer=null;
		warehouse=null;
		importID=null;
		operator=null;
		productList=new ArrayList<ImportLineItem>();
		PS=null;
		status=Status.DRAFT;
	}
	
	public ExportPO toPO(){
		return new ExportPO(date, id, customerID, customer, warehouse, importID, operator, productList, total, PS, status, isHong);
	}
		
	public ArrayList<ImportLineItem> getProductList() {
		return productList;
	}
	
	public void setProductList(ArrayList<ImportLineItem> productList) {
		this.productList = productList;
	}

	public String getID() {
		return id;
	}

	public void setID(String id) {
		this.id = id;
	}

	public String getImportID() {
		return importID;
	}

	public void setImportID(String importID) {
		this.importID = importID;
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

	public String getRemarks() {
		return PS;
	}

	public void setRemarks(String pS) {
		PS = pS;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	@Override
	public boolean isHong() {
		return isHong;
	}

	@Override
	public void setHong(boolean b) {
		isHong = b;
	}
	

}
