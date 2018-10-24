package saleBL;

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
import productBL.ProductBL;
import productBL.ProductItem;
import productBLService.ProductBLService;
import customerBL.CustomerBL;
import customerBLService.CustomerBLService;
import PO.ReturnPO;
import VO.CustomerVO;
import VO.LogVO;
import VO.MessageVO;
import VO.ProductVO;
import VO.ReturnVO;
import VO.SaleVO;
import VO.SpecialProductVO;
import VO.UserVO;
import enums.DataMessage;
import enums.Operation;
import enums.ResultMessage;
import enums.Status;
import factory.Factory;
import factory.RMIFactory;
import saleBLService.ReturnBLService;
import saleBLService.SaleBLService;
import saleDataService.ReturnDataService;

public class ReturnBL implements ReturnBLService{

	public ResultMessage addReturn(ReturnVO vo) {
		RMIFactory rmi=new RMIFactory();
		ResultMessage result=ResultMessage.SUCCESS;
		SaleBLService salebl=new SaleBL();
		DataMessage<SaleVO> salevo=salebl.getSale(new SaleVO(vo.getSaleid()));
		int test=0;
		if(salevo.resultMessage==ResultMessage.SUCCESS){
			SaleVO sale=salevo.data;
			ArrayList<SaleLineItem> saleline=sale.getProductList();
			ArrayList<SaleLineItem> returnline=vo.getProductList();
			for(SaleLineItem i:returnline){
				for(SaleLineItem j:saleline){
					if(i.getID().equals(j.getID())){
						test=1;
						if(i.getNumber()>j.getNumber()){
							return ResultMessage.CANNOT_ADD;
						}
						break;
					}
				}
				if(test==0){
					return ResultMessage.CANNOT_ADD;
				}
				test=0;
			}
		}else{
			return ResultMessage.CANNOT_ADD;
		}
		try {
			ReturnDataService Add=rmi.newReturnDataService();
			String id=Add.newReturn(vo.toPO());
			if(result==ResultMessage.SUCCESS){
				LogBLService log=new LogBL();
				Factory factory=new Factory();
				UserBLService user=new UserBL();
				result=log.add(new LogVO(factory.getDate(),user.getCurrent().getName(),Operation.ADD_RETURN,id));
			}
			return result;
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			return ResultMessage.REMOTE_FAIL;
		}
	}
	
	public ReturnVO fillReturn(ReturnVO vo){
		UserBLService user=new UserBL();
		Factory factory=new Factory();
		vo.setDate(factory.getDate());
		vo.setOperator(user.getCurrent().getName());
		vo.setStatus(Status.DRAFT);
		ArrayList<SaleLineItem> item=vo.getProductList();
		double total=0;
		for(SaleLineItem i:item){
			i.setMoney(i.getNumber()*i.getPrice());
			total=total+i.getMoney();
		}
		vo.setProductList(item);
		vo.setTotal(total);
		vo.setID(factory.getDate());
		return vo;
	}

	public DataMessage<ReturnVO> getReturn(ReturnVO vo) {
		RMIFactory rmi=new RMIFactory();
		DataMessage<ReturnVO> result=new DataMessage<ReturnVO>(ResultMessage.SUCCESS);
		try {
			ReturnDataService Get=rmi.newReturnDataService();
			if(Get.get(vo.toPO())==null){
				result.resultMessage=ResultMessage.ITEM_NOT_EXIST;
				return result;
			}
			result.data=Get.get(vo.toPO()).toVO();
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			result.resultMessage=ResultMessage.REMOTE_FAIL;
			return result;
		}
		return result;
	}

	public DataMessage<ArrayList<ReturnVO>> AllReturn() {
		RMIFactory rmi=new RMIFactory();
		DataMessage<ArrayList<ReturnVO>> result=new DataMessage<ArrayList<ReturnVO>>(ResultMessage.SUCCESS);
		ArrayList<ReturnPO> po=new ArrayList<ReturnPO>();
		try {
			ReturnDataService All=rmi.newReturnDataService();
			po = All.getAll();
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			result.resultMessage=ResultMessage.REMOTE_FAIL;
			return result;
		}
		if(po.isEmpty()){
			result.resultMessage=ResultMessage.IS_EMPTY;
			return result;
		}
	    ArrayList<ReturnVO> vo=new ArrayList<ReturnVO>();
	    for(ReturnPO i:po){
	    	vo.add(i.toVO());
	    }
	    result.data=vo;
		return result;
	}

