package gui.document.sales;

import enums.DataMessage;
import enums.ResultMessage;
import enums.Status;
import gui.GUIUtility;
import gui.ResultDialog;
import gui.inventory.InventoryLineItem;
import gui.inventory.InventoryPresentLineItem;
import gui.inventory.InventorySaleItem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JTextField;

import productBL.ProductItem;
import customerBL.CustomerBL;
import businesslogic.promotionbl.PromotionBL;
import businesslogic.userbl.UserBL;
import businesslogicservice.promotionblservice.PromotionBLService;
import saleBL.SaleBL;
import saleBL.SaleLineItem;
import VO.CustomerVO;
import VO.PresentVO;
import VO.PromotionVO;
import VO.SaleVO;

public class CheckDialog extends ResultDialog {

	private static final long serialVersionUID = -8336594546927537463L;
	private final JPanel contentPanel = new JPanel();
	private JButton btn_submit;
	private SaleLineItemPanel_Uneditable pnl_item;
	private JLabel lbl_promotion;
	private JLabel lbl_gift;
	private JTextField txf_yield;
	private int yield;
	private int limit;
	private JLabel lbl_actualDiscount;
	private double actualDiscount;
	private int actualOff;
	private JLabel lbl_couponGive;
	private JTextField txf_couponGet;
	private int couponGet;
	private JLabel lbl_actualPrice;
	private double actualPrice;
	protected SaleVO vo;
	protected PresentVO present;
	protected JLabel lbl_msg;

	/**
	 * Launch the application.
	 */
	
	private CheckDialog(JDialog parent) {
		super(parent,true);
		setTitle("销售结算");

		setResizable(false);
		setSize(671, 300);
		GUIUtility.setCenter(this);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] {80, 121, 91, 123, 111, 60};
		gbl_contentPanel.rowHeights = new int[]{140, 0, 0, 25, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			pnl_item = new SaleLineItemPanel_Uneditable(this);
			GridBagConstraints gbc_pnl_item = new GridBagConstraints();
			gbc_pnl_item.gridwidth = 6;
			gbc_pnl_item.insets = new Insets(0, 0, 5, 0);
			gbc_pnl_item.fill = GridBagConstraints.BOTH;
			gbc_pnl_item.gridx = 0;
			gbc_pnl_item.gridy = 0;
			contentPanel.add(pnl_item, gbc_pnl_item);
		}
		{
			lbl_promotion = new JLabel("使用了5条促销策略");
			GridBagConstraints gbc_lbl_promotion = new GridBagConstraints();
			gbc_lbl_promotion.gridwidth = 2;
			gbc_lbl_promotion.insets = new Insets(0, 0, 5, 5);
			gbc_lbl_promotion.gridx = 0;
			gbc_lbl_promotion.gridy = 1;
			contentPanel.add(lbl_promotion, gbc_lbl_promotion);
		}
		{
			lbl_gift = new JLabel("赠送商品23件");
			GridBagConstraints gbc_lbl_gift = new GridBagConstraints();
			gbc_lbl_gift.gridwidth = 2;
			gbc_lbl_gift.insets = new Insets(0, 0, 5, 5);
			gbc_lbl_gift.gridx = 2;
			gbc_lbl_gift.gridy = 1;
			contentPanel.add(lbl_gift, gbc_lbl_gift);
		}
		{
			lbl_couponGive = new JLabel("赠送代金券34元");
			GridBagConstraints gbc_lbl_couponGive = new GridBagConstraints();
			gbc_lbl_couponGive.gridwidth = 2;
			gbc_lbl_couponGive.insets = new Insets(0, 0, 5, 0);
			gbc_lbl_couponGive.gridx = 4;
			gbc_lbl_couponGive.gridy = 1;
			contentPanel.add(lbl_couponGive, gbc_lbl_couponGive);
		}
		{
			JLabel lbl_yield = new JLabel("折让：");
			GridBagConstraints gbc_lbl_yield = new GridBagConstraints();
			gbc_lbl_yield.anchor = GridBagConstraints.EAST;
			gbc_lbl_yield.insets = new Insets(0, 0, 5, 5);
			gbc_lbl_yield.gridx = 0;
			gbc_lbl_yield.gridy = 2;
			contentPanel.add(lbl_yield, gbc_lbl_yield);
		}
		{
			txf_yield = new JTextField();
			GridBagConstraints gbc_txf_yield = new GridBagConstraints();
			gbc_txf_yield.fill = GridBagConstraints.HORIZONTAL;
			gbc_txf_yield.insets = new Insets(0, 0, 5, 5);
			gbc_txf_yield.gridx = 1;
			gbc_txf_yield.gridy = 2;
			contentPanel.add(txf_yield, gbc_txf_yield);
			txf_yield.setColumns(10);
		}
		{
			lbl_actualDiscount = new JLabel("折扣：%");
			GridBagConstraints gbc_lbl_offPrice = new GridBagConstraints();
			gbc_lbl_offPrice.gridwidth = 2;
			gbc_lbl_offPrice.insets = new Insets(0, 0, 5, 5);
			gbc_lbl_offPrice.gridx = 2;
			gbc_lbl_offPrice.gridy = 2;
			contentPanel.add(lbl_actualDiscount, gbc_lbl_offPrice);
		}
		{
			JLabel lbl_couponGet = new JLabel("使用代金券：");
			GridBagConstraints gbc_lbl_couponGet = new GridBagConstraints();
			gbc_lbl_couponGet.anchor = GridBagConstraints.EAST;
			gbc_lbl_couponGet.insets = new Insets(0, 0, 5, 5);
			gbc_lbl_couponGet.gridx = 4;
			gbc_lbl_couponGet.gridy = 2;
			contentPanel.add(lbl_couponGet, gbc_lbl_couponGet);
		}
		{
			txf_couponGet = new JTextField();
			GridBagConstraints gbc_txf_couponGet = new GridBagConstraints();
			gbc_txf_couponGet.insets = new Insets(0, 0, 5, 0);
			gbc_txf_couponGet.fill = GridBagConstraints.HORIZONTAL;
			gbc_txf_couponGet.gridx = 5;
			gbc_txf_couponGet.gridy = 2;
			contentPanel.add(txf_couponGet, gbc_txf_couponGet);
			txf_couponGet.setColumns(10);
		}
		{
			lbl_actualPrice = new JLabel("折后总价：2319843元");
			GridBagConstraints gbc_lbl_actualPrice = new GridBagConstraints();
			gbc_lbl_actualPrice.gridwidth = 6;
			gbc_lbl_actualPrice.insets = new Insets(0, 0, 0, 5);
			gbc_lbl_actualPrice.gridx = 0;
			gbc_lbl_actualPrice.gridy = 3;
			contentPanel.add(lbl_actualPrice, gbc_lbl_actualPrice);
		}
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
			
