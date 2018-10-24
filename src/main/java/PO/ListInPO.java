package PO;

import java.io.Serializable;

public class ListInPO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2113622407280758220L;
	private String accountID;
	String account;
	double money;
	String remark;
	public void setAccount(String a){
		account=a;
	}
	public void setMoney(double b){
		money=b;
	}
	public void setRemark(String c){
		remark=c;
	}
	public String getAccount(){
		return account;
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
