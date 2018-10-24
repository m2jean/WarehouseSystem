package gui.log;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import VO.LogVO;

public class LogTableModel extends AbstractTableModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5828433006205378303L;
	ArrayList<LogVO> logList;
	String[] head;
	String[][] data;
	
	public LogTableModel(ArrayList<LogVO> list){
		logList = list;
		head = new String[]{"日期","操作人","操作内容"};
		
		
		
		if(list == null || list.size()==0){
			data = new String[0][0];
			return;
		}else{
			data = new String[list.size()][3];
			
			for(int i=0;i<list.size();i++){
				LogVO log = list.get(i);
				data[i][0] = log.getDate();
				data[i][1] = log.getOperator();
				data[i][2] = log.getOpt().toString()+log.getDetail();
			}
		}
	}

	@Override
	public int getRowCount() {
		return data.length;
	}

	@Override
	public int getColumnCount() {
		return head.length;
	}

	@Override
	public String getValueAt(int rowIndex, int columnIndex) {
		return data[data.length-rowIndex-1][columnIndex];
	}
	
	public String getColumnName(int col){
		return head[col];
	}

}
