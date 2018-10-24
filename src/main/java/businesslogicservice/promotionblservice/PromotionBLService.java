package businesslogicservice.promotionblservice;

import java.util.ArrayList;

import enums.DataMessage;
import enums.ResultMessage;
import enums.UserPermission;
import enums.VipLevel;
import VO.PresentVO;
import VO.PromotionVO;
import VO.SaleVO;

public interface PromotionBLService {
	public DataMessage<ArrayList<PromotionVO>> get();
	public DataMessage<PromotionVO> getPromotion(String id);
	public DataMessage<ArrayList<PromotionVO>> getUnexpired();
	public DataMessage<ArrayList<PromotionVO>> getUnexpired(VipLevel level, double total);
	                    //根据客户等级、总价获取符合要求的促销策略
	public ResultMessage add(PromotionVO promotion);
	public ResultMessage update(PromotionVO promotion);
	public ResultMessage delete(PromotionVO promotion);
	public DataMessage<SaleVO> getDiscount(SaleVO sale);
	public DataMessage<PresentVO> getPresent(VipLevel level, double total, String salesman);
	public DataMessage<Integer> getDiscountPermission(UserPermission user);
	public ResultMessage updateDiscountPermission(int[] array);
}
