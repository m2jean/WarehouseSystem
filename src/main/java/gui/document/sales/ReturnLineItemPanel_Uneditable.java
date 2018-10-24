package gui.document.sales;

import gui.inventory.InventoryLineItem;

import java.util.ArrayList;

import javax.swing.JDialog;

public class ReturnLineItemPanel_Uneditable extends ReturnLineItemPanel {

	private static final long serialVersionUID = 111982231716753718L;

	public ReturnLineItemPanel_Uneditable(JDialog parent,
			ArrayList<InventoryLineItem> list,
			ArrayList<InventoryLineItem> gifts, int[] limit,double total) {
		super(parent, list, gifts, limit);
		txf_total.setText(String.valueOf(total));
		txf_total.setEditable(false);
	}
	
	@Override
	protected void setTableModel(){
		tbl_list.setModel(new ReturnLineItemTableModel_Uneditable(parent,list,gifts,limit));
	}
}
