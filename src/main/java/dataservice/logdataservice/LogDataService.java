package dataservice.logdataservice;

import java.rmi.Remote;

import java.rmi.RemoteException;
import java.util.ArrayList;

import enums.ResultMessage;
import PO.LogPO;

public interface LogDataService extends Remote{
	public ArrayList<LogPO> find(String start,String end) throws RemoteException;
	public ResultMessage add(LogPO log)throws RemoteException;
	public ResultMessage init()throws RemoteException;
	public ResultMessage finish()throws RemoteException;
}
