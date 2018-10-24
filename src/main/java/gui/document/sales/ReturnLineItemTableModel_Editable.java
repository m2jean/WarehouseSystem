package gui.document.sales;

import gui.inventory.InventoryLineItem;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class ReturnLineItemTableModel_Editable extends
		ReturnLineItemTableModel_Uneditable {

	private static final long serialVersionUID = 1010070243350357260L;

	public ReturnLineItemTableModel_Editable(Component parent,
			ArrayList<InventoryLineItem> list,
			ArrayList<InventoryLineItem> gifts, int[] limit) {
		super(parent, list, gifts, limit);
	}

	@Override
	public boolean isCellEditable(int rowIndex,int columnIndex){
		if(columnIndex == 3 || columnIndex == 4)
			return true;
		else 
			return false;
	}
	
	@Override
	public void setValueAt(Object value,int rowIndex,int columnIndex){
		String str = String.valueOf(value);
		switch(columnIndex){
		case 3:
			int num = (int) getValueAt(rowIndex, columnIndex);
			try{
				num = Integer.parseInt(str);
			}catch(NumberFormatException e){
				JOptionPane.showMessageDialog(parent, "请输入合法整数！");
				return;
			}
			if(num <= 0){
				JOptionPane.showMessageDialog(parent, "商品数量必须为正！");
				return;
			}
			else if(num > limit[rowIndex]){
				JOptionPane.showMessageDialog(parent, "退货的数量不能超过进货的数量！");
				return;
			}
			data.get(rowIndex).setNumber(num);
			break;
		case 4:
			data.get(rowIndex).setRemarks(str);
			break;
		}
		this.fireTableCellUpdated(rowIndex, columnIndex);
	}
	
}
