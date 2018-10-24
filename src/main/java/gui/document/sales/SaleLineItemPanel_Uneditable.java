package gui.document.sales;

import java.util.ArrayList;

import javax.swing.JDialog;

import saleBL.SaleLineItem;
import VO.SaleItemVO;
import gui.document.lineitem.LineItemPanel_Uneditable;
import gui.inventory.InventoryLineItem;
import gui.inventory.InventorySaleItem;

public class SaleLineItemPanel_Uneditable extends LineItemPanel_Uneditable {

	private static final long serialVersionUID = -1844197287922795313L;

	public SaleLineItemPanel_Uneditable(JDialog parent,
			ArrayList<InventoryLineItem> list) {
		super(parent, list);
	}
	
	public SaleLineItemPanel_Uneditable(JDialog parent) {
		super(parent);
	}

	@Override
	protected InventoryLineItem getLineItem(SaleItemVO product) {
		return new InventorySaleItem(new SaleLineItem(product.getID(),product.getName(),product.getModel(),1,product.getPrice(),0,product.getPS()));
	}

}
