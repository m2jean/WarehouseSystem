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
import PO.CheckOutPO;
import VO.AccountVO;
import VO.CheckOutVO;
import VO.CustomerVO;
import VO.ListInVO;
import VO.LogVO;
import VO.MessageVO;
import VO.UserVO;
import accountBLService.AccountBLService;
import accountBLService.CheckOutBLService;
import accountDataService.AccountDataService;

public class CheckOutBL implements CheckOutBLService{

	public ResultMessage addPayment(CheckOutVO vo) {
		RMIFactory rmi=new RMIFactory();
		try {
			Factory factory=new Factory();
			vo.setDate(factory.getDate());
			AccountDataService Add=rmi.newAccountDataService();
			String id=Add.newPayment(vo.toPO());
			ResultMessage result=ResultMessage.SUCCESS;
			LogBLService log=new LogBL();
			UserBLService user=new UserBL();
			result=log.add(new LogVO(factory.getDate(),user.getCurrent().getName(),Operation.ADD_CHECKOUT,id));
			return result;
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			return ResultMessage.REMOTE_FAIL;
		}
	}
	
	/*public CheckOutVO fillReceipt(CheckOutVO vo){
		Factory factory=new Factory();
		UserBLService user=new UserBL();
		double total=0;
		for(ListInVO i:vo.getList()){
		    total=total+i.getMoney();
		}
		vo.setTotal(total);
		vo.setOperator(user.getCurrent().getName());
		vo.setStatus(Status.DRAFT);
		return vo;
	}*/

	public DataMessage<CheckOutVO> getPayment(CheckOutVO vo) {
		RMIFactory rmi=new RMIFactory();
		DataMessage<CheckOutVO> result=new DataMessage<CheckOutVO>(ResultMessage.SUCCESS);
		try {
			AccountDataService Get=rmi.newAccountDataService();
			if(Get.getP(vo.toPO())==null){
				result.resultMessage=ResultMessage.ITEM_NOT_EXIST;
				return result;
			}
			result.data=Get.getP(vo.toPO()).toVO();
			return result;
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			result.resultMessage=ResultMessage.REMOTE_FAIL;
			return result;
		}
	}

	public ResultMessage changePayment(CheckOutVO vo) {
		ResultMessage result=ResultMessage.SUCCESS;
		RMIFactory rmi=new RMIFactory();
		Factory factory=new Factory();
		try {
			AccountDataService Upd=rmi.newAccountDataService();
			result=Upd.updatePayment(vo.toPO());
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			return ResultMessage.REMOTE_FAIL;
		}
		if(result==ResultMessage.SUCCESS){
			LogBLService log=new LogBL();
			UserBLService user=new UserBL();
			result=log.add(new LogVO(factory.getDate(),user.getCurrent().getName(),Operation.UPD_CHECKOUT,vo.getID()));
		}
		return result;
	}
	
