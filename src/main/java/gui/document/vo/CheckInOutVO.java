package gui.document.vo;

import java.util.ArrayList;

import VO.ListInVO;

public interface CheckInOutVO extends DocumentVO {
	public String getCustomer();
	public void setCustomer(String customer);
	public ArrayList<ListInVO> getList();
	public void setList(ArrayList<ListInVO> list);
	public double getTotal();
	public void setTotal(double total);
	public String getDate();
	public String getCustomerName();
	public void setName(String name);
}
