package inventoryBL;

import importBL.ExportBL;
import importBL.ImportBL;
import importBL.ImportLineItem;
import inventoryBLService.InventoryBLService;
import inventoryData.InventoryData;
import inventoryDataService.InventoryDataService;

import java.rmi.RemoteException;
import java.util.ArrayList;

import businesslogic.messagebl.MessageBL;
import businesslogic.userbl.UserBL;
import businesslogicservice.messageblservice.MessageBLService;
import productBL.ProductBL;
import productBL.ProductItem;
import productBLService.ProductBLService;
import saleBL.ReturnBL;
import saleBL.SaleBL;
import saleBL.SaleLineItem;
import PO.OverflowPO;
import PO.PresentPO;
import VO.ExportVO;
import VO.ImportVO;
import VO.InventoryVO;
import VO.MessageVO;
import VO.OverflowVO;
import VO.PresentVO;
import VO.ProductVO;
import VO.ReturnVO;
import VO.SaleVO;
import VO.SnapshotVO;
import VO.UserVO;
import enums.DataMessage;
import enums.Operation;
import enums.ResultMessage;
import enums.Status;
import factory.ExportToExcel;
import factory.Factory;
import factory.Helper;
import gui.inventory.InventoryImportItem;
import gui.inventory.InventoryLineItem;
import gui.inventory.InventorySaleItem;
import gui.inventory.InventorySumItem;

public class InventoryBL implements InventoryBLService{
	Helper tool=new Helper();
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public DataMessage<InventoryVO> check(String start, String end){
		ArrayList<InventoryLineItem> inventoryList=new ArrayList<InventoryLineItem>();
		ArrayList<InventorySumItem> sumList = new ArrayList<InventorySumItem>();
		int numIn = 0;
		double sumIn = 0;
		int numOut = 0;
		double sumOut = 0;

		DataMessage<ArrayList<ImportVO>> importList=new ImportBL().getImport(start,end);
		DataMessage<ArrayList<ExportVO>> exportList=new ExportBL().getExport(start,end);	
		DataMessage<ArrayList<SaleVO>> salesList=new SaleBL().getSales(start,end,"","","","");
		DataMessage<ArrayList<ReturnVO>> returnList=new ReturnBL().getReturns(start,end,"","","","");

		if(importList.resultMessage == ResultMessage.REMOTE_FAIL)
			return new DataMessage(ResultMessage.REMOTE_FAIL);
		else if(importList.resultMessage != ResultMessage.SUCCESS)
			importList.data = new ArrayList<ImportVO>(0);
		if(exportList.resultMessage == ResultMessage.REMOTE_FAIL)
			return new DataMessage(ResultMessage.REMOTE_FAIL);
		else if(exportList.resultMessage != ResultMessage.SUCCESS)
			exportList.data = new ArrayList<ExportVO>(0);
		if(salesList.resultMessage == ResultMessage.REMOTE_FAIL)
			return new DataMessage(ResultMessage.REMOTE_FAIL);
		else if(salesList.resultMessage != ResultMessage.SUCCESS)
			salesList.data = new ArrayList<SaleVO>(0);
		if(returnList.resultMessage == ResultMessage.REMOTE_FAIL)
			return new DataMessage(ResultMessage.REMOTE_FAIL);
		else if(returnList.resultMessage != ResultMessage.SUCCESS)
			returnList.data = new ArrayList<ReturnVO>(0);
		
		for(ImportVO vo:importList.data){
			for(ImportLineItem it:vo.getProductList()){
				InventoryLineItem item = new InventoryImportItem(it);
				item.setDate(vo.getDate());
				item.setOpt("进货");
				item.setId(vo.getID());
				inventoryList.add(item);
				
				addToSum(sumList,item);
			}
		}
		for(ExportVO vo:exportList.data){
			for(ImportLineItem it:vo.getProductList()){
				InventoryLineItem item = new InventoryImportItem(it);
				item.setDate(vo.getDate());
				item.setOpt("退货");
				item.setId(vo.getID());
				inventoryList.add(item);
				
				addToSum(sumList,item);
			}
		}
		for(SaleVO vo:salesList.data){
			for(SaleLineItem it:vo.getProductList()){
				InventoryLineItem item = new InventorySaleItem(it);
				item.setDate(vo.getDate());
				item.setOpt("销售");
				item.setId(vo.getID());
				inventoryList.add(item);
				
				addToSum(sumList,item);
			}
		}
		for(ReturnVO vo:returnList.data){
			for(SaleLineItem it:vo.getProductList()){
				InventoryLineItem item = new InventorySaleItem(it);
				item.setDate(vo.getDate());
				item.setOpt("销售退货");
				item.setId(vo.getID());
				inventoryList.add(item);
				
				addToSum(sumList,item);
			}
		}
		
		for(InventorySumItem item:sumList){
			numIn += item.getNumIn();
			sumIn += item.getSumIn();
			numOut += item.getNumOut();
			sumOut += item.getSumOut();
		}
		
		return new DataMessage<InventoryVO>(new InventoryVO(inventoryList,sumList, numIn, sumIn, numOut, sumOut));
	}
	
