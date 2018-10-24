package accountDataService;

import java.rmi.Remote;

import java.rmi.RemoteException;
import java.util.ArrayList;

import enums.ResultMessage;
import PO.AccountPO;
import PO.CheckInPO;
import PO.CheckOutPO;
import PO.CostPO;

public interface AccountDataService extends Remote{
	public ResultMessage insert(AccountPO po) throws RemoteException;
	public ResultMessage delete(AccountPO po)throws RemoteException;
	public ResultMessage update(AccountPO po)throws RemoteException;
	public ArrayList<AccountPO> find(String id,String name)throws RemoteException;
	public AccountPO find(String id)throws RemoteException;
	public ArrayList<AccountPO> show()throws RemoteException;
	public String newReceipt(CheckInPO po)throws RemoteException;
	public String newPayment(CheckOutPO po)throws RemoteException;
	public ResultMessage updateReceipt(CheckInPO po)throws RemoteException;
	public ResultMessage updatePayment(CheckOutPO po)throws RemoteException;
	public CheckInPO getR(CheckInPO po)throws RemoteException;
	public CheckOutPO getP(CheckOutPO po)throws RemoteException;
	public ArrayList<CheckInPO> AllReceipt()throws RemoteException;
	public ArrayList<CheckOutPO> AllPayment()throws RemoteException;
	public ArrayList<CheckInPO> getR(String stime,String etime, String customer)throws RemoteException;
	public ArrayList<CheckOutPO> getP(String stime,String etime,String customer)throws RemoteException;
	public ArrayList<CheckInPO> getRDraft()throws RemoteException;
	public ArrayList<CheckOutPO> getPDraft()throws RemoteException;
	public String newCost(CostPO po)throws RemoteException;
	public ResultMessage updateCost(CostPO po) throws RemoteException;
	public CostPO getC(CostPO po) throws RemoteException;
	public ArrayList<CostPO> AllCost() throws RemoteException;
	public ArrayList<CostPO> getC(String stime,String etime) throws RemoteException;
	public ArrayList<CostPO> getCDraft()throws RemoteException;
	public ArrayList<CheckInPO> getRPass()throws RemoteException;
	public ArrayList<CheckOutPO> getPPass()throws RemoteException;
	public ArrayList<CostPO> getCPass()throws RemoteException;
	public ResultMessage delReceipt(CheckInPO po) throws RemoteException;
	public ResultMessage delPayment(CheckOutPO po) throws RemoteException;
	public ResultMessage delCost(CostPO po) throws RemoteException;
	public ArrayList<CheckInPO> getMyI(String name) throws RemoteException;
	public ArrayList<CheckOutPO> getMyO(String name) throws RemoteException;
	public ArrayList<CostPO> getMyC(String name) throws RemoteException;
	public ArrayList<CheckInPO> getISubmit()throws RemoteException;
	public ArrayList<CheckOutPO> getOSubmit()throws RemoteException;
	public ArrayList<CostPO> getCSubmit()throws RemoteException;
}
