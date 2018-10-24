package gui.document.cash;

import java.util.ArrayList;

import javax.swing.JDialog;

import VO.ListOutVO;

public class CashItemPanel_Uneditable extends CashItemPanel {

	private static final long serialVersionUID = 4231245933663748923L;

	public CashItemPanel_Uneditable(JDialog parent,ArrayList<ListOutVO> list) {
		super(parent, list);
	}
	
	public CashItemPanel_Uneditable(JDialog parent) {
		super(parent);
	}

	@Override
	protected void setTableModel() {
		tbl_list.setModel(new CashItemTableModel(list,lbl_total));
	}

	public double getTotal(){
		return ((CashItemTableModel)tbl_list.getModel()).getTotal();
	}
}
