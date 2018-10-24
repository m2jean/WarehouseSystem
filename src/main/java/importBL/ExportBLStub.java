package importBL;

import java.util.ArrayList;

import VO.ExportVO;
import enums.DataMessage;
import enums.ResultMessage;
import PO.ExportPO;
import importBLService.ExportBLService;
import importData.ExportDataStub;
import enums.Status;

public class ExportBLStub implements ExportBLService{
	public ResultMessage newExport(ExportVO vo){
		new ExportDataStub().add(new ExportPO(null, null, null, null, null, null, null, null, 0, null, Status.DRAFT, false));
		return ResultMessage.SUCCESS;
	}
	
	public DataMessage<ExportVO> getExport(ExportVO vo){
		new ExportDataStub().get(new ExportPO(null, null, null, null, null, null, null, null, 0, null, Status.DRAFT, false));
		return new DataMessage(new ExportVO(null, null, null, null, null, null, null, null, null, Status.DRAFT));
	}
	
	public DataMessage<ArrayList<ExportVO>> getExport(String startTime, String endTime){
		new ExportDataStub().get(startTime, endTime);
		return new DataMessage(new ArrayList<ExportVO>());
	}
	
	public DataMessage<ArrayList<ExportVO>> getExportList(){
		new ExportDataStub().getAllExport();
		return new DataMessage(new ArrayList<ExportVO>());
	}
	
	public ResultMessage updateExport(ExportVO vo){
		new ExportDataStub().update(new ExportPO(null, null, null, null, null, null, null, null, 0, null, Status.DRAFT, false));
		return ResultMessage.SUCCESS;
	}
	
	public DataMessage<ArrayList<ExportVO>> getExport(String startTime, String endTime, String Customer,
			String salesman, String warehouse){
		return new DataMessage<ArrayList<ExportVO>>(new ArrayList<ExportVO>());
	}

	@Override
	public DataMessage<ArrayList<ExportVO>> getDraftByCurrentUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataMessage<ArrayList<ExportVO>> getAllPassed() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataMessage<ArrayList<ExportVO>> getExportByCurrentUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataMessage<ArrayList<ExportVO>> getAllSubmitted() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultMessage updateDraft(ExportVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultMessage deleteDraft(ExportVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

}
