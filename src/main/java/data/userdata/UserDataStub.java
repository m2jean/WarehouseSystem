package data.userdata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;

import PO.MessagePO;
import PO.UserPO;
import dataservice.userdataservice.UserDataService;
import enums.ResultMessage;

public class UserDataStub implements UserDataService {
	private ArrayList<UserPO> userList;
	
	public UserDataStub(){
	}

	@SuppressWarnings("unchecked")
	public void init() {
		File file = new File("userdata.jxc");
		
		FileInputStream fin;
		try {
			if(file.createNewFile())
				userList = new ArrayList<UserPO>();
			else{
				fin = new FileInputStream(file);
				ObjectInputStream obin = new ObjectInputStream(fin);
				userList = (ArrayList<UserPO>) obin.readObject();
				obin.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			userList = new ArrayList<UserPO>();
		}
	}
	
	public void finish(){
		File file = new File("userdata.jxc");
		
		FileOutputStream fout;
		try {			
			fout = new FileOutputStream(file);
			ObjectOutputStream obout = new ObjectOutputStream(fout);
			obout.writeObject(userList);
			obout.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public UserPO get(UserPO po) throws RemoteException {
		return new UserPO(null, null, null, null, null, null);
		
		/*if(userList.isEmpty())
			return new DataMessage<ArrayList<UserPO>>(ResultMessage.IS_EMPTY);
		ArrayList<UserPO> arr = new ArrayList<UserPO>();
		for(UserPO user:userList)
			arr.add(user);
		return new DataMessage<ArrayList<UserPO>>(arr);*/
	}

	public ResultMessage add(UserPO user) throws RemoteException {
		if(userList.isEmpty() || find(user)==null){
			userList.add(user);
			return ResultMessage.SUCCESS;
		}
		else
			return ResultMessage.ITEM_EXIST;
	}

	public ResultMessage delete(UserPO user) throws RemoteException {
		if(userList.isEmpty())
			return ResultMessage.ITEM_NOT_EXIST;
		UserPO u;
		if((u = find(user))!=null){
			userList.remove(u);
			return ResultMessage.SUCCESS;
		}
		return ResultMessage.ITEM_NOT_EXIST;
	}

	public ResultMessage update(UserPO user) throws RemoteException {
		if(userList.isEmpty())
			return ResultMessage.ITEM_NOT_EXIST;
		UserPO u;
		if((u = find(user))!=null){
			u.setPassword(user.getPassword());
			u.setPermission(user.getPermission());
			return ResultMessage.SUCCESS;
		}
		return ResultMessage.ITEM_NOT_EXIST;
	}

	public ResultMessage init(UserPO administor) throws RemoteException {
		return ResultMessage.SUCCESS;
	}
	
	private UserPO find(UserPO user){		
		for(UserPO u:userList)
			if(u.compareTo(user)==0)
				return u;
		return null;
	}

	public ArrayList<MessagePO> getMessage(UserPO user) throws RemoteException {
		ArrayList<MessagePO> arr = new ArrayList<MessagePO>();
		arr.add(new MessagePO(null, null, null));
		return arr;
	}

	public ResultMessage addMessage(MessagePO message, UserPO user) throws RemoteException {
		return ResultMessage.SUCCESS;
	}

	public ResultMessage deleteMessage(MessagePO message, UserPO user) throws RemoteException {
		return ResultMessage.SUCCESS;
	}

	public ResultMessage check(UserPO user) throws RemoteException {
		return ResultMessage.SUCCESS;
	}

	@Override
	public ArrayList<UserPO> getAll() throws RemoteException {
		// TODO Auto-generated method stub
		return new ArrayList<UserPO>();
	}

	@Override
	public ResultMessage changePassword(UserPO user) throws RemoteException {
		// TODO Auto-generated method stub
		return ResultMessage.SUCCESS;
	}

	@Override
	public ResultMessage updateMessage(UserPO user, ArrayList<MessagePO> list)
			throws RemoteException {
		// TODO Auto-generated method stub
		return ResultMessage.SUCCESS;
	}

}
