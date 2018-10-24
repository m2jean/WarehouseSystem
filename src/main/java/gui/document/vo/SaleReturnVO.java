package gui.document.vo;

import java.util.ArrayList;

import saleBL.SaleLineItem;

public interface SaleReturnVO extends TradeVO{

	public ArrayList<SaleLineItem> getProductList();
	
	public void setProductList(ArrayList<SaleLineItem> list);
}
