package VO;

import java.util.ArrayList;

import PO.CostPO;
import PO.ListOutPO;
import enums.Status;
import gui.document.vo.DocumentVO;

public class CostVO implements DocumentVO{
	private String id;
	private String me;
	private String account;
	private String accountName;
	private ArrayList<ListOutVO> list;
	private double total;
	private String date;
	private Status status;
	private String remark;
	private boolean isHong=false;
	
	public CostVO(String id){
		this.id=id;
		this.me="";
		this.account="";
		this.accountName="";
		this.list=new ArrayList<ListOutVO>();
		this.total=0;
		this.date="";
		this.status=Status.DRAFT;
		this.remark="";
		this.isHong=false;
	}
	
	public CostVO(String id,String me,String account,String accountName,ArrayList<ListOutVO> list,double total,String date,Status status,String remark){
		this.id=id;
		this.me=me;
		this.account=account;
		this.accountName=accountName;
		this.list=list;
		this.total=total;
		this.date=date;
		this.status=status;
		this.setRemarks(remark);
	}
	
	public CostVO(String id,String me,String account,String accountName,ArrayList<ListOutVO> list,double total,String date,Status status,String remark,boolean hong){
		this.id=id;
		this.me=me;
		this.account=account;
		this.accountName=accountName;
		this.list=list;
		this.total=total;
		this.date=date;
		this.status=status;
		this.setRemarks(remark);
		this.isHong=hong;
	}
	public CostPO toPO(){
		ArrayList<ListOutPO> d=new ArrayList<ListOutPO>(); 
		for(int i=0;i<list.size();i++){
			ListOutVO ipo=list.get(i);
			ListOutPO ivo=new ListOutPO();
			ivo.setName(ipo.getName());
			ivo.setMoney(ipo.getMoney());
			ivo.setRemark(ipo.getRemark());
			d.add(ivo);
		}
		CostPO po=new CostPO(id,me,account,accountName,d,total,date,status,remark,isHong);
		return po;
	}

	public String getID() {
		return id;
	}

	public void setID(String id) {
		this.id = id;
	}

	public String getOperator() {
		return me;
	}

	public void setOperator(String me) {
		this.me = me;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public ArrayList<ListOutVO> getList() {
		return list;
	}

	public void setList(ArrayList<ListOutVO> list) {
		this.list = list;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
	public Status getStatus(){
		return status;
	}
	public void setStatus(Status status){
		this.status=status;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getRemarks() {
		return remark;
	}
	public void setRemarks(String remark) {
		this.remark = remark;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	public boolean isHong(){
		return isHong;
	}
	public void setHong(boolean hong){
		this.isHong=hong;
	}
}
