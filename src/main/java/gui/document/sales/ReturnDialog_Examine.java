package gui.document.sales;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import saleBL.ReturnBL;
import enums.ResultMessage;
import enums.Status;
import gui.MainFrame;
import VO.ReturnVO;

public class ReturnDialog_Examine extends ReturnDialog {

	private static final long serialVersionUID = 460493888685194428L;
	private JButton btn_pass,btn_deny;

	public ReturnDialog_Examine(MainFrame parent, ReturnVO vo) {
		super(parent, vo);
		setTitle("审批退货单");
		txf_stock.setEditable(false);
		txf_clerk.setEditable(false);
	}
	
	protected void addButtons(JPanel buttonPane) {
		{
			btn_pass = new JButton("通过");
			btn_pass.setActionCommand("pass");
			buttonPane.add(btn_pass);
			getRootPane().setDefaultButton(btn_pass);
		}
		{
			btn_deny = new JButton("否决");
			btn_deny.setActionCommand("deny");
			buttonPane.add(btn_deny);
			getRootPane().setDefaultButton(btn_deny);
		}
		btn_pass.addActionListener(new ConfirmListener());
		btn_deny.addActionListener(new ConfirmListener());
	}
	
	private class ConfirmListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			vo.setProductList(pnl_item.getItemList());
			vo.setTotal(pnl_item.getTotal());

			switch(e.getActionCommand()){
			case "pass":
				vo.setStatus(Status.PASS);
			case "deny":
				vo.setStatus(Status.FAIL);
			}
			
			ResultMessage result = new ReturnBL().shenpi(vo);
			setResult(result);
			switch(result){
			case REMOTE_FAIL:
				JOptionPane.showMessageDialog(ReturnDialog_Examine.this, "通信错误！");
				break;
			case SUCCESS:
				JOptionPane.showMessageDialog(ReturnDialog_Examine.this, "操作成功！");
				setVisible(false);
				break;
			default:
				JOptionPane.showMessageDialog(ReturnDialog_Examine.this, "未知错误！");
				break;
			
			}
		}
	}
}
