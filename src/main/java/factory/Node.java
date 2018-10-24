package factory;

import java.io.Serializable;
import java.util.ArrayList;

import PO.CategoryPO;

public class Node implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2073058394910218152L;
	private CategoryPO category;
	ArrayList<Node> child;
	
	public Node(){
		category=null;
		child=new ArrayList<Node>();
	}
	
	public Node(CategoryPO vo){
		category=vo;
		child=new ArrayList<Node>();
	}
	
	public CategoryPO getCategory(){
		return category;
	}
	
	public void update(CategoryPO po){
		category.setName(po.getName());
	}
}
