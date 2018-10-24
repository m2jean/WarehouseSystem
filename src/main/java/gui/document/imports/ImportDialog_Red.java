package gui.document.imports;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import progressBL.ProgressBL;
import progressBLService.ProgressBLService;
import enums.ResultMessage;
import gui.MainFrame;
import VO.ImportVO;
import businesslogic.userbl.UserBL;

public class ImportDialog_Red extends ImportDialog_Update {

	private static final long serialVersionUID = 9044878824454532123L;
	private JLabel lbl_customer;
	private JButton btn_red;

	public ImportDialog_Red(MainFrame parent, ImportVO imp) {
		super(parent, imp);
		setTitle("红冲进货单");
		txf_stock.setEditable(false);
		lbl_customer.setText(vo.getCustomer());
	}
	
	@Override
	protected void setCustomerComponent(JPanel contentPanel,
			GridBagConstraints gbc) {
		lbl_customer = new JLabel();
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
		{
			JButton btn_cancel = new JButton("取消");
			btn_cancel.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					close();
				}					
			});
			btn_cancel.setActionCommand("Cancel");
			buttonPane.add(btn_cancel);
		}
	}

	private class ConfirmListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(pnl_item.isEmpty()){
				JOptionPane.showMessageDialog(ImportDialog_Red.this,"商品不能为空！");
				return;
			}
			vo.setRemarks(txa_remarks.getText());
			vo.setProductList(pnl_item.getItemList());
			vo.setTotal(pnl_item.getTotal());
			vo.setOperator(new UserBL().getCurrent().getName());
			vo.setHong(true);
			
			ProgressBLService progress = new ProgressBL();
			vo = progress.hong((ImportVO)vo);
			ResultMessage result = progress.hongcopy((ImportVO)vo);
			setResult(result);
			switch(result){
			case ITEM_NOT_EXIST:
				JOptionPane.showMessageDialog(ImportDialog_Red.this, "单据状态已被修改，红冲失败！");
				setVisible(false);
				break;
			case REMOTE_FAIL:
				JOptionPane.showMessageDialog(ImportDialog_Red.this, "通信错误！");
				break;
			case SUCCESS:
				setVisible(false);
				break;
			default:
				JOptionPane.showMessageDialog(ImportDialog_Red.this, "未知错误！");
				break;
			
			}
		}
	}
}
