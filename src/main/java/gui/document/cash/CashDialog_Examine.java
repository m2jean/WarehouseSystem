package gui.document.cash;



import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import VO.CostVO;
import accountBL.CostBL;
import enums.ResultMessage;
import enums.Status;

public class CashDialog_Examine extends CashDialog_Update {

	private static final long serialVersionUID = 522765377599498870L;
	private JButton btn_pass,btn_deny;

	public CashDialog_Examine(Frame parent, CostVO vo) {
		super(parent, vo);
		setTitle("审批现金费用单");
		lbl_account.setText("银行账户："+vo.getAccountName());
	}

	@Override
	protected void setAccountComponent(JPanel contnetPanel,
			Object gbc_cbx_customer) {
		//do nothing
	}
	@Override
	protected void setSelectedAccount(String id){
		//do nothing
	}

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
			
			ResultMessage result = new CostBL().shenpi((CostVO) vo);
			setResult(result);
			switch(result){
			case REMOTE_FAIL:
				JOptionPane.showMessageDialog(CashDialog_Examine.this, "通信错误！");
				break;
			case SUCCESS:
				setVisible(false);
				break;
			default:
				JOptionPane.showMessageDialog(CashDialog_Examine.this, "未知错误！");
				break;
			
			}
		}
	}
}
