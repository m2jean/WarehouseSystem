package importBL;

import importBLService.ImportBLService;
import importDataService.ImportDataService;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import businesslogic.messagebl.MessageBL;
import businesslogic.userbl.UserBL;
import businesslogicservice.messageblservice.MessageBLService;
import businesslogicservice.userblservice.UserBLService;
import productBL.ProductBL;
import productBLService.ProductBLService;
import customerBL.CustomerBL;
import customerBLService.CustomerBLService;
import PO.ImportPO;
import VO.CustomerVO;
import VO.ImportVO;
import VO.MessageVO;
import VO.ProductVO;
import VO.UserVO;
import enums.DataMessage;
import enums.Operation;
import enums.ResultMessage;
import enums.Status;
import factory.Factory;
import factory.Helper;
import factory.RMIFactory;

public class ImportBL implements ImportBLService{
	Helper tool=new Helper();
	
	public ResultMessage newImport(ImportVO vo){
		try{
			Factory factory=new Factory();
			String date=factory.getDate();
			vo.setDate(date);
			//ImportDataService dataService=new ImportData();
			ImportDataService dataService=new RMIFactory().newImportDataService();
			String id=dataService.add(vo.toPO());
			if(id.equals("CANNOT_ADD")){
				return ResultMessage.CANNOT_ADD;
			}else{
				tool.createLog(Operation.ADD_IMPORT, id);
				return ResultMessage.SUCCESS;
			}
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			return ResultMessage.REMOTE_FAIL;
		}
	}
	
	public DataMessage<ImportVO> getImport(ImportVO vo){
		try{
			//ImportDataService dataService=new ImportData();
			ImportDataService dataService=new RMIFactory().newImportDataService();
			ImportPO po=dataService.get(vo.toPO());
			if(po==null){
				return new DataMessage<ImportVO>(ResultMessage.ITEM_NOT_EXIST);
			}
			return new DataMessage<ImportVO>(po.toVO());
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			return new DataMessage<ImportVO>(ResultMessage.REMOTE_FAIL);
		}
	}
	
	public DataMessage<ArrayList<ImportVO>> getImport(String startTime, String endTime){
		try{
			//ImportDataService dataService=new ImportData();
			ImportDataService dataService=new RMIFactory().newImportDataService();
			ArrayList<ImportPO> poList=dataService.get(startTime, endTime);
			ArrayList<ImportVO> voList=new ArrayList<ImportVO>();
			
			if(poList.size()==0){
				return new DataMessage<ArrayList<ImportVO>>(ResultMessage.ITEM_NOT_EXIST);
			}
			
			for(ImportPO po:poList){
				voList.add(po.toVO());
			}
			
			return new DataMessage<ArrayList<ImportVO>>(voList);
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			return new DataMessage<ArrayList<ImportVO>>(ResultMessage.REMOTE_FAIL);
		}
	}
	
	public DataMessage<ArrayList<ImportVO>> getImportList(){
		try{
			//ImportDataService dataService=new ImportData();
			ImportDataService dataService=new RMIFactory().newImportDataService();
			ArrayList<ImportVO> voList=new ArrayList<ImportVO>();
			ArrayList<ImportPO> poList=dataService.getAllImport();
		
			if(poList.size()==0){
				return new DataMessage<ArrayList<ImportVO>>(ResultMessage.ITEM_NOT_EXIST);
			}
			
			for(ImportPO po:poList){
				voList.add(po.toVO());
			}
			return new DataMessage<ArrayList<ImportVO>>(voList);
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			return new DataMessage<ArrayList<ImportVO>>(ResultMessage.REMOTE_FAIL);
		}
	}
	
	public DataMessage<ArrayList<ImportVO>> getImportByCurrentUser(){
		try{
			//ImportDataService dataService=new ImportData();
			ImportDataService dataService=new RMIFactory().newImportDataService();
			UserBLService userService=new UserBL();
			UserVO user=userService.getCurrent();
			ArrayList<ImportVO> voList=new ArrayList<ImportVO>();
			ArrayList<ImportPO> poList=dataService.getAllImport(user.toPO());
		
			if(poList.size()==0){
				return new DataMessage<ArrayList<ImportVO>>(ResultMessage.ITEM_NOT_EXIST);
			}
			
			for(ImportPO po:poList){
				voList.add(po.toVO());
			}
			return new DataMessage<ArrayList<ImportVO>>(voList);
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			return new DataMessage<ArrayList<ImportVO>>(ResultMessage.REMOTE_FAIL);
		}
	}
	
	public ResultMessage updateImport(ImportVO vo){
		try{
			//ImportDataService dataService=new ImportData();
			ImportDataService dataService=new RMIFactory().newImportDataService();
			MessageBLService service=new MessageBL();
			Factory factory=new Factory();
			ResultMessage message=dataService.update(vo.toPO());
			if(message!=ResultMessage.SUCCESS){
				return message;
			}else if(vo.getStatus()==Status.PASS){
				updateProduct(vo.getProductList(), vo.isHong());
				updateCustomer(vo.getCustomerID(), vo.getTotal());
				UserVO user=new UserVO(vo.getOperator());
				String pass=factory.messagePass(vo.getID());
				MessageVO passMessage=new MessageVO(factory.getDate(), pass);
				service.send(user, passMessage);
				tool.createLog(Operation.PASS_IMPORT, vo.getID());
			}else{
				UserVO user=new UserVO(vo.getOperator());
				String fail=factory.messageFail(vo.getID());
				MessageVO failMessage=new MessageVO(factory.getDate(), fail);
				service.send(user, failMessage);
				tool.createLog(Operation.FAIL_IMPORT, vo.getID());
			}
			return ResultMessage.SUCCESS;
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			return ResultMessage.REMOTE_FAIL;
		}
	}
	
