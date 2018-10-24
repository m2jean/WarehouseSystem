package businesslogic.promotionbl;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import productBL.ProductItem;
import businesslogicservice.promotionblservice.PromotionBLService;
import PO.PromotionPO;
import VO.PresentVO;
import VO.PromotionVO;
import VO.SaleVO;
import dataservice.promotiondataservice.PromotionDataService;
import enums.DataMessage;
import enums.Operation;
import enums.ResultMessage;
import enums.Status;
import enums.UserPermission;
import enums.VipLevel;
import factory.Factory;
import factory.Helper;
import factory.RMIFactory;

public class PromotionBL implements PromotionBLService{
	Helper tool=new Helper();
	
	public DataMessage<ArrayList<PromotionVO>> get() {
		try {
			//PromotionDataService promotiondata = new PromotionData();
			PromotionDataService promotiondata = new RMIFactory().newPromotionDataService();
			ArrayList<PromotionPO> list = promotiondata.get();
			if(list.size()==0){
				return new DataMessage<ArrayList<PromotionVO>>(ResultMessage.ITEM_NOT_EXIST);
			}
			
			ArrayList<PromotionVO> voList=new ArrayList<PromotionVO>();
			for(PromotionPO promotion:list){
				voList.add(promotion.toVO());
			}
			return new DataMessage<ArrayList<PromotionVO>>(voList);
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			return new DataMessage<ArrayList<PromotionVO>>(ResultMessage.REMOTE_FAIL);
		}
	}

	public DataMessage<ArrayList<PromotionVO>> getUnexpired() {
		try {
			//PromotionDataService promotiondata = new PromotionData();
			PromotionDataService promotiondata = new RMIFactory().newPromotionDataService();
			ArrayList<PromotionPO> list = promotiondata.getUnexpired();
			if(list.size()==0){
				return new DataMessage<ArrayList<PromotionVO>>(ResultMessage.ITEM_NOT_EXIST);
			}
			
			ArrayList<PromotionVO> voList=new ArrayList<PromotionVO>();
			for(PromotionPO promotion:list)
				voList.add(promotion.toVO());
			return new DataMessage<ArrayList<PromotionVO>>(voList);
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			return new DataMessage<ArrayList<PromotionVO>>(ResultMessage.REMOTE_FAIL);
		}
	}
	
	public DataMessage<ArrayList<PromotionVO>> getUnexpired(VipLevel level, double total) {
		try {
			//PromotionDataService promotiondata = new PromotionData();
			PromotionDataService promotiondata = new RMIFactory().newPromotionDataService();
			ArrayList<PromotionPO> list = promotiondata.getUnexpired(level, total);
			if(list.size()==0){
				return new DataMessage<ArrayList<PromotionVO>>(ResultMessage.ITEM_NOT_EXIST);
			}
			
			ArrayList<PromotionVO> voList=new ArrayList<PromotionVO>();
			for(PromotionPO promotion:list)
				voList.add(promotion.toVO());
			return new DataMessage<ArrayList<PromotionVO>>(voList);
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			return new DataMessage<ArrayList<PromotionVO>>(ResultMessage.REMOTE_FAIL);
		}
	}

	public ResultMessage add(PromotionVO promotion) {
		try {
			//PromotionDataService promotiondata = new PromotionData();
			PromotionDataService promotiondata = new RMIFactory().newPromotionDataService();
			String date=new Factory().getDate();
			promotion.setID(date);
			String id=promotiondata.add(promotion.toPO());
			if(id.equals("ITEM_EXISTS")){
				return ResultMessage.ITEM_EXIST;
			}else{
				tool.createLog(Operation.ADD_PROMOTION, id);
				return ResultMessage.SUCCESS;
			}
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			return ResultMessage.REMOTE_FAIL;
		}
	}
	
	public ResultMessage delete(PromotionVO promotion){
		try {
			//PromotionDataService promotiondata = new PromotionData();
			PromotionDataService promotiondata = new RMIFactory().newPromotionDataService();
			ResultMessage message=promotiondata.delete(promotion.toPO());
			if(message==ResultMessage.SUCCESS){
				tool.createLog(Operation.DEL_PROMOTION, promotion.getID());
			}
			return message;
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			return ResultMessage.REMOTE_FAIL;
		}
	}

	public ResultMessage update(PromotionVO promotion) {
		try {
			//PromotionDataService promotiondata = new PromotionData();
			PromotionDataService promotiondata = new RMIFactory().newPromotionDataService();
			ResultMessage message=promotiondata.update(promotion.toPO());
			if(message==ResultMessage.SUCCESS){
				tool.createLog(Operation.UPD_PROMOTION, promotion.getID());
			}
			return message;
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			return ResultMessage.REMOTE_FAIL;
		}
	}
	
