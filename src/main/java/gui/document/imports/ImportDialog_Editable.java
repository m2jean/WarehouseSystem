package gui.document.imports;

import importBL.ImportBL;
import importBLService.ImportBLService;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import VO.CustomerVO;
import VO.ImportVO;
import businesslogic.userbl.UserBL;
import enums.ResultMessage;
import enums.Status;
import gui.GUIUtility;
import gui.MainFrame;
import gui.document.component.CustomerComboBox;

public abstract class ImportDialog_Editable extends ImportDialog {

	private static final long serialVersionUID = -5200143732997177884L;
	protected CustomerComboBox cbx_customer;
	protected ImportLineItemPanel_Editable pnl_item;
	protected JButton btn_save,btn_submit;
	protected ImportBLService importbl = new ImportBL();
	
	public ImportDialog_Editable(MainFrame parent) {
		super(parent);
	}
	public ImportDialog_Editable(MainFrame parent,ImportVO imp){
		super(parent,imp);
	}

	@Override
	protected void setCustomerComponent(JPanel contentPanel,GridBagConstraints gbc) {
			cbx_customer = CustomerComboBox.getInstance(this,"进货商");
			contentPanel.add(cbx_customer,gbc);
	}
	
	@Override
	protected void setEditable(){
		//Everything is already editable
		txf_stock.addFocusListener(GUIUtility.getSelectFocusListener());
		txa_remarks.addFocusListener(GUIUtility.getSelectFocusListener());
	}

	protected void addButtons(JPanel buttonPane){
		{
			btn_save = new JButton("保存");
			btn_save.setActionCommand("save");
			buttonPane.add(btn_save);
			btn_save.addActionListener(new ConfirmListener());
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

	private class ConfirmListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!checkset())
				return;
			vo.setOperator(new UserBL().getCurrent().getName());
			ResultMessage result = null;
			switch(e.getActionCommand()){
			case "save":
				vo.setStatus(Status.DRAFT);
				result = importbl.newImport((ImportVO) vo);
				if(result == ResultMessage.SUCCESS)
					setResult(ResultMessage.SAVE_SUCCESS);
				else
					setResult(ResultMessage.SAVE_FAILED);
				break;
			case "submit":
				vo.setStatus(Status.SUBMIT);
				result = importbl.newImport((ImportVO) vo);
				if(result == ResultMessage.SUCCESS)
					setResult(ResultMessage.SUBMIT_SUCCESS);
				else
					setResult(ResultMessage.SUBMIT_FAIL);
				break;
			}
			switch(result){
			case CANNOT_ADD:
				JOptionPane.showMessageDialog(ImportDialog_Editable.this, "今日创建单据已达上限！");
				setVisible(false);
				break;
			case REMOTE_FAIL:
				JOptionPane.showMessageDialog(ImportDialog_Editable.this, "通信错误！");
				break;
			case SUCCESS:
				setVisible(false);
				break;
			default:
				JOptionPane.showMessageDialog(ImportDialog_Editable.this, "未知错误！");
				break;
			
			}
		}
	}
	
	protected boolean checkset(){
		String stock = txf_stock.getText().trim();
		int selectedcust = cbx_customer.getSelectedIndex();
		if(stock.equals("")){
			lbl_msg.setText("请填写仓库");
			return false;
		}
		else if(selectedcust == -1){
			lbl_msg.setText("请选择供应商");
			return false;
		}
		else if(pnl_item.isEmpty()){
			lbl_msg.setText("请添加商品");
			return false;
		}
		vo.setWarehouse(stock);
		CustomerVO cust = cbx_customer.getItemAt(selectedcust);
		vo.setCustomerID(cust.getID());
		vo.setCustomer(cust.getName());
		vo.setRemarks(txa_remarks.getText());
		vo.setProductList(pnl_item.getItemList());
		vo.setTotal(pnl_item.getTotal());
		return true;
	}

	
}
