package gui.inventory;

public class InventorySumItem{
	private String name;
	private String type;
	private int numIn = 0;
	private double sumIn = 0;
	private int numOut = 0;
	private double sumOut = 0;
	
	public InventorySumItem(InventoryLineItem item){
		setName(item.getName());
		setType(item.getType());
		calculateItem(item);
	}
	
	public void calculateItem(InventoryLineItem item){
		int num = item.getNumber();
		double sum = num*item.getPrice();
		switch(item.getOpt()){
		case "进货":
			numIn += num;
			sumIn += sum;
			break;
		case "退货":
			numIn -= num;
			sumIn -= sum;
			break;
		case "销售":
			numOut += num;
			sumOut += sum;
			break;
		case "销售退货":
			numOut -= num;
			sumOut -= sum;
			break;
		}
	}
	
	public int compareTo(InventorySumItem item){
		int nameComp = name.compareTo(item.getName());
		if(nameComp == 0){
			return type.compareTo(item.getType());
		}
		else
			return nameComp;
	}
	
	public int compareToItem(InventoryLineItem item){
		int nameComp = name.compareTo(item.getName());
		if(nameComp == 0){
			return type.compareTo(item.getType());
		}
		else
			return nameComp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getNumIn() {
		return numIn;
	}

	public void setNumIn(int numIn) {
		this.numIn = numIn;
	}

	public double getSumIn() {
		return sumIn;
	}

	public void setSumIn(double sumIn) {
		this.sumIn = sumIn;
	}

	public int getNumOut() {
		return numOut;
	}

	public void setNumOut(int numOut) {
		this.numOut = numOut;
	}

	public double getSumOut() {
		return sumOut;
	}

	public void setSumOut(double sumOut) {
		this.sumOut = sumOut;
	}
}
