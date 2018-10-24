package gui.document.sales;

import enums.DataMessage;
import enums.ResultMessage;
import enums.Status;
import gui.GUIUtility;
import gui.MainFrame;
import gui.ResultDialog;
import gui.inventory.InventoryLineItem;
import gui.inventory.InventoryPresentLineItem;
import gui.inventory.InventorySaleItem;
import inventoryBL.InventoryBL;

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

import java.util.ArrayList;

import productBL.ProductItem;
import saleBL.ReturnBL;
import saleBL.SaleBL;
import saleBL.SaleLineItem;
import VO.PresentVO;
import VO.ReturnVO;
import VO.SaleVO;
import businesslogic.userbl.UserBL;

public class ReturnDialog extends ResultDialog {

	private static final long serialVersionUID = -5430848517343841927L;
	protected final JPanel contentPanel = new JPanel();
	private JLabel lbl_id;
	private JLabel lbl_operator;
	protected JTextField txf_stock;
	protected JTextArea txa_remarks;
	protected JButton btn_save;
	protected JButton btn_submit;
	private JLabel label_clerk;
	protected JTextField txf_clerk;
	protected ReturnLineItemPanel pnl_item;
	private JLabel lbl_customer;
	protected ReturnVO vo;
	protected JLabel lbl_msg;

	/**
	 * Create the dialog.
	 */
	public ReturnDialog(MainFrame parent,SaleVO vo){
		super(parent,true);
		setTitle("创建销售退货单");
		
		this.vo = new ReturnVO(vo);
		this.vo.setOperator(new UserBL().getCurrent().getName());
		int giftsL = 0;
		ArrayList<ProductItem> presentlist = new ArrayList<ProductItem>();
		if(vo.getPresent()!=null){
			DataMessage<PresentVO> result = new InventoryBL().getPresentTable(new PresentVO(vo.getPresent()));
			if(result.resultMessage != ResultMessage.SUCCESS){
				lbl_msg.setText("未知错误，无法获取对应的赠品单！");
				dispose();
			}
			presentlist = result.data.getProductList();
			giftsL = presentlist.size();
		}

		int listL = vo.getProductList().size();

		ArrayList<InventoryLineItem> list = new ArrayList<InventoryLineItem>(listL);
		ArrayList<InventoryLineItem> gifts = new ArrayList<InventoryLineItem>(giftsL);
		int[] limit = new int[listL+giftsL];
		for(int i = 0;i < listL;i++){
			SaleLineItem item = vo.getProductList().get(i);
			list.add(new InventorySaleItem(item));
			limit[i] = item.getNumber();
		}
		for(int i = 0;i < giftsL;i++){
			ProductItem item = presentlist.get(i);
			gifts.add(new InventoryPresentLineItem(item));
			limit[i+listL] = item.getNumber();
			
			this.vo.getProductList().add(
					new SaleLineItem(item.getProductID(),item.getProductName(),item.getProductModel(),item.getNumber(),0,0,""));
		}	
		
		construct(list,gifts,limit);
		setProperty();
	}

	public ReturnDialog(MainFrame parent,ReturnVO vo){
		super(parent,true);
		this.vo = vo;
		setTitle("修改销售退货单");
		
		DataMessage<SaleVO> saleResult = new SaleBL().getSale(new SaleVO(vo.getSaleid()));
		if(saleResult.resultMessage != ResultMessage.SUCCESS){
			lbl_msg.setText("未知错误，无法获取对应的销售单！");
			dispose();
		}		
		SaleVO sale = saleResult.data;
		
		int giftsL = 0;
		ArrayList<ProductItem> presentlist = new ArrayList<ProductItem>();
		if(sale.getPresent()!=null){
			DataMessage<PresentVO> result = new InventoryBL().getPresentTable(new PresentVO(sale.getPresent()));
			if(result.resultMessage != ResultMessage.SUCCESS){
				lbl_msg.setText("未知错误，无法获取对应的赠品单！");
				dispose();
			}
			giftsL = result.data.getProductList().size();
			presentlist = result.data.getProductList();
		}
		int listL = sale.getProductList().size();
		
		int[] limit = new int[listL+giftsL];
		for(int i = 0;i < listL;i++){
			SaleLineItem item = vo.getProductList().get(i);
			limit[i] = item.getNumber();
		}
		for(int i = 0;i < giftsL;i++){
			ProductItem item = presentlist.get(i);
			limit[i+listL] = item.getNumber();
		}
		
		ArrayList<InventoryLineItem> list = new ArrayList<InventoryLineItem>(vo.getProductList().size());
		for(SaleLineItem item:vo.getProductList())
			list.add(new InventorySaleItem(item));
		
		construct(list,new ArrayList<InventoryLineItem>(0),limit);
		setProperty();
	}
	
