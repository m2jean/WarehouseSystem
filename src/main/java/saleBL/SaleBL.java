package saleBL;


import inventoryBL.InventoryBL;
import inventoryBLService.InventoryBLService;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import logBL.LogBL;
import businesslogic.messagebl.MessageBL;
import businesslogic.promotionbl.PromotionBL;
import businesslogic.userbl.UserBL;
import businesslogicservice.logblservice.LogBLService;
import businesslogicservice.messageblservice.MessageBLService;
import businesslogicservice.promotionblservice.PromotionBLService;
import businesslogicservice.userblservice.UserBLService;
import productBL.ProductBL;
import productBL.ProductItem;
import productBLService.ProductBLService;
import customerBL.CustomerBL;
import customerBLService.CustomerBLService;
import PO.SalePO;
import VO.CustomerVO;
import VO.LogVO;
import VO.MessageVO;
import VO.PresentVO;
import VO.ProductVO;
import VO.PromotionVO;
import VO.SaleVO;
import VO.SpecialProductVO;
import VO.UserVO;
import enums.DataMessage;
import enums.Operation;
import enums.ResultMessage;
import enums.Status;
import factory.Factory;
import factory.RMIFactory;
import saleBLService.SaleBLService;
import saleDataService.SaleDataService;

public class SaleBL implements SaleBLService{
	
	public ResultMessage addSale(SaleVO vo, PresentVO present) {
		RMIFactory rmi=new RMIFactory();
		Factory factory=new Factory();
		String Date=factory.getDate();
		vo.setDate(Date);
		SalePO po=vo.toPO();
		ResultMessage result=ResultMessage.SUCCESS;
		ArrayList<String> promotion=vo.getPromotion();
		InventoryBLService inventorybl=new InventoryBL();
		PromotionBLService promotionbl=new PromotionBL();
		for(String id:promotion){
			DataMessage<PromotionVO> Time=promotionbl.getPromotion(id);
			if(Time.resultMessage==ResultMessage.SUCCESS){
				PromotionVO promotiondata=Time.data;
				String start=promotiondata.getStartDate();
				String end=promotiondata.getEndDate();
				if((Date.compareTo(start)>=0)&&(Date.compareTo(end)<=0)){
					result=ResultMessage.SUCCESS;
				}else{
					return ResultMessage.DATE_ERROR;
				}
			}else{
				ResultMessage res=Time.resultMessage;
				return res;
			}
		}
		try {
			SaleDataService sale=rmi.newSaleDataService();
			String id=sale.newSale(po);
			if(result==ResultMessage.SUCCESS){
				LogBLService log=new LogBL();
				//Factory factory=new Factory();
				UserBLService user=new UserBL();
				result=log.add(new LogVO(factory.getDate(),user.getCurrent().getName(),Operation.ADD_SALE,id));
			}
			if(present==null){
				vo.setPresent("");
				return result;
			}
			ArrayList<ProductItem> item=present.getProductList();
			if(item.isEmpty()){
				vo.setPresent("");
				return result;
			}
			DataMessage<String> list=inventorybl.newPresentReturnID(present);
			if(list.resultMessage==ResultMessage.SUCCESS){
				String prese=list.data;
				vo.setPresent(prese);
				return result;
			}else{
				return list.resultMessage;
			}
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			return ResultMessage.REMOTE_FAIL;
		}
	}
	
	public DataMessage<SaleVO> fillSale(SaleVO vo){
		//UserBLService user=new UserBLStub();
		PromotionBLService promotion=new PromotionBL();
		//vo.setOperator(user.getCurrent().getName());
		//vo.setStatus(Status.DRAFT);
		ArrayList<SaleLineItem> item=vo.getProductList();
		double total=0;
		for(SaleLineItem i:item){
			i.setMoney(i.getPrice()*i.getNumber());
			total=total+i.getMoney();
		}
		vo.setPreTotal(total);
		DataMessage<SaleVO> result=promotion.getDiscount(vo);
		vo=result.data;
		vo.setPostTotal(vo.getTotal()-vo.getCreditGet()-vo.getDiscount1()-vo.getDiscount2());
		result.data=vo;
		return result;
	}
	
