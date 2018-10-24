package gui.document.lineitem;

import gui.GUIUtility;
import gui.inventory.InventoryLineItem;

import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;

public class LineItemTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8458828932407616745L;
	protected ArrayList<InventoryLineItem> data;
	private String[] header = {"商品编号","名称","型号","数量","单价","金额","备注"};
	protected JLabel lbl_total;

	public LineItemTableModel(ArrayList<InventoryLineItem> list,JLabel lbl_total){
		data = list;
		this.lbl_total = lbl_total;
		updateTotal();
	}
	
	@Override
	public int getColumnCount() {
		return header.length;
	}

	@Override
	public int getRowCount() {
		return data.size();
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
			return String.valueOf(data.get(rowIndex).getNumber());
		case 4:
			return GUIUtility.formatDouble(data.get(rowIndex).getPrice());
		case 5:
			return GUIUtility.formatDouble(data.get(rowIndex).getNumber()*data.get(rowIndex).getPrice());
		case 6:
			return data.get(rowIndex).getRemarks();
		}
		return null;
	}
	
	@Override
	public Class<? extends Object> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }
	
	
	public InventoryLineItem getItemAt(int rowIndex){
		return data.get(rowIndex);
	}
	
	protected void updateTotal(){
		double total = getTotal();
		lbl_total.setText("总计："+GUIUtility.formatDouble(total));
	}
	
	protected double getTotal(){
		double total = 0;
		for(InventoryLineItem ilt:data)
			total += ilt.getNumber()*ilt.getPrice();
		return total;
	}
}
