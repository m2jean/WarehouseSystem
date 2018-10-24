package gui.document.account;

import java.awt.Frame;

import javax.swing.JPanel;

import gui.document.vo.CheckInOutVO;

public class CheckInOutDialog_Uneditable extends CheckInOutDialog {

	private static final long serialVersionUID = -4376628696296613460L;

	public CheckInOutDialog_Uneditable(Frame parent,CheckInOutVO vo) {
		super(parent,vo);
		lbl_customer.setText("客户："+vo.getCustomer());
	}

	@Override
	protected BillItemPanel getItemPanel() {
		return new BillItemPanel_Uneditable(this,vo.getList());
	}

	@Override
	protected void setCustomerComponent() {// do nothing
	}

	@Override
	protected void addButtons(JPanel buttonPane) {
		//do nothing
	}

}
