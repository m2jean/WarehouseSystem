package productDataService;

import java.rmi.Remote;
import java.rmi.RemoteException;

import PO.CategoryPO;
import enums.ResultMessage;
import factory.CategoryTree;

public interface CategoryDataService extends Remote{
	public String add(CategoryPO po) throws RemoteException;
	public ResultMessage delete(CategoryPO po) throws RemoteException;
	public ResultMessage update(CategoryPO po) throws RemoteException;
	public CategoryPO get(CategoryPO po) throws RemoteException;
	public CategoryTree getAllCategory() throws RemoteException;

}
