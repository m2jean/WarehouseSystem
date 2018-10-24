package PO;

import java.io.Serializable;
import java.util.ArrayList;

import productBL.CategoryLineItem;
import VO.CategoryVO;

public class CategoryPO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2024625707488303954L;
	private String id;
	private String name;
	private String parent;
	private boolean hasProduct;
	private ArrayList<String> childCategory;
	private ArrayList<CategoryLineItem> productList;
	
	public CategoryPO(String id, String name, String parent, boolean hasProduct, ArrayList<String> child, ArrayList<CategoryLineItem> productList){
		this.id=id;
		this.setName(name);
		this.setParent(parent);
		this.hasProduct=hasProduct;
		this.childCategory=child;
		this.productList=productList;
		if(child==null){
			childCategory=new ArrayList<String>();
		}
		if(productList==null){
			this.productList=new ArrayList<CategoryLineItem>();
		}
	}
	
	public CategoryPO(String id){
		this.id=id;
		this.name=null;
		this.parent=null;
		this.hasProduct=false;
		this.childCategory=new ArrayList<String>();
		this.productList=new ArrayList<CategoryLineItem>();
	}
	
	public CategoryPO(CategoryPO po){
		id=po.getID();
		name=po.getName();
		parent=po.getParent();
		hasProduct=po.hasProduct();
		this.childCategory=po.getChildCategory();
		this.productList=po.getProductList();
	}
	
	public CategoryVO toVO(){
		return new CategoryVO(id, name, parent, hasProduct, childCategory, productList);
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
