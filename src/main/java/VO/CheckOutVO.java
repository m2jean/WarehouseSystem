package VO;

import java.util.ArrayList;









import enums.Status;
import gui.document.vo.CheckInOutVO;
import PO.CheckOutPO;
import PO.ListInPO;


public class CheckOutVO implements CheckInOutVO{
	private String id;
	private String customer;
	private String name;
	private String me;
	private ArrayList<ListInVO> list;
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
	public String getOperator() {
		return me;
	}
	public void setOperator(String me) {
		this.me = me;
	}
	public ArrayList<ListInVO> getList() {
		return list;
	}
	public void setList(ArrayList<ListInVO> list) {
		this.list = list;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public String getDate(){
		return date;
	}
	public void setDate(String date){
		this.date=date;
	}
	public Status getStatus(){
		return status;
	}
	public void setStatus(Status status){
		this.status=status;
	}
	
	public CheckOutVO(String id){
		this.id=id;
		this.customer="";
		this.name="";
		this.me="";
		this.list=new ArrayList<ListInVO>();
		this.total=0;
		this.date="";
		this.status=Status.DRAFT;
		this.remark="";
		this.isHong=false;
	}
	
	public CheckOutVO(String id,String customer,String name,String me,ArrayList<ListInVO> list,double total,String date,Status status,String remark){
		this.id=id;
		this.customer=customer;
		this.name=name;
		this.me=me;
		this.list=list;
		this.total=total;
		this.date=date;
		this.status=status;
		this.remark=remark;
	}
		
	public CheckOutVO(String id,String customer,String name,String me,ArrayList<ListInVO> list,double total,String date,Status status,String remark,boolean hong){
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
	
	public CheckOutPO toPO(){
		ArrayList<ListInPO> d=new ArrayList<ListInPO>(); 
		for(int i=0;i<list.size();i++){
			ListInVO ipo=list.get(i);
			ListInPO ivo=new ListInPO();
			ivo.setAccountID(ipo.getAccountID());
			ivo.setAccount(ipo.getAccount());
			ivo.setMoney(ipo.getMoney());
			ivo.setRemark(ipo.getRemark());
			d.add(ivo);
		}
		CheckOutPO po=new CheckOutPO(id,customer,name,me,d,total,date,status,remark,isHong);
		return po;
	}
	public String getCustomerName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemarks() {
		return remark;
	}
	public void setRemarks(String remark) {
		this.remark = remark;
	}
	public boolean isHong(){
		return isHong;
	}
	public void setHong(boolean hong){
		this.isHong=hong;
	}
}
