package customerBL;

import java.util.ArrayList;

import customerBLService.CustomerBLService;
import VO.CustomerVO;
import PO.CustomerPO;
import enums.DataMessage;
import enums.ResultMessage;
import customerData.CustomerDataStub;

public class CustomerBLStub implements CustomerBLService{
	public ResultMessage addCustomer(CustomerVO vo){
		new CustomerDataStub().add(new CustomerPO(null, null, null, null, null, null, null, null, null, 0, 0, 0));
		return ResultMessage.SUCCESS;
	}
	
	public ResultMessage deleteCustomer(CustomerVO vo){
		new CustomerDataStub().delete(new CustomerPO(null, null, null, null, null, null, null, null, null, 0, 0, 0));
		return ResultMessage.SUCCESS;
	}
	
	public ResultMessage updateCustomer(CustomerVO vo){
		new CustomerDataStub().update(new CustomerPO(null, null, null, null, null, null, null, null, null, 0, 0, 0));
		return ResultMessage.SUCCESS;
	}
	
	public DataMessage<CustomerVO> getCustomer(CustomerVO vo){
		new CustomerDataStub().get(new CustomerPO(null, null, null, null, null, null, null, null, null, 0, 0, 0));
		return new DataMessage(new CustomerVO(null, null, null, null, null, null, null, null, null, 0, 0, 0));
	}
	
	public DataMessage<ArrayList<CustomerVO>> getCustomer(String id, String name){
		new CustomerDataStub().get(id, name);
		ArrayList<CustomerVO> list=null;
		return new DataMessage(list);
	}
	
	public DataMessage<ArrayList<CustomerVO>> getAllCustomer(){
		new CustomerDataStub().getAll();
		return new DataMessage(new ArrayList<CustomerVO>());
	}

}
