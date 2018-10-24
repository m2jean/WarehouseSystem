package VO;

import gui.inventory.InventoryLineItem;
import gui.inventory.InventorySumItem;

import java.util.ArrayList;

public class InventoryVO {
	private ArrayList<InventoryLineItem> itemlist;
	private ArrayList<InventorySumItem> sumlist;
	private int numIn;
	private double sumIn;
	private int numOut;
	private double sumOut;
	
	public InventoryVO(ArrayList<InventoryLineItem> itemlist,ArrayList<InventorySumItem> sumlist,
			int numin,double sumin,int numout,double sumout){
		this.setItemlist(itemlist);
		this.setSumlist(sumlist);
		setNumIn(numin);
		setSumIn(sumin);
		setNumOut(numout);
		setSumOut(sumout);
	}

	public ArrayList<InventoryLineItem> getItemlist() {
		return itemlist;
	}

	public void setItemlist(ArrayList<InventoryLineItem> itemlist) {
		this.itemlist = itemlist;
	}

	public ArrayList<InventorySumItem> getSumlist() {
		return sumlist;
	}

	public void setSumlist(ArrayList<InventorySumItem> sumlist) {
		this.sumlist = sumlist;
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
