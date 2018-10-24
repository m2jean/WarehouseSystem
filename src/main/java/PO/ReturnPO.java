package PO;

import java.io.Serializable;
import java.util.ArrayList;



import saleBL.SaleLineItem;
import enums.Status;
import VO.ReturnVO;


public class ReturnPO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8655220438426265111L;
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
	public ReturnPO(String id,String customer,String name,String salesman,String me,String cangku,ArrayList<SaleLineItem> list,double total, String date,Status status,String mark,String saleid,boolean hong){
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
	public String getSalesman() {
		return salesman;
	}
	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}
	public String getMe() {
		return me;
	}
	public void setMe(String me) {
		this.me = me;
	}
	public String getCangku() {
		return cangku;
	}
	public void setCangku(String cangku) {
		this.cangku = cangku;
	}
	public ArrayList<SaleLineItem> getList() {
		return list;
	}
	public void setList(ArrayList<SaleLineItem> list) {
		this.list = list;
	}
	public double getTotal() {
		return total;
	}
	public void setChit(double total) {
		this.total = total;
	}
	public ReturnVO toVO(){
		ReturnVO vo=new ReturnVO(id,customer,name,salesman,me,cangku,list,total,date,status,mark,saleid,isHong);
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
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name=name;
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
