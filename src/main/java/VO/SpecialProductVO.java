package VO;

import java.util.ArrayList;

import PO.SpecialProductPO;
import productBL.ProductItem;

public class SpecialProductVO implements SaleItemVO{
	private String id;
	private String name;
	private ArrayList<ProductItem> productList;
	private double price;
	private double originalPrice;
	private int number;           //已售出数量
	
	public SpecialProductVO(String id, String name, ArrayList<ProductItem> productList,
			double originalPrice, double price, int number){
		this.id = id;
		this.name = name;
		this.productList = productList;
		this.originalPrice = originalPrice;
		this.price=price;
		this.number = number;
	}
	
	public SpecialProductVO(String id){
		this.id=id;
		name = null;
		productList=new ArrayList<ProductItem>();
		originalPrice = 0;
		price=0;
		number = 0;
	}
	
	public SpecialProductPO toPO(){
		return new SpecialProductPO(id, name, productList, originalPrice, price, number);
	}
	
	public SpecialProductVO clone(){
		ArrayList<ProductItem> list = new ArrayList<ProductItem>();
		for(ProductItem item:productList){
			ProductItem pi = new ProductItem(item.getProductID(), item.getProductName(), 
					item.getProductModel(), item.getNumber());
			list.add(pi);
		}
		
		return new SpecialProductVO(id, name, list, originalPrice, price, number);
	}
	
	public String toString(){
		return id+" "+name;
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

	public int getNumber() {
		return number;
	}
	
	public void setNumber(int number){
		this.number = number;
	}

	public void addNumber(int num){
		number += num;
	}

	@Override
	public String getModel() {
		return "";
	}

	@Override
	public String getPS() {
		return "特价包";
	}
	
	@Override
	public int compareTo(SaleItemVO o) {
		return o.getID().compareTo(id);
	}
	
}
