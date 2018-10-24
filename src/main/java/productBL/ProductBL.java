package productBL;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import businesslogic.messagebl.MessageBL;
import businesslogic.userbl.UserBL;
import businesslogicservice.messageblservice.MessageBLService;
import businesslogicservice.userblservice.UserBLService;
import productBLService.ProductBLService;
import productDataService.ProductDataService;
import PO.ProductPO;
import PO.SpecialProductPO;
import VO.MessageVO;
import VO.ProductVO;
import VO.SaleItemVO;
import VO.SpecialProductVO;
import VO.UserVO;
import enums.DataMessage;
import enums.Operation;
import enums.ResultMessage;
import enums.UserPermission;
import factory.Helper;
import factory.RMIFactory;

public class ProductBL implements ProductBLService{
	Helper tool = new Helper();
	
	public ResultMessage addProduct(ProductVO vo){
		try{
			//ProductDataService dataService=new ProductData();
			ProductDataService dataService=new RMIFactory().newProductDataService();
			String id=dataService.add(vo.toPO());
			if(id.equals("ITEM_EXIST")){
				return ResultMessage.ITEM_EXIST;
			}else if(id.equals("CANNOT_ADD")){
				return ResultMessage.CANNOT_ADD;
			}else{
				tool.createLog(Operation.ADD_PRODUCT, id);
				return ResultMessage.SUCCESS;
			}
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			return ResultMessage.REMOTE_FAIL;
		}
	}
	
	public ResultMessage deleteProduct(ProductVO vo){
		try{
			//ProductDataService dataService=new ProductData();
			ProductDataService dataService=new RMIFactory().newProductDataService();
			ResultMessage message = dataService.delete(vo.toPO());
			if(message == ResultMessage.SUCCESS){
				tool.createLog(Operation.DEL_PRODUCT, vo.getID());
			}
			return message;
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			return ResultMessage.REMOTE_FAIL;
		}
	}
	
	public ResultMessage updateProduct(ProductVO vo){
		try{
			//ProductDataService dataService=new ProductData();
			ProductDataService dataService=new RMIFactory().newProductDataService();
			ResultMessage message=dataService.update(vo.toPO());
			if(message==ResultMessage.SUCCESS){
				int number=vo.getNumber();
				int lower=vo.getLowerLimit();
				if(number<lower){
					sendProductMessage(vo);
				}
				tool.createLog(Operation.UPD_PRODUCT, vo.getID());
			}
			
			return message;
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			return ResultMessage.REMOTE_FAIL;
		}
	}
	
	private void sendProductMessage(ProductVO vo){
		MessageBLService messageService=new MessageBL();
		UserBLService userService=new UserBL();
		MessageVO message=new MessageVO("商品"+vo.getID()+"数量低于警戒数量 "+vo.getNumber()+"("
				+"警戒数量："+vo.getLowerLimit()+")");
		ArrayList<UserVO> userList=userService.getAllUser().data;
		for(UserVO user:userList){
			if(user.getPermission()==UserPermission.STOCK_MANAGER){
				messageService.send(user, message);
			}
		}
	}
	
	public DataMessage<ProductVO> getProduct(ProductVO vo){
		try{
			//ProductDataService dataService=new ProductData();
			ProductDataService dataService=new RMIFactory().newProductDataService();
			ProductPO po=dataService.get(vo.toPO());
			if(po==null){
				return new DataMessage<ProductVO>(ResultMessage.ITEM_NOT_EXIST);
			}
			return new DataMessage<ProductVO>(po.toVO());
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			return new DataMessage<ProductVO>(ResultMessage.REMOTE_FAIL);
		}
	}
	
	public DataMessage<ArrayList<ProductVO>> getProduct(String id, String name){       //keyword
		try{
			//ProductDataService dataService=new ProductData();
			ProductDataService dataService=new RMIFactory().newProductDataService();
			ArrayList<ProductPO> poList=dataService.get(id, name);
			ArrayList<ProductVO> voList=new ArrayList<ProductVO>();
			if(poList.size()==0){
				return new DataMessage<ArrayList<ProductVO>>(ResultMessage.ITEM_NOT_EXIST);
			}
		
			for(ProductPO po:poList){
				voList.add(po.toVO());
			}
		
			return new DataMessage<ArrayList<ProductVO>>(voList);

		}catch(RemoteException | MalformedURLException | NotBoundException e){
			return new DataMessage<ArrayList<ProductVO>>(ResultMessage.REMOTE_FAIL);
		}
	}
	
	public DataMessage<ArrayList<ProductVO>> getProductList(){
		try{
			//ProductDataService dataService=new ProductData();
			ProductDataService dataService=new RMIFactory().newProductDataService();
			ArrayList<ProductVO> voList=new ArrayList<ProductVO>();
			ArrayList<ProductPO> poList=dataService.getAllProduct();
			if(poList.size()==0){
				return new DataMessage<ArrayList<ProductVO>>(ResultMessage.ITEM_NOT_EXIST);
			}
		
			for(ProductPO po:poList){
				voList.add(po.toVO());
			}
		
			return new DataMessage<ArrayList<ProductVO>>(voList);
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			return new DataMessage<ArrayList<ProductVO>>(ResultMessage.REMOTE_FAIL);
		}
	}

