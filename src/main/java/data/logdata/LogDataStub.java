package data.logdata;

import java.rmi.RemoteException;

import java.util.ArrayList;

import PO.LogPO;
import dataservice.logdataservice.LogDataService;
import enums.ResultMessage;

public class LogDataStub implements LogDataService {

	public ArrayList<LogPO> find(String start, String end) throws RemoteException {
		ArrayList<LogPO> arr = new ArrayList<LogPO>();
		arr.add(new LogPO(end, null, null,null));
		return arr;
	}

	public ResultMessage add(LogPO log) throws RemoteException {
		return ResultMessage.SUCCESS;
	}

	public ResultMessage init() throws RemoteException {
		return ResultMessage.SUCCESS;
	}

	public ResultMessage finish() throws RemoteException {
		return ResultMessage.SUCCESS;
	}

}
