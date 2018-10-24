package accountBLService;

import java.util.ArrayList;

import enums.DataMessage;
import enums.ResultMessage;
import VO.CheckOutVO;
import VO.UserVO;

public interface CheckOutBLService {
	public ResultMessage addPayment (CheckOutVO vo);
	public ResultMessage delPayment (CheckOutVO vo);
	public DataMessage<CheckOutVO> getPayment(CheckOutVO vo);
	public ResultMessage changePayment(CheckOutVO vo);
	public ResultMessage shenpi(CheckOutVO vo);
	public DataMessage<ArrayList<CheckOutVO>> getAllPayment();
	public DataMessage<ArrayList<CheckOutVO>> getAllDraft();
	public DataMessage<ArrayList<CheckOutVO>> AllPass();
	public DataMessage<ArrayList<CheckOutVO>> AllSubmit();
	public DataMessage<ArrayList<CheckOutVO>> getCheck(String stime,String etime,String customer);
	public DataMessage<ArrayList<CheckOutVO>> getMy(UserVO vo);
}
