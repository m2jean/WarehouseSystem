package gui;

public class StringTable {
	static final String LOGIN = "登錄";
	static final String CHANGE_USER = "切换用户";
	static final String CANCEL = "取消";
	static final String QUIT = "退出";
	static final String USERNAME = "用戶名";
	static final String PASSWORD = "密碼";
	
	static String getLabel(String s) {
		return s+"：";
	}
}
