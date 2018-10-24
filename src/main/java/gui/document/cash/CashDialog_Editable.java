package gui.document.cash;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import VO.AccountVO;
import VO.CostVO;
import accountBL.AccountBL;
import enums.DataMessage;
import enums.ResultMessage;
import enums.Status;

public abstract class CashDialog_Editable extends CashDialog {

	private static final long serialVersionUID = 4948837237274346647L;
	protected JComboBox<AccountVO> cbx_account;
	private AccountVO[] accountlist;
	protected JButton btn_submit;
	protected JButton btn_save;

	public CashDialog_Editable(Frame parent,CostVO vo) {
		super(parent,vo);
		pnl_item.setList(vo.getList());
	}

	@Override
	protected CashItemPanel getItemPanel() {
		return new CashItemPanel_Editable(this);
	}

	@Override
	protected void setAccountComponent(JPanel contnetPanel,Object gbc_cbx_customer) {
		DataMessage<ArrayList<AccountVO>> result = new AccountBL().getAll();
		setResult(result.resultMessage);
		switch(result.resultMessage){
		case ITEM_NOT_EXIST:
			JOptionPane.showMessageDialog(this, "无可用银行账户，请先添加银行账户");
			setVisible(false);
			break;
		case REMOTE_FAIL:
			JOptionPane.showMessageDialog(this, "通信错误，无法获得客户列表");
			setVisible(false);
			break;
		case SUCCESS:		
			accountlist = new AccountVO[result.data.size()];
			for(int i = 0;i < accountlist.length;i++)
				accountlist[i] = result.data.get(i);
			break;
		default:
			JOptionPane.showMessageDialog(this, "未知错误！");
			setVisible(false);
			break;
		}
		
		cbx_account = new JComboBox<AccountVO>(accountlist);

		contentPanel.add(cbx_account, gbc_cbx_customer);
	}
	
	protected void setSelectedAccount(String id){
		for(AccountVO cust:accountlist)
			if(cust.getID().equals(id)){
				cbx_account.setSelectedItem(cust);
				break;
			}
	}
	
	protected void addButtons(JPanel buttonPane){
		{
			btn_save = new JButton("保存");
			btn_save.setActionCommand("save");
			btn_save.addActionListener(new ConfirmListener());
			buttonPane.add(btn_save);
		}
		buttonPane.add(Box.createHorizontalStrut(10));
		{
			btn_submit = new JButton("提交");
			btn_submit.setActionCommand("submit");
			btn_submit.addActionListener(new ConfirmListener());
			buttonPane.add(btn_submit);
		}
	}
	
	private class ConfirmListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(cbx_account.getSelectedIndex() == -1){
				lbl_msg.setText("请选择账户");
				return;
			}
			else if(pnl_item.hasEmpty()){
				lbl_msg.setText("请添加条目");
				return;
			}
			else{
				AccountVO account = cbx_account.getItemAt(cbx_account.getSelectedIndex());
				vo.setAccount(account.getID());
				vo.setAccountName(account.getName());
			}
			vo.setList(pnl_item.getList());
			vo.setTotal(pnl_item.getTotal());

			ResultMessage result = null;
			switch(e.getActionCommand()){		
			case "save":
				vo.setStatus(Status.DRAFT);
				result = getSaveResult();
				break;
			case "submit":
				vo.setStatus(Status.SUBMIT);
				result = getSubmitResult();
				break;
			}
			setResult(result);
			switch(result){
			case CANNOT_ADD:
				JOptionPane.showMessageDialog(CashDialog_Editable.this, "今日创建单据已达上限！");
				dispose();
				break;
			case REMOTE_FAIL:
				JOptionPane.showMessageDialog(CashDialog_Editable.this, "通信错误！");
				break;
			case SUCCESS:
				setVisible(false);
				break;
			default:
				JOptionPane.showMessageDialog(CashDialog_Editable.this, "未知错误！");
				break;
			
			}
		}
	}

	public abstract ResultMessage getSaveResult();

	public abstract ResultMessage getSubmitResult();
}
