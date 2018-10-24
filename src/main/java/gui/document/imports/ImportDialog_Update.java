package gui.document.imports;

import java.awt.GridBagConstraints;
import java.util.ArrayList;

import javax.swing.JPanel;

import VO.ImportVO;
import enums.Status;
import gui.MainFrame;
import gui.inventory.InventoryImportItem;
import gui.inventory.InventoryLineItem;
import importBL.ImportLineItem;

public class ImportDialog_Update extends ImportDialog_Editable {

	private static final long serialVersionUID = -2027262998117109598L;

	public ImportDialog_Update(MainFrame parent,ImportVO imp) {
		super(parent,imp);
		setTitle("修改进货单");
		if(vo.getStatus() != Status.DRAFT)
			lbl_id.setText("编号："+vo.getID());
		if(cbx_customer!=null)
			cbx_customer.setSelected(vo.getCustomerID());
		txf_stock.setText(imp.getWarehouse());
		setOperator(imp.getOperator());
		txa_remarks.setText(imp.getRemarks());
	}

	@Override
	protected void setLineItemPanel(JPanel contentPanel, GridBagConstraints gbc) {
		ArrayList<ImportLineItem> list = vo.getProductList();
		ArrayList<InventoryLineItem> items = new ArrayList<InventoryLineItem>();
		for(ImportLineItem item : list)
			items.add(new InventoryImportItem(item));
		pnl_item =new ImportLineItemPanel_Editable(this,items);
		contentPanel.add(pnl_item, gbc);
	}

}
