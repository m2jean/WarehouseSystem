package accountData;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import enums.ResultMessage;
import enums.Status;
import factory.Factory;
import PO.AccountPO;
import PO.CheckInPO;
import PO.CheckOutPO;
import PO.CostPO;
import accountDataService.AccountDataService;

public class AccountData extends UnicastRemoteObject implements AccountDataService{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<AccountPO> account=new ArrayList<AccountPO>();
	ArrayList<CheckInPO> checkin=new ArrayList<CheckInPO>();
	ArrayList<CheckOutPO> checkout=new ArrayList<CheckOutPO>();
	ArrayList<CostPO> cost=new ArrayList<CostPO>();
	File fileA=new File("Account.ser");
	File fileI=new File("CheckIn.ser");
	File fileO=new File("CheckOut.ser");
	File fileC=new File("Cost.ser");
	public AccountData() throws RemoteException{
		if(fileA.exists()){
		    try{
			    FileInputStream fis=new FileInputStream(fileA);
			    ObjectInputStream reader=new ObjectInputStream(fis);
			    while(fis.available()>0){
				    AccountPO po=(AccountPO)reader.readObject();
				    account.add(po);
			    }
			    reader.close();
		    }catch(Exception e){
			    e.printStackTrace();
		    }
		}
		if(fileI.exists()){
		    try{
			    FileInputStream fis=new FileInputStream(fileI);
			    ObjectInputStream reader=new ObjectInputStream(fis);
			    while(fis.available()>0){
				    CheckInPO po=(CheckInPO)reader.readObject();
				    checkin.add(po);
			    }
			    reader.close();
		    }catch(Exception e){
			    e.printStackTrace();
		    }
		}
		if(fileO.exists()){
		    try{
			    FileInputStream fis=new FileInputStream(fileO);
			    ObjectInputStream reader=new ObjectInputStream(fis);
			    while(fis.available()>0){
				    CheckOutPO po=(CheckOutPO)reader.readObject();
				    checkout.add(po);
			    }
			    reader.close();
		    }catch(Exception e){
			    e.printStackTrace();
		    }
		}
		if(fileC.exists()){
		    try{
			    FileInputStream fis=new FileInputStream(fileC);
			    ObjectInputStream reader=new ObjectInputStream(fis);
			    while(fis.available()>0){
				    CostPO po=(CostPO)reader.readObject();
				    cost.add(po);
			    }
			    reader.close();
		    }catch(Exception e){
			    e.printStackTrace();
		    }
		}
	}

	public ResultMessage insert(AccountPO po) throws RemoteException{
		po.setID(new Factory().getAccountID());
		for(AccountPO i:account){
			if(po.getID().equals(i.getID())){
				return ResultMessage.ITEM_EXIST;
			}
		}
		account.add(po);
		try{
			ObjectOutputStream writer=new ObjectOutputStream(new FileOutputStream(fileA));
			for(AccountPO i:account){
				writer.writeObject(i);
			}
			writer.close();
		}catch(Exception e){
			e.printStackTrace();
			return ResultMessage.SAVE_FAIL;
		}
		return ResultMessage.SUCCESS;
	}

	public ResultMessage delete(AccountPO po) throws RemoteException{
		ResultMessage result=ResultMessage.ITEM_NOT_EXIST;
		
		AccountPO toremove = null;
		for(AccountPO i:account){
			if(po.getID().equals(i.getID())){
				toremove = i;				
			}
		}
		if(toremove != null){
			account.remove(toremove);
			result=ResultMessage.SUCCESS;
		}
		try{
			ObjectOutputStream writer=new ObjectOutputStream(new FileOutputStream(fileA));
			for(AccountPO i:account){
					writer.writeObject(i);
			}
			writer.close();
		}catch(Exception e){
			e.printStackTrace();
			return ResultMessage.SAVE_FAIL;
		}
		return result;
	}

	public ResultMessage update(AccountPO po) throws RemoteException{
		ResultMessage result=ResultMessage.ITEM_NOT_EXIST;
		for(AccountPO i:account){
			if(i.getID().equals(po.getID())){
				i.setName(po.getName());
				i.setBalance(po.getBalance());
				result=ResultMessage.SUCCESS;
				break;
			}
		}
		try{
			ObjectOutputStream writer=new ObjectOutputStream(new FileOutputStream(fileA));
			for(AccountPO i:account){
				writer.writeObject(i);
			}
			writer.close();
		}catch(Exception e){
			e.printStackTrace();
			return ResultMessage.SAVE_FAIL;
		}
		return result;
	}

