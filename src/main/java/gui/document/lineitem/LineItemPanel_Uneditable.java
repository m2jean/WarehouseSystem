package gui.document.lineitem;

import gui.inventory.InventoryLineItem;

import java.util.ArrayList;

import javax.swing.JDialog;

public abstract class LineItemPanel_Uneditable extends LineItemPanel {

	private static final long serialVersionUID = 4231245933663748923L;

	public LineItemPanel_Uneditable(JDialog parent,ArrayList<InventoryLineItem> list) {
		super(parent, list);
	}
	
	public LineItemPanel_Uneditable(JDialog parent) {
		super(parent);
	}

	@Override
	protected void setTableModel() {
		tbl_list.setModel(new LineItemTableModel(list,lbl_total));
	}

	public double getTotal(){
		return ((LineItemTableModel)tbl_list.getModel()).getTotal();
	}
}
