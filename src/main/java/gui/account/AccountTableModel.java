package gui.account;

import enums.ResultMessage;
import gui.GUIUtility;
import gui.MainFrame;

import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import accountBL.AccountBL;
import accountBLService.AccountBLService;
import VO.AccountVO;

public class AccountTableModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3324452709974957470L;
	private ArrayList<AccountVO> data;
	private String[] header = {"账户编号","账户名称","账户余额","账户描述"};
	private AccountBLService accountbl = new AccountBL();
	
	public AccountTableModel(ArrayList<AccountVO> list){
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
			return GUIUtility.formatDouble(data.get(rowIndex).getBalance());
		case 3:
			return data.get(rowIndex).getDescription();
		}
		return null;
	}

	
	public void setValueAt(Object value,int rowIndex,int columnIndex){
		String str = ((String)value).trim();

		switch(columnIndex){
		case 1:
			if(str.isEmpty()){
				JOptionPane.showMessageDialog(MainFrame.mf, "名称不能为空！");
				return;
			}
			data.get(rowIndex).setName(str);
			break;
		case 3:
			data.get(rowIndex).setDescription(str);;
			break;
		}
		ResultMessage result = accountbl.updAccount(getAccountVOAt(rowIndex));
		switch(result){
		case SUCCESS:
			fireTableCellUpdated(rowIndex,columnIndex);
			break;
		default:
			JOptionPane.showMessageDialog(MainFrame.mf, "修改账户失败！");
			break;
		}
	}
	
	public boolean isCellEditable(int rowIndex,int columnIndex){
		/*if(columnIndex == 1 || columnIndex == 3)
			return true;
		else*/
			return false;
	}
	
	public AccountVO getAccountVOAt(int rowIndex){
		return data.get(rowIndex);
	}
	
	public AccountVO findAccount(String id){
		for(AccountVO vo:data)
			if(vo.getID().equals(id))
				return vo;
		return null;
	}
}