	private void addToSum(ArrayList<InventorySumItem> sumlist,InventoryLineItem item){
		boolean added = false;
		for(InventorySumItem sumitem:sumlist){
			if(sumitem.compareToItem(item) == 0){
				sumitem.calculateItem(item);
				added = true;
				break;
			}
		}
		if(!added)
			sumlist.add(new InventorySumItem(item));
	}
	
	public DataMessage<SnapshotVO> stocking(){
		ProductBLService productService=new ProductBL();
		DataMessage<ArrayList<ProductVO>> productMessage=productService.getProductList();
		if(productMessage.resultMessage!=ResultMessage.SUCCESS){
			return new DataMessage<SnapshotVO>(productMessage.resultMessage);
		}
		
		ArrayList<ProductVO> productList=productMessage.data;
		Factory factory=new Factory();
		String id=factory.getSnapshotID();
		String date=factory.getDate();
		int number=factory.getSnapshotLot();
		ArrayList<SnapshotItem> itemList=new ArrayList<SnapshotItem>();
		for(ProductVO product:productList){
			SnapshotItem item=new SnapshotItem(product.getID(), product.getName(),
					product.getModel(), product.getNumber(), product.getAverage());
			itemList.add(item);
		}
		SnapshotVO snapshot=new SnapshotVO(id, date, number, itemList);
		
		return new DataMessage<SnapshotVO>(snapshot);
	}
	
	public ResultMessage exportStocking(SnapshotVO vo, String filepath){
		ExportToExcel toExcel=new ExportToExcel();
		ResultMessage message=toExcel.toExcel(vo, filepath);
		if(message==ResultMessage.SUCCESS){
			tool.createLog(Operation.EXPORT_STOCKING, vo.getID());
		}
		return message;
	}
	
	public ResultMessage newPresentTable(PresentVO vo){
		try{
			InventoryDataService dataService=new InventoryData();
			String date=new Factory().getDate();
			vo.setDate(date);
			ResultMessage message=dataService.addPresent(vo.toPO());
			if(message==ResultMessage.SUCCESS){
				tool.createLog(Operation.ADD_PRESENT, vo.getID());
			}
			return message;
		}catch(RemoteException e){
			return ResultMessage.REMOTE_FAIL;
		}
	}
	
	public DataMessage<String> newPresentReturnID(PresentVO present){
		try{
			String date=new Factory().getDate();
			present.setDate(date);
			present.setStatus(Status.SUBMIT);
			InventoryDataService dataService=new InventoryData();
			String id=dataService.addPresent_returnID(present.toPO());
			tool.createLog(Operation.ADD_PRESENT, id);
			return new DataMessage<String>(id);
		}catch(RemoteException e){
			return new DataMessage<String>(ResultMessage.REMOTE_FAIL);
		}
	}
	
	public DataMessage<PresentVO> getPresentTable(PresentVO vo){
		try{
			InventoryDataService dataService=new InventoryData();
			PresentPO po=dataService.getPresent(vo.toPO());
			if(po==null){
				return new DataMessage<PresentVO>(ResultMessage.ITEM_NOT_EXIST);
			}
			return new DataMessage<PresentVO>(po.toVO());
		}catch(RemoteException e){
			return new DataMessage<PresentVO>(ResultMessage.REMOTE_FAIL);
		}
	}
	
	public DataMessage<ArrayList<PresentVO>> getAllPresentTable(){
		try{
			InventoryDataService dataService=new InventoryData();
			ArrayList<PresentVO> voList=new ArrayList<PresentVO>();
			ArrayList<PresentPO> poList=dataService.getAllPresent();
		
			if(poList.size()==0){
				return new DataMessage<ArrayList<PresentVO>>(ResultMessage.ITEM_NOT_EXIST);
			}
			for(PresentPO po:poList){
				voList.add(po.toVO());
			}
		
			return new DataMessage<ArrayList<PresentVO>>(voList);
		}catch(RemoteException e){
			return new DataMessage<ArrayList<PresentVO>>(ResultMessage.REMOTE_FAIL);
		}
	}
	
