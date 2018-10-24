package VO;

import enums.VipLevel;
import PO.PromotionPO;

public class PromotionVO implements Comparable<PromotionVO>{
	private String id;
	private String start;
	private String end;
	private VipLevel viplvl;
	private double priceLine;
	private double discount;
	private int percent;
	private double coupon;
	private PresentVO gift;
	
	public PromotionVO(String id,String start,String end,VipLevel viplvl,double priceLine,double discount,int percent,double coupon,PresentVO gift){
		this.id = id;
		this.start = start;
		this.end = end;
		this.setViplvl(viplvl);
		this.setPriceLine(priceLine);
		this.discount = discount;
		this.percent=percent;
		this.coupon = coupon;
		this.gift = gift;
	}
	
	public PromotionVO(String id){
		this.id=id;
		start=null;
		end=null;
		viplvl=null;
		priceLine=0;
		discount=0;
		percent=0;
		coupon=0;
		gift=null;
	}
	
	public PromotionPO toPO(){
		return new PromotionPO(id,start,end,viplvl,priceLine,discount,percent,coupon,gift==null?null:gift.toPO());
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
	public PresentVO getGift() {
		return gift;
	}
	public void setGift(PresentVO gift) {
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

	@Override
	public int compareTo(PromotionVO o) {
		return o.getEndDate().compareTo(end);
	}
}
