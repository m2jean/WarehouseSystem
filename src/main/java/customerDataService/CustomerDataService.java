package customerDataService;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;

import PO.CustomerPO;
import enums.ResultMessage;

public interface CustomerDataService extends Remote{
	public String add(CustomerPO po) throws RemoteException;
	public ResultMessage delete(CustomerPO po) throws RemoteException;
	public ResultMessage update(CustomerPO po) throws RemoteException;
	public CustomerPO get(CustomerPO po) throws RemoteException;
	public ArrayList<CustomerPO> get(String id, String name) throws RemoteException;
	public ArrayList<CustomerPO> getAll() throws RemoteException;
}
