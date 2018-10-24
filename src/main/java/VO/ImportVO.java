package VO;

import gui.document.vo.ImportExportVO;
import importBL.ImportLineItem;
import java.util.*;
import enums.Status;
import PO.ImportPO;

public class ImportVO implements ImportExportVO {
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
	
	public ImportVO(String date, String id, String customerID, String provider, String warehouse,
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
			for(int i=0;i<productList.size();i++){
				int number=productList.get(i).getNumber();
				double price=productList.get(i).getPrice();
				total+=price*number;
			}
		}else{
			productList=new ArrayList<ImportLineItem>();
		}
		this.status=status;
		this.isHong = isHong;
	}
	
	public ImportVO(String date, String id, String customerID, String provider, String warehouse,
			String operator, ArrayList<ImportLineItem> list, String PS, Status status){
		this(date, id, customerID, provider, warehouse, operator, list, PS, status, false);
	}
	
	public ImportVO(String id){
		this.id=id;
		date=null;
		customerID=null;
		provider=null;
		warehouse=null;
		operator=null;
		total=0;
		PS=null;
		productList=new ArrayList<ImportLineItem>();
		status=Status.DRAFT;
		isHong = false;
	}
	
	public ImportPO toPO(){
		return new ImportPO(date, id, customerID, provider, warehouse, operator, productList, PS, status, isHong);
	}
	
	public ExportVO toExportVO(){
		if(status!=Status.PASS){
			return null;
		}else{
			return new ExportVO(null, null, customerID, provider, warehouse, id, operator, productList, PS, Status.DRAFT, isHong);
		}
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

	public String getCustomer() {
		return provider;
	}

	public void setCustomer(String provider) {
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

	public String getRemarks() {
		return PS;
	}

	public void setRemarks(String pS) {
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

	@Override
	public boolean isHong() {
		return isHong;
	}

	@Override
	public void setHong(boolean b) {
		isHong = b;
	}

	
}
