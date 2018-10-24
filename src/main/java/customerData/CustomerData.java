package customerData;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;

import PO.CustomerPO;
import customerDataService.CustomerDataService;
import enums.ResultMessage;
import factory.Factory;
import factory.Helper;

public class CustomerData extends UnicastRemoteObject implements CustomerDataService{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5076058092207763578L;
	ArrayList<CustomerPO> customerList;
	
	public CustomerData() throws RemoteException{
		super();
		
		File file=new File("customerdata.ser");
		customerList=new ArrayList<CustomerPO>();
		try{
			if(file.exists()){
				FileInputStream fis=new FileInputStream(file);
				ObjectInputStream ois=new ObjectInputStream(fis);
				
				CustomerPO customer;
				while(fis.available()>0){
					customer=(CustomerPO)ois.readObject();
					customerList.add(customer);
				}
				
				ois.close();
				fis.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private ResultMessage saveCustomer(){
		File file=new File("customerdata.ser");
		try{
			FileOutputStream fos=new FileOutputStream(file);
			ObjectOutputStream oos=new ObjectOutputStream(fos);
			
			for(CustomerPO po:customerList){
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
	
	public String add(CustomerPO po){
		for(CustomerPO customer:customerList){
			if(customer.replicCheck(po)){
				return "ITEM_EXIST";
			}
		}
		
		Factory factory=new Factory();
		String id=factory.getCustomerID();
		po.setID(id);
		customerList.add(po);
		saveCustomer();
		return id;
	}
	
	public ResultMessage delete(CustomerPO po){
		for(CustomerPO customer:customerList){
			if(customer.getID().equals(po.getID())){
				customerList.remove(customer);
				return saveCustomer();
			}
		}
		
		return ResultMessage.ITEM_NOT_EXIST;
	}
	
	public ResultMessage update(CustomerPO po){
		for(CustomerPO customer:customerList){
			if(customer.getID().equals(po.getID())){
				customerList.remove(customer);
				customerList.add(po);
				Collections.sort(customerList);
				return saveCustomer();
			}
		}
		
		return ResultMessage.ITEM_NOT_EXIST;
	}
	
	public CustomerPO get(CustomerPO po){
		for(CustomerPO customer:customerList){
			if(customer.getID().equals(po.getID())){
				return new CustomerPO(customer);
			}
		}
		
		return null;
	}
	
	public ArrayList<CustomerPO> get(String id, String name){   //name
		Helper help=new Helper();
		ArrayList<CustomerPO> list=new ArrayList<CustomerPO>();
		for(CustomerPO po:customerList){
			if(id.equals("")||help.StringContains(po.getID(), id)){
				if(name.equals("")||help.StringContains(po.getName(), name)){
					list.add(new CustomerPO(po));
				}
			}
		}
		
		return list;
	}
	
	public ArrayList<CustomerPO> getAll(){
		ArrayList<CustomerPO> list=new ArrayList<CustomerPO>();
		for(CustomerPO po:customerList){
			list.add(new CustomerPO(po));
		}
		
		return list;
	}

}
