package gui.present;

import gui.promotion.ItemExistException;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.table.AbstractTableModel;

import productBL.ProductItem;

public class PresentTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 4127257868590365185L;
	private String[] head;
	private ArrayList<ProductItem> productList;
	
	public PresentTableModel(ArrayList<ProductItem> list){
		productList = list==null?new ArrayList<ProductItem>():list;
		head = new String[]{"ID", "商品名", "型号", "数量"};
	}

	@Override
	public int getRowCount() {
		return productList.size();
	}

	@Override
	public int getColumnCount() {
		return head.length;
	}

	@Override
	public String getValueAt(int rowIndex, int columnIndex) {
		ProductItem item = productList.get(rowIndex);
		
		switch(columnIndex){
		case 0: return item.getProductID();
		case 1: return item.getProductName();
		case 2: return item.getProductModel();
		case 3: return String.valueOf(item.getNumber());
		default: return null;
		}
	}
	
	public String getColumnName(int col){
		return head[col];
	}
	
	public ArrayList<ProductItem> getProductItem(){
		return productList;
	}
	
	public void addItem(ProductItem item) throws ItemExistException {
		for(ProductItem pi:productList){
			if(pi.getProductID().equals(item.getProductID())){
				throw new ItemExistException();
			}
		}
		
		productList.add(item);
		Collections.sort(productList);
	}
	
	public ProductItem getItem(int row) {
		return productList.get(row);
	}
	
	public void updateItem(ProductItem item) {
		for(int i=0;i<productList.size();i++){
			if(productList.get(i).getProductID().equals(item.getProductID())){
				productList.get(i).setNumber(item.getNumber());
			}
		}
	}
	
	public void deleteItem(int row) {
		productList.remove(row);
	}

}
