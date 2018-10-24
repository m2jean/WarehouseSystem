package accountBL;

import java.net.MalformedURLException;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;


import java.util.ArrayList;

import businesslogic.userbl.UserBL;
import businesslogicservice.logblservice.LogBLService;
import businesslogicservice.userblservice.UserBLService;
import logBL.LogBL;
import enums.DataMessage;
import enums.ResultMessage;
import enums.Operation;
import factory.Factory;
import factory.RMIFactory;
import PO.AccountPO;
import VO.AccountVO;
import VO.CheckInVO;
import VO.CheckOutVO;
import VO.ListInVO;
import VO.LogVO;
import accountBLService.AccountBLService;
import accountBLService.CheckInBLService;
import accountBLService.CheckOutBLService;
import accountDataService.AccountDataService;

public class AccountBL implements AccountBLService{

	public DataMessage<Double> getBalance(AccountVO vo) {
		RMIFactory rmi=new RMIFactory();
		try {
			AccountDataService data=rmi.newAccountDataService();
			AccountPO result = data.find(vo.getID());
			if(result==null)
				return new DataMessage<Double>(ResultMessage.ITEM_NOT_EXIST);
			else	
				return new DataMessage<Double>(result.getBalance());
		} catch (RemoteException | MalformedURLException | NotBoundException  e) {
			e.printStackTrace();
			return new DataMessage<Double>(ResultMessage.REMOTE_FAIL);
		}
	}

	public ResultMessage addAccount(AccountVO vo) {
		RMIFactory rmi=new RMIFactory();
		try {
			AccountDataService data=rmi.newAccountDataService();
			ResultMessage result=data.insert(vo.toPO());
			if(result==ResultMessage.SUCCESS){
				LogBLService log=new LogBL();
				Factory factory=new Factory();
				UserBLService user=new UserBL();
				String name=user.getCurrent().getName();
				result=log.add(new LogVO(factory.getDate(),name,Operation.ADD_ACCOUNT,vo.getName() ));
			}
			return result;
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			return ResultMessage.REMOTE_FAIL;
		}
	}

	public ResultMessage delAccount(AccountVO vo) {
		RMIFactory rmi=new RMIFactory();
		CheckInBLService checkin=new CheckInBL();
		DataMessage<ArrayList<CheckInVO>> draftin=checkin.getAllDraft();
		if(draftin.resultMessage==ResultMessage.SUCCESS){
	        ArrayList<CheckInVO> draftindata=draftin.data;
			for(CheckInVO i:draftindata){
				for(ListInVO j:i.getList()){
					if(j.getAccountID().equals(vo.getID())){
						return ResultMessage.CANNOT_DELETE;
					}
				}
			}
		}
		CheckOutBLService checkout=new CheckOutBL();
		DataMessage<ArrayList<CheckOutVO>> draftout=checkout.getAllDraft();
		if(draftout.resultMessage==ResultMessage.SUCCESS){
			ArrayList<CheckOutVO> draftoutdata=draftout.data;
			for(CheckOutVO i:draftoutdata){
				for(ListInVO j:i.getList()){
					if(j.getAccountID().equals(vo.getID())){
						return ResultMessage.CANNOT_DELETE;
					}
				}
			}
		}
		try {
			AccountDataService data=rmi.newAccountDataService();
			ResultMessage result=data.delete(vo.toPO());
			if(result==ResultMessage.SUCCESS){
				LogBLService log=new LogBL();
				Factory factory=new Factory();
				UserBLService user=new UserBL();
				result=log.add(new LogVO(factory.getDate(),user.getCurrent().getName(),Operation.DEL_ACCOUNT,vo.getID() ));
			}
			return result;
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			return ResultMessage.REMOTE_FAIL;
		}
	}

	public ResultMessage updAccount(AccountVO vo) {
		RMIFactory rmi=new RMIFactory();
		try {
			AccountDataService data=rmi.newAccountDataService();
			ResultMessage result=data.update(vo.toPO());
			if(result==ResultMessage.SUCCESS){
				LogBLService log=new LogBL();
				Factory factory=new Factory();
				UserBLService user=new UserBL();
				log.add(new LogVO(factory.getDate(),user.getCurrent().getName(),Operation.UPD_ACCOUNT,vo.getID()));
			}
			return result;
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			return ResultMessage.REMOTE_FAIL;
		}
	}

	public DataMessage<ArrayList<AccountVO>> findAccount(String id,String name) {
		RMIFactory rmi=new RMIFactory();
		try {
			AccountDataService data=rmi.newAccountDataService();
			ArrayList<AccountPO> result = data.find(id,name);
			if(result.isEmpty())
				return new DataMessage<ArrayList<AccountVO>>(ResultMessage.IS_EMPTY);
			else{
				ArrayList<AccountVO> vo = new ArrayList<AccountVO>();
				for(AccountPO po:result)
					vo.add(po.toVO());
				return new DataMessage<ArrayList<AccountVO>>(vo);
			}
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			return new DataMessage<ArrayList<AccountVO>>(ResultMessage.REMOTE_FAIL);
		}
	}
	
	public DataMessage<AccountVO> findAccount(String id){
		RMIFactory rmi=new RMIFactory();

		try {
			AccountDataService data=rmi.newAccountDataService();
			AccountPO result = data.find(id);
			if(result==null)
				return new DataMessage<AccountVO>(ResultMessage.ITEM_NOT_EXIST);
			else{
				return new DataMessage<AccountVO>(result.toVO());
			}
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			return new DataMessage<AccountVO>(ResultMessage.REMOTE_FAIL);
		}
	}
	
	public DataMessage<ArrayList<AccountVO>> getAll(){
		RMIFactory rmi=new RMIFactory();

		try {
			AccountDataService data=rmi.newAccountDataService();
			ArrayList<AccountPO> result = data.show();
			if(result.isEmpty())
				return new DataMessage<ArrayList<AccountVO>>(ResultMessage.IS_EMPTY);
			
			ArrayList<AccountVO> vo=new ArrayList<AccountVO>();
			for(AccountPO i:result){
				vo.add(i.toVO());
			}

			return new DataMessage<ArrayList<AccountVO>>(vo);
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			return new DataMessage<ArrayList<AccountVO>>(ResultMessage.REMOTE_FAIL);
		}
	}

}
