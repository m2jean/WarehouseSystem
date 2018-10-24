package PO;

import java.io.Serializable;
import java.util.ArrayList;




import enums.Status;
import VO.CheckInVO;
import VO.ListInVO;

public class CheckInPO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 721356022881389047L;
	private String id;
	private String customer;
	private String name;
	private String me;
	private ArrayList<ListInPO> list;
	private double total;
	private String date;
	private Status status;
	private String remark;
	private boolean isHong=false;
	public String getID() {
		return id;
	}
	public void setID(String id) {
		this.id = id;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getMe() {
		return me;
	}
	public void setMe(String me) {
		this.me = me;
	}
	public ArrayList<ListInPO> getList() {
		return list;
	}
	public void setList(ArrayList<ListInPO> list) {
		this.list = list;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
		
	public CheckInPO(String id,String customer,String name,String me,ArrayList<ListInPO> list,double total,String date,Status status,String remark,boolean hong){
		this.id=id;
		this.customer=customer;
		this.name=name;
		this.me=me;
		this.list=list;
		this.total=total;
		this.date=date;
		this.status=status;
		this.remark=remark;
		this.isHong=hong;
	}
	public CheckInVO toVO(){
		ArrayList<ListInVO> d=new ArrayList<ListInVO>(); 
		for(int i=0;i<list.size();i++){
			ListInPO ipo=list.get(i);
			ListInVO ivo=new ListInVO();
			ivo.setAccountID(ipo.getAccountID());
			ivo.setAccount(ipo.getAccount());
			ivo.setMoney(ipo.getMoney());
			ivo.setRemark(ipo.getRemark());
			d.add(ivo);
		}
		CheckInVO vo=new CheckInVO(id,customer,name,me,d,total,date,status,remark,isHong);
		return vo;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public boolean isHong(){
		return isHong;
	}
	public void setHong(boolean hong){
		this.isHong=hong;
	}
}
 
