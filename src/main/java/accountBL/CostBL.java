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
import enums.DataMessage;
import enums.Operation;
import enums.ResultMessage;
import enums.Status;
import factory.Factory;
import factory.RMIFactory;
import PO.CostPO;
import VO.AccountVO;
import VO.CostVO;
import VO.ListOutVO;
import VO.LogVO;
import VO.MessageVO;
import VO.UserVO;
import accountBLService.AccountBLService;
import accountBLService.CostBLService;
import accountDataService.AccountDataService;

public class CostBL implements CostBLService{

	public ResultMessage addCost(CostVO vo) {
		RMIFactory rmi=new RMIFactory();
		try {
			AccountDataService Add=rmi.newAccountDataService();
			String id=Add.newCost(vo.toPO());
			ResultMessage result=ResultMessage.SUCCESS;
			LogBLService log=new LogBL();
			Factory factory=new Factory();
			UserBLService user=new UserBL();
			result=log.add(new LogVO(factory.getDate(),user.getCurrent().getName(),Operation.ADD_COST,id));
			return result;
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			return ResultMessage.REMOTE_FAIL;
		}
	}

	public DataMessage<CostVO> getCost(CostVO vo) {
		RMIFactory rmi=new RMIFactory();
		DataMessage<CostVO> result=new DataMessage<CostVO>(ResultMessage.SUCCESS);
		try {
			AccountDataService Get=rmi.newAccountDataService();
			if(Get.getC(vo.toPO())==null){
				result.resultMessage=ResultMessage.ITEM_NOT_EXIST;
				return result;
			}
			result.data=Get.getC(vo.toPO()).toVO();
			return result;
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			result.resultMessage=ResultMessage.REMOTE_FAIL;
			return result;
		}
	}

	public ResultMessage changeCost(CostVO vo) {
		ResultMessage result=ResultMessage.SUCCESS;
		RMIFactory rmi=new RMIFactory();
		Factory factory=new Factory();
		try{
			AccountDataService ser=rmi.newAccountDataService();
			result=ser.updateCost(vo.toPO());
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			e.printStackTrace();
			return ResultMessage.REMOTE_FAIL;
		}
		if(result==ResultMessage.SUCCESS){
			LogBLService log=new LogBL();
			UserBLService user=new UserBL();
			result=log.add(new LogVO(factory.getDate(),user.getCurrent().getName(),Operation.UPD_COST,vo.getID()));
		}
		return result;
	}
	
	public ResultMessage shenpi(CostVO vo){
		ResultMessage result=ResultMessage.SUCCESS;
		RMIFactory rmi=new RMIFactory();
		Factory factory=new Factory();
		try{
			AccountDataService ser=rmi.newAccountDataService();
			result=ser.updateCost(vo.toPO());
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			e.printStackTrace();
			return ResultMessage.REMOTE_FAIL;
		}
		if(vo.getStatus()==Status.PASS&&result==ResultMessage.SUCCESS){
			AccountBLService account=new AccountBL();
			DataMessage<AccountVO> acc=account.findAccount(vo.getAccount());
			if(acc.resultMessage==ResultMessage.ITEM_NOT_EXIST){
				return ResultMessage.ITEM_NOT_EXIST;
			}
			AccountVO accdata=acc.data;
			accdata.setBalance(accdata.getBalance()-vo.getTotal());
		    result=account.updAccount(accdata);
		}
		if(result==ResultMessage.SUCCESS&&vo.getStatus()==Status.PASS){
			LogBLService log=new LogBL();
			UserBLService user=new UserBL();
			result=log.add(new LogVO(factory.getDate(),user.getCurrent().getName(),Operation.PASS_COST,vo.getID()));
		}else if(result==ResultMessage.SUCCESS&&vo.getStatus()==Status.FAIL){
			LogBLService log=new LogBL();
			UserBLService user=new UserBL();
			result=log.add(new LogVO(factory.getDate(),user.getCurrent().getName(),Operation.FAIL_COST,vo.getID()));
		}
		MessageBLService message=new MessageBL();
		if(vo.getStatus()==Status.PASS){
			MessageVO mess=new MessageVO(factory.getDate(),factory.messagePass(vo.getID()));
			result=message.send(new UserVO(vo.getOperator()), mess);
		}else if(vo.getStatus()==Status.FAIL){
			MessageVO mess=new MessageVO(factory.getDate(),factory.messageFail(vo.getID()));
			result=message.send(new UserVO(vo.getOperator()), mess);
		}
		return result;
	}

