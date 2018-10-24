package VO;



import PO.LogPO;
import enums.Operation;

public class LogVO implements Comparable<LogVO>{
	private String date;
	private String operator;
	private Operation opt;
	private String detail;
	
	public LogVO(String data,String operator,Operation opt,String detail){
		this.date=data;
		this.operator = operator;
		this.opt = opt;
		this.detail=detail;
	}
	
	public LogPO toPO(){
		return new LogPO(date,operator,opt,detail);
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Operation getOpt() {
		return opt;
	}
	public void setOpt(Operation opt) {
		this.opt = opt;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	@Override
	public int compareTo(LogVO o) {
		return date.compareTo(o.getDate());
	}
}
