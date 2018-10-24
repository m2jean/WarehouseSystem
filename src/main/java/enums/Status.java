package enums;

public enum Status {
	DRAFT("未提交"), SUBMIT("等待审批"),PASS("已通过"),FAIL("未通过"),ALL("全部");
	
	private String status;
	private Status(String s){
		status = s;
	}
	
	public String toString(){
		return status;
	}
	
	public static Status toEnum(String str){
		for(Status s:values())
			if(str.equals(s.toString()))
				return s;
		return null;
	}
}