	public ResultMessage shenpi(CheckOutVO vo){
		ResultMessage result=ResultMessage.SUCCESS;
		RMIFactory rmi=new RMIFactory();
		Factory factory=new Factory();
		try {
			AccountDataService Upd=rmi.newAccountDataService();
			result=Upd.updatePayment(vo.toPO());
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			return ResultMessage.REMOTE_FAIL;
		}
		AccountBLService acc=new AccountBL();
		if(vo.getStatus()==Status.PASS&&result==ResultMessage.SUCCESS){
			CustomerVO cus=new CustomerVO(vo.getCustomer());
			CustomerBLService customer=new CustomerBL();
			DataMessage<CustomerVO> cust=customer.getCustomer(cus);
			if(cust.resultMessage==ResultMessage.ITEM_NOT_EXIST){
			    result=ResultMessage.ITEM_NOT_EXIST;
			    return result;
			}
			CustomerVO custdata=cust.data;
			custdata.setReceivable(custdata.getReceivable()-vo.getTotal());
			customer.updateCustomer(custdata);
			ArrayList<ListInVO> list=vo.getList();
			for(ListInVO i:list){
				DataMessage<AccountVO> account=acc.findAccount(i.getAccountID());
				if(account.resultMessage==ResultMessage.ITEM_NOT_EXIST){
					return ResultMessage.ITEM_NOT_EXIST;
				}
				AccountVO accountdata=account.data;
			    accountdata.setBalance(accountdata.getBalance()-i.getMoney());
			    acc.updAccount(accountdata);
			}
		}
		if(result==ResultMessage.SUCCESS){
			LogBLService log=new LogBL();
			UserBLService user=new UserBL();
			if(vo.getStatus()==Status.PASS){
				result=log.add(new LogVO(factory.getDate(),user.getCurrent().getName(),Operation.PASS_CHECKOUT,vo.getID()));
			}else if(vo.getStatus()==Status.FAIL){
				result=log.add(new LogVO(factory.getDate(),user.getCurrent().getName(),Operation.FAIL_CHECKOUT,vo.getID()));
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

	public DataMessage<ArrayList<CheckOutVO>> getAllPayment() {
		RMIFactory rmi=new RMIFactory();
		DataMessage<ArrayList<CheckOutVO>> result=new DataMessage<ArrayList<CheckOutVO>>(ResultMessage.SUCCESS);
		ArrayList<CheckOutPO> po;
		try {
			AccountDataService All=rmi.newAccountDataService();
			po = All.AllPayment();
			if(po.isEmpty()){
				result.resultMessage=ResultMessage.IS_EMPTY;
				return result;
			}
			ArrayList<CheckOutVO> vo=new ArrayList<CheckOutVO>();
			for(CheckOutPO i:po){
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

	public DataMessage<ArrayList<CheckOutVO>> getCheck(String stime, String etime,String customer) {
		RMIFactory rmi=new RMIFactory();
		DataMessage<ArrayList<CheckOutVO>> result=new DataMessage<ArrayList<CheckOutVO>>(ResultMessage.SUCCESS);
		ArrayList<CheckOutPO> po;
		try {
			AccountDataService Data=rmi.newAccountDataService();
			po = Data.getP(stime, etime,customer);
			if(po.isEmpty()){
				result.resultMessage=ResultMessage.IS_EMPTY;
				return result;
			}
			ArrayList<CheckOutVO> vo=new ArrayList<CheckOutVO>();
			for(CheckOutPO i:po){
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
	
	public DataMessage<ArrayList<CheckOutVO>> getAllDraft() {
		RMIFactory rmi=new RMIFactory();
		DataMessage<ArrayList<CheckOutVO>> result=new DataMessage<ArrayList<CheckOutVO>>(ResultMessage.SUCCESS);
		ArrayList<CheckOutPO> po;
		try {
			AccountDataService Data=rmi.newAccountDataService();
			po = Data.getPDraft();
			if(po.isEmpty()){
				result.resultMessage=ResultMessage.IS_EMPTY;
				return result;
			}
			ArrayList<CheckOutVO> vo=new ArrayList<CheckOutVO>();
			for(CheckOutPO i:po){
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

	public DataMessage<ArrayList<CheckOutVO>> AllPass() {
		RMIFactory rmi=new RMIFactory();
		DataMessage<ArrayList<CheckOutVO>> result=new DataMessage<ArrayList<CheckOutVO>>(ResultMessage.SUCCESS);
		ArrayList<CheckOutPO> po;
		try {
			AccountDataService Data=rmi.newAccountDataService();
			po = Data.getPPass();
			if(po.isEmpty()){
				result.resultMessage=ResultMessage.IS_EMPTY;
				return result;
			}
			ArrayList<CheckOutVO> vo=new ArrayList<CheckOutVO>();
			for(CheckOutPO i:po){
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

	public ResultMessage delPayment(CheckOutVO vo) {
		RMIFactory rmi=new RMIFactory();
		ResultMessage result=ResultMessage.SUCCESS;
		try{
			AccountDataService Data=rmi.newAccountDataService();
			result=Data.delPayment(vo.toPO());
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			e.printStackTrace();
			return ResultMessage.REMOTE_FAIL;
		}
		return result;
	}

	public DataMessage<ArrayList<CheckOutVO>> getMy(UserVO vo) {
		RMIFactory rmi=new RMIFactory();
		DataMessage<ArrayList<CheckOutVO>> result=new DataMessage<ArrayList<CheckOutVO>>(ResultMessage.SUCCESS);
		ArrayList<CheckOutPO> po;
		try {
			AccountDataService Data=rmi.newAccountDataService();
			po = Data.getMyO(vo.getName());
			if(po.isEmpty()){
				result.resultMessage=ResultMessage.IS_EMPTY;
				return result;
			}
			ArrayList<CheckOutVO> ovo=new ArrayList<CheckOutVO>();
			for(CheckOutPO i:po){
				ovo.add(i.toVO());
			}
			result.data=ovo;
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			result.resultMessage=ResultMessage.REMOTE_FAIL;
			return result;
		}
		return result;
	}

	public DataMessage<ArrayList<CheckOutVO>> AllSubmit() {
		RMIFactory rmi=new RMIFactory();
		DataMessage<ArrayList<CheckOutVO>> result=new DataMessage<ArrayList<CheckOutVO>>(ResultMessage.SUCCESS);
		ArrayList<CheckOutPO> po;
		try {
			AccountDataService Data=rmi.newAccountDataService();
			po = Data.getOSubmit();
			if(po.isEmpty()){
				result.resultMessage=ResultMessage.IS_EMPTY;
				return result;
			}
			ArrayList<CheckOutVO> vo=new ArrayList<CheckOutVO>();
			for(CheckOutPO i:po){
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
