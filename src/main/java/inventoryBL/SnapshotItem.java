package inventoryBL;

public class SnapshotItem {
	private String id;
	private String name;
	private String model;
	private int number;
	private double average;
	
	public SnapshotItem(String id, String name, String model, int number, double average){
		this.id=id;
		this.name=name;
		this.model=model;
		this.number=number;
		this.average=average;
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

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public double getAverage() {
		return average;
	}

	public void setAverage(double average) {
		this.average = average;
	}

}
