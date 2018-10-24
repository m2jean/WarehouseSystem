package PO;

import java.io.Serializable;
import VO.LogVO;
import enums.Operation;

public class LogPO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1357460222780312213L;
	private String date;
	private String operator;
	private Operation opt;
	private String detail;
	
	public LogPO(String date,String operator,Operation opt,String detail){
		this.date = date;
		this.operator = operator;
		this.opt = opt;
		this.detail=detail;
	}
	
	public LogVO toVO(){
		return new LogVO(date,operator,opt,detail);
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
}
