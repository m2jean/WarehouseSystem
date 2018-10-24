package importBLService;

import java.util.ArrayList;
import VO.ExportVO;
import enums.DataMessage;
import enums.ResultMessage;

public interface ExportBLService {
	public ResultMessage newExport(ExportVO vo);
	public DataMessage<ExportVO> getExport(ExportVO vo);
	public DataMessage<ArrayList<ExportVO>> getExportByCurrentUser();
	public DataMessage<ArrayList<ExportVO>> getExport(String startTime, String endTime);
	public DataMessage<ArrayList<ExportVO>> getExportList();
	public ResultMessage updateExport(ExportVO vo);
	public DataMessage<ArrayList<ExportVO>> getExport(String startTime, String endTime, String Customer,
			String salesman, String warehouse);
	public DataMessage<ArrayList<ExportVO>> getAllPassed();
	public DataMessage<ArrayList<ExportVO>> getDraftByCurrentUser();
	public DataMessage<ArrayList<ExportVO>> getAllSubmitted();
	public ResultMessage updateDraft(ExportVO vo);
	public ResultMessage deleteDraft(ExportVO vo);
}
