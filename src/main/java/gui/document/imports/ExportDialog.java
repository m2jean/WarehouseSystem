package gui.document.imports;

import enums.DataMessage;
import enums.ResultMessage;
import enums.Status;
import gui.MainFrame;
import gui.inventory.InventoryImportItem;
import gui.inventory.InventoryLineItem;
import importBL.ExportBL;
import importBL.ImportBL;
import importBL.ImportLineItem;
import importBLService.ExportBLService;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import VO.ExportVO;
import VO.ImportVO;
import businesslogic.userbl.UserBL;

public abstract class ExportDialog extends ImportDialog {

	private static final long serialVersionUID = 6236636750302593548L;
	protected JLabel lbl_customer;
	protected ImportLineItemPanel_Return pnl_item;
	protected ExportBLService exportbl = new ExportBL();
	protected JButton btn_save,btn_submit;
	//private int[] limit;
	
	public ExportDialog(MainFrame parent,ImportVO imp){		
		super(parent,new ExportVO(imp,new UserBL().getCurrent().getName()));
		int[] limit = new int[imp.getProductList().size()];
		for(int i = 0;i < limit.length;i++)
			limit[i] = imp.getProductList().get(i).getNumber();
		pnl_item.setLimit(limit);
		
		lbl_customer.setText(vo.getCustomerID()+"  "+vo.getCustomer());
		txf_stock.setText(vo.getWarehouse());
		setOperator(vo.getOperator());
		txa_remarks.setText(vo.getRemarks());
	}
	
	

	public ExportDialog(MainFrame parent,ExportVO vo) {
		super(parent,vo);
		lbl_customer.setText(vo.getCustomerID()+"  "+vo.getCustomer());
		txf_stock.setText(vo.getWarehouse());
		setOperator(vo.getOperator());
		txa_remarks.setText(vo.getRemarks());
		
		DataMessage<ImportVO> result = new ImportBL().getImport(new ImportVO(vo.getImportID()));
		setResult(result.resultMessage);
		switch(result.resultMessage){
		case REMOTE_FAIL:
			JOptionPane.showMessageDialog(ExportDialog.this, "通信错误，无法获取相应的进货单！");
			setVisible(false);
			break;
		case SUCCESS:
			ImportVO imp = result.data;
			int[] limit = new int[imp.getProductList().size()];
			for(int i = 0;i < limit.length;i++)
				limit[i] = imp.getProductList().get(i).getNumber();
			pnl_item.setLimit(limit);
			break;
		default:
			JOptionPane.showMessageDialog(ExportDialog.this, "未知错误，无法获取相应的进货单！");
			setVisible(false);
			break;
		}
	}
	
	protected void addButtons(JPanel buttonPane){
		{
			btn_save = new JButton("保存");
			btn_save.setActionCommand("save");
			btn_save.addActionListener(new ConfirmListener());
			buttonPane.add(btn_save);
		}
		buttonPane.add(Box.createHorizontalStrut(10));
		{
			btn_submit = new JButton("提交");
			btn_submit.setActionCommand("submit");
			btn_submit.addActionListener(new ConfirmListener());
			buttonPane.add(btn_submit);
		}
		buttonPane.add(Box.createHorizontalStrut(10));
		{
			JButton btn_cancel = new JButton("取消");
			btn_cancel.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					close();
				}					
			});
			btn_cancel.setActionCommand("Cancel");
			buttonPane.add(btn_cancel);
		}
	}

	@Override
	protected void setEditable() {
	}

	@Override
	protected void setCustomerComponent(JPanel contentPanel,
			GridBagConstraints gbc) {
		lbl_customer = new JLabel();
		contentPanel.add(lbl_customer, gbc);
	}

	@Override
	protected void setLineItemPanel(JPanel contentPanel, GridBagConstraints gbc) {
		ArrayList<ImportLineItem> list = vo.getProductList();
		ArrayList<InventoryLineItem> items = new ArrayList<InventoryLineItem>();
		for(ImportLineItem item : list)
			items.add(new InventoryImportItem(item));
		pnl_item =new ImportLineItemPanel_Return(this,items);
		contentPanel.add(pnl_item, gbc);
	}

	private class ConfirmListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!checkset())
				return;
			ResultMessage result = null;
			vo.setOperator(new UserBL().getCurrent().getName());
			switch(e.getActionCommand()){
			case "save":
				vo.setStatus(Status.DRAFT);
				result = getResult();
				break;
			case "submit":
				vo.setStatus(Status.SUBMIT);
				result = exportbl.newExport((ExportVO) vo);
				break;
			}
			setResult(result);
			switch(result){
			case CANNOT_ADD:
				JOptionPane.showMessageDialog(ExportDialog.this, "今日创建单据已达上限！");
				setVisible(false);
				break;
			case REMOTE_FAIL:
				JOptionPane.showMessageDialog(ExportDialog.this, "通信错误！");
				break;
			case SUCCESS:
				setVisible(false);
				break;
			case ITEM_NOT_EXIST:
				JOptionPane.showMessageDialog(ExportDialog.this, "单据状态已变！");
				setVisible(false);
				break;
			case NOT_MATCH:
				lbl_msg.setText("退货数量超出！");
				break;
			default:
				JOptionPane.showMessageDialog(ExportDialog.this, "未知错误！");
				break;
			
			}
		}
	}	
	
	protected boolean checkset(){
		String stock = txf_stock.getText().trim();
		if(stock.equals("")){
			lbl_msg.setText("请填写仓库");
			return false;
		}
		else if(pnl_item.isEmpty()){
			lbl_msg.setText("请添加商品");
			return false;
		}
		vo.setWarehouse(stock);
		vo.setRemarks(txa_remarks.getText());
		vo.setProductList(pnl_item.getItemList());
		vo.setTotal(pnl_item.getTotal());
		return true;
	}
	
}
