package gui.document.imports;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import enums.ResultMessage;
import enums.Status;
import gui.MainFrame;
import VO.ExportVO;

public class ExportDialog_Examine extends ExportDialog_Update {

	private static final long serialVersionUID = 9044878824454532123L;
	private JButton btn_pass,btn_deny;

	public ExportDialog_Examine(MainFrame parent, ExportVO imp) {
		super(parent, imp);
		setTitle("审批退货单");
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
					setResult(ResultMessage.CLOSE);
					setVisible(false);
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

			switch(e.getActionCommand()){
			case "pass":
				vo.setStatus(Status.PASS);
				break;
			case "deny":
				vo.setStatus(Status.FAIL);
				break;
			}
			ResultMessage result = exportbl.updateExport((ExportVO) vo);
			setResult(result);
			switch(result){
			case ITEM_NOT_EXIST:
				JOptionPane.showMessageDialog(ExportDialog_Examine.this, "单据状态已被修改，审批失败！");
				setVisible(false);
				break;
			case REMOTE_FAIL:
				JOptionPane.showMessageDialog(ExportDialog_Examine.this, "通信错误！");
				break;
			case SUCCESS:
				setVisible(false);
				break;
			default:
				JOptionPane.showMessageDialog(ExportDialog_Examine.this, "未知错误！");
				break;
			
			}
		}
	}
}
