package PO;

import java.io.Serializable;

import enums.VipLevel;
import VO.PromotionVO;

public class PromotionPO implements Serializable, Comparable<PromotionPO> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4673746706609800904L;
	private String id;
	private VipLevel viplvl;
	private double priceLine;
	private String start;
	private String end;
	private double discount;
	private int percent;
	private double coupon;
	private PresentPO gift;
	
	public PromotionPO(String id,String start,String end, VipLevel viplvl, double priceLine,
			double discount, int percent, double coupon, PresentPO gift){
		this.id = id;
		this.start = start;
		this.end = end;
		this.viplvl=viplvl;
		this.priceLine=priceLine;
		this.discount = discount;
		this.percent=percent;
		this.coupon = coupon;
		this.gift = gift;
	}
	
	public PromotionPO(PromotionPO po){
		id=po.getID();
		start=po.getStartDate();
		end=po.getEndDate();
		viplvl=po.getViplvl();
		priceLine=po.getPriceLine();
		discount=po.getDiscount();
		percent=po.getPercent();
		coupon=po.getCoupon();
		gift=po.getGift()==null?null:new PresentPO(po.getGift());
	}
	
	public PromotionVO toVO(){
		return new PromotionVO(id, start, end, viplvl, priceLine, discount, percent, coupon, gift==null?null:gift.toVO());
	}
	
	public boolean equals(PromotionPO po){
		if(start.equals(po.getStartDate())&&
		   end.equals(po.getEndDate())&&
		   doubleEqual(priceLine, po.getPriceLine())&&
		   doubleEqual(discount, po.getDiscount())&&
		   percent==po.getPercent()&&
		   doubleEqual(coupon, po.getCoupon())){
			return true;
		}else if(id==po.getID()){
			return true;
		}else{
			return false;
		}
	}
	
	private boolean doubleEqual(double a, double b){
		if(Math.abs(a-b)<=0.001){
			return true;
		}else{
			return false;
		}
	}
	
	public String getID() {
		return id;
	}
	
	public void setID(String id) {
		this.id = id;
	}
	
	public String getStartDate() {
		return start;
	}
	
	public void setStartDate(String start) {
		this.start = start;
	}
	
	public String getEndDate() {
		return end;
	}
	
	public void setEndDate(String end) {
		this.end = end;
	}
	
	public double getDiscount() {
		return discount;
	}
	
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	
	public double getCoupon() {
		return coupon;
	}
	
	public void setCoupon(double coupon) {
		this.coupon = coupon;
	}
	
	public PresentPO getGift() {
		return gift;
	}
	
	public void setGift(PresentPO gift) {
		this.gift = gift;
	}

	public VipLevel getViplvl() {
		return viplvl;
	}

	public void setViplvl(VipLevel viplvl) {
		this.viplvl = viplvl;
	}

	public double getPriceLine() {
		return priceLine;
	}

	public void setPriceLine(double priceLine) {
		this.priceLine = priceLine;
	}

	public int getPercent() {
		return percent;
	}

	public void setPercent(int percent) {
		this.percent = percent;
	}

	public int compareTo(PromotionPO o) {
		int r = end.compareTo(o.getEndDate());
		if(r != 0){
			return -r;
		}else{
			return id.compareTo(o.getID());
		}
	}
}
