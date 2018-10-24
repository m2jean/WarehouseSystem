package PO;

import java.io.Serializable;
import java.util.ArrayList;

import productBL.ProductItem;
import enums.Status;
import VO.PresentVO;

public class PresentPO implements Serializable, Comparable<PresentPO> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7723457833331751483L;
	private String date;
	private String id;
	private ArrayList<ProductItem> productList;
	private String operator;
	private Status status;
	private boolean isHong;
	
	public PresentPO(String date, String id, ArrayList<ProductItem> pList, String operator, Status status, boolean isHong){
		this.date=date;
		this.id=id;
		productList=pList==null?new ArrayList<ProductItem>():pList;
		this.operator=operator;
		this.status=status;
		this.isHong = isHong;
	}
	
	public PresentPO(String date, String id, ArrayList<ProductItem> pList, String operator, Status status){
		this.date=date;
		this.id=id;
		productList=pList==null?new ArrayList<ProductItem>():pList;
		this.operator=operator;
		this.status=status;
		this.isHong = false;
	}
	
	public PresentPO(String id){
		this.id=id;
		date=null;
		productList=new ArrayList<ProductItem>();
		operator=null;
		status=Status.DRAFT;
	}
	
	public PresentPO(PresentPO po){
		date=po.getDate();
		id=po.getID();
		productList=po.getProductList();
		operator=po.getOperator();
		status=po.getStatus();
		isHong = po.isHong();
	}
	
	public PresentVO toVO(){
		return new PresentVO(date, id, productList, operator, status, isHong);
	}
	
	public boolean equals(PresentPO po){
		if(po.getProductList().size()!=productList.size()){
			return false;
		}
		
		for(int i=0;i<productList.size();i++){
			String id1=po.getProductList().get(i).getProductID();
			String id2=productList.get(i).getProductID();
			int number1=po.getProductList().get(i).getNumber();
			int number2=productList.get(i).getNumber();
			
			if(!id1.equals(id2)||number1!=number2){
				return false;
			}
		}
		
		return true;
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

	public void setProductList(ArrayList<ProductItem> presentList) {
		this.productList = presentList;
	}

	public int compareTo(PresentPO o) {
		return id.compareTo(o.getID());
	}

	public boolean isHong() {
		return isHong;
	}

	public void setHong(boolean isHong) {
		this.isHong = isHong;
	}
}
