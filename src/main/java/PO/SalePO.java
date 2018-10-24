package PO;

import java.io.Serializable;
import java.util.ArrayList;











import saleBL.SaleLineItem;
import enums.Status;
import VO.SaleVO;

public class SalePO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1283347113891219004L;
	private String id;
	private String customer;
	private String name;
	private String salesman;
	private String me;
	private String cangku;
	private ArrayList<SaleLineItem> list;
	private double total;
	private double discount1;
	private double discount2;
	private double credit1;
	private double credit2;
	private ArrayList<String> promotion;
	private double chit;
	private String date;
	private Status status;
	private String present;
	private String mark;
	private boolean isHong=false;
	public SalePO(String id,String customer,String name,String salesman,String me,String cangku,ArrayList<SaleLineItem> list,double total,double discount1,double discount2,double credit1,double credit2,String present,ArrayList<String> promotion,double chit,String date,Status status,String mark,boolean hong){
		this.id=id;
		this.customer=customer;
		this.name=name;
		this.salesman=salesman;
		this.me=me;
		this.cangku=cangku;
		this.list=list;
		this.total=total;
		this.discount1=discount1;
		this.discount2=discount2;
		this.credit1=credit1;
		this.credit2=credit2;
		this.present=present;
		this.promotion=promotion;
		this.chit=chit;
		this.date=date;
		this.status=status;
		this.mark=mark;
		this.isHong=hong;
	}
	public SaleVO toVO(){
		SaleVO vo=new SaleVO(id,customer,name,salesman,me,cangku,list,total,discount1,discount2,credit1,credit2,present,promotion,chit,date,status,mark,isHong);
		return vo;
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
	public void setTotal(double total) {
		this.total = total;
	}
	public double getChit() {
		return chit;
	}
	public void setChit(double chit) {
		this.chit = chit;
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
	public double getDiscount1() {
		return discount1;
	}
	public void setDiscount1(double discount1) {
		this.discount1 = discount1;
	}
	public double getDiscount2() {
		return discount2;
	}
	public void setDiscount2(double discount2) {
		this.discount2 = discount2;
	}
	public double getCredit1() {
		return credit1;
	}
	public void setCredit1(double credit1) {
		this.credit1 = credit1;
	}
	public double getCredit2() {
		return credit2;
	}
	public void setCredit2(double credit2) {
		this.credit2 = credit2;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<String> getPromotion() {
		return promotion;
	}
	public void setPromotion(ArrayList<String> promotion) {
		this.promotion = promotion;
	}
	public boolean isHong(){
		return isHong;
	}
	public void setHong(boolean hong){
		this.isHong=hong;
	}
}

