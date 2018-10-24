package enums;

public enum DocumentType {
	IMPORT("进货单"),SALES("销售单"),EXPORT("退货单"),RETURN("销售退货单"),
	CHECKIN("收款单"),CHECKOUT("付款单"),CASH("现金费用单"),PRESENT("赠品单"),OVERFLOW("报损报溢单"),
	ALL("全部");
	private String type;
	DocumentType(String type){
		this.type = type;
	}
	
	public String toString(){
		return type;
	}
	
	public static DocumentType toEnum(String s){
		for(DocumentType dt:values()){
			if(s.equals(dt.toString()))
				return dt;
		}
		return null;
	}
}
