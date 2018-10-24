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

public class OverflowDialog_Red extends OverflowDialog_Uneditable {

	private static final long serialVersionUID = 5420044832755296075L;
	private JButton btn_red;

	public OverflowDialog_Red(Frame parent, OverflowVO vo) {
		super(parent, vo);
		setTitle("红冲报损报溢单");
		txf_actual.setEditable(true);
	}

	@Override
	protected void addButtons(JPanel buttonPane) {
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
			if(!txf_actual.getText().trim().matches("//d+")){
				JOptionPane.showMessageDialog(OverflowDialog_Red.this, "请输入合法整数！");
				return;
			}
			vo.setNumInWarehouse(Integer.parseInt(txf_actual.getText().trim()));
			vo.setHong(true);
			
			vo.setStatus(Status.SUBMIT);
			ResultMessage result = new InventoryBL().newOverflow(vo);
			setResult(result);
			switch(result){
			case ITEM_NOT_EXIST:
				JOptionPane.showMessageDialog(OverflowDialog_Red.this, "单据状态已被修改，红冲失败！");
				setVisible(false);
				break;
			case REMOTE_FAIL:
				JOptionPane.showMessageDialog(OverflowDialog_Red.this, "通信错误！");
				break;
			case SUCCESS:
				JOptionPane.showMessageDialog(OverflowDialog_Red.this, "操作成功！");
				setVisible(false);
				break;
			default:
				JOptionPane.showMessageDialog(OverflowDialog_Red.this, "未知错误！");
				break;
			
			}
		}
	}
}
