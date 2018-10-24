package gui.document.imports;

import importBL.ImportLineItem;

import java.util.ArrayList;

import javax.swing.JDialog;

import VO.ProductVO;
import VO.SaleItemVO;
import gui.document.lineitem.LineItemPanel_Uneditable;
import gui.inventory.InventoryImportItem;
import gui.inventory.InventoryLineItem;

public class ImportLineItemPanel_Uneditable extends LineItemPanel_Uneditable {

	private static final long serialVersionUID = -1844197287922795313L;

	public ImportLineItemPanel_Uneditable(JDialog parent,
			ArrayList<InventoryLineItem> list) {
		super(parent, list);
	}

	@Override
	protected InventoryLineItem getLineItem(SaleItemVO saleitem) {
		ProductVO product = (ProductVO)saleitem;
		return new InventoryImportItem(new ImportLineItem(product.getID(),product.getName(),product.getModel(),1,product.getPriceIn(),""));
	}

}
