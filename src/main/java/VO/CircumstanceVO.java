package VO;

public class CircumstanceVO {
	private double income;//总收入
	private double salein;//销售收入
	private double overin;//报溢
	private double chajia;//进货退货差价
	private double change;//成本调价收入
	private double credit;//代金券收入
	private double discount;//总折让
	private double expense;//总支出
	private double saleout;//销售成本
	private double overout;//报损
	private double present;//赠品
	private double profit;//利润	
	
	public CircumstanceVO(){
		income=0;
		salein=0;
		overin=0;
		chajia=0;
		credit=0;
		discount=0;
		expense=0;
		saleout=0;
		overout=0;
		present=0;
		profit=0;
	}
	public double getIncome() {
		return income;
	}
	public void setIncome(double income) {
		this.income = income;
	}
	public double getSalein() {
		return salein;
	}
	public void setSalein(double salein) {
		this.salein = salein;
	}
	public double getOverin() {
		return overin;
	}
	public void setOverin(double overin) {
		this.overin = overin;
	}
	public double getExpense() {
		return expense;
	}
	public void setExpense(double expense) {
		this.expense = expense;
	}
	public double getSaleout() {
		return saleout;
	}
	public void setSaleout(double saleout) {
		this.saleout = saleout;
	}
	public double getOverout() {
		return overout;
	}
	public void setOverout(double overout) {
		this.overout = overout;
	}
	public double getPresent() {
		return present;
	}
	public void setPresent(double present) {
		this.present = present;
	}
	public double getProfit() {
		return profit;
	}
	public void setProfit(double profit) {
		this.profit = profit;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public double getCredit() {
		return credit;
	}
	public void setCredit(double credit) {
		this.credit = credit;
	}
	public double getChajia() {
		return chajia;
	}
	public void setChajia(double chajia) {
		this.chajia = chajia;
	}
	public double getChange() {
		return change;
	}
	public void setChange(double change) {
		this.change = change;
	}
}
