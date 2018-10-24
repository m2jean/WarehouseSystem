package enums;

import java.io.Serializable;

public enum UserPermission implements Serializable{
	GENERAL_MANAGER("总经理"),ADMINISTOR("管理员"),SALESMAN("销售进货人员"),
	STOCK_MANAGER("库存管理人员"),ACCOUNT("财务人员"),SALES_MANAGER("销售经理"),ACCOUNT_MANAGER("财务经理");
	
	private String chinese;
	private UserPermission(String str){
		chinese = str;
	}
	
	@Override
	public String toString(){
		return chinese;
	}
	
	public static UserPermission toEnum(String str){
		for(UserPermission perm:values())
			if(perm.chinese.equals(str))
				return perm;
		return null;
	}
	
}
