package gui.document.account;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import progressBL.ProgressBL;
import progressBLService.ProgressBLService;
import enums.ResultMessage;
import VO.CheckOutVO;

public class CheckOutDialog_Red extends CheckOutDialog_Update {

	private static final long serialVersionUID = 8717179106003205222L;
	private JButton btn_red;

	public CheckOutDialog_Red(Frame parent, CheckOutVO vo) {
		super(parent, vo);
		setTitle("红冲付款单");
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
			btn_red = new JButton("红冲");
			btn_red.setActionCommand("red");
			btn_red.addActionListener(new ConfirmListener());
			buttonPane.add(btn_red);
			getRootPane().setDefaultButton(btn_red);
		}
	}
	
	private class ConfirmListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			vo.setList(pnl_item.getList());
			vo.setTotal(pnl_item.getTotal());
			vo.setHong(true);
			
			ProgressBLService progress = new ProgressBL();
			vo = progress.hong((CheckOutVO)vo);
			ResultMessage result = progress.hongcopy((CheckOutVO)vo);
			setResult(result);
			switch(result){
			case ITEM_NOT_EXIST:
				JOptionPane.showMessageDialog(CheckOutDialog_Red.this, "单据状态已改变，审批失败！");
				break;
			case REMOTE_FAIL:
				JOptionPane.showMessageDialog(CheckOutDialog_Red.this, "通信错误！");
				break;
			case SUCCESS:
				setVisible(false);
				break;
			default:
				JOptionPane.showMessageDialog(CheckOutDialog_Red.this, "未知错误！");
				break;
			
			}
		}
	}
}
