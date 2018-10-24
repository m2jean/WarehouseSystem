package gui.document.account;

import java.awt.Frame;
import java.util.ArrayList;

import accountBL.CheckInBL;
import businesslogic.userbl.UserBL;
import VO.CheckInVO;
import VO.ListInVO;
import enums.ResultMessage;
import enums.Status;

public class CheckInDialog_Add extends CheckInOutDialog_Editable {

	private static final long serialVersionUID = -8452624821702260629L;

	public CheckInDialog_Add(Frame parent) {
		super(parent,new CheckInVO("","","",new UserBL().getCurrent().getName(),new ArrayList<ListInVO>(0),0,"",Status.DRAFT,""));
		setTitle("创建收款单");
	}

	@Override
	public ResultMessage getSaveResult() {
		return new CheckInBL().addReceipt((CheckInVO) vo);
	}

	@Override
	public ResultMessage getSubmitResult() {
		return new CheckInBL().addReceipt((CheckInVO) vo);
	}

}
