package VO;

import java.util.ArrayList;


import PO.AccountPO;
import PO.CategoryPO;
import PO.CheckInPO;
import PO.CheckOutPO;
import PO.CostPO;
import PO.CustomerPO;
import PO.ExportPO;
import PO.ImportPO;
import PO.OverflowPO;
import PO.PresentPO;
import PO.ProductPO;
import PO.PromotionPO;
import PO.QiChuPO;
import PO.ReturnPO;
import PO.SalePO;

public class QiChuVO {
	private String id;
	private ArrayList<SaleVO> sales;
	private ArrayList<ReturnVO> returns;
	private ArrayList<ImportVO> imports;
	private ArrayList<ExportVO> exports;
	private ArrayList<CheckInVO> checkin;
	private ArrayList<CheckOutVO> checkout;
	private ArrayList<CostVO> cost;
	private ArrayList<OverflowVO> overflow;
	private ArrayList<PresentVO> present;
	private ArrayList<AccountVO> account;
	private ArrayList<CategoryVO> category;
	private ArrayList<CustomerVO> customer;
	private ArrayList<ProductVO> product;
	private ArrayList<PromotionVO> promotion;
	public ArrayList<SaleVO> getSales() {
		return sales;
	}
	public void setSales(ArrayList<SaleVO> sales) {
		this.sales = sales;
	}
	public ArrayList<ReturnVO> getReturns() {
		return returns;
	}
	public void setReturns(ArrayList<ReturnVO> returns) {
		this.returns = returns;
	}
	public ArrayList<ImportVO> getImports() {
		return imports;
	}
	public void setImports(ArrayList<ImportVO> imports) {
		this.imports = imports;
	}
	public ArrayList<ExportVO> getExports() {
		return exports;
	}
	public void setExports(ArrayList<ExportVO> exports) {
		this.exports = exports;
	}
	public ArrayList<CheckInVO> getCheckIn() {
		return checkin;
	}
	public void setCheckIn(ArrayList<CheckInVO> checkin) {
		this.checkin = checkin;
	}
	public ArrayList<CheckOutVO> getCheckOut() {
		return checkout;
	}
	public void setCheckOut(ArrayList<CheckOutVO> checkout) {
		this.checkout = checkout;
	}
	public ArrayList<OverflowVO> getOverflow() {
		return overflow;
	}
	public void setOverflow(ArrayList<OverflowVO> overflow) {
		this.overflow = overflow;
	}
	public ArrayList<PresentVO> getPresent() {
		return present;
	}
	public void setPresent(ArrayList<PresentVO> present) {
		this.present = present;
	}
	public ArrayList<AccountVO> getAccount() {
		return account;
	}
	public void setAccount(ArrayList<AccountVO> account) {
		this.account = account;
	}
	public ArrayList<CategoryVO> getCategory() {
		return category;
	}
	public void setCategory(ArrayList<CategoryVO> category) {
		this.category = category;
	}
	public ArrayList<CustomerVO> getCustomer() {
		return customer;
	}
	public void setCustomer(ArrayList<CustomerVO> customer) {
		this.customer = customer;
	}
	public ArrayList<ProductVO> getProduct() {
		return product;
	}
	public void setProduct(ArrayList<ProductVO> product) {
		this.product = product;
	}
	public QiChuPO toPO(){
		QiChuPO po=new QiChuPO();
		ArrayList<SalePO> a=new ArrayList<SalePO>();
		for(SaleVO i:sales){
			a.add(i.toPO());
		}
		po.setSales(a);
		ArrayList<ReturnPO> b=new ArrayList<ReturnPO>();
		for(ReturnVO i:returns){
			b.add(i.toPO());
		}
		po.setReturns(b);
		ArrayList<ImportPO> c=new ArrayList<ImportPO>();
		for(ImportVO i:imports){
			c.add(i.toPO());
		}
		po.setImports(c);
		ArrayList<ExportPO> d=new ArrayList<ExportPO>();
		for(ExportVO i:exports){
			d.add(i.toPO());
		}
		po.setExports(d);
		ArrayList<CheckInPO> e=new ArrayList<CheckInPO>();
		for(CheckInVO i:checkin){
			e.add(i.toPO());
		}
		po.setCheckIn(e);
		ArrayList<CheckOutPO> f=new ArrayList<CheckOutPO>();
		for(CheckOutVO i:checkout){
			f.add(i.toPO());
		}
		po.setCheckOut(f);
		ArrayList<CostPO> co=new ArrayList<CostPO>();
		for(CostVO i:cost){
			co.add(i.toPO());
		}
		po.setCost(co);
		ArrayList<OverflowPO> g=new ArrayList<OverflowPO>();
		for(OverflowVO i:overflow){
			g.add(i.toPO());
		}
		po.setOverflow(g);
		ArrayList<PresentPO> h=new ArrayList<PresentPO>();
		for(PresentVO i:present){
			h.add(i.toPO());
		}
		po.setPresent(h);
		ArrayList<AccountPO> j=new ArrayList<AccountPO>();
		for(AccountVO i:account){
			j.add(i.toPO());
		}
		po.setAccount(j);
		ArrayList<CategoryPO> k=new ArrayList<CategoryPO>();
		for(CategoryVO i:category){
			k.add(i.toPO());
		}
		po.setCategory(k);
		ArrayList<CustomerPO> l=new ArrayList<CustomerPO>();
		for(CustomerVO i:customer){
			l.add(i.toPO());
		}
		po.setCustomer(l);
		ArrayList<ProductPO> m=new ArrayList<ProductPO>();
		for(ProductVO i:product){
			m.add(i.toPO());
		}
		po.setProduct(m);
		ArrayList<PromotionPO> o=new ArrayList<PromotionPO>();
		for(PromotionVO i:promotion){
			o.add(i.toPO());
		}
		po.setPromotion(o);
		po.setID(id);
		return po;
	}
	public String getID() {
		return id;
	}
	public void setID(String id) {
		this.id = id;
	}
	public ArrayList<PromotionVO> getPromotion() {
		return promotion;
	}
	public void setPromotion(ArrayList<PromotionVO> promotion) {
		this.promotion = promotion;
	}
	public ArrayList<CostVO> getCost() {
		return cost;
	}
	public void setCost(ArrayList<CostVO> cost) {
		this.cost = cost;
	}

}
