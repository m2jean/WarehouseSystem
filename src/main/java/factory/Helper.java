package factory;

import logBL.LogBL;
import businesslogic.userbl.UserBL;
import businesslogicservice.logblservice.LogBLService;
import businesslogicservice.userblservice.UserBLService;
import VO.LogVO;
import enums.Operation;

public class Helper {
	public boolean StringContains(String s1, String s2){
		boolean result=true;
		for(int i=0;i<s2.length();i++){
			char c=s2.charAt(i);
			if(s1.indexOf(c)==-1){
				result=false;
				break;
			}
		}
		
		return result;
	}
	
	public void createLog(Operation opt, String detail){
		UserBLService userService = new UserBL();
		String operator = userService.getCurrent().getName();
		LogVO log=new LogVO(null, operator, opt, detail);
		LogBLService service=new LogBL();
		service.add(log);
	}
}
