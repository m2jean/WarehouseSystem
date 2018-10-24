package accountBL;

import java.net.MalformedURLException;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import logBL.LogBL;
import businesslogic.messagebl.MessageBL;
import businesslogic.userbl.UserBL;
import businesslogicservice.logblservice.LogBLService;
import businesslogicservice.messageblservice.MessageBLService;
import businesslogicservice.userblservice.UserBLService;
import customerBL.CustomerBL;
import customerBLService.CustomerBLService;
import enums.DataMessage;
import enums.Operation;
import enums.ResultMessage;
import enums.Status;
import factory.Factory;
import factory.RMIFactory;
import PO.CheckInPO;
import VO.AccountVO;
import VO.CheckInVO;
import VO.CustomerVO;
import VO.ListInVO;
import VO.LogVO;
import VO.MessageVO;
import VO.UserVO;
import accountBLService.AccountBLService;
import accountBLService.CheckInBLService;
import accountDataService.AccountDataService;

public class CheckInBL implements CheckInBLService{

	public ResultMessage addReceipt(CheckInVO vo) {
		RMIFactory rmi=new RMIFactory();
		try {
			Factory factory=new Factory();
			String date=factory.getDate();
			vo.setDate(date);
			AccountDataService Add=rmi.newAccountDataService();
			String id=Add.newReceipt(vo.toPO());
			ResultMessage result=ResultMessage.SUCCESS;
			LogBLService log=new LogBL();
			UserBLService user=new UserBL();
			result=log.add(new LogVO(factory.getDate(),user.getCurrent().getName(),Operation.ADD_CHECKIN,id));
			return result;
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			return ResultMessage.REMOTE_FAIL;
		}
	}
	
	/*public CheckInVO fillReceipt(CheckInVO vo){
		Factory factory=new Factory();
		UserBLService user=new UserBL();
		vo.setDate(factory.getDate());
		double total=0;
		for(ListInVO i:vo.getList()){
		    total=total+i.getMoney();
		}
		vo.setTotal(total);
		vo.setOperator(user.getCurrent().getName());
		vo.setStatus(Status.DRAFT);
		return vo;
	}*/

	public DataMessage<CheckInVO> getReceipt(CheckInVO vo) {
		RMIFactory rmi=new RMIFactory();
		DataMessage<CheckInVO> result=new DataMessage<CheckInVO>(ResultMessage.SUCCESS);
		try {
			AccountDataService Get=rmi.newAccountDataService();
			if(Get.getR(vo.toPO())==null){
				result.resultMessage=ResultMessage.ITEM_NOT_EXIST;
				return result;
			}
			result.data=Get.getR(vo.toPO()).toVO();
			return result;
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			result.resultMessage=ResultMessage.REMOTE_FAIL;
			return result;
		}
	}

	public ResultMessage changeReceipt(CheckInVO vo) {
		RMIFactory rmi=new RMIFactory();
		ResultMessage result=ResultMessage.SUCCESS;
		Factory factory=new Factory();
		try {
			AccountDataService Upd=rmi.newAccountDataService();
			result=Upd.updateReceipt(vo.toPO());
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			return ResultMessage.REMOTE_FAIL;
		}
		if(result==ResultMessage.SUCCESS){
			LogBLService log=new LogBL();
			UserBLService user=new UserBL();
			result=log.add(new LogVO(factory.getDate(),user.getCurrent().getName(),Operation.UPD_CHECKIN,vo.getID()));
		}
		return result;
	}
	
