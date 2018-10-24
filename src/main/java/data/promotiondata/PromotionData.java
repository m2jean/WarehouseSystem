package data.promotiondata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;

import dataservice.promotiondataservice.PromotionDataService;
import PO.PromotionPO;
import enums.ResultMessage;
import enums.UserPermission;
import enums.VipLevel;
import factory.Factory;

public class PromotionData extends UnicastRemoteObject implements PromotionDataService{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6609088322457308846L;
	ArrayList<PromotionPO> promotionList;
	int[] discountPermission;
	
	public PromotionData() throws RemoteException{
		super();
		
		promotionList=new ArrayList<PromotionPO>();
		discountPermission=new int[2];
		loadPromotion();
		loadDiscountPermission();
	}
	
	private void loadPromotion(){
		File file=new File("promotiondata.ser");
		if(file.exists()){
			try{
				FileInputStream fis=new FileInputStream(file);
				ObjectInputStream ois=new ObjectInputStream(fis);
				PromotionPO po;
				while(fis.available()>0){
					po=(PromotionPO)ois.readObject();
					promotionList.add(po);
				}
				ois.close();
				fis.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	private void loadDiscountPermission(){
		File file=new File("dicountPermission.ser");
		if(file.exists()){
			try{
				FileInputStream fis=new FileInputStream(file);
				ObjectInputStream ois=new ObjectInputStream(fis);
				discountPermission[0]=(Integer)ois.readObject();
				discountPermission[1]=(Integer)ois.readObject();
				ois.close();
				fis.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			discountPermission[0]=1000;
			discountPermission[1]=5000;
		}
	}
	
	private ResultMessage savePromotion(){
		File file=new File("promotiondata.ser");
		try{
			FileOutputStream fos=new FileOutputStream(file);
			ObjectOutputStream oos=new ObjectOutputStream(fos);
			for(PromotionPO po:promotionList){
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
	
	private ResultMessage saveDiscountPermission(){
		File file=new File("discountPermission.ser");
		try{
			FileOutputStream fos=new FileOutputStream(file);
			ObjectOutputStream oos=new ObjectOutputStream(fos);
			oos.writeObject(discountPermission[0]);
			oos.writeObject(discountPermission[1]);
			oos.close();
			fos.close();
			return ResultMessage.SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			return ResultMessage.SAVE_FAIL;
		}
	}
	
	public ArrayList<PromotionPO> get() throws RemoteException {
		ArrayList<PromotionPO> list = new ArrayList<PromotionPO>();
		for(PromotionPO po:promotionList){
			list.add(new PromotionPO(po));
		}
		return list;
	}
	
	public PromotionPO get(String id) throws RemoteException {
		for(PromotionPO po:promotionList){
			if(po.getID().equals(id)){
				return new PromotionPO(po);
			}
		}
		
		return null;
	}

	public ArrayList<PromotionPO> getUnexpired() throws RemoteException {
		ArrayList<PromotionPO> list = new ArrayList<PromotionPO>();
		Factory factory=new Factory();
		String currentDate=factory.getDate();
		for(PromotionPO po:promotionList){
			if(po.getStartDate().compareTo(currentDate)<=0
					&&po.getEndDate().compareTo(currentDate)<=0){
				list.add(new PromotionPO(po));
			}
		}

		return list;
	}

	public ArrayList<PromotionPO> getUnexpired(VipLevel level, double total) throws RemoteException{
		ArrayList<PromotionPO> list = new ArrayList<PromotionPO>();
		Factory factory=new Factory();
		String currentDate=factory.getDate();
		PromotionPO promotion = null;
		for(PromotionPO po:promotionList){
			boolean dateValid = po.getStartDate().compareTo(currentDate)<=0
					&&po.getEndDate().compareTo(currentDate)>=0;
			if(!dateValid){
				continue;
			}else if(po.getViplvl()==VipLevel.LEVEL0){
				list.add(new PromotionPO(po));
			}else if(promotion==null || po.getViplvl().compareTo(promotion.getViplvl())>0){
				promotion = po;
			}else if(promotion==null || po.getViplvl().compareTo(promotion.getViplvl())==0){
				if(po.getPriceLine()>promotion.getPriceLine()){
					promotion = po;
				}
			}
		}
		
		if(promotion!=null){
			list.add(new PromotionPO(promotion));
		}

		return list;
	}

	public String add(PromotionPO promotion) throws RemoteException {
		for(PromotionPO po:promotionList){
			if(po.equals(promotion)){
				return "ITEM_EXIST";
			}
		}
		
		String id=new Factory().getPromotionID();
		promotion.setID(id);
		promotionList.add(promotion);
		Collections.sort(promotionList);
		savePromotion();
		return id;
	}
	
	public ResultMessage delete(PromotionPO promotion) throws RemoteException {
		for(PromotionPO po:promotionList){
			if(po.getID().equals(promotion.getID())){
				promotionList.remove(po);
				return savePromotion();
			}
		}
		
		return ResultMessage.ITEM_NOT_EXIST;
	}

	public ResultMessage update(PromotionPO promotion) throws RemoteException {
		for(PromotionPO po:promotionList){
			if(po.getID().equals(promotion.getID())){
				promotionList.remove(po);
				promotionList.add(promotion);
				Collections.sort(promotionList);
				return savePromotion();
			}
		}
		
		return ResultMessage.ITEM_NOT_EXIST;
	}
	
	public int getDiscountPermission(UserPermission user){
		switch(user){
		case SALESMAN: return discountPermission[0];
		case SALES_MANAGER: return discountPermission[1];
		case GENERAL_MANAGER: return 999999;
		default: return 0;
		}
	}
	
	public ResultMessage updateDiscountPermission(int[] array){
		discountPermission[0]=array[0];
		discountPermission[1]=array[1];
		return saveDiscountPermission();
	}
}
