package PO;

import java.io.Serializable;

import productBL.ProductLineItem;
import enums.Status;
import VO.OverflowVO;

public class OverflowPO implements Serializable, Comparable<OverflowPO> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1997529954215841214L;
	private String date;
	private String id;
	private ProductLineItem product;
	private String warehouse;
	private int numInSystem;
	private int numInWarehouse;
	private String operator;
	private Status status;
	
	public OverflowPO(String date, String id, ProductLineItem product, String warehouse, int s, int w, String operator, Status status){
		this.date=date;
		this.id=id;
		this.warehouse=warehouse;
		this.product=product;
		numInSystem=s;
		numInWarehouse=w;
		this.operator=operator;
		this.status=status;
	}
	
	public OverflowPO(OverflowPO po){
		date=po.getDate();
		id=po.getID();
		warehouse=po.getWarehouse();
		product=po.getProduct();
		numInSystem=po.getNumInSystem();
		numInWarehouse=po.getNumInWarehouse();
		operator=po.getOperator();
		status=po.getStatus();
	}
	
	public OverflowPO(String id){
		this.id=id;
		product=null;
		warehouse=null;
		numInSystem=0;
		numInWarehouse=0;
		operator=null;
		status=Status.DRAFT;
	}
	
	public OverflowVO toVO(){
		return new OverflowVO(date, id, product, warehouse, numInSystem, numInWarehouse, operator, status);
	}
	
	public boolean equals(OverflowPO po){
		if(warehouse.equals(po.getWarehouse())&&
		   product.getID().equals(po.getProduct().getID())&&
		   numInSystem==po.getNumInSystem()&&
		   numInWarehouse==po.getNumInWarehouse()&&
		   operator.equals(po.getOperator())&&
		   status==po.getStatus()){
			return true;
		}else{
			return false;
		}
	}
	
	public String getID(){
		return id;
	}
	
	public void setID(String id){
		this.id=id;
	}
	
	public ProductLineItem getProduct() {
		return product;
	}
	
	public void setProduct(ProductLineItem productName) {
		this.product = productName;
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

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public int compareTo(OverflowPO o) {
		return id.compareTo(o.getID());
	}


}
