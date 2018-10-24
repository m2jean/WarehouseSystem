package gui.document.lineitem;

import gui.inventory.InventoryLineItem;

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class LineItemTableModel_Editable extends LineItemTableModel {
	private static final long serialVersionUID = 6342200958327407686L;
	protected JDialog parent;

	public LineItemTableModel_Editable(ArrayList<InventoryLineItem> list,
			JLabel lbl_total,JDialog parent) {
		super(list, lbl_total);
		this.parent = parent;
	}

	@Override
	public boolean isCellEditable(int rowIndex,int columnIndex){
		if(columnIndex == 3 || columnIndex == 4 || columnIndex == 6)
			return true;
		else 
			return false;
	}
	
	@Override
	public void setValueAt(Object value,int rowIndex,int columnIndex){
		String str = String.valueOf(value);
		switch(columnIndex){
		case 3:
			if(!checksetNumber(str,rowIndex))
				return;
			break;
		case 4:
			try{
				data.get(rowIndex).setPrice(Double.parseDouble(str));
			}catch(NumberFormatException e){
				JOptionPane.showMessageDialog(parent, "请输入合法小数！");
				return;
			}
			break;
		case 6:
			data.get(rowIndex).setRemarks(str);
			break;
		}
		updateTotal();
		this.fireTableCellUpdated(rowIndex, columnIndex);
		this.fireTableCellUpdated(rowIndex, 5);
	}

	protected boolean checksetNumber(String value,int rowIndex) {
		int num;
		try{
			num = Integer.parseInt(value);
		}catch(NumberFormatException e){
			JOptionPane.showMessageDialog(parent, "请输入合法整数！");
			return false;
		}
		if(num <= 0){
			JOptionPane.showMessageDialog(parent, "商品数量必须为正！");
			return false;
		}
		data.get(rowIndex).setNumber(num);
		
		return true;
	}
	
}
