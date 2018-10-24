package gui.document.account;

import javax.swing.JDialog;

import VO.AccountVO;
import VO.ListInVO;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JButton;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import accountBL.AccountBL;
import enums.DataMessage;


public class BillItemPanel_Editable extends BillItemPanel {

	private static final long serialVersionUID = 5694502460547950389L;
	private JComboBox<AccountVO> cbx_account;
	private JButton btn_delete;
	private ArrayList<AccountVO> acclist;
	private JButton btn_add;

	public BillItemPanel_Editable(JDialog parent) {// For adding
		super(parent);
		construct();
	}
	
	public BillItemPanel_Editable(JDialog parent,ArrayList<ListInVO> list){// For updating
		super(parent,list);
		construct();
	}
	
	private void construct(){
		lbl_hint.setText("双击表格编辑");
		
		DataMessage<ArrayList<AccountVO>> result = new AccountBL().getAll();
		switch(result.resultMessage){
		case ITEM_NOT_EXIST:
		case IS_EMPTY:
			JOptionPane.showMessageDialog(parent, "没有账户可以添加！");
			parent.dispose();
			break;
		case REMOTE_FAIL:
			JOptionPane.showMessageDialog(parent, "通信错误，无法获得账户列表！");
			parent.dispose();
			break;
		case SUCCESS:
			AccountVO[] list = new AccountVO[result.data.size()];
			acclist = new ArrayList<AccountVO>(list.length);
			for(int i = 0;i < list.length;i++)
				list[i] = result.data.get(i);
			cbx_account = new JComboBox<AccountVO>(list);
			cbx_account.setSelectedIndex(0);
			pnl_func.add(cbx_account);
			break;
		default:
			JOptionPane.showMessageDialog(parent, "未知错误！");
			parent.dispose();
			break;
		}
		
		Component horizontalStrut = Box.createHorizontalGlue();
		pnl_func.add(horizontalStrut);
		
		btn_add = new JButton("添加");
		btn_add.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				AccountVO account = cbx_account.getItemAt(cbx_account.getSelectedIndex());
				if(!contains(account)){
					list.add(new ListInVO(account));
					tbl_list.setModel(new BillItemTableModel_Editable(list,lbl_total,parent));
					cbx_account.removeItem(account);
					acclist.add(account);
				}
				if(cbx_account.getItemCount() == 0){
					cbx_account.setEnabled(false);
					btn_add.setEnabled(false);
				}
			}			
		});
		btn_add.setEnabled(true);
		pnl_func.add(btn_add);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		pnl_func.add(horizontalStrut_1);
		
		btn_delete = new JButton("删除");
		btn_delete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				BillItemTableModel model = (BillItemTableModel) tbl_list.getModel();
				ListInVO invo = model.getItemAt(tbl_list.getSelectedRow());
				list.remove(invo);
				tbl_list.setModel(new BillItemTableModel_Editable(list,lbl_total,parent));
				
				int i;
				for(i = 0;i < acclist.size();++i){
					if(invo.getAccountID().equals(acclist.get(i).getID())){
						break;
					}
				}
				cbx_account.addItem(acclist.get(i));
				acclist.remove(i);
				cbx_account.setEnabled(true);
				btn_add.setEnabled(true);
			}			
		});
		btn_delete.setEnabled(false);
		pnl_func.add(btn_delete);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		pnl_func.add(horizontalGlue);
		
		tbl_list.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if(tbl_list.getSelectedRow() == -1)
					btn_delete.setEnabled(false);
				else
					btn_delete.setEnabled(true);
			}	
		});
	}

	protected void setTableModel(){
		tbl_list.setModel(new BillItemTableModel_Editable(list,lbl_total,parent));
	}

	public double getTotal(){
		return ((BillItemTableModel_Editable)tbl_list.getModel()).getTotal();
	}
	
	@Override
	public void setList(ArrayList<ListInVO> list){
		super.setList(list);
		for(int i = 0;i < list.size();++i){
			for(int j = 0;j < cbx_account.getItemCount();++j){
				AccountVO acvo = cbx_account.getItemAt(j);
				if(acvo.getID().equals(list.get(i).getAccountID())){
					cbx_account.removeItemAt(j);
					acclist.add(acvo);
				}
			}
		}
		if(cbx_account.getItemCount() == 0){
			cbx_account.setEnabled(false);
			btn_add.setEnabled(false);
		}
	}
}
