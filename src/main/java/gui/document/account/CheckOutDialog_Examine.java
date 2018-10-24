package gui.document.account;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import VO.CheckOutVO;
import accountBL.CheckOutBL;
import enums.ResultMessage;
import enums.Status;

public class CheckOutDialog_Examine extends CheckOutDialog_Update {

	private static final long serialVersionUID = 8717179106003205222L;
	private JButton btn_pass,btn_deny;

	public CheckOutDialog_Examine(Frame parent, CheckOutVO vo) {
		super(parent, vo);
		setTitle("审批付款单");
		lbl_customer.setText("客户："+vo.getCustomer());
	}
	
	@Override
	protected void setCustomerComponent() {// do nothing
	}
	@Override
	public void setSelected(String id){// do nothing
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
	}
	
	private class ConfirmListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			vo.setList(pnl_item.getList());
			vo.setTotal(pnl_item.getTotal());

			switch(e.getActionCommand()){
			case "pass":
				vo.setStatus(Status.PASS);
				break;
			case "deny":
				vo.setStatus(Status.FAIL);
				break;
			}
			
			ResultMessage result = new CheckOutBL().shenpi((CheckOutVO) vo);
			setResult(result);
			switch(result){
			case ITEM_NOT_EXIST:
				JOptionPane.showMessageDialog(CheckOutDialog_Examine.this, "单据状态已改变，审批失败！");
				break;
			case REMOTE_FAIL:
				JOptionPane.showMessageDialog(CheckOutDialog_Examine.this, "通信错误！");
				break;
			case SUCCESS:
				setVisible(false);
				break;
			default:
				JOptionPane.showMessageDialog(CheckOutDialog_Examine.this, "未知错误！");
				break;
			
			}
		}
	}
}
