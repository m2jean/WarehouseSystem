package gui.account;

import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.BorderLayout;

import javax.swing.JButton;

import java.awt.CardLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.JScrollPane;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;

import VO.AccountVO;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;

import enums.DataMessage;
import enums.ResultMessage;
import gui.ClearHintTextField;
import gui.GUIUtility;
import gui.MainFrame;
import gui.ResultDialog;
import accountBL.AccountBL;
import accountBLService.AccountBLService;

import javax.swing.JTextField;

public class AccountPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7286749578439976602L;
	private static final String NOT_FOUND = "not found";
	private static final String ACCOUNT_LIST = "account list";
	private static final String NO_ACCOUNT = "no account";
	
	private JTable tbl_account;
	private JButton btn_add,btn_delete;
	private CardLayout cardlayout;
	private JPanel pnl_account;
	
	private AccountBLService accountbl = new AccountBL();
	private JPanel pnl_search;
	private JButton btn_showall;
	private JTextField txf_name;
	private JLabel lbl_name;
	private JTextField txf_id;
	private JLabel lbl_id;
	private JButton btn_search;
	private JButton btn_update;

	/**
	 * Create the panel.
	 */
	public AccountPanel() {
		setPreferredSize(new Dimension(800, 640));
		setLayout(new BorderLayout(0, 0));
		
		pnl_search = new JPanel();
		FlowLayout flowLayout = (FlowLayout) pnl_search.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		add(pnl_search, BorderLayout.NORTH);
		
		lbl_id = new JLabel("编号：");
		lbl_id.setHorizontalAlignment(SwingConstants.TRAILING);
		pnl_search.add(lbl_id);
		
		txf_id = new ClearHintTextField("默认为任意编号");
		pnl_search.add(txf_id);
		txf_id.setColumns(10);
		
		lbl_name = new JLabel("名称：");
		lbl_name.setHorizontalAlignment(SwingConstants.TRAILING);
		pnl_search.add(lbl_name);
		
		txf_name = new ClearHintTextField("默认为任意名称");
		pnl_search.add(txf_name);
		txf_name.setColumns(10);
		
		btn_search = new JButton("搜索");
		btn_search.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				DataMessage<ArrayList<AccountVO>> result = accountbl.findAccount(txf_id.getText().trim(),txf_name.getText().trim());
				switch(result.resultMessage){
				case IS_EMPTY:
					cardlayout.show(pnl_account, NOT_FOUND);
					break;
				case SUCCESS:
					buildTable(result.data);
					cardlayout.show(pnl_account, ACCOUNT_LIST);
					break;
				default:
					MainFrame.mf.setError("发生未知错误");
					cardlayout.show(pnl_account, NOT_FOUND);
					break;
				}
			}
		});
		pnl_search.add(btn_search);
		
		btn_showall = new JButton("显示所有");
		btn_showall.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				DataMessage<ArrayList<AccountVO>> result = accountbl.findAccount("","");
				switch(result.resultMessage){
				case IS_EMPTY:
					cardlayout.show(pnl_account, NOT_FOUND);
					break;
				case SUCCESS:
					buildTable(result.data);
					cardlayout.show(pnl_account, ACCOUNT_LIST);
					break;
				default:
					MainFrame.mf.setError("发生未知错误");
					cardlayout.show(pnl_account, NOT_FOUND);
					break;
				}
			}
		});
		pnl_search.add(btn_showall);
		
		pnl_account = new JPanel();
		add(pnl_account, BorderLayout.CENTER);
		cardlayout = new CardLayout();
		pnl_account.setLayout(cardlayout);
		
		JLabel lbl_NoUser = new JLabel("没有银行账户，请添加");
		pnl_account.add(lbl_NoUser, NO_ACCOUNT);
		lbl_NoUser.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lbl_NotFound = new JLabel("未找到匹配的账户");
		pnl_account.add(lbl_NotFound, NOT_FOUND);
		lbl_NotFound.setHorizontalAlignment(SwingConstants.CENTER);
		
		JScrollPane scrp_product = new JScrollPane();
		pnl_account.add(scrp_product, ACCOUNT_LIST);
		
		tbl_account = new JTable();
		tbl_account.getTableHeader().setReorderingAllowed(false);
		tbl_account.setRowHeight(30);
		tbl_account.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbl_account.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent me){
				if(me.getButton() == MouseEvent.BUTTON1 && me.getClickCount() == 2)
					btn_update.getActionListeners()[0].actionPerformed(null);
			}
		});
		scrp_product.setViewportView(tbl_account);
		
		JPanel pnl_func = new JPanel();
		add(pnl_func, BorderLayout.SOUTH);
		FlowLayout fl_pnl_userFunc = (FlowLayout) pnl_func.getLayout();
		fl_pnl_userFunc.setAlignment(FlowLayout.LEFT);
		
		btn_add = new JButton("添加账户");
		btn_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ResultDialog dia = new AccountDialog(MainFrame.mf);
				dia.pack();
				//dia.setResizable(false);
				GUIUtility.setCenter(dia);
				dia.setVisible(true);
				ResultMessage result = ResultDialog.getResultAndDispose(dia);
				if(result == ResultMessage.SUCCESS){
					MainFrame.mf.setMessage("账户添加成功！");
					buildTable();
				}
				else if(result != ResultMessage.CLOSE){
					MainFrame.mf.setError("账户添加失败");
					buildTable();
				}
			}
		});
		pnl_func.add(btn_add);
		
		btn_delete = new JButton("删除账户");
		btn_delete.setEnabled(false);
		btn_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AccountTableModel ptmodel = (AccountTableModel) tbl_account.getModel();
				int row = tbl_account.getSelectedRow();
				AccountVO account = ptmodel.getAccountVOAt(row);
				int choice = JOptionPane.showConfirmDialog(MainFrame.mf, "确认删除账户"+account.getName()+"？", "确认删除", JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
				if(choice != JOptionPane.OK_OPTION)
					return;
				ResultMessage result = accountbl.delAccount(account);
				switch(result){
				case SUCCESS:
					MainFrame.mf.setMessage( "账户删除成功！");
					buildTable();
					break;
				case CANNOT_DELETE:
					JOptionPane.showConfirmDialog(MainFrame.mf, "存在与该账户关联的交易信息，无法删除。", "删除账户", JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE);
					break;
				default:
					MainFrame.mf.setError( "账户删除失败");
					break;
				}
			}
		});
		pnl_func.add(btn_delete);
		
		btn_update = new JButton("查看/修改账户");
		btn_update.setEnabled(false);
		btn_update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AccountTableModel model = (AccountTableModel) tbl_account.getModel();
				int row = tbl_account.getSelectedRow();
				AccountVO account = model.getAccountVOAt(row);
				ResultDialog dia = new AccountDialog(MainFrame.mf,account);
				dia.pack();
				//dia.setResizable(false);
				GUIUtility.setCenter(dia);
				dia.setVisible(true);
				ResultMessage result = ResultDialog.getResultAndDispose(dia);
				if(result == ResultMessage.SUCCESS){
					MainFrame.mf.setMessage("账户修改成功！");
					buildTable();
				}
				else if(result != ResultMessage.CLOSE){
					MainFrame.mf.setError("账户修改失败");
					buildTable();
				}
			}
		});
		pnl_func.add(btn_update);
		

		tbl_account.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(tbl_account.getSelectedRow() == -1){
					btn_delete.setEnabled(false);
					btn_update.setEnabled(false);
				}
				else{
					//AccountTableModel model = (AccountTableModel) tbl_account.getModel();
					//int row = tbl_account.getSelectedRow();
					//AccountVO account = model.getAccountVOAt(row);
					btn_delete.setEnabled(true);
					btn_update.setEnabled(true);
					
				}
			}		
		});
		
		buildTable();
	}
	
	protected void buildTable() {
		DataMessage<ArrayList<AccountVO>> result = accountbl.getAll();
		switch(result.resultMessage){
		case REMOTE_FAIL:
			MainFrame.mf.setError( "通信失败！");
			break;
		case IS_EMPTY:
			cardlayout.show(pnl_account,NO_ACCOUNT);
			break;
		default:
			if(result.resultMessage == ResultMessage.SUCCESS){
				if(result.data == null || result.data.isEmpty())
					cardlayout.show(pnl_account,NO_ACCOUNT);
				else{
					buildTable(result.data);
				}
			}
			else
				MainFrame.mf.setError( "未知错误！");
			break;			
		}
	}

	private void buildTable(ArrayList<AccountVO> list) {
		btn_delete.setEnabled(false);
		Collections.sort(list);
		tbl_account.setModel(new AccountTableModel(list));
		TableColumn col = tbl_account.getColumnModel().getColumn(0);
		col.setMaxWidth(60);
		col.setPreferredWidth(60);
		col = tbl_account.getColumnModel().getColumn(1);
		col.setMaxWidth(200);
		col.setPreferredWidth(200);
		col = tbl_account.getColumnModel().getColumn(2);
		col.setMaxWidth(100);
		col.setPreferredWidth(100);
		cardlayout.show(pnl_account,ACCOUNT_LIST);
	}

	public boolean hasAccount(String accountid) {
		if(((AccountTableModel)tbl_account.getModel()).findAccount(accountid) != null)
			return true;
		else
			return false;
	}


}
