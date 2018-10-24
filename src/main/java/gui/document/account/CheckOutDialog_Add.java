package gui.document.account;

import java.awt.Frame;
import java.util.ArrayList;

import accountBL.CheckOutBL;
import businesslogic.userbl.UserBL;
import VO.CheckOutVO;
import VO.ListInVO;
import enums.ResultMessage;
import enums.Status;

public class CheckOutDialog_Add extends CheckInOutDialog_Editable {

	private static final long serialVersionUID = -8452624821702260629L;

	public CheckOutDialog_Add(Frame parent) {
		super(parent,new CheckOutVO("","","",new UserBL().getCurrent().getName(),new ArrayList<ListInVO>(0),0,"",Status.DRAFT,""));
		setTitle("创建付款单");
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
