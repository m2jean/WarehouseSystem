package importBL;

import importBLService.ExportBLService;
import importBLService.ImportBLService;
import importDataService.ExportDataService;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import businesslogic.userbl.UserBL;
import businesslogicservice.userblservice.UserBLService;
import productBL.ProductBL;
import productBLService.ProductBLService;
import customerBL.CustomerBL;
import customerBLService.CustomerBLService;
import PO.ExportPO;
import VO.CustomerVO;
import VO.ExportVO;
import VO.ImportVO;
import VO.ProductVO;
import VO.UserVO;
import enums.DataMessage;
import enums.Operation;
import enums.ResultMessage;
import enums.Status;
import factory.Factory;
import factory.Helper;
import factory.RMIFactory;

public class ExportBL implements ExportBLService{
	Helper tool=new Helper();
	
	public ResultMessage newExport(ExportVO vo){
		try{
			ImportBLService importService=new ImportBL();
			DataMessage<ImportVO> message=importService.getImport(new ImportVO(vo.getImportID()));
			if(message.resultMessage==ResultMessage.ITEM_NOT_EXIST){
				return ResultMessage.ITEM_NOT_EXIST;
			}
			
			Factory factory=new Factory();
			String date=factory.getDate();
			vo.setDate(date);
			
			//ExportDataService dataService=new ExportData();
			ExportDataService dataService=new RMIFactory().newExportDataService();
			String id=dataService.add(vo.toPO());
			if(id.equals("CANNOT_ADD")){
				return ResultMessage.CANNOT_ADD;
			}else{
				tool.createLog(Operation.ADD_EXPORT, id);
				return ResultMessage.SUCCESS;
			}
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			return ResultMessage.REMOTE_FAIL;
		}
	}
	
	public DataMessage<ExportVO> getExport(ExportVO vo){
		try{
			//ExportDataService dataService=new ExportData();
			ExportDataService dataService=new RMIFactory().newExportDataService();
			ExportPO po=dataService.get(vo.toPO());
			if(po==null){
				return new DataMessage<ExportVO>(ResultMessage.ITEM_NOT_EXIST);
			}
			return new DataMessage<ExportVO>(po.toVO());
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			return new DataMessage<ExportVO>(ResultMessage.REMOTE_FAIL);
		}
	}
	
	public DataMessage<ArrayList<ExportVO>> getExport(String startTime, String endTime){
		try{
			//ExportDataService dataService=new ExportData();
			ExportDataService dataService=new RMIFactory().newExportDataService();
			ArrayList<ExportPO> poList=dataService.get(startTime, endTime);
			ArrayList<ExportVO> voList=new ArrayList<ExportVO>();
			if(poList.size()==0){
				return new DataMessage<ArrayList<ExportVO>>(ResultMessage.ITEM_NOT_EXIST);
			}
			
			for(ExportPO po:poList){
				voList.add(po.toVO());
			}
			return new DataMessage<ArrayList<ExportVO>>(voList);
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			return new DataMessage<ArrayList<ExportVO>>(ResultMessage.REMOTE_FAIL);
		}
	}
	
	public DataMessage<ArrayList<ExportVO>> getExportList(){
		try{
			//ExportDataService dataService=new ExportData();
			ExportDataService dataService=new RMIFactory().newExportDataService();
			ArrayList<ExportVO> voList=new ArrayList<ExportVO>();
			ArrayList<ExportPO> poList=dataService.getAllExport();
		
			if(poList.size()==0){
				return new DataMessage<ArrayList<ExportVO>>(ResultMessage.ITEM_NOT_EXIST);
			}
			
			for(ExportPO po:poList){
				voList.add(po.toVO());
			}
			return new DataMessage<ArrayList<ExportVO>>(voList);
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			return new DataMessage<ArrayList<ExportVO>>(ResultMessage.REMOTE_FAIL);
		}
	}
	