	public ResultMessage shenpi(CheckInVO vo){
		RMIFactory rmi=new RMIFactory();
		ResultMessage result=ResultMessage.SUCCESS;
		Factory factory=new Factory();
		try {
			AccountDataService Upd=rmi.newAccountDataService();
			result=Upd.updateReceipt(vo.toPO());
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			return ResultMessage.REMOTE_FAIL;
		}
		if(vo.getStatus()==Status.PASS&&result==ResultMessage.SUCCESS){
			CustomerVO cus=new CustomerVO(vo.getCustomer());
			CustomerBLService customer=new CustomerBL();
			DataMessage<CustomerVO> cust=customer.getCustomer(cus);
			if(cust.resultMessage==ResultMessage.ITEM_NOT_EXIST){
			    result=ResultMessage.ITEM_NOT_EXIST;
			    return result;
			}
			CustomerVO custdata=cust.data;
			custdata.setPayable(custdata.getPayable()-vo.getTotal());
			customer.updateCustomer(custdata);
			ArrayList<ListInVO> list=vo.getList();
			AccountBLService acc=new AccountBL();
			for(ListInVO i:list){
				DataMessage<AccountVO> account=acc.findAccount(i.getAccountID());
				if(account.resultMessage==ResultMessage.ITEM_NOT_EXIST){
					return ResultMessage.ITEM_NOT_EXIST;
				}
				AccountVO accountdata=account.data;
			    accountdata.setBalance(accountdata.getBalance()+i.getMoney());
			    acc.updAccount(accountdata);
			}
		}
		if(result==ResultMessage.SUCCESS){
			LogBLService log=new LogBL();
			UserBLService user=new UserBL();
			if(vo.getStatus()==Status.PASS){
				result=log.add(new LogVO(factory.getDate(),user.getCurrent().getName(),Operation.PASS_CHECKIN,vo.getID()));
			}else if(vo.getStatus()==Status.FAIL){
				result=log.add(new LogVO(factory.getDate(),user.getCurrent().getName(),Operation.FAIL_CHECKIN,vo.getID()));
			}
		}
		MessageBLService message=new MessageBL();
		if(vo.getStatus()==Status.PASS){
			MessageVO mess=new MessageVO(factory.getDate(),factory.messagePass(vo.getID()));
			message.send(new UserVO(vo.getOperator()), mess);
		}else if(vo.getStatus()==Status.FAIL){
			MessageVO mess=new MessageVO(factory.getDate(),factory.messageFail(vo.getID()));
			message.send(new UserVO(vo.getOperator()), mess);
		}
		return result;
	}

	public DataMessage<ArrayList<CheckInVO>> getAllReceipt() {
		RMIFactory rmi=new RMIFactory();
		DataMessage<ArrayList<CheckInVO>> result=new DataMessage<ArrayList<CheckInVO>>(ResultMessage.SUCCESS);
		try {
			AccountDataService All=rmi.newAccountDataService();
			ArrayList<CheckInPO> po = All.AllReceipt();
			if(po.isEmpty()){
				result.resultMessage=ResultMessage.IS_EMPTY;
				return result;
			}
			ArrayList<CheckInVO> vo=new ArrayList<CheckInVO>();
			for(CheckInPO i:po){
				vo.add(i.toVO());
			}
			result.data=vo;
			return result;
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			result.resultMessage=ResultMessage.REMOTE_FAIL;
			return result;
		}
	}

	public DataMessage<ArrayList<CheckInVO>> getCheck(String stime,
			String etime, String customer) {
		RMIFactory rmi=new RMIFactory();
		DataMessage<ArrayList<CheckInVO>> result=new DataMessage<ArrayList<CheckInVO>>(ResultMessage.SUCCESS);
		ArrayList<CheckInPO> po=new ArrayList<CheckInPO>();
		try {
			AccountDataService Data=rmi.newAccountDataService();
			po = Data.getR(stime, etime, customer);
			if(po.isEmpty()){
				result.resultMessage=ResultMessage.IS_EMPTY;
				return result;
			}
			result.data=new ArrayList<CheckInVO>();
			ArrayList<CheckInVO> vo=new ArrayList<CheckInVO>();
			for(CheckInPO i:po){
				vo.add(i.toVO());
			}
			result.data=vo;
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			result.resultMessage=ResultMessage.REMOTE_FAIL;
			return result;
		}
		return result;
	}

