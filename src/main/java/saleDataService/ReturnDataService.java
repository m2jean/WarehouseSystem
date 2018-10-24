package saleDataService;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import enums.ResultMessage;
import PO.ReturnPO;

public interface ReturnDataService extends Remote{
	public String newReturn(ReturnPO po)throws RemoteException;
	public ResultMessage delReturn(ReturnPO po)throws RemoteException; 
	public ReturnPO get(ReturnPO po)throws RemoteException;
	public ArrayList<ReturnPO> find(String stime,String etime, String good, String cus,
			String yewu, String cangku)throws RemoteException;
	public ArrayList<ReturnPO> getAll()throws RemoteException;
	public ArrayList<ReturnPO> getDraft()throws RemoteException;
	public ArrayList<ReturnPO> getPass()throws RemoteException;
	public ArrayList<ReturnPO> getSubmit()throws RemoteException;
	public ResultMessage update(ReturnPO po)throws RemoteException;
	public ArrayList<ReturnPO> getMy(String name)throws RemoteException;
}
