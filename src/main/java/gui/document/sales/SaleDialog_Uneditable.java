package gui.document.sales;

import enums.Status;
import gui.GUIUtility;
import gui.MainFrame;
import gui.inventory.InventoryLineItem;
import gui.inventory.InventorySaleItem;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import saleBL.SaleLineItem;
import VO.SaleVO;

public class SaleDialog_Uneditable extends JDialog {

	private static final long serialVersionUID = -7528743290618586824L;
	protected final JPanel contentPanel = new JPanel();
	private JLabel lbl_id;
	private JLabel lbl_operator;
	protected JTextArea txa_remarks;
	private JLabel lbl_clerk;
	private JLabel lbl_yield;
	private JLabel lbl_coupon;
	private JLabel lbl_postPrice;
	protected SaleLineItemPanel_Uneditable pnl_item;
	private JLabel lbl_promotion;
	private JLabel lbl_actualDiscount;
	private JLabel lbl_couponGive;
	private JLabel lbl_gift;
	private SaleVO vo;
	private JLabel lbl_customer;
	private JLabel lbl_stock;
	private JLabel lbl_remarks;
	
	/**
	 * Create the dialog.
	 */
	public SaleDialog_Uneditable(MainFrame parent,SaleVO vo) {
		super(parent,true);
		this.vo = vo;
		setTitle("查看销售单");

		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setSize(713, 347);
		GUIUtility.setCenter(this);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 15, 5, 15));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{15, 96, 160, 236, 394, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 19, 91, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		
		lbl_id = new JLabel("编号：");
		GridBagConstraints gbc_lbl_id = new GridBagConstraints();
		gbc_lbl_id.gridwidth = 2;
		gbc_lbl_id.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_id.gridx = 0;
		gbc_lbl_id.gridy = 0;
		contentPanel.add(lbl_id, gbc_lbl_id);
		
		lbl_customer = new JLabel(" 客户：");
		GridBagConstraints gbc_lbl_customer = new GridBagConstraints();
		gbc_lbl_customer.gridwidth = 2;
		gbc_lbl_customer.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_customer.gridx = 2;
		gbc_lbl_customer.gridy = 0;
		contentPanel.add(lbl_customer, gbc_lbl_customer);
		
		lbl_clerk = new JLabel("业务员：");
		GridBagConstraints gbc_label_clerk = new GridBagConstraints();
		gbc_label_clerk.insets = new Insets(0, 0, 5, 0);
		gbc_label_clerk.gridx = 4;
		gbc_label_clerk.gridy = 0;
		contentPanel.add(lbl_clerk, gbc_label_clerk);
		
		lbl_operator = new JLabel("操作员：");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 2;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		contentPanel.add(lbl_operator, gbc_lblNewLabel);
		
		lbl_stock = new JLabel("仓库：");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.gridwidth = 2;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 2;
		gbc_lblNewLabel_2.gridy = 1;
		contentPanel.add(lbl_stock, gbc_lblNewLabel_2);
		
		lbl_promotion = new JLabel("使用了3条促销策略");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 0);
		gbc_label.gridx = 4;
		gbc_label.gridy = 1;
		contentPanel.add(lbl_promotion, gbc_label);

		pnl_item = new SaleLineItemPanel_Uneditable(this);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 5;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 2;
		contentPanel.add(pnl_item, gbc_panel);
		
		lbl_yield = new JLabel("销售人员折让：");
		GridBagConstraints gbc_lbl_yield = new GridBagConstraints();
		gbc_lbl_yield.gridwidth = 2;
		gbc_lbl_yield.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_yield.gridx = 0;
		gbc_lbl_yield.gridy = 3;
		contentPanel.add(lbl_yield, gbc_lbl_yield);
		
		lbl_actualDiscount = new JLabel("实际折扣：");
		GridBagConstraints gbc_lbl_actualDiscount = new GridBagConstraints();
		gbc_lbl_actualDiscount.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_actualDiscount.gridx = 2;
		gbc_lbl_actualDiscount.gridy = 3;
		contentPanel.add(lbl_actualDiscount, gbc_lbl_actualDiscount);
		
		lbl_coupon = new JLabel("使用代金券：");
		GridBagConstraints gbc_lbl_coupon = new GridBagConstraints();
		gbc_lbl_coupon.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_coupon.gridx = 3;
		gbc_lbl_coupon.gridy = 3;
		contentPanel.add(lbl_coupon, gbc_lbl_coupon);
		
		lbl_postPrice = new JLabel("折后总价：元");
		GridBagConstraints gbc_lbl_postPrice = new GridBagConstraints();
		gbc_lbl_postPrice.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_postPrice.gridx = 4;
		gbc_lbl_postPrice.gridy = 3;
		contentPanel.add(lbl_postPrice, gbc_lbl_postPrice);
		
		lbl_couponGive = new JLabel("赠送代金券：");
		GridBagConstraints gbc_lbl_couponGive = new GridBagConstraints();
		gbc_lbl_couponGive.gridwidth = 2;
		gbc_lbl_couponGive.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_couponGive.gridx = 0;
		gbc_lbl_couponGive.gridy = 4;
		contentPanel.add(lbl_couponGive, gbc_lbl_couponGive);
		
		lbl_gift = new JLabel("赠送商品件");
		GridBagConstraints gbc_lbl_gift = new GridBagConstraints();
		gbc_lbl_gift.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_gift.gridx = 3;
		gbc_lbl_gift.gridy = 4;
		contentPanel.add(lbl_gift, gbc_lbl_gift);
		
		lbl_remarks = new JLabel("备注：");
		GridBagConstraints gbc_label1 = new GridBagConstraints();
		gbc_label1.anchor = GridBagConstraints.EAST;
		gbc_label1.insets = new Insets(0, 0, 0, 5);
		gbc_label1.gridx = 0;
		gbc_label1.gridy = 5;
		contentPanel.add(lbl_remarks, gbc_label1);
		
		txa_remarks = new JTextArea();
		txa_remarks.setLineWrap(true);
		txa_remarks.setWrapStyleWord(true);
		GridBagConstraints gbc_txa_remarks = new GridBagConstraints();
		gbc_txa_remarks.gridwidth = 4;
		gbc_txa_remarks.fill = GridBagConstraints.BOTH;
		gbc_txa_remarks.gridx = 1;
		gbc_txa_remarks.gridy = 5;
		contentPanel.add(txa_remarks, gbc_txa_remarks);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
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
		
		setProperty();
	}

	private void setProperty() {
		lbl_operator.setText("操作员："+vo.getOperator());
		txa_remarks.setText(vo.getRemarks());
		txa_remarks.setEditable(false);
		lbl_clerk.setText("业务员："+vo.getSalesman());		
		lbl_customer.setText("客户："+vo.getCustomer());
		lbl_stock.setText("仓库："+vo.getWarehouse());
				
		ArrayList<SaleLineItem> list = vo.getProductList();
		ArrayList<InventoryLineItem> items = new ArrayList<InventoryLineItem>();
		for(SaleLineItem item : list)
			items.add(new InventorySaleItem(item));
		pnl_item.setList(items);
		
		if(vo.getStatus() != Status.DRAFT){
			lbl_id.setText("编号："+vo.getID());
			lbl_yield.setText("销售人员折让"+vo.getDiscount1());
			lbl_coupon.setText("使用代金券："+vo.getCreditGet());
			lbl_postPrice.setText("折后总价："+vo.getPostTotal());
			lbl_promotion.setText("使用了"+String.valueOf(vo.getPromotion().size())+"条促销策略");
			lbl_actualDiscount.setText("最终折让："+vo.getDiscount2());
			lbl_couponGive.setText("赠送代金券"+vo.getCreditGive());
			lbl_gift.setText("赠送商品"+"件");
		}
		else{
			lbl_yield.setEnabled(false);
			lbl_coupon.setEnabled(false);
			lbl_postPrice.setEnabled(false);
			lbl_promotion.setEnabled(false);
			lbl_actualDiscount.setEnabled(false);
			lbl_couponGive.setEnabled(false);
			lbl_gift.setEnabled(false);
		}
	}

}
