package gui.customer;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import VO.CustomerVO;

public class CustomerTableModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3324452709974957470L;
	private ArrayList<CustomerVO> data;
	private String[] header = {"编号","名称","分类","级别"/*,"电话","地址","邮编","电子邮箱"*/,"业务员","应收额度","应收","应付"};
	
	public CustomerTableModel(ArrayList<CustomerVO> list){
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

	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex){
		case 0:
			return data.get(rowIndex).getID();
		case 1:
			return data.get(rowIndex).getName();
		case 2:
			return data.get(rowIndex).getType();
		case 3:
			return data.get(rowIndex).getViplvl();
		case 4:
			return data.get(rowIndex).getSalesman();
		case 5:
			return data.get(rowIndex).getPayableLine();
		case 6:
			return data.get(rowIndex).getPayable();
		case 7:
			return data.get(rowIndex).getReceivable();
		}
		return null;
	}
	
	public Class<? extends Object> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }
	
	public CustomerVO getCustomerVOAt(int rowIndex){
		return data.get(rowIndex);
	}
}
