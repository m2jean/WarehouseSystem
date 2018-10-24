package saleBL;

public class SaleLog {
	private String id;
	private String date;
	private String name;
	private String size;
	private int number;
	private double price;
	private double money;
	public SaleLog(String id,String date,String name,String size,int number,double price,double money){
		this.id=id;
		this.setDate(date);
		this.name=name;
		this.size=size;
		this.number=number;
		this.price=price;
		this.money=money;
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
