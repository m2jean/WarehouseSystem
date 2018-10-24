package gui.document.cash;

import java.awt.Frame;
import java.util.ArrayList;

import VO.CostVO;
import VO.ListOutVO;
import accountBL.CostBL;
import businesslogic.userbl.UserBL;
import enums.ResultMessage;
import enums.Status;

public class CashDialog_Add extends CashDialog_Editable {

	private static final long serialVersionUID = -3170119525357868932L;

	public CashDialog_Add(Frame parent) {
		super(parent,new CostVO("",new UserBL().getCurrent().getName(),"","",new ArrayList<ListOutVO>(0),0,"",Status.DRAFT,""));
		setTitle("创建现金费用单");
	}

	@Override
	public ResultMessage getSaveResult() {
		return new CostBL().addCost(vo);
	}

	@Override
	public ResultMessage getSubmitResult() {
		return new CostBL().addCost(vo);
	}

}
