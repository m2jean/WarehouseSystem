package PO;

import java.io.Serializable;

import VO.AccountVO;

public class AccountPO implements Serializable,Cloneable{

	private static final long serialVersionUID = 5230409573986711917L;
	private String id;
	private String name;
	private double balance;
	private String description;
	
	public AccountPO(String id,String name,double balance){
		this.id = id;
		this.name=name;
		this.balance=balance;
	}
	public AccountPO(String id,String name,double balance,String str){
		this.id = id;
		this.name=name;
		this.balance=balance;
		this.description = str;
	}
	public String getName(){
		return name;
	}
	public void setName(String a){
		this.name=a;
	}
	public double getBalance(){
		return balance;
	}
	public void setBalance(double b){
		this.balance=b;
	}
	public AccountVO toVO(){
		AccountVO vo=new AccountVO(id,this.name,this.balance,description);
		return vo;
	}
	public String getID() {
		return id;
	}
	public void setID(String id) {
		this.id = id;
	}
	
	public AccountPO clone(){
		return new AccountPO(id,name,balance);
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
