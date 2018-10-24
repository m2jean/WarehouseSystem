package gui.document.lineitem;

import gui.inventory.InventoryLineItem;

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class LineItemTableModel_Return extends LineItemTableModel_Editable {
	private static final long serialVersionUID = 4119791236808271245L;
	private int[] numberLimit;

	public LineItemTableModel_Return(ArrayList<InventoryLineItem> list, JLabel lbl_total,JDialog parent){
		super(list, lbl_total,parent);
	}
	
	public void setLimit(int[] limit){
		numberLimit = limit;
	}
	
	@Override
	public void setValueAt(Object value,int rowIndex,int columnIndex){
		String str = String.valueOf(value);
		switch(columnIndex){
		case 3:
			int num;
			try{
				num = Integer.parseInt(str);
			}catch(NumberFormatException e){
				JOptionPane.showMessageDialog(parent, "请输入合法整数！");
				return;
			}
			if(num < 0){
				JOptionPane.showMessageDialog(parent, "商品数量不能为负！");
				return;
			}
			else if(num > numberLimit[rowIndex]){
				JOptionPane.showMessageDialog(parent, "退货的数量不能超过进货的数量！");
				return;
			}
			data.get(rowIndex).setNumber(num);
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
}
