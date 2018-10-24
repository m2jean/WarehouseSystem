package importBL;

import java.util.ArrayList;

import VO.ImportVO;
import enums.DataMessage;
import enums.ResultMessage;
import enums.Status;
import importBLService.ImportBLService;
import PO.ImportPO;
import importData.ImportDataStub;

public class ImportBLStub implements ImportBLService{
	public ResultMessage newImport(ImportVO vo){
		new ImportDataStub().add(new ImportPO(null, null, null, null, null, null, null, null, Status.DRAFT, false));
		return ResultMessage.SUCCESS;
	}
	
	public DataMessage<ImportVO> getImport(ImportVO vo){
		new ImportDataStub().get(new ImportPO(null, null, null, null, null, null, null, null, Status.DRAFT, false));
		return new DataMessage(new ImportVO(null, null, null, null, null, null, null, null, Status.DRAFT));
	}
	
	public DataMessage<ArrayList<ImportVO>> getImport(String startTime, String endTime){
		new ImportDataStub().get(startTime, endTime);
		return new DataMessage(new ArrayList<ImportVO>());
	}
	
	public DataMessage<ArrayList<ImportVO>> getImportList(){
		new ImportDataStub().getAllImport();
		return new DataMessage(new ArrayList<ImportVO>());
	}
	
	public ResultMessage updateImport(ImportVO vo){
		new ImportDataStub().update(new ImportPO(null, null, null, null, null, null, null, null, Status.DRAFT, false));
		return ResultMessage.SUCCESS;
	}
	
	public DataMessage<ArrayList<ImportVO>> getImport(String startTime, String endTime, String Customer,
			String salesman, String warehouse){
		return new DataMessage<ArrayList<ImportVO>>(new ArrayList<ImportVO>());
	}

	@Override
	public DataMessage<ArrayList<ImportVO>> getDraftByCurrentUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataMessage<ArrayList<ImportVO>> getAllPassed() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataMessage<ArrayList<ImportVO>> getImportByCurrentUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataMessage<ArrayList<ImportVO>> getAllSubmitted() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultMessage updateDraft(ImportVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultMessage deleteDraft(ImportVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

}
