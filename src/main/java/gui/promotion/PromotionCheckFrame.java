package gui.promotion;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import VO.PresentVO;
import VO.PromotionVO;
import businesslogic.promotionbl.PromotionBL;
import businesslogicservice.promotionblservice.PromotionBLService;
import enums.ResultMessage;
import enums.VipLevel;
import gui.GUIUtility;
import gui.datecombobox.ChangeFutureDatePanel;

public class PromotionCheckFrame extends JFrame implements AddPresentInterface {

	private static final long serialVersionUID = 135990459789952684L;
	private PromotionPanel promotionPanel;
	private PromotionVO promotion;
	private PresentVO present;
	private PromotionBLService promotionService;
	
	private JTextField textField_discount;
	private JTextField textField_percent;
	private JTextField textField_coupon;
	private JTextField textField_total;
	private ChangeFutureDatePanel chooseDatePanel;
	private JComboBox<VipLevel> comboBox_viplvl;
	private VipLevel[] viplvlData;
	
	public PromotionCheckFrame(PromotionPanel promotionPanel, PromotionVO promotion) {
		this.promotionPanel=promotionPanel;
		present = promotion.getGift();
		promotionService = new PromotionBL();
		this.promotion = promotion;
		viplvlData = new VipLevel[]{VipLevel.LEVEL1, VipLevel.LEVEL2, VipLevel.LEVEL3, VipLevel.LEVEL4, VipLevel.LEVEL5};
		
		setSize(450, 330);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		GUIUtility.setCenter(this);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel_button = new JPanel();
		getContentPane().add(panel_button, BorderLayout.SOUTH);
		
		JButton button = new JButton("保存修改");
		button.addActionListener(new UpdateListener());
		panel_button.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel_button.add(button);
		
		JButton button_1 = new JButton("返回");
		button_1.addActionListener(new CloseListener());
		panel_button.add(button_1);
		
		JPanel panel_info = new JPanel();
		getContentPane().add(panel_info, BorderLayout.CENTER);
		panel_info.setLayout(new BorderLayout(0, 0));
		
		chooseDatePanel = new ChangeFutureDatePanel(promotion.getStartDate(), promotion.getEndDate());
		panel_info.add(chooseDatePanel, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		panel_info.add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 51, 102, 0, 0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel label = new JLabel("要求：");
		label.setFont(new Font("宋体", Font.PLAIN, 20));
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		panel.add(label, gbc_label);
		
		JLabel label_1 = new JLabel("客户等级：");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.anchor = GridBagConstraints.EAST;
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 1;
		gbc_label_1.gridy = 1;
		panel.add(label_1, gbc_label_1);
		
		comboBox_viplvl = new JComboBox<VipLevel>(viplvlData);
		GridBagConstraints gbc_comboBox_viplvl = new GridBagConstraints();
		gbc_comboBox_viplvl.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_viplvl.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_viplvl.gridx = 2;
		gbc_comboBox_viplvl.gridy = 1;
		panel.add(comboBox_viplvl, gbc_comboBox_viplvl);
		
		JLabel label_2 = new JLabel("总价：");
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.anchor = GridBagConstraints.EAST;
		gbc_label_2.insets = new Insets(0, 0, 5, 5);
		gbc_label_2.gridx = 1;
		gbc_label_2.gridy = 2;
		panel.add(label_2, gbc_label_2);
		
		textField_total = new JTextField();
		GridBagConstraints gbc_textField_total = new GridBagConstraints();
		gbc_textField_total.anchor = GridBagConstraints.WEST;
		gbc_textField_total.insets = new Insets(0, 0, 5, 5);
		gbc_textField_total.gridx = 2;
		gbc_textField_total.gridy = 2;
		panel.add(textField_total, gbc_textField_total);
		textField_total.setColumns(10);
		
		JLabel label_3 = new JLabel("优惠：");
		label_3.setFont(new Font("宋体", Font.PLAIN, 20));
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.insets = new Insets(0, 0, 5, 5);
		gbc_label_3.gridx = 0;
		gbc_label_3.gridy = 3;
		panel.add(label_3, gbc_label_3);
		
		JLabel lblNewLabel = new JLabel("折让：");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 4;
		panel.add(lblNewLabel, gbc_lblNewLabel);
		
		textField_discount = new JTextField();
		GridBagConstraints gbc_textField_discount = new GridBagConstraints();
		gbc_textField_discount.anchor = GridBagConstraints.WEST;
		gbc_textField_discount.insets = new Insets(0, 0, 5, 5);
		gbc_textField_discount.gridx = 2;
		gbc_textField_discount.gridy = 4;
		panel.add(textField_discount, gbc_textField_discount);
		textField_discount.setColumns(10);
		
		JLabel label_4 = new JLabel("折扣（%）：");
		GridBagConstraints gbc_label_4 = new GridBagConstraints();
		gbc_label_4.anchor = GridBagConstraints.EAST;
		gbc_label_4.insets = new Insets(0, 0, 5, 5);
		gbc_label_4.gridx = 1;
		gbc_label_4.gridy = 5;
		panel.add(label_4, gbc_label_4);
		
		textField_percent = new JTextField();
		GridBagConstraints gbc_textField_percent = new GridBagConstraints();
		gbc_textField_percent.anchor = GridBagConstraints.WEST;
		gbc_textField_percent.insets = new Insets(0, 0, 5, 5);
		gbc_textField_percent.gridx = 2;
		gbc_textField_percent.gridy = 5;
		panel.add(textField_percent, gbc_textField_percent);
		textField_percent.setColumns(10);
		
		JLabel label_5 = new JLabel("代金券：");
		GridBagConstraints gbc_label_5 = new GridBagConstraints();
		gbc_label_5.anchor = GridBagConstraints.EAST;
		gbc_label_5.insets = new Insets(0, 0, 5, 5);
		gbc_label_5.gridx = 1;
		gbc_label_5.gridy = 6;
		panel.add(label_5, gbc_label_5);
		
		textField_coupon = new JTextField();
		GridBagConstraints gbc_textField_coupon = new GridBagConstraints();
		gbc_textField_coupon.anchor = GridBagConstraints.WEST;
		gbc_textField_coupon.insets = new Insets(0, 0, 5, 5);
		gbc_textField_coupon.gridx = 2;
		gbc_textField_coupon.gridy = 6;
		panel.add(textField_coupon, gbc_textField_coupon);
		textField_coupon.setColumns(10);
		/*
		JButton button_2 = new JButton("赠品设置");
		button_2.addActionListener(new AddPresentListener());
		GridBagConstraints gbc_button_2 = new GridBagConstraints();
		gbc_button_2.anchor = GridBagConstraints.WEST;
		gbc_button_2.insets = new Insets(0, 0, 0, 5);
		gbc_button_2.gridx = 2;
		gbc_button_2.gridy = 7;
		panel.add(button_2, gbc_button_2);*/
		
		showData();
	}
	
	public void showData(){
		comboBox_viplvl.setSelectedItem(promotion.getViplvl());
		textField_total.setText(String.valueOf(promotion.getPriceLine()));
		textField_discount.setText(String.valueOf(promotion.getDiscount()));
		textField_percent.setText(String.valueOf(promotion.getPercent()));
		textField_coupon.setText(String.valueOf(promotion.getCoupon()));
		//修改时间
	}
	
	public void setPresent(PresentVO vo) {
		present = vo;
	}
	
	class UpdateListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String startDate = chooseDatePanel.getStartDate();
			String endDate = chooseDatePanel.getEndDate();
			VipLevel viplvl = (VipLevel)comboBox_viplvl.getSelectedItem();
			double priceLine = Double.parseDouble(textField_total.getText());
			double discount = Double.parseDouble(textField_discount.getText());
			int percent = Integer.parseInt(textField_percent.getText());
			double coupon = Double.parseDouble(textField_coupon.getText());
			
			PromotionVO vo = new PromotionVO(promotion.getID(), startDate, endDate, viplvl, priceLine, discount, percent, coupon, present);
			ResultMessage result = promotionService.update(vo);
			if(result == ResultMessage.SUCCESS){
				JOptionPane.showMessageDialog(null, "更新成功", "提示", JOptionPane.INFORMATION_MESSAGE);
				promotionPanel.showPromotion();
				dispose();
			}else if(result == ResultMessage.REMOTE_FAIL){
				JOptionPane.showMessageDialog(null, "网络连接错误", "错误", JOptionPane.ERROR_MESSAGE);
			}else if(result == ResultMessage.ITEM_EXIST){
				JOptionPane.showMessageDialog(null, "已存在相同的促销策略", "错误", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	
	class CloseListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();	
		}
		
	}
	
	/*
	class AddPresentListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			AddPresentFrame frame = new AddPresentFrame(thisFrame, present);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setVisible(true);
		}
		
	}*/

}
