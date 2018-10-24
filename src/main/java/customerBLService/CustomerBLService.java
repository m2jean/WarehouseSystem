package customerBLService;

import java.util.*;

import VO.CustomerVO;
import enums.DataMessage;
import enums.ResultMessage;

public interface CustomerBLService{
	public ResultMessage addCustomer(CustomerVO vo);  
	public ResultMessage deleteCustomer(CustomerVO vo);
	public ResultMessage updateCustomer(CustomerVO vo);
	public DataMessage<CustomerVO> getCustomer(CustomerVO vo);
	public DataMessage<ArrayList<CustomerVO>> getCustomer(String id, String name);
	public DataMessage<ArrayList<CustomerVO>> getAllCustomer();
}
