package PO;

import java.io.Serializable;
import java.util.ArrayList;

import VO.*;

public class QiChuPO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 461356885122637131L;
	private String id;
	private ArrayList<SalePO> sales;
	private ArrayList<ReturnPO> returns;
	private ArrayList<ImportPO> imports;
	private ArrayList<ExportPO> exports;
	private ArrayList<CheckInPO> checkin;
	private ArrayList<CheckOutPO> checkout;
	private ArrayList<CostPO> cost;
	private ArrayList<OverflowPO> overflow;
	private ArrayList<PresentPO> present;
	private ArrayList<AccountPO> account;
	private ArrayList<CategoryPO> category;
	private ArrayList<CustomerPO> customer;
	private ArrayList<ProductPO> product;
	private ArrayList<PromotionPO> promotion;
	public ArrayList<SalePO> getSales() {
		return sales;
	}
	public void setSales(ArrayList<SalePO> sales) {
		this.sales = sales;
	}
	public ArrayList<ReturnPO> getReturns() {
		return returns;
	}
	public void setReturns(ArrayList<ReturnPO> returns) {
		this.returns = returns;
	}
	public ArrayList<ImportPO> getImports() {
		return imports;
	}
	public void setImports(ArrayList<ImportPO> imports) {
		this.imports = imports;
	}
	public ArrayList<ExportPO> getExports() {
		return exports;
	}
	public void setExports(ArrayList<ExportPO> exports) {
		this.exports = exports;
	}
	public ArrayList<CheckInPO> getCheckIn() {
		return checkin;
	}
	public void setCheckIn(ArrayList<CheckInPO> checkin) {
		this.checkin = checkin;
	}
	public ArrayList<CheckOutPO> getCheckOut() {
		return checkout;
	}
	public void setCheckOut(ArrayList<CheckOutPO> checkout) {
		this.checkout = checkout;
	}
	public ArrayList<OverflowPO> getOverflow() {
		return overflow;
	}
	public void setOverflow(ArrayList<OverflowPO> overflow) {
		this.overflow = overflow;
	}
	public ArrayList<PresentPO> getPresent() {
		return present;
	}
	public void setPresent(ArrayList<PresentPO> present) {
		this.present = present;
	}
	public ArrayList<AccountPO> getAccount() {
		return account;
	}
	public void setAccount(ArrayList<AccountPO> account) {
		this.account = account;
	}
	public ArrayList<CategoryPO> getCategory() {
		return category;
	}
	public void setCategory(ArrayList<CategoryPO> category) {
		this.category = category;
	}
	public ArrayList<CustomerPO> getCustomer() {
		return customer;
	}
	public void setCustomer(ArrayList<CustomerPO> customer) {
		this.customer = customer;
	}
	public ArrayList<ProductPO> getProduct() {
		return product;
	}
	public void setProduct(ArrayList<ProductPO> product) {
		this.product = product;
	}
	public QiChuVO toVO(){
		QiChuVO vo=new QiChuVO();
		ArrayList<SaleVO> a=new ArrayList<SaleVO>();
		for(SalePO i:sales){
			a.add(i.toVO());
		}
		vo.setSales(a);
		ArrayList<ReturnVO> b=new ArrayList<ReturnVO>();
		for(ReturnPO i:returns){
			b.add(i.toVO());
		}
		vo.setReturns(b);
		ArrayList<ImportVO> c=new ArrayList<ImportVO>();
		for(ImportPO i:imports){
			c.add(i.toVO());
		}
		vo.setImports(c);
		ArrayList<ExportVO> d=new ArrayList<ExportVO>();
		for(ExportPO i:exports){
			d.add(i.toVO());
		}
		vo.setExports(d);
		ArrayList<CheckInVO> e=new ArrayList<CheckInVO>();
		for(CheckInPO i:checkin){
			e.add(i.toVO());
		}
		vo.setCheckIn(e);
		ArrayList<CheckOutVO> f=new ArrayList<CheckOutVO>();
		for(CheckOutPO i:checkout){
			f.add(i.toVO());
		}
		vo.setCheckOut(f);
		ArrayList<CostVO> co=new ArrayList<CostVO>();
		for(CostPO i:cost){
			co.add(i.toVO());
		}
		vo.setCost(co);
		ArrayList<OverflowVO> g=new ArrayList<OverflowVO>();
		for(OverflowPO i:overflow){
			g.add(i.toVO());
		}
		vo.setOverflow(g);
		ArrayList<PresentVO> h=new ArrayList<PresentVO>();
		for(PresentPO i:present){
			h.add(i.toVO());
		}
		vo.setPresent(h);
		ArrayList<AccountVO> j=new ArrayList<AccountVO>();
		for(AccountPO i:account){
			j.add(i.toVO());
		}
		vo.setAccount(j);
		ArrayList<CategoryVO> k=new ArrayList<CategoryVO>();
		for(CategoryPO i:category){
			k.add(i.toVO());
		}
		vo.setCategory(k);
		ArrayList<CustomerVO> l=new ArrayList<CustomerVO>();
		for(CustomerPO i:customer){
			l.add(i.toVO());
		}
		vo.setCustomer(l);
		ArrayList<ProductVO> m=new ArrayList<ProductVO>();
		for(ProductPO i:product){
			m.add(i.toVO());
		}
		vo.setProduct(m);
		ArrayList<PromotionVO> o=new ArrayList<PromotionVO>();
		for(PromotionPO i:promotion){
			o.add(i.toVO());
		}
		vo.setPromotion(o);
		vo.setID(id);
		return vo;
	}
	public String getID() {
		return id;
	}
	public void setID(String id) {
		this.id = id;
	}
	public ArrayList<PromotionPO> getPromotion() {
		return promotion;
	}
	public void setPromotion(ArrayList<PromotionPO> promotion) {
		this.promotion = promotion;
	}
	public ArrayList<CostPO> getCost() {
		return cost;
	}
	public void setCost(ArrayList<CostPO> cost) {
		this.cost = cost;
	}
}
