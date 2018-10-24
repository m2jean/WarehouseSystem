package saleDataStub;

import java.rmi.RemoteException;
import java.util.ArrayList;

import PO.SalePO;
import enums.ResultMessage;
import saleDataService.SaleDataService;

public class SaleDataStub implements SaleDataService{

	public String newSale(SalePO po) {
		return "123";
	}

	public ArrayList<SalePO> find(String stime,String etime, String good, String cus,
			String yewu, String cangku) {
		return new ArrayList<SalePO>();
	}

	public SalePO get(SalePO po) {
		return new SalePO(null, null, null, null, null, null, null, 0, 0, 0, 0, 0, null, null, 0, null, null, null, false);
	}

	public ResultMessage update(SalePO po) {
		return ResultMessage.SUCCESS;
	}

	public ArrayList<SalePO> getAll() {
		return new ArrayList<SalePO>();
	}

	@Override
	public ArrayList<SalePO> getDraft() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<SalePO> getPass() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultMessage delSale(SalePO po) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<SalePO> getMy(String name) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<SalePO> getSubmit() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