	public DataMessage<ArrayList<PresentVO>> getAllPassedPresent(){
		try{
			InventoryDataService dataService=new InventoryData();
			ArrayList<PresentVO> voList=new ArrayList<PresentVO>();
			ArrayList<PresentPO> poList=dataService.getAllPassedPresent();
		
			if(poList.size()==0){
				return new DataMessage<ArrayList<PresentVO>>(ResultMessage.ITEM_NOT_EXIST);
			}
			for(PresentPO po:poList){
				voList.add(po.toVO());
			}
		
			return new DataMessage<ArrayList<PresentVO>>(voList);
		}catch(RemoteException e){
			return new DataMessage<ArrayList<PresentVO>>(ResultMessage.REMOTE_FAIL);
		}
	}
	
	public ResultMessage newOverflow(OverflowVO vo){
		try{
		    InventoryDataService dataService=new InventoryData();
		    String date=new Factory().getDate();
			vo.setDate(date);
		    ResultMessage message=dataService.addOverflow(vo.toPO());
		    if(message==ResultMessage.SUCCESS){
		    	tool.createLog(Operation.ADD_OVERFLOW, vo.getID());
		    }
		    return message;
		}catch(RemoteException e){
			return ResultMessage.REMOTE_FAIL;
		}
	}
	
	public DataMessage<OverflowVO> getOverflow(OverflowVO vo){
		try{
			InventoryDataService dataService=new InventoryData();
			OverflowPO po=dataService.getOverflow(vo.toPO());
			if(po==null){
				return new DataMessage<OverflowVO>(ResultMessage.ITEM_NOT_EXIST);
			}
			return new DataMessage<OverflowVO>(po.toVO());
		}catch(RemoteException e){
			return new DataMessage<OverflowVO>(ResultMessage.REMOTE_FAIL);
		}
	}
	
	public DataMessage<ArrayList<OverflowVO>> getAllOverflow(){
		try{
			InventoryDataService dataService=new InventoryData();
			ArrayList<OverflowVO> voList=new ArrayList<OverflowVO>();
			ArrayList<OverflowPO> poList=dataService.getAllOverflow();
		
			if(poList.size()==0){
				return new DataMessage<ArrayList<OverflowVO>>(ResultMessage.ITEM_NOT_EXIST);
			}
			for(OverflowPO po:poList){
				voList.add(po.toVO());
			}
		
			return new DataMessage<ArrayList<OverflowVO>>(voList);
		}catch(RemoteException e){
			return new DataMessage<ArrayList<OverflowVO>>(ResultMessage.REMOTE_FAIL);
		}
	}
	
	public DataMessage<ArrayList<OverflowVO>> getAllPassedOverflow(){
		try{
			InventoryDataService dataService=new InventoryData();
			ArrayList<OverflowVO> voList=new ArrayList<OverflowVO>();
			ArrayList<OverflowPO> poList=dataService.getAllPassedOverflow();
		
			if(poList.size()==0){
				return new DataMessage<ArrayList<OverflowVO>>(ResultMessage.ITEM_NOT_EXIST);
			}
			for(OverflowPO po:poList){
				voList.add(po.toVO());
			}
		
			return new DataMessage<ArrayList<OverflowVO>>(voList);
		}catch(RemoteException e){
			return new DataMessage<ArrayList<OverflowVO>>(ResultMessage.REMOTE_FAIL);
		}
	}
	
	public ResultMessage updateOverflow(OverflowVO vo){
		try{
			InventoryDataService dataService=new InventoryData();
			MessageBLService messageService=new MessageBL();
			ResultMessage message=dataService.updateOverflow(vo.toPO());
			Factory factory=new Factory();
			if(message!=ResultMessage.SUCCESS){
				return message;
			}else if(vo.getStatus()==Status.PASS){
				ProductBLService productService=new ProductBL();
				String productID=vo.getProduct().getID();
				ProductVO product=productService.getProduct(new ProductVO(productID)).data;
				product.setNumber(vo.getNumInWarehouse());
				productService.updateProduct(product);
				UserVO user=new UserVO(vo.getOperator());
				String pass=factory.messagePass(vo.getID());
				MessageVO passMessage=new MessageVO(factory.getDate(), pass);
				messageService.send(user, passMessage);
				tool.createLog(Operation.PASS_OVERFLOW, vo.getID());
			}else{
				UserVO user=new UserVO(vo.getOperator());
				String fail=factory.messageFail(vo.getID());
				MessageVO failMessage=new MessageVO(factory.getDate(), fail);
				messageService.send(user, failMessage);
				tool.createLog(Operation.FAIL_OVERFLOW, vo.getID());
			}
			return ResultMessage.SUCCESS;
		}catch(RemoteException e){
			return ResultMessage.REMOTE_FAIL;
		}
	}
	
