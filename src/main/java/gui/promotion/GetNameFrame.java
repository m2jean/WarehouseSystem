package gui.promotion;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JButton;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.Font;

import javax.swing.JTextField;

import enums.ResultMessage;
import gui.GUIUtility;
import productBL.ProductBL;
import productBL.ProductItem;
import productBLService.ProductBLService;
import VO.SpecialProductVO;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GetNameFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8294900593946506206L;
	private JFrame frame;
	private PromotionPanel mainPanel;
	private SpecialProductVO product;
	private JTextField textField;
	
	public GetNameFrame(JFrame frame, SpecialProductVO product, PromotionPanel mainPanel) {
		this.frame = frame;
		this.mainPanel = mainPanel;
		this.product = product;
		
		GUIUtility.setCenter(this);
		setSize(350, 150);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		
		JButton button = new JButton("确定");
		button.addActionListener(new ConfirmListener());
		panel.add(button);
		
		JButton button_1 = new JButton("返回");
		button_1.addActionListener(new CloseListener());
		panel.add(button_1);
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.CENTER);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{14, 0, 0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JLabel label = new JLabel("特价包名：");
		label.setFont(new Font("宋体", Font.PLAIN, 15));
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 0, 5);
		gbc_label.anchor = GridBagConstraints.EAST;
		gbc_label.gridx = 1;
		gbc_label.gridy = 1;
		panel_1.add(label, gbc_label);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 0, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 1;
		panel_1.add(textField, gbc_textField);
		textField.setColumns(10);
		
		setDefaultName();
	}
	
	private void setDefaultName(){
		StringBuffer name = new StringBuffer();
		boolean first = true;
		for(ProductItem item:product.getProductList()){
			if(first){
				name.append(item.getProductName());
				first = false;
			}else{
				name.append("·"+item.getProductName());
			}
		}
		
		textField.setText(name.toString());
	}
	
	class ConfirmListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			ProductBLService service = new ProductBL();
			String name = textField.getText();
			if(name.equals("")){
				JOptionPane.showMessageDialog(null, "特价包名不能为空", "错误", JOptionPane.ERROR_MESSAGE);
				return;
			}else{
				product.setName("（特价包）"+name);
				ResultMessage result = service.addSpecialProduct(product);
				
				if(result == ResultMessage.SUCCESS){
					JOptionPane.showMessageDialog(null, "添加成功", "提示", JOptionPane.INFORMATION_MESSAGE);
					mainPanel.showSpecialPackage();
					frame.dispose();
					dispose();
				}else if(result == ResultMessage.REMOTE_FAIL){
					JOptionPane.showMessageDialog(null, "网络连接错误", "错误", JOptionPane.ERROR_MESSAGE);
				}else if(result == ResultMessage.ITEM_EXIST){
					JOptionPane.showMessageDialog(null, "已存在相同的特价包", "错误", JOptionPane.ERROR_MESSAGE);
					dispose();
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
