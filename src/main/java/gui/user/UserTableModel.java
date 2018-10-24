package gui.user;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import VO.UserVO;

public class UserTableModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3324452709974957470L;
	private ArrayList<UserVO> data;
	private String[] header = {"用户名","姓名","权限","联系电话"};
	
	public UserTableModel(ArrayList<UserVO> list){
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
			return data.get(rowIndex).getUsername();
		case 1:
			return data.get(rowIndex).getName();
		case 2:
			return data.get(rowIndex).getPermission().toString();
		case 3:
			return data.get(rowIndex).getTele();
		}
		return null;
	}
	
	public Class<? extends Object> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }
	
	public UserVO getUserVOAt(int rowIndex){
		return data.get(rowIndex);
	}
	
	UserVO findUser(String username){
		for(UserVO user:data){
			if(username.equals(user.getUsername()))
				return user;
		}
		return null;
	}
}
