package VO;

import java.util.ArrayList;

import productBL.CategoryLineItem;
import PO.CategoryPO;

public class CategoryVO {
	private String id;
	private String name;
	private String parent;
	private boolean hasProduct;
	private ArrayList<String> childCategory;
	private ArrayList<CategoryLineItem> productList;
	
	public CategoryVO(String id, String name, String parent, boolean hasProduct, ArrayList<String> child, ArrayList<CategoryLineItem> productList){
		this.id=id;
		this.setName(name);
		this.setParent(parent);
		this.hasProduct=hasProduct;
		this.childCategory=child==null?new ArrayList<String>():child;
		this.productList=productList==null?new ArrayList<CategoryLineItem>():productList;
	}
	
	public CategoryVO(String id){
		this.id=id;
		this.name=null;
		this.parent=null;
		this.hasProduct=false;
		this.childCategory=new ArrayList<String>();
		this.productList=new ArrayList<CategoryLineItem>();
	}
	
	public CategoryPO toPO(){
		return new CategoryPO(id, name, parent, hasProduct, childCategory, productList);
	}
	
	public String toString(){
		return name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getParent() {
		return parent;
	}
	
	public void setParent(String parent) {
		this.parent = parent;
	}

	public boolean hasProduct() {
		return hasProduct;
	}

	public void setHasProduct(boolean hasProduct) {
		this.hasProduct = hasProduct;
	}

	public ArrayList<String> getChildCategory() {
		return childCategory;
	}

	public void setChildCategory(ArrayList<String> categoryList) {
		childCategory = categoryList;
	}

	public ArrayList<CategoryLineItem> getProductList() {
		return productList;
	}

	public void setProductIDList(ArrayList<CategoryLineItem> productList) {
		this.productList = productList;
	}

	public String getID() {
		return id;
	}

	public void setID(String id) {
		this.id = id;
	}
}
