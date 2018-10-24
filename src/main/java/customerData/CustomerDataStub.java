package customerData;

import java.util.ArrayList;

import customerDataService.CustomerDataService;
import PO.CustomerPO;
import enums.ResultMessage;

public class CustomerDataStub implements CustomerDataService{
	public String add(CustomerPO po){
		return "00001";
	}
	
	public ResultMessage delete(CustomerPO po){
		return ResultMessage.SUCCESS;
	}
	
	public ResultMessage update(CustomerPO po){
		return ResultMessage.SUCCESS;
	}
	
	public CustomerPO get(CustomerPO po){
		return new CustomerPO(null, null, null, null, null, null, null, null, null, 0, 0, 0);
	}
	
	public ArrayList<CustomerPO> get(String id, String name){
		return new ArrayList<CustomerPO>();
	}
	
	public ArrayList<CustomerPO> getAll(){
		return new ArrayList<CustomerPO>();
	}

}