	public DataMessage<SaleVO> getSale(SaleVO vo) {
		SalePO po=vo.toPO();
		RMIFactory rmi=new RMIFactory();
		DataMessage<SaleVO> result=new DataMessage<SaleVO>(ResultMessage.SUCCESS);
		try {
			SaleDataService sale=rmi.newSaleDataService();
			if(sale.get(po)==null){
				result.resultMessage=ResultMessage.ITEM_NOT_EXIST;
				return result;
			}
			result.data=sale.get(po).toVO();
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			result.resultMessage=ResultMessage.REMOTE_FAIL;
			return result;
		}
		return result;
	}
	
	public DataMessage<ArrayList<SaleVO>> AllSale() {
		ArrayList<SalePO> po=new ArrayList<SalePO>();
		RMIFactory rmi=new RMIFactory();
		DataMessage<ArrayList<SaleVO>> result=new DataMessage<ArrayList<SaleVO>>(ResultMessage.SUCCESS);
		try {
			SaleDataService sale=rmi.newSaleDataService();
			po=sale.getAll();
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			result.resultMessage=ResultMessage.REMOTE_FAIL;
			return result;
		}
		if(po.isEmpty()){
			result.resultMessage=ResultMessage.IS_EMPTY;
			return result;
		}
		ArrayList<SaleVO> vo=new ArrayList<SaleVO>();
		for(SalePO i:po){
			vo.add(i.toVO());
		}
		result.data=vo;
		return result;
	}
	public DataMessage<ArrayList<SaleVO>> getSales(String stime, String etime,String good, String cus,
			String yewu, String cangku) {
		RMIFactory rmi=new RMIFactory();
		ArrayList<SalePO> po=new ArrayList<SalePO>();
		DataMessage<ArrayList<SaleVO>> result=new DataMessage<ArrayList<SaleVO>>(ResultMessage.SUCCESS);
		try {
			SaleDataService sale=rmi.newSaleDataService();
			po = sale.find(stime,etime,good,cus,yewu,cangku);
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			result.resultMessage=ResultMessage.REMOTE_FAIL;
			return result;
		}
		if(po.isEmpty()){
			result.resultMessage=ResultMessage.IS_EMPTY;
			return result;
		}
		ArrayList<SaleVO> vo=new ArrayList<SaleVO>();
		for(SalePO i:po){
			vo.add(i.toVO());
		}
		result.data=vo;
		return result;
	}
	
	public ResultMessage updateSales(SaleVO vo) {
		RMIFactory rmi=new RMIFactory();
		SalePO po=vo.toPO();
		try {
			SaleDataService sale=rmi.newSaleDataService();
			ResultMessage result=sale.update(po);
			if(result==ResultMessage.SUCCESS){
				LogBLService log=new LogBL();
				Factory factory=new Factory();
				UserBLService user=new UserBL();
				result=log.add(new LogVO(factory.getDate(),user.getCurrent().getName(),Operation.UPD_SALE,vo.getCustomerID() ));
			}
			return result;
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			return ResultMessage.REMOTE_FAIL;
		}
	}
	
