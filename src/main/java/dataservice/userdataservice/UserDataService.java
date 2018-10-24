package dataservice.userdataservice;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import PO.MessagePO;
import PO.UserPO;
import enums.ResultMessage;

public interface UserDataService extends Remote{
	public UserPO get(UserPO po) throws RemoteException;
	public ArrayList<UserPO> getAll() throws RemoteException;
	public ResultMessage add(UserPO user) throws RemoteException;
	public ResultMessage delete(UserPO user) throws RemoteException;
	public ResultMessage update(UserPO user) throws RemoteException;
	public ArrayList<MessagePO> getMessage(UserPO user) throws RemoteException;
	public ResultMessage addMessage(MessagePO message,UserPO user) throws RemoteException;
	public ResultMessage deleteMessage(MessagePO message,UserPO user) throws RemoteException;
	public ResultMessage updateMessage(UserPO user,ArrayList<MessagePO> list) throws RemoteException;
	public ResultMessage changePassword(UserPO userPO) throws RemoteException;
}