	public DataMessage<ArrayList<CostChange>> getCostChange(){
		try{
			//ProductDataService dataService=new ProductData();
			ProductDataService dataService=new RMIFactory().newProductDataService();
			ArrayList<CostChange> list=dataService.getCostChange();
			return new DataMessage<ArrayList<CostChange>>(list);
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			return new DataMessage<ArrayList<CostChange>>(ResultMessage.REMOTE_FAIL);
		}
	}

	public ResultMessage addSpecialProduct(SpecialProductVO vo) {
		try{
			//ProductDataService dataService=new ProductData();
			ProductDataService dataService=new RMIFactory().newProductDataService();
			SpecialProductPO po=new SpecialProductPO(null, vo.getName(), vo.getProductList(), vo.getOriginalPrice(), vo.getPrice(), vo.getNumber());
			String id = dataService.addSpecial(po);
			if(id.equals("ITEM_EXIST")){
				return ResultMessage.ITEM_EXIST;
			}else{
				tool.createLog(Operation.ADD_SPECIALPRODUCT, id);
				return ResultMessage.SUCCESS;
			}
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			e.printStackTrace();
			return ResultMessage.REMOTE_FAIL;
		}
	}

	public ResultMessage deleteSpecialProduct(SpecialProductVO vo) {
		try{
			//ProductDataService dataService=new ProductData();
			ProductDataService dataService=new RMIFactory().newProductDataService();
			SpecialProductPO po=new SpecialProductPO(vo.getID(), null, null, 0, 0, 0);
			ResultMessage message = dataService.deleteSpecial(po);
			if(message == ResultMessage.SUCCESS){
				tool.createLog(Operation.DEL_SPECIALPRODUCT, vo.getID());
			}
			return message;
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			return ResultMessage.REMOTE_FAIL;
		}
	}

	public ResultMessage updateSpecialProduct(SpecialProductVO vo) {
		try{
			//ProductDataService dataService=new ProductData();
			ProductDataService dataService=new RMIFactory().newProductDataService();
			SpecialProductPO po=new SpecialProductPO(vo.getID(), vo.getName(), vo.getProductList(), vo.getOriginalPrice(), vo.getPrice(), vo.getNumber());
			ResultMessage message = dataService.updateSpecial(po);
			if(message == ResultMessage.SUCCESS){
				tool.createLog(Operation.UPD_SPECIALPRODUCT, vo.getID());
			}
			return message;
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			return ResultMessage.REMOTE_FAIL;
		}
	}
	
	public DataMessage<SpecialProductVO> getSpecialProduct(SpecialProductVO vo){
		try{
			//ProductDataService dataService=new ProductData();
			ProductDataService dataService=new RMIFactory().newProductDataService();
			SpecialProductPO po=new SpecialProductPO(vo.getID(), null, null, 0, 0, 0);
			SpecialProductPO product = dataService.getSpecial(po);
			if(product == null){
				return new DataMessage<SpecialProductVO>(ResultMessage.ITEM_NOT_EXIST);
			}else{
				return new DataMessage<SpecialProductVO>(product.toVO());
			}
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			return new DataMessage<SpecialProductVO>(ResultMessage.REMOTE_FAIL);
		}
	}

	public DataMessage<ArrayList<SpecialProductVO>> getAllSpecialProduct() {
		try{
			//ProductDataService dataService=new ProductData();
			ProductDataService dataService=new RMIFactory().newProductDataService();
			ArrayList<SpecialProductVO> voList=new ArrayList<SpecialProductVO>();
			ArrayList<SpecialProductPO> poList=dataService.getAllSpecial();
			if(poList.size()==0){
				return new DataMessage<ArrayList<SpecialProductVO>>(ResultMessage.IS_EMPTY);
			}else{
				for(SpecialProductPO po:poList){
					SpecialProductVO vo=new SpecialProductVO(po.getID(), po.getName(),
							po.getProductList(), po.getOriginalPrice(), po.getPrice(), po.getNumber());
					voList.add(vo);
				}
				
				return new DataMessage<ArrayList<SpecialProductVO>>(voList);
			}
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			return new DataMessage<ArrayList<SpecialProductVO>>(ResultMessage.REMOTE_FAIL);
		}
	}
	
	public DataMessage<ArrayList<SaleItemVO>> getAllSaleItem(){
		try{
			//ProductDataService dataService=new ProductData();
			ProductDataService dataService=new RMIFactory().newProductDataService();
			ArrayList<SaleItemVO> list = new ArrayList<SaleItemVO>();
			
			ArrayList<ProductPO> productList = dataService.getAllProduct();
			ArrayList<SpecialProductPO> specialProductList = dataService.getAllSpecial();
			
			for(ProductPO po:productList){
				//SaleItemVO item = new SaleItemVO(po.getID(), po.getName(), po.getModel(), po.getPriceOut(), "");
				list.add(po.toVO());
			}
			
			for(SpecialProductPO po:specialProductList){
				StringBuffer PS = new StringBuffer();
				for(ProductItem p:po.getProductList()){
					PS.append(p.getProductName()+"*"+p.getNumber()+" ");
				}
				//SaleItemVO item = new SaleItemVO(po.getID(), po.getName(), "", po.getPrice(), PS.toString());
				list.add(po.toVO());
			}
			
			if(list.size()==0){
				return new DataMessage<ArrayList<SaleItemVO>>(ResultMessage.ITEM_NOT_EXIST);
			}
			
			return new DataMessage<ArrayList<SaleItemVO>>(list);
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			return new DataMessage<ArrayList<SaleItemVO>>(ResultMessage.REMOTE_FAIL);
		}
	}
	
}
