package productBL;

import java.io.Serializable;

public class CostChange implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5228819241906975970L;
	private String date;
	private double change;
	
	public CostChange(String date, double change){
		this.date=date;
		this.change=change;
	}
	
	public String getDate() {
		return date;
	}

	public double getChange() {
		return change;
	}

}