	public ResultMessage updateExport(ExportVO vo){
		try{
			//ExportDataService dataService=new ExportData();
			ExportDataService dataService=new RMIFactory().newExportDataService();
			ResultMessage message=dataService.update(vo.toPO());
			if(message!=ResultMessage.SUCCESS){
				return message;
			}else if(vo.getStatus()==Status.PASS){
				updateProduct(vo.getProductList());
				updateCustomer(vo.getCustomerID(), vo.getTotal());
				tool.createLog(Operation.PASS_EXPORT, vo.getID());
			}else{
				tool.createLog(Operation.FAIL_EXPORT, vo.getID());
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
		customer.setReceivable(customer.getReceivable()-total);
		service.updateCustomer(customer);
	}
	
	private void updateProduct(ArrayList<ImportLineItem> list){
		ProductBLService service=new ProductBL();
		for(ImportLineItem item:list){
			ProductVO getvo=new ProductVO(item.getProductID());
			ProductVO product=service.getProduct(getvo).data;
			int number=product.getNumber()-item.getNumber();
			double total=product.getAverage()*product.getNumber()-item.getPrice()*item.getNumber();
			double average=total/number;
			product.setNumber(product.getNumber()-item.getNumber());
			product.setAverage(average);
			service.updateProduct(product);
		}
	}
	
	public DataMessage<ArrayList<ExportVO>> getExport(String startTime, String endTime, String customer,
			String salesman, String warehouse){
		try{
			//ExportDataService dataService=new ExportData();
			ExportDataService dataService=new RMIFactory().newExportDataService();
			ArrayList<ExportVO> voList=new ArrayList<ExportVO>();
			ArrayList<ExportPO> poList=dataService.get(startTime, endTime);
			
			for(ExportPO po:poList){
				if(customer==null||po.getCustomer().equals(customer)){
					if(salesman==null||po.getOperator().equals(salesman)){
						if(warehouse==null||po.getWarehouse().equals(warehouse)){
							voList.add(po.toVO());
						}
					}
				}
			}
		
			if(poList.size()==0){
				return new DataMessage<ArrayList<ExportVO>>(ResultMessage.ITEM_NOT_EXIST);
			}

			return new DataMessage<ArrayList<ExportVO>>(voList);
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			return new DataMessage<ArrayList<ExportVO>>(ResultMessage.REMOTE_FAIL);
		}
	}
	
	public DataMessage<ArrayList<ExportVO>> getDraftByCurrentUser(){
		try{
			//ExportDataService dataService=new ExportData();
			ExportDataService dataService=new RMIFactory().newExportDataService();
			ArrayList<ExportVO> voList=new ArrayList<ExportVO>();
			UserBLService userService=new UserBL();
			UserVO user=userService.getCurrent();
			ArrayList<ExportPO> poList=dataService.getDraft(user.toPO());
			if(poList.size()==0){
				return new DataMessage<ArrayList<ExportVO>>(ResultMessage.ITEM_NOT_EXIST);
			}
			
			for(ExportPO po:poList){
				voList.add(po.toVO());
			}
			
			return new DataMessage<ArrayList<ExportVO>>(voList);
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			return new DataMessage<ArrayList<ExportVO>>(ResultMessage.REMOTE_FAIL);
		}
	}
	
	public DataMessage<ArrayList<ExportVO>> getAllPassed(){
		try{
			//ExportDataService dataService=new ExportData();
			ExportDataService dataService=new RMIFactory().newExportDataService();
			ArrayList<ExportVO> voList=new ArrayList<ExportVO>();
			ArrayList<ExportPO> poList=dataService.getPassed();
			if(poList.size()==0){
				return new DataMessage<ArrayList<ExportVO>>(ResultMessage.ITEM_NOT_EXIST);
			}
			
			for(ExportPO po:poList){
				voList.add(po.toVO());
			}
			
			return new DataMessage<ArrayList<ExportVO>>(voList);
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			return new DataMessage<ArrayList<ExportVO>>(ResultMessage.REMOTE_FAIL);
		}
	}

	public DataMessage<ArrayList<ExportVO>> getExportByCurrentUser() {
		try{
			//ExportDataService dataService=new ExportData();
			ExportDataService dataService=new RMIFactory().newExportDataService();
			ArrayList<ExportVO> voList=new ArrayList<ExportVO>();
			UserBLService userService=new UserBL();
			UserVO user=userService.getCurrent();
			ArrayList<ExportPO> poList=dataService.getAllExport(user.toPO());
			if(poList.size()==0){
				return new DataMessage<ArrayList<ExportVO>>(ResultMessage.ITEM_NOT_EXIST);
			}
			
			for(ExportPO po:poList){
				voList.add(po.toVO());
			}
			
			return new DataMessage<ArrayList<ExportVO>>(voList);
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			return new DataMessage<ArrayList<ExportVO>>(ResultMessage.REMOTE_FAIL);
		}
	}

	public DataMessage<ArrayList<ExportVO>> getAllSubmitted() {
		try{
			//ExportDataService dataService=new ExportData();
			ExportDataService dataService=new RMIFactory().newExportDataService();
			ArrayList<ExportVO> voList=new ArrayList<ExportVO>();
			ArrayList<ExportPO> poList=dataService.getSubmitted();
			if(poList.size()==0){
				return new DataMessage<ArrayList<ExportVO>>(ResultMessage.ITEM_NOT_EXIST);
			}
			
			for(ExportPO po:poList){
				voList.add(po.toVO());
			}
			
			return new DataMessage<ArrayList<ExportVO>>(voList);
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			return new DataMessage<ArrayList<ExportVO>>(ResultMessage.REMOTE_FAIL);
		}
	}

	public ResultMessage updateDraft(ExportVO vo) {
		if(vo.getStatus()!=Status.DRAFT){
			return ResultMessage.CANNOT_UPDATE;
		}
		
		try{
			//ImportDataService dataService=new ImportData();
			ExportDataService dataService=new RMIFactory().newExportDataService();
			ResultMessage message=dataService.update(vo.toPO());
			if(message==ResultMessage.SUCCESS){
				tool.createLog(Operation.UPD_EXPORT, vo.getID());
			}
			return message;
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			return ResultMessage.REMOTE_FAIL;
		}
	}

	public ResultMessage deleteDraft(ExportVO vo) {
		if(vo.getStatus()!=Status.DRAFT){
			return ResultMessage.CANNOT_DELETE;
		}
		
		try{
			//ImportDataService dataService=new ImportData();
			ExportDataService dataService=new RMIFactory().newExportDataService();
			ResultMessage message=dataService.delete(vo.toPO());
			if(message==ResultMessage.SUCCESS){
				tool.createLog(Operation.DEL_EXPORT, vo.getID());
			}
			return message;
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			return ResultMessage.REMOTE_FAIL;
		}
	}

}
