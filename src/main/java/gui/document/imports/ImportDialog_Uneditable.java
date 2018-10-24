package gui.document.imports;

import enums.Status;
import gui.MainFrame;
import gui.document.vo.ImportExportVO;
import gui.inventory.InventoryImportItem;
import gui.inventory.InventoryLineItem;
import importBL.ImportLineItem;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ImportDialog_Uneditable extends ImportDialog {

	private static final long serialVersionUID = 7217874421384134834L;
	
	private JLabel lbl_customer;
	private ImportLineItemPanel_Uneditable pnl_item;

	public ImportDialog_Uneditable(MainFrame parent,ImportExportVO vo) {
		super(parent,vo);
		setTitle("查看进退货单据");
		if(vo.getStatus() != Status.DRAFT)
			lbl_id.setText("编号："+vo.getID());
		lbl_customer.setText(vo.getCustomer());
		txf_stock.setText(vo.getWarehouse());
		setOperator(vo.getOperator());
		txa_remarks.setText(vo.getRemarks());
	}

	@Override
	protected void setEditable() {
		txf_stock.setEditable(false);
		txa_remarks.setEditable(false);
	}
	
	@Override
	protected void addButtons(JPanel buttonPane){
		JButton btn_cancel = new JButton("取消");
		btn_cancel.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}					
		});
		btn_cancel.setActionCommand("Cancel");
		buttonPane.add(btn_cancel);
	}

	@Override
	protected void setCustomerComponent(JPanel contentPanel,
			GridBagConstraints gbc) {
		lbl_customer = new JLabel();
		contentPanel.add(lbl_customer,gbc);
	}

	@Override
	protected void setLineItemPanel(JPanel contentPanel, GridBagConstraints gbc) {
		ArrayList<ImportLineItem> list = vo.getProductList();
		ArrayList<InventoryLineItem> items = new ArrayList<InventoryLineItem>();
		for(ImportLineItem item : list)
			items.add(new InventoryImportItem(item));
		pnl_item =new ImportLineItemPanel_Uneditable(this,items);
		contentPanel.add(pnl_item, gbc);
	}

}
