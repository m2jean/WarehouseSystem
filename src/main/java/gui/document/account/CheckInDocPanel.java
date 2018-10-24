package gui.document.account;

import progressBL.ProgressBL;
import progressBLService.ProgressBLService;
import accountBL.CheckInBL;
import VO.CheckInVO;
import enums.DocumentType;
import enums.ResultMessage;
import enums.Status;
import gui.MainFrame;
import gui.ResultDialog;
import gui.document.doclist.DocListPanel;

public class CheckInDocPanel extends CheckInOutDocPanel {

	private static final long serialVersionUID = -8511603554808139470L;
	private CheckInVO vo;

	public CheckInDocPanel(CheckInVO vo, int func, DocListPanel pnl_list) {
		super(vo, func, pnl_list);
		this.vo = vo;
	}

	@Override
	public DocumentType getType() {
		return DocumentType.CHECKIN;
	}

	@Override
	public void check() {
		new CheckInOutDialog_Uneditable(MainFrame.mf,vo).setVisible(true);
	}

	@Override
	public ResultMessage update() {
		ResultDialog dia = new CheckInDialog_Update(MainFrame.mf,vo);
		dia.setVisible(true);
		return ResultDialog.getResultAndDispose(dia);
	}

	@Override
	public ResultMessage getDeletionResult() {
		return new CheckInBL().delReceipt(vo);
	}

	@Override
	public ResultMessage examine() {
		ResultDialog dia = new CheckInDialog_Examine(MainFrame.mf,vo);
		dia.setVisible(true);
		return ResultDialog.getResultAndDispose(dia);
	}

	@Override
	public ResultMessage getExaminationResult(boolean pass) {
		if(pass)
			vo.setStatus(Status.PASS);
		else
			vo.setStatus(Status.FAIL);
		return new CheckInBL().shenpi(vo);
	}

	@Override
	public ResultMessage redcopy() {
		ResultDialog dia = new CheckInDialog_Red(MainFrame.mf,vo);
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
