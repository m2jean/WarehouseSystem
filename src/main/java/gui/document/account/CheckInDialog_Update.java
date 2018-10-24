package gui.document.account;

import java.awt.Frame;

import accountBL.CheckInBL;
import VO.CheckInVO;
import enums.ResultMessage;

public class CheckInDialog_Update extends CheckInOutDialog_Editable {

	private static final long serialVersionUID = -8452624821702260629L;

	public CheckInDialog_Update(Frame parent, CheckInVO vo) {
		super(parent,vo);
		setTitle("更改收款单");
		setSelected(vo.getCustomerName());
	}

	protected void setSelected(String name) {
		cbx_customer.setSelected(name);
	}

	@Override
	public ResultMessage getSaveResult() {
		return new CheckInBL().changeReceipt((CheckInVO) vo);
	}

	@Override
	public ResultMessage getSubmitResult() {
		return new CheckInBL().changeReceipt((CheckInVO) vo);
	}

}