			buttonPane.add(Box.createHorizontalStrut(5));
		}
	}
	
	protected void addButtons(JPanel buttonPane){
		{
			btn_submit = new JButton("提交");
			btn_submit.setActionCommand("submit");
			btn_submit.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(!checkset())
						return;
					
					calculate();
					
					vo.setStatus(Status.SUBMIT);
					
					ResultMessage submit = new SaleBL().addSale(vo, present);
					setResult(submit);
					switch(submit){
					case CANNOT_ADD:
						JOptionPane.showMessageDialog(CheckDialog.this, "今日创建单据已达上限！");
						setVisible(false);
						break;
					case REMOTE_FAIL:
						JOptionPane.showMessageDialog(CheckDialog.this, "通信失败！");
						break;
					case SUCCESS:
						setVisible(false);
						break;
					default:
						JOptionPane.showMessageDialog(CheckDialog.this, "未知错误！");
						break;
					}
				}					
			});
			buttonPane.add(btn_submit);
			getRootPane().setDefaultButton(btn_submit);
		}
		buttonPane.add(Box.createHorizontalStrut(10));
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

	protected boolean checkset() {
		String str = txf_yield.getText().trim();
		if(!str.matches("\\d+")){
			lbl_msg.setText("请输入合理的折让金额！");
			return false;
		}
		else if(Integer.parseInt(str) > limit){
			JOptionPane.showMessageDialog(CheckDialog.this, " 折让数额超出用户权限所允许，目前用户允许的最大数额为"+limit);
			return false;
		}
		else
			yield = Integer.parseInt(str);
		
		str = txf_couponGet.getText().trim();
		if(!str.matches("\\d+")){
			JOptionPane.showMessageDialog(CheckDialog.this, "请输入合理的优惠券金额");
			return false;
		}
		else
			couponGet = Integer.parseInt(str);
		
		return true;
	}

	public CheckDialog(JDialog parent,SaleVO sale,ArrayList<PromotionVO> promotion) {
		this(parent);
		PromotionBLService prombl = new PromotionBL();
		
		ArrayList<String> pros = new ArrayList<String>(promotion.size());
		for(PromotionVO pro:promotion)
			pros.add(pro.getID());
		sale.setPromotion(pros);
		DataMessage<SaleVO> result = prombl.getDiscount(sale);
		if(result.resultMessage != ResultMessage.SUCCESS){
			JOptionPane.showMessageDialog(this,"获取促销策略失败！");
		}
		
		this.vo = result.data;
		
		DataMessage<CustomerVO> customer = new CustomerBL().getCustomer(new CustomerVO(vo.getCustomerID()));
		if(customer.resultMessage != ResultMessage.SUCCESS){
			JOptionPane.showMessageDialog(this,"获取客户信息失败！");
			dispose();
		}
		
		DataMessage<PresentVO> presentResult = prombl.getPresent(customer.data.getViplvl(),vo.getTotal(),vo.getSalesman());
		if(presentResult.resultMessage == ResultMessage.ITEM_NOT_EXIST)
			present = new PresentVO("","",new ArrayList<ProductItem>(0),"",null);
		else if(presentResult.resultMessage != ResultMessage.SUCCESS){
			JOptionPane.showMessageDialog(this,"获取赠品失败！");
			dispose();
		}
		else
			present = presentResult.data;
		
		ArrayList<InventoryLineItem> items = new ArrayList<InventoryLineItem>();
		for(SaleLineItem item : vo.getProductList())
			items.add(new InventorySaleItem(item));
		for(ProductItem item:present.getProductList())
			items.add(new InventoryPresentLineItem(item));
		pnl_item.setList(items);
		
		lbl_promotion.setText("使用了"+promotion.size()+"条促销策略");
		lbl_gift.setText("赠送商品"+present.getProductList().size()+"种");
		
		lbl_couponGive.setText("赠送现金券："+GUIUtility.formatDouble(vo.getCreditGive()));
		
		couponGet = 0;
		yield = 0;
		txf_couponGet.setText("0");
		txf_couponGet.addFocusListener(new FocusListener(){
			private String temp;
			@Override
			public void focusGained(FocusEvent arg0) {
				txf_couponGet.selectAll();
				temp = txf_couponGet.getText();
			}
			@Override
			public void focusLost(FocusEvent e) {
				String str = txf_couponGet.getText().trim();
				if(!str.matches("\\d+")){
					lbl_msg.setText("请输入合理的优惠券金额！");
					txf_couponGet.setText(temp);
				}
				else{
					couponGet = Integer.parseInt(str);
					calculate();
				}
			}				
		});
		DataMessage<Integer> yieldLimit = prombl.getDiscountPermission(new UserBL().getCurrent().getPermission());
		if(yieldLimit.resultMessage != ResultMessage.SUCCESS){
			JOptionPane.showMessageDialog(this,"获取用户权限失败！");
			dispose();
		}
		limit = yieldLimit.data;
		txf_yield.setText("0");
		//txf_couponGet.addFocusListener(new YieldListener());
		calculate();
	}

	/*
	private class YieldListener implements FocusListener{
		private String temp;
		
		@Override
		public void focusGained(FocusEvent arg0) {
			txf_couponGet.selectAll();
			temp = txf_couponGet.getText();
		}
		@Override
		public void focusLost(FocusEvent e) {
			String str = txf_yield.getText().trim();
			if(!str.matches("\\d+")){
				JOptionPane.showMessageDialog(CheckDialog.this, "请输入合法的整数！");
				txf_yield.setText(temp);
			}
			else if(Integer.parseInt(str) > limit){
				JOptionPane.showMessageDialog(CheckDialog.this, "折让数额超出用户权限允许！");
				txf_yield.setText(temp);
			}
			else{
				yield = Integer.parseInt(str);
				calculate();
			}
		}				
	}
	*/
	
	
	protected void calculate(){
		actualDiscount = yield+couponGet+vo.getDiscount2();
		actualOff = (int) (100-100*actualDiscount/vo.getTotal());
		lbl_actualDiscount.setText("最终折让："+GUIUtility.formatDouble(actualDiscount)+"  最终折扣："+String.valueOf(actualOff)+"%");
		actualPrice = vo.getTotal()-actualDiscount;
		actualPrice = actualPrice<0?0:actualPrice;
		lbl_actualPrice.setText("实际总价："+GUIUtility.formatDouble(actualPrice));	
		
		vo.setCreditGet(couponGet);
		vo.setDiscount1(yield);
		vo.setPostTotal(actualPrice);
	}

}
