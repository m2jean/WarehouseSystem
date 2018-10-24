package data.promotiondata;

import java.rmi.RemoteException;
import java.util.ArrayList;

import PO.PromotionPO;
import dataservice.promotiondataservice.PromotionDataService;
import enums.ResultMessage;
import enums.UserPermission;
import enums.VipLevel;

public class PromotionDataStub implements PromotionDataService {

	public ArrayList<PromotionPO> get() throws RemoteException {
		ArrayList<PromotionPO> arr = new ArrayList<PromotionPO>();
		arr.add(new PromotionPO(null, null, null, null, 0, 0, 0, 0, null));
		return new ArrayList<PromotionPO>();
	}

	public ArrayList<PromotionPO> getUnexpired() throws RemoteException {
		ArrayList<PromotionPO> arr = new ArrayList<PromotionPO>();
		arr.add(new PromotionPO(null, null, null, null, 0, 0, 0, 0, null));
		return new ArrayList<PromotionPO>();
	}

	/*public DataMessage<ArrayList<PromotionPO>> find(PromotionType type) throws RemoteException {
		ArrayList<PromotionPO> arr = new ArrayList<PromotionPO>();
		arr.add(new PromotionPO(null, null, null, 0, 0, null));
		return new DataMessage<ArrayList<PromotionPO>>(arr);
	}*/

	public String add(PromotionPO promotion) throws RemoteException {
		return "00001";
	}

	public ResultMessage update(PromotionPO promotion) throws RemoteException {
		return ResultMessage.SUCCESS;
	}

	public ResultMessage init() throws RemoteException {
		return ResultMessage.SUCCESS;
	}

	public ResultMessage finish() throws RemoteException {
		return ResultMessage.SUCCESS;
	}

	@Override
	public ArrayList<PromotionPO> getUnexpired(VipLevel level, double total)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getDiscountPermission(UserPermission user)
			throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ResultMessage updateDiscountPermission(int[] array)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultMessage delete(PromotionPO promotion) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PromotionPO get(String id) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
