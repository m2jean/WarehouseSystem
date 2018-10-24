package businesslogic.userbl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import businesslogicservice.userblservice.UserBLService;
import PO.UserPO;
import VO.UserVO;
import dataservice.userdataservice.UserDataService;
import enums.DataMessage;
import enums.Operation;
import enums.ResultMessage;
import factory.Helper;
import factory.RMIFactory;

public class UserBL implements UserBLService{
	private Helper tool=new Helper();
	static private UserVO current = null;

	public ResultMessage login(UserVO user, boolean autolog) {
		try {
			//UserDataService userdata = new UserData();
			UserDataService userdata = new RMIFactory().newUserDataService();
			UserPO po=userdata.get(user.toPO());
			
			if(po == null){
				return ResultMessage.USER_NOT_EXIST;
			}
			else if(!user.getPassword().equals(po.getPassword())){
				return ResultMessage.LOGIN_FAIL;
			}
			saveUserInfo(po.toVO());
			try{
				if(autolog)
					saveAutoLoginInfo(po.toVO());
				else{
					clearAutoLoginInfo();
				}
			}catch(IOException ioe){
				ioe.printStackTrace();
				return ResultMessage.SAVE_FAIL;
			}
			return ResultMessage.SUCCESS;			
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			return ResultMessage.REMOTE_FAIL;
		}
	}
	
	private void saveAutoLoginInfo(UserVO user) throws IOException{
		FileOutputStream fos = new FileOutputStream(".autolog");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeBoolean(true);
		oos.writeObject(user);
		oos.close();
	}
	public void clearAutoLoginInfo() throws IOException{
		File file = new File(".autolog");
		file.delete();
	}
	public UserVO getAutoLoginInfo() throws ClassNotFoundException, IOException{
		File file = new File(".autolog");
		if(!file.exists())	return null;
		FileInputStream fos = new FileInputStream(file);
		ObjectInputStream oos = new ObjectInputStream(fos);
		Boolean auto = oos.readBoolean();
		UserVO user = null;
		if(auto){
			user = (UserVO) oos.readObject();
			oos.close();
		}
		return user;
	}
	
	private void saveUserInfo(UserVO vo){
		current = vo;
	}

	public ResultMessage add(UserVO user) {
		try {
			//UserDataService userdata = new UserData();
			UserDataService userdata = new RMIFactory().newUserDataService();
			ResultMessage message=userdata.add(user.toPO());
			if(message.equals(ResultMessage.SUCCESS)){
				tool.createLog(Operation.ADD_USER, user.getName());
			}
			return message;
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			return ResultMessage.REMOTE_FAIL;
		}
	}

	public DataMessage<ArrayList<UserVO>> getAllUser() {
		try {
			//UserDataService userdata = new UserData();
			UserDataService userdata = new RMIFactory().newUserDataService();
			ArrayList<UserPO> list= userdata.getAll();
			if(list.size()==0)
				return new DataMessage<ArrayList<UserVO>>(ResultMessage.IS_EMPTY);
			else{
				ArrayList<UserVO> voList=new ArrayList<UserVO>();
				for(UserPO user:list)
					voList.add(user.toVO());
				return new DataMessage<ArrayList<UserVO>>(voList);
			}
			
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			return new DataMessage<ArrayList<UserVO>>(ResultMessage.REMOTE_FAIL);
		}
	}

	public ResultMessage delete(UserVO user) {
		try {
			//UserDataService userdata = new UserData();
			UserDataService userdata = new RMIFactory().newUserDataService();
			UserVO current=getCurrent();
			if(current.getUsername().equals(user.getUsername())){
				return ResultMessage.CANNOT_DELETE;
			}
			ResultMessage result = userdata.delete(user.toPO());
			if(result==ResultMessage.SUCCESS){
				tool.createLog(Operation.DEL_USER, user.getName());
			}
			return result;
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			return ResultMessage.REMOTE_FAIL;
		}
	}

	public ResultMessage update(UserVO user) {
		try {
			//UserDataService userdata = new UserData();
			UserDataService userdata = new RMIFactory().newUserDataService();
			ResultMessage result = userdata.update(user.toPO());
			if(result==ResultMessage.SUCCESS){
				tool.createLog(Operation.UPD_USER, user.getName());
			}
			return result;
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			return ResultMessage.REMOTE_FAIL;
		}
	}
	
	

	public UserVO getCurrent() {
		if(current == null) throw new NullPointerException("Current user at UserBL!");
		return current;
	}

	@Override
	public ResultMessage changePassword(UserVO user) {
		try {
			//UserDataService userdata = new UserData();
			UserDataService userdata = new RMIFactory().newUserDataService();
			ResultMessage result = userdata.changePassword(user.toPO());
			if(result==ResultMessage.SUCCESS){
				tool.createLog(Operation.UPD_USER, user.getName());
			}
			return result;	
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			return ResultMessage.REMOTE_FAIL;
		}
	}
	
	
}
