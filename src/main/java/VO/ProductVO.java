package VO;

import PO.ProductPO;

public class ProductVO implements SaleItemVO{
	private String id;
	private String name;
	private String model;
	private int number;
	private double priceIn;
	private double priceOut;
	private double lastIn;
	private double lastOut;
	private String parentID;
	private String parentName;
	private int upperLimit;           //报警数量上限
	private int lowerLimit;           //报警数量下限
	private double average;           //库存均价
	
	public ProductVO(String id, String name, String model, int number, double priceIn, 
			double priceOut, double lastIn, double lastOut, String parentID, String parentName,
			int upperLimit, int lowerLimit){
		this.id=id;
		this.name=name;
		this.model=model;
		this.number=number;
		this.priceIn=priceIn;
		this.priceOut=priceOut;
		this.lastIn=lastIn;
		this.lastOut=lastOut;
		this.parentID=parentID;
		this.parentName=parentName;
		this.upperLimit=upperLimit;
		this.lowerLimit=lowerLimit;
	}
	
	public ProductVO(String id, String name, String model, int number, double priceIn, 
			double priceOut, double lastIn, double lastOut, String parentID, String parentName){
		this.id=id;
		this.name=name;
		this.model=model;
		this.number=number;
		this.priceIn=priceIn;
		this.priceOut=priceOut;
		this.lastIn=lastIn;
		this.lastOut=lastOut;
		this.parentID=parentID;
		this.parentName=parentName;
		upperLimit=99999;
		lowerLimit=0;
	}
	
	public ProductVO(String id){
		this.id=id;
		name=null;
		model=null;
		number=0;
		priceIn=0;
		priceOut=0;
		lastIn=0;
		lastOut=0;
		parentID=null;
		parentName=name;
		upperLimit=99999;
		lowerLimit=0;
		average=0;
	}
	
	public ProductPO toPO(){
		ProductPO po=new ProductPO(id, name, model, number, priceIn, priceOut, lastIn, lastOut,
				parentID, parentName, upperLimit, lowerLimit);
		po.setAverage(average);
		return po;
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

	public double getPriceIn() {
		return priceIn;
	}

	public void setPriceIn(double priceIn) {
		this.priceIn = priceIn;
	}

	public double getPriceOut() {
		return priceOut;
	}

	public void setPriceOut(double priceOut) {
		this.priceOut = priceOut;
	}

	public double getLastIn() {
		return lastIn;
	}

	public void setLastIn(double lastIn) {
		this.lastIn = lastIn;
	}

	public double getLastOut() {
		return lastOut;
	}

	public void setLastOut(double lastOut) {
		this.lastOut = lastOut;
	}

	public String getParentID() {
		return parentID;
	}

	public void setParentID(String parentID) {
		this.parentID = parentID;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
	public String toString(){
		return getID()+" "+getName()+" "+getModel();
	}

	public int getUpperLimit() {
		return upperLimit;
	}

	public void setUpperLimit(int upperLimit) {
		this.upperLimit = upperLimit;
	}

	public int getLowerLimit() {
		return lowerLimit;
	}

	public void setLowerLimit(int lowerLimit) {
		this.lowerLimit = lowerLimit;
	}

	public double getAverage() {
		return average;
	}

	public void setAverage(double average) {
		this.average = average;
	}

	@Override
	public double getPrice() {
		return this.getPriceOut();
	}

	@Override
	public String getPS() {
		return "";
	}

	@Override
	public int compareTo(SaleItemVO o) {
		return id.compareTo(o.getID());
	}

}
