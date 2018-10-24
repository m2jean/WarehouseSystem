package gui.document.account;

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import VO.ListInVO;

public class BillItemTableModel_Editable extends BillItemTableModel {
	private static final long serialVersionUID = 6342200958327407686L;
	protected JDialog parent;

	public BillItemTableModel_Editable(ArrayList<ListInVO> list,
			JLabel lbl_total,JDialog parent) {
		super(list, lbl_total);
		this.parent = parent;
	}

	@Override
	public boolean isCellEditable(int rowIndex,int columnIndex){
		if(columnIndex == 1 || columnIndex == 2)
			return true;
		else 
			return false;
	}
	
	@Override
	public void setValueAt(Object value,int rowIndex,int columnIndex){
		String str = String.valueOf(value);
		switch(columnIndex){
		case 1:
			try{
				data.get(rowIndex).setMoney(Double.parseDouble(str));
			}catch(NumberFormatException e){
				JOptionPane.showMessageDialog(parent, "请输入合法小数！");
				return;
			}
			break;
		case 2:
			data.get(rowIndex).setRemark(str);
			break;
		}
		updateTotal();
		this.fireTableCellUpdated(rowIndex, columnIndex);
	}
	
}
