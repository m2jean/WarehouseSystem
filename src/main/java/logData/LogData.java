package logData;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import PO.LogPO;
import dataservice.logdataservice.LogDataService;
import enums.ResultMessage;

import java.io.*;

public class LogData extends UnicastRemoteObject implements LogDataService{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<LogPO> log=new ArrayList<LogPO>();
	File file=new File("log.ser");
	public LogData() throws RemoteException{
		if(file.exists()){
		try{
			FileInputStream fis=new FileInputStream(file);
			ObjectInputStream reader=new ObjectInputStream(fis);
			while(fis.available()>0){
				LogPO po=(LogPO)reader.readObject();
				log.add(po);
			}
			reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		}
	}
	public ArrayList<LogPO> find(String start, String end)
			throws RemoteException {
		ArrayList<LogPO> search=new ArrayList<LogPO>();
		for(LogPO i:log){
			if((i.getDate().compareTo(start)>=0)&&(i.getDate().compareTo(end)<=0)){
				search.add(i.toVO().toPO());
			}
		}
		return search;
	}

	public ResultMessage add(LogPO po) throws RemoteException {
		log.add(po);
		try{
			ObjectOutputStream writer=new ObjectOutputStream(new FileOutputStream(file));
			for(LogPO i:log){
				writer.writeObject(i);
			}
			writer.close();
		}catch(Exception e){
			e.printStackTrace();
			return ResultMessage.SAVE_FAIL;
		}
		return ResultMessage.SUCCESS;
	}

	public ResultMessage init() throws RemoteException {
		try{
			ObjectOutputStream writer=new ObjectOutputStream(new FileOutputStream(file));
			writer.writeChars("");
			writer.close();
		}catch(Exception e){
			e.printStackTrace();
			return ResultMessage.SAVE_FAIL;
		}
		return null;
	}

	public ResultMessage finish() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
