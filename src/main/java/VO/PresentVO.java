package VO;

import java.util.*;

import productBL.ProductItem;
import enums.Status;
import PO.PresentPO;
import gui.document.vo.DocumentVO;

public class PresentVO implements DocumentVO{
	private String date;
	private String id;
	private ArrayList<ProductItem> productList;
	private String operator;
	private Status status;
	private boolean isHong;
	
	public PresentVO(String date, String id, ArrayList<ProductItem> pList, String operator, Status status, boolean isHong){
		this.date=date;
		this.id=id;
		productList=pList==null?new ArrayList<ProductItem>():pList;
		this.operator=operator;
		this.status=status;
		this.isHong = isHong;
	}
	
	public PresentVO(String date, String id, ArrayList<ProductItem> pList, String operator, Status status){
		this.date=date;
		this.id=id;
		productList=pList==null?new ArrayList<ProductItem>():pList;
		this.operator=operator;
		this.status=status;
		this.isHong = false;
	}
	
	public PresentVO(String id){
		this.id=id;
		date=null;
		productList=new ArrayList<ProductItem>();
		operator=null;
		status=Status.DRAFT;
		isHong = false;
	}
	
	public PresentPO toPO(){
		return new PresentPO(date, id, productList, operator, status, isHong);
	}
	
	public String getID() {
		return id;
	}

	public void setID(String id) {
		this.id = id;
	}
	
	public Status getStatus(){
		return status;
	}
	
	public void setStatus(Status status){
		this.status=status;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public ArrayList<ProductItem> getProductList() {
		return productList;
	}

	public void setProductListList(ArrayList<ProductItem> productList) {
		this.productList = productList;
	}

	@Override
	public boolean isHong() {
		return isHong;
	}

	@Override
	public void setHong(boolean b) {
		isHong = b;
	}

	@Override
	public void setRemarks(String s) {
		
	}

	@Override
	public String getRemarks() {
		return "";
	}

	
}
