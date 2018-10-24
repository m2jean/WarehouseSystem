package VO;

import PO.AccountPO;

public class AccountVO implements Comparable<AccountVO>{
	private String id;
	private String name;
	private double balance;
	private String description = "";
	
	public AccountVO(String id,String name,double balance){
		this.id = id;
		this.name=name;
		this.balance=balance;
	}
	public AccountVO(String id,String name,double balance,String des){
		this.id = id;
		this.name=name;
		this.balance=balance;
		this.description = des;
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
	public AccountPO toPO(){
		AccountPO po=new AccountPO(id,name,balance,description);
		return po;
	}
	public String getID() {
		return id;
	}
	public void setID(String id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public int compareTo(AccountVO vo) {
		return id.compareTo(vo.getID());
	}
	
	@Override
	public String toString(){
		return this.name;
	}
}
