package importData;

import importDataService.ImportDataService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;

import PO.ImportPO;
import PO.UserPO;
import enums.ResultMessage;
import enums.Status;
import factory.Factory;

public class ImportData extends UnicastRemoteObject implements ImportDataService{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3597076903748246216L;
	ArrayList<ImportPO> importList;
	
	public ImportData() throws RemoteException{
		importList=new ArrayList<ImportPO>();
		File file=new File("importdata.ser");
		if(file.exists()){
			try{
				FileInputStream fis=new FileInputStream(file);
				ObjectInputStream ois=new ObjectInputStream(fis);
				ImportPO po;
				while(fis.available()>0){
					po=(ImportPO)ois.readObject();
					importList.add(po);
				}
				ois.close();
				fis.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public ResultMessage saveImport(){
		File file=new File("importdata.ser");
		try{
			FileOutputStream fos=new FileOutputStream(file);
			ObjectOutputStream oos=new ObjectOutputStream(fos);
			for(ImportPO po:importList){
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
	
	public String add(ImportPO po){
		String id;
		Factory factory = new Factory();
		if(po.getStatus() == Status.SUBMIT){
			id = factory.getImportID();
		}else if(po.getStatus() == Status.DRAFT){
			id = factory.getDraftID();
		}else{
			id = po.getID();
		}
		
		if(id.equals(Factory.maxID)){
			return "CANNOT_ADD";
		}
		
		po.setID(id);
		importList.add(po);
		saveImport();
		return id;
	}
	
	public ResultMessage delete(ImportPO po){
		for(ImportPO importpo:importList){
			if(po.getID().equals(importpo.getID())){
				importList.remove(importpo);
				return saveImport();
			}
		}
		
		return ResultMessage.ITEM_NOT_EXIST;
	}
	
	public ResultMessage update(ImportPO po){
		for(ImportPO importpo:importList){
			if(po.getID().equals(importpo.getID())){
				importList.remove(importpo);
				importList.add(po);
				Collections.sort(importList);
				return saveImport();
			}
		}
		
		return ResultMessage.ITEM_NOT_EXIST;
	}
	
	public ImportPO get(ImportPO po){
		for(ImportPO importpo:importList){
			if(po.getID().equals(importpo.getID())){
				return new ImportPO(importpo);
			}
		}
		
		return null;
	}
	
	public ArrayList<ImportPO> get(String startTime, String endTime){
		ArrayList<ImportPO> list=new ArrayList<ImportPO>();
		for(ImportPO po:importList){
			String date=po.getDate();
			if(date.compareTo(startTime)>=0&&date.compareTo(endTime)<=0){
				list.add(new ImportPO(po));
			}
		}
		
		return list;
	}
	
	public ArrayList<ImportPO> getAllImport(){
		ArrayList<ImportPO> list=new ArrayList<ImportPO>();
		for(ImportPO po:importList){
			if(po.getStatus()!=Status.DRAFT){
				list.add(new ImportPO(po));
			}
		}
		
		return list;
	}
	
	public ArrayList<ImportPO> getAllImport(UserPO user){
		ArrayList<ImportPO> list=new ArrayList<ImportPO>();
		for(ImportPO po:importList){
			if(po.getOperator().equals(user.getName())){
				list.add(new ImportPO(po));
			}
		}
		
		return list;
	}
	
	public ArrayList<ImportPO> getSubmitted(){
		ArrayList<ImportPO> list=new ArrayList<ImportPO>();
		for(ImportPO po:importList){
			if(po.getStatus()==Status.SUBMIT){
				list.add(new ImportPO(po));
			}
		}
		return list;
	}
	
	public ArrayList<ImportPO> getDraft(UserPO user){
		ArrayList<ImportPO> list=new ArrayList<ImportPO>();
		for(ImportPO po:importList){
			if(po.getStatus()==Status.DRAFT
					&&po.getOperator().equals(user.getName())){
				list.add(new ImportPO(po));
			}
		}
		return list;
	}
	
	public ArrayList<ImportPO> getPassed(){
		ArrayList<ImportPO> list=new ArrayList<ImportPO>();
		for(ImportPO po:importList){
			if(po.getStatus()==Status.PASS){
				list.add(new ImportPO(po));
			}
		}
		return list;
	}

}
