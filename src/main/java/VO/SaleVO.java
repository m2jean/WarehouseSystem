package VO;

import java.util.ArrayList;







import saleBL.SaleLineItem;
import enums.Status;
import gui.document.vo.SaleReturnVO;
import PO.SalePO;


public class SaleVO implements SaleReturnVO{
	private String id;
	private String customerID;
	private String customerName;
	private String salesman;
	private String operator;
	private String cangku;
	private ArrayList<SaleLineItem> list;
	private double total;//原商品总价
	private double discount1;//销售员折让
	private double discount2;//促销折让
	private double credit1;//收代金券
	private double credit2;//送代金券
	private String present;//赠品单编号
	private ArrayList<String> promotionID = new ArrayList<String>();
	private double chit;//真正总价
	private String mark;
	private String date;
	private Status status;
	private boolean isHong=false;
	public SaleVO(String id,String customer,String name,String salesman,String operator,
			String cangku,ArrayList<SaleLineItem> list,double preTotal,double discount1,double discount2,
			double creditGet,double creditGive,String present,ArrayList<String> promotion,
			double postTotal,String date,Status status,String remarks,boolean hong){
		this.id=id;
		this.customerID=customer;
		this.customerName=name;
		this.salesman=salesman;
		this.operator=operator;
		this.cangku=cangku;
		this.list=list;
		this.total=preTotal;
		this.discount1=discount1;
		this.discount2=discount2;
		this.credit1=creditGet;
		this.credit2=creditGive;
		this.present=present;
		this.promotionID=promotion;
		this.chit=postTotal;
		this.date=date;
		this.status=status;
		this.mark=remarks;
		this.isHong=hong;
	}
	
	public SaleVO(String id,String customer,String name,String salesman,String operator,
			String cangku,ArrayList<SaleLineItem> list,double preTotal,double discount1,double discount2,
			double creditGet,double creditGive,String present,ArrayList<String> promotion,
			double postTotal,String date,Status status,String remarks){
		this.id=id;
		this.customerID=customer;
		this.customerName=name;
		this.salesman=salesman;
		this.operator=operator;
		this.cangku=cangku;
		this.list=list;
		this.total=preTotal;
		this.discount1=discount1;
		this.discount2=discount2;
		this.credit1=creditGet;
		this.credit2=creditGive;
		this.present=present;
		this.promotionID=promotion;
		this.chit=postTotal;
		this.date=date;
		this.status=status;
		this.mark=remarks;
	}
	
	public SaleVO() {
		
	}
	
	public SaleVO(String saleid) {
		this(saleid,"", "","","","",null,0,0,0,0,0,"",null,0,"",null,"");
	}
	
	public String getID() {
		return id;
	}
	public void setID(String id) {
		this.id = id;
	}
	public String getCustomerID() {
		return customerID;
	}
	public void setCustomerID(String customer) {
		this.customerID = customer;
	}
	public String getSalesman() {
		return salesman;
	}
	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String me) {
		this.operator = me;
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
	public void setPreTotal(double total) {
		this.total = total;
	}
	public double getPostTotal() {
		return chit;
	}
	public void setPostTotal(double chit) {
		this.chit = chit;
	}
	public Status getStatus(){
		return status;
	}
	public void setStatus(Status status){
		this.status=status;
	}
	public SalePO toPO(){
		SalePO po=new SalePO(id,customerID,customerName,salesman,operator,cangku,list,total,discount1,discount2,credit1,credit2,present,promotionID,chit,date,status,mark,isHong);
		return po;
	}
	public String getRemarks() {
		return mark;
	}
	public void setRemarks(String mark) {
		this.mark = mark;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getPresent() {
		return present;
	}
	public void setPresent(String present) {
		this.present = present;
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
	public double getCreditGet() {
		return credit1;
	}
	public void setCreditGet(double credit1) {
		this.credit1 = credit1;
	}
	public double getCreditGive() {
		return credit2;
	}
	public void setCreditGive(double credit2) {
		this.credit2 = credit2;
	}
	public String getCustomer() {
		return customerName;
	}
	public void setCustomer(String name) {
		this.customerName = name;
	}
	public ArrayList<String> getPromotion() {
		return promotionID;
	}
	public void setPromotion(ArrayList<String> promotion) {
		this.promotionID = promotion;
	}
	public boolean isHong(){
		return isHong;
	}
	public void setHong(boolean hong){
		this.isHong=hong;
	}
}
