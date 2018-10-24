package data.userdata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import dataservice.userdataservice.UserDataService;
import enums.ResultMessage;
import enums.UserPermission;
import factory.Factory;
import PO.MessagePO;
import PO.UserPO;

public class UserData extends UnicastRemoteObject implements UserDataService{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8245662885920105495L;
	private ArrayList<UserPO> userList;
	
	public UserData() throws RemoteException{
		userList=new ArrayList<UserPO>();
		File file=new File("userdata.ser");
		if(file.exists()){
			loadData();
		}else{
			UserPO admin=new UserPO("admin", "admin", UserPermission.ADMINISTOR, "管理员（默认）", "***********", null);
			userList.add(admin);
		}
	}
	
	private void loadData() {
		File file = new File("userdata.ser");
		
		try{
			FileInputStream fis=new FileInputStream(file);
			ObjectInputStream ois=new ObjectInputStream(fis);
			while(fis.available()>0){
				UserPO po=(UserPO)ois.readObject();
				userList.add(po);
			}
			
			ois.close();
			fis.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void saveData(){
		File file = new File("userdata.ser");
		try{
			FileOutputStream fos=new FileOutputStream(file);
			ObjectOutputStream oos=new ObjectOutputStream(fos);
			for(UserPO po:userList){
				oos.writeObject(po);
			}
			oos.close();
			fos.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public ArrayList<UserPO> getAll() throws RemoteException {
		ArrayList<UserPO> list=new ArrayList<UserPO>();
		for(UserPO po:userList){
			list.add(new UserPO(po));
		}
		
		return list;
	}
	
	public UserPO get(UserPO user){
		for(UserPO po:userList){
			if(po.getUsername().equals(user.getUsername())||
					po.getName().equals(user.getName())){
				return po;
			}
		}
		
		return null;
	}

	public ResultMessage add(UserPO user) throws RemoteException {
		if(get(user)!=null){
			return ResultMessage.ITEM_EXIST;
		}else{
			userList.add(user);
			saveData();
			return ResultMessage.SUCCESS;
		}
	}

	public ResultMessage delete(UserPO user) throws RemoteException {
		UserPO po=get(user);
		if(po==null){
			return ResultMessage.ITEM_NOT_EXIST;
		}else{
			userList.remove(po);
			saveData();
			return ResultMessage.SUCCESS;
		}
	}

	public ResultMessage update(UserPO user) throws RemoteException {
		UserPO po=get(user);
		if(po==null){
			return ResultMessage.ITEM_NOT_EXIST;
		}else{
			po.setPermission(user.getPermission());
			po.setName(user.getName());
			po.setTele(user.getTele());
			saveData();
			return ResultMessage.SUCCESS;
		}
	}
	
	public ResultMessage changePassword(UserPO user) throws RemoteException {
		UserPO po=get(user);
		if(po==null){
			return ResultMessage.ITEM_NOT_EXIST;
		}else{
			po.setPassword(user.getPassword());
			saveData();
			return ResultMessage.SUCCESS;
		}
	}

	public ArrayList<MessagePO> getMessage(UserPO user) throws RemoteException {
		ArrayList<MessagePO> list = new ArrayList<MessagePO>();
		for(UserPO userpo:userList){
			if(!user.getUsername().equals(userpo.getUsername())){
				continue;
			}
			ArrayList<MessagePO> messageList=userpo.getMessage();
			for(MessagePO messagepo:messageList){
				list.add(new MessagePO(messagepo));
			}
			return list;		
		}
		
		return list;
	}

	public ResultMessage addMessage(MessagePO message, UserPO user) throws RemoteException {
		for(UserPO userpo:userList){
			if(!user.getUsername().equals(userpo.getUsername())){
				continue;
			}
			
			String id=new Factory().getMessageID();
			message.setID(id);
			userpo.getMessage().add(message);
			saveData();
		}
		return ResultMessage.SUCCESS;
	}

	public ResultMessage deleteMessage(MessagePO message, UserPO user) throws RemoteException {
		for(UserPO userpo:userList){
			if(!user.getUsername().equals(userpo.getUsername())){
				continue;
			}
			ArrayList<MessagePO> messageList=userpo.getMessage();
			for(MessagePO messagepo:messageList){
				if(messagepo.getID().equals(message.getID())){
					messageList.remove(messagepo);
					saveData();
					return ResultMessage.SUCCESS;
				}
			}
			return ResultMessage.MESSAGE_NOT_EXIST;
			
		}
		return ResultMessage.USER_NOT_EXIST;
	}

	@Override
	public ResultMessage updateMessage(UserPO user, ArrayList<MessagePO> list) throws RemoteException {
		for(UserPO userpo:userList){
			if(user.getUsername().equals(userpo.getUsername())){
				userpo.setMessage(list);
				saveData();
				return ResultMessage.SUCCESS;
			}
		}	
	return ResultMessage.USER_NOT_EXIST;
	}

}
