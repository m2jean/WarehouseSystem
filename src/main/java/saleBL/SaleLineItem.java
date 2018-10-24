package saleBL;

import java.io.Serializable;

public class SaleLineItem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1676014209626748348L;
	private String id;
	private String name;
	private String size;
	private int number;
	private double price;
	private double money;
	private String remark;
	public SaleLineItem(String id,String name,String size,int number,double price,double money,String remark){
		this.id=id;
		this.name=name;
		this.size=size;
		this.number=number;
		this.price=price;
		this.money=money;
		this.remark=remark;
	}
	public String getID() {
		return id;
	}
	public void setID(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