	private void updateCustomer(String id, double total){
		CustomerBLService service=new CustomerBL();
		CustomerVO getvo=new CustomerVO(id);
		CustomerVO customer=service.getCustomer(getvo).data;
		customer.setReceivable(customer.getReceivable()+total);
		service.updateCustomer(customer);
	}
	
	private void updateProduct(ArrayList<ImportLineItem> list, boolean isHong){
		ProductBLService service=new ProductBL();
		for(ImportLineItem item:list){
			ProductVO getvo=new ProductVO(item.getProductID());
			ProductVO product=service.getProduct(getvo).data;
			int number=product.getNumber()+item.getNumber();
			double total=item.getPrice()*item.getNumber()+product.getAverage()*product.getNumber();
			double average=total/number;
			if(!isHong){
				product.setLastIn(item.getPrice());
			}
			product.setNumber(number);
			product.setAverage(average);
			service.updateProduct(product);
		}
	}
	
	public DataMessage<ArrayList<ImportVO>> getImport(String startTime, String endTime, String customer,
			String salesman, String warehouse){
		try{
			//ImportDataService dataService=new ImportData();
			ImportDataService dataService=new RMIFactory().newImportDataService();
			ArrayList<ImportVO> voList=new ArrayList<ImportVO>();
			ArrayList<ImportPO> poList=dataService.get(startTime, endTime);
			
			for(ImportPO po:poList){
				if(customer==null||po.getProvider().equals(customer)){
					if(salesman==null||po.getOperator().equals(salesman)){
						if(warehouse==null||po.getWarehouse().equals(warehouse)){
							voList.add(po.toVO());
						}
					}
				}
			}
		
			if(poList.size()==0){
				return new DataMessage<ArrayList<ImportVO>>(ResultMessage.ITEM_NOT_EXIST);
			}

			return new DataMessage<ArrayList<ImportVO>>(voList);
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			return new DataMessage<ArrayList<ImportVO>>(ResultMessage.REMOTE_FAIL);
		}
	}
	
	
	public DataMessage<ArrayList<ImportVO>> getAllSubmitted(){
		try{
			//ImportDataService dataService=new ImportData();
			ImportDataService dataService=new RMIFactory().newImportDataService();
			ArrayList<ImportVO> voList=new ArrayList<ImportVO>();
			ArrayList<ImportPO> poList=dataService.getSubmitted();
			if(poList.size()==0){
				return new DataMessage<ArrayList<ImportVO>>(ResultMessage.ITEM_NOT_EXIST);
			}
			
			for(ImportPO po:poList){
				voList.add(po.toVO());
			}
			
			return new DataMessage<ArrayList<ImportVO>>(voList);
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			return new DataMessage<ArrayList<ImportVO>>(ResultMessage.REMOTE_FAIL);
		}
	}
	
	public DataMessage<ArrayList<ImportVO>> getDraftByCurrentUser(){
		try{
			//ImportDataService dataService=new ImportData();
			ImportDataService dataService=new RMIFactory().newImportDataService();
			UserBLService userService=new UserBL();
			UserVO user=userService.getCurrent();
			ArrayList<ImportVO> voList=new ArrayList<ImportVO>();
			ArrayList<ImportPO> poList=dataService.getDraft(user.toPO());
			if(poList.size()==0){
				return new DataMessage<ArrayList<ImportVO>>(ResultMessage.ITEM_NOT_EXIST);
			}
			
			for(ImportPO po:poList){
				voList.add(po.toVO());
			}
			
			return new DataMessage<ArrayList<ImportVO>>(voList);
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			return new DataMessage<ArrayList<ImportVO>>(ResultMessage.REMOTE_FAIL);
		}
	}
	
	public DataMessage<ArrayList<ImportVO>> getAllPassed(){
		try{
			//ImportDataService dataService=new ImportData();
			ImportDataService dataService=new RMIFactory().newImportDataService();
			ArrayList<ImportVO> voList=new ArrayList<ImportVO>();
			ArrayList<ImportPO> poList=dataService.getPassed();
			if(poList.size()==0){
				return new DataMessage<ArrayList<ImportVO>>(ResultMessage.ITEM_NOT_EXIST);
			}
			
			for(ImportPO po:poList){
				voList.add(po.toVO());
			}
			
			return new DataMessage<ArrayList<ImportVO>>(voList);
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			return new DataMessage<ArrayList<ImportVO>>(ResultMessage.REMOTE_FAIL);
		}
	}
	
	public ResultMessage updateDraft(ImportVO vo){
		if(vo.getStatus()!=Status.DRAFT){
			return ResultMessage.CANNOT_UPDATE;
		}
		
		try{
			//ImportDataService dataService=new ImportData();
			ImportDataService dataService=new RMIFactory().newImportDataService();
			ResultMessage message=dataService.update(vo.toPO());
			if(message==ResultMessage.SUCCESS){
				tool.createLog(Operation.UPD_IMPORT, vo.getID());
			}
			return message;
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			return ResultMessage.REMOTE_FAIL;
		}
	}
	
	public ResultMessage deleteDraft(ImportVO vo){
		if(vo.getStatus()!=Status.DRAFT){
			return ResultMessage.CANNOT_DELETE;
		}
		
		try{
			//ImportDataService dataService=new ImportData();
			ImportDataService dataService=new RMIFactory().newImportDataService();
			ResultMessage message=dataService.delete(vo.toPO());
			if(message==ResultMessage.SUCCESS){
				tool.createLog(Operation.DEL_IMPORT, vo.getID());
			}
			return message;
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			return ResultMessage.REMOTE_FAIL;
		}
	}

}
