package gui.document.sales;

import inventoryBL.InventoryBL;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import progressBL.ProgressBL;
import progressBLService.ProgressBLService;
import enums.DataMessage;
import enums.ResultMessage;
import gui.MainFrame;
import VO.PresentVO;
import VO.SaleVO;

public class SaleDialog_Red extends SaleDialog {

	private static final long serialVersionUID = -6205923860087276444L;
	private JButton btn_red;

	public SaleDialog_Red(MainFrame parent, SaleVO vo) {
		super(parent, vo);
		setTitle("红冲销售单");
		txf_stock.setEditable(false);
		txf_clerk.setEditable(false);
		cbx_customer.setEnabled(false);
	}

	protected void addButtons(JPanel buttonPane){
		{
			btn_red = new JButton("红冲");
			btn_red.setActionCommand("red");
			btn_red.addActionListener(new ConfirmListener());
			buttonPane.add(btn_red);
			getRootPane().setDefaultButton(btn_red);
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
	
	private class ConfirmListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!checkset())
				return;
			vo.setHong(true);
			
			PresentVO prevo = null;
			if(vo.getPresent()!=null){
				DataMessage<PresentVO> present = new InventoryBL().getPresentTable(new PresentVO(vo.getPresent()));
				if(present.resultMessage != ResultMessage.SUCCESS){
					JOptionPane.showMessageDialog(SaleDialog_Red.this, "未知错误，无法获取对应的赠品单！");
					dispose();
				}
			}

			ProgressBLService progress = new ProgressBL();
			vo = progress.hong(vo);
			ResultMessage result = progress.hongcopy(vo,prevo);
			setResult(result);
			switch(result){
			case REMOTE_FAIL:
				JOptionPane.showMessageDialog(SaleDialog_Red.this, "通信错误！");
				break;
			case SUCCESS:
				setVisible(false);
				break;
			default:
				JOptionPane.showMessageDialog(SaleDialog_Red.this, "未知错误！");
				break;
			
			}
		}
	}
}
