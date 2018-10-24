package saleBLService;

import java.util.ArrayList;





import saleBL.SaleLog;
import enums.DataMessage;
import enums.ResultMessage;
import VO.PresentVO;
import VO.SaleVO;
import VO.UserVO;

public interface SaleBLService {
	public ResultMessage addSale(SaleVO vo,PresentVO present);
	public ResultMessage delSale(SaleVO vo);
	public DataMessage<SaleVO> getSale(SaleVO vo);
	public DataMessage<ArrayList<SaleVO>> AllSale();
	public DataMessage<ArrayList<SaleVO>> AllDraft();
	public DataMessage<ArrayList<SaleVO>> AllPass();
	public DataMessage<ArrayList<SaleVO>> AllSubmit();
	public DataMessage<ArrayList<SaleVO>> getSales(String stime,String etime,String good,String cus,String yewu,String cangku);
	public DataMessage<ArrayList<SaleLog>> getMingxi(String stime,String etime,String good,String cus,String yewu,String cangku);
	public DataMessage<SaleVO> fillSale(SaleVO vo);
	public ResultMessage updateSales(SaleVO vo);
	public ResultMessage shenpi(SaleVO vo);
	public DataMessage<ArrayList<SaleVO>> getMy(UserVO vo);
}
