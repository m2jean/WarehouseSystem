package gui.document.account;

import java.awt.Frame;

import accountBL.CheckOutBL;
import VO.CheckOutVO;
import enums.ResultMessage;

public class CheckOutDialog_Update extends CheckInOutDialog_Editable {

	private static final long serialVersionUID = -8452624821702260629L;

	public CheckOutDialog_Update(Frame parent, CheckOutVO vo) {
		super(parent,vo);
		setTitle("修改付款单");
		setSelected(vo.getCustomerName());
	}

	protected void setSelected(String name) {
		cbx_customer.setSelected(name);
	}

	@Override
	public ResultMessage getSaveResult() {
		return new CheckOutBL().addPayment((CheckOutVO) vo);
	}

	@Override
	public ResultMessage getSubmitResult() {
		return new CheckOutBL().addPayment((CheckOutVO) vo);
	}

}