	public DataMessage<ArrayList<CheckInVO>> getAllDraft() {
		RMIFactory rmi=new RMIFactory();
		DataMessage<ArrayList<CheckInVO>> result=new DataMessage<ArrayList<CheckInVO>>(ResultMessage.SUCCESS);
		ArrayList<CheckInPO> po=new ArrayList<CheckInPO>();
		try {
			AccountDataService Data=rmi.newAccountDataService();
			po = Data.getRDraft();
			if(po.isEmpty()){
				result.resultMessage=ResultMessage.IS_EMPTY;
				return result;
			}
			ArrayList<CheckInVO> vo=new ArrayList<CheckInVO>();
			for(CheckInPO i:po){
				vo.add(i.toVO());
			}
			result.data=vo;
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			result.resultMessage=ResultMessage.REMOTE_FAIL;
			return result;
		}
		return result;
	}

	public DataMessage<ArrayList<CheckInVO>> AllPass() {
		RMIFactory rmi=new RMIFactory();
		DataMessage<ArrayList<CheckInVO>> result=new DataMessage<ArrayList<CheckInVO>>(ResultMessage.SUCCESS);
		ArrayList<CheckInPO> po=new ArrayList<CheckInPO>();
		try {
			AccountDataService Data=rmi.newAccountDataService();
			po = Data.getRPass();
			if(po.isEmpty()){
				result.resultMessage=ResultMessage.IS_EMPTY;
				return result;
			}
			ArrayList<CheckInVO> vo=new ArrayList<CheckInVO>();
			for(CheckInPO i:po){
				vo.add(i.toVO());
			}
			result.data=vo;
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			result.resultMessage=ResultMessage.REMOTE_FAIL;
			return result;
		}
		return result;
	}

	public ResultMessage delReceipt(CheckInVO vo) {
		RMIFactory rmi=new RMIFactory();
		ResultMessage result=ResultMessage.SUCCESS;
		try{
			AccountDataService Data=rmi.newAccountDataService();
			result=Data.delReceipt(vo.toPO());
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			e.printStackTrace();
			return ResultMessage.REMOTE_FAIL;
		}
		return result;
	}

	public DataMessage<ArrayList<CheckInVO>> getMy(UserVO vo) {
		RMIFactory rmi=new RMIFactory();
		DataMessage<ArrayList<CheckInVO>> result=new DataMessage<ArrayList<CheckInVO>>(ResultMessage.SUCCESS);
		ArrayList<CheckInPO> po=new ArrayList<CheckInPO>();
		try {
			AccountDataService Data=rmi.newAccountDataService();
			po = Data.getMyI(vo.getName());
			if(po.isEmpty()){
				result.resultMessage=ResultMessage.IS_EMPTY;
				return result;
			}
			ArrayList<CheckInVO> ivo=new ArrayList<CheckInVO>();
			for(CheckInPO i:po){
				ivo.add(i.toVO());
			}
			result.data=ivo;
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			result.resultMessage=ResultMessage.REMOTE_FAIL;
			return result;
		}
		return result;
	}

	public DataMessage<ArrayList<CheckInVO>> AllSubmit() {
		RMIFactory rmi=new RMIFactory();
		DataMessage<ArrayList<CheckInVO>> result=new DataMessage<ArrayList<CheckInVO>>(ResultMessage.SUCCESS);
		ArrayList<CheckInPO> po=new ArrayList<CheckInPO>();
		try {
			AccountDataService Data=rmi.newAccountDataService();
			po = Data.getISubmit();
			if(po.isEmpty()){
				result.resultMessage=ResultMessage.IS_EMPTY;
				return result;
			}
			ArrayList<CheckInVO> vo=new ArrayList<CheckInVO>();
			for(CheckInPO i:po){
				vo.add(i.toVO());
			}
			result.data=vo;
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			result.resultMessage=ResultMessage.REMOTE_FAIL;
			return result;
		}
		return result;
	}

}
