package gui.document.imports;

import progressBL.ProgressBL;
import progressBLService.ProgressBLService;
import gui.GUIUtility;
import gui.MainFrame;
import gui.ResultDialog;
import gui.document.doclist.DocListPanel;
import importBL.ImportBL;
import enums.DocumentType;
import enums.ResultMessage;
import enums.Status;
import VO.ImportVO;

public class ImportDocPanel_Import extends ImportDocPanel {

	private static final long serialVersionUID = -4224428602351889370L;
	private ImportVO vo;

	public ImportDocPanel_Import(ImportVO imp, int func, DocListPanel pnl_list) {
		super(imp, func, pnl_list);
		vo = imp;
	}
	
	public ImportVO getImportVO(){
		return vo;
	}

	@Override
	public ResultMessage update() {
		ResultDialog dia = new ImportDialog_Update(MainFrame.mf,vo);
		dia.pack();
		dia.setResizable(false);
		GUIUtility.setCenter(dia);
		dia.setVisible(true);
		return ResultDialog.getResultAndDispose(dia);
	}
	
	@Override
	public ResultMessage getDeletionResult() {
		if(getStatus() != Status.DRAFT)
			return ResultMessage.CANNOT_DELETE;
		return new ImportBL().deleteDraft(vo);		
	}

	@Override
	public DocumentType getType() {
		return DocumentType.IMPORT;
	}
	
	@Override
	public ResultMessage examine() {
		ResultDialog dia = new ImportDialog_Examine(MainFrame.mf,vo);
		dia.pack();
		dia.setResizable(false);
		GUIUtility.setCenter(dia);
		dia.setVisible(true);
		return ResultDialog.getResultAndDispose(dia);
	}

	@Override
	public ResultMessage getExaminationResult(boolean pass) {
		if(pass)
			vo.setStatus(Status.PASS);
		else
			vo.setStatus(Status.FAIL);
		return new ImportBL().updateImport(vo);
	}

	@Override
	public ResultMessage redcopy() {
		ResultDialog dia = new ImportDialog_Red(MainFrame.mf,vo);
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
