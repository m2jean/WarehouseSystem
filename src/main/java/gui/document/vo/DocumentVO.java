package gui.document.vo;

import enums.Status;

public interface DocumentVO {
	
	public String getID();

	public void setID(String id);

	public String getOperator();

	public void setOperator(String operator);

	public String getDate();

	public void setDate(String date);

	public Status getStatus() ;

	public void setStatus(Status status);
	
	public boolean isHong();
	
	public void setHong(boolean b);
	
	public void setRemarks(String s);

	public String getRemarks();
}