	public DataMessage<ArrayList<ReturnVO>> getReturns(String stime, String etime,String good, String cus,
			String yewu, String cangku) {
		RMIFactory rmi=new RMIFactory();
		DataMessage<ArrayList<ReturnVO>> result=new DataMessage<ArrayList<ReturnVO>>(ResultMessage.SUCCESS);
		ArrayList<ReturnPO> po=new ArrayList<ReturnPO>();
		try {
			ReturnDataService All=rmi.newReturnDataService();
			po = All.find(stime,etime,good,cus,yewu,cangku);
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			result.resultMessage=ResultMessage.REMOTE_FAIL;
			return result;
		}
		if(po.isEmpty()){
			result.resultMessage=ResultMessage.IS_EMPTY;
			return result;
		}
		ArrayList<ReturnVO> vo=new ArrayList<ReturnVO>();
	    for(ReturnPO i:po){
	    	vo.add(i.toVO());
	    }
	    result.data=vo;
		return result;
	}

	public ResultMessage updateReturn(ReturnVO vo) {
		RMIFactory rmi=new RMIFactory();
		Factory factory=new Factory();
		ResultMessage result=ResultMessage.SUCCESS;
		try {
			ReturnDataService All=rmi.newReturnDataService();
			result=All.update(vo.toPO());
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			return ResultMessage.REMOTE_FAIL;
		}
		if(result==ResultMessage.SUCCESS){
			LogBLService log=new LogBL();
			UserBLService user=new UserBL();
			result=log.add(new LogVO(factory.getDate(),user.getCurrent().getName(),Operation.UPD_RETURN,vo.getCustomerID() ));
		}
		return result;
	}
	
