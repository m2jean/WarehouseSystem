package gui.document.cash;

import gui.GUIUtility;

import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;

import VO.ListOutVO;

public class CashItemTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8458828932407616745L;
	protected ArrayList<ListOutVO> data;
	private String[] header = {"条目名称","金额","备注"};
	protected JLabel lbl_total;

	public CashItemTableModel(ArrayList<ListOutVO> list,JLabel lbl_total){
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
			return data.get(rowIndex).getName();
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
	
	
	public ListOutVO getItemAt(int rowIndex){
		return data.get(rowIndex);
	}
	
	protected void updateTotal(){
		double total = getTotal();
		lbl_total.setText("总计："+GUIUtility.formatDouble(total));
	}
	
	protected double getTotal(){
		double total = 0;
		for(ListOutVO ilt:data)
			total += ilt.getMoney();
		return total;
	}
}
