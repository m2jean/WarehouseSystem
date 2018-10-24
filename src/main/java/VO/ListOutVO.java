package VO;

public class ListOutVO {
	private String name;
	private double money;
	private String remark;
	
	public ListOutVO(String name,double money,String remark){
		this.name=name;
		this.money=money;
		this.remark=remark;
	}
	
	public void setName(String name){
		this.name=name;
	}
	public void setMoney(double money){
		this.money=money;
	}
	public void setRemark(String remark){
		this.remark=remark;
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
}
