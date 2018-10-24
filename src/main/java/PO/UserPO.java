package PO;

import java.io.Serializable;
import java.util.ArrayList;

import VO.MessageVO;
import VO.UserVO;
import enums.UserPermission;

public class UserPO implements Comparable<UserPO>,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3988907332210224158L;
	private String username;
	private String password;
	private UserPermission perm;
	private String name;
	private String tele;
	private ArrayList<MessagePO> message;
	
	public UserPO(String username, String password, UserPermission perm, String name, String tele, ArrayList<MessagePO> message){
		this.username = username;
		this.password = password;
		this.perm = perm;
		this.name=name;
		this.tele=tele;
		this.message=message==null?new ArrayList<MessagePO>():message;
	}
	
	public UserPO(UserPO po){
		username=po.getUsername();
		password=po.getPassword();
		perm=po.getPermission();
		name=po.getName();
		tele=po.getTele();
		message=po.getMessage();
	}
	
	public UserVO toVO(){
		ArrayList<MessageVO> list=new ArrayList<MessageVO>();
		for(MessagePO po:message){
			list.add(po.toVO());
		}
		return new UserVO(username, password, perm, name, tele, list);
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
	
	public int compareTo(UserPO user) {
		return username.compareTo(user.getUsername());
	}

	public ArrayList<MessagePO> getMessage() {
		return message;
	}

	public void setMessage(ArrayList<MessagePO> message) {
		this.message = message;
	}
	
	
}
