package gui.document.sales;

import enums.DataMessage;
import enums.ResultMessage;
import enums.Status;
import gui.GUIUtility;
import gui.MainFrame;
import gui.ResultDialog;
import gui.document.component.CustomerComboBox;
import gui.inventory.InventoryLineItem;
import gui.inventory.InventorySaleItem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import businesslogic.promotionbl.PromotionBL;
import businesslogic.userbl.UserBL;

import java.util.ArrayList;

import saleBL.SaleBL;
import saleBL.SaleLineItem;
import VO.CustomerVO;
import VO.PromotionVO;
import VO.SaleVO;

public class SaleDialog extends ResultDialog {

	private static final long serialVersionUID = -4241320508731561606L;
	protected final JPanel contentPanel = new JPanel();
	private JLabel lbl_id;
	private JLabel lbl_operator;
	protected JTextField txf_stock;
	private JTextArea txa_remarks;
	private JButton btn_save;
	protected JButton btn_account;
	private JLabel lbl_clerk;
	protected JTextField txf_clerk;
	private SaleLineItemPanel_Editable pnl_item;
	private JButton btn_promotion;
	protected CustomerComboBox cbx_customer;
	protected SaleVO vo;
	protected JLabel lbl_msg;

	/**
	 * Create the dialog.
	 */
	public SaleDialog(MainFrame parent,SaleVO vo){
		this(parent);
		setTitle("修改销售单");
		
		this.vo = vo;
		if(vo.getStatus() != Status.DRAFT)
			lbl_id.setText("编号："+vo.getID());
		txf_stock.setText(vo.getWarehouse());
		txa_remarks.setText(vo.getRemarks());
		txf_clerk.setText(vo.getSalesman());
		cbx_customer.setSelected(vo.getCustomerID());
		
		ArrayList<SaleLineItem> list = vo.getProductList();
		ArrayList<InventoryLineItem> items = new ArrayList<InventoryLineItem>();
		for(SaleLineItem item : list)
			items.add(new InventorySaleItem(item));
		pnl_item.setList(items);
		 
	}
	