	public ResultMessage shenpi(ReturnVO vo){
		RMIFactory rmi=new RMIFactory();
		Factory factory=new Factory();
		ResultMessage result=ResultMessage.SUCCESS;
		try {
			ReturnDataService All=rmi.newReturnDataService();
			result=All.update(vo.toPO());
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			return ResultMessage.REMOTE_FAIL;
		}
		if(vo.getStatus()==Status.PASS&&result==ResultMessage.SUCCESS){
			CustomerBLService cus=new CustomerBL();
			DataMessage<CustomerVO> customer=cus.getCustomer(new CustomerVO(vo.getCustomerID()));
			if(customer.resultMessage==ResultMessage.ITEM_NOT_EXIST){
				return ResultMessage.ITEM_NOT_EXIST;
			}
			CustomerVO customerdata=customer.data;
			customerdata.setReceivable(customerdata.getReceivable()+vo.getTotal());
			cus.updateCustomer(customerdata);
			
			ArrayList<SaleLineItem> saleline=vo.getProductList();
			for(SaleLineItem item:saleline){
				ProductBLService pro=new ProductBL();
				if(item.getName().contains("特价包")){
					DataMessage<SpecialProductVO> special=pro.getSpecialProduct(new SpecialProductVO(item.getID()));
					if(special.resultMessage==ResultMessage.SUCCESS){
						SpecialProductVO spedata=special.data;
						if(vo.isHong()){
							spedata.setNumber(spedata.getNumber()+1);
						}else{
							spedata.setNumber(spedata.getNumber()-1);
						}
						result=pro.updateSpecialProduct(spedata);
						ArrayList<ProductItem> productitem=spedata.getProductList();
						for(ProductItem i:productitem){
							DataMessage<ProductVO> product=pro.getProduct(new ProductVO(i.getProductID()));
							if(product.resultMessage==ResultMessage.ITEM_NOT_EXIST){
								return ResultMessage.ITEM_NOT_EXIST;
							}
							ProductVO productdata=product.data;
							productdata.setNumber(productdata.getNumber()+i.getNumber());
							pro.updateProduct(productdata);
						}
					}
				}else{
					DataMessage<ProductVO> product=pro.getProduct(new ProductVO(item.getID()));
					if(product.resultMessage==ResultMessage.ITEM_NOT_EXIST){
						return ResultMessage.ITEM_NOT_EXIST;
					}
					ProductVO productdata=product.data;
					productdata.setNumber(productdata.getNumber()+item.getNumber());
					pro.updateProduct(productdata);
				}
			}
		}
		if(result==ResultMessage.SUCCESS){
			LogBLService log=new LogBL();
			UserBLService user=new UserBL();
			if(vo.getStatus()==Status.PASS){
				result=log.add(new LogVO(factory.getDate(),user.getCurrent().getName(),Operation.PASS_RETURN,vo.getID() ));
			}else if(vo.getStatus()==Status.FAIL){
				result=log.add(new LogVO(factory.getDate(),user.getCurrent().getName(),Operation.FAIL_RETURN,vo.getID() ));
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

	public DataMessage<ArrayList<ReturnVO>> AllDraft() {
		RMIFactory rmi=new RMIFactory();
		DataMessage<ArrayList<ReturnVO>> result=new DataMessage<ArrayList<ReturnVO>>(ResultMessage.SUCCESS);
		ArrayList<ReturnPO> po=new ArrayList<ReturnPO>();
		try {
			ReturnDataService All=rmi.newReturnDataService();
			po = All.getDraft();
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			result.resultMessage=ResultMessage.REMOTE_FAIL;
			return result;
		}
		if(po.isEmpty()){
			result.resultMessage=ResultMessage.IS_EMPTY;
			return result;
		}
	    ArrayList<ReturnVO> vo=new ArrayList<ReturnVO>();
	    for(ReturnPO i:po){
	    	vo.add(i.toVO());
	    }
	    result.data=vo;
		return result;
	}

	public DataMessage<ArrayList<ReturnVO>> AllPass() {
		RMIFactory rmi=new RMIFactory();
		DataMessage<ArrayList<ReturnVO>> result=new DataMessage<ArrayList<ReturnVO>>(ResultMessage.SUCCESS);
		ArrayList<ReturnPO> po=new ArrayList<ReturnPO>();
		try {
			ReturnDataService All=rmi.newReturnDataService();
			po = All.getPass();
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			result.resultMessage=ResultMessage.REMOTE_FAIL;
			return result;
		}
		if(po.isEmpty()){
			result.resultMessage=ResultMessage.IS_EMPTY;
			return result;
		}
	    ArrayList<ReturnVO> vo=new ArrayList<ReturnVO>();
	    for(ReturnPO i:po){
	    	vo.add(i.toVO());
	    }
	    result.data=vo;
		return result;
	}

	public ResultMessage delReturn(ReturnVO vo) {
		RMIFactory rmi=new RMIFactory();
		ResultMessage result=ResultMessage.SUCCESS;
		try{
			ReturnDataService Data=rmi.newReturnDataService();
			result=Data.delReturn(vo.toPO());
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			e.printStackTrace();
			return ResultMessage.REMOTE_FAIL;
		}
		return result;
	}

	public DataMessage<ArrayList<ReturnVO>> getMy(UserVO vo) {
		RMIFactory rmi=new RMIFactory();
		DataMessage<ArrayList<ReturnVO>> result=new DataMessage<ArrayList<ReturnVO>>(ResultMessage.SUCCESS);
		ArrayList<ReturnPO> po=new ArrayList<ReturnPO>();
		try {
			ReturnDataService All=rmi.newReturnDataService();
			po = All.getMy(vo.getName());
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			result.resultMessage=ResultMessage.REMOTE_FAIL;
			return result;
		}
		if(po.isEmpty()){
			result.resultMessage=ResultMessage.IS_EMPTY;
			return result;
		}
	    ArrayList<ReturnVO> rvo=new ArrayList<ReturnVO>();
	    for(ReturnPO i:po){
	    	rvo.add(i.toVO());
	    }
	    result.data=rvo;
		return result;
	}

	public DataMessage<ArrayList<ReturnVO>> AllSubmit() {
		RMIFactory rmi=new RMIFactory();
		DataMessage<ArrayList<ReturnVO>> result=new DataMessage<ArrayList<ReturnVO>>(ResultMessage.SUCCESS);
		ArrayList<ReturnPO> po=new ArrayList<ReturnPO>();
		try {
			ReturnDataService All=rmi.newReturnDataService();
			po = All.getSubmit();
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			result.resultMessage=ResultMessage.REMOTE_FAIL;
			return result;
		}
		if(po.isEmpty()){
			result.resultMessage=ResultMessage.IS_EMPTY;
			return result;
		}
	    ArrayList<ReturnVO> vo=new ArrayList<ReturnVO>();
	    for(ReturnPO i:po){
	    	vo.add(i.toVO());
	    }
	    result.data=vo;
		return result;
	}
	
}
