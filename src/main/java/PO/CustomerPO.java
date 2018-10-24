package PO;

import java.io.Serializable;

import enums.VipLevel;
import VO.CustomerVO;

public class CustomerPO implements Serializable, Comparable<CustomerPO>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 693744816082462258L;
	private String id;
	private String type;
	private VipLevel viplvl;
	private String name;
	private String tele;
	private String addr;
	private String zipCode;
	private String email;
	private String salesman;
	private double payableLine;
	private double payable;
	private double receivable;
	
	public CustomerPO(String id, String type, VipLevel viplvl, String name,String tele,
			String addr, String zipCode, String email, String salesman, double payableLine, 
			double payable, double receivable){
		this.setID(id);
		this.setType(type);
		this.setViplvl(viplvl);
		this.setName(name);
		this.setTele(tele);
		this.setAddr(addr);
		this.setZipCode(zipCode);
		this.setEmail(email);
		this.setSalesman(salesman);
		this.setPayableLine(payableLine);
		this.setPayable(payable);
		this.setReceivable(receivable);
	}
	
	public CustomerPO(CustomerPO po){
		id=po.getID();
		type=po.getType();
		viplvl=po.getViplvl();
		name=po.getName();
		tele=po.getTele();
		addr=po.getAddr();
		zipCode=po.getZipCode();
		email=po.getEmail();
		salesman=po.getSalesman();
		payableLine=po.getPayableLine();
		payable=po.getPayable();
		receivable=po.getReceivable();
	}
	
	public CustomerPO(String id){
		this.id=id;
		type=null;
		viplvl=null;
		name=null;
		tele=null;
		addr=null;
		zipCode=null;
		email=null;
		salesman=null;
		payableLine=0;
		payable=0;
		receivable=0;
	}
	
	public CustomerVO toVO(){
		return new CustomerVO(id, type, viplvl, name, tele, addr, zipCode, email, salesman, payableLine, payable, receivable);
	}

	public String getID() {
		return id;
	}

	public void setID(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public VipLevel getViplvl() {
		return viplvl;
	}

	public void setViplvl(VipLevel viplvl) {
		this.viplvl = viplvl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTele() {
		return tele;
	}

	public void setTele(String tele) {
		this.tele = tele;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSalesman() {
		return salesman;
	}

	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}

	public double getPayable() {
		return payable;
	}

	public void setPayable(double payable) {
		this.payable = payable;
	}

	public double getReceivable() {
		return receivable;
	}

	public void setReceivable(double receivable) {
		this.receivable = receivable;
	}
	
	public boolean replicCheck(CustomerPO po){
		//TODO
		if((!tele.equals("")&&tele.equals(po.getTele())) 
		    || (!addr.equals("")&&addr.equals(po.getAddr())) 
			|| (!email.equals("")&&email.equals(po.getEmail()))){
			return true;
		}
		return false;
	}

	public double getPayableLine() {
		return payableLine;
	}

	public void setPayableLine(double payableLine) {
		this.payableLine = payableLine;
	}

	public int compareTo(CustomerPO o) {
		return id.compareTo(o.getID());
	}

}