	public ArrayList<AccountPO> find(String id,String name) throws RemoteException{
		ArrayList<AccountPO> list = new ArrayList<AccountPO>();
		for(AccountPO i:account){
			if(!id.isEmpty()){
				if(i.getID().equals(id))
					if(i.getName().contains(name))
						list.add(i);
			}
			else
				if(i.getName().contains(name))
					list.add(i);									
		}
		return list;
	}
	
	public AccountPO find(String id) throws RemoteException{
		for(AccountPO po:account)
			if(po.getID().equals(id))
				return po;
		return null;
	}

	public ArrayList<AccountPO> show() {
		ArrayList<AccountPO> po=new ArrayList<AccountPO>();
		for(AccountPO i:account){
			po.add(i.toVO().toPO());
		}
		return po;
	}

	public String newReceipt(CheckInPO po) throws RemoteException{
		Factory factory=new Factory();
		if(po.getStatus()==Status.DRAFT){
			po.setID(factory.getDraftID());
		}else{
			po.setID(factory.getCheckInID());
		}
		String id=po.getID();
		checkin.add(po);
		try{
			ObjectOutputStream writer=new ObjectOutputStream(new FileOutputStream(fileI));
			for(CheckInPO i:checkin){
				writer.writeObject(i);
			}
			writer.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return id;
	}

	public String newPayment(CheckOutPO po) throws RemoteException{
		Factory factory=new Factory();
		if(po.getStatus()==Status.DRAFT){
			po.setID(factory.getDraftID());
		}else{
			po.setID(factory.getCheckOutID());
		}
		String id=po.getID();
		checkout.add(po);
		try{
			ObjectOutputStream writer=new ObjectOutputStream(new FileOutputStream(fileO));
			for(CheckOutPO i:checkout){
				writer.writeObject(i);
			}
			writer.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return id;
	}

	public ResultMessage updateReceipt(CheckInPO po) throws RemoteException{
		ResultMessage result=ResultMessage.ITEM_NOT_EXIST;
		for(CheckInPO i:checkin){
			if(i.getID().equals(po.getID())){
				checkin.remove(i);
				checkin.add(po);
				result=ResultMessage.SUCCESS;
				break;
			}
		}
		try{
			ObjectOutputStream writer=new ObjectOutputStream(new FileOutputStream(fileI));
			for(CheckInPO i:checkin){
					writer.writeObject(i);
			}
			writer.close();
		}catch(Exception e){
			e.printStackTrace();
			return ResultMessage.SAVE_FAIL;
		}
		return result;
	}

	public ResultMessage updatePayment(CheckOutPO po) throws RemoteException{
		ResultMessage result=ResultMessage.ITEM_NOT_EXIST;
		for(CheckOutPO i:checkout){
			if(i.getID().equals(po.getID())){
				checkout.remove(i);
				checkout.add(po);
				result=ResultMessage.SUCCESS;
				break;
			}
		}
		try{
			ObjectOutputStream writer=new ObjectOutputStream(new FileOutputStream(fileO));
			for(CheckOutPO i:checkout){
				writer.writeObject(i);	
			}
			writer.close();
		}catch(Exception e){
			e.printStackTrace();
			return ResultMessage.SAVE_FAIL;
		}
		return result;
	}

	public CheckInPO getR(CheckInPO po) throws RemoteException{
		for(CheckInPO i:checkin){
			if(i.getID().equals(po.getID())){
				return i.toVO().toPO();
			}
		}
		return null;
	}

	public CheckOutPO getP(CheckOutPO po) throws RemoteException{
		for(CheckOutPO i:checkout){
			if(i.getID().equals(po.getID())){
				return i.toVO().toPO();
			}
		}
		return null;
	}

	public ArrayList<CheckInPO> AllReceipt()throws RemoteException {
		ArrayList<CheckInPO> po=new ArrayList<CheckInPO>();
		for(CheckInPO i:checkin){
			if(i.getStatus()!=Status.DRAFT)
				po.add(i.toVO().toPO());
		}
		return po;
	}

	public ArrayList<CheckOutPO> AllPayment() throws RemoteException{
		ArrayList<CheckOutPO> po=new ArrayList<CheckOutPO>();
		for(CheckOutPO i:checkout){
			if(i.getStatus()!= Status.DRAFT)
			po.add(i.toVO().toPO());
		}
		return po;
	}

	public ArrayList<CheckInPO> getR(String stime, String etime, String customer) throws RemoteException{
		ArrayList<CheckInPO> search=new ArrayList<CheckInPO>();
		for(CheckInPO i:checkin){
			CheckInPO j=i.toVO().toPO();
			search.add(j);
		}
		if(!stime.equals("")){
			ArrayList<CheckInPO> del=new ArrayList<CheckInPO>();
			for(CheckInPO i:search){
				int compare=i.getDate().compareTo(stime);
				if(compare<0){
					del.add(i);
				}
			}
			search.removeAll(del);
		}
		if(!etime.equals("")){
			ArrayList<CheckInPO> del=new ArrayList<CheckInPO>();
			for(CheckInPO i:search){
				int compare=i.getDate().compareTo(etime);
				if(compare>0){
					del.add(i);
				}
			}
			search.removeAll(del);
		}
		if(!customer.equals("")){
			ArrayList<CheckInPO> del=new ArrayList<CheckInPO>();
			for(CheckInPO i:search){
				if(!i.getName().equals(customer)){
					del.add(i);
				}
			}
			search.removeAll(del);
		}
		return search;
	}

	public ArrayList<CheckOutPO> getP(String stime, String etime,String customer) throws RemoteException{
		ArrayList<CheckOutPO> search=new ArrayList<CheckOutPO>();
		for(CheckOutPO i:checkout){
			CheckOutPO j=i.toVO().toPO();
			search.add(j);
		}
		if(!stime.equals("")){
			ArrayList<CheckOutPO> del=new ArrayList<CheckOutPO>();
			for(CheckOutPO i:search){
				int compare=i.getDate().compareTo(stime);
				if(compare<0){
					del.add(i);
				}
			}
			search.removeAll(del);
		}
		if(!etime.equals("")){
			ArrayList<CheckOutPO> del=new ArrayList<CheckOutPO>();
			for(CheckOutPO i:search){
				int compare=i.getDate().compareTo(etime);
				if(compare>0){
					del.add(i);
				}
			}
			search.removeAll(del);
		}
		if(!customer.equals("")){
			ArrayList<CheckOutPO> del=new ArrayList<CheckOutPO>();
			for(CheckOutPO i:search){
				if(!i.getName().equals(customer)){
					del.add(i);
				}
			}
			search.removeAll(del);
		}
		return search;
	}

	public ArrayList<CheckInPO> getRDraft() throws RemoteException{
		ArrayList<CheckInPO> result=new ArrayList<CheckInPO>();
		for(CheckInPO i:checkin){
			if(i.getStatus()==Status.DRAFT){
				result.add(i.toVO().toPO());
			}
		}
		return result;
	}

	public ArrayList<CheckOutPO> getPDraft() throws RemoteException{
		ArrayList<CheckOutPO> result=new ArrayList<CheckOutPO>();
		for(CheckOutPO i:checkout){
			if(i.getStatus()==Status.DRAFT){
				result.add(i.toVO().toPO());
			}
		}
		return result;
	}

	public String newCost(CostPO po) throws RemoteException {
		Factory factory=new Factory();
		if(po.getStatus()==Status.DRAFT){
			po.setID(factory.getDraftID());
		}else{
			po.setID(factory.getCostID());
		}
		String id=po.getID();
		cost.add(po);
		try{
			ObjectOutputStream writer=new ObjectOutputStream(new FileOutputStream(fileC));
			for(CostPO i:cost){
				writer.writeObject(i);
			}
			writer.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return id;
	}

	public ResultMessage updateCost(CostPO po) throws RemoteException {
		ResultMessage result=ResultMessage.ITEM_NOT_EXIST;
		for(CostPO i:cost){
			if(i.getID().equals(po.getID())){
				cost.remove(i);
				cost.add(po);
				result=ResultMessage.SUCCESS;
				break;
			}
		}
		try{
			ObjectOutputStream writer=new ObjectOutputStream(new FileOutputStream(fileC));
			for(CostPO i:cost){
					writer.writeObject(i);
			}
			writer.close();
		}catch(Exception e){
			e.printStackTrace();
			return ResultMessage.SAVE_FAIL;
		}
		return result;
	}

	public CostPO getC(CostPO po) throws RemoteException {
		for(CostPO i:cost){
			if(i.getID().equals(po.getID())){
				return i.toVO().toPO();
			}
		}
		return null;
	}

	public ArrayList<CostPO> AllCost() throws RemoteException {
		ArrayList<CostPO> po=new ArrayList<CostPO>();
		for(CostPO i:cost){
			if(i.getStatus()!= Status.DRAFT)
				po.add(i.toVO().toPO());
		}
		return po;
	}

	public ArrayList<CostPO> getC(String stime, String etime)
			throws RemoteException {
		ArrayList<CostPO> search=new ArrayList<CostPO>();
		for(CostPO i:cost){
			CostPO j=i.toVO().toPO();
			search.add(j);
		}
		if(!stime.equals("")){
			ArrayList<CostPO> del=new ArrayList<CostPO>();
			for(CostPO i:search){
				int compare=i.getDate().compareTo(stime);
				if(compare<0){
					del.add(i);
				}
			}
			search.removeAll(del);
		}
		if(!etime.equals("")){
			ArrayList<CostPO> del=new ArrayList<CostPO>();
			for(CostPO i:search){
				int compare=i.getDate().compareTo(etime);
				if(compare>0){
					del.add(i);
				}
			}
			search.removeAll(del);
		}
		return search;
	}

	public ArrayList<CostPO> getCDraft() throws RemoteException {
		ArrayList<CostPO> result=new ArrayList<CostPO>();
		for(CostPO i:cost){
			if(i.getStatus()==Status.DRAFT){
				result.add(i.toVO().toPO());
			}
		}
		return result;
	}

	public ArrayList<CheckInPO> getRPass() throws RemoteException {
		ArrayList<CheckInPO> result=new ArrayList<CheckInPO>();
		for(CheckInPO i:checkin){
			if(i.getStatus()==Status.PASS){
				result.add(i.toVO().toPO());
			}
		}
		return result;
	}

	public ArrayList<CheckOutPO> getPPass() throws RemoteException {
		ArrayList<CheckOutPO> result=new ArrayList<CheckOutPO>();
		for(CheckOutPO i:checkout){
			if(i.getStatus()==Status.PASS){
				result.add(i.toVO().toPO());
			}
		}
		return result;
	}

	public ArrayList<CostPO> getCPass() throws RemoteException {
		ArrayList<CostPO> result=new ArrayList<CostPO>();
		for(CostPO i:cost){
			if(i.getStatus()==Status.PASS){
				result.add(i.toVO().toPO());
			}
		}
		return result;
	}

	public ResultMessage delReceipt(CheckInPO po) throws RemoteException {
		ArrayList<CheckInPO> del=new ArrayList<CheckInPO>();
		for(CheckInPO i:checkin){
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
			checkin.removeAll(del);
			try {
				ObjectOutputStream writer=new ObjectOutputStream(new FileOutputStream(fileI));
				for(CheckInPO i:checkin){
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

	public ResultMessage delPayment(CheckOutPO po) throws RemoteException {
		ArrayList<CheckOutPO> del=new ArrayList<CheckOutPO>();
		for(CheckOutPO i:checkout){
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
			checkout.removeAll(del);
			try {
				ObjectOutputStream writer=new ObjectOutputStream(new FileOutputStream(fileO));
				for(CheckOutPO i:checkout){
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

	public ResultMessage delCost(CostPO po) throws RemoteException {
		ArrayList<CostPO> del=new ArrayList<CostPO>();
		for(CostPO i:cost){
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
			cost.removeAll(del);
			try {
				ObjectOutputStream writer=new ObjectOutputStream(new FileOutputStream(fileC));
				for(CostPO i:cost){
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

	public ArrayList<CheckInPO> getMyI(String name) throws RemoteException {
		ArrayList<CheckInPO> my=new ArrayList<CheckInPO>();
		for(CheckInPO i:checkin){
			if(i.getMe().equals(name)){
				my.add(i.toVO().toPO());
			}
		}
		return my;
	}

	public ArrayList<CheckOutPO> getMyO(String name) throws RemoteException {
		ArrayList<CheckOutPO> my=new ArrayList<CheckOutPO>();
		for(CheckOutPO i:checkout){
			if(i.getMe().equals(name)){
				my.add(i.toVO().toPO());
			}
		}
		return my;
	}

	public ArrayList<CostPO> getMyC(String name) throws RemoteException {
		ArrayList<CostPO> my=new ArrayList<CostPO>();
		for(CostPO i:cost){
			if(i.getMe().equals(name)){
				my.add(i.toVO().toPO());
			}
		}
		return my;
	}

	public ArrayList<CheckInPO> getISubmit() throws RemoteException {
		ArrayList<CheckInPO> result=new ArrayList<CheckInPO>();
		for(CheckInPO i:checkin){
			if(i.getStatus()==Status.SUBMIT){
				result.add(i.toVO().toPO());
			}
		}
		return result;
	}

	public ArrayList<CheckOutPO> getOSubmit() throws RemoteException {
		ArrayList<CheckOutPO> result=new ArrayList<CheckOutPO>();
		for(CheckOutPO i:checkout){
			if(i.getStatus()==Status.SUBMIT){
				result.add(i.toVO().toPO());
			}
		}
		return result;
	}

	public ArrayList<CostPO> getCSubmit() throws RemoteException {
		ArrayList<CostPO> result=new ArrayList<CostPO>();
		for(CostPO i:cost){
			if(i.getStatus()==Status.SUBMIT){
				result.add(i.toVO().toPO());
			}
		}
		return result;
	}

}
