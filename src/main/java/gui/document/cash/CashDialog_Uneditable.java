package gui.document.cash;

import java.awt.Frame;

import javax.swing.JPanel;

import VO.CostVO;

public class CashDialog_Uneditable extends CashDialog {

	private static final long serialVersionUID = -4376628696296613460L;

	public CashDialog_Uneditable(Frame parent,CostVO vo) {
		super(parent,vo);
		lbl_account.setText("银行账户："+vo.getAccountName());
	}

	@Override
	protected CashItemPanel getItemPanel() {
		return new CashItemPanel_Uneditable(this,vo.getList());
	}

	@Override
	protected void addButtons(JPanel buttonPane) {
		//do nothing
	}

	@Override
	protected void setAccountComponent(JPanel contnetPanel,
			Object gbc_cbx_customer) {
		//do nothing
	}

}
