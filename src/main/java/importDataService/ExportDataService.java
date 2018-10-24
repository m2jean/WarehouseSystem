package importDataService;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import PO.ExportPO;
import PO.UserPO;
import enums.ResultMessage;

public interface ExportDataService extends Remote{
	public String add(ExportPO po) throws RemoteException;
	public ResultMessage delete(ExportPO po) throws RemoteException;
	public ResultMessage update(ExportPO po) throws RemoteException;
	public ExportPO get(ExportPO po) throws RemoteException;
	public ArrayList<ExportPO> get(String startTime, String endTime) throws RemoteException;
	public ArrayList<ExportPO> getAllExport() throws RemoteException;
	public ArrayList<ExportPO> getAllExport(UserPO user) throws RemoteException;
	public ArrayList<ExportPO> getDraft(UserPO user) throws RemoteException;
	public ArrayList<ExportPO> getSubmitted() throws RemoteException;
	public ArrayList<ExportPO> getPassed() throws RemoteException;
}
