package gui.document.sales;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import progressBL.ProgressBL;
import progressBLService.ProgressBLService;
import enums.ResultMessage;
import gui.MainFrame;
import VO.ReturnVO;

public class ReturnDialog_Red extends ReturnDialog {

	private static final long serialVersionUID = 460493888685194428L;
	private JButton btn_red;

	public ReturnDialog_Red(MainFrame parent, ReturnVO vo) {
		super(parent, vo);
		setTitle("红冲退货单");
		txf_stock.setEditable(false);
		txf_clerk.setEditable(false);
	}
	
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
			vo.setProductList(pnl_item.getItemList());
			vo.setTotal(pnl_item.getTotal());
			vo.setHong(true);

			ProgressBLService progress = new ProgressBL();
			vo = progress.hong(vo);
			ResultMessage result = progress.hongcopy(vo);
			setResult(result);
			switch(result){
			case REMOTE_FAIL:
				JOptionPane.showMessageDialog(ReturnDialog_Red.this, "通信错误！");
				break;
			case SUCCESS:
				JOptionPane.showMessageDialog(ReturnDialog_Red.this, "操作成功！");
				setVisible(false);
				break;
			default:
				JOptionPane.showMessageDialog(ReturnDialog_Red.this, "未知错误！");
				break;
			
			}
		}
	}
}
