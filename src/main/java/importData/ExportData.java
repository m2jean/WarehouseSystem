package importData;

import importDataService.ExportDataService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;

import PO.ExportPO;
import PO.UserPO;
import enums.ResultMessage;
import enums.Status;
import factory.Factory;

public class ExportData extends UnicastRemoteObject implements ExportDataService{
	/**
	 * 
	 */
	private static final long serialVersionUID = -401118675900869467L;
	ArrayList<ExportPO> exportList;
	
	public ExportData() throws RemoteException{
		exportList=new ArrayList<ExportPO>();
		File file=new File("exportdata.ser");
		if(file.exists()){
			try{
				FileInputStream fis=new FileInputStream(file);
				ObjectInputStream ois=new ObjectInputStream(fis);
				ExportPO po;
				while(fis.available()>0){
					po=(ExportPO)ois.readObject();
					exportList.add(po);
				}
				ois.close();
				fis.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public ResultMessage saveExport(){
		File file=new File("exportdata.ser");
		try{
			FileOutputStream fos=new FileOutputStream(file);
			ObjectOutputStream oos=new ObjectOutputStream(fos);
			for(ExportPO po:exportList){
				oos.writeObject(po);
			}
			oos.close();
			fos.close();
			return ResultMessage.SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			return ResultMessage.SAVE_FAIL;
		}
	}
	
	public String add(ExportPO po){
		String id;
		Factory factory = new Factory();
		if(po.getStatus() == Status.SUBMIT){
			id = factory.getExportID();
		}else if(po.getStatus() == Status.DRAFT){
			id = factory.getDraftID();
		}else{
			id = po.getID();
		}
		
		if(id.equals(Factory.maxID)){
			return "CANNOT_ADD";
		}
		
		po.setID(id);
		exportList.add(po);
		saveExport();
		return id;
	}
	
	public ResultMessage delete(ExportPO po){
		for(ExportPO exportpo:exportList){
			if(po.getID().equals(exportpo.getID())){
				exportList.remove(exportpo);
				return saveExport();
			}
		}
		
		return ResultMessage.ITEM_NOT_EXIST;
	}
	
	public ResultMessage update(ExportPO po){
		for(ExportPO exportpo:exportList){
			if(po.getID().equals(exportpo.getID())){
				exportList.remove(exportpo);
				exportList.add(po);
				Collections.sort(exportList);
				return saveExport();
			}
		}
		
		return ResultMessage.ITEM_NOT_EXIST;
	}
	
	public ExportPO get(ExportPO po){
		for(ExportPO exportpo:exportList){
			if(po.getID().equals(exportpo.getID())){
				return new ExportPO(exportpo);
			}
		}
		
		return null;
	}
	
	public ArrayList<ExportPO> get(String startTime, String endTime){
		ArrayList<ExportPO> list=new ArrayList<ExportPO>();
		for(ExportPO po:exportList){
			String date=po.getDate();
			if(date.compareTo(startTime)>=0&&date.compareTo(endTime)<=0){
				list.add(new ExportPO(po));
			}
		}
		
		return list;
	}
	
	public ArrayList<ExportPO> getAllExport(){
		ArrayList<ExportPO> list=new ArrayList<ExportPO>();
		for(ExportPO po:exportList){
			if(po.getStatus()!=Status.DRAFT){
				list.add(new ExportPO(po));
			}
		}
		
		return list;
	}
	
	public ArrayList<ExportPO> getDraft(UserPO user){
		ArrayList<ExportPO> list=new ArrayList<ExportPO>();
		for(ExportPO po:exportList){
			if(po.getOperator().equals(user.getName())&&po.getStatus()==Status.DRAFT){
				list.add(new ExportPO(po));
			}
		}
		return list;
	}
	
	public ArrayList<ExportPO> getPassed(){
		ArrayList<ExportPO> list=new ArrayList<ExportPO>();
		for(ExportPO po:exportList){
			if(po.getStatus()==Status.PASS){
				list.add(new ExportPO(po));
			}
		}
		return list;
	}

	public ArrayList<ExportPO> getAllExport(UserPO user) throws RemoteException {
		ArrayList<ExportPO> list=new ArrayList<ExportPO>();
		for(ExportPO po:exportList){
			if(po.getOperator().equals(user.getName())){
				list.add(new ExportPO(po));
			}
		}
		return list;
	}

	public ArrayList<ExportPO> getSubmitted() throws RemoteException {
		ArrayList<ExportPO> list=new ArrayList<ExportPO>();
		for(ExportPO po:exportList){
			if(po.getStatus()==Status.SUBMIT){
				list.add(new ExportPO(po));
			}
		}
		return list;
	}
}
