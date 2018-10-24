package gui.promotion;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTabbedPane;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JTable;

import productBL.ProductBL;
import productBLService.ProductBLService;
import enums.DataMessage;
import enums.ResultMessage;
import gui.GUIUtility;
import businesslogic.promotionbl.PromotionBL;
import businesslogicservice.promotionblservice.PromotionBLService;
import VO.PromotionVO;
import VO.SpecialProductVO;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class PromotionPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9177540667321216518L;
	private PromotionBLService promotionService;
	private JTable table_promotion;
	private PromotionListTableModel promotionModel;
	private ProductBLService productService;
	private JTable table_product;
	private SpecialPackageTableModel productModel;
	
	private PromotionPanel thispanel;      //自身的引用
	
	public PromotionPanel() {
		thispanel = this;
		promotionService = new PromotionBL();
		productService = new ProductBL();
		
		setSize(800, 640);
		setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel_promotion = new JPanel();
		tabbedPane.addTab("促销策略", null, panel_promotion, null);
		panel_promotion.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_promotion.add(scrollPane);
		
		table_promotion = new JTable();
		table_promotion.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table_promotion);
		
		JPanel panel = new JPanel();
		panel_promotion.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JButton button = new JButton("添加促销策略");
		button.addActionListener(new PromotionAddListener());
		panel.add(button);
		
		JButton button_1 = new JButton("查看/修改");
		button_1.addActionListener(new PromotionCheckListener());
		panel.add(button_1);
		
		JButton button_2 = new JButton("删除");
		button_2.addActionListener(new PromotionDeleteListener());
		panel.add(button_2);
		
		JButton button_6 = new JButton("设置折让权限");
		button_6.addActionListener(new DiscountPermissionListener());
		panel.add(button_6);
		
		JPanel panel_1 = new JPanel();
		panel_promotion.add(panel_1, BorderLayout.NORTH);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{724, 57, 0};
		gbl_panel_1.rowHeights = new int[]{23, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JButton btnNewButton = new JButton("刷新");
		btnNewButton.addActionListener(new PromotionRefreshListener());
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 0;
		panel_1.add(btnNewButton, gbc_btnNewButton);
		
		JPanel panel_specialPackage = new JPanel();
		tabbedPane.addTab("制定特价包", null, panel_specialPackage, null);
		panel_specialPackage.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_specialPackage.add(panel_2, BorderLayout.NORTH);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{724, 57, 0};
		gbl_panel_2.rowHeights = new int[]{23, 0};
		gbl_panel_2.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		JButton button_3 = new JButton("刷新");
		button_3.addActionListener(new ProductRefreshListener());
		GridBagConstraints gbc_button_3 = new GridBagConstraints();
		gbc_button_3.anchor = GridBagConstraints.NORTHWEST;
		gbc_button_3.gridx = 1;
		gbc_button_3.gridy = 0;
		panel_2.add(button_3, gbc_button_3);
		
		JPanel panel_3 = new JPanel();
		panel_specialPackage.add(panel_3, BorderLayout.SOUTH);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));
		
		JButton btnNewButton_1 = new JButton("添加特价包");
		btnNewButton_1.addActionListener(new ProductAddListener());
		panel_3.add(btnNewButton_1);
		
		JButton button_4 = new JButton("查看/修改");
		button_4.addActionListener(new ProductCheckListener());
		panel_3.add(button_4);
		
		JButton button_5 = new JButton("删除");
		button_5.addActionListener(new ProductDeleteListener());
		panel_3.add(button_5);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel_specialPackage.add(scrollPane_1, BorderLayout.CENTER);
		
		table_product = new JTable();
		table_product.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_1.setViewportView(table_product);
		
		showData();
	}
	
	public void showData(){
		DataMessage<ArrayList<PromotionVO>> message1 = promotionService.get();
		ArrayList<PromotionVO> list1 = message1.data;
		showPromotionTable(list1);
		
		DataMessage<ArrayList<SpecialProductVO>> message2 = productService.getAllSpecialProduct();
		ArrayList<SpecialProductVO> list2 = message2.data;
		showProductTable(list2);
		
		if(message1.resultMessage == ResultMessage.REMOTE_FAIL ||
				message2.resultMessage == ResultMessage.REMOTE_FAIL){
			JOptionPane.showMessageDialog(null, "网络连接出错", "错误", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void showPromotion(){
		DataMessage<ArrayList<PromotionVO>> message = promotionService.get();
		ArrayList<PromotionVO> list;
		if(message.resultMessage == ResultMessage.REMOTE_FAIL){
			JOptionPane.showMessageDialog(null, "网络连接错误", "错误", JOptionPane.ERROR_MESSAGE);
			list=null;
		}else if(message.resultMessage == ResultMessage.ITEM_NOT_EXIST){
			JOptionPane.showMessageDialog(null, "没有可用的促销策略", "提示", JOptionPane.INFORMATION_MESSAGE);
			list=null;
		}else{
			list = message.data;
		}
		
		showPromotionTable(list);
	}
	
	public void showSpecialPackage(){
		DataMessage<ArrayList<SpecialProductVO>> message = productService.getAllSpecialProduct();
		ArrayList<SpecialProductVO> list;
		if(message.resultMessage == ResultMessage.REMOTE_FAIL){
			JOptionPane.showMessageDialog(null, "网络连接错误", "错误", JOptionPane.ERROR_MESSAGE);
			list=null;
		}else if(message.resultMessage == ResultMessage.IS_EMPTY){
			JOptionPane.showMessageDialog(null, "没有可用的特价包", "提示", JOptionPane.INFORMATION_MESSAGE);
			list=null;
		}else{
			list = message.data;
		}
		
		showProductTable(list);
	}
	
	public void showPromotionTable(ArrayList<PromotionVO> list){
		promotionModel = new PromotionListTableModel(list);
		table_promotion.setModel(promotionModel);
	}
	
	public void showProductTable(ArrayList<SpecialProductVO> list){
		productModel = new SpecialPackageTableModel(list);
		table_product.setModel(productModel);
	}
	
	class PromotionRefreshListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			showPromotion();
		}	
	}
	
	class ProductRefreshListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			showSpecialPackage();
		}	
	}
	
	
	class PromotionAddListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			PromotionAddFrame frame = new PromotionAddFrame(thispanel);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setVisible(true);
		}
		
	}
	
	class PromotionCheckListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(table_promotion.getSelectedRowCount() == 0){
				return;
			}
			
			int row = table_promotion.getSelectedRow();
			if(row < 0){
				return;
			}
			PromotionVO vo = ((PromotionListTableModel)table_promotion.getModel()).getPromotion(row);
			PromotionCheckFrame frame = new PromotionCheckFrame(thispanel, vo);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setVisible(true);
		}
		
	}
	
	class PromotionDeleteListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(table_promotion.getSelectedRowCount() == 0){
				return;
			}
			
			int row = table_promotion.getSelectedRow();
			if(row < 0){
				return;
			}
			PromotionVO vo = ((PromotionListTableModel)table_promotion.getModel()).getPromotion(row);
			ResultMessage message = promotionService.delete(vo);
			if(message == ResultMessage.SUCCESS){
				JOptionPane.showMessageDialog(null, "删除成功", "提示", JOptionPane.INFORMATION_MESSAGE);
				showPromotion();
			}else if(message == ResultMessage.ITEM_NOT_EXIST){
				JOptionPane.showMessageDialog(null, "促销策略不存在", "错误", JOptionPane.ERROR_MESSAGE);
			}else if(message == ResultMessage.REMOTE_FAIL){
				JOptionPane.showMessageDialog(null, "网络连接错误", "错误", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	
	class ProductAddListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			SpecialAddFrame frame = new SpecialAddFrame(thispanel);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setVisible(true);		
		}
		
	}
	
	class ProductCheckListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(table_product.getSelectedRowCount() == 0){
				return;
			}
			
			int row = table_product.getSelectedRow();
			if(row < 0){
				return;
			}
			SpecialProductVO product = productModel.getProduct(row).clone();
			SpecialCheckFrame frame = new SpecialCheckFrame(thispanel, product);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setVisible(true);	
		}
		
	}
	
	class ProductDeleteListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(table_product.getSelectedRowCount() == 0){
				return;
			}
			
			ProductBLService service = new ProductBL();
			int row = table_product.getSelectedRow();
			if(row < 0){
				return;
			}
			SpecialProductVO vo = productModel.getProduct(row);
			ResultMessage message = service.deleteSpecialProduct(vo);
			
			if(message == ResultMessage.SUCCESS){
				JOptionPane.showMessageDialog(null, "删除成功", "提示", JOptionPane.INFORMATION_MESSAGE);
				showSpecialPackage();
			}else if(message == ResultMessage.ITEM_NOT_EXIST){
				JOptionPane.showMessageDialog(null, "特价包不存在", "错误", JOptionPane.ERROR_MESSAGE);
			}else if(message == ResultMessage.REMOTE_FAIL){
				JOptionPane.showMessageDialog(null, "网络连接错误", "错误", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	
	class DiscountPermissionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			DiscountPermissionFrame frame = new DiscountPermissionFrame();
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setVisible(true);
		}
		
	}
	
	
	
	
	public static void main(String[] args){
		JFrame frame = new JFrame();
		PromotionPanel panel = new PromotionPanel();
		frame.setSize(800, 640);
		GUIUtility.setCenter(frame);
		frame.getContentPane().add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	

}