	public ResultMessage shenpi(SaleVO vo){
		RMIFactory rmi=new RMIFactory();
		SalePO po=vo.toPO();
		if(vo.getStatus()==Status.PASS){
			CustomerBLService cus=new CustomerBL();
			DataMessage<CustomerVO> customer=cus.getCustomer(new CustomerVO(vo.getCustomerID()));
			if(customer.resultMessage==ResultMessage.ITEM_NOT_EXIST){
				return ResultMessage.ITEM_NOT_EXIST;
			}
			CustomerVO customerdata=customer.data;
			customerdata.setPayable(customerdata.getPayable()+vo.getPostTotal());
			cus.updateCustomer(customerdata);
			
			ArrayList<SaleLineItem> saleline=vo.getProductList();
			for(SaleLineItem item:saleline){
				ProductBLService pro=new ProductBL();
				if(item.getName().contains("特价包")){
					DataMessage<SpecialProductVO> special=pro.getSpecialProduct(new SpecialProductVO(item.getID()));
					if(special.resultMessage==ResultMessage.SUCCESS){
						SpecialProductVO spedata=special.data;
						if(vo.isHong()){
							spedata.setNumber(spedata.getNumber()-1);
						}else{
							spedata.setNumber(spedata.getNumber()+1);
						}		
						pro.updateSpecialProduct(spedata);
						ArrayList<ProductItem> productitem=spedata.getProductList();
						for(ProductItem i:productitem){
							DataMessage<ProductVO> product=pro.getProduct(new ProductVO(i.getProductID()));
							if(product.resultMessage==ResultMessage.ITEM_NOT_EXIST){
								return ResultMessage.ITEM_NOT_EXIST;
							}
							ProductVO productdata=product.data;
							productdata.setNumber(productdata.getNumber()-i.getNumber());
							pro.updateProduct(productdata);
						}
					}
				}else{
					DataMessage<ProductVO> product=pro.getProduct(new ProductVO(item.getID()));
					if(product.resultMessage==ResultMessage.ITEM_NOT_EXIST){
						return ResultMessage.ITEM_NOT_EXIST;
					}
					ProductVO productdata=product.data;
					if(!vo.isHong()){
						productdata.setLastOut(item.getPrice());
					}
					productdata.setNumber(productdata.getNumber()-item.getNumber());
					pro.updateProduct(productdata);
				}
			}
			
		}
		try {
			SaleDataService sale=rmi.newSaleDataService();
			ResultMessage result=sale.update(po);
			if(result==ResultMessage.SUCCESS){
				LogBLService log=new LogBL();
				Factory factory=new Factory();
				UserBLService user=new UserBL();
				if(vo.getStatus()==Status.PASS){
					result=log.add(new LogVO(factory.getDate(),user.getCurrent().getName(),Operation.PASS_SALE,vo.getID() ));
				}else if(vo.getStatus()==Status.FAIL){
					result=log.add(new LogVO(factory.getDate(),user.getCurrent().getName(),Operation.FAIL_SALE,vo.getID() ));
				}
				MessageBLService message=new MessageBL();
				if(vo.getStatus()==Status.PASS){
					MessageVO mess=new MessageVO(factory.getDate(),factory.messagePass(vo.getID()));
					message.send(new UserVO(vo.getOperator()), mess);
				}else if(vo.getStatus()==Status.FAIL){
					MessageVO mess=new MessageVO(factory.getDate(),factory.messageFail(vo.getID()));
					message.send(new UserVO(vo.getOperator()), mess);
				}
			}
			return result;
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			return ResultMessage.REMOTE_FAIL;
		}
	}

	public DataMessage<ArrayList<SaleVO>> AllDraft() {
		ArrayList<SalePO> po=new ArrayList<SalePO>();
		RMIFactory rmi=new RMIFactory();
		DataMessage<ArrayList<SaleVO>> result=new DataMessage<ArrayList<SaleVO>>(ResultMessage.SUCCESS);
		try {
			SaleDataService sale=rmi.newSaleDataService();
			po=sale.getDraft();
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			result.resultMessage=ResultMessage.REMOTE_FAIL;
			return result;
		}
		if(po.isEmpty()){
			result.resultMessage=ResultMessage.IS_EMPTY;
			return result;
		}
		ArrayList<SaleVO> vo=new ArrayList<SaleVO>();
		for(SalePO i:po){
			vo.add(i.toVO());
		}
		result.data=vo;
		return result;
	}

