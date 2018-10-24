package gui.document.sales;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import gui.MainFrame;
import VO.SaleVO;

public class SaleDialog_Examine extends SaleDialog {

	private static final long serialVersionUID = -6205923860087276444L;

	public SaleDialog_Examine(MainFrame parent, SaleVO vo) {
		super(parent, vo);
		setTitle("审批销售单");
		txf_stock.setEditable(false);
		txf_clerk.setEditable(false);
		cbx_customer.setEnabled(false);
	}

	protected void addButtons(JPanel buttonPane){
		{
			btn_account = new JButton("结算");
			btn_account.setActionCommand("submit");
			btn_account.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					if(!checkset())
						dispose();
					
					new CheckDialog_Examine(SaleDialog_Examine.this,vo,getPromotion()).setVisible(true);
				}					
			});
			buttonPane.add(btn_account);
			getRootPane().setDefaultButton(btn_account);
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
}
