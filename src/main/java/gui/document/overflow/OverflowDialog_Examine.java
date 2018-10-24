package gui.document.overflow;



import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import VO.OverflowVO;
import enums.ResultMessage;
import enums.Status;
import inventoryBL.InventoryBL;

public class OverflowDialog_Examine extends OverflowDialog_Uneditable {

	private static final long serialVersionUID = 5420044832755296075L;
	private JButton btn_pass,btn_deny;

	public OverflowDialog_Examine(Frame parent, OverflowVO vo) {
		super(parent, vo);
		setTitle("审批报损报溢单");
	}

	@Override
	protected void addButtons(JPanel buttonPane) {
		{
			btn_pass = new JButton("通过");
			btn_pass.setActionCommand("pass");
			btn_pass.addActionListener(new ConfirmListener());
			buttonPane.add(btn_pass);
		}
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

			switch(e.getActionCommand()){
			case "pass":
				vo.setStatus(Status.PASS);
				break;
			case "deny":
				vo.setStatus(Status.FAIL);
				break;
			}
			ResultMessage result = new InventoryBL().updateOverflow((OverflowVO) vo);
			setResult(result);
			switch(result){
			case ITEM_NOT_EXIST:
				JOptionPane.showMessageDialog(OverflowDialog_Examine.this, "单据状态已被修改，审批失败！");
				setVisible(false);
				break;
			case REMOTE_FAIL:
				JOptionPane.showMessageDialog(OverflowDialog_Examine.this, "通信错误！");
				break;
			case SUCCESS:
				setVisible(false);
				break;
			default:
				JOptionPane.showMessageDialog(OverflowDialog_Examine.this, "未知错误！");
				break;
			
			}
		}
	}
}
