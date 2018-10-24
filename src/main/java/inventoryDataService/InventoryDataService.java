package inventoryDataService;

import PO.OverflowPO;
import PO.PresentPO;
import PO.UserPO;
import enums.ResultMessage;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;

public interface InventoryDataService extends Remote {
	public ResultMessage addOverflow(OverflowPO po) throws RemoteException;
	public ResultMessage addPresent(PresentPO po) throws RemoteException;
	public ResultMessage updateOverflow(OverflowPO po) throws RemoteException;
	public ResultMessage updatePresent(PresentPO po) throws RemoteException;
	public ResultMessage deleteOverflow(OverflowPO po) throws RemoteException;
	public ResultMessage deletePresent(PresentPO po) throws RemoteException;
	public String addPresent_returnID(PresentPO po) throws RemoteException;
	public OverflowPO getOverflow(OverflowPO po) throws RemoteException;
	public PresentPO getPresent(PresentPO po) throws RemoteException;
	public ArrayList<PresentPO> getAllPresent() throws RemoteException;
	public ArrayList<PresentPO> getPresent(String start, String end) throws RemoteException;
	public ArrayList<OverflowPO> getAllOverflow() throws RemoteException;
	public ArrayList<OverflowPO> getOverflow(String start, String end) throws RemoteException;
	public ArrayList<PresentPO> getPresentDraft(UserPO user) throws RemoteException;
	public ArrayList<PresentPO> getAllPassedPresent() throws RemoteException;
	public ArrayList<OverflowPO> getOverflowDraft(UserPO user) throws RemoteException;
	public ArrayList<OverflowPO> getAllPassedOverflow() throws RemoteException;
	public ArrayList<PresentPO> getPresent(UserPO user) throws RemoteException;
	public ArrayList<OverflowPO> getOverflow(UserPO user) throws RemoteException;
	
}