	public DataMessage<ArrayList<CostVO>> getAllCost() {
		RMIFactory rmi=new RMIFactory();
		DataMessage<ArrayList<CostVO>> result=new DataMessage<ArrayList<CostVO>>(ResultMessage.SUCCESS);
		try {
			AccountDataService All=rmi.newAccountDataService();
			ArrayList<CostPO> po = All.AllCost();
			if(po.isEmpty()){
				result.resultMessage=ResultMessage.IS_EMPTY;
				return result;
			}
			ArrayList<CostVO> vo=new ArrayList<CostVO>();
			for(CostPO i:po){
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

	public DataMessage<ArrayList<CostVO>> getAllDraft() {
		RMIFactory rmi=new RMIFactory();
		DataMessage<ArrayList<CostVO>> result=new DataMessage<ArrayList<CostVO>>(ResultMessage.SUCCESS);
		ArrayList<CostPO> po=new ArrayList<CostPO>();
		try {
			AccountDataService Data=rmi.newAccountDataService();
			po = Data.getCDraft();
			if(po.isEmpty()){
				result.resultMessage=ResultMessage.IS_EMPTY;
				return result;
			}
			ArrayList<CostVO> vo=new ArrayList<CostVO>();
			for(CostPO i:po){
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

	public CostVO fillCost(CostVO vo) {
		ArrayList<ListOutVO> list=vo.getList();
		double total=0;
		for(ListOutVO i:list){
			total=total+i.getMoney();
		}
		vo.setTotal(total);
		UserBLService user=new UserBL();
		vo.setOperator(user.getCurrent().getName());
		return vo;
	}

	public DataMessage<ArrayList<CostVO>> getCheck(String stime, String etime) {
		RMIFactory rmi=new RMIFactory();
		DataMessage<ArrayList<CostVO>> result=new DataMessage<ArrayList<CostVO>>(ResultMessage.SUCCESS);
		ArrayList<CostPO> po=new ArrayList<CostPO>();
		try {
			AccountDataService Data=rmi.newAccountDataService();
			po = Data.getC(stime, etime);
			if(po.isEmpty()){
				result.resultMessage=ResultMessage.IS_EMPTY;
				return result;
			}
			ArrayList<CostVO> vo=new ArrayList<CostVO>();
			for(CostPO i:po){
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

	public DataMessage<ArrayList<CostVO>> AllPass() {
		RMIFactory rmi=new RMIFactory();
		DataMessage<ArrayList<CostVO>> result=new DataMessage<ArrayList<CostVO>>(ResultMessage.SUCCESS);
		ArrayList<CostPO> po=new ArrayList<CostPO>();
		try {
			AccountDataService Data=rmi.newAccountDataService();
			po = Data.getCPass();
			if(po.isEmpty()){
				result.resultMessage=ResultMessage.IS_EMPTY;
				return result;
			}
			ArrayList<CostVO> vo=new ArrayList<CostVO>();
			for(CostPO i:po){
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

	public ResultMessage delCost(CostVO vo) {
		RMIFactory rmi=new RMIFactory();
		ResultMessage result=ResultMessage.SUCCESS;
		try{
			AccountDataService Data=rmi.newAccountDataService();
			result=Data.delCost(vo.toPO());
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			e.printStackTrace();
			return ResultMessage.REMOTE_FAIL;
		}
		return result;
	}

	public DataMessage<ArrayList<CostVO>> getMy(UserVO vo) {
		RMIFactory rmi=new RMIFactory();
		DataMessage<ArrayList<CostVO>> result=new DataMessage<ArrayList<CostVO>>(ResultMessage.SUCCESS);
		ArrayList<CostPO> po=new ArrayList<CostPO>();
		try {
			AccountDataService Data=rmi.newAccountDataService();
			po = Data.getMyC(vo.getName());
			if(po.isEmpty()){
				result.resultMessage=ResultMessage.IS_EMPTY;
				return result;
			}
			ArrayList<CostVO> cvo=new ArrayList<CostVO>();
			for(CostPO i:po){
				cvo.add(i.toVO());
			}
			result.data=cvo;
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			result.resultMessage=ResultMessage.REMOTE_FAIL;
			return result;
		}
		return result;
	}

	public DataMessage<ArrayList<CostVO>> AllSubmit() {
		RMIFactory rmi=new RMIFactory();
		DataMessage<ArrayList<CostVO>> result=new DataMessage<ArrayList<CostVO>>(ResultMessage.SUCCESS);
		ArrayList<CostPO> po=new ArrayList<CostPO>();
		try {
			AccountDataService Data=rmi.newAccountDataService();
			po = Data.getCSubmit();
			if(po.isEmpty()){
				result.resultMessage=ResultMessage.IS_EMPTY;
				return result;
			}
			ArrayList<CostVO> vo=new ArrayList<CostVO>();
			for(CostPO i:po){
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
