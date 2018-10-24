package gui.document.cash;

import java.awt.Frame;

import accountBL.CostBL;
import VO.CostVO;
import enums.ResultMessage;

public class CashDialog_Update extends CashDialog_Editable {

	private static final long serialVersionUID = -3170119525357868932L;

	public CashDialog_Update(Frame parent, CostVO vo) {
		super(parent,vo);
		setTitle("修改现金费用单");
		setSelectedAccount(vo.getAccount());
	}

	@Override
	public ResultMessage getSaveResult() {
		return new CostBL().changeCost(vo);
	}

	@Override
	public ResultMessage getSubmitResult() {
		return new CostBL().addCost(vo);
	}

}
