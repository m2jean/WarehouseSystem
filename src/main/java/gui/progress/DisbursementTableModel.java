package gui.progress;

import javax.swing.table.AbstractTableModel;

public class DisbursementTableModel extends AbstractTableModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5739313046030962358L;
	String[] head;
	String[] data;
	
	public DisbursementTableModel(String[] data){
		head = new String[]{"销售成本","赠品支出","报损支出","总支出","总折让"};
		this.data=data;
		
		if(data==null){
			data = new String[]{"","","","",""};
		}
	}
	
	@Override
	public int getRowCount() {
		return 1;
	}

	@Override
	public int getColumnCount() {
		return head.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data[columnIndex];
	}
	
	public String getColumnName(int col){
		return head[col];
	}

}
