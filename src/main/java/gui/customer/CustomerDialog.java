package gui.customer;

import enums.Operation;
import enums.ResultMessage;
import enums.UserPermission;
import enums.VipLevel;
import gui.DefaultTextField;
import gui.GUIUtility;
import gui.MainFrame;
import gui.ResultDialog;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;

import VO.CustomerVO;
import businesslogic.userbl.UserBL;
import customerBL.CustomerBL;
import customerBLService.CustomerBLService;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CustomerDialog extends ResultDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5914251930730773984L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txf_name;
	private JTextField txf_phone;
	private JTextField txf_address;
	private JTextField txf_zipcode;
	private JTextField txf_email;
	private JTextField txf_limit;
	private JTextField txf_salesman;
	private JComboBox<String> cbx_cate;
	private JComboBox<VipLevel> cbx_level;
	private static final String[] category = {"进货商","销售商"};
	private Operation opt;
	private CustomerBLService custbl = new CustomerBL();
	private CustomerVO customer;
	private JLabel lbl_msg;

	public CustomerDialog(MainFrame mf){
		super(mf,true);
		customer = new CustomerVO("");
		setTitle("添加客户");
		opt = Operation.ADD_USER;
		construct();
	}
	
	public CustomerDialog(MainFrame mf,CustomerVO cust){
		super(mf,true);
		this.customer = cust;
		setTitle("更改客户信息");
		opt = Operation.UPD_USER;
		construct();
		txf_name.setText(customer.getName());
		txf_name.setEditable(false);
		txf_name.setBorder(null);
		txf_name.setFocusable(false);
		txf_name.setBackground(new Color(contentPanel.getBackground().getRGB()));
		cbx_cate.setSelectedItem(customer.getType());
		txf_phone.setText(customer.getTele());
		cbx_level.setSelectedItem(customer.getViplvl());
		txf_address.setText(customer.getAddr());
		txf_zipcode.setText(customer.getZipCode());
		txf_salesman.setText(customer.getSalesman());
		txf_email.setText(customer.getEmail());
		txf_limit.setText(String.valueOf(customer.getPayableLine()));
		if(new UserBL().getCurrent().getPermission()!= UserPermission.SALES_MANAGER)
			txf_limit.setEditable(false);
	}
	
	private void construct() {
		this.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent we){
				close();
			}
		});
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(20, 20, 5, 10));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		contentPanel.setLayout(gbl_contentPanel);
		int x = 0, y = 0;
		{
			JLabel lbl_name = new JLabel("名称：");
			lbl_name.setHorizontalAlignment(SwingConstants.TRAILING);
			GridBagConstraints gbc_lbl_name = new GridBagConstraints();
			gbc_lbl_name.anchor = GridBagConstraints.ABOVE_BASELINE_TRAILING;
			gbc_lbl_name.insets = new Insets(0, 0, 5, 5);
			gbc_lbl_name.gridx = x++;
			gbc_lbl_name.gridy = y;
			contentPanel.add(lbl_name, gbc_lbl_name);
		}
		{
			txf_name = new JTextField();
			GridBagConstraints gbc_txf_name = new GridBagConstraints();
			gbc_txf_name.fill = GridBagConstraints.BOTH;
			gbc_txf_name.insets = new Insets(0, 0, 5, 5);
			gbc_txf_name.gridx = x++;
			gbc_txf_name.gridy = y;
			txf_name.addFocusListener(GUIUtility.getSelectFocusListener());
			txf_name.addActionListener(new ConfirmListener());
			contentPanel.add(txf_name, gbc_txf_name);
			txf_name.setColumns(10);
		}
		x = 0; ++y;
		{
			JLabel lbl_cate = new JLabel("分类：");
			lbl_cate.setHorizontalAlignment(SwingConstants.TRAILING);
			GridBagConstraints gbc_lbl_cate = new GridBagConstraints();
			gbc_lbl_cate.fill = GridBagConstraints.BOTH;
			gbc_lbl_cate.insets = new Insets(0, 0, 5, 5);
			gbc_lbl_cate.gridx = x++;
			gbc_lbl_cate.gridy = y;
			contentPanel.add(lbl_cate, gbc_lbl_cate);
		}
		{
			cbx_cate = new JComboBox<String>(category);
			GridBagConstraints gbc_cbx_cate = new GridBagConstraints();
			gbc_cbx_cate.fill = GridBagConstraints.BOTH;
			gbc_cbx_cate.insets = new Insets(0, 0, 5, 0);
			gbc_cbx_cate.gridx = x++;
			gbc_cbx_cate.gridy = y;
			contentPanel.add(cbx_cate, gbc_cbx_cate);
		}
		{
			JLabel lbl_level = new JLabel("级别：");
			lbl_level.setHorizontalAlignment(SwingConstants.TRAILING);
			GridBagConstraints gbc_lbl_level = new GridBagConstraints();
			gbc_lbl_level.fill = GridBagConstraints.BOTH;
			gbc_lbl_level.insets = new Insets(0, 0, 5, 5);
			gbc_lbl_level.gridx = x++;
			gbc_lbl_level.gridy = y;
			contentPanel.add(lbl_level, gbc_lbl_level);
		}
		{
			cbx_level = new JComboBox<VipLevel>(VipLevel.getEnumList());
			GridBagConstraints gbc_cbx_level = new GridBagConstraints();
			gbc_cbx_level.fill = GridBagConstraints.BOTH;
			gbc_cbx_level.insets = new Insets(0, 0, 5, 0);
			gbc_cbx_level.gridx = x++;
			gbc_cbx_level.gridy = y;
			contentPanel.add(cbx_level, gbc_cbx_level);
		}
		x = 0; ++y;
		{
			JLabel lbl_phone = new JLabel("电话：");
			lbl_phone.setHorizontalAlignment(SwingConstants.TRAILING);
			GridBagConstraints gbc_lbl_phone = new GridBagConstraints();
			gbc_lbl_phone.fill = GridBagConstraints.BOTH;
			gbc_lbl_phone.insets = new Insets(0, 0, 5, 5);
			gbc_lbl_phone.gridx = x++;
			gbc_lbl_phone.gridy = y;
			contentPanel.add(lbl_phone, gbc_lbl_phone);
		}
		{
			txf_phone = new JTextField();
			GridBagConstraints gbc_txf_phone = new GridBagConstraints();
			gbc_txf_phone.fill = GridBagConstraints.BOTH;
			gbc_txf_phone.insets = new Insets(0, 0, 5, 5);
			gbc_txf_phone.gridx = x++;
			gbc_txf_phone.gridy = y;
			txf_phone.addFocusListener(GUIUtility.getSelectFocusListener());
			txf_phone.addActionListener(new ConfirmListener());
			contentPanel.add(txf_phone, gbc_txf_phone);
			txf_phone.setColumns(10);
		}
		x = 0; ++y;
		{
			JLabel lbl_email = new JLabel("电子邮箱：");
			lbl_email.setHorizontalAlignment(SwingConstants.TRAILING);
			GridBagConstraints gbc_lbl_email = new GridBagConstraints();
			gbc_lbl_email.fill = GridBagConstraints.BOTH;
			gbc_lbl_email.insets = new Insets(0, 0, 0, 5);
			gbc_lbl_email.gridx = x++;
			gbc_lbl_email.gridy = y;
			contentPanel.add(lbl_email, gbc_lbl_email);
		}
		{
			txf_email = new JTextField();
			GridBagConstraints gbc_txf_email = new GridBagConstraints();
			gbc_txf_email.fill = GridBagConstraints.BOTH;
			gbc_txf_email.insets = new Insets(0, 0, 5, 5);
			gbc_txf_email.gridwidth = 2;
			gbc_txf_email.gridx = x++;
			gbc_txf_email.gridy = y;
			txf_email.addFocusListener(GUIUtility.getSelectFocusListener());
			txf_email.addActionListener(new ConfirmListener());
			contentPanel.add(txf_email, gbc_txf_email);
			txf_email.setColumns(10);
		}
		x = 0; ++y;
		{
			JLabel lbl_address = new JLabel("地址：");
			lbl_address.setHorizontalAlignment(SwingConstants.TRAILING);
			GridBagConstraints gbc_lbl_address = new GridBagConstraints();
			gbc_lbl_address.fill = GridBagConstraints.BOTH;
			gbc_lbl_address.insets = new Insets(0, 0, 5, 5);
			gbc_lbl_address.gridx = x++;
			gbc_lbl_address.gridy = y;
			contentPanel.add(lbl_address, gbc_lbl_address);
		}
		{
			txf_address = new JTextField();
			GridBagConstraints gbc_txf_address = new GridBagConstraints();
			gbc_txf_address.gridwidth = 3;
			gbc_txf_address.fill = GridBagConstraints.BOTH;
			gbc_txf_address.insets = new Insets(0, 0, 5, 0);
			gbc_txf_address.gridx = x++;
			gbc_txf_address.gridy = y;
			txf_address.addFocusListener(GUIUtility.getSelectFocusListener());
			txf_address.addActionListener(new ConfirmListener());
			contentPanel.add(txf_address, gbc_txf_address);
			txf_address.setColumns(10);
		}
		x = 0; ++y;
		{
			JLabel lbl_zipcode = new JLabel("邮编：");
			lbl_zipcode.setHorizontalAlignment(SwingConstants.TRAILING);
			GridBagConstraints gbc_lbl_zipcode = new GridBagConstraints();
			gbc_lbl_zipcode.fill = GridBagConstraints.BOTH;
			gbc_lbl_zipcode.insets = new Insets(0, 0, 5, 5);
			gbc_lbl_zipcode.gridx = x++;
			gbc_lbl_zipcode.gridy = y;
			contentPanel.add(lbl_zipcode, gbc_lbl_zipcode);
		}
		{
			txf_zipcode = new JTextField();
			GridBagConstraints gbc_txf_zipcode = new GridBagConstraints();
			gbc_txf_zipcode.fill = GridBagConstraints.BOTH;
			gbc_txf_zipcode.insets = new Insets(0, 0, 5, 5);
			gbc_txf_zipcode.gridx = x++;
			gbc_txf_zipcode.gridy = y;
			txf_zipcode.addFocusListener(GUIUtility.getSelectFocusListener());
			txf_zipcode.addActionListener(new ConfirmListener());
			contentPanel.add(txf_zipcode, gbc_txf_zipcode);
			txf_zipcode.setColumns(10);
		}
		x = 0; ++y;
		{
			JLabel lbl_limit = new JLabel("应收额度：");
			lbl_limit.setHorizontalAlignment(SwingConstants.TRAILING);
			GridBagConstraints gbc_lbl_limit = new GridBagConstraints();
			gbc_lbl_limit.fill = GridBagConstraints.BOTH;
			gbc_lbl_limit.insets = new Insets(0, 0, 5, 5);
			gbc_lbl_limit.gridx = x++;
			gbc_lbl_limit.gridy = y;
			contentPanel.add(lbl_limit, gbc_lbl_limit);
		}
		{
			txf_limit = new DefaultTextField("0");
			GridBagConstraints gbc_txf_limit = new GridBagConstraints();
			gbc_txf_limit.fill = GridBagConstraints.BOTH;
			gbc_txf_limit.insets = new Insets(0, 0, 5, 5);
			gbc_txf_limit.gridx = x++;
			gbc_txf_limit.gridy = y;
			txf_limit.addActionListener(new ConfirmListener());
			contentPanel.add(txf_limit, gbc_txf_limit);
			txf_limit.setColumns(10);
		}
		{
			JLabel lbl_salesman = new JLabel("默认业务员：");
			lbl_salesman.setHorizontalAlignment(SwingConstants.TRAILING);
			GridBagConstraints gbc_lbl_salesman = new GridBagConstraints();
			gbc_lbl_salesman.fill = GridBagConstraints.BOTH;
			gbc_lbl_salesman.insets = new Insets(0, 0, 5, 5);
			gbc_lbl_salesman.gridx = x++;
			gbc_lbl_salesman.gridy = y;
			contentPanel.add(lbl_salesman, gbc_lbl_salesman);
		}
		{
			txf_salesman = new JTextField();
			GridBagConstraints gbc_txf_salesman = new GridBagConstraints();
			gbc_txf_salesman.insets = new Insets(0, 0, 5, 5);
			gbc_txf_salesman.fill = GridBagConstraints.BOTH;
			gbc_txf_salesman.gridx = x++;
			gbc_txf_salesman.gridy = y;
			txf_salesman.addFocusListener(GUIUtility.getSelectFocusListener());
			txf_salesman.addActionListener(new ConfirmListener());
			contentPanel.add(txf_salesman, gbc_txf_salesman);
			txf_salesman.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new BoxLayout(buttonPane,BoxLayout.X_AXIS));
			buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

			buttonPane.add(Box.createHorizontalGlue());
			lbl_msg = new JLabel();
			lbl_msg.setForeground(Color.RED);
			buttonPane.add(lbl_msg);

			buttonPane.add(Box.createHorizontalStrut(10));
			{
				JButton btn_confirm = new JButton("确定(Enter)");
				btn_confirm.setActionCommand("OK");
				btn_confirm.addActionListener(new ConfirmListener());
				buttonPane.add(btn_confirm);
				getRootPane().setDefaultButton(btn_confirm);
			}
			buttonPane.add(Box.createHorizontalStrut(10));
			{
				JButton btn_cancel = new JButton("取消");
				btn_cancel.setActionCommand("Cancel");
				btn_cancel.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg0) {
						close();
					}
				});
				buttonPane.add(btn_cancel);
			}
			buttonPane.add(Box.createHorizontalStrut(5));
		}
	}
	
	private class ConfirmListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			String name = txf_name.getText().trim();
			int cate = cbx_cate.getSelectedIndex();
			String phone = txf_phone.getText().trim();
			int level = cbx_level.getSelectedIndex();
			String addr = txf_address.getText().trim();
			String zipc = txf_zipcode.getText().trim();
			String salesman = txf_salesman.getText().trim();
			String email = txf_email.getText().trim();
			String limit = txf_limit.getText().trim();
			if(name.equals("")){
				lbl_msg.setText( "名称不能为空！");
				txf_name.requestFocus();
			}
			else if(cate == -1){
				lbl_msg.setText( "请选择客户分类！");
			}
			else if(level == -1){
				lbl_msg.setText( "请选择客户级别！");
			}
			else if(limit.equals("")){
				lbl_msg.setText( "应收额度不能为空！");
				txf_limit.requestFocus();
			}
			else if(!limit.matches("\\d*\\.?\\d+")){
				lbl_msg.setText( "应收额度须为合理金额！");
				txf_limit.requestFocus();
			}
			else{
				customer.setName(name);
				customer.setType(cbx_cate.getItemAt(cate));
				customer.setTele(phone);
				customer.setViplvl(cbx_level.getItemAt(level));
				customer.setAddr(addr);
				customer.setZipCode(zipc);
				customer.setSalesman(salesman);
				customer.setEmail(email);
				customer.setPayableLine(Double.parseDouble(limit));
				ResultMessage result = null;
				if(opt == Operation.ADD_USER){
					result = custbl.addCustomer(customer);
				}
				else if(opt == Operation.UPD_USER){
					result = custbl.updateCustomer(customer);
				}
				setResult(result);
				switch(result){
				case REMOTE_FAIL:
					lbl_msg.setText( "通信错误！");
					break;
				case SUCCESS:
					setVisible(false);
					break;
				case ITEM_EXIST:
					JOptionPane.showConfirmDialog(CustomerDialog.this, "可能存在重复的客户！");
					setVisible(false);
					break;
				default:
					lbl_msg.setText( "未知错误！");
					break;
				}			
			}
		}
	}
}
