package importDataService;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;

import PO.ImportPO;
import PO.UserPO;
import enums.ResultMessage;

public interface ImportDataService extends Remote{
	public String add(ImportPO po) throws RemoteException;
	public ResultMessage delete(ImportPO po) throws RemoteException;
	public ResultMessage update(ImportPO po) throws RemoteException;
	public ImportPO get(ImportPO po) throws RemoteException;
	public ArrayList<ImportPO> get(String startTime, String endTime) throws RemoteException;
	public ArrayList<ImportPO> getAllImport() throws RemoteException;
	public ArrayList<ImportPO> getAllImport(UserPO user) throws RemoteException;
	public ArrayList<ImportPO> getDraft(UserPO user) throws RemoteException;
	public ArrayList<ImportPO> getSubmitted() throws RemoteException;
	public ArrayList<ImportPO> getPassed() throws RemoteException;

}