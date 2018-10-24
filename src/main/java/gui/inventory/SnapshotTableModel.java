package gui.inventory;

import inventoryBL.SnapshotItem;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import VO.SnapshotVO;

public class SnapshotTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -1578310402261125129L;
	private ArrayList<SnapshotItem> itemList;
	private String[] head;
	
	public SnapshotTableModel(SnapshotVO snapshot){
		itemList = snapshot.getList();
		head = new String[]{"行号", "编号", "商品名", "型号", "库存数量", "库存均价"};
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
		SnapshotItem item = itemList.get(rowIndex);
		
		switch(columnIndex){
		case 0: return String.valueOf(rowIndex+1);
		case 1: return item.getID();
		case 2: return item.getName();
		case 3: return item.getModel();
		case 4: return String.valueOf(item.getNumber());
		case 5: return String.valueOf(item.getAverage());
		default: return null;
		}
	}
	
	public String getColumnName(int col){
		return head[col];
	}

}
