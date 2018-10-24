package businesslogic.promotionbl;

import java.rmi.RemoteException;
import java.util.ArrayList;

import data.promotiondata.PromotionDataStub;
import dataservice.promotiondataservice.PromotionDataService;
import enums.DataMessage;
import enums.ResultMessage;
import enums.UserPermission;
import enums.VipLevel;
import PO.PromotionPO;
import VO.PresentVO;
import VO.PromotionVO;
import VO.SaleVO;
import businesslogicservice.promotionblservice.PromotionBLService;

public class PromotionBLStub implements PromotionBLService{
	private PromotionDataService promotiondata = new PromotionDataStub();
	
	public DataMessage<ArrayList<PromotionVO>> get() {
		try {
			ArrayList<PromotionPO> list = promotiondata.get();
			DataMessage<ArrayList<PromotionVO>> result = new DataMessage<ArrayList<PromotionVO>>(new ArrayList<PromotionVO>());
			for(PromotionPO promotion:list)
				result.data.add(promotion.toVO());
			return result;
		} catch (RemoteException e) {
			return new DataMessage<ArrayList<PromotionVO>>(ResultMessage.REMOTE_FAIL);
		}
	}

	public DataMessage<ArrayList<PromotionVO>> getUnexpired() {
		try {
			ArrayList<PromotionPO> list = promotiondata.getUnexpired();
			DataMessage<ArrayList<PromotionVO>> result = new DataMessage<ArrayList<PromotionVO>>(new ArrayList<PromotionVO>());
			for(PromotionPO promotion:list)
				result.data.add(promotion.toVO());
			return result;
		} catch (RemoteException e) {
			return new DataMessage<ArrayList<PromotionVO>>(ResultMessage.REMOTE_FAIL);
		}
	}

	/*public DataMessage<ArrayList<PromotionVO>> find(PromotionType type) {
		try {
			ArrayList<PromotionPO> list = promotiondata.find(type).data;
			DataMessage<ArrayList<PromotionVO>> result = new DataMessage<ArrayList<PromotionVO>>(new ArrayList<PromotionVO>());
			for(PromotionPO promotion:list)
				result.data.add(promotion.toVO());
			return result;
		} catch (RemoteException e) {
			return new DataMessage<ArrayList<PromotionVO>>(ResultMessage.REMOTE_FAIL);
		}
	}*/

	public ResultMessage add(PromotionVO promotion) {
		return ResultMessage.SUCCESS;
	}

	public DataMessage<ArrayList<PromotionVO>> check(SaleVO sale) {
		try {
			ArrayList<PromotionPO> list = promotiondata.getUnexpired();
			DataMessage<ArrayList<PromotionVO>> result = new DataMessage<ArrayList<PromotionVO>>(new ArrayList<PromotionVO>());
			for(PromotionPO promotion:list)
				result.data.add(promotion.toVO());
			return result;
		} catch (RemoteException e) {
			return new DataMessage<ArrayList<PromotionVO>>(ResultMessage.REMOTE_FAIL);
		}
	}

	public ResultMessage update(PromotionVO promotion) {
		try {
			return promotiondata.update(promotion.toPO());
		} catch (RemoteException e) {
			return ResultMessage.REMOTE_FAIL;
		}
	}
	
	public DataMessage<SaleVO> getDiscount(SaleVO sale){
		if(sale.getTotal()>=10000){
			sale.setCreditGive(500);
			sale.setDiscount2(800);
			sale.setPostTotal(sale.getTotal()-800);
			return new DataMessage<SaleVO>(sale);
		}
		return new DataMessage<SaleVO>(new SaleVO(null, null, null, null, null, null, null, 0, 0, 0, 0, 0, null, null, 0, null, null, null));
	}

	@Override
	public DataMessage<ArrayList<PromotionVO>> getUnexpired(VipLevel level,
			double total) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataMessage<Integer> getDiscountPermission(UserPermission user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultMessage updateDiscountPermission(int[] array) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultMessage delete(PromotionVO promotion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataMessage<PromotionVO> getPromotion(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataMessage<PresentVO> getPresent(VipLevel level, double total, String salesman) {
		// TODO Auto-generated method stub
		return null;
	}


}
