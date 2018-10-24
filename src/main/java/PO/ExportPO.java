package PO;

import importBL.ImportLineItem;

import java.io.Serializable;
import java.util.ArrayList;

import VO.ExportVO;
import enums.Status;

public class ExportPO implements Serializable, Comparable<ExportPO> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -214018030769370111L;
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
	
	public ExportPO(String date, String id, String customerID, String customer, String warehouse, String importID, String operator, ArrayList<ImportLineItem> list, double total, String PS, Status status, boolean isHong){
		this.date=date;
		this.id=id;
		this.customerID=customerID;
		this.customer=customer;
		this.warehouse=warehouse;
		this.importID=importID;
		this.operator=operator;
		productList=list;
		this.total=total;
		this.PS=PS;
		this.status=status;
		this.isHong = isHong;
	}
	
	public ExportPO(ExportPO po){
		date=po.getDate();
		id=po.getID();
		customerID=po.getCustomerID();
		customer=po.getCustomer();
		warehouse=po.getWarehouse();
		importID=po.getImportID();
		operator=po.getOperator();
		productList=po.getProductList();
		PS=po.getPS();
		status=po.getStatus();
		total=po.getTotal();
		isHong = po.isHong;
	}
	
	public ExportPO(String id){
		this.date=null;
		this.id=id;
		importID=null;
		operator=null;
		productList=null;
		PS=null;
		status=Status.DRAFT;
	}
	
	public ExportVO toVO(){
		return new ExportVO(date, id, customerID, customer, warehouse, importID, operator, productList, PS, status, isHong);
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

	public String getPS() {
		return PS;
	}

	public void setPS(String pS) {
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

	public int compareTo(ExportPO o) {
		return id.compareTo(o.getID());
	}
	
	public void setHong(boolean b){
		isHong = b;
	}
	
	public boolean isHong(){
		return isHong;
	}
	
}
