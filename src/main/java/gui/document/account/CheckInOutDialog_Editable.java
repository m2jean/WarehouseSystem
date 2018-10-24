package gui.document.account;

import enums.ResultMessage;
import enums.Status;
import gui.document.component.CustomerComboBox;
import gui.document.vo.CheckInOutVO;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public abstract class CheckInOutDialog_Editable extends CheckInOutDialog {

	private static final long serialVersionUID = 4948837237274346647L;
	protected CustomerComboBox cbx_customer;
	protected JButton btn_submit;
	protected JButton btn_save;

	public CheckInOutDialog_Editable(Frame parent,CheckInOutVO vo) {
		super(parent,vo);
		pnl_item.setList(vo.getList());
		if(cbx_customer != null)
			for(int i = 0; i < cbx_customer.getItemCount();++i){
				if(cbx_customer.getItemAt(i).getID().equals(vo.getCustomer())){
					cbx_customer.setSelectedIndex(i);
					break;
				}
			}
	}

	@Override
	protected BillItemPanel getItemPanel() {
		return new BillItemPanel_Editable(this);
	}

	@Override
	protected void setCustomerComponent() {
		cbx_customer = CustomerComboBox.getInstance(this,"");
		GridBagConstraints gbc_cbx_customer = new GridBagConstraints();
		gbc_cbx_customer.insets = new Insets(0, 0, 5, 0);
		gbc_cbx_customer.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbx_customer.gridx = 3;
		gbc_cbx_customer.gridy = 0;
		contentPanel.add(cbx_customer, gbc_cbx_customer);
	}
	
	private class ConfirmListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(cbx_customer.getSelectedIndex() == -1){
				lbl_msg.setText("请选择客户");
				return;
			}
			else{
				vo.setCustomer(cbx_customer.getSelectedCustomer().getID());
				vo.setName(cbx_customer.getSelectedCustomer().getName());
			}
			vo.setList(pnl_item.getList());
			vo.setTotal(pnl_item.getTotal());
			
			ResultMessage result = null;
			switch(e.getActionCommand()){		
			case "save":
				vo.setStatus(Status.DRAFT);
				result = getSaveResult();
				if(result == ResultMessage.SUCCESS)
					setResult(ResultMessage.SAVE_SUCCESS);
				else
					setResult(ResultMessage.SAVE_FAIL);
				break;
			case "submit":
				vo.setStatus(Status.SUBMIT);
				result = getSubmitResult();
				if(result == ResultMessage.SUCCESS)
					setResult(ResultMessage.SUBMIT_SUCCESS);
				else
					setResult(ResultMessage.SUBMIT_FAIL);
				break;
			}
			switch(result){
			case CANNOT_ADD:
				JOptionPane.showMessageDialog(CheckInOutDialog_Editable.this, "今日创建单据已达上限！");
				break;
			case REMOTE_FAIL:
				JOptionPane.showMessageDialog(CheckInOutDialog_Editable.this, "通信错误！");
				break;
			case SUCCESS:
				setVisible(false);
				break;
			default:
				JOptionPane.showMessageDialog(CheckInOutDialog_Editable.this, "未知错误！");
				break;
			
			}
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
	}

	public abstract ResultMessage getSaveResult();

	public abstract ResultMessage getSubmitResult();
}
