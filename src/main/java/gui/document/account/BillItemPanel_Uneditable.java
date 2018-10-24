package gui.document.account;

import java.util.ArrayList;

import javax.swing.JDialog;

import VO.ListInVO;

public class BillItemPanel_Uneditable extends BillItemPanel {

	private static final long serialVersionUID = 4231245933663748923L;

	public BillItemPanel_Uneditable(JDialog parent,ArrayList<ListInVO> list) {
		super(parent, list);
	}
	
	public BillItemPanel_Uneditable(JDialog parent) {
		super(parent);
	}

	@Override
	protected void setTableModel() {
		tbl_list.setModel(new BillItemTableModel(list,lbl_total));
	}

	public double getTotal(){
		return ((BillItemTableModel)tbl_list.getModel()).getTotal();
	}
}
