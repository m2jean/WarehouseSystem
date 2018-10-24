package PO;

import java.io.Serializable;

import VO.MessageVO;

public class MessagePO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1399949510746815856L;
	private String id;
	private String date;
	private String content;
	private boolean read;
	
	public MessagePO(String id, String date, String content){
		this.id = id;
		this.date = date;
		this.content = content;			
	}
	
	public MessagePO(String id, String date, String content,boolean read){
		this.id = id;
		this.date = date;
		this.content = content;
		this.read = read;
	}
	
	public MessagePO(MessagePO po){
		id=po.getID();
		date=po.getDate();
		content=po.getContent();
		read = po.read;
	}
	
	public MessageVO toVO(){
		return new MessageVO(id, date, content,read);
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getID() {
		return id;
	}
	public void setID(String id) {
		this.id = id;
	}
}
