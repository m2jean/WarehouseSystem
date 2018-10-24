package accountDataStub;

import java.rmi.RemoteException;
import java.util.ArrayList;

import enums.ResultMessage;
import PO.AccountPO;
import PO.CheckInPO;
import PO.CheckOutPO;
import PO.CostPO;
import accountDataService.AccountDataService;

public class AccountDataStub implements AccountDataService{

	public ResultMessage insert(AccountPO po) {
		return ResultMessage.SUCCESS;
	}

	public ResultMessage delete(AccountPO po) {
		return ResultMessage.SUCCESS;
	}

	public ResultMessage update(AccountPO po) {
		return ResultMessage.SUCCESS;
	}

	public String newReceipt(CheckInPO po) {
		return null;
	}

	public String newPayment(CheckOutPO po) {
		return null;
	}

	public ResultMessage updateReceipt(CheckInPO po) {
		return ResultMessage.SUCCESS;
	}

	public ResultMessage updatePayment(CheckOutPO po) {
		return ResultMessage.SUCCESS;
	}
	
	public ArrayList<CheckInPO> AllReceipt(){
		return new ArrayList<CheckInPO>();
	}
	
	public ArrayList<CheckOutPO> AllPayment(){
		return new ArrayList<CheckOutPO>();
	}

	public CheckInPO getR(CheckInPO po) {
		return null;
	}

	public CheckOutPO getP(CheckOutPO po) {
		return null;
	}

	@Override
	public ArrayList<CheckInPO> getR(String stime, String etime,
			String customer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<CheckOutPO> getP(String stime, String etime,String customer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<CheckInPO> getRDraft() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<CheckOutPO> getPDraft() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String newCost(CostPO po) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultMessage updateCost(CostPO po) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CostPO getC(CostPO po) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<CostPO> AllCost() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<CostPO> getC(String stime, String etime)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<CostPO> getCDraft() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<CheckInPO> getRPass() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<CheckOutPO> getPPass() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<CostPO> getCPass() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultMessage delReceipt(CheckInPO po) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultMessage delPayment(CheckOutPO po) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultMessage delCost(CostPO po) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<CheckInPO> getMyI(String name) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<CheckOutPO> getMyO(String name) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<CostPO> getMyC(String name) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<CheckInPO> getISubmit() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<CheckOutPO> getOSubmit() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<CostPO> getCSubmit() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<AccountPO> find(String id, String name)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AccountPO find(String id) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<AccountPO> show() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
