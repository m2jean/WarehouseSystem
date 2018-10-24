package inventoryData;

import inventoryDataService.InventoryDataService;
import PO.OverflowPO;
import PO.PresentPO;
import PO.UserPO;
import enums.ResultMessage;
import enums.Status;

import java.rmi.RemoteException;
import java.util.*;

public class InventoryDataStub implements InventoryDataService{
	public ResultMessage addOverflow(OverflowPO po){
		return ResultMessage.SUCCESS;
	}
	
	public ResultMessage addPresent(PresentPO po){
		return ResultMessage.SUCCESS;
	}
	
	public OverflowPO getOverflow(OverflowPO po){
		return new OverflowPO(null, null, null, null, 0, 0, null, Status.DRAFT);
	}
	
	public ArrayList<OverflowPO> getAllOverflow(){
		return new ArrayList<OverflowPO>();
	}
	
	public PresentPO getPresent(PresentPO po){
		return new PresentPO(null, null, null, null, null);
	}
	
	public ArrayList<PresentPO> getAllPresent(){
		return new ArrayList<PresentPO>();
	}
	
	public ResultMessage updateOverflow(OverflowPO po){
		return ResultMessage.SUCCESS;
	}
	
	public ResultMessage updatePresent(PresentPO po){
		return ResultMessage.SUCCESS;
	}

	@Override
	public ArrayList<OverflowPO> getOverflow(String start, String end)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<PresentPO> getPresent(String start, String end)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<PresentPO> getAllPassedPresent() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<OverflowPO> getAllPassedOverflow() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addPresent_returnID(PresentPO po) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<PresentPO> getPresentDraft(UserPO user)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<OverflowPO> getOverflowDraft(UserPO user)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<PresentPO> getPresent(UserPO user) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<OverflowPO> getOverflow(UserPO user)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultMessage deleteOverflow(OverflowPO po) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultMessage deletePresent(PresentPO po) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}


}
