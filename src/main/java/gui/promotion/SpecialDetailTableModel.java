package gui.promotion;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import productBL.ProductItem;

public class SpecialDetailTableModel extends AbstractTableModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6290838687176655414L;
	String[] head;
	ArrayList<ProductItem> itemList;
	
	public SpecialDetailTableModel(ArrayList<ProductItem> list){
		head = new String[]{"ID", "名称", "型号", "数量"};
		itemList = list==null?new ArrayList<ProductItem>():list;
	}

	@Override
	public int getRowCount() {
		return itemList.size();
	}

	@Override
	public int getColumnCount() {
		return head.length;
	}

	@Override
	public String getValueAt(int rowIndex, int columnIndex) {
		ProductItem item = itemList.get(rowIndex);
		
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
	
	public ArrayList<ProductItem> getItemList(){
		return itemList;
	}
	
	public void addItem(ProductItem item) throws ItemExistException {
		for(ProductItem pi:itemList){
			if(pi.getProductID().equals(item.getProductID())){
				throw new ItemExistException();
			}
		}
		
		itemList.add(item);
	}
	
	public void updateItem(ProductItem item){
		for(int i=0;i<itemList.size();i++){
			if(itemList.get(i).getProductID().equals(item.getProductID())){
				itemList.get(i).setNumber(item.getNumber());
			}
		}
	}
	
	public void deleteItem(int row){
		itemList.remove(row);
	}
	
	public ProductItem getItem(int row){
		return itemList.get(row);
	}

}
