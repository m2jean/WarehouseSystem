package gui.promotion;

import gui.GUIUtility;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import productBL.ProductBL;
import productBL.ProductItem;
import productBLService.ProductBLService;

import javax.swing.JLabel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JTextField;
import VO.ProductVO;
import VO.SpecialProductVO;

public class SpecialAddFrame extends JFrame implements AddProductInterface {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5910858993199577978L;
	private PromotionPanel panel;
	private SpecialAddFrame thisFrame;
	private SpecialDetailTableModel model;
	private double originalPrice;
	private JTable table;
	private JTextField textField;
	private JLabel label_originalPrice;
	
	public SpecialAddFrame(PromotionPanel panel){
		this.panel = panel;
		thisFrame = this;
		originalPrice = 0;
		
		setSize(450, 300);
		GUIUtility.setCenter(this);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.SOUTH);
		
		JButton button_3 = new JButton("确认");
		button_3.addActionListener(new ConfirmListener());
		panel_1.add(button_3);
		
		JButton button_4 = new JButton("返回");
		button_4.addActionListener(new CloseListener());
		panel_1.add(button_4);
		
		JPanel panel_2 = new JPanel();
		getContentPane().add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_3 = new JPanel();
		panel_2.add(panel_3, BorderLayout.SOUTH);
		
		JButton button = new JButton("添加商品");
		button.addActionListener(new AddListener());
		panel_3.add(button);
		
		JButton button_1 = new JButton("修改");
		button_1.addActionListener(new UpdateListener());
		panel_3.add(button_1);
		
		JButton button_2 = new JButton("删除商品");
		button_2.addActionListener(new DeleteListener());
		panel_3.add(button_2);
		
		JPanel panel_4 = new JPanel();
		panel_2.add(panel_4, BorderLayout.CENTER);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_5 = new JPanel();
		panel_4.add(panel_5, BorderLayout.SOUTH);
		GridBagLayout gbl_panel_5 = new GridBagLayout();
		gbl_panel_5.columnWidths = new int[]{38, 108, 61, 63, 108, 0};
		gbl_panel_5.rowHeights = new int[]{21, 0};
		gbl_panel_5.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_5.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_5.setLayout(gbl_panel_5);
		
		JLabel label = new JLabel("原价：");
		label.setFont(new Font("宋体", Font.PLAIN, 15));
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.EAST;
		gbc_label.fill = GridBagConstraints.VERTICAL;
		gbc_label.insets = new Insets(0, 0, 0, 5);
		gbc_label.gridx = 1;
		gbc_label.gridy = 0;
		panel_5.add(label, gbc_label);
		
		label_originalPrice = new JLabel("0.0");
		label_originalPrice.setFont(new Font("宋体", Font.PLAIN, 15));
		GridBagConstraints gbc_label_originalPrice = new GridBagConstraints();
		gbc_label_originalPrice.anchor = GridBagConstraints.WEST;
		gbc_label_originalPrice.fill = GridBagConstraints.VERTICAL;
		gbc_label_originalPrice.insets = new Insets(0, 0, 0, 5);
		gbc_label_originalPrice.gridx = 2;
		gbc_label_originalPrice.gridy = 0;
		panel_5.add(label_originalPrice, gbc_label_originalPrice);
		
		JLabel label_2 = new JLabel("售价：");
		label_2.setFont(new Font("宋体", Font.PLAIN, 15));
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.anchor = GridBagConstraints.EAST;
		gbc_label_2.fill = GridBagConstraints.VERTICAL;
		gbc_label_2.insets = new Insets(0, 0, 0, 5);
		gbc_label_2.gridx = 3;
		gbc_label_2.gridy = 0;
		panel_5.add(label_2, gbc_label_2);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.anchor = GridBagConstraints.WEST;
		gbc_textField.fill = GridBagConstraints.VERTICAL;
		gbc_textField.gridx = 4;
		gbc_textField.gridy = 0;
		panel_5.add(textField, gbc_textField);
		textField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		panel_4.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
		
		showTable();
	}
	
	public void showTable(){
		model = new SpecialDetailTableModel(null);
		table.setModel(model);
	}
	
	public void refresh(){
		model.fireTableDataChanged();
		label_originalPrice.setText(String.valueOf(originalPrice));
	}
	
	public void addProduct(ProductItem item, double price) throws ItemExistException {
		model.addItem(item);
		originalPrice += price;
		refresh();
	}
	
	public void updateProduct(ProductItem item, double priceDifference){
		model.updateItem(item);
		originalPrice += priceDifference;
		refresh();
	}
	
	class AddListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			ChooseProductFrame frame = new ChooseProductFrame(thisFrame);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setVisible(true);
		}
		
	}
	
	class UpdateListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(table.getSelectedRowCount() == 0){
				return;
			}
			
			int row = table.getSelectedRow();
			if(row < 0){
				return;
			}
			ProductItem item = model.getItem(row);
			ChangeProductFrame frame = new ChangeProductFrame(thisFrame, item);
			frame.setVisible(true);
		}
		
	}
	
	class DeleteListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(table.getSelectedRowCount() == 0){
				return;
			}
			
			int row = table.getSelectedRow();
			if(row < 0){
				return;
			}
			ProductItem pi = model.getItem(row);
			ProductBLService service = new ProductBL();
			ProductVO vo = service.getProduct(new ProductVO(pi.getProductID())).data;
			model.deleteItem(row);
			model.fireTableDataChanged();
			originalPrice -= vo.getPriceOut() * pi.getNumber();
			label_originalPrice.setText(String.valueOf(originalPrice));
		}
		
	}
	
	class ConfirmListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			double price;
			try{
				price = Double.parseDouble(textField.getText());
			}catch(Exception ex){
				JOptionPane.showMessageDialog(null, "请正确输入售价", "错误", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if(price <= 0){
				JOptionPane.showMessageDialog(null, "售价必须大于0", "错误", JOptionPane.ERROR_MESSAGE);
			}else{
				ArrayList<ProductItem> itemList = model.getItemList();
				if(itemList == null || itemList.size() == 0){
					JOptionPane.showMessageDialog(null, "未添加任何商品", "错误", JOptionPane.ERROR_MESSAGE);
				}else{
					SpecialProductVO vo = new SpecialProductVO(null,null, itemList, originalPrice, price, 0);
					GetNameFrame frame = new GetNameFrame(thisFrame, vo, panel);
					frame.setVisible(true);
				}
			}
		}
		
	}
	
	class CloseListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
		
	}

}
