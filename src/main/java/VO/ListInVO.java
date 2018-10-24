package VO;

public class ListInVO implements Comparable<ListInVO>{
	private String accountID;
	String account;
	double money;
    String remark;
    public ListInVO(String accountID,String account,double money,String remark){
    	this.accountID = accountID;
    	this.account=account;
    	this.money=money;
    	this.remark=remark;
    }
    public ListInVO(){
    	account=null;
    	money=0;
    	remark=null;
    }
    public ListInVO(AccountVO account){
    	accountID = account.getID();
    	this.account = account.getName();
    	money = 0;
    	remark = account.getDescription();
    }
    public void setAccount(String account){
		this.account=account;
	}
	public void setMoney(double money){
		this.money=money;
	}
	public void setRemark(String remark){
		this.remark=remark;
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
	@Override
	public int compareTo(ListInVO o) {
		return accountID.compareTo(o.getAccountID());
	}
}
