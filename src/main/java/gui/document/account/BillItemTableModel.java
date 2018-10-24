package gui.document.account;

import gui.GUIUtility;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;

import VO.ListInVO;

public class BillItemTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8458828932407616745L;
	protected ArrayList<ListInVO> data;
	private String[] header = {"银行账户","转账金额","备注"};
	protected JLabel lbl_total;

	public BillItemTableModel(ArrayList<ListInVO> list,JLabel lbl_total){
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
			return data.get(rowIndex).getAccount();
		case 1:
			return GUIUtility.formatDouble(data.get(rowIndex).getMoney());
		case 2:
			return data.get(rowIndex).getRemark();
		}
		return null;
	}
	
	@Override
	public Class<? extends Object> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }
	
	
	public ListInVO getItemAt(int rowIndex){
		return data.get(rowIndex);
	}
	
	protected void updateTotal(){
		double total = getTotal();
		lbl_total.setText("总计："+GUIUtility.formatDouble(total));
	}
	
	protected double getTotal(){
		double total = 0;
		for(ListInVO ilt:data)
			total += ilt.getMoney();
		return total;
	}
}
