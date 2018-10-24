package gui.document.sales;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import saleBL.SaleBL;
import enums.ResultMessage;
import enums.Status;
import VO.PromotionVO;
import VO.SaleVO;

public class CheckDialog_Examine extends CheckDialog {

	private static final long serialVersionUID = -808018797280677285L;

	public CheckDialog_Examine(JDialog parent, SaleVO sale,
			ArrayList<PromotionVO> promotion) {
		super(parent, sale, promotion);
		setTitle("审批结算");
	}

	protected void addButtons(JPanel buttonPane){
		{
			JButton btn_pass = new JButton("通过");
			btn_pass.setActionCommand("pass");
			btn_pass.addActionListener(new ExamListener());
			buttonPane.add(btn_pass);
		}
		{
			JButton btn_deny = new JButton("否决");
			btn_deny.setActionCommand("deny");
			btn_deny.addActionListener(new ExamListener());
			buttonPane.add(btn_deny);
		}
		{
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
	}
	
	private class ExamListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!checkset())
				return;
			
			calculate();
			
			switch(e.getActionCommand()){
			case "pass":
				vo.setStatus(Status.PASS);
				break;
			case "deny":
				vo.setStatus(Status.FAIL);
				break;
			}
			
			ResultMessage submit = new SaleBL().shenpi(vo);
			setResult(submit);
			switch(submit){
			case REMOTE_FAIL:
				JOptionPane.showMessageDialog(CheckDialog_Examine.this, "通信失败！");
				break;
			case SUCCESS:
				JOptionPane.showMessageDialog(CheckDialog_Examine.this, "操作成功！");
				setVisible(false);
				break;
			default:
				JOptionPane.showMessageDialog(CheckDialog_Examine.this, "未知错误！");
				break;
			}
		}	
	}
}