	public DataMessage<SaleVO> getDiscount(SaleVO sale){
		ArrayList<PromotionVO> promotionList=new ArrayList<PromotionVO>();
		try{
			//PromotionDataService promotiondata = new PromotionData();
			PromotionDataService promotiondata = new RMIFactory().newPromotionDataService();
			ArrayList<String> idList=sale.getPromotion();
			for(String id:idList){
				PromotionPO po=promotiondata.get(id);
				if(po!=null){
					promotionList.add(po.toVO());
				}
			}
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			return new DataMessage<SaleVO>(ResultMessage.REMOTE_FAIL);
		}
		
		if(promotionList.size()==0){
			return new DataMessage<SaleVO>(sale);
		}else{
			ArrayList<Integer> percent=new ArrayList<Integer>();
			double credit2=0;
			double discount2=0;
			for(PromotionVO promotion:promotionList){
				credit2+=promotion.getCoupon();
				discount2+=promotion.getDiscount();
				if(promotion.getPercent()!=0){
					percent.add(promotion.getPercent());
				}
			}
			
			double total=sale.getTotal();
			double remainTotal=total-discount2;
			
			for(int p:percent){
				remainTotal=remainTotal*p/100;
			}
			
			sale.setCreditGive(credit2);
			sale.setDiscount2(total-remainTotal);
			
			return new DataMessage<SaleVO>(sale);
		}
	}
	
	public DataMessage<PromotionVO> getPromotion(String id){
		try {
			//PromotionDataService promotiondata = new PromotionData();
			PromotionDataService promotiondata = new RMIFactory().newPromotionDataService();
			PromotionPO promotion=promotiondata.get(id);
			if(promotion!=null){
				return new DataMessage<PromotionVO>(promotion.toVO());
			}else{
				return new DataMessage<PromotionVO>(ResultMessage.ITEM_NOT_EXIST);
			}
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			return new DataMessage<PromotionVO>(ResultMessage.REMOTE_FAIL);
		}
	}
	
	public DataMessage<PresentVO> getPresent(VipLevel level, double total, String salesman){
		try {
			//PromotionDataService promotiondata = new PromotionData();
			PromotionDataService promotiondata = new RMIFactory().newPromotionDataService();
			ArrayList<PromotionPO> list = promotiondata.getUnexpired(level, total);
			if(list.size()==0){
				return new DataMessage<PresentVO>(ResultMessage.ITEM_NOT_EXIST);
			}
			
			ArrayList<ProductItem> itemList=new ArrayList<ProductItem>();
			for(PromotionPO promotion:list){
				if(promotion.getGift()!=null){
					itemList.addAll(promotion.getGift().getProductList());
				}
			}
			
			if(itemList.size()==0){
				return new DataMessage<PresentVO>(ResultMessage.ITEM_NOT_EXIST);
			}
			
			ArrayList<ProductItem> newItemList=mergeProductItem(itemList);
			String date=new Factory().getDate();
			PresentVO present=new PresentVO(date, null, newItemList, salesman, Status.DRAFT);
			return new DataMessage<PresentVO>(present);
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			return new DataMessage<PresentVO>(ResultMessage.REMOTE_FAIL);
		}
	}
	
	private ArrayList<ProductItem> mergeProductItem(ArrayList<ProductItem> list){
		ArrayList<ProductItem> itemList=new ArrayList<ProductItem>();
		for(ProductItem item1:list){
			boolean exists=false;
			for(ProductItem item2:itemList){
				if(item1.getProductID().equals(item2.getProductID())){
					exists=true;
					int number=item1.getNumber()+item2.getNumber();
					item2.setNumber(number);
					break;
				}
			}
			
			if(!exists){
				itemList.add(item1);
			}
		}
		
		return itemList;
	}
	
	public DataMessage<Integer> getDiscountPermission(UserPermission user){
		try{
			//PromotionDataService promotiondata = new PromotionData();
			PromotionDataService promotiondata = new RMIFactory().newPromotionDataService();
			int discount=promotiondata.getDiscountPermission(user);
			return new DataMessage<Integer>(discount);
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			return new DataMessage<Integer>(ResultMessage.REMOTE_FAIL);
		}
	}
	
	public ResultMessage updateDiscountPermission(int[] array){
		try{
			//PromotionDataService promotiondata = new PromotionData();
			PromotionDataService promotiondata = new RMIFactory().newPromotionDataService();
			return promotiondata.updateDiscountPermission(array);
		}catch(Exception e){
			return ResultMessage.REMOTE_FAIL;
		}
	}
}
