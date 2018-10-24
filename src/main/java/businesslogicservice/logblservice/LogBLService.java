package businesslogicservice.logblservice;

import java.util.ArrayList;

import enums.DataMessage;
import enums.ResultMessage;
import VO.LogVO;

public interface LogBLService {
	public ResultMessage add(LogVO log);
	public DataMessage<ArrayList<LogVO>> find(String start,String end);
	public ResultMessage init();
	public ResultMessage finish();
	public DataMessage<ArrayList<LogVO>> getAll();
}
