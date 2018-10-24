package accountBLService;

import java.util.ArrayList;



import VO.CostVO;
import VO.UserVO;
import enums.DataMessage;
import enums.ResultMessage;

public interface CostBLService {
	public ResultMessage addCost (CostVO vo);
	public ResultMessage delCost(CostVO vo);
	public DataMessage<CostVO> getCost(CostVO vo);
	public ResultMessage changeCost(CostVO vo);
	public ResultMessage shenpi(CostVO vo);
	public DataMessage<ArrayList<CostVO>> getAllCost();
	public DataMessage<ArrayList<CostVO>> getAllDraft();
	public DataMessage<ArrayList<CostVO>> AllPass();
	public DataMessage<ArrayList<CostVO>> AllSubmit();
	public CostVO fillCost(CostVO vo);
	public DataMessage<ArrayList<CostVO>> getCheck(String stime,String etime);
	public DataMessage<ArrayList<CostVO>> getMy(UserVO vo);
}
