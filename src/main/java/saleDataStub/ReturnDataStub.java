package saleDataStub;

import java.rmi.RemoteException;
import java.util.ArrayList;

import PO.ReturnPO;
import enums.ResultMessage;
import saleDataService.ReturnDataService;

public class ReturnDataStub implements ReturnDataService{

	public String newReturn(ReturnPO po) {
		return "123";
	}

	public ReturnPO get(ReturnPO po) {
		return new ReturnPO(null, null, null, null, null, null, null, 0,null,null, null,null, false);
	}

	public ResultMessage update(ReturnPO po) {
		return ResultMessage.SUCCESS;
	}

	public ArrayList<ReturnPO> getAll() {
		return new ArrayList<ReturnPO>();
	}
	
	public ArrayList<ReturnPO> find(String stime,String etime, String good, String cus,
			String yewu, String cangku) {
		return null;
	}

	@Override
	public ArrayList<ReturnPO> getDraft() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<ReturnPO> getPass() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultMessage delReturn(ReturnPO po) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<ReturnPO> getMy(String name) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<ReturnPO> getSubmit() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
