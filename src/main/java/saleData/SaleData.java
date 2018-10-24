package saleData;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.io.*;

import PO.SalePO;
import enums.ResultMessage;
import enums.Status;
import factory.Factory;
import saleBL.SaleLineItem;
import saleDataService.SaleDataService;

public class SaleData extends UnicastRemoteObject implements SaleDataService{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<SalePO> sale=new ArrayList<SalePO>();
	File file=new File("sale.ser");
	public SaleData() throws RemoteException{
		if(file.exists()){
		    try{
			    FileInputStream fis=new FileInputStream(file);
			    ObjectInputStream reader=new ObjectInputStream(fis);
			    while(fis.available()>0){
				    SalePO po=(SalePO)reader.readObject();
				    sale.add(po);
			    }
			    reader.close();
		    }catch(Exception e){
			    e.printStackTrace();
		    }
		}
	}

	public String newSale(SalePO po) throws RemoteException{
		Factory factory=new Factory();
		if(po.getStatus()==Status.DRAFT){
			po.setID(factory.getDraftID());
		}else{
			po.setID(factory.getSaleID());
		}
		String id=po.getID();
		sale.add(po);
		try {
			ObjectOutputStream writer=new ObjectOutputStream(new FileOutputStream(file));
			for(SalePO i:sale){
				writer.writeObject(i);
			}
			writer.close();
		}catch (Exception e){
			e.printStackTrace();
		}
		return id;
	}

	public ArrayList<SalePO> find(String stime,String etime, String good, String cus,
			String yewu, String cangku) throws RemoteException{
		ArrayList<SalePO> search=new ArrayList<SalePO>();
		for(SalePO i:sale){
			SalePO j=i.toVO().toPO();
			search.add(j);
		}
		if(!stime.equals("")){
			ArrayList<SalePO> del=new ArrayList<SalePO>();
			for(SalePO i:search){
				int compare=i.getDate().compareTo(stime);
				if(compare<0){
					del.add(i);
				}
			}
			search.removeAll(del);
		}
		if(!etime.equals("")){
			ArrayList<SalePO> del=new ArrayList<SalePO>();
			for(SalePO i:search){
				int compare=i.getDate().compareTo(etime);
				if(compare>0){
					del.add(i);
				}
			}
			search.removeAll(del);
		}
		if(!good.equals("")){
			ArrayList<SalePO> del=new ArrayList<SalePO>();
			for(SalePO i:search){
				int test=0;
				ArrayList<SaleLineItem> item=i.getList();
				for(SaleLineItem j:item){
					if(j.getName().equals(good)){
						test=1;
					}
				}
				if(test==0){
					del.add(i);
				}
			}
			search.removeAll(del);
		}
		if(!cus.equals("")){
			ArrayList<SalePO> del=new ArrayList<SalePO>();
			for(SalePO i:search){
				if(!i.getName().equals(cus)){
					del.add(i);
				}
			}
			search.removeAll(del);
		}
		if(!yewu.equals("")){
			ArrayList<SalePO> del=new ArrayList<SalePO>();
			for(SalePO i:search){
				if(!i.getSalesman().equals(yewu)){
					del.add(i);
				}
			}
			search.removeAll(del);
		}
		if(!cangku.equals("")){
			ArrayList<SalePO> del=new ArrayList<SalePO>();
			for(SalePO i:search){
				if(!i.getCangku().equals(cangku)){
					del.add(i);
				}
			}
			search.removeAll(del);
		}
		return search;
	}

	public ArrayList<SalePO> getAll() throws RemoteException{
		ArrayList<SalePO> All=new ArrayList<SalePO>();
		for(SalePO i:sale){
			if(i.getStatus()!=Status.DRAFT)
				All.add(i.toVO().toPO());
		}
		return All;
	}

	public SalePO get(SalePO po) throws RemoteException{
		for(SalePO i:sale){
			if(i.getID().equals(po.getID())){
				SalePO res=i.toVO().toPO();
				return res;
			}
		}
		return null;
	}

	public ResultMessage update(SalePO po) throws RemoteException{
		ResultMessage result=ResultMessage.ITEM_NOT_EXIST;
		for(SalePO i:sale){
			if(i.getID().equals(po.getID())){
				sale.remove(i);
				sale.add(po);
				result=ResultMessage.SUCCESS;
				break;
			}
		}
		try{
			ObjectOutputStream writer=new ObjectOutputStream(new FileOutputStream(file));
			for(SalePO i:sale){
				writer.writeObject(i);
			}
			writer.close();
		}catch(Exception e){
			result=ResultMessage.SAVE_FAIL;
			e.printStackTrace();
		}
		return result;
	}

	public ArrayList<SalePO> getDraft() throws RemoteException {
		ArrayList<SalePO> All=new ArrayList<SalePO>();
		for(SalePO i:sale){
			if(i.getStatus()==Status.DRAFT){
				SalePO j=i.toVO().toPO();
				All.add(j);
			}
		}
		return All;
	}

	public ArrayList<SalePO> getPass() throws RemoteException {
		ArrayList<SalePO> All=new ArrayList<SalePO>();
		for(SalePO i:sale){
			if(i.getStatus()==Status.PASS){
				SalePO j=i.toVO().toPO();
				All.add(j);
			}
		}
		return All;
	}

	public ResultMessage delSale(SalePO po) throws RemoteException {
		ArrayList<SalePO> del=new ArrayList<SalePO>();
		for(SalePO i:sale){
			if(i.getID().equals(po.getID())){
				if(i.getStatus()==Status.DRAFT){
					del.add(i);
				}else{
					return ResultMessage.CANNOT_DELETE;
				}
			}
		}
		if(del.isEmpty()){
			return ResultMessage.ITEM_NOT_EXIST;
		}else{
			sale.removeAll(del);
			try {
				ObjectOutputStream writer=new ObjectOutputStream(new FileOutputStream(file));
				for(SalePO i:sale){
					writer.writeObject(i);
				}
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
				return ResultMessage.SAVE_FAIL;
			}
			return ResultMessage.SUCCESS;
		}
	}

	public ArrayList<SalePO> getMy(String name) throws RemoteException {
		ArrayList<SalePO> my=new ArrayList<SalePO>();
		for(SalePO i:sale){
			if(i.getMe().equals(name)){
				my.add(i.toVO().toPO());
			}
		}
		return my;
	}

	public ArrayList<SalePO> getSubmit() throws RemoteException {
		ArrayList<SalePO> All=new ArrayList<SalePO>();
		for(SalePO i:sale){
			if(i.getStatus()==Status.SUBMIT){
				SalePO j=i.toVO().toPO();
				All.add(j);
			}
		}
		return All;
	}

}
