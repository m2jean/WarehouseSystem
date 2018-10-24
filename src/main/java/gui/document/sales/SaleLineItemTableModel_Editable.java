package gui.document.sales;

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import gui.document.lineitem.LineItemTableModel_Editable;
import gui.inventory.InventoryLineItem;

public class SaleLineItemTableModel_Editable extends LineItemTableModel_Editable {

	private static final long serialVersionUID = -5311763091593559697L;
	private ArrayList<Integer> numlimit;

	public SaleLineItemTableModel_Editable(ArrayList<InventoryLineItem> list,ArrayList<Integer> numlimit,
			JLabel lbl_total, JDialog parent) {
		super(list, lbl_total, parent);
		this.numlimit = numlimit;
	}
	
	@Override
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
		else if(num > numlimit.get(rowIndex)){
			JOptionPane.showMessageDialog(parent, "库存数量不足！");
		}
		data.get(rowIndex).setNumber(num);

		return true;
	}

}
