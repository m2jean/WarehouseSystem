package customerBL;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import PO.CustomerPO;
import VO.CustomerVO;
import customerBLService.CustomerBLService;
import customerDataService.CustomerDataService;
import enums.DataMessage;
import enums.Operation;
import enums.ResultMessage;
import factory.Helper;
import factory.RMIFactory;

public class CustomerBL implements CustomerBLService{
	Helper tool=new Helper();
	public ResultMessage addCustomer(CustomerVO vo){
		try{
			//CustomerDataService dataService=new CustomerData();
			CustomerDataService dataService=new RMIFactory().newCustomerDataService();
			String id=dataService.add(vo.toPO());
			if(id.equals("ITEM_EXIST")){
				return ResultMessage.ITEM_EXIST;
			}else{
				tool.createLog(Operation.ADD_CUSTOMER, id);
				return ResultMessage.SUCCESS;
			}
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			return ResultMessage.REMOTE_FAIL;
		}
	}
	
	public ResultMessage deleteCustomer(CustomerVO vo){
		try{
			//CustomerDataService dataService=new CustomerData();
			CustomerDataService dataService=new RMIFactory().newCustomerDataService();
			ResultMessage message=dataService.delete(vo.toPO());
			if(message==ResultMessage.SUCCESS){
				tool.createLog(Operation.DEL_CUSTOMER, vo.getID());
			}
			return message;
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			return ResultMessage.REMOTE_FAIL;
		}
	}
	
	public ResultMessage updateCustomer(CustomerVO vo){
		try{
			//CustomerDataService dataService=new CustomerData();
			CustomerDataService dataService=new RMIFactory().newCustomerDataService();
			ResultMessage message=dataService.update(vo.toPO());
			if(message==ResultMessage.SUCCESS){
				tool.createLog(Operation.UPD_CUSTOMER, vo.getID());
			}
			return message;
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			return ResultMessage.REMOTE_FAIL;
		}
	}
	
	public DataMessage<CustomerVO> getCustomer(CustomerVO vo){
		try{
			//CustomerDataService dataService=new CustomerData();
			CustomerDataService dataService=new RMIFactory().newCustomerDataService();
			CustomerPO po=dataService.get(vo.toPO());
			if(po==null){
				return new DataMessage<CustomerVO>(ResultMessage.ITEM_NOT_EXIST);
			}
			return new DataMessage<CustomerVO>(po.toVO());
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			return new DataMessage<CustomerVO>(ResultMessage.REMOTE_FAIL);
		}
	}
	
	public DataMessage<ArrayList<CustomerVO>> getCustomer(String id, String name){
		try{
			//CustomerDataService dataService=new CustomerData();
			CustomerDataService dataService=new RMIFactory().newCustomerDataService();
			ArrayList<CustomerVO> voList=new ArrayList<CustomerVO>();
			ArrayList<CustomerPO> poList=dataService.get(id, name);
		
			if(poList==null){
				return new DataMessage<ArrayList<CustomerVO>>(ResultMessage.ITEM_NOT_EXIST);
			}
			
			for(CustomerPO po:poList){
				voList.add(po.toVO());
			}
			return new DataMessage<ArrayList<CustomerVO>>(voList);
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			return new DataMessage<ArrayList<CustomerVO>>(ResultMessage.REMOTE_FAIL);
		}
	}
	
	public DataMessage<ArrayList<CustomerVO>> getAllCustomer(){
		try{
			//CustomerDataService dataService=new CustomerData();
			CustomerDataService dataService=new RMIFactory().newCustomerDataService();
			ArrayList<CustomerVO> voList=new ArrayList<CustomerVO>();
			ArrayList<CustomerPO> poList=dataService.getAll();
		
			if(poList.size()==0){
				return new DataMessage<ArrayList<CustomerVO>>(ResultMessage.ITEM_NOT_EXIST);
			}
			
			for(CustomerPO po:poList){
				voList.add(po.toVO());
			}
			return new DataMessage<ArrayList<CustomerVO>>(voList);
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			return new DataMessage<ArrayList<CustomerVO>>(ResultMessage.REMOTE_FAIL);
		}
	}

}