	public ResultMessage updatePresentTable(PresentVO vo){
		try{
			InventoryDataService dataService=new InventoryData();
			MessageBLService messageService=new MessageBL();
			ResultMessage message=dataService.updatePresent(vo.toPO());
			Factory factory=new Factory();
			if(message!=ResultMessage.SUCCESS){
				return message;
			}else if(vo.getStatus()==Status.PASS){
				ProductBLService productService=new ProductBL();
				ArrayList<ProductItem> productList=vo.getProductList();
				for(ProductItem item:productList){
					ProductVO pvo=new ProductVO(item.getProductID());
					ProductVO product=productService.getProduct(pvo).data;
					product.setNumber(product.getNumber()-pvo.getNumber());
					productService.updateProduct(product);
				}
				UserVO user=new UserVO(vo.getOperator());
				String pass=factory.messagePass(vo.getID());
				MessageVO passMessage=new MessageVO(factory.getDate(), pass);
				messageService.send(user, passMessage);
				tool.createLog(Operation.PASS_PRESENT, vo.getID());
			}else{
				UserVO user=new UserVO(vo.getOperator());
				String fail=factory.messageFail(vo.getID());
				MessageVO failMessage=new MessageVO(factory.getDate(), fail);
				messageService.send(user, failMessage);
				tool.createLog(Operation.FAIL_PRESENT, vo.getID());
			}
			return ResultMessage.SUCCESS;
		}catch(RemoteException e){
			return ResultMessage.REMOTE_FAIL;
		}
	}
	
	public DataMessage<ArrayList<OverflowVO>> getOverflow(String startTime, String endTime, String warehouse){
		try{
			InventoryDataService dataService=new InventoryData();
			ArrayList<OverflowVO> voList=new ArrayList<OverflowVO>();
			ArrayList<OverflowPO> poList=dataService.getOverflow(startTime, endTime);
			for(OverflowPO po:poList){
				if(warehouse==null||po.getWarehouse().equals(warehouse)){
					voList.add(po.toVO());
				}
			}
			
			if(voList.size()==0){
				return new DataMessage<ArrayList<OverflowVO>>(ResultMessage.ITEM_NOT_EXIST);
			}else{
				return new DataMessage<ArrayList<OverflowVO>>(voList);
			}
		}catch(RemoteException e){
			return new DataMessage<ArrayList<OverflowVO>>(ResultMessage.REMOTE_FAIL);
		}
	}
	
	public DataMessage<ArrayList<PresentVO>> getPresent(String startTime, String endTime){
		try{
			InventoryDataService dataService=new InventoryData();
			ArrayList<PresentVO> voList=new ArrayList<PresentVO>();
			ArrayList<PresentPO> poList=dataService.getPresent(startTime, endTime);
			for(PresentPO po:poList){
				voList.add(po.toVO());
			}
			
			if(voList.size()==0){
				return new DataMessage<ArrayList<PresentVO>>(ResultMessage.ITEM_NOT_EXIST);
			}else{
				return new DataMessage<ArrayList<PresentVO>>(voList);
			}
		}catch(RemoteException e){
			return new DataMessage<ArrayList<PresentVO>>(ResultMessage.REMOTE_FAIL);
		}
	}

	public DataMessage<ArrayList<PresentVO>> getAllPresentByCurrentUser() {
		try{
			InventoryDataService dataService=new InventoryData();
			UserVO user=new UserBL().getCurrent();
			ArrayList<PresentVO> voList=new ArrayList<PresentVO>();
			ArrayList<PresentPO> poList=dataService.getPresent(user.toPO());
			for(PresentPO po:poList){
				voList.add(po.toVO());
			}
			
			if(voList.size()==0){
				return new DataMessage<ArrayList<PresentVO>>(ResultMessage.ITEM_NOT_EXIST);
			}else{
				return new DataMessage<ArrayList<PresentVO>>(voList);
			}
		}catch(RemoteException e){
			return new DataMessage<ArrayList<PresentVO>>(ResultMessage.REMOTE_FAIL);
		}
	}

