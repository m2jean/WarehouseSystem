package gui.inventory;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class InventoryDetailTableModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3324452709974957470L;
	private ArrayList<InventoryLineItem> data;
	private String[] header = {"时间","操作","商品名称","型号","单价","数量","总价","单据编号","备注"};
	
	public InventoryDetailTableModel(ArrayList<InventoryLineItem> list){
		data = list;
	}
	
	
	public int getColumnCount() {
		return header.length;
	}

	public int getRowCount() {
		return data.size();
	}
	
	@Override
	public String getColumnName(int col){
		return header[col];
	}
	
	public Class<? extends Object> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }

	public Object getValueAt(int rowIndex, int columnIndex) {
		InventoryLineItem vo = data.get(rowIndex);
		switch(columnIndex){
		case 0:return vo.getDate();
		case 1:return vo.getOpt();
		case 2:return vo.getName();
		case 3:return vo.getType();
		case 4:return vo.getNumber();
		case 5:return vo.getPrice();
		case 6:return vo.getNumber()*vo.getPrice();
		case 7:return vo.getId();
		case 8:return vo.getRemarks();
		}
		return null;
	}
	
}
