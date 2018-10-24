package VO;

import inventoryBL.SnapshotItem;
import java.util.*;

public class SnapshotVO {
	private String id;
	private String date;
	private int lotNumber;              //批号
	private ArrayList<SnapshotItem> list;

	public SnapshotVO(String id, String date, int lotNumber, ArrayList<SnapshotItem> list){
		this.id=id;
		this.setDate(date);
		this.lotNumber=lotNumber;
		this.setList(list);
	}

	public String getID() {
		return id;
	}

	public void setID(String id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public ArrayList<SnapshotItem> getList() {
		return list;
	}

	public void setList(ArrayList<SnapshotItem> list) {
		this.list = list;
	}

	public int getLotNumber() {
		return lotNumber;
	}

	public void setLotNumber(int lotNumber) {
		this.lotNumber = lotNumber;
	}
	
}
