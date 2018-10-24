package VO;

import java.util.ArrayList;



import enums.DataMessage;
import enums.ResultMessage;


public class ProgressVO {
	private DataMessage<ArrayList<SaleVO>> sales;
	private DataMessage<ArrayList<ReturnVO>> returns;
	private DataMessage<ArrayList<ImportVO>> imports;
	private DataMessage<ArrayList<ExportVO>> exports;
	private DataMessage<ArrayList<CheckInVO>> checkin;
	private DataMessage<ArrayList<CheckOutVO>> checkout;
	private DataMessage<ArrayList<CostVO>> cost;
	private DataMessage<ArrayList<OverflowVO>> overflow;
	private DataMessage<ArrayList<PresentVO>> present;
	
	public ProgressVO(){
		sales=new DataMessage<ArrayList<SaleVO>>(ResultMessage.IS_EMPTY);
		returns=new DataMessage<ArrayList<ReturnVO>>(ResultMessage.IS_EMPTY);
		imports=new DataMessage<ArrayList<ImportVO>>(ResultMessage.IS_EMPTY);
		exports=new DataMessage<ArrayList<ExportVO>>(ResultMessage.IS_EMPTY);
		checkin=new DataMessage<ArrayList<CheckInVO>>(ResultMessage.IS_EMPTY);
		checkout=new DataMessage<ArrayList<CheckOutVO>>(ResultMessage.IS_EMPTY);
		cost=new DataMessage<ArrayList<CostVO>>(ResultMessage.IS_EMPTY);
		overflow=new DataMessage<ArrayList<OverflowVO>>(ResultMessage.IS_EMPTY);
		present=new DataMessage<ArrayList<PresentVO>>(ResultMessage.IS_EMPTY);
	}

	public DataMessage<ArrayList<SaleVO>> getSales() {
		return sales;
	}

	public void setSales(DataMessage<ArrayList<SaleVO>> sales) {
		this.sales = sales;
	}

	public DataMessage<ArrayList<ReturnVO>> getReturns() {
		return returns;
	}

	public void setReturns(DataMessage<ArrayList<ReturnVO>> returns) {
		this.returns = returns;
	}

	public DataMessage<ArrayList<ImportVO>> getImports() {
		return imports;
	}

	public void setImports(DataMessage<ArrayList<ImportVO>> imports) {
		this.imports = imports;
	}

	public DataMessage<ArrayList<ExportVO>> getExports() {
		return exports;
	}

	public void setExports(DataMessage<ArrayList<ExportVO>> exports) {
		this.exports = exports;
	}

	public DataMessage<ArrayList<CheckInVO>> getCheckin() {
		return checkin;
	}

	public void setCheckin(DataMessage<ArrayList<CheckInVO>> checkin) {
		this.checkin = checkin;
	}

	public DataMessage<ArrayList<CheckOutVO>> getCheckout() {
		return checkout;
	}

	public void setCheckout(DataMessage<ArrayList<CheckOutVO>> checkout) {
		this.checkout = checkout;
	}

	public DataMessage<ArrayList<OverflowVO>> getOverflow() {
		return overflow;
	}

	public void setOverflow(DataMessage<ArrayList<OverflowVO>> overflow) {
		this.overflow = overflow;
	}

	public DataMessage<ArrayList<PresentVO>> getPresent() {
		return present;
	}

	public void setPresent(DataMessage<ArrayList<PresentVO>> present) {
		this.present = present;
	}

	public DataMessage<ArrayList<CostVO>> getCost() {
		return cost;
	}

	public void setCost(DataMessage<ArrayList<CostVO>> cost) {
		this.cost = cost;
	}
}
