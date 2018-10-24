package PO;

import java.io.Serializable;

public class ListOutPO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7025192578565629744L;
	private String accountID;
	String name;
	double money;
	String remark;
	public void setName(String a){
		name=a;
	}
	public void setMoney(double b){
		money=b;
	}
	public void setRemark(String c){
		remark=c;
	}
	public String getName(){
		return name;
	}
	public double getMoney(){
		return money;
	}
	public String getRemark(){
		return remark;
	}
	public String getAccountID() {
		return accountID;
	}
	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}
}
