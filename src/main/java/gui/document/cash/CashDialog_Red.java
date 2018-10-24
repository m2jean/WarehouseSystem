package gui.document.cash;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import VO.CostVO;
import enums.ResultMessage;
import progressBL.ProgressBL;
import progressBLService.ProgressBLService;

public class CashDialog_Red extends CashDialog_Update {

	private static final long serialVersionUID = 522765377599498870L;
	private JButton btn_red;

	public CashDialog_Red(Frame parent, CostVO vo) {
		super(parent, vo);
		setTitle("红冲现金费用单");
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
			vo = progress.hong((CostVO)vo);
			ResultMessage result = progress.hongcopy((CostVO)vo);
			setResult(result);
			switch(result){
			case REMOTE_FAIL:
				JOptionPane.showMessageDialog(CashDialog_Red.this, "通信错误！");
				break;
			case SUCCESS:
				setVisible(false);
				break;
			default:
				JOptionPane.showMessageDialog(CashDialog_Red.this, "未知错误！");
				break;
			
			}
		}
	}
}
