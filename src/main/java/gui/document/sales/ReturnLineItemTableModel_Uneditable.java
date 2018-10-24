package gui.document.sales;

import gui.inventory.InventoryLineItem;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class ReturnLineItemTableModel_Uneditable extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8458828932407616745L;
	protected ArrayList<InventoryLineItem> data;
	private ArrayList<InventoryLineItem> gifts;
	private String[] header = {"商品编号","名称","型号","数量","备注"};
	protected Component parent;
	protected int[] limit;

	public ReturnLineItemTableModel_Uneditable(Component parent,ArrayList<InventoryLineItem> list,ArrayList<InventoryLineItem> gifts,int[] limit){
		data = list;
		this.parent = parent;
		this.limit = limit;
		this.gifts = gifts;
	}
	
	@Override
	public int getColumnCount() {
		return header.length;
	}

	@Override
	public int getRowCount() {
		return data.size()+gifts.size();
	}
	
	@Override
	public String getColumnName(int col){
		return header[col];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex){
		case 0:
			return data.get(rowIndex).getId();
		case 1:
			return data.get(rowIndex).getName();
		case 2:
			return data.get(rowIndex).getType();
		case 3:
			return data.get(rowIndex).getNumber();
		case 4:
			return data.get(rowIndex).getRemarks();
		}
		return null;
	}
	
	@Override
	public Class<? extends Object> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }
	
	
	public InventoryLineItem getItemAt(int rowIndex){
		if(rowIndex < data.size())
			return data.get(rowIndex);
		else
			return gifts.get(rowIndex-data.size());
	}
	
	public ArrayList<InventoryLineItem> getProductList(){
		return data;
	}
	public ArrayList<InventoryLineItem> getGiftList(){
		return gifts;
	}	
	
}
