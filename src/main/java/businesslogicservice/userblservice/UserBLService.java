package businesslogicservice.userblservice;

import java.io.IOException;
import java.util.ArrayList;
import VO.UserVO;
import enums.ResultMessage;
import enums.DataMessage;

public interface UserBLService {
	public ResultMessage login(UserVO user, boolean autologin);
	public void clearAutoLoginInfo() throws IOException;
	public UserVO getAutoLoginInfo() throws ClassNotFoundException, IOException;
	public DataMessage<ArrayList<UserVO>> getAllUser();
	public ResultMessage add(UserVO user);
	public ResultMessage delete(UserVO user);
	public ResultMessage update(UserVO user);
	public ResultMessage changePassword(UserVO user);
	public UserVO getCurrent();
}
