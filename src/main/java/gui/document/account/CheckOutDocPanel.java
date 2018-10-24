package gui.document.account;

import progressBL.ProgressBL;
import progressBLService.ProgressBLService;
import accountBL.CheckOutBL;
import VO.CheckOutVO;
import enums.DocumentType;
import enums.ResultMessage;
import enums.Status;
import gui.MainFrame;
import gui.ResultDialog;
import gui.document.doclist.DocListPanel;

public class CheckOutDocPanel extends CheckInOutDocPanel {

	private static final long serialVersionUID = -8511603554808139470L;
	private CheckOutVO vo;

	public CheckOutDocPanel(CheckOutVO vo, int func, DocListPanel pnl_list) {
		super(vo, func, pnl_list);
		this.vo = vo;
	}

	@Override
	public DocumentType getType() {
		return DocumentType.CHECKOUT;
	}

	@Override
	public void check() {
		new CheckInOutDialog_Uneditable(MainFrame.mf,vo).setVisible(true);
	}

	@Override
	public ResultMessage update() {
		ResultDialog dia = new CheckOutDialog_Update(MainFrame.mf,vo);
		dia.setVisible(true);
		return ResultDialog.getResultAndDispose(dia);
	}

	@Override
	public ResultMessage getDeletionResult() {
		return new CheckOutBL().delPayment(vo);
	}


	@Override
	public ResultMessage examine() {
		ResultDialog dia = new CheckOutDialog_Examine(MainFrame.mf,vo);
		dia.setVisible(true);
		return ResultDialog.getResultAndDispose(dia);
	}

	@Override
	public ResultMessage getExaminationResult(boolean pass) {
		if(pass)
			vo.setStatus(Status.PASS);
		else
			vo.setStatus(Status.FAIL);
		return new CheckOutBL().shenpi(vo);
	}
	
	@Override
	public ResultMessage redcopy() {
		ResultDialog dia = new CheckOutDialog_Red(MainFrame.mf,vo);
		dia.setVisible(true);
		return ResultDialog.getResultAndDispose(dia);
	}

	@Override
	public ResultMessage getRedResult() {
		vo.setHong(true);
		ProgressBLService progress = new ProgressBL();
		vo = progress.hong(vo);
		return progress.hongcopy(vo);
	}
}