	public SaleDialog(MainFrame parent) {
		super(parent,true);
		setTitle("创建销售单");

		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setSize(713, 347);
		GUIUtility.setCenter(this);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 15, 5, 15));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{15, 83, 55, 57, 161, 137, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 19, 91, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		
		lbl_id = new JLabel("编号：无");
		GridBagConstraints gbc_lbl_id = new GridBagConstraints();
		gbc_lbl_id.anchor = GridBagConstraints.WEST;
		gbc_lbl_id.gridwidth = 3;
		gbc_lbl_id.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_id.gridx = 0;
		gbc_lbl_id.gridy = 0;
		contentPanel.add(lbl_id, gbc_lbl_id);
		
		JLabel lbl_customer = new JLabel(" 客户：");
		GridBagConstraints gbc_lbl_customer = new GridBagConstraints();
		gbc_lbl_customer.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_customer.anchor = GridBagConstraints.EAST;
		gbc_lbl_customer.gridx = 3;
		gbc_lbl_customer.gridy = 0;
		contentPanel.add(lbl_customer, gbc_lbl_customer);
		
		cbx_customer = CustomerComboBox.getInstance(this,"销售商");
		GridBagConstraints gbc_cbx_customer = new GridBagConstraints();
		gbc_cbx_customer.insets = new Insets(0, 0, 5, 5);
		gbc_cbx_customer.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbx_customer.gridx = 4;
		gbc_cbx_customer.gridy = 0;
		cbx_customer.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				txf_clerk.setText(cbx_customer.getSelectedCustomer().getSalesman());
			}
		});
		contentPanel.add(cbx_customer, gbc_cbx_customer);
		
		lbl_clerk = new JLabel("业务员：");
		GridBagConstraints gbc_label_clerk = new GridBagConstraints();
		gbc_label_clerk.anchor = GridBagConstraints.EAST;
		gbc_label_clerk.insets = new Insets(0, 0, 5, 5);
		gbc_label_clerk.gridx = 5;
		gbc_label_clerk.gridy = 0;
		contentPanel.add(lbl_clerk, gbc_label_clerk);
		
		txf_clerk = new JTextField();
		GridBagConstraints gbc_txf_clerk = new GridBagConstraints();
		gbc_txf_clerk.insets = new Insets(0, 0, 5, 0);
		gbc_txf_clerk.fill = GridBagConstraints.HORIZONTAL;
		gbc_txf_clerk.gridx = 6;
		gbc_txf_clerk.gridy = 0;
		contentPanel.add(txf_clerk, gbc_txf_clerk);
		txf_clerk.setColumns(10);
		
		JLabel lbl_stock = new JLabel("仓库：");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.gridx = 3;
		gbc_lblNewLabel_2.gridy = 1;
		contentPanel.add(lbl_stock, gbc_lblNewLabel_2);
		
		txf_stock = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.gridx = 4;
		gbc_textField_1.gridy = 1;
		contentPanel.add(txf_stock, gbc_textField_1);
		txf_stock.setColumns(30);
		
		btn_promotion = new JButton("查看促销策略");
		btn_promotion.addActionListener(new PromotionListener());
		GridBagConstraints gbc_btn_promotion = new GridBagConstraints();
		gbc_btn_promotion.insets = new Insets(0, 0, 5, 0);
		gbc_btn_promotion.gridx = 6;
		gbc_btn_promotion.gridy = 1;
		contentPanel.add(btn_promotion, gbc_btn_promotion);
		
		lbl_operator = new JLabel("操作员：");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.gridwidth = 3;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		contentPanel.add(lbl_operator, gbc_lblNewLabel);
		
		pnl_item = new SaleLineItemPanel_Editable(this);
		GridBagConstraints gbc_lineItemPanel = new GridBagConstraints();
		gbc_lineItemPanel.gridwidth = 7;
		gbc_lineItemPanel.insets = new Insets(0, 0, 5, 0);
		gbc_lineItemPanel.fill = GridBagConstraints.BOTH;
		gbc_lineItemPanel.gridx = 0;
		gbc_lineItemPanel.gridy = 2;
		contentPanel.add(pnl_item, gbc_lineItemPanel);
		
		JLabel lbl_remarks = new JLabel("备注：");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.EAST;
		gbc_label.insets = new Insets(0, 0, 0, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 3;
		contentPanel.add(lbl_remarks, gbc_label);
		
		txa_remarks = new JTextArea();
		txa_remarks.setLineWrap(true);
		txa_remarks.setWrapStyleWord(true);
		GridBagConstraints gbc_txa_remarks = new GridBagConstraints();
		gbc_txa_remarks.gridwidth = 6;
		gbc_txa_remarks.fill = GridBagConstraints.BOTH;
		gbc_txa_remarks.gridx = 1;
		gbc_txa_remarks.gridy = 3;
		contentPanel.add(txa_remarks, gbc_txa_remarks);
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
			
			addButtons(buttonPane);
			
			buttonPane.add(Box.createHorizontalStrut(5));
		}
		
		
		vo = new SaleVO();
		vo.setOperator(new UserBL().getCurrent().getName());
		lbl_operator.setText("操作员："+vo.getOperator());
		
		txf_clerk.setText(cbx_customer.getSelectedCustomer().getSalesman());
	}
	
	protected void addButtons(JPanel buttonPane){
		{
			btn_save = new JButton("保存");
			btn_save.setActionCommand("save");
			btn_save.addActionListener(new SaveListener());
			buttonPane.add(btn_save);
			getRootPane().setDefaultButton(btn_save);
		}
		buttonPane.add(Box.createHorizontalStrut(10));
		{
			btn_account = new JButton("结算");
			btn_account.setActionCommand("submit");
			btn_account.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					if(!checkset())
						return;
					
					ResultDialog dia = new CheckDialog(SaleDialog.this,vo,getPromotion());
					dia.setVisible(true);
					setResult(ResultDialog.getResultAndDispose(dia));
				}					
			});
			buttonPane.add(btn_account);
			getRootPane().setDefaultButton(btn_account);
		}
		buttonPane.add(Box.createHorizontalStrut(10));
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
	
	private class SaveListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!checkset())
				return;

			vo.setStatus(Status.DRAFT);
			ResultMessage result = new SaleBL().addSale(vo,null);
			setResult(result);
			switch(result){
			case CANNOT_ADD:
				JOptionPane.showMessageDialog(SaleDialog.this, "今日创建单据已达上限！");
				break;
			case REMOTE_FAIL:
				JOptionPane.showMessageDialog(SaleDialog.this, "通信错误！");
				break;
			case SUCCESS:
				setVisible(false);
				break;
			default:
				JOptionPane.showMessageDialog(SaleDialog.this, "未知错误！");
				break;
			
			}
		}
	}
	
	private class PromotionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(cbx_customer.getSelectedIndex() == -1){
				lbl_msg.setText("选择客户后才可查看其对应的促销策略");
				return;
			}
			DataMessage<ArrayList<PromotionVO>> result = new PromotionBL().getUnexpired(
					cbx_customer.getItemAt(cbx_customer.getSelectedIndex()).getViplvl(),Integer.MAX_VALUE);
			switch(result.resultMessage){
			case REMOTE_FAIL:
				JOptionPane.showMessageDialog(SaleDialog.this, "通信错误！");
				break;
			case ITEM_NOT_EXIST:
				lbl_msg.setText( "没有可用的促销策略！");
				break;
			case SUCCESS:
				new CheckPromotionDialog(SaleDialog.this,result.data).setVisible(true);
				break;
			default:
				lbl_msg.setText( "未知错误！");
				break;
			}
		}
	}
	
	protected boolean checkset(){
		String stock = txf_stock.getText().trim();
		int selectedcust = cbx_customer.getSelectedIndex();
		String clerk = txf_clerk.getText().trim();
		if(stock.equals("")){
			lbl_msg.setText("请填写仓库");
			return false;
		}
		else if(selectedcust == -1){
			lbl_msg.setText("请选择客户");
			return false;
		}
		else if(clerk.equals("")){
			lbl_msg.setText("请填写业务员");
			return false;
		}
		else if(pnl_item.isEmpty()){
			lbl_msg.setText("请添加商品");
			return false;
		}
		vo.setWarehouse(stock);
		CustomerVO cust = cbx_customer.getItemAt(selectedcust);
		vo.setCustomerID(cust.getID());
		vo.setCustomer(cust.getName());
		vo.setSalesman(clerk);
		vo.setRemarks(txa_remarks.getText());
		vo.setProductList(pnl_item.getItemList());
		vo.setPreTotal(pnl_item.getTotal());
	
		return true;
	}
	
	protected ArrayList<PromotionVO> getPromotion(){
		DataMessage<ArrayList<PromotionVO>> result = new PromotionBL().getUnexpired(
				cbx_customer.getItemAt(cbx_customer.getSelectedIndex()).getViplvl(),pnl_item.getTotal());
		switch(result.resultMessage){
		case REMOTE_FAIL:
			JOptionPane.showMessageDialog(SaleDialog.this, "通信错误！");
			break;
		case ITEM_NOT_EXIST:
			return new ArrayList<PromotionVO>();
		case SUCCESS:
			return result.data;
		default:
			JOptionPane.showMessageDialog(SaleDialog.this, "未知错误！");
			break;
		}
		return null;
	}

}
