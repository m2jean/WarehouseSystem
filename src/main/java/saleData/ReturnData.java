package saleData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import PO.ReturnPO;
import enums.ResultMessage;
import enums.Status;
import factory.Factory;
import saleBL.SaleLineItem;
import saleDataService.ReturnDataService;

public class ReturnData extends UnicastRemoteObject implements ReturnDataService{
	private static final long serialVersionUID = -191887149960639578L;
	ArrayList<ReturnPO> returns=new ArrayList<ReturnPO>();
	File file=new File("return.ser");
	public ReturnData() throws RemoteException{
		if(file.exists()){
		    try{
			    FileInputStream fis=new FileInputStream(file);
			    ObjectInputStream reader=new ObjectInputStream(fis);
			    while(fis.available()>0){
				    ReturnPO po=(ReturnPO)reader.readObject();
				    returns.add(po);
			    }
			    reader.close();
		    }catch(Exception e){
			    e.printStackTrace();
		    }
		}
	}

	public String newReturn(ReturnPO po) throws RemoteException{
		Factory factory=new Factory();
		if(po.getStatus()==Status.DRAFT){
			po.setID(factory.getDraftID());
		}else{
			po.setID(factory.getReturnID());
		}
		String id=po.getID();
		returns.add(po);
		try {
			ObjectOutputStream writer=new ObjectOutputStream(new FileOutputStream(file));
			for(ReturnPO i:returns){
				writer.writeObject(i);
			}
			writer.close();
		}catch (Exception e){
			e.printStackTrace();
		}
		return id;
	}

	public ReturnPO get(ReturnPO po) throws RemoteException{
		for(ReturnPO i:returns){
			if(i.getID().equals(po.getID())){
				ReturnPO res=i.toVO().toPO();
				return res;
			}
		}
		return null;
	}

	public ArrayList<ReturnPO> find(String stime, String etime, String good,
			String cus, String yewu, String cangku)throws RemoteException {
		ArrayList<ReturnPO> search=new ArrayList<ReturnPO>();
		for(ReturnPO i:returns){
			ReturnPO j=i.toVO().toPO();
			search.add(j);
		}
		if(!stime.equals("")){
			ArrayList<ReturnPO> del=new ArrayList<ReturnPO>();
			for(ReturnPO i:search){
				int compare=i.getDate().compareTo(stime);
				if(compare<0){
					del.add(i);
				}
			}
			search.removeAll(del);
		}
		if(!etime.equals("")){
			ArrayList<ReturnPO> del=new ArrayList<ReturnPO>();
			for(ReturnPO i:search){
				int compare=i.getDate().compareTo(etime);
				if(compare>0){
					del.add(i);
				}
			}
			search.removeAll(del);
		}
		if(!good.equals("")){
			ArrayList<ReturnPO> del=new ArrayList<ReturnPO>();
			for(ReturnPO i:search){
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
			ArrayList<ReturnPO> del=new ArrayList<ReturnPO>();
			for(ReturnPO i:search){
				if(!i.getName().equals(cus)){
					del.add(i);
				}
			}
			search.removeAll(del);
		}
		if(!yewu.equals("")){
			ArrayList<ReturnPO> del=new ArrayList<ReturnPO>();
			for(ReturnPO i:search){
				if(!i.getSalesman().equals(yewu)){
					del.add(i);
				}
			}
			search.removeAll(del);
		}
		if(!cangku.equals("")){
			ArrayList<ReturnPO> del=new ArrayList<ReturnPO>();
			for(ReturnPO i:search){
				if(!i.getCangku().equals(cangku)){
					del.add(i);
				}
			}
			search.removeAll(del);
		}
		return search;
	}

	public ArrayList<ReturnPO> getAll() throws RemoteException{
		ArrayList<ReturnPO> All=new ArrayList<ReturnPO>();
		for(ReturnPO i:returns){
			if(i.getStatus()!=Status.DRAFT)
			All.add(i.toVO().toPO());
		}
		return All;
	}

	public ResultMessage update(ReturnPO po) throws RemoteException{
		ResultMessage result=ResultMessage.ITEM_NOT_EXIST;
		for(ReturnPO i:returns){
			if(i.getID().equals(po.getID())){
				returns.remove(i);
				returns.add(po);
				result=ResultMessage.SUCCESS;
				break;
			}
		}
		try{
			ObjectOutputStream writer=new ObjectOutputStream(new FileOutputStream(file));
			for(ReturnPO i:returns){
				writer.writeObject(i);
			}
			writer.close();
		}catch(Exception e){
			result=ResultMessage.SAVE_FAIL;
			e.printStackTrace();
		}
		return result;
	}
	
	public ArrayList<ReturnPO> getDraft() throws RemoteException {
		ArrayList<ReturnPO> All=new ArrayList<ReturnPO>();
		for(ReturnPO i:returns){
			if(i.getStatus()==Status.DRAFT){
				ReturnPO j=i.toVO().toPO();
				All.add(j);
			}
		}
		return All;
	}

	public ArrayList<ReturnPO> getPass() throws RemoteException {
		ArrayList<ReturnPO> All=new ArrayList<ReturnPO>();
		for(ReturnPO i:returns){
			if(i.getStatus()==Status.PASS){
				ReturnPO j=i.toVO().toPO();
				All.add(j);
			}
		}
		return All;
	}

	public ResultMessage delReturn(ReturnPO po) throws RemoteException {
		ArrayList<ReturnPO> del=new ArrayList<ReturnPO>();
		for(ReturnPO i:returns){
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
			returns.removeAll(del);
			try {
				ObjectOutputStream writer=new ObjectOutputStream(new FileOutputStream(file));
				for(ReturnPO i:returns){
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

	public ArrayList<ReturnPO> getMy(String name) throws RemoteException {
		ArrayList<ReturnPO> my=new ArrayList<ReturnPO>();
		for(ReturnPO i:returns){
			if(i.getMe().equals(name)){
				my.add(i.toVO().toPO());
			}
		}
		return my;
	}

	public ArrayList<ReturnPO> getSubmit() throws RemoteException {
		ArrayList<ReturnPO> All=new ArrayList<ReturnPO>();
		for(ReturnPO i:returns){
			if(i.getStatus()==Status.SUBMIT){
				ReturnPO j=i.toVO().toPO();
				All.add(j);
			}
		}
		return All;
	}

}
