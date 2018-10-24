package gui.document.lineitem;

import gui.inventory.InventoryLineItem;

import java.util.ArrayList;

import javax.swing.JDialog;

public abstract class LineItemPanel_Return extends LineItemPanel {

	private static final long serialVersionUID = -6375564413132703923L;
	protected int[] limit;

	public LineItemPanel_Return(JDialog parent,ArrayList<InventoryLineItem> list) {
		super(parent, list);
	}

	protected void setTableModel(){
		tbl_list.setModel(new LineItemTableModel_Return(list,lbl_total,parent));
	}
	
	public void setLimit(int[] limit){
		((LineItemTableModel_Return)tbl_list.getModel()).setLimit(limit);
	}
	
	public double getTotal(){
		return ((LineItemTableModel)tbl_list.getModel()).getTotal();
	}
}