	public DataMessage<ArrayList<SaleVO>> AllPass() {
		ArrayList<SalePO> po=new ArrayList<SalePO>();
		RMIFactory rmi=new RMIFactory();
		DataMessage<ArrayList<SaleVO>> result=new DataMessage<ArrayList<SaleVO>>(ResultMessage.SUCCESS);
		try {
			SaleDataService sale=rmi.newSaleDataService();
			po=sale.getPass();
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			result.resultMessage=ResultMessage.REMOTE_FAIL;
			return result;
		}
		if(po.isEmpty()){
			result.resultMessage=ResultMessage.IS_EMPTY;
			return result;
		}
		ArrayList<SaleVO> vo=new ArrayList<SaleVO>();
		for(SalePO i:po){
			vo.add(i.toVO());
		}
		result.data=vo;
		return result;
	}

	public ResultMessage delSale(SaleVO vo) {
		RMIFactory rmi=new RMIFactory();
		ResultMessage result=ResultMessage.SUCCESS;
		try{
			SaleDataService Data=rmi.newSaleDataService();
			result=Data.delSale(vo.toPO());
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			e.printStackTrace();
			return ResultMessage.REMOTE_FAIL;
		}
		return result;
	}

	public DataMessage<ArrayList<SaleVO>> getMy(UserVO vo) {
		ArrayList<SalePO> po=new ArrayList<SalePO>();
		RMIFactory rmi=new RMIFactory();
		DataMessage<ArrayList<SaleVO>> result=new DataMessage<ArrayList<SaleVO>>(ResultMessage.SUCCESS);
		try {
			SaleDataService sale=rmi.newSaleDataService();
			po=sale.getMy(vo.getName());
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			result.resultMessage=ResultMessage.REMOTE_FAIL;
			return result;
		}
		if(po.isEmpty()){
			result.resultMessage=ResultMessage.IS_EMPTY;
			return result;
		}
		ArrayList<SaleVO> svo=new ArrayList<SaleVO>();
		for(SalePO i:po){
			svo.add(i.toVO());
		}
		result.data=svo;
		return result;
	}

	public DataMessage<ArrayList<SaleVO>> AllSubmit() {
		ArrayList<SalePO> po=new ArrayList<SalePO>();
		RMIFactory rmi=new RMIFactory();
		DataMessage<ArrayList<SaleVO>> result=new DataMessage<ArrayList<SaleVO>>(ResultMessage.SUCCESS);
		try {
			SaleDataService sale=rmi.newSaleDataService();
			po=sale.getSubmit();
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			result.resultMessage=ResultMessage.REMOTE_FAIL;
			return result;
		}
		if(po.isEmpty()){
			result.resultMessage=ResultMessage.IS_EMPTY;
			return result;
		}
		ArrayList<SaleVO> vo=new ArrayList<SaleVO>();
		for(SalePO i:po){
			vo.add(i.toVO());
		}
		result.data=vo;
		return result;
	}

	public DataMessage<ArrayList<SaleLog>> getMingxi(String stime,
			String etime, String good, String cus, String yewu, String cangku) {
		DataMessage<ArrayList<SaleLog>> result=new DataMessage<ArrayList<SaleLog>>(ResultMessage.SUCCESS);
		DataMessage<ArrayList<SaleVO>> allsale=getSales(stime, etime, good, cus, yewu, cangku);
		if(allsale.resultMessage==ResultMessage.SUCCESS){
			ArrayList<SaleVO> sale=allsale.data;
			ArrayList<SaleLog> log=new ArrayList<SaleLog>();
			if(good.equals("")){
				for(SaleVO i:sale){
					for(SaleLineItem j:i.getProductList()){
						SaleLog salelog=new SaleLog(j.getID(),i.getDate(),j.getName(),j.getSize(),j.getNumber(),j.getPrice(),j.getMoney());
						log.add(salelog);
					}
				}
			}else{
                for(SaleVO i:sale){
                	for(SaleLineItem j:i.getProductList()){
                		if(good.equals(j.getName())){
                			SaleLog salelog=new SaleLog(j.getID(),i.getDate(),j.getName(),j.getSize(),j.getNumber(),j.getPrice(),j.getMoney());
    						log.add(salelog);
                		}
                	}
                }
			}
			result.data=log;
		}else{
			result.resultMessage=allsale.resultMessage;
		}
		return result;
	}

}
