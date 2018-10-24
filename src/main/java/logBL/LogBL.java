package logBL;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import dataservice.logdataservice.LogDataService;
import enums.DataMessage;
import enums.ResultMessage;
import factory.Factory;
import factory.RMIFactory;
import PO.LogPO;
import VO.LogVO;
import businesslogicservice.logblservice.LogBLService;

public class LogBL implements LogBLService{
	
	public ResultMessage add(LogVO log) {
		RMIFactory rmi=new RMIFactory();
		Factory factory=new Factory();
		log.setDate(factory.getTime());
		try {
			LogDataService data=rmi.newLogDataService();
			return data.add(log.toPO());
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			return ResultMessage.REMOTE_FAIL;
		}
	}

	public DataMessage<ArrayList<LogVO>> find(String start, String end) {
		RMIFactory rmi=new RMIFactory();
		ArrayList<LogPO> check=new ArrayList<LogPO>();
		try {
			LogDataService data=rmi.newLogDataService();
			check=data.find(start, end);
			DataMessage<ArrayList<LogVO>> result=new DataMessage<ArrayList<LogVO>>(ResultMessage.SUCCESS);
			if(check.isEmpty()){
				result.resultMessage=ResultMessage.IS_EMPTY;
				return result;
			}
			ArrayList<LogVO> vo=new ArrayList<LogVO>();
			for(LogPO i:check){
				vo.add(i.toVO());
			}
			result.data=vo;
			return result;
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			return new DataMessage(ResultMessage.REMOTE_FAIL);
		}
	}

	public ResultMessage init() {
		RMIFactory rmi=new RMIFactory();
		try {
			LogDataService data=rmi.newLogDataService();
			return data.init();
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			return ResultMessage.REMOTE_FAIL;
		}
	}

	public ResultMessage finish() {
		// TODO Auto-generated method stub
		return null;
	}

	public DataMessage<ArrayList<LogVO>> getAll() {
		RMIFactory rmi=new RMIFactory();
		try {
			LogDataService data=rmi.newLogDataService();
			ArrayList<LogPO> save=data.find("0000-00-00", "9999-99-99");
			DataMessage<ArrayList<LogVO>> result=new DataMessage<ArrayList<LogVO>>(ResultMessage.SUCCESS);
			if(save.isEmpty()){
				result.resultMessage=ResultMessage.IS_EMPTY;
				return result;
			}
			ArrayList<LogVO> asd=new ArrayList<LogVO>();
			for(LogPO i:save){
				asd.add(i.toVO());
			}
			result.data=asd;
			return result;
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			return new DataMessage(ResultMessage.REMOTE_FAIL);
		}
	}

}
