package saleDataService;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import enums.ResultMessage;
import PO.SalePO;

public interface SaleDataService extends Remote{
	public String newSale(SalePO po) throws RemoteException;
	public ResultMessage delSale(SalePO po) throws RemoteException;
	public ArrayList<SalePO> find(String stime,String etime, String good, String cus,
			String yewu, String cangku)throws RemoteException;
	public ArrayList<SalePO> getAll()throws RemoteException;
	public ArrayList<SalePO> getDraft()throws RemoteException;
	public ArrayList<SalePO> getPass()throws RemoteException;
	public ArrayList<SalePO> getSubmit()throws RemoteException;
	public SalePO get(SalePO po)throws RemoteException;
	public ResultMessage update(SalePO po)throws RemoteException;
	public ArrayList<SalePO> getMy(String name) throws RemoteException;
}
