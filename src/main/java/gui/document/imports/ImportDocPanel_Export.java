package gui.document.imports;

import progressBL.ProgressBL;
import progressBLService.ProgressBLService;
import gui.MainFrame;
import gui.ResultDialog;
import gui.document.doclist.DocListPanel;
import importBL.ExportBL;
import VO.ExportVO;
import enums.DocumentType;
import enums.ResultMessage;
import enums.Status;

public class ImportDocPanel_Export extends ImportDocPanel {

	private static final long serialVersionUID = -5441220364389447682L;
	private ExportVO vo;

	public ImportDocPanel_Export(ExportVO exp, int func, DocListPanel pnl_list) {
		super(exp, func, pnl_list);
		this.vo = exp;
	}

	public ExportVO getExportVO(){
		return vo;
	}

	@Override
	public ResultMessage update() {
		ResultDialog dia = new ExportDialog_Update(MainFrame.mf,vo);
		dia.setVisible(true);
		return ResultDialog.getResultAndDispose(dia);
	}

	@Override
	public ResultMessage getDeletionResult() {
		if(getStatus() != Status.DRAFT)
			return ResultMessage.CANNOT_DELETE;
		return new ExportBL().deleteDraft(vo);		
	}

	@Override
	public DocumentType getType() {
		return DocumentType.EXPORT;
	}

	@Override
	public ResultMessage examine() {
		ResultDialog dia = new ExportDialog_Examine(MainFrame.mf,vo);
		dia.setVisible(true);
		return ResultDialog.getResultAndDispose(dia);
	}

	@Override
	public ResultMessage getExaminationResult(boolean pass) {
		if(pass)
			vo.setStatus(Status.PASS);
		else
			vo.setStatus(Status.FAIL);
		return new ExportBL().updateExport(vo);
	}

	@Override
	public ResultMessage redcopy() {
		ResultDialog dia = new ExportDialog_Red(MainFrame.mf,vo);
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
