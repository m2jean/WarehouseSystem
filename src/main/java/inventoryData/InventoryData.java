package inventoryData;

import inventoryDataService.InventoryDataService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;

import PO.OverflowPO;
import PO.PresentPO;
import PO.UserPO;
import enums.ResultMessage;
import enums.Status;
import factory.Factory;

public class InventoryData extends UnicastRemoteObject implements InventoryDataService{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1139750357230686749L;
	private ArrayList<OverflowPO> overflowList;
	private ArrayList<PresentPO> presentList;
	private ArrayList<PresentPO> presentListWithSale;
	
	
	public InventoryData() throws RemoteException{
		super();
		
		presentList=new ArrayList<PresentPO>();
		overflowList=new ArrayList<OverflowPO>();
		presentListWithSale=new ArrayList<PresentPO>();
		
		loadOverflow();
		loadPresent();
		loadPresentWithSale();
	}
	
	private void loadOverflow(){
		FileInputStream fis;
		ObjectInputStream ois;
		try{
			File file=new File("overflowdata.ser");
			if(file.exists()){
				fis=new FileInputStream(file);
				ois=new ObjectInputStream(fis);
				OverflowPO overflowPO;
				while(fis.available()>0){
					overflowPO=(OverflowPO)ois.readObject();
					overflowList.add(overflowPO);
				}
			ois.close();
			fis.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void loadPresent(){
		FileInputStream fis;
		ObjectInputStream ois;
		try{
			File file=new File("presentdata.ser");
			if(file.exists()){
				fis=new FileInputStream(file);
				ois=new ObjectInputStream(fis);
				PresentPO presentPO;
				while(fis.available()>0){
					presentPO=(PresentPO)ois.readObject();
					presentList.add(presentPO);
				}
				ois.close();
				fis.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void loadPresentWithSale(){
		FileInputStream fis;
		ObjectInputStream ois;
		try{
			File file=new File("present_saledata.ser");
			if(file.exists()){
				fis=new FileInputStream(file);
				ois=new ObjectInputStream(fis);
				PresentPO presentPO;
				while(fis.available()>0){
					presentPO=(PresentPO)ois.readObject();
					presentListWithSale.add(presentPO);
				}
				ois.close();
				fis.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private ResultMessage saveOverflow(){
		FileOutputStream fos;
		ObjectOutputStream oos;
		try{
			fos=new FileOutputStream(new File("overflowdata.ser"));
			oos=new ObjectOutputStream(fos);
			for(OverflowPO po:overflowList){
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
	
	private ResultMessage savePresent(){
		FileOutputStream fos;
		ObjectOutputStream oos;
		try{
			fos=new FileOutputStream(new File("presentdata.ser"));
			oos=new ObjectOutputStream(fos);
			for(PresentPO po:presentList){
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
	
	private ResultMessage savePresentWithSale(){
		FileOutputStream fos;
		ObjectOutputStream oos;
		try{
			fos=new FileOutputStream(new File("present_saledata.ser"));
			oos=new ObjectOutputStream(fos);
			for(PresentPO po:presentListWithSale){
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
	
	public ResultMessage addOverflow(OverflowPO po) throws RemoteException{
		for(OverflowPO overflow:overflowList){
			if(overflow.equals(po)&&overflow.getStatus()==Status.DRAFT){
				return ResultMessage.ITEM_EXIST;
			}
		}
		
		String id;
		Factory factory = new Factory();
		if(po.getStatus() == Status.SUBMIT){
			id = factory.getOverflowID();
		}else if(po.getStatus() == Status.DRAFT){
			id = factory.getDraftID();
		}else{
			id = po.getID();
		}
		
		po.setID(id);
		overflowList.add(po);
		return saveOverflow();
	}
	
	public ResultMessage addPresent(PresentPO po) throws RemoteException{
		String id;
		Factory factory = new Factory();
		if(po.getStatus() == Status.SUBMIT){
			id = factory.getPresentID();
		}else if(po.getStatus() == Status.DRAFT){
			id = factory.getDraftID();
		}else{
			id = po.getID();
		}
		
		po.setID(id);
		presentList.add(po);
		return savePresent();
	}
	
	public String addPresent_returnID(PresentPO po) throws RemoteException{
		String id=new Factory().getPresentID();
		po.setID(id);
		presentListWithSale.add(po);
		savePresentWithSale();
		return id;
	}
	
	public OverflowPO getOverflow(OverflowPO po) throws RemoteException{
		for(OverflowPO overflow:overflowList){
			if(po.getID().equals(overflow.getID())){
				return new OverflowPO(overflow);
			}
		}
		return null;
	}
	
	public ArrayList<OverflowPO> getAllOverflow() throws RemoteException{
		ArrayList<OverflowPO> list=new ArrayList<OverflowPO>();
		for(OverflowPO po:overflowList){
			if(po.getStatus()!=Status.DRAFT)
				list.add(new OverflowPO(po));
		}
		return list;
	}
	
	public ArrayList<OverflowPO> getOverflowDraft(UserPO user) throws RemoteException{
		ArrayList<OverflowPO> list=new ArrayList<OverflowPO>();
		for(OverflowPO po:overflowList){
			if(po.getOperator().equals(user.getName())&&po.getStatus()==Status.DRAFT){
				list.add(new OverflowPO(po));
			}
		}
		return list;
	}
	
	public ArrayList<OverflowPO> getAllPassedOverflow() throws RemoteException{
		ArrayList<OverflowPO> list=new ArrayList<OverflowPO>();
		for(OverflowPO po:overflowList){
			if(po.getStatus()==Status.PASS){
				list.add(new OverflowPO(po));
			}
		}
		return list;
	}
	
	public ArrayList<OverflowPO> getOverflow(String start, String end){
		ArrayList<OverflowPO> list=new ArrayList<OverflowPO>();
		for(OverflowPO po:overflowList){
			if(po.getDate().compareTo(start)>=0&&po.getDate().compareTo(end)<=0){
				list.add(new OverflowPO(po));
			}
		}
		return list;
	}
	
	public PresentPO getPresent(PresentPO po) throws RemoteException{
		for(PresentPO present:presentList){
			if(po.getID().equals(present.getID())){
				return new PresentPO(present);
			}
		}
		return null;
	}
	
	public ArrayList<PresentPO> getAllPresent() throws RemoteException{
		ArrayList<PresentPO> list=new ArrayList<PresentPO>();
		for(PresentPO po:presentList){
			if(po.getStatus()!=Status.DRAFT)
				list.add(new PresentPO(po));
		}
		
		for(PresentPO po:presentListWithSale){
			if(po.getStatus()!=Status.DRAFT)
				list.add(new PresentPO(po));
		}
		return list;
	}
	
	public ArrayList<PresentPO> getPresentDraft(UserPO user) throws RemoteException{
		ArrayList<PresentPO> list=new ArrayList<PresentPO>();
		for(PresentPO po:presentList){
			if(po.getOperator().equals(user.getName())&&po.getStatus()==Status.DRAFT){
				list.add(new PresentPO(po));
			}
		}
		return list;
	}
	
	public ArrayList<PresentPO> getAllPassedPresent() throws RemoteException{
		ArrayList<PresentPO> list=new ArrayList<PresentPO>();
		for(PresentPO po:presentList){
			if(po.getStatus()==Status.PASS){
				list.add(new PresentPO(po));
			}
		}
		
		for(PresentPO po:presentListWithSale){
			if(po.getStatus()==Status.PASS){
				list.add(new PresentPO(po));
			}
		}
		return list;
	}
	
	public ArrayList<PresentPO> getPresent(String start, String end){
		ArrayList<PresentPO> list=new ArrayList<PresentPO>();
		for(PresentPO po:presentList){
			if(po.getDate().compareTo(start)>=0&&po.getDate().compareTo(end)<=0){
				list.add(new PresentPO(po));
			}
		}
		return list;
	}
	
	public ResultMessage updateOverflow(OverflowPO po) throws RemoteException{
		for(OverflowPO overflow:overflowList){
			if(overflow.getID().equals(po.getID())){
				overflowList.remove(overflow);
				overflowList.add(po);
				Collections.sort(overflowList);
				return saveOverflow();
			}
		}
		
		return ResultMessage.ITEM_NOT_EXIST;
	}
	
	public ResultMessage updatePresent(PresentPO po) throws RemoteException{
		for(PresentPO present:presentList){
			if(present.getID().equals(po.getID())){
				presentList.remove(present);
				presentList.add(po);
				Collections.sort(presentList);
				return savePresent();
			}
		}
		
		return ResultMessage.ITEM_NOT_EXIST;
	}

	public ArrayList<PresentPO> getPresent(UserPO user) throws RemoteException {
		ArrayList<PresentPO> list=new ArrayList<PresentPO>();
		for(PresentPO po:presentList){
			if(po.getOperator().equals(user.getName())){
				list.add(new PresentPO(po));
			}
		}
		
		for(PresentPO po:presentListWithSale){
			if(po.getOperator().equals(user.getName())){
				list.add(new PresentPO(po));
			}
		}
		return list;
	}

	public ArrayList<OverflowPO> getOverflow(UserPO user) throws RemoteException {
		ArrayList<OverflowPO> list=new ArrayList<OverflowPO>();
		for(OverflowPO po:overflowList){
			if(po.getOperator().equals(user.getName())){
				list.add(new OverflowPO(po));
			}
		}
		return list;
	}

	public ResultMessage deleteOverflow(OverflowPO overflow) throws RemoteException {
		for(OverflowPO po:overflowList){
			if(po.getID().equals(overflow.getID())){
				overflowList.remove(po);
				return saveOverflow();
			}
		}
		
		return ResultMessage.ITEM_NOT_EXIST;
	}

	public ResultMessage deletePresent(PresentPO present) throws RemoteException {
		for(PresentPO po:presentList){
			if(po.getID().equals(present.getID())){
				presentList.remove(po);
				return savePresent();
			}
		}
		
		return ResultMessage.ITEM_NOT_EXIST;
	}

}
