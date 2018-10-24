package saleBLService;

import java.util.ArrayList;






import enums.DataMessage;
import enums.ResultMessage;
import VO.ReturnVO;
import VO.UserVO;


public interface ReturnBLService {
	public ResultMessage addReturn(ReturnVO vo);
	public ResultMessage delReturn(ReturnVO vo);
	public DataMessage<ReturnVO> getReturn(ReturnVO vo);
	public DataMessage<ArrayList<ReturnVO>> AllReturn();
	public DataMessage<ArrayList<ReturnVO>> AllDraft();
	public DataMessage<ArrayList<ReturnVO>> AllPass();
	public DataMessage<ArrayList<ReturnVO>> AllSubmit();
	public DataMessage<ArrayList<ReturnVO>> getReturns(String stime,String etime,String good,String cus,String yewu,String cangku);
	public ResultMessage updateReturn(ReturnVO vo);
	public ResultMessage shenpi(ReturnVO vo);
	public DataMessage<ArrayList<ReturnVO>> getMy(UserVO vo);
}