	public DataMessage<ArrayList<OverflowVO>> getAllOverflowByCurrentUser() {
		try{
			InventoryDataService dataService=new InventoryData();
			UserVO user=new UserBL().getCurrent();
			ArrayList<OverflowVO> voList=new ArrayList<OverflowVO>();
			ArrayList<OverflowPO> poList=dataService.getOverflow(user.toPO());
			for(OverflowPO po:poList){
				voList.add(po.toVO());
			}
			
			if(voList.size()==0){
				return new DataMessage<ArrayList<OverflowVO>>(ResultMessage.ITEM_NOT_EXIST);
			}else{
				return new DataMessage<ArrayList<OverflowVO>>(voList);
			}
		}catch(Exception e){
			return new DataMessage<ArrayList<OverflowVO>>(ResultMessage.ITEM_NOT_EXIST);
		}
	}

	public DataMessage<ArrayList<PresentVO>> getPresentDraftByCurrentUser() {
		try{
			InventoryDataService dataService=new InventoryData();
			UserVO user=new UserBL().getCurrent();
			ArrayList<PresentVO> voList=new ArrayList<PresentVO>();
			ArrayList<PresentPO> poList=dataService.getPresentDraft(user.toPO());
			for(PresentPO po:poList){
				voList.add(po.toVO());
			}
			
			if(voList.size()==0){
				return new DataMessage<ArrayList<PresentVO>>(ResultMessage.ITEM_NOT_EXIST);
			}else{
				return new DataMessage<ArrayList<PresentVO>>(voList);
			}
		}catch(RemoteException e){
			return new DataMessage<ArrayList<PresentVO>>(ResultMessage.REMOTE_FAIL);
		}
	}

	public DataMessage<ArrayList<OverflowVO>> getOverflowDraftByCurrentUser() {
		try{
			InventoryDataService dataService=new InventoryData();
			UserVO user=new UserBL().getCurrent();
			ArrayList<OverflowVO> voList=new ArrayList<OverflowVO>();
			ArrayList<OverflowPO> poList=dataService.getOverflowDraft(user.toPO());
			for(OverflowPO po:poList){
				voList.add(po.toVO());
			}
			
			if(voList.size()==0){
				return new DataMessage<ArrayList<OverflowVO>>(ResultMessage.ITEM_NOT_EXIST);
			}else{
				return new DataMessage<ArrayList<OverflowVO>>(voList);
			}
		}catch(Exception e){
			return new DataMessage<ArrayList<OverflowVO>>(ResultMessage.ITEM_NOT_EXIST);
		}
	}

	public ResultMessage updateOverflowDraft(OverflowVO vo) {
		if(vo.getStatus()!=Status.DRAFT){
			return ResultMessage.CANNOT_UPDATE;
		}
		
		try{
			InventoryDataService dataService=new InventoryData();
			ResultMessage message=dataService.updateOverflow(vo.toPO());
			if(message==ResultMessage.SUCCESS){
				tool.createLog(Operation.UPD_PRESENT, vo.getID());
			}
			return message;
		}catch(RemoteException e){
			return ResultMessage.REMOTE_FAIL;
		}
	}

	public ResultMessage deleteOverflowDraft(OverflowVO vo) {
		if(vo.getStatus()!=Status.DRAFT){
			return ResultMessage.CANNOT_DELETE;
		}
		
		try{
			InventoryDataService dataService=new InventoryData();
			ResultMessage message=dataService.deleteOverflow(vo.toPO());
			if(message==ResultMessage.SUCCESS){
				tool.createLog(Operation.DEL_OVERFLOW, vo.getID());
			}
			return message;
		}catch(RemoteException e){
			return ResultMessage.REMOTE_FAIL;
		}
	}

	public ResultMessage updatePresentDraft(PresentVO vo) {
		if(vo.getStatus()!=Status.DRAFT){
			return ResultMessage.CANNOT_UPDATE;
		}
		
		try{
			InventoryDataService dataService=new InventoryData();
			ResultMessage message=dataService.updatePresent(vo.toPO());
			if(message==ResultMessage.SUCCESS){
				tool.createLog(Operation.UPD_PRESENT, vo.getID());
			}
			return message;
		}catch(RemoteException e){
			return ResultMessage.REMOTE_FAIL;
		}
	}

	public ResultMessage deletePresentDraft(PresentVO vo) {
		if(vo.getStatus()!=Status.DRAFT){
			return ResultMessage.CANNOT_DELETE;
		}
		
		try{
			InventoryDataService dataService=new InventoryData();
			ResultMessage message=dataService.deletePresent(vo.toPO());
			if(message==ResultMessage.SUCCESS){
				tool.createLog(Operation.DEL_PRESENT, vo.getID());
			}
			return message;
		}catch(RemoteException e){
			return ResultMessage.REMOTE_FAIL;
		}
	}

}
