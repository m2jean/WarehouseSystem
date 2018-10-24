package businesslogic.logbl;

import java.rmi.RemoteException;

import java.util.ArrayList;

import data.logdata.LogDataStub;
import dataservice.logdataservice.LogDataService;
import enums.DataMessage;
import enums.ResultMessage;
import PO.LogPO;
import VO.LogVO;
import businesslogicservice.logblservice.LogBLService;

public class LogBLStub implements LogBLService{
	private LogDataService stub = new LogDataStub();
	
	public ResultMessage add(LogVO log) {
		try {
			return stub.add(log.toPO());
		} catch (RemoteException e) {
			return ResultMessage.REMOTE_FAIL;
		}
	}

	public DataMessage<ArrayList<LogVO>> find(String start,String end) {
		try {
			ArrayList<LogPO> list = stub.find(start,end);
			DataMessage<ArrayList<LogVO>> result = new DataMessage<ArrayList<LogVO>>(new ArrayList<LogVO>());
			for(LogPO log:list)
				result.data.add(log.toVO());
			return result;
		} catch (RemoteException e) {
			return new DataMessage<ArrayList<LogVO>>(ResultMessage.REMOTE_FAIL);
		}
	}

	public ResultMessage init() {
		try {
			return stub.init();
		} catch (RemoteException e) {
			return ResultMessage.REMOTE_FAIL;
		}
	}

	public ResultMessage finish() {
		try {
			return stub.finish();
		} catch (RemoteException e) {
			return ResultMessage.REMOTE_FAIL;
		}
	}

	public DataMessage<ArrayList<LogVO>> getAll() {
		return null;
	}

}
