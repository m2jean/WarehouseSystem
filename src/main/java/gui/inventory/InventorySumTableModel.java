package gui.inventory;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class InventorySumTableModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3324452709974957470L;
	private ArrayList<InventorySumItem> data;
	private String[] header = {"商品名称","型号","进货数量","进货总价","销售数量","销售总价"};
	
	public InventorySumTableModel(ArrayList<InventorySumItem> list){
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
		InventorySumItem vo = data.get(rowIndex);
		switch(columnIndex){
		case 0:return vo.getName();
		case 1:return vo.getType();
		case 2:return vo.getNumIn();
		case 3:return vo.getSumIn();
		case 4:return vo.getNumOut();
		case 5:return vo.getSumOut();
		}
		return null;
	}
	
}
