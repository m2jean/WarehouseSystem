package importBLService;

import java.util.*;
import VO.ImportVO;
import enums.DataMessage;
import enums.ResultMessage;

public interface ImportBLService {
	public ResultMessage newImport(ImportVO vo);
	public DataMessage<ImportVO> getImport(ImportVO vo);
	public DataMessage<ArrayList<ImportVO>> getImportByCurrentUser();
	public DataMessage<ArrayList<ImportVO>> getImport(String startTime, String endTime);
	public DataMessage<ArrayList<ImportVO>> getImportList();
	public ResultMessage updateImport(ImportVO vo);	
	public DataMessage<ArrayList<ImportVO>> getImport(String startTime, String endTime, String Customer,
			String salesman, String warehouse);
	public DataMessage<ArrayList<ImportVO>> getDraftByCurrentUser();
	public DataMessage<ArrayList<ImportVO>> getAllPassed();
	public DataMessage<ArrayList<ImportVO>> getAllSubmitted();
	public ResultMessage updateDraft(ImportVO vo);
	public ResultMessage deleteDraft(ImportVO vo);
}
