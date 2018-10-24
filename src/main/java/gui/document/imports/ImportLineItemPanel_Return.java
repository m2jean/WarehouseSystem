package gui.document.imports;

import importBL.ImportLineItem;

import java.util.ArrayList;

import javax.swing.JDialog;

import VO.ProductVO;
import VO.SaleItemVO;
import gui.document.lineitem.LineItemPanel_Return;
import gui.inventory.InventoryImportItem;
import gui.inventory.InventoryLineItem;

public class ImportLineItemPanel_Return extends LineItemPanel_Return implements ImportLineItemPanel{

	private static final long serialVersionUID = 7164324458736301589L;

	public ImportLineItemPanel_Return(JDialog parent,
			ArrayList<InventoryLineItem> list) {
		super(parent,list);
	}

	@Override
	protected InventoryLineItem getLineItem(SaleItemVO saleitem) {
		ProductVO product = (ProductVO)saleitem;
		return new InventoryImportItem(new ImportLineItem(product.getID(),product.getName(),product.getModel(),1,product.getPriceIn(),""));
	}

	@Override
	public ArrayList<ImportLineItem> getItemList() {
		ArrayList<ImportLineItem> itemlist = new ArrayList<ImportLineItem>();
		for(InventoryLineItem item:list)
			itemlist.add(((InventoryImportItem)item).getLineItem());
		return itemlist;
	}
}
