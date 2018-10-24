package VO;

import java.io.Serializable;

import PO.MessagePO;

public class MessageVO implements Serializable,Comparable<MessageVO>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2332955508218388297L;
	private String id;
	private String date;
	private String content;
	private boolean read = false;
	
	public MessageVO(String id, String date, String content){
		this.id = id;
		this.date = date;
		this.content = content;			
	}
	
	public MessageVO(String id, String date, String content,boolean read){
		this.id = id;
		this.date = date;
		this.content = content;	
		this.setRead(read);
	}
	
	public MessageVO(String date, String content){
		id=null;
		this.date=date;
		this.content=content;
	}
	
	public MessageVO(String content){
		this.content=content;
		id=null;
		date=null;
	}
	
	public MessagePO toPO(){
		return new MessagePO(id, date, content,read);
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

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	@Override
	public int compareTo(MessageVO o) {
		return o.getDate().compareTo(date);
	}
}
