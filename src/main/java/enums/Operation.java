package enums;
public enum Operation {
	ADD_PRODUCT("添加商品"),DEL_PRODUCT("删除商品"),UPD_PRODUCT("更新商品"),
	ADD_SPECIALPRODUCT("添加特价包"),DEL_SPECIALPRODUCT("删除特价包"),UPD_SPECIALPRODUCT("更新特价包"),
	ADD_IMPORT("新增进货单"),ADD_EXPORT("新增进货退货单"),ADD_PROMOTION("新增促销策略"),DEL_PROMOTION("删除促销策略"),UPD_PROMOTION("更新促销策略"),
	ADD_USER("新增用户"),DEL_USER("删除用户"),UPD_USER("更新用户"),
	ADD_ACCOUNT("新增银行账户"),DEL_ACCOUNT("删除银行账户"),UPD_ACCOUNT("更新银行账户"),
	ADD_SALE("新增销售单"),ADD_RETURN("新增销售退货单"),ADD_CHECKIN("新增收款单"),ADD_CHECKOUT("新增付款单"),
	UPD_CHECKIN("更新收款单"),UPD_CHECKOUT("更新付款单"),
	PASS_CHECKIN("批准收款单"),PASS_CHECKOUT("批准付款单"),PASS_RETURN("批准销售退货单"),PASS_SALE("批准销售单"),PASS_IMPORT("批准进货单"),PASS_EXPORT("批准进货退货单"),
	    PASS_PRESENT("批准赠品单"),PASS_OVERFLOW("批准报溢单"),
	FAIL_CHECKIN("驳回收款单"),FAIL_CHECKOUT("驳回付款单"),FAIL_RETURN("驳回销售退货单"),FAIL_SALE("驳回销售单"),FAIL_IMPORT("驳回进货单"),FAIL_EXPORT("驳回进货退货单"),
	    FAIL_PRESENT("驳回赠品单"),FAIL_OVERFLOW("驳回报溢单"), UPD_RETURN("更新销售退货单"),
	UPD_SALE("更新销售单"),UPD_IMPORT("更新进货单"),UPD_EXPORT("更新进货退货单"),
	ADD_CATEGORY("新增库存快照"),DEL_CATEGORY("删除库存快照"),UPD_CATEGORY("更新库存快照"),
	ADD_COST("新增现金费用单"),UPD_COST("更新现金费用单"),PASS_COST("批准现金费用单"),FAIL_COST("驳回现金费用单"),
	ADD_CUSTOMER("新增客户"),DEL_CUSTOMER("删除客户"),UPD_CUSTOMER("更新客户"),
	DEL_SALE("删除销售单"),DEL_RETURN("删除销售退货单"),DEL_CHECKIN("删除收款单"),DEL_CHECKINOUT("删除付款单"),DEL_COST("删除现金费用单"),DEL_IMPORT("删除进货单"),DEL_EXPORT("删除进货退货单"),
	ADD_PRESENT("新增赠品单"),EXPORT_STOCKING("导出库存快照"),ADD_OVERFLOW("新增报溢单"),UPD_PRESENT("更新赠品单"),DEL_OVERFLOW("删除报溢单"),DEL_PRESENT("删除赠品单")
	;
	private String operation;
	private Operation(String s){
		operation = s;
	}
	
	public String toString(){
		return operation;
	}
	
	public static Operation toEnum(String str){
		for(Operation s:values())
			if(str.equals(s.toString()))
				return s;
		return null;
	}
	
}
