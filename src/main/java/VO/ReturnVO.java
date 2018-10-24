package VO;

import java.util.ArrayList;









import saleBL.SaleLineItem;
import enums.Status;
import gui.document.vo.SaleReturnVO;
import PO.ReturnPO;


public class ReturnVO implements SaleReturnVO{
	private String id;
	private String customer;
	private String name;
	private String salesman;
	private String me;
	private String cangku;
	private ArrayList<SaleLineItem> list;
	private double total;
	private String date;
	private Status status;
	private String mark;
	private String saleid;
	private boolean isHong=false;
	
	public ReturnVO(String id,String saleid){
		this.id=id;
		this.saleid=saleid;
	}
	
	public ReturnVO(String id,String customer,String name,String salesman,String me,String cangku,ArrayList<SaleLineItem> list,double total, String date,Status status,String mark,String saleid){
		this.id=id;
		this.customer=customer;
		this.name=name;
		this.salesman=salesman;
		this.me=me;
		this.cangku=cangku;
		this.list=list;
		this.total=total;
		this.date=date;
		this.status=status;
		this.mark=mark;
		this.saleid=saleid;
	}
	
	public ReturnVO(String id,String customer,String name,String salesman,String me,String cangku,ArrayList<SaleLineItem> list,double total, String date,Status status,String mark,String saleid,boolean hong){
		this.id=id;
		this.customer=customer;
		this.name=name;
		this.salesman=salesman;
		this.me=me;
		this.cangku=cangku;
		this.list=list;
		this.total=total;
		this.date=date;
		this.status=status;
		this.mark=mark;
		this.saleid=saleid;
		this.isHong=hong;
	}
	public ReturnVO(SaleVO sale){
		this("",sale.getCustomer(),sale.getCustomer(),sale.getSalesman(),sale.getOperator(),sale.getWarehouse(),sale.getProductList(),
				sale.getTotal(),"",Status.DRAFT,"",sale.getID(),sale.isHong());
	}
	public String getID() {
		return id;
	}
	public void setID(String id) {
		this.id = id;
	}
	public String getCustomerID() {
		return customer;
	}
	public void setCustomerID(String customer) {
		this.customer = customer;
	}
	public String getSalesman() {
		return salesman;
	}
	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}
	public String getOperator() {
		return me;
	}
	public void setOperator(String me) {
		this.me = me;
	}
	public String getWarehouse() {
		return cangku;
	}
	public void setWarehouse(String cangku) {
		this.cangku = cangku;
	}
	public ArrayList<SaleLineItem> getProductList() {
		return list;
	}
	public void setProductList(ArrayList<SaleLineItem> list) {
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
	
	public ReturnPO toPO(){
		ReturnPO po=new ReturnPO(id,customer,name,salesman,me,cangku,list,total,date,status,mark,saleid,isHong);
		return po;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getRemarks() {
		return mark;
	}
	public void setRemarks(String mark) {
		this.mark = mark;
	}
	public String getCustomer() {
		return name;
	}
	public void setCustomer(String name) {
		this.name = name;
	}
	public String getSaleid() {
		return saleid;
	}
	public void setSaleid(String saleid) {
		this.saleid = saleid;
	}
	public boolean isHong(){
		return isHong;
	}
	public void setHong(boolean hong){
		this.isHong=hong;
	}

}
