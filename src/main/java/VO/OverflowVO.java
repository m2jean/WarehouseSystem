package VO;

import productBL.ProductLineItem;
import enums.Status;
import gui.document.vo.DocumentVO;
import PO.OverflowPO;

public class OverflowVO implements DocumentVO{
	private String date;
	private String id;
	private ProductLineItem product;
	private String warehouse;
	private int numInSystem;
	private int numInWarehouse;
	private String operator;
	private Status status;
	
	public OverflowVO(String date, String id, ProductLineItem productName, String warehouse, int s, int w, String operator, Status status){
		this.date=date;
		this.id=id;
		this.product=productName;
		this.warehouse=warehouse;
		numInSystem=s;
		numInWarehouse=w;
		this.operator=operator;
		this.status=status;
	}
	
	public OverflowVO(String id){
		date=null;
		this.id=id;
		product=null;
		warehouse=null;
		numInSystem=0;
		numInWarehouse=0;
		operator=null;
		status=Status.DRAFT;
	}
	
	public OverflowPO toPO(){
		return new OverflowPO(date, id, product, warehouse, numInSystem, numInWarehouse, operator, status);
	}
	
	public String getID(){
		return id;
	}
	
	public void setID(String id){
		this.id=id;
	}
	
	public int getNumInSystem() {
		return numInSystem;
	}
	
	public void setNumInSystem(int numInSystem) {
		this.numInSystem = numInSystem;
	}
	
	public int getNumInWarehouse() {
		return numInWarehouse;
	}
	
	public void setNumInWarehouse(int numInWarehouse) {
		this.numInWarehouse = numInWarehouse;
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

	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	public ProductLineItem getProduct() {
		return product;
	}

	public void setProduct(ProductLineItem product) {
		this.product = product;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Override
	public boolean isHong() {
		return false;
	}

	@Override
	public void setHong(boolean b) {
		
	}

	@Override
	public void setRemarks(String s) {
		
	}

	@Override
	public String getRemarks() {
		return "";
	}

}