	private void construct(ArrayList<InventoryLineItem> list,ArrayList<InventoryLineItem> gifts,int[] limit) {
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setSize(713,300);
		GUIUtility.setCenter(this);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 15, 5, 15));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{15, 83, 55, 57, 161, 137, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 19, 91, 0, 0, 0};
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
		
		lbl_customer = new JLabel(" 客户：");
		GridBagConstraints gbc_lbl_customer = new GridBagConstraints();
		gbc_lbl_customer.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_customer.anchor = GridBagConstraints.EAST;
		gbc_lbl_customer.gridx = 3;
		gbc_lbl_customer.gridy = 0;
		contentPanel.add(lbl_customer, gbc_lbl_customer);
		
		label_clerk = new JLabel("业务员：");
		GridBagConstraints gbc_label_clerk = new GridBagConstraints();
		gbc_label_clerk.anchor = GridBagConstraints.EAST;
		gbc_label_clerk.insets = new Insets(0, 0, 5, 5);
		gbc_label_clerk.gridx = 5;
		gbc_label_clerk.gridy = 0;
		contentPanel.add(label_clerk, gbc_label_clerk);
		
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
		gbc_lblNewLabel_2.gridx = 5;
		gbc_lblNewLabel_2.gridy = 1;
		contentPanel.add(lbl_stock, gbc_lblNewLabel_2);
		
		txf_stock = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.gridx = 6;
		gbc_textField_1.gridy = 1;
		contentPanel.add(txf_stock, gbc_textField_1);
		txf_stock.setColumns(30);
		
		lbl_operator = new JLabel("操作员：");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.gridwidth = 3;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		contentPanel.add(lbl_operator, gbc_lblNewLabel);
		
		pnl_item = getItemPanel(this,list,gifts,limit);
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
		gbc_txa_remarks.anchor = GridBagConstraints.SOUTH;
		gbc_txa_remarks.fill = GridBagConstraints.BOTH;
		gbc_txa_remarks.gridx = 1;
		gbc_txa_remarks.gridy = 3;
		contentPanel.add(txa_remarks, gbc_txa_remarks);
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new BoxLayout(buttonPane,BoxLayout.X_AXIS));
			buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
			
			buttonPane.add(Box.createHorizontalStrut(10));
			lbl_msg = new JLabel();
			lbl_msg.setForeground(Color.RED);
			buttonPane.add(lbl_msg);
			
			buttonPane.add(Box.createHorizontalGlue());
			addButtons(buttonPane);
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
			buttonPane.add(Box.createHorizontalStrut(5));
		}
	}
	
	protected void addButtons(JPanel buttonPane) {
		{
			btn_save = new JButton("保存");
			btn_save.setActionCommand("save");
			btn_save.addActionListener(new ConfirmListener());
			buttonPane.add(btn_save);
		}
		{
			btn_submit = new JButton("提交");
			btn_submit.setActionCommand("submit");
			btn_submit.addActionListener(new ConfirmListener());
			buttonPane.add(btn_submit);
		}
	}
	
	protected boolean checkset(){
		String stock = txf_stock.getText().trim();
		String clerk = txf_clerk.getText().trim();
		if(stock.isEmpty()){
			JOptionPane.showMessageDialog(this,"");
			return false;
		}
		else if(clerk.isEmpty()){
			JOptionPane.showMessageDialog(this,"");
			return false;
		}
		vo.setWarehouse(stock);
		vo.setSalesman(clerk);
		vo.setProductList(pnl_item.getItemList());
		vo.setTotal(pnl_item.getTotal());
		return true;
	}
	
	private class ConfirmListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!checkset())
				return;
			vo.setOperator(new UserBL().getCurrent().getName());
			ResultMessage result = null;
			switch(e.getActionCommand()){
			case "save":
				vo.setStatus(Status.DRAFT);
				if(vo.getID().isEmpty())
					result = new ReturnBL().addReturn(vo);
				else
					result = new ReturnBL().updateReturn(vo);
				break;
			case "submit":
				vo.setStatus(Status.SUBMIT);
				result = new ReturnBL().addReturn(vo);
				break;
			}
			setResult(result);
			switch(result){
			case CANNOT_ADD:
				JOptionPane.showMessageDialog(ReturnDialog.this, "今日创建单据已达上限！");
				break;
			case REMOTE_FAIL:
				JOptionPane.showMessageDialog(ReturnDialog.this, "通信错误！");
				break;
			case SUCCESS:
				setVisible(false);
				break;
			default:
				JOptionPane.showMessageDialog(ReturnDialog.this, "未知错误！");
				break;
			
			}
		}
	}

	private void setProperty() {
		if(vo.getStatus() != Status.DRAFT){
			lbl_id.setText(""+vo.getID());
		}
		lbl_operator.setText("操作员："+vo.getOperator());
		lbl_customer.setText("客户："+vo.getCustomer());
		txf_stock.setText(vo.getWarehouse());
		txa_remarks.setText(vo.getRemarks());
		txf_clerk.setText(vo.getSalesman());
		pnl_item.setTotal(vo.getTotal());
	}
	
	protected ReturnLineItemPanel getItemPanel(ReturnDialog returnDialog,
			ArrayList<InventoryLineItem> list,
			ArrayList<InventoryLineItem> gifts, int[] limit) {
		return new ReturnLineItemPanel(this,list,gifts,limit);
	}

}
