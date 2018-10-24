package enums;

public enum VipLevel {
	LEVEL0("无"), LEVEL1("一级用户"), LEVEL2("二级用户"), LEVEL3("三级用户"), LEVEL4("四级用户"), LEVEL5("五级用户");
	
	public static final VipLevel[] list = {LEVEL1,LEVEL2,LEVEL3,LEVEL4,LEVEL5};
	
	private String level;
	private VipLevel(String level){
		this.level = level;
	}
	
	public String toString(){
		return level;
	}
	
	public static VipLevel[] getEnumList(){
		return list;
	}
}
