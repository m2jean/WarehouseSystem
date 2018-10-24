package PO;

import java.io.Serializable;
import java.util.ArrayList;

import VO.SpecialProductVO;
import productBL.ProductItem;

public class SpecialProductPO implements Serializable, Comparable<SpecialProductPO> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1736389644545722473L;
	private String id;
	private String name;
	private ArrayList<ProductItem> productList;
	private double originalPrice;
	private double price;
	private int number;
	
	public SpecialProductPO(String id, String name, ArrayList<ProductItem> productList,
			double originalPrice, double price, int number){
		this.id = id;
		this.name = name;
		this.productList = productList;
		this.originalPrice = originalPrice;
		this.price = price;
		this.number = number;
	}
	
	public SpecialProductPO(SpecialProductPO po){
		this.id = po.getID();
		this.name = po.getName();
		this.productList = po.getProductList();
		this.originalPrice = po.getOriginalPrice();
		this.price = po.getPrice();
		this.number = po.getNumber();
	}
	
	public SpecialProductVO toVO(){
		return new SpecialProductVO(id, name, productList, originalPrice, price, number);
	}

	public ArrayList<ProductItem> getProductList() {
		return productList;
	}

	public void setProductList(ArrayList<ProductItem> productList) {
		this.productList = productList;
	}

	public String getID() {
		return id;
	}

	public void setID(String id) {
		this.id = id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(double originalPrice) {
		this.originalPrice = originalPrice;
	}
	
	public int getNumber(){
		return number;
	}
	
	public void setNumber(int number){
		this.number = number;
	}

	public int compareTo(SpecialProductPO o) {
		return id.compareTo(o.getID());
	}
}
