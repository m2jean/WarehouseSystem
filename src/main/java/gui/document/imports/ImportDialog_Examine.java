package gui.document.imports;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import enums.ResultMessage;
import enums.Status;
import gui.MainFrame;
import VO.ImportVO;

public class ImportDialog_Examine extends ImportDialog_Update {

	private static final long serialVersionUID = 9044878824454532123L;
	private JButton btn_pass,btn_deny;
	private JLabel lbl_customer;

	public ImportDialog_Examine(MainFrame parent, ImportVO imp) {
		super(parent, imp);
		setTitle("审批进货单");
		lbl_customer.setText(vo.getCustomer());
	}
	
	@Override
	protected void setCustomerComponent(JPanel contentPanel,
			GridBagConstraints gbc) {
		lbl_customer = new JLabel();
		contentPanel.add(lbl_customer, gbc);
	}


	@Override
	protected void addButtons(JPanel buttonPane){
		{
			btn_pass = new JButton("通过");
			btn_pass.setActionCommand("pass");
			btn_pass.addActionListener(new ConfirmListener());
			buttonPane.add(btn_pass);
		}
		buttonPane.add(Box.createHorizontalStrut(10));
		{
			btn_deny = new JButton("否决");
			btn_deny.setActionCommand("deny");
			btn_deny.addActionListener(new ConfirmListener());
			buttonPane.add(btn_deny);
			getRootPane().setDefaultButton(btn_deny);
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
			String stock = txf_stock.getText().trim();
			if(stock.equals("")){
				lbl_msg.setText("请填写仓库");
				return;
			}
			else if(pnl_item.isEmpty()){
				lbl_msg.setText("请添加商品");
				return;
			}
			vo.setWarehouse(stock);
			vo.setRemarks(txa_remarks.getText());
			vo.setProductList(pnl_item.getItemList());
			vo.setTotal(pnl_item.getTotal());

			switch(e.getActionCommand()){
			case "pass":
				vo.setStatus(Status.PASS);
				break;
			case "deny":
				vo.setStatus(Status.FAIL);
				break;
			}
			ResultMessage result = importbl.updateImport((ImportVO) vo);
			setResult(result);
			switch(result){
			case ITEM_NOT_EXIST:
				JOptionPane.showMessageDialog(ImportDialog_Examine.this, "单据状态已被修改，审批失败！");
				setVisible(false);
				break;
			case REMOTE_FAIL:
				JOptionPane.showMessageDialog(ImportDialog_Examine.this, "通信错误！");
				break;
			case SUCCESS:
				setVisible(false);
				break;
			default:
				JOptionPane.showMessageDialog(ImportDialog_Examine.this, "未知错误！");
				break;
			
			}
		}
	}
}
