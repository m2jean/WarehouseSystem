package gui.document.imports;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import progressBL.ProgressBL;
import progressBLService.ProgressBLService;
import enums.ResultMessage;
import gui.MainFrame;
import VO.ExportVO;

public class ExportDialog_Red extends ExportDialog_Update {

	private static final long serialVersionUID = 9044878824454532123L;
	private JButton btn_red;

	public ExportDialog_Red(MainFrame parent, ExportVO imp) {
		super(parent, imp);
		setTitle("红冲退货单");
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
			vo.setHong(true);

			ProgressBLService progress = new ProgressBL();
			vo = progress.hong((ExportVO)vo);
			ResultMessage result = progress.hongcopy((ExportVO)vo);
			setResult(result);
			switch(result){
			case ITEM_NOT_EXIST:
				JOptionPane.showMessageDialog(ExportDialog_Red.this, "单据状态已被修改，红冲失败！");
				setVisible(false);
				break;
			case REMOTE_FAIL:
				JOptionPane.showMessageDialog(ExportDialog_Red.this, "通信错误！");
				break;
			case SUCCESS:
				setVisible(false);
				break;
			default:
				JOptionPane.showMessageDialog(ExportDialog_Red.this, "未知错误！");
				break;
			
			}
		}
	}
}
