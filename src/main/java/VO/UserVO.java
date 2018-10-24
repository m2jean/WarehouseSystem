package VO;

import java.io.Serializable;
import java.util.ArrayList;

import PO.MessagePO;
import PO.UserPO;
import enums.UserPermission;

public class UserVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4522108511033158832L;
	private String username;
	private String password;
	private UserPermission perm;
	private String name;
	private String tele;
	private ArrayList<MessageVO> message;
	
	public UserVO(String username, String password,  UserPermission perm, String name, String tele, ArrayList<MessageVO> message){
		this.username = username;
		this.password = password;
		this.perm = perm;
		this.name=name;
		this.tele=tele;
		this.message=message==null?new ArrayList<MessageVO>():message;
	}
	
	public UserVO(String name){               //方便起见，只有一个参数时username,name均会赋值
		username=name;
		password=null;
		perm=null;
		this.name=name;
		tele=null;
		message=new ArrayList<MessageVO>();
	}
	
	public UserVO(String username, String password){
		this.username=username;
		this.password=password;
		perm=null;
		name=null;
		tele=null;
		message=new ArrayList<MessageVO>();
	}
	
	public UserVO(UserVO vo){
		username=vo.getUsername();
		password=vo.getPassword();
		perm=vo.getPermission();
		name=vo.getName();
		tele=vo.getTele();
		message=vo.getMessage();
	}
	
	public UserPO toPO(){
		ArrayList<MessagePO> list=new ArrayList<MessagePO>();
		for(MessageVO vo:message){
			list.add(vo.toPO());
		}
		return new UserPO(username ,password, perm, name, tele, list);
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public UserPermission getPermission() {
		return perm;
	}
	public void setPermission(UserPermission permi) {
		this.perm = permi;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name=name;
	}

	public String getTele() {
		return tele;
	}

	public void setTele(String tele) {
		this.tele = tele;
	}

	public ArrayList<MessageVO> getMessage() {
		return message;
	}

	public void setMessage(ArrayList<MessageVO> message) {
		this.message = message;
	}
	
}
