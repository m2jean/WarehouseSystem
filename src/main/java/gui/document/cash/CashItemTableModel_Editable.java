package gui.document.cash;

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import VO.ListOutVO;

public class CashItemTableModel_Editable extends CashItemTableModel {
	private static final long serialVersionUID = 6342200958327407686L;
	protected JDialog parent;

	public CashItemTableModel_Editable(ArrayList<ListOutVO> list,
			JLabel lbl_total,JDialog parent) {
		super(list, lbl_total);
		this.parent = parent;
	}

	@Override
	public boolean isCellEditable(int rowIndex,int columnIndex){
			return true;
	}
	
	@Override
	public void setValueAt(Object value,int rowIndex,int columnIndex){
		String str = String.valueOf(value);
		switch(columnIndex){
		case 0:
			data.get(rowIndex).setName(str);
			break;
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
