package gui.customer;

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

import VO.CustomerVO;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import customerBL.CustomerBL;
import customerBLService.CustomerBLService;
import enums.DataMessage;
import enums.ResultMessage;
import gui.ClearHintTextField;
import gui.GUIUtility;
import gui.LeftAlignTableRenderer;
import gui.MainFrame;
import gui.ResultDialog;

import javax.swing.JTextField;

public class CustomerPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7286749578439976602L;
	private static final String NO_CUSTOMER = "no customer";
	private static final String CUSTOMER_LIST = "customer list";
	
	private JTable tbl_customer;
	private JButton btn_addCustomer,btn_delCustomer,btn_updCustomer;
	private CardLayout cardlayout;
	private JPanel pnl_customer;
	
	private CustomerBLService customerbl = new CustomerBL();
	private JPanel pnl_search;
	private JLabel lbl_name;
	private JTextField txf_name;
	private JLabel lbl_id;
	private JTextField txf_id;
	private JButton btn_search;
	private JButton btn_showall;

	/**
	 * Create the panel.
	 */
	public CustomerPanel() {
		setPreferredSize(new Dimension(800, 640));
		setLayout(new BorderLayout(0, 0));
		
		pnl_search = new JPanel();
		FlowLayout fl_pnl_search = (FlowLayout) pnl_search.getLayout();
		fl_pnl_search.setAlignment(FlowLayout.RIGHT);
		add(pnl_search, BorderLayout.NORTH);
		
		lbl_id = new JLabel("编号：");
		lbl_id.setHorizontalAlignment(SwingConstants.TRAILING);
		pnl_search.add(lbl_id);
		
		txf_id = new ClearHintTextField("默认为任意编号");
		txf_id.setColumns(10);
		pnl_search.add(txf_id);
		
		lbl_name = new JLabel("名称：");
		lbl_name.setHorizontalAlignment(SwingConstants.TRAILING);
		pnl_search.add(lbl_name);
		
		txf_name = new ClearHintTextField("默认为任意名称");
		txf_name.setColumns(10);
		pnl_search.add(txf_name);
		
		btn_search = new JButton("搜索客户");
		pnl_search.add(btn_search);
		//TODO
		btn_showall = new JButton("显示所有客户");
		pnl_search.add(btn_showall);
		
		pnl_customer = new JPanel();
		add(pnl_customer, BorderLayout.CENTER);
		cardlayout = new CardLayout();
		pnl_customer.setLayout(cardlayout);
		
		JLabel lbl_NoCustomer = new JLabel("客户列表没有客户，请添加。");
		pnl_customer.add(lbl_NoCustomer, NO_CUSTOMER);
		lbl_NoCustomer.setHorizontalAlignment(SwingConstants.CENTER);
		
		JScrollPane scrp_product = new JScrollPane();
		pnl_customer.add(scrp_product, CUSTOMER_LIST);
		
		tbl_customer = new JTable();
		tbl_customer.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbl_customer.getTableHeader().setReorderingAllowed(false);
		tbl_customer.setRowHeight(30);
		tbl_customer.setDefaultRenderer(Double.class, new LeftAlignTableRenderer());
		tbl_customer.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent me){
				if(me.getButton() == MouseEvent.BUTTON1 && me.getClickCount() == 2)
					btn_updCustomer.getActionListeners()[0].actionPerformed(null);
			}
		});
		scrp_product.setViewportView(tbl_customer);
		
		JPanel pnl_func = new JPanel();
		add(pnl_func, BorderLayout.SOUTH);
		FlowLayout fl_pnl_func = (FlowLayout) pnl_func.getLayout();
		fl_pnl_func.setAlignment(FlowLayout.LEFT);
		
		btn_addCustomer = new JButton("添加客户");
		btn_addCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ResultDialog dia = new CustomerDialog(MainFrame.mf);
				dia.pack();
				dia.setResizable(false);
				GUIUtility.setCenter(dia);
				dia.setVisible(true);
				ResultMessage result = ResultDialog.getResultAndDispose(dia);
				if(result == ResultMessage.SUCCESS){
					MainFrame.mf.setMessage("客户添加成功！");
					buildTable();
				}
				else if(result != ResultMessage.CLOSE){
					buildTable();
					MainFrame.mf.setError("客户添加失败");
				}
			}
		});
		pnl_func.add(btn_addCustomer);
		
		btn_delCustomer = new JButton("删除客户");
		btn_delCustomer.setEnabled(false);
		btn_delCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CustomerTableModel model = (CustomerTableModel) tbl_customer.getModel();
				int row = tbl_customer.getSelectedRow();
				CustomerVO cust = model.getCustomerVOAt(row);
				if(cust.getPayable() != 0 || cust.getReceivable() != 0){
					JOptionPane.showMessageDialog(MainFrame.mf, "客户存在未完成的记录！");
					return;
				}
				int choice = JOptionPane.showConfirmDialog(MainFrame.mf, "确认删除客户"+cust.getName()+"？", "确认删除", JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
				if(choice != JOptionPane.OK_OPTION)
					return;
				ResultMessage result = customerbl.deleteCustomer(cust);
				switch(result){
				case SUCCESS:
					MainFrame.mf.setMessage("客户删除成功！");
					buildTable();
					break;
				default:
					MainFrame.mf.setError("客户删除失败");
					buildTable();
					break;
				}
			}
		});
		pnl_func.add(btn_delCustomer);
		
		btn_updCustomer = new JButton("查看/修改客户");
		btn_updCustomer.setEnabled(false);
		btn_updCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CustomerTableModel model = (CustomerTableModel) tbl_customer.getModel();
				CustomerVO cus = model.getCustomerVOAt(tbl_customer.getSelectedRow());
				ResultDialog dia = new CustomerDialog(MainFrame.mf,cus);
				dia.pack();
				dia.setResizable(false);
				GUIUtility.setCenter(dia);
				dia.setVisible(true);
				ResultMessage result = ResultDialog.getResultAndDispose(dia);
				if(result == ResultMessage.SUCCESS){
					MainFrame.mf.setMessage("客户信息修改成功！");
					buildTable();
				}
				else if(result != ResultMessage.CLOSE){
					MainFrame.mf.setError("客户信息修改失败");
					buildTable();
				}
			}
		});
		pnl_func.add(btn_updCustomer);

		tbl_customer.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(tbl_customer.getSelectedRow() == -1){
					btn_delCustomer.setEnabled(false);
					btn_updCustomer.setEnabled(false);
				}
				else{
					btn_delCustomer.setEnabled(true);
					btn_updCustomer.setEnabled(true);					
				}
			}		
		});
		
		buildTable();
	}
	
	protected void buildTable() {
		DataMessage<ArrayList<CustomerVO>> result = customerbl.getAllCustomer();
		switch(result.resultMessage){
		case REMOTE_FAIL:
			JOptionPane.showMessageDialog(MainFrame.mf, "通信失败！");
			break;
		case ITEM_NOT_EXIST:
		case IS_EMPTY:
			cardlayout.show(pnl_customer,NO_CUSTOMER);
			break;
		default:
			if(result.resultMessage == ResultMessage.SUCCESS){
				if(result.data == null || result.data.isEmpty())
					cardlayout.show(pnl_customer,NO_CUSTOMER);
				else{
					buildTable(result.data);
				}
			}
			else
				JOptionPane.showMessageDialog(MainFrame.mf, "未知错误！");
			break;			
		}
	}

	private void buildTable(ArrayList<CustomerVO> cuses) {
		Collections.sort(cuses);
		tbl_customer.setModel(new CustomerTableModel(cuses));
		cardlayout.show(pnl_customer,CUSTOMER_LIST);
	}


}
