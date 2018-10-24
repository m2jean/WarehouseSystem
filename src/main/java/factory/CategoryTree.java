package factory;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

import enums.ResultMessage;
import productBL.CategoryLineItem;
import PO.CategoryPO;
import PO.ProductPO;

public class CategoryTree implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2291776973849602042L;
	Node root;
	
	public CategoryTree(){
		root=new Node();
	}
	
	public void add(CategoryPO po) throws Info{
		if(po.getParent()==null){
			root.child.add(new Node(po));
			throw new Info(true);
		}else{
			add(root, po);
		}
	}
	
	private void add(Node node, CategoryPO po) throws Info{
		if(node==null){
			return ;
		}
		
		CategoryPO category=node.getCategory();
		if(category!=null&&category.getName().equals(po.getParent())){
			if(category.hasProduct()){
				throw new Info(false);
			}
			node.child.add(new Node(po));
			node.getCategory().getChildCategory().add(po.getName());
			throw new Info(true);
		}
		
		ArrayList<Node> list=node.child;
		if(list.size()==0){
			return;
		}
		for(Node n:list){
			add(n, po);
		}
	}
	
	public void delete(CategoryPO po) throws Info{
		delete(root, po);
	}
	
	private void delete(Node node, CategoryPO po) throws Info{
		if(node.child.size()==0){
			return;
		}
		
		for(Node n:node.child){
			if(n.getCategory().getID().equals(po.getID())){
				node.child.remove(n);
				throw new Info(true);
			}else{
				delete(n, po);
			}
		}
	}
	
	public void update(CategoryPO po) throws Info{
		CategoryPO category=get(po);
		if(category!=null){
			category.setName(po.getName());
			throw new Info(true);
		}
	}
	
	public CategoryPO get(CategoryPO po){
		return get(root, po);
	}
	
	private CategoryPO get(Node node, CategoryPO po){
		if(node.child.size()==0){
			return null;
		}
		
		for(Node n:node.child){
			if(n.getCategory().getID().equals(po.getID())){
				return n.getCategory();
			}else{
				CategoryPO c=get(n, po);
				if(c!=null){
					return c;
				}
			}
		}
		
		return null;
	}
	
	public CategoryPO getByName(String name){
		return get(root, name);
	}
	
	private CategoryPO get(Node node, String name){
		if(node.child.size()==0){
			return null;
		}
		
		for(Node n:node.child){
			if(n.getCategory().getName().equals(name)){
				return n.getCategory();
			}else{
				CategoryPO c=get(n, name);
				if(c!=null){
					return c;
				}
			}
		}
		
		return null;
	}
	
	public ResultMessage addProduct(ProductPO po){
		CategoryPO c=new CategoryPO(po.getParentID());
		CategoryPO category=get(c);
		if(c.getChildCategory() != null &&c.getChildCategory().size() != 0){
			return ResultMessage.CANNOT_ADD;
		}
		
		CategoryLineItem item=new CategoryLineItem(po.getID(), po.getName(), po.getModel());
		category.getProductList().add(item);
		category.setHasProduct(true);
		return ResultMessage.SUCCESS;
	}
	
	public void deleteProduct(ProductPO po){
		CategoryPO c=new CategoryPO(po.getParentID());
		CategoryPO category=get(c);
		CategoryLineItem item=new CategoryLineItem(po.getID(), po.getName(), po.getModel());
		for(CategoryLineItem it:category.getProductList()){
			if(it.equals(item)){
				category.getProductList().remove(it);
				break;
			}
		}
		if(category.getProductList().size()==0){
			category.setHasProduct(false);
		}
	}
	
	public void toJTreeNode(DefaultMutableTreeNode NodeRoot){
		addNode(NodeRoot, root);
	}
	
	private void addNode(DefaultMutableTreeNode treenode, Node node){
		if(node==null||node.child.size()==0){
			return;
		}else{
			for(Node n:node.child){
				DefaultMutableTreeNode tn=new DefaultMutableTreeNode(n.getCategory().toVO());
				addNode(tn, n);
				treenode.add(tn);
			}
		}
	}
}
