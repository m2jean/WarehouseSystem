package gui.progress;

import javax.swing.table.AbstractTableModel;

public class IncomeTableModel extends AbstractTableModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3975253805375517744L;
	String[] head;
	String[] data;

	public IncomeTableModel(String[] data){
		head = new String[]{"销售收入","成本调价收入","进退货差价收入","代金券差价收入","报溢收入",
				"折让前总收入","折让后总收入"};
		this.data=data;
		
		if(data==null){
			data=new String[]{"","","","","","",""};
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
	public String getValueAt(int rowIndex, int columnIndex) {
		return data[columnIndex];
	}
	
	public String getColumnName(int col){
		return head[col];
	}

}
