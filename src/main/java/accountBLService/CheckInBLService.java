package accountBLService;

import java.util.ArrayList;



import enums.DataMessage;
import enums.ResultMessage;
import VO.CheckInVO;
import VO.UserVO;

public interface CheckInBLService {
	public ResultMessage addReceipt (CheckInVO vo);
	public ResultMessage delReceipt(CheckInVO vo);
	public DataMessage<CheckInVO> getReceipt(CheckInVO vo);
	public ResultMessage changeReceipt(CheckInVO vo);
	public ResultMessage shenpi(CheckInVO vo);
	public DataMessage<ArrayList<CheckInVO>> getAllReceipt();
	public DataMessage<ArrayList<CheckInVO>> getAllDraft();
	public DataMessage<ArrayList<CheckInVO>> AllPass();
	public DataMessage<ArrayList<CheckInVO>> AllSubmit();
	//public CheckInVO fillReceipt(CheckInVO vo);
	public DataMessage<ArrayList<CheckInVO>> getCheck(String stime,String etime,String customer);
	public DataMessage<ArrayList<CheckInVO>> getMy(UserVO vo);
}
